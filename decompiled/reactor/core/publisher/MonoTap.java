/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.publisher;

import reactor.core.CoreSubscriber;
import reactor.core.Fuseable;
import reactor.core.Scannable;
import reactor.core.observability.SignalListener;
import reactor.core.observability.SignalListenerFactory;
import reactor.core.publisher.ContextPropagation;
import reactor.core.publisher.FluxTap;
import reactor.core.publisher.InternalMonoOperator;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;
import reactor.util.annotation.Nullable;

final class MonoTap<T, STATE>
extends InternalMonoOperator<T, T> {
    final SignalListenerFactory<T, STATE> tapFactory;
    final STATE commonTapState;

    MonoTap(Mono<? extends T> source2, SignalListenerFactory<T, STATE> tapFactory) {
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
            return new FluxTap.TapConditionalSubscriber<T>((Fuseable.ConditionalSubscriber)actual, signalListener);
        }
        return new FluxTap.TapSubscriber<T>(actual, signalListener);
    }

    @Override
    @Nullable
    public Object scanUnsafe(Scannable.Attr key) {
        if (key == Scannable.Attr.PREFETCH) {
            return -1;
        }
        if (key == Scannable.Attr.RUN_STYLE) {
            return Scannable.Attr.RunStyle.SYNC;
        }
        return super.scanUnsafe(key);
    }
}

