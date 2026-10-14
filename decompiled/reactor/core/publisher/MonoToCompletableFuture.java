/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Operators;
import reactor.util.context.Context;

final class MonoToCompletableFuture<T>
extends CompletableFuture<T>
implements CoreSubscriber<T> {
    final AtomicReference<Subscription> ref = new AtomicReference();
    final boolean cancelSourceOnNext;

    MonoToCompletableFuture(boolean sourceCanEmitMoreThanOnce) {
        this.cancelSourceOnNext = sourceCanEmitMoreThanOnce;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        Subscription s2;
        boolean cancelled = super.cancel(mayInterruptIfRunning);
        if (cancelled && (s2 = (Subscription)this.ref.getAndSet(null)) != null) {
            s2.cancel();
        }
        return cancelled;
    }

    @Override
    public void onSubscribe(Subscription s2) {
        if (Operators.validate(this.ref.getAndSet(s2), s2)) {
            s2.request(Long.MAX_VALUE);
        } else {
            s2.cancel();
        }
    }

    @Override
    public void onNext(T t2) {
        Subscription s2 = this.ref.getAndSet(null);
        if (s2 != null) {
            this.complete(t2);
            if (this.cancelSourceOnNext) {
                s2.cancel();
            }
        } else {
            Operators.onNextDropped(t2, this.currentContext());
        }
    }

    @Override
    public void onError(Throwable t2) {
        if (this.ref.getAndSet(null) != null) {
            this.completeExceptionally(t2);
        }
    }

    @Override
    public void onComplete() {
        if (this.ref.getAndSet(null) != null) {
            this.complete(null);
        }
    }

    @Override
    public Context currentContext() {
        return Context.empty();
    }
}

