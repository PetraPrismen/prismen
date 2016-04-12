package net.prismen.prismen.data;

import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * Created by iri on 29.02.16.
 */
public final class Thema {

    @Nullable
    private final String tId;
    private  String tName;
    private  int tNb_lektionen;

    public Thema(String name){
        tName = name;
        tNb_lektionen = 0;
        tId = UUID.randomUUID().toString();
    }

    public Thema(String id, String name, int nb_lektionen){
        tName = name;
        tId= id;
        tNb_lektionen = nb_lektionen;
    }

    public String getId(){
        return tId;
    }

    public String getName(){
        return tName;
    }

    public int getNb_lektionen(){
        return tNb_lektionen;
    }

    public void setName(String newName){
        this.tName = newName;
    }
}
