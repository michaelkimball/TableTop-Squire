package edu.uwf.tabletopgroup.tabletop_squire.game_lobby;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.uwf.tabletopgroup.models.Invite;
import edu.uwf.tabletopgroup.models.Player;
import edu.uwf.tabletopgroup.models.User;
import edu.uwf.tabletopgroup.R;
import edu.uwf.tabletopgroup.rest.TableTopKeys;

/**
 * TableTopSquire
 * GameLobbyFragment.java
 *
 * TODO: Write Description
 *
 * Created By Michael Kimball
 * On 4/13/16
 */
public class GameLobbyFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_lobby, container, false);
        ArrayList<Invite> testInvites = new ArrayList<>();
        testInvites.add(new Invite("Bilbo", "Game of Thrones", "123"));
        testInvites.add(new Invite("Bilbo", "Game of Thrones", "123"));
        testInvites.add(new Invite("Bilbo", "Game of Thrones", "123"));
        testInvites.add(new Invite("Bilbo", "Game of Thrones", "123"));
        testInvites.add(new Invite("Bilbo", "Game of Thrones", "123"));
        testInvites.add(new Invite("Bilbo", "Game of Thrones", "123"));
        testInvites.add(new Invite("Bilbo", "Game of Thrones", "123"));
        testInvites.add(new Invite("Bilbo", "Game of Thrones", "123"));
        User.setInvites(testInvites);
        InviteAdapter inviteAdapter = new InviteAdapter(this, User.getInvites());
        ListView pendingInvites = (ListView)v.findViewById(R.id.pending_invites);
        pendingInvites.setAdapter(inviteAdapter);
        final EditText gameNameET = (EditText)v.findViewById(R.id.game_name);
        Button createGameB = (Button)v.findViewById(R.id.create_game);
        createGameB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gameName = gameNameET.getText().toString();
                if(!gameName.isEmpty()) {
                    try {
                        JSONObject object = new JSONObject();
                        Player player = new Player(User.getUsername(), User.getEmail(), User.getCharacter(0));
                        object.put(TableTopKeys.KEY_GAME, gameName);
                        object.put(TableTopKeys.KEY_PLAYER, player.toJSON());
                        Intent i = new Intent(TableTopKeys.ACTION_CREATE_GAME);
                        i.putExtra(TableTopKeys.KEY_GAME, object.toString());
                        getActivity().sendBroadcast(i);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        });
        return v;
    }
}
