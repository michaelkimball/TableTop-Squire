package edu.uwf.tabletopgroup.tabletop_squire.create_character;

import android.support.v4.app.Fragment;

import edu.uwf.tabletopgroup.tabletop_squire.SingleFragmentActivity;

/**
 * Created by michael on 3/9/16.
 */
public class CreateCharacterActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CreateCharacterFragment();
    }
}
