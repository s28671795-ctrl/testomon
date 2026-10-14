/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  sun.misc.Contended
 */
package org.glassfish.jersey.internal.jsr166;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import org.glassfish.jersey.internal.jsr166.Flow;
import org.glassfish.jersey.internal.jsr166.SubmittableFlowPublisher;
import org.glassfish.jersey.internal.jsr166.UnsafeAccessor;
import sun.misc.Contended;
import sun.misc.Unsafe;

public class SubmissionPublisher<T>
implements Flow.Publisher<T>,
SubmittableFlowPublisher<T>,
AutoCloseable {
    static final int BUFFER_CAPACITY_LIMIT = 0x40000000;
    private static final Executor ASYNC_POOL = ForkJoinPool.getCommonPoolParallelism() > 1 ? ForkJoinPool.commonPool() : new ThreadPerTaskExecutor();
    BufferedSubscription<T> clients;
    volatile boolean closed;
    volatile Throwable closedException;
    final Executor executor;
    final BiConsumer<? super Flow.Subscriber<? super T>, ? super Throwable> onNextHandler;
    final int maxBufferCapacity;

    static final int roundCapacity(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        return (n |= n >>> 16) <= 0 ? 1 : (n >= 0x40000000 ? 0x40000000 : n + 1);
    }

    public SubmissionPublisher(Executor executor2, int maxBufferCapacity, BiConsumer<? super Flow.Subscriber<? super T>, ? super Throwable> handler) {
        if (executor2 == null) {
            throw new NullPointerException();
        }
        if (maxBufferCapacity <= 0) {
            throw new IllegalArgumentException("capacity must be positive");
        }
        this.executor = executor2;
        this.onNextHandler = handler;
        this.maxBufferCapacity = SubmissionPublisher.roundCapacity(maxBufferCapacity);
    }

    public SubmissionPublisher(Executor executor2, int maxBufferCapacity) {
        this(executor2, maxBufferCapacity, null);
    }

    public SubmissionPublisher() {
        this(ASYNC_POOL, Flow.defaultBufferSize(), null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void subscribe(Flow.Subscriber<? super T> subscriber) {
        if (subscriber == null) {
            throw new NullPointerException();
        }
        BufferedSubscription<T> subscription = new BufferedSubscription<T>(subscriber, this.executor, this.onNextHandler, this.maxBufferCapacity);
        SubmissionPublisher submissionPublisher = this;
        synchronized (submissionPublisher) {
            BufferedSubscription<T> b = this.clients;
            BufferedSubscription<T> pred = null;
            while (true) {
                if (b == null) {
                    subscription.onSubscribe();
                    Throwable ex = this.closedException;
                    if (ex != null) {
                        subscription.onError(ex);
                        break;
                    }
                    if (this.closed) {
                        subscription.onComplete();
                        break;
                    }
                    if (pred == null) {
                        this.clients = subscription;
                        break;
                    }
                    pred.next = subscription;
                    break;
                }
                BufferedSubscription next = b.next;
                if (b.isDisabled()) {
                    b.next = null;
                    if (pred == null) {
                        this.clients = next;
                    } else {
                        pred.next = next;
                    }
                } else {
                    if (subscriber.equals(b.subscriber)) {
                        b.onError(new IllegalStateException("Duplicate subscribe"));
                        break;
                    }
                    pred = b;
                }
                b = next;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int submit(T item) {
        boolean complete;
        if (item == null) {
            throw new NullPointerException();
        }
        int lag = 0;
        SubmissionPublisher submissionPublisher = this;
        synchronized (submissionPublisher) {
            complete = this.closed;
            BufferedSubscription<T> b = this.clients;
            if (!complete) {
                int stat;
                BufferedSubscription<T> pred = null;
                BufferedSubscription<T> r = null;
                BufferedSubscription<T> rtail = null;
                while (b != null) {
                    BufferedSubscription next = b.next;
                    stat = b.offer(item);
                    if (stat < 0) {
                        b.next = null;
                        if (pred == null) {
                            this.clients = next;
                        } else {
                            pred.next = next;
                        }
                    } else {
                        if (stat > lag) {
                            lag = stat;
                        } else if (stat == 0) {
                            b.nextRetry = null;
                            if (rtail == null) {
                                r = b;
                            } else {
                                rtail.nextRetry = b;
                            }
                            rtail = b;
                        }
                        pred = b;
                    }
                    b = next;
                }
                while (r != null) {
                    BufferedSubscription<T> nextRetry = r.nextRetry;
                    r.nextRetry = null;
                    stat = r.submit(item);
                    if (stat > lag) {
                        lag = stat;
                    } else if (stat < 0 && this.clients == r) {
                        this.clients = r.next;
                    }
                    r = nextRetry;
                }
            }
        }
        if (complete) {
            throw new IllegalStateException("Closed");
        }
        return lag;
    }

    @Override
    public int offer(T item, BiPredicate<Flow.Subscriber<? super T>, ? super T> onDrop) {
        return this.doOffer(0L, item, onDrop);
    }

    @Override
    public int offer(T item, long timeout2, TimeUnit unit, BiPredicate<Flow.Subscriber<? super T>, ? super T> onDrop) {
        return this.doOffer(unit.toNanos(timeout2), item, onDrop);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    final int doOffer(long nanos, T item, BiPredicate<Flow.Subscriber<? super T>, ? super T> onDrop) {
        boolean complete;
        if (item == null) {
            throw new NullPointerException();
        }
        int lag = 0;
        int drops = 0;
        SubmissionPublisher submissionPublisher = this;
        synchronized (submissionPublisher) {
            complete = this.closed;
            BufferedSubscription<T> b = this.clients;
            if (!complete) {
                int stat;
                BufferedSubscription<T> pred = null;
                BufferedSubscription<T> r = null;
                BufferedSubscription<T> rtail = null;
                while (b != null) {
                    BufferedSubscription next = b.next;
                    stat = b.offer(item);
                    if (stat < 0) {
                        b.next = null;
                        if (pred == null) {
                            this.clients = next;
                        } else {
                            pred.next = next;
                        }
                    } else {
                        if (stat > lag) {
                            lag = stat;
                        } else if (stat == 0) {
                            b.nextRetry = null;
                            if (rtail == null) {
                                r = b;
                            } else {
                                rtail.nextRetry = b;
                            }
                            rtail = b;
                        } else if (stat > lag) {
                            lag = stat;
                        }
                        pred = b;
                    }
                    b = next;
                }
                while (r != null) {
                    BufferedSubscription nextRetry = r.nextRetry;
                    r.nextRetry = null;
                    int n = stat = nanos > 0L ? r.timedOffer(item, nanos) : r.offer(item);
                    if (stat == 0 && onDrop != null && onDrop.test(r.subscriber, item)) {
                        stat = r.offer(item);
                    }
                    if (stat == 0) {
                        ++drops;
                    } else if (stat > lag) {
                        lag = stat;
                    } else if (stat < 0 && this.clients == r) {
                        this.clients = r.next;
                    }
                    r = nextRetry;
                }
            }
        }
        if (complete) {
            throw new IllegalStateException("Closed");
        }
        return drops > 0 ? -drops : lag;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() {
        if (!this.closed) {
            BufferedSubscription<T> b;
            SubmissionPublisher submissionPublisher = this;
            synchronized (submissionPublisher) {
                b = this.clients;
                this.clients = null;
                this.closed = true;
            }
            while (b != null) {
                BufferedSubscription next = b.next;
                b.next = null;
                b.onComplete();
                b = next;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void closeExceptionally(Throwable error) {
        if (error == null) {
            throw new NullPointerException();
        }
        if (!this.closed) {
            BufferedSubscription<T> b;
            SubmissionPublisher submissionPublisher = this;
            synchronized (submissionPublisher) {
                b = this.clients;
                this.clients = null;
                this.closed = true;
                this.closedException = error;
            }
            while (b != null) {
                BufferedSubscription next = b.next;
                b.next = null;
                b.onError(error);
                b = next;
            }
        }
    }

    public boolean isClosed() {
        return this.closed;
    }

    @Override
    public Throwable getClosedException() {
        return this.closedException;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean hasSubscribers() {
        boolean nonEmpty = false;
        if (!this.closed) {
            SubmissionPublisher submissionPublisher = this;
            synchronized (submissionPublisher) {
                BufferedSubscription<T> b = this.clients;
                while (b != null) {
                    BufferedSubscription next = b.next;
                    if (b.isDisabled()) {
                        b.next = null;
                        this.clients = next;
                        b = this.clients;
                        continue;
                    }
                    nonEmpty = true;
                    break;
                }
            }
        }
        return nonEmpty;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getNumberOfSubscribers() {
        int count = 0;
        if (!this.closed) {
            SubmissionPublisher submissionPublisher = this;
            synchronized (submissionPublisher) {
                BufferedSubscription<T> pred = null;
                BufferedSubscription<T> b = this.clients;
                while (b != null) {
                    BufferedSubscription next = b.next;
                    if (b.isDisabled()) {
                        b.next = null;
                        if (pred == null) {
                            this.clients = next;
                        } else {
                            pred.next = next;
                        }
                    } else {
                        pred = b;
                        ++count;
                    }
                    b = next;
                }
            }
        }
        return count;
    }

    public Executor getExecutor() {
        return this.executor;
    }

    @Override
    public int getMaxBufferCapacity() {
        return this.maxBufferCapacity;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public List<Flow.Subscriber<? super T>> getSubscribers() {
        ArrayList<Flow.Subscriber<T>> subs = new ArrayList<Flow.Subscriber<T>>();
        SubmissionPublisher submissionPublisher = this;
        synchronized (submissionPublisher) {
            Object pred = null;
            BufferedSubscription<T> b = this.clients;
            while (b != null) {
                BufferedSubscription next = b.next;
                if (b.isDisabled()) {
                    b.next = null;
                    if (pred == null) {
                        this.clients = next;
                    } else {
                        pred.next = next;
                    }
                } else {
                    subs.add(b.subscriber);
                }
                b = next;
            }
        }
        return subs;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean isSubscribed(Flow.Subscriber<? super T> subscriber) {
        if (subscriber == null) {
            throw new NullPointerException();
        }
        if (!this.closed) {
            SubmissionPublisher submissionPublisher = this;
            synchronized (submissionPublisher) {
                BufferedSubscription<T> pred = null;
                BufferedSubscription<T> b = this.clients;
                while (b != null) {
                    BufferedSubscription next = b.next;
                    if (b.isDisabled()) {
                        b.next = null;
                        if (pred == null) {
                            this.clients = next;
                        } else {
                            pred.next = next;
                        }
                    } else {
                        if (subscriber.equals(b.subscriber)) {
                            return true;
                        }
                        pred = b;
                    }
                    b = next;
                }
            }
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long estimateMinimumDemand() {
        long min2 = Long.MAX_VALUE;
        boolean nonEmpty = false;
        SubmissionPublisher submissionPublisher = this;
        synchronized (submissionPublisher) {
            BufferedSubscription<T> pred = null;
            BufferedSubscription<T> b = this.clients;
            while (b != null) {
                BufferedSubscription next = b.next;
                int n = b.estimateLag();
                if (n < 0) {
                    b.next = null;
                    if (pred == null) {
                        this.clients = next;
                    } else {
                        pred.next = next;
                    }
                } else {
                    long d = b.demand - (long)n;
                    if (d < min2) {
                        min2 = d;
                    }
                    nonEmpty = true;
                    pred = b;
                }
                b = next;
            }
        }
        return nonEmpty ? min2 : 0L;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int estimateMaximumLag() {
        int max = 0;
        SubmissionPublisher submissionPublisher = this;
        synchronized (submissionPublisher) {
            BufferedSubscription<T> pred = null;
            BufferedSubscription<T> b = this.clients;
            while (b != null) {
                BufferedSubscription next = b.next;
                int n = b.estimateLag();
                if (n < 0) {
                    b.next = null;
                    if (pred == null) {
                        this.clients = next;
                    } else {
                        pred.next = next;
                    }
                } else {
                    if (n > max) {
                        max = n;
                    }
                    pred = b;
                }
                b = next;
            }
        }
        return max;
    }

    @Override
    public CompletableFuture<Void> consume(Consumer<? super T> consumer) {
        if (consumer == null) {
            throw new NullPointerException();
        }
        CompletableFuture<Void> status = new CompletableFuture<Void>();
        this.subscribe(new ConsumerSubscriber<T>(status, consumer));
        return status;
    }

    @Contended
    private static final class BufferedSubscription<T>
    implements Flow.Subscription,
    ForkJoinPool.ManagedBlocker {
        long timeout;
        volatile long demand;
        int maxCapacity;
        int putStat;
        volatile int ctl;
        volatile int head;
        int tail;
        Object[] array;
        Flow.Subscriber<? super T> subscriber;
        Executor executor;
        BiConsumer<? super Flow.Subscriber<? super T>, ? super Throwable> onNextHandler;
        volatile Throwable pendingError;
        volatile Thread waiter;
        T putItem;
        BufferedSubscription<T> next;
        BufferedSubscription<T> nextRetry;
        static final int ACTIVE = 1;
        static final int CONSUME = 2;
        static final int DISABLED = 4;
        static final int ERROR = 8;
        static final int SUBSCRIBE = 16;
        static final int COMPLETE = 32;
        static final long INTERRUPTED = -1L;
        static final int DEFAULT_INITIAL_CAP = 32;
        private static final Unsafe U = UnsafeAccessor.getUnsafe();
        private static final long CTL;
        private static final long TAIL;
        private static final long HEAD;
        private static final long DEMAND;
        private static final int ABASE;
        private static final int ASHIFT;

        BufferedSubscription(Flow.Subscriber<? super T> subscriber, Executor executor2, BiConsumer<? super Flow.Subscriber<? super T>, ? super Throwable> onNextHandler, int maxBufferCapacity) {
            this.subscriber = subscriber;
            this.executor = executor2;
            this.onNextHandler = onNextHandler;
            this.maxCapacity = maxBufferCapacity;
            this.array = new Object[maxBufferCapacity < 32 ? (maxBufferCapacity < 2 ? 2 : maxBufferCapacity) : 32];
        }

        public String toString() {
            if (this.subscriber != null) {
                return this.subscriber.toString();
            }
            return super.toString();
        }

        final boolean isDisabled() {
            return this.ctl == 4;
        }

        final int estimateLag() {
            int n;
            return this.ctl == 4 ? -1 : ((n = this.tail - this.head) > 0 ? n : 0);
        }

        final int offer(T item) {
            int stat;
            int size;
            int cap;
            int h2 = this.head;
            int t2 = this.tail;
            Object[] a = this.array;
            if (a != null && (cap = a.length) > 0 && cap >= (size = t2 + 1 - h2)) {
                a[cap - 1 & t2] = item;
                this.tail = t2 + 1;
                stat = size;
            } else {
                stat = this.growAndAdd(a, item);
            }
            return stat > 0 && (this.ctl & 3) != 3 ? this.startOnOffer(stat) : stat;
        }

        private int growAndAdd(Object[] a, T item) {
            boolean alloc;
            int stat;
            int cap;
            if ((this.ctl & 0xC) != 0) {
                cap = 0;
                stat = -1;
                alloc = false;
            } else if (a == null || (cap = a.length) <= 0) {
                cap = 0;
                stat = 1;
                alloc = true;
            } else {
                U.fullFence();
                int h2 = this.head;
                int t2 = this.tail;
                int size = t2 + 1 - h2;
                if (cap >= size) {
                    a[cap - 1 & t2] = item;
                    this.tail = t2 + 1;
                    stat = size;
                    alloc = false;
                } else if (cap >= this.maxCapacity) {
                    stat = 0;
                    alloc = false;
                } else {
                    stat = cap + 1;
                    alloc = true;
                }
            }
            if (alloc) {
                int newCap;
                int n = newCap = cap > 0 ? cap << 1 : 1;
                if (newCap <= cap) {
                    stat = 0;
                } else {
                    Object[] newArray = null;
                    try {
                        newArray = new Object[newCap];
                    }
                    catch (Throwable size) {
                        // empty catch block
                    }
                    if (newArray == null) {
                        if (cap > 0) {
                            this.maxCapacity = cap;
                        }
                        stat = 0;
                    } else {
                        this.array = newArray;
                        int t3 = this.tail;
                        int newMask = newCap - 1;
                        if (a != null && cap > 0) {
                            int mask = cap - 1;
                            for (int j = this.head; j != t3; ++j) {
                                long k = ((long)(j & mask) << ASHIFT) + (long)ABASE;
                                Object x = U.getObjectVolatile(a, k);
                                if (x == null || !U.compareAndSwapObject(a, k, x, null)) continue;
                                newArray[j & newMask] = x;
                            }
                        }
                        newArray[t3 & newMask] = item;
                        this.tail = t3 + 1;
                    }
                }
            }
            return stat;
        }

        final int submit(T item) {
            int stat = this.offer(item);
            if (stat == 0) {
                this.putItem = item;
                this.timeout = 0L;
                this.putStat = 0;
                stat = this.putStat;
                if (stat == 0) {
                    try {
                        ForkJoinPool.managedBlock(this);
                    }
                    catch (InterruptedException ie) {
                        this.timeout = -1L;
                    }
                    stat = this.putStat;
                }
                if (this.timeout < 0L) {
                    Thread.currentThread().interrupt();
                }
            }
            return stat;
        }

        final int timedOffer(T item, long nanos) {
            int stat = this.offer(item);
            if (stat == 0 && (this.timeout = nanos) > 0L) {
                this.putItem = item;
                this.putStat = 0;
                stat = this.putStat;
                if (stat == 0) {
                    try {
                        ForkJoinPool.managedBlock(this);
                    }
                    catch (InterruptedException ie) {
                        this.timeout = -1L;
                    }
                    stat = this.putStat;
                }
                if (this.timeout < 0L) {
                    Thread.currentThread().interrupt();
                }
            }
            return stat;
        }

        private int startOnOffer(int stat) {
            block6: {
                Executor e;
                int c;
                while (true) {
                    if ((c = this.ctl) == 4 || (e = this.executor) == null) {
                        stat = -1;
                        break block6;
                    }
                    if ((c & 1) != 0) {
                        if ((c & 2) == 0 && !U.compareAndSwapInt(this, CTL, c, c | 2)) continue;
                        break block6;
                    }
                    if (this.demand == 0L || this.tail == this.head) break block6;
                    if (U.compareAndSwapInt(this, CTL, c, c | 3)) break;
                }
                try {
                    e.execute(new ConsumerTask(this));
                }
                catch (Error | RuntimeException ex) {
                    while (((c = this.ctl) & 4) == 0 && (c & 1) != 0 && !U.compareAndSwapInt(this, CTL, c, c & 0xFFFFFFFE)) {
                    }
                    throw ex;
                }
            }
            return stat;
        }

        private void signalWaiter(Thread w) {
            this.waiter = null;
            LockSupport.unpark(w);
        }

        private void detach() {
            Thread w = this.waiter;
            this.executor = null;
            this.subscriber = null;
            this.pendingError = null;
            this.signalWaiter(w);
        }

        final void onError(Throwable ex) {
            int c;
            while (((c = this.ctl) & 0xC) == 0) {
                if ((c & 1) != 0) {
                    this.pendingError = ex;
                    if (!U.compareAndSwapInt(this, CTL, c, c | 8)) continue;
                    break;
                }
                if (!U.compareAndSwapInt(this, CTL, c, 4)) continue;
                Flow.Subscriber<T> s2 = this.subscriber;
                if (s2 != null && ex != null) {
                    try {
                        s2.onError(ex);
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                this.detach();
                break;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void startOrDisable() {
            Executor e = this.executor;
            if (e == null) return;
            try {
                e.execute(new ConsumerTask(this));
                return;
            }
            catch (Throwable ex) {
                int c;
                do {
                    if ((c = this.ctl) == 4) return;
                    if ((c & 1) != 0) continue;
                    return;
                } while (!U.compareAndSwapInt(this, CTL, c, c & 0xFFFFFFFE));
                this.onError(ex);
                return;
            }
        }

        final void onComplete() {
            int c;
            while ((c = this.ctl) != 4) {
                if (!U.compareAndSwapInt(this, CTL, c, c | 0x23)) continue;
                if ((c & 1) != 0) break;
                this.startOrDisable();
                break;
            }
        }

        final void onSubscribe() {
            int c;
            while ((c = this.ctl) != 4) {
                if (!U.compareAndSwapInt(this, CTL, c, c | 0x13)) continue;
                if ((c & 1) != 0) break;
                this.startOrDisable();
                break;
            }
        }

        @Override
        public void cancel() {
            int c;
            while ((c = this.ctl) != 4) {
                if ((c & 1) != 0) {
                    if (!U.compareAndSwapInt(this, CTL, c, c | 0xA)) continue;
                    break;
                }
                if (!U.compareAndSwapInt(this, CTL, c, 4)) continue;
                this.detach();
                break;
            }
        }

        @Override
        public void request(long n) {
            if (n > 0L) {
                int c;
                long d;
                long prev;
                do {
                    if ((d = (prev = this.demand) + n) >= prev) continue;
                    d = Long.MAX_VALUE;
                } while (!U.compareAndSwapLong(this, DEMAND, prev, d));
                while ((c = this.ctl) != 4) {
                    if ((c & 1) != 0) {
                        if ((c & 2) != 0 || U.compareAndSwapInt(this, CTL, c, c | 2)) {
                            break;
                        }
                    } else {
                        int h2 = this.head;
                        if (h2 != this.tail) {
                            if (U.compareAndSwapInt(this, CTL, c, c | 3)) {
                                this.startOrDisable();
                                break;
                            }
                        } else if (this.head == h2 && this.tail == h2) break;
                    }
                    if (this.demand != 0L) continue;
                    break;
                }
            } else if (n < 0L) {
                this.onError(new IllegalArgumentException("negative subscription request"));
            }
        }

        @Override
        public final boolean isReleasable() {
            T item = this.putItem;
            if (item != null) {
                this.putStat = this.offer(item);
                if (this.putStat == 0) {
                    return false;
                }
                this.putItem = null;
            }
            return true;
        }

        @Override
        public final boolean block() {
            T item = this.putItem;
            if (item != null) {
                long deadline;
                this.putItem = null;
                long nanos = this.timeout;
                long l = deadline = nanos > 0L ? System.nanoTime() + nanos : 0L;
                while ((this.putStat = this.offer(item)) == 0) {
                    if (Thread.interrupted()) {
                        this.timeout = -1L;
                        if (nanos <= 0L) continue;
                        break;
                    }
                    if (nanos > 0L && (nanos = deadline - System.nanoTime()) <= 0L) break;
                    if (this.waiter == null) {
                        this.waiter = Thread.currentThread();
                        continue;
                    }
                    if (nanos > 0L) {
                        LockSupport.parkNanos(this, nanos);
                    } else {
                        LockSupport.park(this);
                    }
                    this.waiter = null;
                }
            }
            this.waiter = null;
            return true;
        }

        final void consume() {
            int h2 = this.head;
            Flow.Subscriber<Object> s2 = this.subscriber;
            if (s2 != null) {
                while (true) {
                    long i;
                    Object x;
                    int n;
                    long d = this.demand;
                    int c = this.ctl;
                    if ((c & 0x1C) != 0) {
                        if (this.checkControl(s2, c)) continue;
                        break;
                    }
                    Object[] a = this.array;
                    if (this.array == null || h2 == this.tail || (n = a.length) == 0 || (x = U.getObjectVolatile(a, i = ((long)(n - 1 & h2) << ASHIFT) + (long)ABASE)) == null) {
                        if (this.checkEmpty(s2, c)) continue;
                        break;
                    }
                    if (d == 0L) {
                        if (this.checkDemand(c)) continue;
                        break;
                    }
                    if ((c & 2) == 0 && !U.compareAndSwapInt(this, CTL, c, c | 2) || !U.compareAndSwapObject(a, i, x, null)) continue;
                    U.putOrderedInt(this, HEAD, ++h2);
                    U.getAndAddLong(this, DEMAND, -1L);
                    Thread w = this.waiter;
                    if (w != null) {
                        this.signalWaiter(w);
                    }
                    try {
                        Object y = x;
                        s2.onNext(y);
                    }
                    catch (Throwable ex) {
                        this.handleOnNext(s2, ex);
                    }
                }
            }
        }

        private boolean checkControl(Flow.Subscriber<? super T> s2, int c) {
            boolean stat = true;
            if ((c & 0x10) != 0) {
                if (U.compareAndSwapInt(this, CTL, c, c & 0xFFFFFFEF)) {
                    try {
                        if (s2 != null) {
                            s2.onSubscribe(this);
                        }
                    }
                    catch (Throwable ex) {
                        this.onError(ex);
                    }
                }
            } else if ((c & 8) != 0) {
                Throwable ex = this.pendingError;
                this.ctl = 4;
                if (ex != null) {
                    try {
                        if (s2 != null) {
                            s2.onError(ex);
                        }
                    }
                    catch (Throwable throwable) {}
                }
            } else {
                this.detach();
                stat = false;
            }
            return stat;
        }

        private boolean checkEmpty(Flow.Subscriber<? super T> s2, int c) {
            boolean stat = true;
            if (this.head == this.tail) {
                if ((c & 2) != 0) {
                    U.compareAndSwapInt(this, CTL, c, c & 0xFFFFFFFD);
                } else if ((c & 0x20) != 0) {
                    if (U.compareAndSwapInt(this, CTL, c, 4)) {
                        try {
                            if (s2 != null) {
                                s2.onComplete();
                            }
                        }
                        catch (Throwable throwable) {}
                    }
                } else if (U.compareAndSwapInt(this, CTL, c, c & 0xFFFFFFFE)) {
                    stat = false;
                }
            }
            return stat;
        }

        private boolean checkDemand(int c) {
            boolean stat = true;
            if (this.demand == 0L) {
                if ((c & 2) != 0) {
                    U.compareAndSwapInt(this, CTL, c, c & 0xFFFFFFFD);
                } else if (U.compareAndSwapInt(this, CTL, c, c & 0xFFFFFFFE)) {
                    stat = false;
                }
            }
            return stat;
        }

        private void handleOnNext(Flow.Subscriber<? super T> s2, Throwable ex) {
            BiConsumer<Flow.Subscriber<Flow.Subscriber<? super T>>, Throwable> h2 = this.onNextHandler;
            if (h2 != null) {
                try {
                    h2.accept(s2, ex);
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
            this.onError(ex);
        }

        static {
            try {
                CTL = U.objectFieldOffset(BufferedSubscription.class.getDeclaredField("ctl"));
                TAIL = U.objectFieldOffset(BufferedSubscription.class.getDeclaredField("tail"));
                HEAD = U.objectFieldOffset(BufferedSubscription.class.getDeclaredField("head"));
                DEMAND = U.objectFieldOffset(BufferedSubscription.class.getDeclaredField("demand"));
                ABASE = U.arrayBaseOffset(Object[].class);
                int scale = U.arrayIndexScale(Object[].class);
                if ((scale & scale - 1) != 0) {
                    throw new Error("data type scale not a power of two");
                }
                ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
            }
            catch (ReflectiveOperationException e) {
                throw new Error(e);
            }
            Class<LockSupport> clazz = LockSupport.class;
        }
    }

    static final class ConsumerTask<T>
    extends ForkJoinTask<Void>
    implements Runnable,
    CompletableFuture.AsynchronousCompletionTask {
        final BufferedSubscription<T> consumer;

        ConsumerTask(BufferedSubscription<T> consumer) {
            this.consumer = consumer;
        }

        @Override
        public final Void getRawResult() {
            return null;
        }

        @Override
        public final void setRawResult(Void v) {
        }

        @Override
        public final boolean exec() {
            this.consumer.consume();
            return false;
        }

        @Override
        public final void run() {
            this.consumer.consume();
        }
    }

    private static final class ConsumerSubscriber<T>
    implements Flow.Subscriber<T> {
        final CompletableFuture<Void> status;
        final Consumer<? super T> consumer;
        Flow.Subscription subscription;

        ConsumerSubscriber(CompletableFuture<Void> status, Consumer<? super T> consumer) {
            this.status = status;
            this.consumer = consumer;
        }

        @Override
        public final void onSubscribe(Flow.Subscription subscription) {
            this.subscription = subscription;
            this.status.whenComplete((v, e) -> subscription.cancel());
            if (!this.status.isDone()) {
                subscription.request(Long.MAX_VALUE);
            }
        }

        @Override
        public final void onError(Throwable ex) {
            this.status.completeExceptionally(ex);
        }

        @Override
        public final void onComplete() {
            this.status.complete(null);
        }

        @Override
        public final void onNext(T item) {
            try {
                this.consumer.accept(item);
            }
            catch (Throwable ex) {
                this.subscription.cancel();
                this.status.completeExceptionally(ex);
            }
        }
    }

    private static final class ThreadPerTaskExecutor
    implements Executor {
        private ThreadPerTaskExecutor() {
        }

        @Override
        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }
}

