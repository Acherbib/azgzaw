package com.azgzaw.app.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Follows.
 */
@Document(collection = "follows")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "follows")
public class Follows implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @Field("following_start_date")
    private Instant followingStartDate;

    @Field("accepted")
    private Boolean accepted;

    @Field("blocked")
    private Boolean blocked;

    @DBRef
    @Field("profile")
    private Profile profile;

    @DBRef
    @Field("friend")
    private User friend;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getFollowingStartDate() {
        return followingStartDate;
    }

    public Follows followingStartDate(Instant followingStartDate) {
        this.followingStartDate = followingStartDate;
        return this;
    }

    public void setFollowingStartDate(Instant followingStartDate) {
        this.followingStartDate = followingStartDate;
    }

    public Boolean isAccepted() {
        return accepted;
    }

    public Follows accepted(Boolean accepted) {
        this.accepted = accepted;
        return this;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Boolean isBlocked() {
        return blocked;
    }

    public Follows blocked(Boolean blocked) {
        this.blocked = blocked;
        return this;
    }

    public void setBlocked(Boolean blocked) {
        this.blocked = blocked;
    }

    public Profile getProfile() {
        return profile;
    }

    public Follows profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public User getFriend() {
        return friend;
    }

    public Follows friend(User user) {
        this.friend = user;
        return this;
    }

    public void setFriend(User user) {
        this.friend = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Follows)) {
            return false;
        }
        return id != null && id.equals(((Follows) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Follows{" +
            "id=" + getId() +
            ", followingStartDate='" + getFollowingStartDate() + "'" +
            ", accepted='" + isAccepted() + "'" +
            ", blocked='" + isBlocked() + "'" +
            "}";
    }
}
