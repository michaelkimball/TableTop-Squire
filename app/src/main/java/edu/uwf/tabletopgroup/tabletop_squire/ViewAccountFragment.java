package edu.uwf.tabletopgroup.tabletop_squire;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.uwf.tabletopgroup.models.User;
import edu.uwf.tabletopgroup.rest.TableTopRestClientUser;

/**
 * Created by Brandon on 2/21/2016.
 */
public class ViewAccountFragment extends Fragment implements EmailFragment.EditEmailDialogListener {

    public static final String TAG = "ViewAccountFragment";
    private EditText usernameET;
    private EditText emailET;
    private Button passwordChangeBT;
    private Button saveChangesBT;
    private TableTopRestClientUser client;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_account, container, false);
        try {
            client = TableTopRestClientUser.getClientUser(getActivity().getApplicationContext());
        }catch(Exception e){
            Log.e(TAG, "onCreateView", e);
        }
        usernameET = (EditText)v.findViewById(R.id.username_text);
        usernameET.setText(User.getUsername());
        usernameET.setFocusable(false);
        emailET = (EditText)v.findViewById(R.id.email_text);
        emailET.setText(User.getEmail());
        emailET.setFocusable(false);
        emailET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEmailChange();
            }
        });

        passwordChangeBT = (Button)v.findViewById(R.id.change_password);
        passwordChangeBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPasswordChange();
            }
        });

        saveChangesBT = (Button)v.findViewById(R.id.save_changes);
        saveChangesBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.putUser(new Handler.Callback(){
                    @Override
                    public boolean handleMessage(Message msg) {
                        if(msg.what == TableTopRestClientUser.SUCCESS_MESSAGE){
                            Toast.makeText(getContext(), "Changes saved", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(), "Error saving changes", Toast.LENGTH_LONG).show();
                        }
                        return false;
                    }
                });
            }
        });

        return v;
    }

    private void startEmailChange(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        EmailFragment emailFragment = new EmailFragment();
        emailFragment.setTargetFragment(this, 0);
        emailFragment.show(fm, "fragment_email");
    }

    private void startPasswordChange(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        PasswordFragment passwordFragment = new PasswordFragment();
        passwordFragment.setTargetFragment(this, 0);
        passwordFragment.show(fm, "fragment_password");
    }

    @Override
    public void onFinishEmailDialog(String inputText) {
        emailET.setText(inputText);
        User.setEmail(inputText);
    }
}
