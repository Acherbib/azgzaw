package com.azgzaw.app.repository.search;

import com.azgzaw.app.domain.LikeOfGift;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link LikeOfGift} entity.
 */
public interface LikeOfGiftSearchRepository extends ElasticsearchRepository<LikeOfGift, String> {
}
