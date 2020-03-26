package com.azgzaw.app.service.impl;

import com.azgzaw.app.service.FollowsService;
import com.azgzaw.app.domain.Follows;
import com.azgzaw.app.repository.FollowsRepository;
import com.azgzaw.app.repository.search.FollowsSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Follows}.
 */
@Service
public class FollowsServiceImpl implements FollowsService {

    private final Logger log = LoggerFactory.getLogger(FollowsServiceImpl.class);

    private final FollowsRepository followsRepository;

    private final FollowsSearchRepository followsSearchRepository;

    public FollowsServiceImpl(FollowsRepository followsRepository, FollowsSearchRepository followsSearchRepository) {
        this.followsRepository = followsRepository;
        this.followsSearchRepository = followsSearchRepository;
    }

    /**
     * Save a follows.
     *
     * @param follows the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Follows save(Follows follows) {
        log.debug("Request to save Follows : {}", follows);
        Follows result = followsRepository.save(follows);
        followsSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the follows.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Follows> findAll(Pageable pageable) {
        log.debug("Request to get all Follows");
        return followsRepository.findAll(pageable);
    }


    /**
     * Get one follows by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Follows> findOne(String id) {
        log.debug("Request to get Follows : {}", id);
        return followsRepository.findById(id);
    }

    /**
     * Delete the follows by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Follows : {}", id);
        followsRepository.deleteById(id);
        followsSearchRepository.deleteById(id);
    }

    /**
     * Search for the follows corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Follows> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Follows for query {}", query);
        return followsSearchRepository.search(queryStringQuery(query), pageable);    }
}
