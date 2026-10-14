/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import io.grpc.Attributes;
import io.grpc.ClientCall;
import io.grpc.Context;
import io.grpc.Deadline;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.internal.ContextRunnable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class DelayedClientCall<ReqT, RespT>
extends ClientCall<ReqT, RespT> {
    private static final Logger logger = Logger.getLogger(DelayedClientCall.class.getName());
    @Nullable
    private final ScheduledFuture<?> initialDeadlineMonitor;
    private final Executor callExecutor;
    private final Context context;
    private volatile boolean passThrough;
    private ClientCall.Listener<RespT> listener;
    private ClientCall<ReqT, RespT> realCall;
    @GuardedBy(value="this")
    private Status error;
    @GuardedBy(value="this")
    private List<Runnable> pendingRunnables = new ArrayList<Runnable>();
    @GuardedBy(value="this")
    private DelayedListener<RespT> delayedListener;
    private static final ClientCall<Object, Object> NOOP_CALL = new ClientCall<Object, Object>(){

        @Override
        public void start(ClientCall.Listener<Object> responseListener, Metadata headers) {
        }

        @Override
        public void request(int numMessages) {
        }

        @Override
        public void cancel(String message, Throwable cause) {
        }

        @Override
        public void halfClose() {
        }

        @Override
        public void sendMessage(Object message) {
        }

        @Override
        public boolean isReady() {
            return false;
        }
    };

    protected DelayedClientCall(Executor callExecutor, ScheduledExecutorService scheduler, @Nullable Deadline deadline) {
        this.callExecutor = Preconditions.checkNotNull(callExecutor, "callExecutor");
        Preconditions.checkNotNull(scheduler, "scheduler");
        this.context = Context.current();
        this.initialDeadlineMonitor = this.scheduleDeadlineIfNeeded(scheduler, deadline);
    }

    private boolean isAbeforeB(@Nullable Deadline a, @Nullable Deadline b) {
        if (b == null) {
            return true;
        }
        if (a == null) {
            return false;
        }
        return a.isBefore(b);
    }

    @Nullable
    private ScheduledFuture<?> scheduleDeadlineIfNeeded(ScheduledExecutorService scheduler, @Nullable Deadline deadline) {
        String deadlineName;
        Deadline contextDeadline = this.context.getDeadline();
        if (deadline == null && contextDeadline == null) {
            return null;
        }
        long remainingNanos = Long.MAX_VALUE;
        if (deadline != null) {
            remainingNanos = deadline.timeRemaining(TimeUnit.NANOSECONDS);
        }
        if (contextDeadline != null && contextDeadline.timeRemaining(TimeUnit.NANOSECONDS) < remainingNanos) {
            remainingNanos = contextDeadline.timeRemaining(TimeUnit.NANOSECONDS);
            if (logger.isLoggable(Level.FINE)) {
                StringBuilder builder = new StringBuilder(String.format(Locale.US, "Call timeout set to '%d' ns, due to context deadline.", remainingNanos));
                if (deadline == null) {
                    builder.append(" Explicit call timeout was not set.");
                } else {
                    long callTimeout = deadline.timeRemaining(TimeUnit.NANOSECONDS);
                    builder.append(String.format(Locale.US, " Explicit call timeout was '%d' ns.", callTimeout));
                }
                logger.fine(builder.toString());
            }
        }
        long seconds = Math.abs(remainingNanos) / TimeUnit.SECONDS.toNanos(1L);
        long nanos = Math.abs(remainingNanos) % TimeUnit.SECONDS.toNanos(1L);
        final StringBuilder buf = new StringBuilder();
        String string = deadlineName = this.isAbeforeB(contextDeadline, deadline) ? "Context" : "CallOptions";
        if (remainingNanos < 0L) {
            buf.append("ClientCall started after ");
            buf.append(deadlineName);
            buf.append(" deadline was exceeded. Deadline has been exceeded for ");
        } else {
            buf.append("Deadline ");
            buf.append(deadlineName);
            buf.append(" will be exceeded in ");
        }
        buf.append(seconds);
        buf.append(String.format(Locale.US, ".%09d", nanos));
        buf.append("s. ");
        class DeadlineExceededRunnable
        implements Runnable {
            DeadlineExceededRunnable() {
            }

            @Override
            public void run() {
                DelayedClientCall.this.cancel(Status.DEADLINE_EXCEEDED.withDescription(buf.toString()), true);
            }
        }
        return scheduler.schedule(new DeadlineExceededRunnable(), remainingNanos, TimeUnit.NANOSECONDS);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final Runnable setCall(ClientCall<ReqT, RespT> call) {
        DelayedClientCall delayedClientCall = this;
        synchronized (delayedClientCall) {
            if (this.realCall != null) {
                return null;
            }
            this.setRealCall(Preconditions.checkNotNull(call, "call"));
        }
        return new ContextRunnable(this.context){

            @Override
            public void runInContext() {
                DelayedClientCall.this.drainPendingCalls();
            }
        };
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final void start(ClientCall.Listener<RespT> listener, final Metadata headers) {
        boolean savedPassThrough;
        Status savedError;
        Preconditions.checkState(this.listener == null, "already started");
        DelayedClientCall delayedClientCall = this;
        synchronized (delayedClientCall) {
            this.listener = Preconditions.checkNotNull(listener, "listener");
            savedError = this.error;
            savedPassThrough = this.passThrough;
            if (!savedPassThrough) {
                this.delayedListener = new DelayedListener<RespT>(listener);
                listener = this.delayedListener;
            }
        }
        if (savedError != null) {
            this.callExecutor.execute(new CloseListenerRunnable(listener, savedError));
            return;
        }
        if (savedPassThrough) {
            this.realCall.start(listener, headers);
        } else {
            final ClientCall.Listener<RespT> finalListener = listener;
            this.delayOrExecute(new Runnable(){

                @Override
                public void run() {
                    DelayedClientCall.this.realCall.start(finalListener, headers);
                }
            });
        }
    }

    @Override
    public final void cancel(@Nullable String message, @Nullable Throwable cause) {
        Status status = Status.CANCELLED;
        status = message != null ? status.withDescription(message) : status.withDescription("Call cancelled without message");
        if (cause != null) {
            status = status.withCause(cause);
        }
        this.cancel(status, false);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void cancel(final Status status, boolean onlyCancelPendingCall) {
        boolean delegateToRealCall = true;
        ClientCall.Listener<RespT> listenerToClose = null;
        DelayedClientCall delayedClientCall = this;
        synchronized (delayedClientCall) {
            if (this.realCall == null) {
                ClientCall<Object, Object> noopCall = NOOP_CALL;
                this.setRealCall(noopCall);
                delegateToRealCall = false;
                listenerToClose = this.listener;
                this.error = status;
            } else if (onlyCancelPendingCall) {
                return;
            }
        }
        if (delegateToRealCall) {
            this.delayOrExecute(new Runnable(){

                @Override
                public void run() {
                    DelayedClientCall.this.realCall.cancel(status.getDescription(), status.getCause());
                }
            });
        } else {
            if (listenerToClose != null) {
                this.callExecutor.execute(new CloseListenerRunnable(listenerToClose, status));
            }
            this.drainPendingCalls();
        }
        this.callCancelled();
    }

    protected void callCancelled() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void delayOrExecute(Runnable runnable2) {
        DelayedClientCall delayedClientCall = this;
        synchronized (delayedClientCall) {
            if (!this.passThrough) {
                this.pendingRunnables.add(runnable2);
                return;
            }
        }
        runnable2.run();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void drainPendingCalls() {
        DelayedListener<RespT> delayedListener;
        assert (this.realCall != null);
        assert (!this.passThrough);
        List<Runnable> toRun = new ArrayList<Runnable>();
        while (true) {
            DelayedClientCall delayedClientCall = this;
            synchronized (delayedClientCall) {
                if (this.pendingRunnables.isEmpty()) {
                    this.pendingRunnables = null;
                    this.passThrough = true;
                    delayedListener = this.delayedListener;
                    break;
                }
                ArrayList<Runnable> tmp = toRun;
                toRun = this.pendingRunnables;
                this.pendingRunnables = tmp;
            }
            for (Runnable runnable2 : toRun) {
                runnable2.run();
            }
            toRun.clear();
        }
        if (delayedListener != null) {
            final DelayedListener<RespT> listener = delayedListener;
            class DrainListenerRunnable
            extends ContextRunnable {
                DrainListenerRunnable() {
                    super(DelayedClientCall.this.context);
                }

                @Override
                public void runInContext() {
                    listener.drainPendingCallbacks();
                }
            }
            this.callExecutor.execute(new DrainListenerRunnable());
        }
    }

    @GuardedBy(value="this")
    private void setRealCall(ClientCall<ReqT, RespT> realCall) {
        Preconditions.checkState(this.realCall == null, "realCall already set to %s", this.realCall);
        if (this.initialDeadlineMonitor != null) {
            this.initialDeadlineMonitor.cancel(false);
        }
        this.realCall = realCall;
    }

    @VisibleForTesting
    final ClientCall<ReqT, RespT> getRealCall() {
        return this.realCall;
    }

    @Override
    public final void sendMessage(final ReqT message) {
        if (this.passThrough) {
            this.realCall.sendMessage(message);
        } else {
            this.delayOrExecute(new Runnable(){

                @Override
                public void run() {
                    DelayedClientCall.this.realCall.sendMessage(message);
                }
            });
        }
    }

    @Override
    public final void setMessageCompression(final boolean enable) {
        if (this.passThrough) {
            this.realCall.setMessageCompression(enable);
        } else {
            this.delayOrExecute(new Runnable(){

                @Override
                public void run() {
                    DelayedClientCall.this.realCall.setMessageCompression(enable);
                }
            });
        }
    }

    @Override
    public final void request(final int numMessages) {
        if (this.passThrough) {
            this.realCall.request(numMessages);
        } else {
            this.delayOrExecute(new Runnable(){

                @Override
                public void run() {
                    DelayedClientCall.this.realCall.request(numMessages);
                }
            });
        }
    }

    @Override
    public final void halfClose() {
        this.delayOrExecute(new Runnable(){

            @Override
            public void run() {
                DelayedClientCall.this.realCall.halfClose();
            }
        });
    }

    @Override
    public final boolean isReady() {
        if (this.passThrough) {
            return this.realCall.isReady();
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public final Attributes getAttributes() {
        ClientCall<ReqT, RespT> savedRealCall;
        DelayedClientCall delayedClientCall = this;
        synchronized (delayedClientCall) {
            savedRealCall = this.realCall;
        }
        if (savedRealCall != null) {
            return savedRealCall.getAttributes();
        }
        return Attributes.EMPTY;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this).add("realCall", this.realCall).toString();
    }

    private static final class DelayedListener<RespT>
    extends ClientCall.Listener<RespT> {
        private final ClientCall.Listener<RespT> realListener;
        private volatile boolean passThrough;
        @GuardedBy(value="this")
        private List<Runnable> pendingCallbacks = new ArrayList<Runnable>();

        public DelayedListener(ClientCall.Listener<RespT> listener) {
            this.realListener = listener;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void delayOrExecute(Runnable runnable2) {
            DelayedListener delayedListener = this;
            synchronized (delayedListener) {
                if (!this.passThrough) {
                    this.pendingCallbacks.add(runnable2);
                    return;
                }
            }
            runnable2.run();
        }

        @Override
        public void onHeaders(final Metadata headers) {
            if (this.passThrough) {
                this.realListener.onHeaders(headers);
            } else {
                this.delayOrExecute(new Runnable(){

                    @Override
                    public void run() {
                        realListener.onHeaders(headers);
                    }
                });
            }
        }

        @Override
        public void onMessage(final RespT message) {
            if (this.passThrough) {
                this.realListener.onMessage(message);
            } else {
                this.delayOrExecute(new Runnable(){

                    @Override
                    public void run() {
                        realListener.onMessage(message);
                    }
                });
            }
        }

        @Override
        public void onClose(final Status status, final Metadata trailers) {
            this.delayOrExecute(new Runnable(){

                @Override
                public void run() {
                    realListener.onClose(status, trailers);
                }
            });
        }

        @Override
        public void onReady() {
            if (this.passThrough) {
                this.realListener.onReady();
            } else {
                this.delayOrExecute(new Runnable(){

                    @Override
                    public void run() {
                        realListener.onReady();
                    }
                });
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        void drainPendingCallbacks() {
            assert (!this.passThrough);
            List<Runnable> toRun = new ArrayList<Runnable>();
            while (true) {
                DelayedListener delayedListener = this;
                synchronized (delayedListener) {
                    if (this.pendingCallbacks.isEmpty()) {
                        this.pendingCallbacks = null;
                        this.passThrough = true;
                        break;
                    }
                    ArrayList<Runnable> tmp = toRun;
                    toRun = this.pendingCallbacks;
                    this.pendingCallbacks = tmp;
                }
                for (Runnable runnable2 : toRun) {
                    runnable2.run();
                }
                toRun.clear();
            }
        }
    }

    private final class CloseListenerRunnable
    extends ContextRunnable {
        final ClientCall.Listener<RespT> listener;
        final Status status;

        CloseListenerRunnable(ClientCall.Listener<RespT> listener, Status status) {
            super(DelayedClientCall.this.context);
            this.listener = listener;
            this.status = status;
        }

        @Override
        public void runInContext() {
            this.listener.onClose(this.status, new Metadata());
        }
    }
}

