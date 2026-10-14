/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.notifications;

import com.incoresoft.middleware.vms.notifications.VmsNotification;
import com.incoresoft.middleware.vms.notifications.VmsNotificationType;

public interface VmsNotificationManager {
    public VmsNotification addNotification(VmsNotification var1);

    public void registerNotificationType(VmsNotificationType var1);

    public void unregisterNotificationType(VmsNotificationType var1);
}

