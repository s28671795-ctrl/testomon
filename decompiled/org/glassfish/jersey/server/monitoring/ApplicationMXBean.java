/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.monitoring;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public interface ApplicationMXBean {
    public String getApplicationName();

    public String getApplicationClass();

    public Map<String, String> getProperties();

    public Date getStartTime();

    public Set<String> getRegisteredClasses();

    public Set<String> getRegisteredInstances();

    public Set<String> getProviderClasses();
}

