package net.prismen.prismen.themen;

import android.content.Context;
import android.util.Log;

import net.prismen.prismen.data.Thema;
import net.prismen.prismen.data.ThemaDataContract;
import net.prismen.prismen.data.db.ThemaDataDB;

import java.util.ArrayList;

/**
 * Created by iri on 26.02.16.
 */
public class ThemenseitePresenter implements ThemenseiteContract.Presenter{

    private ThemenseiteContract.View view;
    private ThemaDataContract themenData;



    public ThemenseitePresenter (Context ctx,ThemenseiteContract.View view){
        this.view = view;
        themenData = new ThemaDataDB(ctx);
    }

    @Override
    public void loadThemen(){
        try {
            ArrayList<Thema> themen = themenData.getAllThemen();
            view.showThemen(themen);
        }
        catch (Exception e){
            e.getStackTrace();
            view.showErrorGetThemen();
        }
    }

    @Override
    public void deleteThema(String id_thema_item){
        try {
            themenData.deleteThema(id_thema_item);
        } catch (Exception e) {
            e.printStackTrace();
            view.showErrorDeleteThema();
        }
    }

    @Override
    public void updateThema(String id, String newName){
        try {
            themenData.renameThema(id, newName);
        } catch (Exception e) {
            e.printStackTrace();
            view.showErrorUpdateThema();
        }

    }

    @Override
    public void insertThema(Thema th) {
        try {
            themenData.insertThema(th);
        } catch (Exception e) {
            e.printStackTrace();
            view.showErrorSaveThema();
        }
    }

}
