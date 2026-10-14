/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Spliterator;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxIterable;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxFlattenIterable<T, R>
extends InternalFluxOperator<T, R>
implements Fuseable {
    final Function<? super T, ? extends Iterable<? extends R>> mapper;
    final int prefetch;
    final Supplier<Queue<T>> queueSupplier;

    FluxFlattenIterable(Flux<? extends T> source2, Function<? super T, ? extends Iterable<? extends R>> mapper2, int prefetch, Supplier<Queue<T>> queueSupplier) {
        super(source2);
        if (prefetch <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        this.mapper = Objects.requireNonNull(mapper2, "mapper");
        this.prefetch = prefetch;
        this.queueSupplier = Objects.requireNonNull(queueSupplier, "queueSupplier");
    }

    @Override
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) throws Exception {
        if (this.source instanceof Callable) {
            boolean knownToBeFinite;
            Spliterator<? extends R> sp2;
            Object v = ((Callable)((Object)this.source)).call();
            if (v == null) {
                Operators.complete(actual);
                return null;
            }
            try {
                Iterable<R> iter = this.mapper.apply(v);
                sp2 = iter.spliterator();
                knownToBeFinite = FluxIterable.checkFinite(sp2);
            }
            catch (Throwable ex) {
                Context ctx = actual.currentContext();
                Throwable e_ = Operators.onNextError(v, ex, ctx);
                Operators.onDiscard(v, ctx);
                if (e_ != null) {
                    Operators.error(actual, e_);
                } else {
                    Operators.complete(actual);
                }
                return null;
            }
            FluxIterable.subscribe(actual, sp2, knownToBeFinite);
            return null;
        }
        return new FlattenIterableSubscriber<T, R>(actual, this.mapper, this.prefetch, this.queueSupplier);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class FlattenIterableSubscriber<T, R>
    implements InnerOperator<T, R>,
    Fuseable.QueueSubscription<R>,
    Consumer<R> {
        final CoreSubscriber<? super R> actual;
        final Function<? super T, ? extends Iterable<? extends R>> mapper;
        final int prefetch;
        final int limit;
        final Supplier<Queue<T>> queueSupplier;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<FlattenIterableSubscriber> WIP = AtomicIntegerFieldUpdater.newUpdater(FlattenIterableSubscriber.class, "wip");
        volatile long requested;
        static final AtomicLongFieldUpdater<FlattenIterableSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(FlattenIterableSubscriber.class, "requested");
        Subscription s;
        Queue<T> queue;
        volatile boolean done;
        volatile boolean cancelled;
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<FlattenIterableSubscriber, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(FlattenIterableSubscriber.class, Throwable.class, "error");
        @Nullable
        Spliterator<? extends R> current;
        boolean currentKnownToBeFinite;
        boolean valueReady = false;
        R nextElement;
        int consumed;
        int fusionMode;

        FlattenIterableSubscriber(CoreSubscriber<? super R> actual, Function<? super T, ? extends Iterable<? extends R>> mapper2, int prefetch, Supplier<Queue<T>> queueSupplier) {
            this.actual = actual;
            this.mapper = mapper2;
            this.prefetch = prefetch;
            this.queueSupplier = queueSupplier;
            this.limit = Operators.unboundedOrLimit(prefetch);
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
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
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
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                if (s2 instanceof Fuseable.QueueSubscription) {
                    Fuseable.QueueSubscription qs = (Fuseable.QueueSubscription)s2;
                    int m4 = qs.requestFusion(3);
                    if (m4 == 1) {
                        this.fusionMode = m4;
                        this.queue = qs;
                        this.done = true;
                        this.actual.onSubscribe(this);
                        return;
                    }
                    if (m4 == 2) {
                        this.fusionMode = m4;
                        this.queue = qs;
                        this.actual.onSubscribe(this);
                        s2.request(Operators.unboundedOrPrefetch(this.prefetch));
                        return;
                    }
                }
                this.queue = this.queueSupplier.get();
                this.actual.onSubscribe(this);
                s2.request(Operators.unboundedOrPrefetch(this.prefetch));
            }
        }

        @Override
        public void onNext(T t2) {
            if (this.fusionMode != 2 && !this.queue.offer(t2)) {
                Context ctx = this.actual.currentContext();
                this.onError(Operators.onOperatorError(this.s, Exceptions.failWithOverflow("Queue is full: Reactive Streams source doesn't respect backpressure"), ctx));
                Operators.onDiscard(t2, ctx);
                return;
            }
            this.drain(t2);
        }

        @Override
        public void onError(Throwable t2) {
            if (Exceptions.addThrowable(ERROR, this, t2)) {
                this.done = true;
                this.drain(null);
            } else {
                Operators.onErrorDropped(t2, this.actual.currentContext());
            }
        }

        @Override
        public void onComplete() {
            this.done = true;
            this.drain(null);
        }

        @Override
        public void accept(R t2) {
            this.valueReady = true;
            this.nextElement = t2;
        }

        boolean hasNext(Spliterator<? extends R> spliterator) {
            if (!this.valueReady) {
                spliterator.tryAdvance(this);
            }
            return this.valueReady;
        }

        R next(Spliterator<? extends R> spliterator) {
            if (!this.valueReady && !this.hasNext(spliterator)) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            R t2 = this.nextElement;
            this.nextElement = null;
            return t2;
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
            if (!this.cancelled) {
                this.cancelled = true;
                this.s.cancel();
                if (WIP.getAndIncrement(this) == 0) {
                    Context context = this.actual.currentContext();
                    Operators.onDiscardQueueWithClear(this.queue, context, null);
                    Operators.onDiscard(this.nextElement, context);
                    Operators.onDiscardMultiple(this.current, this.currentKnownToBeFinite, context);
                }
            }
        }

        final void resetCurrent() {
            this.current = null;
            this.currentKnownToBeFinite = false;
        }

        void drainAsync() {
            CoreSubscriber<R> a = this.actual;
            Queue<T> q = this.queue;
            int missed = 1;
            Spliterator<Object> sp2 = this.current;
            boolean itFinite = this.currentKnownToBeFinite;
            while (true) {
                if (sp2 == null) {
                    boolean empty;
                    T t2;
                    if (this.cancelled) {
                        Operators.onDiscardQueueWithClear(q, this.actual.currentContext(), null);
                        return;
                    }
                    Throwable ex = this.error;
                    if (ex != null) {
                        ex = Exceptions.terminate(ERROR, this);
                        this.resetCurrent();
                        Operators.onDiscardQueueWithClear(q, this.actual.currentContext(), null);
                        a.onError(ex);
                        return;
                    }
                    boolean d = this.done;
                    try {
                        t2 = q.poll();
                    }
                    catch (Throwable pollEx) {
                        this.resetCurrent();
                        Operators.onDiscardQueueWithClear(q, this.actual.currentContext(), null);
                        a.onError(pollEx);
                        return;
                    }
                    boolean bl = empty = t2 == null;
                    if (d && empty) {
                        a.onComplete();
                        return;
                    }
                    if (!empty) {
                        boolean isEmpty;
                        try {
                            Iterable<R> iterable = this.mapper.apply(t2);
                            sp2 = iterable.spliterator();
                            itFinite = FluxIterable.checkFinite(sp2);
                            isEmpty = itFinite ? sp2.estimateSize() == 0L : !this.hasNext(sp2);
                        }
                        catch (Throwable exc) {
                            sp2 = null;
                            itFinite = false;
                            Context ctx = this.actual.currentContext();
                            Throwable e_ = Operators.onNextError(t2, exc, ctx, this.s);
                            Operators.onDiscard(t2, ctx);
                            if (e_ == null) continue;
                            this.onError(e_);
                            continue;
                        }
                        if (isEmpty) {
                            sp2 = null;
                            itFinite = false;
                            int c = this.consumed + 1;
                            if (c == this.limit) {
                                this.consumed = 0;
                                this.s.request(c);
                                continue;
                            }
                            this.consumed = c;
                            continue;
                        }
                    }
                }
                if (sp2 != null) {
                    Throwable ex;
                    Context context;
                    long r = this.requested;
                    long e = 0L;
                    while (e != r) {
                        boolean b;
                        R v;
                        if (this.cancelled) {
                            this.resetCurrent();
                            context = this.actual.currentContext();
                            Operators.onDiscardQueueWithClear(q, context, null);
                            Operators.onDiscard(this.nextElement, context);
                            Operators.onDiscardMultiple(sp2, itFinite, context);
                            return;
                        }
                        ex = this.error;
                        if (ex != null) {
                            ex = Exceptions.terminate(ERROR, this);
                            this.resetCurrent();
                            Context context2 = this.actual.currentContext();
                            Operators.onDiscardQueueWithClear(q, context2, null);
                            Operators.onDiscard(this.nextElement, context2);
                            Operators.onDiscardMultiple(sp2, itFinite, context2);
                            a.onError(ex);
                            return;
                        }
                        try {
                            v = Objects.requireNonNull(this.next(sp2), "iterator returned null");
                        }
                        catch (Throwable exc) {
                            this.onError(Operators.onOperatorError(this.s, exc, this.actual.currentContext()));
                            continue;
                        }
                        a.onNext(v);
                        if (this.cancelled) {
                            this.resetCurrent();
                            Context context3 = this.actual.currentContext();
                            Operators.onDiscardQueueWithClear(q, context3, null);
                            Operators.onDiscard(this.nextElement, context3);
                            Operators.onDiscardMultiple(sp2, itFinite, context3);
                            return;
                        }
                        ++e;
                        try {
                            b = this.hasNext(sp2);
                        }
                        catch (Throwable exc) {
                            this.onError(Operators.onOperatorError(this.s, exc, this.actual.currentContext()));
                            continue;
                        }
                        if (b) continue;
                        int c = this.consumed + 1;
                        if (c == this.limit) {
                            this.consumed = 0;
                            this.s.request(c);
                        } else {
                            this.consumed = c;
                        }
                        sp2 = null;
                        itFinite = false;
                        this.resetCurrent();
                        break;
                    }
                    if (e == r) {
                        boolean empty;
                        if (this.cancelled) {
                            this.resetCurrent();
                            context = this.actual.currentContext();
                            Operators.onDiscardQueueWithClear(q, context, null);
                            Operators.onDiscard(this.nextElement, context);
                            Operators.onDiscardMultiple(sp2, itFinite, context);
                            return;
                        }
                        ex = this.error;
                        if (ex != null) {
                            ex = Exceptions.terminate(ERROR, this);
                            this.resetCurrent();
                            Context context4 = this.actual.currentContext();
                            Operators.onDiscardQueueWithClear(q, context4, null);
                            Operators.onDiscard(this.nextElement, context4);
                            Operators.onDiscardMultiple(sp2, itFinite, context4);
                            a.onError(ex);
                            return;
                        }
                        boolean d = this.done;
                        boolean bl = empty = q.isEmpty() && sp2 == null;
                        if (d && empty) {
                            this.resetCurrent();
                            a.onComplete();
                            return;
                        }
                    }
                    if (e != 0L && r != Long.MAX_VALUE) {
                        REQUESTED.addAndGet(this, -e);
                    }
                    if (sp2 == null) continue;
                }
                this.current = sp2;
                this.currentKnownToBeFinite = itFinite;
                if ((missed = WIP.addAndGet(this, -missed)) == 0) break;
            }
        }

        void drainSync() {
            CoreSubscriber<R> a = this.actual;
            int missed = 1;
            Spliterator<Object> sp2 = this.current;
            boolean itFinite = this.currentKnownToBeFinite;
            while (true) {
                if (sp2 == null) {
                    boolean empty;
                    T t2;
                    if (this.cancelled) {
                        Operators.onDiscardQueueWithClear(this.queue, this.actual.currentContext(), null);
                        return;
                    }
                    boolean d = this.done;
                    Queue<T> q = this.queue;
                    try {
                        t2 = q.poll();
                    }
                    catch (Throwable pollEx) {
                        this.resetCurrent();
                        Operators.onDiscardQueueWithClear(q, this.actual.currentContext(), null);
                        a.onError(pollEx);
                        return;
                    }
                    boolean bl = empty = t2 == null;
                    if (d && empty) {
                        a.onComplete();
                        return;
                    }
                    if (!empty) {
                        boolean isEmpty;
                        try {
                            Iterable<R> iterable = this.mapper.apply(t2);
                            sp2 = iterable.spliterator();
                            itFinite = FluxIterable.checkFinite(sp2);
                            isEmpty = itFinite ? sp2.estimateSize() == 0L : !this.hasNext(sp2);
                        }
                        catch (Throwable exc) {
                            this.resetCurrent();
                            Context ctx = this.actual.currentContext();
                            Throwable e_ = Operators.onNextError(t2, exc, ctx, this.s);
                            Operators.onDiscard(t2, ctx);
                            if (e_ == null) continue;
                            a.onError(e_);
                            return;
                        }
                        if (isEmpty) {
                            sp2 = null;
                            itFinite = false;
                            continue;
                        }
                    }
                }
                if (sp2 != null) {
                    Context context;
                    long e;
                    long r = this.requested;
                    for (e = 0L; e != r; ++e) {
                        R v;
                        if (this.cancelled) {
                            this.resetCurrent();
                            context = this.actual.currentContext();
                            Operators.onDiscardQueueWithClear(this.queue, context, null);
                            Operators.onDiscard(this.nextElement, context);
                            Operators.onDiscardMultiple(sp2, itFinite, context);
                            return;
                        }
                        try {
                            v = Objects.requireNonNull(this.next(sp2), "iterator returned null");
                        }
                        catch (Throwable exc) {
                            this.resetCurrent();
                            a.onError(Operators.onOperatorError(this.s, exc, this.actual.currentContext()));
                            return;
                        }
                        a.onNext(v);
                        if (this.cancelled) {
                            this.resetCurrent();
                            Context context2 = this.actual.currentContext();
                            Operators.onDiscardQueueWithClear(this.queue, context2, null);
                            Operators.onDiscard(this.nextElement, context2);
                            Operators.onDiscardMultiple(sp2, itFinite, context2);
                            return;
                        }
                        try {
                            boolean b = this.hasNext(sp2);
                            continue;
                        }
                        catch (Throwable exc) {
                            this.resetCurrent();
                            a.onError(Operators.onOperatorError(this.s, exc, this.actual.currentContext()));
                            return;
                        }
                    }
                    if (e == r) {
                        boolean empty;
                        if (this.cancelled) {
                            this.resetCurrent();
                            context = this.actual.currentContext();
                            Operators.onDiscardQueueWithClear(this.queue, context, null);
                            Operators.onDiscard(this.nextElement, context);
                            Operators.onDiscardMultiple(sp2, itFinite, context);
                            return;
                        }
                        boolean d = this.done;
                        boolean bl = empty = this.queue.isEmpty() && sp2 == null;
                        if (d && empty) {
                            this.resetCurrent();
                            a.onComplete();
                            return;
                        }
                    }
                    if (e != 0L && r != Long.MAX_VALUE) {
                        REQUESTED.addAndGet(this, -e);
                    }
                    if (sp2 == null) continue;
                }
                this.current = sp2;
                this.currentKnownToBeFinite = itFinite;
                if ((missed = WIP.addAndGet(this, -missed)) == 0) break;
            }
        }

        void drain(@Nullable T dataSignal) {
            if (WIP.getAndIncrement(this) != 0) {
                if (dataSignal != null && this.cancelled) {
                    Operators.onDiscard(dataSignal, this.actual.currentContext());
                }
                return;
            }
            if (this.fusionMode == 1) {
                this.drainSync();
            } else {
                this.drainAsync();
            }
        }

        @Override
        public void clear() {
            Context context = this.actual.currentContext();
            Operators.onDiscard(this.nextElement, context);
            Operators.onDiscardMultiple(this.current, this.currentKnownToBeFinite, context);
            this.resetCurrent();
            Operators.onDiscardQueueWithClear(this.queue, context, null);
        }

        @Override
        public boolean isEmpty() {
            Spliterator<? extends R> sp2 = this.current;
            if (sp2 != null) {
                return !this.hasNext(sp2);
            }
            return this.queue.isEmpty();
        }

        @Override
        @Nullable
        public R poll() {
            Spliterator<R> sp2 = this.current;
            while (true) {
                if (sp2 == null) {
                    boolean itFinite;
                    T v = this.queue.poll();
                    if (v == null) {
                        return null;
                    }
                    try {
                        Iterable<R> iterable = this.mapper.apply(v);
                        sp2 = iterable.spliterator();
                        itFinite = FluxIterable.checkFinite(sp2);
                    }
                    catch (Throwable error) {
                        Operators.onDiscard(v, this.actual.currentContext());
                        throw error;
                    }
                    if (!this.hasNext(sp2)) continue;
                    this.current = sp2;
                    this.currentKnownToBeFinite = itFinite;
                    break;
                }
                if (this.hasNext(sp2)) break;
                sp2 = null;
            }
            R r = Objects.requireNonNull(this.next(sp2), "iterator returned null");
            if (!this.hasNext(sp2)) {
                this.resetCurrent();
            }
            return r;
        }

        @Override
        public int requestFusion(int requestedMode) {
            if ((requestedMode & 1) != 0 && this.fusionMode == 1) {
                return 1;
            }
            return 0;
        }

        @Override
        public int size() {
            return this.queue.size();
        }
    }
}

