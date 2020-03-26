package com.azgzaw.app.repository;

import com.azgzaw.app.domain.CommentOfCitation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the CommentOfCitation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommentOfCitationRepository extends MongoRepository<CommentOfCitation, String> {

}
