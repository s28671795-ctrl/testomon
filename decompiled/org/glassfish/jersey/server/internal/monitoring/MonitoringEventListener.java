/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import org.glassfish.jersey.internal.inject.InjectionManager;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.monitoring.MonitoringStatisticsProcessor;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.DestroyListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;
import org.glassfish.jersey.uri.UriTemplate;

@Priority(value=1100)
public final class MonitoringEventListener
implements ApplicationEventListener {
    private static final Logger LOGGER = Logger.getLogger(MonitoringEventListener.class.getName());
    private static final int EVENT_QUEUE_SIZE = 500000;
    @Inject
    private InjectionManager injectionManager;
    private final Queue<RequestStats> requestQueuedItems = new ArrayBlockingQueue<RequestStats>(500000);
    private final Queue<Integer> responseStatuses = new ArrayBlockingQueue<Integer>(500000);
    private final Queue<RequestEvent> exceptionMapperEvents = new ArrayBlockingQueue<RequestEvent>(500000);
    private volatile MonitoringStatisticsProcessor monitoringStatisticsProcessor;

    @Override
    public ReqEventListener onRequest(RequestEvent requestEvent) {
        switch (requestEvent.getType()) {
            case START: {
                return new ReqEventListener();
            }
        }
        return null;
    }

    @Override
    public void onEvent(ApplicationEvent event) {
        ApplicationEvent.Type type = event.getType();
        switch (type) {
            case INITIALIZATION_START: {
                break;
            }
            case RELOAD_FINISHED: 
            case INITIALIZATION_FINISHED: {
                this.monitoringStatisticsProcessor = new MonitoringStatisticsProcessor(this.injectionManager, this);
                this.monitoringStatisticsProcessor.startMonitoringWorker();
                break;
            }
            case DESTROY_FINISHED: {
                if (this.monitoringStatisticsProcessor != null) {
                    try {
                        this.monitoringStatisticsProcessor.shutDown();
                    }
                    catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        throw new ProcessingException(LocalizationMessages.ERROR_MONITORING_SHUTDOWN_INTERRUPTED(), e);
                    }
                }
                List listeners = this.injectionManager.getAllInstances((Type)((Object)DestroyListener.class));
                for (DestroyListener listener : listeners) {
                    try {
                        listener.onDestroy();
                    }
                    catch (Exception e) {
                        LOGGER.log(Level.WARNING, LocalizationMessages.ERROR_MONITORING_STATISTICS_LISTENER_DESTROY(listener.getClass()), e);
                    }
                }
                break;
            }
        }
    }

    Queue<RequestEvent> getExceptionMapperEvents() {
        return this.exceptionMapperEvents;
    }

    Queue<RequestStats> getRequestQueuedItems() {
        return this.requestQueuedItems;
    }

    Queue<Integer> getResponseStatuses() {
        return this.responseStatuses;
    }

    private class ReqEventListener
    implements RequestEventListener {
        private final long requestTimeStart = System.currentTimeMillis();
        private volatile long methodTimeStart;
        private volatile MethodStats methodStats;

        @Override
        public void onEvent(RequestEvent event) {
            long now = System.currentTimeMillis();
            switch (event.getType()) {
                case RESOURCE_METHOD_START: {
                    this.methodTimeStart = now;
                    break;
                }
                case RESOURCE_METHOD_FINISHED: {
                    ResourceMethod method2 = event.getUriInfo().getMatchedResourceMethod();
                    this.methodStats = new MethodStats(method2, this.methodTimeStart, now - this.methodTimeStart);
                    break;
                }
                case EXCEPTION_MAPPING_FINISHED: {
                    if (MonitoringEventListener.this.exceptionMapperEvents.offer(event)) break;
                    LOGGER.warning(LocalizationMessages.ERROR_MONITORING_QUEUE_MAPPER());
                    break;
                }
                case FINISHED: {
                    if (event.isResponseWritten() && !MonitoringEventListener.this.responseStatuses.offer(event.getContainerResponse().getStatus())) {
                        LOGGER.warning(LocalizationMessages.ERROR_MONITORING_QUEUE_RESPONSE());
                    }
                    StringBuilder sb = new StringBuilder();
                    List orderedTemplates = event.getUriInfo().getMatchedTemplates().stream().collect(Collectors.collectingAndThen(Collectors.toList(), uriTemplates -> {
                        Collections.reverse(uriTemplates);
                        return uriTemplates;
                    }));
                    for (UriTemplate uriTemplate : orderedTemplates) {
                        sb.append(uriTemplate.getTemplate());
                        if (!uriTemplate.endsWithSlash()) {
                            sb.append("/");
                        }
                        sb.setLength(sb.length() - 1);
                    }
                    if (MonitoringEventListener.this.requestQueuedItems.offer(new RequestStats(new TimeStats(this.requestTimeStart, now - this.requestTimeStart), this.methodStats, sb.toString()))) break;
                    LOGGER.warning(LocalizationMessages.ERROR_MONITORING_QUEUE_REQUEST());
                }
            }
        }
    }

    static class RequestStats {
        private final TimeStats requestStats;
        private final MethodStats methodStats;
        private final String requestUri;

        private RequestStats(TimeStats requestStats, MethodStats methodStats, String requestUri) {
            this.requestStats = requestStats;
            this.methodStats = methodStats;
            this.requestUri = requestUri;
        }

        TimeStats getRequestStats() {
            return this.requestStats;
        }

        MethodStats getMethodStats() {
            return this.methodStats;
        }

        String getRequestUri() {
            return this.requestUri;
        }
    }

    static class MethodStats
    extends TimeStats {
        private final ResourceMethod method;

        private MethodStats(ResourceMethod method2, long startTime, long requestDuration) {
            super(startTime, requestDuration);
            this.method = method2;
        }

        ResourceMethod getMethod() {
            return this.method;
        }
    }

    static class TimeStats {
        private final long duration;
        private final long startTime;

        private TimeStats(long startTime, long requestDuration) {
            this.duration = requestDuration;
            this.startTime = startTime;
        }

        long getDuration() {
            return this.duration;
        }

        long getStartTime() {
            return this.startTime;
        }
    }
}

