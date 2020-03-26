package com.azgzaw.app.repository.search;

import com.azgzaw.app.domain.Follows;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Follows} entity.
 */
public interface FollowsSearchRepository extends ElasticsearchRepository<Follows, String> {
}
