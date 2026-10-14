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
import reactor.util.context.Context;

final class FluxContextWrite<T>
extends InternalFluxOperator<T, T>
implements Fuseable {
    final Function<Context, Context> doOnContext;

    FluxContextWrite(Flux<? extends T> source2, Function<Context, Context> doOnContext) {
        super(source2);
        this.doOnContext = Objects.requireNonNull(doOnContext, "doOnContext");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        Context c = this.doOnContext.apply(actual.currentContext());
        return new ContextWriteSubscriber<T>(actual, c);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class ContextWriteSubscriber<T>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, T>,
    Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final Fuseable.ConditionalSubscriber<? super T> actualConditional;
        final Context context;
        Fuseable.QueueSubscription<T> qs;
        Subscription s;

        ContextWriteSubscriber(CoreSubscriber<? super T> actual, Context context) {
            this.actual = actual;
            this.context = context;
            this.actualConditional = actual instanceof Fuseable.ConditionalSubscriber ? (Fuseable.ConditionalSubscriber)actual : null;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public Context currentContext() {
            return this.context;
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                if (s2 instanceof Fuseable.QueueSubscription) {
                    this.qs = (Fuseable.QueueSubscription)s2;
                }
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public void onNext(T t2) {
            this.actual.onNext(t2);
        }

        @Override
        public boolean tryOnNext(T t2) {
            if (this.actualConditional != null) {
                return this.actualConditional.tryOnNext(t2);
            }
            this.actual.onNext(t2);
            return true;
        }

        @Override
        public void onError(Throwable t2) {
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
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
        }

        @Override
        public int requestFusion(int requestedMode) {
            if (this.qs == null) {
                return 0;
            }
            return this.qs.requestFusion(requestedMode);
        }

        @Override
        @Nullable
        public T poll() {
            if (this.qs != null) {
                return (T)this.qs.poll();
            }
            return null;
        }

        @Override
        public boolean isEmpty() {
            return this.qs == null || this.qs.isEmpty();
        }

        @Override
        public void clear() {
            if (this.qs != null) {
                this.qs.clear();
            }
        }

        @Override
        public int size() {
            return this.qs != null ? this.qs.size() : 0;
        }
    }
}

