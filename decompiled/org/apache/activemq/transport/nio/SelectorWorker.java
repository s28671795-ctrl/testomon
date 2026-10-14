/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.activemq.transport.nio.SelectorManager;
import org.apache.activemq.transport.nio.SelectorSelection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelectorWorker
implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(SelectorWorker.class);
    private static final AtomicInteger NEXT_ID = new AtomicInteger();
    final SelectorManager manager;
    final Selector selector;
    final int id = NEXT_ID.getAndIncrement();
    private final int maxChannelsPerWorker;
    final AtomicInteger retainCounter = new AtomicInteger(1);
    private final ConcurrentLinkedQueue<Runnable> ioTasks = new ConcurrentLinkedQueue();

    public SelectorWorker(SelectorManager manager) throws IOException {
        this.manager = manager;
        this.selector = Selector.open();
        this.maxChannelsPerWorker = manager.getMaxChannelsPerWorker();
        manager.getSelectorExecutor().execute(this);
    }

    void retain() {
        if (this.retainCounter.incrementAndGet() == this.maxChannelsPerWorker) {
            this.manager.onWorkerFullEvent(this);
        }
    }

    void release() {
        int use = this.retainCounter.decrementAndGet();
        if (use == 0) {
            this.manager.onWorkerEmptyEvent(this);
        } else if (use == this.maxChannelsPerWorker - 1) {
            this.manager.onWorkerNotFullEvent(this);
        }
    }

    boolean isReleased() {
        return this.retainCounter.get() == 0;
    }

    public void addIoTask(Runnable work) {
        this.ioTasks.add(work);
        this.selector.wakeup();
    }

    private void processIoTasks() {
        Runnable task;
        while ((task = this.ioTasks.poll()) != null) {
            try {
                task.run();
            }
            catch (Throwable e) {
                LOG.debug(e.getMessage(), e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        String origName = Thread.currentThread().getName();
        try {
            Thread.currentThread().setName("Selector Worker: " + this.id);
            while (!this.isReleased()) {
                this.processIoTasks();
                int count = this.selector.select(10L);
                if (count == 0) continue;
                Set<SelectionKey> keys2 = this.selector.selectedKeys();
                Iterator<SelectionKey> i = keys2.iterator();
                while (i.hasNext()) {
                    SelectionKey key = i.next();
                    i.remove();
                    final SelectorSelection s2 = (SelectorSelection)key.attachment();
                    try {
                        if (key.isValid()) {
                            key.interestOps(0);
                        }
                        this.manager.getChannelExecutor().execute(new Runnable(){

                            @Override
                            public void run() {
                                try {
                                    s2.onSelect();
                                    s2.enable();
                                }
                                catch (Throwable e) {
                                    s2.onError(e);
                                }
                            }
                        });
                    }
                    catch (Throwable e) {
                        s2.onError(e);
                    }
                }
            }
        }
        catch (Throwable e) {
            e.printStackTrace();
            Set<SelectionKey> keys3 = this.selector.keys();
            for (SelectionKey key : keys3) {
                SelectorSelection s3 = (SelectorSelection)key.attachment();
                s3.onError(e);
            }
        }
        finally {
            try {
                this.manager.onWorkerEmptyEvent(this);
                this.selector.close();
            }
            catch (IOException ignore) {
                LOG.debug(ignore.getMessage(), ignore);
            }
            Thread.currentThread().setName(origName);
        }
    }
}

