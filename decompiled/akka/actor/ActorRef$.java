/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor$;
import akka.actor.ActorRef;
import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class ActorRef$
implements Serializable {
    public static final ActorRef$ MODULE$ = new ActorRef$();
    private static final ActorRef noSender = Actor$.MODULE$.noSender();

    public final ActorRef noSender() {
        return noSender;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ActorRef$.class);
    }

    private ActorRef$() {
    }
}

