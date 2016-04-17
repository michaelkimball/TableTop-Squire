package edu.uwf.tabletopgroup.tabletop_squire.game_room;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import edu.uwf.tabletopgroup.R;
import edu.uwf.tabletopgroup.models.Game;
import edu.uwf.tabletopgroup.rest.TableTopKeys;

/**
 * TableTopSquire
 * GameRoomActivity.java
 *
 * TODO: Write Description
 *
 * Created By Michael Kimball
 * On 4/16/16
 */
public class GameRoomActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_switch);
        boolean mDualPane = findViewById(R.id.dual_pane)!=null;

        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(R.id.fragment_container)==null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_container, new PlayerListFragment()).commit();
        }
        if(mDualPane && fm.findFragmentById(R.id.details_container)==null) {
            GameRoomFragment fragment = new GameRoomFragment();
            Intent currentIntent = getIntent();
            Bundle args = currentIntent.getBundleExtra(TableTopKeys.KEY_GAME);
            fragment.setArguments(args);
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.details_container, fragment).commit();
        }
    }
}
