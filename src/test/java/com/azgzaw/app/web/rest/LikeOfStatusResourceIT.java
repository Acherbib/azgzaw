package com.azgzaw.app.web.rest;

import com.azgzaw.app.AzgzawApp;
import com.azgzaw.app.domain.LikeOfStatus;
import com.azgzaw.app.repository.LikeOfStatusRepository;
import com.azgzaw.app.repository.search.LikeOfStatusSearchRepository;
import com.azgzaw.app.service.LikeOfStatusService;
import com.azgzaw.app.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;


import java.util.Collections;
import java.util.List;

import static com.azgzaw.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.azgzaw.app.domain.enumeration.LikeType;
/**
 * Integration tests for the {@link LikeOfStatusResource} REST controller.
 */
@SpringBootTest(classes = AzgzawApp.class)
public class LikeOfStatusResourceIT {

    private static final LikeType DEFAULT_TYPE_OF_LIKE = LikeType.LIKE;
    private static final LikeType UPDATED_TYPE_OF_LIKE = LikeType.LOVE;

    @Autowired
    private LikeOfStatusRepository likeOfStatusRepository;

    @Autowired
    private LikeOfStatusService likeOfStatusService;

    /**
     * This repository is mocked in the com.azgzaw.app.repository.search test package.
     *
     * @see com.azgzaw.app.repository.search.LikeOfStatusSearchRepositoryMockConfiguration
     */
    @Autowired
    private LikeOfStatusSearchRepository mockLikeOfStatusSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restLikeOfStatusMockMvc;

    private LikeOfStatus likeOfStatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LikeOfStatusResource likeOfStatusResource = new LikeOfStatusResource(likeOfStatusService);
        this.restLikeOfStatusMockMvc = MockMvcBuilders.standaloneSetup(likeOfStatusResource)
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
    public static LikeOfStatus createEntity() {
        LikeOfStatus likeOfStatus = new LikeOfStatus()
            .typeOfLike(DEFAULT_TYPE_OF_LIKE);
        return likeOfStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LikeOfStatus createUpdatedEntity() {
        LikeOfStatus likeOfStatus = new LikeOfStatus()
            .typeOfLike(UPDATED_TYPE_OF_LIKE);
        return likeOfStatus;
    }

    @BeforeEach
    public void initTest() {
        likeOfStatusRepository.deleteAll();
        likeOfStatus = createEntity();
    }

    @Test
    public void createLikeOfStatus() throws Exception {
        int databaseSizeBeforeCreate = likeOfStatusRepository.findAll().size();

        // Create the LikeOfStatus
        restLikeOfStatusMockMvc.perform(post("/api/like-of-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeOfStatus)))
            .andExpect(status().isCreated());

        // Validate the LikeOfStatus in the database
        List<LikeOfStatus> likeOfStatusList = likeOfStatusRepository.findAll();
        assertThat(likeOfStatusList).hasSize(databaseSizeBeforeCreate + 1);
        LikeOfStatus testLikeOfStatus = likeOfStatusList.get(likeOfStatusList.size() - 1);
        assertThat(testLikeOfStatus.getTypeOfLike()).isEqualTo(DEFAULT_TYPE_OF_LIKE);

        // Validate the LikeOfStatus in Elasticsearch
        verify(mockLikeOfStatusSearchRepository, times(1)).save(testLikeOfStatus);
    }

    @Test
    public void createLikeOfStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = likeOfStatusRepository.findAll().size();

        // Create the LikeOfStatus with an existing ID
        likeOfStatus.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikeOfStatusMockMvc.perform(post("/api/like-of-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeOfStatus)))
            .andExpect(status().isBadRequest());

        // Validate the LikeOfStatus in the database
        List<LikeOfStatus> likeOfStatusList = likeOfStatusRepository.findAll();
        assertThat(likeOfStatusList).hasSize(databaseSizeBeforeCreate);

        // Validate the LikeOfStatus in Elasticsearch
        verify(mockLikeOfStatusSearchRepository, times(0)).save(likeOfStatus);
    }


    @Test
    public void checkTypeOfLikeIsRequired() throws Exception {
        int databaseSizeBeforeTest = likeOfStatusRepository.findAll().size();
        // set the field null
        likeOfStatus.setTypeOfLike(null);

        // Create the LikeOfStatus, which fails.

        restLikeOfStatusMockMvc.perform(post("/api/like-of-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeOfStatus)))
            .andExpect(status().isBadRequest());

        List<LikeOfStatus> likeOfStatusList = likeOfStatusRepository.findAll();
        assertThat(likeOfStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllLikeOfStatuses() throws Exception {
        // Initialize the database
        likeOfStatusRepository.save(likeOfStatus);

        // Get all the likeOfStatusList
        restLikeOfStatusMockMvc.perform(get("/api/like-of-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likeOfStatus.getId())))
            .andExpect(jsonPath("$.[*].typeOfLike").value(hasItem(DEFAULT_TYPE_OF_LIKE.toString())));
    }
    
    @Test
    public void getLikeOfStatus() throws Exception {
        // Initialize the database
        likeOfStatusRepository.save(likeOfStatus);

        // Get the likeOfStatus
        restLikeOfStatusMockMvc.perform(get("/api/like-of-statuses/{id}", likeOfStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(likeOfStatus.getId()))
            .andExpect(jsonPath("$.typeOfLike").value(DEFAULT_TYPE_OF_LIKE.toString()));
    }

    @Test
    public void getNonExistingLikeOfStatus() throws Exception {
        // Get the likeOfStatus
        restLikeOfStatusMockMvc.perform(get("/api/like-of-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateLikeOfStatus() throws Exception {
        // Initialize the database
        likeOfStatusService.save(likeOfStatus);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockLikeOfStatusSearchRepository);

        int databaseSizeBeforeUpdate = likeOfStatusRepository.findAll().size();

        // Update the likeOfStatus
        LikeOfStatus updatedLikeOfStatus = likeOfStatusRepository.findById(likeOfStatus.getId()).get();
        updatedLikeOfStatus
            .typeOfLike(UPDATED_TYPE_OF_LIKE);

        restLikeOfStatusMockMvc.perform(put("/api/like-of-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLikeOfStatus)))
            .andExpect(status().isOk());

        // Validate the LikeOfStatus in the database
        List<LikeOfStatus> likeOfStatusList = likeOfStatusRepository.findAll();
        assertThat(likeOfStatusList).hasSize(databaseSizeBeforeUpdate);
        LikeOfStatus testLikeOfStatus = likeOfStatusList.get(likeOfStatusList.size() - 1);
        assertThat(testLikeOfStatus.getTypeOfLike()).isEqualTo(UPDATED_TYPE_OF_LIKE);

        // Validate the LikeOfStatus in Elasticsearch
        verify(mockLikeOfStatusSearchRepository, times(1)).save(testLikeOfStatus);
    }

    @Test
    public void updateNonExistingLikeOfStatus() throws Exception {
        int databaseSizeBeforeUpdate = likeOfStatusRepository.findAll().size();

        // Create the LikeOfStatus

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikeOfStatusMockMvc.perform(put("/api/like-of-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeOfStatus)))
            .andExpect(status().isBadRequest());

        // Validate the LikeOfStatus in the database
        List<LikeOfStatus> likeOfStatusList = likeOfStatusRepository.findAll();
        assertThat(likeOfStatusList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LikeOfStatus in Elasticsearch
        verify(mockLikeOfStatusSearchRepository, times(0)).save(likeOfStatus);
    }

    @Test
    public void deleteLikeOfStatus() throws Exception {
        // Initialize the database
        likeOfStatusService.save(likeOfStatus);

        int databaseSizeBeforeDelete = likeOfStatusRepository.findAll().size();

        // Delete the likeOfStatus
        restLikeOfStatusMockMvc.perform(delete("/api/like-of-statuses/{id}", likeOfStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LikeOfStatus> likeOfStatusList = likeOfStatusRepository.findAll();
        assertThat(likeOfStatusList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LikeOfStatus in Elasticsearch
        verify(mockLikeOfStatusSearchRepository, times(1)).deleteById(likeOfStatus.getId());
    }

    @Test
    public void searchLikeOfStatus() throws Exception {
        // Initialize the database
        likeOfStatusService.save(likeOfStatus);
        when(mockLikeOfStatusSearchRepository.search(queryStringQuery("id:" + likeOfStatus.getId())))
            .thenReturn(Collections.singletonList(likeOfStatus));
        // Search the likeOfStatus
        restLikeOfStatusMockMvc.perform(get("/api/_search/like-of-statuses?query=id:" + likeOfStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likeOfStatus.getId())))
            .andExpect(jsonPath("$.[*].typeOfLike").value(hasItem(DEFAULT_TYPE_OF_LIKE.toString())));
    }
}
