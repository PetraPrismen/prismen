package net.prismen.prismen.lernen_vorbereiten;

import android.content.Context;

import net.prismen.prismen.data.Lektion;
import net.prismen.prismen.data.LektionDataContract;
import net.prismen.prismen.data.Thema;
import net.prismen.prismen.data.ThemaDataContract;
import net.prismen.prismen.data.db.LektionDataDB;
import net.prismen.prismen.data.db.ThemaDataDB;

import java.util.ArrayList;

/**
 * Created by iri on 20.03.16.
 */
public class LernenVorbereitenPresenter implements LernenVorbereitenContract.Presenter {

    private LernenVorbereitenContract.View view;
    private ThemaDataContract themenData;
    private LektionDataContract lektionenData;

    public LernenVorbereitenPresenter(Context ctx, LernenVorbereitenContract.View view){
        this.view = view;
        themenData = new ThemaDataDB(ctx);
        lektionenData = new LektionDataDB(ctx);
    }


    @Override
    public void getAllThemen() {
        try{
            ArrayList<Thema> themen = themenData.getAllThemen();
            view.showThemen(themen);
        }
        catch(Exception e){
            e.printStackTrace();
            view.showErrorGetThemen();
        }
    }

    @Override
    public void getLektionenForThema(String thema_id) {
        try{
            ArrayList<Lektion> lektionen = lektionenData.getLektionenForThema(thema_id);
            view.showLektionen(lektionen);
        }
        catch (Exception e){
            e.printStackTrace();
            view.showErrorGetLektionen();
        }

    }
}
