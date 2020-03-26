package com.azgzaw.app.service;

import com.azgzaw.app.domain.Follows;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Follows}.
 */
public interface FollowsService {

    /**
     * Save a follows.
     *
     * @param follows the entity to save.
     * @return the persisted entity.
     */
    Follows save(Follows follows);

    /**
     * Get all the follows.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Follows> findAll(Pageable pageable);


    /**
     * Get the "id" follows.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Follows> findOne(String id);

    /**
     * Delete the "id" follows.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the follows corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Follows> search(String query, Pageable pageable);
}
