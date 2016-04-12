package net.prismen.prismen.prismen;

import android.content.Context;

import net.prismen.prismen.data.Prisma;
import net.prismen.prismen.data.PrismaDataContract;
import net.prismen.prismen.data.Seite;
import net.prismen.prismen.data.db.PrismaDataDB;

import java.util.ArrayList;

/**
 * Created by iri on 18.03.16.
 */
public class PrismenseitePresenter implements PrismenseiteContract.Presenter{

    private PrismenseiteContract.View view;
    private PrismaDataContract prismenData;

    public PrismenseitePresenter(Context ctx,PrismenseiteContract.View view){
        this.view = view;
        prismenData = new PrismaDataDB(ctx);
    }


    @Override
    public void loadPrismenForLektion(String id_lektion) {
        try{
            ArrayList<Prisma> prismen = prismenData.getPrismenForLektion(id_lektion);
            view.showPrismen(prismen);
        }
        catch(Exception e){
            e.printStackTrace();
            view.showErrorGetPrismen();
        }
    }

    @Override
    public void deletePrisma(String id_prisma) {
        try{
            prismenData.deletePrisma(id_prisma);
        }
        catch(Exception e){
            e.printStackTrace();
            view.showErrorDelete();
        }
    }

    @Override
    public void updatePrisma(String id, String newName) {
        try{
            prismenData.renamePrisma(id,newName);
        }
        catch(Exception e){
            e.printStackTrace();
            view.showErrorUpdate();
        }

    }

    @Override
    public void insertPrisma(Prisma pr) {
        try{
            prismenData.insertPrisma(pr);
        }
        catch(Exception e){
            e.printStackTrace();
            view.showErrorInsert();
        }

    }

    @Override
    public ArrayList<Seite> loadSeitenForPrisma(String id_prisma) {
        ArrayList<Seite> seiten = null;
        try {
            seiten = prismenData.getSeitenForPrisma(id_prisma);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return seiten;
    }

    @Override
    public void deleteSeite(String id_seite) {
        try{
            prismenData.deleteSeite(id_seite);
        }
        catch(Exception e){
            e.printStackTrace();
            view.showErrorDelete();
        }
    }

    @Override
    public void updateSeite(String id, String frage, String antwort, int spTyp) {
        try{
            prismenData.updateSeite(id,frage,antwort,spTyp);
        }
        catch(Exception e){
            e.printStackTrace();
            view.showErrorUpdate();
        }
    }
}
