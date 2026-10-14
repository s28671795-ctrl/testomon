/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiFunction;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class FluxScan<T>
extends InternalFluxOperator<T, T> {
    final BiFunction<T, ? super T, T> accumulator;

    FluxScan(Flux<? extends T> source2, BiFunction<T, ? super T, T> accumulator) {
        super(source2);
        this.accumulator = Objects.requireNonNull(accumulator, "accumulator");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new ScanSubscriber<T>(actual, this.accumulator);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class ScanSubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final BiFunction<T, ? super T, T> accumulator;
        Subscription s;
        T value;
        boolean done;

        ScanSubscriber(CoreSubscriber<? super T> actual, BiFunction<T, ? super T, T> accumulator) {
            this.actual = actual;
            this.accumulator = accumulator;
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
            if (this.done) {
                Operators.onNextDropped(t2, this.actual.currentContext());
                return;
            }
            T v = this.value;
            if (v != null) {
                try {
                    t2 = Objects.requireNonNull(this.accumulator.apply(v, t2), "The accumulator returned a null value");
                }
                catch (Throwable e) {
                    this.onError(Operators.onOperatorError(this.s, e, t2, this.actual.currentContext()));
                    return;
                }
            }
            this.value = t2;
            this.actual.onNext(t2);
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.value = null;
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.value = null;
            this.actual.onComplete();
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
            if (key == Scannable.Attr.BUFFERED) {
                return this.value != null ? 1 : 0;
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

