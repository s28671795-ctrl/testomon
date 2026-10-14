/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.io.IOException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.BrokerServiceAware;
import org.apache.activemq.broker.Lockable;
import org.apache.activemq.broker.Locker;
import org.apache.activemq.broker.SuppressReplyException;
import org.apache.activemq.store.PersistenceAdapter;
import org.apache.activemq.util.ServiceStopper;
import org.apache.activemq.util.ServiceSupport;
import org.apache.activemq.util.ThreadPoolUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class LockableServiceSupport
extends ServiceSupport
implements Lockable,
BrokerServiceAware {
    private static final Logger LOG = LoggerFactory.getLogger(LockableServiceSupport.class);
    boolean useLock = true;
    boolean stopOnError = false;
    Locker locker;
    long lockKeepAlivePeriod = 0L;
    private ScheduledFuture<?> keepAliveTicket;
    protected ScheduledThreadPoolExecutor clockDaemon;
    protected BrokerService brokerService;

    public abstract void init() throws Exception;

    @Override
    public void setUseLock(boolean useLock) {
        this.useLock = useLock;
    }

    public boolean isUseLock() {
        return this.useLock;
    }

    @Override
    public void setStopOnError(boolean stopOnError) {
        this.stopOnError = stopOnError;
    }

    public boolean isStopOnError() {
        return this.stopOnError;
    }

    @Override
    public void setLocker(Locker locker) throws IOException {
        this.locker = locker;
        locker.setLockable(this);
        if (this instanceof PersistenceAdapter) {
            this.locker.configure((PersistenceAdapter)((Object)this));
        }
    }

    public Locker getLocker() throws IOException {
        if (this.locker == null) {
            this.setLocker(this.createDefaultLocker());
        }
        return this.locker;
    }

    @Override
    public void setLockKeepAlivePeriod(long lockKeepAlivePeriod) {
        this.lockKeepAlivePeriod = lockKeepAlivePeriod;
    }

    @Override
    public long getLockKeepAlivePeriod() {
        return this.lockKeepAlivePeriod;
    }

    @Override
    public void preStart() throws Exception {
        this.init();
        if (this.useLock) {
            if (this.getLocker() == null) {
                LOG.warn("No locker configured");
            } else {
                this.getLocker().start();
                if (this.lockKeepAlivePeriod > 0L) {
                    this.keepAliveTicket = this.getScheduledThreadPoolExecutor().scheduleAtFixedRate(new Runnable(){

                        @Override
                        public void run() {
                            LockableServiceSupport.this.keepLockAlive();
                        }
                    }, this.lockKeepAlivePeriod, this.lockKeepAlivePeriod, TimeUnit.MILLISECONDS);
                }
            }
        }
    }

    @Override
    public void postStop(ServiceStopper stopper) throws Exception {
        if (this.useLock) {
            if (this.keepAliveTicket != null) {
                this.keepAliveTicket.cancel(false);
                this.keepAliveTicket = null;
            }
            if (this.locker != null) {
                this.getLocker().stop();
                this.locker = null;
            }
        }
        ThreadPoolUtils.shutdown(this.clockDaemon);
        this.clockDaemon = null;
    }

    protected void keepLockAlive() {
        boolean stop = false;
        try {
            Locker locker = this.getLocker();
            if (locker != null && !locker.keepAlive()) {
                stop = true;
            }
        }
        catch (SuppressReplyException e) {
            if (this.stopOnError) {
                stop = true;
            }
            LOG.warn("locker keepAlive resulted in", e);
        }
        catch (IOException e) {
            if (this.stopOnError) {
                stop = true;
            }
            LOG.warn("locker keepAlive resulted in", e);
        }
        if (stop) {
            this.stopBroker();
        }
    }

    protected void stopBroker() {
        LOG.error("{}, no longer able to keep the exclusive lock so giving up being a master", (Object)this.brokerService.getBrokerName());
        try {
            if (this.brokerService.isRestartAllowed()) {
                this.brokerService.requestRestart();
            }
            this.brokerService.stop();
        }
        catch (Exception e) {
            LOG.warn("Failure occurred while stopping broker");
        }
    }

    public ScheduledThreadPoolExecutor getScheduledThreadPoolExecutor() {
        if (this.clockDaemon == null) {
            this.clockDaemon = new ScheduledThreadPoolExecutor(5, new ThreadFactory(){

                @Override
                public Thread newThread(Runnable runnable2) {
                    Thread thread2 = new Thread(runnable2, "ActiveMQ Lock KeepAlive Timer");
                    thread2.setDaemon(true);
                    return thread2;
                }
            });
        }
        return this.clockDaemon;
    }

    public void setScheduledThreadPoolExecutor(ScheduledThreadPoolExecutor clockDaemon) {
        this.clockDaemon = clockDaemon;
    }

    @Override
    public void setBrokerService(BrokerService brokerService) {
        this.brokerService = brokerService;
    }

    public BrokerService getBrokerService() {
        return this.brokerService;
    }
}

