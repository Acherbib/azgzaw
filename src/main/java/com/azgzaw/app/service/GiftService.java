package com.azgzaw.app.service;

import com.azgzaw.app.domain.Gift;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Gift}.
 */
public interface GiftService {

    /**
     * Save a gift.
     *
     * @param gift the entity to save.
     * @return the persisted entity.
     */
    Gift save(Gift gift);

    /**
     * Get all the gifts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Gift> findAll(Pageable pageable);


    /**
     * Get the "id" gift.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Gift> findOne(String id);

    /**
     * Delete the "id" gift.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the gift corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Gift> search(String query, Pageable pageable);
}
