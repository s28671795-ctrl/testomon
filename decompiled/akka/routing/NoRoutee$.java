/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorRef;
import akka.routing.Routee;

public final class NoRoutee$
implements Routee {
    public static final NoRoutee$ MODULE$ = new NoRoutee$();

    @Override
    public void send(Object message, ActorRef sender) {
    }

    private NoRoutee$() {
    }
}

