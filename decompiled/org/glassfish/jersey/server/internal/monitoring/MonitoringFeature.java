/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring;

import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.core.GenericType;
import org.glassfish.jersey.internal.inject.AbstractBinder;
import org.glassfish.jersey.internal.inject.ClassBinding;
import org.glassfish.jersey.internal.inject.ReferencingFactory;
import org.glassfish.jersey.internal.inject.SupplierInstanceBinding;
import org.glassfish.jersey.internal.util.collection.Ref;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.monitoring.ApplicationInfoListener;
import org.glassfish.jersey.server.internal.monitoring.MonitoringEventListener;
import org.glassfish.jersey.server.internal.monitoring.jmx.MBeanExposer;
import org.glassfish.jersey.server.monitoring.ApplicationInfo;
import org.glassfish.jersey.server.monitoring.MonitoringStatistics;
import org.glassfish.jersey.server.monitoring.MonitoringStatisticsListener;

public final class MonitoringFeature
implements Feature {
    private static final Logger LOGGER = Logger.getLogger(MonitoringFeature.class.getName());
    private boolean monitoringEnabled = true;
    private boolean statisticsEnabled = true;
    private boolean mBeansEnabled;

    @Override
    public boolean configure(FeatureContext context) {
        Boolean monitoringEnabledProperty = ServerProperties.getValue(context.getConfiguration().getProperties(), "jersey.config.server.monitoring.enabled", null, Boolean.class);
        Boolean statisticsEnabledProperty = ServerProperties.getValue(context.getConfiguration().getProperties(), "jersey.config.server.monitoring.statistics.enabled", null, Boolean.class);
        Boolean mbeansEnabledProperty = ServerProperties.getValue(context.getConfiguration().getProperties(), "jersey.config.server.monitoring.statistics.mbeans.enabled", null, Boolean.class);
        if (monitoringEnabledProperty != null) {
            this.statisticsEnabled = this.monitoringEnabled = monitoringEnabledProperty.booleanValue();
        }
        if (statisticsEnabledProperty != null) {
            this.monitoringEnabled = this.monitoringEnabled || statisticsEnabledProperty != false;
            this.statisticsEnabled = statisticsEnabledProperty;
        }
        if (mbeansEnabledProperty != null) {
            this.monitoringEnabled = this.monitoringEnabled || mbeansEnabledProperty != false;
            this.statisticsEnabled = this.statisticsEnabled || mbeansEnabledProperty != false;
            this.mBeansEnabled = mbeansEnabledProperty;
        }
        if (statisticsEnabledProperty != null && !statisticsEnabledProperty.booleanValue()) {
            if (mbeansEnabledProperty != null && this.mBeansEnabled) {
                LOGGER.log(Level.WARNING, LocalizationMessages.WARNING_MONITORING_FEATURE_ENABLED("jersey.config.server.monitoring.statistics.enabled"));
            } else {
                LOGGER.log(Level.WARNING, LocalizationMessages.WARNING_MONITORING_FEATURE_DISABLED("jersey.config.server.monitoring.statistics.enabled"));
            }
        }
        if (this.monitoringEnabled) {
            context.register(ApplicationInfoListener.class);
            context.register(new AbstractBinder(){

                @Override
                protected void configure() {
                    ((SupplierInstanceBinding)this.bindFactory(ReferencingFactory.referenceFactory()).to(new GenericType<Ref<ApplicationInfo>>(){})).in(Singleton.class);
                    this.bindFactory(ApplicationInfoInjectionFactory.class).to((Type)((Object)ApplicationInfo.class));
                }
            });
        }
        if (this.statisticsEnabled) {
            context.register(MonitoringEventListener.class);
            context.register(new AbstractBinder(){

                @Override
                protected void configure() {
                    ((SupplierInstanceBinding)this.bindFactory(ReferencingFactory.referenceFactory()).to(new GenericType<Ref<MonitoringStatistics>>(){})).in(Singleton.class);
                    this.bindFactory(StatisticsInjectionFactory.class).to((Type)((Object)MonitoringStatistics.class));
                    ((ClassBinding)this.bind(StatisticsListener.class).to(MonitoringStatisticsListener.class)).in(Singleton.class);
                }
            });
        }
        if (this.mBeansEnabled) {
            context.register(new MBeanExposer());
        }
        return this.monitoringEnabled;
    }

    public void setmBeansEnabled(boolean mBeansEnabled) {
        this.mBeansEnabled = mBeansEnabled;
    }

    private static class StatisticsListener
    implements MonitoringStatisticsListener {
        @Inject
        Provider<Ref<MonitoringStatistics>> statisticsFactory;

        private StatisticsListener() {
        }

        @Override
        public void onStatistics(MonitoringStatistics statistics) {
            this.statisticsFactory.get().set(statistics);
        }
    }

    private static class StatisticsInjectionFactory
    extends ReferencingFactory<MonitoringStatistics> {
        @Inject
        public StatisticsInjectionFactory(Provider<Ref<MonitoringStatistics>> referenceFactory) {
            super(referenceFactory);
        }

        @Override
        public MonitoringStatistics get() {
            return (MonitoringStatistics)super.get();
        }
    }

    private static class ApplicationInfoInjectionFactory
    extends ReferencingFactory<ApplicationInfo> {
        @Inject
        public ApplicationInfoInjectionFactory(Provider<Ref<ApplicationInfo>> referenceFactory) {
            super(referenceFactory);
        }
    }
}

