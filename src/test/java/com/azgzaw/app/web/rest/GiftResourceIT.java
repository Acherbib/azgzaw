package com.azgzaw.app.web.rest;

import com.azgzaw.app.AzgzawApp;
import com.azgzaw.app.domain.Gift;
import com.azgzaw.app.repository.GiftRepository;
import com.azgzaw.app.repository.search.GiftSearchRepository;
import com.azgzaw.app.service.GiftService;
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

/**
 * Integration tests for the {@link GiftResource} REST controller.
 */
@SpringBootTest(classes = AzgzawApp.class)
public class GiftResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_A_GIFT = false;
    private static final Boolean UPDATED_IS_A_GIFT = true;

    private static final Boolean DEFAULT_RESERVED = false;
    private static final Boolean UPDATED_RESERVED = true;

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_AUCTION = false;
    private static final Boolean UPDATED_IS_AUCTION = true;

    private static final Double DEFAULT_START_PRICE = 0D;
    private static final Double UPDATED_START_PRICE = 1D;

    private static final String DEFAULT_BACKGROUND_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_BACKGROUND_COLOR = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMBER_LIKES_OF_GIFTS = 1L;
    private static final Long UPDATED_NUMBER_LIKES_OF_GIFTS = 2L;

    private static final Long DEFAULT_NUMBER_SEEN_OF_GIFTS = 1L;
    private static final Long UPDATED_NUMBER_SEEN_OF_GIFTS = 2L;

    private static final ZonedDateTime DEFAULT_DATE_OF_CREATION = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_OF_CREATION = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private GiftRepository giftRepository;

    @Autowired
    private GiftService giftService;

    /**
     * This repository is mocked in the com.azgzaw.app.repository.search test package.
     *
     * @see com.azgzaw.app.repository.search.GiftSearchRepositoryMockConfiguration
     */
    @Autowired
    private GiftSearchRepository mockGiftSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restGiftMockMvc;

    private Gift gift;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GiftResource giftResource = new GiftResource(giftService);
        this.restGiftMockMvc = MockMvcBuilders.standaloneSetup(giftResource)
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
    public static Gift createEntity() {
        Gift gift = new Gift()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .isAGift(DEFAULT_IS_A_GIFT)
            .reserved(DEFAULT_RESERVED)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY)
            .isAuction(DEFAULT_IS_AUCTION)
            .startPrice(DEFAULT_START_PRICE)
            .backgroundColor(DEFAULT_BACKGROUND_COLOR)
            .numberLikesOfGifts(DEFAULT_NUMBER_LIKES_OF_GIFTS)
            .numberSeenOfGifts(DEFAULT_NUMBER_SEEN_OF_GIFTS)
            .dateOfCreation(DEFAULT_DATE_OF_CREATION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return gift;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Gift createUpdatedEntity() {
        Gift gift = new Gift()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .isAGift(UPDATED_IS_A_GIFT)
            .reserved(UPDATED_RESERVED)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .isAuction(UPDATED_IS_AUCTION)
            .startPrice(UPDATED_START_PRICE)
            .backgroundColor(UPDATED_BACKGROUND_COLOR)
            .numberLikesOfGifts(UPDATED_NUMBER_LIKES_OF_GIFTS)
            .numberSeenOfGifts(UPDATED_NUMBER_SEEN_OF_GIFTS)
            .dateOfCreation(UPDATED_DATE_OF_CREATION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return gift;
    }

    @BeforeEach
    public void initTest() {
        giftRepository.deleteAll();
        gift = createEntity();
    }

    @Test
    public void createGift() throws Exception {
        int databaseSizeBeforeCreate = giftRepository.findAll().size();

        // Create the Gift
        restGiftMockMvc.perform(post("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gift)))
            .andExpect(status().isCreated());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeCreate + 1);
        Gift testGift = giftList.get(giftList.size() - 1);
        assertThat(testGift.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testGift.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGift.isIsAGift()).isEqualTo(DEFAULT_IS_A_GIFT);
        assertThat(testGift.isReserved()).isEqualTo(DEFAULT_RESERVED);
        assertThat(testGift.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testGift.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testGift.isIsAuction()).isEqualTo(DEFAULT_IS_AUCTION);
        assertThat(testGift.getStartPrice()).isEqualTo(DEFAULT_START_PRICE);
        assertThat(testGift.getBackgroundColor()).isEqualTo(DEFAULT_BACKGROUND_COLOR);
        assertThat(testGift.getNumberLikesOfGifts()).isEqualTo(DEFAULT_NUMBER_LIKES_OF_GIFTS);
        assertThat(testGift.getNumberSeenOfGifts()).isEqualTo(DEFAULT_NUMBER_SEEN_OF_GIFTS);
        assertThat(testGift.getDateOfCreation()).isEqualTo(DEFAULT_DATE_OF_CREATION);
        assertThat(testGift.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testGift.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);

        // Validate the Gift in Elasticsearch
        verify(mockGiftSearchRepository, times(1)).save(testGift);
    }

    @Test
    public void createGiftWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = giftRepository.findAll().size();

        // Create the Gift with an existing ID
        gift.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restGiftMockMvc.perform(post("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gift)))
            .andExpect(status().isBadRequest());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeCreate);

        // Validate the Gift in Elasticsearch
        verify(mockGiftSearchRepository, times(0)).save(gift);
    }


    @Test
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = giftRepository.findAll().size();
        // set the field null
        gift.setTitle(null);

        // Create the Gift, which fails.

        restGiftMockMvc.perform(post("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gift)))
            .andExpect(status().isBadRequest());

        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllGifts() throws Exception {
        // Initialize the database
        giftRepository.save(gift);

        // Get all the giftList
        restGiftMockMvc.perform(get("/api/gifts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gift.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isAGift").value(hasItem(DEFAULT_IS_A_GIFT.booleanValue())))
            .andExpect(jsonPath("$.[*].reserved").value(hasItem(DEFAULT_RESERVED.booleanValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].isAuction").value(hasItem(DEFAULT_IS_AUCTION.booleanValue())))
            .andExpect(jsonPath("$.[*].startPrice").value(hasItem(DEFAULT_START_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].backgroundColor").value(hasItem(DEFAULT_BACKGROUND_COLOR)))
            .andExpect(jsonPath("$.[*].numberLikesOfGifts").value(hasItem(DEFAULT_NUMBER_LIKES_OF_GIFTS.intValue())))
            .andExpect(jsonPath("$.[*].numberSeenOfGifts").value(hasItem(DEFAULT_NUMBER_SEEN_OF_GIFTS.intValue())))
            .andExpect(jsonPath("$.[*].dateOfCreation").value(hasItem(sameInstant(DEFAULT_DATE_OF_CREATION))))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    public void getGift() throws Exception {
        // Initialize the database
        giftRepository.save(gift);

        // Get the gift
        restGiftMockMvc.perform(get("/api/gifts/{id}", gift.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(gift.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isAGift").value(DEFAULT_IS_A_GIFT.booleanValue()))
            .andExpect(jsonPath("$.reserved").value(DEFAULT_RESERVED.booleanValue()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.isAuction").value(DEFAULT_IS_AUCTION.booleanValue()))
            .andExpect(jsonPath("$.startPrice").value(DEFAULT_START_PRICE.doubleValue()))
            .andExpect(jsonPath("$.backgroundColor").value(DEFAULT_BACKGROUND_COLOR))
            .andExpect(jsonPath("$.numberLikesOfGifts").value(DEFAULT_NUMBER_LIKES_OF_GIFTS.intValue()))
            .andExpect(jsonPath("$.numberSeenOfGifts").value(DEFAULT_NUMBER_SEEN_OF_GIFTS.intValue()))
            .andExpect(jsonPath("$.dateOfCreation").value(sameInstant(DEFAULT_DATE_OF_CREATION)))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    public void getNonExistingGift() throws Exception {
        // Get the gift
        restGiftMockMvc.perform(get("/api/gifts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateGift() throws Exception {
        // Initialize the database
        giftService.save(gift);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockGiftSearchRepository);

        int databaseSizeBeforeUpdate = giftRepository.findAll().size();

        // Update the gift
        Gift updatedGift = giftRepository.findById(gift.getId()).get();
        updatedGift
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .isAGift(UPDATED_IS_A_GIFT)
            .reserved(UPDATED_RESERVED)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .isAuction(UPDATED_IS_AUCTION)
            .startPrice(UPDATED_START_PRICE)
            .backgroundColor(UPDATED_BACKGROUND_COLOR)
            .numberLikesOfGifts(UPDATED_NUMBER_LIKES_OF_GIFTS)
            .numberSeenOfGifts(UPDATED_NUMBER_SEEN_OF_GIFTS)
            .dateOfCreation(UPDATED_DATE_OF_CREATION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restGiftMockMvc.perform(put("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGift)))
            .andExpect(status().isOk());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeUpdate);
        Gift testGift = giftList.get(giftList.size() - 1);
        assertThat(testGift.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testGift.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGift.isIsAGift()).isEqualTo(UPDATED_IS_A_GIFT);
        assertThat(testGift.isReserved()).isEqualTo(UPDATED_RESERVED);
        assertThat(testGift.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testGift.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testGift.isIsAuction()).isEqualTo(UPDATED_IS_AUCTION);
        assertThat(testGift.getStartPrice()).isEqualTo(UPDATED_START_PRICE);
        assertThat(testGift.getBackgroundColor()).isEqualTo(UPDATED_BACKGROUND_COLOR);
        assertThat(testGift.getNumberLikesOfGifts()).isEqualTo(UPDATED_NUMBER_LIKES_OF_GIFTS);
        assertThat(testGift.getNumberSeenOfGifts()).isEqualTo(UPDATED_NUMBER_SEEN_OF_GIFTS);
        assertThat(testGift.getDateOfCreation()).isEqualTo(UPDATED_DATE_OF_CREATION);
        assertThat(testGift.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testGift.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);

        // Validate the Gift in Elasticsearch
        verify(mockGiftSearchRepository, times(1)).save(testGift);
    }

    @Test
    public void updateNonExistingGift() throws Exception {
        int databaseSizeBeforeUpdate = giftRepository.findAll().size();

        // Create the Gift

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGiftMockMvc.perform(put("/api/gifts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(gift)))
            .andExpect(status().isBadRequest());

        // Validate the Gift in the database
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Gift in Elasticsearch
        verify(mockGiftSearchRepository, times(0)).save(gift);
    }

    @Test
    public void deleteGift() throws Exception {
        // Initialize the database
        giftService.save(gift);

        int databaseSizeBeforeDelete = giftRepository.findAll().size();

        // Delete the gift
        restGiftMockMvc.perform(delete("/api/gifts/{id}", gift.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Gift> giftList = giftRepository.findAll();
        assertThat(giftList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Gift in Elasticsearch
        verify(mockGiftSearchRepository, times(1)).deleteById(gift.getId());
    }

    @Test
    public void searchGift() throws Exception {
        // Initialize the database
        giftService.save(gift);
        when(mockGiftSearchRepository.search(queryStringQuery("id:" + gift.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(gift), PageRequest.of(0, 1), 1));
        // Search the gift
        restGiftMockMvc.perform(get("/api/_search/gifts?query=id:" + gift.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gift.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isAGift").value(hasItem(DEFAULT_IS_A_GIFT.booleanValue())))
            .andExpect(jsonPath("$.[*].reserved").value(hasItem(DEFAULT_RESERVED.booleanValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].isAuction").value(hasItem(DEFAULT_IS_AUCTION.booleanValue())))
            .andExpect(jsonPath("$.[*].startPrice").value(hasItem(DEFAULT_START_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].backgroundColor").value(hasItem(DEFAULT_BACKGROUND_COLOR)))
            .andExpect(jsonPath("$.[*].numberLikesOfGifts").value(hasItem(DEFAULT_NUMBER_LIKES_OF_GIFTS.intValue())))
            .andExpect(jsonPath("$.[*].numberSeenOfGifts").value(hasItem(DEFAULT_NUMBER_SEEN_OF_GIFTS.intValue())))
            .andExpect(jsonPath("$.[*].dateOfCreation").value(hasItem(sameInstant(DEFAULT_DATE_OF_CREATION))))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
}
