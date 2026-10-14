/*
 * Decompiled with CFR 0.152.
 */
package com.codahale.metrics;

import com.codahale.metrics.Snapshot;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class WeightedSnapshot
extends Snapshot {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final long[] values;
    private final double[] normWeights;
    private final double[] quantiles;

    public WeightedSnapshot(Collection<WeightedSample> values2) {
        int i;
        WeightedSample[] copy = values2.toArray(new WeightedSample[0]);
        Arrays.sort(copy, new Comparator<WeightedSample>(){

            @Override
            public int compare(WeightedSample o1, WeightedSample o2) {
                if (o1.value > o2.value) {
                    return 1;
                }
                if (o1.value < o2.value) {
                    return -1;
                }
                return 0;
            }
        });
        this.values = new long[copy.length];
        this.normWeights = new double[copy.length];
        this.quantiles = new double[copy.length];
        double sumWeight = 0.0;
        for (WeightedSample sample : copy) {
            sumWeight += sample.weight;
        }
        for (i = 0; i < copy.length; ++i) {
            this.values[i] = copy[i].value;
            this.normWeights[i] = copy[i].weight / sumWeight;
        }
        for (i = 1; i < copy.length; ++i) {
            this.quantiles[i] = this.quantiles[i - 1] + this.normWeights[i - 1];
        }
    }

    @Override
    public double getValue(double quantile) {
        if (quantile < 0.0 || quantile > 1.0 || Double.isNaN(quantile)) {
            throw new IllegalArgumentException(quantile + " is not in [0..1]");
        }
        if (this.values.length == 0) {
            return 0.0;
        }
        int posx = Arrays.binarySearch(this.quantiles, quantile);
        if (posx < 0) {
            posx = -posx - 1 - 1;
        }
        if (posx < 1) {
            return this.values[0];
        }
        if (posx >= this.values.length) {
            return this.values[this.values.length - 1];
        }
        return this.values[posx];
    }

    @Override
    public int size() {
        return this.values.length;
    }

    @Override
    public long[] getValues() {
        return Arrays.copyOf(this.values, this.values.length);
    }

    @Override
    public long getMax() {
        if (this.values.length == 0) {
            return 0L;
        }
        return this.values[this.values.length - 1];
    }

    @Override
    public long getMin() {
        if (this.values.length == 0) {
            return 0L;
        }
        return this.values[0];
    }

    @Override
    public double getMean() {
        if (this.values.length == 0) {
            return 0.0;
        }
        double sum = 0.0;
        for (int i = 0; i < this.values.length; ++i) {
            sum += (double)this.values[i] * this.normWeights[i];
        }
        return sum;
    }

    @Override
    public double getStdDev() {
        if (this.values.length <= 1) {
            return 0.0;
        }
        double mean = this.getMean();
        double variance = 0.0;
        for (int i = 0; i < this.values.length; ++i) {
            double diff = (double)this.values[i] - mean;
            variance += this.normWeights[i] * diff * diff;
        }
        return Math.sqrt(variance);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void dump(OutputStream output) {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(output, UTF_8));
        try {
            for (long value : this.values) {
                out.printf("%d%n", value);
            }
        }
        finally {
            out.close();
        }
    }

    public static class WeightedSample {
        public final long value;
        public final double weight;

        public WeightedSample(long value, double weight) {
            this.value = value;
            this.weight = weight;
        }
    }
}

