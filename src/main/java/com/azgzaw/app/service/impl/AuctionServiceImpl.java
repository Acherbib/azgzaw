package com.azgzaw.app.service.impl;

import com.azgzaw.app.service.AuctionService;
import com.azgzaw.app.domain.Auction;
import com.azgzaw.app.repository.AuctionRepository;
import com.azgzaw.app.repository.search.AuctionSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Auction}.
 */
@Service
public class AuctionServiceImpl implements AuctionService {

    private final Logger log = LoggerFactory.getLogger(AuctionServiceImpl.class);

    private final AuctionRepository auctionRepository;

    private final AuctionSearchRepository auctionSearchRepository;

    public AuctionServiceImpl(AuctionRepository auctionRepository, AuctionSearchRepository auctionSearchRepository) {
        this.auctionRepository = auctionRepository;
        this.auctionSearchRepository = auctionSearchRepository;
    }

    /**
     * Save a auction.
     *
     * @param auction the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Auction save(Auction auction) {
        log.debug("Request to save Auction : {}", auction);
        Auction result = auctionRepository.save(auction);
        auctionSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the auctions.
     *
     * @return the list of entities.
     */
    @Override
    public List<Auction> findAll() {
        log.debug("Request to get all Auctions");
        return auctionRepository.findAll();
    }


    /**
     * Get one auction by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Auction> findOne(String id) {
        log.debug("Request to get Auction : {}", id);
        return auctionRepository.findById(id);
    }

    /**
     * Delete the auction by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Auction : {}", id);
        auctionRepository.deleteById(id);
        auctionSearchRepository.deleteById(id);
    }

    /**
     * Search for the auction corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<Auction> search(String query) {
        log.debug("Request to search Auctions for query {}", query);
        return StreamSupport
            .stream(auctionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
