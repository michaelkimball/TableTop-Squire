package edu.uwf.tabletopgroup.tabletop_squire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;

import edu.uwf.tabletopgroup.models.User;
import edu.uwf.tabletopgroup.rest.SocketService;
import edu.uwf.tabletopgroup.rest.TableTopRestClientUser;
import edu.uwf.tabletopgroup.tabletop_squire.create_character.CreateCharacterActivity;
import edu.uwf.tabletopgroup.tabletop_squire.game_lobby.GameLobbyActivity;
import edu.uwf.tabletopgroup.tabletop_squire.view_account.ViewAccountActivity;
import edu.uwf.tabletopgroup.tabletop_squire.view_character.ViewCharacterActivity;
import edu.uwf.tabletopgroup.tabletop_squire.view_character.ViewCharacterFragment;
import edu.uwf.tabletopgroup.R;

public class WelcomeActivity extends Activity {
    private static final String TAG = "WelcomeActivity";
    private Button viewCharacter;
    private Button createCharacter;
    private TableTopRestClientUser client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        try{
            client = TableTopRestClientUser.getClientUser(this);
        }catch(Exception e){
            Log.e(TAG, "onCreate()", e);
        }
        viewCharacter = (Button)findViewById(R.id.btn_character);
        viewCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startViewCharacter();
            }
        });
        createCharacter = (Button)findViewById(R.id.btn_create_character);
        createCharacter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreateCharacter();
            }
        });
        Intent i = new Intent(getApplicationContext(), SocketService.class);
        startService(i);
        try {
            client.getCharacters(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    return false;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void startViewCharacter(){
        try {
            client.getCharacters(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (msg.what == TableTopRestClientUser.SUCCESS_MESSAGE) {
                        Log.d(TAG, "Characters acquired");
                        if (User.getCharacters() != null) {
                            Intent intent = new Intent(getApplicationContext(), ViewCharacterActivity.class);
                            intent.putExtra(ViewCharacterFragment.CHARACTER_KEY, User.getCharacters().get(0).getId());
                            startActivity(intent);
                        }
                    } else {
                        Log.e(TAG, "Error acquiring characters!");
                    }
                    return false;
                }
            });
        }catch(JSONException je){
            Log.e(TAG, "startViewCharacter()", je);
        }

    }

    private void startCreateCharacter(){
        Intent intent = new Intent(this, CreateCharacterActivity.class);
        startActivity(intent);
    }

    public void btn_create_game_room_onClick(View view) {
        Intent intent = new Intent(this, GameLobbyActivity.class);
        startActivity(intent);
    }

    public void btn_join_game_room_onClick(View view) {
        JoinGameByIdDialog dialog = new JoinGameByIdDialog();
        dialog.show(getFragmentManager(), "join_game");
    }

    public void btn_view_account_onClick(View view){
        Intent intent = new Intent(getApplicationContext(), ViewAccountActivity.class);
        startActivity(intent);
    }
}
