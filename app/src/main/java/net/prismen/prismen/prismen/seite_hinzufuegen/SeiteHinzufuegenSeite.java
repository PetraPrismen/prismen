package net.prismen.prismen.prismen.seite_hinzufuegen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.prismen.prismen.R;
import net.prismen.prismen.data.Seite;
import net.prismen.prismen.start.Startseite;

/**
 * Created by iri on 07.01.16.
 */
public class SeiteHinzufuegenSeite extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
                                                                        SeiteHinzufuegenContract.View{


    private Toolbar toolbar;
    private String id_prisma;
    private String prisma_name;

    private SeiteHinzufuegenPresenter presenter;

    KarteFragment fragment = new KarteFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seite_hinzufuegen_seite);
        this.overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);

        // get data from intent
        Intent i = this.getIntent();
        id_prisma = i.getStringExtra("id_prisma");
        prisma_name = i.getStringExtra("prisma_name");

        // set prism name in TextView
        TextView prism_name_view = (TextView) findViewById(R.id.prisma_name);
        prism_name_view.setText(prisma_name);

        // populate the typ spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinnerTyp);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.seiten_typ,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        // set toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.titleDialogSeiteHinzufuegen);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new SeiteHinzufuegenPresenter(this,this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_seite_hinzufuegen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.bt_seite_speichern) {
            // get the data for typ, frage and antwort
            Spinner spTyp = (Spinner)findViewById(R.id.spinnerTyp);
            int posSelectedSpinner = spTyp.getSelectedItemPosition();
            EditText editTextAntwort = (EditText) findViewById(R.id.editText_antwort);
            String antwort = editTextAntwort.getText().toString();
            if (antwort != null && !antwort.matches(" ") && !antwort.matches("")) {
                EditText editTextFrage = null;
                String frage = null;
                if (posSelectedSpinner == 1) {
                    editTextFrage = (EditText) findViewById(R.id.editText_frage);
                    frage = editTextFrage.getText().toString();
                }
                Seite st = new Seite(frage, antwort, posSelectedSpinner, id_prisma);
                presenter.saveSeiteForPrisma(id_prisma, st);
            }
            else{
                Toast.makeText(this,R.string.seite_not_saved_void_frage, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if  (id == android.R.id.home){
            finish();
        }
        if (id == R.id.bt_home) {
            // go to Startseite
            final Intent i = new Intent(getApplicationContext(), Startseite.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemSelected(AdapterView parent,View view,int pos, long id){
        switch (pos){
            case 0:// typ Antwort
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                break;
            case 1:// typ Karte
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_placeholder, fragment).commit();
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView parent){

    }

    @Override
    public void showErrorSaveSeite() {
        Toast.makeText(this,R.string.error_save, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSeiteInsertedOK() {
        Toast.makeText(this," 1 Seite hinzugefügt ", Toast.LENGTH_SHORT).show();
        // clear the Edittext fields to insert new
        EditText editTextAntwort = (EditText) findViewById(R.id.editText_antwort);
        editTextAntwort.setText("");
        EditText editTextFrage = (EditText) findViewById(R.id.editText_frage);
        if (editTextAntwort != null) {
            editTextFrage.setText("");
        }
    }
}
