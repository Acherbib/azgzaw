package com.azgzaw.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

import com.azgzaw.app.domain.enumeration.Privacy;

/**
 * A Status.
 */
@Document(collection = "status")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "status")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Field("description")
    private String description;

    @Field("status_media")
    private byte[] statusMedia;

    @Field("status_media_content_type")
    private String statusMediaContentType;

    @Field("number_seen_of_status")
    private Long numberSeenOfStatus;

    @NotNull
    @Field("status_privacy")
    private Privacy statusPrivacy;

    @DBRef
    @Field("authorOfStatus")
    @JsonIgnoreProperties("statuses")
    private User authorOfStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Status description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getStatusMedia() {
        return statusMedia;
    }

    public Status statusMedia(byte[] statusMedia) {
        this.statusMedia = statusMedia;
        return this;
    }

    public void setStatusMedia(byte[] statusMedia) {
        this.statusMedia = statusMedia;
    }

    public String getStatusMediaContentType() {
        return statusMediaContentType;
    }

    public Status statusMediaContentType(String statusMediaContentType) {
        this.statusMediaContentType = statusMediaContentType;
        return this;
    }

    public void setStatusMediaContentType(String statusMediaContentType) {
        this.statusMediaContentType = statusMediaContentType;
    }

    public Long getNumberSeenOfStatus() {
        return numberSeenOfStatus;
    }

    public Status numberSeenOfStatus(Long numberSeenOfStatus) {
        this.numberSeenOfStatus = numberSeenOfStatus;
        return this;
    }

    public void setNumberSeenOfStatus(Long numberSeenOfStatus) {
        this.numberSeenOfStatus = numberSeenOfStatus;
    }

    public Privacy getStatusPrivacy() {
        return statusPrivacy;
    }

    public Status statusPrivacy(Privacy statusPrivacy) {
        this.statusPrivacy = statusPrivacy;
        return this;
    }

    public void setStatusPrivacy(Privacy statusPrivacy) {
        this.statusPrivacy = statusPrivacy;
    }

    public User getAuthorOfStatus() {
        return authorOfStatus;
    }

    public Status authorOfStatus(User user) {
        this.authorOfStatus = user;
        return this;
    }

    public void setAuthorOfStatus(User user) {
        this.authorOfStatus = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Status)) {
            return false;
        }
        return id != null && id.equals(((Status) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Status{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", statusMedia='" + getStatusMedia() + "'" +
            ", statusMediaContentType='" + getStatusMediaContentType() + "'" +
            ", numberSeenOfStatus=" + getNumberSeenOfStatus() +
            ", statusPrivacy='" + getStatusPrivacy() + "'" +
            "}";
    }
}
