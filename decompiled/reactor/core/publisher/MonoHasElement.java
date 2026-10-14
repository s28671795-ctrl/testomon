/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class MonoHasElement<T>
extends InternalMonoOperator<T, Boolean>
implements Fuseable {
    MonoHasElement(Mono<? extends T> source2) {
        super(source2);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Boolean> actual) {
        return new HasElementSubscriber((CoreSubscriber<? super Boolean>)((CoreSubscriber<Boolean>)actual));
    }

    static final class HasElementSubscriber<T>
    extends Operators.BaseFluxToMonoOperator<T, Boolean> {
        boolean done;

        HasElementSubscriber(CoreSubscriber<? super Boolean> actual) {
            super(actual);
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
                Operators.onNextDropped(t2, this.currentContext());
                return;
            }
            this.done = true;
            Operators.onDiscard(t2, this.currentContext());
            this.actual.onNext(true);
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

