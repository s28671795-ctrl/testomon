/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.eclipse.jetty.util.NanoTime;
import org.eclipse.jetty.util.Promise;
import org.eclipse.jetty.util.annotation.ManagedAttribute;
import org.eclipse.jetty.util.annotation.ManagedObject;
import org.eclipse.jetty.util.thread.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface SocketAddressResolver {
    public void resolve(String var1, int var2, Promise<List<InetSocketAddress>> var3);

    @ManagedObject(value="The asynchronous address resolver")
    public static class Async
    implements SocketAddressResolver {
        private static final Logger LOG = LoggerFactory.getLogger(SocketAddressResolver.class);
        private final Executor executor;
        private final Scheduler scheduler;
        private final long timeout;

        public Async(Executor executor2, Scheduler scheduler, long timeout2) {
            this.executor = executor2;
            this.scheduler = scheduler;
            this.timeout = timeout2;
        }

        public Executor getExecutor() {
            return this.executor;
        }

        public Scheduler getScheduler() {
            return this.scheduler;
        }

        @ManagedAttribute(value="The timeout, in milliseconds, to resolve an address", readonly=true)
        public long getTimeout() {
            return this.timeout;
        }

        @Override
        public void resolve(String host2, int port, Promise<List<InetSocketAddress>> promise) {
            this.executor.execute(() -> {
                Scheduler.Task task = null;
                AtomicBoolean complete = new AtomicBoolean();
                if (this.timeout > 0L) {
                    Thread thread2 = Thread.currentThread();
                    task = this.scheduler.schedule(() -> {
                        if (complete.compareAndSet(false, true)) {
                            promise.failed(new TimeoutException("DNS timeout " + this.getTimeout() + " ms"));
                            thread2.interrupt();
                        }
                    }, this.timeout, TimeUnit.MILLISECONDS);
                }
                try {
                    long start2 = NanoTime.now();
                    InetAddress[] addresses = InetAddress.getAllByName(host2);
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Resolved {} in {} ms", (Object)host2, (Object)NanoTime.millisSince(start2));
                    }
                    ArrayList<InetSocketAddress> result = new ArrayList<InetSocketAddress>(addresses.length);
                    for (InetAddress address : addresses) {
                        result.add(new InetSocketAddress(address, port));
                    }
                    if (complete.compareAndSet(false, true)) {
                        if (result.isEmpty()) {
                            promise.failed(new UnknownHostException());
                        } else {
                            promise.succeeded(result);
                        }
                    }
                }
                catch (Throwable x) {
                    if (complete.compareAndSet(false, true)) {
                        promise.failed(x);
                    }
                }
                finally {
                    if (task != null) {
                        task.cancel();
                    }
                }
            });
        }
    }

    @ManagedObject(value="The synchronous address resolver")
    public static class Sync
    implements SocketAddressResolver {
        @Override
        public void resolve(String host2, int port, Promise<List<InetSocketAddress>> promise) {
            try {
                InetAddress[] addresses = InetAddress.getAllByName(host2);
                ArrayList<InetSocketAddress> result = new ArrayList<InetSocketAddress>(addresses.length);
                for (InetAddress address : addresses) {
                    result.add(new InetSocketAddress(address, port));
                }
                if (result.isEmpty()) {
                    promise.failed(new UnknownHostException());
                } else {
                    promise.succeeded(result);
                }
            }
            catch (Throwable x) {
                promise.failed(x);
            }
        }
    }
}

