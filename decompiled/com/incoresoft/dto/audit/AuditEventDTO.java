/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.dto.audit;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.incoresoft.middleware.audit.AuditEvent;
import com.incoresoft.middleware.model.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AuditEventDTO {
    @JsonProperty(value="timestamp")
    @JSONField(name="timestamp")
    @Schema(name="timestamp", description="Timestamp of the event(unix timestamp in milliseconds)", example="1627987200000", implementation=Long.class)
    private final Instant timestamp;
    @JsonProperty(value="user_id")
    @JSONField(name="user_id")
    @Schema(name="user_id", description="Id of the user who performed the action", example="1")
    private final long userId;
    @JsonProperty(value="user_ip")
    @JSONField(name="user_ip")
    @Schema(name="user_ip", description="Ip of the user who performed the action", example="192.168.0.1")
    private final String userIp;
    @JsonProperty(value="user_login")
    @JSONField(name="user_login")
    @Schema(name="user_login", description="Login of the user who performed the action", example="admin")
    private final String userLogin;
    @JsonProperty(value="source_id")
    @JSONField(name="source_id")
    @Schema(name="source_id", description="Id of the source of the event", example="1")
    private final String sourceId;
    @JsonProperty(value="event_category")
    @JSONField(name="event_category")
    @Schema(name="event_category", description="Category of the event", example="system_management")
    private final String eventCategory;
    @JsonProperty(value="event_action")
    @JSONField(name="event_action")
    @Schema(name="event_action", description="Action of the event", example="layout_added")
    private final String eventAction;
    @JsonProperty(value="message")
    @JSONField(name="message")
    @Schema(name="message", description="Message of the event", example="Layout added")
    private final String message;
    @JsonProperty(value="localized_event_action")
    @JSONField(name="localized_event_action")
    @Schema(name="localized_event_action", description="Localization key of the event event action", example="LAYOUT_ADDED_AUDIT_ACTION")
    private final String localizedEventAction;
    @JsonProperty(value="localized_event_category")
    @JSONField(name="localized_event_category")
    @Schema(name="localized_event_category", description="Localization key of the event event category", example="SYSTEM_MANAGEMENT_AUDIT_CATEGORY")
    private final String localizedEventCategory;

    public AuditEventDTO(@NotNull AuditEvent inner, @Nullable User user) {
        this.timestamp = inner.getTimestamp();
        this.userId = inner.getUserId();
        this.userIp = inner.getUserIp();
        this.userLogin = user != null ? user.getEmail() : "";
        this.sourceId = inner.getSourceId();
        this.eventCategory = inner.getCategory().toString();
        this.eventAction = inner.getAction().toString();
        this.message = inner.getMessage();
        this.localizedEventAction = inner.getAction().getLocalizationKey();
        this.localizedEventCategory = inner.getCategory().getLocalizationKey();
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public long getUserId() {
        return this.userId;
    }

    public String getUserIp() {
        return this.userIp;
    }

    public String getUserLogin() {
        return this.userLogin;
    }

    public String getSourceId() {
        return this.sourceId;
    }

    public String getEventCategory() {
        return this.eventCategory;
    }

    public String getEventAction() {
        return this.eventAction;
    }

    public String getMessage() {
        return this.message;
    }

    public String getLocalizedEventAction() {
        return this.localizedEventAction;
    }

    public String getLocalizedEventCategory() {
        return this.localizedEventCategory;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AuditEventDTO)) {
            return false;
        }
        AuditEventDTO other = (AuditEventDTO)o;
        if (this.getUserId() != other.getUserId()) {
            return false;
        }
        Instant this$timestamp = this.getTimestamp();
        Instant other$timestamp = other.getTimestamp();
        if (this$timestamp == null ? other$timestamp != null : !((Object)this$timestamp).equals(other$timestamp)) {
            return false;
        }
        String this$userIp = this.getUserIp();
        String other$userIp = other.getUserIp();
        if (this$userIp == null ? other$userIp != null : !this$userIp.equals(other$userIp)) {
            return false;
        }
        String this$userLogin = this.getUserLogin();
        String other$userLogin = other.getUserLogin();
        if (this$userLogin == null ? other$userLogin != null : !this$userLogin.equals(other$userLogin)) {
            return false;
        }
        String this$sourceId = this.getSourceId();
        String other$sourceId = other.getSourceId();
        if (this$sourceId == null ? other$sourceId != null : !this$sourceId.equals(other$sourceId)) {
            return false;
        }
        String this$eventCategory = this.getEventCategory();
        String other$eventCategory = other.getEventCategory();
        if (this$eventCategory == null ? other$eventCategory != null : !this$eventCategory.equals(other$eventCategory)) {
            return false;
        }
        String this$eventAction = this.getEventAction();
        String other$eventAction = other.getEventAction();
        if (this$eventAction == null ? other$eventAction != null : !this$eventAction.equals(other$eventAction)) {
            return false;
        }
        String this$message = this.getMessage();
        String other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) {
            return false;
        }
        String this$localizedEventAction = this.getLocalizedEventAction();
        String other$localizedEventAction = other.getLocalizedEventAction();
        if (this$localizedEventAction == null ? other$localizedEventAction != null : !this$localizedEventAction.equals(other$localizedEventAction)) {
            return false;
        }
        String this$localizedEventCategory = this.getLocalizedEventCategory();
        String other$localizedEventCategory = other.getLocalizedEventCategory();
        return !(this$localizedEventCategory == null ? other$localizedEventCategory != null : !this$localizedEventCategory.equals(other$localizedEventCategory));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        long $userId = this.getUserId();
        result = result * 59 + (int)($userId >>> 32 ^ $userId);
        Instant $timestamp = this.getTimestamp();
        result = result * 59 + ($timestamp == null ? 43 : ((Object)$timestamp).hashCode());
        String $userIp = this.getUserIp();
        result = result * 59 + ($userIp == null ? 43 : $userIp.hashCode());
        String $userLogin = this.getUserLogin();
        result = result * 59 + ($userLogin == null ? 43 : $userLogin.hashCode());
        String $sourceId = this.getSourceId();
        result = result * 59 + ($sourceId == null ? 43 : $sourceId.hashCode());
        String $eventCategory = this.getEventCategory();
        result = result * 59 + ($eventCategory == null ? 43 : $eventCategory.hashCode());
        String $eventAction = this.getEventAction();
        result = result * 59 + ($eventAction == null ? 43 : $eventAction.hashCode());
        String $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        String $localizedEventAction = this.getLocalizedEventAction();
        result = result * 59 + ($localizedEventAction == null ? 43 : $localizedEventAction.hashCode());
        String $localizedEventCategory = this.getLocalizedEventCategory();
        result = result * 59 + ($localizedEventCategory == null ? 43 : $localizedEventCategory.hashCode());
        return result;
    }

    public String toString() {
        return "AuditEventDTO(timestamp=" + String.valueOf(this.getTimestamp()) + ", userId=" + this.getUserId() + ", userIp=" + this.getUserIp() + ", userLogin=" + this.getUserLogin() + ", sourceId=" + this.getSourceId() + ", eventCategory=" + this.getEventCategory() + ", eventAction=" + this.getEventAction() + ", message=" + this.getMessage() + ", localizedEventAction=" + this.getLocalizedEventAction() + ", localizedEventCategory=" + this.getLocalizedEventCategory() + ")";
    }
}

