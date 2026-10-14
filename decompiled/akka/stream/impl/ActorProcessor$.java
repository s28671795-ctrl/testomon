/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.stream.impl.ActorProcessor;
import akka.stream.impl.ExposedPublisher;

@InternalApi
public final class ActorProcessor$ {
    public static final ActorProcessor$ MODULE$ = new ActorProcessor$();

    public <I, O> ActorProcessor<I, O> apply(ActorRef impl) {
        ActorProcessor p = new ActorProcessor(impl);
        ExposedPublisher x$1 = new ExposedPublisher(p);
        ActorRef x$2 = impl.$bang$default$2(x$1);
        impl.$bang(x$1, x$2);
        return p;
    }

    private ActorProcessor$() {
    }
}

