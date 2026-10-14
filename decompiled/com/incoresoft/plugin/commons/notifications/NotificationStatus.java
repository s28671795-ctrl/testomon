/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.notifications;

import java.util.Arrays;

public enum NotificationStatus {
    NEW(1),
    ACCEPTED(0),
    REJECTED(2),
    IGNORE(3),
    DELETED(-1);

    private final int id;

    private NotificationStatus(int id) {
        this.id = id;
    }

    public static NotificationStatus getByID(int id) {
        return Arrays.stream(NotificationStatus.values()).filter(it -> it.id == id).findFirst().orElse(null);
    }

    public int getId() {
        return this.id;
    }
}

