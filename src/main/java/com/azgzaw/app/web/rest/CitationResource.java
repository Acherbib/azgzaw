package com.azgzaw.app.web.rest;

import com.azgzaw.app.domain.Citation;
import com.azgzaw.app.service.CitationService;
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
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.azgzaw.app.domain.Citation}.
 */
@RestController
@RequestMapping("/api")
public class CitationResource {

    private final Logger log = LoggerFactory.getLogger(CitationResource.class);

    private static final String ENTITY_NAME = "citation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CitationService citationService;

    public CitationResource(CitationService citationService) {
        this.citationService = citationService;
    }

    /**
     * {@code POST  /citations} : Create a new citation.
     *
     * @param citation the citation to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new citation, or with status {@code 400 (Bad Request)} if the citation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/citations")
    public ResponseEntity<Citation> createCitation(@Valid @RequestBody Citation citation) throws URISyntaxException {
        log.debug("REST request to save Citation : {}", citation);
        if (citation.getId() != null) {
            throw new BadRequestAlertException("A new citation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Citation result = citationService.save(citation);
        return ResponseEntity.created(new URI("/api/citations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /citations} : Updates an existing citation.
     *
     * @param citation the citation to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citation,
     * or with status {@code 400 (Bad Request)} if the citation is not valid,
     * or with status {@code 500 (Internal Server Error)} if the citation couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/citations")
    public ResponseEntity<Citation> updateCitation(@Valid @RequestBody Citation citation) throws URISyntaxException {
        log.debug("REST request to update Citation : {}", citation);
        if (citation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Citation result = citationService.save(citation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citation.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /citations} : get all the citations.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of citations in body.
     */
    @GetMapping("/citations")
    public ResponseEntity<List<Citation>> getAllCitations(Pageable pageable) {
        log.debug("REST request to get a page of Citations");
        Page<Citation> page = citationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /citations/:id} : get the "id" citation.
     *
     * @param id the id of the citation to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the citation, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/citations/{id}")
    public ResponseEntity<Citation> getCitation(@PathVariable String id) {
        log.debug("REST request to get Citation : {}", id);
        Optional<Citation> citation = citationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(citation);
    }

    /**
     * {@code DELETE  /citations/:id} : delete the "id" citation.
     *
     * @param id the id of the citation to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/citations/{id}")
    public ResponseEntity<Void> deleteCitation(@PathVariable String id) {
        log.debug("REST request to delete Citation : {}", id);
        citationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/citations?query=:query} : search for the citation corresponding
     * to the query.
     *
     * @param query the query of the citation search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/citations")
    public ResponseEntity<List<Citation>> searchCitations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Citations for query {}", query);
        Page<Citation> page = citationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
