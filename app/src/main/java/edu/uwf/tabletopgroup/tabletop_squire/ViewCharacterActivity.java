package edu.uwf.tabletopgroup.tabletop_squire;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by michael on 2/29/16.
 */
//public class ViewCharacterActivity extends SingleFragmentActivity{
//    @Override
//    protected Fragment createFragment() {
//        return new CharacterListFragment();
//    }
//}
public class ViewCharacterActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_switch);
        boolean mDualPane = findViewById(R.id.dual_pane)!=null;

        FragmentManager fm = getSupportFragmentManager();
        if (fm.findFragmentById(R.id.fragment_container)==null) {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_container, new CharacterListFragment()).commit();
        }
//        if(mDualPane && fm.findFragmentById(R.id.details_container)==null) {
//            FragmentTransaction ft = fm.beginTransaction();
//            ft.replace(R.id.details_container, ViewCharacterFragment.newInstance(null)).commit();
//        }
    }
}