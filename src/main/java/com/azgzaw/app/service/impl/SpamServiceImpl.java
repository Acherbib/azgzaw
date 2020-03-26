package com.azgzaw.app.service.impl;

import com.azgzaw.app.service.SpamService;
import com.azgzaw.app.domain.Spam;
import com.azgzaw.app.repository.SpamRepository;
import com.azgzaw.app.repository.search.SpamSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Spam}.
 */
@Service
public class SpamServiceImpl implements SpamService {

    private final Logger log = LoggerFactory.getLogger(SpamServiceImpl.class);

    private final SpamRepository spamRepository;

    private final SpamSearchRepository spamSearchRepository;

    public SpamServiceImpl(SpamRepository spamRepository, SpamSearchRepository spamSearchRepository) {
        this.spamRepository = spamRepository;
        this.spamSearchRepository = spamSearchRepository;
    }

    /**
     * Save a spam.
     *
     * @param spam the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Spam save(Spam spam) {
        log.debug("Request to save Spam : {}", spam);
        Spam result = spamRepository.save(spam);
        spamSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the spams.
     *
     * @return the list of entities.
     */
    @Override
    public List<Spam> findAll() {
        log.debug("Request to get all Spams");
        return spamRepository.findAll();
    }


    /**
     * Get one spam by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Spam> findOne(String id) {
        log.debug("Request to get Spam : {}", id);
        return spamRepository.findById(id);
    }

    /**
     * Delete the spam by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Spam : {}", id);
        spamRepository.deleteById(id);
        spamSearchRepository.deleteById(id);
    }

    /**
     * Search for the spam corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<Spam> search(String query) {
        log.debug("Request to search Spams for query {}", query);
        return StreamSupport
            .stream(spamSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
