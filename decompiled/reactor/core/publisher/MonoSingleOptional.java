/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Optional;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoSingleOptional<T>
extends InternalMonoOperator<T, Optional<T>> {
    MonoSingleOptional(Mono<? extends T> source2) {
        super(source2);
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super Optional<T>> actual) {
        return new SingleOptionalSubscriber(actual);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class SingleOptionalSubscriber<T>
    extends Operators.MonoInnerProducerBase<Optional<T>>
    implements InnerConsumer<T> {
        Subscription s;
        boolean done;

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }

        @Override
        public Context currentContext() {
            return this.actual().currentContext();
        }

        SingleOptionalSubscriber(CoreSubscriber<? super Optional<T>> actual) {
            super(actual);
        }

        @Override
        public void doOnRequest(long n) {
            this.s.request(Long.MAX_VALUE);
        }

        @Override
        public void doOnCancel() {
            this.s.cancel();
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                this.actual().onSubscribe(this);
            }
        }

        @Override
        public void onNext(T t2) {
            if (this.done) {
                Operators.onNextDropped(t2, this.actual().currentContext());
                return;
            }
            this.done = true;
            this.complete(Optional.of(t2));
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.actual().currentContext());
                return;
            }
            this.done = true;
            this.actual().onError(t2);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.complete(Optional.empty());
        }
    }
}

