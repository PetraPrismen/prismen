package net.prismen.prismen.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.prismen.prismen.R;
import net.prismen.prismen.utils.ActivityUtil;


public class Startseite extends AppCompatActivity implements StartseiteContract.View {

    private Toolbar toolbar;
    private StartseitePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startseite);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextAppearance(this, R.style.appTitleStyle);
        setSupportActionBar(toolbar);

        presenter = new StartseitePresenter(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_startseite, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.bt_close) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClickVerwalten(final View bView) {
        presenter.onClickVerwalten();
    }

    public void onClickLernen(final View bView){
        presenter.onClickLernen();
    }

    @Override
    public void startVerwaltenSeite() {
       new ActivityUtil(this).startVerwaltenSeite();
    }

    @Override
    public void startLernenVorbereitenSeite() {
        new ActivityUtil(this).startLernenVorbereitenSeite();
    }
}
