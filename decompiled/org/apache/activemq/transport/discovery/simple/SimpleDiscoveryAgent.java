/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.discovery.simple;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.activemq.command.DiscoveryEvent;
import org.apache.activemq.thread.TaskRunnerFactory;
import org.apache.activemq.transport.discovery.DiscoveryAgent;
import org.apache.activemq.transport.discovery.DiscoveryListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleDiscoveryAgent
implements DiscoveryAgent {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleDiscoveryAgent.class);
    private long initialReconnectDelay = 1000L;
    private long maxReconnectDelay = 30000L;
    private long backOffMultiplier = 2L;
    private boolean useExponentialBackOff = true;
    private int maxReconnectAttempts;
    private final Object sleepMutex = new Object();
    private long minConnectTime = 5000L;
    private DiscoveryListener listener;
    private String[] services = new String[0];
    private final AtomicBoolean running = new AtomicBoolean(false);
    private TaskRunnerFactory taskRunner;

    @Override
    public void setDiscoveryListener(DiscoveryListener listener) {
        this.listener = listener;
    }

    @Override
    public void registerService(String name) throws IOException {
    }

    @Override
    public void start() throws Exception {
        this.taskRunner = new TaskRunnerFactory();
        this.taskRunner.init();
        this.running.set(true);
        for (int i = 0; i < this.services.length; ++i) {
            this.listener.onServiceAdd(new SimpleDiscoveryEvent(this.services[i]));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void stop() throws Exception {
        this.running.set(false);
        if (this.taskRunner != null) {
            this.taskRunner.shutdown();
        }
        Object object = this.sleepMutex;
        synchronized (object) {
            this.sleepMutex.notifyAll();
        }
    }

    public String[] getServices() {
        return this.services;
    }

    public void setServices(String services) {
        this.services = services.split(",");
    }

    public void setServices(String[] services) {
        this.services = services;
    }

    public void setServices(URI[] services) {
        this.services = new String[services.length];
        for (int i = 0; i < services.length; ++i) {
            this.services[i] = services[i].toString();
        }
    }

    @Override
    public void serviceFailed(DiscoveryEvent devent) throws IOException {
        final SimpleDiscoveryEvent sevent = (SimpleDiscoveryEvent)devent;
        if (this.running.get() && sevent.failed.compareAndSet(false, true)) {
            this.listener.onServiceRemove(sevent);
            this.taskRunner.execute(new Runnable(){

                @Override
                public void run() {
                    SimpleDiscoveryEvent event = new SimpleDiscoveryEvent(sevent);
                    if (event.connectTime + SimpleDiscoveryAgent.this.minConnectTime > System.currentTimeMillis()) {
                        LOG.debug("Failure occurred soon after the discovery event was generated.  It will be classified as a connection failure: {}", (Object)event);
                        event.connectFailures++;
                        if (SimpleDiscoveryAgent.this.maxReconnectAttempts > 0 && event.connectFailures >= SimpleDiscoveryAgent.this.maxReconnectAttempts) {
                            LOG.warn("Reconnect attempts exceeded {} tries.  Reconnecting has been disabled for: {}", (Object)SimpleDiscoveryAgent.this.maxReconnectAttempts, (Object)event);
                            return;
                        }
                        if (!SimpleDiscoveryAgent.this.useExponentialBackOff || event.reconnectDelay == -1L) {
                            event.reconnectDelay = SimpleDiscoveryAgent.this.initialReconnectDelay;
                        } else {
                            SimpleDiscoveryEvent simpleDiscoveryEvent = event;
                            simpleDiscoveryEvent.reconnectDelay = simpleDiscoveryEvent.reconnectDelay * SimpleDiscoveryAgent.this.backOffMultiplier;
                            if (event.reconnectDelay > SimpleDiscoveryAgent.this.maxReconnectDelay) {
                                event.reconnectDelay = SimpleDiscoveryAgent.this.maxReconnectDelay;
                            }
                        }
                        SimpleDiscoveryAgent.this.doReconnectDelay(event);
                    } else {
                        LOG.trace("Failure occurred to long after the discovery event was generated.  It will not be classified as a connection failure: {}", (Object)event);
                        event.connectFailures = 0;
                        event.reconnectDelay = SimpleDiscoveryAgent.this.initialReconnectDelay;
                        SimpleDiscoveryAgent.this.doReconnectDelay(event);
                    }
                    if (!SimpleDiscoveryAgent.this.running.get()) {
                        LOG.debug("Reconnecting disabled: stopped");
                        return;
                    }
                    event.connectTime = System.currentTimeMillis();
                    event.failed.set(false);
                    SimpleDiscoveryAgent.this.listener.onServiceAdd(event);
                }
            }, "Simple Discovery Agent");
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected void doReconnectDelay(SimpleDiscoveryEvent event) {
        Object object = this.sleepMutex;
        synchronized (object) {
            try {
                if (!this.running.get()) {
                    LOG.debug("Reconnecting disabled: stopped");
                    return;
                }
                LOG.debug("Waiting {}ms before attempting to reconnect.", (Object)event.reconnectDelay);
                this.sleepMutex.wait(event.reconnectDelay);
            }
            catch (InterruptedException ie) {
                LOG.debug("Reconnecting disabled: ", ie);
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public long getBackOffMultiplier() {
        return this.backOffMultiplier;
    }

    public void setBackOffMultiplier(long backOffMultiplier) {
        this.backOffMultiplier = backOffMultiplier;
    }

    public long getInitialReconnectDelay() {
        return this.initialReconnectDelay;
    }

    public void setInitialReconnectDelay(long initialReconnectDelay) {
        this.initialReconnectDelay = initialReconnectDelay;
    }

    public int getMaxReconnectAttempts() {
        return this.maxReconnectAttempts;
    }

    public void setMaxReconnectAttempts(int maxReconnectAttempts) {
        this.maxReconnectAttempts = maxReconnectAttempts;
    }

    public long getMaxReconnectDelay() {
        return this.maxReconnectDelay;
    }

    public void setMaxReconnectDelay(long maxReconnectDelay) {
        this.maxReconnectDelay = maxReconnectDelay;
    }

    public long getMinConnectTime() {
        return this.minConnectTime;
    }

    public void setMinConnectTime(long minConnectTime) {
        this.minConnectTime = minConnectTime;
    }

    public boolean isUseExponentialBackOff() {
        return this.useExponentialBackOff;
    }

    public void setUseExponentialBackOff(boolean useExponentialBackOff) {
        this.useExponentialBackOff = useExponentialBackOff;
    }

    class SimpleDiscoveryEvent
    extends DiscoveryEvent {
        private int connectFailures;
        private long reconnectDelay;
        private long connectTime;
        private final AtomicBoolean failed;

        public SimpleDiscoveryEvent(String service2) {
            super(service2);
            this.reconnectDelay = -1L;
            this.connectTime = System.currentTimeMillis();
            this.failed = new AtomicBoolean(false);
        }

        public SimpleDiscoveryEvent(SimpleDiscoveryEvent copy) {
            super(copy);
            this.reconnectDelay = -1L;
            this.connectTime = System.currentTimeMillis();
            this.failed = new AtomicBoolean(false);
            this.connectFailures = copy.connectFailures;
            this.reconnectDelay = copy.reconnectDelay;
            this.connectTime = copy.connectTime;
            this.failed.set(copy.failed.get());
        }

        public String toString() {
            return "[" + this.serviceName + ", failed:" + this.failed + ", connectionFailures:" + this.connectFailures + "]";
        }
    }
}

