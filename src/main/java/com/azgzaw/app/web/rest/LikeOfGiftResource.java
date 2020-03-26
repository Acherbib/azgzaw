package com.azgzaw.app.web.rest;

import com.azgzaw.app.domain.LikeOfGift;
import com.azgzaw.app.service.LikeOfGiftService;
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
 * REST controller for managing {@link com.azgzaw.app.domain.LikeOfGift}.
 */
@RestController
@RequestMapping("/api")
public class LikeOfGiftResource {

    private final Logger log = LoggerFactory.getLogger(LikeOfGiftResource.class);

    private static final String ENTITY_NAME = "likeOfGift";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LikeOfGiftService likeOfGiftService;

    public LikeOfGiftResource(LikeOfGiftService likeOfGiftService) {
        this.likeOfGiftService = likeOfGiftService;
    }

    /**
     * {@code POST  /like-of-gifts} : Create a new likeOfGift.
     *
     * @param likeOfGift the likeOfGift to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new likeOfGift, or with status {@code 400 (Bad Request)} if the likeOfGift has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/like-of-gifts")
    public ResponseEntity<LikeOfGift> createLikeOfGift(@Valid @RequestBody LikeOfGift likeOfGift) throws URISyntaxException {
        log.debug("REST request to save LikeOfGift : {}", likeOfGift);
        if (likeOfGift.getId() != null) {
            throw new BadRequestAlertException("A new likeOfGift cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LikeOfGift result = likeOfGiftService.save(likeOfGift);
        return ResponseEntity.created(new URI("/api/like-of-gifts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /like-of-gifts} : Updates an existing likeOfGift.
     *
     * @param likeOfGift the likeOfGift to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated likeOfGift,
     * or with status {@code 400 (Bad Request)} if the likeOfGift is not valid,
     * or with status {@code 500 (Internal Server Error)} if the likeOfGift couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/like-of-gifts")
    public ResponseEntity<LikeOfGift> updateLikeOfGift(@Valid @RequestBody LikeOfGift likeOfGift) throws URISyntaxException {
        log.debug("REST request to update LikeOfGift : {}", likeOfGift);
        if (likeOfGift.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LikeOfGift result = likeOfGiftService.save(likeOfGift);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, likeOfGift.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /like-of-gifts} : get all the likeOfGifts.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of likeOfGifts in body.
     */
    @GetMapping("/like-of-gifts")
    public List<LikeOfGift> getAllLikeOfGifts() {
        log.debug("REST request to get all LikeOfGifts");
        return likeOfGiftService.findAll();
    }

    /**
     * {@code GET  /like-of-gifts/:id} : get the "id" likeOfGift.
     *
     * @param id the id of the likeOfGift to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the likeOfGift, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/like-of-gifts/{id}")
    public ResponseEntity<LikeOfGift> getLikeOfGift(@PathVariable String id) {
        log.debug("REST request to get LikeOfGift : {}", id);
        Optional<LikeOfGift> likeOfGift = likeOfGiftService.findOne(id);
        return ResponseUtil.wrapOrNotFound(likeOfGift);
    }

    /**
     * {@code DELETE  /like-of-gifts/:id} : delete the "id" likeOfGift.
     *
     * @param id the id of the likeOfGift to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/like-of-gifts/{id}")
    public ResponseEntity<Void> deleteLikeOfGift(@PathVariable String id) {
        log.debug("REST request to delete LikeOfGift : {}", id);
        likeOfGiftService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/like-of-gifts?query=:query} : search for the likeOfGift corresponding
     * to the query.
     *
     * @param query the query of the likeOfGift search.
     * @return the result of the search.
     */
    @GetMapping("/_search/like-of-gifts")
    public List<LikeOfGift> searchLikeOfGifts(@RequestParam String query) {
        log.debug("REST request to search LikeOfGifts for query {}", query);
        return likeOfGiftService.search(query);
    }
}
