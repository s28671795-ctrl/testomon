/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.nio;

import java.nio.channels.CancelledKeyException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.activemq.transport.nio.SelectorManager;
import org.apache.activemq.transport.nio.SelectorWorker;

public final class SelectorSelection {
    private final SelectorWorker worker;
    private final SelectorManager.Listener listener;
    private int interest;
    private SelectionKey key;
    private final AtomicBoolean closed = new AtomicBoolean();

    public SelectorSelection(final SelectorWorker worker, final AbstractSelectableChannel selectable, SelectorManager.Listener listener) throws ClosedChannelException {
        this.worker = worker;
        this.listener = listener;
        worker.addIoTask(new Runnable(){

            @Override
            public void run() {
                try {
                    SelectorSelection.this.key = selectable.register(worker.selector, 0, SelectorSelection.this);
                }
                catch (Exception e) {
                    SelectorSelection.this.onError(e);
                }
            }
        });
    }

    public void setInterestOps(int ops) {
        this.interest = ops;
    }

    public void enable() {
        this.worker.addIoTask(new Runnable(){

            @Override
            public void run() {
                try {
                    SelectorSelection.this.key.interestOps(SelectorSelection.this.interest);
                }
                catch (CancelledKeyException cancelledKeyException) {
                    // empty catch block
                }
            }
        });
    }

    public void disable() {
        this.worker.addIoTask(new Runnable(){

            @Override
            public void run() {
                try {
                    SelectorSelection.this.key.interestOps(0);
                }
                catch (CancelledKeyException cancelledKeyException) {
                    // empty catch block
                }
            }
        });
    }

    public void close() {
        if (this.closed.compareAndSet(false, true)) {
            this.worker.addIoTask(new Runnable(){

                @Override
                public void run() {
                    try {
                        SelectorSelection.this.key.cancel();
                    }
                    catch (CancelledKeyException cancelledKeyException) {
                    }
                    finally {
                        SelectorSelection.this.worker.release();
                    }
                }
            });
        }
    }

    public void onSelect() {
        this.listener.onSelect(this);
    }

    public void onError(Throwable e) {
        this.listener.onError(this, e);
    }
}

