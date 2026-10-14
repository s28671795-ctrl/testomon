/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.monitoring;

import java.util.Date;
import java.util.Set;
import org.glassfish.jersey.server.ResourceConfig;

public interface ApplicationInfo {
    public ResourceConfig getResourceConfig();

    public Date getStartTime();

    public Set<Class<?>> getRegisteredClasses();

    public Set<Object> getRegisteredInstances();

    public Set<Class<?>> getProviders();

    public ApplicationInfo snapshot();
}

