/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscription;
import reactor.core.Disposable;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.InnerConsumer;
import reactor.core.scheduler.Schedulers;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class BlockingOptionalMonoSubscriber<T>
extends CountDownLatch
implements InnerConsumer<T>,
Disposable {
    T value;
    Throwable error;
    Subscription s;
    volatile boolean cancelled;

    BlockingOptionalMonoSubscriber() {
        super(1);
    }

    @Override
    public void onNext(T t2) {
        if (this.value == null) {
            this.value = t2;
            this.countDown();
        }
    }

    @Override
    public void onError(Throwable t2) {
        if (this.value == null) {
            this.error = t2;
        }
        this.countDown();
    }

    @Override
    public final void onSubscribe(Subscription s2) {
        this.s = s2;
        if (!this.cancelled) {
            s2.request(Long.MAX_VALUE);
        }
    }

    @Override
    public final void onComplete() {
        this.countDown();
    }

    @Override
    public Context currentContext() {
        return Context.empty();
    }

    @Override
    public final void dispose() {
        this.cancelled = true;
        Subscription s2 = this.s;
        if (s2 != null) {
            this.s = null;
            s2.cancel();
        }
    }

    final Optional<T> blockingGet() {
        Throwable e;
        if (Schedulers.isInNonBlockingThread()) {
            throw new IllegalStateException("blockOptional() is blocking, which is not supported in thread " + Thread.currentThread().getName());
        }
        if (this.getCount() != 0L) {
            try {
                this.await();
            }
            catch (InterruptedException ex) {
                this.dispose();
                RuntimeException re = Exceptions.propagate(ex);
                re.addSuppressed(new Exception("#blockOptional() has been interrupted"));
                throw re;
            }
        }
        if ((e = this.error) != null) {
            RuntimeException re = Exceptions.propagate(e);
            re.addSuppressed(new Exception("#block terminated with an error"));
            throw re;
        }
        return Optional.ofNullable(this.value);
    }

    final Optional<T> blockingGet(long timeout2, TimeUnit unit) {
        Throwable e;
        if (Schedulers.isInNonBlockingThread()) {
            throw new IllegalStateException("blockOptional() is blocking, which is not supported in thread " + Thread.currentThread().getName());
        }
        if (this.getCount() != 0L) {
            try {
                if (!this.await(timeout2, unit)) {
                    this.dispose();
                    throw new IllegalStateException("Timeout on blocking read for " + timeout2 + " " + (Object)((Object)unit));
                }
            }
            catch (InterruptedException ex) {
                this.dispose();
                RuntimeException re = Exceptions.propagate(ex);
                re.addSuppressed(new Exception("#blockOptional(timeout) has been interrupted"));
                throw re;
            }
        }
        if ((e = this.error) != null) {
            RuntimeException re = Exceptions.propagate(e);
            re.addSuppressed(new Exception("#block terminated with an error"));
            throw re;
        }
        return Optional.ofNullable(this.value);
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.TERMINATED) {
            return this.getCount() == 0L;
        }
        if (key == Scannable.Attr.PARENT) {
            return this.s;
        }
        if (key == Scannable.Attr.CANCELLED) {
            return this.cancelled;
        }
        if (key == Scannable.Attr.ERROR) {
            return this.error;
        }
        if (key == Scannable.Attr.PREFETCH) {
            return Integer.MAX_VALUE;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public boolean isDisposed() {
        return this.cancelled || this.getCount() == 0L;
    }

    @Override
    public String stepName() {
        return "blockOptional";
    }
}

