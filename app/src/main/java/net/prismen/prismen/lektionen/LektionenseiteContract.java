package net.prismen.prismen.lektionen;

import net.prismen.prismen.data.Lektion;
import net.prismen.prismen.data.Thema;

import java.util.ArrayList;

/**
 * Created by iri on 16.03.16.
 */
public interface LektionenseiteContract {

    interface View{

        void showLektionen(ArrayList<Lektion> lektionen);

        void showErrorGetLektionen();

        void showErrorUpdateLektion();

        void showErrorInsertLektion();

        void showErrorDeleteLektion();
    }

    interface Presenter {

        public void loadLektionenForThema(String thema_id);

        public void deleteLektion(String id_lektion);

        public void updateLektion(String id, String newName);

        public void insertLektion(Lektion l);
    }
}
