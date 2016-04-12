package net.prismen.prismen.themen;

import net.prismen.prismen.data.Thema;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iri on 26.02.16.
 */
public interface ThemenseiteContract {

    interface View{

        void showThemen(ArrayList<Thema> themen);

        void showErrorGetThemen();

        void showErrorDeleteThema();

        void showErrorUpdateThema();

        void showErrorSaveThema();
    }

    interface Presenter {

        public void loadThemen() throws Exception;

        public void deleteThema(String id_thema_item);

        public void updateThema(String id, String newName);

        public void insertThema(Thema th);
    }
}
