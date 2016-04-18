package edu.uwf.tabletopgroup.tabletop_squire.view_character;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.uwf.tabletopgroup.R;

/**
 * TableTopSquire
 * CharacterDialog.java
 *
 * TODO: Write Description
 *
 * Created By Michael Kimball
 * On 4/18/16
 */
public class CharacterDialog extends DialogFragment {
    public interface CharacterDialogListener{
        void onSave(String data);
    }
    private CharacterDialogListener listener;
    private String title;
    private int inputType;
    public static CharacterDialog newInstance(String title, int inputType, CharacterDialogListener listener){
        CharacterDialog dialog = new CharacterDialog();
        dialog.listener = listener;
        dialog.title = title;
        dialog.inputType = inputType;
        return dialog;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_join_game_id, container, false);
        final EditText input = (EditText)v.findViewById(R.id.game_id);
        input.setHint(title);
        input.setInputType(inputType);
        Button join = (Button)v.findViewById(R.id.join);
        join.setText("Save");
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSave(input.getText().toString());
                dismiss();
            }
        });
        return v;
    }
}
