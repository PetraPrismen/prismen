package net.prismen.prismen.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import net.prismen.prismen.prismen.seite_hinzufuegen.KarteFragment;
import net.prismen.prismen.R;

/**
 * Created by iri on 12.12.15.
 */
public class AlertDialogFragmentSeiteBearbeiten extends DialogFragment implements AdapterView.OnItemSelectedListener{

    KarteFragment fragment = new KarteFragment();
    TextView textFrage;
    EditText editTextFrage;


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:// typ Antwort
                textFrage.setVisibility(View.INVISIBLE);
                editTextFrage.setVisibility(View.INVISIBLE);
                //getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                break;
            case 1:// typ Karte
                textFrage.setVisibility(View.VISIBLE);
                editTextFrage.setVisibility(View.VISIBLE);
                //getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_placeholder,fragment).commit();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /* The activity that creates an instance of this dialog fragment must
         * implement this interface in order to receive event callbacks.
         * Each method passees the DialogFragment in case the host needs to query it.
         */
    public interface AlertDialogListener2{
        void onDialogSeiteBearbeitenPositiveClick(DialogFragment dialog, String id);
    }

    // Use this instance of the interface to deliver action events
    AlertDialogListener2 listener;
    // Override the Fragment.onAttach() method to instantiate the AlertDialogListener
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try{
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (AlertDialogListener2) activity;
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
        String frage = getArguments().getString("frage");
        String antwort = getArguments().getString("antwort");
        final String id = getArguments().getString("id");

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        View myView = inflater.inflate(R.layout.seite_bearbeiten, null);
        alertDialogBuilder.setView(myView);

        alertDialogBuilder.setTitle(R.string.titleDialogSeiteBearbeiten);


        // populate the typ spinner

        Spinner spinner = (Spinner) myView.findViewById(R.id.spinnerTyp);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.seiten_typ, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        textFrage = (TextView) myView.findViewById(R.id.textView_frage);
        editTextFrage = (EditText) myView.findViewById(R.id.editText_frage);

        if (frage != null){
            spinner.setSelection(1);
            textFrage.setVisibility(View.VISIBLE);
            editTextFrage.setVisibility(View.VISIBLE);
            editTextFrage.setText(frage);
        }

        EditText editTextAntwort = (EditText) myView.findViewById(R.id.editText_antwort);
        editTextAntwort.setText(antwort);

        // Positive button
        alertDialogBuilder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Send the positive button event back to the calling activity
                listener.onDialogSeiteBearbeitenPositiveClick(AlertDialogFragmentSeiteBearbeiten.this, id);
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
