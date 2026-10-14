/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxPublish<T>
extends ConnectableFlux<T>
implements Scannable {
    final Flux<? extends T> source;
    final int prefetch;
    final Supplier<? extends Queue<T>> queueSupplier;
    final boolean resetUponSourceTermination;
    volatile PublishSubscriber<T> connection;
    static final AtomicReferenceFieldUpdater<FluxPublish, PublishSubscriber> CONNECTION = AtomicReferenceFieldUpdater.newUpdater(FluxPublish.class, PublishSubscriber.class, "connection");

    FluxPublish(Flux<? extends T> source2, int prefetch, Supplier<? extends Queue<T>> queueSupplier, boolean resetUponSourceTermination) {
        if (prefetch <= 0) {
            throw new IllegalArgumentException("bufferSize > 0 required but it was " + prefetch);
        }
        this.source = Objects.requireNonNull(source2, "source");
        this.prefetch = prefetch;
        this.queueSupplier = Objects.requireNonNull(queueSupplier, "queueSupplier");
        this.resetUponSourceTermination = resetUponSourceTermination;
    }

    @Override
    public void connect(Consumer<? super Disposable> cancelSupport) {
        PublishSubscriber<T> s2;
        while ((s2 = this.connection) == null || s2.isTerminated()) {
            PublishSubscriber u = new PublishSubscriber(this.prefetch, this);
            if (!CONNECTION.compareAndSet(this, s2, u)) continue;
            s2 = u;
            break;
        }
        boolean doConnect = s2.tryConnect();
        cancelSupport.accept(s2);
        if (doConnect) {
            this.source.subscribe(s2);
        }
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        PublishInner<T> inner = new PublishInner<T>(actual);
        actual.onSubscribe(inner);
        while (!inner.isCancelled()) {
            PublishSubscriber<Object> c = this.connection;
            if (c == null || this.resetUponSourceTermination && c.isTerminated()) {
                PublishSubscriber u = new PublishSubscriber(this.prefetch, this);
                if (!CONNECTION.compareAndSet(this, c, u)) continue;
                c = u;
            }
            if (c.add(inner)) {
                if (inner.isCancelled()) {
                    c.remove(inner);
                } else {
                    inner.parent = c;
                }
                c.drain();
                break;
            }
            if (this.resetUponSourceTermination) continue;
            if (c.error != null) {
                inner.actual.onError(c.error);
                break;
            }
            inner.actual.onComplete();
            break;
        }
    }

    @Override
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PREFETCH) {
            return this.getPrefetch();
        }
        if (key == Scannable.Attr.PARENT) {
            return this.source;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class PublishInner<T>
    extends PubSubInner<T> {
        PublishSubscriber<T> parent;

        PublishInner(CoreSubscriber<? super T> actual) {
            super(actual);
        }

        @Override
        void drainParent() {
            PublishSubscriber<T> p = this.parent;
            if (p != null) {
                p.drain();
            }
        }

        @Override
        void removeAndDrainParent() {
            PublishSubscriber<T> p = this.parent;
            if (p != null) {
                p.remove(this);
                p.drain();
            }
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.parent != null && this.parent.isTerminated();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }
    }

    static abstract class PubSubInner<T>
    implements InnerProducer<T> {
        final CoreSubscriber<? super T> actual;
        volatile long requested;
        static final AtomicLongFieldUpdater<PubSubInner> REQUESTED = AtomicLongFieldUpdater.newUpdater(PubSubInner.class, "requested");

        PubSubInner(CoreSubscriber<? super T> actual) {
            this.actual = actual;
        }

        @Override
        public final void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCapCancellable(REQUESTED, this, n);
                this.drainParent();
            }
        }

        @Override
        public final void cancel() {
            long r = this.requested;
            if (r != Long.MIN_VALUE && (r = REQUESTED.getAndSet(this, Long.MIN_VALUE)) != Long.MIN_VALUE) {
                this.removeAndDrainParent();
            }
        }

        final boolean isCancelled() {
            return this.requested == Long.MIN_VALUE;
        }

        @Override
        public final CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.isCancelled();
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.isCancelled() ? 0L : this.requested;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        abstract void drainParent();

        abstract void removeAndDrainParent();
    }

    static final class PublishSubscriber<T>
    implements InnerConsumer<T>,
    Disposable {
        final int prefetch;
        final FluxPublish<T> parent;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<PublishSubscriber, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(PublishSubscriber.class, Subscription.class, "s");
        volatile PubSubInner<T>[] subscribers;
        static final AtomicReferenceFieldUpdater<PublishSubscriber, PubSubInner[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(PublishSubscriber.class, PubSubInner[].class, "subscribers");
        volatile int wip;
        static final AtomicIntegerFieldUpdater<PublishSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(PublishSubscriber.class, "wip");
        volatile int connected;
        static final AtomicIntegerFieldUpdater<PublishSubscriber> CONNECTED = AtomicIntegerFieldUpdater.newUpdater(PublishSubscriber.class, "connected");
        static final PubSubInner[] INIT = new PublishInner[0];
        static final PubSubInner[] CANCELLED = new PublishInner[0];
        static final PubSubInner[] TERMINATED = new PublishInner[0];
        volatile Queue<T> queue;
        int sourceMode;
        volatile boolean done;
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<PublishSubscriber, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(PublishSubscriber.class, Throwable.class, "error");

        PublishSubscriber(int prefetch, FluxPublish<T> parent) {
            this.prefetch = prefetch;
            this.parent = parent;
            SUBSCRIBERS.lazySet(this, INIT);
        }

        boolean isTerminated() {
            return this.subscribers == TERMINATED;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.setOnce(S, this, s2)) {
                if (s2 instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription f = (Fuseable.QueueSubscription)s2;
                    int m4 = f.requestFusion(7);
                    if (m4 == 1) {
                        this.sourceMode = m4;
                        this.queue = f;
                        this.drain();
                        return;
                    }
                    if (m4 == 2) {
                        this.sourceMode = m4;
                        this.queue = f;
                        s2.request(Operators.unboundedOrPrefetch(this.prefetch));
                        return;
                    }
                }
                this.queue = this.parent.queueSupplier.get();
                s2.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        @Override
        public void onNext(T t2) {
            if (this.done) {
                if (t2 != null) {
                    Operators.onNextDropped(t2, this.currentContext());
                }
                return;
            }
            if (this.sourceMode == 2) {
                this.drain();
                return;
            }
            if (!this.queue.offer(t2)) {
                Throwable ex = Operators.onOperatorError(this.s, Exceptions.failWithOverflow("Queue is full: Reactive Streams source doesn't respect backpressure"), t2, this.currentContext());
                if (!Exceptions.addThrowable(ERROR, this, ex)) {
                    Operators.onErrorDroppedMulticast(ex, this.subscribers);
                    return;
                }
                this.done = true;
            }
            this.drain();
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDroppedMulticast(t2, this.subscribers);
                return;
            }
            if (Exceptions.addThrowable(ERROR, this, t2)) {
                this.done = true;
                this.drain();
            } else {
                Operators.onErrorDroppedMulticast(t2, this.subscribers);
            }
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.drain();
        }

        @Override
        public void dispose() {
            if (SUBSCRIBERS.get(this) == TERMINATED) {
                return;
            }
            if (CONNECTION.compareAndSet(this.parent, this, null)) {
                Operators.terminate(S, this);
                if (WIP.getAndIncrement(this) != 0) {
                    return;
                }
                this.disconnectAction();
            }
        }

        void disconnectAction() {
            PubSubInner[] inners = SUBSCRIBERS.getAndSet(this, CANCELLED);
            if (inners.length > 0) {
                this.queue.clear();
                CancellationException ex = new CancellationException("Disconnected");
                for (PubSubInner inner : inners) {
                    inner.actual.onError(ex);
                }
            }
        }

        boolean add(PublishInner<T> inner) {
            PubSubInner[] b;
            PubSubInner<T>[] a;
            do {
                if ((a = this.subscribers) == TERMINATED) {
                    return false;
                }
                int n = a.length;
                b = new PubSubInner[n + 1];
                System.arraycopy(a, 0, b, 0, n);
                b[n] = inner;
            } while (!SUBSCRIBERS.compareAndSet(this, a, b));
            return true;
        }

        public void remove(PubSubInner<T> inner) {
            PubSubInner[] b;
            PubSubInner<T>[] a;
            do {
                if ((a = this.subscribers) == TERMINATED || a == CANCELLED) {
                    return;
                }
                int n = a.length;
                int j = -1;
                for (int i = 0; i < n; ++i) {
                    if (a[i] != inner) continue;
                    j = i;
                    break;
                }
                if (j < 0) {
                    return;
                }
                if (n == 1) {
                    b = CANCELLED;
                    continue;
                }
                b = new PubSubInner[n - 1];
                System.arraycopy(a, 0, b, 0, j);
                System.arraycopy(a, j + 1, b, j, n - j - 1);
            } while (!SUBSCRIBERS.compareAndSet(this, a, b));
        }

        PubSubInner<T>[] terminate() {
            return SUBSCRIBERS.getAndSet(this, TERMINATED);
        }

        boolean tryConnect() {
            return this.connected == 0 && CONNECTED.compareAndSet(this, 0, 1);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        final void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            int missed = 1;
            while (true) {
                boolean empty;
                boolean d = this.done;
                Queue<T> q = this.queue;
                boolean bl = empty = q == null || q.isEmpty();
                if (this.checkTerminated(d, empty)) {
                    return;
                }
                PubSubInner<T>[] a = this.subscribers;
                if (a != CANCELLED && !empty) {
                    long maxRequested = Long.MAX_VALUE;
                    int len = a.length;
                    int cancel = 0;
                    for (PubSubInner<T> inner : a) {
                        long r = inner.requested;
                        if (r >= 0L) {
                            maxRequested = Math.min(maxRequested, r);
                            continue;
                        }
                        ++cancel;
                    }
                    if (len == cancel) {
                        T v;
                        try {
                            v = q.poll();
                        }
                        catch (Throwable ex) {
                            Exceptions.addThrowable(ERROR, this, Operators.onOperatorError(this.s, ex, this.currentContext()));
                            d = true;
                            v = null;
                        }
                        if (this.checkTerminated(d, v == null)) {
                            return;
                        }
                        if (this.sourceMode == 1) continue;
                        this.s.request(1L);
                        continue;
                    }
                    int e = 0;
                    while ((long)e < maxRequested && cancel != Integer.MIN_VALUE) {
                        T v;
                        d = this.done;
                        try {
                            v = q.poll();
                        }
                        catch (Throwable ex) {
                            Exceptions.addThrowable(ERROR, this, Operators.onOperatorError(this.s, ex, this.currentContext()));
                            d = true;
                            v = null;
                        }
                        boolean bl2 = empty = v == null;
                        if (this.checkTerminated(d, empty)) {
                            return;
                        }
                        if (empty) {
                            if (this.sourceMode != 1) break;
                            this.done = true;
                            this.checkTerminated(true, true);
                            break;
                        }
                        for (PubSubInner<T> inner : a) {
                            inner.actual.onNext(v);
                            if (Operators.producedCancellable(PubSubInner.REQUESTED, inner, 1L) != Long.MIN_VALUE) continue;
                            cancel = Integer.MIN_VALUE;
                        }
                        ++e;
                    }
                    if (e != 0 && this.sourceMode != 1) {
                        this.s.request(e);
                    }
                    if (maxRequested != 0L && !empty) {
                        continue;
                    }
                } else if (this.sourceMode == 1) {
                    this.done = true;
                    if (this.checkTerminated(true, empty)) return;
                }
                if ((missed = WIP.addAndGet(this, -missed)) == 0) return;
            }
        }

        boolean checkTerminated(boolean d, boolean empty) {
            if (this.s == Operators.cancelledSubscription()) {
                this.disconnectAction();
                return true;
            }
            if (d) {
                Throwable e = this.error;
                if (e != null && e != Exceptions.TERMINATED) {
                    if (this.parent.resetUponSourceTermination) {
                        CONNECTION.compareAndSet(this.parent, this, null);
                        e = Exceptions.terminate(ERROR, this);
                    }
                    this.queue.clear();
                    for (PubSubInner<T> inner : this.terminate()) {
                        inner.actual.onError(e);
                    }
                    return true;
                }
                if (empty) {
                    if (this.parent.resetUponSourceTermination) {
                        CONNECTION.compareAndSet(this.parent, this, null);
                    }
                    for (PubSubInner<T> inner : this.terminate()) {
                        inner.actual.onComplete();
                    }
                    return true;
                }
            }
            return false;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.subscribers);
        }

        @Override
        public Context currentContext() {
            return Operators.multiSubscribersContext(this.subscribers);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.prefetch;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue != null ? this.queue.size() : 0;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.isTerminated();
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.s == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public boolean isDisposed() {
            return this.s == Operators.cancelledSubscription() || this.done;
        }
    }
}

