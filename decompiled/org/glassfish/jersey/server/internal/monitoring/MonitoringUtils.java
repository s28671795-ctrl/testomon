/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring;

import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.monitoring.ExecutionStatistics;
import org.glassfish.jersey.server.monitoring.TimeWindowStatistics;

public final class MonitoringUtils {
    private static final double CACHEABLE_REQUEST_RATE_LIMIT = 0.001;

    public static String getMethodUniqueId(ResourceMethod method2) {
        String path = method2.getParent() != null ? MonitoringUtils.createPath(method2.getParent()) : "null";
        return method2.getProducedTypes().toString() + "|" + method2.getConsumedTypes().toString() + "|" + method2.getHttpMethod() + "|" + path + "|" + method2.getInvocable().getHandlingMethod().getName();
    }

    private static String createPath(Resource resource) {
        return MonitoringUtils.appendPath(resource, new StringBuilder()).toString();
    }

    private static StringBuilder appendPath(Resource resource, StringBuilder path) {
        return resource.getParent() == null ? path.append(resource.getPath()) : MonitoringUtils.appendPath(resource.getParent(), path).append(".").append(resource.getPath());
    }

    static boolean isCacheable(ExecutionStatistics stats) {
        for (TimeWindowStatistics window : stats.getTimeWindowStatistics().values()) {
            if (!(window.getRequestsPerSecond() >= 0.001)) continue;
            return false;
        }
        return true;
    }

    private MonitoringUtils() {
    }
}

