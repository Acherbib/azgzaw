package com.azgzaw.app.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link AuctionSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AuctionSearchRepositoryMockConfiguration {

    @MockBean
    private AuctionSearchRepository mockAuctionSearchRepository;

}
