/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.ScalaActorRef;

public final class package$ {
    public static final package$ MODULE$ = new package$();

    public final ScalaActorRef actorRef2Scala(ActorRef ref) {
        return (ScalaActorRef)((Object)ref);
    }

    public final ActorRef scala2ActorRef(ScalaActorRef ref) {
        return (ActorRef)((Object)ref);
    }

    private package$() {
    }
}

