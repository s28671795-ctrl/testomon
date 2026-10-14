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
import java.util.stream.Stream;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.InternalManySink;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

final class SinkManyUnicast<T>
extends Flux<T>
implements InternalManySink<T>,
Disposable,
Fuseable.QueueSubscription<T>,
Fuseable {
    final Queue<T> queue;
    volatile Disposable onTerminate;
    static final AtomicReferenceFieldUpdater<SinkManyUnicast, Disposable> ON_TERMINATE = AtomicReferenceFieldUpdater.newUpdater(SinkManyUnicast.class, Disposable.class, "onTerminate");
    volatile boolean done;
    Throwable error;
    boolean hasDownstream;
    volatile CoreSubscriber<? super T> actual;
    volatile boolean cancelled;
    volatile int once;
    static final AtomicIntegerFieldUpdater<SinkManyUnicast> ONCE = AtomicIntegerFieldUpdater.newUpdater(SinkManyUnicast.class, "once");
    volatile int wip;
    static final AtomicIntegerFieldUpdater<SinkManyUnicast> WIP = AtomicIntegerFieldUpdater.newUpdater(SinkManyUnicast.class, "wip");
    volatile int discardGuard;
    static final AtomicIntegerFieldUpdater<SinkManyUnicast> DISCARD_GUARD = AtomicIntegerFieldUpdater.newUpdater(SinkManyUnicast.class, "discardGuard");
    volatile long requested;
    static final AtomicLongFieldUpdater<SinkManyUnicast> REQUESTED = AtomicLongFieldUpdater.newUpdater(SinkManyUnicast.class, "requested");
    boolean outputFused;

    static <E> SinkManyUnicast<E> create() {
        return new SinkManyUnicast(Queues.unbounded().get());
    }

    static <E> SinkManyUnicast<E> create(Queue<E> queue) {
        return new SinkManyUnicast<E>(Hooks.wrapQueue(queue));
    }

    static <E> SinkManyUnicast<E> create(Queue<E> queue, Disposable endcallback) {
        return new SinkManyUnicast<E>(Hooks.wrapQueue(queue), endcallback);
    }

    SinkManyUnicast(Queue<T> queue) {
        this.queue = Objects.requireNonNull(queue, "queue");
        this.onTerminate = null;
    }

    SinkManyUnicast(Queue<T> queue, Disposable onTerminate) {
        this.queue = Objects.requireNonNull(queue, "queue");
        this.onTerminate = Objects.requireNonNull(onTerminate, "onTerminate");
    }

    public Stream<Scannable> inners() {
        return this.hasDownstream ? Stream.of(Scannable.from(this.actual)) : Stream.empty();
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (Scannable.Attr.ACTUAL == key) {
            return this.actual;
        }
        if (Scannable.Attr.BUFFERED == key) {
            return this.queue.size();
        }
        if (Scannable.Attr.CAPACITY == key) {
            return Queues.capacity(this.queue);
        }
        if (Scannable.Attr.PREFETCH == key) {
            return Integer.MAX_VALUE;
        }
        if (Scannable.Attr.CANCELLED == key) {
            return this.cancelled;
        }
        if (Scannable.Attr.TERMINATED == key) {
            return this.done;
        }
        if (Scannable.Attr.ERROR == key) {
            return this.error;
        }
        return null;
    }

    @Override
    public Sinks.EmitResult tryEmitComplete() {
        if (this.done) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        if (this.cancelled) {
            return Sinks.EmitResult.FAIL_CANCELLED;
        }
        this.done = true;
        this.doTerminate();
        this.drain(null);
        return Sinks.EmitResult.OK;
    }

    @Override
    public Sinks.EmitResult tryEmitError(Throwable t2) {
        if (this.done) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        if (this.cancelled) {
            return Sinks.EmitResult.FAIL_CANCELLED;
        }
        this.error = t2;
        this.done = true;
        this.doTerminate();
        this.drain(null);
        return Sinks.EmitResult.OK;
    }

    @Override
    public Sinks.EmitResult tryEmitNext(T t2) {
        if (this.done) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        if (this.cancelled) {
            return Sinks.EmitResult.FAIL_CANCELLED;
        }
        if (!this.queue.offer(t2)) {
            return this.once > 0 ? Sinks.EmitResult.FAIL_OVERFLOW : Sinks.EmitResult.FAIL_ZERO_SUBSCRIBER;
        }
        this.drain(t2);
        return Sinks.EmitResult.OK;
    }

    @Override
    public int currentSubscriberCount() {
        return this.hasDownstream ? 1 : 0;
    }

    @Override
    public Flux<T> asFlux() {
        return this;
    }

    void doTerminate() {
        Disposable r = this.onTerminate;
        if (r != null && ON_TERMINATE.compareAndSet(this, r, null)) {
            r.dispose();
        }
    }

    void drainRegular(CoreSubscriber<? super T> a) {
        int missed = 1;
        Queue<T> q = this.queue;
        do {
            long e;
            long r = this.requested;
            for (e = 0L; r != e; ++e) {
                boolean empty;
                boolean d = this.done;
                T t2 = q.poll();
                boolean bl = empty = t2 == null;
                if (this.checkTerminated(d, empty, a, q, t2)) {
                    return;
                }
                if (empty) break;
                a.onNext(t2);
            }
            if (r == e && this.checkTerminated(this.done, q.isEmpty(), a, q, null)) {
                return;
            }
            if (e == 0L || r == Long.MAX_VALUE) continue;
            REQUESTED.addAndGet(this, -e);
        } while ((missed = WIP.addAndGet(this, -missed)) != 0);
    }

    void drainFused(CoreSubscriber<? super T> a) {
        int missed = 1;
        do {
            if (this.cancelled) {
                this.clear();
                this.hasDownstream = false;
                return;
            }
            boolean d = this.done;
            a.onNext(null);
            if (!d) continue;
            this.hasDownstream = false;
            Throwable ex = this.error;
            if (ex != null) {
                a.onError(ex);
            } else {
                a.onComplete();
            }
            return;
        } while ((missed = WIP.addAndGet(this, -missed)) != 0);
    }

    void drain(@Nullable T dataSignalOfferedBeforeDrain) {
        if (WIP.getAndIncrement(this) != 0) {
            if (dataSignalOfferedBeforeDrain != null) {
                if (this.cancelled) {
                    Operators.onDiscard(dataSignalOfferedBeforeDrain, this.actual.currentContext());
                } else if (this.done) {
                    Operators.onNextDropped(dataSignalOfferedBeforeDrain, this.currentContext());
                }
            }
            return;
        }
        int missed = 1;
        do {
            CoreSubscriber<? super T> a;
            if ((a = this.actual) == null) continue;
            if (this.outputFused) {
                this.drainFused(a);
            } else {
                this.drainRegular(a);
            }
            return;
        } while ((missed = WIP.addAndGet(this, -missed)) != 0);
    }

    boolean checkTerminated(boolean d, boolean empty, CoreSubscriber<? super T> a, Queue<T> q, @Nullable T t2) {
        if (this.cancelled) {
            Operators.onDiscard(t2, a.currentContext());
            Operators.onDiscardQueueWithClear(q, a.currentContext(), null);
            this.hasDownstream = false;
            return true;
        }
        if (d && empty) {
            Throwable e = this.error;
            this.hasDownstream = false;
            if (e != null) {
                a.onError(e);
            } else {
                a.onComplete();
            }
            return true;
        }
        return false;
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Context currentContext() {
        CoreSubscriber<T> actual = this.actual;
        return actual != null ? actual.currentContext() : Context.empty();
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Objects.requireNonNull(actual, "subscribe");
        if (this.once == 0 && ONCE.compareAndSet(this, 0, 1)) {
            this.hasDownstream = true;
            actual.onSubscribe(this);
            this.actual = actual;
            if (this.cancelled) {
                this.hasDownstream = false;
            } else {
                this.drain(null);
            }
        } else {
            Operators.error(actual, new IllegalStateException("Sinks.many().unicast() sinks only allow a single Subscriber"));
        }
    }

    @Override
    public void request(long n) {
        if (Operators.validate(n)) {
            Operators.addCap(REQUESTED, this, n);
            this.drain(null);
        }
    }

    @Override
    public void cancel() {
        if (this.cancelled) {
            return;
        }
        this.cancelled = true;
        this.doTerminate();
        if (WIP.getAndIncrement(this) == 0) {
            if (!this.outputFused) {
                Operators.onDiscardQueueWithClear(this.queue, this.currentContext(), null);
            }
            this.hasDownstream = false;
        }
    }

    @Override
    @Nullable
    public T poll() {
        return this.queue.poll();
    }

    @Override
    public int size() {
        return this.queue.size();
    }

    @Override
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    @Override
    public void clear() {
        if (DISCARD_GUARD.getAndIncrement(this) != 0) {
            return;
        }
        int missed = 1;
        while (true) {
            Operators.onDiscardQueueWithClear(this.queue, this.currentContext(), null);
            int dg = this.discardGuard;
            if (missed == dg) {
                if ((missed = DISCARD_GUARD.addAndGet(this, -missed)) != 0) continue;
                break;
            }
            missed = dg;
        }
    }

    @Override
    public int requestFusion(int requestedMode) {
        if ((requestedMode & 2) != 0) {
            this.outputFused = true;
            return 2;
        }
        return 0;
    }

    @Override
    public void dispose() {
        this.emitError(new CancellationException("Disposed"), Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Override
    public boolean isDisposed() {
        return this.cancelled || this.done;
    }
}

