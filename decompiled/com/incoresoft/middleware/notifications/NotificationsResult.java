/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.notifications;

import com.incoresoft.middleware.notifications.Notification;
import java.util.Collection;

public interface NotificationsResult {
    public int getCount();

    public Collection<? extends Notification> getNotifications();

    public String pluginId();
}

