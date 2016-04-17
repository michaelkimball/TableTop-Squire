package edu.uwf.tabletopgroup.tabletop_squire.game_room;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import edu.uwf.tabletopgroup.R;
import edu.uwf.tabletopgroup.models.User;

/**
 * TableTopSquire
 * PlayerListFragment.java
 *
 * TODO: Write Description
 *
 * Created By Michael Kimball
 * On 4/16/16
 */
public class PlayerListFragment extends Fragment {
    private ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player_list, container, false);
        lv = (ListView)v.findViewById(R.id.player_list);
        /**************** Create Custom Adapter *********/
        PlayerAdapter adapter= new PlayerAdapter( this, User.getCurrentGame().getPlayers());
        lv.setAdapter( adapter );

        return v;
    }
}
