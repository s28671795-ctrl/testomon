/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.micrometer.context.ContextSnapshot$Scope
 */
package reactor.core.publisher;

import io.micrometer.context.ContextSnapshot;
import java.util.Objects;
import java.util.function.Function;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.ContextPropagation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxOperator;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxContextWriteRestoringThreadLocals<T>
extends FluxOperator<T, T>
implements Fuseable {
    final Function<Context, Context> doOnContext;

    FluxContextWriteRestoringThreadLocals(Flux<? extends T> source2, Function<Context, Context> doOnContext) {
        super(source2);
        this.doOnContext = Objects.requireNonNull(doOnContext, "doOnContext");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        Context c = this.doOnContext.apply(actual.currentContext());
        try (ContextSnapshot.Scope ignored = ContextPropagation.setThreadLocals(c);){
            this.source.subscribe(new ContextWriteRestoringThreadLocalsSubscriber<T>(actual, c));
        }
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class ContextWriteRestoringThreadLocalsSubscriber<T>
    implements Fuseable.ConditionalSubscriber<T>,
    InnerOperator<T, T>,
    Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final Fuseable.ConditionalSubscriber<? super T> actualConditional;
        final Context context;
        Subscription s;

        ContextWriteRestoringThreadLocalsSubscriber(CoreSubscriber<? super T> actual, Context context) {
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
            try (ContextSnapshot.Scope ignored = ContextPropagation.setThreadLocals(this.actual.currentContext());){
                if (Operators.validate(this.s, s2)) {
                    this.s = s2;
                    this.actual.onSubscribe(this);
                }
            }
        }

        @Override
        public void onNext(T t2) {
            try (ContextSnapshot.Scope ignored = ContextPropagation.setThreadLocals(this.actual.currentContext());){
                this.actual.onNext(t2);
            }
        }

        @Override
        public boolean tryOnNext(T t2) {
            try (ContextSnapshot.Scope ignored = ContextPropagation.setThreadLocals(this.actual.currentContext());){
                if (this.actualConditional != null) {
                    boolean bl = this.actualConditional.tryOnNext(t2);
                    return bl;
                }
                this.actual.onNext(t2);
                boolean bl = true;
                return bl;
            }
        }

        @Override
        public void onError(Throwable t2) {
            try (ContextSnapshot.Scope ignored = ContextPropagation.setThreadLocals(this.actual.currentContext());){
                this.actual.onError(t2);
            }
        }

        @Override
        public void onComplete() {
            try (ContextSnapshot.Scope ignored = ContextPropagation.setThreadLocals(this.actual.currentContext());){
                this.actual.onComplete();
            }
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public void request(long n) {
            try (ContextSnapshot.Scope ignored = ContextPropagation.setThreadLocals(this.context);){
                this.s.request(n);
            }
        }

        @Override
        public void cancel() {
            try (ContextSnapshot.Scope ignored = ContextPropagation.setThreadLocals(this.context);){
                this.s.cancel();
            }
        }

        @Override
        public int requestFusion(int requestedMode) {
            return 0;
        }

        @Override
        @Nullable
        public T poll() {
            throw new UnsupportedOperationException("Operator does not support fusion");
        }

        @Override
        public boolean isEmpty() {
            throw new UnsupportedOperationException("Operator does not support fusion");
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("Operator does not support fusion");
        }

        @Override
        public int size() {
            throw new UnsupportedOperationException("Operator does not support fusion");
        }
    }
}

