package com.azgzaw.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Gift.
 */
@Document(collection = "gift")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "gift")
public class Gift implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("is_a_gift")
    private Boolean isAGift;

    @Field("reserved")
    private Boolean reserved;

    @Field("city")
    private String city;

    @Field("country")
    private String country;

    @Field("is_auction")
    private Boolean isAuction;

    @DecimalMin(value = "0")
    @Field("start_price")
    private Double startPrice;

    @Field("background_color")
    private String backgroundColor;

    @Field("number_likes_of_gifts")
    private Long numberLikesOfGifts;

    @Field("number_seen_of_gifts")
    private Long numberSeenOfGifts;

    @Field("date_of_creation")
    private ZonedDateTime dateOfCreation;

    @Field("image")
    private byte[] image;

    @Field("image_content_type")
    private String imageContentType;

    @DBRef
    @Field("authorOfGift")
    @JsonIgnoreProperties("gifts")
    private User authorOfGift;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Gift title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Gift description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isIsAGift() {
        return isAGift;
    }

    public Gift isAGift(Boolean isAGift) {
        this.isAGift = isAGift;
        return this;
    }

    public void setIsAGift(Boolean isAGift) {
        this.isAGift = isAGift;
    }

    public Boolean isReserved() {
        return reserved;
    }

    public Gift reserved(Boolean reserved) {
        this.reserved = reserved;
        return this;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public String getCity() {
        return city;
    }

    public Gift city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public Gift country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean isIsAuction() {
        return isAuction;
    }

    public Gift isAuction(Boolean isAuction) {
        this.isAuction = isAuction;
        return this;
    }

    public void setIsAuction(Boolean isAuction) {
        this.isAuction = isAuction;
    }

    public Double getStartPrice() {
        return startPrice;
    }

    public Gift startPrice(Double startPrice) {
        this.startPrice = startPrice;
        return this;
    }

    public void setStartPrice(Double startPrice) {
        this.startPrice = startPrice;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public Gift backgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Long getNumberLikesOfGifts() {
        return numberLikesOfGifts;
    }

    public Gift numberLikesOfGifts(Long numberLikesOfGifts) {
        this.numberLikesOfGifts = numberLikesOfGifts;
        return this;
    }

    public void setNumberLikesOfGifts(Long numberLikesOfGifts) {
        this.numberLikesOfGifts = numberLikesOfGifts;
    }

    public Long getNumberSeenOfGifts() {
        return numberSeenOfGifts;
    }

    public Gift numberSeenOfGifts(Long numberSeenOfGifts) {
        this.numberSeenOfGifts = numberSeenOfGifts;
        return this;
    }

    public void setNumberSeenOfGifts(Long numberSeenOfGifts) {
        this.numberSeenOfGifts = numberSeenOfGifts;
    }

    public ZonedDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public Gift dateOfCreation(ZonedDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
        return this;
    }

    public void setDateOfCreation(ZonedDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public byte[] getImage() {
        return image;
    }

    public Gift image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Gift imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public User getAuthorOfGift() {
        return authorOfGift;
    }

    public Gift authorOfGift(User user) {
        this.authorOfGift = user;
        return this;
    }

    public void setAuthorOfGift(User user) {
        this.authorOfGift = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gift)) {
            return false;
        }
        return id != null && id.equals(((Gift) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Gift{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", isAGift='" + isIsAGift() + "'" +
            ", reserved='" + isReserved() + "'" +
            ", city='" + getCity() + "'" +
            ", country='" + getCountry() + "'" +
            ", isAuction='" + isIsAuction() + "'" +
            ", startPrice=" + getStartPrice() +
            ", backgroundColor='" + getBackgroundColor() + "'" +
            ", numberLikesOfGifts=" + getNumberLikesOfGifts() +
            ", numberSeenOfGifts=" + getNumberSeenOfGifts() +
            ", dateOfCreation='" + getDateOfCreation() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            "}";
    }
}
