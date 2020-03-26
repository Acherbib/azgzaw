package com.azgzaw.app.web.rest;

import com.azgzaw.app.AzgzawApp;
import com.azgzaw.app.domain.Auction;
import com.azgzaw.app.repository.AuctionRepository;
import com.azgzaw.app.repository.search.AuctionSearchRepository;
import com.azgzaw.app.service.AuctionService;
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
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static com.azgzaw.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.azgzaw.app.domain.enumeration.CardType;
/**
 * Integration tests for the {@link AuctionResource} REST controller.
 */
@SpringBootTest(classes = AzgzawApp.class)
public class AuctionResourceIT {

    private static final CardType DEFAULT_CARD_TYPE = CardType.VISA;
    private static final CardType UPDATED_CARD_TYPE = CardType.MASTERCARD;

    private static final Long DEFAULT_CARD_NUMBER = 1L;
    private static final Long UPDATED_CARD_NUMBER = 2L;

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CVC = 1;
    private static final Integer UPDATED_CVC = 2;

    private static final Long DEFAULT_PRICE_OFFER = 0L;
    private static final Long UPDATED_PRICE_OFFER = 1L;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private AuctionService auctionService;

    /**
     * This repository is mocked in the com.azgzaw.app.repository.search test package.
     *
     * @see com.azgzaw.app.repository.search.AuctionSearchRepositoryMockConfiguration
     */
    @Autowired
    private AuctionSearchRepository mockAuctionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private Validator validator;

    private MockMvc restAuctionMockMvc;

    private Auction auction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuctionResource auctionResource = new AuctionResource(auctionService);
        this.restAuctionMockMvc = MockMvcBuilders.standaloneSetup(auctionResource)
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
    public static Auction createEntity() {
        Auction auction = new Auction()
            .cardType(DEFAULT_CARD_TYPE)
            .cardNumber(DEFAULT_CARD_NUMBER)
            .endDate(DEFAULT_END_DATE)
            .cvc(DEFAULT_CVC)
            .priceOffer(DEFAULT_PRICE_OFFER);
        return auction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auction createUpdatedEntity() {
        Auction auction = new Auction()
            .cardType(UPDATED_CARD_TYPE)
            .cardNumber(UPDATED_CARD_NUMBER)
            .endDate(UPDATED_END_DATE)
            .cvc(UPDATED_CVC)
            .priceOffer(UPDATED_PRICE_OFFER);
        return auction;
    }

    @BeforeEach
    public void initTest() {
        auctionRepository.deleteAll();
        auction = createEntity();
    }

    @Test
    public void createAuction() throws Exception {
        int databaseSizeBeforeCreate = auctionRepository.findAll().size();

        // Create the Auction
        restAuctionMockMvc.perform(post("/api/auctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auction)))
            .andExpect(status().isCreated());

        // Validate the Auction in the database
        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeCreate + 1);
        Auction testAuction = auctionList.get(auctionList.size() - 1);
        assertThat(testAuction.getCardType()).isEqualTo(DEFAULT_CARD_TYPE);
        assertThat(testAuction.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testAuction.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testAuction.getCvc()).isEqualTo(DEFAULT_CVC);
        assertThat(testAuction.getPriceOffer()).isEqualTo(DEFAULT_PRICE_OFFER);

        // Validate the Auction in Elasticsearch
        verify(mockAuctionSearchRepository, times(1)).save(testAuction);
    }

    @Test
    public void createAuctionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auctionRepository.findAll().size();

        // Create the Auction with an existing ID
        auction.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuctionMockMvc.perform(post("/api/auctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auction)))
            .andExpect(status().isBadRequest());

        // Validate the Auction in the database
        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Auction in Elasticsearch
        verify(mockAuctionSearchRepository, times(0)).save(auction);
    }


    @Test
    public void getAllAuctions() throws Exception {
        // Initialize the database
        auctionRepository.save(auction);

        // Get all the auctionList
        restAuctionMockMvc.perform(get("/api/auctions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auction.getId())))
            .andExpect(jsonPath("$.[*].cardType").value(hasItem(DEFAULT_CARD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].cvc").value(hasItem(DEFAULT_CVC)))
            .andExpect(jsonPath("$.[*].priceOffer").value(hasItem(DEFAULT_PRICE_OFFER.intValue())));
    }
    
    @Test
    public void getAuction() throws Exception {
        // Initialize the database
        auctionRepository.save(auction);

        // Get the auction
        restAuctionMockMvc.perform(get("/api/auctions/{id}", auction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auction.getId()))
            .andExpect(jsonPath("$.cardType").value(DEFAULT_CARD_TYPE.toString()))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER.intValue()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.cvc").value(DEFAULT_CVC))
            .andExpect(jsonPath("$.priceOffer").value(DEFAULT_PRICE_OFFER.intValue()));
    }

    @Test
    public void getNonExistingAuction() throws Exception {
        // Get the auction
        restAuctionMockMvc.perform(get("/api/auctions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAuction() throws Exception {
        // Initialize the database
        auctionService.save(auction);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockAuctionSearchRepository);

        int databaseSizeBeforeUpdate = auctionRepository.findAll().size();

        // Update the auction
        Auction updatedAuction = auctionRepository.findById(auction.getId()).get();
        updatedAuction
            .cardType(UPDATED_CARD_TYPE)
            .cardNumber(UPDATED_CARD_NUMBER)
            .endDate(UPDATED_END_DATE)
            .cvc(UPDATED_CVC)
            .priceOffer(UPDATED_PRICE_OFFER);

        restAuctionMockMvc.perform(put("/api/auctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAuction)))
            .andExpect(status().isOk());

        // Validate the Auction in the database
        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeUpdate);
        Auction testAuction = auctionList.get(auctionList.size() - 1);
        assertThat(testAuction.getCardType()).isEqualTo(UPDATED_CARD_TYPE);
        assertThat(testAuction.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testAuction.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testAuction.getCvc()).isEqualTo(UPDATED_CVC);
        assertThat(testAuction.getPriceOffer()).isEqualTo(UPDATED_PRICE_OFFER);

        // Validate the Auction in Elasticsearch
        verify(mockAuctionSearchRepository, times(1)).save(testAuction);
    }

    @Test
    public void updateNonExistingAuction() throws Exception {
        int databaseSizeBeforeUpdate = auctionRepository.findAll().size();

        // Create the Auction

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuctionMockMvc.perform(put("/api/auctions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auction)))
            .andExpect(status().isBadRequest());

        // Validate the Auction in the database
        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Auction in Elasticsearch
        verify(mockAuctionSearchRepository, times(0)).save(auction);
    }

    @Test
    public void deleteAuction() throws Exception {
        // Initialize the database
        auctionService.save(auction);

        int databaseSizeBeforeDelete = auctionRepository.findAll().size();

        // Delete the auction
        restAuctionMockMvc.perform(delete("/api/auctions/{id}", auction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Auction> auctionList = auctionRepository.findAll();
        assertThat(auctionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Auction in Elasticsearch
        verify(mockAuctionSearchRepository, times(1)).deleteById(auction.getId());
    }

    @Test
    public void searchAuction() throws Exception {
        // Initialize the database
        auctionService.save(auction);
        when(mockAuctionSearchRepository.search(queryStringQuery("id:" + auction.getId())))
            .thenReturn(Collections.singletonList(auction));
        // Search the auction
        restAuctionMockMvc.perform(get("/api/_search/auctions?query=id:" + auction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auction.getId())))
            .andExpect(jsonPath("$.[*].cardType").value(hasItem(DEFAULT_CARD_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].cvc").value(hasItem(DEFAULT_CVC)))
            .andExpect(jsonPath("$.[*].priceOffer").value(hasItem(DEFAULT_PRICE_OFFER.intValue())));
    }
}
