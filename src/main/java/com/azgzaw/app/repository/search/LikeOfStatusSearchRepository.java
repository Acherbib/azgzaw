package com.azgzaw.app.repository.search;

import com.azgzaw.app.domain.LikeOfStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link LikeOfStatus} entity.
 */
public interface LikeOfStatusSearchRepository extends ElasticsearchRepository<LikeOfStatus, String> {
}
