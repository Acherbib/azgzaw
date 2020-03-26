package com.azgzaw.app.service;

import com.azgzaw.app.domain.Spam;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Spam}.
 */
public interface SpamService {

    /**
     * Save a spam.
     *
     * @param spam the entity to save.
     * @return the persisted entity.
     */
    Spam save(Spam spam);

    /**
     * Get all the spams.
     *
     * @return the list of entities.
     */
    List<Spam> findAll();


    /**
     * Get the "id" spam.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Spam> findOne(String id);

    /**
     * Delete the "id" spam.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the spam corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Spam> search(String query);
}
