package com.azgzaw.app.web.rest;

import com.azgzaw.app.AzgzawApp;
import com.azgzaw.app.domain.Follows;
import com.azgzaw.app.repository.FollowsRepository;
import com.azgzaw.app.repository.search.FollowsSearchRepository;
import com.azgzaw.app.service.FollowsService;
import com.azgzaw.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static com.azgzaw.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FollowsResource} REST controller.
 */
@SpringBootTest(classes = AzgzawApp.class)
public class FollowsResourceIT {

    private static final Instant DEFAULT_FOLLOWING_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FOLLOWING_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ACCEPTED = false;
    private static final Boolean UPDATED_ACCEPTED = true;

    private static final Boolean DEFAULT_BLOCKED = false;
    private static final Boolean UPDATED_BLOCKED = true;

    @Autowired
    private FollowsRepository followsRepository;

    @Autowired
    private FollowsService followsService;

    /**
     * This repository is mocked in the com.azgzaw.app.repository.search test package.
     *
     * @see com.azgzaw.app.repository.search.FollowsSearchRepositoryMockConfiguration
     */
    @Autowired
    private FollowsSearchRepository mockFollowsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restFollowsMockMvc;

    private Follows follows;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FollowsResource followsResource = new FollowsResource(followsService);
        this.restFollowsMockMvc = MockMvcBuilders.standaloneSetup(followsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Follows createEntity() {
        Follows follows = new Follows()
            .followingStartDate(DEFAULT_FOLLOWING_START_DATE)
            .accepted(DEFAULT_ACCEPTED)
            .blocked(DEFAULT_BLOCKED);
        return follows;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Follows createUpdatedEntity() {
        Follows follows = new Follows()
            .followingStartDate(UPDATED_FOLLOWING_START_DATE)
            .accepted(UPDATED_ACCEPTED)
            .blocked(UPDATED_BLOCKED);
        return follows;
    }

    @BeforeEach
    public void initTest() {
        followsRepository.deleteAll();
        follows = createEntity();
    }

    @Test
    public void createFollows() throws Exception {
        int databaseSizeBeforeCreate = followsRepository.findAll().size();

        // Create the Follows
        restFollowsMockMvc.perform(post("/api/follows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(follows)))
            .andExpect(status().isCreated());

        // Validate the Follows in the database
        List<Follows> followsList = followsRepository.findAll();
        assertThat(followsList).hasSize(databaseSizeBeforeCreate + 1);
        Follows testFollows = followsList.get(followsList.size() - 1);
        assertThat(testFollows.getFollowingStartDate()).isEqualTo(DEFAULT_FOLLOWING_START_DATE);
        assertThat(testFollows.isAccepted()).isEqualTo(DEFAULT_ACCEPTED);
        assertThat(testFollows.isBlocked()).isEqualTo(DEFAULT_BLOCKED);

        // Validate the Follows in Elasticsearch
        verify(mockFollowsSearchRepository, times(1)).save(testFollows);
    }

    @Test
    public void createFollowsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = followsRepository.findAll().size();

        // Create the Follows with an existing ID
        follows.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restFollowsMockMvc.perform(post("/api/follows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(follows)))
            .andExpect(status().isBadRequest());

        // Validate the Follows in the database
        List<Follows> followsList = followsRepository.findAll();
        assertThat(followsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Follows in Elasticsearch
        verify(mockFollowsSearchRepository, times(0)).save(follows);
    }


    @Test
    public void getAllFollows() throws Exception {
        // Initialize the database
        followsRepository.save(follows);

        // Get all the followsList
        restFollowsMockMvc.perform(get("/api/follows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(follows.getId())))
            .andExpect(jsonPath("$.[*].followingStartDate").value(hasItem(DEFAULT_FOLLOWING_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].accepted").value(hasItem(DEFAULT_ACCEPTED.booleanValue())))
            .andExpect(jsonPath("$.[*].blocked").value(hasItem(DEFAULT_BLOCKED.booleanValue())));
    }
    
    @Test
    public void getFollows() throws Exception {
        // Initialize the database
        followsRepository.save(follows);

        // Get the follows
        restFollowsMockMvc.perform(get("/api/follows/{id}", follows.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(follows.getId()))
            .andExpect(jsonPath("$.followingStartDate").value(DEFAULT_FOLLOWING_START_DATE.toString()))
            .andExpect(jsonPath("$.accepted").value(DEFAULT_ACCEPTED.booleanValue()))
            .andExpect(jsonPath("$.blocked").value(DEFAULT_BLOCKED.booleanValue()));
    }

    @Test
    public void getNonExistingFollows() throws Exception {
        // Get the follows
        restFollowsMockMvc.perform(get("/api/follows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateFollows() throws Exception {
        // Initialize the database
        followsService.save(follows);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockFollowsSearchRepository);

        int databaseSizeBeforeUpdate = followsRepository.findAll().size();

        // Update the follows
        Follows updatedFollows = followsRepository.findById(follows.getId()).get();
        updatedFollows
            .followingStartDate(UPDATED_FOLLOWING_START_DATE)
            .accepted(UPDATED_ACCEPTED)
            .blocked(UPDATED_BLOCKED);

        restFollowsMockMvc.perform(put("/api/follows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFollows)))
            .andExpect(status().isOk());

        // Validate the Follows in the database
        List<Follows> followsList = followsRepository.findAll();
        assertThat(followsList).hasSize(databaseSizeBeforeUpdate);
        Follows testFollows = followsList.get(followsList.size() - 1);
        assertThat(testFollows.getFollowingStartDate()).isEqualTo(UPDATED_FOLLOWING_START_DATE);
        assertThat(testFollows.isAccepted()).isEqualTo(UPDATED_ACCEPTED);
        assertThat(testFollows.isBlocked()).isEqualTo(UPDATED_BLOCKED);

        // Validate the Follows in Elasticsearch
        verify(mockFollowsSearchRepository, times(1)).save(testFollows);
    }

    @Test
    public void updateNonExistingFollows() throws Exception {
        int databaseSizeBeforeUpdate = followsRepository.findAll().size();

        // Create the Follows

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFollowsMockMvc.perform(put("/api/follows")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(follows)))
            .andExpect(status().isBadRequest());

        // Validate the Follows in the database
        List<Follows> followsList = followsRepository.findAll();
        assertThat(followsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Follows in Elasticsearch
        verify(mockFollowsSearchRepository, times(0)).save(follows);
    }

    @Test
    public void deleteFollows() throws Exception {
        // Initialize the database
        followsService.save(follows);

        int databaseSizeBeforeDelete = followsRepository.findAll().size();

        // Delete the follows
        restFollowsMockMvc.perform(delete("/api/follows/{id}", follows.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Follows> followsList = followsRepository.findAll();
        assertThat(followsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Follows in Elasticsearch
        verify(mockFollowsSearchRepository, times(1)).deleteById(follows.getId());
    }

    @Test
    public void searchFollows() throws Exception {
        // Initialize the database
        followsService.save(follows);
        when(mockFollowsSearchRepository.search(queryStringQuery("id:" + follows.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(follows), PageRequest.of(0, 1), 1));
        // Search the follows
        restFollowsMockMvc.perform(get("/api/_search/follows?query=id:" + follows.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(follows.getId())))
            .andExpect(jsonPath("$.[*].followingStartDate").value(hasItem(DEFAULT_FOLLOWING_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].accepted").value(hasItem(DEFAULT_ACCEPTED.booleanValue())))
            .andExpect(jsonPath("$.[*].blocked").value(hasItem(DEFAULT_BLOCKED.booleanValue())));
    }
}
