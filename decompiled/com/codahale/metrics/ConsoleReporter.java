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
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class ConsoleReporter
extends ScheduledReporter {
    private static final int CONSOLE_WIDTH = 80;
    private final PrintStream output;
    private final Locale locale;
    private final Clock clock;
    private final DateFormat dateFormat;

    public static Builder forRegistry(MetricRegistry registry) {
        return new Builder(registry);
    }

    private ConsoleReporter(MetricRegistry registry, PrintStream output, Locale locale, Clock clock, TimeZone timeZone, TimeUnit rateUnit, TimeUnit durationUnit, MetricFilter filter2) {
        super(registry, "console-reporter", filter2, rateUnit, durationUnit);
        this.output = output;
        this.locale = locale;
        this.clock = clock;
        this.dateFormat = DateFormat.getDateTimeInstance(3, 2, locale);
        this.dateFormat.setTimeZone(timeZone);
    }

    @Override
    public void report(SortedMap<String, Gauge> gauges, SortedMap<String, Counter> counters, SortedMap<String, Histogram> histograms, SortedMap<String, Meter> meters, SortedMap<String, Timer> timers) {
        String dateTime = this.dateFormat.format(new Date(this.clock.getTime()));
        this.printWithBanner(dateTime, '=');
        this.output.println();
        if (!gauges.isEmpty()) {
            this.printWithBanner("-- Gauges", '-');
            for (Map.Entry<String, Metric> entry : gauges.entrySet()) {
                this.output.println(entry.getKey());
                this.printGauge(entry);
            }
            this.output.println();
        }
        if (!counters.isEmpty()) {
            this.printWithBanner("-- Counters", '-');
            for (Map.Entry<String, Metric> entry : counters.entrySet()) {
                this.output.println(entry.getKey());
                this.printCounter(entry);
            }
            this.output.println();
        }
        if (!histograms.isEmpty()) {
            this.printWithBanner("-- Histograms", '-');
            for (Map.Entry<String, Metric> entry : histograms.entrySet()) {
                this.output.println(entry.getKey());
                this.printHistogram((Histogram)entry.getValue());
            }
            this.output.println();
        }
        if (!meters.isEmpty()) {
            this.printWithBanner("-- Meters", '-');
            for (Map.Entry<String, Metric> entry : meters.entrySet()) {
                this.output.println(entry.getKey());
                this.printMeter((Meter)entry.getValue());
            }
            this.output.println();
        }
        if (!timers.isEmpty()) {
            this.printWithBanner("-- Timers", '-');
            for (Map.Entry<String, Metric> entry : timers.entrySet()) {
                this.output.println(entry.getKey());
                this.printTimer((Timer)entry.getValue());
            }
            this.output.println();
        }
        this.output.println();
        this.output.flush();
    }

    private void printMeter(Meter meter) {
        this.output.printf(this.locale, "             count = %d%n", meter.getCount());
        this.output.printf(this.locale, "         mean rate = %2.2f events/%s%n", this.convertRate(meter.getMeanRate()), this.getRateUnit());
        this.output.printf(this.locale, "     1-minute rate = %2.2f events/%s%n", this.convertRate(meter.getOneMinuteRate()), this.getRateUnit());
        this.output.printf(this.locale, "     5-minute rate = %2.2f events/%s%n", this.convertRate(meter.getFiveMinuteRate()), this.getRateUnit());
        this.output.printf(this.locale, "    15-minute rate = %2.2f events/%s%n", this.convertRate(meter.getFifteenMinuteRate()), this.getRateUnit());
    }

    private void printCounter(Map.Entry<String, Counter> entry) {
        this.output.printf(this.locale, "             count = %d%n", entry.getValue().getCount());
    }

    private void printGauge(Map.Entry<String, Gauge> entry) {
        this.output.printf(this.locale, "             value = %s%n", entry.getValue().getValue());
    }

    private void printHistogram(Histogram histogram) {
        this.output.printf(this.locale, "             count = %d%n", histogram.getCount());
        Snapshot snapshot = histogram.getSnapshot();
        this.output.printf(this.locale, "               min = %d%n", snapshot.getMin());
        this.output.printf(this.locale, "               max = %d%n", snapshot.getMax());
        this.output.printf(this.locale, "              mean = %2.2f%n", snapshot.getMean());
        this.output.printf(this.locale, "            stddev = %2.2f%n", snapshot.getStdDev());
        this.output.printf(this.locale, "            median = %2.2f%n", snapshot.getMedian());
        this.output.printf(this.locale, "              75%% <= %2.2f%n", snapshot.get75thPercentile());
        this.output.printf(this.locale, "              95%% <= %2.2f%n", snapshot.get95thPercentile());
        this.output.printf(this.locale, "              98%% <= %2.2f%n", snapshot.get98thPercentile());
        this.output.printf(this.locale, "              99%% <= %2.2f%n", snapshot.get99thPercentile());
        this.output.printf(this.locale, "            99.9%% <= %2.2f%n", snapshot.get999thPercentile());
    }

    private void printTimer(Timer timer) {
        Snapshot snapshot = timer.getSnapshot();
        this.output.printf(this.locale, "             count = %d%n", timer.getCount());
        this.output.printf(this.locale, "         mean rate = %2.2f calls/%s%n", this.convertRate(timer.getMeanRate()), this.getRateUnit());
        this.output.printf(this.locale, "     1-minute rate = %2.2f calls/%s%n", this.convertRate(timer.getOneMinuteRate()), this.getRateUnit());
        this.output.printf(this.locale, "     5-minute rate = %2.2f calls/%s%n", this.convertRate(timer.getFiveMinuteRate()), this.getRateUnit());
        this.output.printf(this.locale, "    15-minute rate = %2.2f calls/%s%n", this.convertRate(timer.getFifteenMinuteRate()), this.getRateUnit());
        this.output.printf(this.locale, "               min = %2.2f %s%n", this.convertDuration(snapshot.getMin()), this.getDurationUnit());
        this.output.printf(this.locale, "               max = %2.2f %s%n", this.convertDuration(snapshot.getMax()), this.getDurationUnit());
        this.output.printf(this.locale, "              mean = %2.2f %s%n", this.convertDuration(snapshot.getMean()), this.getDurationUnit());
        this.output.printf(this.locale, "            stddev = %2.2f %s%n", this.convertDuration(snapshot.getStdDev()), this.getDurationUnit());
        this.output.printf(this.locale, "            median = %2.2f %s%n", this.convertDuration(snapshot.getMedian()), this.getDurationUnit());
        this.output.printf(this.locale, "              75%% <= %2.2f %s%n", this.convertDuration(snapshot.get75thPercentile()), this.getDurationUnit());
        this.output.printf(this.locale, "              95%% <= %2.2f %s%n", this.convertDuration(snapshot.get95thPercentile()), this.getDurationUnit());
        this.output.printf(this.locale, "              98%% <= %2.2f %s%n", this.convertDuration(snapshot.get98thPercentile()), this.getDurationUnit());
        this.output.printf(this.locale, "              99%% <= %2.2f %s%n", this.convertDuration(snapshot.get99thPercentile()), this.getDurationUnit());
        this.output.printf(this.locale, "            99.9%% <= %2.2f %s%n", this.convertDuration(snapshot.get999thPercentile()), this.getDurationUnit());
    }

    private void printWithBanner(String s2, char c) {
        this.output.print(s2);
        this.output.print(' ');
        for (int i = 0; i < 80 - s2.length() - 1; ++i) {
            this.output.print(c);
        }
        this.output.println();
    }

    public static class Builder {
        private final MetricRegistry registry;
        private PrintStream output;
        private Locale locale;
        private Clock clock;
        private TimeZone timeZone;
        private TimeUnit rateUnit;
        private TimeUnit durationUnit;
        private MetricFilter filter;

        private Builder(MetricRegistry registry) {
            this.registry = registry;
            this.output = System.out;
            this.locale = Locale.getDefault();
            this.clock = Clock.defaultClock();
            this.timeZone = TimeZone.getDefault();
            this.rateUnit = TimeUnit.SECONDS;
            this.durationUnit = TimeUnit.MILLISECONDS;
            this.filter = MetricFilter.ALL;
        }

        public Builder outputTo(PrintStream output) {
            this.output = output;
            return this;
        }

        public Builder formattedFor(Locale locale) {
            this.locale = locale;
            return this;
        }

        public Builder withClock(Clock clock) {
            this.clock = clock;
            return this;
        }

        public Builder formattedFor(TimeZone timeZone) {
            this.timeZone = timeZone;
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

        public Builder filter(MetricFilter filter2) {
            this.filter = filter2;
            return this;
        }

        public ConsoleReporter build() {
            return new ConsoleReporter(this.registry, this.output, this.locale, this.clock, this.timeZone, this.rateUnit, this.durationUnit, this.filter);
        }
    }
}

