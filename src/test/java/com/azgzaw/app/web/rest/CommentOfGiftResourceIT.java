package com.azgzaw.app.web.rest;

import com.azgzaw.app.AzgzawApp;
import com.azgzaw.app.domain.CommentOfGift;
import com.azgzaw.app.repository.CommentOfGiftRepository;
import com.azgzaw.app.repository.search.CommentOfGiftSearchRepository;
import com.azgzaw.app.service.CommentOfGiftService;
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
 * Integration tests for the {@link CommentOfGiftResource} REST controller.
 */
@SpringBootTest(classes = AzgzawApp.class)
public class CommentOfGiftResourceIT {

    private static final String DEFAULT_BODY_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_BODY_COMMENT = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COMMENT_OF_GIFT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMMENT_OF_GIFT_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COMMENT_OF_GIFT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMMENT_OF_GIFT_IMAGE_CONTENT_TYPE = "image/png";

    private static final ZonedDateTime DEFAULT_DATE_OF_CREATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_OF_CREATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private CommentOfGiftRepository commentOfGiftRepository;

    @Autowired
    private CommentOfGiftService commentOfGiftService;

    /**
     * This repository is mocked in the com.azgzaw.app.repository.search test package.
     *
     * @see com.azgzaw.app.repository.search.CommentOfGiftSearchRepositoryMockConfiguration
     */
    @Autowired
    private CommentOfGiftSearchRepository mockCommentOfGiftSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCommentOfGiftMockMvc;

    private CommentOfGift commentOfGift;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CommentOfGiftResource commentOfGiftResource = new CommentOfGiftResource(commentOfGiftService);
        this.restCommentOfGiftMockMvc = MockMvcBuilders.standaloneSetup(commentOfGiftResource)
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
    public static CommentOfGift createEntity() {
        CommentOfGift commentOfGift = new CommentOfGift()
            .bodyComment(DEFAULT_BODY_COMMENT)
            .commentOfGiftImage(DEFAULT_COMMENT_OF_GIFT_IMAGE)
            .commentOfGiftImageContentType(DEFAULT_COMMENT_OF_GIFT_IMAGE_CONTENT_TYPE)
            .dateOfCreation(DEFAULT_DATE_OF_CREATION);
        return commentOfGift;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CommentOfGift createUpdatedEntity() {
        CommentOfGift commentOfGift = new CommentOfGift()
            .bodyComment(UPDATED_BODY_COMMENT)
            .commentOfGiftImage(UPDATED_COMMENT_OF_GIFT_IMAGE)
            .commentOfGiftImageContentType(UPDATED_COMMENT_OF_GIFT_IMAGE_CONTENT_TYPE)
            .dateOfCreation(UPDATED_DATE_OF_CREATION);
        return commentOfGift;
    }

    @BeforeEach
    public void initTest() {
        commentOfGiftRepository.deleteAll();
        commentOfGift = createEntity();
    }

    @Test
    public void createCommentOfGift() throws Exception {
        int databaseSizeBeforeCreate = commentOfGiftRepository.findAll().size();

        // Create the CommentOfGift
        restCommentOfGiftMockMvc.perform(post("/api/comment-of-gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentOfGift)))
            .andExpect(status().isCreated());

        // Validate the CommentOfGift in the database
        List<CommentOfGift> commentOfGiftList = commentOfGiftRepository.findAll();
        assertThat(commentOfGiftList).hasSize(databaseSizeBeforeCreate + 1);
        CommentOfGift testCommentOfGift = commentOfGiftList.get(commentOfGiftList.size() - 1);
        assertThat(testCommentOfGift.getBodyComment()).isEqualTo(DEFAULT_BODY_COMMENT);
        assertThat(testCommentOfGift.getCommentOfGiftImage()).isEqualTo(DEFAULT_COMMENT_OF_GIFT_IMAGE);
        assertThat(testCommentOfGift.getCommentOfGiftImageContentType()).isEqualTo(DEFAULT_COMMENT_OF_GIFT_IMAGE_CONTENT_TYPE);
        assertThat(testCommentOfGift.getDateOfCreation()).isEqualTo(DEFAULT_DATE_OF_CREATION);

        // Validate the CommentOfGift in Elasticsearch
        verify(mockCommentOfGiftSearchRepository, times(1)).save(testCommentOfGift);
    }

    @Test
    public void createCommentOfGiftWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = commentOfGiftRepository.findAll().size();

        // Create the CommentOfGift with an existing ID
        commentOfGift.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCommentOfGiftMockMvc.perform(post("/api/comment-of-gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentOfGift)))
            .andExpect(status().isBadRequest());

        // Validate the CommentOfGift in the database
        List<CommentOfGift> commentOfGiftList = commentOfGiftRepository.findAll();
        assertThat(commentOfGiftList).hasSize(databaseSizeBeforeCreate);

        // Validate the CommentOfGift in Elasticsearch
        verify(mockCommentOfGiftSearchRepository, times(0)).save(commentOfGift);
    }


    @Test
    public void checkBodyCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = commentOfGiftRepository.findAll().size();
        // set the field null
        commentOfGift.setBodyComment(null);

        // Create the CommentOfGift, which fails.

        restCommentOfGiftMockMvc.perform(post("/api/comment-of-gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentOfGift)))
            .andExpect(status().isBadRequest());

        List<CommentOfGift> commentOfGiftList = commentOfGiftRepository.findAll();
        assertThat(commentOfGiftList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllCommentOfGifts() throws Exception {
        // Initialize the database
        commentOfGiftRepository.save(commentOfGift);

        // Get all the commentOfGiftList
        restCommentOfGiftMockMvc.perform(get("/api/comment-of-gifts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentOfGift.getId())))
            .andExpect(jsonPath("$.[*].bodyComment").value(hasItem(DEFAULT_BODY_COMMENT)))
            .andExpect(jsonPath("$.[*].commentOfGiftImageContentType").value(hasItem(DEFAULT_COMMENT_OF_GIFT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].commentOfGiftImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT_OF_GIFT_IMAGE))))
            .andExpect(jsonPath("$.[*].dateOfCreation").value(hasItem(sameInstant(DEFAULT_DATE_OF_CREATION))));
    }
    
    @Test
    public void getCommentOfGift() throws Exception {
        // Initialize the database
        commentOfGiftRepository.save(commentOfGift);

        // Get the commentOfGift
        restCommentOfGiftMockMvc.perform(get("/api/comment-of-gifts/{id}", commentOfGift.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(commentOfGift.getId()))
            .andExpect(jsonPath("$.bodyComment").value(DEFAULT_BODY_COMMENT))
            .andExpect(jsonPath("$.commentOfGiftImageContentType").value(DEFAULT_COMMENT_OF_GIFT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.commentOfGiftImage").value(Base64Utils.encodeToString(DEFAULT_COMMENT_OF_GIFT_IMAGE)))
            .andExpect(jsonPath("$.dateOfCreation").value(sameInstant(DEFAULT_DATE_OF_CREATION)));
    }

    @Test
    public void getNonExistingCommentOfGift() throws Exception {
        // Get the commentOfGift
        restCommentOfGiftMockMvc.perform(get("/api/comment-of-gifts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCommentOfGift() throws Exception {
        // Initialize the database
        commentOfGiftService.save(commentOfGift);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCommentOfGiftSearchRepository);

        int databaseSizeBeforeUpdate = commentOfGiftRepository.findAll().size();

        // Update the commentOfGift
        CommentOfGift updatedCommentOfGift = commentOfGiftRepository.findById(commentOfGift.getId()).get();
        updatedCommentOfGift
            .bodyComment(UPDATED_BODY_COMMENT)
            .commentOfGiftImage(UPDATED_COMMENT_OF_GIFT_IMAGE)
            .commentOfGiftImageContentType(UPDATED_COMMENT_OF_GIFT_IMAGE_CONTENT_TYPE)
            .dateOfCreation(UPDATED_DATE_OF_CREATION);

        restCommentOfGiftMockMvc.perform(put("/api/comment-of-gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCommentOfGift)))
            .andExpect(status().isOk());

        // Validate the CommentOfGift in the database
        List<CommentOfGift> commentOfGiftList = commentOfGiftRepository.findAll();
        assertThat(commentOfGiftList).hasSize(databaseSizeBeforeUpdate);
        CommentOfGift testCommentOfGift = commentOfGiftList.get(commentOfGiftList.size() - 1);
        assertThat(testCommentOfGift.getBodyComment()).isEqualTo(UPDATED_BODY_COMMENT);
        assertThat(testCommentOfGift.getCommentOfGiftImage()).isEqualTo(UPDATED_COMMENT_OF_GIFT_IMAGE);
        assertThat(testCommentOfGift.getCommentOfGiftImageContentType()).isEqualTo(UPDATED_COMMENT_OF_GIFT_IMAGE_CONTENT_TYPE);
        assertThat(testCommentOfGift.getDateOfCreation()).isEqualTo(UPDATED_DATE_OF_CREATION);

        // Validate the CommentOfGift in Elasticsearch
        verify(mockCommentOfGiftSearchRepository, times(1)).save(testCommentOfGift);
    }

    @Test
    public void updateNonExistingCommentOfGift() throws Exception {
        int databaseSizeBeforeUpdate = commentOfGiftRepository.findAll().size();

        // Create the CommentOfGift

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCommentOfGiftMockMvc.perform(put("/api/comment-of-gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(commentOfGift)))
            .andExpect(status().isBadRequest());

        // Validate the CommentOfGift in the database
        List<CommentOfGift> commentOfGiftList = commentOfGiftRepository.findAll();
        assertThat(commentOfGiftList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CommentOfGift in Elasticsearch
        verify(mockCommentOfGiftSearchRepository, times(0)).save(commentOfGift);
    }

    @Test
    public void deleteCommentOfGift() throws Exception {
        // Initialize the database
        commentOfGiftService.save(commentOfGift);

        int databaseSizeBeforeDelete = commentOfGiftRepository.findAll().size();

        // Delete the commentOfGift
        restCommentOfGiftMockMvc.perform(delete("/api/comment-of-gifts/{id}", commentOfGift.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CommentOfGift> commentOfGiftList = commentOfGiftRepository.findAll();
        assertThat(commentOfGiftList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CommentOfGift in Elasticsearch
        verify(mockCommentOfGiftSearchRepository, times(1)).deleteById(commentOfGift.getId());
    }

    @Test
    public void searchCommentOfGift() throws Exception {
        // Initialize the database
        commentOfGiftService.save(commentOfGift);
        when(mockCommentOfGiftSearchRepository.search(queryStringQuery("id:" + commentOfGift.getId())))
            .thenReturn(Collections.singletonList(commentOfGift));
        // Search the commentOfGift
        restCommentOfGiftMockMvc.perform(get("/api/_search/comment-of-gifts?query=id:" + commentOfGift.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(commentOfGift.getId())))
            .andExpect(jsonPath("$.[*].bodyComment").value(hasItem(DEFAULT_BODY_COMMENT)))
            .andExpect(jsonPath("$.[*].commentOfGiftImageContentType").value(hasItem(DEFAULT_COMMENT_OF_GIFT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].commentOfGiftImage").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT_OF_GIFT_IMAGE))))
            .andExpect(jsonPath("$.[*].dateOfCreation").value(hasItem(sameInstant(DEFAULT_DATE_OF_CREATION))));
    }
}
