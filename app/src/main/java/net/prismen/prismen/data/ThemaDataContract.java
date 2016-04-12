package net.prismen.prismen.data;

import java.util.ArrayList;

/**
 * Created by iri on 07.03.16.
 * Persistence methods for a Thema
 */
public interface ThemaDataContract {
    /**
     * Get all Themen
     * @return List of all Themen
     * @throws Exception if anything goes wrong with the persistence layer
     */
    public ArrayList<Thema> getAllThemen() throws Exception;

    /**
     *
     * @param id
     * @throws Exception if anything goes wrong with the persistence layer
     */
    public void deleteThema(String id) throws Exception;

    /**
     * Change the name of the Thema with the given id
     * @param id Thema unique identifier for the Thema to change
     * @param newName the new name for the Thema
     * @throws Exception if anything goes wrong with the persistence layer
     */
    public void renameThema(String id, String newName) throws Exception;

    /**
     * Save given Thema
     * @param th Thema to save
     * @throws Exception if anything goes wrong with the persistence layer
     */
    public void insertThema(Thema th) throws Exception;
}
