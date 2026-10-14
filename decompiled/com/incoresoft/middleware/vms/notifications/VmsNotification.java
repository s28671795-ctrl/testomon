/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.notifications;

import com.incoresoft.middleware.vms.notifications.VmsNotificationMessage;
import com.incoresoft.middleware.vms.notifications.VmsNotificationType;
import java.util.UUID;

public interface VmsNotification {
    public VmsNotificationType getType();

    public UUID getSourceId();

    public UUID getServerId();

    public VmsNotificationMessage getMessage();
}

