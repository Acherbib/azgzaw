package com.azgzaw.app.web.rest;

import com.azgzaw.app.AzgzawApp;
import com.azgzaw.app.domain.LikeOfCitation;
import com.azgzaw.app.repository.LikeOfCitationRepository;
import com.azgzaw.app.repository.search.LikeOfCitationSearchRepository;
import com.azgzaw.app.service.LikeOfCitationService;
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
 * Integration tests for the {@link LikeOfCitationResource} REST controller.
 */
@SpringBootTest(classes = AzgzawApp.class)
public class LikeOfCitationResourceIT {

    private static final LikeType DEFAULT_TYPE_OF_LIKE = LikeType.LIKE;
    private static final LikeType UPDATED_TYPE_OF_LIKE = LikeType.LOVE;

    @Autowired
    private LikeOfCitationRepository likeOfCitationRepository;

    @Autowired
    private LikeOfCitationService likeOfCitationService;

    /**
     * This repository is mocked in the com.azgzaw.app.repository.search test package.
     *
     * @see com.azgzaw.app.repository.search.LikeOfCitationSearchRepositoryMockConfiguration
     */
    @Autowired
    private LikeOfCitationSearchRepository mockLikeOfCitationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restLikeOfCitationMockMvc;

    private LikeOfCitation likeOfCitation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LikeOfCitationResource likeOfCitationResource = new LikeOfCitationResource(likeOfCitationService);
        this.restLikeOfCitationMockMvc = MockMvcBuilders.standaloneSetup(likeOfCitationResource)
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
    public static LikeOfCitation createEntity() {
        LikeOfCitation likeOfCitation = new LikeOfCitation()
            .typeOfLike(DEFAULT_TYPE_OF_LIKE);
        return likeOfCitation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LikeOfCitation createUpdatedEntity() {
        LikeOfCitation likeOfCitation = new LikeOfCitation()
            .typeOfLike(UPDATED_TYPE_OF_LIKE);
        return likeOfCitation;
    }

    @BeforeEach
    public void initTest() {
        likeOfCitationRepository.deleteAll();
        likeOfCitation = createEntity();
    }

    @Test
    public void createLikeOfCitation() throws Exception {
        int databaseSizeBeforeCreate = likeOfCitationRepository.findAll().size();

        // Create the LikeOfCitation
        restLikeOfCitationMockMvc.perform(post("/api/like-of-citations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeOfCitation)))
            .andExpect(status().isCreated());

        // Validate the LikeOfCitation in the database
        List<LikeOfCitation> likeOfCitationList = likeOfCitationRepository.findAll();
        assertThat(likeOfCitationList).hasSize(databaseSizeBeforeCreate + 1);
        LikeOfCitation testLikeOfCitation = likeOfCitationList.get(likeOfCitationList.size() - 1);
        assertThat(testLikeOfCitation.getTypeOfLike()).isEqualTo(DEFAULT_TYPE_OF_LIKE);

        // Validate the LikeOfCitation in Elasticsearch
        verify(mockLikeOfCitationSearchRepository, times(1)).save(testLikeOfCitation);
    }

    @Test
    public void createLikeOfCitationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = likeOfCitationRepository.findAll().size();

        // Create the LikeOfCitation with an existing ID
        likeOfCitation.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikeOfCitationMockMvc.perform(post("/api/like-of-citations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeOfCitation)))
            .andExpect(status().isBadRequest());

        // Validate the LikeOfCitation in the database
        List<LikeOfCitation> likeOfCitationList = likeOfCitationRepository.findAll();
        assertThat(likeOfCitationList).hasSize(databaseSizeBeforeCreate);

        // Validate the LikeOfCitation in Elasticsearch
        verify(mockLikeOfCitationSearchRepository, times(0)).save(likeOfCitation);
    }


    @Test
    public void checkTypeOfLikeIsRequired() throws Exception {
        int databaseSizeBeforeTest = likeOfCitationRepository.findAll().size();
        // set the field null
        likeOfCitation.setTypeOfLike(null);

        // Create the LikeOfCitation, which fails.

        restLikeOfCitationMockMvc.perform(post("/api/like-of-citations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeOfCitation)))
            .andExpect(status().isBadRequest());

        List<LikeOfCitation> likeOfCitationList = likeOfCitationRepository.findAll();
        assertThat(likeOfCitationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllLikeOfCitations() throws Exception {
        // Initialize the database
        likeOfCitationRepository.save(likeOfCitation);

        // Get all the likeOfCitationList
        restLikeOfCitationMockMvc.perform(get("/api/like-of-citations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likeOfCitation.getId())))
            .andExpect(jsonPath("$.[*].typeOfLike").value(hasItem(DEFAULT_TYPE_OF_LIKE.toString())));
    }
    
    @Test
    public void getLikeOfCitation() throws Exception {
        // Initialize the database
        likeOfCitationRepository.save(likeOfCitation);

        // Get the likeOfCitation
        restLikeOfCitationMockMvc.perform(get("/api/like-of-citations/{id}", likeOfCitation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(likeOfCitation.getId()))
            .andExpect(jsonPath("$.typeOfLike").value(DEFAULT_TYPE_OF_LIKE.toString()));
    }

    @Test
    public void getNonExistingLikeOfCitation() throws Exception {
        // Get the likeOfCitation
        restLikeOfCitationMockMvc.perform(get("/api/like-of-citations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateLikeOfCitation() throws Exception {
        // Initialize the database
        likeOfCitationService.save(likeOfCitation);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockLikeOfCitationSearchRepository);

        int databaseSizeBeforeUpdate = likeOfCitationRepository.findAll().size();

        // Update the likeOfCitation
        LikeOfCitation updatedLikeOfCitation = likeOfCitationRepository.findById(likeOfCitation.getId()).get();
        updatedLikeOfCitation
            .typeOfLike(UPDATED_TYPE_OF_LIKE);

        restLikeOfCitationMockMvc.perform(put("/api/like-of-citations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLikeOfCitation)))
            .andExpect(status().isOk());

        // Validate the LikeOfCitation in the database
        List<LikeOfCitation> likeOfCitationList = likeOfCitationRepository.findAll();
        assertThat(likeOfCitationList).hasSize(databaseSizeBeforeUpdate);
        LikeOfCitation testLikeOfCitation = likeOfCitationList.get(likeOfCitationList.size() - 1);
        assertThat(testLikeOfCitation.getTypeOfLike()).isEqualTo(UPDATED_TYPE_OF_LIKE);

        // Validate the LikeOfCitation in Elasticsearch
        verify(mockLikeOfCitationSearchRepository, times(1)).save(testLikeOfCitation);
    }

    @Test
    public void updateNonExistingLikeOfCitation() throws Exception {
        int databaseSizeBeforeUpdate = likeOfCitationRepository.findAll().size();

        // Create the LikeOfCitation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikeOfCitationMockMvc.perform(put("/api/like-of-citations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeOfCitation)))
            .andExpect(status().isBadRequest());

        // Validate the LikeOfCitation in the database
        List<LikeOfCitation> likeOfCitationList = likeOfCitationRepository.findAll();
        assertThat(likeOfCitationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LikeOfCitation in Elasticsearch
        verify(mockLikeOfCitationSearchRepository, times(0)).save(likeOfCitation);
    }

    @Test
    public void deleteLikeOfCitation() throws Exception {
        // Initialize the database
        likeOfCitationService.save(likeOfCitation);

        int databaseSizeBeforeDelete = likeOfCitationRepository.findAll().size();

        // Delete the likeOfCitation
        restLikeOfCitationMockMvc.perform(delete("/api/like-of-citations/{id}", likeOfCitation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LikeOfCitation> likeOfCitationList = likeOfCitationRepository.findAll();
        assertThat(likeOfCitationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LikeOfCitation in Elasticsearch
        verify(mockLikeOfCitationSearchRepository, times(1)).deleteById(likeOfCitation.getId());
    }

    @Test
    public void searchLikeOfCitation() throws Exception {
        // Initialize the database
        likeOfCitationService.save(likeOfCitation);
        when(mockLikeOfCitationSearchRepository.search(queryStringQuery("id:" + likeOfCitation.getId())))
            .thenReturn(Collections.singletonList(likeOfCitation));
        // Search the likeOfCitation
        restLikeOfCitationMockMvc.perform(get("/api/_search/like-of-citations?query=id:" + likeOfCitation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likeOfCitation.getId())))
            .andExpect(jsonPath("$.[*].typeOfLike").value(hasItem(DEFAULT_TYPE_OF_LIKE.toString())));
    }
}
