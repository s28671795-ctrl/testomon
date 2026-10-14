/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.InnerConsumer;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class LambdaMonoSubscriber<T>
implements InnerConsumer<T>,
Disposable {
    final Consumer<? super T> consumer;
    final Consumer<? super Throwable> errorConsumer;
    final Runnable completeConsumer;
    final Consumer<? super Subscription> subscriptionConsumer;
    final Context initialContext;
    volatile Subscription subscription;
    static final AtomicReferenceFieldUpdater<LambdaMonoSubscriber, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(LambdaMonoSubscriber.class, Subscription.class, "subscription");

    LambdaMonoSubscriber(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> errorConsumer, @Nullable Runnable completeConsumer, @Nullable Consumer<? super Subscription> subscriptionConsumer, @Nullable Context initialContext) {
        this.consumer = consumer;
        this.errorConsumer = errorConsumer;
        this.completeConsumer = completeConsumer;
        this.subscriptionConsumer = subscriptionConsumer;
        this.initialContext = initialContext == null ? Context.empty() : initialContext;
    }

    LambdaMonoSubscriber(@Nullable Consumer<? super T> consumer, @Nullable Consumer<? super Throwable> errorConsumer, @Nullable Runnable completeConsumer, @Nullable Consumer<? super Subscription> subscriptionConsumer) {
        this(consumer, errorConsumer, completeConsumer, subscriptionConsumer, null);
    }

    @Override
    public Context currentContext() {
        return this.initialContext;
    }

    @Override
    public final void onSubscribe(Subscription s2) {
        if (Operators.validate(this.subscription, s2)) {
            this.subscription = s2;
            if (this.subscriptionConsumer != null) {
                try {
                    this.subscriptionConsumer.accept(s2);
                }
                catch (Throwable t2) {
                    Exceptions.throwIfFatal(t2);
                    s2.cancel();
                    this.onError(t2);
                }
            } else {
                s2.request(Long.MAX_VALUE);
            }
        }
    }

    @Override
    public final void onComplete() {
        Subscription s2 = S.getAndSet(this, Operators.cancelledSubscription());
        if (s2 == Operators.cancelledSubscription()) {
            return;
        }
        if (this.completeConsumer != null) {
            try {
                this.completeConsumer.run();
            }
            catch (Throwable t2) {
                Operators.onErrorDropped(t2, this.initialContext);
            }
        }
    }

    @Override
    public final void onError(Throwable t2) {
        Subscription s2 = S.getAndSet(this, Operators.cancelledSubscription());
        if (s2 == Operators.cancelledSubscription()) {
            Operators.onErrorDropped(t2, this.initialContext);
            return;
        }
        this.doError(t2);
    }

    void doError(Throwable t2) {
        if (this.errorConsumer != null) {
            this.errorConsumer.accept(t2);
        } else {
            Operators.onErrorDropped(Exceptions.errorCallbackNotImplemented(t2), this.initialContext);
        }
    }

    @Override
    public final void onNext(T x) {
        Subscription s2 = S.getAndSet(this, Operators.cancelledSubscription());
        if (s2 == Operators.cancelledSubscription()) {
            Operators.onNextDropped(x, this.initialContext);
            return;
        }
        if (this.consumer != null) {
            try {
                this.consumer.accept(x);
            }
            catch (Throwable t2) {
                Exceptions.throwIfFatal(t2);
                s2.cancel();
                this.doError(t2);
            }
        }
        if (this.completeConsumer != null) {
            try {
                this.completeConsumer.run();
            }
            catch (Throwable t3) {
                Operators.onErrorDropped(t3, this.initialContext);
            }
        }
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.subscription;
        }
        if (key == Scannable.Attr.PREFETCH) {
            return Integer.MAX_VALUE;
        }
        if (key == Scannable.Attr.TERMINATED || key == Scannable.Attr.CANCELLED) {
            return this.isDisposed();
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public boolean isDisposed() {
        return this.subscription == Operators.cancelledSubscription();
    }

    @Override
    public void dispose() {
        Subscription s2 = S.getAndSet(this, Operators.cancelledSubscription());
        if (s2 != null && s2 != Operators.cancelledSubscription()) {
            s2.cancel();
        }
    }
}

