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

import com.azgzaw.app.domain.enumeration.Privacy;

/**
 * A Citation.
 */
@Document(collection = "citation")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "citation")
public class Citation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Field("description")
    private String description;

    @Field("green_citation_media")
    private byte[] greenCitationMedia;

    @Field("green_citation_media_content_type")
    private String greenCitationMediaContentType;

    @NotNull
    @Field("date_of_creation")
    private ZonedDateTime dateOfCreation;

    @Field("background_color")
    private String backgroundColor;

    @NotNull
    @Field("green_citation_privacy")
    private Privacy greenCitationPrivacy;

    @DBRef
    @Field("authorOfCitation")
    @JsonIgnoreProperties("citations")
    private User authorOfCitation;

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

    public Citation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getGreenCitationMedia() {
        return greenCitationMedia;
    }

    public Citation greenCitationMedia(byte[] greenCitationMedia) {
        this.greenCitationMedia = greenCitationMedia;
        return this;
    }

    public void setGreenCitationMedia(byte[] greenCitationMedia) {
        this.greenCitationMedia = greenCitationMedia;
    }

    public String getGreenCitationMediaContentType() {
        return greenCitationMediaContentType;
    }

    public Citation greenCitationMediaContentType(String greenCitationMediaContentType) {
        this.greenCitationMediaContentType = greenCitationMediaContentType;
        return this;
    }

    public void setGreenCitationMediaContentType(String greenCitationMediaContentType) {
        this.greenCitationMediaContentType = greenCitationMediaContentType;
    }

    public ZonedDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public Citation dateOfCreation(ZonedDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
        return this;
    }

    public void setDateOfCreation(ZonedDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public Citation backgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Privacy getGreenCitationPrivacy() {
        return greenCitationPrivacy;
    }

    public Citation greenCitationPrivacy(Privacy greenCitationPrivacy) {
        this.greenCitationPrivacy = greenCitationPrivacy;
        return this;
    }

    public void setGreenCitationPrivacy(Privacy greenCitationPrivacy) {
        this.greenCitationPrivacy = greenCitationPrivacy;
    }

    public User getAuthorOfCitation() {
        return authorOfCitation;
    }

    public Citation authorOfCitation(User user) {
        this.authorOfCitation = user;
        return this;
    }

    public void setAuthorOfCitation(User user) {
        this.authorOfCitation = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Citation)) {
            return false;
        }
        return id != null && id.equals(((Citation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Citation{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", greenCitationMedia='" + getGreenCitationMedia() + "'" +
            ", greenCitationMediaContentType='" + getGreenCitationMediaContentType() + "'" +
            ", dateOfCreation='" + getDateOfCreation() + "'" +
            ", backgroundColor='" + getBackgroundColor() + "'" +
            ", greenCitationPrivacy='" + getGreenCitationPrivacy() + "'" +
            "}";
    }
}
