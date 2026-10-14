/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.core.Configuration;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.internal.util.PropertiesHelper;
import org.glassfish.jersey.server.BackgroundSchedulerLiteral;
import org.glassfish.jersey.server.ExtendedResourceContext;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.monitoring.ExceptionMapperStatisticsImpl;
import org.glassfish.jersey.server.internal.monitoring.MonitoringEventListener;
import org.glassfish.jersey.server.internal.monitoring.MonitoringStatisticsImpl;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.model.ResourceModel;
import org.glassfish.jersey.server.monitoring.MonitoringStatisticsListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;

final class MonitoringStatisticsProcessor {
    private static final Logger LOGGER = Logger.getLogger(MonitoringStatisticsProcessor.class.getName());
    private static final int DEFAULT_INTERVAL = 500;
    private static final int SHUTDOWN_TIMEOUT = 10;
    private final MonitoringEventListener monitoringEventListener;
    private final MonitoringStatisticsImpl.Builder statisticsBuilder;
    private final List<MonitoringStatisticsListener> statisticsCallbackList;
    private final ScheduledExecutorService scheduler;
    private final int interval;

    MonitoringStatisticsProcessor(InjectionManager injectionManager, MonitoringEventListener monitoringEventListener) {
        this.monitoringEventListener = monitoringEventListener;
        ResourceModel resourceModel = injectionManager.getInstance(ExtendedResourceContext.class).getResourceModel();
        this.statisticsBuilder = new MonitoringStatisticsImpl.Builder(resourceModel);
        this.statisticsCallbackList = injectionManager.getAllInstances((Type)((Object)MonitoringStatisticsListener.class));
        this.scheduler = injectionManager.getInstance(ScheduledExecutorService.class, BackgroundSchedulerLiteral.INSTANCE);
        this.interval = PropertiesHelper.getValue(injectionManager.getInstance(Configuration.class).getProperties(), "jersey.config.server.monitoring.statistics.refresh.interval", 500, Collections.emptyMap());
    }

    public void startMonitoringWorker() {
        this.scheduler.scheduleWithFixedDelay(new Runnable(){

            @Override
            public void run() {
                try {
                    MonitoringStatisticsProcessor.this.processRequestItems();
                    MonitoringStatisticsProcessor.this.processResponseCodeEvents();
                    MonitoringStatisticsProcessor.this.processExceptionMapperEvents();
                }
                catch (Throwable t2) {
                    LOGGER.log(Level.SEVERE, LocalizationMessages.ERROR_MONITORING_STATISTICS_GENERATION(), t2);
                    throw new ProcessingException(LocalizationMessages.ERROR_MONITORING_STATISTICS_GENERATION(), t2);
                }
                MonitoringStatisticsImpl immutableStats = MonitoringStatisticsProcessor.this.statisticsBuilder.build();
                Iterator iterator2 = MonitoringStatisticsProcessor.this.statisticsCallbackList.iterator();
                while (iterator2.hasNext() && !Thread.currentThread().isInterrupted()) {
                    MonitoringStatisticsListener listener = (MonitoringStatisticsListener)iterator2.next();
                    try {
                        listener.onStatistics(immutableStats);
                    }
                    catch (Throwable t3) {
                        LOGGER.log(Level.SEVERE, LocalizationMessages.ERROR_MONITORING_STATISTICS_LISTENER(listener.getClass()), t3);
                        iterator2.remove();
                    }
                }
            }
        }, 0L, this.interval, TimeUnit.MILLISECONDS);
    }

    private void processExceptionMapperEvents() {
        Queue<RequestEvent> eventQueue = this.monitoringEventListener.getExceptionMapperEvents();
        FloodingLogger floodingLogger = new FloodingLogger(eventQueue);
        while (!eventQueue.isEmpty()) {
            floodingLogger.conditionallyLogFlooding();
            RequestEvent event = eventQueue.remove();
            ExceptionMapperStatisticsImpl.Builder mapperStats = this.statisticsBuilder.getExceptionMapperStatisticsBuilder();
            if (event.getExceptionMapper() != null) {
                mapperStats.addExceptionMapperExecution(event.getExceptionMapper().getClass(), 1);
            }
            mapperStats.addMapping(event.isResponseSuccessfullyMapped(), 1);
        }
    }

    private void processRequestItems() {
        Queue<MonitoringEventListener.RequestStats> requestQueuedItems = this.monitoringEventListener.getRequestQueuedItems();
        FloodingLogger floodingLogger = new FloodingLogger(requestQueuedItems);
        while (!requestQueuedItems.isEmpty()) {
            floodingLogger.conditionallyLogFlooding();
            MonitoringEventListener.RequestStats event = requestQueuedItems.remove();
            MonitoringEventListener.TimeStats requestStats = event.getRequestStats();
            this.statisticsBuilder.addRequestExecution(requestStats.getStartTime(), requestStats.getDuration());
            MonitoringEventListener.MethodStats methodStat = event.getMethodStats();
            if (methodStat == null) continue;
            ResourceMethod method2 = methodStat.getMethod();
            this.statisticsBuilder.addExecution(event.getRequestUri(), method2, methodStat.getStartTime(), methodStat.getDuration(), requestStats.getStartTime(), requestStats.getDuration());
        }
    }

    private void processResponseCodeEvents() {
        Queue<Integer> responseEvents = this.monitoringEventListener.getResponseStatuses();
        FloodingLogger floodingLogger = new FloodingLogger(responseEvents);
        while (!responseEvents.isEmpty()) {
            floodingLogger.conditionallyLogFlooding();
            Integer code = responseEvents.remove();
            this.statisticsBuilder.addResponseCode(code);
        }
    }

    void shutDown() throws InterruptedException {
        this.scheduler.shutdown();
        boolean success = this.scheduler.awaitTermination(10L, TimeUnit.SECONDS);
        if (!success) {
            LOGGER.warning(LocalizationMessages.ERROR_MONITORING_SCHEDULER_DESTROY_TIMEOUT());
        }
    }

    private static class FloodingLogger {
        private static final int FLOODING_WARNING_LOG_INTERVAL_MILLIS = 5000;
        private final Collection<?> collection;
        private final long startTime = System.nanoTime();
        private int i = 0;
        private int lastSize;

        public FloodingLogger(Collection<?> collection) {
            this.collection = collection;
            this.lastSize = collection.size();
        }

        public void conditionallyLogFlooding() {
            if ((System.nanoTime() - this.startTime) / TimeUnit.NANOSECONDS.convert(5000L, TimeUnit.MILLISECONDS) <= (long)this.i) {
                return;
            }
            if (this.collection.size() > this.lastSize) {
                LOGGER.warning(LocalizationMessages.ERROR_MONITORING_QUEUE_FLOODED(this.collection.size()));
            }
            ++this.i;
            this.lastSize = this.collection.size();
        }
    }
}

