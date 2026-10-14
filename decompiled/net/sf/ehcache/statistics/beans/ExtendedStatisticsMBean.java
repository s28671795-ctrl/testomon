/*
 * Decompiled with CFR 0.152.
 */
package net.sf.ehcache.statistics.beans;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.statistics.beans.AttributeProxy;
import net.sf.ehcache.statistics.beans.BooleanBeanProxy;
import net.sf.ehcache.statistics.beans.DoubleBeanProxy;
import net.sf.ehcache.statistics.beans.LongBeanProxy;
import net.sf.ehcache.statistics.beans.ProxiedDynamicMBean;
import net.sf.ehcache.statistics.extended.ExtendedStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terracotta.statistics.ValueStatistic;
import org.terracotta.statistics.archive.Timestamped;

public class ExtendedStatisticsMBean
extends ProxiedDynamicMBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtendedStatisticsMBean.class);

    public ExtendedStatisticsMBean(Ehcache cache, ExtendedStatistics extendedStatistics) {
        super(ExtendedStatisticsMBean.divineName(cache), "Extended statistics for " + ExtendedStatisticsMBean.divineName(cache), Collections.EMPTY_LIST);
        LinkedList<AttributeProxy> proxies = new LinkedList<AttributeProxy>();
        this.processMethods(extendedStatistics, proxies);
        this.initialize(proxies);
    }

    private void processMethods(ExtendedStatistics extendedStatistics, List<AttributeProxy> proxies) {
        for (Method m4 : ExtendedStatistics.class.getDeclaredMethods()) {
            try {
                this.extractAttributes(extendedStatistics, proxies, m4);
            }
            catch (IllegalArgumentException e) {
                LOGGER.info(e.getMessage());
            }
            catch (IllegalAccessException e) {
                LOGGER.info(e.getMessage());
            }
            catch (InvocationTargetException e) {
                LOGGER.info(e.getMessage());
            }
        }
    }

    private void extractAttributes(ExtendedStatistics extendedStatistics, List<AttributeProxy> proxies, Method m4) throws IllegalAccessException, InvocationTargetException {
        ExtendedStatistics.Operation op;
        if (m4.getReturnType().equals(ValueStatistic.class)) {
            ValueStatistic stat = (ValueStatistic)m4.invoke((Object)extendedStatistics, new Object[0]);
            if (stat != null) {
                this.recordValueStatistic(proxies, stat, "cache.", m4);
            }
        } else if (m4.getReturnType().equals(ExtendedStatistics.Statistic.class)) {
            ExtendedStatistics.Statistic stat = (ExtendedStatistics.Statistic)m4.invoke((Object)extendedStatistics, new Object[0]);
            if (stat != null) {
                this.recordStatistic(proxies, stat, "cache.", m4);
            }
        } else if (m4.getReturnType().equals(ExtendedStatistics.Result.class)) {
            ExtendedStatistics.Result res = (ExtendedStatistics.Result)m4.invoke((Object)extendedStatistics, new Object[0]);
            if (res != null) {
                this.recordResult(proxies, res, m4.getName());
            }
        } else if (m4.getReturnType().equals(ExtendedStatistics.Operation.class) && (op = (ExtendedStatistics.Operation)m4.invoke((Object)extendedStatistics, new Object[0])).type() != null && op.type().isEnum() && op.type().getEnumConstants() != null) {
            this.recordOperation(proxies, extendedStatistics, m4.getName(), op);
        }
    }

    private void recordStatistic(List<AttributeProxy> proxies, final ExtendedStatistics.Statistic<Number> stat, String prefix, Method m4) {
        String name = m4.getName();
        if (name.startsWith("get")) {
            name = name.substring("get".length());
            name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
        }
        name = prefix + name;
        AttributeProxy<Object> proxy = new AttributeProxy<Object>(Object.class, name, name, true, false){

            @Override
            public Object get(String name) {
                return stat.value();
            }
        };
        proxies.add(proxy);
    }

    private void recordValueStatistic(List<AttributeProxy> proxies, final ValueStatistic stat, String prefix, Method m4) {
        String name = m4.getName();
        if (name.startsWith("get")) {
            name = name.substring("get".length());
            name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
        }
        name = prefix + name;
        AttributeProxy<Object> proxy = new AttributeProxy<Object>(Object.class, name, name, true, false){

            @Override
            public Object get(String name) {
                return stat.value();
            }
        };
        proxies.add(proxy);
    }

    private void recordOperation(List<AttributeProxy> proxies, ExtendedStatistics extendedStatistics, String name, final ExtendedStatistics.Operation op) {
        String smallName = name;
        AttributeProxy proxy = new BooleanBeanProxy(smallName + ".alwaysOn", "Set this operation statistic always on/off", true, true){

            @Override
            public void set(String name, Boolean t2) {
                op.setAlwaysOn(t2);
            }

            @Override
            public Boolean get(String name) {
                return op.isAlwaysOn();
            }
        };
        proxies.add(proxy);
        proxy = new LongBeanProxy(smallName + ".sampleWindow", "Sampling window size, nanoseconds", true, true){

            @Override
            public void set(String name, Long t2) {
                op.setWindow(t2, TimeUnit.NANOSECONDS);
            }

            @Override
            public Long get(String name) {
                return op.getWindowSize(TimeUnit.NANOSECONDS);
            }
        };
        proxies.add(proxy);
        proxy = new LongBeanProxy(smallName + ".sampleHistoryCapacity", "Sampling history capacity", true, true){

            @Override
            public void set(String name, Long t2) {
                op.setHistory(t2.intValue(), op.getHistorySampleTime(TimeUnit.NANOSECONDS), TimeUnit.NANOSECONDS);
            }

            @Override
            public Long get(String name) {
                return op.getHistorySampleSize();
            }
        };
        proxies.add(proxy);
        proxy = new LongBeanProxy(smallName + ".sampleHistoryTime", "Sampling history capacity", true, true){

            @Override
            public void set(String name, Long t2) {
                op.setHistory(t2.intValue(), op.getHistorySampleTime(TimeUnit.NANOSECONDS), TimeUnit.NANOSECONDS);
            }

            @Override
            public Long get(String name) {
                return op.getHistorySampleTime(TimeUnit.NANOSECONDS);
            }
        };
        proxies.add(proxy);
        for (Object t2 : op.type().getEnumConstants()) {
            String camelCase = t2.toString().toLowerCase();
            camelCase = Character.toUpperCase(camelCase.charAt(0)) + camelCase.substring(1);
            this.recordResult(proxies, op.component((Enum)t2), smallName + "." + camelCase);
        }
    }

    private void recordResult(List<AttributeProxy> proxies, ExtendedStatistics.Result result, String longerName) {
        this.recordLongStatistic(proxies, longerName + ".count", "Statistic Counter", result.count());
        this.recordDoubleStatistic(proxies, longerName + ".rate", "Statistic Rate", result.rate());
        this.recordLongStatistic(proxies, longerName + ".latencyMin", "Statistic Latency Minimum", result.latency().minimum());
        this.recordLongStatistic(proxies, longerName + ".latencyMax", "Statistic Latency Maximum", result.latency().maximum());
        this.recordDoubleStatistic(proxies, longerName + ".latencyAvg", "Statistic Latency Average", result.latency().average());
    }

    public void recordDoubleStatistic(List<AttributeProxy> proxies, String longerName, String baseDescription, final ExtendedStatistics.Statistic<Double> stat) {
        AttributeProxy proxy = new BooleanBeanProxy(longerName + "Active", baseDescription + " active?", true, false){

            @Override
            public Boolean get(String name) {
                return stat.active();
            }
        };
        proxies.add(proxy);
        proxy = new DoubleBeanProxy(longerName, baseDescription, true, false){

            @Override
            public Double get(String name) {
                return (Double)stat.value();
            }
        };
        proxies.add(proxy);
        proxy = new AttributeProxy<Map>(Map.class, longerName + "History", baseDescription + " History", true, false){

            @Override
            public Map get(String name) {
                if (stat.active()) {
                    return ExtendedStatisticsMBean.this.historyToMapDouble(stat.history());
                }
                return Collections.EMPTY_MAP;
            }
        };
        proxies.add(proxy);
    }

    public void recordLongStatistic(List<AttributeProxy> proxies, String longerName, String baseDescription, final ExtendedStatistics.Statistic<Long> stat) {
        AttributeProxy proxy = new BooleanBeanProxy(longerName + "Active", baseDescription + " active?", true, false){

            @Override
            public Boolean get(String name) {
                return stat.active();
            }
        };
        proxies.add(proxy);
        proxy = new LongBeanProxy(longerName, baseDescription, true, false){

            @Override
            public Long get(String name) {
                return (Long)stat.value();
            }
        };
        proxies.add(proxy);
        proxy = new AttributeProxy<Map>(Map.class, longerName + "History", baseDescription + " History", true, false){

            @Override
            public Map get(String name) {
                if (stat.active()) {
                    return ExtendedStatisticsMBean.this.historyToMapLong(stat.history());
                }
                return Collections.EMPTY_MAP;
            }
        };
        proxies.add(proxy);
    }

    protected Map historyToMapLong(List<Timestamped<Long>> history) {
        TreeMap<Long, Long> map = new TreeMap<Long, Long>();
        for (Timestamped<Long> ts : history) {
            map.put(ts.getTimestamp(), ts.getSample());
        }
        return map;
    }

    protected Map historyToMapDouble(List<Timestamped<Double>> history) {
        TreeMap<Long, Double> map = new TreeMap<Long, Double>();
        for (Timestamped<Double> ts : history) {
            map.put(ts.getTimestamp(), ts.getSample());
        }
        return map;
    }

    public static String divineName(Ehcache cache) {
        return cache.getCacheManager().getName() + "." + cache.getName();
    }
}

