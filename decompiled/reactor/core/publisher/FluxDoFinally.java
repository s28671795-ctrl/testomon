/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.Consumer;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SignalType;
import reactor.util.annotation.Nullable;

final class FluxDoFinally<T>
extends InternalFluxOperator<T, T> {
    final Consumer<SignalType> onFinally;

    static <T> CoreSubscriber<T> createSubscriber(CoreSubscriber<? super T> s2, Consumer<SignalType> onFinally) {
        if (s2 instanceof Fuseable.ConditionalSubscriber) {
            return new DoFinallyConditionalSubscriber((Fuseable.ConditionalSubscriber)s2, onFinally);
        }
        return new DoFinallySubscriber<T>(s2, onFinally);
    }

    FluxDoFinally(Flux<? extends T> source2, Consumer<SignalType> onFinally) {
        super(source2);
        this.onFinally = onFinally;
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return FluxDoFinally.createSubscriber(actual, this.onFinally);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class DoFinallyConditionalSubscriber<T>
    extends DoFinallySubscriber<T>
    implements Fuseable.ConditionalSubscriber<T> {
        DoFinallyConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> actual, Consumer<SignalType> onFinally) {
            super(actual, onFinally);
        }

        @Override
        public boolean tryOnNext(T t2) {
            return ((Fuseable.ConditionalSubscriber)this.actual).tryOnNext(t2);
        }
    }

    static class DoFinallySubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Consumer<SignalType> onFinally;
        volatile int once;
        static final AtomicIntegerFieldUpdater<DoFinallySubscriber> ONCE = AtomicIntegerFieldUpdater.newUpdater(DoFinallySubscriber.class, "once");
        Subscription s;

        DoFinallySubscriber(CoreSubscriber<? super T> actual, Consumer<SignalType> onFinally) {
            this.actual = actual;
            this.onFinally = onFinally;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
                return this.once == 1;
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
            }
        }

        @Override
        public void onNext(T t2) {
            this.actual.onNext(t2);
        }

        @Override
        public void onError(Throwable t2) {
            try {
                this.actual.onError(t2);
            }
            finally {
                this.runFinally(SignalType.ON_ERROR);
            }
        }

        @Override
        public void onComplete() {
            this.actual.onComplete();
            this.runFinally(SignalType.ON_COMPLETE);
        }

        @Override
        public void cancel() {
            this.s.cancel();
            this.runFinally(SignalType.CANCEL);
        }

        @Override
        public void request(long n) {
            this.s.request(n);
        }

        void runFinally(SignalType signalType) {
            if (ONCE.compareAndSet(this, 0, 1)) {
                try {
                    this.onFinally.accept(signalType);
                }
                catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    Operators.onErrorDropped(ex, this.actual.currentContext());
                }
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }
    }
}

