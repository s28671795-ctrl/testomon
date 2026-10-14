/*
 * Decompiled with CFR 0.152.
 */
package com.codahale.metrics;

import com.codahale.metrics.Metric;

public interface MetricFilter {
    public static final MetricFilter ALL = new MetricFilter(){

        @Override
        public boolean matches(String name, Metric metric) {
            return true;
        }
    };

    public boolean matches(String var1, Metric var2);
}

