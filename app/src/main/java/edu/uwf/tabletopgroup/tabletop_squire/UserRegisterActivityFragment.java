package edu.uwf.tabletopgroup.tabletop_squire;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;

import edu.uwf.tabletopgroup.models.User;
import edu.uwf.tabletopgroup.rest.TableTopRestClientUser;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserRegisterActivityFragment extends Fragment {

    private String email;
    private String password1;
    private String password2;
    private UserLoginTask mAuthTask;
    private EditText emailT;
    private EditText passwordT;
    private Button registerB;
    private TableTopRestClientUser client;
    private static String TAG = "UserRegisterFragment";

    public UserRegisterActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_register, container, false);
        try {
            client = TableTopRestClientUser.getClientUser(getActivity().getApplicationContext());
        }catch(Exception e){
            Log.e(TAG, "onCreate", e);
        }
        emailT = (EditText)v.findViewById(R.id.email);
        passwordT = (EditText)v.findViewById(R.id.password);
        registerB = (Button)v.findViewById(R.id.email_register_button);
        registerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = emailT.getText().toString();
                password1 = passwordT.getText().toString();
                mAuthTask = new UserLoginTask(email, password1);
                mAuthTask.execute((Void) null);
            }
        });
        return v;
    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
        public static final String TAG = "UserLoginTask";
        private final Handler.Callback mCallback;
        private boolean hasData;
        UserLoginTask(String email, String password) {
            User.setUser(email, password);
            /**
             * Lets {@link #onPostExecute(Boolean)} know whether request passed or failed
             */
            mCallback = new Handler.Callback() {
                public static final String TAG = "ULTcb";
                @Override
                public boolean handleMessage(Message msg) {
                    if(msg.what == TableTopRestClientUser.SUCCESS_MESSAGE){
                        Log.d(TAG + "handleMessage", "Characters Grabbed!");
                        stopLooper();
                        hasData = true;
                        return hasData;
                    }else if(msg.what == TableTopRestClientUser.FAILURE_MESSAGE){
                        Log.e(TAG + "handleMessage", "Failed to grab Characters!");
                    }else{
                        Log.e(TAG + "handleMessage", "IDK WHAT IS GOING ON!");
                    }
                    stopLooper();
                    hasData = false;
                    return hasData;
                }
            };
        }
        public void stopLooper() {
            if (Looper.myLooper()!=null)
                Looper.myLooper().quitSafely();
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                if(Looper.myLooper() == null)
                    Looper.prepare();
                client.postUsers(email, password1, Looper.myLooper());
            } catch (Exception e) {
                Log.e(TAG, String.format("doInBackground(%s)", params), e);
                return false;
            }
            Looper.loop();
            return hasData;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            Log.d(TAG, String.format("onPostExecute(%b)", (boolean)success));
            if (success) {
                //TODO: Link to next activity
            } else {

            }
        }

        @Override
        protected void onCancelled() {

        }
    }
}
