package net.prismen.prismen.data;

import java.util.ArrayList;

/**
 * Created by iri on 16.03.16.
 */
public interface LektionDataContract {
    /**
     * Get Lektionen for Thema
     * @return List of all Lektionen of a Thema
     * @param thema_id thema id
     * @throws Exception if anything goes wrong with the persistence layer
     */
    public ArrayList<Lektion> getLektionenForThema(String thema_id) throws Exception;

    /**
     * Deletes Lektion with given id
     * @param id unique identifier of the Lektion to delete
     * @throws Exception if anything goes wrong with the persistence layer
     */
    public void deleteLektion(String id) throws Exception;

    /**
     * Change the name of the Lektion with the given id
     * @param id unique identifier of the Lektion to change
     * @param newName the new name for Lektion
     * @throws Exception if anything goes wrong with the persistence layer
     */
    public void renameLektion(String id, String newName) throws Exception;

    /**
     * Save given Lektion
     * @param lk Lektion to save
     * @throws Exception if anything goes wrong with the persistence layer
     */
    public void insertLektion(Lektion lk) throws Exception;
}
