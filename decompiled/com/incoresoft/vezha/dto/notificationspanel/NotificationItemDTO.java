/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.notificationspanel;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.notifications.Notification;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="NotificationItemDTO", description="Notification item class structure")
public final class NotificationItemDTO {
    @Schema(name="plugin_id", description="Plugin id", implementation=String.class, example="object_in_zone")
    @JSONField(name="plugin_id")
    private final String pluginId;
    @Schema(name="notification", description="Notification item", implementation=Notification.class)
    @JSONField(name="notification")
    private final Notification notification;

    public NotificationItemDTO(String pluginId, Notification entity) {
        this.pluginId = pluginId;
        this.notification = entity;
    }

    public String getPluginId() {
        return this.pluginId;
    }

    public Notification getNotification() {
        return this.notification;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NotificationItemDTO)) {
            return false;
        }
        NotificationItemDTO other = (NotificationItemDTO)o;
        String this$pluginId = this.getPluginId();
        String other$pluginId = other.getPluginId();
        if (this$pluginId == null ? other$pluginId != null : !this$pluginId.equals(other$pluginId)) {
            return false;
        }
        Notification this$notification = this.getNotification();
        Notification other$notification = other.getNotification();
        return !(this$notification == null ? other$notification != null : !this$notification.equals(other$notification));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $pluginId = this.getPluginId();
        result = result * 59 + ($pluginId == null ? 43 : $pluginId.hashCode());
        Notification $notification = this.getNotification();
        result = result * 59 + ($notification == null ? 43 : $notification.hashCode());
        return result;
    }

    public String toString() {
        return "NotificationItemDTO(pluginId=" + this.getPluginId() + ", notification=" + String.valueOf(this.getNotification()) + ")";
    }
}

