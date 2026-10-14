/*
 * Decompiled with CFR 0.152.
 */
package com.codahale.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.DefaultObjectNameFactory;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Metered;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.MetricRegistryListener;
import com.codahale.metrics.ObjectNameFactory;
import com.codahale.metrics.Reporter;
import com.codahale.metrics.Timer;
import java.io.Closeable;
import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.JMException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmxReporter
implements Reporter,
Closeable {
    private static final Logger LOGGER = LoggerFactory.getLogger(JmxReporter.class);
    private final MetricRegistry registry;
    private final JmxListener listener;

    public static Builder forRegistry(MetricRegistry registry) {
        return new Builder(registry);
    }

    private JmxReporter(MBeanServer mBeanServer, String domain, MetricRegistry registry, MetricFilter filter2, MetricTimeUnits timeUnits, ObjectNameFactory objectNameFactory) {
        this.registry = registry;
        this.listener = new JmxListener(mBeanServer, domain, filter2, timeUnits, objectNameFactory);
    }

    public void start() {
        this.registry.addListener(this.listener);
    }

    public void stop() {
        this.registry.removeListener(this.listener);
        this.listener.unregisterAll();
    }

    @Override
    public void close() {
        this.stop();
    }

    ObjectNameFactory getObjectNameFactory() {
        return this.listener.objectNameFactory;
    }

    private static class MetricTimeUnits {
        private final TimeUnit defaultRate;
        private final TimeUnit defaultDuration;
        private final Map<String, TimeUnit> rateOverrides;
        private final Map<String, TimeUnit> durationOverrides;

        MetricTimeUnits(TimeUnit defaultRate, TimeUnit defaultDuration, Map<String, TimeUnit> rateOverrides, Map<String, TimeUnit> durationOverrides) {
            this.defaultRate = defaultRate;
            this.defaultDuration = defaultDuration;
            this.rateOverrides = rateOverrides;
            this.durationOverrides = durationOverrides;
        }

        public TimeUnit durationFor(String name) {
            return this.durationOverrides.containsKey(name) ? this.durationOverrides.get(name) : this.defaultDuration;
        }

        public TimeUnit rateFor(String name) {
            return this.rateOverrides.containsKey(name) ? this.rateOverrides.get(name) : this.defaultRate;
        }
    }

    private static class JmxListener
    implements MetricRegistryListener {
        private final String name;
        private final MBeanServer mBeanServer;
        private final MetricFilter filter;
        private final MetricTimeUnits timeUnits;
        private final Map<ObjectName, ObjectName> registered;
        private final ObjectNameFactory objectNameFactory;

        private JmxListener(MBeanServer mBeanServer, String name, MetricFilter filter2, MetricTimeUnits timeUnits, ObjectNameFactory objectNameFactory) {
            this.mBeanServer = mBeanServer;
            this.name = name;
            this.filter = filter2;
            this.timeUnits = timeUnits;
            this.registered = new ConcurrentHashMap<ObjectName, ObjectName>();
            this.objectNameFactory = objectNameFactory;
        }

        private void registerMBean(Object mBean, ObjectName objectName) throws InstanceAlreadyExistsException, JMException {
            ObjectInstance objectInstance = this.mBeanServer.registerMBean(mBean, objectName);
            if (objectInstance != null) {
                this.registered.put(objectName, objectInstance.getObjectName());
            } else {
                this.registered.put(objectName, objectName);
            }
        }

        private void unregisterMBean(ObjectName originalObjectName) throws InstanceNotFoundException, MBeanRegistrationException {
            ObjectName storedObjectName = this.registered.remove(originalObjectName);
            if (storedObjectName != null) {
                this.mBeanServer.unregisterMBean(storedObjectName);
            } else {
                this.mBeanServer.unregisterMBean(originalObjectName);
            }
        }

        @Override
        public void onGaugeAdded(String name, Gauge<?> gauge) {
            try {
                if (this.filter.matches(name, gauge)) {
                    ObjectName objectName = this.createName("gauges", name);
                    this.registerMBean(new JmxGauge(gauge, objectName), objectName);
                }
            }
            catch (InstanceAlreadyExistsException e) {
                LOGGER.debug("Unable to register gauge", e);
            }
            catch (JMException e) {
                LOGGER.warn("Unable to register gauge", e);
            }
        }

        @Override
        public void onGaugeRemoved(String name) {
            try {
                ObjectName objectName = this.createName("gauges", name);
                this.unregisterMBean(objectName);
            }
            catch (InstanceNotFoundException e) {
                LOGGER.debug("Unable to unregister gauge", e);
            }
            catch (MBeanRegistrationException e) {
                LOGGER.warn("Unable to unregister gauge", e);
            }
        }

        @Override
        public void onCounterAdded(String name, Counter counter) {
            try {
                if (this.filter.matches(name, counter)) {
                    ObjectName objectName = this.createName("counters", name);
                    this.registerMBean(new JmxCounter(counter, objectName), objectName);
                }
            }
            catch (InstanceAlreadyExistsException e) {
                LOGGER.debug("Unable to register counter", e);
            }
            catch (JMException e) {
                LOGGER.warn("Unable to register counter", e);
            }
        }

        @Override
        public void onCounterRemoved(String name) {
            try {
                ObjectName objectName = this.createName("counters", name);
                this.unregisterMBean(objectName);
            }
            catch (InstanceNotFoundException e) {
                LOGGER.debug("Unable to unregister counter", e);
            }
            catch (MBeanRegistrationException e) {
                LOGGER.warn("Unable to unregister counter", e);
            }
        }

        @Override
        public void onHistogramAdded(String name, Histogram histogram) {
            try {
                if (this.filter.matches(name, histogram)) {
                    ObjectName objectName = this.createName("histograms", name);
                    this.registerMBean(new JmxHistogram(histogram, objectName), objectName);
                }
            }
            catch (InstanceAlreadyExistsException e) {
                LOGGER.debug("Unable to register histogram", e);
            }
            catch (JMException e) {
                LOGGER.warn("Unable to register histogram", e);
            }
        }

        @Override
        public void onHistogramRemoved(String name) {
            try {
                ObjectName objectName = this.createName("histograms", name);
                this.unregisterMBean(objectName);
            }
            catch (InstanceNotFoundException e) {
                LOGGER.debug("Unable to unregister histogram", e);
            }
            catch (MBeanRegistrationException e) {
                LOGGER.warn("Unable to unregister histogram", e);
            }
        }

        @Override
        public void onMeterAdded(String name, Meter meter) {
            try {
                if (this.filter.matches(name, meter)) {
                    ObjectName objectName = this.createName("meters", name);
                    this.registerMBean(new JmxMeter(meter, objectName, this.timeUnits.rateFor(name)), objectName);
                }
            }
            catch (InstanceAlreadyExistsException e) {
                LOGGER.debug("Unable to register meter", e);
            }
            catch (JMException e) {
                LOGGER.warn("Unable to register meter", e);
            }
        }

        @Override
        public void onMeterRemoved(String name) {
            try {
                ObjectName objectName = this.createName("meters", name);
                this.unregisterMBean(objectName);
            }
            catch (InstanceNotFoundException e) {
                LOGGER.debug("Unable to unregister meter", e);
            }
            catch (MBeanRegistrationException e) {
                LOGGER.warn("Unable to unregister meter", e);
            }
        }

        @Override
        public void onTimerAdded(String name, Timer timer) {
            try {
                if (this.filter.matches(name, timer)) {
                    ObjectName objectName = this.createName("timers", name);
                    this.registerMBean(new JmxTimer(timer, objectName, this.timeUnits.rateFor(name), this.timeUnits.durationFor(name)), objectName);
                }
            }
            catch (InstanceAlreadyExistsException e) {
                LOGGER.debug("Unable to register timer", e);
            }
            catch (JMException e) {
                LOGGER.warn("Unable to register timer", e);
            }
        }

        @Override
        public void onTimerRemoved(String name) {
            try {
                ObjectName objectName = this.createName("timers", name);
                this.unregisterMBean(objectName);
            }
            catch (InstanceNotFoundException e) {
                LOGGER.debug("Unable to unregister timer", e);
            }
            catch (MBeanRegistrationException e) {
                LOGGER.warn("Unable to unregister timer", e);
            }
        }

        private ObjectName createName(String type, String name) {
            return this.objectNameFactory.createName(type, this.name, name);
        }

        void unregisterAll() {
            for (ObjectName name : this.registered.keySet()) {
                try {
                    this.unregisterMBean(name);
                }
                catch (InstanceNotFoundException e) {
                    LOGGER.debug("Unable to unregister metric", e);
                }
                catch (MBeanRegistrationException e) {
                    LOGGER.warn("Unable to unregister metric", e);
                }
            }
            this.registered.clear();
        }
    }

    static class JmxTimer
    extends JmxMeter
    implements JmxTimerMBean {
        private final Timer metric;
        private final double durationFactor;
        private final String durationUnit;

        private JmxTimer(Timer metric, ObjectName objectName, TimeUnit rateUnit, TimeUnit durationUnit) {
            super(metric, objectName, rateUnit);
            this.metric = metric;
            this.durationFactor = 1.0 / (double)durationUnit.toNanos(1L);
            this.durationUnit = durationUnit.toString().toLowerCase(Locale.US);
        }

        @Override
        public double get50thPercentile() {
            return this.metric.getSnapshot().getMedian() * this.durationFactor;
        }

        @Override
        public double getMin() {
            return (double)this.metric.getSnapshot().getMin() * this.durationFactor;
        }

        @Override
        public double getMax() {
            return (double)this.metric.getSnapshot().getMax() * this.durationFactor;
        }

        @Override
        public double getMean() {
            return this.metric.getSnapshot().getMean() * this.durationFactor;
        }

        @Override
        public double getStdDev() {
            return this.metric.getSnapshot().getStdDev() * this.durationFactor;
        }

        @Override
        public double get75thPercentile() {
            return this.metric.getSnapshot().get75thPercentile() * this.durationFactor;
        }

        @Override
        public double get95thPercentile() {
            return this.metric.getSnapshot().get95thPercentile() * this.durationFactor;
        }

        @Override
        public double get98thPercentile() {
            return this.metric.getSnapshot().get98thPercentile() * this.durationFactor;
        }

        @Override
        public double get99thPercentile() {
            return this.metric.getSnapshot().get99thPercentile() * this.durationFactor;
        }

        @Override
        public double get999thPercentile() {
            return this.metric.getSnapshot().get999thPercentile() * this.durationFactor;
        }

        @Override
        public long[] values() {
            return this.metric.getSnapshot().getValues();
        }

        @Override
        public String getDurationUnit() {
            return this.durationUnit;
        }
    }

    public static interface JmxTimerMBean
    extends JmxMeterMBean {
        public double getMin();

        public double getMax();

        public double getMean();

        public double getStdDev();

        public double get50thPercentile();

        public double get75thPercentile();

        public double get95thPercentile();

        public double get98thPercentile();

        public double get99thPercentile();

        public double get999thPercentile();

        public long[] values();

        public String getDurationUnit();
    }

    private static class JmxMeter
    extends AbstractBean
    implements JmxMeterMBean {
        private final Metered metric;
        private final double rateFactor;
        private final String rateUnit;

        private JmxMeter(Metered metric, ObjectName objectName, TimeUnit rateUnit) {
            super(objectName);
            this.metric = metric;
            this.rateFactor = rateUnit.toSeconds(1L);
            this.rateUnit = "events/" + this.calculateRateUnit(rateUnit);
        }

        @Override
        public long getCount() {
            return this.metric.getCount();
        }

        @Override
        public double getMeanRate() {
            return this.metric.getMeanRate() * this.rateFactor;
        }

        @Override
        public double getOneMinuteRate() {
            return this.metric.getOneMinuteRate() * this.rateFactor;
        }

        @Override
        public double getFiveMinuteRate() {
            return this.metric.getFiveMinuteRate() * this.rateFactor;
        }

        @Override
        public double getFifteenMinuteRate() {
            return this.metric.getFifteenMinuteRate() * this.rateFactor;
        }

        @Override
        public String getRateUnit() {
            return this.rateUnit;
        }

        private String calculateRateUnit(TimeUnit unit) {
            String s2 = unit.toString().toLowerCase(Locale.US);
            return s2.substring(0, s2.length() - 1);
        }
    }

    public static interface JmxMeterMBean
    extends MetricMBean {
        public long getCount();

        public double getMeanRate();

        public double getOneMinuteRate();

        public double getFiveMinuteRate();

        public double getFifteenMinuteRate();

        public String getRateUnit();
    }

    private static class JmxHistogram
    implements JmxHistogramMBean {
        private final ObjectName objectName;
        private final Histogram metric;

        private JmxHistogram(Histogram metric, ObjectName objectName) {
            this.metric = metric;
            this.objectName = objectName;
        }

        @Override
        public ObjectName objectName() {
            return this.objectName;
        }

        @Override
        public double get50thPercentile() {
            return this.metric.getSnapshot().getMedian();
        }

        @Override
        public long getCount() {
            return this.metric.getCount();
        }

        @Override
        public long getMin() {
            return this.metric.getSnapshot().getMin();
        }

        @Override
        public long getMax() {
            return this.metric.getSnapshot().getMax();
        }

        @Override
        public double getMean() {
            return this.metric.getSnapshot().getMean();
        }

        @Override
        public double getStdDev() {
            return this.metric.getSnapshot().getStdDev();
        }

        @Override
        public double get75thPercentile() {
            return this.metric.getSnapshot().get75thPercentile();
        }

        @Override
        public double get95thPercentile() {
            return this.metric.getSnapshot().get95thPercentile();
        }

        @Override
        public double get98thPercentile() {
            return this.metric.getSnapshot().get98thPercentile();
        }

        @Override
        public double get99thPercentile() {
            return this.metric.getSnapshot().get99thPercentile();
        }

        @Override
        public double get999thPercentile() {
            return this.metric.getSnapshot().get999thPercentile();
        }

        @Override
        public long[] values() {
            return this.metric.getSnapshot().getValues();
        }
    }

    public static interface JmxHistogramMBean
    extends MetricMBean {
        public long getCount();

        public long getMin();

        public long getMax();

        public double getMean();

        public double getStdDev();

        public double get50thPercentile();

        public double get75thPercentile();

        public double get95thPercentile();

        public double get98thPercentile();

        public double get99thPercentile();

        public double get999thPercentile();

        public long[] values();
    }

    private static class JmxCounter
    extends AbstractBean
    implements JmxCounterMBean {
        private final Counter metric;

        private JmxCounter(Counter metric, ObjectName objectName) {
            super(objectName);
            this.metric = metric;
        }

        @Override
        public long getCount() {
            return this.metric.getCount();
        }
    }

    public static interface JmxCounterMBean
    extends MetricMBean {
        public long getCount();
    }

    private static class JmxGauge
    extends AbstractBean
    implements JmxGaugeMBean {
        private final Gauge<?> metric;

        private JmxGauge(Gauge<?> metric, ObjectName objectName) {
            super(objectName);
            this.metric = metric;
        }

        @Override
        public Object getValue() {
            return this.metric.getValue();
        }
    }

    public static interface JmxGaugeMBean
    extends MetricMBean {
        public Object getValue();
    }

    private static abstract class AbstractBean
    implements MetricMBean {
        private final ObjectName objectName;

        AbstractBean(ObjectName objectName) {
            this.objectName = objectName;
        }

        @Override
        public ObjectName objectName() {
            return this.objectName;
        }
    }

    public static interface MetricMBean {
        public ObjectName objectName();
    }

    public static class Builder {
        private final MetricRegistry registry;
        private MBeanServer mBeanServer;
        private TimeUnit rateUnit;
        private TimeUnit durationUnit;
        private ObjectNameFactory objectNameFactory;
        private MetricFilter filter = MetricFilter.ALL;
        private String domain;
        private Map<String, TimeUnit> specificDurationUnits;
        private Map<String, TimeUnit> specificRateUnits;

        private Builder(MetricRegistry registry) {
            this.registry = registry;
            this.rateUnit = TimeUnit.SECONDS;
            this.durationUnit = TimeUnit.MILLISECONDS;
            this.domain = "metrics";
            this.objectNameFactory = new DefaultObjectNameFactory();
            this.specificDurationUnits = Collections.emptyMap();
            this.specificRateUnits = Collections.emptyMap();
        }

        public Builder registerWith(MBeanServer mBeanServer) {
            this.mBeanServer = mBeanServer;
            return this;
        }

        public Builder convertRatesTo(TimeUnit rateUnit) {
            this.rateUnit = rateUnit;
            return this;
        }

        public Builder createsObjectNamesWith(ObjectNameFactory onFactory) {
            if (onFactory == null) {
                throw new IllegalArgumentException("null objectNameFactory");
            }
            this.objectNameFactory = onFactory;
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

        public Builder inDomain(String domain) {
            this.domain = domain;
            return this;
        }

        public Builder specificDurationUnits(Map<String, TimeUnit> specificDurationUnits) {
            this.specificDurationUnits = Collections.unmodifiableMap(specificDurationUnits);
            return this;
        }

        public Builder specificRateUnits(Map<String, TimeUnit> specificRateUnits) {
            this.specificRateUnits = Collections.unmodifiableMap(specificRateUnits);
            return this;
        }

        public JmxReporter build() {
            MetricTimeUnits timeUnits = new MetricTimeUnits(this.rateUnit, this.durationUnit, this.specificRateUnits, this.specificDurationUnits);
            if (this.mBeanServer == null) {
                this.mBeanServer = ManagementFactory.getPlatformMBeanServer();
            }
            return new JmxReporter(this.mBeanServer, this.domain, this.registry, this.filter, timeUnits, this.objectNameFactory);
        }
    }
}

