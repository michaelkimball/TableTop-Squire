package edu.uwf.tabletopgroup.tabletop_squire.game_lobby;

import android.support.v4.app.Fragment;

import edu.uwf.tabletopgroup.tabletop_squire.SingleFragmentActivity;

public class GameLobbyActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new GameLobbyFragment();
    }

}
