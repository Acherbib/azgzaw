package com.azgzaw.app.repository.search;

import com.azgzaw.app.domain.Spam;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Spam} entity.
 */
public interface SpamSearchRepository extends ElasticsearchRepository<Spam, String> {
}
