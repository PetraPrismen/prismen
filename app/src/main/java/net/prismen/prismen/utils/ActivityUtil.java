package net.prismen.prismen.utils;

/**
 * Created by iri on 26.02.16.
 */

import android.content.Context;
import android.content.Intent;

import net.prismen.prismen.lektionen.LektionenSeite;
import net.prismen.prismen.lernen_vorbereiten.LernenVorbereitenSeite;
import net.prismen.prismen.themen.Themenseite;


public class ActivityUtil {
    private Context context;

    public ActivityUtil(Context context) {
        this.context = context;
    }

    public void startVerwaltenSeite() {
        context.startActivity(new Intent(context, Themenseite.class));
    }

    public void startLernenVorbereitenSeite() {
        context.startActivity(new Intent(context, LernenVorbereitenSeite.class));
    }

    public void startLektionenSeite(String thema_id, String thema_name) {
        //final Intent i =;
       // i.putExtra("id_thema", thema_id);
        //i.putExtra("thema_name", thema_name);
        context.startActivity(new Intent(context, LektionenSeite.class)
                .putExtra("id_thema",thema_id)
                .putExtra("thema_name",thema_name));
    }
}
