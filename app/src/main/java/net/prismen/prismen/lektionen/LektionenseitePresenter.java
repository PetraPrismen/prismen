package net.prismen.prismen.lektionen;

import android.content.Context;
import android.widget.ExpandableListView;

import net.prismen.prismen.data.Lektion;
import net.prismen.prismen.data.LektionDataContract;
import net.prismen.prismen.data.db.LektionDataDB;

import java.util.ArrayList;

/**
 * Created by iri on 16.03.16.
 */
public class LektionenseitePresenter implements LektionenseiteContract.Presenter {

    private LektionenseiteContract.View view;
    private LektionDataContract lektionenData;



    public LektionenseitePresenter (Context ctx,LektionenseiteContract.View view){
        this.view = view;
        lektionenData = new LektionDataDB(ctx);
    }

    @Override
    public void loadLektionenForThema(String thema_id){
        try{
            ArrayList<Lektion> lektionnen = lektionenData.getLektionenForThema(thema_id);
            view.showLektionen(lektionnen);
        }
        catch (Exception e){
            e.printStackTrace();
            view.showErrorGetLektionen();
        }

    }

    @Override
    public void deleteLektion(String id_lektion) {
        try {
            lektionenData.deleteLektion(id_lektion);
        }
        catch (Exception e){
            e.printStackTrace();
            view.showErrorDeleteLektion();
        }

    }

    @Override
    public void updateLektion(String id, String newName) {
        try {
            lektionenData.renameLektion(id,newName);
        }
        catch (Exception e){
            e.printStackTrace();
            view.showErrorUpdateLektion();
        }

    }

    @Override
    public void insertLektion(Lektion l) {
        try {
            lektionenData.insertLektion(l);
        }
        catch (Exception e){
            e.printStackTrace();
            view.showErrorInsertLektion();
        }

    }
}
