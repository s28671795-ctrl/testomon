/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.function.Predicate;
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

final class FluxFilterFuseable<T>
extends InternalFluxOperator<T, T>
implements Fuseable {
    final Predicate<? super T> predicate;

    FluxFilterFuseable(Flux<? extends T> source2, Predicate<? super T> predicate) {
        super(source2);
        this.predicate = Objects.requireNonNull(predicate, "predicate");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            return new FilterFuseableConditionalSubscriber<T>((Fuseable.ConditionalSubscriber)actual, this.predicate);
        }
        return new FilterFuseableSubscriber<T>(actual, this.predicate);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class FilterFuseableConditionalSubscriber<T>
    implements InnerOperator<T, T>,
    Fuseable.ConditionalSubscriber<T>,
    Fuseable.QueueSubscription<T> {
        final Fuseable.ConditionalSubscriber<? super T> actual;
        final Context ctx;
        final Predicate<? super T> predicate;
        Fuseable.QueueSubscription<T> s;
        boolean done;
        int sourceMode;

        FilterFuseableConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> actual, Predicate<? super T> predicate) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.predicate = predicate;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = (Fuseable.QueueSubscription)s2;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public void onNext(T t2) {
            if (this.sourceMode == 2) {
                this.actual.onNext(null);
            } else {
                boolean b;
                if (this.done) {
                    Operators.onNextDropped(t2, this.ctx);
                    return;
                }
                try {
                    b = this.predicate.test(t2);
                }
                catch (Throwable e) {
                    Throwable e_ = Operators.onNextError(t2, e, this.ctx, this.s);
                    if (e_ != null) {
                        this.onError(e_);
                    } else {
                        this.s.request(1L);
                    }
                    Operators.onDiscard(t2, this.ctx);
                    return;
                }
                if (b) {
                    this.actual.onNext(t2);
                } else {
                    this.s.request(1L);
                    Operators.onDiscard(t2, this.ctx);
                }
            }
        }

        @Override
        public boolean tryOnNext(T t2) {
            boolean b;
            if (this.done) {
                Operators.onNextDropped(t2, this.ctx);
                return false;
            }
            try {
                b = this.predicate.test(t2);
            }
            catch (Throwable e) {
                Throwable e_ = Operators.onNextError(t2, e, this.ctx, this.s);
                if (e_ != null) {
                    this.onError(e_);
                }
                Operators.onDiscard(t2, this.ctx);
                return false;
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
        }

        /*
         * Unable to fully structure code
         */
        @Override
        @Nullable
        public T poll() {
            block9: {
                if (this.sourceMode != 2) break block9;
                dropped = 0L;
                while (true) lbl-1000:
                // 3 sources

                {
                    v = this.s.poll();
                    try {
                        if (v == null || this.predicate.test(v)) {
                            if (dropped != 0L) {
                                this.request(dropped);
                            }
                            return (T)v;
                        }
                        Operators.onDiscard(v, this.ctx);
                        ++dropped;
                    }
                    catch (Throwable e) {
                        e_ = Operators.onNextPollError(v, e, this.ctx);
                        Operators.onDiscard(v, this.ctx);
                        if (e_ != null) ** break;
                        continue;
                        throw e_;
                    }
                    break;
                }
                ** GOTO lbl-1000
            }
            while (true) {
                v = this.s.poll();
                try {
                    if (v == null || this.predicate.test(v)) {
                        return (T)v;
                    }
                    Operators.onDiscard(v, this.ctx);
                    continue;
                }
                catch (Throwable e) {
                    e_ = Operators.onNextPollError(v, e, this.ctx);
                    Operators.onDiscard(v, this.ctx);
                    if (e_ != null) ** break;
                    continue;
                    throw e_;
                }
                break;
            }
        }

        @Override
        public boolean isEmpty() {
            return this.s.isEmpty();
        }

        @Override
        public void clear() {
            this.s.clear();
        }

        @Override
        public int size() {
            return this.s.size();
        }

        @Override
        public int requestFusion(int requestedMode) {
            int m4;
            if ((requestedMode & 4) != 0) {
                return 0;
            }
            this.sourceMode = m4 = this.s.requestFusion(requestedMode);
            return m4;
        }
    }

    static final class FilterFuseableSubscriber<T>
    implements InnerOperator<T, T>,
    Fuseable.QueueSubscription<T>,
    Fuseable.ConditionalSubscriber<T> {
        final CoreSubscriber<? super T> actual;
        final Context ctx;
        final Predicate<? super T> predicate;
        Fuseable.QueueSubscription<T> s;
        boolean done;
        int sourceMode;

        FilterFuseableSubscriber(CoreSubscriber<? super T> actual, Predicate<? super T> predicate) {
            this.actual = actual;
            this.ctx = actual.currentContext();
            this.predicate = predicate;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = (Fuseable.QueueSubscription)s2;
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public void onNext(T t2) {
            if (this.sourceMode == 2) {
                this.actual.onNext(null);
            } else {
                boolean b;
                if (this.done) {
                    Operators.onNextDropped(t2, this.ctx);
                    return;
                }
                try {
                    b = this.predicate.test(t2);
                }
                catch (Throwable e) {
                    Throwable e_ = Operators.onNextError(t2, e, this.ctx, this.s);
                    if (e_ != null) {
                        this.onError(e_);
                    } else {
                        this.s.request(1L);
                    }
                    Operators.onDiscard(t2, this.ctx);
                    return;
                }
                if (b) {
                    this.actual.onNext(t2);
                } else {
                    this.s.request(1L);
                    Operators.onDiscard(t2, this.ctx);
                }
            }
        }

        @Override
        public boolean tryOnNext(T t2) {
            boolean b;
            if (this.done) {
                Operators.onNextDropped(t2, this.ctx);
                return false;
            }
            try {
                b = this.predicate.test(t2);
            }
            catch (Throwable e) {
                Throwable e_ = Operators.onNextError(t2, e, this.ctx, this.s);
                if (e_ != null) {
                    this.onError(e_);
                }
                Operators.onDiscard(t2, this.ctx);
                return false;
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
        }

        /*
         * Unable to fully structure code
         */
        @Override
        @Nullable
        public T poll() {
            block9: {
                if (this.sourceMode != 2) break block9;
                dropped = 0L;
                while (true) lbl-1000:
                // 3 sources

                {
                    v = this.s.poll();
                    try {
                        if (v == null || this.predicate.test(v)) {
                            if (dropped != 0L) {
                                this.request(dropped);
                            }
                            return (T)v;
                        }
                        Operators.onDiscard(v, this.ctx);
                        ++dropped;
                    }
                    catch (Throwable e) {
                        e_ = Operators.onNextPollError(v, e, this.currentContext());
                        Operators.onDiscard(v, this.ctx);
                        if (e_ != null) ** break;
                        continue;
                        throw e_;
                    }
                    break;
                }
                ** GOTO lbl-1000
            }
            while (true) {
                v = this.s.poll();
                try {
                    if (v == null || this.predicate.test(v)) {
                        return (T)v;
                    }
                    Operators.onDiscard(v, this.ctx);
                    continue;
                }
                catch (Throwable e) {
                    e_ = Operators.onNextPollError(v, e, this.currentContext());
                    Operators.onDiscard(v, this.ctx);
                    if (e_ != null) ** break;
                    continue;
                    throw e_;
                }
                break;
            }
        }

        @Override
        public boolean isEmpty() {
            return this.s.isEmpty();
        }

        @Override
        public void clear() {
            this.s.clear();
        }

        @Override
        public int requestFusion(int requestedMode) {
            int m4;
            if ((requestedMode & 4) != 0) {
                return 0;
            }
            this.sourceMode = m4 = this.s.requestFusion(requestedMode);
            return m4;
        }

        @Override
        public int size() {
            return this.s.size();
        }
    }
}

