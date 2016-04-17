package edu.uwf.tabletopgroup.models;


import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import edu.uwf.tabletopgroup.rest.TableTopKeys;

/**
 * Model of the character to hold all of the
 * data from the character sheet
 * @author Michael Kimball
 * TODO: Add skills, spells, health, inventory, saves, armor, and more
 */
public class Character implements Parcelable {
    private String mId;
    private String mName;
    private String mRace;
    private String mCharacterClass;
    private int mStrength;
    private int mDexterity;
    private int mConstitution;
    private int mIntelligence;
    private int mWisdom;
    private int mCharisma;
    private int mLevel;
    private int mExperience;

    public Character(String name){
        setName(name);
    }
    public Character(String name, String race, String characterClass, String id){
        setName(name);
        setRace(race);
        setCharacterClass(characterClass);
        setId(id);
    }

    public Character(Character character){
        this.mName = character.getName();
        this.mRace = character.getRace();
        this.mCharacterClass = character.getCharacterClass();
        this.mId = character.getId();
        setStats(
                character.getStrength(),
                character.getDexterity(),
                character.getConstitution(),
                character.getIntelligence(),
                character.getWisdom(),
                character.getCharisma()
        );
        this.mLevel = character.getLevel();
        this.mExperience = character.getExperience();
    }

    public Character(Parcel in){
        String[] strings = new String[4];
        int[] ints = new int[8];
        in.readStringArray(strings);
        in.readIntArray(ints);
        setId(strings[0]);
        setName(strings[1]);
        setRace(strings[2]);
        setCharacterClass(strings[3]);
        setStats(ints[0], ints[1], ints[2], ints[3], ints[4], ints[5]);
        setLevel(ints[6]);
        setExperience(ints[7]);
//        Bundle bundle = in.readBundle();
//        this.mId = bundle.getString(TableTopKeys.KEY_ID);
//        this.mName = bundle.getString(TableTopKeys.KEY_NAME);
//        this.mRace = bundle.getString(TableTopKeys.KEY_RACE);
//        this.mCharacterClass = bundle.getString(TableTopKeys.KEY_CLASS);
//        this.mStrength = bundle.getInt(TableTopKeys.KEY_STRENGTH);
//        this.mDexterity = bundle.getInt(TableTopKeys.KEY_DEXTERITY);
//        this.mConstitution = bundle.getInt(TableTopKeys.KEY_CONSTITUTION);
//        this.mIntelligence = bundle.getInt(TableTopKeys.KEY_INTELLIGENCE);
//        this.mWisdom = bundle.getInt(TableTopKeys.KEY_WISDOM);
//        this.mCharisma = bundle.getInt(TableTopKeys.KEY_CHARISMA);
//        this.mLevel = bundle.getInt(TableTopKeys.KEY_LEVEL);
//        this.mExperience = bundle.getInt(TableTopKeys.KEY_EXPERIENCE);
    }

    public void setStats(int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma){
        setStrength(strength);
        setDexterity(dexterity);
        setConstitution(constitution);
        setIntelligence(intelligence);
        setWisdom(wisdom);
        setCharisma(charisma);
    }

    public void setId(String id){
        mId = id;
    }

    public String getId(){
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getRace() {
        return mRace;
    }

    public void setRace(String race) {
        mRace = race;
    }

    public String getCharacterClass() {
        return mCharacterClass;
    }

    public void setCharacterClass(String characterClass) {
        mCharacterClass = characterClass;
    }

    public int getStrength() {
        return mStrength;
    }

    public void setStrength(int strength) {
        mStrength = strength;
    }

    public int getDexterity() {
        return mDexterity;
    }

    public void setDexterity(int dexterity) {
        mDexterity = dexterity;
    }

    public int getConstitution() {
        return mConstitution;
    }

    public void setConstitution(int constitution) {
        mConstitution = constitution;
    }

    public int getIntelligence() {
        return mIntelligence;
    }

    public void setIntelligence(int intelligence) {
        mIntelligence = intelligence;
    }

    public int getWisdom() {
        return mWisdom;
    }

    public void setWisdom(int wisdom) {
        mWisdom = wisdom;
    }

    public int getCharisma() {
        return mCharisma;
    }

    public void setCharisma(int charisma) {
        mCharisma = charisma;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public int getExperience() {
        return mExperience;
    }

    public void setExperience(int experience) {
        mExperience = experience;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String[] strings = new String[]{
            mId, mName, mRace, mCharacterClass
        };
        int[] ints = new int[]{
            mStrength, mDexterity, mConstitution,
            mIntelligence, mWisdom, mCharisma,
            mLevel, mExperience
        };
        dest.writeStringArray(strings);
        dest.writeIntArray(ints);
//        Bundle bundle = new Bundle();
//        bundle.putString(TableTopKeys.KEY_ID, mId);
//        bundle.putString(TableTopKeys.KEY_NAME, mName);
//        bundle.putString(TableTopKeys.KEY_RACE, mRace);
//        bundle.putString(TableTopKeys.KEY_CLASS, mCharacterClass);
//        bundle.putInt(TableTopKeys.KEY_STRENGTH, mStrength);
//        bundle.putInt(TableTopKeys.KEY_DEXTERITY, mDexterity);
//        bundle.putInt(TableTopKeys.KEY_CONSTITUTION, mConstitution);
//        bundle.putInt(TableTopKeys.KEY_INTELLIGENCE, mIntelligence);
//        bundle.putInt(TableTopKeys.KEY_WISDOM, mWisdom);
//        bundle.putInt(TableTopKeys.KEY_CHARISMA, mCharisma);
//        bundle.putInt(TableTopKeys.KEY_LEVEL, mLevel);
//        bundle.putInt(TableTopKeys.KEY_EXPERIENCE, mExperience);
//        dest.writeBundle(bundle);
    }

    public static final Parcelable.Creator<Character> CREATOR = new Parcelable.Creator<Character>(){

        @Override
        public Character createFromParcel(Parcel source) {
            return new Character(source);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

}
