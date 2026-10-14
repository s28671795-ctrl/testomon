/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
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

final class FluxDistinctUntilChanged<T, K>
extends InternalFluxOperator<T, T> {
    final Function<? super T, K> keyExtractor;
    final BiPredicate<? super K, ? super K> keyComparator;

    FluxDistinctUntilChanged(Flux<? extends T> source2, Function<? super T, K> keyExtractor, BiPredicate<? super K, ? super K> keyComparator) {
        super(source2);
        this.keyExtractor = Objects.requireNonNull(keyExtractor, "keyExtractor");
        this.keyComparator = Objects.requireNonNull(keyComparator, "keyComparator");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            return new DistinctUntilChangedConditionalSubscriber<T, K>((Fuseable.ConditionalSubscriber)actual, this.keyExtractor, this.keyComparator);
        }
        return new DistinctUntilChangedSubscriber<T, K>(actual, this.keyExtractor, this.keyComparator);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class DistinctUntilChangedConditionalSubscriber<T, K>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;
        final Context ctx;
        final Function<? super T, K> keyExtractor;
        final BiPredicate<? super K, ? super K> keyComparator;
        Subscription s;
        boolean done;
        @Nullable
        K lastKey;

        DistinctUntilChangedConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> actual, Function<? super T, K> keyExtractor, BiPredicate<? super K, ? super K> keyComparator) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.keyExtractor = keyExtractor;
            this.keyComparator = keyComparator;
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
            boolean equiv;
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
            if (null == this.lastKey) {
                this.lastKey = k;
                return this.actual.tryOnNext(t2);
            }
            try {
                equiv = this.keyComparator.test(this.lastKey, k);
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t2, this.ctx));
                Operators.onDiscard(t2, this.ctx);
                return true;
            }
            if (equiv) {
                Operators.onDiscard(t2, this.ctx);
                return false;
            }
            this.lastKey = k;
            return this.actual.tryOnNext(t2);
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.ctx);
                return;
            }
            this.done = true;
            this.lastKey = null;
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.lastKey = null;
            this.actual.onComplete();
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
            this.lastKey = null;
        }
    }

    static final class DistinctUntilChangedSubscriber<T, K>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        final Function<? super T, K> keyExtractor;
        final BiPredicate<? super K, ? super K> keyComparator;
        Subscription s;
        boolean done;
        @Nullable
        K lastKey;

        DistinctUntilChangedSubscriber(CoreSubscriber<? super T> actual, Function<? super T, K> keyExtractor, BiPredicate<? super K, ? super K> keyComparator) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.keyExtractor = keyExtractor;
            this.keyComparator = keyComparator;
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
            boolean equiv;
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
            if (null == this.lastKey) {
                this.lastKey = k;
                this.actual.onNext(t2);
                return true;
            }
            try {
                equiv = this.keyComparator.test(this.lastKey, k);
            }
            catch (Throwable e) {
                this.onError(Operators.onOperatorError(this.s, e, t2, this.ctx));
                Operators.onDiscard(t2, this.ctx);
                return true;
            }
            if (equiv) {
                Operators.onDiscard(t2, this.ctx);
                return false;
            }
            this.lastKey = k;
            this.actual.onNext(t2);
            return true;
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.ctx);
                return;
            }
            this.done = true;
            this.lastKey = null;
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.lastKey = null;
            this.actual.onComplete();
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
            this.lastKey = null;
        }
    }
}

