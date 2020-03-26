package com.azgzaw.app.service;

import com.azgzaw.app.domain.LikeOfCitation;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link LikeOfCitation}.
 */
public interface LikeOfCitationService {

    /**
     * Save a likeOfCitation.
     *
     * @param likeOfCitation the entity to save.
     * @return the persisted entity.
     */
    LikeOfCitation save(LikeOfCitation likeOfCitation);

    /**
     * Get all the likeOfCitations.
     *
     * @return the list of entities.
     */
    List<LikeOfCitation> findAll();


    /**
     * Get the "id" likeOfCitation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LikeOfCitation> findOne(String id);

    /**
     * Delete the "id" likeOfCitation.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the likeOfCitation corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<LikeOfCitation> search(String query);
}
