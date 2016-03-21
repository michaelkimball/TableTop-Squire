package edu.uwf.tabletopgroup.tabletop_squire;

import android.content.ComponentName;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.uwf.tabletopgroup.models.User;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Test_UserLogin {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class);


    @Before
    public void setup()
    {
        // Can't inject user, code is private.....
        // Assume user has already been created instead.

    }

    /**
     * Attempt to login valid user
     * Users account required to access services
     * Should mark user as logged in
     * Should redirect to Welcome activity
     */
    @Test
    public void try_LoginUser()
    {
        onView(withId(R.id.username)).perform(typeText("valuetestuser"),closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("validPassword!"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());

        intended(hasComponent(new ComponentName(getTargetContext(), WelcomeActivity.class)));
    }

    @Test
    public void try_LoginUserAgain()
    {
        // Is there a way to login twice from UI?
        // If so, should just log out previous user, close session and start new session and login new user. If same user, quietly fail(do nothing)
    }

    /**
     * Attempt to login with bad username
     * Should remain on login activity
     * Should display a helpful message
     * Note, no distincation is made between bad username and bad password
     */
    @Test
     public void try_LoginInvalidUser()
    {
        if(User.isLoggedIn) {
            ;
            // logout user, private methods
        }

        onView(withId(R.id.username)).perform(typeText("invaliduseraccount"),closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("validPassword!"), closeSoftKeyboard()); // not really meaniful if username is invalid
        onView(withId(R.id.btn_login)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), LoginActivity.class)));
        // intended(withId(R.id.lbl_error_result).matches((new TextView(getContext().getApplicationContext()).setText("Invalid Username or Password."))); // forgot how to get string table.....
    }

    /**
     * Attempt to login with bad password
     * Should remain on login activity
     * Should display a helpful message
     * Note, no distincation is made between bad username and bad password
     */
    @Test
    public void try_LoginInvalidPassword()
    {
        if(User.isLoggedIn) {
            ;
            // logout user
        }

        onView(withId(R.id.username)).perform(typeText("valuetestuser"),closeSoftKeyboard()); // not really meaniful if username is invalid
        onView(withId(R.id.password)).perform(typeText("validPassword!"), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        intended(hasComponent(new ComponentName(getTargetContext(), LoginActivity.class)));
        // intended(withId(R.id.lbl_error_result).matches((new TextView(getContext().getApplicationContext()).setText("Invalid Username or Password."))); // forgot how to get string table.....
    }

    /**
     * Attempt to logout a logged in user
     * Should mark user as logged out
     * Should close session and socket
     * Should redirect to LoginActivity
     */
    @Test
    public void try_LogoutUser()
    {
        // Not yet implemented
    }

    @After
    public void CleanUp()
    {
        // Remove injected user
    }
}