package com.azgzaw.app.repository;

import com.azgzaw.app.domain.Spam;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Spam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpamRepository extends MongoRepository<Spam, String> {

}
