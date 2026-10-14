/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.monitoring;

import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.monitoring.ExecutionStatistics;

public interface ResourceMethodStatistics {
    public ExecutionStatistics getMethodStatistics();

    public ExecutionStatistics getRequestStatistics();

    public ResourceMethod getResourceMethod();

    @Deprecated
    public ResourceMethodStatistics snapshot();
}

