package com.azgzaw.app.service;

import com.azgzaw.app.domain.LikeOfGift;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LikeOfGift}.
 */
public interface LikeOfGiftService {

    /**
     * Save a likeOfGift.
     *
     * @param likeOfGift the entity to save.
     * @return the persisted entity.
     */
    LikeOfGift save(LikeOfGift likeOfGift);

    /**
     * Get all the likeOfGifts.
     *
     * @return the list of entities.
     */
    List<LikeOfGift> findAll();


    /**
     * Get the "id" likeOfGift.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LikeOfGift> findOne(String id);

    /**
     * Delete the "id" likeOfGift.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the likeOfGift corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<LikeOfGift> search(String query);
}
