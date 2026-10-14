/*
 * Decompiled with CFR 0.152.
 */
package reactor.core.observability;

import reactor.core.publisher.SignalType;
import reactor.util.context.Context;

public interface SignalListener<T> {
    public void doFirst() throws Throwable;

    public void doFinally(SignalType var1) throws Throwable;

    public void doOnSubscription() throws Throwable;

    public void doOnFusion(int var1) throws Throwable;

    public void doOnRequest(long var1) throws Throwable;

    public void doOnCancel() throws Throwable;

    public void doOnNext(T var1) throws Throwable;

    public void doOnComplete() throws Throwable;

    public void doOnError(Throwable var1) throws Throwable;

    public void doAfterComplete() throws Throwable;

    public void doAfterError(Throwable var1) throws Throwable;

    public void doOnMalformedOnNext(T var1) throws Throwable;

    public void doOnMalformedOnError(Throwable var1) throws Throwable;

    public void doOnMalformedOnComplete() throws Throwable;

    public void handleListenerError(Throwable var1);

    default public Context addToContext(Context originalContext) {
        return originalContext;
    }
}

