package edu.uwf.tabletopgroup.tabletop_squire;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.github.nkzawa.socketio.client.*;



/**
 * A simple {@link Fragment} subclass.
 */
public class ChatBoxFragment extends Fragment {

    private EditText chatbox;

    public ChatBoxFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        chatbox = (EditText) getActivity().findViewById(R.id.chatbox);

        //
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_box, container, false);

    }

}
