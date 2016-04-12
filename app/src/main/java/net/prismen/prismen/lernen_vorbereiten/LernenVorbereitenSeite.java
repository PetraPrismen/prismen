package net.prismen.prismen.lernen_vorbereiten;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.prismen.prismen.R;
import net.prismen.prismen.data.Lektion;
import net.prismen.prismen.data.Thema;

import net.prismen.prismen.lernen.Lernenseite;
import net.prismen.prismen.start.Startseite;
import net.prismen.prismen.utils.MyThemenArrayAdapter;

import java.util.ArrayList;

/**
 * Created by iri on 14.01.16.
 */
public class LernenVorbereitenSeite extends AppCompatActivity implements LernenVorbereitenContract.View{

    private Toolbar toolbar;
    ListView listLektionen = null;


    String id_lektion_to_learn = " ";
    String name_lektion_to_learn = " ";

    private LernenVorbereitenPresenter presenter;
    private MyThemenArrayAdapter themenAdapter;
    private MyLektionenLernenArrayAdapter lektionenAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lernen_vorbereiten_seite);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new LernenVorbereitenPresenter(this, this);
        presenter.getAllThemen();
    }

    public void showThemen(ArrayList<Thema> themen) {
        themenAdapter = new MyThemenArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, themen);
        setAdapterListThemen();
    }

    private void setAdapterListThemen(){
        final ListView listThemen = (ListView)findViewById(R.id.list_themen_lernen);
        listThemen.setAdapter(themenAdapter);
        listThemen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                id_lektion_to_learn = " ";
                for (int i = 0; i < listThemen.getAdapter().getCount(); i++) {
                    listThemen.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
                parent.getChildAt(position).setBackgroundColor(Color.LTGRAY);
                Thema th = themenAdapter.getThema(position);
                String id_thema = th.getId();
                presenter.getLektionenForThema(id_thema);
            }
        });
    }

    @Override
    public void showLektionen(ArrayList<Lektion> lektionen) {
        listLektionen = (ListView) findViewById(R.id.list_lektionen_lernen);
        lektionenAdapter = new MyLektionenLernenArrayAdapter(this, R.layout.lektionen_lernen,lektionen);
        listLektionen.setAdapter(lektionenAdapter);
    }

    public void onCheckboxClicked(final View v) {
        int position_lektion = listLektionen.getPositionForView(v);
        // get lektion id
        Lektion lk = lektionenAdapter.getLektion(position_lektion);
        String id_lektion = lk.getId();
        
        //find out if checkbox is checked
        CheckBox actual_chkbx = (CheckBox)v;
        if (actual_chkbx.isChecked()){
            // uncheck other Lektionen
            CheckBox cb;
            for(int i=0;i<listLektionen.getChildCount();i++){
                cb = (CheckBox)listLektionen.getChildAt(i).findViewById(R.id.checkbox_lektion);
                cb.setChecked(false);
            }
            actual_chkbx.setChecked(true);
            lektionenAdapter.notifyDataSetChanged();
            id_lektion_to_learn = id_lektion;
            name_lektion_to_learn = lk.getName();

        }
        else{
            id_lektion_to_learn = " ";
            name_lektion_to_learn = " ";
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lernen, menu);
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

        if (id == R.id.bt_home) {
            // go to Startseite
            final Intent i = new Intent(getApplicationContext(), Startseite.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickLernenAnfangen(View v){

        if (!id_lektion_to_learn.equals(" ")) {
            final Intent i = new Intent(this,
                    Lernenseite.class);
            i.putExtra("to_learn_id", id_lektion_to_learn);
            i.putExtra("to_learn_name", name_lektion_to_learn);
            startActivity(i);
        }
        else{
            Toast.makeText(this,R.string.for_learning_should_choose_lektion, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void showErrorGetThemen() {
        Toast.makeText(this, R.string.error_get_themen, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorGetLektionen() {
        Toast.makeText(this, R.string.error_get_lektionen, Toast.LENGTH_LONG).show();
    }

    public class MyLektionenLernenArrayAdapter extends ArrayAdapter<Lektion> {
        Context ctx;
        int layoutResId;
        ArrayList<Lektion> lektionen;


        public MyLektionenLernenArrayAdapter(Context ctx,int layoutResId,ArrayList<Lektion> lektionenList){
            super(ctx,layoutResId,lektionenList);
            this.layoutResId = layoutResId;
            this.ctx = ctx;
            this.lektionen = lektionenList;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // inflate Layout
            if(convertView==null){
                LayoutInflater inflater = ((Activity) ctx).getLayoutInflater();
                convertView = inflater.inflate(layoutResId, parent, false);
            }
            // get object item based on the position
            Lektion lektion = lektionen.get(position);

            // get the TextView and then set the text (item name) and tag (item ID) values
            TextView textViewItem1 = (TextView) convertView.findViewById(R.id.text_lektionen_lernen);
            textViewItem1.setText(lektion.getName());

            return convertView;
        }

        public Lektion getLektion(int position){
            return lektionen.get(position);
        }
    }
}
