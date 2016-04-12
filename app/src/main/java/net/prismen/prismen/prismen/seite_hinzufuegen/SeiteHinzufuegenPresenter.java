package net.prismen.prismen.prismen.seite_hinzufuegen;

import android.content.Context;

import net.prismen.prismen.data.PrismaDataContract;
import net.prismen.prismen.data.Seite;
import net.prismen.prismen.data.db.PrismaDataDB;

/**
 * Created by iri on 19.03.16.
 */
public class SeiteHinzufuegenPresenter implements SeiteHinzufuegenContract.Presenter{

    private SeiteHinzufuegenContract.View view;
    private PrismaDataContract prismenData;

    public SeiteHinzufuegenPresenter(Context ctx,SeiteHinzufuegenContract.View view){
        this.view = view;
        prismenData = new PrismaDataDB(ctx);
    }

    @Override
    public void saveSeiteForPrisma(String prisma_id,Seite st) {
        try{
            prismenData.insertSeite(prisma_id,st);
            view.showSeiteInsertedOK();
        }
        catch(Exception e){
            e.printStackTrace();
            view.showErrorSaveSeite();
        }
    }
}
