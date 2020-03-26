package com.azgzaw.app.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;

import com.azgzaw.app.domain.enumeration.CardType;

/**
 * A Auction.
 */
@Document(collection = "auction")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "auction")
public class Auction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @Field("card_type")
    private CardType cardType;

    @Field("card_number")
    private Long cardNumber;

    @Field("end_date")
    private LocalDate endDate;

    @Field("cvc")
    private Integer cvc;

    @Min(value = 0L)
    @Field("price_offer")
    private Long priceOffer;

    @DBRef
    @Field("gift")
    private Gift gift;

    @DBRef
    @Field("author")
    private User author;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Auction cardType(CardType cardType) {
        this.cardType = cardType;
        return this;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public Auction cardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Auction endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getCvc() {
        return cvc;
    }

    public Auction cvc(Integer cvc) {
        this.cvc = cvc;
        return this;
    }

    public void setCvc(Integer cvc) {
        this.cvc = cvc;
    }

    public Long getPriceOffer() {
        return priceOffer;
    }

    public Auction priceOffer(Long priceOffer) {
        this.priceOffer = priceOffer;
        return this;
    }

    public void setPriceOffer(Long priceOffer) {
        this.priceOffer = priceOffer;
    }

    public Gift getGift() {
        return gift;
    }

    public Auction gift(Gift gift) {
        this.gift = gift;
        return this;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public User getAuthor() {
        return author;
    }

    public Auction author(User user) {
        this.author = user;
        return this;
    }

    public void setAuthor(User user) {
        this.author = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Auction)) {
            return false;
        }
        return id != null && id.equals(((Auction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Auction{" +
            "id=" + getId() +
            ", cardType='" + getCardType() + "'" +
            ", cardNumber=" + getCardNumber() +
            ", endDate='" + getEndDate() + "'" +
            ", cvc=" + getCvc() +
            ", priceOffer=" + getPriceOffer() +
            "}";
    }
}
