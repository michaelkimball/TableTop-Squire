package edu.uwf.tabletopgroup.tabletop_squire;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import edu.uwf.tabletopgroup.models.User;

/**
 * TableTopSquire
 * CharacterListFragment.java
 *
 * TODO: Write Description
 *
 * Created By Michael Kimball
 *         On 3/15/16
 */
public class CharacterListFragment extends Fragment {
    private ListView lv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_character_list, container, false);
        lv = (ListView)v.findViewById(R.id.character_list);
        /**************** Create Custom Adapter *********/
        CharacterAdapter adapter= new CharacterAdapter( this, User.getCharacters());
        lv.setAdapter( adapter );

        return v;
    }
}
