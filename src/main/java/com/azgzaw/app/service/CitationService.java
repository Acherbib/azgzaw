package com.azgzaw.app.service;

import com.azgzaw.app.domain.Citation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Citation}.
 */
public interface CitationService {

    /**
     * Save a citation.
     *
     * @param citation the entity to save.
     * @return the persisted entity.
     */
    Citation save(Citation citation);

    /**
     * Get all the citations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Citation> findAll(Pageable pageable);


    /**
     * Get the "id" citation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Citation> findOne(String id);

    /**
     * Delete the "id" citation.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the citation corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Citation> search(String query, Pageable pageable);
}
