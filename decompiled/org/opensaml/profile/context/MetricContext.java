/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.BaseContext
 */
package org.opensaml.profile.context;

import com.codahale.metrics.Timer;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.Live;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.core.metrics.MetricsSupport;
import org.opensaml.messaging.context.BaseContext;

public class MetricContext
extends BaseContext {
    @Nonnull
    @NonnullElements
    private Multimap<String, Pair<String, String>> timerMap = ArrayListMultimap.create();
    @Nonnull
    @NonnullElements
    private Multimap<String, Timer.Context> timerContextMap = ArrayListMultimap.create();
    @Nonnull
    @NonnullElements
    private Map<String, String> counterMap = new HashMap<String, String>();

    @Nonnull
    public MetricContext addTimer(@Nonnull @NotEmpty String timerName, @Nonnull @NotEmpty String startId, @Nonnull @NotEmpty String stopId) {
        String key = Constraint.isNotNull(StringSupport.trimOrNull(startId), "Starting object ID cannot be null or empty");
        String stop = Constraint.isNotNull(StringSupport.trimOrNull(stopId), "Stop object ID cannot be null or empty");
        String name = Constraint.isNotNull(StringSupport.trimOrNull(timerName), "Timer name cannot be null or empty");
        this.timerMap.put(key, new Pair<String, String>(name, stop));
        return this;
    }

    @Nonnull
    @NonnullElements
    @Live
    public Collection<Pair<String, String>> getTimerMappings(@Nonnull @NotEmpty String objectId) {
        return this.timerMap.get(objectId);
    }

    @Nonnull
    public MetricContext addCounter(@Nonnull @NotEmpty String counterName, @Nonnull @NotEmpty String objectId) {
        String key = Constraint.isNotNull(StringSupport.trimOrNull(objectId), "Starting object ID cannot be null or empty");
        String name = Constraint.isNotNull(StringSupport.trimOrNull(counterName), "Counter name cannot be null or empty");
        this.counterMap.put(key, name);
        return this;
    }

    @Nonnull
    @NonnullElements
    @Live
    public Map<String, String> getCounterMappings() {
        return this.counterMap;
    }

    public void start(@Nonnull @NotEmpty String objectId) {
        for (Pair<String, String> timer : this.timerMap.get(objectId)) {
            if (timer == null) continue;
            this.timerContextMap.put(timer.getSecond(), MetricsSupport.getMetricRegistry().timer(timer.getFirst()).time());
        }
    }

    public void stop(@Nonnull @NotEmpty String objectId) {
        Iterator<Timer.Context> iter = this.timerContextMap.get(objectId).iterator();
        while (iter.hasNext()) {
            Timer.Context tc = iter.next();
            if (tc == null) continue;
            tc.stop();
            iter.remove();
        }
    }

    public void inc(@Nonnull @NotEmpty String objectId) {
        String name = this.counterMap.get(objectId);
        if (name != null) {
            MetricsSupport.getMetricRegistry().counter(name).inc();
        }
    }

    public void dec(@Nonnull @NotEmpty String objectId) {
        String name = this.counterMap.get(objectId);
        if (name != null) {
            MetricsSupport.getMetricRegistry().counter(name).dec();
        }
    }
}

