package com.azgzaw.app.web.rest;

import com.azgzaw.app.AzgzawApp;
import com.azgzaw.app.domain.LikeOfGift;
import com.azgzaw.app.repository.LikeOfGiftRepository;
import com.azgzaw.app.repository.search.LikeOfGiftSearchRepository;
import com.azgzaw.app.service.LikeOfGiftService;
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
 * Integration tests for the {@link LikeOfGiftResource} REST controller.
 */
@SpringBootTest(classes = AzgzawApp.class)
public class LikeOfGiftResourceIT {

    private static final LikeType DEFAULT_TYPE_OF_LIKE = LikeType.LIKE;
    private static final LikeType UPDATED_TYPE_OF_LIKE = LikeType.LOVE;

    @Autowired
    private LikeOfGiftRepository likeOfGiftRepository;

    @Autowired
    private LikeOfGiftService likeOfGiftService;

    /**
     * This repository is mocked in the com.azgzaw.app.repository.search test package.
     *
     * @see com.azgzaw.app.repository.search.LikeOfGiftSearchRepositoryMockConfiguration
     */
    @Autowired
    private LikeOfGiftSearchRepository mockLikeOfGiftSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restLikeOfGiftMockMvc;

    private LikeOfGift likeOfGift;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LikeOfGiftResource likeOfGiftResource = new LikeOfGiftResource(likeOfGiftService);
        this.restLikeOfGiftMockMvc = MockMvcBuilders.standaloneSetup(likeOfGiftResource)
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
    public static LikeOfGift createEntity() {
        LikeOfGift likeOfGift = new LikeOfGift()
            .typeOfLike(DEFAULT_TYPE_OF_LIKE);
        return likeOfGift;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LikeOfGift createUpdatedEntity() {
        LikeOfGift likeOfGift = new LikeOfGift()
            .typeOfLike(UPDATED_TYPE_OF_LIKE);
        return likeOfGift;
    }

    @BeforeEach
    public void initTest() {
        likeOfGiftRepository.deleteAll();
        likeOfGift = createEntity();
    }

    @Test
    public void createLikeOfGift() throws Exception {
        int databaseSizeBeforeCreate = likeOfGiftRepository.findAll().size();

        // Create the LikeOfGift
        restLikeOfGiftMockMvc.perform(post("/api/like-of-gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeOfGift)))
            .andExpect(status().isCreated());

        // Validate the LikeOfGift in the database
        List<LikeOfGift> likeOfGiftList = likeOfGiftRepository.findAll();
        assertThat(likeOfGiftList).hasSize(databaseSizeBeforeCreate + 1);
        LikeOfGift testLikeOfGift = likeOfGiftList.get(likeOfGiftList.size() - 1);
        assertThat(testLikeOfGift.getTypeOfLike()).isEqualTo(DEFAULT_TYPE_OF_LIKE);

        // Validate the LikeOfGift in Elasticsearch
        verify(mockLikeOfGiftSearchRepository, times(1)).save(testLikeOfGift);
    }

    @Test
    public void createLikeOfGiftWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = likeOfGiftRepository.findAll().size();

        // Create the LikeOfGift with an existing ID
        likeOfGift.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikeOfGiftMockMvc.perform(post("/api/like-of-gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeOfGift)))
            .andExpect(status().isBadRequest());

        // Validate the LikeOfGift in the database
        List<LikeOfGift> likeOfGiftList = likeOfGiftRepository.findAll();
        assertThat(likeOfGiftList).hasSize(databaseSizeBeforeCreate);

        // Validate the LikeOfGift in Elasticsearch
        verify(mockLikeOfGiftSearchRepository, times(0)).save(likeOfGift);
    }


    @Test
    public void checkTypeOfLikeIsRequired() throws Exception {
        int databaseSizeBeforeTest = likeOfGiftRepository.findAll().size();
        // set the field null
        likeOfGift.setTypeOfLike(null);

        // Create the LikeOfGift, which fails.

        restLikeOfGiftMockMvc.perform(post("/api/like-of-gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeOfGift)))
            .andExpect(status().isBadRequest());

        List<LikeOfGift> likeOfGiftList = likeOfGiftRepository.findAll();
        assertThat(likeOfGiftList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllLikeOfGifts() throws Exception {
        // Initialize the database
        likeOfGiftRepository.save(likeOfGift);

        // Get all the likeOfGiftList
        restLikeOfGiftMockMvc.perform(get("/api/like-of-gifts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likeOfGift.getId())))
            .andExpect(jsonPath("$.[*].typeOfLike").value(hasItem(DEFAULT_TYPE_OF_LIKE.toString())));
    }
    
    @Test
    public void getLikeOfGift() throws Exception {
        // Initialize the database
        likeOfGiftRepository.save(likeOfGift);

        // Get the likeOfGift
        restLikeOfGiftMockMvc.perform(get("/api/like-of-gifts/{id}", likeOfGift.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(likeOfGift.getId()))
            .andExpect(jsonPath("$.typeOfLike").value(DEFAULT_TYPE_OF_LIKE.toString()));
    }

    @Test
    public void getNonExistingLikeOfGift() throws Exception {
        // Get the likeOfGift
        restLikeOfGiftMockMvc.perform(get("/api/like-of-gifts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateLikeOfGift() throws Exception {
        // Initialize the database
        likeOfGiftService.save(likeOfGift);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockLikeOfGiftSearchRepository);

        int databaseSizeBeforeUpdate = likeOfGiftRepository.findAll().size();

        // Update the likeOfGift
        LikeOfGift updatedLikeOfGift = likeOfGiftRepository.findById(likeOfGift.getId()).get();
        updatedLikeOfGift
            .typeOfLike(UPDATED_TYPE_OF_LIKE);

        restLikeOfGiftMockMvc.perform(put("/api/like-of-gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLikeOfGift)))
            .andExpect(status().isOk());

        // Validate the LikeOfGift in the database
        List<LikeOfGift> likeOfGiftList = likeOfGiftRepository.findAll();
        assertThat(likeOfGiftList).hasSize(databaseSizeBeforeUpdate);
        LikeOfGift testLikeOfGift = likeOfGiftList.get(likeOfGiftList.size() - 1);
        assertThat(testLikeOfGift.getTypeOfLike()).isEqualTo(UPDATED_TYPE_OF_LIKE);

        // Validate the LikeOfGift in Elasticsearch
        verify(mockLikeOfGiftSearchRepository, times(1)).save(testLikeOfGift);
    }

    @Test
    public void updateNonExistingLikeOfGift() throws Exception {
        int databaseSizeBeforeUpdate = likeOfGiftRepository.findAll().size();

        // Create the LikeOfGift

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikeOfGiftMockMvc.perform(put("/api/like-of-gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(likeOfGift)))
            .andExpect(status().isBadRequest());

        // Validate the LikeOfGift in the database
        List<LikeOfGift> likeOfGiftList = likeOfGiftRepository.findAll();
        assertThat(likeOfGiftList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LikeOfGift in Elasticsearch
        verify(mockLikeOfGiftSearchRepository, times(0)).save(likeOfGift);
    }

    @Test
    public void deleteLikeOfGift() throws Exception {
        // Initialize the database
        likeOfGiftService.save(likeOfGift);

        int databaseSizeBeforeDelete = likeOfGiftRepository.findAll().size();

        // Delete the likeOfGift
        restLikeOfGiftMockMvc.perform(delete("/api/like-of-gifts/{id}", likeOfGift.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LikeOfGift> likeOfGiftList = likeOfGiftRepository.findAll();
        assertThat(likeOfGiftList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LikeOfGift in Elasticsearch
        verify(mockLikeOfGiftSearchRepository, times(1)).deleteById(likeOfGift.getId());
    }

    @Test
    public void searchLikeOfGift() throws Exception {
        // Initialize the database
        likeOfGiftService.save(likeOfGift);
        when(mockLikeOfGiftSearchRepository.search(queryStringQuery("id:" + likeOfGift.getId())))
            .thenReturn(Collections.singletonList(likeOfGift));
        // Search the likeOfGift
        restLikeOfGiftMockMvc.perform(get("/api/_search/like-of-gifts?query=id:" + likeOfGift.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likeOfGift.getId())))
            .andExpect(jsonPath("$.[*].typeOfLike").value(hasItem(DEFAULT_TYPE_OF_LIKE.toString())));
    }
}
