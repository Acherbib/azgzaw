package com.azgzaw.app.web.rest;

import com.azgzaw.app.AzgzawApp;
import com.azgzaw.app.domain.Citation;
import com.azgzaw.app.repository.CitationRepository;
import com.azgzaw.app.repository.search.CitationSearchRepository;
import com.azgzaw.app.service.CitationService;
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

import com.azgzaw.app.domain.enumeration.Privacy;
/**
 * Integration tests for the {@link CitationResource} REST controller.
 */
@SpringBootTest(classes = AzgzawApp.class)
public class CitationResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_GREEN_CITATION_MEDIA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_GREEN_CITATION_MEDIA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_GREEN_CITATION_MEDIA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_GREEN_CITATION_MEDIA_CONTENT_TYPE = "image/png";

    private static final ZonedDateTime DEFAULT_DATE_OF_CREATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_OF_CREATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_BACKGROUND_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND_COLOR = "BBBBBBBBBB";

    private static final Privacy DEFAULT_GREEN_CITATION_PRIVACY = Privacy.PUBLIC;
    private static final Privacy UPDATED_GREEN_CITATION_PRIVACY = Privacy.ONLYME;

    @Autowired
    private CitationRepository citationRepository;

    @Autowired
    private CitationService citationService;

    /**
     * This repository is mocked in the com.azgzaw.app.repository.search test package.
     *
     * @see com.azgzaw.app.repository.search.CitationSearchRepositoryMockConfiguration
     */
    @Autowired
    private CitationSearchRepository mockCitationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restCitationMockMvc;

    private Citation citation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CitationResource citationResource = new CitationResource(citationService);
        this.restCitationMockMvc = MockMvcBuilders.standaloneSetup(citationResource)
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
    public static Citation createEntity() {
        Citation citation = new Citation()
            .description(DEFAULT_DESCRIPTION)
            .greenCitationMedia(DEFAULT_GREEN_CITATION_MEDIA)
            .greenCitationMediaContentType(DEFAULT_GREEN_CITATION_MEDIA_CONTENT_TYPE)
            .dateOfCreation(DEFAULT_DATE_OF_CREATION)
            .backgroundColor(DEFAULT_BACKGROUND_COLOR)
            .greenCitationPrivacy(DEFAULT_GREEN_CITATION_PRIVACY);
        return citation;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Citation createUpdatedEntity() {
        Citation citation = new Citation()
            .description(UPDATED_DESCRIPTION)
            .greenCitationMedia(UPDATED_GREEN_CITATION_MEDIA)
            .greenCitationMediaContentType(UPDATED_GREEN_CITATION_MEDIA_CONTENT_TYPE)
            .dateOfCreation(UPDATED_DATE_OF_CREATION)
            .backgroundColor(UPDATED_BACKGROUND_COLOR)
            .greenCitationPrivacy(UPDATED_GREEN_CITATION_PRIVACY);
        return citation;
    }

    @BeforeEach
    public void initTest() {
        citationRepository.deleteAll();
        citation = createEntity();
    }

    @Test
    public void createCitation() throws Exception {
        int databaseSizeBeforeCreate = citationRepository.findAll().size();

        // Create the Citation
        restCitationMockMvc.perform(post("/api/citations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citation)))
            .andExpect(status().isCreated());

        // Validate the Citation in the database
        List<Citation> citationList = citationRepository.findAll();
        assertThat(citationList).hasSize(databaseSizeBeforeCreate + 1);
        Citation testCitation = citationList.get(citationList.size() - 1);
        assertThat(testCitation.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCitation.getGreenCitationMedia()).isEqualTo(DEFAULT_GREEN_CITATION_MEDIA);
        assertThat(testCitation.getGreenCitationMediaContentType()).isEqualTo(DEFAULT_GREEN_CITATION_MEDIA_CONTENT_TYPE);
        assertThat(testCitation.getDateOfCreation()).isEqualTo(DEFAULT_DATE_OF_CREATION);
        assertThat(testCitation.getBackgroundColor()).isEqualTo(DEFAULT_BACKGROUND_COLOR);
        assertThat(testCitation.getGreenCitationPrivacy()).isEqualTo(DEFAULT_GREEN_CITATION_PRIVACY);

        // Validate the Citation in Elasticsearch
        verify(mockCitationSearchRepository, times(1)).save(testCitation);
    }

    @Test
    public void createCitationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = citationRepository.findAll().size();

        // Create the Citation with an existing ID
        citation.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitationMockMvc.perform(post("/api/citations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citation)))
            .andExpect(status().isBadRequest());

        // Validate the Citation in the database
        List<Citation> citationList = citationRepository.findAll();
        assertThat(citationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Citation in Elasticsearch
        verify(mockCitationSearchRepository, times(0)).save(citation);
    }


    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = citationRepository.findAll().size();
        // set the field null
        citation.setDescription(null);

        // Create the Citation, which fails.

        restCitationMockMvc.perform(post("/api/citations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citation)))
            .andExpect(status().isBadRequest());

        List<Citation> citationList = citationRepository.findAll();
        assertThat(citationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDateOfCreationIsRequired() throws Exception {
        int databaseSizeBeforeTest = citationRepository.findAll().size();
        // set the field null
        citation.setDateOfCreation(null);

        // Create the Citation, which fails.

        restCitationMockMvc.perform(post("/api/citations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citation)))
            .andExpect(status().isBadRequest());

        List<Citation> citationList = citationRepository.findAll();
        assertThat(citationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkGreenCitationPrivacyIsRequired() throws Exception {
        int databaseSizeBeforeTest = citationRepository.findAll().size();
        // set the field null
        citation.setGreenCitationPrivacy(null);

        // Create the Citation, which fails.

        restCitationMockMvc.perform(post("/api/citations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citation)))
            .andExpect(status().isBadRequest());

        List<Citation> citationList = citationRepository.findAll();
        assertThat(citationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllCitations() throws Exception {
        // Initialize the database
        citationRepository.save(citation);

        // Get all the citationList
        restCitationMockMvc.perform(get("/api/citations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citation.getId())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].greenCitationMediaContentType").value(hasItem(DEFAULT_GREEN_CITATION_MEDIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].greenCitationMedia").value(hasItem(Base64Utils.encodeToString(DEFAULT_GREEN_CITATION_MEDIA))))
            .andExpect(jsonPath("$.[*].dateOfCreation").value(hasItem(sameInstant(DEFAULT_DATE_OF_CREATION))))
            .andExpect(jsonPath("$.[*].backgroundColor").value(hasItem(DEFAULT_BACKGROUND_COLOR)))
            .andExpect(jsonPath("$.[*].greenCitationPrivacy").value(hasItem(DEFAULT_GREEN_CITATION_PRIVACY.toString())));
    }
    
    @Test
    public void getCitation() throws Exception {
        // Initialize the database
        citationRepository.save(citation);

        // Get the citation
        restCitationMockMvc.perform(get("/api/citations/{id}", citation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(citation.getId()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.greenCitationMediaContentType").value(DEFAULT_GREEN_CITATION_MEDIA_CONTENT_TYPE))
            .andExpect(jsonPath("$.greenCitationMedia").value(Base64Utils.encodeToString(DEFAULT_GREEN_CITATION_MEDIA)))
            .andExpect(jsonPath("$.dateOfCreation").value(sameInstant(DEFAULT_DATE_OF_CREATION)))
            .andExpect(jsonPath("$.backgroundColor").value(DEFAULT_BACKGROUND_COLOR))
            .andExpect(jsonPath("$.greenCitationPrivacy").value(DEFAULT_GREEN_CITATION_PRIVACY.toString()));
    }

    @Test
    public void getNonExistingCitation() throws Exception {
        // Get the citation
        restCitationMockMvc.perform(get("/api/citations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateCitation() throws Exception {
        // Initialize the database
        citationService.save(citation);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockCitationSearchRepository);

        int databaseSizeBeforeUpdate = citationRepository.findAll().size();

        // Update the citation
        Citation updatedCitation = citationRepository.findById(citation.getId()).get();
        updatedCitation
            .description(UPDATED_DESCRIPTION)
            .greenCitationMedia(UPDATED_GREEN_CITATION_MEDIA)
            .greenCitationMediaContentType(UPDATED_GREEN_CITATION_MEDIA_CONTENT_TYPE)
            .dateOfCreation(UPDATED_DATE_OF_CREATION)
            .backgroundColor(UPDATED_BACKGROUND_COLOR)
            .greenCitationPrivacy(UPDATED_GREEN_CITATION_PRIVACY);

        restCitationMockMvc.perform(put("/api/citations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCitation)))
            .andExpect(status().isOk());

        // Validate the Citation in the database
        List<Citation> citationList = citationRepository.findAll();
        assertThat(citationList).hasSize(databaseSizeBeforeUpdate);
        Citation testCitation = citationList.get(citationList.size() - 1);
        assertThat(testCitation.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCitation.getGreenCitationMedia()).isEqualTo(UPDATED_GREEN_CITATION_MEDIA);
        assertThat(testCitation.getGreenCitationMediaContentType()).isEqualTo(UPDATED_GREEN_CITATION_MEDIA_CONTENT_TYPE);
        assertThat(testCitation.getDateOfCreation()).isEqualTo(UPDATED_DATE_OF_CREATION);
        assertThat(testCitation.getBackgroundColor()).isEqualTo(UPDATED_BACKGROUND_COLOR);
        assertThat(testCitation.getGreenCitationPrivacy()).isEqualTo(UPDATED_GREEN_CITATION_PRIVACY);

        // Validate the Citation in Elasticsearch
        verify(mockCitationSearchRepository, times(1)).save(testCitation);
    }

    @Test
    public void updateNonExistingCitation() throws Exception {
        int databaseSizeBeforeUpdate = citationRepository.findAll().size();

        // Create the Citation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitationMockMvc.perform(put("/api/citations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citation)))
            .andExpect(status().isBadRequest());

        // Validate the Citation in the database
        List<Citation> citationList = citationRepository.findAll();
        assertThat(citationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Citation in Elasticsearch
        verify(mockCitationSearchRepository, times(0)).save(citation);
    }

    @Test
    public void deleteCitation() throws Exception {
        // Initialize the database
        citationService.save(citation);

        int databaseSizeBeforeDelete = citationRepository.findAll().size();

        // Delete the citation
        restCitationMockMvc.perform(delete("/api/citations/{id}", citation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Citation> citationList = citationRepository.findAll();
        assertThat(citationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Citation in Elasticsearch
        verify(mockCitationSearchRepository, times(1)).deleteById(citation.getId());
    }

    @Test
    public void searchCitation() throws Exception {
        // Initialize the database
        citationService.save(citation);
        when(mockCitationSearchRepository.search(queryStringQuery("id:" + citation.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(citation), PageRequest.of(0, 1), 1));
        // Search the citation
        restCitationMockMvc.perform(get("/api/_search/citations?query=id:" + citation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citation.getId())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].greenCitationMediaContentType").value(hasItem(DEFAULT_GREEN_CITATION_MEDIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].greenCitationMedia").value(hasItem(Base64Utils.encodeToString(DEFAULT_GREEN_CITATION_MEDIA))))
            .andExpect(jsonPath("$.[*].dateOfCreation").value(hasItem(sameInstant(DEFAULT_DATE_OF_CREATION))))
            .andExpect(jsonPath("$.[*].backgroundColor").value(hasItem(DEFAULT_BACKGROUND_COLOR)))
            .andExpect(jsonPath("$.[*].greenCitationPrivacy").value(hasItem(DEFAULT_GREEN_CITATION_PRIVACY.toString())));
    }
}
