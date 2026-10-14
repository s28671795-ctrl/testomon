/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.OperatorDisposables;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

final class FluxCreate<T>
extends Flux<T>
implements SourceProducer<T> {
    final Consumer<? super FluxSink<T>> source;
    final FluxSink.OverflowStrategy backpressure;
    final CreateMode createMode;

    FluxCreate(Consumer<? super FluxSink<T>> source2, FluxSink.OverflowStrategy backpressure, CreateMode createMode) {
        this.source = Objects.requireNonNull(source2, "source");
        this.backpressure = Objects.requireNonNull(backpressure, "backpressure");
        this.createMode = createMode;
    }

    static <T> BaseSink<T> createSink(CoreSubscriber<? super T> t2, FluxSink.OverflowStrategy backpressure) {
        switch (backpressure) {
            case IGNORE: {
                return new IgnoreSink<T>(t2);
            }
            case ERROR: {
                return new ErrorAsyncSink<T>(t2);
            }
            case DROP: {
                return new DropAsyncSink<T>(t2);
            }
            case LATEST: {
                return new LatestAsyncSink<T>(t2);
            }
        }
        return new BufferAsyncSink<T>(t2, Queues.SMALL_BUFFER_SIZE);
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        BaseSink<T> sink2 = FluxCreate.createSink(actual, this.backpressure);
        actual.onSubscribe(sink2);
        try {
            this.source.accept((FluxSink<BaseSink<T>>)((Object)(this.createMode == CreateMode.PUSH_PULL ? new SerializedFluxSink<T>(sink2) : sink2)));
        }
        catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            sink2.error(Operators.onOperatorError(ex, actual.currentContext()));
        }
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.ASYNC;
        }
        return null;
    }

    static final class SinkDisposable
    implements Disposable {
        Disposable onCancel;
        Disposable disposable;

        SinkDisposable(@Nullable Disposable disposable, @Nullable Disposable onCancel) {
            this.disposable = disposable;
            this.onCancel = onCancel;
        }

        @Override
        public void dispose() {
            if (this.disposable != null) {
                this.disposable.dispose();
            }
        }

        public void cancel() {
            if (this.onCancel != null) {
                this.onCancel.dispose();
            }
        }
    }

    static final class LatestAsyncSink<T>
    extends BaseSink<T> {
        final AtomicReference<T> queue = new AtomicReference();
        Throwable error;
        volatile boolean done;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<LatestAsyncSink> WIP = AtomicIntegerFieldUpdater.newUpdater(LatestAsyncSink.class, "wip");

        LatestAsyncSink(CoreSubscriber<? super T> actual) {
            super(actual);
        }

        @Override
        public FluxSink<T> next(T t2) {
            T old = this.queue.getAndSet(t2);
            Operators.onDiscard(old, this.ctx);
            this.drain();
            return this;
        }

        @Override
        public void error(Throwable e) {
            this.error = e;
            this.done = true;
            this.drain();
        }

        @Override
        public void complete() {
            this.done = true;
            this.drain();
        }

        @Override
        void onRequestedFromDownstream() {
            this.drain();
        }

        @Override
        void onCancel() {
            this.drain();
        }

        /*
         * Enabled aggressive block sorting
         */
        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            CoreSubscriber a = this.actual;
            AtomicReference<T> q = this.queue;
            while (true) {
                long r = this.requested;
                long e = 0L;
                while (e != r) {
                    boolean empty;
                    if (this.isCancelled()) {
                        Object old = q.getAndSet(null);
                        Operators.onDiscard(old, this.ctx);
                        if (WIP.decrementAndGet(this) == 0) return;
                        continue;
                    }
                    boolean d = this.done;
                    Object o = q.getAndSet(null);
                    boolean bl = empty = o == null;
                    if (d && empty) {
                        Throwable ex = this.error;
                        if (ex != null) {
                            super.error(ex);
                            return;
                        }
                        super.complete();
                        return;
                    }
                    if (empty) break;
                    a.onNext(o);
                    ++e;
                }
                if (e == r) {
                    boolean empty;
                    if (this.isCancelled()) {
                        Object old = q.getAndSet(null);
                        Operators.onDiscard(old, this.ctx);
                        if (WIP.decrementAndGet(this) == 0) return;
                        continue;
                    }
                    boolean d = this.done;
                    boolean bl = empty = q.get() == null;
                    if (d && empty) {
                        Throwable ex = this.error;
                        if (ex != null) {
                            super.error(ex);
                            return;
                        }
                        super.complete();
                        return;
                    }
                }
                if (e != 0L) {
                    Operators.produced(REQUESTED, this, e);
                }
                if (WIP.decrementAndGet(this) == 0) break;
            }
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue.get() == null ? 0 : 1;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            return super.scanUnsafe(key);
        }

        @Override
        public String toString() {
            return "FluxSink(" + (Object)((Object)FluxSink.OverflowStrategy.LATEST) + ")";
        }
    }

    static final class BufferAsyncSink<T>
    extends BaseSink<T> {
        final Queue<T> queue;
        Throwable error;
        volatile boolean done;
        volatile int wip;
        static final AtomicIntegerFieldUpdater<BufferAsyncSink> WIP = AtomicIntegerFieldUpdater.newUpdater(BufferAsyncSink.class, "wip");

        BufferAsyncSink(CoreSubscriber<? super T> actual, int capacityHint) {
            super(actual);
            this.queue = Queues.unbounded(capacityHint).get();
        }

        @Override
        public FluxSink<T> next(T t2) {
            this.queue.offer(t2);
            this.drain();
            return this;
        }

        @Override
        public void error(Throwable e) {
            this.error = e;
            this.done = true;
            this.drain();
        }

        @Override
        public void complete() {
            this.done = true;
            this.drain();
        }

        @Override
        void onRequestedFromDownstream() {
            this.drain();
        }

        @Override
        void onCancel() {
            this.drain();
        }

        /*
         * Enabled aggressive block sorting
         */
        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            CoreSubscriber a = this.actual;
            Queue<T> q = this.queue;
            while (true) {
                boolean d;
                long r = this.requested;
                long e = 0L;
                while (e != r) {
                    boolean empty;
                    if (this.isCancelled()) {
                        Operators.onDiscardQueueWithClear(q, this.ctx, null);
                        if (WIP.decrementAndGet(this) == 0) return;
                        continue;
                    }
                    d = this.done;
                    T o = q.poll();
                    boolean bl = empty = o == null;
                    if (d && empty) {
                        Throwable ex = this.error;
                        if (ex != null) {
                            super.error(ex);
                            return;
                        }
                        super.complete();
                        return;
                    }
                    if (empty) break;
                    a.onNext(o);
                    ++e;
                }
                if (e == r) {
                    if (this.isCancelled()) {
                        Operators.onDiscardQueueWithClear(q, this.ctx, null);
                        if (WIP.decrementAndGet(this) == 0) return;
                        continue;
                    }
                    d = this.done;
                    boolean empty = q.isEmpty();
                    if (d && empty) {
                        Throwable ex = this.error;
                        if (ex != null) {
                            super.error(ex);
                            return;
                        }
                        super.complete();
                        return;
                    }
                }
                if (e != 0L) {
                    Operators.produced(REQUESTED, this, e);
                }
                if (WIP.decrementAndGet(this) == 0) break;
            }
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue.size();
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            return super.scanUnsafe(key);
        }

        @Override
        public String toString() {
            return "FluxSink(" + (Object)((Object)FluxSink.OverflowStrategy.BUFFER) + ")";
        }
    }

    static final class ErrorAsyncSink<T>
    extends NoOverflowBaseAsyncSink<T> {
        ErrorAsyncSink(CoreSubscriber<? super T> actual) {
            super(actual);
        }

        @Override
        void onOverflow() {
            this.error(Exceptions.failWithOverflow());
        }

        @Override
        public String toString() {
            return "FluxSink(" + (Object)((Object)FluxSink.OverflowStrategy.ERROR) + ")";
        }
    }

    static final class DropAsyncSink<T>
    extends NoOverflowBaseAsyncSink<T> {
        DropAsyncSink(CoreSubscriber<? super T> actual) {
            super(actual);
        }

        @Override
        void onOverflow() {
        }

        @Override
        public String toString() {
            return "FluxSink(" + (Object)((Object)FluxSink.OverflowStrategy.DROP) + ")";
        }
    }

    static abstract class NoOverflowBaseAsyncSink<T>
    extends BaseSink<T> {
        NoOverflowBaseAsyncSink(CoreSubscriber<? super T> actual) {
            super(actual);
        }

        @Override
        public final FluxSink<T> next(T t2) {
            if (this.isTerminated()) {
                Operators.onNextDropped(t2, this.ctx);
                return this;
            }
            if (this.requested != 0L) {
                this.actual.onNext(t2);
                Operators.produced(REQUESTED, this, 1L);
            } else {
                this.onOverflow();
                Operators.onDiscard(t2, this.ctx);
            }
            return this;
        }

        abstract void onOverflow();
    }

    static final class IgnoreSink<T>
    extends BaseSink<T> {
        IgnoreSink(CoreSubscriber<? super T> actual) {
            super(actual);
        }

        @Override
        public FluxSink<T> next(T t2) {
            long r;
            if (this.isTerminated()) {
                Operators.onNextDropped(t2, this.ctx);
                return this;
            }
            if (this.isCancelled()) {
                Operators.onDiscard(t2, this.ctx);
                return this;
            }
            this.actual.onNext(t2);
            while ((r = this.requested) != 0L && !REQUESTED.compareAndSet(this, r, r - 1L)) {
            }
            return this;
        }

        @Override
        public String toString() {
            return "FluxSink(" + (Object)((Object)FluxSink.OverflowStrategy.IGNORE) + ")";
        }
    }

    static abstract class BaseSink<T>
    extends AtomicBoolean
    implements FluxSink<T>,
    InnerProducer<T> {
        static final Disposable TERMINATED = OperatorDisposables.DISPOSED;
        static final Disposable CANCELLED = Disposables.disposed();
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        volatile Disposable disposable;
        static final AtomicReferenceFieldUpdater<BaseSink, Disposable> DISPOSABLE = AtomicReferenceFieldUpdater.newUpdater(BaseSink.class, Disposable.class, "disposable");
        volatile long requested;
        static final AtomicLongFieldUpdater<BaseSink> REQUESTED = AtomicLongFieldUpdater.newUpdater(BaseSink.class, "requested");
        volatile LongConsumer requestConsumer;
        static final AtomicReferenceFieldUpdater<BaseSink, LongConsumer> REQUEST_CONSUMER = AtomicReferenceFieldUpdater.newUpdater(BaseSink.class, LongConsumer.class, "requestConsumer");

        BaseSink(CoreSubscriber<? super T> actual) {
            this.actual = actual;
            this.ctx = actual.currentContext();
        }

        @Override
        @Deprecated
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override
        public ContextView contextView() {
            return this.actual.currentContext();
        }

        @Override
        public void complete() {
            if (this.isTerminated()) {
                return;
            }
            try {
                this.actual.onComplete();
            }
            finally {
                this.disposeResource(false);
            }
        }

        @Override
        public void error(Throwable e) {
            if (this.isTerminated()) {
                Operators.onOperatorError(e, this.ctx);
                return;
            }
            try {
                this.actual.onError(e);
            }
            finally {
                this.disposeResource(false);
            }
        }

        @Override
        public final void cancel() {
            this.disposeResource(true);
            this.onCancel();
        }

        void disposeResource(boolean isCancel) {
            Disposable disposed = isCancel ? CANCELLED : TERMINATED;
            Disposable d = this.disposable;
            if (d != TERMINATED && d != CANCELLED && (d = DISPOSABLE.getAndSet(this, disposed)) != null && d != TERMINATED && d != CANCELLED) {
                if (isCancel && d instanceof SinkDisposable) {
                    ((SinkDisposable)d).cancel();
                }
                d.dispose();
            }
        }

        @Override
        public long requestedFromDownstream() {
            return this.requested;
        }

        void onCancel() {
        }

        @Override
        public final boolean isCancelled() {
            return this.disposable == CANCELLED;
        }

        final boolean isTerminated() {
            return this.disposable == TERMINATED;
        }

        @Override
        public final void request(long n) {
            if (Operators.validate(n)) {
                Operators.addCap(REQUESTED, this, n);
                LongConsumer consumer = this.requestConsumer;
                if (n > 0L && consumer != null && !this.isCancelled()) {
                    consumer.accept(n);
                }
                this.onRequestedFromDownstream();
            }
        }

        void onRequestedFromDownstream() {
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public FluxSink<T> onRequest(LongConsumer consumer) {
            Objects.requireNonNull(consumer, "onRequest");
            this.onRequest(consumer, n -> {}, Long.MAX_VALUE);
            return this;
        }

        protected void onRequest(LongConsumer initialRequestConsumer, LongConsumer requestConsumer, long value) {
            if (!REQUEST_CONSUMER.compareAndSet(this, null, requestConsumer)) {
                throw new IllegalStateException("A consumer has already been assigned to consume requests");
            }
            if (value > 0L) {
                initialRequestConsumer.accept(value);
            }
        }

        @Override
        public final FluxSink<T> onCancel(Disposable d) {
            Objects.requireNonNull(d, "onCancel");
            SinkDisposable sd = new SinkDisposable(null, d);
            if (!DISPOSABLE.compareAndSet(this, null, sd)) {
                Disposable c = this.disposable;
                if (c == CANCELLED) {
                    d.dispose();
                } else if (c instanceof SinkDisposable) {
                    SinkDisposable current = (SinkDisposable)c;
                    if (current.onCancel == null) {
                        current.onCancel = d;
                    } else {
                        d.dispose();
                    }
                }
            }
            return this;
        }

        @Override
        public final FluxSink<T> onDispose(Disposable d) {
            Objects.requireNonNull(d, "onDispose");
            SinkDisposable sd = new SinkDisposable(d, null);
            if (!DISPOSABLE.compareAndSet(this, null, sd)) {
                Disposable c = this.disposable;
                if (c == TERMINATED || c == CANCELLED) {
                    d.dispose();
                } else if (c instanceof SinkDisposable) {
                    SinkDisposable current = (SinkDisposable)c;
                    if (current.disposable == null) {
                        current.disposable = d;
                    } else {
                        d.dispose();
                    }
                }
            }
            return this;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.disposable == TERMINATED;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.disposable == CANCELLED;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.ASYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        public String toString() {
            return "FluxSink";
        }
    }

    static class SerializeOnRequestSink<T>
    implements FluxSink<T>,
    Scannable {
        final BaseSink<T> baseSink;
        SerializedFluxSink<T> serializedSink;
        FluxSink<T> sink;

        SerializeOnRequestSink(BaseSink<T> sink2) {
            this.baseSink = sink2;
            this.sink = sink2;
        }

        @Override
        @Deprecated
        public Context currentContext() {
            return this.sink.currentContext();
        }

        @Override
        public ContextView contextView() {
            return this.sink.contextView();
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            return this.serializedSink != null ? this.serializedSink.scanUnsafe(key) : this.baseSink.scanUnsafe(key);
        }

        @Override
        public void complete() {
            this.sink.complete();
        }

        @Override
        public void error(Throwable e) {
            this.sink.error(e);
        }

        @Override
        public FluxSink<T> next(T t2) {
            this.sink.next(t2);
            return this.serializedSink == null ? this : this.serializedSink;
        }

        @Override
        public long requestedFromDownstream() {
            return this.sink.requestedFromDownstream();
        }

        @Override
        public boolean isCancelled() {
            return this.sink.isCancelled();
        }

        @Override
        public FluxSink<T> onRequest(LongConsumer consumer) {
            if (this.serializedSink == null) {
                this.serializedSink = new SerializedFluxSink<T>(this.baseSink);
                this.sink = this.serializedSink;
            }
            return this.sink.onRequest(consumer);
        }

        @Override
        public FluxSink<T> onCancel(Disposable d) {
            this.sink.onCancel(d);
            return this.sink;
        }

        @Override
        public FluxSink<T> onDispose(Disposable d) {
            this.sink.onDispose(d);
            return this;
        }

        public String toString() {
            return this.baseSink.toString();
        }
    }

    static final class SerializedFluxSink<T>
    implements FluxSink<T>,
    Scannable {
        final BaseSink<T> sink;
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<SerializedFluxSink, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(SerializedFluxSink.class, Throwable.class, "error");
        volatile int wip;
        static final AtomicIntegerFieldUpdater<SerializedFluxSink> WIP = AtomicIntegerFieldUpdater.newUpdater(SerializedFluxSink.class, "wip");
        final Queue<T> mpscQueue;
        volatile boolean done;

        SerializedFluxSink(BaseSink<T> sink2) {
            this.sink = sink2;
            this.mpscQueue = Queues.unboundedMultiproducer().get();
        }

        @Override
        @Deprecated
        public Context currentContext() {
            return this.sink.currentContext();
        }

        @Override
        public ContextView contextView() {
            return this.sink.contextView();
        }

        @Override
        public FluxSink<T> next(T t2) {
            Objects.requireNonNull(t2, "t is null in sink.next(t)");
            if (this.sink.isTerminated() || this.done) {
                Operators.onNextDropped(t2, this.sink.currentContext());
                return this;
            }
            if (WIP.get(this) == 0 && WIP.compareAndSet(this, 0, 1)) {
                try {
                    this.sink.next(t2);
                }
                catch (Throwable ex) {
                    Operators.onOperatorError(this.sink, ex, t2, this.sink.currentContext());
                }
                if (WIP.decrementAndGet(this) == 0) {
                    return this;
                }
            } else {
                this.mpscQueue.offer(t2);
                if (WIP.getAndIncrement(this) != 0) {
                    return this;
                }
            }
            this.drainLoop();
            return this;
        }

        @Override
        public void error(Throwable t2) {
            Objects.requireNonNull(t2, "t is null in sink.error(t)");
            if (this.sink.isTerminated() || this.done) {
                Operators.onOperatorError(t2, this.sink.currentContext());
                return;
            }
            if (Exceptions.addThrowable(ERROR, this, t2)) {
                this.done = true;
                this.drain();
            } else {
                Context ctx = this.sink.currentContext();
                Operators.onDiscardQueueWithClear(this.mpscQueue, ctx, null);
                Operators.onOperatorError(t2, ctx);
            }
        }

        @Override
        public void complete() {
            if (this.sink.isTerminated() || this.done) {
                return;
            }
            this.done = true;
            this.drain();
        }

        void drain() {
            if (WIP.getAndIncrement(this) == 0) {
                this.drainLoop();
            }
        }

        void drainLoop() {
            Context ctx = this.sink.currentContext();
            BaseSink<T> e = this.sink;
            Queue<T> q = this.mpscQueue;
            while (true) {
                boolean empty;
                if (e.isCancelled()) {
                    Operators.onDiscardQueueWithClear(q, ctx, null);
                    if (WIP.decrementAndGet(this) != 0) continue;
                    return;
                }
                if (ERROR.get(this) != null) {
                    Operators.onDiscardQueueWithClear(q, ctx, null);
                    e.error(Exceptions.terminate(ERROR, this));
                    return;
                }
                boolean d = this.done;
                T v = q.poll();
                boolean bl = empty = v == null;
                if (d && empty) {
                    e.complete();
                    return;
                }
                if (!empty) {
                    try {
                        e.next(v);
                    }
                    catch (Throwable ex) {
                        Operators.onOperatorError(this.sink, ex, v, this.sink.currentContext());
                    }
                    continue;
                }
                if (WIP.decrementAndGet(this) == 0) break;
            }
        }

        @Override
        public FluxSink<T> onRequest(LongConsumer consumer) {
            this.sink.onRequest(consumer, consumer, this.sink.requested);
            return this;
        }

        @Override
        public FluxSink<T> onCancel(Disposable d) {
            this.sink.onCancel(d);
            return this;
        }

        @Override
        public FluxSink<T> onDispose(Disposable d) {
            this.sink.onDispose(d);
            return this;
        }

        @Override
        public long requestedFromDownstream() {
            return this.sink.requestedFromDownstream();
        }

        @Override
        public boolean isCancelled() {
            return this.sink.isCancelled();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.BUFFERED) {
                return this.mpscQueue.size();
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            return this.sink.scanUnsafe(key);
        }

        public String toString() {
            return this.sink.toString();
        }
    }

    static enum CreateMode {
        PUSH_ONLY,
        PUSH_PULL;

    }
}

