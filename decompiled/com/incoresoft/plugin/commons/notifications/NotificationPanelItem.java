/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.notifications;

import com.incoresoft.middleware.notifications.Notification;
import com.incoresoft.middleware.notifications.NotificationItem;

public class NotificationPanelItem<T extends Notification>
implements NotificationItem<T> {
    private final String pluginId;
    private final T notification;

    public NotificationPanelItem(String pluginId, T notification) {
        this.pluginId = pluginId;
        this.notification = notification;
    }

    @Override
    public String getPluginId() {
        return this.pluginId;
    }

    @Override
    public T getNotification() {
        return this.notification;
    }
}

