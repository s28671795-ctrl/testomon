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
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoContextWriteRestoringThreadLocals<T>
extends MonoOperator<T, T> {
    final Function<Context, Context> doOnContext;

    MonoContextWriteRestoringThreadLocals(Mono<? extends T> source2, Function<Context, Context> doOnContext) {
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
    implements InnerOperator<T, T>,
    Fuseable.QueueSubscription<T> {
        final CoreSubscriber<? super T> actual;
        final Context context;
        Subscription s;
        boolean done;

        ContextWriteRestoringThreadLocalsSubscriber(CoreSubscriber<? super T> actual, Context context) {
            this.actual = actual;
            this.context = context;
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
            this.done = true;
            try (ContextSnapshot.Scope ignored = ContextPropagation.setThreadLocals(this.actual.currentContext());){
                this.actual.onNext(t2);
                this.actual.onComplete();
            }
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.context);
                return;
            }
            this.done = true;
            try (ContextSnapshot.Scope ignored = ContextPropagation.setThreadLocals(this.actual.currentContext());){
                this.actual.onError(t2);
            }
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
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

