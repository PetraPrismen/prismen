
package net.prismen.prismen.prismen;

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
import android.widget.EditText;
import android.app.Dialog;
import android.widget.ExpandableListView;

import net.prismen.prismen.R;
import net.prismen.prismen.data.Prisma;
import net.prismen.prismen.data.Seite;
import net.prismen.prismen.prismen.seite_hinzufuegen.SeiteHinzufuegenSeite;
import net.prismen.prismen.start.Startseite;
import net.prismen.prismen.utils.AlertDialogFragment;
import net.prismen.prismen.utils.AlertDialogFragment.AlertDialogListener;
import net.prismen.prismen.utils.AlertDialogFragmentSeiteBearbeiten;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class PrismenVerwaltenSeite extends AppCompatActivity
        implements AlertDialogListener,ExpandableListView.OnChildClickListener,
                    AlertDialogFragmentSeiteBearbeiten.AlertDialogListener2,
                    PrismenseiteContract.View{

    AlertDialogFragment alertdFragment;
    AlertDialogFragmentSeiteBearbeiten alertFragmentSeite;

    private Toolbar toolbar;

    private String id_thema;
    private String id_lektion;

    ExpandableListView listPrismen;

    private PrismenseitePresenter presenter;

    int groupPos,childPos;


    PrismenExpandableListAdapter prismenExpAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialize Layout
        setContentView(R.layout.prismen_verwalten_seite);
        // custom animation to this activity
        this.overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
        // get infos from intent
        Intent i = this.getIntent();
        id_lektion = i.getStringExtra("id_lektion");
        String lektion_name = i.getStringExtra("lektion_name");
        String thema_name = this.getIntent().getStringExtra("thema_name");

        // add thema name on the top
        TextView title_up = (TextView) findViewById(R.id.thema_prismen_seite);
        String titleThema = getString(R.string.thema) + ": " + thema_name;
        title_up.setText(titleThema);

        //add lektion name next
        TextView lektion_up = (TextView) findViewById(R.id.lektion_prismen_seite);
        String titleLektion = getString(R.string.lektion) + ": " + lektion_name;
        lektion_up.setText(titleLektion);

        // set toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.prismen);
        setSupportActionBar(toolbar);

        // initialize presenter and call for the Prismen
        presenter = new PrismenseitePresenter(this, this);
        presenter.loadPrismenForLektion(id_lektion);

    }

    @Override
    public void onResume(){
        super.onResume();
        int length_goups = prismenExpAdapter.getGroupCount();
        for(int i=0;i< length_goups;i++){
            listPrismen.collapseGroup(i);
        }
    }

    @Override
    public void showPrismen(ArrayList<Prisma> prismen) {
        // load data to list with adapter
        prismenExpAdapter = new PrismenExpandableListAdapter(prismen,this,presenter);
        prismenExpAdapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),
                this);
        listPrismen = (ExpandableListView)findViewById(R.id.list_prismen);
        listPrismen.setAdapter(prismenExpAdapter);
        registerForContextMenu(listPrismen);
    }

    /**
     * get prismen from database and fill ArrayList prismItems
     * for each prism get the seiten, make a child object and fill ArrayList seitenItems
     */

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_themen_lektionen, menu);
    }


    public boolean onContextItemSelected(MenuItem item){
        ExpandableListView.ExpandableListContextMenuInfo info = (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition);
        int selected = item.getItemId();


        Prisma actual_prism = prismenExpAdapter.getGroup(groupPos);
        String  prisma_item_name = actual_prism.getName();
        String  id_prisma_item = actual_prism.getId();
        // Context menu selected for Prisma
        if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP){
            switch (selected){
                case R.id.opt_bearbeiten: {
                    Bundle args = new Bundle();
                    args.putInt("title", R.string.titleDialogAddPrismaBearbeiten);
                    args.putString("id", id_prisma_item);
                    args.putString("name", prisma_item_name);
                    showDialogFragment(args);
                    return true;
                }
                case R.id.opt_loeschen: {
                    // delete the prisma and its seiten
                    presenter.deletePrisma(id_prisma_item);
                    // actualize Array prismItems
                    prismenExpAdapter.deleteGroup(groupPos);
                    prismenExpAdapter.notifyDataSetChanged();
                    Toast.makeText(this,R.string.prisma_and_content_deleted, Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        }
        else{ // context menu selected for Seite
            childPos = ExpandableListView.getPackedPositionChild(info.packedPosition);
            Seite childItem = prismenExpAdapter.getChild(groupPos,childPos);
            String id_seite = childItem.getId();
            switch (selected){
                case R.id.opt_bearbeiten: {
                    Bundle args = new Bundle();
                    args.putString("id", id_seite);
                    args.putString("antwort",childItem.getAntwort());
                    if (childItem.getFrage() != null){
                        args.putString("frage",childItem.getFrage());
                    }
                    showDialogFragmentSeiteBearbeiten(args);
                    return true;
                }
                case R.id.opt_loeschen: {
                    presenter.deleteSeite(id_seite);
                    prismenExpAdapter.deleteChild(groupPos, childPos);
                    prismenExpAdapter.notifyDataSetChanged();
                    Toast.makeText(this,R.string.seite_deleted, Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        }

        return super.onContextItemSelected(item);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_prismen_verwalten, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // option add prisma selected
        if (id == R.id.bt_add_prisma) {
            Bundle args = new Bundle();
            args.putInt("title", R.string.titleDialogAddPrisma);
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

    public void showDialogFragmentSeiteBearbeiten(Bundle args) {
        AlertDialogFragmentSeiteBearbeiten alertdFragmentS = new AlertDialogFragmentSeiteBearbeiten();
        alertdFragmentS.setArguments(args);
        alertdFragmentS.show(getSupportFragmentManager(), "Alert Dialog Fragment");
    }
    /**
     * onClick method button save in Dialog for add or edit Prisma
     * @param dialog the dialog
     * @param id id prisma (if edit ) otherwise (new prisma) null
     */
    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String id, int position){
        // get data from dialog
        Dialog dialogView = dialog.getDialog();
        EditText editTextPrismaName = (EditText) dialogView.findViewById(R.id.editTextNameThemaLektion);
        String newNamePrisma = editTextPrismaName.getText().toString();

        // perform the suitable database operation
        // if id sent => update
        if (id!=null){
            presenter.updatePrisma(id,newNamePrisma);
            prismenExpAdapter.editGroup(groupPos,newNamePrisma);
            prismenExpAdapter.notifyDataSetChanged();
        }
        // else new prisma => insert + increment number of prismen for lektion
        else {
            Prisma pr = new Prisma(newNamePrisma,id_lektion);
            presenter.insertPrisma(pr);
            prismenExpAdapter.addGroup(pr);
            prismenExpAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDialogSeiteBearbeitenPositiveClick(DialogFragment dialog, String id){
        Dialog dialogView = dialog.getDialog();


        Spinner spTyp = (Spinner)dialogView.findViewById(R.id.spinnerTyp);
        int posSelectedSpinner = spTyp.getSelectedItemPosition();
        String frage = null;
        if (posSelectedSpinner==1) {
            EditText editTextFrage = (EditText) dialogView.findViewById(R.id.editText_frage);
            frage = editTextFrage.getText().toString();
        }

        EditText editTextAntwort = (EditText) dialogView.findViewById(R.id.editText_antwort);
        String antwort = editTextAntwort.getText().toString();
        if (antwort != null && !antwort.matches(" ") && !antwort.matches("")) {
            presenter.updateSeite(id,frage,antwort,posSelectedSpinner);
            // actualize the view
            prismenExpAdapter.editChild(groupPos, childPos, frage, antwort);
            prismenExpAdapter.notifyDataSetChanged();
        }
        else{
            Toast.makeText(this,R.string.inform_error_speichern_seite_bearbeiten_antwort_null, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        //Toast.makeText(PrismenVerwaltenSeite.this, "Clicked On Child",
          //      Toast.LENGTH_SHORT).show();
        return true;
    }


    public void onClickAddSeite(View v){
        int position = (int)v.getTag();

        Prisma actual_prism = prismenExpAdapter.getGroup(position);
        String  prisma_name = actual_prism.getName();
        String  id_prisma = actual_prism.getId();

        final Intent i = new Intent(getApplicationContext(), SeiteHinzufuegenSeite.class);
        i.putExtra("id_prisma", id_prisma);
        i.putExtra("prisma_name", prisma_name);
        startActivity(i);


        
    }


    /**
     * Inform User about errors handling persistent data
     */

    @Override
    public void showErrorGetPrismen() {
        Toast.makeText(this, R.string.error_get_prismen, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorUpdate() {
        Toast.makeText(this,R.string.error_update, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorInsert() {
        Toast.makeText(this,R.string.error_save, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorDelete() {
        Toast.makeText(this,R.string.error_delete, Toast.LENGTH_LONG).show();
    }
}

