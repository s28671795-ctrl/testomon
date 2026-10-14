/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.stream.Stream;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.InternalEmptySink;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

class SinkEmptyMulticast<T>
extends Mono<T>
implements InternalEmptySink<T> {
    volatile Inner<T>[] subscribers;
    static final AtomicReferenceFieldUpdater<SinkEmptyMulticast, Inner[]> SUBSCRIBERS = AtomicReferenceFieldUpdater.newUpdater(SinkEmptyMulticast.class, Inner[].class, "subscribers");
    static final Inner[] EMPTY = new Inner[0];
    static final Inner[] TERMINATED_EMPTY = new Inner[0];
    static final Inner[] TERMINATED_ERROR = new Inner[0];
    static final int STATE_ADDED = 0;
    static final int STATE_ERROR = -1;
    static final int STATE_EMPTY = -2;
    @Nullable
    Throwable error;

    SinkEmptyMulticast() {
        SUBSCRIBERS.lazySet(this, EMPTY);
    }

    @Override
    public int currentSubscriberCount() {
        return this.subscribers.length;
    }

    @Override
    public Mono<T> asMono() {
        return this;
    }

    boolean isTerminated(Inner<?>[] array) {
        return array == TERMINATED_EMPTY || array == TERMINATED_ERROR;
    }

    @Override
    public Sinks.EmitResult tryEmitEmpty() {
        Inner<T>[] array;
        do {
            if (!this.isTerminated(array = this.subscribers)) continue;
            return Sinks.EmitResult.FAIL_TERMINATED;
        } while (!SUBSCRIBERS.compareAndSet(this, array, TERMINATED_EMPTY));
        for (Inner<T> as : array) {
            as.complete();
        }
        return Sinks.EmitResult.OK;
    }

    @Override
    public Sinks.EmitResult tryEmitError(Throwable cause) {
        Objects.requireNonNull(cause, "onError cannot be null");
        Inner<T>[] prevSubscribers = this.subscribers;
        if (this.isTerminated(prevSubscribers)) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        this.error = cause;
        while (!SUBSCRIBERS.compareAndSet(this, prevSubscribers, TERMINATED_ERROR)) {
            prevSubscribers = this.subscribers;
            if (!this.isTerminated(prevSubscribers)) continue;
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        for (Inner<T> as : prevSubscribers) {
            as.error(cause);
        }
        return Sinks.EmitResult.OK;
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.TERMINATED) {
            return this.isTerminated(this.subscribers);
        }
        if (key == Scannable.Attr.ERROR) {
            return this.subscribers == TERMINATED_ERROR ? this.error : null;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public Context currentContext() {
        return Operators.multiSubscribersContext(this.subscribers);
    }

    int add(Inner<T> ps) {
        Inner[] b;
        Inner<T>[] a;
        do {
            if ((a = this.subscribers) == TERMINATED_EMPTY) {
                return -2;
            }
            if (a == TERMINATED_ERROR) {
                return -1;
            }
            int n = a.length;
            b = new Inner[n + 1];
            System.arraycopy(a, 0, b, 0, n);
            b[n] = ps;
        } while (!SUBSCRIBERS.compareAndSet(this, a, b));
        return 0;
    }

    void remove(Inner<T> ps) {
        Inner[] b;
        Inner<T>[] a;
        do {
            int n;
            if ((n = (a = this.subscribers).length) == 0) {
                return;
            }
            int j = -1;
            for (int i = 0; i < n; ++i) {
                if (a[i] != ps) continue;
                j = i;
                break;
            }
            if (j < 0) {
                return;
            }
            if (n == 1) {
                b = EMPTY;
                continue;
            }
            b = new Inner[n - 1];
            System.arraycopy(a, 0, b, 0, j);
            System.arraycopy(a, j + 1, b, j, n - j - 1);
        } while (!SUBSCRIBERS.compareAndSet(this, a, b));
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        VoidInner<T> as = new VoidInner<T>(actual, this);
        actual.onSubscribe(as);
        int addedState = this.add(as);
        if (addedState == 0) {
            if (as.isCancelled()) {
                this.remove(as);
            }
        } else if (addedState == -1) {
            Throwable ex = this.error;
            actual.onError(ex);
        } else {
            as.complete();
        }
    }

    @Override
    public Stream<? extends Scannable> inners() {
        return Stream.of(this.subscribers);
    }

    static final class VoidInner<T>
    extends AtomicBoolean
    implements Inner<T> {
        final SinkEmptyMulticast<T> parent;
        final CoreSubscriber<? super T> actual;

        VoidInner(CoreSubscriber<? super T> actual, SinkEmptyMulticast<T> parent) {
            this.actual = actual;
            this.parent = parent;
        }

        @Override
        public void cancel() {
            if (this.getAndSet(true)) {
                return;
            }
            this.parent.remove(this);
        }

        @Override
        public boolean isCancelled() {
            return this.get();
        }

        @Override
        public void request(long l) {
            Operators.validate(l);
        }

        @Override
        public void complete(T value) {
        }

        @Override
        public void complete() {
            if (this.get()) {
                return;
            }
            this.actual.onComplete();
        }

        @Override
        public void error(Throwable t2) {
            if (this.get()) {
                Operators.onOperatorError(t2, this.actual.currentContext());
                return;
            }
            this.actual.onError(t2);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.parent;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.get();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return Inner.super.scanUnsafe(key);
        }
    }

    static interface Inner<T>
    extends InnerProducer<T> {
        public void error(Throwable var1);

        public void complete(T var1);

        public void complete();

        public boolean isCancelled();
    }
}

