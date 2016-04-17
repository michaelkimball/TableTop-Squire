package edu.uwf.tabletopgroup.tabletop_squire.game_lobby;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.uwf.tabletopgroup.models.Invite;
import edu.uwf.tabletopgroup.models.User;
import edu.uwf.tabletopgroup.R;

/**
 * TableTopSquire
 * CharacterAdapter.java
 *
 * TODO: Write Description
 *
 * Created By Michael Kimball
 *         On 3/15/16
 */
public class InviteAdapter extends BaseAdapter implements View.OnClickListener{
    private GameLobbyFragment fragment;
    private ArrayList<Invite> invites;
    private static LayoutInflater inflater;
    private Invite temp;

    public InviteAdapter(GameLobbyFragment fragment, ArrayList<Invite> invites){
        this.fragment = fragment;
        this.invites = invites;
        inflater = (LayoutInflater)fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return invites.size();
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
        public TextView gameName;
        public TextView playerName;
        public int position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;
        if(convertView==null){
            /****** Inflate row_character.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.row_pending_invite, null);
            /****** View Holder Object to contain row_character.xml file elements ******/
            holder = new ViewHolder();
            holder.playerName = (TextView) vi.findViewById(R.id.game_name);
            holder.gameName =(TextView)vi.findViewById(R.id.player_name);
            holder.position = position;
            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(invites.size()<=0)
        {
            holder.playerName.setText("No Data");
        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            temp = null;
            temp = ( Invite ) invites.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.playerName.setText( temp.getPlayerName() );
            holder.gameName.setText( temp.getGameName() );

            /******** Set Item Click Listener for LayoutInflater for each row *******/
            vi.setOnClickListener(this);
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        ViewHolder holder = (ViewHolder)v.getTag();
        String id = User.getInvite(holder.position).getGameId();
        if(id == null)
            return;
        (new JoinGameDialogBuilder(fragment.getContext(), "test")).create().show();
    }
}
