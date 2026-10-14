/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.LongConsumer;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.CountingCallback;
import org.eclipse.jetty.util.IteratingCallback;
import org.eclipse.jetty.util.StaticException;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.IncomingFrames;
import org.eclipse.jetty.websocket.core.internal.DemandChain;

public abstract class DemandingFlusher
extends IteratingCallback
implements DemandChain {
    private static final Throwable SENTINEL_CLOSE_EXCEPTION = new StaticException("Closed");
    private final IncomingFrames _emitFrame;
    private final AtomicLong _demand = new AtomicLong();
    private final AtomicReference<Throwable> _failure = new AtomicReference();
    private LongConsumer _nextDemand;
    private Frame _frame;
    private Callback _callback;
    private boolean _needContent = true;
    private boolean _first = true;

    public DemandingFlusher(IncomingFrames emitFrame) {
        this._emitFrame = emitFrame;
    }

    protected abstract boolean handle(Frame var1, Callback var2, boolean var3);

    @Override
    public void demand(long n) {
        this._demand.getAndUpdate(d -> Math.addExact(d, n));
        this.iterate();
    }

    @Override
    public void setNextDemand(LongConsumer nextDemand) {
        this._nextDemand = nextDemand;
    }

    public void onFrame(Frame frame, Callback callback) {
        if (this._frame != null || this._callback != null) {
            throw new IllegalStateException("Not expecting onFrame");
        }
        this._frame = frame;
        this._callback = new CountingCallback(callback, 1);
        this.succeeded();
    }

    public void closeFlusher() {
        if (this._failure.compareAndSet(null, SENTINEL_CLOSE_EXCEPTION)) {
            this.failed(SENTINEL_CLOSE_EXCEPTION);
            this.iterate();
        }
    }

    public void failFlusher(Throwable t2) {
        if (this._failure.compareAndSet(null, t2)) {
            this.failed(t2);
            this.iterate();
        }
    }

    public void emitFrame(Frame frame, Callback callback) {
        if (this._demand.decrementAndGet() < 0L) {
            throw new IllegalStateException("Negative Demand");
        }
        this._emitFrame.onFrame(frame, callback);
    }

    @Override
    protected IteratingCallback.Action process() throws Throwable {
        while (true) {
            Throwable failure;
            if ((failure = this._failure.get()) != null) {
                throw failure;
            }
            if (this._demand.get() <= 0L) break;
            if (this._needContent) {
                this._needContent = false;
                this._nextDemand.accept(1L);
                return IteratingCallback.Action.SCHEDULED;
            }
            boolean first = this._first;
            this._first = false;
            boolean needContent = this.handle(this._frame, this._callback, first);
            if (!needContent) continue;
            this._needContent = true;
            this._first = true;
            this._frame = null;
            this._callback = null;
        }
        return IteratingCallback.Action.IDLE;
    }

    @Override
    protected void onCompleteFailure(Throwable cause) {
        Throwable suppressed = this._failure.getAndSet(cause);
        if (suppressed != null && suppressed != cause) {
            cause.addSuppressed(suppressed);
        }
        if (this._callback != null) {
            this._callback.failed(cause);
        }
        this._frame = null;
        this._callback = null;
    }
}

