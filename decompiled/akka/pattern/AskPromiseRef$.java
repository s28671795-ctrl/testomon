/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRefProvider;
import akka.pattern.AskPromiseRef;
import akka.pattern.PromiseActorRef;
import akka.pattern.PromiseActorRef$;
import akka.util.Timeout;

public final class AskPromiseRef$ {
    public static final AskPromiseRef$ MODULE$ = new AskPromiseRef$();

    public AskPromiseRef apply(ActorRefProvider provider, Timeout timeout2) {
        if (timeout2.duration().length() > 0L) {
            PromiseActorRef promiseActorRef = PromiseActorRef$.MODULE$.apply(provider, timeout2, "unknown", "unknown", "deadLetters", provider.deadLetters(), PromiseActorRef$.MODULE$.apply$default$7());
            return new AskPromiseRef(promiseActorRef);
        }
        throw new IllegalArgumentException(new StringBuilder(42).append("Timeout length must not be negative, was: ").append(timeout2).toString());
    }

    private AskPromiseRef$() {
    }
}

