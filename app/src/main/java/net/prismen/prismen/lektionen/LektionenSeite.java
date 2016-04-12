package net.prismen.prismen.lektionen;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.app.Dialog;
import android.widget.SimpleCursorAdapter;
import android.widget.ListView;

import net.prismen.prismen.data.Lektion;
import net.prismen.prismen.prismen.PrismenVerwaltenSeite;
import net.prismen.prismen.R;
import net.prismen.prismen.start.Startseite;
import net.prismen.prismen.utils.AlertDialogFragment;
import net.prismen.prismen.utils.AlertDialogFragment.AlertDialogListener;
import net.prismen.prismen.data.db.LektionenTbl;
import net.prismen.prismen.data.db.PrismenDatabase;
import net.prismen.prismen.data.db.ThemenTbl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class LektionenSeite extends AppCompatActivity implements AlertDialogListener,LektionenseiteContract.View{

    AlertDialogFragment alertdFragment;
    private Toolbar toolbar;

    String thema_name;
    private String id_thema;

    private LektionenseitePresenter presenter;
    MyArrayAdapterLektion lektionenAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lektionen_seite);
        this.overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
        // get Thema data from intent
        id_thema = this.getIntent().getStringExtra("id_thema");
        thema_name = this.getIntent().getStringExtra("thema_name");

        // add thema name on the top
        TextView title_up = (TextView)findViewById(R.id.thema_lektionen_seite);
        String titleThema = getString(R.string.thema) + ": " + thema_name;
        title_up.setText(titleThema);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.lektionen);
        setSupportActionBar(toolbar);

        presenter = new LektionenseitePresenter(this,this);
        presenter.loadLektionenForThema(id_thema);

    }

    /**
     * Presenter callback
     * @param lektionen Lektionen to show
     */

    @Override
    public void showLektionen(ArrayList<Lektion> lektionen) {
        lektionenAdapter = new MyArrayAdapterLektion(this,android.R.layout.simple_list_item_2,lektionen);
        setAdapterListLektionen();
    }


    /**
     * Find ListView
     * associate it with the Lektionen Adapter
     */
    private void setAdapterListLektionen() {
        final ListView vListLektionen = (ListView)findViewById(R.id.list_lektionen);
        vListLektionen.setAdapter(lektionenAdapter);
        vListLektionen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lektion lk = lektionenAdapter.getLektion(position);
                showPrismenseiteForLektion(lk);
            }
        });

        registerForContextMenu(vListLektionen);
    }


    /**
     * Show Prismenseite for a Lektion
     * @param lk selected Lektion
     */
    private void showPrismenseiteForLektion(Lektion lk){
        final Intent i = new Intent(getApplicationContext(), PrismenVerwaltenSeite.class);
        i.putExtra("id_lektion",  lk.getId());
        i.putExtra("thema_name", thema_name);
        i.putExtra("lektion_name", lk.getName());
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
        Lektion lk = lektionenAdapter.getLektion(index);
        String id_lektion_item = lk.getId();
        String lektion_item_name = lk.getName();
        switch (item.getItemId()){
            case R.id.opt_bearbeiten: {
                // prepare Arguments to show dialog bearbeiten
                Bundle args = new Bundle();
                args.putInt("title",R.string.titleDialogLektionBearbeiten);
                args.putString("id", id_lektion_item);
                args.putString("name",lektion_item_name);
                args.putInt("position",index);
                showDialogFragment(args);
                return true;
            }
            case R.id.opt_loeschen: {
                presenter.deleteLektion(id_lektion_item);
                lektionenAdapter.removeLektion(index);
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
     * Item selected options menu
     * @param item
     * @return
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //option Add Lektion selected
        if (id == R.id.bt_add_thema_lektion) {
            Bundle args = new Bundle();
            args.putInt("title", R.string.titleDialogAddLektion);
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


    public void showDialogFragment(Bundle args) {
        AlertDialogFragment alertdFragment = new AlertDialogFragment();
        alertdFragment.setArguments(args);
        alertdFragment.show(getSupportFragmentManager(), "Alert Dialog Fragment");
    }

    /**
     * onClick method button save in Dialog for add or edit Lektion
     * @param dialog the dialog
     * @param id id lektion (if edit ) otherwise (new lektion) null
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String id, int pos){
        // get the data from the dialog
        Dialog dialogView = dialog.getDialog();
        EditText editTexLektionName = (EditText) dialogView.findViewById(R.id.editTextNameThemaLektion);
        String newNameLektion = editTexLektionName.getText().toString();

        // if id sent => update
        if (id!=null){
            presenter.updateLektion(id,newNameLektion);
            lektionenAdapter.updateLektion(pos,newNameLektion);
        }
        // else new lektion => insert lektion
        else {
            Lektion lk = new Lektion(newNameLektion,id_thema);
            presenter.insertLektion(lk);
            lektionenAdapter.addLektion(lk);

        }

    }


    /**
     * Inform User about errors handling persistent data
     */

    @Override
    public void showErrorGetLektionen() {
        Toast.makeText(this, R.string.error_get_lektionen, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorUpdateLektion() {
        Toast.makeText(this,R.string.error_update, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorInsertLektion() {
        Toast.makeText(this,R.string.error_save, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorDeleteLektion() {
        Toast.makeText(this,R.string.error_delete, Toast.LENGTH_LONG).show();
    }

    /**
     * Adapter class for the list of Themen
     * extends ArrayAdapter
     */
    public class MyArrayAdapterLektion extends ArrayAdapter<Lektion> {
        Context myCtxt;
        int  layoutResId;
        ArrayList<Lektion> lektionen;

        public MyArrayAdapterLektion(Context ctx, int layoutResId, ArrayList<Lektion> lektionenList){
            super(ctx,layoutResId,lektionenList);
            this.layoutResId = layoutResId;
            this.myCtxt = ctx;
            this.lektionen = lektionenList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                // inflate the layout
                LayoutInflater inflater = ((Activity) myCtxt).getLayoutInflater();
                convertView = inflater.inflate(layoutResId, parent, false);
            }

            // get object item based on the position
            Lektion lektion = lektionen.get(position);


            // get the TextView and then set the text (item name) and tag (item ID) values
            // TODO show prismen nb
            TextView textViewItem1 = (TextView) convertView.findViewById(android.R.id.text1);
            //TextView textViewItem2 = (TextView) convertView.findViewById(android.R.id.text2);
            textViewItem1.setText(lektion.getName());
            //textViewItem2.setText(lektion.getNb_prismen() + " " + getString(R.string.prismen));
            return convertView;
        }

        public void addLektion(Lektion lk){
            this.lektionen.add(lk);
            notifyDataSetChanged();
        }

        public void updateLektion(int position,String newName){
            Lektion lk_change = this.lektionen.get(position);
            lk_change.setName(newName);
            notifyDataSetChanged();
        }

        public Lektion getLektion(int position){
            return lektionen.get(position);
        }

        public void removeLektion(int position){
            lektionen.remove(position);
            notifyDataSetChanged();
        }
    }

    
}
