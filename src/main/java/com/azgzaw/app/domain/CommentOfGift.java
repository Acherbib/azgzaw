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
 * A CommentOfGift.
 */
@Document(collection = "comment_of_gift")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "commentofgift")
public class CommentOfGift implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Field("body_comment")
    private String bodyComment;

    @Field("comment_of_gift_image")
    private byte[] commentOfGiftImage;

    @Field("comment_of_gift_image_content_type")
    private String commentOfGiftImageContentType;

    @Field("date_of_creation")
    private ZonedDateTime dateOfCreation;

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

    public String getBodyComment() {
        return bodyComment;
    }

    public CommentOfGift bodyComment(String bodyComment) {
        this.bodyComment = bodyComment;
        return this;
    }

    public void setBodyComment(String bodyComment) {
        this.bodyComment = bodyComment;
    }

    public byte[] getCommentOfGiftImage() {
        return commentOfGiftImage;
    }

    public CommentOfGift commentOfGiftImage(byte[] commentOfGiftImage) {
        this.commentOfGiftImage = commentOfGiftImage;
        return this;
    }

    public void setCommentOfGiftImage(byte[] commentOfGiftImage) {
        this.commentOfGiftImage = commentOfGiftImage;
    }

    public String getCommentOfGiftImageContentType() {
        return commentOfGiftImageContentType;
    }

    public CommentOfGift commentOfGiftImageContentType(String commentOfGiftImageContentType) {
        this.commentOfGiftImageContentType = commentOfGiftImageContentType;
        return this;
    }

    public void setCommentOfGiftImageContentType(String commentOfGiftImageContentType) {
        this.commentOfGiftImageContentType = commentOfGiftImageContentType;
    }

    public ZonedDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public CommentOfGift dateOfCreation(ZonedDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
        return this;
    }

    public void setDateOfCreation(ZonedDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Gift getGift() {
        return gift;
    }

    public CommentOfGift gift(Gift gift) {
        this.gift = gift;
        return this;
    }

    public void setGift(Gift gift) {
        this.gift = gift;
    }

    public User getAuthor() {
        return author;
    }

    public CommentOfGift author(User user) {
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
        if (!(o instanceof CommentOfGift)) {
            return false;
        }
        return id != null && id.equals(((CommentOfGift) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CommentOfGift{" +
            "id=" + getId() +
            ", bodyComment='" + getBodyComment() + "'" +
            ", commentOfGiftImage='" + getCommentOfGiftImage() + "'" +
            ", commentOfGiftImageContentType='" + getCommentOfGiftImageContentType() + "'" +
            ", dateOfCreation='" + getDateOfCreation() + "'" +
            "}";
    }
}
