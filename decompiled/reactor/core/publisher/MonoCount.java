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

final class MonoCount<T>
extends MonoFromFluxOperator<T, Long>
implements Fuseable {
    MonoCount(Flux<? extends T> source2) {
        super(source2);
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Long> actual) {
        return new CountSubscriber((CoreSubscriber<? super Long>)((CoreSubscriber<Long>)actual));
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class CountSubscriber<T>
    extends Operators.BaseFluxToMonoOperator<T, Long> {
        boolean done;
        long counter;

        CountSubscriber(CoreSubscriber<? super Long> actual) {
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
            ++this.counter;
        }

        @Override
        public void onError(Throwable t2) {
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            this.completePossiblyEmpty();
        }

        @Override
        Long accumulatedValue() {
            return this.counter;
        }
    }
}

