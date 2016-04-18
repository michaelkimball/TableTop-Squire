package edu.uwf.tabletopgroup.tabletop_squire.game_room;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.uwf.tabletopgroup.R;
import edu.uwf.tabletopgroup.models.*;
import edu.uwf.tabletopgroup.models.Character;
import edu.uwf.tabletopgroup.tabletop_squire.view_character.ViewCharacterFragment;

/**
 * TableTopSquire
 * PlayerAdapter.java
 *
 * TODO: Write Description
 *
 * Created By Michael Kimball
 * On 4/16/16
 */
public class PlayerAdapter extends BaseAdapter implements View.OnClickListener {
    private PlayerListFragment fragment;
    private ArrayList<Player> players;
    private Player temp;
    private static LayoutInflater inflater;

    public PlayerAdapter(PlayerListFragment fragment, ArrayList<Player> players){
        this.fragment = fragment;
        this.players = players;
        inflater = (LayoutInflater)fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{

        public TextView playerName;
        public TextView characterName;
        public Player player;
        public int position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate row_character.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.row_player, null);

            /****** View Holder Object to contain row_character.xml file elements ******/

            holder = new ViewHolder();
            holder.playerName = (TextView) vi.findViewById(R.id.player_name);
            holder.characterName =(TextView)vi.findViewById(R.id.character_name);
            holder.position = position;

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(players.size()<=0)
        {
            holder.playerName.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            temp = null;
            temp = ( Player ) players.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.player = new Player(temp);
            holder.playerName.setText(temp.getName());
            holder.characterName.setText( temp.getCharacter().getName() );

            /******** Set Item Click Listener for LayoutInflater for each row *******/
            vi.setOnClickListener(this);
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        ViewHolder holder = (ViewHolder)v.getTag();
        Fragment newFragment = ViewCharacterFragment.newInstance(holder.player.getCharacter());
        FragmentManager fm = fragment.getFragmentManager();
        Fragment fragmentToRemove = fm.findFragmentByTag("player_details");
        if(fragmentToRemove != null){
            FragmentTransaction removalTransaction = fm.beginTransaction();
            removalTransaction.remove(fragmentToRemove).commit();
            fm.executePendingTransactions();
            fm.popBackStack();
        }
        FragmentTransaction transaction = fm.beginTransaction();
        boolean mDualPane = fragment.getActivity().findViewById(R.id.dual_pane)!=null;
        if(!mDualPane)
            transaction.replace(R.id.fragment_container, newFragment);
        else {
            transaction.replace(R.id.details_container, newFragment, "player_details");
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }}
