/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.activemq.command.KeepAliveInfo;
import org.apache.activemq.command.WireFormatInfo;
import org.apache.activemq.thread.SchedulerTimerTask;
import org.apache.activemq.transport.InactivityIOException;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFilter;
import org.apache.activemq.util.ThreadPoolUtils;
import org.apache.activemq.wireformat.WireFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractInactivityMonitor
extends TransportFilter {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractInactivityMonitor.class);
    private static final long DEFAULT_CHECK_TIME_MILLS = 30000L;
    private static ThreadPoolExecutor ASYNC_TASKS;
    private static int CHECKER_COUNTER;
    private static Timer READ_CHECK_TIMER;
    private static Timer WRITE_CHECK_TIMER;
    private final AtomicBoolean monitorStarted = new AtomicBoolean(false);
    private final AtomicBoolean commandSent = new AtomicBoolean(false);
    private final AtomicBoolean inSend = new AtomicBoolean(false);
    private final AtomicBoolean failed = new AtomicBoolean(false);
    private final AtomicBoolean commandReceived = new AtomicBoolean(true);
    private final AtomicBoolean inReceive = new AtomicBoolean(false);
    private final AtomicInteger lastReceiveCounter = new AtomicInteger(0);
    private final ReentrantReadWriteLock sendLock = new ReentrantReadWriteLock();
    private SchedulerTimerTask connectCheckerTask;
    private SchedulerTimerTask writeCheckerTask;
    private SchedulerTimerTask readCheckerTask;
    private long connectAttemptTimeout = 30000L;
    private long readCheckTime = 30000L;
    private long writeCheckTime = 30000L;
    private long initialDelayTime = 30000L;
    private boolean useKeepAlive = true;
    private boolean keepAliveResponseRequired;
    protected WireFormat wireFormat;
    private final Runnable connectChecker = new Runnable(){
        private final long startTime = System.currentTimeMillis();

        @Override
        public void run() {
            block3: {
                long now = System.currentTimeMillis();
                if (now - this.startTime >= AbstractInactivityMonitor.this.connectAttemptTimeout && AbstractInactivityMonitor.this.connectCheckerTask != null && !ASYNC_TASKS.isShutdown()) {
                    LOG.debug("No connection attempt made in time for {}! Throwing InactivityIOException.", (Object)AbstractInactivityMonitor.this.toString());
                    try {
                        ASYNC_TASKS.execute(new Runnable(){

                            @Override
                            public void run() {
                                AbstractInactivityMonitor.this.onException(new InactivityIOException("Channel was inactive (no connection attempt made) for too (>" + AbstractInactivityMonitor.this.connectAttemptTimeout + ") long: " + AbstractInactivityMonitor.this.next.getRemoteAddress()));
                            }
                        });
                    }
                    catch (RejectedExecutionException ex) {
                        if (ASYNC_TASKS.isShutdown()) break block3;
                        LOG.error("Async connection timeout task was rejected from the executor: ", ex);
                        throw ex;
                    }
                }
            }
        }
    };
    private final Runnable readChecker = new Runnable(){
        long lastRunTime;

        @Override
        public void run() {
            long now = System.currentTimeMillis();
            long elapsed = now - this.lastRunTime;
            if (this.lastRunTime != 0L) {
                LOG.debug("{}ms elapsed since last read check.", (Object)elapsed);
            }
            if (!AbstractInactivityMonitor.this.allowReadCheck(elapsed)) {
                LOG.debug("Aborting read check...Not enough time elapsed since last read check.");
                return;
            }
            this.lastRunTime = now;
            AbstractInactivityMonitor.this.readCheck();
        }

        public String toString() {
            return "ReadChecker";
        }
    };
    private final Runnable writeChecker = new Runnable(){
        long lastRunTime;

        @Override
        public void run() {
            long now = System.currentTimeMillis();
            if (this.lastRunTime != 0L) {
                LOG.debug("{}: {}ms elapsed since last write check.", (Object)this, (Object)(now - this.lastRunTime));
            }
            this.lastRunTime = now;
            AbstractInactivityMonitor.this.writeCheck();
        }

        public String toString() {
            return "WriteChecker";
        }
    };
    private final ThreadFactory factory = new ThreadFactory(){

        @Override
        public Thread newThread(Runnable runnable2) {
            Thread thread2 = new Thread(runnable2, "ActiveMQ InactivityMonitor Worker");
            thread2.setDaemon(true);
            return thread2;
        }
    };

    private boolean allowReadCheck(long elapsed) {
        return elapsed > this.readCheckTime * 9L / 10L;
    }

    public AbstractInactivityMonitor(Transport next, WireFormat wireFormat) {
        super(next);
        this.wireFormat = wireFormat;
    }

    @Override
    public void start() throws Exception {
        this.next.start();
        this.startMonitorThreads();
    }

    @Override
    public void stop() throws Exception {
        this.stopMonitorThreads();
        this.next.stop();
    }

    /*
     * Unable to fully structure code
     */
    final void writeCheck() {
        if (this.inSend.get()) {
            AbstractInactivityMonitor.LOG.trace("Send in progress. Skipping write check.");
            return;
        }
        if (!this.commandSent.get() && this.useKeepAlive && this.monitorStarted.get() && !AbstractInactivityMonitor.ASYNC_TASKS.isShutdown()) {
            AbstractInactivityMonitor.LOG.trace("{} no message sent since last write check, sending a KeepAliveInfo", (Object)this);
            try {
                AbstractInactivityMonitor.ASYNC_TASKS.execute(new Runnable(){

                    @Override
                    public void run() {
                        LOG.debug("Running {}", (Object)this);
                        if (AbstractInactivityMonitor.this.monitorStarted.get()) {
                            try {
                                if (AbstractInactivityMonitor.this.sendLock.writeLock().tryLock()) {
                                    KeepAliveInfo info = new KeepAliveInfo();
                                    info.setResponseRequired(AbstractInactivityMonitor.this.keepAliveResponseRequired);
                                    AbstractInactivityMonitor.this.doOnewaySend(info);
                                }
                            }
                            catch (IOException e) {
                                AbstractInactivityMonitor.this.onException(e);
                            }
                            finally {
                                if (AbstractInactivityMonitor.this.sendLock.writeLock().isHeldByCurrentThread()) {
                                    AbstractInactivityMonitor.this.sendLock.writeLock().unlock();
                                }
                            }
                        }
                    }

                    public String toString() {
                        return "WriteCheck[" + AbstractInactivityMonitor.this.getRemoteAddress() + "]";
                    }
                });
            }
            catch (RejectedExecutionException ex) {
                if (AbstractInactivityMonitor.ASYNC_TASKS.isShutdown()) ** GOTO lbl14
                AbstractInactivityMonitor.LOG.error("Async write check was rejected from the executor: ", ex);
                throw ex;
            }
        } else {
            AbstractInactivityMonitor.LOG.trace("{} message sent since last write check, resetting flag.", (Object)this);
        }
lbl14:
        // 3 sources

        this.commandSent.set(false);
    }

    /*
     * Unable to fully structure code
     */
    final void readCheck() {
        currentCounter = this.next.getReceiveCounter();
        previousCounter = this.lastReceiveCounter.getAndSet(currentCounter);
        if (this.inReceive.get() || currentCounter != previousCounter) {
            AbstractInactivityMonitor.LOG.trace("A receive is in progress, skipping read check.");
            return;
        }
        if (!this.commandReceived.get() && this.monitorStarted.get() && !AbstractInactivityMonitor.ASYNC_TASKS.isShutdown()) {
            AbstractInactivityMonitor.LOG.debug("No message received since last read check for {}. Throwing InactivityIOException.", (Object)this);
            try {
                AbstractInactivityMonitor.ASYNC_TASKS.execute(new Runnable(){

                    @Override
                    public void run() {
                        LOG.debug("Running {}", (Object)this);
                        AbstractInactivityMonitor.this.onException(new InactivityIOException("Channel was inactive for too (>" + AbstractInactivityMonitor.this.readCheckTime + ") long: " + AbstractInactivityMonitor.this.next.getRemoteAddress()));
                    }

                    public String toString() {
                        return "ReadCheck[" + AbstractInactivityMonitor.this.getRemoteAddress() + "]";
                    }
                });
            }
            catch (RejectedExecutionException ex) {
                if (AbstractInactivityMonitor.ASYNC_TASKS.isShutdown()) ** GOTO lbl17
                AbstractInactivityMonitor.LOG.error("Async read check was rejected from the executor: ", ex);
                throw ex;
            }
        } else if (AbstractInactivityMonitor.LOG.isTraceEnabled()) {
            AbstractInactivityMonitor.LOG.trace("Message received since last read check, resetting flag: {}", (Object)this);
        }
lbl17:
        // 5 sources

        this.commandReceived.set(false);
    }

    protected abstract void processInboundWireFormatInfo(WireFormatInfo var1) throws IOException;

    protected abstract void processOutboundWireFormatInfo(WireFormatInfo var1) throws IOException;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onCommand(Object command) {
        block15: {
            this.commandReceived.set(true);
            this.inReceive.set(true);
            try {
                if (command.getClass() == KeepAliveInfo.class) {
                    KeepAliveInfo info = (KeepAliveInfo)command;
                    if (!info.isResponseRequired()) break block15;
                    this.sendLock.readLock().lock();
                    try {
                        info.setResponseRequired(false);
                        this.oneway(info);
                        break block15;
                    }
                    catch (IOException e) {
                        this.onException(e);
                        break block15;
                    }
                    finally {
                        this.sendLock.readLock().unlock();
                    }
                }
                if (command.getClass() == WireFormatInfo.class) {
                    AbstractInactivityMonitor abstractInactivityMonitor = this;
                    synchronized (abstractInactivityMonitor) {
                        try {
                            this.processInboundWireFormatInfo((WireFormatInfo)command);
                        }
                        catch (IOException e) {
                            this.onException(e);
                        }
                    }
                }
                this.transportListener.onCommand(command);
            }
            finally {
                this.inReceive.set(false);
            }
        }
    }

    @Override
    public void oneway(Object o) throws IOException {
        this.sendLock.readLock().lock();
        this.inSend.set(true);
        try {
            this.doOnewaySend(o);
        }
        finally {
            this.commandSent.set(true);
            this.inSend.set(false);
            this.sendLock.readLock().unlock();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void doOnewaySend(Object command) throws IOException {
        if (this.failed.get()) {
            throw new InactivityIOException("Cannot send, channel has already failed: " + this.next.getRemoteAddress());
        }
        if (command.getClass() == WireFormatInfo.class) {
            AbstractInactivityMonitor abstractInactivityMonitor = this;
            synchronized (abstractInactivityMonitor) {
                this.processOutboundWireFormatInfo((WireFormatInfo)command);
            }
        }
        this.next.oneway(command);
    }

    @Override
    public void onException(IOException error) {
        if (this.failed.compareAndSet(false, true)) {
            this.stopMonitorThreads();
            if (this.sendLock.writeLock().isHeldByCurrentThread()) {
                this.sendLock.writeLock().unlock();
            }
            this.transportListener.onException(error);
        }
    }

    public void setUseKeepAlive(boolean val) {
        this.useKeepAlive = val;
    }

    public long getConnectAttemptTimeout() {
        return this.connectAttemptTimeout;
    }

    public void setConnectAttemptTimeout(long connectionTimeout) {
        this.connectAttemptTimeout = connectionTimeout;
    }

    public long getReadCheckTime() {
        return this.readCheckTime;
    }

    public void setReadCheckTime(long readCheckTime) {
        this.readCheckTime = readCheckTime;
    }

    public long getWriteCheckTime() {
        return this.writeCheckTime;
    }

    public void setWriteCheckTime(long writeCheckTime) {
        this.writeCheckTime = writeCheckTime;
    }

    public long getInitialDelayTime() {
        return this.initialDelayTime;
    }

    public void setInitialDelayTime(long initialDelayTime) {
        this.initialDelayTime = initialDelayTime;
    }

    public boolean isKeepAliveResponseRequired() {
        return this.keepAliveResponseRequired;
    }

    public void setKeepAliveResponseRequired(boolean value) {
        this.keepAliveResponseRequired = value;
    }

    public boolean isMonitorStarted() {
        return this.monitorStarted.get();
    }

    protected abstract boolean configuredOk() throws IOException;

    public synchronized void startConnectCheckTask() {
        this.startConnectCheckTask(this.getConnectAttemptTimeout());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public synchronized void startConnectCheckTask(long connectionTimeout) {
        if (connectionTimeout <= 0L) {
            return;
        }
        LOG.trace("Starting connection check task for: {}", (Object)this);
        this.connectAttemptTimeout = connectionTimeout;
        if (this.connectCheckerTask != null) return;
        this.connectCheckerTask = new SchedulerTimerTask(this.connectChecker);
        Class<AbstractInactivityMonitor> clazz = AbstractInactivityMonitor.class;
        synchronized (AbstractInactivityMonitor.class) {
            if (CHECKER_COUNTER == 0) {
                if (ASYNC_TASKS == null || ASYNC_TASKS.isShutdown()) {
                    ASYNC_TASKS = this.createExecutor();
                }
                if (READ_CHECK_TIMER == null) {
                    READ_CHECK_TIMER = new Timer("ActiveMQ InactivityMonitor ReadCheckTimer", true);
                }
            }
            ++CHECKER_COUNTER;
            READ_CHECK_TIMER.schedule((TimerTask)this.connectCheckerTask, connectionTimeout);
            // ** MonitorExit[var3_2] (shouldn't be in output)
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public synchronized void stopConnectCheckTask() {
        if (this.connectCheckerTask == null) return;
        LOG.trace("Stopping connection check task for: {}", (Object)this);
        this.connectCheckerTask.cancel();
        this.connectCheckerTask = null;
        Class<AbstractInactivityMonitor> clazz = AbstractInactivityMonitor.class;
        synchronized (AbstractInactivityMonitor.class) {
            READ_CHECK_TIMER.purge();
            --CHECKER_COUNTER;
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected synchronized void startMonitorThreads() throws IOException {
        if (this.monitorStarted.get()) {
            return;
        }
        if (!this.configuredOk()) {
            return;
        }
        if (this.readCheckTime > 0L) {
            this.readCheckerTask = new SchedulerTimerTask(this.readChecker);
        }
        if (this.writeCheckTime > 0L) {
            this.writeCheckerTask = new SchedulerTimerTask(this.writeChecker);
        }
        if (this.writeCheckTime <= 0L && this.readCheckTime <= 0L) return;
        this.monitorStarted.set(true);
        Class<AbstractInactivityMonitor> clazz = AbstractInactivityMonitor.class;
        synchronized (AbstractInactivityMonitor.class) {
            if (ASYNC_TASKS == null || ASYNC_TASKS.isShutdown()) {
                ASYNC_TASKS = this.createExecutor();
            }
            if (READ_CHECK_TIMER == null) {
                READ_CHECK_TIMER = new Timer("ActiveMQ InactivityMonitor ReadCheckTimer", true);
            }
            if (WRITE_CHECK_TIMER == null) {
                WRITE_CHECK_TIMER = new Timer("ActiveMQ InactivityMonitor WriteCheckTimer", true);
            }
            ++CHECKER_COUNTER;
            if (this.readCheckTime > 0L) {
                READ_CHECK_TIMER.schedule((TimerTask)this.readCheckerTask, this.initialDelayTime, this.readCheckTime);
            }
            if (this.writeCheckTime <= 0L) return;
            WRITE_CHECK_TIMER.schedule((TimerTask)this.writeCheckerTask, this.initialDelayTime, this.writeCheckTime);
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    protected synchronized void stopMonitorThreads() {
        this.stopConnectCheckTask();
        if (!this.monitorStarted.compareAndSet(true, false)) return;
        if (this.readCheckerTask != null) {
            this.readCheckerTask.cancel();
        }
        if (this.writeCheckerTask != null) {
            this.writeCheckerTask.cancel();
        }
        Class<AbstractInactivityMonitor> clazz = AbstractInactivityMonitor.class;
        synchronized (AbstractInactivityMonitor.class) {
            WRITE_CHECK_TIMER.purge();
            READ_CHECK_TIMER.purge();
            if (--CHECKER_COUNTER != 0) return;
            WRITE_CHECK_TIMER.cancel();
            READ_CHECK_TIMER.cancel();
            WRITE_CHECK_TIMER = null;
            READ_CHECK_TIMER = null;
            try {
                ThreadPoolUtils.shutdownGraceful(ASYNC_TASKS, TimeUnit.SECONDS.toMillis(10L));
            }
            finally {
                ASYNC_TASKS = null;
            }
            // ** MonitorExit[var1_1] (shouldn't be in output)
            return;
        }
    }

    private ThreadPoolExecutor createExecutor() {
        ThreadPoolExecutor exec = new ThreadPoolExecutor(0, Integer.MAX_VALUE, (long)AbstractInactivityMonitor.getDefaultKeepAliveTime(), TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), this.factory);
        exec.allowCoreThreadTimeOut(true);
        return exec;
    }

    private static int getDefaultKeepAliveTime() {
        return Integer.getInteger("org.apache.activemq.transport.AbstractInactivityMonitor.keepAliveTime", 30);
    }
}

