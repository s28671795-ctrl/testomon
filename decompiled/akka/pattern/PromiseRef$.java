/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.actor.ActorRefProvider;
import akka.actor.ActorSystem;
import akka.actor.ExtendedActorSystem;
import akka.pattern.AskPromiseRef$;
import akka.pattern.PromiseRef;
import akka.pattern.PromiseRefImpl;
import akka.util.Timeout;
import scala.concurrent.Promise;

public final class PromiseRef$ {
    public static final PromiseRef$ MODULE$ = new PromiseRef$();

    public <T> PromiseRef<T> wrap(ActorRef actorRef2, Promise<T> promise) {
        return new PromiseRefImpl<T>(actorRef2, promise);
    }

    public PromiseRef<Object> apply(ActorSystem system, Timeout timeout2) {
        ActorRefProvider provider = ((ExtendedActorSystem)system).provider();
        return AskPromiseRef$.MODULE$.apply(provider, timeout2);
    }

    public PromiseRef<Object> apply(Timeout timeout2, ActorSystem system) {
        return this.apply(system, timeout2);
    }

    private PromiseRef$() {
    }
}

