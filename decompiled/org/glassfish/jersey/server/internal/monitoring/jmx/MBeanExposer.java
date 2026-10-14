/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring.jmx;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.ws.rs.ProcessingException;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.monitoring.jmx.ApplicationMXBeanImpl;
import org.glassfish.jersey.server.internal.monitoring.jmx.ExceptionMapperMXBeanImpl;
import org.glassfish.jersey.server.internal.monitoring.jmx.ExecutionStatisticsDynamicBean;
import org.glassfish.jersey.server.internal.monitoring.jmx.ResourcesMBeanGroup;
import org.glassfish.jersey.server.internal.monitoring.jmx.ResponseMXBeanImpl;
import org.glassfish.jersey.server.monitoring.ApplicationInfo;
import org.glassfish.jersey.server.monitoring.MonitoringStatistics;
import org.glassfish.jersey.server.monitoring.MonitoringStatisticsListener;
import org.glassfish.jersey.server.monitoring.ResourceStatistics;
import org.glassfish.jersey.server.spi.AbstractContainerLifecycleListener;
import org.glassfish.jersey.server.spi.Container;

public class MBeanExposer
extends AbstractContainerLifecycleListener
implements MonitoringStatisticsListener {
    private static final Logger LOGGER = Logger.getLogger(MBeanExposer.class.getName());
    private static final String PROPERTY_SUBTYPE_GLOBAL = "Global";
    static final String PROPERTY_EXECUTION_TIMES_REQUESTS = "RequestTimes";
    static final String PROPERTY_EXECUTION_TIMES_METHODS = "MethodTimes";
    private volatile ExecutionStatisticsDynamicBean requestMBean;
    private volatile ResponseMXBeanImpl responseMXBean;
    private volatile ResourcesMBeanGroup uriStatsGroup;
    private volatile ResourcesMBeanGroup resourceClassStatsGroup;
    private volatile ExceptionMapperMXBeanImpl exceptionMapperMXBean;
    private final AtomicBoolean destroyed = new AtomicBoolean(false);
    private final Object LOCK = new Object();
    private volatile String domain;
    @Inject
    private Provider<ApplicationInfo> applicationInfoProvider;

    private Map<String, ResourceStatistics> transformToStringKeys(Map<Class<?>, ResourceStatistics> stats) {
        HashMap<String, ResourceStatistics> newMap = new HashMap<String, ResourceStatistics>();
        for (Map.Entry<Class<?>, ResourceStatistics> entry : stats.entrySet()) {
            newMap.put(entry.getKey().getName(), entry.getValue());
        }
        return newMap;
    }

    static String convertToObjectName(String name, boolean isUri) {
        if (!isUri) {
            return name;
        }
        String str = name.replace("\\", "\\\\");
        str = str.replace("?", "\\?");
        str = str.replace("*", "\\*");
        return "\"" + str + "\"";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void registerMBean(Object mbean, String namePostfix) {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        String name = this.domain + namePostfix;
        try {
            Object object = this.LOCK;
            synchronized (object) {
                if (this.destroyed.get()) {
                    return;
                }
                ObjectName objectName = new ObjectName(name);
                if (mBeanServer.isRegistered(objectName)) {
                    LOGGER.log(Level.WARNING, LocalizationMessages.WARNING_MONITORING_MBEANS_BEAN_ALREADY_REGISTERED(objectName));
                    mBeanServer.unregisterMBean(objectName);
                }
                mBeanServer.registerMBean(mbean, objectName);
            }
        }
        catch (JMException e) {
            throw new ProcessingException(LocalizationMessages.ERROR_MONITORING_MBEANS_REGISTRATION(name), e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void unregisterJerseyMBeans(boolean destroy) {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        try {
            Object object = this.LOCK;
            synchronized (object) {
                if (destroy) {
                    this.destroyed.set(true);
                }
                if (this.domain == null) {
                    return;
                }
                Set<ObjectName> names = mBeanServer.queryNames(new ObjectName(this.domain + ",*"), null);
                for (ObjectName name : names) {
                    mBeanServer.unregisterMBean(name);
                }
            }
        }
        catch (Exception e) {
            throw new ProcessingException(LocalizationMessages.ERROR_MONITORING_MBEANS_UNREGISTRATION_DESTROY(), e);
        }
    }

    @Override
    public void onStatistics(MonitoringStatistics statistics) {
        if (this.domain == null) {
            String globalSubType = ",subType=Global";
            ApplicationInfo appStats = this.applicationInfoProvider.get();
            String appName = appStats.getResourceConfig().getApplicationName();
            if (appName == null) {
                appName = "App_" + Integer.toHexString(appStats.getResourceConfig().hashCode());
            }
            this.domain = "org.glassfish.jersey:type=" + appName;
            this.unregisterJerseyMBeans(false);
            this.uriStatsGroup = new ResourcesMBeanGroup(statistics.getUriStatistics(), true, this, ",subType=Uris");
            Map<String, ResourceStatistics> newMap = this.transformToStringKeys(statistics.getResourceClassStatistics());
            this.resourceClassStatsGroup = new ResourcesMBeanGroup(newMap, false, this, ",subType=Resources");
            this.responseMXBean = new ResponseMXBeanImpl();
            this.registerMBean(this.responseMXBean, ",subType=Global,global=Responses");
            this.requestMBean = new ExecutionStatisticsDynamicBean(statistics.getRequestStatistics(), this, ",subType=Global", "AllRequestTimes");
            this.exceptionMapperMXBean = new ExceptionMapperMXBeanImpl(statistics.getExceptionMapperStatistics(), this, ",subType=Global");
            new ApplicationMXBeanImpl(appStats, this, ",subType=Global");
        }
        this.requestMBean.updateExecutionStatistics(statistics.getRequestStatistics());
        this.uriStatsGroup.updateResourcesStatistics(statistics.getUriStatistics());
        this.responseMXBean.updateResponseStatistics(statistics.getResponseStatistics());
        this.exceptionMapperMXBean.updateExceptionMapperStatistics(statistics.getExceptionMapperStatistics());
        this.resourceClassStatsGroup.updateResourcesStatistics(this.transformToStringKeys(statistics.getResourceClassStatistics()));
    }

    @Override
    public void onShutdown(Container container) {
        this.unregisterJerseyMBeans(true);
    }
}

