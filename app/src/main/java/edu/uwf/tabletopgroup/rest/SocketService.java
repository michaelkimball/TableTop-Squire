package edu.uwf.tabletopgroup.rest;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

import edu.uwf.tabletopgroup.models.*;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import edu.uwf.tabletopgroup.models.Character;
/**
 * TableTopSquire
 * SocketService.java
 *
 * TODO: Write Description
 *
 * Created By Michael Kimball
 * On 4/15/16
 */
public class SocketService extends Service {
    private static final String TAG = "SocketService";
    private IBinder iBinder = new SocketServiceBinder();
    private Socket socket;
    private String player;
    public void setPlayer(String player){
        this.player = player;
    }
    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e(TAG, "onReceive " + action);
            try {
                connectToSocketIO();
            }catch(URISyntaxException e){
                e.printStackTrace();
            }
            switch(action){
                case TableTopKeys.ACTION_CREATE_GAME:
                    try {
                        String json = intent.getStringExtra(TableTopKeys.KEY_GAME);
                        JSONObject object = new JSONObject(json);
                        String playerName = object.getString(TableTopKeys.KEY_PLAYER);
                        setPlayer(playerName);
                        object.put("socketID", socket.id());
                        socket.emit("createNewGame", object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case TableTopKeys.ACTION_SEND_MESSAGE_TO_ROOM:
                    try{
                        Message message = intent.getParcelableExtra(TableTopKeys.KEY_MESSAGE);
                        JSONObject json = message.toJSON();
                        Log.d(TAG, json.toString());
                        json.put("socketID", socket.id());
                        socket.emit("messageRoom", json);
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                    break;
                case TableTopKeys.ACTION_JOIN_GAME:
                    try {
                        String gameId = intent.getStringExtra(TableTopKeys.KEY_GAME_ID);
                        String playerName = intent.getStringExtra(TableTopKeys.KEY_PLAYER);
                        setPlayer(playerName);
                        String characterName = intent.getStringExtra(TableTopKeys.KEY_CHARACTER);
                        JSONObject object = new JSONObject();
                        object.put("gameID", gameId);
                        object.put("playerName", playerName);
                        object.put("characterName", characterName);
                        object.put("socketID", socket.id());
                        socket.emit("playerJoinGame", object);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter filter = new IntentFilter();
        filter.addAction(TableTopKeys.ACTION_CREATE_GAME); //further more
        filter.addAction(TableTopKeys.ACTION_SEND_MESSAGE_TO_ROOM);
        filter.addAction(TableTopKeys.ACTION_JOIN_GAME);
        registerReceiver(receiver, filter);
    }

    private void connectToSocketIO() throws URISyntaxException {
        if(socket == null)
            socket = IO.socket(TableTopRestClient.BASE_URL);
        if(!socket.connected()) {
            socket.on("message", new OnMessage());
            socket.on("roomData", new OnRoomData());
            socket.on("playerJoinedRoom", new OnJoinedGame());
            socket.connect();
            socket.emit("join");
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            connectToSocketIO();
        } catch (URISyntaxException e) {
            Log.e(TAG, "onStartCommand", e);
        }
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Disconnected from socket");
        socket.disconnect();
        unregisterReceiver(receiver);
    }

    public class SocketServiceBinder extends Binder {
        public SocketService getService(){
            return SocketService.this;
        }
    }


    private class OnMessage implements Emitter.Listener{
        @Override
        public void call(Object... args) {
            Log.d(TAG, "Message has been received by " + player);
            JSONObject data = (JSONObject) args[0];
            try {
                Intent i = new Intent(TableTopKeys.ACTION_MESSAGE_RECEIVED);
                i.putExtra("playerName", data.getString("playerName"));
                i.putExtra("message", data.getString("message"));
                sendBroadcast(i);
                Log.e(TAG, data.getString("message"));
            } catch (JSONException e) {
                Log.e(TAG, "OnMessage JSON error", e);
            }
        }
    }
    private class OnRoomData implements Emitter.Listener{
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            try {
                Log.e(TAG, "OnRoomData");
                String gameId = data.getString("gameID");
                String socketId = data.getString("socketID");
                Game game = new Game(gameId, socketId);
                Bundle bundle = new Bundle();
                bundle.putParcelable(TableTopKeys.KEY_GAME, game);
                Intent i = new Intent(TableTopKeys.ACTION_GAME_CREATED);
                i.putExtra(TableTopKeys.KEY_GAME, bundle);
                sendBroadcast(i);
            } catch (JSONException e) {
                Log.e(TAG, "OnRoomData JSON error", e);
            }
        }
    }

    private class OnJoinedGame implements Emitter.Listener {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            try {
                Log.e(TAG, "OnJoinedGame");
                String gameId = data.getString("gameID");
                String socketId = data.getString("socketID");
                JSONArray playerListJSON = data.getJSONArray("players");
                ArrayList<Player> players = new ArrayList<>();
                for(int i = 0; i < playerListJSON.length(); i++){
                    JSONObject playerJSON = playerListJSON.getJSONObject(i);
                    Player player = new Player();
                    player.setName(playerJSON.getString(TableTopKeys.KEY_NAME));
                    Character character = new Character(playerJSON.getString(TableTopKeys.KEY_CHARACTER));
                    player.setCharacter(character);
                    players.add(player);
                }
                Game game = new Game(gameId, socketId);
                Bundle bundle = new Bundle();
                bundle.putParcelable(TableTopKeys.KEY_GAME, game);
                bundle.putParcelableArrayList(TableTopKeys.KEY_PLAYER_LIST, players);
                Intent i = new Intent(TableTopKeys.ACTION_GAME_JOINED);
                i.putExtra(TableTopKeys.KEY_GAME, bundle);
                sendBroadcast(i);
            } catch (JSONException e) {
                Log.e(TAG, "OnRoomData JSON error", e);
            }
        }
    }
}
