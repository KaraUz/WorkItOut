package com.example.karolis.workitout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.example.karolis.workitout.dao.Database;
import com.example.karolis.workitout.dataobjects.Workout;

/**
 * Created by Karolis on 2016-11-18.
 */

public class ConfirmDeletionDialog  extends DialogFragment {
    public ConfirmDeletionDialog() {
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final Bundle bundle = this.getArguments();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you really want to delete this?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Database database = (Database) bundle.getSerializable("database");
                        Workout workout  = (Workout) bundle.getSerializable("workout");

                        database.deleteWorkout(workout);
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        return;
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
