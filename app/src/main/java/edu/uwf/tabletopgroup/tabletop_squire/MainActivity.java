package edu.uwf.tabletopgroup.tabletop_squire;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import edu.uwf.tabletopgroup.models.User;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!User.isLoggedIn)
        {
            setContentView(R.layout.activity_main);
        }
        else
        {
            setContentView(R.layout.welcome_activity);
        }
    }

    /**
     * Called from /res/layout/fragment_main.xml
     *
     * @see /res/layout/fragment_main.xml
     *
     * @param view
     */
    public void btn_login_onClick(View view) {
        Intent i = new Intent(getApplicationContext(), AuthActivity.class);
        startActivity(i);
    }

    /**
     * Called from /res/layout/fragment_main.xml
     * @param view
     */
    public void btn_register_onClick(View view) {
        Intent i = new Intent(getApplicationContext(), UserRegisterActivity.class);
        startActivity(i);
    }

    public void btn_join_game_room_onClick(View view) {
    }

    public void btn_test_welcome_onClick(View view) {
        Intent i = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(i);
    }
}
