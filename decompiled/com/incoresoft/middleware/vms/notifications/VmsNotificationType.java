/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.vms.notifications;

import com.incoresoft.middleware.vms.notifications.VmsNotificationMessage;
import com.incoresoft.middleware.vms.notifications.VmsNotificationMessagePostProcessor;

public interface VmsNotificationType {
    public String getTypeId();

    public String getTypeLocaleKey();

    public String getPluginName();

    public Class<? extends VmsNotificationMessage> getNotificationMessageClass();

    default public VmsNotificationMessagePostProcessor getNotificationMessagePostProcessor() {
        return (vmsNotificationMessage, user) -> vmsNotificationMessage;
    }
}

