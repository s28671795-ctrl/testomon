/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.function.Supplier;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxFlatMap;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxConcatMap<T, R>
extends InternalFluxOperator<T, R> {
    final Function<? super T, ? extends Publisher<? extends R>> mapper;
    final Supplier<? extends Queue<T>> queueSupplier;
    final int prefetch;
    final ErrorMode errorMode;

    static <T, R> CoreSubscriber<T> subscriber(CoreSubscriber<? super R> s2, Function<? super T, ? extends Publisher<? extends R>> mapper2, Supplier<? extends Queue<T>> queueSupplier, int prefetch, ErrorMode errorMode) {
        switch (errorMode) {
            case BOUNDARY: {
                return new ConcatMapDelayed<T, R>(s2, mapper2, queueSupplier, prefetch, false);
            }
            case END: {
                return new ConcatMapDelayed<T, R>(s2, mapper2, queueSupplier, prefetch, true);
            }
        }
        return new ConcatMapImmediate<T, R>(s2, mapper2, queueSupplier, prefetch);
    }

    FluxConcatMap(Flux<? extends T> source2, Function<? super T, ? extends Publisher<? extends R>> mapper2, Supplier<? extends Queue<T>> queueSupplier, int prefetch, ErrorMode errorMode) {
        super(source2);
        if (prefetch <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        this.mapper = Objects.requireNonNull(mapper2, "mapper");
        this.queueSupplier = Objects.requireNonNull(queueSupplier, "queueSupplier");
        this.prefetch = prefetch;
        this.errorMode = Objects.requireNonNull(errorMode, "errorMode");
    }

    @Override
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        if (FluxFlatMap.trySubscribeScalarMap(this.source, actual, this.mapper, false, true)) {
            return null;
        }
        return FluxConcatMap.subscriber(actual, this.mapper, this.queueSupplier, this.prefetch, this.errorMode);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class ConcatMapInner<R>
    extends Operators.MultiSubscriptionSubscriber<R, R> {
        final FluxConcatMapSupport<?, R> parent;
        long produced;

        ConcatMapInner(FluxConcatMapSupport<?, R> parent) {
            super(Operators.emptySubscriber());
            this.parent = parent;
        }

        @Override
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }

        @Override
        public void onNext(R t2) {
            ++this.produced;
            this.parent.innerNext(t2);
        }

        @Override
        public void onError(Throwable t2) {
            long p = this.produced;
            if (p != 0L) {
                this.produced = 0L;
                this.produced(p);
            }
            this.parent.innerError(t2);
        }

        @Override
        public void onComplete() {
            long p = this.produced;
            if (p != 0L) {
                this.produced = 0L;
                this.produced(p);
            }
            this.parent.innerComplete();
        }
    }

    static interface FluxConcatMapSupport<I, T>
    extends InnerOperator<I, T> {
        public void innerNext(T var1);

        public void innerComplete();

        public void innerError(Throwable var1);
    }

    static final class ConcatMapDelayed<T, R>
    implements FluxConcatMapSupport<T, R> {
        final CoreSubscriber<? super R> actual;
        final ConcatMapInner<R> inner;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final Supplier<? extends Queue<T>> queueSupplier;
        final int prefetch;
        final int limit;
        final boolean veryEnd;
        Subscription s;
        int consumed;
        volatile Queue<T> queue;
        volatile boolean done;
        volatile boolean cancelled;
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<ConcatMapDelayed, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(ConcatMapDelayed.class, Throwable.class, "error");
        volatile boolean active;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<ConcatMapDelayed> WIP = AtomicIntegerFieldUpdater.newUpdater(ConcatMapDelayed.class, "wip");
        int sourceMode;

        ConcatMapDelayed(CoreSubscriber<? super R> actual, Function<? super T, ? extends Publisher<? extends R>> mapper2, Supplier<? extends Queue<T>> queueSupplier, int prefetch, boolean veryEnd) {
            this.actual = actual;
            this.mapper = mapper2;
            this.queueSupplier = queueSupplier;
            this.prefetch = prefetch;
            this.limit = Operators.unboundedOrLimit(prefetch);
            this.veryEnd = veryEnd;
            this.inner = new ConcatMapInner(this);
        }

        @Override
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.prefetch;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue != null ? this.queue.size() : 0;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.DELAY_ERROR) {
                return true;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return FluxConcatMapSupport.super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                if (s2 instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription f = (Fuseable.QueueSubscription)s2;
                    int m4 = f.requestFusion(7);
                    if (m4 == 1) {
                        this.sourceMode = 1;
                        this.queue = f;
                        this.done = true;
                        this.actual.onSubscribe(this);
                        this.drain();
                        return;
                    }
                    if (m4 == 2) {
                        this.sourceMode = 2;
                        this.queue = f;
                    } else {
                        this.queue = this.queueSupplier.get();
                    }
                } else {
                    this.queue = this.queueSupplier.get();
                }
                this.actual.onSubscribe(this);
                s2.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        @Override
        public void onNext(T t2) {
            if (this.sourceMode == 2) {
                this.drain();
            } else if (!this.queue.offer(t2)) {
                Context ctx = this.actual.currentContext();
                this.onError(Operators.onOperatorError(this.s, Exceptions.failWithOverflow("Queue is full: Reactive Streams source doesn't respect backpressure"), t2, ctx));
                Operators.onDiscard(t2, ctx);
            } else {
                this.drain();
            }
        }

        @Override
        public void onError(Throwable t2) {
            if (Exceptions.addThrowable(ERROR, this, t2)) {
                this.done = true;
                this.drain();
            } else {
                Operators.onErrorDropped(t2, this.actual.currentContext());
            }
        }

        @Override
        public void onComplete() {
            this.done = true;
            this.drain();
        }

        @Override
        public void innerNext(R value) {
            this.actual.onNext(value);
        }

        @Override
        public void innerComplete() {
            this.active = false;
            this.drain();
        }

        @Override
        public void innerError(Throwable e) {
            if ((e = Operators.onNextInnerError(e, this.currentContext(), this.s)) != null) {
                if (Exceptions.addThrowable(ERROR, this, e)) {
                    if (!this.veryEnd) {
                        this.s.cancel();
                        this.done = true;
                    }
                    this.active = false;
                    this.drain();
                } else {
                    Operators.onErrorDropped(e, this.actual.currentContext());
                }
            } else {
                this.active = false;
            }
        }

        @Override
        public void request(long n) {
            this.inner.request(n);
        }

        @Override
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.inner.cancel();
                this.s.cancel();
                Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
            }
        }

        void drain() {
            block23: {
                if (WIP.getAndIncrement(this) != 0) break block23;
                Context ctx = null;
                while (true) {
                    if (this.cancelled) {
                        return;
                    }
                    if (!this.active) {
                        boolean empty;
                        T v;
                        Throwable ex;
                        boolean d = this.done;
                        if (d && !this.veryEnd && (ex = this.error) != null) {
                            ex = Exceptions.terminate(ERROR, this);
                            if (ex != Exceptions.TERMINATED) {
                                this.actual.onError(ex);
                            }
                            return;
                        }
                        try {
                            v = this.queue.poll();
                        }
                        catch (Throwable e) {
                            this.actual.onError(Operators.onOperatorError(this.s, e, this.actual.currentContext()));
                            return;
                        }
                        boolean bl = empty = v == null;
                        if (d && empty) {
                            Throwable ex2 = Exceptions.terminate(ERROR, this);
                            if (ex2 != null && ex2 != Exceptions.TERMINATED) {
                                this.actual.onError(ex2);
                            } else {
                                this.actual.onComplete();
                            }
                            return;
                        }
                        if (!empty) {
                            Publisher<R> p;
                            try {
                                p = Objects.requireNonNull(this.mapper.apply(v), "The mapper returned a null Publisher");
                            }
                            catch (Throwable e) {
                                if (ctx == null) {
                                    ctx = this.actual.currentContext();
                                }
                                Operators.onDiscard(v, ctx);
                                Throwable e_ = Operators.onNextError(v, e, ctx, this.s);
                                if (e_ == null) continue;
                                this.actual.onError(Operators.onOperatorError(this.s, e, v, ctx));
                                return;
                            }
                            if (this.sourceMode != 1) {
                                int c = this.consumed + 1;
                                if (c == this.limit) {
                                    this.consumed = 0;
                                    this.s.request(c);
                                } else {
                                    this.consumed = c;
                                }
                            }
                            if (p instanceof Callable) {
                                Object vr;
                                Callable supplier = (Callable)((Object)p);
                                try {
                                    vr = supplier.call();
                                }
                                catch (Throwable e) {
                                    Throwable e_;
                                    if (ctx == null) {
                                        ctx = this.actual.currentContext();
                                    }
                                    if ((e_ = Operators.onNextError(v, e, ctx)) == null || this.veryEnd && Exceptions.addThrowable(ERROR, this, e_)) continue;
                                    this.actual.onError(Operators.onOperatorError(this.s, e_, v, ctx));
                                    return;
                                }
                                if (vr == null) continue;
                                if (this.inner.isUnbounded()) {
                                    this.actual.onNext(vr);
                                    continue;
                                }
                                this.active = true;
                                this.inner.set(new WeakScalarSubscription(vr, this.inner));
                            } else {
                                this.active = true;
                                p.subscribe(this.inner);
                            }
                        }
                    }
                    if (WIP.decrementAndGet(this) == 0) break;
                }
            }
        }
    }

    static final class WeakScalarSubscription<T>
    implements Subscription {
        final CoreSubscriber<? super T> actual;
        final T value;
        boolean once;

        WeakScalarSubscription(T value, CoreSubscriber<? super T> actual) {
            this.value = value;
            this.actual = actual;
        }

        @Override
        public void request(long n) {
            if (n > 0L && !this.once) {
                this.once = true;
                CoreSubscriber<T> a = this.actual;
                a.onNext(this.value);
                a.onComplete();
            }
        }

        @Override
        public void cancel() {
            Operators.onDiscard(this.value, this.actual.currentContext());
        }
    }

    static final class ConcatMapImmediate<T, R>
    implements FluxConcatMapSupport<T, R> {
        final CoreSubscriber<? super R> actual;
        final Context ctx;
        final ConcatMapInner<R> inner;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        final Supplier<? extends Queue<T>> queueSupplier;
        final int prefetch;
        final int limit;
        Subscription s;
        int consumed;
        volatile Queue<T> queue;
        volatile boolean done;
        volatile boolean cancelled;
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<ConcatMapImmediate, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(ConcatMapImmediate.class, Throwable.class, "error");
        volatile boolean active;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<ConcatMapImmediate> WIP = AtomicIntegerFieldUpdater.newUpdater(ConcatMapImmediate.class, "wip");
        volatile int guard;
        static final AtomicIntegerFieldUpdater<ConcatMapImmediate> GUARD = AtomicIntegerFieldUpdater.newUpdater(ConcatMapImmediate.class, "guard");
        int sourceMode;

        ConcatMapImmediate(CoreSubscriber<? super R> actual, Function<? super T, ? extends Publisher<? extends R>> mapper2, Supplier<? extends Queue<T>> queueSupplier, int prefetch) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.mapper = mapper2;
            this.queueSupplier = queueSupplier;
            this.prefetch = prefetch;
            this.limit = Operators.unboundedOrLimit(prefetch);
            this.inner = new ConcatMapInner(this);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done || this.error == Exceptions.TERMINATED;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.prefetch;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue != null ? this.queue.size() : 0;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return FluxConcatMapSupport.super.scanUnsafe(key);
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                if (s2 instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription f = (Fuseable.QueueSubscription)s2;
                    int m4 = f.requestFusion(7);
                    if (m4 == 1) {
                        this.sourceMode = 1;
                        this.queue = f;
                        this.done = true;
                        this.actual.onSubscribe(this);
                        this.drain();
                        return;
                    }
                    if (m4 == 2) {
                        this.sourceMode = 2;
                        this.queue = f;
                    } else {
                        this.queue = this.queueSupplier.get();
                    }
                } else {
                    this.queue = this.queueSupplier.get();
                }
                this.actual.onSubscribe(this);
                s2.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        @Override
        public void onNext(T t2) {
            if (this.sourceMode == 2) {
                this.drain();
            } else if (!this.queue.offer(t2)) {
                this.onError(Operators.onOperatorError(this.s, Exceptions.failWithOverflow("Queue is full: Reactive Streams source doesn't respect backpressure"), t2, this.ctx));
                Operators.onDiscard(t2, this.ctx);
            } else {
                this.drain();
            }
        }

        @Override
        public void onError(Throwable t2) {
            if (Exceptions.addThrowable(ERROR, this, t2)) {
                this.inner.cancel();
                if (GUARD.getAndIncrement(this) == 0 && (t2 = Exceptions.terminate(ERROR, this)) != Exceptions.TERMINATED) {
                    this.actual.onError(t2);
                    Operators.onDiscardQueueWithClear(this.queue, this.ctx, null);
                }
            } else {
                Operators.onErrorDropped(t2, this.ctx);
            }
        }

        @Override
        public void onComplete() {
            this.done = true;
            this.drain();
        }

        @Override
        public void innerNext(R value) {
            if (this.guard == 0 && GUARD.compareAndSet(this, 0, 1)) {
                this.actual.onNext(value);
                if (GUARD.compareAndSet(this, 1, 0)) {
                    return;
                }
                Throwable e = Exceptions.terminate(ERROR, this);
                if (e != Exceptions.TERMINATED) {
                    this.actual.onError(e);
                }
            }
        }

        @Override
        public void innerComplete() {
            this.active = false;
            this.drain();
        }

        @Override
        public void innerError(Throwable e) {
            if ((e = Operators.onNextInnerError(e, this.currentContext(), this.s)) != null) {
                if (Exceptions.addThrowable(ERROR, this, e)) {
                    this.s.cancel();
                    if (GUARD.getAndIncrement(this) == 0 && (e = Exceptions.terminate(ERROR, this)) != Exceptions.TERMINATED) {
                        this.actual.onError(e);
                        Operators.onDiscardQueueWithClear(this.queue, this.ctx, null);
                    }
                } else {
                    Operators.onErrorDropped(e, this.ctx);
                }
            } else {
                this.active = false;
                this.drain();
            }
        }

        @Override
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override
        public void request(long n) {
            this.inner.request(n);
        }

        @Override
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                this.inner.cancel();
                this.s.cancel();
                Operators.onDiscardQueueWithClear(this.queue, this.ctx, null);
            }
        }

        void drain() {
            block18: {
                if (WIP.getAndIncrement(this) != 0) break block18;
                while (true) {
                    if (this.cancelled) {
                        return;
                    }
                    if (!this.active) {
                        boolean empty;
                        T v;
                        boolean d = this.done;
                        try {
                            v = this.queue.poll();
                        }
                        catch (Throwable e) {
                            this.actual.onError(Operators.onOperatorError(this.s, e, this.ctx));
                            return;
                        }
                        boolean bl = empty = v == null;
                        if (d && empty) {
                            this.actual.onComplete();
                            return;
                        }
                        if (!empty) {
                            Publisher<R> p;
                            try {
                                p = Objects.requireNonNull(this.mapper.apply(v), "The mapper returned a null Publisher");
                            }
                            catch (Throwable e) {
                                Operators.onDiscard(v, this.ctx);
                                Throwable e_ = Operators.onNextError(v, e, this.ctx, this.s);
                                if (e_ == null) continue;
                                this.actual.onError(Operators.onOperatorError(this.s, e, v, this.ctx));
                                return;
                            }
                            if (this.sourceMode != 1) {
                                int c = this.consumed + 1;
                                if (c == this.limit) {
                                    this.consumed = 0;
                                    this.s.request(c);
                                } else {
                                    this.consumed = c;
                                }
                            }
                            if (p instanceof Callable) {
                                Object vr;
                                Callable callable = (Callable)((Object)p);
                                try {
                                    vr = callable.call();
                                }
                                catch (Throwable e) {
                                    Throwable e_ = Operators.onNextError(v, e, this.ctx, this.s);
                                    if (e_ == null) continue;
                                    this.actual.onError(Operators.onOperatorError(this.s, e, v, this.ctx));
                                    Operators.onDiscardQueueWithClear(this.queue, this.ctx, null);
                                    return;
                                }
                                if (vr == null) continue;
                                if (this.inner.isUnbounded()) {
                                    if (this.guard != 0 || !GUARD.compareAndSet(this, 0, 1)) continue;
                                    this.actual.onNext(vr);
                                    if (GUARD.compareAndSet(this, 1, 0)) continue;
                                    e = Exceptions.terminate(ERROR, this);
                                    if (e != Exceptions.TERMINATED) {
                                        this.actual.onError(e);
                                    }
                                    return;
                                }
                                this.active = true;
                                this.inner.set(new WeakScalarSubscription(vr, this.inner));
                            } else {
                                this.active = true;
                                p.subscribe(this.inner);
                            }
                        }
                    }
                    if (WIP.decrementAndGet(this) == 0) break;
                }
            }
        }
    }

    static enum ErrorMode {
        IMMEDIATE,
        BOUNDARY,
        END;

    }
}

