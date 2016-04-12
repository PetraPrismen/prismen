package net.prismen.prismen.themen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.app.Dialog;
import android.widget.ListView;

import net.prismen.prismen.data.Thema;
import net.prismen.prismen.lektionen.LektionenSeite;
import net.prismen.prismen.R;
import net.prismen.prismen.start.Startseite;
import net.prismen.prismen.utils.AlertDialogFragment;
import net.prismen.prismen.utils.AlertDialogFragment.AlertDialogListener;
import net.prismen.prismen.utils.MyThemenArrayAdapter;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Themenseite extends AppCompatActivity implements AlertDialogListener,
                                                    ThemenseiteContract.View{

    private Toolbar toolbar;
    AlertDialogFragment alertdFragment;
    private ThemenseitePresenter presenter;
    MyThemenArrayAdapter themenAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Layout
        setContentView(R.layout.verwalten_seite);
        // Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Presenter
        presenter = new ThemenseitePresenter(this,this);
        presenter.loadThemen();
    }

    /**
     * Presenter Callback
     * @param themen Themen to show
     */
    @Override
    public void showThemen(ArrayList<Thema> themen) {
        Log.i("show themen ", "nb_themen: " + themen.size());
        themenAdapter = new MyThemenArrayAdapter(this,android.R.layout.simple_list_item_2,themen);
        setAdapterListThemen();
    }

    /**
     * Find ListView
     * associate it with Adapter themenAdapter
     */
    private void setAdapterListThemen(){
        final ListView vlistThemen = (ListView)findViewById(R.id.list_themen);
        vlistThemen.setAdapter(themenAdapter);
        // Listener for Click on Thema item from List of Themen
        vlistThemen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Thema th = themenAdapter.getThema(position);
                showLektionenseiteForThema(th);
            }
        });
        registerForContextMenu(vlistThemen);
    }

    /**
     * Show Lektionenseite when thema selected
     * @param th selected Thema
     */
    public void showLektionenseiteForThema(Thema th){
        final Intent i = new Intent(getApplicationContext(), LektionenSeite.class);
        i.putExtra("id_thema", th.getId());
        i.putExtra("thema_name", th.getName());
        startActivity(i);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        getMenuInflater().inflate(R.menu.context_menu_themen_lektionen, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }


    public boolean onContextItemSelected(MenuItem item){
        final AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        Thema th = themenAdapter.getThema(index);
        String id_thema_item = th.getId();
        String thema_item_name = th.getName();

        switch (item.getItemId()){
            case R.id.opt_bearbeiten: {
                // prepare arguments to show in dialog
                Bundle args = new Bundle();
                args.putInt("title", R.string.titleDialogThemaBearbeiten);
                args.putString("id", id_thema_item);
                args.putString("name",thema_item_name);
                args.putInt("position", index);
                showDialogFragment(args);
                return true;
            }
            case R.id.opt_loeschen: {
                presenter.deleteThema(id_thema_item);
                themenAdapter.removeThema(index);
                return true;
            }
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_verwalten, menu);
        return true;
    }

    /**
     * Item selected in options menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // option Add Thema selected
        if (id == R.id.bt_add_thema_lektion) {
            Bundle args = new Bundle();
            args.putInt("title", R.string.titleDialogAddThema);
            showDialogFragment(args);
            return true;
        }
        if (id == R.id.bt_home) {
            // go to Startseite
            final Intent i = new Intent(getApplicationContext(), Startseite.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Click on save button in Dialog to add or edit Thema
     * @param dialog the dialog
     * @param id id edited thema (null if new thema)
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String id, int position){
        // get data from dialog
        Dialog dialogView = dialog.getDialog();
        EditText editTexThemaName = (EditText) dialogView.findViewById(R.id.editTextNameThemaLektion);
        String newName = editTexThemaName.getText().toString();

        // if id  => update
        if (id!=null){
            themenAdapter.updateThema(position, newName);
            presenter.updateThema(id, newName);
        }
        // no id => new thema => insert
        else{
            Thema th = new Thema(newName);
            Log.i("******* new thema *******","id:"+th.getId());
            presenter.insertThema(th);
            themenAdapter.addThema(th);
        }
    }




    public void showDialogFragment(Bundle args) {
        AlertDialogFragment alertdFragment = new AlertDialogFragment();
        alertdFragment.setArguments(args);
        alertdFragment.show(getSupportFragmentManager(), "Alert Dialog Fragment");
    }


    /**
     * Inform User about errors handling persistent data
     */

    @Override
    public void showErrorGetThemen() {
        Toast.makeText(this,R.string.error_get_themen, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorDeleteThema() {
        Toast.makeText(this,R.string.error_delete, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorUpdateThema() {
        Toast.makeText(this,R.string.error_update, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorSaveThema() {
        Toast.makeText(this,R.string.error_save, Toast.LENGTH_LONG).show();
    }



}
