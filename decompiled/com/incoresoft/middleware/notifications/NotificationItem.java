/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.notifications;

import com.incoresoft.middleware.model.clients.ClientId;
import com.incoresoft.middleware.notifications.Notification;

public interface NotificationItem<T extends Notification>
extends ClientId {
    public String getPluginId();

    public T getNotification();

    @Override
    default public int getClientId() {
        return this.getNotification().getClientId();
    }
}

