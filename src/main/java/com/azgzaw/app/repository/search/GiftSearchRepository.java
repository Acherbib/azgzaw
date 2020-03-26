package com.azgzaw.app.repository.search;

import com.azgzaw.app.domain.Gift;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Gift} entity.
 */
public interface GiftSearchRepository extends ElasticsearchRepository<Gift, String> {
}
