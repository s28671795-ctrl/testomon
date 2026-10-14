/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.AbstractActor;
import scala.PartialFunction$;

public final class AbstractActor$ {
    public static final AbstractActor$ MODULE$ = new AbstractActor$();
    private static final AbstractActor.Receive emptyBehavior = new AbstractActor.Receive(PartialFunction$.MODULE$.empty());

    public final AbstractActor.Receive emptyBehavior() {
        return emptyBehavior;
    }

    private AbstractActor$() {
    }
}

