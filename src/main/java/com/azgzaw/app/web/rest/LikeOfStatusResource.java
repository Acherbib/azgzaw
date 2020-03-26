package com.azgzaw.app.web.rest;

import com.azgzaw.app.domain.LikeOfStatus;
import com.azgzaw.app.service.LikeOfStatusService;
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
 * REST controller for managing {@link com.azgzaw.app.domain.LikeOfStatus}.
 */
@RestController
@RequestMapping("/api")
public class LikeOfStatusResource {

    private final Logger log = LoggerFactory.getLogger(LikeOfStatusResource.class);

    private static final String ENTITY_NAME = "likeOfStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LikeOfStatusService likeOfStatusService;

    public LikeOfStatusResource(LikeOfStatusService likeOfStatusService) {
        this.likeOfStatusService = likeOfStatusService;
    }

    /**
     * {@code POST  /like-of-statuses} : Create a new likeOfStatus.
     *
     * @param likeOfStatus the likeOfStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new likeOfStatus, or with status {@code 400 (Bad Request)} if the likeOfStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/like-of-statuses")
    public ResponseEntity<LikeOfStatus> createLikeOfStatus(@Valid @RequestBody LikeOfStatus likeOfStatus) throws URISyntaxException {
        log.debug("REST request to save LikeOfStatus : {}", likeOfStatus);
        if (likeOfStatus.getId() != null) {
            throw new BadRequestAlertException("A new likeOfStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LikeOfStatus result = likeOfStatusService.save(likeOfStatus);
        return ResponseEntity.created(new URI("/api/like-of-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /like-of-statuses} : Updates an existing likeOfStatus.
     *
     * @param likeOfStatus the likeOfStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated likeOfStatus,
     * or with status {@code 400 (Bad Request)} if the likeOfStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the likeOfStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/like-of-statuses")
    public ResponseEntity<LikeOfStatus> updateLikeOfStatus(@Valid @RequestBody LikeOfStatus likeOfStatus) throws URISyntaxException {
        log.debug("REST request to update LikeOfStatus : {}", likeOfStatus);
        if (likeOfStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LikeOfStatus result = likeOfStatusService.save(likeOfStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, likeOfStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /like-of-statuses} : get all the likeOfStatuses.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of likeOfStatuses in body.
     */
    @GetMapping("/like-of-statuses")
    public List<LikeOfStatus> getAllLikeOfStatuses() {
        log.debug("REST request to get all LikeOfStatuses");
        return likeOfStatusService.findAll();
    }

    /**
     * {@code GET  /like-of-statuses/:id} : get the "id" likeOfStatus.
     *
     * @param id the id of the likeOfStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the likeOfStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/like-of-statuses/{id}")
    public ResponseEntity<LikeOfStatus> getLikeOfStatus(@PathVariable String id) {
        log.debug("REST request to get LikeOfStatus : {}", id);
        Optional<LikeOfStatus> likeOfStatus = likeOfStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(likeOfStatus);
    }

    /**
     * {@code DELETE  /like-of-statuses/:id} : delete the "id" likeOfStatus.
     *
     * @param id the id of the likeOfStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/like-of-statuses/{id}")
    public ResponseEntity<Void> deleteLikeOfStatus(@PathVariable String id) {
        log.debug("REST request to delete LikeOfStatus : {}", id);
        likeOfStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/like-of-statuses?query=:query} : search for the likeOfStatus corresponding
     * to the query.
     *
     * @param query the query of the likeOfStatus search.
     * @return the result of the search.
     */
    @GetMapping("/_search/like-of-statuses")
    public List<LikeOfStatus> searchLikeOfStatuses(@RequestParam String query) {
        log.debug("REST request to search LikeOfStatuses for query {}", query);
        return likeOfStatusService.search(query);
    }
}
