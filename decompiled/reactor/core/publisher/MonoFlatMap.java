/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.FluxFlatMap;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoFlatMap<T, R>
extends InternalMonoOperator<T, R>
implements Fuseable {
    final Function<? super T, ? extends Mono<? extends R>> mapper;

    MonoFlatMap(Mono<? extends T> source2, Function<? super T, ? extends Mono<? extends R>> mapper2) {
        super(source2);
        this.mapper = Objects.requireNonNull(mapper2, "mapper");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        if (FluxFlatMap.trySubscribeScalarMap(this.source, actual, this.mapper, true, false)) {
            return null;
        }
        return new FlatMapMain<T, R>(actual, this.mapper);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class FlatMapInner<R>
    implements InnerConsumer<R> {
        static final FlatMapInner<?> CANCELLED = new FlatMapInner(null);
        final FlatMapMain<?, R> parent;
        Subscription s;
        boolean done;

        FlatMapInner(FlatMapMain<?, R> parent) {
            this.parent = parent;
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
            if (key == Scannable.Attr.CANCELLED) {
                return this.s == Operators.cancelledSubscription();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                if (this.parent.setSecond(this)) {
                    s2.request(Long.MAX_VALUE);
                } else {
                    s2.cancel();
                }
            }
        }

        @Override
        public void onNext(R t2) {
            if (this.done) {
                Operators.onNextDropped(t2, this.parent.currentContext());
                return;
            }
            this.done = true;
            this.parent.secondComplete(t2);
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.parent.currentContext());
                return;
            }
            this.done = true;
            this.parent.secondError(t2);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.parent.secondComplete();
        }
    }

    static final class FlatMapMain<T, R>
    implements InnerOperator<T, R>,
    Fuseable,
    Fuseable.QueueSubscription<R> {
        final Function<? super T, ? extends Mono<? extends R>> mapper;
        final CoreSubscriber<? super R> actual;
        boolean done;
        Subscription s;
        volatile FlatMapInner<R> second;
        static final AtomicReferenceFieldUpdater<FlatMapMain, FlatMapInner> SECOND = AtomicReferenceFieldUpdater.newUpdater(FlatMapMain.class, FlatMapInner.class, "second");

        FlatMapMain(CoreSubscriber<? super R> actual, Function<? super T, ? extends Mono<? extends R>> mapper2) {
            this.actual = actual;
            this.mapper = mapper2;
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(this.second);
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
            if (key == Scannable.Attr.PREFETCH) {
                return 0;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.second == FlatMapInner.CANCELLED;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
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
            Mono<R> m4;
            if (this.done) {
                Operators.onNextDropped(t2, this.actual.currentContext());
                return;
            }
            this.done = true;
            try {
                m4 = Objects.requireNonNull(this.mapper.apply(t2), "The mapper returned a null Mono");
            }
            catch (Throwable ex) {
                this.actual.onError(Operators.onOperatorError(this.s, ex, t2, this.actual.currentContext()));
                return;
            }
            if (m4 instanceof Callable) {
                Object v;
                Callable c = (Callable)((Object)m4);
                try {
                    v = c.call();
                }
                catch (Throwable ex) {
                    this.actual.onError(Operators.onOperatorError(this.s, ex, t2, this.actual.currentContext()));
                    return;
                }
                if (v == null) {
                    this.actual.onComplete();
                } else {
                    this.actual.onNext(v);
                    this.actual.onComplete();
                }
                return;
            }
            try {
                m4.subscribe(new FlatMapInner(this));
            }
            catch (Throwable e) {
                this.actual.onError(Operators.onOperatorError(this, e, t2, this.actual.currentContext()));
            }
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(t2);
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
        public void request(long n) {
            this.s.request(n);
        }

        @Override
        public void cancel() {
            this.s.cancel();
            FlatMapInner<R> second = this.second;
            if (second == FlatMapInner.CANCELLED || !SECOND.compareAndSet(this, second, FlatMapInner.CANCELLED)) {
                return;
            }
            if (second != null) {
                second.s.cancel();
            }
        }

        @Override
        public int requestFusion(int requestedMode) {
            return 0;
        }

        @Override
        public R poll() {
            return null;
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

        boolean setSecond(FlatMapInner<R> inner) {
            return this.second == null && SECOND.compareAndSet(this, null, inner);
        }

        void secondError(Throwable ex) {
            this.actual.onError(ex);
        }

        void secondComplete(R t2) {
            this.actual.onNext(t2);
            this.actual.onComplete();
        }

        void secondComplete() {
            this.actual.onComplete();
        }
    }
}

