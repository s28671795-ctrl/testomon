/*
 * Decompiled with CFR 0.152.
 */
package com.codahale.metrics;

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
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

public class Slf4jReporter
extends ScheduledReporter {
    private final LoggerProxy loggerProxy;
    private final Marker marker;
    private final String prefix;

    public static Builder forRegistry(MetricRegistry registry) {
        return new Builder(registry);
    }

    private Slf4jReporter(MetricRegistry registry, LoggerProxy loggerProxy, Marker marker, String prefix, TimeUnit rateUnit, TimeUnit durationUnit, MetricFilter filter2) {
        super(registry, "logger-reporter", filter2, rateUnit, durationUnit);
        this.loggerProxy = loggerProxy;
        this.marker = marker;
        this.prefix = prefix;
    }

    @Override
    public void report(SortedMap<String, Gauge> gauges, SortedMap<String, Counter> counters, SortedMap<String, Histogram> histograms, SortedMap<String, Meter> meters, SortedMap<String, Timer> timers) {
        if (this.loggerProxy.isEnabled(this.marker)) {
            for (Map.Entry<String, Gauge> entry : gauges.entrySet()) {
                this.logGauge(entry.getKey(), entry.getValue());
            }
            for (Map.Entry<String, Metric> entry : counters.entrySet()) {
                this.logCounter(entry.getKey(), (Counter)entry.getValue());
            }
            for (Map.Entry<String, Metric> entry : histograms.entrySet()) {
                this.logHistogram(entry.getKey(), (Histogram)entry.getValue());
            }
            for (Map.Entry<String, Metric> entry : meters.entrySet()) {
                this.logMeter(entry.getKey(), (Meter)entry.getValue());
            }
            for (Map.Entry<String, Metric> entry : timers.entrySet()) {
                this.logTimer(entry.getKey(), (Timer)entry.getValue());
            }
        }
    }

    private void logTimer(String name, Timer timer) {
        Snapshot snapshot = timer.getSnapshot();
        this.loggerProxy.log(this.marker, "type=TIMER, name={}, count={}, min={}, max={}, mean={}, stddev={}, median={}, p75={}, p95={}, p98={}, p99={}, p999={}, mean_rate={}, m1={}, m5={}, m15={}, rate_unit={}, duration_unit={}", this.prefix(name), timer.getCount(), this.convertDuration(snapshot.getMin()), this.convertDuration(snapshot.getMax()), this.convertDuration(snapshot.getMean()), this.convertDuration(snapshot.getStdDev()), this.convertDuration(snapshot.getMedian()), this.convertDuration(snapshot.get75thPercentile()), this.convertDuration(snapshot.get95thPercentile()), this.convertDuration(snapshot.get98thPercentile()), this.convertDuration(snapshot.get99thPercentile()), this.convertDuration(snapshot.get999thPercentile()), this.convertRate(timer.getMeanRate()), this.convertRate(timer.getOneMinuteRate()), this.convertRate(timer.getFiveMinuteRate()), this.convertRate(timer.getFifteenMinuteRate()), this.getRateUnit(), this.getDurationUnit());
    }

    private void logMeter(String name, Meter meter) {
        this.loggerProxy.log(this.marker, "type=METER, name={}, count={}, mean_rate={}, m1={}, m5={}, m15={}, rate_unit={}", this.prefix(name), meter.getCount(), this.convertRate(meter.getMeanRate()), this.convertRate(meter.getOneMinuteRate()), this.convertRate(meter.getFiveMinuteRate()), this.convertRate(meter.getFifteenMinuteRate()), this.getRateUnit());
    }

    private void logHistogram(String name, Histogram histogram) {
        Snapshot snapshot = histogram.getSnapshot();
        this.loggerProxy.log(this.marker, "type=HISTOGRAM, name={}, count={}, min={}, max={}, mean={}, stddev={}, median={}, p75={}, p95={}, p98={}, p99={}, p999={}", this.prefix(name), histogram.getCount(), snapshot.getMin(), snapshot.getMax(), snapshot.getMean(), snapshot.getStdDev(), snapshot.getMedian(), snapshot.get75thPercentile(), snapshot.get95thPercentile(), snapshot.get98thPercentile(), snapshot.get99thPercentile(), snapshot.get999thPercentile());
    }

    private void logCounter(String name, Counter counter) {
        this.loggerProxy.log(this.marker, "type=COUNTER, name={}, count={}", this.prefix(name), counter.getCount());
    }

    private void logGauge(String name, Gauge gauge) {
        this.loggerProxy.log(this.marker, "type=GAUGE, name={}, value={}", this.prefix(name), gauge.getValue());
    }

    @Override
    protected String getRateUnit() {
        return "events/" + super.getRateUnit();
    }

    private String prefix(String ... components) {
        return MetricRegistry.name(this.prefix, components);
    }

    private static class ErrorLoggerProxy
    extends LoggerProxy {
        public ErrorLoggerProxy(Logger logger2) {
            super(logger2);
        }

        @Override
        public void log(Marker marker, String format, Object ... arguments) {
            this.logger.error(marker, format, arguments);
        }

        @Override
        public boolean isEnabled(Marker marker) {
            return this.logger.isErrorEnabled(marker);
        }
    }

    private static class WarnLoggerProxy
    extends LoggerProxy {
        public WarnLoggerProxy(Logger logger2) {
            super(logger2);
        }

        @Override
        public void log(Marker marker, String format, Object ... arguments) {
            this.logger.warn(marker, format, arguments);
        }

        @Override
        public boolean isEnabled(Marker marker) {
            return this.logger.isWarnEnabled(marker);
        }
    }

    private static class InfoLoggerProxy
    extends LoggerProxy {
        public InfoLoggerProxy(Logger logger2) {
            super(logger2);
        }

        @Override
        public void log(Marker marker, String format, Object ... arguments) {
            this.logger.info(marker, format, arguments);
        }

        @Override
        public boolean isEnabled(Marker marker) {
            return this.logger.isInfoEnabled(marker);
        }
    }

    private static class TraceLoggerProxy
    extends LoggerProxy {
        public TraceLoggerProxy(Logger logger2) {
            super(logger2);
        }

        @Override
        public void log(Marker marker, String format, Object ... arguments) {
            this.logger.trace(marker, format, arguments);
        }

        @Override
        public boolean isEnabled(Marker marker) {
            return this.logger.isTraceEnabled(marker);
        }
    }

    private static class DebugLoggerProxy
    extends LoggerProxy {
        public DebugLoggerProxy(Logger logger2) {
            super(logger2);
        }

        @Override
        public void log(Marker marker, String format, Object ... arguments) {
            this.logger.debug(marker, format, arguments);
        }

        @Override
        public boolean isEnabled(Marker marker) {
            return this.logger.isDebugEnabled(marker);
        }
    }

    static abstract class LoggerProxy {
        protected final Logger logger;

        public LoggerProxy(Logger logger2) {
            this.logger = logger2;
        }

        abstract void log(Marker var1, String var2, Object ... var3);

        abstract boolean isEnabled(Marker var1);
    }

    public static class Builder {
        private final MetricRegistry registry;
        private Logger logger;
        private LoggingLevel loggingLevel;
        private Marker marker;
        private String prefix;
        private TimeUnit rateUnit;
        private TimeUnit durationUnit;
        private MetricFilter filter;

        private Builder(MetricRegistry registry) {
            this.registry = registry;
            this.logger = LoggerFactory.getLogger("metrics");
            this.marker = null;
            this.prefix = "";
            this.rateUnit = TimeUnit.SECONDS;
            this.durationUnit = TimeUnit.MILLISECONDS;
            this.filter = MetricFilter.ALL;
            this.loggingLevel = LoggingLevel.INFO;
        }

        public Builder outputTo(Logger logger2) {
            this.logger = logger2;
            return this;
        }

        public Builder markWith(Marker marker) {
            this.marker = marker;
            return this;
        }

        public Builder prefixedWith(String prefix) {
            this.prefix = prefix;
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

        public Builder withLoggingLevel(LoggingLevel loggingLevel) {
            this.loggingLevel = loggingLevel;
            return this;
        }

        public Slf4jReporter build() {
            LoggerProxy loggerProxy;
            switch (this.loggingLevel) {
                case TRACE: {
                    loggerProxy = new TraceLoggerProxy(this.logger);
                    break;
                }
                case INFO: {
                    loggerProxy = new InfoLoggerProxy(this.logger);
                    break;
                }
                case WARN: {
                    loggerProxy = new WarnLoggerProxy(this.logger);
                    break;
                }
                case ERROR: {
                    loggerProxy = new ErrorLoggerProxy(this.logger);
                    break;
                }
                default: {
                    loggerProxy = new DebugLoggerProxy(this.logger);
                }
            }
            return new Slf4jReporter(this.registry, loggerProxy, this.marker, this.prefix, this.rateUnit, this.durationUnit, this.filter);
        }
    }

    public static enum LoggingLevel {
        TRACE,
        DEBUG,
        INFO,
        WARN,
        ERROR;

    }
}

