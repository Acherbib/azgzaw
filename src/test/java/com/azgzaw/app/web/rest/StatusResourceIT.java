package com.azgzaw.app.web.rest;

import com.azgzaw.app.AzgzawApp;
import com.azgzaw.app.domain.Status;
import com.azgzaw.app.repository.StatusRepository;
import com.azgzaw.app.repository.search.StatusSearchRepository;
import com.azgzaw.app.service.StatusService;
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


import java.util.Collections;
import java.util.List;

import static com.azgzaw.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.azgzaw.app.domain.enumeration.Privacy;
/**
 * Integration tests for the {@link StatusResource} REST controller.
 */
@SpringBootTest(classes = AzgzawApp.class)
public class StatusResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_STATUS_MEDIA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_STATUS_MEDIA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_STATUS_MEDIA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_STATUS_MEDIA_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_NUMBER_SEEN_OF_STATUS = 1L;
    private static final Long UPDATED_NUMBER_SEEN_OF_STATUS = 2L;

    private static final Privacy DEFAULT_STATUS_PRIVACY = Privacy.PUBLIC;
    private static final Privacy UPDATED_STATUS_PRIVACY = Privacy.ONLYME;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusService statusService;

    /**
     * This repository is mocked in the com.azgzaw.app.repository.search test package.
     *
     * @see com.azgzaw.app.repository.search.StatusSearchRepositoryMockConfiguration
     */
    @Autowired
    private StatusSearchRepository mockStatusSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restStatusMockMvc;

    private Status status;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StatusResource statusResource = new StatusResource(statusService);
        this.restStatusMockMvc = MockMvcBuilders.standaloneSetup(statusResource)
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
    public static Status createEntity() {
        Status status = new Status()
            .description(DEFAULT_DESCRIPTION)
            .statusMedia(DEFAULT_STATUS_MEDIA)
            .statusMediaContentType(DEFAULT_STATUS_MEDIA_CONTENT_TYPE)
            .numberSeenOfStatus(DEFAULT_NUMBER_SEEN_OF_STATUS)
            .statusPrivacy(DEFAULT_STATUS_PRIVACY);
        return status;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Status createUpdatedEntity() {
        Status status = new Status()
            .description(UPDATED_DESCRIPTION)
            .statusMedia(UPDATED_STATUS_MEDIA)
            .statusMediaContentType(UPDATED_STATUS_MEDIA_CONTENT_TYPE)
            .numberSeenOfStatus(UPDATED_NUMBER_SEEN_OF_STATUS)
            .statusPrivacy(UPDATED_STATUS_PRIVACY);
        return status;
    }

    @BeforeEach
    public void initTest() {
        statusRepository.deleteAll();
        status = createEntity();
    }

    @Test
    public void createStatus() throws Exception {
        int databaseSizeBeforeCreate = statusRepository.findAll().size();

        // Create the Status
        restStatusMockMvc.perform(post("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isCreated());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate + 1);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testStatus.getStatusMedia()).isEqualTo(DEFAULT_STATUS_MEDIA);
        assertThat(testStatus.getStatusMediaContentType()).isEqualTo(DEFAULT_STATUS_MEDIA_CONTENT_TYPE);
        assertThat(testStatus.getNumberSeenOfStatus()).isEqualTo(DEFAULT_NUMBER_SEEN_OF_STATUS);
        assertThat(testStatus.getStatusPrivacy()).isEqualTo(DEFAULT_STATUS_PRIVACY);

        // Validate the Status in Elasticsearch
        verify(mockStatusSearchRepository, times(1)).save(testStatus);
    }

    @Test
    public void createStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statusRepository.findAll().size();

        // Create the Status with an existing ID
        status.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusMockMvc.perform(post("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeCreate);

        // Validate the Status in Elasticsearch
        verify(mockStatusSearchRepository, times(0)).save(status);
    }


    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setDescription(null);

        // Create the Status, which fails.

        restStatusMockMvc.perform(post("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkStatusPrivacyIsRequired() throws Exception {
        int databaseSizeBeforeTest = statusRepository.findAll().size();
        // set the field null
        status.setStatusPrivacy(null);

        // Create the Status, which fails.

        restStatusMockMvc.perform(post("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isBadRequest());

        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllStatuses() throws Exception {
        // Initialize the database
        statusRepository.save(status);

        // Get all the statusList
        restStatusMockMvc.perform(get("/api/statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(status.getId())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].statusMediaContentType").value(hasItem(DEFAULT_STATUS_MEDIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].statusMedia").value(hasItem(Base64Utils.encodeToString(DEFAULT_STATUS_MEDIA))))
            .andExpect(jsonPath("$.[*].numberSeenOfStatus").value(hasItem(DEFAULT_NUMBER_SEEN_OF_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].statusPrivacy").value(hasItem(DEFAULT_STATUS_PRIVACY.toString())));
    }
    
    @Test
    public void getStatus() throws Exception {
        // Initialize the database
        statusRepository.save(status);

        // Get the status
        restStatusMockMvc.perform(get("/api/statuses/{id}", status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(status.getId()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.statusMediaContentType").value(DEFAULT_STATUS_MEDIA_CONTENT_TYPE))
            .andExpect(jsonPath("$.statusMedia").value(Base64Utils.encodeToString(DEFAULT_STATUS_MEDIA)))
            .andExpect(jsonPath("$.numberSeenOfStatus").value(DEFAULT_NUMBER_SEEN_OF_STATUS.intValue()))
            .andExpect(jsonPath("$.statusPrivacy").value(DEFAULT_STATUS_PRIVACY.toString()));
    }

    @Test
    public void getNonExistingStatus() throws Exception {
        // Get the status
        restStatusMockMvc.perform(get("/api/statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateStatus() throws Exception {
        // Initialize the database
        statusService.save(status);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockStatusSearchRepository);

        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Update the status
        Status updatedStatus = statusRepository.findById(status.getId()).get();
        updatedStatus
            .description(UPDATED_DESCRIPTION)
            .statusMedia(UPDATED_STATUS_MEDIA)
            .statusMediaContentType(UPDATED_STATUS_MEDIA_CONTENT_TYPE)
            .numberSeenOfStatus(UPDATED_NUMBER_SEEN_OF_STATUS)
            .statusPrivacy(UPDATED_STATUS_PRIVACY);

        restStatusMockMvc.perform(put("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatus)))
            .andExpect(status().isOk());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);
        Status testStatus = statusList.get(statusList.size() - 1);
        assertThat(testStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testStatus.getStatusMedia()).isEqualTo(UPDATED_STATUS_MEDIA);
        assertThat(testStatus.getStatusMediaContentType()).isEqualTo(UPDATED_STATUS_MEDIA_CONTENT_TYPE);
        assertThat(testStatus.getNumberSeenOfStatus()).isEqualTo(UPDATED_NUMBER_SEEN_OF_STATUS);
        assertThat(testStatus.getStatusPrivacy()).isEqualTo(UPDATED_STATUS_PRIVACY);

        // Validate the Status in Elasticsearch
        verify(mockStatusSearchRepository, times(1)).save(testStatus);
    }

    @Test
    public void updateNonExistingStatus() throws Exception {
        int databaseSizeBeforeUpdate = statusRepository.findAll().size();

        // Create the Status

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusMockMvc.perform(put("/api/statuses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(status)))
            .andExpect(status().isBadRequest());

        // Validate the Status in the database
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Status in Elasticsearch
        verify(mockStatusSearchRepository, times(0)).save(status);
    }

    @Test
    public void deleteStatus() throws Exception {
        // Initialize the database
        statusService.save(status);

        int databaseSizeBeforeDelete = statusRepository.findAll().size();

        // Delete the status
        restStatusMockMvc.perform(delete("/api/statuses/{id}", status.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Status> statusList = statusRepository.findAll();
        assertThat(statusList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Status in Elasticsearch
        verify(mockStatusSearchRepository, times(1)).deleteById(status.getId());
    }

    @Test
    public void searchStatus() throws Exception {
        // Initialize the database
        statusService.save(status);
        when(mockStatusSearchRepository.search(queryStringQuery("id:" + status.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(status), PageRequest.of(0, 1), 1));
        // Search the status
        restStatusMockMvc.perform(get("/api/_search/statuses?query=id:" + status.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(status.getId())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].statusMediaContentType").value(hasItem(DEFAULT_STATUS_MEDIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].statusMedia").value(hasItem(Base64Utils.encodeToString(DEFAULT_STATUS_MEDIA))))
            .andExpect(jsonPath("$.[*].numberSeenOfStatus").value(hasItem(DEFAULT_NUMBER_SEEN_OF_STATUS.intValue())))
            .andExpect(jsonPath("$.[*].statusPrivacy").value(hasItem(DEFAULT_STATUS_PRIVACY.toString())));
    }
}
