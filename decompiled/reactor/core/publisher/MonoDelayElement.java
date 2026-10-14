/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Disposable;
import reactor.core.Disposables;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.core.scheduler.Scheduler;
import reactor.util.annotation.Nullable;

final class MonoDelayElement<T>
extends InternalMonoOperator<T, T> {
    final Scheduler timedScheduler;
    final long delay;
    final TimeUnit unit;

    MonoDelayElement(Mono<? extends T> source2, long delay, TimeUnit unit, Scheduler timedScheduler) {
        super(source2);
        this.delay = delay;
        this.unit = Objects.requireNonNull(unit, "unit");
        this.timedScheduler = Objects.requireNonNull(timedScheduler, "timedScheduler");
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) {
        return new DelayElementSubscriber<T>(actual, this.timedScheduler, this.delay, this.unit);
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_ON) {
            return this.timedScheduler;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.ASYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class DelayElementSubscriber<T>
    implements InnerOperator<T, T>,
    Fuseable,
    Fuseable.QueueSubscription<T>,
    Runnable {
        static final Disposable CANCELLED = Disposables.disposed();
        static final Disposable TERMINATED = Disposables.disposed();
        final CoreSubscriber<? super T> actual;
        final long delay;
        final Scheduler scheduler;
        final TimeUnit unit;
        Subscription s;
        T value;
        boolean done;
        volatile Disposable task;
        static final AtomicReferenceFieldUpdater<DelayElementSubscriber, Disposable> TASK = AtomicReferenceFieldUpdater.newUpdater(DelayElementSubscriber.class, Disposable.class, "task");

        DelayElementSubscriber(CoreSubscriber<? super T> actual, Scheduler scheduler, long delay, TimeUnit unit) {
            this.actual = actual;
            this.scheduler = scheduler;
            this.delay = delay;
            this.unit = unit;
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                Disposable task = this.task;
                return this.done && (task == TERMINATED || task == null && this.value == null);
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.task == CANCELLED;
            }
            if (key == Scannable.Attr.PREFETCH) {
                return 0;
            }
            if (key == Scannable.Attr.PARENT) {
                return this.s;
            }
            if (key == Scannable.Attr.RUN_ON) {
                return this.scheduler;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.ASYNC;
            }
            return InnerOperator.super.scanUnsafe(key);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
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
            if (this.done) {
                Operators.onNextDropped(t2, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.value = t2;
            try {
                Disposable currentTask = this.task;
                Disposable nextTask = this.scheduler.schedule(this, this.delay, this.unit);
                if (currentTask != null || !TASK.compareAndSet(this, null, nextTask)) {
                    this.value = null;
                    nextTask.dispose();
                    Operators.onDiscard(t2, this.actual.currentContext());
                }
            }
            catch (RejectedExecutionException ree) {
                this.value = null;
                Operators.onDiscard(t2, this.actual.currentContext());
                this.actual.onError(Operators.onRejectedExecution(ree, this, null, t2, this.actual.currentContext()));
            }
        }

        @Override
        public void run() {
            Disposable currentTask = this.task;
            if (currentTask == CANCELLED || !TASK.compareAndSet(this, currentTask, TERMINATED)) {
                return;
            }
            T value = this.value;
            this.value = null;
            this.actual.onNext(value);
            this.actual.onComplete();
        }

        @Override
        public void cancel() {
            Disposable task = this.task;
            if (task == CANCELLED || task == TERMINATED) {
                return;
            }
            if (TASK.compareAndSet(this, task, CANCELLED) && task != null) {
                task.dispose();
                T value = this.value;
                this.value = null;
                Operators.onDiscard(value, this.actual.currentContext());
                return;
            }
            this.s.cancel();
        }

        @Override
        public void request(long n) {
            this.s.request(n);
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
        public void onError(Throwable t2) {
            if (this.done) {
                Operators.onErrorDropped(t2, this.actual.currentContext());
                return;
            }
            this.done = true;
            this.actual.onError(t2);
        }

        @Override
        public T poll() {
            return null;
        }

        @Override
        public int requestFusion(int requestedMode) {
            return 0;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public void clear() {
        }
    }
}

