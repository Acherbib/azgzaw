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
 * A LikeOfCitation.
 */
@Document(collection = "like_of_citation")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "likeofcitation")
public class LikeOfCitation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Field("type_of_like")
    private LikeType typeOfLike;

    @DBRef
    @Field("citation")
    private Citation citation;

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

    public LikeOfCitation typeOfLike(LikeType typeOfLike) {
        this.typeOfLike = typeOfLike;
        return this;
    }

    public void setTypeOfLike(LikeType typeOfLike) {
        this.typeOfLike = typeOfLike;
    }

    public Citation getCitation() {
        return citation;
    }

    public LikeOfCitation citation(Citation citation) {
        this.citation = citation;
        return this;
    }

    public void setCitation(Citation citation) {
        this.citation = citation;
    }

    public User getAuthorOfLike() {
        return authorOfLike;
    }

    public LikeOfCitation authorOfLike(User user) {
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
        if (!(o instanceof LikeOfCitation)) {
            return false;
        }
        return id != null && id.equals(((LikeOfCitation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LikeOfCitation{" +
            "id=" + getId() +
            ", typeOfLike='" + getTypeOfLike() + "'" +
            "}";
    }
}
