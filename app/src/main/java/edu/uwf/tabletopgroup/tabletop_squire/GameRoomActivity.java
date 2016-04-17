package edu.uwf.tabletopgroup.tabletop_squire;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import edu.uwf.tabletopgroup.rest.TableTopRestClient;

/*

 */



public class GameRoomActivity extends FragmentActivity {

    @Deprecated
    private Socket connection;
    {
        if(SocketManager.connection == null || !SocketManager.connection.connected()) {
            try {

                Log.d(TAG, "Before socket creation");
                SocketManager.connection = IO.socket(TableTopRestClient.getBaseUrl());
                Log.d(TAG, "After socket creation");

            } catch (URISyntaxException ex) {
                Log.e(TAG, "Socket error: ", ex);
            } finally {
                connection = SocketManager.connection;
            }
        }
    }

    @Deprecated
    private Socket mSocket;

    private EditText chattext;
    private Fragment chatbox;
    private Fragment userList;
    public static final String TAG = "GameRoom";
    private EditText roomID;
    private EditText role;
    private Button startRoom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_room);

        chattext = (EditText) findViewById(R.id.chatbox);
        chatbox = getFragmentManager().findFragmentById(R.id.chatbox);
        userList = getFragmentManager().findFragmentById(R.id.userlist_frag);
        roomID = (EditText)findViewById(R.id.roomID);
        role = (EditText)findViewById(R.id.role);

        SocketManager.connection.connect();
        Log.d(TAG, "After connect");
        SocketManager.connection.emit("join", SocketManager.connection);
        Log.d(TAG, "Emit join");
        startRoom = (Button)findViewById(R.id.startRoom);
        startRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocketManager.connection.emit("createNewGame", SocketManager.connection);
                Log.d(TAG, "emitted createNewGame");

                SocketManager.connection.on("roomData", onRoomData);
                SocketManager.connection.on("roleData", onRoleData);
                SocketManager.connection.on("playerJoined", onPlayerJoined);
                SocketManager.connection.on("chat", onChat);
                SocketManager.connection.on("playerQuit", onPlayerQuit);
            }
        });

    }


    private Emitter.Listener onRoomData = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject obj = (JSONObject) args[0];
                    String gameID;
                    try {
                        gameID = obj.getString("gameID");
                        roomID.setText(gameID);
                        SocketManager.connection.emit("newGameCreated", obj);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON error: ", e);
                    }
                }

            });
        }
    };

    private Emitter.Listener onRoleData = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject obj = (JSONObject) args[0];
                    String gameRole;
                    try {
                        gameRole = obj.getString("gameRole");
                        role.setText(gameRole);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON error: ", e);
                    }
                }

            });
        }
    };

    @Deprecated
    protected Socket getConnection()
    {
        return connection;
    }

    /**
     * Player joins the room
     */
    private Emitter.Listener onPlayerJoined = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String user = data.getString("username");
                        chattext.append("\n " + user + "has joined the room.");

                        // add user to userlist here

                    } catch (JSONException e) {
                        // meh, so what...
                    }
                    finally
                    {
                    }

                }
            });
        }
    };

    /**
     * a player has left the room....
     */
    private Emitter.Listener onPlayerQuit = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String user = data.getString("username");
                        chattext.append("\n " + user + "has left the room.");
                    } catch (JSONException e) {
                        // meh, so what...
                    }

                }
            });
        }
    };

    /**
     * Respond to general chat text, should receive player name and chat text.
     */
    private Emitter.Listener onChat = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String username = data.getString("username");
                        String message = data.getString("chattext");

                        chattext.append("\n[" + username + "] " + message);
                    } catch (JSONException e) {
                    }

                }
            });
        }
    };

    @Override
    public void onPause()
    {
        super.onPause();

        // Notify server that client is no longer focused
        //SocketManager.connection.emit("sleeping");
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        SocketManager.connection.disconnect();
        SocketManager.connection.off();
        SocketManager.connection = null;
    }
}

;