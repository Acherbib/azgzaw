package com.azgzaw.app.service.impl;

import com.azgzaw.app.service.GiftService;
import com.azgzaw.app.domain.Gift;
import com.azgzaw.app.repository.GiftRepository;
import com.azgzaw.app.repository.search.GiftSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Gift}.
 */
@Service
public class GiftServiceImpl implements GiftService {

    private final Logger log = LoggerFactory.getLogger(GiftServiceImpl.class);

    private final GiftRepository giftRepository;

    private final GiftSearchRepository giftSearchRepository;

    public GiftServiceImpl(GiftRepository giftRepository, GiftSearchRepository giftSearchRepository) {
        this.giftRepository = giftRepository;
        this.giftSearchRepository = giftSearchRepository;
    }

    /**
     * Save a gift.
     *
     * @param gift the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Gift save(Gift gift) {
        log.debug("Request to save Gift : {}", gift);
        Gift result = giftRepository.save(gift);
        giftSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the gifts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Gift> findAll(Pageable pageable) {
        log.debug("Request to get all Gifts");
        return giftRepository.findAll(pageable);
    }


    /**
     * Get one gift by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Gift> findOne(String id) {
        log.debug("Request to get Gift : {}", id);
        return giftRepository.findById(id);
    }

    /**
     * Delete the gift by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Gift : {}", id);
        giftRepository.deleteById(id);
        giftSearchRepository.deleteById(id);
    }

    /**
     * Search for the gift corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Gift> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Gifts for query {}", query);
        return giftSearchRepository.search(queryStringQuery(query), pageable);    }
}
