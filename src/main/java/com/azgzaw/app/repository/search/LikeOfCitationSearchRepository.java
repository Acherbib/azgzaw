package com.azgzaw.app.repository.search;

import com.azgzaw.app.domain.LikeOfCitation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link LikeOfCitation} entity.
 */
public interface LikeOfCitationSearchRepository extends ElasticsearchRepository<LikeOfCitation, String> {
}
