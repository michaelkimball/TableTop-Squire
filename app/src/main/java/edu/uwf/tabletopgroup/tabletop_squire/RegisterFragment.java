package edu.uwf.tabletopgroup.tabletop_squire;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.uwf.tabletopgroup.rest.TableTopRestClientUser;

/**
 * Created by michael on 2/17/16.
 */

public class RegisterFragment extends Fragment {
    public static final String TAG = "RegisterFragment";
    private EditText usernameET;
    private EditText emailET;
    private EditText passwordET;
    private EditText passwordConfirmET;
    private Button registerBT;
    private TableTopRestClientUser client;
    private TextView statusResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        try {
            client = TableTopRestClientUser.getClientUser(getActivity().getApplicationContext());
        }catch(Exception e){
            Log.e(TAG, "onCreateView", e);
        }
        usernameET = (EditText)v.findViewById(R.id.username);
        emailET = (EditText)v.findViewById(R.id.email);
        passwordET = (EditText)v.findViewById(R.id.password);
        passwordConfirmET = (EditText)v.findViewById(R.id.password_match);
        registerBT = (Button)v.findViewById(R.id.email_register_button);
        statusResult = (TextView)v.findViewById(R.id.status_result);
        registerBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        return v;
    }

    public void register(){
        String password = passwordET.getText().toString();
        String email = emailET.getText().toString();
        String username = usernameET.getText().toString();
        if(passwordsAreEqual(password) && hasEmail(email) && hasUsername(username)){
            TableTopRestClientUser.postUsers(username, email, password, new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (msg.what == TableTopRestClientUser.SUCCESS_MESSAGE) {
                        Intent i = new Intent(getContext(), WelcomeActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(getContext(), "Error creating user!", Toast.LENGTH_LONG).show();
                    }
                    return false;
                }
            });
        }
    }

    private boolean hasUsername(String username) {
        return !textIsNotEmpty("Username", username);
    }

    private boolean passwordsAreEqual(String password){
        String passwordMatch = passwordConfirmET.getText().toString();
        String key = "Password";
        boolean match = password.equals(passwordMatch);
        if(textIsNotEmpty(key, password) || textIsNotEmpty(key, passwordMatch))
            return false;
        if(!match){
            Toast.makeText(getContext(), "Passwords do not match!", Toast.LENGTH_LONG).show();
        }
        return match;
    }
    private boolean hasEmail(String email){
        boolean isEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if(textIsNotEmpty("Email", email))
            return false;
        if(!isEmail)
            Toast.makeText(getContext(), "Email must be valid", Toast.LENGTH_LONG).show();
        return isEmail;
    }
    private boolean textIsNotEmpty(String key, String toBeChecked){
        boolean isEmpty = toBeChecked.equals("");
        if(isEmpty)
            Toast.makeText(getContext(), key + " cannot be empty", Toast.LENGTH_LONG).show();
        return isEmpty;
    }
}
