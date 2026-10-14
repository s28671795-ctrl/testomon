/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.settings;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Schema(name="GeneralSettingsDTO", description="General settings class structure")
public class GeneralSettingsDTO {
    @JsonProperty(value="api_token")
    @JSONField(name="api_token")
    @Schema(name="api_token", description="API token", example="XIuL7yrJTtzSX67tHIoXCj77N2BZ0ROk", implementation=String.class)
    private String apiToken;
    @JsonProperty(value="google_maps_api_key")
    @JSONField(name="google_maps_api_key")
    @Schema(name="google_maps_api_key", description="Google maps API key", example="AIzaSyDjKl7yrJTtzSX67tHIoXCj77N2BZ0ROk", implementation=String.class)
    private String googleMapsApiKey;
    @JsonProperty(value="user_latitude")
    @JSONField(name="user_latitude")
    @Schema(name="user_latitude", description="User latitude", example="50.4501", implementation=Double.class)
    private Double userLatitude;
    @JsonProperty(value="user_longitude")
    @JSONField(name="user_longitude")
    @Schema(name="user_longitude", description="User longitude", example="30.5234", implementation=Double.class)
    private Double userLongitude;
    @JsonProperty(value="metadata_history_days")
    @JSONField(name="metadata_history_days")
    @Schema(name="metadata_history_days", description="Metadata history days", example="30", implementation=Integer.class)
    private Integer metadataStorageDays;
    @JsonProperty(value="image_history_days")
    @JSONField(name="image_history_days")
    @Schema(name="image_history_days", description="Image history days", example="30", implementation=Integer.class)
    private Integer imageStorageDays;
    @JsonProperty(value="notifications_history_days")
    @JSONField(name="notifications_history_days")
    @Schema(name="notifications_history_days", description="Notifications history days", example="30", implementation=Integer.class)
    private Integer notificationsStorageDays;

    @JsonIgnore
    @JSONField(serialize=false)
    public Map<String, String> getSettingsMap() {
        HashMap<String, String> settings = new HashMap<String, String>();
        settings.put("google_maps_api_key", Optional.ofNullable(this.getGoogleMapsApiKey()).orElse(""));
        Optional.ofNullable(this.getUserLatitude()).filter(value -> value > 0.0).map(String::valueOf).ifPresent(value -> settings.put("user_latitude", (String)value));
        Optional.ofNullable(this.getUserLongitude()).filter(value -> value > 0.0).map(String::valueOf).ifPresent(value -> settings.put("user_longitude", (String)value));
        Optional.ofNullable(this.getMetadataStorageDays()).map(String::valueOf).ifPresent(value -> settings.put("metadata_history_days", (String)value));
        Optional.ofNullable(this.getImageStorageDays()).map(String::valueOf).ifPresent(value -> settings.put("image_history_days", (String)value));
        Optional.ofNullable(this.getNotificationsStorageDays()).map(String::valueOf).ifPresent(value -> settings.put("alerts_history_days", (String)value));
        return settings;
    }

    public String getApiToken() {
        return this.apiToken;
    }

    public String getGoogleMapsApiKey() {
        return this.googleMapsApiKey;
    }

    public Double getUserLatitude() {
        return this.userLatitude;
    }

    public Double getUserLongitude() {
        return this.userLongitude;
    }

    public Integer getMetadataStorageDays() {
        return this.metadataStorageDays;
    }

    public Integer getImageStorageDays() {
        return this.imageStorageDays;
    }

    public Integer getNotificationsStorageDays() {
        return this.notificationsStorageDays;
    }

    @JsonProperty(value="api_token")
    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    @JsonProperty(value="google_maps_api_key")
    public void setGoogleMapsApiKey(String googleMapsApiKey) {
        this.googleMapsApiKey = googleMapsApiKey;
    }

    @JsonProperty(value="user_latitude")
    public void setUserLatitude(Double userLatitude) {
        this.userLatitude = userLatitude;
    }

    @JsonProperty(value="user_longitude")
    public void setUserLongitude(Double userLongitude) {
        this.userLongitude = userLongitude;
    }

    @JsonProperty(value="metadata_history_days")
    public void setMetadataStorageDays(Integer metadataStorageDays) {
        this.metadataStorageDays = metadataStorageDays;
    }

    @JsonProperty(value="image_history_days")
    public void setImageStorageDays(Integer imageStorageDays) {
        this.imageStorageDays = imageStorageDays;
    }

    @JsonProperty(value="notifications_history_days")
    public void setNotificationsStorageDays(Integer notificationsStorageDays) {
        this.notificationsStorageDays = notificationsStorageDays;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GeneralSettingsDTO)) {
            return false;
        }
        GeneralSettingsDTO other = (GeneralSettingsDTO)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Double this$userLatitude = this.getUserLatitude();
        Double other$userLatitude = other.getUserLatitude();
        if (this$userLatitude == null ? other$userLatitude != null : !((Object)this$userLatitude).equals(other$userLatitude)) {
            return false;
        }
        Double this$userLongitude = this.getUserLongitude();
        Double other$userLongitude = other.getUserLongitude();
        if (this$userLongitude == null ? other$userLongitude != null : !((Object)this$userLongitude).equals(other$userLongitude)) {
            return false;
        }
        Integer this$metadataStorageDays = this.getMetadataStorageDays();
        Integer other$metadataStorageDays = other.getMetadataStorageDays();
        if (this$metadataStorageDays == null ? other$metadataStorageDays != null : !((Object)this$metadataStorageDays).equals(other$metadataStorageDays)) {
            return false;
        }
        Integer this$imageStorageDays = this.getImageStorageDays();
        Integer other$imageStorageDays = other.getImageStorageDays();
        if (this$imageStorageDays == null ? other$imageStorageDays != null : !((Object)this$imageStorageDays).equals(other$imageStorageDays)) {
            return false;
        }
        Integer this$notificationsStorageDays = this.getNotificationsStorageDays();
        Integer other$notificationsStorageDays = other.getNotificationsStorageDays();
        if (this$notificationsStorageDays == null ? other$notificationsStorageDays != null : !((Object)this$notificationsStorageDays).equals(other$notificationsStorageDays)) {
            return false;
        }
        String this$apiToken = this.getApiToken();
        String other$apiToken = other.getApiToken();
        if (this$apiToken == null ? other$apiToken != null : !this$apiToken.equals(other$apiToken)) {
            return false;
        }
        String this$googleMapsApiKey = this.getGoogleMapsApiKey();
        String other$googleMapsApiKey = other.getGoogleMapsApiKey();
        return !(this$googleMapsApiKey == null ? other$googleMapsApiKey != null : !this$googleMapsApiKey.equals(other$googleMapsApiKey));
    }

    protected boolean canEqual(Object other) {
        return other instanceof GeneralSettingsDTO;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Double $userLatitude = this.getUserLatitude();
        result = result * 59 + ($userLatitude == null ? 43 : ((Object)$userLatitude).hashCode());
        Double $userLongitude = this.getUserLongitude();
        result = result * 59 + ($userLongitude == null ? 43 : ((Object)$userLongitude).hashCode());
        Integer $metadataStorageDays = this.getMetadataStorageDays();
        result = result * 59 + ($metadataStorageDays == null ? 43 : ((Object)$metadataStorageDays).hashCode());
        Integer $imageStorageDays = this.getImageStorageDays();
        result = result * 59 + ($imageStorageDays == null ? 43 : ((Object)$imageStorageDays).hashCode());
        Integer $notificationsStorageDays = this.getNotificationsStorageDays();
        result = result * 59 + ($notificationsStorageDays == null ? 43 : ((Object)$notificationsStorageDays).hashCode());
        String $apiToken = this.getApiToken();
        result = result * 59 + ($apiToken == null ? 43 : $apiToken.hashCode());
        String $googleMapsApiKey = this.getGoogleMapsApiKey();
        result = result * 59 + ($googleMapsApiKey == null ? 43 : $googleMapsApiKey.hashCode());
        return result;
    }

    public String toString() {
        return "GeneralSettingsDTO(apiToken=" + this.getApiToken() + ", googleMapsApiKey=" + this.getGoogleMapsApiKey() + ", userLatitude=" + this.getUserLatitude() + ", userLongitude=" + this.getUserLongitude() + ", metadataStorageDays=" + this.getMetadataStorageDays() + ", imageStorageDays=" + this.getImageStorageDays() + ", notificationsStorageDays=" + this.getNotificationsStorageDays() + ")";
    }

    public GeneralSettingsDTO() {
    }

    public GeneralSettingsDTO(String apiToken, String googleMapsApiKey, Double userLatitude, Double userLongitude, Integer metadataStorageDays, Integer imageStorageDays, Integer notificationsStorageDays) {
        this.apiToken = apiToken;
        this.googleMapsApiKey = googleMapsApiKey;
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.metadataStorageDays = metadataStorageDays;
        this.imageStorageDays = imageStorageDays;
        this.notificationsStorageDays = notificationsStorageDays;
    }
}

