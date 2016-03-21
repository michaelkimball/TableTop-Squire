package edu.uwf.tabletopgroup.tabletop_squire;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Test_CharacterSheetCreate {

    @Rule
    public ActivityTestRule<CharacterActivity> mActivityRule = new ActivityTestRule<>(CharacterActivity.class);


    @Before
    public void init()
    {

    }

    @Test
    public void SendCharacter()
    {

    }

    @Test
    public void BadCharacterName()
    {

    }

    @Test
    public void incompleteStats()
    {

    }
}
