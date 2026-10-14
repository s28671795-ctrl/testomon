/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorRef;
import akka.annotation.InternalApi;
import akka.stream.impl.ActorPublisher;
import akka.stream.impl.ExposedPublisher;
import scala.Some;

@InternalApi
public final class ActorPublisher$ {
    public static final ActorPublisher$ MODULE$ = new ActorPublisher$();
    private static final String NormalShutdownReasonMessage = "Cannot subscribe to shut-down Publisher";
    private static final Throwable NormalShutdownReason = new ActorPublisher.NormalShutdownException();
    private static final Some<Throwable> SomeNormalShutdownReason = new Some<Throwable>(MODULE$.NormalShutdownReason());

    public String NormalShutdownReasonMessage() {
        return NormalShutdownReasonMessage;
    }

    public Throwable NormalShutdownReason() {
        return NormalShutdownReason;
    }

    public Some<Throwable> SomeNormalShutdownReason() {
        return SomeNormalShutdownReason;
    }

    public <T> ActorPublisher<T> apply(ActorRef impl) {
        ActorPublisher<Object> a = new ActorPublisher<Object>(impl);
        ExposedPublisher x$1 = new ExposedPublisher(a);
        ActorRef x$2 = impl.$bang$default$2(x$1);
        impl.$bang(x$1, x$2);
        return a;
    }

    private ActorPublisher$() {
    }
}

