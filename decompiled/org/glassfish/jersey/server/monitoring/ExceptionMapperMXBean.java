/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.monitoring;

import java.util.Map;

public interface ExceptionMapperMXBean {
    public Map<String, Long> getExceptionMapperCount();

    public long getSuccessfulMappings();

    public long getUnsuccessfulMappings();

    public long getTotalMappings();
}

