package com.azgzaw.app.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.azgzaw.app.domain.enumeration.LikeType;

/**
 * A LikeOfGift.
 */
@Document(collection = "like_of_gift")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "likeofgift")
public class LikeOfGift implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Field("type_of_like")
    private LikeType typeOfLike;

    @DBRef
    @Field("gift")
    private Gift gift;

    @DBRef
    @Field("authorOfLike")
    private User authorOfLike;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LikeType getTypeOfLike() {
        return typeOfLike;
    }

    public LikeOfGift typeOfLike(LikeType typeOfLike) {
        this.typeOfLike = typeOfLike;
        return this;
    }

    public void setTypeOfLike(LikeType typeOfLike) {
        this.typeOfLike = typeOfLike;
    }

    public Gift getGift() {
        return gift;
    }

    public LikeOfGift gift(Gift gift) {
        this.gift = gift;
        return this;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public User getAuthorOfLike() {
        return authorOfLike;
    }

    public LikeOfGift authorOfLike(User user) {
        this.authorOfLike = user;
        return this;
    }

    public void setAuthorOfLike(User user) {
        this.authorOfLike = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LikeOfGift)) {
            return false;
        }
        return id != null && id.equals(((LikeOfGift) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LikeOfGift{" +
            "id=" + getId() +
            ", typeOfLike='" + getTypeOfLike() + "'" +
            "}";
    }
}
