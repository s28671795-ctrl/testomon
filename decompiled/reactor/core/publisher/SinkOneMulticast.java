/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.time.Duration;
import reactor.core.CoreSubscriber;
import reactor.core.Scannable;
import reactor.core.publisher.InternalOneSink;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SinkEmptyMulticast;
import reactor.core.publisher.Sinks;
import reactor.util.annotation.Nullable;

final class SinkOneMulticast<O>
extends SinkEmptyMulticast<O>
implements InternalOneSink<O> {
    static final SinkEmptyMulticast.Inner[] TERMINATED_VALUE = new SinkEmptyMulticast.Inner[0];
    static final int STATE_VALUE = 1;
    @Nullable
    O value;

    SinkOneMulticast() {
    }

    @Override
    boolean isTerminated(SinkEmptyMulticast.Inner<?>[] array) {
        return array == TERMINATED_VALUE || super.isTerminated(array);
    }

    @Override
    public Sinks.EmitResult tryEmitValue(@Nullable O value) {
        SinkEmptyMulticast.Inner[] prevSubscribers = this.subscribers;
        if (this.isTerminated(prevSubscribers)) {
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        if (value == null) {
            return this.tryEmitEmpty();
        }
        this.value = value;
        while (!SUBSCRIBERS.compareAndSet(this, prevSubscribers, TERMINATED_VALUE)) {
            prevSubscribers = this.subscribers;
            if (!this.isTerminated(prevSubscribers)) continue;
            return Sinks.EmitResult.FAIL_TERMINATED;
        }
        for (SinkEmptyMulticast.Inner as : prevSubscribers) {
            as.complete(value);
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
    public void subscribe(CoreSubscriber<? super O> actual) {
        NextInner<O> as = new NextInner<O>(actual, this);
        actual.onSubscribe(as);
        int addState = this.add((SinkEmptyMulticast.Inner<O>)as);
        if (addState == 0) {
            if (as.isCancelled()) {
                this.remove(as);
            }
        } else if (addState == -1) {
            actual.onError(this.error);
        } else if (addState == -2) {
            as.complete();
        } else {
            as.complete(this.value);
        }
    }

    @Override
    int add(SinkEmptyMulticast.Inner<O> ps) {
        SinkEmptyMulticast.Inner[] b;
        SinkEmptyMulticast.Inner[] a;
        do {
            if ((a = this.subscribers) == TERMINATED_EMPTY) {
                return -2;
            }
            if (a == TERMINATED_ERROR) {
                return -1;
            }
            if (a == TERMINATED_VALUE) {
                return 1;
            }
            int n = a.length;
            b = new SinkEmptyMulticast.Inner[n + 1];
            System.arraycopy(a, 0, b, 0, n);
            b[n] = ps;
        } while (!SUBSCRIBERS.compareAndSet(this, a, b));
        return 0;
    }

    @Override
    @Nullable
    public O block(Duration timeout2) {
        if (timeout2.isNegative()) {
            return (O)super.block(Duration.ZERO);
        }
        return (O)super.block(timeout2);
    }

    static final class NextInner<T>
    extends Operators.MonoInnerProducerBase<T>
    implements SinkEmptyMulticast.Inner<T> {
        final SinkOneMulticast<T> parent;

        NextInner(CoreSubscriber<? super T> actual, SinkOneMulticast<T> parent) {
            super(actual);
            this.parent = parent;
        }

        @Override
        protected void doOnCancel() {
            this.parent.remove(this);
        }

        @Override
        public void error(Throwable t2) {
            if (!this.isCancelled()) {
                this.actual().onError(t2);
            }
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return super.scanUnsafe(key);
        }
    }
}

