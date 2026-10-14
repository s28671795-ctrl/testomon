/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring;

import java.util.Date;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.inject.Provider;
import org.glassfish.jersey.internal.util.collection.Ref;
import org.glassfish.jersey.server.internal.monitoring.ApplicationInfoImpl;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.ApplicationInfo;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

@Priority(value=1000)
public final class ApplicationInfoListener
implements ApplicationEventListener {
    public static final int PRIORITY = 1000;
    @Inject
    private Provider<Ref<ApplicationInfo>> applicationInfoRefProvider;

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        return null;
    }

    @Override
    public void onEvent(ApplicationEvent event) {
        ApplicationEvent.Type type = event.getType();
        switch (type) {
            case RELOAD_FINISHED: 
            case INITIALIZATION_FINISHED: {
                this.processApplicationStatistics(event);
            }
        }
    }

    private void processApplicationStatistics(ApplicationEvent event) {
        long now = System.currentTimeMillis();
        ApplicationInfoImpl applicationInfo = new ApplicationInfoImpl(event.getResourceConfig(), new Date(now), event.getRegisteredClasses(), event.getRegisteredInstances(), event.getProviders());
        this.applicationInfoRefProvider.get().set(applicationInfo);
    }
}

