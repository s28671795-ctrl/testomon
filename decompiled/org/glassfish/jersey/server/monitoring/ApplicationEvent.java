/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.monitoring;

import java.util.Set;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.ResourceModel;

public interface ApplicationEvent {
    public Type getType();

    public ResourceConfig getResourceConfig();

    public Set<Class<?>> getRegisteredClasses();

    public Set<Object> getRegisteredInstances();

    public Set<Class<?>> getProviders();

    public ResourceModel getResourceModel();

    public static enum Type {
        INITIALIZATION_START,
        INITIALIZATION_APP_FINISHED,
        INITIALIZATION_FINISHED,
        DESTROY_FINISHED,
        RELOAD_FINISHED;

    }
}

