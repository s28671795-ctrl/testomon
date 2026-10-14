/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.notifications;

import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.notifications.NotificationsManager;
import com.incoresoft.middleware.notifications.NotificationsResult;
import java.util.Collection;

public interface NotificationsPanel {
    public Collection<NotificationsManager> getNotificationsManagers();

    public Collection<NotificationsResult> getNotificationsResult(User var1);

    public void registerManager(NotificationsManager var1);

    public void unregisterManager(NotificationsManager var1);
}

