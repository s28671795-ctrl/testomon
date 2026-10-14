/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.MonoFromFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class MonoCollect<T, R>
extends MonoFromFluxOperator<T, R>
implements Fuseable {
    final Supplier<R> supplier;
    final BiConsumer<? super R, ? super T> action;

    MonoCollect(Flux<? extends T> source2, Supplier<R> supplier, BiConsumer<? super R, ? super T> action) {
        super(source2);
        this.supplier = Objects.requireNonNull(supplier, "supplier");
        this.action = Objects.requireNonNull(action);
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super R> actual) {
        R container = Objects.requireNonNull(this.supplier.get(), "The supplier returned a null container");
        return new CollectSubscriber<T, R>(actual, this.action, container);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class CollectSubscriber<T, R>
    extends Operators.BaseFluxToMonoOperator<T, R> {
        final BiConsumer<? super R, ? super T> action;
        R container;
        boolean done;

        CollectSubscriber(CoreSubscriber<? super R> actual, BiConsumer<? super R, ? super T> action, R container) {
            super(actual);
            this.action = action;
            this.container = container;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            return super.scanUnsafe(key);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onNext(T t2) {
            if (this.done) {
                Operators.onNextDropped(t2, this.actual.currentContext());
                return;
            }
            try {
                CollectSubscriber collectSubscriber = this;
                synchronized (collectSubscriber) {
                    R c = this.container;
                    if (c != null) {
                        this.action.accept(c, t2);
                        return;
                    }
                }
                Operators.onDiscard(t2, this.actual.currentContext());
            }
            catch (Throwable e) {
                Context ctx = this.actual.currentContext();
                Operators.onDiscard(t2, ctx);
                this.onError(Operators.onOperatorError(this.s, e, t2, ctx));
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onError(Throwable t2) {
            R c;
            if (this.done) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.done = true;
            CollectSubscriber collectSubscriber = this;
            synchronized (collectSubscriber) {
                c = this.container;
                this.container = null;
            }
            if (c == null) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.discard(c);
            this.actual.onError(t2);
        }

        @Override
        public void onComplete() {
            if (this.done) {
                return;
            }
            this.done = true;
            this.completePossiblyEmpty();
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void cancel() {
            R c;
            super.cancel();
            CollectSubscriber collectSubscriber = this;
            synchronized (collectSubscriber) {
                c = this.container;
                this.container = null;
            }
            if (c != null) {
                this.discard(c);
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        R accumulatedValue() {
            R c;
            CollectSubscriber collectSubscriber = this;
            synchronized (collectSubscriber) {
                c = this.container;
                this.container = null;
            }
            return c;
        }

        void discard(R v) {
            if (v instanceof Collection) {
                Collection c = (Collection)v;
                Operators.onDiscardMultiple(c, this.actual.currentContext());
            } else {
                Operators.onDiscard(v, this.actual.currentContext());
            }
        }
    }
}

