/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern.extended;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.pattern.ExplicitAskSupport;
import akka.util.Timeout;
import scala.Function1;
import scala.concurrent.Future;

public final class package$
implements ExplicitAskSupport {
    public static final package$ MODULE$ = new package$();

    static {
        ExplicitAskSupport.$init$(MODULE$);
    }

    @Override
    public ActorRef ask(ActorRef actorRef2) {
        return ExplicitAskSupport.ask$((ExplicitAskSupport)this, actorRef2);
    }

    @Override
    public Future<Object> ask(ActorRef actorRef2, Function1<ActorRef, Object> messageFactory, Timeout timeout2) {
        return ExplicitAskSupport.ask$((ExplicitAskSupport)this, actorRef2, messageFactory, timeout2);
    }

    @Override
    public Future<Object> ask(ActorRef actorRef2, Function1<ActorRef, Object> messageFactory, ActorRef sender, Timeout timeout2) {
        return ExplicitAskSupport.ask$((ExplicitAskSupport)this, actorRef2, messageFactory, sender, timeout2);
    }

    @Override
    public ActorSelection ask(ActorSelection actorSelection) {
        return ExplicitAskSupport.ask$((ExplicitAskSupport)this, actorSelection);
    }

    @Override
    public Future<Object> ask(ActorSelection actorSelection, Function1<ActorRef, Object> messageFactory, Timeout timeout2) {
        return ExplicitAskSupport.ask$((ExplicitAskSupport)this, actorSelection, messageFactory, timeout2);
    }

    @Override
    public Future<Object> ask(ActorSelection actorSelection, Function1<ActorRef, Object> messageFactory, ActorRef sender, Timeout timeout2) {
        return ExplicitAskSupport.ask$((ExplicitAskSupport)this, actorSelection, messageFactory, sender, timeout2);
    }

    private package$() {
    }
}

