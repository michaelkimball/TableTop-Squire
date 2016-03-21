package edu.uwf.tabletopgroup.tabletop_squire;

import android.app.Activity;
import android.os.Bundle;

/**
 * "A Fragment represents a behavior or a portion of user interface *in an Activity*."
 * -- Android Documentation
 *
 */
public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
