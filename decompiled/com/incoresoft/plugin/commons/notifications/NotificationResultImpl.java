/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.notifications;

import com.incoresoft.middleware.notifications.Notification;
import com.incoresoft.middleware.notifications.NotificationsResult;
import java.util.Collection;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public final class NotificationResultImpl
implements NotificationsResult {
    private final String pluginId;
    private final int count;
    private final Collection<? extends Notification> items;

    public NotificationResultImpl(String pluginId, int count, @NotNull Collection<? extends Notification> items) {
        this.pluginId = Objects.requireNonNull(pluginId);
        this.count = count;
        this.items = Objects.requireNonNull(items);
    }

    @Override
    public int getCount() {
        return this.count;
    }

    @Override
    public Collection<? extends Notification> getNotifications() {
        return this.items;
    }

    @Override
    public String pluginId() {
        return this.pluginId;
    }

    public String getPluginId() {
        return this.pluginId;
    }

    public Collection<? extends Notification> getItems() {
        return this.items;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NotificationResultImpl)) {
            return false;
        }
        NotificationResultImpl other = (NotificationResultImpl)o;
        if (this.getCount() != other.getCount()) {
            return false;
        }
        String this$pluginId = this.getPluginId();
        String other$pluginId = other.getPluginId();
        if (this$pluginId == null ? other$pluginId != null : !this$pluginId.equals(other$pluginId)) {
            return false;
        }
        Collection<? extends Notification> this$items = this.getItems();
        Collection<? extends Notification> other$items = other.getItems();
        return !(this$items == null ? other$items != null : !((Object)this$items).equals(other$items));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getCount();
        String $pluginId = this.getPluginId();
        result = result * 59 + ($pluginId == null ? 43 : $pluginId.hashCode());
        Collection<? extends Notification> $items = this.getItems();
        result = result * 59 + ($items == null ? 43 : ((Object)$items).hashCode());
        return result;
    }

    public String toString() {
        return "NotificationResultImpl(pluginId=" + this.getPluginId() + ", count=" + this.getCount() + ", items=" + String.valueOf(this.getItems()) + ")";
    }
}

