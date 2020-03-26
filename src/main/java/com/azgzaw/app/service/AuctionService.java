package com.azgzaw.app.service;

import com.azgzaw.app.domain.Auction;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Auction}.
 */
public interface AuctionService {

    /**
     * Save a auction.
     *
     * @param auction the entity to save.
     * @return the persisted entity.
     */
    Auction save(Auction auction);

    /**
     * Get all the auctions.
     *
     * @return the list of entities.
     */
    List<Auction> findAll();


    /**
     * Get the "id" auction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Auction> findOne(String id);

    /**
     * Delete the "id" auction.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    /**
     * Search for the auction corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @return the list of entities.
     */
    List<Auction> search(String query);
}
