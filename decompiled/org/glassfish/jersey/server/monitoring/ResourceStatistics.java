/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.monitoring;

import java.util.Map;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.monitoring.ExecutionStatistics;
import org.glassfish.jersey.server.monitoring.ResourceMethodStatistics;

public interface ResourceStatistics {
    public ExecutionStatistics getResourceMethodExecutionStatistics();

    public ExecutionStatistics getRequestExecutionStatistics();

    public Map<ResourceMethod, ResourceMethodStatistics> getResourceMethodStatistics();

    @Deprecated
    public ResourceStatistics snapshot();
}

