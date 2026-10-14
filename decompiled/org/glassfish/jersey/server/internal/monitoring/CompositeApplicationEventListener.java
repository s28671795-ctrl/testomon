/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring;

import java.util.ArrayList;
import org.glassfish.jersey.server.internal.monitoring.CompositeRequestEventListener;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

public class CompositeApplicationEventListener
implements ApplicationEventListener {
    private final Iterable<ApplicationEventListener> applicationEventListeners;

    public CompositeApplicationEventListener(Iterable<ApplicationEventListener> applicationEventListeners) {
        this.applicationEventListeners = applicationEventListeners;
    }

    @Override
    public void onEvent(ApplicationEvent event) {
        for (ApplicationEventListener applicationEventListener : this.applicationEventListeners) {
            applicationEventListener.onEvent(event);
        }
    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        ArrayList<RequestEventListener> requestEventListeners = new ArrayList<RequestEventListener>();
        for (ApplicationEventListener applicationEventListener : this.applicationEventListeners) {
            RequestEventListener requestEventListener = applicationEventListener.onRequest(requestEvent);
            if (requestEventListener == null) continue;
            requestEventListeners.add(requestEventListener);
        }
        return requestEventListeners.isEmpty() ? null : new CompositeRequestEventListener(requestEventListeners);
    }
}

