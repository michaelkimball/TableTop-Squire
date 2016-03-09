package edu.uwf.tabletopgroup.tabletop_squire;


import android.support.v4.app.Fragment;

/**
 * Created by michael on 2/29/16.
 */
public class ViewCharacterActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        String id = (String)getIntent().getExtras().get(ViewCharacterFragment.CHARACTER_KEY);
        return ViewCharacterFragment.newInstance(id);
    }
}
