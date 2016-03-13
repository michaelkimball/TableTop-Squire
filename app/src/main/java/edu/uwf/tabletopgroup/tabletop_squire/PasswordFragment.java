package edu.uwf.tabletopgroup.tabletop_squire;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.uwf.tabletopgroup.models.User;
import edu.uwf.tabletopgroup.rest.TableTopRestClientUser;

/**
 * Created by Brandon on 2/21/2016.
 */
public class PasswordFragment extends DialogFragment implements TextView.OnEditorActionListener {
    private static final String TAG = "PasswordFragment";
    private EditText newPasswordET;
    private EditText confirmNewPasswordET;
    private TableTopRestClientUser client;

    public PasswordFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_password, container, false);
        try {
            client = TableTopRestClientUser.getClientUser(getActivity().getApplicationContext());
        }catch(Exception e){
            Log.e(TAG, "onCreateView", e);
        }
        newPasswordET = (EditText)v.findViewById(R.id.new_password_text);
        confirmNewPasswordET = (EditText)v.findViewById(R.id.confirm_new_password_text);
        getDialog().setTitle("Change Password");
        //show keyboard on load
        newPasswordET.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        );
        confirmNewPasswordET.setOnEditorActionListener(this);
        return v;
    }

    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        Log.d("PasswordFragment", String.format("onEditorAction(%s, %d, %s) enter", v, actionId, event));
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            String password = newPasswordET.getText().toString();
            String passwordMatch = confirmNewPasswordET.getText().toString();
            // Return input text to activity
            changePassword(password, passwordMatch);
            this.dismiss();
            return true;
        }
        return false;
    }

    private void changePassword(String password, String passwordMatch){
        if(passwordsAreEqual(password, passwordMatch)){
            try {
                User.setPassword(password);
                Toast.makeText(getContext(), "Your password has been changed.", Toast.LENGTH_SHORT).show();
            }catch(Exception e){
                Log.e(TAG, "changePassword: ", e);
                Toast.makeText(getActivity(), "Error setting new password!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean passwordsAreEqual(String password, String passwordMatch){
        String key = "New Password";
        boolean match = password.equals(passwordMatch);
        if(textIsNotEmpty(key, password) || textIsNotEmpty(key, passwordMatch))
            return false;
        if(!match){
            Toast.makeText(getContext(), "Passwords do not match!", Toast.LENGTH_LONG).show();
        }
        return match;
    }

    private boolean textIsNotEmpty(String key, String toBeChecked){
        boolean isEmpty = toBeChecked.equals("");
        if(isEmpty)
            Toast.makeText(getContext(), key + " cannot be empty", Toast.LENGTH_LONG).show();
        return isEmpty;
    }

}
