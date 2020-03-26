package com.azgzaw.app.repository.search;

import com.azgzaw.app.domain.CommentOfGift;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CommentOfGift} entity.
 */
public interface CommentOfGiftSearchRepository extends ElasticsearchRepository<CommentOfGift, String> {
}
