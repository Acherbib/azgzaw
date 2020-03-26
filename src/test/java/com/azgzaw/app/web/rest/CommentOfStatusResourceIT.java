package com.azgzaw.app.web.rest;

import com.azgzaw.app.AzgzawApp;
import com.azgzaw.app.domain.CommentOfStatus;
import com.azgzaw.app.repository.CommentOfStatusRepository;
import com.azgzaw.app.repository.search.CommentOfStatusSearchRepository;
import com.azgzaw.app.service.CommentOfStatusService;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;


import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.azgzaw.app.web.rest.TestUtil.sameInstant;
import static com.azgzaw.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CommentOfStatusResource} REST controller.
 */
@SpringBootTest(classes = AzgzawApp.class)
public class CommentOfStatusResourceIT {

    private static final String DEFAULT_BODY_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_BODY_COMMENT = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COMMENT_OF_STATUS_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMMENT_OF_STATUS_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COMMENT_OF_STATUS_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMMENT_OF_STATUS_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_MEDIA_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_TYPE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_OF_CREATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_OF_CREATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CommentOfStatusRepository commentOfStatusRepository;

    @Autowired
    private CommentOfStatusService commentOfStatusService;

    /**
     * This repository is mocked in the com.azgzaw.app.repository.search test package.
     *
     * @see com.azgzaw.app.repository.search.CommentOfStatusSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommentOfStatusSearchRepository mockCommentOfStatusSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCommentOfStatusMockMvc;

    private CommentOfStatus commentOfStatus;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommentOfStatusResource commentOfStatusResource = new CommentOfStatusResource(commentOfStatusService);
        this.restCommentOfStatusMockMvc = MockMvcBuilders.standaloneSetup(commentOfStatusResource)
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
    public static CommentOfStatus createEntity() {
        CommentOfStatus commentOfStatus = new CommentOfStatus()
            .bodyComment(DEFAULT_BODY_COMMENT)
            .commentOfStatusImage(DEFAULT_COMMENT_OF_STATUS_IMAGE)
            .commentOfStatusImageContentType(DEFAULT_COMMENT_OF_STATUS_IMAGE_CONTENT_TYPE)
            .mediaType(DEFAULT_MEDIA_TYPE)
            .dateOfCreation(DEFAULT_DATE_OF_CREATION);
        return commentOfStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommentOfStatus createUpdatedEntity() {
        CommentOfStatus commentOfStatus = new CommentOfStatus()
            .bodyComment(UPDATED_BODY_COMMENT)
            .commentOfStatusImage(UPDATED_COMMENT_OF_STATUS_IMAGE)
            .commentOfStatusImageContentType(UPDATED_COMMENT_OF_STATUS_IMAGE_CONTENT_TYPE)
            .mediaType(UPDATED_MEDIA_TYPE)
            .dateOfCreation(UPDATED_DATE_OF_CREATION);
        return commentOfStatus;
    }

    @BeforeEach
    public void initTest() {
        commentOfStatusRepository.deleteAll();
        commentOfStatus = createEntity();
    }

    @Test
    public void createCommentOfStatus() throws Exception {
        int databaseSizeBeforeCreate = commentOfStatusRepository.findAll().size();

        // Create the CommentOfStatus
        restCommentOfStatusMockMvc.perform(post("/api/comment-of-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentOfStatus)))
            .andExpect(status().isCreated());

        // Validate the CommentOfStatus in the database
        List<CommentOfStatus> commentOfStatusList = commentOfStatusRepository.findAll();
        assertThat(commentOfStatusList).hasSize(databaseSizeBeforeCreate + 1);
        CommentOfStatus testCommentOfStatus = commentOfStatusList.get(commentOfStatusList.size() - 1);
        assertThat(testCommentOfStatus.getBodyComment()).isEqualTo(DEFAULT_BODY_COMMENT);
        assertThat(testCommentOfStatus.getCommentOfStatusImage()).isEqualTo(DEFAULT_COMMENT_OF_STATUS_IMAGE);
        assertThat(testCommentOfStatus.getCommentOfStatusImageContentType()).isEqualTo(DEFAULT_COMMENT_OF_STATUS_IMAGE_CONTENT_TYPE);
        assertThat(testCommentOfStatus.getMediaType()).isEqualTo(DEFAULT_MEDIA_TYPE);
        assertThat(testCommentOfStatus.getDateOfCreation()).isEqualTo(DEFAULT_DATE_OF_CREATION);

        // Validate the CommentOfStatus in Elasticsearch
        verify(mockCommentOfStatusSearchRepository, times(1)).save(testCommentOfStatus);
    }

    @Test
    public void createCommentOfStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentOfStatusRepository.findAll().size();

        // Create the CommentOfStatus with an existing ID
        commentOfStatus.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentOfStatusMockMvc.perform(post("/api/comment-of-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentOfStatus)))
            .andExpect(status().isBadRequest());

        // Validate the CommentOfStatus in the database
        List<CommentOfStatus> commentOfStatusList = commentOfStatusRepository.findAll();
        assertThat(commentOfStatusList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommentOfStatus in Elasticsearch
        verify(mockCommentOfStatusSearchRepository, times(0)).save(commentOfStatus);
    }


    @Test
    public void checkBodyCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentOfStatusRepository.findAll().size();
        // set the field null
        commentOfStatus.setBodyComment(null);

        // Create the CommentOfStatus, which fails.

        restCommentOfStatusMockMvc.perform(post("/api/comment-of-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentOfStatus)))
            .andExpect(status().isBadRequest());

        List<CommentOfStatus> commentOfStatusList = commentOfStatusRepository.findAll();
        assertThat(commentOfStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllCommentOfStatuses() throws Exception {
        // Initialize the database
        commentOfStatusRepository.save(commentOfStatus);

        // Get all the commentOfStatusList
        restCommentOfStatusMockMvc.perform(get("/api/comment-of-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentOfStatus.getId())))
            .andExpect(jsonPath("$.[*].bodyComment").value(hasItem(DEFAULT_BODY_COMMENT)))
            .andExpect(jsonPath("$.[*].commentOfStatusImageContentType").value(hasItem(DEFAULT_COMMENT_OF_STATUS_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].commentOfStatusImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT_OF_STATUS_IMAGE))))
            .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE)))
            .andExpect(jsonPath("$.[*].dateOfCreation").value(hasItem(sameInstant(DEFAULT_DATE_OF_CREATION))));
    }
    
    @Test
    public void getCommentOfStatus() throws Exception {
        // Initialize the database
        commentOfStatusRepository.save(commentOfStatus);

        // Get the commentOfStatus
        restCommentOfStatusMockMvc.perform(get("/api/comment-of-statuses/{id}", commentOfStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commentOfStatus.getId()))
            .andExpect(jsonPath("$.bodyComment").value(DEFAULT_BODY_COMMENT))
            .andExpect(jsonPath("$.commentOfStatusImageContentType").value(DEFAULT_COMMENT_OF_STATUS_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.commentOfStatusImage").value(Base64Utils.encodeToString(DEFAULT_COMMENT_OF_STATUS_IMAGE)))
            .andExpect(jsonPath("$.mediaType").value(DEFAULT_MEDIA_TYPE))
            .andExpect(jsonPath("$.dateOfCreation").value(sameInstant(DEFAULT_DATE_OF_CREATION)));
    }

    @Test
    public void getNonExistingCommentOfStatus() throws Exception {
        // Get the commentOfStatus
        restCommentOfStatusMockMvc.perform(get("/api/comment-of-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCommentOfStatus() throws Exception {
        // Initialize the database
        commentOfStatusService.save(commentOfStatus);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCommentOfStatusSearchRepository);

        int databaseSizeBeforeUpdate = commentOfStatusRepository.findAll().size();

        // Update the commentOfStatus
        CommentOfStatus updatedCommentOfStatus = commentOfStatusRepository.findById(commentOfStatus.getId()).get();
        updatedCommentOfStatus
            .bodyComment(UPDATED_BODY_COMMENT)
            .commentOfStatusImage(UPDATED_COMMENT_OF_STATUS_IMAGE)
            .commentOfStatusImageContentType(UPDATED_COMMENT_OF_STATUS_IMAGE_CONTENT_TYPE)
            .mediaType(UPDATED_MEDIA_TYPE)
            .dateOfCreation(UPDATED_DATE_OF_CREATION);

        restCommentOfStatusMockMvc.perform(put("/api/comment-of-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommentOfStatus)))
            .andExpect(status().isOk());

        // Validate the CommentOfStatus in the database
        List<CommentOfStatus> commentOfStatusList = commentOfStatusRepository.findAll();
        assertThat(commentOfStatusList).hasSize(databaseSizeBeforeUpdate);
        CommentOfStatus testCommentOfStatus = commentOfStatusList.get(commentOfStatusList.size() - 1);
        assertThat(testCommentOfStatus.getBodyComment()).isEqualTo(UPDATED_BODY_COMMENT);
        assertThat(testCommentOfStatus.getCommentOfStatusImage()).isEqualTo(UPDATED_COMMENT_OF_STATUS_IMAGE);
        assertThat(testCommentOfStatus.getCommentOfStatusImageContentType()).isEqualTo(UPDATED_COMMENT_OF_STATUS_IMAGE_CONTENT_TYPE);
        assertThat(testCommentOfStatus.getMediaType()).isEqualTo(UPDATED_MEDIA_TYPE);
        assertThat(testCommentOfStatus.getDateOfCreation()).isEqualTo(UPDATED_DATE_OF_CREATION);

        // Validate the CommentOfStatus in Elasticsearch
        verify(mockCommentOfStatusSearchRepository, times(1)).save(testCommentOfStatus);
    }

    @Test
    public void updateNonExistingCommentOfStatus() throws Exception {
        int databaseSizeBeforeUpdate = commentOfStatusRepository.findAll().size();

        // Create the CommentOfStatus

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentOfStatusMockMvc.perform(put("/api/comment-of-statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentOfStatus)))
            .andExpect(status().isBadRequest());

        // Validate the CommentOfStatus in the database
        List<CommentOfStatus> commentOfStatusList = commentOfStatusRepository.findAll();
        assertThat(commentOfStatusList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommentOfStatus in Elasticsearch
        verify(mockCommentOfStatusSearchRepository, times(0)).save(commentOfStatus);
    }

    @Test
    public void deleteCommentOfStatus() throws Exception {
        // Initialize the database
        commentOfStatusService.save(commentOfStatus);

        int databaseSizeBeforeDelete = commentOfStatusRepository.findAll().size();

        // Delete the commentOfStatus
        restCommentOfStatusMockMvc.perform(delete("/api/comment-of-statuses/{id}", commentOfStatus.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommentOfStatus> commentOfStatusList = commentOfStatusRepository.findAll();
        assertThat(commentOfStatusList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommentOfStatus in Elasticsearch
        verify(mockCommentOfStatusSearchRepository, times(1)).deleteById(commentOfStatus.getId());
    }

    @Test
    public void searchCommentOfStatus() throws Exception {
        // Initialize the database
        commentOfStatusService.save(commentOfStatus);
        when(mockCommentOfStatusSearchRepository.search(queryStringQuery("id:" + commentOfStatus.getId())))
            .thenReturn(Collections.singletonList(commentOfStatus));
        // Search the commentOfStatus
        restCommentOfStatusMockMvc.perform(get("/api/_search/comment-of-statuses?query=id:" + commentOfStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentOfStatus.getId())))
            .andExpect(jsonPath("$.[*].bodyComment").value(hasItem(DEFAULT_BODY_COMMENT)))
            .andExpect(jsonPath("$.[*].commentOfStatusImageContentType").value(hasItem(DEFAULT_COMMENT_OF_STATUS_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].commentOfStatusImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT_OF_STATUS_IMAGE))))
            .andExpect(jsonPath("$.[*].mediaType").value(hasItem(DEFAULT_MEDIA_TYPE)))
            .andExpect(jsonPath("$.[*].dateOfCreation").value(hasItem(sameInstant(DEFAULT_DATE_OF_CREATION))));
    }
}
