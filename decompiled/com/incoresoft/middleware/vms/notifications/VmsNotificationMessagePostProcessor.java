/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.notifications;

import com.incoresoft.middleware.model.user.User;
import com.incoresoft.middleware.vms.notifications.VmsNotificationMessage;

public interface VmsNotificationMessagePostProcessor {
    public VmsNotificationMessage processVmsNotification(VmsNotificationMessage var1, User var2);
}

