/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.MonoFromFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class MonoHasElements<T>
extends MonoFromFluxOperator<T, Boolean>
implements Fuseable {
    MonoHasElements(Flux<? extends T> source2) {
        super(source2);
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Boolean> actual) {
        return new HasElementsSubscriber((CoreSubscriber<? super Boolean>)((CoreSubscriber<Boolean>)actual));
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class HasElementsSubscriber<T>
    extends Operators.BaseFluxToMonoOperator<T, Boolean> {
        boolean done;

        HasElementsSubscriber(CoreSubscriber<? super Boolean> actual) {
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
            Operators.onDiscard(t2, this.currentContext());
            if (!this.done) {
                this.s.cancel();
                this.done = true;
                this.actual.onNext(true);
                this.actual.onComplete();
            }
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.currentContext());
                return;
            }
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

