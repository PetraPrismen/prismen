package net.prismen.prismen.lernen;

import android.content.Context;

import net.prismen.prismen.data.Prisma;
import net.prismen.prismen.data.PrismaDataContract;
import net.prismen.prismen.data.Seite;
import net.prismen.prismen.data.db.PrismaDataDB;


import java.util.ArrayList;

/**
 * Created by iri on 21.03.16.
 */
public class LernenseitePresenter implements LernenseiteContract.Presenter{

    private LernenseiteContract.View view;
    private PrismaDataContract prismenData;


    public LernenseitePresenter(Context ctx,LernenseiteContract.View view){
        this.view = view;
        prismenData = new PrismaDataDB(ctx);
    }

    @Override
    public void getPrismenForLektion(String id_lektion) {
        try{
            ArrayList<Prisma> prismen = prismenData.getPrismenForLektion(id_lektion);
            view.loadPrismen(prismen);
        }
        catch(Exception e){
            e.printStackTrace();
            view.showErrorGetPrismen();
        }
    }

    @Override
    public void getSeitenForPrisma(String id_prisma) {
        try {
            ArrayList<Seite> seiten = prismenData.getSeitenForPrisma(id_prisma);
            view.loadSeiten(seiten);
        }
        catch(Exception e){
            e.printStackTrace();
            view.showErrorGetSeiten();
        }

    }
}
