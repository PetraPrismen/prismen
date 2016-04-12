package net.prismen.prismen.prismen.seite_hinzufuegen;

import net.prismen.prismen.data.Seite;

/**
 * Created by iri on 19.03.16.
 */
public interface SeiteHinzufuegenContract {

    interface View{

        void showErrorSaveSeite();

        void showSeiteInsertedOK();
    }

    interface Presenter{

        void saveSeiteForPrisma(String id_prisma,Seite st);

    }
}
