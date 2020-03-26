package com.azgzaw.app.web.rest;

import com.azgzaw.app.AzgzawApp;
import com.azgzaw.app.domain.CommentOfCitation;
import com.azgzaw.app.repository.CommentOfCitationRepository;
import com.azgzaw.app.repository.search.CommentOfCitationSearchRepository;
import com.azgzaw.app.service.CommentOfCitationService;
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
 * Integration tests for the {@link CommentOfCitationResource} REST controller.
 */
@SpringBootTest(classes = AzgzawApp.class)
public class CommentOfCitationResourceIT {

    private static final String DEFAULT_BODY_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_BODY_COMMENT = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COMMENT_OF_CITATION_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMMENT_OF_CITATION_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COMMENT_OF_CITATION_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMMENT_OF_CITATION_IMAGE_CONTENT_TYPE = "image/png";

    private static final ZonedDateTime DEFAULT_DATE_OF_CREATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_OF_CREATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CommentOfCitationRepository commentOfCitationRepository;

    @Autowired
    private CommentOfCitationService commentOfCitationService;

    /**
     * This repository is mocked in the com.azgzaw.app.repository.search test package.
     *
     * @see com.azgzaw.app.repository.search.CommentOfCitationSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommentOfCitationSearchRepository mockCommentOfCitationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCommentOfCitationMockMvc;

    private CommentOfCitation commentOfCitation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommentOfCitationResource commentOfCitationResource = new CommentOfCitationResource(commentOfCitationService);
        this.restCommentOfCitationMockMvc = MockMvcBuilders.standaloneSetup(commentOfCitationResource)
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
    public static CommentOfCitation createEntity() {
        CommentOfCitation commentOfCitation = new CommentOfCitation()
            .bodyComment(DEFAULT_BODY_COMMENT)
            .commentOfCitationImage(DEFAULT_COMMENT_OF_CITATION_IMAGE)
            .commentOfCitationImageContentType(DEFAULT_COMMENT_OF_CITATION_IMAGE_CONTENT_TYPE)
            .dateOfCreation(DEFAULT_DATE_OF_CREATION);
        return commentOfCitation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommentOfCitation createUpdatedEntity() {
        CommentOfCitation commentOfCitation = new CommentOfCitation()
            .bodyComment(UPDATED_BODY_COMMENT)
            .commentOfCitationImage(UPDATED_COMMENT_OF_CITATION_IMAGE)
            .commentOfCitationImageContentType(UPDATED_COMMENT_OF_CITATION_IMAGE_CONTENT_TYPE)
            .dateOfCreation(UPDATED_DATE_OF_CREATION);
        return commentOfCitation;
    }

    @BeforeEach
    public void initTest() {
        commentOfCitationRepository.deleteAll();
        commentOfCitation = createEntity();
    }

    @Test
    public void createCommentOfCitation() throws Exception {
        int databaseSizeBeforeCreate = commentOfCitationRepository.findAll().size();

        // Create the CommentOfCitation
        restCommentOfCitationMockMvc.perform(post("/api/comment-of-citations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentOfCitation)))
            .andExpect(status().isCreated());

        // Validate the CommentOfCitation in the database
        List<CommentOfCitation> commentOfCitationList = commentOfCitationRepository.findAll();
        assertThat(commentOfCitationList).hasSize(databaseSizeBeforeCreate + 1);
        CommentOfCitation testCommentOfCitation = commentOfCitationList.get(commentOfCitationList.size() - 1);
        assertThat(testCommentOfCitation.getBodyComment()).isEqualTo(DEFAULT_BODY_COMMENT);
        assertThat(testCommentOfCitation.getCommentOfCitationImage()).isEqualTo(DEFAULT_COMMENT_OF_CITATION_IMAGE);
        assertThat(testCommentOfCitation.getCommentOfCitationImageContentType()).isEqualTo(DEFAULT_COMMENT_OF_CITATION_IMAGE_CONTENT_TYPE);
        assertThat(testCommentOfCitation.getDateOfCreation()).isEqualTo(DEFAULT_DATE_OF_CREATION);

        // Validate the CommentOfCitation in Elasticsearch
        verify(mockCommentOfCitationSearchRepository, times(1)).save(testCommentOfCitation);
    }

    @Test
    public void createCommentOfCitationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentOfCitationRepository.findAll().size();

        // Create the CommentOfCitation with an existing ID
        commentOfCitation.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentOfCitationMockMvc.perform(post("/api/comment-of-citations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentOfCitation)))
            .andExpect(status().isBadRequest());

        // Validate the CommentOfCitation in the database
        List<CommentOfCitation> commentOfCitationList = commentOfCitationRepository.findAll();
        assertThat(commentOfCitationList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommentOfCitation in Elasticsearch
        verify(mockCommentOfCitationSearchRepository, times(0)).save(commentOfCitation);
    }


    @Test
    public void checkBodyCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentOfCitationRepository.findAll().size();
        // set the field null
        commentOfCitation.setBodyComment(null);

        // Create the CommentOfCitation, which fails.

        restCommentOfCitationMockMvc.perform(post("/api/comment-of-citations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentOfCitation)))
            .andExpect(status().isBadRequest());

        List<CommentOfCitation> commentOfCitationList = commentOfCitationRepository.findAll();
        assertThat(commentOfCitationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllCommentOfCitations() throws Exception {
        // Initialize the database
        commentOfCitationRepository.save(commentOfCitation);

        // Get all the commentOfCitationList
        restCommentOfCitationMockMvc.perform(get("/api/comment-of-citations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentOfCitation.getId())))
            .andExpect(jsonPath("$.[*].bodyComment").value(hasItem(DEFAULT_BODY_COMMENT)))
            .andExpect(jsonPath("$.[*].commentOfCitationImageContentType").value(hasItem(DEFAULT_COMMENT_OF_CITATION_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].commentOfCitationImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT_OF_CITATION_IMAGE))))
            .andExpect(jsonPath("$.[*].dateOfCreation").value(hasItem(sameInstant(DEFAULT_DATE_OF_CREATION))));
    }
    
    @Test
    public void getCommentOfCitation() throws Exception {
        // Initialize the database
        commentOfCitationRepository.save(commentOfCitation);

        // Get the commentOfCitation
        restCommentOfCitationMockMvc.perform(get("/api/comment-of-citations/{id}", commentOfCitation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commentOfCitation.getId()))
            .andExpect(jsonPath("$.bodyComment").value(DEFAULT_BODY_COMMENT))
            .andExpect(jsonPath("$.commentOfCitationImageContentType").value(DEFAULT_COMMENT_OF_CITATION_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.commentOfCitationImage").value(Base64Utils.encodeToString(DEFAULT_COMMENT_OF_CITATION_IMAGE)))
            .andExpect(jsonPath("$.dateOfCreation").value(sameInstant(DEFAULT_DATE_OF_CREATION)));
    }

    @Test
    public void getNonExistingCommentOfCitation() throws Exception {
        // Get the commentOfCitation
        restCommentOfCitationMockMvc.perform(get("/api/comment-of-citations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCommentOfCitation() throws Exception {
        // Initialize the database
        commentOfCitationService.save(commentOfCitation);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCommentOfCitationSearchRepository);

        int databaseSizeBeforeUpdate = commentOfCitationRepository.findAll().size();

        // Update the commentOfCitation
        CommentOfCitation updatedCommentOfCitation = commentOfCitationRepository.findById(commentOfCitation.getId()).get();
        updatedCommentOfCitation
            .bodyComment(UPDATED_BODY_COMMENT)
            .commentOfCitationImage(UPDATED_COMMENT_OF_CITATION_IMAGE)
            .commentOfCitationImageContentType(UPDATED_COMMENT_OF_CITATION_IMAGE_CONTENT_TYPE)
            .dateOfCreation(UPDATED_DATE_OF_CREATION);

        restCommentOfCitationMockMvc.perform(put("/api/comment-of-citations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommentOfCitation)))
            .andExpect(status().isOk());

        // Validate the CommentOfCitation in the database
        List<CommentOfCitation> commentOfCitationList = commentOfCitationRepository.findAll();
        assertThat(commentOfCitationList).hasSize(databaseSizeBeforeUpdate);
        CommentOfCitation testCommentOfCitation = commentOfCitationList.get(commentOfCitationList.size() - 1);
        assertThat(testCommentOfCitation.getBodyComment()).isEqualTo(UPDATED_BODY_COMMENT);
        assertThat(testCommentOfCitation.getCommentOfCitationImage()).isEqualTo(UPDATED_COMMENT_OF_CITATION_IMAGE);
        assertThat(testCommentOfCitation.getCommentOfCitationImageContentType()).isEqualTo(UPDATED_COMMENT_OF_CITATION_IMAGE_CONTENT_TYPE);
        assertThat(testCommentOfCitation.getDateOfCreation()).isEqualTo(UPDATED_DATE_OF_CREATION);

        // Validate the CommentOfCitation in Elasticsearch
        verify(mockCommentOfCitationSearchRepository, times(1)).save(testCommentOfCitation);
    }

    @Test
    public void updateNonExistingCommentOfCitation() throws Exception {
        int databaseSizeBeforeUpdate = commentOfCitationRepository.findAll().size();

        // Create the CommentOfCitation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentOfCitationMockMvc.perform(put("/api/comment-of-citations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentOfCitation)))
            .andExpect(status().isBadRequest());

        // Validate the CommentOfCitation in the database
        List<CommentOfCitation> commentOfCitationList = commentOfCitationRepository.findAll();
        assertThat(commentOfCitationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommentOfCitation in Elasticsearch
        verify(mockCommentOfCitationSearchRepository, times(0)).save(commentOfCitation);
    }

    @Test
    public void deleteCommentOfCitation() throws Exception {
        // Initialize the database
        commentOfCitationService.save(commentOfCitation);

        int databaseSizeBeforeDelete = commentOfCitationRepository.findAll().size();

        // Delete the commentOfCitation
        restCommentOfCitationMockMvc.perform(delete("/api/comment-of-citations/{id}", commentOfCitation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommentOfCitation> commentOfCitationList = commentOfCitationRepository.findAll();
        assertThat(commentOfCitationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommentOfCitation in Elasticsearch
        verify(mockCommentOfCitationSearchRepository, times(1)).deleteById(commentOfCitation.getId());
    }

    @Test
    public void searchCommentOfCitation() throws Exception {
        // Initialize the database
        commentOfCitationService.save(commentOfCitation);
        when(mockCommentOfCitationSearchRepository.search(queryStringQuery("id:" + commentOfCitation.getId())))
            .thenReturn(Collections.singletonList(commentOfCitation));
        // Search the commentOfCitation
        restCommentOfCitationMockMvc.perform(get("/api/_search/comment-of-citations?query=id:" + commentOfCitation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentOfCitation.getId())))
            .andExpect(jsonPath("$.[*].bodyComment").value(hasItem(DEFAULT_BODY_COMMENT)))
            .andExpect(jsonPath("$.[*].commentOfCitationImageContentType").value(hasItem(DEFAULT_COMMENT_OF_CITATION_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].commentOfCitationImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT_OF_CITATION_IMAGE))))
            .andExpect(jsonPath("$.[*].dateOfCreation").value(hasItem(sameInstant(DEFAULT_DATE_OF_CREATION))));
    }
}
