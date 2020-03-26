package com.azgzaw.app.service;

import com.azgzaw.app.domain.CommentOfStatus;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CommentOfStatus}.
 */
public interface CommentOfStatusService {

    /**
     * Save a commentOfStatus.
     *
     * @param commentOfStatus the entity to save.
     * @return the persisted entity.
     */
    CommentOfStatus save(CommentOfStatus commentOfStatus);

    /**
     * Get all the commentOfStatuses.
     *
     * @return the list of entities.
     */
    List<CommentOfStatus> findAll();


    /**
     * Get the "id" commentOfStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommentOfStatus> findOne(String id);

    /**
     * Delete the "id" commentOfStatus.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the commentOfStatus corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<CommentOfStatus> search(String query);
}
