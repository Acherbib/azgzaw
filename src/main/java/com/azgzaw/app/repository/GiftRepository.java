package com.azgzaw.app.repository;

import com.azgzaw.app.domain.Gift;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data MongoDB repository for the Gift entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GiftRepository extends MongoRepository<Gift, String> {

}
