/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.analytics.events;

import com.incoresoft.middleware.analytics.dto.events.EventsHolderDTO;
import com.incoresoft.middleware.eventmanager.Event;
import com.incoresoft.middleware.eventmanager.EventsHolder;
import com.incoresoft.plugin.commons.analytics.events.EventImpl;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class EventsHolderImpl
implements EventsHolder {
    private boolean enabled = false;
    private List<Event> events = new LinkedList<Event>();

    public EventsHolderImpl(EventsHolder eventsHolder) {
        if (eventsHolder != null) {
            this.enabled = eventsHolder.isEnabled();
            this.events = eventsHolder.getEvents();
        }
    }

    public EventsHolderImpl(EventsHolderDTO eventsHolderDTO) {
        if (eventsHolderDTO != null) {
            this.enabled = eventsHolderDTO.isNotifyEnabled();
            this.events = eventsHolderDTO.getEvents().stream().map(EventImpl::new).collect(Collectors.toList());
        }
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public List<Event> getEvents() {
        return this.events;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}

