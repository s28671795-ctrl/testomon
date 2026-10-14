/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.metrics;

import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.config.ConfigurationService;

public final class MetricsSupport {
    private MetricsSupport() {
    }

    @Nullable
    public static MetricRegistry getMetricRegistry() {
        return ConfigurationService.get(MetricRegistry.class);
    }

    @Nullable
    public static <T extends Metric> T register(@Nonnull String name, @Nonnull T metric) {
        return MetricsSupport.register(name, metric, true, null);
    }

    @Nullable
    public static <T extends Metric> T register(@Nonnull String name, @Nonnull T metric, boolean replaceExisting) {
        return MetricsSupport.register(name, metric, replaceExisting, null);
    }

    @Nullable
    public static <T extends Metric> T register(@Nonnull String name, @Nonnull T metric, boolean replaceExisting, @Nullable MetricRegistry registry) {
        Constraint.isNotNull(name, "Metric name was null");
        Constraint.isNotNull(metric, "Metric was null");
        MetricRegistry metricRegistry = registry;
        if (metricRegistry == null) {
            metricRegistry = MetricsSupport.getMetricRegistry();
        }
        if (metricRegistry == null) {
            return null;
        }
        MetricRegistry metricRegistry2 = metricRegistry;
        synchronized (metricRegistry2) {
            try {
                if (replaceExisting) {
                    metricRegistry.remove(name);
                }
                return metricRegistry.register(name, metric);
            }
            catch (IllegalArgumentException e) {
                if (replaceExisting) {
                    metricRegistry.remove(name);
                    return metricRegistry.register(name, metric);
                }
                throw e;
            }
        }
    }

    public static boolean remove(@Nonnull String name) {
        return MetricsSupport.remove(name, null, null);
    }

    public static boolean remove(@Nonnull String name, @Nullable Metric metric) {
        return MetricsSupport.remove(name, metric, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean remove(@Nonnull String name, @Nullable Metric metric, @Nullable MetricRegistry registry) {
        Constraint.isNotNull(name, "Metric name was null");
        MetricRegistry metricRegistry = registry;
        if (metricRegistry == null) {
            metricRegistry = MetricsSupport.getMetricRegistry();
        }
        if (metricRegistry == null) {
            return false;
        }
        MetricRegistry metricRegistry2 = metricRegistry;
        synchronized (metricRegistry2) {
            if (metric != null && !MetricsSupport.isMetricInstanceRegisteredUnderName(name, metric, metricRegistry)) {
                return false;
            }
            return metricRegistry.remove(name);
        }
    }

    public static boolean isMetricInstanceRegisteredUnderName(@Nonnull String name, @Nonnull Metric metric, @Nonnull MetricRegistry registry) {
        Constraint.isNotNull(registry, "MetricRegistry was null");
        Constraint.isNotNull(name, "Metric name was null");
        Constraint.isNotNull(metric, "Metric was null");
        Metric registeredMetric = registry.getMetrics().get(name);
        return metric == registeredMetric;
    }

    @Nullable
    public static Timer.Context startTimer(@Nullable Timer timer) {
        if (timer != null) {
            return timer.time();
        }
        return null;
    }

    @Nullable
    public static Long stopTimer(@Nullable Timer.Context context) {
        if (context != null) {
            return context.stop();
        }
        return null;
    }
}

