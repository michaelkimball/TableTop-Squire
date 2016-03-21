package edu.uwf.tabletopgroup.tabletop_squire;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class Test_CreateGameRoom {

    @Rule
    public ActivityTestRule<GameRoomActivity> mActivityRule = new ActivityTestRule<>(GameRoomActivity.class);


    @Before
    public void init()
    {

    }

    /**
     * Attempt to create a new Gameroom
     * Should return to game room list activity/layout
     */
    @Test
    public void createNewGameRoom()
    {

    }

    /**
     * Attempt to create a duplicate room
     * Shoudl display a helpful message
     * Should remoain on CreateRoomActivity
     */
    @Test
    public void createDuplicateRoom()
    {

    }


    @After
    public void cleanUp()
    {

    }

}
