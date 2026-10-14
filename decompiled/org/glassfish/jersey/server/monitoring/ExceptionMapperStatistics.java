/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.monitoring;

import java.util.Map;

public interface ExceptionMapperStatistics {
    public Map<Class<?>, Long> getExceptionMapperExecutions();

    public long getSuccessfulMappings();

    public long getUnsuccessfulMappings();

    public long getTotalMappings();

    @Deprecated
    public ExceptionMapperStatistics snapshot();
}

