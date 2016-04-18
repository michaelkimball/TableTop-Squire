package edu.uwf.tabletopgroup.rest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.ArrayList;

import edu.uwf.tabletopgroup.models.Game;
import edu.uwf.tabletopgroup.models.Player;
import edu.uwf.tabletopgroup.models.User;
import edu.uwf.tabletopgroup.tabletop_squire.WelcomeActivity;
import edu.uwf.tabletopgroup.tabletop_squire.game_room.GameRoomActivity;

/**
 * TableTopSquire
 * SocketBroadcastReceiver.java
 *
 * TODO: Write Description
 *
 * Created By Michael Kimball
 * On 4/16/16
 */
public class SocketBroadcastReceiver extends BroadcastReceiver {
    public static final int MESSAGE_ID = 9403;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("SocketBroadcastReceiver", "onReceive() start");
        String action = intent.getAction();
        switch(action){
            case TableTopKeys.ACTION_MESSAGE_RECEIVED:
                onMessageSent(context, intent);
                break;
            case TableTopKeys.ACTION_GAME_CREATED:
                onGameCreated(context, intent);
                break;
            case TableTopKeys.ACTION_GAME_JOINED:
                onGameJoined(context, intent);
                break;
        }
    }

    private void onGameJoined(Context context, Intent intent) {
        Bundle bundle = intent.getBundleExtra(TableTopKeys.KEY_GAME);
        Game game = getGame(bundle);
        Player player = getPlayer();
        ArrayList<Player> inRoom = bundle.getParcelableArrayList(TableTopKeys.KEY_PLAYER_LIST);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        for(Player otherPlayer: inRoom){
            if(!otherPlayer.getName().equals(player.getName()))
                players.add(otherPlayer);
        }
        game.setPlayers(players);
        bundle = makeGameBundle(game, player);
        joinGame(context, bundle);
    }

    private void onGameCreated(Context context, Intent intent) {
        Bundle bundle = intent.getBundleExtra(TableTopKeys.KEY_GAME);
        Game game = getGame(bundle);
        Player player = getPlayer();
        game.addPlayer(player);
        bundle = makeGameBundle(game, player);
        joinGame(context, bundle);
    }

    public void onMessageSent(Context context, Intent intent){
        Log.d("SBR", "onMessageSent() start");
        String message = intent.getStringExtra("message");
        Intent i = new Intent(context, WelcomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notifyBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(android.R.drawable.ic_dialog_email)
                        .setContentTitle("TableTopSquire Message")
                        .setContentText(message)
                        .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MESSAGE_ID, notifyBuilder.build());
    }

    private void joinGame(Context context, Bundle bundle) {
        Intent i = new Intent(context, GameRoomActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(TableTopKeys.KEY_GAME, bundle);
        context.startActivity(i);
    }

    private Game getGame(Bundle bundle){
        Game game = bundle.getParcelable(TableTopKeys.KEY_GAME);
        User.setCurrentGame(game);
        return game;
    }

    private Player getPlayer() {
        Player player = new Player(User.getUsername(), User.getEmail());
        player.setCharacter(User.getCharacter(0));
        return player;
    }

    @NonNull
    private Bundle makeGameBundle(Game game, Player player) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(TableTopKeys.KEY_GAME, game);
        bundle.putParcelable(TableTopKeys.KEY_PLAYER, player);
        return bundle;
    }
}
