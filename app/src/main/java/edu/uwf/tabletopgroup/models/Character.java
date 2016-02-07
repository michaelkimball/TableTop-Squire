package edu.uwf.tabletopgroup.models;

import java.util.Comparator;

/**
 * Model of the character to hold all of the
 * data from the character sheet
 * @author Michael Kimball
 * TODO: Add skills, spells, health, inventory, saves, armor, and more
 */
public class Character implements Comparator{
    public static final String NAME = "name";
    public static final String RACE = "race";
    public static final String CLASS = "class";
    public static final String STRENGTH = "strength";
    public static final String DEXTERITY = "dexterity";
    public static final String CONSTITUTION = "constitution";
    public static final String INTELLIGENCE = "intelligence";
    public static final String WISDOM = "wisdom";
    public static final String CHARISMA = "charisma";
    public static final String LEVEL = "level";
    public static final String EXPERIENCE = "experience";
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
    public Character(String name, String race, String characterClass){
        setName(name);
        setRace(race);
        setCharacterClass(characterClass);
    }

    public void setStats(int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma){
        setStrength(strength);
        setDexterity(dexterity);
        setConstitution(constitution);
        setIntelligence(intelligence);
        setWisdom(wisdom);
        setCharisma(charisma);
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
    public int compare(Object lhs, Object rhs) {
        if(lhs == null && rhs == null)
            return 0;
        else if(lhs == null)
            return -1;
        else if(rhs == null)
            return 1;
        Character left = (Character) lhs;
        Character right = (Character) rhs;
        return left.getName().compareTo(right.getName());
    }
}
