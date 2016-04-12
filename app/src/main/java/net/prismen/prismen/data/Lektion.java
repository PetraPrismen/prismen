package net.prismen.prismen.data;

import java.util.UUID;

/**
 * Created by iri on 02.03.16.
 */
public class Lektion {
    private final String lId;
    private  String lName;
    private int  lNb_prismen;
    private String lThema_id;

    public Lektion(String name, String thema_id){
        lName = name;
        lNb_prismen = 0;
        lThema_id = thema_id;
        lId = UUID.randomUUID().toString();
    }

    public Lektion(String id, String name, int nb_prismen, String thema_id){
        lName = name;
        lId = id;
        lNb_prismen = nb_prismen;
        lThema_id = thema_id;
    }

    public String getId(){
        return lId;
    }

    public String getName(){
        return lName;
    }

    public int getNb_prismen(){
        return lNb_prismen;
    }

    public String getThema_id(){
        return lThema_id;
    }

    public void setName(String name){
        lName = name;
    }



}
