package com.azgzaw.app.web.rest;

import com.azgzaw.app.AzgzawApp;
import com.azgzaw.app.domain.Spam;
import com.azgzaw.app.repository.SpamRepository;
import com.azgzaw.app.repository.search.SpamSearchRepository;
import com.azgzaw.app.service.SpamService;
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
 * Integration tests for the {@link SpamResource} REST controller.
 */
@SpringBootTest(classes = AzgzawApp.class)
public class SpamResourceIT {

    private static final String DEFAULT_CAUSE = "AAAAAAAAAA";
    private static final String UPDATED_CAUSE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATE_OF_CREATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_OF_CREATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private SpamRepository spamRepository;

    @Autowired
    private SpamService spamService;

    /**
     * This repository is mocked in the com.azgzaw.app.repository.search test package.
     *
     * @see com.azgzaw.app.repository.search.SpamSearchRepositoryMockConfiguration
     */
    @Autowired
    private SpamSearchRepository mockSpamSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restSpamMockMvc;

    private Spam spam;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SpamResource spamResource = new SpamResource(spamService);
        this.restSpamMockMvc = MockMvcBuilders.standaloneSetup(spamResource)
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
    public static Spam createEntity() {
        Spam spam = new Spam()
            .cause(DEFAULT_CAUSE)
            .dateOfCreation(DEFAULT_DATE_OF_CREATION);
        return spam;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Spam createUpdatedEntity() {
        Spam spam = new Spam()
            .cause(UPDATED_CAUSE)
            .dateOfCreation(UPDATED_DATE_OF_CREATION);
        return spam;
    }

    @BeforeEach
    public void initTest() {
        spamRepository.deleteAll();
        spam = createEntity();
    }

    @Test
    public void createSpam() throws Exception {
        int databaseSizeBeforeCreate = spamRepository.findAll().size();

        // Create the Spam
        restSpamMockMvc.perform(post("/api/spams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spam)))
            .andExpect(status().isCreated());

        // Validate the Spam in the database
        List<Spam> spamList = spamRepository.findAll();
        assertThat(spamList).hasSize(databaseSizeBeforeCreate + 1);
        Spam testSpam = spamList.get(spamList.size() - 1);
        assertThat(testSpam.getCause()).isEqualTo(DEFAULT_CAUSE);
        assertThat(testSpam.getDateOfCreation()).isEqualTo(DEFAULT_DATE_OF_CREATION);

        // Validate the Spam in Elasticsearch
        verify(mockSpamSearchRepository, times(1)).save(testSpam);
    }

    @Test
    public void createSpamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = spamRepository.findAll().size();

        // Create the Spam with an existing ID
        spam.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restSpamMockMvc.perform(post("/api/spams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spam)))
            .andExpect(status().isBadRequest());

        // Validate the Spam in the database
        List<Spam> spamList = spamRepository.findAll();
        assertThat(spamList).hasSize(databaseSizeBeforeCreate);

        // Validate the Spam in Elasticsearch
        verify(mockSpamSearchRepository, times(0)).save(spam);
    }


    @Test
    public void checkCauseIsRequired() throws Exception {
        int databaseSizeBeforeTest = spamRepository.findAll().size();
        // set the field null
        spam.setCause(null);

        // Create the Spam, which fails.

        restSpamMockMvc.perform(post("/api/spams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spam)))
            .andExpect(status().isBadRequest());

        List<Spam> spamList = spamRepository.findAll();
        assertThat(spamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateOfCreationIsRequired() throws Exception {
        int databaseSizeBeforeTest = spamRepository.findAll().size();
        // set the field null
        spam.setDateOfCreation(null);

        // Create the Spam, which fails.

        restSpamMockMvc.perform(post("/api/spams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spam)))
            .andExpect(status().isBadRequest());

        List<Spam> spamList = spamRepository.findAll();
        assertThat(spamList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllSpams() throws Exception {
        // Initialize the database
        spamRepository.save(spam);

        // Get all the spamList
        restSpamMockMvc.perform(get("/api/spams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spam.getId())))
            .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE)))
            .andExpect(jsonPath("$.[*].dateOfCreation").value(hasItem(sameInstant(DEFAULT_DATE_OF_CREATION))));
    }
    
    @Test
    public void getSpam() throws Exception {
        // Initialize the database
        spamRepository.save(spam);

        // Get the spam
        restSpamMockMvc.perform(get("/api/spams/{id}", spam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(spam.getId()))
            .andExpect(jsonPath("$.cause").value(DEFAULT_CAUSE))
            .andExpect(jsonPath("$.dateOfCreation").value(sameInstant(DEFAULT_DATE_OF_CREATION)));
    }

    @Test
    public void getNonExistingSpam() throws Exception {
        // Get the spam
        restSpamMockMvc.perform(get("/api/spams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateSpam() throws Exception {
        // Initialize the database
        spamService.save(spam);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockSpamSearchRepository);

        int databaseSizeBeforeUpdate = spamRepository.findAll().size();

        // Update the spam
        Spam updatedSpam = spamRepository.findById(spam.getId()).get();
        updatedSpam
            .cause(UPDATED_CAUSE)
            .dateOfCreation(UPDATED_DATE_OF_CREATION);

        restSpamMockMvc.perform(put("/api/spams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSpam)))
            .andExpect(status().isOk());

        // Validate the Spam in the database
        List<Spam> spamList = spamRepository.findAll();
        assertThat(spamList).hasSize(databaseSizeBeforeUpdate);
        Spam testSpam = spamList.get(spamList.size() - 1);
        assertThat(testSpam.getCause()).isEqualTo(UPDATED_CAUSE);
        assertThat(testSpam.getDateOfCreation()).isEqualTo(UPDATED_DATE_OF_CREATION);

        // Validate the Spam in Elasticsearch
        verify(mockSpamSearchRepository, times(1)).save(testSpam);
    }

    @Test
    public void updateNonExistingSpam() throws Exception {
        int databaseSizeBeforeUpdate = spamRepository.findAll().size();

        // Create the Spam

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSpamMockMvc.perform(put("/api/spams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(spam)))
            .andExpect(status().isBadRequest());

        // Validate the Spam in the database
        List<Spam> spamList = spamRepository.findAll();
        assertThat(spamList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Spam in Elasticsearch
        verify(mockSpamSearchRepository, times(0)).save(spam);
    }

    @Test
    public void deleteSpam() throws Exception {
        // Initialize the database
        spamService.save(spam);

        int databaseSizeBeforeDelete = spamRepository.findAll().size();

        // Delete the spam
        restSpamMockMvc.perform(delete("/api/spams/{id}", spam.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Spam> spamList = spamRepository.findAll();
        assertThat(spamList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Spam in Elasticsearch
        verify(mockSpamSearchRepository, times(1)).deleteById(spam.getId());
    }

    @Test
    public void searchSpam() throws Exception {
        // Initialize the database
        spamService.save(spam);
        when(mockSpamSearchRepository.search(queryStringQuery("id:" + spam.getId())))
            .thenReturn(Collections.singletonList(spam));
        // Search the spam
        restSpamMockMvc.perform(get("/api/_search/spams?query=id:" + spam.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(spam.getId())))
            .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE)))
            .andExpect(jsonPath("$.[*].dateOfCreation").value(hasItem(sameInstant(DEFAULT_DATE_OF_CREATION))));
    }
}
