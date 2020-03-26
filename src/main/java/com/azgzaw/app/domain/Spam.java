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
 * A Spam.
 */
@Document(collection = "spam")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "spam")
public class Spam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Field("cause")
    private String cause;

    @NotNull
    @Field("date_of_creation")
    private ZonedDateTime dateOfCreation;

    @DBRef
    @Field("citation")
    @JsonIgnoreProperties("spams")
    private Citation citation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCause() {
        return cause;
    }

    public Spam cause(String cause) {
        this.cause = cause;
        return this;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public ZonedDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public Spam dateOfCreation(ZonedDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
        return this;
    }

    public void setDateOfCreation(ZonedDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Citation getCitation() {
        return citation;
    }

    public Spam citation(Citation citation) {
        this.citation = citation;
        return this;
    }

    public void setCitation(Citation citation) {
        this.citation = citation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Spam)) {
            return false;
        }
        return id != null && id.equals(((Spam) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Spam{" +
            "id=" + getId() +
            ", cause='" + getCause() + "'" +
            ", dateOfCreation='" + getDateOfCreation() + "'" +
            "}";
    }
}
