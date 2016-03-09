package edu.uwf.tabletopgroup.tabletop_squire;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import edu.uwf.tabletopgroup.models.User;

public class WelcomeActivity extends Activity {
    private Button viewCharacter;
    private Button createCharacter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
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
    }

    private void startViewCharacter(){
        if(User.getCharacters() != null) {
            Intent intent = new Intent(this, ViewCharacterActivity.class);
            intent.putExtra(ViewCharacterFragment.CHARACTER_KEY, User.getCharacters().get(0).getId());
            startActivity(intent);
        }
    }

    private void startCreateCharacter(){
        Intent intent = new Intent(this, CreateCharacterActivity.class);
        startActivity(intent);
    }

    public void btn_create_game_room_onClick(View view) {
    }

    public void btn_join_game_room_onClick(View view) {
    }
}
