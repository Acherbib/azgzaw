package com.azgzaw.app.web.rest;

import com.azgzaw.app.domain.CommentOfStatus;
import com.azgzaw.app.service.CommentOfStatusService;
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
 * REST controller for managing {@link com.azgzaw.app.domain.CommentOfStatus}.
 */
@RestController
@RequestMapping("/api")
public class CommentOfStatusResource {

    private final Logger log = LoggerFactory.getLogger(CommentOfStatusResource.class);

    private static final String ENTITY_NAME = "commentOfStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommentOfStatusService commentOfStatusService;

    public CommentOfStatusResource(CommentOfStatusService commentOfStatusService) {
        this.commentOfStatusService = commentOfStatusService;
    }

    /**
     * {@code POST  /comment-of-statuses} : Create a new commentOfStatus.
     *
     * @param commentOfStatus the commentOfStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commentOfStatus, or with status {@code 400 (Bad Request)} if the commentOfStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comment-of-statuses")
    public ResponseEntity<CommentOfStatus> createCommentOfStatus(@Valid @RequestBody CommentOfStatus commentOfStatus) throws URISyntaxException {
        log.debug("REST request to save CommentOfStatus : {}", commentOfStatus);
        if (commentOfStatus.getId() != null) {
            throw new BadRequestAlertException("A new commentOfStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommentOfStatus result = commentOfStatusService.save(commentOfStatus);
        return ResponseEntity.created(new URI("/api/comment-of-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comment-of-statuses} : Updates an existing commentOfStatus.
     *
     * @param commentOfStatus the commentOfStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentOfStatus,
     * or with status {@code 400 (Bad Request)} if the commentOfStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commentOfStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comment-of-statuses")
    public ResponseEntity<CommentOfStatus> updateCommentOfStatus(@Valid @RequestBody CommentOfStatus commentOfStatus) throws URISyntaxException {
        log.debug("REST request to update CommentOfStatus : {}", commentOfStatus);
        if (commentOfStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommentOfStatus result = commentOfStatusService.save(commentOfStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commentOfStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comment-of-statuses} : get all the commentOfStatuses.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commentOfStatuses in body.
     */
    @GetMapping("/comment-of-statuses")
    public List<CommentOfStatus> getAllCommentOfStatuses() {
        log.debug("REST request to get all CommentOfStatuses");
        return commentOfStatusService.findAll();
    }

    /**
     * {@code GET  /comment-of-statuses/:id} : get the "id" commentOfStatus.
     *
     * @param id the id of the commentOfStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commentOfStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comment-of-statuses/{id}")
    public ResponseEntity<CommentOfStatus> getCommentOfStatus(@PathVariable String id) {
        log.debug("REST request to get CommentOfStatus : {}", id);
        Optional<CommentOfStatus> commentOfStatus = commentOfStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commentOfStatus);
    }

    /**
     * {@code DELETE  /comment-of-statuses/:id} : delete the "id" commentOfStatus.
     *
     * @param id the id of the commentOfStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comment-of-statuses/{id}")
    public ResponseEntity<Void> deleteCommentOfStatus(@PathVariable String id) {
        log.debug("REST request to delete CommentOfStatus : {}", id);
        commentOfStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/comment-of-statuses?query=:query} : search for the commentOfStatus corresponding
     * to the query.
     *
     * @param query the query of the commentOfStatus search.
     * @return the result of the search.
     */
    @GetMapping("/_search/comment-of-statuses")
    public List<CommentOfStatus> searchCommentOfStatuses(@RequestParam String query) {
        log.debug("REST request to search CommentOfStatuses for query {}", query);
        return commentOfStatusService.search(query);
    }
}
