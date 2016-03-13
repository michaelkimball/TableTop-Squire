package edu.uwf.tabletopgroup.tabletop_squire;

import android.support.v4.app.Fragment;

public class ViewAccountActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new ViewAccountFragment();
    }
}
