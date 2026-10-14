/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.ArrayDeque;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.BooleanSupplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.DrainUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class FluxTakeLast<T>
extends InternalFluxOperator<T, T> {
    final int n;

    FluxTakeLast(Flux<? extends T> source2, int n) {
        super(source2);
        if (n < 0) {
            throw new IllegalArgumentException("n >= required but it was " + n);
        }
        this.n = n;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        if (this.n == 0) {
            return new TakeLastZeroSubscriber<T>(actual);
        }
        return new TakeLastManySubscriber<T>(actual, this.n);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    @Override
    public int getPrefetch() {
        return Integer.MAX_VALUE;
    }

    static final class TakeLastManySubscriber<T>
    extends ArrayDeque<T>
    implements BooleanSupplier,
    InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final int n;
        volatile boolean cancelled;
        Subscription s;
        volatile long requested;
        static final AtomicLongFieldUpdater<TakeLastManySubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(TakeLastManySubscriber.class, "requested");

        TakeLastManySubscriber(CoreSubscriber<? super T> actual, int n) {
            this.actual = actual;
            this.n = n;
        }

        @Override
        public boolean getAsBoolean() {
            return this.cancelled;
        }

        @Override
        public void request(long n) {
            if (Operators.validate(n)) {
                DrainUtils.postCompleteRequest(n, this.actual, this, REQUESTED, this, this);
            }
        }

        @Override
        public void cancel() {
            this.cancelled = true;
            this.s.cancel();
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                this.actual.onSubscribe(this);
                s2.request(Long.MAX_VALUE);
            }
        }

        @Override
        public void onNext(T t2) {
            if (this.size() == this.n) {
                this.poll();
            }
            this.offer(t2);
        }

        @Override
        public void onError(Throwable t2) {
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            DrainUtils.postComplete(this.actual, this, REQUESTED, this, this);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.size();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }
    }

    static final class TakeLastZeroSubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        Subscription s;

        TakeLastZeroSubscriber(CoreSubscriber<? super T> actual) {
            this.actual = actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
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
                s2.request(Long.MAX_VALUE);
            }
        }

        @Override
        public void onNext(T t2) {
        }

        @Override
        public void onError(Throwable t2) {
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            this.actual.onComplete();
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public void request(long n) {
            this.s.request(n);
        }

        @Override
        public void cancel() {
            this.s.cancel();
        }
    }
}

