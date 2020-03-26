package com.azgzaw.app.repository.search;

import com.azgzaw.app.domain.CommentOfCitation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CommentOfCitation} entity.
 */
public interface CommentOfCitationSearchRepository extends ElasticsearchRepository<CommentOfCitation, String> {
}
