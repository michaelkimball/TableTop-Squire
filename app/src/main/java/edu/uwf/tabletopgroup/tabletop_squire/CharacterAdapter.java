package edu.uwf.tabletopgroup.tabletop_squire;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import edu.uwf.tabletopgroup.models.Character;
import edu.uwf.tabletopgroup.models.User;

import java.util.ArrayList;

/**
 * TableTopSquire
 * CharacterAdapter.java
 *
 * TODO: Write Description
 *
 * Created By Michael Kimball
 *         On 3/15/16
 */
public class CharacterAdapter extends BaseAdapter implements View.OnClickListener{
    private CharacterListFragment fragment;
    private ArrayList<Character> characters;
    private Character temp;
    private int mPosition;
    private static LayoutInflater inflater;

    public CharacterAdapter(CharacterListFragment fragment, ArrayList<Character> characters){
        this.fragment = fragment;
        this.characters = characters;
        inflater = (LayoutInflater)fragment.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return characters.size();
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

        public TextView name;
        public TextView character_class;
        public TextView race;
        public TextView level;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate row_character.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.row_character, null);

            /****** View Holder Object to contain row_character.xml file elements ******/

            holder = new ViewHolder();
            holder.name = (TextView) vi.findViewById(R.id.name);
            holder.race =(TextView)vi.findViewById(R.id.race);
            holder.character_class = (TextView)vi.findViewById(R.id.character_class);
            holder.level = (TextView)vi.findViewById(R.id.level);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(characters.size()<=0)
        {
            holder.name.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            temp = null;
            temp = ( Character ) characters.get( position );

            /************  Set Model values in Holder elements ***********/

            holder.name.setText( temp.getName() );
            holder.race.setText( temp.getRace() );
            holder.character_class.setText( temp.getCharacterClass() );
            holder.level.setText( String.valueOf(temp.getLevel()) );

            /******** Set Item Click Listener for LayoutInflater for each row *******/
            mPosition = position;
            vi.setOnClickListener(this);
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        String id = User.getCharacter(mPosition).getId();
        if(id == null)
            return;
        Fragment newFragment = ViewCharacterFragment.newInstance(id);
        FragmentTransaction transaction = fragment.getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
