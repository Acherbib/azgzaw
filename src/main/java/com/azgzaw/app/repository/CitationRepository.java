package com.azgzaw.app.repository;

import com.azgzaw.app.domain.Citation;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Citation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CitationRepository extends MongoRepository<Citation, String> {

}
