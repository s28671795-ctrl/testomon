/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.ContextHolder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxPublish;
import reactor.core.publisher.InternalManySink;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

final class SinkManyEmitterProcessor<T>
extends Flux<T>
implements InternalManySink<T>,
Sinks.ManyWithUpstream<T>,
CoreSubscriber<T>,
Scannable,
Disposable,
ContextHolder {
    static final FluxPublish.PubSubInner[] EMPTY = new FluxPublish.PublishInner[0];
    final int prefetch;
    final boolean autoCancel;
    volatile Subscription s;
    static final AtomicReferenceFieldUpdater<SinkManyEmitterProcessor, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(SinkManyEmitterProcessor.class, Subscription.class, "s");
    volatile FluxPublish.PubSubInner<T>[] subscribers;
    static final AtomicReferenceFieldUpdater<SinkManyEmitterProcessor, FluxPublish.PubSubInner[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(SinkManyEmitterProcessor.class, FluxPublish.PubSubInner[].class, "subscribers");
    volatile EmitterDisposable upstreamDisposable;
    static final AtomicReferenceFieldUpdater<SinkManyEmitterProcessor, EmitterDisposable> UPSTREAM_DISPOSABLE = AtomicReferenceFieldUpdater.newUpdater(SinkManyEmitterProcessor.class, EmitterDisposable.class, "upstreamDisposable");
    volatile int wip;
    static final AtomicIntegerFieldUpdater<SinkManyEmitterProcessor> WIP = AtomicIntegerFieldUpdater.newUpdater(SinkManyEmitterProcessor.class, "wip");
    volatile Queue<T> queue;
    int sourceMode;
    volatile boolean done;
    volatile Throwable error;
    static final AtomicReferenceFieldUpdater<SinkManyEmitterProcessor, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(SinkManyEmitterProcessor.class, Throwable.class, "error");

    SinkManyEmitterProcessor(boolean autoCancel, int prefetch) {
        if (prefetch < 1) {
            throw new IllegalArgumentException("bufferSize must be strictly positive, was: " + prefetch);
        }
        this.autoCancel = autoCancel;
        this.prefetch = prefetch;
        SUBSCRIBERS.lazySet(this, EMPTY);
    }

    @Override
    public Stream<? extends Scannable> inners() {
        return Stream.of(this.subscribers);
    }

    @Override
    public Context currentContext() {
        return Operators.multiSubscribersContext(this.subscribers);
    }

    private boolean isDetached() {
        return this.s == Operators.cancelledSubscription() && this.done && this.error instanceof CancellationException;
    }

    private boolean detach() {
        if (Operators.terminate(S, this)) {
            this.done = true;
            CancellationException detachException = new CancellationException("the ManyWithUpstream sink had a Subscription to an upstream which has been manually cancelled");
            if (ERROR.compareAndSet(this, null, detachException)) {
                Queue<T> q = this.queue;
                if (q != null) {
                    q.clear();
                }
                for (FluxPublish.PubSubInner<T> inner : this.terminate()) {
                    inner.actual.onError(detachException);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public Disposable subscribeTo(Publisher<? extends T> upstream) {
        EmitterDisposable ed = new EmitterDisposable(this);
        if (UPSTREAM_DISPOSABLE.compareAndSet(this, null, ed)) {
            upstream.subscribe(this);
            return ed;
        }
        throw new IllegalStateException("A Sinks.ManyWithUpstream must be subscribed to a source only once");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Objects.requireNonNull(actual, "subscribe");
        EmitterInner<T> inner = new EmitterInner<T>(actual, this);
        actual.onSubscribe(inner);
        if (inner.isCancelled()) {
            return;
        }
        if (this.add(inner)) {
            if (inner.isCancelled()) {
                this.remove(inner);
            }
            this.drain();
        } else {
            Throwable e = this.error;
            if (e != null) {
                inner.actual.onError(e);
            } else {
                inner.actual.onComplete();
            }
        }
    }

    @Override
    public void onComplete() {
        Sinks.EmitResult emitResult = this.tryEmitComplete();
    }

    @Override
    public Sinks.EmitResult tryEmitComplete() {
        if (this.done) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        this.done = true;
        this.drain();
        return Sinks.EmitResult.OK;
    }

    @Override
    public void onError(Throwable throwable) {
        this.emitError(throwable, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Override
    public Sinks.EmitResult tryEmitError(Throwable t2) {
        Objects.requireNonNull(t2, "tryEmitError must be invoked with a non-null Throwable");
        if (this.done) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        if (Exceptions.addThrowable(ERROR, this, t2)) {
            this.done = true;
            this.drain();
            return Sinks.EmitResult.OK;
        }
        return Sinks.EmitResult.FAIL_TERMINATED;
    }

    @Override
    public void onNext(T t2) {
        if (this.sourceMode == 2) {
            this.drain();
            return;
        }
        this.emitNext(t2, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Override
    public Sinks.EmitResult tryEmitNext(T t2) {
        if (this.done) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        Objects.requireNonNull(t2, "tryEmitNext must be invoked with a non-null value");
        Queue<T> q = this.queue;
        if (q == null) {
            if (Operators.setOnce(S, this, Operators.emptySubscription())) {
                q = Queues.get(this.prefetch).get();
                this.queue = q;
            } else {
                do {
                    if (!this.isCancelled()) continue;
                    return Sinks.EmitResult.FAIL_CANCELLED;
                } while ((q = this.queue) == null);
            }
        }
        if (!q.offer(t2)) {
            return this.subscribers == EMPTY ? Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER : Sinks.EmitResult.FAIL_OVERFLOW;
        }
        this.drain();
        return Sinks.EmitResult.OK;
    }

    @Override
    public int currentSubscriberCount() {
        return this.subscribers.length;
    }

    @Override
    public Flux<T> asFlux() {
        return this;
    }

    int getPending() {
        Queue<T> q = this.queue;
        return q != null ? q.size() : 0;
    }

    @Override
    public void dispose() {
        this.onError(new CancellationException("Disposed"));
    }

    @Override
    public boolean isDisposed() {
        return this.isTerminated() || this.isCancelled();
    }

    @Override
    public void onSubscribe(Subscription s2) {
        if (Operators.setOnce(S, this, s2)) {
            if (s2 instanceof Fuseable.QueueSubscription) {
                Fuseable.QueueSubscription f = (Fuseable.QueueSubscription)s2;
                int m4 = f.requestFusion(3);
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
            this.queue = Queues.get(this.prefetch).get();
            s2.request(Operators.unboundedOrPrefetch(this.prefetch));
        }
    }

    @Nullable
    Throwable getError() {
        return this.error;
    }

    boolean isCancelled() {
        return Operators.cancelledSubscription() == this.s;
    }

    public boolean isTerminated() {
        return this.done && this.getPending() == 0;
    }

    @Override
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.s;
        }
        if (key == Scannable.Attr.BUFFERED) {
            return this.getPending();
        }
        if (key == Scannable.Attr.CANCELLED) {
            return this.isCancelled();
        }
        if (key == Scannable.Attr.PREFETCH) {
            return this.getPrefetch();
        }
        if (key == Scannable.Attr.TERMINATED) {
            return this.isTerminated();
        }
        if (key == Scannable.Attr.ERROR) {
            return this.getError();
        }
        if (key == Scannable.Attr.CAPACITY) {
            return this.getPrefetch();
        }
        return null;
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
            FluxPublish.PubSubInner<T>[] a = this.subscribers;
            if (a != EMPTY && !empty) {
                long maxRequested = Long.MAX_VALUE;
                int len = a.length;
                int cancel = 0;
                for (FluxPublish.PubSubInner<T> inner : a) {
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
                    for (FluxPublish.PubSubInner<T> inner : a) {
                        inner.actual.onNext(v);
                        if (Operators.producedCancellable(FluxPublish.PublishInner.REQUESTED, inner, 1L) != Long.MIN_VALUE) continue;
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

    FluxPublish.PubSubInner<T>[] terminate() {
        return SUBSCRIBERS.getAndSet(this, FluxPublish.PublishSubscriber.TERMINATED);
    }

    boolean checkTerminated(boolean d, boolean empty) {
        if (this.s == Operators.cancelledSubscription()) {
            if (this.autoCancel) {
                this.terminate();
                Queue<T> q = this.queue;
                if (q != null) {
                    q.clear();
                }
            }
            return true;
        }
        if (d) {
            Throwable e = this.error;
            if (e != null && e != Exceptions.TERMINATED) {
                Queue<T> q = this.queue;
                if (q != null) {
                    q.clear();
                }
                for (FluxPublish.PubSubInner<T> inner : this.terminate()) {
                    inner.actual.onError(e);
                }
                return true;
            }
            if (empty) {
                for (FluxPublish.PubSubInner<T> inner : this.terminate()) {
                    inner.actual.onComplete();
                }
                return true;
            }
        }
        return false;
    }

    final boolean add(EmitterInner<T> inner) {
        FluxPublish.PubSubInner[] b;
        FluxPublish.PubSubInner<T>[] a;
        do {
            if ((a = this.subscribers) == FluxPublish.PublishSubscriber.TERMINATED) {
                return false;
            }
            int n = a.length;
            b = new FluxPublish.PubSubInner[n + 1];
            System.arraycopy(a, 0, b, 0, n);
            b[n] = inner;
        } while (!SUBSCRIBERS.compareAndSet(this, a, b));
        return true;
    }

    final void remove(FluxPublish.PubSubInner<T> inner) {
        FluxPublish.PubSubInner[] b;
        FluxPublish.PubSubInner<T>[] a;
        do {
            if ((a = this.subscribers) == FluxPublish.PublishSubscriber.TERMINATED || a == EMPTY) {
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
                b = EMPTY;
                continue;
            }
            b = new FluxPublish.PubSubInner[n - 1];
            System.arraycopy(a, 0, b, 0, j);
            System.arraycopy(a, j + 1, b, j, n - j - 1);
        } while (!SUBSCRIBERS.compareAndSet(this, a, b));
        if (this.autoCancel && b == EMPTY && Operators.terminate(S, this)) {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            this.terminate();
            Queue<T> q = this.queue;
            if (q != null) {
                q.clear();
            }
        }
    }

    static final class EmitterDisposable
    implements Disposable {
        @Nullable
        SinkManyEmitterProcessor<?> target;

        public EmitterDisposable(SinkManyEmitterProcessor<?> emitterProcessor) {
            this.target = emitterProcessor;
        }

        @Override
        public boolean isDisposed() {
            return this.target == null || ((SinkManyEmitterProcessor)this.target).isDetached();
        }

        @Override
        public void dispose() {
            SinkManyEmitterProcessor<?> t2 = this.target;
            if (t2 == null) {
                return;
            }
            if (((SinkManyEmitterProcessor)t2).detach() || ((SinkManyEmitterProcessor)t2).isDetached()) {
                this.target = null;
            }
        }
    }

    static final class EmitterInner<T>
    extends FluxPublish.PubSubInner<T> {
        final SinkManyEmitterProcessor<T> parent;

        EmitterInner(CoreSubscriber<? super T> actual, SinkManyEmitterProcessor<T> parent) {
            super(actual);
            this.parent = parent;
        }

        @Override
        void drainParent() {
            this.parent.drain();
        }

        @Override
        void removeAndDrainParent() {
            this.parent.remove(this);
            this.parent.drain();
        }
    }
}

