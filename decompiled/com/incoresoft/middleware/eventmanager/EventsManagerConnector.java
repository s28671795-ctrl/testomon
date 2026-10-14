/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.eventmanager;

import com.incoresoft.middleware.eventmanager.Event;
import com.incoresoft.middleware.eventmanager.EventManagerMessage;
import com.incoresoft.middleware.eventmanager.EventsHolder;
import java.util.Collection;

public interface EventsManagerConnector {
    public Collection<Event> getEvents();

    public void sendEvent(EventManagerMessage var1);

    public void sendEvent(EventsHolder var1, EventManagerMessage var2);
}

