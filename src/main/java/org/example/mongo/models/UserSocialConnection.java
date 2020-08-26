package org.example.mongo.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

/**
 * The MongoDB collection for the spring social connections.
 * <p>
 * Note: After conversion to {@link UserSocialConnection} all OAuth tokens are
 * encrypted.
 *
 *
 * @author mendlik
 */
@Document(collection = "UserSocialConnection")
public class UserSocialConnection {

    public UserSocialConnection() {
        super();
    }

    public UserSocialConnection(String userId, String providerId, String providerUserId, int rank,
                                String displayName, String profileUrl, String imageUrl, String accessToken, String secret,
                                String refreshToken, Long expireTime) {
        super();
        this.userId = userId;
        this.providerId = providerId;
        this.providerUserId = providerUserId;
        this.displayName = displayName;
        this.profileUrl = profileUrl;
        this.imageUrl = imageUrl;
        this.accessToken = accessToken;
        this.secret = secret;
        this.refreshToken = refreshToken;
        this.expireTime = expireTime;
    }

    @Id
    private ObjectId id;
    private String userId;
    private String providerId;
    private String providerUserId;
    private String displayName;
    private String profileUrl;
    private String imageUrl;
    private String accessToken;
    private String secret;
    private String refreshToken;
    private Long expireTime;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserSocialConnection)) return false;
        UserSocialConnection that = (UserSocialConnection) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getUserId(), that.getUserId()) &&
                Objects.equals(getProviderId(), that.getProviderId()) &&
                Objects.equals(getProviderUserId(), that.getProviderUserId()) &&
                Objects.equals(getDisplayName(), that.getDisplayName()) &&
                Objects.equals(getProfileUrl(), that.getProfileUrl()) &&
                Objects.equals(getImageUrl(), that.getImageUrl()) &&
                Objects.equals(getAccessToken(), that.getAccessToken()) &&
                Objects.equals(getSecret(), that.getSecret()) &&
                Objects.equals(getRefreshToken(), that.getRefreshToken()) &&
                Objects.equals(getExpireTime(), that.getExpireTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getProviderId(), getProviderUserId(), getDisplayName(), getProfileUrl(), getImageUrl(), getAccessToken(), getSecret(), getRefreshToken(), getExpireTime());
    }
}