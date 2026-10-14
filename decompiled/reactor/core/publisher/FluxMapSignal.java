/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.DrainUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class FluxMapSignal<T, R>
extends InternalFluxOperator<T, R> {
    final Function<? super T, ? extends R> mapperNext;
    final Function<? super Throwable, ? extends R> mapperError;
    final Supplier<? extends R> mapperComplete;

    FluxMapSignal(Flux<? extends T> source2, @Nullable Function<? super T, ? extends R> mapperNext, @Nullable Function<? super Throwable, ? extends R> mapperError, @Nullable Supplier<? extends R> mapperComplete) {
        super(source2);
        if (mapperNext == null && mapperError == null && mapperComplete == null) {
            throw new IllegalArgumentException("Map Signal needs at least one valid mapper");
        }
        this.mapperNext = mapperNext;
        this.mapperError = mapperError;
        this.mapperComplete = mapperComplete;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        return new FluxMapSignalSubscriber<T, R>(actual, this.mapperNext, this.mapperError, this.mapperComplete);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class FluxMapSignalSubscriber<T, R>
    extends AbstractQueue<R>
    implements InnerOperator<T, R>,
    BooleanSupplier {
        final CoreSubscriber<? super R> actual;
        final Function<? super T, ? extends R> mapperNext;
        final Function<? super Throwable, ? extends R> mapperError;
        final Supplier<? extends R> mapperComplete;
        boolean done;
        Subscription s;
        R value;
        volatile long requested;
        static final AtomicLongFieldUpdater<FluxMapSignalSubscriber> REQUESTED = AtomicLongFieldUpdater.newUpdater(FluxMapSignalSubscriber.class, "requested");
        volatile boolean cancelled;
        long produced;

        FluxMapSignalSubscriber(CoreSubscriber<? super R> actual, @Nullable Function<? super T, ? extends R> mapperNext, @Nullable Function<? super Throwable, ? extends R> mapperError, @Nullable Supplier<? extends R> mapperComplete) {
            this.actual = actual;
            this.mapperNext = mapperNext;
            this.mapperError = mapperError;
            this.mapperComplete = mapperComplete;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                this.actual.onSubscribe(this);
                if (this.mapperNext == null) {
                    s2.request(Long.MAX_VALUE);
                }
            }
        }

        @Override
        public void onNext(T t2) {
            R v;
            if (this.done) {
                Operators.onNextDropped(t2, this.actual.currentContext());
                return;
            }
            if (this.mapperNext == null) {
                return;
            }
            try {
                v = this.mapperNext.apply(t2);
                if (v == null) {
                    throw new NullPointerException("The mapper [" + this.mapperNext.getClass().getName() + "] returned a null value.");
                }
            }
            catch (Throwable e) {
                this.done = true;
                this.actual.onError(Operators.onOperatorError(this.s, e, t2, this.actual.currentContext()));
                return;
            }
            ++this.produced;
            this.actual.onNext(v);
        }

        @Override
        public void onError(Throwable t2) {
            R v;
            if (this.done) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.done = true;
            if (this.mapperError == null) {
                this.actual.onError(t2);
                return;
            }
            try {
                v = this.mapperError.apply(t2);
                if (v == null) {
                    throw new NullPointerException("The mapper [" + this.mapperError.getClass().getName() + "] returned a null value.");
                }
            }
            catch (Throwable e) {
                this.done = true;
                this.actual.onError(Operators.onOperatorError(this.s, e, t2, this.actual.currentContext()));
                return;
            }
            this.value = v;
            long p = this.produced;
            if (p != 0L) {
                Operators.addCap(REQUESTED, this, -p);
            }
            DrainUtils.postComplete(this.actual, this, REQUESTED, this, this);
        }

        @Override
        public void onComplete() {
            R v;
            if (this.done) {
                return;
            }
            this.done = true;
            if (this.mapperComplete == null) {
                this.actual.onComplete();
                return;
            }
            try {
                v = this.mapperComplete.get();
                if (v == null) {
                    throw new NullPointerException("The mapper [" + this.mapperComplete.getClass().getName() + "] returned a null value.");
                }
            }
            catch (Throwable e) {
                this.done = true;
                this.actual.onError(Operators.onOperatorError(this.s, e, this.actual.currentContext()));
                return;
            }
            this.value = v;
            long p = this.produced;
            if (p != 0L) {
                Operators.addCap(REQUESTED, this, -p);
            }
            DrainUtils.postComplete(this.actual, this, REQUESTED, this, this);
        }

        @Override
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override
        public void request(long n) {
            if (Operators.validate(n) && !DrainUtils.postCompleteRequest(n, this.actual, this, REQUESTED, this, this)) {
                this.s.request(n);
            }
        }

        @Override
        public boolean offer(R e) {
            throw new UnsupportedOperationException();
        }

        @Override
        @Nullable
        public R poll() {
            R v = this.value;
            if (v != null) {
                this.value = null;
                return v;
            }
            return null;
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
                return this.getAsBoolean();
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
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
        @Nullable
        public R peek() {
            return this.value;
        }

        @Override
        public boolean getAsBoolean() {
            return this.cancelled;
        }

        @Override
        public void cancel() {
            this.cancelled = true;
            this.s.cancel();
        }

        @Override
        public Iterator<R> iterator() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return this.value == null ? 0 : 1;
        }
    }
}

