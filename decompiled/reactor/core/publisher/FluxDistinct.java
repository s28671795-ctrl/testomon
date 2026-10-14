/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxDistinct<T, K, C>
extends InternalFluxOperator<T, T> {
    final Function<? super T, ? extends K> keyExtractor;
    final Supplier<C> collectionSupplier;
    final BiPredicate<C, K> distinctPredicate;
    final Consumer<C> cleanupCallback;

    FluxDistinct(Flux<? extends T> source2, Function<? super T, ? extends K> keyExtractor, Supplier<C> collectionSupplier, BiPredicate<C, K> distinctPredicate, Consumer<C> cleanupCallback) {
        super(source2);
        this.keyExtractor = Objects.requireNonNull(keyExtractor, "keyExtractor");
        this.collectionSupplier = Objects.requireNonNull(collectionSupplier, "collectionSupplier");
        this.distinctPredicate = Objects.requireNonNull(distinctPredicate, "distinctPredicate");
        this.cleanupCallback = Objects.requireNonNull(cleanupCallback, "cleanupCallback");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        C collection = Objects.requireNonNull(this.collectionSupplier.get(), "The collectionSupplier returned a null collection");
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            return new DistinctConditionalSubscriber<T, K, C>((Fuseable.ConditionalSubscriber)actual, collection, this.keyExtractor, this.distinctPredicate, this.cleanupCallback);
        }
        return new DistinctSubscriber<T, K, C>(actual, collection, this.keyExtractor, this.distinctPredicate, this.cleanupCallback);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class DistinctFuseableSubscriber<T, K, C>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, T>,
    Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        final C collection;
        final Function<? super T, ? extends K> keyExtractor;
        final BiPredicate<C, K> distinctPredicate;
        final Consumer<C> cleanupCallback;
        Fuseable.QueueSubscription<T> qs;
        boolean done;
        int sourceMode;

        DistinctFuseableSubscriber(CoreSubscriber<? super T> actual, C collection, Function<? super T, ? extends K> keyExtractor, BiPredicate<C, K> predicate, Consumer<C> callback) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.collection = collection;
            this.keyExtractor = keyExtractor;
            this.distinctPredicate = predicate;
            this.cleanupCallback = callback;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.qs, s2)) {
                this.qs = (Fuseable.QueueSubscription)s2;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public void onNext(T t2) {
            if (!this.tryOnNext(t2)) {
                this.qs.request(1L);
            }
        }

        @Override
        public boolean tryOnNext(T t2) {
            boolean b;
            K k;
            if (this.sourceMode == 2) {
                this.actual.onNext(null);
                return true;
            }
            if (this.done) {
                Operators.onNextDropped(t2, this.ctx);
                return true;
            }
            try {
                k = Objects.requireNonNull(this.keyExtractor.apply(t2), "The distinct extractor returned a null value.");
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.qs, e, t2, this.ctx));
                Operators.onDiscard(t2, this.ctx);
                return true;
            }
            try {
                b = this.distinctPredicate.test(this.collection, k);
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.qs, e, t2, this.ctx));
                Operators.onDiscard(t2, this.ctx);
                return true;
            }
            if (b) {
                this.actual.onNext(t2);
                return true;
            }
            Operators.onDiscard(t2, this.ctx);
            return false;
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.ctx);
                return;
            }
            this.done = true;
            this.cleanupCallback.accept(this.collection);
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.cleanupCallback.accept(this.collection);
            this.actual.onComplete();
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public void request(long n) {
            this.qs.request(n);
        }

        @Override
        public void cancel() {
            this.qs.cancel();
            if (this.collection != null) {
                this.cleanupCallback.accept(this.collection);
            }
        }

        @Override
        public int requestFusion(int requestedMode) {
            int m4;
            this.sourceMode = m4 = this.qs.requestFusion(requestedMode);
            return m4;
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.qs;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        @Nullable
        public T poll() {
            if (this.sourceMode == 2) {
                long dropped = 0L;
                while (true) {
                    Object v;
                    if ((v = this.qs.poll()) == null) {
                        return null;
                    }
                    try {
                        K r = Objects.requireNonNull(this.keyExtractor.apply(v), "The keyExtractor returned a null collection");
                        if (this.distinctPredicate.test(this.collection, r)) {
                            if (dropped != 0L) {
                                this.request(dropped);
                            }
                            return (T)v;
                        }
                        Operators.onDiscard(v, this.ctx);
                        ++dropped;
                    }
                    catch (Throwable error) {
                        Operators.onDiscard(v, this.ctx);
                        throw error;
                    }
                }
            }
            Object v;
            while ((v = this.qs.poll()) != null) {
                try {
                    K r = Objects.requireNonNull(this.keyExtractor.apply(v), "The keyExtractor returned a null collection");
                    if (this.distinctPredicate.test(this.collection, r)) {
                        return (T)v;
                    }
                    Operators.onDiscard(v, this.ctx);
                }
                catch (Throwable error) {
                    Operators.onDiscard(v, this.ctx);
                    throw error;
                }
            }
            return null;
        }

        @Override
        public boolean isEmpty() {
            return this.qs.isEmpty();
        }

        @Override
        public void clear() {
            this.qs.clear();
            this.cleanupCallback.accept(this.collection);
        }

        @Override
        public int size() {
            return this.qs.size();
        }
    }

    static final class DistinctConditionalSubscriber<T, K, C>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;
        final Context ctx;
        final C collection;
        final Function<? super T, ? extends K> keyExtractor;
        final BiPredicate<C, K> distinctPredicate;
        final Consumer<C> cleanupCallback;
        Subscription s;
        boolean done;

        DistinctConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> actual, C collection, Function<? super T, ? extends K> keyExtractor, BiPredicate<C, K> distinctPredicate, Consumer<C> cleanupCallback) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.collection = collection;
            this.keyExtractor = keyExtractor;
            this.distinctPredicate = distinctPredicate;
            this.cleanupCallback = cleanupCallback;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public void onNext(T t2) {
            boolean b;
            K k;
            if (this.done) {
                Operators.onNextDropped(t2, this.ctx);
                return;
            }
            try {
                k = Objects.requireNonNull(this.keyExtractor.apply(t2), "The distinct extractor returned a null value.");
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t2, this.ctx));
                Operators.onDiscard(t2, this.ctx);
                return;
            }
            try {
                b = this.distinctPredicate.test(this.collection, k);
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t2, this.ctx));
                Operators.onDiscard(t2, this.ctx);
                return;
            }
            if (b) {
                this.actual.onNext(t2);
            } else {
                Operators.onDiscard(t2, this.ctx);
                this.s.request(1L);
            }
        }

        @Override
        public boolean tryOnNext(T t2) {
            boolean b;
            K k;
            if (this.done) {
                Operators.onNextDropped(t2, this.ctx);
                return true;
            }
            try {
                k = Objects.requireNonNull(this.keyExtractor.apply(t2), "The distinct extractor returned a null value.");
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t2, this.ctx));
                Operators.onDiscard(t2, this.ctx);
                return true;
            }
            try {
                b = this.distinctPredicate.test(this.collection, k);
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t2, this.ctx));
                Operators.onDiscard(t2, this.ctx);
                return true;
            }
            if (b) {
                return this.actual.tryOnNext(t2);
            }
            Operators.onDiscard(t2, this.ctx);
            return false;
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.ctx);
                return;
            }
            this.done = true;
            this.cleanupCallback.accept(this.collection);
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.cleanupCallback.accept(this.collection);
            this.actual.onComplete();
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public void request(long n) {
            this.s.request(n);
        }

        @Override
        public void cancel() {
            this.s.cancel();
            if (this.collection != null) {
                this.cleanupCallback.accept(this.collection);
            }
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }

    static final class DistinctSubscriber<T, K, C>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        final C collection;
        final Function<? super T, ? extends K> keyExtractor;
        final BiPredicate<C, K> distinctPredicate;
        final Consumer<C> cleanupCallback;
        Subscription s;
        boolean done;

        DistinctSubscriber(CoreSubscriber<? super T> actual, C collection, Function<? super T, ? extends K> keyExtractor, BiPredicate<C, K> distinctPredicate, Consumer<C> cleanupCallback) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.collection = collection;
            this.keyExtractor = keyExtractor;
            this.distinctPredicate = distinctPredicate;
            this.cleanupCallback = cleanupCallback;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public void onNext(T t2) {
            if (!this.tryOnNext(t2)) {
                this.s.request(1L);
            }
        }

        @Override
        public boolean tryOnNext(T t2) {
            boolean b;
            K k;
            if (this.done) {
                Operators.onNextDropped(t2, this.ctx);
                return true;
            }
            try {
                k = Objects.requireNonNull(this.keyExtractor.apply(t2), "The distinct extractor returned a null value.");
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t2, this.ctx));
                Operators.onDiscard(t2, this.ctx);
                return true;
            }
            try {
                b = this.distinctPredicate.test(this.collection, k);
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t2, this.ctx));
                Operators.onDiscard(t2, this.ctx);
                return true;
            }
            if (b) {
                this.actual.onNext(t2);
                return true;
            }
            Operators.onDiscard(t2, this.ctx);
            return false;
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.ctx);
                return;
            }
            this.done = true;
            this.cleanupCallback.accept(this.collection);
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.cleanupCallback.accept(this.collection);
            this.actual.onComplete();
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public void request(long n) {
            this.s.request(n);
        }

        @Override
        public void cancel() {
            this.s.cancel();
            if (this.collection != null) {
                this.cleanupCallback.accept(this.collection);
            }
        }

        @Override
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }
    }
}

