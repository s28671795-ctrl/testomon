/*
 * Decompiled with CFR 0.152.
 */
package io.r2dbc.spi;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

class DefaultLob<T> {
    private static final AtomicIntegerFieldUpdater<DefaultLob<?>> ATOMIC_DISCARD = AtomicIntegerFieldUpdater.newUpdater(DefaultLob.class, "discarded");
    private static final AtomicIntegerFieldUpdater<DefaultLob<?>> ATOMIC_CONSUMED = AtomicIntegerFieldUpdater.newUpdater(DefaultLob.class, "consumed");
    private static final int NOT_DISCARDED = 0;
    private static final int DISCARDED = 1;
    private static final int NOT_CONSUMED = 0;
    private static final int CONSUMED = 1;
    private final Publisher<T> p;
    private volatile int discarded = 0;
    private volatile int consumed = 0;

    DefaultLob(Publisher<T> p) {
        this.p = p;
    }

    Publisher<T> stream() {
        return subscriber -> {
            if (ATOMIC_DISCARD.get(this) == 1) {
                subscriber.onError(new IllegalStateException("Source stream was already released"));
                return;
            }
            if (!ATOMIC_CONSUMED.compareAndSet(this, 0, 1)) {
                subscriber.onError(new IllegalStateException("Source stream was already consumed"));
                return;
            }
            this.p.subscribe(subscriber);
        };
    }

    Publisher<Void> discard() {
        return outer -> {
            block3: {
                final AtomicBoolean completed2 = new AtomicBoolean();
                if (!ATOMIC_DISCARD.compareAndSet(this, 0, 1)) {
                    outer.onError(new IllegalStateException("Source stream was already released"));
                    return;
                }
                try {
                    this.p.subscribe(new Subscriber<T>(){

                        @Override
                        public void onSubscribe(Subscription s2) {
                            s2.cancel();
                            if (completed2.compareAndSet(false, true)) {
                                outer.onComplete();
                            }
                        }

                        @Override
                        public void onNext(T t2) {
                        }

                        @Override
                        public void onError(Throwable t2) {
                            if (completed2.compareAndSet(false, true)) {
                                outer.onError(new IllegalStateException("Resource release has failed", t2));
                            }
                        }

                        @Override
                        public void onComplete() {
                            if (completed2.compareAndSet(false, true)) {
                                outer.onComplete();
                            }
                        }
                    });
                }
                catch (Exception e) {
                    if (!completed2.compareAndSet(false, true)) break block3;
                    outer.onError(new IllegalStateException("Resource release has failed", e));
                }
            }
        };
    }
}

