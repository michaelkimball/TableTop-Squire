package edu.uwf.tabletopgroup.tabletop_squire;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import edu.uwf.tabletopgroup.models.Character;
import edu.uwf.tabletopgroup.rest.TableTopRestClientUser;

/**
 * Created by michael on 3/7/16.
 */
public class CreateCharacterFragment extends Fragment {
    private EditText nameET;
    private EditText raceET;
    private EditText characterClassET;
    private TextView strengthTV;
    private TextView dexterityTV;
    private TextView constitutionTV;
    private TextView intelligenceTV;
    private TextView wisdomTV;
    private TextView charismaTV;
    private Button rollBT;
    private Button saveBT;
    private TableTopRestClientUser client;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_character, container, false);
        initializeViews(v);
        try {
            client = TableTopRestClientUser.getClientUser(getContext());
        }catch(Exception e){
            Toast.makeText(getActivity(), "Error connecting to database!", Toast.LENGTH_SHORT).show();
        }
        rollBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollAttributes();
            }
        });
        saveBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCharacter();
            }
        });
        return v;
    }
    private void initializeViews(View v){
        nameET = (EditText)v.findViewById(R.id.name);
        raceET = (EditText)v.findViewById(R.id.race);
        characterClassET = (EditText)v.findViewById(R.id.character_class);
        strengthTV = (TextView)v.findViewById(R.id.strength);
        dexterityTV = (TextView)v.findViewById(R.id.dexterity);
        constitutionTV = (TextView)v.findViewById(R.id.constitution);
        intelligenceTV = (TextView)v.findViewById(R.id.intelligence);
        wisdomTV = (TextView)v.findViewById(R.id.wisdom);
        charismaTV = (TextView)v.findViewById(R.id.charisma);
        rollBT = (Button)v.findViewById(R.id.roll);
        saveBT = (Button)v.findViewById(R.id.save);
    }
    private void rollAttributes(){
        strengthTV.setText(getRandomAttribute());
        dexterityTV.setText(getRandomAttribute());
        constitutionTV.setText(getRandomAttribute());
        intelligenceTV.setText(getRandomAttribute());
        wisdomTV.setText(getRandomAttribute());
        charismaTV.setText(getRandomAttribute());
    }
    private String getRandomAttribute(){
        Random random = new Random();
        return String.valueOf(Math.abs((random.nextInt()) % 18) + 1);
    }
    private void saveCharacter(){
        if(!validation())
            return;
        @SuppressLint("UseValueOf") Character character = new Character(nameET.getText().toString());
        character.setLevel(1);
        character.setExperience(0);
        character.setCharacterClass(characterClassET.getText().toString());
        character.setRace(raceET.getText().toString());
        character.setStrength(Integer.valueOf(strengthTV.getText().toString()));
        character.setDexterity(Integer.valueOf(dexterityTV.getText().toString()));
        character.setConstitution(Integer.valueOf(constitutionTV.getText().toString()));
        character.setIntelligence(Integer.valueOf(intelligenceTV.getText().toString()));
        character.setWisdom(Integer.valueOf(wisdomTV.getText().toString()));
        character.setCharisma(Integer.valueOf(charismaTV.getText().toString()));
        client.postCharacters(character, new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if(msg.what == TableTopRestClientUser.SUCCESS_MESSAGE){
                    Toast.makeText(getContext(), "Character created successfully!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(), "Error creating character!", Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }
    private boolean validation(){
        String name = nameET.getText().toString();
        String charClass = characterClassET.getText().toString();
        String race = raceET.getText().toString();
        if(name.equals("")) {
            Toast.makeText(getActivity(), "You must enter a name", Toast.LENGTH_LONG).show();
            return false;
        }
        if(charClass.equals("")) {
            Toast.makeText(getActivity(), "You must enter a class", Toast.LENGTH_LONG).show();
            return false;
        }
        if(race.equals("")) {
            Toast.makeText(getActivity(), "You must enter a race", Toast.LENGTH_LONG).show();
            return false;
        }
        if(strengthTV.getText().toString().equals("-1")){
            Toast.makeText(getActivity(), "You must roll for your attributes", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
