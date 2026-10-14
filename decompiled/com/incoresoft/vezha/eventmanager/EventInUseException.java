/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.eventmanager;

import java.util.Set;

public class EventInUseException
extends RuntimeException {
    private final Set<Integer> idOfAnalyticsUsingEvent;

    public EventInUseException(int idOfAnalyticsUsingEvent) {
        this.idOfAnalyticsUsingEvent = Set.of(Integer.valueOf(idOfAnalyticsUsingEvent));
    }

    public Set<Integer> getIdOfAnalyticsUsingEvent() {
        return this.idOfAnalyticsUsingEvent;
    }
}

