package edu.uwf.tabletopgroup.tabletop_squire.view_account;

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
import edu.uwf.tabletopgroup.R;

/**
 * Created by Brandon on 2/21/2016.
 */
public class EmailFragment extends DialogFragment implements TextView.OnEditorActionListener {
    public static final String TAG = "EmailFragment";
    private EditText newEmailET;
    private TableTopRestClientUser client;

    public interface EditEmailDialogListener {
        void onFinishEmailDialog(String inputText);
    }

    public EmailFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_email, container, false);
        try {
            client = TableTopRestClientUser.getClientUser(getActivity().getApplicationContext());
        }catch(Exception e){
            Log.e(TAG, "onCreateView", e);
        }
        newEmailET = (EditText)v.findViewById(R.id.new_email_text);
        getDialog().setTitle("Change Email");
        // show keyboard on load
        newEmailET.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        );
        newEmailET.setOnEditorActionListener(this);
        return v;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            changeEmail();
            EditEmailDialogListener fragment = (EditEmailDialogListener) getTargetFragment();
            fragment.onFinishEmailDialog(newEmailET.getText().toString());
            this.dismiss();
            return true;
        }
        return false;
    }
    private void changeEmail(){
        String email = newEmailET.getText().toString();
        newEmailET.setText(email);
        if(email.equals("")){
            Toast.makeText(getActivity(), "New Email is required!", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Toast.makeText(getActivity(), "Your email has been changed.", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Log.e(TAG, "changeEmail: ", e);
            Toast.makeText(getActivity(), "Error setting new e-mail!", Toast.LENGTH_LONG).show();
        }
    }
}

