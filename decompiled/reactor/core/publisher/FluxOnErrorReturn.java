/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Predicate;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class FluxOnErrorReturn<T>
extends InternalFluxOperator<T, T> {
    @Nullable
    final Predicate<? super Throwable> resumableErrorPredicate;
    @Nullable
    final T fallbackValue;

    FluxOnErrorReturn(Flux<? extends T> source2, @Nullable Predicate<? super Throwable> predicate, @Nullable T value) {
        super(source2);
        this.resumableErrorPredicate = predicate;
        this.fallbackValue = value;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new ReturnSubscriber<T>(actual, (Predicate<Throwable>)this.resumableErrorPredicate, this.fallbackValue, false);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class ReturnSubscriber<T>
    implements InnerOperator<T, T> {
        private static final byte STATE_CANCELLED = -3;
        private static final byte STATE_TERMINATED = -2;
        private static final byte STATE_PENDING_FALLBACK = -1;
        final CoreSubscriber<? super T> actual;
        final boolean trackRequestWhenFallbackDeferred;
        Subscription s;
        @Nullable
        final Predicate<? super Throwable> resumableErrorPredicate;
        @Nullable
        final T fallbackValue;
        volatile long requested;
        static final AtomicLongFieldUpdater<ReturnSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(ReturnSubscriber.class, "requested");

        ReturnSubscriber(CoreSubscriber<? super T> actual, @Nullable Predicate<? super Throwable> predicate, @Nullable T value, boolean trackRequestWhenFallbackDeferred) {
            this.actual = actual;
            this.resumableErrorPredicate = predicate;
            this.fallbackValue = value;
            this.trackRequestWhenFallbackDeferred = trackRequestWhenFallbackDeferred;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        void producedOne() {
            long r;
            do {
                if ((r = REQUESTED.get(this)) != Long.MAX_VALUE && r > 0L) continue;
                return;
            } while (!REQUESTED.compareAndSet(this, r, r - 1L));
        }

        @Override
        public void cancel() {
            if (REQUESTED.getAndSet(this, -3L) != -3L) {
                this.s.cancel();
            }
        }

        @Override
        public void request(long n) {
            long u;
            long r;
            do {
                if ((r = REQUESTED.get(this)) == Long.MAX_VALUE || r < -1L) {
                    return;
                }
                if (r != -1L) continue;
                if (REQUESTED.compareAndSet(this, r, -2L)) {
                    if (this.fallbackValue != null) {
                        this.actual.onNext(this.fallbackValue);
                    }
                    this.actual.onComplete();
                    if (this.trackRequestWhenFallbackDeferred) {
                        this.s.request(n);
                        this.s.cancel();
                    }
                }
                return;
            } while (!REQUESTED.compareAndSet(this, r, u = Operators.addCap(r, n)));
            this.s.request(n);
        }

        @Override
        public void onNext(T t2) {
            this.producedOne();
            this.actual.onNext(t2);
        }

        @Override
        public void onComplete() {
            if (this.requested != -3L) {
                REQUESTED.set(this, -2L);
                this.actual.onComplete();
            }
        }

        @Override
        public void onError(Throwable t2) {
            boolean shouldResume;
            boolean bl = shouldResume = this.resumableErrorPredicate == null || this.resumableErrorPredicate.test(t2);
            if (!shouldResume) {
                if (this.requested != -3L) {
                    REQUESTED.set(this, -2L);
                    this.actual.onError(t2);
                }
                return;
            }
            if (this.fallbackValue == null) {
                if (this.requested != -3L) {
                    REQUESTED.set(this, -2L);
                    this.actual.onComplete();
                }
                return;
            }
            long r = this.requested;
            if (r > 0L) {
                REQUESTED.set(this, -2L);
                this.actual.onNext(this.fallbackValue);
                this.actual.onComplete();
                return;
            }
            if (!REQUESTED.compareAndSet(this, 0L, -1L) && (r = this.requested) > 0L) {
                REQUESTED.set(this, -2L);
                this.actual.onNext(this.fallbackValue);
                this.actual.onComplete();
            }
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            long r = this.requested;
            if (key == Scannable.Attr.CANCELLED) {
                return r == -3L;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return r == -2L;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return r > 0L ? r : 0L;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }
}

