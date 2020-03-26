package com.azgzaw.app.web.rest;

import com.azgzaw.app.domain.Auction;
import com.azgzaw.app.service.AuctionService;
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
 * REST controller for managing {@link com.azgzaw.app.domain.Auction}.
 */
@RestController
@RequestMapping("/api")
public class AuctionResource {

    private final Logger log = LoggerFactory.getLogger(AuctionResource.class);

    private static final String ENTITY_NAME = "auction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AuctionService auctionService;

    public AuctionResource(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    /**
     * {@code POST  /auctions} : Create a new auction.
     *
     * @param auction the auction to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new auction, or with status {@code 400 (Bad Request)} if the auction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/auctions")
    public ResponseEntity<Auction> createAuction(@Valid @RequestBody Auction auction) throws URISyntaxException {
        log.debug("REST request to save Auction : {}", auction);
        if (auction.getId() != null) {
            throw new BadRequestAlertException("A new auction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Auction result = auctionService.save(auction);
        return ResponseEntity.created(new URI("/api/auctions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /auctions} : Updates an existing auction.
     *
     * @param auction the auction to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated auction,
     * or with status {@code 400 (Bad Request)} if the auction is not valid,
     * or with status {@code 500 (Internal Server Error)} if the auction couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/auctions")
    public ResponseEntity<Auction> updateAuction(@Valid @RequestBody Auction auction) throws URISyntaxException {
        log.debug("REST request to update Auction : {}", auction);
        if (auction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Auction result = auctionService.save(auction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, auction.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /auctions} : get all the auctions.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of auctions in body.
     */
    @GetMapping("/auctions")
    public List<Auction> getAllAuctions() {
        log.debug("REST request to get all Auctions");
        return auctionService.findAll();
    }

    /**
     * {@code GET  /auctions/:id} : get the "id" auction.
     *
     * @param id the id of the auction to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the auction, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/auctions/{id}")
    public ResponseEntity<Auction> getAuction(@PathVariable String id) {
        log.debug("REST request to get Auction : {}", id);
        Optional<Auction> auction = auctionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(auction);
    }

    /**
     * {@code DELETE  /auctions/:id} : delete the "id" auction.
     *
     * @param id the id of the auction to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/auctions/{id}")
    public ResponseEntity<Void> deleteAuction(@PathVariable String id) {
        log.debug("REST request to delete Auction : {}", id);
        auctionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }

    /**
     * {@code SEARCH  /_search/auctions?query=:query} : search for the auction corresponding
     * to the query.
     *
     * @param query the query of the auction search.
     * @return the result of the search.
     */
    @GetMapping("/_search/auctions")
    public List<Auction> searchAuctions(@RequestParam String query) {
        log.debug("REST request to search Auctions for query {}", query);
        return auctionService.search(query);
    }
}
