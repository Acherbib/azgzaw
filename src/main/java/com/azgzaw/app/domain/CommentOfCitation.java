package com.azgzaw.app.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A CommentOfCitation.
 */
@Document(collection = "comment_of_citation")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "commentofcitation")
public class CommentOfCitation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Field("body_comment")
    private String bodyComment;

    @Field("comment_of_citation_image")
    private byte[] commentOfCitationImage;

    @Field("comment_of_citation_image_content_type")
    private String commentOfCitationImageContentType;

    @Field("date_of_creation")
    private ZonedDateTime dateOfCreation;

    @DBRef
    @Field("citation")
    private Citation citation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBodyComment() {
        return bodyComment;
    }

    public CommentOfCitation bodyComment(String bodyComment) {
        this.bodyComment = bodyComment;
        return this;
    }

    public void setBodyComment(String bodyComment) {
        this.bodyComment = bodyComment;
    }

    public byte[] getCommentOfCitationImage() {
        return commentOfCitationImage;
    }

    public CommentOfCitation commentOfCitationImage(byte[] commentOfCitationImage) {
        this.commentOfCitationImage = commentOfCitationImage;
        return this;
    }

    public void setCommentOfCitationImage(byte[] commentOfCitationImage) {
        this.commentOfCitationImage = commentOfCitationImage;
    }

    public String getCommentOfCitationImageContentType() {
        return commentOfCitationImageContentType;
    }

    public CommentOfCitation commentOfCitationImageContentType(String commentOfCitationImageContentType) {
        this.commentOfCitationImageContentType = commentOfCitationImageContentType;
        return this;
    }

    public void setCommentOfCitationImageContentType(String commentOfCitationImageContentType) {
        this.commentOfCitationImageContentType = commentOfCitationImageContentType;
    }

    public ZonedDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public CommentOfCitation dateOfCreation(ZonedDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
        return this;
    }

    public void setDateOfCreation(ZonedDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Citation getCitation() {
        return citation;
    }

    public CommentOfCitation citation(Citation citation) {
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
        if (!(o instanceof CommentOfCitation)) {
            return false;
        }
        return id != null && id.equals(((CommentOfCitation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CommentOfCitation{" +
            "id=" + getId() +
            ", bodyComment='" + getBodyComment() + "'" +
            ", commentOfCitationImage='" + getCommentOfCitationImage() + "'" +
            ", commentOfCitationImageContentType='" + getCommentOfCitationImageContentType() + "'" +
            ", dateOfCreation='" + getDateOfCreation() + "'" +
            "}";
    }
}
