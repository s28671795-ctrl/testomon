/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal;

import java.util.ArrayDeque;
import java.util.Queue;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.IteratingCallback;
import org.eclipse.jetty.util.StaticException;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.internal.FrameEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TransformingFlusher {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final Throwable SENTINEL_CLOSE_EXCEPTION = new StaticException("Closed");
    private final AutoLock lock = new AutoLock();
    private final Queue<FrameEntry> entries = new ArrayDeque<FrameEntry>();
    private final IteratingCallback flusher = new Flusher();
    private boolean finished = true;
    private Throwable failure;

    protected abstract boolean onFrame(Frame var1, Callback var2, boolean var3);

    protected abstract boolean transform(Callback var1);

    public final void sendFrame(Frame frame, Callback callback, boolean batch) {
        FrameEntry entry = new FrameEntry(frame, callback, batch);
        if (this.log.isDebugEnabled()) {
            this.log.debug("Queuing {}", (Object)entry);
        }
        boolean enqueued = false;
        try (AutoLock l = this.lock.lock();){
            if (this.failure == null) {
                enqueued = this.entries.add(entry);
            }
        }
        if (enqueued) {
            this.flusher.iterate();
        } else {
            this.notifyCallbackFailure(callback, this.failure);
        }
    }

    public void closeFlusher() {
        this.failFlusher(SENTINEL_CLOSE_EXCEPTION);
    }

    public void failFlusher(Throwable t2) {
        boolean failed = false;
        try (AutoLock l = this.lock.lock();){
            if (this.failure == null) {
                this.failure = t2;
                failed = true;
            } else {
                this.failure.addSuppressed(t2);
            }
        }
        if (failed) {
            this.flusher.failed(t2);
            this.flusher.iterate();
        }
    }

    private void onFailure(Throwable t2) {
        try (AutoLock l = this.lock.lock();){
            if (this.failure == null) {
                this.failure = t2;
            }
        }
        for (FrameEntry entry : this.entries) {
            this.notifyCallbackFailure(entry.callback, t2);
        }
        this.entries.clear();
    }

    private FrameEntry pollEntry() {
        try (AutoLock l = this.lock.lock();){
            FrameEntry frameEntry = this.entries.poll();
            return frameEntry;
        }
    }

    private void notifyCallbackSuccess(Callback callback) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("notifyCallbackSuccess {}", (Object)callback);
        }
        try {
            if (callback != null) {
                callback.succeeded();
            }
        }
        catch (Throwable x) {
            this.log.warn("Exception while notifying success of callback {}", (Object)callback, (Object)x);
        }
    }

    private void notifyCallbackFailure(Callback callback, Throwable failure) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("notifyCallbackFailure {} {}", (Object)callback, (Object)failure.toString());
        }
        try {
            if (callback != null) {
                callback.failed(failure);
            }
        }
        catch (Throwable x) {
            this.log.warn("Exception while notifying failure of callback {}", (Object)callback, (Object)x);
        }
    }

    private class Flusher
    extends IteratingCallback
    implements Callback {
        private FrameEntry current;

        private Flusher() {
        }

        @Override
        protected IteratingCallback.Action process() throws Throwable {
            try (AutoLock l = TransformingFlusher.this.lock.lock();){
                if (TransformingFlusher.this.failure != null) {
                    throw TransformingFlusher.this.failure;
                }
            }
            if (TransformingFlusher.this.finished) {
                if (this.current != null) {
                    TransformingFlusher.this.notifyCallbackSuccess(this.current.callback);
                }
                this.current = TransformingFlusher.this.pollEntry();
                if (this.current == null) {
                    return IteratingCallback.Action.IDLE;
                }
                if (TransformingFlusher.this.log.isDebugEnabled()) {
                    TransformingFlusher.this.log.debug("onFrame {}", (Object)this.current);
                }
                TransformingFlusher.this.finished = TransformingFlusher.this.onFrame(this.current.frame, this, this.current.batch);
                return IteratingCallback.Action.SCHEDULED;
            }
            if (TransformingFlusher.this.log.isDebugEnabled()) {
                TransformingFlusher.this.log.debug("transform {}", (Object)this.current);
            }
            TransformingFlusher.this.finished = TransformingFlusher.this.transform(this);
            return IteratingCallback.Action.SCHEDULED;
        }

        @Override
        protected void onCompleteFailure(Throwable t2) {
            if (TransformingFlusher.this.log.isDebugEnabled()) {
                TransformingFlusher.this.log.debug("onCompleteFailure {}", (Object)t2.toString());
            }
            if (this.current != null) {
                TransformingFlusher.this.notifyCallbackFailure(this.current.callback, t2);
                this.current = null;
            }
            TransformingFlusher.this.onFailure(t2);
        }
    }
}

