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
import reactor.core.publisher.InnerOperator;
import reactor.core.publisher.InternalFluxOperator;
import reactor.core.publisher.Operators;
import reactor.core.publisher.SignalType;
import reactor.util.annotation.Nullable;
import reactor.util.context.Context;

final class FluxTap<T, STATE>
extends InternalFluxOperator<T, T> {
    final SignalListenerFactory<T, STATE> tapFactory;
    final STATE commonTapState;

    FluxTap(Flux<? extends T> source2, SignalListenerFactory<T, STATE> tapFactory) {
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
            return new TapConditionalSubscriber<T>((Fuseable.ConditionalSubscriber)actual, signalListener);
        }
        return new TapSubscriber<T>(actual, signalListener);
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }

    static final class TapConditionalSubscriber<T>
    extends TapSubscriber<T>
    implements Fuseable.ConditionalSubscriber<T> {
        final Fuseable.ConditionalSubscriber<? super T> actualConditional;

        public TapConditionalSubscriber(Fuseable.ConditionalSubscriber<? super T> actual, SignalListener<T> signalListener) {
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

    static class TapSubscriber<T>
    implements InnerOperator<T, T> {
        final CoreSubscriber<? super T> actual;
        final Context context;
        final SignalListener<T> listener;
        boolean done;
        Subscription s;

        TapSubscriber(CoreSubscriber<? super T> actual, SignalListener<T> signalListener) {
            Context ctx;
            this.actual = actual;
            this.listener = signalListener;
            try {
                ctx = signalListener.addToContext(actual.currentContext());
            }
            catch (Throwable e) {
                signalListener.handleListenerError(new IllegalStateException("Unable to augment tap Context at construction via addToContext", e));
                ctx = actual.currentContext();
            }
            this.context = ctx;
        }

        @Override
        public CoreSubscriber<? super T> actual() {
            return this.actual;
        }

        @Override
        public Context currentContext() {
            return this.context;
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

        protected void handleListenerErrorPreSubscription(Throwable listenerError, Subscription toCancel) {
            toCancel.cancel();
            this.listener.handleListenerError(listenerError);
            Operators.error(this.actual, listenerError);
        }

        protected void handleListenerErrorAndTerminate(Throwable listenerError) {
            this.s.cancel();
            this.listener.handleListenerError(listenerError);
            this.actual.onError(listenerError);
        }

        protected void handleListenerErrorMultipleAndTerminate(Throwable listenerError, Throwable originalError) {
            this.s.cancel();
            this.listener.handleListenerError(listenerError);
            RuntimeException multiple = Exceptions.multiple(listenerError, originalError);
            this.actual.onError(multiple);
        }

        protected void handleListenerErrorPostTermination(Throwable listenerError) {
            this.listener.handleListenerError(listenerError);
            Operators.onErrorDropped(listenerError, this.actual.currentContext());
        }

        @Override
        public void onSubscribe(Subscription s2) {
            if (Operators.validate(this.s, s2)) {
                this.s = s2;
                try {
                    this.listener.doOnSubscription();
                }
                catch (Throwable observerError) {
                    this.handleListenerErrorPreSubscription(observerError, s2);
                    return;
                }
                this.actual.onSubscribe(this);
            }
        }

        @Override
        public void onNext(T t2) {
            if (this.done) {
                try {
                    this.listener.doOnMalformedOnNext(t2);
                }
                catch (Throwable observerError) {
                    this.handleListenerErrorPostTermination(observerError);
                }
                finally {
                    Operators.onNextDropped(t2, this.currentContext());
                }
                return;
            }
            try {
                this.listener.doOnNext(t2);
            }
            catch (Throwable observerError) {
                this.handleListenerErrorAndTerminate(observerError);
                return;
            }
            this.actual.onNext(t2);
        }

        @Override
        public void onError(Throwable t2) {
            if (this.done) {
                try {
                    this.listener.doOnMalformedOnError(t2);
                }
                catch (Throwable observerError) {
                    this.handleListenerErrorPostTermination(observerError);
                }
                finally {
                    Operators.onErrorDropped(t2, this.currentContext());
                }
                return;
            }
            this.done = true;
            try {
                this.listener.doOnError(t2);
            }
            catch (Throwable observerError) {
                this.handleListenerErrorMultipleAndTerminate(observerError, t2);
                return;
            }
            this.actual.onError(t2);
            try {
                this.listener.doAfterError(t2);
                this.listener.doFinally(SignalType.ON_ERROR);
            }
            catch (Throwable observerError) {
                this.handleListenerErrorPostTermination(observerError);
            }
        }

        @Override
        public void onComplete() {
            if (this.done) {
                try {
                    this.listener.doOnMalformedOnComplete();
                }
                catch (Throwable observerError) {
                    this.handleListenerErrorPostTermination(observerError);
                }
                return;
            }
            this.done = true;
            try {
                this.listener.doOnComplete();
            }
            catch (Throwable observerError) {
                this.handleListenerErrorAndTerminate(observerError);
                return;
            }
            this.actual.onComplete();
            try {
                this.listener.doAfterComplete();
                this.listener.doFinally(SignalType.ON_COMPLETE);
            }
            catch (Throwable observerError) {
                this.handleListenerErrorPostTermination(observerError);
            }
        }

        @Override
        public void request(long n) {
            if (Operators.validate(n)) {
                try {
                    this.listener.doOnRequest(n);
                }
                catch (Throwable observerError) {
                    this.handleListenerErrorAndTerminate(observerError);
                    return;
                }
                this.s.request(n);
            }
        }

        @Override
        public void cancel() {
            try {
                this.listener.doOnCancel();
            }
            catch (Throwable observerError) {
                this.handleListenerErrorAndTerminate(observerError);
                return;
            }
            try {
                this.s.cancel();
            }
            finally {
                try {
                    this.listener.doFinally(SignalType.CANCEL);
                }
                catch (Throwable observerError) {
                    this.handleListenerErrorAndTerminate(observerError);
                }
            }
        }
    }
}

