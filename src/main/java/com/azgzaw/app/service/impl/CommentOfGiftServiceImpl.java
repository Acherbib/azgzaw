package com.azgzaw.app.service.impl;

import com.azgzaw.app.service.CommentOfGiftService;
import com.azgzaw.app.domain.CommentOfGift;
import com.azgzaw.app.repository.CommentOfGiftRepository;
import com.azgzaw.app.repository.search.CommentOfGiftSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CommentOfGift}.
 */
@Service
public class CommentOfGiftServiceImpl implements CommentOfGiftService {

    private final Logger log = LoggerFactory.getLogger(CommentOfGiftServiceImpl.class);

    private final CommentOfGiftRepository commentOfGiftRepository;

    private final CommentOfGiftSearchRepository commentOfGiftSearchRepository;

    public CommentOfGiftServiceImpl(CommentOfGiftRepository commentOfGiftRepository, CommentOfGiftSearchRepository commentOfGiftSearchRepository) {
        this.commentOfGiftRepository = commentOfGiftRepository;
        this.commentOfGiftSearchRepository = commentOfGiftSearchRepository;
    }

    /**
     * Save a commentOfGift.
     *
     * @param commentOfGift the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CommentOfGift save(CommentOfGift commentOfGift) {
        log.debug("Request to save CommentOfGift : {}", commentOfGift);
        CommentOfGift result = commentOfGiftRepository.save(commentOfGift);
        commentOfGiftSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the commentOfGifts.
     *
     * @return the list of entities.
     */
    @Override
    public List<CommentOfGift> findAll() {
        log.debug("Request to get all CommentOfGifts");
        return commentOfGiftRepository.findAll();
    }


    /**
     * Get one commentOfGift by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<CommentOfGift> findOne(String id) {
        log.debug("Request to get CommentOfGift : {}", id);
        return commentOfGiftRepository.findById(id);
    }

    /**
     * Delete the commentOfGift by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete CommentOfGift : {}", id);
        commentOfGiftRepository.deleteById(id);
        commentOfGiftSearchRepository.deleteById(id);
    }

    /**
     * Search for the commentOfGift corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<CommentOfGift> search(String query) {
        log.debug("Request to search CommentOfGifts for query {}", query);
        return StreamSupport
            .stream(commentOfGiftSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
