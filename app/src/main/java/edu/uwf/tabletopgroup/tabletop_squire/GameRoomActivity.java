package edu.uwf.tabletopgroup.tabletop_squire;

import android.app.Fragment;
import android.os.Bundle;
import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.EditText;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.*;
import com.github.nkzawa.socketio.client.IO;

import org.json.JSONObject;

import java.net.URISyntaxException;

public class GameRoomActivity extends Activity implements Emitter.Listener {

    private Socket connection;
    {
        try {
            connection = IO.socket("localhost");
        }
        catch( URISyntaxException ex)
        {

        }
    }

    private EditText chattext;
    private android.app.Fragment chatbox;
    private Fragment userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make the connection.....
        connection.connect();

        // Get references to our fragments for control ;)
        chatbox = getFragmentManager().findFragmentById(R.id.chatbox_frag);
        userlist = getFragmentManager().findFragmentById(R.id.userlist_frag);

        // Direct access to the editbox, hopefully
        chattext = (EditText) chatbox.getActivity().findViewById(R.id.chatbox);


        // Begin processing messages from SocketIO
        connection.on("message", this);

        // set the view
        setContentView(R.layout.game_room);
    }

    public Socket getConnection()
    {
        return connection;
    }

    /**
     * Called in response to all SocketIO messages 'message'
     * @param args
     */
    @Override
    public void call(Object... args) {
        // Parse ellipted args, treat as JSON
        JSONObject data = (JSONObject) args[0];




    }
}
