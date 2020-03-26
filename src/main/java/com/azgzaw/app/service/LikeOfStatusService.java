package com.azgzaw.app.service;

import com.azgzaw.app.domain.LikeOfStatus;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LikeOfStatus}.
 */
public interface LikeOfStatusService {

    /**
     * Save a likeOfStatus.
     *
     * @param likeOfStatus the entity to save.
     * @return the persisted entity.
     */
    LikeOfStatus save(LikeOfStatus likeOfStatus);

    /**
     * Get all the likeOfStatuses.
     *
     * @return the list of entities.
     */
    List<LikeOfStatus> findAll();


    /**
     * Get the "id" likeOfStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LikeOfStatus> findOne(String id);

    /**
     * Delete the "id" likeOfStatus.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the likeOfStatus corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<LikeOfStatus> search(String query);
}
