/*
 * Decompiled with CFR 0.152.
 */
package com.codahale.metrics;

import com.codahale.metrics.Clock;
import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvReporter
extends ScheduledReporter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvReporter.class);
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final File directory;
    private final Locale locale;
    private final Clock clock;

    public static Builder forRegistry(MetricRegistry registry) {
        return new Builder(registry);
    }

    private CsvReporter(MetricRegistry registry, File directory, Locale locale, TimeUnit rateUnit, TimeUnit durationUnit, Clock clock, MetricFilter filter2) {
        super(registry, "csv-reporter", filter2, rateUnit, durationUnit);
        this.directory = directory;
        this.locale = locale;
        this.clock = clock;
    }

    @Override
    public void report(SortedMap<String, Gauge> gauges, SortedMap<String, Counter> counters, SortedMap<String, Histogram> histograms, SortedMap<String, Meter> meters, SortedMap<String, Timer> timers) {
        long timestamp = TimeUnit.MILLISECONDS.toSeconds(this.clock.getTime());
        for (Map.Entry<String, Gauge> entry : gauges.entrySet()) {
            this.reportGauge(timestamp, entry.getKey(), entry.getValue());
        }
        for (Map.Entry<String, Metric> entry : counters.entrySet()) {
            this.reportCounter(timestamp, entry.getKey(), (Counter)entry.getValue());
        }
        for (Map.Entry<String, Metric> entry : histograms.entrySet()) {
            this.reportHistogram(timestamp, entry.getKey(), (Histogram)entry.getValue());
        }
        for (Map.Entry<String, Metric> entry : meters.entrySet()) {
            this.reportMeter(timestamp, entry.getKey(), (Meter)entry.getValue());
        }
        for (Map.Entry<String, Metric> entry : timers.entrySet()) {
            this.reportTimer(timestamp, entry.getKey(), (Timer)entry.getValue());
        }
    }

    private void reportTimer(long timestamp, String name, Timer timer) {
        Snapshot snapshot = timer.getSnapshot();
        this.report(timestamp, name, "count,max,mean,min,stddev,p50,p75,p95,p98,p99,p999,mean_rate,m1_rate,m5_rate,m15_rate,rate_unit,duration_unit", "%d,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,%f,calls/%s,%s", timer.getCount(), this.convertDuration(snapshot.getMax()), this.convertDuration(snapshot.getMean()), this.convertDuration(snapshot.getMin()), this.convertDuration(snapshot.getStdDev()), this.convertDuration(snapshot.getMedian()), this.convertDuration(snapshot.get75thPercentile()), this.convertDuration(snapshot.get95thPercentile()), this.convertDuration(snapshot.get98thPercentile()), this.convertDuration(snapshot.get99thPercentile()), this.convertDuration(snapshot.get999thPercentile()), this.convertRate(timer.getMeanRate()), this.convertRate(timer.getOneMinuteRate()), this.convertRate(timer.getFiveMinuteRate()), this.convertRate(timer.getFifteenMinuteRate()), this.getRateUnit(), this.getDurationUnit());
    }

    private void reportMeter(long timestamp, String name, Meter meter) {
        this.report(timestamp, name, "count,mean_rate,m1_rate,m5_rate,m15_rate,rate_unit", "%d,%f,%f,%f,%f,events/%s", meter.getCount(), this.convertRate(meter.getMeanRate()), this.convertRate(meter.getOneMinuteRate()), this.convertRate(meter.getFiveMinuteRate()), this.convertRate(meter.getFifteenMinuteRate()), this.getRateUnit());
    }

    private void reportHistogram(long timestamp, String name, Histogram histogram) {
        Snapshot snapshot = histogram.getSnapshot();
        this.report(timestamp, name, "count,max,mean,min,stddev,p50,p75,p95,p98,p99,p999", "%d,%d,%f,%d,%f,%f,%f,%f,%f,%f,%f", histogram.getCount(), snapshot.getMax(), snapshot.getMean(), snapshot.getMin(), snapshot.getStdDev(), snapshot.getMedian(), snapshot.get75thPercentile(), snapshot.get95thPercentile(), snapshot.get98thPercentile(), snapshot.get99thPercentile(), snapshot.get999thPercentile());
    }

    private void reportCounter(long timestamp, String name, Counter counter) {
        this.report(timestamp, name, "count", "%d", counter.getCount());
    }

    private void reportGauge(long timestamp, String name, Gauge gauge) {
        this.report(timestamp, name, "value", "%s", gauge.getValue());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void report(long timestamp, String name, String header, String line, Object ... values2) {
        block6: {
            try {
                File file = new File(this.directory, this.sanitize(name) + ".csv");
                boolean fileAlreadyExists = file.exists();
                if (!fileAlreadyExists && !file.createNewFile()) break block6;
                PrintWriter out = new PrintWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(file, true), UTF_8));
                try {
                    if (!fileAlreadyExists) {
                        out.println("t," + header);
                    }
                    out.printf(this.locale, String.format(this.locale, "%d,%s%n", timestamp, line), values2);
                }
                finally {
                    out.close();
                }
            }
            catch (IOException e) {
                LOGGER.warn("Error writing to {}", (Object)name, (Object)e);
            }
        }
    }

    protected String sanitize(String name) {
        return name;
    }

    public static class Builder {
        private final MetricRegistry registry;
        private Locale locale;
        private TimeUnit rateUnit;
        private TimeUnit durationUnit;
        private Clock clock;
        private MetricFilter filter;

        private Builder(MetricRegistry registry) {
            this.registry = registry;
            this.locale = Locale.getDefault();
            this.rateUnit = TimeUnit.SECONDS;
            this.durationUnit = TimeUnit.MILLISECONDS;
            this.clock = Clock.defaultClock();
            this.filter = MetricFilter.ALL;
        }

        public Builder formatFor(Locale locale) {
            this.locale = locale;
            return this;
        }

        public Builder convertRatesTo(TimeUnit rateUnit) {
            this.rateUnit = rateUnit;
            return this;
        }

        public Builder convertDurationsTo(TimeUnit durationUnit) {
            this.durationUnit = durationUnit;
            return this;
        }

        public Builder withClock(Clock clock) {
            this.clock = clock;
            return this;
        }

        public Builder filter(MetricFilter filter2) {
            this.filter = filter2;
            return this;
        }

        public CsvReporter build(File directory) {
            return new CsvReporter(this.registry, directory, this.locale, this.rateUnit, this.durationUnit, this.clock, this.filter);
        }
    }
}

