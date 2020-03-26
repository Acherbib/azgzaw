package com.azgzaw.app.web.rest;

import com.azgzaw.app.AzgzawApp;
import com.azgzaw.app.domain.Profile;
import com.azgzaw.app.repository.ProfileRepository;
import com.azgzaw.app.repository.search.ProfileSearchRepository;
import com.azgzaw.app.service.ProfileService;
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


import java.time.LocalDate;
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

import com.azgzaw.app.domain.enumeration.Gender;
import com.azgzaw.app.domain.enumeration.MaritalStatus;
import com.azgzaw.app.domain.enumeration.Privacy;
/**
 * Integration tests for the {@link ProfileResource} REST controller.
 */
@SpringBootTest(classes = AzgzawApp.class)
public class ProfileResourceIT {

    private static final Long DEFAULT_NUMBER_OF_MINUTES_CONNECTED = 1L;
    private static final Long UPDATED_NUMBER_OF_MINUTES_CONNECTED = 2L;

    private static final Boolean DEFAULT_VERIFIED = false;
    private static final Boolean UPDATED_VERIFIED = true;

    private static final ZonedDateTime DEFAULT_JOINED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_JOINED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_DATE_OF_BIRTH_VISIBLE = false;
    private static final Boolean UPDATED_DATE_OF_BIRTH_VISIBLE = true;

    private static final Long DEFAULT_PHONE_NUMBER = 1L;
    private static final Long UPDATED_PHONE_NUMBER = 2L;

    private static final Boolean DEFAULT_PHONE_NUMBER_VISIBLE = false;
    private static final Boolean UPDATED_PHONE_NUMBER_VISIBLE = true;

    private static final String DEFAULT_ABOUT_ME = "AAAAAAAAAA";
    private static final String UPDATED_ABOUT_ME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ABOUT_ME_VISIBLE = false;
    private static final Boolean UPDATED_ABOUT_ME_VISIBLE = true;

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final Boolean DEFAULT_GENDER_VISIBLE = false;
    private static final Boolean UPDATED_GENDER_VISIBLE = true;

    private static final MaritalStatus DEFAULT_MARITAL_STATUS = MaritalStatus.SINGLE;
    private static final MaritalStatus UPDATED_MARITAL_STATUS = MaritalStatus.MARRIED;

    private static final Boolean DEFAULT_MARITAL_STATUS_VISIBLE = false;
    private static final Boolean UPDATED_MARITAL_STATUS_VISIBLE = true;

    private static final Privacy DEFAULT_PROFILE_PRIVACY = Privacy.PUBLIC;
    private static final Privacy UPDATED_PROFILE_PRIVACY = Privacy.ONLYME;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    /**
     * This repository is mocked in the com.azgzaw.app.repository.search test package.
     *
     * @see com.azgzaw.app.repository.search.ProfileSearchRepositoryMockConfiguration
     */
    @Autowired
    private ProfileSearchRepository mockProfileSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restProfileMockMvc;

    private Profile profile;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileResource profileResource = new ProfileResource(profileService);
        this.restProfileMockMvc = MockMvcBuilders.standaloneSetup(profileResource)
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
    public static Profile createEntity() {
        Profile profile = new Profile()
            .numberOfMinutesConnected(DEFAULT_NUMBER_OF_MINUTES_CONNECTED)
            .verified(DEFAULT_VERIFIED)
            .joinedDate(DEFAULT_JOINED_DATE)
            .dateOfBirth(DEFAULT_DATE_OF_BIRTH)
            .dateOfBirthVisible(DEFAULT_DATE_OF_BIRTH_VISIBLE)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .phoneNumberVisible(DEFAULT_PHONE_NUMBER_VISIBLE)
            .aboutMe(DEFAULT_ABOUT_ME)
            .aboutMeVisible(DEFAULT_ABOUT_ME_VISIBLE)
            .gender(DEFAULT_GENDER)
            .genderVisible(DEFAULT_GENDER_VISIBLE)
            .maritalStatus(DEFAULT_MARITAL_STATUS)
            .maritalStatusVisible(DEFAULT_MARITAL_STATUS_VISIBLE)
            .profilePrivacy(DEFAULT_PROFILE_PRIVACY);
        return profile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profile createUpdatedEntity() {
        Profile profile = new Profile()
            .numberOfMinutesConnected(UPDATED_NUMBER_OF_MINUTES_CONNECTED)
            .verified(UPDATED_VERIFIED)
            .joinedDate(UPDATED_JOINED_DATE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .dateOfBirthVisible(UPDATED_DATE_OF_BIRTH_VISIBLE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .phoneNumberVisible(UPDATED_PHONE_NUMBER_VISIBLE)
            .aboutMe(UPDATED_ABOUT_ME)
            .aboutMeVisible(UPDATED_ABOUT_ME_VISIBLE)
            .gender(UPDATED_GENDER)
            .genderVisible(UPDATED_GENDER_VISIBLE)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .maritalStatusVisible(UPDATED_MARITAL_STATUS_VISIBLE)
            .profilePrivacy(UPDATED_PROFILE_PRIVACY);
        return profile;
    }

    @BeforeEach
    public void initTest() {
        profileRepository.deleteAll();
        profile = createEntity();
    }

    @Test
    public void createProfile() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isCreated());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate + 1);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getNumberOfMinutesConnected()).isEqualTo(DEFAULT_NUMBER_OF_MINUTES_CONNECTED);
        assertThat(testProfile.isVerified()).isEqualTo(DEFAULT_VERIFIED);
        assertThat(testProfile.getJoinedDate()).isEqualTo(DEFAULT_JOINED_DATE);
        assertThat(testProfile.getDateOfBirth()).isEqualTo(DEFAULT_DATE_OF_BIRTH);
        assertThat(testProfile.isDateOfBirthVisible()).isEqualTo(DEFAULT_DATE_OF_BIRTH_VISIBLE);
        assertThat(testProfile.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testProfile.isPhoneNumberVisible()).isEqualTo(DEFAULT_PHONE_NUMBER_VISIBLE);
        assertThat(testProfile.getAboutMe()).isEqualTo(DEFAULT_ABOUT_ME);
        assertThat(testProfile.isAboutMeVisible()).isEqualTo(DEFAULT_ABOUT_ME_VISIBLE);
        assertThat(testProfile.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testProfile.isGenderVisible()).isEqualTo(DEFAULT_GENDER_VISIBLE);
        assertThat(testProfile.getMaritalStatus()).isEqualTo(DEFAULT_MARITAL_STATUS);
        assertThat(testProfile.isMaritalStatusVisible()).isEqualTo(DEFAULT_MARITAL_STATUS_VISIBLE);
        assertThat(testProfile.getProfilePrivacy()).isEqualTo(DEFAULT_PROFILE_PRIVACY);

        // Validate the Profile in Elasticsearch
        verify(mockProfileSearchRepository, times(1)).save(testProfile);
    }

    @Test
    public void createProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileRepository.findAll().size();

        // Create the Profile with an existing ID
        profile.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeCreate);

        // Validate the Profile in Elasticsearch
        verify(mockProfileSearchRepository, times(0)).save(profile);
    }


    @Test
    public void checkProfilePrivacyIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileRepository.findAll().size();
        // set the field null
        profile.setProfilePrivacy(null);

        // Create the Profile, which fails.

        restProfileMockMvc.perform(post("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllProfiles() throws Exception {
        // Initialize the database
        profileRepository.save(profile);

        // Get all the profileList
        restProfileMockMvc.perform(get("/api/profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId())))
            .andExpect(jsonPath("$.[*].numberOfMinutesConnected").value(hasItem(DEFAULT_NUMBER_OF_MINUTES_CONNECTED.intValue())))
            .andExpect(jsonPath("$.[*].verified").value(hasItem(DEFAULT_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].joinedDate").value(hasItem(sameInstant(DEFAULT_JOINED_DATE))))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirthVisible").value(hasItem(DEFAULT_DATE_OF_BIRTH_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].phoneNumberVisible").value(hasItem(DEFAULT_PHONE_NUMBER_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].aboutMe").value(hasItem(DEFAULT_ABOUT_ME)))
            .andExpect(jsonPath("$.[*].aboutMeVisible").value(hasItem(DEFAULT_ABOUT_ME_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].genderVisible").value(hasItem(DEFAULT_GENDER_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].maritalStatusVisible").value(hasItem(DEFAULT_MARITAL_STATUS_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].profilePrivacy").value(hasItem(DEFAULT_PROFILE_PRIVACY.toString())));
    }
    
    @Test
    public void getProfile() throws Exception {
        // Initialize the database
        profileRepository.save(profile);

        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profile.getId()))
            .andExpect(jsonPath("$.numberOfMinutesConnected").value(DEFAULT_NUMBER_OF_MINUTES_CONNECTED.intValue()))
            .andExpect(jsonPath("$.verified").value(DEFAULT_VERIFIED.booleanValue()))
            .andExpect(jsonPath("$.joinedDate").value(sameInstant(DEFAULT_JOINED_DATE)))
            .andExpect(jsonPath("$.dateOfBirth").value(DEFAULT_DATE_OF_BIRTH.toString()))
            .andExpect(jsonPath("$.dateOfBirthVisible").value(DEFAULT_DATE_OF_BIRTH_VISIBLE.booleanValue()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.intValue()))
            .andExpect(jsonPath("$.phoneNumberVisible").value(DEFAULT_PHONE_NUMBER_VISIBLE.booleanValue()))
            .andExpect(jsonPath("$.aboutMe").value(DEFAULT_ABOUT_ME))
            .andExpect(jsonPath("$.aboutMeVisible").value(DEFAULT_ABOUT_ME_VISIBLE.booleanValue()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.genderVisible").value(DEFAULT_GENDER_VISIBLE.booleanValue()))
            .andExpect(jsonPath("$.maritalStatus").value(DEFAULT_MARITAL_STATUS.toString()))
            .andExpect(jsonPath("$.maritalStatusVisible").value(DEFAULT_MARITAL_STATUS_VISIBLE.booleanValue()))
            .andExpect(jsonPath("$.profilePrivacy").value(DEFAULT_PROFILE_PRIVACY.toString()));
    }

    @Test
    public void getNonExistingProfile() throws Exception {
        // Get the profile
        restProfileMockMvc.perform(get("/api/profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProfile() throws Exception {
        // Initialize the database
        profileService.save(profile);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockProfileSearchRepository);

        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Update the profile
        Profile updatedProfile = profileRepository.findById(profile.getId()).get();
        updatedProfile
            .numberOfMinutesConnected(UPDATED_NUMBER_OF_MINUTES_CONNECTED)
            .verified(UPDATED_VERIFIED)
            .joinedDate(UPDATED_JOINED_DATE)
            .dateOfBirth(UPDATED_DATE_OF_BIRTH)
            .dateOfBirthVisible(UPDATED_DATE_OF_BIRTH_VISIBLE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .phoneNumberVisible(UPDATED_PHONE_NUMBER_VISIBLE)
            .aboutMe(UPDATED_ABOUT_ME)
            .aboutMeVisible(UPDATED_ABOUT_ME_VISIBLE)
            .gender(UPDATED_GENDER)
            .genderVisible(UPDATED_GENDER_VISIBLE)
            .maritalStatus(UPDATED_MARITAL_STATUS)
            .maritalStatusVisible(UPDATED_MARITAL_STATUS_VISIBLE)
            .profilePrivacy(UPDATED_PROFILE_PRIVACY);

        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfile)))
            .andExpect(status().isOk());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);
        Profile testProfile = profileList.get(profileList.size() - 1);
        assertThat(testProfile.getNumberOfMinutesConnected()).isEqualTo(UPDATED_NUMBER_OF_MINUTES_CONNECTED);
        assertThat(testProfile.isVerified()).isEqualTo(UPDATED_VERIFIED);
        assertThat(testProfile.getJoinedDate()).isEqualTo(UPDATED_JOINED_DATE);
        assertThat(testProfile.getDateOfBirth()).isEqualTo(UPDATED_DATE_OF_BIRTH);
        assertThat(testProfile.isDateOfBirthVisible()).isEqualTo(UPDATED_DATE_OF_BIRTH_VISIBLE);
        assertThat(testProfile.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testProfile.isPhoneNumberVisible()).isEqualTo(UPDATED_PHONE_NUMBER_VISIBLE);
        assertThat(testProfile.getAboutMe()).isEqualTo(UPDATED_ABOUT_ME);
        assertThat(testProfile.isAboutMeVisible()).isEqualTo(UPDATED_ABOUT_ME_VISIBLE);
        assertThat(testProfile.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testProfile.isGenderVisible()).isEqualTo(UPDATED_GENDER_VISIBLE);
        assertThat(testProfile.getMaritalStatus()).isEqualTo(UPDATED_MARITAL_STATUS);
        assertThat(testProfile.isMaritalStatusVisible()).isEqualTo(UPDATED_MARITAL_STATUS_VISIBLE);
        assertThat(testProfile.getProfilePrivacy()).isEqualTo(UPDATED_PROFILE_PRIVACY);

        // Validate the Profile in Elasticsearch
        verify(mockProfileSearchRepository, times(1)).save(testProfile);
    }

    @Test
    public void updateNonExistingProfile() throws Exception {
        int databaseSizeBeforeUpdate = profileRepository.findAll().size();

        // Create the Profile

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileMockMvc.perform(put("/api/profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profile)))
            .andExpect(status().isBadRequest());

        // Validate the Profile in the database
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Profile in Elasticsearch
        verify(mockProfileSearchRepository, times(0)).save(profile);
    }

    @Test
    public void deleteProfile() throws Exception {
        // Initialize the database
        profileService.save(profile);

        int databaseSizeBeforeDelete = profileRepository.findAll().size();

        // Delete the profile
        restProfileMockMvc.perform(delete("/api/profiles/{id}", profile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profile> profileList = profileRepository.findAll();
        assertThat(profileList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Profile in Elasticsearch
        verify(mockProfileSearchRepository, times(1)).deleteById(profile.getId());
    }

    @Test
    public void searchProfile() throws Exception {
        // Initialize the database
        profileService.save(profile);
        when(mockProfileSearchRepository.search(queryStringQuery("id:" + profile.getId())))
            .thenReturn(Collections.singletonList(profile));
        // Search the profile
        restProfileMockMvc.perform(get("/api/_search/profiles?query=id:" + profile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profile.getId())))
            .andExpect(jsonPath("$.[*].numberOfMinutesConnected").value(hasItem(DEFAULT_NUMBER_OF_MINUTES_CONNECTED.intValue())))
            .andExpect(jsonPath("$.[*].verified").value(hasItem(DEFAULT_VERIFIED.booleanValue())))
            .andExpect(jsonPath("$.[*].joinedDate").value(hasItem(sameInstant(DEFAULT_JOINED_DATE))))
            .andExpect(jsonPath("$.[*].dateOfBirth").value(hasItem(DEFAULT_DATE_OF_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].dateOfBirthVisible").value(hasItem(DEFAULT_DATE_OF_BIRTH_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].phoneNumberVisible").value(hasItem(DEFAULT_PHONE_NUMBER_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].aboutMe").value(hasItem(DEFAULT_ABOUT_ME)))
            .andExpect(jsonPath("$.[*].aboutMeVisible").value(hasItem(DEFAULT_ABOUT_ME_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].genderVisible").value(hasItem(DEFAULT_GENDER_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].maritalStatus").value(hasItem(DEFAULT_MARITAL_STATUS.toString())))
            .andExpect(jsonPath("$.[*].maritalStatusVisible").value(hasItem(DEFAULT_MARITAL_STATUS_VISIBLE.booleanValue())))
            .andExpect(jsonPath("$.[*].profilePrivacy").value(hasItem(DEFAULT_PROFILE_PRIVACY.toString())));
    }
}
