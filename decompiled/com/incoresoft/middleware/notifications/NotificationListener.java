/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.notifications;

import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.notifications.Notification;
import com.incoresoft.middleware.notifications.NotificationsManager;

public interface NotificationListener {
    public void onNotificationAdded(NotificationsManager<?> var1, Notification var2);

    public void onNotificationAccepted(NotificationsManager<?> var1, User var2, Notification var3);

    public void onAllNotificationsAccepted(NotificationsManager<?> var1, User var2);
}

