/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.MoreExecutors;
import io.grpc.Status;
import io.grpc.internal.ClientTransport;
import io.grpc.internal.ConnectionClientTransport;
import io.grpc.internal.LogExceptionRunnable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;

public class KeepAliveManager {
    private static final long MIN_KEEPALIVE_TIME_NANOS = TimeUnit.SECONDS.toNanos(10L);
    private static final long MIN_KEEPALIVE_TIMEOUT_NANOS = TimeUnit.MILLISECONDS.toNanos(10L);
    private final ScheduledExecutorService scheduler;
    @GuardedBy(value="this")
    private final Stopwatch stopwatch;
    private final KeepAlivePinger keepAlivePinger;
    private final boolean keepAliveDuringTransportIdle;
    @GuardedBy(value="this")
    private State state = State.IDLE;
    @GuardedBy(value="this")
    private ScheduledFuture<?> shutdownFuture;
    @GuardedBy(value="this")
    private ScheduledFuture<?> pingFuture;
    private final Runnable shutdown = new LogExceptionRunnable(new Runnable(){

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            boolean shouldShutdown = false;
            KeepAliveManager keepAliveManager = KeepAliveManager.this;
            synchronized (keepAliveManager) {
                if (KeepAliveManager.this.state != State.DISCONNECTED) {
                    KeepAliveManager.this.state = State.DISCONNECTED;
                    shouldShutdown = true;
                }
            }
            if (shouldShutdown) {
                KeepAliveManager.this.keepAlivePinger.onPingTimeout();
            }
        }
    });
    private final Runnable sendPing = new LogExceptionRunnable(new Runnable(){

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void run() {
            boolean shouldSendPing = false;
            KeepAliveManager keepAliveManager = KeepAliveManager.this;
            synchronized (keepAliveManager) {
                KeepAliveManager.this.pingFuture = null;
                if (KeepAliveManager.this.state == State.PING_SCHEDULED) {
                    shouldSendPing = true;
                    KeepAliveManager.this.state = State.PING_SENT;
                    KeepAliveManager.this.shutdownFuture = KeepAliveManager.this.scheduler.schedule(KeepAliveManager.this.shutdown, KeepAliveManager.this.keepAliveTimeoutInNanos, TimeUnit.NANOSECONDS);
                } else if (KeepAliveManager.this.state == State.PING_DELAYED) {
                    KeepAliveManager.this.pingFuture = KeepAliveManager.this.scheduler.schedule(KeepAliveManager.this.sendPing, KeepAliveManager.this.keepAliveTimeInNanos - KeepAliveManager.this.stopwatch.elapsed(TimeUnit.NANOSECONDS), TimeUnit.NANOSECONDS);
                    KeepAliveManager.this.state = State.PING_SCHEDULED;
                }
            }
            if (shouldSendPing) {
                KeepAliveManager.this.keepAlivePinger.ping();
            }
        }
    });
    private final long keepAliveTimeInNanos;
    private final long keepAliveTimeoutInNanos;

    public KeepAliveManager(KeepAlivePinger keepAlivePinger, ScheduledExecutorService scheduler, long keepAliveTimeInNanos, long keepAliveTimeoutInNanos, boolean keepAliveDuringTransportIdle) {
        this(keepAlivePinger, scheduler, Stopwatch.createUnstarted(), keepAliveTimeInNanos, keepAliveTimeoutInNanos, keepAliveDuringTransportIdle);
    }

    @VisibleForTesting
    KeepAliveManager(KeepAlivePinger keepAlivePinger, ScheduledExecutorService scheduler, Stopwatch stopwatch, long keepAliveTimeInNanos, long keepAliveTimeoutInNanos, boolean keepAliveDuringTransportIdle) {
        this.keepAlivePinger = Preconditions.checkNotNull(keepAlivePinger, "keepAlivePinger");
        this.scheduler = Preconditions.checkNotNull(scheduler, "scheduler");
        this.stopwatch = Preconditions.checkNotNull(stopwatch, "stopwatch");
        this.keepAliveTimeInNanos = keepAliveTimeInNanos;
        this.keepAliveTimeoutInNanos = keepAliveTimeoutInNanos;
        this.keepAliveDuringTransportIdle = keepAliveDuringTransportIdle;
        stopwatch.reset().start();
    }

    public synchronized void onTransportStarted() {
        if (this.keepAliveDuringTransportIdle) {
            this.onTransportActive();
        }
    }

    public synchronized void onDataReceived() {
        this.stopwatch.reset().start();
        if (this.state == State.PING_SCHEDULED) {
            this.state = State.PING_DELAYED;
        } else if (this.state == State.PING_SENT || this.state == State.IDLE_AND_PING_SENT) {
            if (this.shutdownFuture != null) {
                this.shutdownFuture.cancel(false);
            }
            if (this.state == State.IDLE_AND_PING_SENT) {
                this.state = State.IDLE;
                return;
            }
            this.state = State.PING_SCHEDULED;
            Preconditions.checkState(this.pingFuture == null, "There should be no outstanding pingFuture");
            this.pingFuture = this.scheduler.schedule(this.sendPing, this.keepAliveTimeInNanos, TimeUnit.NANOSECONDS);
        }
    }

    public synchronized void onTransportActive() {
        if (this.state == State.IDLE) {
            this.state = State.PING_SCHEDULED;
            if (this.pingFuture == null) {
                this.pingFuture = this.scheduler.schedule(this.sendPing, this.keepAliveTimeInNanos - this.stopwatch.elapsed(TimeUnit.NANOSECONDS), TimeUnit.NANOSECONDS);
            }
        } else if (this.state == State.IDLE_AND_PING_SENT) {
            this.state = State.PING_SENT;
        }
    }

    public synchronized void onTransportIdle() {
        if (this.keepAliveDuringTransportIdle) {
            return;
        }
        if (this.state == State.PING_SCHEDULED || this.state == State.PING_DELAYED) {
            this.state = State.IDLE;
        }
        if (this.state == State.PING_SENT) {
            this.state = State.IDLE_AND_PING_SENT;
        }
    }

    public synchronized void onTransportTermination() {
        if (this.state != State.DISCONNECTED) {
            this.state = State.DISCONNECTED;
            if (this.shutdownFuture != null) {
                this.shutdownFuture.cancel(false);
            }
            if (this.pingFuture != null) {
                this.pingFuture.cancel(false);
                this.pingFuture = null;
            }
        }
    }

    public static long clampKeepAliveTimeInNanos(long keepAliveTimeInNanos) {
        return Math.max(keepAliveTimeInNanos, MIN_KEEPALIVE_TIME_NANOS);
    }

    public static long clampKeepAliveTimeoutInNanos(long keepAliveTimeoutInNanos) {
        return Math.max(keepAliveTimeoutInNanos, MIN_KEEPALIVE_TIMEOUT_NANOS);
    }

    public static final class ClientKeepAlivePinger
    implements KeepAlivePinger {
        private final ConnectionClientTransport transport;

        public ClientKeepAlivePinger(ConnectionClientTransport transport) {
            this.transport = transport;
        }

        @Override
        public void ping() {
            this.transport.ping(new ClientTransport.PingCallback(){

                @Override
                public void onSuccess(long roundTripTimeNanos) {
                }

                @Override
                public void onFailure(Throwable cause) {
                    transport.shutdownNow(Status.UNAVAILABLE.withDescription("Keepalive failed. The connection is likely gone"));
                }
            }, MoreExecutors.directExecutor());
        }

        @Override
        public void onPingTimeout() {
            this.transport.shutdownNow(Status.UNAVAILABLE.withDescription("Keepalive failed. The connection is likely gone"));
        }
    }

    public static interface KeepAlivePinger {
        public void ping();

        public void onPingTimeout();
    }

    private static enum State {
        IDLE,
        PING_SCHEDULED,
        PING_DELAYED,
        PING_SENT,
        IDLE_AND_PING_SENT,
        DISCONNECTED;

    }
}

