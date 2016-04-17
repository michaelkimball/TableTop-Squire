package edu.uwf.tabletopgroup.tabletop_squire.view_account;

import android.support.v4.app.Fragment;

import edu.uwf.tabletopgroup.tabletop_squire.SingleFragmentActivity;

public class ViewAccountActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new ViewAccountFragment();
    }
}
