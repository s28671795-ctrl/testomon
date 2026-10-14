/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

class MonoFilterWhen<T>
extends InternalMonoOperator<T, T> {
    final Function<? super T, ? extends Publisher<Boolean>> asyncPredicate;

    MonoFilterWhen(Mono<T> source2, Function<? super T, ? extends Publisher<Boolean>> asyncPredicate) {
        super(source2);
        this.asyncPredicate = asyncPredicate;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new MonoFilterWhenMain<T>(actual, this.asyncPredicate);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class FilterWhenInner<T>
    implements InnerConsumer<Boolean> {
        final MonoFilterWhenMain<T> parent;
        final boolean cancelOnNext;
        final T value;
        boolean done;
        Subscription s;

        FilterWhenInner(MonoFilterWhenMain<T> parent, boolean cancelOnNext, T value) {
            this.parent = parent;
            this.cancelOnNext = cancelOnNext;
            this.value = value;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                if (this.parent.trySetInner(this)) {
                    s2.request(Long.MAX_VALUE);
                } else {
                    s2.cancel();
                }
            }
        }

        @Override
        public void onNext(Boolean t2) {
            if (this.done) {
                return;
            }
            this.done = true;
            if (this.cancelOnNext) {
                this.s.cancel();
            }
            this.parent.innerResult(t2, this);
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.parent.currentContext());
                return;
            }
            this.done = true;
            this.parent.innerError(t2, this);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.innerResult(false, this);
        }

        @Override
        public Context currentContext() {
            return this.parent.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return 0;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.done ? 0L : 1L;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        void cancel() {
            this.s.cancel();
        }
    }

    static final class MonoFilterWhenMain<T>
    implements InnerOperator<T, T>,
    Fuseable,
    Fuseable.QueueSubscription<T> {
        final Function<? super T, ? extends Publisher<Boolean>> asyncPredicate;
        final CoreSubscriber<? super T> actual;
        Subscription s;
        boolean done;
        volatile FilterWhenInner<T> asyncFilter;
        static final AtomicReferenceFieldUpdater<MonoFilterWhenMain, FilterWhenInner> ASYNC_FILTER = AtomicReferenceFieldUpdater.newUpdater(MonoFilterWhenMain.class, FilterWhenInner.class, "asyncFilter");
        static final FilterWhenInner INNER_CANCELLED = new FilterWhenInner<Object>(null, false, null);
        static final FilterWhenInner INNER_TERMINATED = new FilterWhenInner<Object>(null, false, null);

        MonoFilterWhenMain(CoreSubscriber<? super T> actual, Function<? super T, ? extends Publisher<Boolean>> asyncPredicate) {
            this.actual = actual;
            this.asyncPredicate = asyncPredicate;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public void onNext(T t2) {
            Publisher<Boolean> p;
            this.done = true;
            try {
                p = Objects.requireNonNull(this.asyncPredicate.apply(t2), "The asyncPredicate returned a null value");
            }
            catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                Operators.onDiscard(t2, this.actual.currentContext());
                this.actual.onError(ex);
                return;
            }
            if (p instanceof Callable) {
                Boolean u;
                try {
                    u = (Boolean)((Callable)((Object)p)).call();
                }
                catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    Operators.onDiscard(t2, this.actual.currentContext());
                    this.actual.onError(ex);
                    return;
                }
                if (u != null && u.booleanValue()) {
                    this.actual.onNext(t2);
                    this.actual.onComplete();
                } else {
                    Operators.onDiscard(t2, this.actual.currentContext());
                    this.actual.onComplete();
                }
            } else {
                FilterWhenInner<T> inner = new FilterWhenInner<T>(this, !(p instanceof Mono), t2);
                p.subscribe(inner);
            }
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.actual.onComplete();
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(t2);
        }

        @Override
        public void request(long n) {
            this.s.request(n);
        }

        @Override
        public void cancel() {
            this.s.cancel();
            FilterWhenInner<T> a = this.asyncFilter;
            if (a != INNER_CANCELLED && a != INNER_TERMINATED && ASYNC_FILTER.compareAndSet(this, a, INNER_CANCELLED) && a != null) {
                a.cancel();
            }
        }

        public boolean trySetInner(FilterWhenInner<T> inner) {
            FilterWhenInner<T> a = this.asyncFilter;
            if (a == null && ASYNC_FILTER.compareAndSet(this, null, inner)) {
                return true;
            }
            Operators.onDiscard(inner.value, this.currentContext());
            return false;
        }

        void innerResult(boolean item, FilterWhenInner<T> inner) {
            FilterWhenInner<T> a = this.asyncFilter;
            if (a == inner && ASYNC_FILTER.compareAndSet(this, inner, INNER_TERMINATED)) {
                if (item) {
                    this.actual.onNext(inner.value);
                    this.actual.onComplete();
                } else {
                    Operators.onDiscard(inner.value, this.currentContext());
                    this.actual.onComplete();
                }
            }
        }

        void innerError(Throwable ex, FilterWhenInner<T> inner) {
            FilterWhenInner<T> a = this.asyncFilter;
            if (a == inner && ASYNC_FILTER.compareAndSet(this, inner, INNER_TERMINATED)) {
                Operators.onDiscard(inner.value, this.currentContext());
                this.actual.onError(ex);
            }
            Operators.onErrorDropped(ex, this.currentContext());
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return 0;
            }
            if (key == Scannable.Attr.TERMINATED) {
                FilterWhenInner<T> af = this.asyncFilter;
                return this.done && (af == null || af == INNER_TERMINATED);
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.asyncFilter == INNER_CANCELLED;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            FilterWhenInner<T> c = this.asyncFilter;
            return c == null || c == INNER_CANCELLED || c == INNER_TERMINATED ? Stream.empty() : Stream.of(c);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public T poll() {
            return null;
        }

        @Override
        public int requestFusion(int requestedMode) {
            return 0;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public void clear() {
        }
    }
}

