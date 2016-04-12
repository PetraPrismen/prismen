package net.prismen.prismen.lernen;

import net.prismen.prismen.data.Prisma;
import net.prismen.prismen.data.Seite;

import java.util.ArrayList;

/**
 * Created by iri on 21.03.16.
 */
public interface LernenseiteContract {

    interface View{

            void loadPrismen(ArrayList<Prisma> prismen);

            void loadSeiten(ArrayList<Seite> seiten);

            void showErrorGetPrismen();

            void showErrorGetSeiten();

    }

    interface Presenter{

        void getPrismenForLektion(String id_lektion);

        void getSeitenForPrisma(String id_prisma);
    }
}
