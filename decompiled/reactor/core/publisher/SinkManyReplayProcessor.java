/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.ContextHolder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxReplay;
import reactor.core.publisher.InternalManySink;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

final class SinkManyReplayProcessor<T>
extends Flux<T>
implements InternalManySink<T>,
CoreSubscriber<T>,
ContextHolder,
Disposable,
Fuseable,
Scannable {
    final FluxReplay.ReplayBuffer<T> buffer;
    Subscription subscription;
    volatile FluxReplay.ReplaySubscription<T>[] subscribers;
    static final AtomicReferenceFieldUpdater<SinkManyReplayProcessor, FluxReplay.ReplaySubscription[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(SinkManyReplayProcessor.class, FluxReplay.ReplaySubscription[].class, "subscribers");

    static <T> SinkManyReplayProcessor<T> cacheLast() {
        return SinkManyReplayProcessor.cacheLastOrDefault(null);
    }

    static <T> SinkManyReplayProcessor<T> cacheLastOrDefault(@Nullable T value) {
        SinkManyReplayProcessor b = SinkManyReplayProcessor.create(1);
        if (value != null) {
            b.onNext(value);
        }
        return b;
    }

    static <E> SinkManyReplayProcessor<E> create() {
        return SinkManyReplayProcessor.create(Queues.SMALL_BUFFER_SIZE, true);
    }

    static <E> SinkManyReplayProcessor<E> create(int historySize) {
        return SinkManyReplayProcessor.create(historySize, false);
    }

    static <E> SinkManyReplayProcessor<E> create(int historySize, boolean unbounded) {
        FluxReplay.ReplayBuffer buffer = unbounded ? new FluxReplay.UnboundedReplayBuffer(historySize) : new FluxReplay.SizeBoundReplayBuffer(historySize);
        return new SinkManyReplayProcessor(buffer);
    }

    static <T> SinkManyReplayProcessor<T> createTimeout(Duration maxAge) {
        return SinkManyReplayProcessor.createTimeout(maxAge, Schedulers.parallel());
    }

    static <T> SinkManyReplayProcessor<T> createTimeout(Duration maxAge, Scheduler scheduler) {
        return SinkManyReplayProcessor.createSizeAndTimeout(Integer.MAX_VALUE, maxAge, scheduler);
    }

    static <T> SinkManyReplayProcessor<T> createSizeAndTimeout(int size, Duration maxAge) {
        return SinkManyReplayProcessor.createSizeAndTimeout(size, maxAge, Schedulers.parallel());
    }

    static <T> SinkManyReplayProcessor<T> createSizeAndTimeout(int size, Duration maxAge, Scheduler scheduler) {
        Objects.requireNonNull(scheduler, "scheduler is null");
        if (size <= 0) {
            throw new IllegalArgumentException("size > 0 required but it was " + size);
        }
        return new SinkManyReplayProcessor(new FluxReplay.SizeAndTimeBoundReplayBuffer(size, maxAge.toNanos(), scheduler));
    }

    SinkManyReplayProcessor(FluxReplay.ReplayBuffer<T> buffer) {
        this.buffer = buffer;
        SUBSCRIBERS.lazySet(this, FluxReplay.ReplaySubscriber.EMPTY);
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Objects.requireNonNull(actual, "subscribe");
        ReplayInner<T> rs = new ReplayInner<T>(actual, this);
        actual.onSubscribe(rs);
        if (this.add(rs) && rs.isCancelled()) {
            this.remove(rs);
            return;
        }
        this.buffer.replay(rs);
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.subscription;
        }
        if (key == Scannable.Attr.CAPACITY) {
            return this.buffer.capacity();
        }
        if (key == Scannable.Attr.TERMINATED) {
            return this.buffer.isDone();
        }
        if (key == Scannable.Attr.ERROR) {
            return this.buffer.getError();
        }
        return null;
    }

    @Override
    public Stream<? extends Scannable> inners() {
        return Stream.of(this.subscribers);
    }

    @Override
    public void dispose() {
        this.emitError(new CancellationException("Disposed"), Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Override
    public boolean isDisposed() {
        return this.buffer.isDone();
    }

    boolean add(FluxReplay.ReplaySubscription<T> rs) {
        ReplayInner[] b;
        FluxReplay.ReplaySubscription<T>[] a;
        do {
            if ((a = this.subscribers) == FluxReplay.ReplaySubscriber.TERMINATED) {
                return false;
            }
            int n = a.length;
            b = new ReplayInner[n + 1];
            System.arraycopy(a, 0, b, 0, n);
            b[n] = rs;
        } while (!SUBSCRIBERS.compareAndSet(this, a, b));
        return true;
    }

    void remove(FluxReplay.ReplaySubscription<T> rs) {
        block0: while (true) {
            FluxReplay.ReplaySubscription<T>[] a;
            if ((a = this.subscribers) == FluxReplay.ReplaySubscriber.TERMINATED || a == FluxReplay.ReplaySubscriber.EMPTY) {
                return;
            }
            int n = a.length;
            for (int i = 0; i < n; ++i) {
                FluxReplay.ReplaySubscription[] b;
                if (a[i] != rs) continue;
                if (n == 1) {
                    b = FluxReplay.ReplaySubscriber.EMPTY;
                } else {
                    b = new ReplayInner[n - 1];
                    System.arraycopy(a, 0, b, 0, i);
                    System.arraycopy(a, i + 1, b, i, n - i - 1);
                }
                if (!SUBSCRIBERS.compareAndSet(this, a, b)) continue block0;
                return;
            }
            break;
        }
    }

    @Override
    public void onSubscribe(Subscription s2) {
        if (this.buffer.isDone()) {
            s2.cancel();
        } else if (Operators.validate(this.subscription, s2)) {
            this.subscription = s2;
            s2.request(Long.MAX_VALUE);
        }
    }

    @Override
    public Context currentContext() {
        return Operators.multiSubscribersContext(this.subscribers);
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void onComplete() {
        Sinks.EmitResult emitResult = this.tryEmitComplete();
    }

    @Override
    public Sinks.EmitResult tryEmitComplete() {
        FluxReplay.ReplaySubscription[] a;
        FluxReplay.ReplayBuffer<T> b = this.buffer;
        if (b.isDone()) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        b.onComplete();
        for (FluxReplay.ReplaySubscription rs : a = SUBSCRIBERS.getAndSet(this, FluxReplay.ReplaySubscriber.TERMINATED)) {
            b.replay(rs);
        }
        return Sinks.EmitResult.OK;
    }

    @Override
    public void onError(Throwable throwable) {
        this.emitError(throwable, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Override
    public Sinks.EmitResult tryEmitError(Throwable t2) {
        FluxReplay.ReplaySubscription[] a;
        FluxReplay.ReplayBuffer<T> b = this.buffer;
        if (b.isDone()) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        b.onError(t2);
        for (FluxReplay.ReplaySubscription rs : a = SUBSCRIBERS.getAndSet(this, FluxReplay.ReplaySubscriber.TERMINATED)) {
            b.replay(rs);
        }
        return Sinks.EmitResult.OK;
    }

    @Override
    public void onNext(T t2) {
        this.emitNext(t2, Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Override
    public Sinks.EmitResult tryEmitNext(T t2) {
        FluxReplay.ReplayBuffer<T> b = this.buffer;
        if (b.isDone()) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        b.add(t2);
        for (FluxReplay.ReplaySubscription<T> rs : this.subscribers) {
            b.replay(rs);
        }
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

    static final class ReplayInner<T>
    implements FluxReplay.ReplaySubscription<T> {
        final CoreSubscriber<? super T> actual;
        final SinkManyReplayProcessor<T> parent;
        final FluxReplay.ReplayBuffer<T> buffer;
        int index;
        int tailIndex;
        Object node;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<ReplayInner> WIP = AtomicIntegerFieldUpdater.newUpdater(ReplayInner.class, "wip");
        volatile long requested;
        static final AtomicLongFieldUpdater<ReplayInner> REQUESTED = AtomicLongFieldUpdater.newUpdater(ReplayInner.class, "requested");
        int fusionMode;

        ReplayInner(CoreSubscriber<? super T> actual, SinkManyReplayProcessor<T> parent) {
            this.actual = actual;
            this.parent = parent;
            this.buffer = parent.buffer;
        }

        @Override
        public long requested() {
            return this.requested;
        }

        @Override
        public boolean isCancelled() {
            return this.requested == Long.MIN_VALUE;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public int requestFusion(int requestedMode) {
            if ((requestedMode & 2) != 0) {
                this.fusionMode = 2;
                return 2;
            }
            return 0;
        }

        @Override
        @Nullable
        public T poll() {
            return this.buffer.poll(this);
        }

        @Override
        public void clear() {
            this.buffer.clear(this);
        }

        @Override
        public boolean isEmpty() {
            return this.buffer.isEmpty(this);
        }

        @Override
        public int size() {
            return this.buffer.size(this);
        }

        @Override
        public void request(long n) {
            if (Operators.validate(n)) {
                if (this.fusionMode() == 0) {
                    Operators.addCapCancellable(REQUESTED, this, n);
                }
                this.buffer.replay(this);
            }
        }

        @Override
        public void requestMore(int index) {
            this.index = index;
        }

        @Override
        public void cancel() {
            if (REQUESTED.getAndSet(this, Long.MIN_VALUE) != Long.MIN_VALUE) {
                this.parent.remove(this);
                if (this.enter()) {
                    this.node = null;
                }
            }
        }

        @Override
        public void node(@Nullable Object node) {
            this.node = node;
        }

        @Override
        public int fusionMode() {
            return this.fusionMode;
        }

        @Override
        @Nullable
        public Object node() {
            return this.node;
        }

        @Override
        public int index() {
            return this.index;
        }

        @Override
        public void index(int index) {
            this.index = index;
        }

        @Override
        public int tailIndex() {
            return this.tailIndex;
        }

        @Override
        public void tailIndex(int tailIndex) {
            this.tailIndex = tailIndex;
        }

        @Override
        public boolean enter() {
            return WIP.getAndIncrement(this) == 0;
        }

        @Override
        public int leave(int missed) {
            return WIP.addAndGet(this, -missed);
        }

        @Override
        public void produced(long n) {
            REQUESTED.addAndGet(this, -n);
        }
    }
}

