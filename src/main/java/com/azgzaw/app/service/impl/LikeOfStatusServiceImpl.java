package com.azgzaw.app.service.impl;

import com.azgzaw.app.service.LikeOfStatusService;
import com.azgzaw.app.domain.LikeOfStatus;
import com.azgzaw.app.repository.LikeOfStatusRepository;
import com.azgzaw.app.repository.search.LikeOfStatusSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link LikeOfStatus}.
 */
@Service
public class LikeOfStatusServiceImpl implements LikeOfStatusService {

    private final Logger log = LoggerFactory.getLogger(LikeOfStatusServiceImpl.class);

    private final LikeOfStatusRepository likeOfStatusRepository;

    private final LikeOfStatusSearchRepository likeOfStatusSearchRepository;

    public LikeOfStatusServiceImpl(LikeOfStatusRepository likeOfStatusRepository, LikeOfStatusSearchRepository likeOfStatusSearchRepository) {
        this.likeOfStatusRepository = likeOfStatusRepository;
        this.likeOfStatusSearchRepository = likeOfStatusSearchRepository;
    }

    /**
     * Save a likeOfStatus.
     *
     * @param likeOfStatus the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LikeOfStatus save(LikeOfStatus likeOfStatus) {
        log.debug("Request to save LikeOfStatus : {}", likeOfStatus);
        LikeOfStatus result = likeOfStatusRepository.save(likeOfStatus);
        likeOfStatusSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the likeOfStatuses.
     *
     * @return the list of entities.
     */
    @Override
    public List<LikeOfStatus> findAll() {
        log.debug("Request to get all LikeOfStatuses");
        return likeOfStatusRepository.findAll();
    }


    /**
     * Get one likeOfStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<LikeOfStatus> findOne(String id) {
        log.debug("Request to get LikeOfStatus : {}", id);
        return likeOfStatusRepository.findById(id);
    }

    /**
     * Delete the likeOfStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete LikeOfStatus : {}", id);
        likeOfStatusRepository.deleteById(id);
        likeOfStatusSearchRepository.deleteById(id);
    }

    /**
     * Search for the likeOfStatus corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<LikeOfStatus> search(String query) {
        log.debug("Request to search LikeOfStatuses for query {}", query);
        return StreamSupport
            .stream(likeOfStatusSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
