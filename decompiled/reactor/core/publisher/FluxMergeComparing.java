/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;
import reactor.util.concurrent.Queues;
import reactor.util.context.Context;

final class FluxMergeComparing<T>
extends Flux<T>
implements SourceProducer<T> {
    final int prefetch;
    final Comparator<? super T> valueComparator;
    final Publisher<? extends T>[] sources;
    final boolean delayError;
    final boolean waitForAllSources;

    @SafeVarargs
    FluxMergeComparing(int prefetch, Comparator<? super T> valueComparator, boolean delayError, boolean waitForAllSources, Publisher<? extends T> ... sources) {
        if (prefetch <= 0) {
            throw new IllegalArgumentException("prefetch > 0 required but it was " + prefetch);
        }
        this.sources = Objects.requireNonNull(sources, "sources must be non-null");
        for (int i = 0; i < sources.length; ++i) {
            Publisher<? extends T> source2 = sources[i];
            if (source2 != null) continue;
            throw new NullPointerException("sources[" + i + "] is null");
        }
        this.prefetch = prefetch;
        this.valueComparator = valueComparator;
        this.delayError = delayError;
        this.waitForAllSources = waitForAllSources;
    }

    FluxMergeComparing<T> mergeAdditionalSource(Publisher<? extends T> source2, Comparator<? super T> otherComparator) {
        int n = this.sources.length;
        Publisher[] newArray = new Publisher[n + 1];
        System.arraycopy(this.sources, 0, newArray, 0, n);
        newArray[n] = source2;
        if (!this.valueComparator.equals(otherComparator)) {
            Comparator<? super T> currentComparator = this.valueComparator;
            Comparator<? super T> newComparator = currentComparator.thenComparing(otherComparator);
            return new FluxMergeComparing<T>(this.prefetch, newComparator, this.delayError, this.waitForAllSources, newArray);
        }
        return new FluxMergeComparing<T>(this.prefetch, this.valueComparator, this.delayError, this.waitForAllSources, newArray);
    }

    @Override
    public int getPrefetch() {
        return this.prefetch;
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PARENT) {
            return this.sources.length > 0 ? this.sources[0] : null;
        }
        if (key == Scannable.Attr.PREFETCH) {
            return this.prefetch;
        }
        if (key == Scannable.Attr.DELAY_ERROR) {
            return this.delayError;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        MergeOrderedMainProducer<? extends T> main = new MergeOrderedMainProducer<T>(actual, this.valueComparator, this.prefetch, this.sources.length, this.delayError, this.waitForAllSources);
        actual.onSubscribe(main);
        main.subscribe(this.sources);
    }

    static final class MergeOrderedInnerSubscriber<T>
    implements InnerOperator<T, T> {
        final MergeOrderedMainProducer<T> parent;
        final int prefetch;
        final int limit;
        final Queue<T> queue;
        int consumed;
        volatile boolean done;
        volatile Subscription s;
        static final AtomicReferenceFieldUpdater<MergeOrderedInnerSubscriber, Subscription> S = AtomicReferenceFieldUpdater.newUpdater(MergeOrderedInnerSubscriber.class, Subscription.class, "s");

        MergeOrderedInnerSubscriber(MergeOrderedMainProducer<T> parent, int prefetch) {
            this.parent = parent;
            this.prefetch = prefetch;
            this.limit = prefetch - (prefetch >> 2);
            this.queue = Queues.get(prefetch).get();
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.setOnce(S, this, s2)) {
                s2.request(this.prefetch);
            }
        }

        @Override
        public void onNext(T item) {
            if (this.parent.done || this.done) {
                Operators.onNextDropped(item, this.actual().currentContext());
                return;
            }
            this.queue.offer(item);
            this.parent.drain();
        }

        @Override
        public void onError(Throwable throwable) {
            this.parent.onInnerError(this, throwable);
        }

        @Override
        public void onComplete() {
            this.done = true;
            this.parent.drain();
        }

        @Override
        public void request(long n) {
            int c = this.consumed + 1;
            if (c == this.limit) {
                this.consumed = 0;
                Subscription sub = this.s;
                if (sub != this) {
                    sub.request(c);
                }
            } else {
                this.consumed = c;
            }
        }

        @Override
        public void cancel() {
            Subscription sub = S.getAndSet(this, this);
            if (sub != null && sub != this) {
                sub.cancel();
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.parent.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ACTUAL) {
                return this.parent;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return this.prefetch;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.BUFFERED) {
                return this.queue.size();
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }

    static final class MergeOrderedMainProducer<T>
    implements InnerProducer<T> {
        static final Object DONE = new Object();
        final CoreSubscriber<? super T> actual;
        final MergeOrderedInnerSubscriber<T>[] subscribers;
        final Comparator<? super T> comparator;
        final Object[] values;
        final boolean delayError;
        final boolean waitForAllSources;
        boolean done;
        volatile Throwable error;
        static final AtomicReferenceFieldUpdater<MergeOrderedMainProducer, Throwable> ERROR = AtomicReferenceFieldUpdater.newUpdater(MergeOrderedMainProducer.class, Throwable.class, "error");
        volatile int cancelled;
        static final AtomicIntegerFieldUpdater<MergeOrderedMainProducer> CANCELLED = AtomicIntegerFieldUpdater.newUpdater(MergeOrderedMainProducer.class, "cancelled");
        volatile long requested;
        static final AtomicLongFieldUpdater<MergeOrderedMainProducer> REQUESTED = AtomicLongFieldUpdater.newUpdater(MergeOrderedMainProducer.class, "requested");
        volatile long emitted;
        static final AtomicLongFieldUpdater<MergeOrderedMainProducer> EMITTED = AtomicLongFieldUpdater.newUpdater(MergeOrderedMainProducer.class, "emitted");
        volatile int wip;
        static final AtomicIntegerFieldUpdater<MergeOrderedMainProducer> WIP = AtomicIntegerFieldUpdater.newUpdater(MergeOrderedMainProducer.class, "wip");

        MergeOrderedMainProducer(CoreSubscriber<? super T> actual, Comparator<? super T> comparator, int prefetch, int n, boolean delayError, boolean waitForAllSources) {
            this.actual = actual;
            this.comparator = comparator;
            this.delayError = delayError;
            this.waitForAllSources = waitForAllSources;
            MergeOrderedInnerSubscriber[] mergeOrderedInnerSub = new MergeOrderedInnerSubscriber[n];
            this.subscribers = mergeOrderedInnerSub;
            for (int i = 0; i < n; ++i) {
                this.subscribers[i] = new MergeOrderedInnerSubscriber(this, prefetch);
            }
            this.values = new Object[n];
        }

        void subscribe(Publisher<? extends T>[] sources) {
            if (sources.length != this.subscribers.length) {
                throw new IllegalArgumentException("must subscribe with " + this.subscribers.length + " sources");
            }
            for (int i = 0; i < sources.length; ++i) {
                Objects.requireNonNull(sources[i], "subscribed with a null source: sources[" + i + "]");
                sources[i].subscribe(this.subscribers[i]);
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public void request(long n) {
            Operators.addCap(REQUESTED, this, n);
            this.drain();
        }

        @Override
        public void cancel() {
            if (CANCELLED.compareAndSet(this, 0, 1)) {
                for (MergeOrderedInnerSubscriber<T> subscriber : this.subscribers) {
                    subscriber.cancel();
                }
                if (WIP.getAndIncrement(this) == 0) {
                    this.discardData();
                }
            }
        }

        void onInnerError(MergeOrderedInnerSubscriber<T> inner, Throwable ex) {
            Throwable e = Operators.onNextInnerError(ex, this.actual().currentContext(), this);
            if (e != null) {
                if (Exceptions.addThrowable(ERROR, this, e)) {
                    if (!this.delayError) {
                        this.done = true;
                    }
                    inner.done = true;
                    this.drain();
                } else {
                    inner.done = true;
                    Operators.onErrorDropped(e, this.actual.currentContext());
                }
            } else {
                inner.done = true;
                this.drain();
            }
        }

        void drain() {
            if (WIP.getAndIncrement(this) != 0) {
                return;
            }
            int missed = 1;
            CoreSubscriber<Object> actual = this.actual;
            Comparator<Object> comparator = this.comparator;
            MergeOrderedInnerSubscriber<T>[] subscribers = this.subscribers;
            int n = subscribers.length;
            Object[] values2 = this.values;
            long e = this.emitted;
            do {
                long r = this.requested;
                while (true) {
                    boolean d = this.done;
                    if (this.cancelled != 0) {
                        Arrays.fill(values2, null);
                        for (MergeOrderedInnerSubscriber<T> inner : subscribers) {
                            inner.queue.clear();
                        }
                        return;
                    }
                    int innerDoneCount = 0;
                    int nonEmpty = 0;
                    for (int i = 0; i < n; ++i) {
                        Object o = values2[i];
                        if (o == DONE) {
                            ++innerDoneCount;
                            ++nonEmpty;
                            continue;
                        }
                        if (o == null) {
                            boolean innerDone = subscribers[i].done;
                            o = subscribers[i].queue.poll();
                            if (o != null) {
                                values2[i] = o;
                                ++nonEmpty;
                                continue;
                            }
                            if (!innerDone) continue;
                            values2[i] = DONE;
                            ++innerDoneCount;
                            ++nonEmpty;
                            continue;
                        }
                        ++nonEmpty;
                    }
                    if (this.checkTerminated(d || innerDoneCount == n, actual)) {
                        return;
                    }
                    if (this.waitForAllSources && nonEmpty != n || !this.waitForAllSources && (nonEmpty == 0 || nonEmpty == innerDoneCount) || e >= r) break;
                    Object min2 = null;
                    int minIndex = -1;
                    int i = 0;
                    for (Object o : values2) {
                        if (o != DONE && o != null) {
                            boolean smaller;
                            Object t2;
                            try {
                                t2 = o;
                                smaller = min2 == null || comparator.compare(min2, t2) > 0;
                            }
                            catch (Throwable ex) {
                                Exceptions.addThrowable(ERROR, this, ex);
                                this.cancel();
                                actual.onError(Exceptions.terminate(ERROR, this));
                                return;
                            }
                            if (smaller) {
                                min2 = t2 = o;
                                minIndex = i;
                            }
                        }
                        ++i;
                    }
                    if (minIndex < 0) continue;
                    values2[minIndex] = null;
                    actual.onNext(min2);
                    ++e;
                    subscribers[minIndex].request(1L);
                }
                this.emitted = e;
            } while ((missed = WIP.addAndGet(this, -missed)) != 0);
        }

        boolean checkTerminated(boolean d, Subscriber<?> a) {
            if (this.cancelled != 0) {
                this.discardData();
                return true;
            }
            if (!d) {
                return false;
            }
            if (this.delayError) {
                Throwable e = this.error;
                if (e != null && e != Exceptions.TERMINATED) {
                    e = Exceptions.terminate(ERROR, this);
                    a.onError(e);
                } else {
                    a.onComplete();
                }
            } else {
                Throwable e = this.error;
                if (e != null && e != Exceptions.TERMINATED) {
                    e = Exceptions.terminate(ERROR, this);
                    this.cancel();
                    this.discardData();
                    a.onError(e);
                } else {
                    a.onComplete();
                }
            }
            return true;
        }

        private void discardData() {
            Context ctx = this.actual().currentContext();
            for (Object v : this.values) {
                if (v == DONE) continue;
                Operators.onDiscard(v, ctx);
            }
            Arrays.fill(this.values, null);
            for (MergeOrderedInnerSubscriber<T> subscriber : this.subscribers) {
                Operators.onDiscardQueueWithClear(subscriber.queue, ctx, null);
            }
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.ACTUAL) {
                return this.actual;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled > 0;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.error;
            }
            if (key == Scannable.Attr.DELAY_ERROR) {
                return this.delayError;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested - this.emitted;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return null;
        }
    }
}

