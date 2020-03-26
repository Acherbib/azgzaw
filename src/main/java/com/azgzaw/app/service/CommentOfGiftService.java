package com.azgzaw.app.service;

import com.azgzaw.app.domain.CommentOfGift;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CommentOfGift}.
 */
public interface CommentOfGiftService {

    /**
     * Save a commentOfGift.
     *
     * @param commentOfGift the entity to save.
     * @return the persisted entity.
     */
    CommentOfGift save(CommentOfGift commentOfGift);

    /**
     * Get all the commentOfGifts.
     *
     * @return the list of entities.
     */
    List<CommentOfGift> findAll();


    /**
     * Get the "id" commentOfGift.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommentOfGift> findOne(String id);

    /**
     * Delete the "id" commentOfGift.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the commentOfGift corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<CommentOfGift> search(String query);
}
