package com.azgzaw.app.web.rest;

import com.azgzaw.app.domain.CommentOfCitation;
import com.azgzaw.app.service.CommentOfCitationService;
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
 * REST controller for managing {@link com.azgzaw.app.domain.CommentOfCitation}.
 */
@RestController
@RequestMapping("/api")
public class CommentOfCitationResource {

    private final Logger log = LoggerFactory.getLogger(CommentOfCitationResource.class);

    private static final String ENTITY_NAME = "commentOfCitation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CommentOfCitationService commentOfCitationService;

    public CommentOfCitationResource(CommentOfCitationService commentOfCitationService) {
        this.commentOfCitationService = commentOfCitationService;
    }

    /**
     * {@code POST  /comment-of-citations} : Create a new commentOfCitation.
     *
     * @param commentOfCitation the commentOfCitation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new commentOfCitation, or with status {@code 400 (Bad Request)} if the commentOfCitation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/comment-of-citations")
    public ResponseEntity<CommentOfCitation> createCommentOfCitation(@Valid @RequestBody CommentOfCitation commentOfCitation) throws URISyntaxException {
        log.debug("REST request to save CommentOfCitation : {}", commentOfCitation);
        if (commentOfCitation.getId() != null) {
            throw new BadRequestAlertException("A new commentOfCitation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommentOfCitation result = commentOfCitationService.save(commentOfCitation);
        return ResponseEntity.created(new URI("/api/comment-of-citations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /comment-of-citations} : Updates an existing commentOfCitation.
     *
     * @param commentOfCitation the commentOfCitation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated commentOfCitation,
     * or with status {@code 400 (Bad Request)} if the commentOfCitation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the commentOfCitation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/comment-of-citations")
    public ResponseEntity<CommentOfCitation> updateCommentOfCitation(@Valid @RequestBody CommentOfCitation commentOfCitation) throws URISyntaxException {
        log.debug("REST request to update CommentOfCitation : {}", commentOfCitation);
        if (commentOfCitation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CommentOfCitation result = commentOfCitationService.save(commentOfCitation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, commentOfCitation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /comment-of-citations} : get all the commentOfCitations.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of commentOfCitations in body.
     */
    @GetMapping("/comment-of-citations")
    public List<CommentOfCitation> getAllCommentOfCitations() {
        log.debug("REST request to get all CommentOfCitations");
        return commentOfCitationService.findAll();
    }

    /**
     * {@code GET  /comment-of-citations/:id} : get the "id" commentOfCitation.
     *
     * @param id the id of the commentOfCitation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the commentOfCitation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/comment-of-citations/{id}")
    public ResponseEntity<CommentOfCitation> getCommentOfCitation(@PathVariable String id) {
        log.debug("REST request to get CommentOfCitation : {}", id);
        Optional<CommentOfCitation> commentOfCitation = commentOfCitationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(commentOfCitation);
    }

    /**
     * {@code DELETE  /comment-of-citations/:id} : delete the "id" commentOfCitation.
     *
     * @param id the id of the commentOfCitation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/comment-of-citations/{id}")
    public ResponseEntity<Void> deleteCommentOfCitation(@PathVariable String id) {
        log.debug("REST request to delete CommentOfCitation : {}", id);
        commentOfCitationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/comment-of-citations?query=:query} : search for the commentOfCitation corresponding
     * to the query.
     *
     * @param query the query of the commentOfCitation search.
     * @return the result of the search.
     */
    @GetMapping("/_search/comment-of-citations")
    public List<CommentOfCitation> searchCommentOfCitations(@RequestParam String query) {
        log.debug("REST request to search CommentOfCitations for query {}", query);
        return commentOfCitationService.search(query);
    }
}
