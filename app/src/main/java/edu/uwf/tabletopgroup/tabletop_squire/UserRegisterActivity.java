package edu.uwf.tabletopgroup.tabletop_squire;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class UserRegisterActivity extends Activity {

    private String email;
    private String password1;
    private String password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
    }

    /**
     * Called from
     * @param view
     */
    public void doRegister(View view) {

    }
}
