package com.azgzaw.app.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Message.
 */
@Document(collection = "message")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Field("content")
    private String content;

    @NotNull
    @Field("date_of_send")
    private Instant dateOfSend;

    @Field("media")
    private byte[] media;

    @Field("media_content_type")
    private String mediaContentType;

    @Field("seen")
    private Boolean seen;

    @DBRef
    @Field("authorOfMessage")
    @JsonIgnoreProperties("messages")
    private User authorOfMessage;

    @DBRef
    @Field("receiverOfMessage")
    @JsonIgnoreProperties("messages")
    private User receiverOfMessage;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Message content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getDateOfSend() {
        return dateOfSend;
    }

    public Message dateOfSend(Instant dateOfSend) {
        this.dateOfSend = dateOfSend;
        return this;
    }

    public void setDateOfSend(Instant dateOfSend) {
        this.dateOfSend = dateOfSend;
    }

    public byte[] getMedia() {
        return media;
    }

    public Message media(byte[] media) {
        this.media = media;
        return this;
    }

    public void setMedia(byte[] media) {
        this.media = media;
    }

    public String getMediaContentType() {
        return mediaContentType;
    }

    public Message mediaContentType(String mediaContentType) {
        this.mediaContentType = mediaContentType;
        return this;
    }

    public void setMediaContentType(String mediaContentType) {
        this.mediaContentType = mediaContentType;
    }

    public Boolean isSeen() {
        return seen;
    }

    public Message seen(Boolean seen) {
        this.seen = seen;
        return this;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public User getAuthorOfMessage() {
        return authorOfMessage;
    }

    public Message authorOfMessage(User user) {
        this.authorOfMessage = user;
        return this;
    }

    public void setAuthorOfMessage(User user) {
        this.authorOfMessage = user;
    }

    public User getReceiverOfMessage() {
        return receiverOfMessage;
    }

    public Message receiverOfMessage(User user) {
        this.receiverOfMessage = user;
        return this;
    }

    public void setReceiverOfMessage(User user) {
        this.receiverOfMessage = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Message)) {
            return false;
        }
        return id != null && id.equals(((Message) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Message{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", dateOfSend='" + getDateOfSend() + "'" +
            ", media='" + getMedia() + "'" +
            ", mediaContentType='" + getMediaContentType() + "'" +
            ", seen='" + isSeen() + "'" +
            "}";
    }
}
