/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.FluxFlatMap;
import reactor.core.publisher.FluxFromMonoOperator;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoFlatMapMany<T, R>
extends FluxFromMonoOperator<T, R> {
    final Function<? super T, ? extends Publisher<? extends R>> mapper;

    MonoFlatMapMany(Mono<? extends T> source2, Function<? super T, ? extends Publisher<? extends R>> mapper2) {
        super(source2);
        this.mapper = mapper2;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        if (FluxFlatMap.trySubscribeScalarMap(this.source, actual, this.mapper, false, false)) {
            return null;
        }
        return new FlatMapManyMain<T, R>(actual, this.mapper);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class FlatMapManyInner<R>
    implements InnerConsumer<R> {
        final FlatMapManyMain<?, R> parent;
        final CoreSubscriber<? super R> actual;

        FlatMapManyInner(FlatMapManyMain<?, R> parent, CoreSubscriber<? super R> actual) {
            this.parent = parent;
            this.actual = actual;
        }

        @Override
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.parent.inner;
            }
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.parent.requested;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            this.parent.onSubscribeInner(s2);
        }

        @Override
        public void onNext(R t2) {
            this.actual.onNext(t2);
        }

        @Override
        public void onError(Throwable t2) {
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            this.actual.onComplete();
        }
    }

    static final class FlatMapManyMain<T, R>
    implements InnerOperator<T, R> {
        final CoreSubscriber<? super R> actual;
        final Function<? super T, ? extends Publisher<? extends R>> mapper;
        Subscription main;
        volatile Subscription inner;
        static final AtomicReferenceFieldUpdater<FlatMapManyMain, Subscription> INNER = AtomicReferenceFieldUpdater.newUpdater(FlatMapManyMain.class, Subscription.class, "inner");
        volatile long requested;
        static final AtomicLongFieldUpdater<FlatMapManyMain> REQUESTED = AtomicLongFieldUpdater.newUpdater(FlatMapManyMain.class, "requested");
        boolean hasValue;

        FlatMapManyMain(CoreSubscriber<? super R> actual, Function<? super T, ? extends Publisher<? extends R>> mapper2) {
            this.actual = actual;
            this.mapper = mapper2;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.main;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public Stream<? extends Scannable> inners() {
            return Stream.of(Scannable.from(this.inner));
        }

        @Override
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override
        public void request(long n) {
            Subscription a = this.inner;
            if (a != null) {
                a.request(n);
            } else if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                a = this.inner;
                if (a != null && (n = REQUESTED.getAndSet(this, 0L)) != 0L) {
                    a.request(n);
                }
            }
        }

        @Override
        public void cancel() {
            this.main.cancel();
            Operators.terminate(INNER, this);
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.main, s2)) {
                this.main = s2;
                this.actual.onSubscribe(this);
                s2.request(Long.MAX_VALUE);
            }
        }

        void onSubscribeInner(Subscription s2) {
            long r;
            if (Operators.setOnce(INNER, this, s2) && (r = REQUESTED.getAndSet(this, 0L)) != 0L) {
                s2.request(r);
            }
        }

        @Override
        public void onNext(T t2) {
            Publisher<R> p;
            this.hasValue = true;
            try {
                p = Objects.requireNonNull(this.mapper.apply(t2), "The mapper returned a null Publisher.");
            }
            catch (Throwable ex) {
                this.actual.onError(Operators.onOperatorError(this, ex, t2, this.actual.currentContext()));
                return;
            }
            if (p instanceof Callable) {
                Object v;
                try {
                    v = ((Callable)((Object)p)).call();
                }
                catch (Throwable ex) {
                    this.actual.onError(Operators.onOperatorError(this, ex, t2, this.actual.currentContext()));
                    return;
                }
                if (v == null) {
                    this.actual.onComplete();
                } else {
                    this.onSubscribeInner(Operators.scalarSubscription(this.actual, v));
                }
                return;
            }
            p.subscribe(new FlatMapManyInner<R>(this, this.actual));
        }

        @Override
        public void onError(Throwable t2) {
            if (this.hasValue) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            if (!this.hasValue) {
                this.actual.onComplete();
            }
        }
    }
}

