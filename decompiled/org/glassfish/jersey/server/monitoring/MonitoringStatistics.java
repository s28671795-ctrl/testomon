/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.monitoring;

import java.util.Map;
import org.glassfish.jersey.server.monitoring.ExceptionMapperStatistics;
import org.glassfish.jersey.server.monitoring.ExecutionStatistics;
import org.glassfish.jersey.server.monitoring.ResourceStatistics;
import org.glassfish.jersey.server.monitoring.ResponseStatistics;

public interface MonitoringStatistics {
    public Map<String, ResourceStatistics> getUriStatistics();

    public Map<Class<?>, ResourceStatistics> getResourceClassStatistics();

    public ExecutionStatistics getRequestStatistics();

    public ResponseStatistics getResponseStatistics();

    public ExceptionMapperStatistics getExceptionMapperStatistics();

    @Deprecated
    public MonitoringStatistics snapshot();
}

