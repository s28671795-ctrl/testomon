/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.function.BiFunction;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoCompletionStage<T>
extends Mono<T>
implements Fuseable,
Scannable {
    final CompletionStage<? extends T> future;
    final boolean suppressCancellation;

    MonoCompletionStage(CompletionStage<? extends T> future, boolean suppressCancellation) {
        this.future = Objects.requireNonNull(future, "future");
        this.suppressCancellation = suppressCancellation;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        actual.onSubscribe(new MonoCompletionStageSubscription<T>(actual, this.future, this.suppressCancellation));
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.ASYNC;
        }
        return null;
    }

    static class MonoCompletionStageSubscription<T>
    implements InnerProducer<T>,
    Fuseable,
    Fuseable.QueueSubscription<T>,
    BiFunction<T, Throwable, Void> {
        final CoreSubscriber<? super T> actual;
        final CompletionStage<? extends T> future;
        final boolean suppressCancellation;
        volatile int requestedOnce;
        static final AtomicIntegerFieldUpdater<MonoCompletionStageSubscription> REQUESTED_ONCE = AtomicIntegerFieldUpdater.newUpdater(MonoCompletionStageSubscription.class, "requestedOnce");
        volatile boolean cancelled;

        MonoCompletionStageSubscription(CoreSubscriber<? super T> actual, CompletionStage<? extends T> future, boolean suppressCancellation) {
            this.actual = actual;
            this.future = future;
            this.suppressCancellation = suppressCancellation;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public Void apply(@Nullable T value, @Nullable Throwable e) {
            CoreSubscriber<T> actual = this.actual;
            if (this.cancelled) {
                Context ctx = actual.currentContext();
                if (e == null || e instanceof CancellationException) {
                    Operators.onDiscard(value, ctx);
                } else {
                    Operators.onErrorDropped(e, ctx);
                    Operators.onDiscard(value, ctx);
                }
                return null;
            }
            try {
                if (e instanceof CompletionException) {
                    actual.onError(e.getCause());
                } else if (e != null) {
                    actual.onError(e);
                } else if (value != null) {
                    actual.onNext(value);
                    actual.onComplete();
                } else {
                    actual.onComplete();
                }
            }
            catch (Throwable e1) {
                Operators.onErrorDropped(e1, actual.currentContext());
                throw Exceptions.bubble(e1);
            }
            return null;
        }

        @Override
        public void request(long n) {
            if (this.cancelled) {
                return;
            }
            if (this.requestedOnce == 1 || !REQUESTED_ONCE.compareAndSet(this, 0, 1)) {
                return;
            }
            this.future.handle(this);
        }

        @Override
        public void cancel() {
            this.cancelled = true;
            CompletionStage<? extends T> future = this.future;
            if (!this.suppressCancellation && future instanceof Future) {
                ((Future)((Object)future)).cancel(true);
            }
        }

        @Override
        public int requestFusion(int requestedMode) {
            return 0;
        }

        @Override
        public T poll() {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public void clear() {
        }
    }
}

