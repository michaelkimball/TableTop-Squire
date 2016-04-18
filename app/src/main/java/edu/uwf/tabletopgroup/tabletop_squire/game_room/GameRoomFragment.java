package edu.uwf.tabletopgroup.tabletop_squire.game_room;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import edu.uwf.tabletopgroup.R;
import edu.uwf.tabletopgroup.models.Game;
import edu.uwf.tabletopgroup.models.Message;
import edu.uwf.tabletopgroup.models.Player;
import edu.uwf.tabletopgroup.rest.TableTopKeys;

/**
 * TableTopSquire
 * GameRoomFragment.java
 *
 * TODO: Write Description
 *
 * Created By Michael Kimball
 * On 4/16/16
 */
public class GameRoomFragment extends Fragment {
    private static final String TAG = "GameRoomFragment";
    private EditText mInputMessageView;
    private RecyclerView mMessagesView;
    private List<Message> mMessages = new ArrayList<>();
    private RecyclerView.Adapter mAdapter;
    private Game game;
    private Player player;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction(TableTopKeys.ACTION_MESSAGE_RECEIVED);
        getActivity().registerReceiver(receiver, filter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_room, container, false);
        Bundle args = getArguments();
        if(args.containsKey(TableTopKeys.KEY_GAME)) {
            game = args.getParcelable(TableTopKeys.KEY_GAME);
        }
        if(args.containsKey(TableTopKeys.KEY_PLAYER))
            player = args.getParcelable(TableTopKeys.KEY_PLAYER);
        mMessagesView = (RecyclerView) v.findViewById(R.id.chat_display);
        mMessagesView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMessagesView.setAdapter(mAdapter = new MessageAdapter(mMessages
        ));

        ImageButton sendButton = (ImageButton) v.findViewById(R.id.send);
        mInputMessageView = (EditText) v.findViewById(R.id.message_box);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
        return v;
    }

    private void sendMessage(){
        String message = mInputMessageView.getText().toString().trim();
        mInputMessageView.setText("");
        Message m = new Message(player.getCharacter().getName(), message, true);
        m.setGameId(game.getGameId());
        addMessage(m);
        //TODO emit message
        //socket.emit("message", sendText);
        Intent i = new Intent(TableTopKeys.ACTION_SEND_MESSAGE_TO_ROOM);
        i.putExtra(TableTopKeys.KEY_MESSAGE, m);
        getActivity().sendBroadcast(i);

    }

    private void addMessage(Message message) {

        mMessages.add(message);
        mAdapter = new MessageAdapter(mMessages);
        mAdapter.notifyItemInserted(0);
        scrollToBottom();
    }

    private void scrollToBottom() {
        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.e(TAG, "onReceive " + action);
            switch(action){
                case TableTopKeys.ACTION_MESSAGE_RECEIVED:
                    onMessageSent(intent);
                    break;
            }
        }
        public void onMessageSent(Intent intent){
            Log.d("SBR", "onMessageSent() start");
            String message = intent.getStringExtra("message");
            String playerName = intent.getStringExtra("playerName");
            addMessage(new Message(playerName, message));
        }

    };
}
