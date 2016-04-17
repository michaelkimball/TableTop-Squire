package edu.uwf.tabletopgroup.tabletop_squire.game_lobby;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * TableTopSquire
 * JoinGameDialogBuilder.java
 *
 * TODO: Write Description
 *
 * Created By Michael Kimball
 * On 4/13/16
 */
public class JoinGameDialogBuilder extends AlertDialog.Builder {
    public JoinGameDialogBuilder(Context context, String gameName) {
        super(context);
        setMessage(gameName);
        setPositiveButton("Join", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do stuff
            }
        });
    }
}
