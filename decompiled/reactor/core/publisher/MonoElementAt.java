/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.MonoFromFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class MonoElementAt<T>
extends MonoFromFluxOperator<T, T>
implements Fuseable {
    final long index;
    final T defaultValue;

    MonoElementAt(Flux<? extends T> source2, long index) {
        super(source2);
        if (index < 0L) {
            throw new IndexOutOfBoundsException("index >= required but it was " + index);
        }
        this.index = index;
        this.defaultValue = null;
    }

    MonoElementAt(Flux<? extends T> source2, long index, T defaultValue) {
        super(source2);
        if (index < 0L) {
            throw new IndexOutOfBoundsException("index >= required but it was " + index);
        }
        this.index = index;
        this.defaultValue = Objects.requireNonNull(defaultValue, "defaultValue");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new ElementAtSubscriber<T>(actual, this.index, this.defaultValue);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class ElementAtSubscriber<T>
    extends Operators.BaseFluxToMonoOperator<T, T> {
        @Nullable
        final T defaultValue;
        long index;
        final long target;
        boolean done;

        ElementAtSubscriber(CoreSubscriber<? super T> actual, long index, @Nullable T defaultValue) {
            super(actual);
            this.index = index;
            this.target = index;
            this.defaultValue = defaultValue;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            return super.scanUnsafe(key);
        }

        @Override
        public void onNext(T t2) {
            if (this.done) {
                Operators.onNextDropped(t2, this.actual.currentContext());
                return;
            }
            long i = this.index;
            if (i == 0L) {
                this.done = true;
                this.s.cancel();
                this.actual.onNext(t2);
                this.actual.onComplete();
                return;
            }
            this.index = i - 1L;
            Operators.onDiscard(t2, this.actual.currentContext());
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
            T dv = this.defaultValue;
            if (dv != null) {
                this.completePossiblyEmpty();
            } else {
                long count = this.target - this.index;
                this.actual.onError(Operators.onOperatorError(new IndexOutOfBoundsException("source had " + count + " elements, expected at least " + (this.target + 1L)), this.actual.currentContext()));
            }
        }

        @Override
        T accumulatedValue() {
            return this.defaultValue;
        }
    }
}

