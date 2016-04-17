package edu.uwf.tabletopgroup.rest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

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
        }
    }

    private void onGameCreated(Context context, Intent intent) {
        Bundle bundle = intent.getBundleExtra(TableTopKeys.KEY_GAME);
        Game game = bundle.getParcelable(TableTopKeys.KEY_GAME);
        User.setCurrentGame(game);
        Player player = new Player(User.getUsername(), User.getEmail());
        player.setCharacter(User.getCharacter(0));
        game.addPlayer(player);
        bundle.putParcelable(TableTopKeys.KEY_GAME, game);
        bundle.putParcelable(TableTopKeys.KEY_PLAYER, player);
        Intent i = new Intent(context, GameRoomActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(TableTopKeys.KEY_GAME, bundle);
        context.startActivity(i);
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
}
