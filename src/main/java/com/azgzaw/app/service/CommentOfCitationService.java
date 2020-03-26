package com.azgzaw.app.service;

import com.azgzaw.app.domain.CommentOfCitation;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link CommentOfCitation}.
 */
public interface CommentOfCitationService {

    /**
     * Save a commentOfCitation.
     *
     * @param commentOfCitation the entity to save.
     * @return the persisted entity.
     */
    CommentOfCitation save(CommentOfCitation commentOfCitation);

    /**
     * Get all the commentOfCitations.
     *
     * @return the list of entities.
     */
    List<CommentOfCitation> findAll();


    /**
     * Get the "id" commentOfCitation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CommentOfCitation> findOne(String id);

    /**
     * Delete the "id" commentOfCitation.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the commentOfCitation corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<CommentOfCitation> search(String query);
}
