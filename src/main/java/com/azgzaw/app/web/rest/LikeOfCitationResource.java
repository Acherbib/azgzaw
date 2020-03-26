package com.azgzaw.app.web.rest;

import com.azgzaw.app.domain.LikeOfCitation;
import com.azgzaw.app.service.LikeOfCitationService;
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
 * REST controller for managing {@link com.azgzaw.app.domain.LikeOfCitation}.
 */
@RestController
@RequestMapping("/api")
public class LikeOfCitationResource {

    private final Logger log = LoggerFactory.getLogger(LikeOfCitationResource.class);

    private static final String ENTITY_NAME = "likeOfCitation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LikeOfCitationService likeOfCitationService;

    public LikeOfCitationResource(LikeOfCitationService likeOfCitationService) {
        this.likeOfCitationService = likeOfCitationService;
    }

    /**
     * {@code POST  /like-of-citations} : Create a new likeOfCitation.
     *
     * @param likeOfCitation the likeOfCitation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new likeOfCitation, or with status {@code 400 (Bad Request)} if the likeOfCitation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/like-of-citations")
    public ResponseEntity<LikeOfCitation> createLikeOfCitation(@Valid @RequestBody LikeOfCitation likeOfCitation) throws URISyntaxException {
        log.debug("REST request to save LikeOfCitation : {}", likeOfCitation);
        if (likeOfCitation.getId() != null) {
            throw new BadRequestAlertException("A new likeOfCitation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LikeOfCitation result = likeOfCitationService.save(likeOfCitation);
        return ResponseEntity.created(new URI("/api/like-of-citations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /like-of-citations} : Updates an existing likeOfCitation.
     *
     * @param likeOfCitation the likeOfCitation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated likeOfCitation,
     * or with status {@code 400 (Bad Request)} if the likeOfCitation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the likeOfCitation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/like-of-citations")
    public ResponseEntity<LikeOfCitation> updateLikeOfCitation(@Valid @RequestBody LikeOfCitation likeOfCitation) throws URISyntaxException {
        log.debug("REST request to update LikeOfCitation : {}", likeOfCitation);
        if (likeOfCitation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LikeOfCitation result = likeOfCitationService.save(likeOfCitation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, likeOfCitation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /like-of-citations} : get all the likeOfCitations.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of likeOfCitations in body.
     */
    @GetMapping("/like-of-citations")
    public List<LikeOfCitation> getAllLikeOfCitations() {
        log.debug("REST request to get all LikeOfCitations");
        return likeOfCitationService.findAll();
    }

    /**
     * {@code GET  /like-of-citations/:id} : get the "id" likeOfCitation.
     *
     * @param id the id of the likeOfCitation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the likeOfCitation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/like-of-citations/{id}")
    public ResponseEntity<LikeOfCitation> getLikeOfCitation(@PathVariable String id) {
        log.debug("REST request to get LikeOfCitation : {}", id);
        Optional<LikeOfCitation> likeOfCitation = likeOfCitationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(likeOfCitation);
    }

    /**
     * {@code DELETE  /like-of-citations/:id} : delete the "id" likeOfCitation.
     *
     * @param id the id of the likeOfCitation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/like-of-citations/{id}")
    public ResponseEntity<Void> deleteLikeOfCitation(@PathVariable String id) {
        log.debug("REST request to delete LikeOfCitation : {}", id);
        likeOfCitationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/like-of-citations?query=:query} : search for the likeOfCitation corresponding
     * to the query.
     *
     * @param query the query of the likeOfCitation search.
     * @return the result of the search.
     */
    @GetMapping("/_search/like-of-citations")
    public List<LikeOfCitation> searchLikeOfCitations(@RequestParam String query) {
        log.debug("REST request to search LikeOfCitations for query {}", query);
        return likeOfCitationService.search(query);
    }
}
