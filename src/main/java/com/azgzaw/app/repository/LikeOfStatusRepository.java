package com.azgzaw.app.repository;

import com.azgzaw.app.domain.LikeOfStatus;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the LikeOfStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeOfStatusRepository extends MongoRepository<LikeOfStatus, String> {

}
