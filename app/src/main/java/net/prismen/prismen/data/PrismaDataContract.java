package net.prismen.prismen.data;

import android.support.annotation.Nullable;

import java.util.ArrayList;

/**
 * Created by iri on 18.03.16.
 */
public interface PrismaDataContract {
    /**
     * Get Prismen for Lektion
     * @return List of all Prismen of a Lektion
     * @param lektion_id unique identifier of Lektion for this Prisma
     * @throws Exception if anything goes wrong with the persistence layer
     */
    public ArrayList<Prisma> getPrismenForLektion(String lektion_id) throws Exception;

    /**
     * Deletes Prisma with given id
     * @param id unique identifier of the Prisma to delete
     * @throws Exception if anything goes wrong with the persistence layer
     */
    public void deletePrisma(String id) throws Exception;

    /**
     * Change the name of the Prisma with the given id
     * @param id unique identifier of the Prisma to change
     * @param newName the new name for Prisma
     * @throws Exception if anything goes wrong with the persistence layer
     */
    public void renamePrisma(String id, String newName) throws Exception;

    /**
     * Save given Prisma
     * @param pr Prisma to save
     * @throws Exception if anything goes wrong with the persistence layer
     */
    public void insertPrisma(Prisma pr) throws Exception;

    /**
     * Get Seiten for Prisma
     * @return List of all Prismen of a Lektion
     * @param prisma_id unique identifier of Lektion for this Prisma
     * @throws Exception if anything goes wrong with the persistence layer
     */
    public ArrayList<Seite> getSeitenForPrisma(String prisma_id) throws Exception;


    /**
     * Delete Seite with given id
     * @param seite_id unique identifier for the Seite to delte
     * @throws Exception if anything goes wrong with persitence layer
     */
    public void deleteSeite(String seite_id) throws Exception;


    /**
     * Update Seite with given id
     * @param id unique identifier of Seite to update
     * @param frage
     * @param antwort
     * @param typ
     * @throws Exception if anything goes wrong with persistence layer
     */
    public void updateSeite(String id,String frage,String antwort,int typ) throws Exception;


    /**
     * Insert a Seite for Prisma
     * @param id_prisma unique identifier for the prisma
     * @param st the Seite to insert
     * @throws Exception if anything goes wrong with persistence layer
     */
    public void insertSeite(String id_prisma,Seite st) throws Exception;

}
