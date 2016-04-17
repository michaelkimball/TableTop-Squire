package edu.uwf.tabletopgroup.tabletop_squire;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/*

 */



public class GameRoomActivity extends FragmentActivity {

    private Socket connection;
    {
        try {
            connection = IO.socket("localhost"); // Where is the server addy kept globally?
        }
        catch( URISyntaxException ex)
        {

        }
    }

    private EditText chattext;
    private android.app.Fragment chatbox;
    private Fragment userlist;
    private int socketID;
    private int roomID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get room id from calling activity
        long roomID = 10001; // testing room number

        JSONObject data = new JSONObject();
        try {
            data.put("roomID", roomID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        connection.emit("playerJoinGame"); // Send a request to join the room

        // Get references to our fragments for control ;)
        chatbox = getFragmentManager().findFragmentById(R.id.chatbox_frag);
        userlist = getFragmentManager().findFragmentById(R.id.userlist_frag);

        // Direct access to the editbox, hopefully
        chattext = (EditText) chatbox.getActivity().findViewById(R.id.chatbox);

        // Begin processing messages from SocketIO
        connection.on("message", onChatMessage);
        connection.on("playerJoinedRoom", onPlayerJoined);
//        connection.on("playerJoinGame", playerJoin)
//        connection.on("playerLeave", onPlayerLeave);
//        connection.on("roomData", onRoomData);
//        connection.on("error", onError);
//        connection.on("roleData", onRoleData);

        // set the view
        setContentView(R.layout.game_room);
    }

    protected Socket getConnection()
    {
        return connection;
    }

    /**
     * Respond to successful connection....
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
                        chattext.append("\n " );
                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };

    /**
     * Respond to general chat text, should receive player name and chat text.
     */
    private Emitter.Listener onChatMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String username = data.getString("username");
                        String message = data.getString("chattext");

                        message = data.getString("text");
                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };


    private void playerQuit(JSONObject data) throws JSONException {
        String userName = data.getString("user_name");
        String characterName = data.getString("character_name");
        String customMessage = data.getString("quit_message");

        // Remove player from userlist
        // userList.remove(characterName); // Use charactername as key?

        chattext.append(characterName + " has left the game. (" + customMessage + ") ");
    }

    private void playerJoin(JSONObject data) throws JSONException {
        String userName = data.getString("user_name");
        String characterName = data.getString("character_name");
        int characterHP = data.getInt("health");
        String customMessage = data.getString("join_message");

        // Add user to player list.....

        // userList.add(data.getString("userName"), data.getString("characterName"));

        // Display chat message to console (assume everybody else already got this same message
        chattext.append(characterName + "( " + userName + " ) has joined the room.");

    }

    /**
     * updates chat box
     *
     * @param data JSON data from caller
     * @throws JSONException let the calling event handler deal with exceptions for now
     */
    private void chatMessage(JSONObject data) throws JSONException {
        String characterName = data.getString("character_name");
        String message = data.getString("text");

        chattext.append(characterName + " : " + message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        connection.disconnect();
        connection.off("message", onChatMessage);
    }
}

;