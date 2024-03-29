package com.azgzaw.app.service.impl;

import com.azgzaw.app.service.ProfileService;
import com.azgzaw.app.domain.Profile;
import com.azgzaw.app.repository.ProfileRepository;
import com.azgzaw.app.repository.search.ProfileSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link Profile}.
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    private final Logger log = LoggerFactory.getLogger(ProfileServiceImpl.class);

    private final ProfileRepository profileRepository;

    private final ProfileSearchRepository profileSearchRepository;

    public ProfileServiceImpl(ProfileRepository profileRepository, ProfileSearchRepository profileSearchRepository) {
        this.profileRepository = profileRepository;
        this.profileSearchRepository = profileSearchRepository;
    }

    /**
     * Save a profile.
     *
     * @param profile the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Profile save(Profile profile) {
        log.debug("Request to save Profile : {}", profile);
        Profile result = profileRepository.save(profile);
        profileSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the profiles.
     *
     * @return the list of entities.
     */
    @Override
    public List<Profile> findAll() {
        log.debug("Request to get all Profiles");
        return profileRepository.findAll();
    }


    /**
     * Get one profile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Profile> findOne(String id) {
        log.debug("Request to get Profile : {}", id);
        return profileRepository.findById(id);
    }

    /**
     * Delete the profile by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Profile : {}", id);
        profileRepository.deleteById(id);
        profileSearchRepository.deleteById(id);
    }

    /**
     * Search for the profile corresponding to the query.
     *
     * @param query the query of the search.
     * @return the list of entities.
     */
    @Override
    public List<Profile> search(String query) {
        log.debug("Request to search Profiles for query {}", query);
        return StreamSupport
            .stream(profileSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
