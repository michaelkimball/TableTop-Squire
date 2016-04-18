package edu.uwf.tabletopgroup.tabletop_squire.view_character;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.uwf.tabletopgroup.models.Character;
import edu.uwf.tabletopgroup.models.User;
import edu.uwf.tabletopgroup.R;
import edu.uwf.tabletopgroup.rest.TableTopKeys;

/**
 * Created by michael on 2/29/16.
 */
public class ViewCharacterFragment extends Fragment {
    public static final String CHARACTER_KEY = "character_id";
    private Character character;
    private TextView nameTV;
    private TextView raceTV;
    private TextView classTV;
    private TextView levelTV;
    private TextView expTV;
    private TextView strengthTV;
    private TextView dexterityTV;
    private TextView constitutionTV;
    private TextView intelligenceTV;
    private TextView wisdomTV;
    private TextView charismaTV;

    public static ViewCharacterFragment newInstance(String id) {
        ViewCharacterFragment viewCharacterFragment = new ViewCharacterFragment();
        Bundle args = new Bundle();
        args.putSerializable(CHARACTER_KEY, id);
        viewCharacterFragment.setArguments(args);
        return viewCharacterFragment;
    }

    public static ViewCharacterFragment newInstance(Character character){
        ViewCharacterFragment viewCharacterFragment = new ViewCharacterFragment();
        Bundle args = new Bundle();
        args.putParcelable(TableTopKeys.KEY_CHARACTER, character);
        viewCharacterFragment.setArguments(args);
        return viewCharacterFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_character, container, false);
        Bundle args = getArguments();
        if(args.containsKey(CHARACTER_KEY))
            character = User.getCharacter((String)args.get(CHARACTER_KEY));
        else if(args.containsKey(TableTopKeys.KEY_CHARACTER))
            character = args.getParcelable(TableTopKeys.KEY_CHARACTER);
        if(character != null)
            initializeTextViews(v);
        return v;
    }

    private void initializeTextViews(View v){
        nameTV = (TextView)v.findViewById(R.id.name);
        nameTV.setText(character.getName());
        raceTV = (TextView)v.findViewById(R.id.race);
        raceTV.setText(character.getRace());
        classTV = (TextView)v.findViewById(R.id.character_class);
        classTV.setText(character.getCharacterClass());
        levelTV = (TextView)v.findViewById(R.id.level);
        levelTV.setText(String.valueOf(character.getLevel()));
        expTV = (TextView)v.findViewById(R.id.experience);
        expTV.setText(String.valueOf(character.getExperience()));
        strengthTV = (TextView)v.findViewById(R.id.strength);
        strengthTV.setText(String.valueOf(character.getStrength()));
        dexterityTV = (TextView)v.findViewById(R.id.dexterity);
        dexterityTV.setText(String.valueOf(character.getDexterity()));
        constitutionTV = (TextView)v.findViewById(R.id.constitution);
        constitutionTV.setText(String.valueOf(character.getConstitution()));
        intelligenceTV = (TextView)v.findViewById(R.id.intelligence);
        intelligenceTV.setText(String.valueOf(character.getIntelligence()));
        wisdomTV = (TextView)v.findViewById(R.id.wisdom);
        wisdomTV.setText(String.valueOf(character.getWisdom()));
        charismaTV = (TextView)v.findViewById(R.id.charisma);
        charismaTV.setText(String.valueOf(character.getCharisma()));
    }
}
