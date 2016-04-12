package net.prismen.prismen.data;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by iri on 02.03.16.
 */
public class Prisma {
    private String pId;
    private String pName;
    private int pNb_seiten;
    private String pLektion_id;

    public Prisma (String name, String lektion_id){
        pId = UUID.randomUUID().toString();
        pName = name;
        pNb_seiten = 0;
        pLektion_id = lektion_id;
    }

    public Prisma(String id,String name,int nb_seiten,String lektion_id){
        pId = id;
        pName = name;
        pNb_seiten = nb_seiten;
        pLektion_id = lektion_id;
    }

    public String getId(){
        return pId;
    }

    public String getName(){
        return pName;
    }

    public void setName(String newName){pName = newName;}

    public int getNb_seiten(){
        return pNb_seiten;
    }

    public String getLektion_id(){
        return pLektion_id;
    }
}
