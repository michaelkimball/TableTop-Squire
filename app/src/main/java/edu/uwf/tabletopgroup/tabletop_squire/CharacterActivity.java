package edu.uwf.tabletopgroup.tabletop_squire;


import android.support.v4.app.Fragment;

/**
 * Created by michael on 2/29/16.
 */
public class CharacterActivity extends SingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new ViewCharacterFragment();
    }
}
