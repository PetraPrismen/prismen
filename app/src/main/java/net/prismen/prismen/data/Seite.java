package net.prismen.prismen.data;

import android.support.annotation.Nullable;

import java.util.UUID;

/**
 * Created by iri on 18.03.16.
 */
public class Seite {
    private String sId;
    @Nullable
    private String sFrage;
    private String sAntwort;
    private String sPrisma_id;
    private int sKnown;
    private int sTyp;




    public Seite(String antwort,@Nullable String frage, String prisma_id){
        sId = UUID.randomUUID().toString();
        sAntwort = antwort;
        sKnown = 0;
        sPrisma_id = prisma_id;
        sFrage = frage;
        if (frage == null){
            sTyp = 0;
        }
        else{
            sTyp = 1;
        }
    }

    public Seite(String id,String frage,String antwort,String prisma_id,int known,int typ){
        sId = id;
        sFrage = frage;
        sAntwort = antwort;
        sKnown = known;
        sTyp = typ;
        sPrisma_id = prisma_id;

    }
    public Seite(String frage,String antwort,int typ,String prisma_id){
        sId = UUID.randomUUID().toString();
        sFrage = frage;
        sAntwort = antwort;
        sTyp = typ;
        sKnown = 0;
        sPrisma_id = prisma_id;
    }

    public String getId(){
        return sId;
    }

    public String getFrage(){
        return sFrage;
    }

    public void setFrage(@Nullable String frage){
        sFrage = frage;
    }

    public String getAntwort(){
        return sAntwort;
    }

    public void setAntwort(String antwort){
        sAntwort = antwort;
    }

    public void setTyp(int typ){ sTyp = typ;}

    public int getTyp(){return sTyp;}

    public int getKnown(){return sKnown;}


}
