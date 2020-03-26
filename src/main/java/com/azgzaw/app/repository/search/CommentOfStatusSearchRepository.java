package com.azgzaw.app.repository.search;

import com.azgzaw.app.domain.CommentOfStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CommentOfStatus} entity.
 */
public interface CommentOfStatusSearchRepository extends ElasticsearchRepository<CommentOfStatus, String> {
}
