/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.InnerProducer;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SourceProducer;
import reactor.core.publisher.SynchronousSink;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;
import reactor.util.context.ContextView;

final class FluxGenerate<T, S>
extends Flux<T>
implements Fuseable,
SourceProducer<T> {
    static final Callable EMPTY_CALLABLE = () -> null;
    final Callable<S> stateSupplier;
    final BiFunction<S, SynchronousSink<T>, S> generator;
    final Consumer<? super S> stateConsumer;

    FluxGenerate(Consumer<SynchronousSink<T>> generator) {
        this(EMPTY_CALLABLE, (state, sink2) -> {
            generator.accept((SynchronousSink)sink2);
            return null;
        });
    }

    FluxGenerate(Callable<S> stateSupplier, BiFunction<S, SynchronousSink<T>, S> generator) {
        this(stateSupplier, generator, s2 -> {});
    }

    FluxGenerate(Callable<S> stateSupplier, BiFunction<S, SynchronousSink<T>, S> generator, Consumer<? super S> stateConsumer) {
        this.stateSupplier = Objects.requireNonNull(stateSupplier, "stateSupplier");
        this.generator = Objects.requireNonNull(generator, "generator");
        this.stateConsumer = Objects.requireNonNull(stateConsumer, "stateConsumer");
    }

    @Override
    public void subscribe(CoreSubscriber<? super T> actual) {
        S state;
        try {
            state = this.stateSupplier.call();
        }
        catch (Throwable e) {
            Operators.error(actual, Operators.onOperatorError(e, actual.currentContext()));
            return;
        }
        actual.onSubscribe(new GenerateSubscription<T, S>(actual, state, this.generator, this.stateConsumer));
    }

    @Override
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return null;
    }

    static final class GenerateSubscription<T, S>
    implements Fuseable.QueueSubscription<T>,
    InnerProducer<T>,
    SynchronousSink<T> {
        final CoreSubscriber<? super T> actual;
        final BiFunction<S, SynchronousSink<T>, S> generator;
        final Consumer<? super S> stateConsumer;
        volatile boolean cancelled;
        S state;
        boolean terminate;
        boolean hasValue;
        boolean outputFused;
        T generatedValue;
        Throwable generatedError;
        volatile long requested;
        static final AtomicLongFieldUpdater<GenerateSubscription> REQUESTED = AtomicLongFieldUpdater.newUpdater(GenerateSubscription.class, "requested");

        GenerateSubscription(CoreSubscriber<? super T> actual, S state, BiFunction<S, SynchronousSink<T>, S> generator, Consumer<? super S> stateConsumer) {
            this.actual = actual;
            this.state = state;
            this.generator = generator;
            this.stateConsumer = stateConsumer;
        }

        @Override
        @Deprecated
        public Context currentContext() {
            return this.actual.currentContext();
        }

        @Override
        public ContextView contextView() {
            return this.actual.currentContext();
        }

        @Override
        @Nullable
        public Object scanUnsafe(Scannable.Attr key) {
            if (key == Scannable.Attr.TERMINATED) {
                return this.terminate;
            }
            if (key == Scannable.Attr.REQUESTED_FROM_DOWNSTREAM) {
                return this.requested;
            }
            if (key == Scannable.Attr.CANCELLED) {
                return this.cancelled;
            }
            if (key == Scannable.Attr.ERROR) {
                return this.generatedError;
            }
            if (key == Scannable.Attr.RUN_STYLE) {
                return Scannable.Attr.RunStyle.SYNC;
            }
            return InnerProducer.super.scanUnsafe(key);
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public void next(T t2) {
            if (this.terminate) {
                Operators.onNextDropped(t2, this.actual.currentContext());
                return;
            }
            if (this.hasValue) {
                this.error(new IllegalStateException("More than one call to onNext"));
                return;
            }
            if (t2 == null) {
                this.error(new NullPointerException("The generator produced a null value"));
                return;
            }
            this.hasValue = true;
            if (this.outputFused) {
                this.generatedValue = t2;
            } else {
                this.actual.onNext(t2);
            }
        }

        @Override
        public void error(Throwable e) {
            if (this.terminate) {
                return;
            }
            this.terminate = true;
            if (this.outputFused) {
                this.generatedError = e;
            } else {
                this.actual.onError(e);
            }
        }

        @Override
        public void complete() {
            if (this.terminate) {
                return;
            }
            this.terminate = true;
            if (!this.outputFused) {
                this.actual.onComplete();
            }
        }

        @Override
        public void request(long n) {
            if (Operators.validate(n) && Operators.addCap(REQUESTED, this, n) == 0L) {
                if (n == Long.MAX_VALUE) {
                    this.fastPath();
                } else {
                    this.slowPath(n);
                }
            }
        }

        void fastPath() {
            S s2 = this.state;
            BiFunction<S, SynchronousSink<S>, S> g2 = this.generator;
            while (true) {
                if (this.cancelled) {
                    this.cleanup(s2);
                    return;
                }
                try {
                    s2 = g2.apply(s2, this);
                }
                catch (Throwable e) {
                    this.cleanup(s2);
                    this.actual.onError(Operators.onOperatorError(e, this.actual.currentContext()));
                    return;
                }
                if (this.terminate || this.cancelled) {
                    this.cleanup(s2);
                    return;
                }
                if (!this.hasValue) {
                    this.cleanup(s2);
                    this.actual.onError(new IllegalStateException("The generator didn't call any of the SynchronousSink method"));
                    return;
                }
                this.hasValue = false;
            }
        }

        void slowPath(long n) {
            S s2 = this.state;
            long e = 0L;
            BiFunction<S, SynchronousSink<S>, S> g2 = this.generator;
            while (true) {
                if (e != n) {
                    if (this.cancelled) {
                        this.cleanup(s2);
                        return;
                    }
                    try {
                        s2 = g2.apply(s2, this);
                    }
                    catch (Throwable ex) {
                        this.cleanup(s2);
                        this.actual.onError(ex);
                        return;
                    }
                    if (this.terminate || this.cancelled) {
                        this.cleanup(s2);
                        return;
                    }
                    if (!this.hasValue) {
                        this.cleanup(s2);
                        this.actual.onError(new IllegalStateException("The generator didn't call any of the SynchronousSink method"));
                        return;
                    }
                    ++e;
                    this.hasValue = false;
                    continue;
                }
                n = this.requested;
                if (n != e) continue;
                this.state = s2;
                n = REQUESTED.addAndGet(this, -e);
                e = 0L;
                if (n == 0L) break;
            }
        }

        @Override
        public void cancel() {
            if (!this.cancelled) {
                this.cancelled = true;
                if (REQUESTED.getAndIncrement(this) == 0L) {
                    this.cleanup(this.state);
                }
            }
        }

        void cleanup(S s2) {
            try {
                this.state = null;
                this.stateConsumer.accept(s2);
            }
            catch (Throwable e) {
                Operators.onErrorDropped(e, this.actual.currentContext());
            }
        }

        @Override
        public int requestFusion(int requestedMode) {
            if ((requestedMode & 1) != 0 && (requestedMode & 4) == 0) {
                this.outputFused = true;
                return 1;
            }
            return 0;
        }

        @Override
        @Nullable
        public T poll() {
            S s2 = this.state;
            if (this.terminate) {
                this.cleanup(s2);
                Throwable e = this.generatedError;
                if (e != null) {
                    this.generatedError = null;
                    throw Exceptions.propagate(e);
                }
                return null;
            }
            try {
                s2 = this.generator.apply(s2, this);
            }
            catch (Throwable ex) {
                this.cleanup(s2);
                throw ex;
            }
            if (!this.hasValue) {
                this.cleanup(s2);
                if (!this.terminate) {
                    throw new IllegalStateException("The generator didn't call any of the SynchronousSink method");
                }
                Throwable e = this.generatedError;
                if (e != null) {
                    this.generatedError = null;
                    throw Exceptions.propagate(e);
                }
                return null;
            }
            T v = this.generatedValue;
            this.generatedValue = null;
            this.hasValue = false;
            this.state = s2;
            return v;
        }

        @Override
        public boolean isEmpty() {
            return this.terminate;
        }

        @Override
        public int size() {
            return this.isEmpty() ? 0 : -1;
        }

        @Override
        public void clear() {
            this.generatedError = null;
            this.generatedValue = null;
        }
    }
}

