package edu.uwf.tabletopgroup.tabletop_squire;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import edu.uwf.tabletopgroup.rest.TableTopRestClient;

/**
 * Created by michael on 2/29/16.
 */
public class JoinRoomFragment extends Fragment {
    public static final String TAG = "JoinRoom";
    private Socket mSocket;
    private Button joinRoom;
    private EditText gameID;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_join_room, container, false);

        gameID = (EditText)v.findViewById(R.id.game_id);
        joinRoom = (Button)v.findViewById(R.id.join);

        Log.d(TAG, "Before socket creation");
        try {
            mSocket = IO.socket(TableTopRestClient.getBaseUrl());
        } catch (URISyntaxException e) {
            Log.e(TAG, "Socket error: ", e);
        }
        Log.d(TAG, "After socket creation");

        mSocket.connect();
        Log.d(TAG, "After connect");
        mSocket.emit("join", mSocket);
        Log.d(TAG, "Emit join");

        return v;
    }

}
