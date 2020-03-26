package com.azgzaw.app.web.rest;

import com.azgzaw.app.domain.CommentOfGift;
import com.azgzaw.app.service.CommentOfGiftService;
import com.azgzaw.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.azgzaw.app.domain.CommentOfGift}.
 */
@RestController
@RequestMapping("/api")
public class CommentOfGiftResource {

    private final Logger log = LoggerFactory.getLogger(CommentOfGiftResource.class);

    private static final String ENTITY_NAME = "commentOfGift";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommentOfGiftService commentOfGiftService;

    public CommentOfGiftResource(CommentOfGiftService commentOfGiftService) {
        this.commentOfGiftService = commentOfGiftService;
    }

    /**
     * {@code POST  /comment-of-gifts} : Create a new commentOfGift.
     *
     * @param commentOfGift the commentOfGift to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commentOfGift, or with status {@code 400 (Bad Request)} if the commentOfGift has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comment-of-gifts")
    public ResponseEntity<CommentOfGift> createCommentOfGift(@Valid @RequestBody CommentOfGift commentOfGift) throws URISyntaxException {
        log.debug("REST request to save CommentOfGift : {}", commentOfGift);
        if (commentOfGift.getId() != null) {
            throw new BadRequestAlertException("A new commentOfGift cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommentOfGift result = commentOfGiftService.save(commentOfGift);
        return ResponseEntity.created(new URI("/api/comment-of-gifts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comment-of-gifts} : Updates an existing commentOfGift.
     *
     * @param commentOfGift the commentOfGift to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentOfGift,
     * or with status {@code 400 (Bad Request)} if the commentOfGift is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commentOfGift couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comment-of-gifts")
    public ResponseEntity<CommentOfGift> updateCommentOfGift(@Valid @RequestBody CommentOfGift commentOfGift) throws URISyntaxException {
        log.debug("REST request to update CommentOfGift : {}", commentOfGift);
        if (commentOfGift.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommentOfGift result = commentOfGiftService.save(commentOfGift);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commentOfGift.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comment-of-gifts} : get all the commentOfGifts.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commentOfGifts in body.
     */
    @GetMapping("/comment-of-gifts")
    public List<CommentOfGift> getAllCommentOfGifts() {
        log.debug("REST request to get all CommentOfGifts");
        return commentOfGiftService.findAll();
    }

    /**
     * {@code GET  /comment-of-gifts/:id} : get the "id" commentOfGift.
     *
     * @param id the id of the commentOfGift to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commentOfGift, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comment-of-gifts/{id}")
    public ResponseEntity<CommentOfGift> getCommentOfGift(@PathVariable String id) {
        log.debug("REST request to get CommentOfGift : {}", id);
        Optional<CommentOfGift> commentOfGift = commentOfGiftService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commentOfGift);
    }

    /**
     * {@code DELETE  /comment-of-gifts/:id} : delete the "id" commentOfGift.
     *
     * @param id the id of the commentOfGift to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comment-of-gifts/{id}")
    public ResponseEntity<Void> deleteCommentOfGift(@PathVariable String id) {
        log.debug("REST request to delete CommentOfGift : {}", id);
        commentOfGiftService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/comment-of-gifts?query=:query} : search for the commentOfGift corresponding
     * to the query.
     *
     * @param query the query of the commentOfGift search.
     * @return the result of the search.
     */
    @GetMapping("/_search/comment-of-gifts")
    public List<CommentOfGift> searchCommentOfGifts(@RequestParam String query) {
        log.debug("REST request to search CommentOfGifts for query {}", query);
        return commentOfGiftService.search(query);
    }
}
