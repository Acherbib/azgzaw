package com.azgzaw.app.repository;

import com.azgzaw.app.domain.CommentOfStatus;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the CommentOfStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentOfStatusRepository extends MongoRepository<CommentOfStatus, String> {

}
