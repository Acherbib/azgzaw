package com.azgzaw.app.repository;

import com.azgzaw.app.domain.LikeOfCitation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the LikeOfCitation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeOfCitationRepository extends MongoRepository<LikeOfCitation, String> {

}
