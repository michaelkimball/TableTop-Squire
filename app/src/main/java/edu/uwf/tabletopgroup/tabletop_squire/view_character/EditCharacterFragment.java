package edu.uwf.tabletopgroup.tabletop_squire.view_character;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import edu.uwf.tabletopgroup.R;
import edu.uwf.tabletopgroup.models.Character;
import edu.uwf.tabletopgroup.models.User;
import edu.uwf.tabletopgroup.rest.TableTopKeys;
import edu.uwf.tabletopgroup.rest.TableTopRestClientUser;

/**
 * Created by michael on 2/29/16.
 */
public class EditCharacterFragment extends Fragment {
    private static final String TAG = "EditCharacterFragment";
    public static final String CHARACTER_KEY = "character_id";
    private TableTopRestClientUser client;
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

    public static EditCharacterFragment newInstance(String id) {
        EditCharacterFragment viewCharacterFragment = new EditCharacterFragment();
        Bundle args = new Bundle();
        args.putSerializable(CHARACTER_KEY, id);
        viewCharacterFragment.setArguments(args);
        return viewCharacterFragment;
    }

    public static EditCharacterFragment newInstance(Character character){
        EditCharacterFragment viewCharacterFragment = new EditCharacterFragment();
        Bundle args = new Bundle();
        args.putParcelable(TableTopKeys.KEY_CHARACTER, character);
        viewCharacterFragment.setArguments(args);
        return viewCharacterFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit_character, container, false);
        Bundle args = getArguments();
        try{
            client = TableTopRestClientUser.getClientUser(getActivity());
        }catch(Exception e){
            Log.e(TAG, "onCreate()", e);
        }
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
        nameTV.setOnClickListener(new CharacterStatOnClickListener("Name", 0, nameTV));
        raceTV = (TextView)v.findViewById(R.id.race);
        raceTV.setText(character.getRace());
        raceTV.setOnClickListener(new CharacterStatOnClickListener("Race", 0, raceTV));
        classTV = (TextView)v.findViewById(R.id.character_class);
        classTV.setText(character.getCharacterClass());
        classTV.setOnClickListener(new CharacterStatOnClickListener("Class", 0, classTV));
        levelTV = (TextView)v.findViewById(R.id.level);
        levelTV.setText(String.valueOf(character.getLevel()));
        levelTV.setOnClickListener(new CharacterStatOnClickListener("Level", InputType.TYPE_CLASS_NUMBER, levelTV));
        expTV = (TextView)v.findViewById(R.id.experience);
        expTV.setText(String.valueOf(character.getExperience()));
        expTV.setOnClickListener(new CharacterStatOnClickListener("Experience", InputType.TYPE_CLASS_NUMBER, expTV));
        strengthTV = (TextView)v.findViewById(R.id.strength);
        strengthTV.setText(String.valueOf(character.getStrength()));
        strengthTV.setOnClickListener(new CharacterStatOnClickListener("Strength", InputType.TYPE_CLASS_NUMBER, strengthTV));
        dexterityTV = (TextView)v.findViewById(R.id.dexterity);
        dexterityTV.setText(String.valueOf(character.getDexterity()));
        dexterityTV.setOnClickListener(new CharacterStatOnClickListener("Dexterity", InputType.TYPE_CLASS_NUMBER, dexterityTV));
        constitutionTV = (TextView)v.findViewById(R.id.constitution);
        constitutionTV.setText(String.valueOf(character.getConstitution()));
        constitutionTV.setOnClickListener(new CharacterStatOnClickListener("Constitution", InputType.TYPE_CLASS_NUMBER, constitutionTV));
        intelligenceTV = (TextView)v.findViewById(R.id.intelligence);
        intelligenceTV.setText(String.valueOf(character.getIntelligence()));
        intelligenceTV.setOnClickListener(new CharacterStatOnClickListener("Intelligence", InputType.TYPE_CLASS_NUMBER, intelligenceTV));
        wisdomTV = (TextView)v.findViewById(R.id.wisdom);
        wisdomTV.setText(String.valueOf(character.getWisdom()));
        wisdomTV.setOnClickListener(new CharacterStatOnClickListener("Wisdom", InputType.TYPE_CLASS_NUMBER, wisdomTV));
        charismaTV = (TextView)v.findViewById(R.id.charisma);
        charismaTV.setText(String.valueOf(character.getCharisma()));
        charismaTV.setOnClickListener(new CharacterStatOnClickListener("Charisma", InputType.TYPE_CLASS_NUMBER, charismaTV));
        Button save = (Button)v.findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCharacter();
                client.putCharacter(character, new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        if (msg.what == TableTopRestClientUser.SUCCESS_MESSAGE) {
                            Toast.makeText(getActivity(), "Character Saved!", Toast.LENGTH_LONG).show();
                            return true;
                        } else {
                            Toast.makeText(getActivity(), "Error Saving Character!", Toast.LENGTH_LONG).show();
                            return false;
                        }
                    }
                });
                try {
                    client.getCharacters(new Handler.Callback() {
                        @Override
                        public boolean handleMessage(Message msg) {
                            return false;
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void updateCharacter() {
        character.setName(nameTV.getText().toString());
        character.setRace(raceTV.getText().toString());
        character.setCharacterClass(classTV.getText().toString());
        character.setLevel(Integer.valueOf(levelTV.getText().toString()));
        character.setExperience(Integer.valueOf(expTV.getText().toString()));
        character.setStrength(Integer.valueOf(strengthTV.getText().toString()));
        character.setDexterity(Integer.valueOf(dexterityTV.getText().toString()));
        character.setConstitution(Integer.valueOf(constitutionTV.getText().toString()));
        character.setIntelligence(Integer.valueOf(intelligenceTV.getText().toString()));
        character.setWisdom(Integer.valueOf(wisdomTV.getText().toString()));
        character.setCharisma(Integer.valueOf(charismaTV.getText().toString()));
    }

    private class CharacterStatOnClickListener implements View.OnClickListener{
        private String title;
        private int inputType;
        private TextView textView;
        public CharacterStatOnClickListener(String title, int inputType, TextView textView){
            this.title = title;
            this. inputType = inputType;
            this.textView = textView;
        }
        @Override
        public void onClick(View v) {
            CharacterDialog dialog = CharacterDialog.newInstance(title, inputType, new CharacterDialog.CharacterDialogListener() {
                @Override
                public void onSave(String data) {
                    textView.setText(data);
                }
            });
            dialog.show(getFragmentManager(), "character_info");
        }
    }
}
