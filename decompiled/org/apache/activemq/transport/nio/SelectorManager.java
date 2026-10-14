/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.nio;

import java.io.IOException;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.activemq.transport.nio.SelectorSelection;
import org.apache.activemq.transport.nio.SelectorWorker;

public final class SelectorManager {
    public static final SelectorManager SINGLETON = new SelectorManager();
    private Executor selectorExecutor;
    private Executor channelExecutor;
    private final LinkedList<SelectorWorker> freeWorkers;
    private int maxChannelsPerWorker;

    public SelectorManager() {
        this.channelExecutor = this.selectorExecutor = this.createDefaultExecutor();
        this.freeWorkers = new LinkedList();
        this.maxChannelsPerWorker = -1;
    }

    protected ExecutorService createDefaultExecutor() {
        ThreadPoolExecutor rc = new ThreadPoolExecutor(SelectorManager.getDefaultCorePoolSize(), SelectorManager.getDefaultMaximumPoolSize(), SelectorManager.getDefaultKeepAliveTime(), TimeUnit.SECONDS, this.newWorkQueue(), new ThreadFactory(){
            private long i = 0L;

            @Override
            public Thread newThread(Runnable runnable2) {
                Thread t2 = new Thread(runnable2, "ActiveMQ NIO Worker " + this.i++);
                t2.setDaemon(true);
                return t2;
            }
        }, this.newRejectionHandler());
        return rc;
    }

    private RejectedExecutionHandler newRejectionHandler() {
        return SelectorManager.canRejectWork() ? new ThreadPoolExecutor.AbortPolicy() : new ThreadPoolExecutor.CallerRunsPolicy();
    }

    private BlockingQueue<Runnable> newWorkQueue() {
        int workQueueCapicity = SelectorManager.getDefaultWorkQueueCapacity();
        return workQueueCapicity > 0 ? new LinkedBlockingQueue(workQueueCapicity) : new SynchronousQueue();
    }

    private static boolean canRejectWork() {
        return Boolean.getBoolean("org.apache.activemq.transport.nio.SelectorManager.rejectWork");
    }

    private static int getDefaultWorkQueueCapacity() {
        return Integer.getInteger("org.apache.activemq.transport.nio.SelectorManager.workQueueCapacity", 0);
    }

    private static int getDefaultCorePoolSize() {
        return Integer.getInteger("org.apache.activemq.transport.nio.SelectorManager.corePoolSize", 10);
    }

    private static int getDefaultMaximumPoolSize() {
        return Integer.getInteger("org.apache.activemq.transport.nio.SelectorManager.maximumPoolSize", 1024);
    }

    private static int getDefaultKeepAliveTime() {
        return Integer.getInteger("org.apache.activemq.transport.nio.SelectorManager.keepAliveTime", 30);
    }

    private static int getDefaultMaxChannelsPerWorker() {
        return Integer.getInteger("org.apache.activemq.transport.nio.SelectorManager.maxChannelsPerWorker", 1024);
    }

    public static SelectorManager getInstance() {
        return SINGLETON;
    }

    public synchronized SelectorSelection register(AbstractSelectableChannel selectableChannel, Listener listener) throws IOException {
        SelectorSelection selection = null;
        while (selection == null) {
            SelectorWorker worker;
            if (this.freeWorkers.size() > 0) {
                worker = this.freeWorkers.getFirst();
                if (worker.isReleased()) {
                    this.freeWorkers.remove(worker);
                    continue;
                }
                worker.retain();
                selection = new SelectorSelection(worker, selectableChannel, listener);
                continue;
            }
            worker = new SelectorWorker(this);
            this.freeWorkers.addFirst(worker);
            selection = new SelectorSelection(worker, selectableChannel, listener);
        }
        return selection;
    }

    synchronized void onWorkerFullEvent(SelectorWorker worker) {
        this.freeWorkers.remove(worker);
    }

    public synchronized void onWorkerEmptyEvent(SelectorWorker worker) {
        this.freeWorkers.remove(worker);
    }

    public synchronized void onWorkerNotFullEvent(SelectorWorker worker) {
        this.freeWorkers.addFirst(worker);
    }

    public Executor getChannelExecutor() {
        return this.channelExecutor;
    }

    public void setChannelExecutor(Executor channelExecutor) {
        this.channelExecutor = channelExecutor;
    }

    public int getMaxChannelsPerWorker() {
        return this.maxChannelsPerWorker >= 0 ? this.maxChannelsPerWorker : SelectorManager.getDefaultMaxChannelsPerWorker();
    }

    public void setMaxChannelsPerWorker(int maxChannelsPerWorker) {
        this.maxChannelsPerWorker = maxChannelsPerWorker;
    }

    public Executor getSelectorExecutor() {
        return this.selectorExecutor;
    }

    public void setSelectorExecutor(Executor selectorExecutor) {
        this.selectorExecutor = selectorExecutor;
    }

    public static interface Listener {
        public void onSelect(SelectorSelection var1);

        public void onError(SelectorSelection var1, Throwable var2);
    }
}

