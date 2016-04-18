package edu.uwf.tabletopgroup.tabletop_squire.game_room;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import edu.uwf.tabletopgroup.R;
import edu.uwf.tabletopgroup.models.Player;
import edu.uwf.tabletopgroup.models.User;
import edu.uwf.tabletopgroup.rest.TableTopKeys;

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
    private static final String TAG = "PlayerListFragment";
    private ListView lv;
    private PlayerAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(TableTopKeys.ACTION_PLAYER_JOINED);
        getActivity().registerReceiver(receiver, filter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_player_list, container, false);
        lv = (ListView)v.findViewById(R.id.player_list);
        /**************** Create Custom Adapter *********/
        setAdapter();
        return v;
    }

    private void setAdapter(){
        adapter= new PlayerAdapter( this, User.getCurrentGame().getPlayers());
        lv.setAdapter( adapter );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e(TAG, "onReceive " + action);
            switch(action){
                case TableTopKeys.ACTION_PLAYER_JOINED:
                    Player player = intent.getParcelableExtra(TableTopKeys.KEY_PLAYER);
                    User.getCurrentGame().addPlayer(player);
                    setAdapter();
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    };
}
