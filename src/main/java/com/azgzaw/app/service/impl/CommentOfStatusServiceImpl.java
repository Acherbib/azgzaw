package com.azgzaw.app.service.impl;

import com.azgzaw.app.service.CommentOfStatusService;
import com.azgzaw.app.domain.CommentOfStatus;
import com.azgzaw.app.repository.CommentOfStatusRepository;
import com.azgzaw.app.repository.search.CommentOfStatusSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CommentOfStatus}.
 */
@Service
public class CommentOfStatusServiceImpl implements CommentOfStatusService {

    private final Logger log = LoggerFactory.getLogger(CommentOfStatusServiceImpl.class);

    private final CommentOfStatusRepository commentOfStatusRepository;

    private final CommentOfStatusSearchRepository commentOfStatusSearchRepository;

    public CommentOfStatusServiceImpl(CommentOfStatusRepository commentOfStatusRepository, CommentOfStatusSearchRepository commentOfStatusSearchRepository) {
        this.commentOfStatusRepository = commentOfStatusRepository;
        this.commentOfStatusSearchRepository = commentOfStatusSearchRepository;
    }

    /**
     * Save a commentOfStatus.
     *
     * @param commentOfStatus the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CommentOfStatus save(CommentOfStatus commentOfStatus) {
        log.debug("Request to save CommentOfStatus : {}", commentOfStatus);
        CommentOfStatus result = commentOfStatusRepository.save(commentOfStatus);
        commentOfStatusSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the commentOfStatuses.
     *
     * @return the list of entities.
     */
    @Override
    public List<CommentOfStatus> findAll() {
        log.debug("Request to get all CommentOfStatuses");
        return commentOfStatusRepository.findAll();
    }


    /**
     * Get one commentOfStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<CommentOfStatus> findOne(String id) {
        log.debug("Request to get CommentOfStatus : {}", id);
        return commentOfStatusRepository.findById(id);
    }

    /**
     * Delete the commentOfStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete CommentOfStatus : {}", id);
        commentOfStatusRepository.deleteById(id);
        commentOfStatusSearchRepository.deleteById(id);
    }

    /**
     * Search for the commentOfStatus corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<CommentOfStatus> search(String query) {
        log.debug("Request to search CommentOfStatuses for query {}", query);
        return StreamSupport
            .stream(commentOfStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
