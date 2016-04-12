package net.prismen.prismen.lernen_vorbereiten;

import net.prismen.prismen.data.Lektion;
import net.prismen.prismen.data.Thema;

import java.util.ArrayList;

/**
 * Created by iri on 20.03.16.
 */
public interface LernenVorbereitenContract {

    interface View{

        void showThemen(ArrayList<Thema> themen);

        void showErrorGetThemen();

        void showErrorGetLektionen();

        void showLektionen(ArrayList<Lektion> lektionen);
    }

    interface Presenter{

        void getAllThemen();

        public void getLektionenForThema(String thema_id);
    }
}
