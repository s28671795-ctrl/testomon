/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.observability;

import reactor.core.observability.SignalListener;
import reactor.core.publisher.SignalType;

public abstract class DefaultSignalListener<T>
implements SignalListener<T> {
    int fusionMode = 0;

    @Override
    public void doFirst() throws Throwable {
    }

    @Override
    public void doFinally(SignalType terminationType) throws Throwable {
    }

    @Override
    public void doOnSubscription() throws Throwable {
    }

    @Override
    public void doOnFusion(int negotiatedFusion) throws Throwable {
        this.fusionMode = negotiatedFusion;
    }

    protected int getFusionMode() {
        return this.fusionMode;
    }

    @Override
    public void doOnRequest(long requested) throws Throwable {
    }

    @Override
    public void doOnCancel() throws Throwable {
    }

    @Override
    public void doOnNext(T value) throws Throwable {
    }

    @Override
    public void doOnComplete() throws Throwable {
    }

    @Override
    public void doOnError(Throwable error) throws Throwable {
    }

    @Override
    public void doAfterComplete() throws Throwable {
    }

    @Override
    public void doAfterError(Throwable error) throws Throwable {
    }

    @Override
    public void doOnMalformedOnNext(T value) throws Throwable {
    }

    @Override
    public void doOnMalformedOnComplete() throws Throwable {
    }

    @Override
    public void doOnMalformedOnError(Throwable error) throws Throwable {
    }

    @Override
    public void handleListenerError(Throwable listenerError) {
    }
}

