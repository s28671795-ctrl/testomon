/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
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

final class FluxMap<T, R>
extends InternalFluxOperator<T, R> {
    final Function<? super T, ? extends R> mapper;

    FluxMap(Flux<? extends T> source2, Function<? super T, ? extends R> mapper2) {
        super(source2);
        this.mapper = Objects.requireNonNull(mapper2, "mapper");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            Fuseable.ConditionalSubscriber cs = (Fuseable.ConditionalSubscriber)actual;
            return new MapConditionalSubscriber<T, R>(cs, this.mapper);
        }
        return new MapSubscriber<T, R>(actual, this.mapper);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class MapConditionalSubscriber<T, R>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, R> {
        final Fuseable.ConditionalSubscriber<? super R> actual;
        final Function<? super T, ? extends R> mapper;
        boolean done;
        Subscription s;

        MapConditionalSubscriber(Fuseable.ConditionalSubscriber<? super R> actual, Function<? super T, ? extends R> mapper2) {
            this.actual = actual;
            this.mapper = mapper2;
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
            R v;
            if (this.done) {
                Operators.onNextDropped(t2, this.actual.currentContext());
                return;
            }
            try {
                v = this.mapper.apply(t2);
                if (v == null) {
                    throw new NullPointerException("The mapper [" + this.mapper.getClass().getName() + "] returned a null value.");
                }
            }
            catch (Throwable e) {
                Throwable e_ = Operators.onNextError(t2, e, this.actual.currentContext(), this.s);
                if (e_ != null) {
                    this.onError(e_);
                } else {
                    this.s.request(1L);
                }
                return;
            }
            this.actual.onNext(v);
        }

        @Override
        public boolean tryOnNext(T t2) {
            if (this.done) {
                Operators.onNextDropped(t2, this.actual.currentContext());
                return true;
            }
            try {
                R v = this.mapper.apply(t2);
                if (v == null) {
                    throw new NullPointerException("The mapper [" + this.mapper.getClass().getName() + "] returned a null value.");
                }
                return this.actual.tryOnNext(v);
            }
            catch (Throwable e) {
                Throwable e_ = Operators.onNextError(t2, e, this.actual.currentContext(), this.s);
                if (e_ != null) {
                    this.done = true;
                    this.actual.onError(e_);
                    return true;
                }
                return false;
            }
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
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
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override
        public void request(long n) {
            this.s.request(n);
        }

        @Override
        public void cancel() {
            this.s.cancel();
        }
    }

    static final class MapSubscriber<T, R>
    implements InnerOperator<T, R> {
        final CoreSubscriber<? super R> actual;
        final Function<? super T, ? extends R> mapper;
        boolean done;
        Subscription s;

        MapSubscriber(CoreSubscriber<? super R> actual, Function<? super T, ? extends R> mapper2) {
            this.actual = actual;
            this.mapper = mapper2;
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
            R v;
            if (this.done) {
                Operators.onNextDropped(t2, this.actual.currentContext());
                return;
            }
            try {
                v = this.mapper.apply(t2);
                if (v == null) {
                    throw new NullPointerException("The mapper [" + this.mapper.getClass().getName() + "] returned a null value.");
                }
            }
            catch (Throwable e) {
                Throwable e_ = Operators.onNextError(t2, e, this.actual.currentContext(), this.s);
                if (e_ != null) {
                    this.onError(e_);
                } else {
                    this.s.request(1L);
                }
                return;
            }
            this.actual.onNext(v);
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
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
        public CoreSubscriber<? super R> actual() {
            return this.actual;
        }

        @Override
        public void request(long n) {
            this.s.request(n);
        }

        @Override
        public void cancel() {
            this.s.cancel();
        }
    }
}

