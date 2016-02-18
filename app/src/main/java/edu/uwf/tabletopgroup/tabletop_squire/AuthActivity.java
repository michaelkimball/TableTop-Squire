package edu.uwf.tabletopgroup.tabletop_squire;

import android.support.v4.app.Fragment;

/**
 * Created by michael on 2/17/16.
 */
public class AuthActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }
}
