package edu.uwf.tabletopgroup.tabletop_squire;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import edu.uwf.tabletopgroup.rest.TableTopRestClient;

public class GameRoom extends Activity {
    public static final String TAG = "GameRoom";
    private Socket mSocket;
    private EditText roomID;
    private EditText role;
    private Button startRoom;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_room);
        roomID = (EditText)findViewById(R.id.roomID);
        role = (EditText)findViewById(R.id.role);
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
        startRoom = (Button)findViewById(R.id.startRoom);
        startRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSocket.emit("createNewGame", mSocket);
                Log.d(TAG, "emitted createNewGame");

                mSocket.on("roomData", new Emitter.Listener() {

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
                                    mSocket.emit("newGameCreated", obj);
                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON error: ", e);
                                    return;
                                }
                            }

                        });
                    }
                });
                mSocket.on("roleData", new Emitter.Listener() {

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
                                    return;
                                }
                            }

                        });
                    }
                });
            }
        });

    }
}
