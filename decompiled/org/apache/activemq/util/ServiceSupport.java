/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.activemq.Service;
import org.apache.activemq.util.ServiceListener;
import org.apache.activemq.util.ServiceStopper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ServiceSupport
implements Service {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceSupport.class);
    private AtomicBoolean started = new AtomicBoolean(false);
    private AtomicBoolean stopping = new AtomicBoolean(false);
    private AtomicBoolean stopped = new AtomicBoolean(false);
    private List<ServiceListener> serviceListeners = new CopyOnWriteArrayList<ServiceListener>();

    public static void dispose(Service service2) {
        try {
            service2.stop();
        }
        catch (Exception e) {
            LOG.debug("Could not stop service: " + service2 + ". Reason: " + e, e);
        }
    }

    @Override
    public void start() throws Exception {
        if (this.started.compareAndSet(false, true)) {
            boolean success = false;
            this.stopped.set(false);
            try {
                this.preStart();
                this.doStart();
                success = true;
            }
            finally {
                this.started.set(success);
            }
            for (ServiceListener l : this.serviceListeners) {
                l.started(this);
            }
        }
    }

    @Override
    public void stop() throws Exception {
        if (this.stopped.compareAndSet(false, true)) {
            this.stopping.set(true);
            ServiceStopper stopper = new ServiceStopper();
            try {
                this.doStop(stopper);
            }
            catch (Exception e) {
                stopper.onException(this, e);
            }
            finally {
                this.postStop(stopper);
            }
            this.stopped.set(true);
            this.started.set(false);
            this.stopping.set(false);
            for (ServiceListener l : this.serviceListeners) {
                l.stopped(this);
            }
            stopper.throwFirstException();
        }
    }

    public boolean isStarted() {
        return this.started.get();
    }

    public boolean isStopping() {
        return this.stopping.get();
    }

    public boolean isStopped() {
        return this.stopped.get();
    }

    public void addServiceListener(ServiceListener l) {
        this.serviceListeners.add(l);
    }

    public void removeServiceListener(ServiceListener l) {
        this.serviceListeners.remove(l);
    }

    protected void postStop(ServiceStopper stopper) throws Exception {
    }

    protected abstract void doStop(ServiceStopper var1) throws Exception;

    protected void preStart() throws Exception {
    }

    protected abstract void doStart() throws Exception;
}

