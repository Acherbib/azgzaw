package com.azgzaw.app.web.rest;

import com.azgzaw.app.domain.Gift;
import com.azgzaw.app.service.GiftService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.azgzaw.app.domain.Gift}.
 */
@RestController
@RequestMapping("/api")
public class GiftResource {

    private final Logger log = LoggerFactory.getLogger(GiftResource.class);

    private static final String ENTITY_NAME = "gift";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GiftService giftService;

    public GiftResource(GiftService giftService) {
        this.giftService = giftService;
    }

    /**
     * {@code POST  /gifts} : Create a new gift.
     *
     * @param gift the gift to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gift, or with status {@code 400 (Bad Request)} if the gift has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gifts")
    public ResponseEntity<Gift> createGift(@Valid @RequestBody Gift gift) throws URISyntaxException {
        log.debug("REST request to save Gift : {}", gift);
        if (gift.getId() != null) {
            throw new BadRequestAlertException("A new gift cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Gift result = giftService.save(gift);
        return ResponseEntity.created(new URI("/api/gifts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gifts} : Updates an existing gift.
     *
     * @param gift the gift to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gift,
     * or with status {@code 400 (Bad Request)} if the gift is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gift couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gifts")
    public ResponseEntity<Gift> updateGift(@Valid @RequestBody Gift gift) throws URISyntaxException {
        log.debug("REST request to update Gift : {}", gift);
        if (gift.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Gift result = giftService.save(gift);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gift.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /gifts} : get all the gifts.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gifts in body.
     */
    @GetMapping("/gifts")
    public ResponseEntity<List<Gift>> getAllGifts(Pageable pageable) {
        log.debug("REST request to get a page of Gifts");
        Page<Gift> page = giftService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gifts/:id} : get the "id" gift.
     *
     * @param id the id of the gift to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gift, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gifts/{id}")
    public ResponseEntity<Gift> getGift(@PathVariable String id) {
        log.debug("REST request to get Gift : {}", id);
        Optional<Gift> gift = giftService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gift);
    }

    /**
     * {@code DELETE  /gifts/:id} : delete the "id" gift.
     *
     * @param id the id of the gift to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gifts/{id}")
    public ResponseEntity<Void> deleteGift(@PathVariable String id) {
        log.debug("REST request to delete Gift : {}", id);
        giftService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/gifts?query=:query} : search for the gift corresponding
     * to the query.
     *
     * @param query the query of the gift search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/gifts")
    public ResponseEntity<List<Gift>> searchGifts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Gifts for query {}", query);
        Page<Gift> page = giftService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
