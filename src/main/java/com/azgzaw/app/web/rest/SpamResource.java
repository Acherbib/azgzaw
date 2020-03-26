package com.azgzaw.app.web.rest;

import com.azgzaw.app.domain.Spam;
import com.azgzaw.app.service.SpamService;
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
 * REST controller for managing {@link com.azgzaw.app.domain.Spam}.
 */
@RestController
@RequestMapping("/api")
public class SpamResource {

    private final Logger log = LoggerFactory.getLogger(SpamResource.class);

    private static final String ENTITY_NAME = "spam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SpamService spamService;

    public SpamResource(SpamService spamService) {
        this.spamService = spamService;
    }

    /**
     * {@code POST  /spams} : Create a new spam.
     *
     * @param spam the spam to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new spam, or with status {@code 400 (Bad Request)} if the spam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/spams")
    public ResponseEntity<Spam> createSpam(@Valid @RequestBody Spam spam) throws URISyntaxException {
        log.debug("REST request to save Spam : {}", spam);
        if (spam.getId() != null) {
            throw new BadRequestAlertException("A new spam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Spam result = spamService.save(spam);
        return ResponseEntity.created(new URI("/api/spams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /spams} : Updates an existing spam.
     *
     * @param spam the spam to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated spam,
     * or with status {@code 400 (Bad Request)} if the spam is not valid,
     * or with status {@code 500 (Internal Server Error)} if the spam couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/spams")
    public ResponseEntity<Spam> updateSpam(@Valid @RequestBody Spam spam) throws URISyntaxException {
        log.debug("REST request to update Spam : {}", spam);
        if (spam.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Spam result = spamService.save(spam);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, spam.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /spams} : get all the spams.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of spams in body.
     */
    @GetMapping("/spams")
    public List<Spam> getAllSpams() {
        log.debug("REST request to get all Spams");
        return spamService.findAll();
    }

    /**
     * {@code GET  /spams/:id} : get the "id" spam.
     *
     * @param id the id of the spam to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the spam, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/spams/{id}")
    public ResponseEntity<Spam> getSpam(@PathVariable String id) {
        log.debug("REST request to get Spam : {}", id);
        Optional<Spam> spam = spamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(spam);
    }

    /**
     * {@code DELETE  /spams/:id} : delete the "id" spam.
     *
     * @param id the id of the spam to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/spams/{id}")
    public ResponseEntity<Void> deleteSpam(@PathVariable String id) {
        log.debug("REST request to delete Spam : {}", id);
        spamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/spams?query=:query} : search for the spam corresponding
     * to the query.
     *
     * @param query the query of the spam search.
     * @return the result of the search.
     */
    @GetMapping("/_search/spams")
    public List<Spam> searchSpams(@RequestParam String query) {
        log.debug("REST request to search Spams for query {}", query);
        return spamService.search(query);
    }
}
