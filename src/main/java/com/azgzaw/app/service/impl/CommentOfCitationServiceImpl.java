package com.azgzaw.app.service.impl;

import com.azgzaw.app.service.CommentOfCitationService;
import com.azgzaw.app.domain.CommentOfCitation;
import com.azgzaw.app.repository.CommentOfCitationRepository;
import com.azgzaw.app.repository.search.CommentOfCitationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CommentOfCitation}.
 */
@Service
public class CommentOfCitationServiceImpl implements CommentOfCitationService {

    private final Logger log = LoggerFactory.getLogger(CommentOfCitationServiceImpl.class);

    private final CommentOfCitationRepository commentOfCitationRepository;

    private final CommentOfCitationSearchRepository commentOfCitationSearchRepository;

    public CommentOfCitationServiceImpl(CommentOfCitationRepository commentOfCitationRepository, CommentOfCitationSearchRepository commentOfCitationSearchRepository) {
        this.commentOfCitationRepository = commentOfCitationRepository;
        this.commentOfCitationSearchRepository = commentOfCitationSearchRepository;
    }

    /**
     * Save a commentOfCitation.
     *
     * @param commentOfCitation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CommentOfCitation save(CommentOfCitation commentOfCitation) {
        log.debug("Request to save CommentOfCitation : {}", commentOfCitation);
        CommentOfCitation result = commentOfCitationRepository.save(commentOfCitation);
        commentOfCitationSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the commentOfCitations.
     *
     * @return the list of entities.
     */
    @Override
    public List<CommentOfCitation> findAll() {
        log.debug("Request to get all CommentOfCitations");
        return commentOfCitationRepository.findAll();
    }


    /**
     * Get one commentOfCitation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<CommentOfCitation> findOne(String id) {
        log.debug("Request to get CommentOfCitation : {}", id);
        return commentOfCitationRepository.findById(id);
    }

    /**
     * Delete the commentOfCitation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete CommentOfCitation : {}", id);
        commentOfCitationRepository.deleteById(id);
        commentOfCitationSearchRepository.deleteById(id);
    }

    /**
     * Search for the commentOfCitation corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<CommentOfCitation> search(String query) {
        log.debug("Request to search CommentOfCitations for query {}", query);
        return StreamSupport
            .stream(commentOfCitationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
