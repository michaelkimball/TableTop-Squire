package edu.uwf.tabletopgroup.tabletop_squire.game_room;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edu.uwf.tabletopgroup.R;
import edu.uwf.tabletopgroup.models.Message;

/**
 * TableTopSquire
 * MessageAdapter.java
 *
 * TODO: Write Description
 *
 * Created By Michael Kimball
 * On 4/17/16
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Message> mMessages;

    public MessageAdapter(List<Message> messages) {
        mMessages = messages;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = -1;
        View v = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.row_message, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Message message = mMessages.get(position);
        viewHolder.setMessage(message.getMessage());
        viewHolder.setName(message.getName());
    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mMessageView;
        private TextView mPlayerNameView;
        public ViewHolder(View itemView) {
            super(itemView);
            mMessageView = (TextView) itemView.findViewById(R.id.message);
            mPlayerNameView = (TextView) itemView.findViewById(R.id.player_name);
        }

        public void setMessage(String message) {
            if (null == mMessageView) return;
            if(null == message) return;
            mMessageView.setText(message);
        }

        public void setName(String name){
            if(null == mPlayerNameView) return;
            if(null == name) return;
            mPlayerNameView.setText(name);
        }
    }
}
