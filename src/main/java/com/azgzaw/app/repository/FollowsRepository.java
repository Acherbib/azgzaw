package com.azgzaw.app.repository;

import com.azgzaw.app.domain.Follows;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Follows entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FollowsRepository extends MongoRepository<Follows, String> {

}
