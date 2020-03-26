package com.azgzaw.app.repository;

import com.azgzaw.app.domain.LikeOfGift;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the LikeOfGift entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeOfGiftRepository extends MongoRepository<LikeOfGift, String> {

}
