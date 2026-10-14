/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import org.reactivestreams.Subscription;
import reactor.core.CoreSubscriber;
import reactor.core.Exceptions;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.observability.SignalListener;
import reactor.core.observability.SignalListenerFactory;
import reactor.core.publisher.ContextPropagation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxTap;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SignalType;
import reactor.util.annotation.Nullable;

final class FluxTapFuseable<T, STATE>
extends InternalFluxOperator<T, T>
implements Fuseable {
    final SignalListenerFactory<T, STATE> tapFactory;
    final STATE commonTapState;

    FluxTapFuseable(Flux<? extends T> source2, SignalListenerFactory<T, STATE> tapFactory) {
        super(source2);
        this.tapFactory = tapFactory;
        this.commonTapState = tapFactory.initializePublisherState(source2);
    }

    @Override
    public CoreSubscriber<? super T> subscribeOrReturn(CoreSubscriber<? super T> actual) throws Throwable {
        SignalListener<T> signalListener;
        try {
            signalListener = this.tapFactory.createListener(this.source, actual.currentContext().readOnly(), this.commonTapState);
        }
        catch (Throwable generatorError) {
            Operators.error(actual, generatorError);
            return null;
        }
        signalListener = ContextPropagation.contextRestoreForTap(signalListener, actual::currentContext);
        try {
            signalListener.doFirst();
        }
        catch (Throwable listenerError) {
            signalListener.handleListenerError(listenerError);
            Operators.error(actual, listenerError);
            return null;
        }
        if (actual instanceof Fuseable.ConditionalSubscriber) {
            return new TapConditionalFuseableSubscriber<T>((Fuseable.ConditionalSubscriber)actual, signalListener);
        }
        return new TapFuseableSubscriber<T>(actual, signalListener);
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class TapConditionalFuseableSubscriber<T>
    extends TapFuseableSubscriber<T>
    implements Fuseable.ConditionalSubscriber<T> {
        final Fuseable.ConditionalSubscriber<? super T> actualConditional;

        public TapConditionalFuseableSubscriber(Fuseable.ConditionalSubscriber<? super T> actual, SignalListener<T> signalListener) {
            super(actual, signalListener);
            this.actualConditional = actual;
        }

        @Override
        public boolean tryOnNext(T t2) {
            if (this.actualConditional.tryOnNext(t2)) {
                try {
                    this.listener.doOnNext(t2);
                }
                catch (Throwable listenerError) {
                    this.handleListenerErrorAndTerminate(listenerError);
                }
                return true;
            }
            return false;
        }
    }

    static class TapFuseableSubscriber<T>
    extends FluxTap.TapSubscriber<T>
    implements Fuseable.QueueSubscription<T> {
        int mode;
        Fuseable.QueueSubscription<T> qs;

        TapFuseableSubscriber(CoreSubscriber<? super T> actual, SignalListener<T> signalListener) {
            super(actual, signalListener);
        }

        protected RuntimeException handleObserverErrorInPoll(Throwable listenerError) {
            this.qs.cancel();
            this.listener.handleListenerError(listenerError);
            return Exceptions.propagate(listenerError);
        }

        protected RuntimeException handleObserverErrorMultipleInPoll(Throwable listenerError, RuntimeException pollError) {
            this.qs.cancel();
            this.listener.handleListenerError(listenerError);
            return Exceptions.multiple(listenerError, pollError);
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                if (!(s2 instanceof Fuseable.QueueSubscription)) {
                    this.handleListenerErrorPreSubscription(new IllegalStateException("Fuseable subscriber but no QueueSubscription"), s2);
                    return;
                }
                this.s = s2;
                this.qs = (Fuseable.QueueSubscription)s2;
                try {
                    this.listener.doOnSubscription();
                }
                catch (Throwable listenerError) {
                    this.handleListenerErrorPreSubscription(listenerError, s2);
                    return;
                }
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public int requestFusion(int requestedMode) {
            if (this.qs == null) {
                this.mode = 0;
                return 0;
            }
            this.mode = this.qs.requestFusion(requestedMode);
            try {
                this.listener.doOnFusion(this.mode);
                return this.mode;
            }
            catch (Throwable listenerError) {
                if (this.mode == 2 || this.mode == 0) {
                    this.handleListenerErrorAndTerminate(listenerError);
                    return 0;
                }
                this.listener.handleListenerError(listenerError);
                return this.mode;
            }
        }

        @Override
        public void onNext(T t2) {
            if (this.mode == 2) {
                this.actual.onNext(null);
                return;
            }
            super.onNext(t2);
        }

        @Override
        @Nullable
        public T poll() {
            Object v;
            if (this.qs == null) {
                return null;
            }
            try {
                v = this.qs.poll();
            }
            catch (RuntimeException pollError) {
                try {
                    this.listener.doOnError(pollError);
                }
                catch (Throwable listenerError) {
                    throw this.handleObserverErrorMultipleInPoll(listenerError, pollError);
                }
                try {
                    this.listener.doFinally(SignalType.ON_ERROR);
                }
                catch (Throwable listenerError) {
                    throw this.handleObserverErrorMultipleInPoll(listenerError, pollError);
                }
                throw pollError;
            }
            if (v == null && (this.done || this.mode == 1)) {
                try {
                    this.listener.doOnComplete();
                }
                catch (Throwable listenerError) {
                    throw this.handleObserverErrorInPoll(listenerError);
                }
                try {
                    this.listener.doFinally(SignalType.ON_COMPLETE);
                }
                catch (Throwable listenerError) {
                    throw this.handleObserverErrorInPoll(listenerError);
                }
                return null;
            }
            if (v != null) {
                try {
                    this.listener.doOnNext(v);
                }
                catch (Throwable listenerError) {
                    if (this.mode == 1) {
                        throw this.handleObserverErrorInPoll(listenerError);
                    }
                    this.handleListenerErrorAndTerminate(listenerError);
                    return null;
                }
            }
            return (T)v;
        }

        @Override
        public int size() {
            return this.qs == null ? 0 : this.qs.size();
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
    }
}

