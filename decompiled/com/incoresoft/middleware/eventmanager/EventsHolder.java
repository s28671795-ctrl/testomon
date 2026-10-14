/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.eventmanager;

import com.incoresoft.middleware.eventmanager.Event;
import java.util.LinkedList;
import java.util.List;

public interface EventsHolder {
    default public boolean isEnabled() {
        return false;
    }

    default public List<Event> getEvents() {
        return new LinkedList<Event>();
    }
}

