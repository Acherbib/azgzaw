package com.azgzaw.app.web.rest;

import com.azgzaw.app.domain.Follows;
import com.azgzaw.app.service.FollowsService;
import com.azgzaw.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.azgzaw.app.domain.Follows}.
 */
@RestController
@RequestMapping("/api")
public class FollowsResource {

    private final Logger log = LoggerFactory.getLogger(FollowsResource.class);

    private static final String ENTITY_NAME = "follows";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FollowsService followsService;

    public FollowsResource(FollowsService followsService) {
        this.followsService = followsService;
    }

    /**
     * {@code POST  /follows} : Create a new follows.
     *
     * @param follows the follows to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new follows, or with status {@code 400 (Bad Request)} if the follows has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/follows")
    public ResponseEntity<Follows> createFollows(@RequestBody Follows follows) throws URISyntaxException {
        log.debug("REST request to save Follows : {}", follows);
        if (follows.getId() != null) {
            throw new BadRequestAlertException("A new follows cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Follows result = followsService.save(follows);
        return ResponseEntity.created(new URI("/api/follows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /follows} : Updates an existing follows.
     *
     * @param follows the follows to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated follows,
     * or with status {@code 400 (Bad Request)} if the follows is not valid,
     * or with status {@code 500 (Internal Server Error)} if the follows couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/follows")
    public ResponseEntity<Follows> updateFollows(@RequestBody Follows follows) throws URISyntaxException {
        log.debug("REST request to update Follows : {}", follows);
        if (follows.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Follows result = followsService.save(follows);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, follows.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /follows} : get all the follows.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of follows in body.
     */
    @GetMapping("/follows")
    public ResponseEntity<List<Follows>> getAllFollows(Pageable pageable) {
        log.debug("REST request to get a page of Follows");
        Page<Follows> page = followsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /follows/:id} : get the "id" follows.
     *
     * @param id the id of the follows to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the follows, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/follows/{id}")
    public ResponseEntity<Follows> getFollows(@PathVariable String id) {
        log.debug("REST request to get Follows : {}", id);
        Optional<Follows> follows = followsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(follows);
    }

    /**
     * {@code DELETE  /follows/:id} : delete the "id" follows.
     *
     * @param id the id of the follows to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/follows/{id}")
    public ResponseEntity<Void> deleteFollows(@PathVariable String id) {
        log.debug("REST request to delete Follows : {}", id);
        followsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/follows?query=:query} : search for the follows corresponding
     * to the query.
     *
     * @param query the query of the follows search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/follows")
    public ResponseEntity<List<Follows>> searchFollows(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Follows for query {}", query);
        Page<Follows> page = followsService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
