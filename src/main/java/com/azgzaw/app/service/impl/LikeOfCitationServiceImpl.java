package com.azgzaw.app.service.impl;

import com.azgzaw.app.service.LikeOfCitationService;
import com.azgzaw.app.domain.LikeOfCitation;
import com.azgzaw.app.repository.LikeOfCitationRepository;
import com.azgzaw.app.repository.search.LikeOfCitationSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link LikeOfCitation}.
 */
@Service
public class LikeOfCitationServiceImpl implements LikeOfCitationService {

    private final Logger log = LoggerFactory.getLogger(LikeOfCitationServiceImpl.class);

    private final LikeOfCitationRepository likeOfCitationRepository;

    private final LikeOfCitationSearchRepository likeOfCitationSearchRepository;

    public LikeOfCitationServiceImpl(LikeOfCitationRepository likeOfCitationRepository, LikeOfCitationSearchRepository likeOfCitationSearchRepository) {
        this.likeOfCitationRepository = likeOfCitationRepository;
        this.likeOfCitationSearchRepository = likeOfCitationSearchRepository;
    }

    /**
     * Save a likeOfCitation.
     *
     * @param likeOfCitation the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LikeOfCitation save(LikeOfCitation likeOfCitation) {
        log.debug("Request to save LikeOfCitation : {}", likeOfCitation);
        LikeOfCitation result = likeOfCitationRepository.save(likeOfCitation);
        likeOfCitationSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the likeOfCitations.
     *
     * @return the list of entities.
     */
    @Override
    public List<LikeOfCitation> findAll() {
        log.debug("Request to get all LikeOfCitations");
        return likeOfCitationRepository.findAll();
    }


    /**
     * Get one likeOfCitation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<LikeOfCitation> findOne(String id) {
        log.debug("Request to get LikeOfCitation : {}", id);
        return likeOfCitationRepository.findById(id);
    }

    /**
     * Delete the likeOfCitation by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete LikeOfCitation : {}", id);
        likeOfCitationRepository.deleteById(id);
        likeOfCitationSearchRepository.deleteById(id);
    }

    /**
     * Search for the likeOfCitation corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<LikeOfCitation> search(String query) {
        log.debug("Request to search LikeOfCitations for query {}", query);
        return StreamSupport
            .stream(likeOfCitationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
