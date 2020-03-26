package com.azgzaw.app.service.impl;

import com.azgzaw.app.service.LikeOfGiftService;
import com.azgzaw.app.domain.LikeOfGift;
import com.azgzaw.app.repository.LikeOfGiftRepository;
import com.azgzaw.app.repository.search.LikeOfGiftSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link LikeOfGift}.
 */
@Service
public class LikeOfGiftServiceImpl implements LikeOfGiftService {

    private final Logger log = LoggerFactory.getLogger(LikeOfGiftServiceImpl.class);

    private final LikeOfGiftRepository likeOfGiftRepository;

    private final LikeOfGiftSearchRepository likeOfGiftSearchRepository;

    public LikeOfGiftServiceImpl(LikeOfGiftRepository likeOfGiftRepository, LikeOfGiftSearchRepository likeOfGiftSearchRepository) {
        this.likeOfGiftRepository = likeOfGiftRepository;
        this.likeOfGiftSearchRepository = likeOfGiftSearchRepository;
    }

    /**
     * Save a likeOfGift.
     *
     * @param likeOfGift the entity to save.
     * @return the persisted entity.
     */
    @Override
    public LikeOfGift save(LikeOfGift likeOfGift) {
        log.debug("Request to save LikeOfGift : {}", likeOfGift);
        LikeOfGift result = likeOfGiftRepository.save(likeOfGift);
        likeOfGiftSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the likeOfGifts.
     *
     * @return the list of entities.
     */
    @Override
    public List<LikeOfGift> findAll() {
        log.debug("Request to get all LikeOfGifts");
        return likeOfGiftRepository.findAll();
    }


    /**
     * Get one likeOfGift by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<LikeOfGift> findOne(String id) {
        log.debug("Request to get LikeOfGift : {}", id);
        return likeOfGiftRepository.findById(id);
    }

    /**
     * Delete the likeOfGift by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete LikeOfGift : {}", id);
        likeOfGiftRepository.deleteById(id);
        likeOfGiftSearchRepository.deleteById(id);
    }

    /**
     * Search for the likeOfGift corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<LikeOfGift> search(String query) {
        log.debug("Request to search LikeOfGifts for query {}", query);
        return StreamSupport
            .stream(likeOfGiftSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
