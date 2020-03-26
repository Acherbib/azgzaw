package com.azgzaw.app.repository.search;

import com.azgzaw.app.domain.Citation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Citation} entity.
 */
public interface CitationSearchRepository extends ElasticsearchRepository<Citation, String> {
}
