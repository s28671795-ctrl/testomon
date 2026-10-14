/*
 * Decompiled with CFR 0.152.
 */
package com.codahale.metrics;

import com.codahale.metrics.Gauge;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricSet;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class JvmAttributeGaugeSet
implements MetricSet {
    private final RuntimeMXBean runtime;

    public JvmAttributeGaugeSet() {
        this(ManagementFactory.getRuntimeMXBean());
    }

    public JvmAttributeGaugeSet(RuntimeMXBean runtime) {
        this.runtime = runtime;
    }

    @Override
    public Map<String, Metric> getMetrics() {
        HashMap<String, Gauge<String>> gauges = new HashMap<String, Gauge<String>>();
        gauges.put("name", new Gauge<String>(){

            @Override
            public String getValue() {
                return JvmAttributeGaugeSet.this.runtime.getName();
            }
        });
        gauges.put("vendor", new Gauge<String>(){

            @Override
            public String getValue() {
                return String.format(Locale.US, "%s %s %s (%s)", JvmAttributeGaugeSet.this.runtime.getVmVendor(), JvmAttributeGaugeSet.this.runtime.getVmName(), JvmAttributeGaugeSet.this.runtime.getVmVersion(), JvmAttributeGaugeSet.this.runtime.getSpecVersion());
            }
        });
        gauges.put("uptime", new Gauge<Long>(){

            @Override
            public Long getValue() {
                return JvmAttributeGaugeSet.this.runtime.getUptime();
            }
        });
        return Collections.unmodifiableMap(gauges);
    }
}

