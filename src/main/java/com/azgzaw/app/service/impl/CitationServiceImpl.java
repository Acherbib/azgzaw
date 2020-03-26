package com.azgzaw.app.service.impl;

import com.azgzaw.app.repository.UserRepository;
import com.azgzaw.app.security.SecurityUtils;
import com.azgzaw.app.service.CitationService;
import com.azgzaw.app.domain.Citation;
import com.azgzaw.app.repository.CitationRepository;
import com.azgzaw.app.repository.search.CitationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.TimeZone;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Citation}.
 */
@Service
public class CitationServiceImpl implements CitationService {

    private final Logger log = LoggerFactory.getLogger(CitationServiceImpl.class);

    private final CitationRepository citationRepository;

    @Autowired
    private  UserRepository userRepository;

    private final CitationSearchRepository citationSearchRepository;

    public CitationServiceImpl(CitationRepository citationRepository, CitationSearchRepository citationSearchRepository) {
        this.citationRepository = citationRepository;
        this.citationSearchRepository = citationSearchRepository;
    }

    /**
     * Save a citation.
     *
     * @param citation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Citation save(Citation citation) {
        log.debug("Request to save Citation : {}", citation);
        citation.setAuthorOfCitation(userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().get()).get());
        Citation result = citationRepository.save(citation);
        citationSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the citations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Citation> findAll(Pageable pageable) {
        log.debug("Request to get all Citations");
        return citationRepository.findAll(pageable);
    }


    /**
     * Get one citation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Citation> findOne(String id) {
        log.debug("Request to get Citation : {}", id);
        return citationRepository.findById(id);
    }

    /**
     * Delete the citation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Citation : {}", id);
        citationRepository.deleteById(id);
        citationSearchRepository.deleteById(id);
    }

    /**
     * Search for the citation corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Citation> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Citations for query {}", query);
        return citationSearchRepository.search(queryStringQuery(query), pageable);    }
}
