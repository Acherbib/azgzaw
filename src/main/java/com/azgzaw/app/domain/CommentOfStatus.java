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
 * A CommentOfStatus.
 */
@Document(collection = "comment_of_status")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "commentofstatus")
public class CommentOfStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Field("body_comment")
    private String bodyComment;

    @Field("comment_of_status_image")
    private byte[] commentOfStatusImage;

    @Field("comment_of_status_image_content_type")
    private String commentOfStatusImageContentType;

    @Field("media_type")
    private String mediaType;

    @Field("date_of_creation")
    private ZonedDateTime dateOfCreation;

    @DBRef
    @Field("status")
    private Status status;

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

    public String getBodyComment() {
        return bodyComment;
    }

    public CommentOfStatus bodyComment(String bodyComment) {
        this.bodyComment = bodyComment;
        return this;
    }

    public void setBodyComment(String bodyComment) {
        this.bodyComment = bodyComment;
    }

    public byte[] getCommentOfStatusImage() {
        return commentOfStatusImage;
    }

    public CommentOfStatus commentOfStatusImage(byte[] commentOfStatusImage) {
        this.commentOfStatusImage = commentOfStatusImage;
        return this;
    }

    public void setCommentOfStatusImage(byte[] commentOfStatusImage) {
        this.commentOfStatusImage = commentOfStatusImage;
    }

    public String getCommentOfStatusImageContentType() {
        return commentOfStatusImageContentType;
    }

    public CommentOfStatus commentOfStatusImageContentType(String commentOfStatusImageContentType) {
        this.commentOfStatusImageContentType = commentOfStatusImageContentType;
        return this;
    }

    public void setCommentOfStatusImageContentType(String commentOfStatusImageContentType) {
        this.commentOfStatusImageContentType = commentOfStatusImageContentType;
    }

    public String getMediaType() {
        return mediaType;
    }

    public CommentOfStatus mediaType(String mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public ZonedDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public CommentOfStatus dateOfCreation(ZonedDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
        return this;
    }

    public void setDateOfCreation(ZonedDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Status getStatus() {
        return status;
    }

    public CommentOfStatus status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getAuthor() {
        return author;
    }

    public CommentOfStatus author(User user) {
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
        if (!(o instanceof CommentOfStatus)) {
            return false;
        }
        return id != null && id.equals(((CommentOfStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CommentOfStatus{" +
            "id=" + getId() +
            ", bodyComment='" + getBodyComment() + "'" +
            ", commentOfStatusImage='" + getCommentOfStatusImage() + "'" +
            ", commentOfStatusImageContentType='" + getCommentOfStatusImageContentType() + "'" +
            ", mediaType='" + getMediaType() + "'" +
            ", dateOfCreation='" + getDateOfCreation() + "'" +
            "}";
    }
}
