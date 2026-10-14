/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.plugins;

import com.google.inject.Inject;
import com.incoresoft.middleware.analytics.Analytics;
import com.incoresoft.middleware.analytics.AnalyticsManager;
import com.incoresoft.middleware.eventmanager.Event;
import com.incoresoft.middleware.eventmanager.EventManagerMessage;
import com.incoresoft.middleware.eventmanager.EventsHolder;
import com.incoresoft.middleware.eventmanager.EventsManagerConnector;
import com.incoresoft.vezha.eventmanager.EventManager;
import com.incoresoft.vezha.eventmanager.EventScheme;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventsManagerConnectorImpl
implements EventsManagerConnector {
    private static final Logger log = LoggerFactory.getLogger(EventsManagerConnectorImpl.class);
    private final EventManager eventManager;
    private final AnalyticsManager analyticsManager;

    @Override
    public Collection<Event> getEvents() {
        return this.eventManager.getEventsList().stream().map(it -> new Event(){
            final /* synthetic */ EventScheme val$it;
            {
                this.val$it = eventScheme;
            }

            @Override
            public String getId() {
                return this.val$it.getUuid();
            }

            @Override
            public Map<String, String> getParams() {
                return Collections.emptyMap();
            }
        }).toList();
    }

    @Override
    public void sendEvent(EventManagerMessage message) {
        Analytics analytics = (Analytics)this.analyticsManager.get(message.getVaId());
        if (Objects.isNull(analytics)) {
            return;
        }
        EventsHolder eventsHolder = analytics.getEventsHolder();
        this.sendEvent(eventsHolder, message);
    }

    @Override
    public void sendEvent(EventsHolder eventsHolder, EventManagerMessage message) {
        if (Objects.isNull(eventsHolder) || !eventsHolder.isEnabled()) {
            return;
        }
        for (Event event : eventsHolder.getEvents()) {
            try {
                this.eventManager.send(event, message);
            }
            catch (Exception e) {
                log.error("Exception while sending event", e);
            }
        }
    }

    @Inject
    public EventsManagerConnectorImpl(EventManager eventManager, AnalyticsManager analyticsManager) {
        this.eventManager = eventManager;
        this.analyticsManager = analyticsManager;
    }
}

