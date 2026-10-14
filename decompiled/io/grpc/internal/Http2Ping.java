/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Stopwatch;
import io.grpc.internal.ClientTransport;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

public class Http2Ping {
    private static final Logger log = Logger.getLogger(Http2Ping.class.getName());
    private final long data;
    private final Stopwatch stopwatch;
    @GuardedBy(value="this")
    private Map<ClientTransport.PingCallback, Executor> callbacks = new LinkedHashMap<ClientTransport.PingCallback, Executor>();
    @GuardedBy(value="this")
    private boolean completed;
    @GuardedBy(value="this")
    private Throwable failureCause;
    @GuardedBy(value="this")
    private long roundTripTimeNanos;

    public Http2Ping(long data, Stopwatch stopwatch) {
        this.data = data;
        this.stopwatch = stopwatch;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addCallback(ClientTransport.PingCallback callback, Executor executor2) {
        Runnable runnable2;
        Http2Ping http2Ping = this;
        synchronized (http2Ping) {
            if (!this.completed) {
                this.callbacks.put(callback, executor2);
                return;
            }
            runnable2 = this.failureCause != null ? Http2Ping.asRunnable(callback, this.failureCause) : Http2Ping.asRunnable(callback, this.roundTripTimeNanos);
        }
        Http2Ping.doExecute(executor2, runnable2);
    }

    public long payload() {
        return this.data;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean complete() {
        Map<ClientTransport.PingCallback, Executor> callbacks;
        long roundTripTimeNanos;
        Http2Ping http2Ping = this;
        synchronized (http2Ping) {
            if (this.completed) {
                return false;
            }
            this.completed = true;
            roundTripTimeNanos = this.roundTripTimeNanos = this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
            callbacks = this.callbacks;
            this.callbacks = null;
        }
        for (Map.Entry entry : callbacks.entrySet()) {
            Http2Ping.doExecute((Executor)entry.getValue(), Http2Ping.asRunnable((ClientTransport.PingCallback)entry.getKey(), roundTripTimeNanos));
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void failed(Throwable failureCause) {
        Map<ClientTransport.PingCallback, Executor> callbacks;
        Http2Ping http2Ping = this;
        synchronized (http2Ping) {
            if (this.completed) {
                return;
            }
            this.completed = true;
            this.failureCause = failureCause;
            callbacks = this.callbacks;
            this.callbacks = null;
        }
        for (Map.Entry entry : callbacks.entrySet()) {
            Http2Ping.notifyFailed((ClientTransport.PingCallback)entry.getKey(), (Executor)entry.getValue(), failureCause);
        }
    }

    public static void notifyFailed(ClientTransport.PingCallback callback, Executor executor2, Throwable cause) {
        Http2Ping.doExecute(executor2, Http2Ping.asRunnable(callback, cause));
    }

    private static void doExecute(Executor executor2, Runnable runnable2) {
        try {
            executor2.execute(runnable2);
        }
        catch (Throwable th) {
            log.log(Level.SEVERE, "Failed to execute PingCallback", th);
        }
    }

    private static Runnable asRunnable(final ClientTransport.PingCallback callback, final long roundTripTimeNanos) {
        return new Runnable(){

            @Override
            public void run() {
                callback.onSuccess(roundTripTimeNanos);
            }
        };
    }

    private static Runnable asRunnable(final ClientTransport.PingCallback callback, final Throwable failureCause) {
        return new Runnable(){

            @Override
            public void run() {
                callback.onFailure(failureCause);
            }
        };
    }
}

