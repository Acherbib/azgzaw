package com.azgzaw.app.repository.search;

import com.azgzaw.app.domain.Auction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Auction} entity.
 */
public interface AuctionSearchRepository extends ElasticsearchRepository<Auction, String> {
}
