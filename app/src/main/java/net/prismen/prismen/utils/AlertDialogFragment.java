package net.prismen.prismen.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import net.prismen.prismen.R;

/**
 * Created by iri on 12.12.15.
 */
public class AlertDialogFragment extends DialogFragment {
    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passees the DialogFragment in case the host needs to query it.
     */
    public interface AlertDialogListener{
        void onDialogPositiveClick(DialogFragment dialog, String id,@Nullable int position);
    }

    // Use this instance of the interface to deliver action events
    AlertDialogListener listener;
    // Override the Fragment.onAttach() method to instantiate the AlertDialogListener
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try{
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (AlertDialogListener) activity;
        }catch(ClassCastException e){
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString() + " must implement AlertDialogListener");
        }
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle bundle){
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final int title = getArguments().getInt("title");
        String name = getArguments().getString("name");
        final String id = getArguments().getString("id");
        final int pos= getArguments().getInt("position");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // TODO what ist the root parent (null not recommended)
        View myView = inflater.inflate(R.layout.form_add_edit_thema_lektion, null);



        alertDialogBuilder.setView(myView);

        if (id != null) {
            EditText text = (EditText) myView.findViewById(R.id.editTextNameThemaLektion);
            text.setText(name);
        }


        alertDialogBuilder.setTitle(title);

        // Positive button
        alertDialogBuilder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Send the positive button event back to the calling activity
                listener.onDialogPositiveClick(AlertDialogFragment.this,id,pos);
            }
        });

        // Negative Button
        alertDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO cancel then do nothing ??? oder doch  dialog.dismiss();
            }
        });

        return alertDialogBuilder.create();
    }



}
