/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.FutureRef;
import akka.pattern.FutureRefImpl;
import akka.pattern.PromiseRef$;
import akka.util.Timeout;
import scala.concurrent.Future;

public final class FutureRef$ {
    public static final FutureRef$ MODULE$ = new FutureRef$();

    public <T> FutureRef<T> wrap(ActorRef actorRef2, Future<T> future) {
        return new FutureRefImpl<T>(actorRef2, future);
    }

    public FutureRef<Object> apply(ActorSystem system, Timeout timeout2) {
        return PromiseRef$.MODULE$.apply(system, timeout2).toFutureRef();
    }

    public FutureRef<Object> apply(Timeout timeout2, ActorSystem system) {
        return this.apply(system, timeout2);
    }

    private FutureRef$() {
    }
}

