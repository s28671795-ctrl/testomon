/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Predicate;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.MonoFromFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class MonoAny<T>
extends MonoFromFluxOperator<T, Boolean>
implements Fuseable {
    final Predicate<? super T> predicate;

    MonoAny(Flux<? extends T> source2, Predicate<? super T> predicate) {
        super(source2);
        this.predicate = Objects.requireNonNull(predicate, "predicate");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Boolean> actual) {
        return new AnySubscriber<T>(actual, this.predicate);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class AnySubscriber<T>
    extends Operators.BaseFluxToMonoOperator<T, Boolean> {
        final Predicate<? super T> predicate;
        boolean done;

        AnySubscriber(CoreSubscriber<? super Boolean> actual, Predicate<? super T> predicate) {
            super(actual);
            this.predicate = predicate;
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
            boolean b;
            if (this.done) {
                Operators.onDiscard(t2, this.actual.currentContext());
                return;
            }
            try {
                b = this.predicate.test(t2);
            }
            catch (Throwable e) {
                this.done = true;
                this.actual.onError(Operators.onOperatorError(this.s, e, t2, this.actual.currentContext()));
                return;
            }
            if (b) {
                this.done = true;
                this.s.cancel();
                this.actual.onNext(true);
                this.actual.onComplete();
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
            this.completePossiblyEmpty();
        }

        @Override
        Boolean accumulatedValue() {
            return false;
        }
    }
}

