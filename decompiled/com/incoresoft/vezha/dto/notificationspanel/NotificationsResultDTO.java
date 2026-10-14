/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.dto.notificationspanel;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.notifications.Notification;
import com.incoresoft.middleware.notifications.NotificationItem;
import com.incoresoft.middleware.notifications.NotificationsResult;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;

@Schema(name="NotificationsResultDTO", description="NotificationsResultDTO class structure")
public final class NotificationsResultDTO {
    @Schema(name="count", description="Count of notifications", implementation=Integer.class, example="1")
    @JSONField(name="count")
    private final int count;
    @ArraySchema(arraySchema=@Schema(name="notifications", description="Notifications", implementation=NotificationItem.class, example="[]"))
    @JSONField(name="notifications")
    private final Collection<? extends Notification> notifications;
    @Schema(name="plugin_id", description="Plugin name/id", implementation=String.class, example="alpr")
    @JSONField(name="plugin_id")
    private final String pluginId;

    public NotificationsResultDTO(String pluginId, NotificationsResult entity) {
        this.pluginId = pluginId;
        this.count = entity.getCount();
        this.notifications = entity.getNotifications();
    }

    public int getCount() {
        return this.count;
    }

    public Collection<? extends Notification> getNotifications() {
        return this.notifications;
    }

    public String getPluginId() {
        return this.pluginId;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NotificationsResultDTO)) {
            return false;
        }
        NotificationsResultDTO other = (NotificationsResultDTO)o;
        if (this.getCount() != other.getCount()) {
            return false;
        }
        Collection<? extends Notification> this$notifications = this.getNotifications();
        Collection<? extends Notification> other$notifications = other.getNotifications();
        if (this$notifications == null ? other$notifications != null : !((Object)this$notifications).equals(other$notifications)) {
            return false;
        }
        String this$pluginId = this.getPluginId();
        String other$pluginId = other.getPluginId();
        return !(this$pluginId == null ? other$pluginId != null : !this$pluginId.equals(other$pluginId));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getCount();
        Collection<? extends Notification> $notifications = this.getNotifications();
        result = result * 59 + ($notifications == null ? 43 : ((Object)$notifications).hashCode());
        String $pluginId = this.getPluginId();
        result = result * 59 + ($pluginId == null ? 43 : $pluginId.hashCode());
        return result;
    }

    public String toString() {
        return "NotificationsResultDTO(count=" + this.getCount() + ", notifications=" + String.valueOf(this.getNotifications()) + ", pluginId=" + this.getPluginId() + ")";
    }
}

