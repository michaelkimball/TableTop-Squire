package edu.uwf.tabletopgroup.tabletop_squire.authorization;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.uwf.tabletopgroup.models.User;
import edu.uwf.tabletopgroup.rest.TableTopRestClientUser;
import edu.uwf.tabletopgroup.R;
import edu.uwf.tabletopgroup.tabletop_squire.WelcomeActivity;

/**
 * Created by michael on 2/17/16.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";

    private EditText usernameET;
    private EditText passwordET;
    private Button loginBT;
    private Button registerBT;
    private TableTopRestClientUser client;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        try {
            client = TableTopRestClientUser.getClientUser(getActivity().getApplicationContext());
        }catch(Exception e){
            Toast.makeText(getActivity(), "Error connecting to database!", Toast.LENGTH_SHORT).show();
        }
        usernameET = (EditText)v.findViewById(R.id.username);
        passwordET = (EditText)v.findViewById(R.id.password);

        loginBT = (Button)v.findViewById(R.id.login);
        loginBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLogin();
            }
        });

        registerBT = (Button)v.findViewById(R.id.register);
        registerBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });
        return v;
    }
    private void startLogin(){
        String email = usernameET.getText().toString();
        String password = passwordET.getText().toString();
        if(email.equals("") || password.equals("")){
            Toast.makeText(getActivity(), "Email and password required!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            User.setUser(email, password);
            client.getUser(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if(msg.what == TableTopRestClientUser.SUCCESS_MESSAGE){
                        Intent i = new Intent(getContext(), WelcomeActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(getContext(), "Username/Password combination incorrect!", Toast.LENGTH_LONG).show();
                    }
                    return false;
                }
            });
        }catch(Exception e){
            Log.e(TAG, "startLogin: ", e);
            Toast.makeText(getActivity(), "Error getting user!", Toast.LENGTH_SHORT).show();
        }
    }
    private void startRegister(){
        Fragment newFragment = new RegisterFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
