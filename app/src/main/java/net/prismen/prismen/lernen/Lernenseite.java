package net.prismen.prismen.lernen;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import net.prismen.prismen.R;
import net.prismen.prismen.data.Prisma;
import net.prismen.prismen.data.Seite;
import net.prismen.prismen.data.db.LektionenTbl;
import net.prismen.prismen.data.db.PrismenDatabase;
import net.prismen.prismen.data.db.PrismenTbl;
import net.prismen.prismen.data.db.SeitenTbl;

import java.util.ArrayList;

/**
 * Created by iri on 15.01.16.
 */
public class Lernenseite extends AppCompatActivity implements GestureDetector.OnGestureListener,
                                                                LernenseiteContract.View{

    private Toolbar toolbar;
    private PrismenDatabase prismenDb;
    private String lektion_id,lektion_name;
    private String text_view_nb_prismen_message;

    private LernenseitePresenter presenter;

    private RadioGroup radio_group;

    ArrayList<Prisma> prismen;
    ArrayList<Seite> seiten;

    Prisma actual_prisma;
    Seite actual_seite;
    int actual_index_seite = 0;
    int actual_index_prisma = 0;
    int actual_index_prisma_to_show;
    int nb_seiten_actual_prisma;
    int nb_prismen_lektion;

    Button bt_prisma_name;
    TextView text_frage_view,text_antwort_view,text_view_nb_prismen;

    //int screen_width, screen_height;
    //LinearLayout.LayoutParams params_bt;

    private GestureDetectorCompat mDetector;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lernen_seite);


        lektion_id = getIntent().getStringExtra("to_learn_id");
        lektion_name = getIntent().getStringExtra("to_learn_name");


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(lektion_name);
        setSupportActionBar(toolbar);

        presenter = new LernenseitePresenter(this, this);
        presenter.getPrismenForLektion(lektion_id);

        mDetector = new GestureDetectorCompat(this, this);


    }

    @Override
    public void loadPrismen(ArrayList<Prisma> prismen) {
        this.prismen = prismen;
        nb_prismen_lektion = prismen.size();
        if (nb_prismen_lektion > 0) {
            actual_prisma = prismen.get(0);
            presenter.getSeitenForPrisma(actual_prisma.getId());
        }
        else{
            text_frage_view = (TextView) findViewById(R.id.text_frage_lernen);
            text_frage_view.setText(R.string.error_lektion_kein_prisma);
        }
    }

    @Override
    public void loadSeiten(ArrayList<Seite> seiten) {
        this.seiten = seiten;
        nb_seiten_actual_prisma = seiten.size();
        if (nb_seiten_actual_prisma > 0) {
            actual_seite = seiten.get(actual_index_seite);
            // View Prisma name
            bt_prisma_name = (Button) findViewById(R.id.bt_prisma_lernen);
            bt_prisma_name.setText(actual_prisma.getName());
            bt_prisma_name.setTag(false);

            // View
            text_view_nb_prismen = (TextView) findViewById(R.id.nb_prismen_lektion);
            text_view_nb_prismen_message = getString(R.string.prisma)+" 1 "+ getString(R.string.of) +" "+ nb_prismen_lektion;
            text_view_nb_prismen.setText(text_view_nb_prismen_message);

            // View frage
            text_frage_view = (TextView) findViewById(R.id.text_frage_lernen);
            String frage = actual_seite.getFrage();
            if (frage != null) {
                text_frage_view.setText(frage);
            } else {
                text_frage_view.setText("");
            }

            //View antwort
            text_antwort_view = (TextView) findViewById(R.id.text_antwort_lernen);
            String antwort = actual_seite.getAntwort();
            text_antwort_view.setVisibility(View.INVISIBLE);
            text_antwort_view.setText(" ");

            radio_group = (RadioGroup)findViewById(R.id.radio_group);
            radio_group.removeAllViews();

            for(int i=0;i<nb_seiten_actual_prisma;i++){
                RadioButton rb = new RadioButton(this);
                radio_group.addView(rb);
            }
            RadioButton rselected = (RadioButton)radio_group.getChildAt(0);
            rselected.setChecked(true);

        }
        else{
            text_frage_view = (TextView) findViewById(R.id.text_frage_lernen);
            text_frage_view.setText(R.string.error_prisma_keine_seite);
        }
    }

    public void onClickPrismaLernen(View v){
        // animate Prisma Name
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
            this.mDetector.onTouchEvent(event);
            // Be sure to call the superclass implementation
            return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if ( nb_prismen_lektion > 0 && nb_seiten_actual_prisma > 0) {
            // Gesture = swipe left to right
            if ((e2.getX() - e1.getX()) > 150) {
                actual_index_seite = (actual_index_seite + nb_seiten_actual_prisma - 1) % nb_seiten_actual_prisma;
                actual_seite = seiten.get(actual_index_seite);

                text_antwort_view.setVisibility(View.INVISIBLE);
                // View frage
                text_frage_view.setText(actual_seite.getFrage());
                //View antwort
                text_antwort_view.setText(actual_seite.getAntwort());

                RadioButton rselected = (RadioButton)radio_group.getChildAt(actual_index_seite);
                rselected.setChecked(true);

            }


            // Gesture = swipe right to left
            if ((e1.getX() - e2.getX()) > 150) {
                Log.i("*********", "swipe right to left");
                actual_index_seite = (actual_index_seite + 1) % nb_seiten_actual_prisma;
                actual_seite = seiten.get(actual_index_seite);

                text_antwort_view.setVisibility(View.INVISIBLE);
                // View frage
                text_frage_view.setText(actual_seite.getFrage());

                RadioButton rselected = (RadioButton)radio_group.getChildAt(actual_index_seite);
                rselected.setChecked(true);

            }
            // Gesture == swipe down
            if (e1.getY() < e2.getY()) {
            }
            // SWIPE UP
            if ((e1.getY() - e2.getY()) > 150) {
                text_antwort_view.setVisibility(View.VISIBLE);
                text_antwort_view.setText(actual_seite.getAntwort());
            }
        }
        return true;
    }


    public void onClickPreviousPrisma(View v){
        if (nb_prismen_lektion > 0) {
            actual_index_prisma = (actual_index_prisma + nb_prismen_lektion - 1) % nb_prismen_lektion;
            actual_prisma = prismen.get(actual_index_prisma);

            bt_prisma_name.setText(actual_prisma.getName());
            bt_prisma_name.setTag(false);

            actual_index_prisma_to_show = actual_index_prisma + 1;
            text_view_nb_prismen_message = getString(R.string.prisma)+" "+actual_index_prisma_to_show+" "+ getString(R.string.of) +" "+ nb_prismen_lektion;
            text_view_nb_prismen.setText(text_view_nb_prismen_message);

            presenter.getSeitenForPrisma(actual_prisma.getId());
        }
    }
    public void onClickNextPrisma(View v){
        if (nb_prismen_lektion > 0) {
            actual_index_prisma = (actual_index_prisma + 1) % nb_prismen_lektion;
            actual_prisma = prismen.get(actual_index_prisma);

            bt_prisma_name.setText(actual_prisma.getName());
            bt_prisma_name.setTag(false);

            actual_index_prisma_to_show = actual_index_prisma + 1;
            text_view_nb_prismen_message = getString(R.string.prisma)+" "+actual_index_prisma_to_show+" "+ getString(R.string.of) +" "+ nb_prismen_lektion;
            text_view_nb_prismen.setText(text_view_nb_prismen_message);

            text_frage_view.setText(" ");
            text_antwort_view.setText(" ");

            presenter.getSeitenForPrisma(actual_prisma.getId());
        }
    }



    @Override
    public void showErrorGetPrismen() {
        Toast.makeText(this, R.string.error_get_prismen, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorGetSeiten() {
        Toast.makeText(this,R.string.error_get_seiten, Toast.LENGTH_SHORT).show();

    }
}