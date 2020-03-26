package com.azgzaw.app.repository;

import com.azgzaw.app.domain.CommentOfGift;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the CommentOfGift entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentOfGiftRepository extends MongoRepository<CommentOfGift, String> {

}
