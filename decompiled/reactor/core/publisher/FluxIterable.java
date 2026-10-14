/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.Consumer;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.util.annotation.Nullable;
import reactor.util.function.Tuple2;

final class FluxIterable<T>
extends Flux<T>
implements Fuseable,
SourceProducer<T> {
    final Iterable<? extends T> iterable;
    @Nullable
    private final Runnable onClose;

    static <T> boolean checkFinite(Spliterator<? extends T> spliterator) {
        return spliterator.hasCharacteristics(64);
    }

    FluxIterable(Iterable<? extends T> iterable) {
        this.iterable = Objects.requireNonNull(iterable, "iterable");
        this.onClose = null;
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        boolean knownToBeFinite;
        Spliterator<? extends T> sp2;
        try {
            sp2 = this.iterable.spliterator();
            knownToBeFinite = FluxIterable.checkFinite(sp2);
        }
        catch (Throwable e) {
            Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
            return;
        }
        FluxIterable.subscribe(actual, sp2, knownToBeFinite, this.onClose);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.BUFFERED) {
            if (this.iterable instanceof Collection) {
                return ((Collection)this.iterable).size();
            }
            if (this.iterable instanceof Tuple2) {
                return ((Tuple2)this.iterable).size();
            }
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static <T> void subscribe(CoreSubscriber<? super T> s2, Spliterator<? extends T> sp2, boolean knownToBeFinite) {
        FluxIterable.subscribe(s2, sp2, knownToBeFinite, null);
    }

    static <T> void subscribe(CoreSubscriber<? super T> s2, Spliterator<? extends T> sp2, boolean knownToBeFinite, @Nullable Runnable onClose2) {
        boolean isEmpty;
        if (sp2 == null) {
            Operators.error(s2, new NullPointerException("The iterator is null"));
            return;
        }
        try {
            isEmpty = knownToBeFinite && sp2.estimateSize() == 0L;
        }
        catch (Throwable e) {
            Operators.error(s2, Operators.onOperatorError(e, s2.currentContext()));
            if (onClose2 != null) {
                try {
                    onClose2.run();
                }
                catch (Throwable t2) {
                    Operators.onErrorDropped(t2, s2.currentContext());
                }
            }
            return;
        }
        if (isEmpty) {
            Operators.complete(s2);
            if (onClose2 != null) {
                try {
                    onClose2.run();
                }
                catch (Throwable t3) {
                    Operators.onErrorDropped(t3, s2.currentContext());
                }
            }
            return;
        }
        if (s2 instanceof Fuseable.ConditionalSubscriber) {
            boolean hasNext;
            IterableSubscriptionConditional<T> isc = new IterableSubscriptionConditional<T>((Fuseable.ConditionalSubscriber)s2, sp2, knownToBeFinite, onClose2);
            try {
                hasNext = isc.hasNext();
            }
            catch (Throwable ex) {
                Operators.error(s2, ex);
                ((IterableSubscriptionConditional)isc).onCloseWithDropError();
                return;
            }
            if (!hasNext) {
                Operators.complete(s2);
                ((IterableSubscriptionConditional)isc).onCloseWithDropError();
                return;
            }
            s2.onSubscribe(isc);
        } else {
            boolean hasNext;
            IterableSubscription<T> is = new IterableSubscription<T>(s2, sp2, knownToBeFinite, onClose2);
            try {
                hasNext = is.hasNext();
            }
            catch (Throwable ex) {
                Operators.error(s2, ex);
                ((IterableSubscription)is).onCloseWithDropError();
                return;
            }
            if (!hasNext) {
                Operators.complete(s2);
                ((IterableSubscription)is).onCloseWithDropError();
                return;
            }
            s2.onSubscribe(is);
        }
    }

    static final class IterableSubscriptionConditional<T>
    implements InnerProducer<T>,
    Fuseable.SynchronousSubscription<T>,
    Consumer<T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;
        final Spliterator<? extends T> spliterator;
        final boolean knownToBeFinite;
        final Runnable onClose;
        volatile boolean cancelled;
        volatile long requested;
        static final AtomicLongFieldUpdater<IterableSubscriptionConditional> REQUESTED = AtomicLongFieldUpdater.newUpdater(IterableSubscriptionConditional.class, "requested");
        int state;
        static final int STATE_HAS_NEXT_NO_VALUE = 0;
        static final int STATE_HAS_NEXT_HAS_VALUE = 1;
        static final int STATE_NO_NEXT = 2;
        static final int STATE_CALL_HAS_NEXT = 3;
        T current;
        boolean valueReady = false;
        T nextElement;
        Throwable hasNextFailure;

        IterableSubscriptionConditional(Fuseable.ConditionalSubscriber<? super T> actual, Spliterator<? extends T> spliterator, boolean knownToBeFinite, @Nullable Runnable onClose2) {
            this.actual = actual;
            this.spliterator = spliterator;
            this.knownToBeFinite = knownToBeFinite;
            this.onClose = onClose2;
        }

        IterableSubscriptionConditional(Fuseable.ConditionalSubscriber<? super T> actual, Spliterator<? extends T> spliterator, boolean knownToBeFinite) {
            this(actual, spliterator, knownToBeFinite, null);
        }

        @Override
        public void accept(T t2) {
            this.valueReady = true;
            this.nextElement = t2;
        }

        boolean hasNext() {
            if (!this.valueReady) {
                this.spliterator.tryAdvance(this);
            }
            return this.valueReady;
        }

        T next() {
            if (!this.valueReady && !this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            T t2 = this.nextElement;
            this.nextElement = null;
            return t2;
        }

        @Override
        public void request(long n) {
            if (Operators.validate(n) && Operators.addCap(REQUESTED, this, n) == 0L) {
                if (n == Long.MAX_VALUE) {
                    this.fastPath();
                } else {
                    this.slowPath(n);
                }
            }
        }

        private void onCloseWithDropError() {
            if (this.onClose != null) {
                try {
                    this.onClose.run();
                }
                catch (Throwable t2) {
                    Operators.onErrorDropped(t2, this.actual.currentContext());
                }
            }
        }

        void slowPath(long n) {
            Fuseable.ConditionalSubscriber<T> s2 = this.actual;
            long e = 0L;
            while (true) {
                if (e != n) {
                    boolean b;
                    T t2;
                    try {
                        t2 = Objects.requireNonNull(this.next(), "The iterator returned a null value");
                    }
                    catch (Throwable ex) {
                        s2.onError(ex);
                        this.onCloseWithDropError();
                        return;
                    }
                    if (this.cancelled) {
                        return;
                    }
                    boolean consumed = s2.tryOnNext(t2);
                    if (this.cancelled) {
                        return;
                    }
                    try {
                        b = this.hasNext();
                    }
                    catch (Throwable ex) {
                        s2.onError(ex);
                        this.onCloseWithDropError();
                        return;
                    }
                    if (this.cancelled) {
                        return;
                    }
                    if (!b) {
                        s2.onComplete();
                        this.onCloseWithDropError();
                        return;
                    }
                    if (!consumed) continue;
                    ++e;
                    continue;
                }
                n = this.requested;
                if (n != e) continue;
                n = REQUESTED.addAndGet(this, -e);
                if (n == 0L) {
                    return;
                }
                e = 0L;
            }
        }

        void fastPath() {
            boolean b;
            Fuseable.ConditionalSubscriber<T> s2 = this.actual;
            do {
                T t2;
                if (this.cancelled) {
                    return;
                }
                try {
                    t2 = Objects.requireNonNull(this.next(), "The iterator returned a null value");
                }
                catch (Exception ex) {
                    s2.onError(ex);
                    this.onCloseWithDropError();
                    return;
                }
                if (this.cancelled) {
                    return;
                }
                s2.tryOnNext(t2);
                if (this.cancelled) {
                    return;
                }
                try {
                    b = this.hasNext();
                }
                catch (Exception ex) {
                    s2.onError(ex);
                    this.onCloseWithDropError();
                    return;
                }
                if (!this.cancelled) continue;
                return;
            } while (b);
            s2.onComplete();
            this.onCloseWithDropError();
        }

        @Override
        public void cancel() {
            this.onCloseWithDropError();
            this.cancelled = true;
            Operators.onDiscard(this.nextElement, this.actual.currentContext());
            Operators.onDiscardMultiple(this.spliterator, this.knownToBeFinite, this.actual.currentContext());
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.state == 2;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        public void clear() {
            Operators.onDiscard(this.nextElement, this.actual.currentContext());
            Operators.onDiscardMultiple(this.spliterator, this.knownToBeFinite, this.actual.currentContext());
            this.state = 2;
        }

        @Override
        public boolean isEmpty() {
            boolean hasNext;
            int s2 = this.state;
            if (s2 == 2) {
                return true;
            }
            if (this.cancelled && !this.knownToBeFinite) {
                return true;
            }
            if (s2 == 1 || s2 == 0) {
                return false;
            }
            try {
                hasNext = this.hasNext();
            }
            catch (Throwable t2) {
                this.state = 0;
                this.hasNextFailure = t2;
                return false;
            }
            if (hasNext) {
                this.state = 0;
                return false;
            }
            this.state = 2;
            return true;
        }

        @Override
        @Nullable
        public T poll() {
            if (this.hasNextFailure != null) {
                this.state = 2;
                throw Exceptions.propagate(this.hasNextFailure);
            }
            if (!this.isEmpty()) {
                T c;
                if (this.state == 0) {
                    c = this.next();
                } else {
                    c = this.current;
                    this.current = null;
                }
                this.state = 3;
                return c;
            }
            this.onCloseWithDropError();
            return null;
        }

        @Override
        public int size() {
            if (this.state == 2) {
                return 0;
            }
            return 1;
        }
    }

    static final class IterableSubscription<T>
    implements InnerProducer<T>,
    Fuseable.SynchronousSubscription<T>,
    Consumer<T> {
        final CoreSubscriber<? super T> actual;
        final Spliterator<? extends T> spliterator;
        final boolean knownToBeFinite;
        final Runnable onClose;
        volatile boolean cancelled;
        volatile long requested;
        static final AtomicLongFieldUpdater<IterableSubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(IterableSubscription.class, "requested");
        int state;
        static final int STATE_HAS_NEXT_NO_VALUE = 0;
        static final int STATE_HAS_NEXT_HAS_VALUE = 1;
        static final int STATE_NO_NEXT = 2;
        static final int STATE_CALL_HAS_NEXT = 3;
        T current;
        boolean valueReady = false;
        T nextElement;
        Throwable hasNextFailure;

        IterableSubscription(CoreSubscriber<? super T> actual, Spliterator<? extends T> spliterator, boolean knownToBeFinite, @Nullable Runnable onClose2) {
            this.actual = actual;
            this.spliterator = spliterator;
            this.knownToBeFinite = knownToBeFinite;
            this.onClose = onClose2;
        }

        IterableSubscription(CoreSubscriber<? super T> actual, Spliterator<? extends T> spliterator, boolean knownToBeFinite) {
            this(actual, spliterator, knownToBeFinite, null);
        }

        @Override
        public void accept(T t2) {
            this.valueReady = true;
            this.nextElement = t2;
        }

        boolean hasNext() {
            if (!this.valueReady) {
                this.spliterator.tryAdvance(this);
            }
            return this.valueReady;
        }

        T next() {
            if (!this.valueReady && !this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.valueReady = false;
            T t2 = this.nextElement;
            this.nextElement = null;
            return t2;
        }

        @Override
        public void request(long n) {
            if (Operators.validate(n) && Operators.addCap(REQUESTED, this, n) == 0L) {
                if (n == Long.MAX_VALUE) {
                    this.fastPath();
                } else {
                    this.slowPath(n);
                }
            }
        }

        private void onCloseWithDropError() {
            if (this.onClose != null) {
                try {
                    this.onClose.run();
                }
                catch (Throwable t2) {
                    Operators.onErrorDropped(t2, this.actual.currentContext());
                }
            }
        }

        void slowPath(long n) {
            CoreSubscriber<T> s2 = this.actual;
            long e = 0L;
            while (true) {
                if (e != n) {
                    boolean b;
                    T t2;
                    try {
                        t2 = Objects.requireNonNull(this.next(), "The iterator returned a null value");
                    }
                    catch (Throwable ex) {
                        s2.onError(ex);
                        this.onCloseWithDropError();
                        return;
                    }
                    if (this.cancelled) {
                        return;
                    }
                    s2.onNext(t2);
                    if (this.cancelled) {
                        return;
                    }
                    try {
                        b = this.hasNext();
                    }
                    catch (Throwable ex) {
                        s2.onError(ex);
                        this.onCloseWithDropError();
                        return;
                    }
                    if (this.cancelled) {
                        return;
                    }
                    if (!b) {
                        s2.onComplete();
                        this.onCloseWithDropError();
                        return;
                    }
                    ++e;
                    continue;
                }
                n = this.requested;
                if (n != e) continue;
                n = REQUESTED.addAndGet(this, -e);
                if (n == 0L) {
                    return;
                }
                e = 0L;
            }
        }

        void fastPath() {
            boolean b;
            CoreSubscriber<T> s2 = this.actual;
            do {
                T t2;
                if (this.cancelled) {
                    return;
                }
                try {
                    t2 = Objects.requireNonNull(this.next(), "The iterator returned a null value");
                }
                catch (Exception ex) {
                    s2.onError(ex);
                    this.onCloseWithDropError();
                    return;
                }
                if (this.cancelled) {
                    return;
                }
                s2.onNext(t2);
                if (this.cancelled) {
                    return;
                }
                try {
                    b = this.hasNext();
                }
                catch (Exception ex) {
                    s2.onError(ex);
                    this.onCloseWithDropError();
                    return;
                }
                if (!this.cancelled) continue;
                return;
            } while (b);
            s2.onComplete();
            this.onCloseWithDropError();
        }

        @Override
        public void cancel() {
            this.onCloseWithDropError();
            this.cancelled = true;
            Operators.onDiscard(this.nextElement, this.actual.currentContext());
            Operators.onDiscardMultiple(this.spliterator, this.knownToBeFinite, this.actual.currentContext());
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.state == 2;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        public void clear() {
            Operators.onDiscard(this.nextElement, this.actual.currentContext());
            Operators.onDiscardMultiple(this.spliterator, this.knownToBeFinite, this.actual.currentContext());
            this.state = 2;
        }

        @Override
        public boolean isEmpty() {
            boolean hasNext;
            int s2 = this.state;
            if (s2 == 2) {
                return true;
            }
            if (this.cancelled && !this.knownToBeFinite) {
                return true;
            }
            if (s2 == 1 || s2 == 0) {
                return false;
            }
            try {
                hasNext = this.hasNext();
            }
            catch (Throwable t2) {
                this.state = 0;
                this.hasNextFailure = t2;
                return false;
            }
            if (hasNext) {
                this.state = 0;
                return false;
            }
            this.state = 2;
            return true;
        }

        @Override
        @Nullable
        public T poll() {
            if (this.hasNextFailure != null) {
                this.state = 2;
                throw Exceptions.propagate(this.hasNextFailure);
            }
            if (!this.isEmpty()) {
                T c;
                if (this.state == 0) {
                    c = this.next();
                } else {
                    c = this.current;
                    this.current = null;
                }
                this.state = 3;
                if (c == null) {
                    this.onCloseWithDropError();
                    throw new NullPointerException("iterator returned a null value");
                }
                return c;
            }
            this.onCloseWithDropError();
            return null;
        }

        @Override
        public int size() {
            if (this.state == 2) {
                return 0;
            }
            return 1;
        }
    }
}

