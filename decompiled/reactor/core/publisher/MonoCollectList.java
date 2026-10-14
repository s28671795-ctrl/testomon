/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.ArrayList;
import java.util.List;
import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.MonoFromFluxOperator;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class MonoCollectList<T>
extends MonoFromFluxOperator<T, List<T>>
implements Fuseable {
    MonoCollectList(Flux<? extends T> source2) {
        super(source2);
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super List<T>> actual) {
        return new MonoCollectListSubscriber(actual);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class MonoCollectListSubscriber<T>
    extends Operators.BaseFluxToMonoOperator<T, List<T>> {
        List<T> list = new ArrayList<T>();
        boolean done;

        MonoCollectListSubscriber(CoreSubscriber<? super List<T>> actual) {
            super(actual);
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.done;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return !this.done && this.list == null;
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
            MonoCollectListSubscriber monoCollectListSubscriber = this;
            synchronized (monoCollectListSubscriber) {
                List<T> l = this.list;
                if (l != null) {
                    l.add(t2);
                    return;
                }
            }
            Operators.onDiscard(t2, this.actual.currentContext());
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        public void onError(Throwable t2) {
            List<T> l;
            if (this.done) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.done = true;
            MonoCollectListSubscriber monoCollectListSubscriber = this;
            synchronized (monoCollectListSubscriber) {
                l = this.list;
                this.list = null;
            }
            if (l == null) {
                return;
            }
            Operators.onDiscardMultiple(l, this.actual.currentContext());
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
            List<T> l;
            this.s.cancel();
            MonoCollectListSubscriber monoCollectListSubscriber = this;
            synchronized (monoCollectListSubscriber) {
                l = this.list;
                this.list = null;
            }
            if (l != null) {
                Operators.onDiscardMultiple(l, this.actual.currentContext());
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        @Override
        List<T> accumulatedValue() {
            List<T> l;
            MonoCollectListSubscriber monoCollectListSubscriber = this;
            synchronized (monoCollectListSubscriber) {
                l = this.list;
                this.list = null;
            }
            return l;
        }
    }
}

