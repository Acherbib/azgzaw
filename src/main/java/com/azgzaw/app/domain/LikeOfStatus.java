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
 * A LikeOfStatus.
 */
@Document(collection = "like_of_status")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "likeofstatus")
public class LikeOfStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Field("type_of_like")
    private LikeType typeOfLike;

    @DBRef
    @Field("status")
    private Status status;

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

    public LikeOfStatus typeOfLike(LikeType typeOfLike) {
        this.typeOfLike = typeOfLike;
        return this;
    }

    public void setTypeOfLike(LikeType typeOfLike) {
        this.typeOfLike = typeOfLike;
    }

    public Status getStatus() {
        return status;
    }

    public LikeOfStatus status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getAuthorOfLike() {
        return authorOfLike;
    }

    public LikeOfStatus authorOfLike(User user) {
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
        if (!(o instanceof LikeOfStatus)) {
            return false;
        }
        return id != null && id.equals(((LikeOfStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LikeOfStatus{" +
            "id=" + getId() +
            ", typeOfLike='" + getTypeOfLike() + "'" +
            "}";
    }
}
