package edu.uwf.tabletopgroup.tabletop_squire.game_room;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.uwf.tabletopgroup.R;
import edu.uwf.tabletopgroup.models.Player;
import edu.uwf.tabletopgroup.models.User;
import edu.uwf.tabletopgroup.rest.TableTopKeys;

/**
 * TableTopSquire
 * InviteDialog.java
 *
 * TODO: Write Description
 *
 * Created By Michael Kimball
 * On 4/18/16
 */
public class InviteDialog extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_join_game_id, container, false);
        final EditText input = (EditText)v.findViewById(R.id.game_id);
        Button invite = (Button)v.findViewById(R.id.join);
        invite.setText("Invite");
        input.setHint("Player Name");
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = input.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getActivity(), "Enter a player to invite.", Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(TableTopKeys.ACTION_INVITE_PLAYER_NAME);
                    i.putExtra(TableTopKeys.KEY_NAME, name);
                    i.putExtra(TableTopKeys.KEY_GAME_ID, User.getCurrentGame().getGameId());
                    getActivity().sendBroadcast(i);
                    Toast.makeText(getActivity(), "Player invited.", Toast.LENGTH_LONG).show();
                }
                dismiss();
            }
        });
        return v;
    }
}
