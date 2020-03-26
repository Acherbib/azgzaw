package com.azgzaw.app.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import com.azgzaw.app.domain.enumeration.Gender;

import com.azgzaw.app.domain.enumeration.MaritalStatus;

import com.azgzaw.app.domain.enumeration.Privacy;

/**
 * A Profile.
 */
@Document(collection = "profile")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @Field("number_of_minutes_connected")
    private Long numberOfMinutesConnected;

    @Field("verified")
    private Boolean verified;

    @Field("joined_date")
    private ZonedDateTime joinedDate;

    @Field("date_of_birth")
    private LocalDate dateOfBirth;

    @Field("date_of_birth_visible")
    private Boolean dateOfBirthVisible;

    @Field("phone_number")
    private Long phoneNumber;

    @Field("phone_number_visible")
    private Boolean phoneNumberVisible;

    @Field("about_me")
    private String aboutMe;

    @Field("about_me_visible")
    private Boolean aboutMeVisible;

    @Field("gender")
    private Gender gender;

    @Field("gender_visible")
    private Boolean genderVisible;

    @Field("marital_status")
    private MaritalStatus maritalStatus;

    @Field("marital_status_visible")
    private Boolean maritalStatusVisible;

    @NotNull
    @Field("profile_privacy")
    private Privacy profilePrivacy;

    @DBRef
    @Field("location")
    private Location location;

    @DBRef
    @Field("user")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getNumberOfMinutesConnected() {
        return numberOfMinutesConnected;
    }

    public Profile numberOfMinutesConnected(Long numberOfMinutesConnected) {
        this.numberOfMinutesConnected = numberOfMinutesConnected;
        return this;
    }

    public void setNumberOfMinutesConnected(Long numberOfMinutesConnected) {
        this.numberOfMinutesConnected = numberOfMinutesConnected;
    }

    public Boolean isVerified() {
        return verified;
    }

    public Profile verified(Boolean verified) {
        this.verified = verified;
        return this;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }

    public ZonedDateTime getJoinedDate() {
        return joinedDate;
    }

    public Profile joinedDate(ZonedDateTime joinedDate) {
        this.joinedDate = joinedDate;
        return this;
    }

    public void setJoinedDate(ZonedDateTime joinedDate) {
        this.joinedDate = joinedDate;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Profile dateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Boolean isDateOfBirthVisible() {
        return dateOfBirthVisible;
    }

    public Profile dateOfBirthVisible(Boolean dateOfBirthVisible) {
        this.dateOfBirthVisible = dateOfBirthVisible;
        return this;
    }

    public void setDateOfBirthVisible(Boolean dateOfBirthVisible) {
        this.dateOfBirthVisible = dateOfBirthVisible;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public Profile phoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean isPhoneNumberVisible() {
        return phoneNumberVisible;
    }

    public Profile phoneNumberVisible(Boolean phoneNumberVisible) {
        this.phoneNumberVisible = phoneNumberVisible;
        return this;
    }

    public void setPhoneNumberVisible(Boolean phoneNumberVisible) {
        this.phoneNumberVisible = phoneNumberVisible;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public Profile aboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
        return this;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public Boolean isAboutMeVisible() {
        return aboutMeVisible;
    }

    public Profile aboutMeVisible(Boolean aboutMeVisible) {
        this.aboutMeVisible = aboutMeVisible;
        return this;
    }

    public void setAboutMeVisible(Boolean aboutMeVisible) {
        this.aboutMeVisible = aboutMeVisible;
    }

    public Gender getGender() {
        return gender;
    }

    public Profile gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean isGenderVisible() {
        return genderVisible;
    }

    public Profile genderVisible(Boolean genderVisible) {
        this.genderVisible = genderVisible;
        return this;
    }

    public void setGenderVisible(Boolean genderVisible) {
        this.genderVisible = genderVisible;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public Profile maritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
        return this;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public Boolean isMaritalStatusVisible() {
        return maritalStatusVisible;
    }

    public Profile maritalStatusVisible(Boolean maritalStatusVisible) {
        this.maritalStatusVisible = maritalStatusVisible;
        return this;
    }

    public void setMaritalStatusVisible(Boolean maritalStatusVisible) {
        this.maritalStatusVisible = maritalStatusVisible;
    }

    public Privacy getProfilePrivacy() {
        return profilePrivacy;
    }

    public Profile profilePrivacy(Privacy profilePrivacy) {
        this.profilePrivacy = profilePrivacy;
        return this;
    }

    public void setProfilePrivacy(Privacy profilePrivacy) {
        this.profilePrivacy = profilePrivacy;
    }

    public Location getLocation() {
        return location;
    }

    public Profile location(Location location) {
        this.location = location;
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public User getUser() {
        return user;
    }

    public Profile user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", numberOfMinutesConnected=" + getNumberOfMinutesConnected() +
            ", verified='" + isVerified() + "'" +
            ", joinedDate='" + getJoinedDate() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", dateOfBirthVisible='" + isDateOfBirthVisible() + "'" +
            ", phoneNumber=" + getPhoneNumber() +
            ", phoneNumberVisible='" + isPhoneNumberVisible() + "'" +
            ", aboutMe='" + getAboutMe() + "'" +
            ", aboutMeVisible='" + isAboutMeVisible() + "'" +
            ", gender='" + getGender() + "'" +
            ", genderVisible='" + isGenderVisible() + "'" +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", maritalStatusVisible='" + isMaritalStatusVisible() + "'" +
            ", profilePrivacy='" + getProfilePrivacy() + "'" +
            "}";
    }
}
