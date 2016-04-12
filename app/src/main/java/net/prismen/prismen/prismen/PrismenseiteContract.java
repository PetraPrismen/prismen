package net.prismen.prismen.prismen;

import net.prismen.prismen.data.Prisma;
import net.prismen.prismen.data.Seite;

import java.util.ArrayList;

/**
 * Created by iri on 18.03.16.
 */
public interface PrismenseiteContract {

    interface View{

        void showPrismen(ArrayList<Prisma> prismen);

        void showErrorGetPrismen();

        void showErrorUpdate();

        void showErrorInsert();

        void showErrorDelete();

    }


    interface Presenter{

        void loadPrismenForLektion(String id_lektion);

        void deletePrisma(String id_prisma);

        void updatePrisma(String id, String newName);

        void insertPrisma(Prisma pr);

        ArrayList<Seite> loadSeitenForPrisma(String id_prisma);

        void deleteSeite(String id_seite);

        void updateSeite(String id,String frage,String antwort,int spTyp);

    }
}
