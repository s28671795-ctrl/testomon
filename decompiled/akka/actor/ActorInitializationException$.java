/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorInitializationException;
import akka.actor.ActorRef;
import java.io.Serializable;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.ModuleSerializationProxy;

public final class ActorInitializationException$
implements Serializable {
    public static final ActorInitializationException$ MODULE$ = new ActorInitializationException$();

    public String akka$actor$ActorInitializationException$$enrichedMessage(ActorRef actor, String message) {
        if (actor == null) {
            return message;
        }
        return new StringBuilder(2).append(actor.path()).append(": ").append(message).toString();
    }

    public ActorInitializationException apply(ActorRef actor, String message, Throwable cause) {
        return new ActorInitializationException(actor, message, cause);
    }

    public ActorInitializationException apply(String message) {
        return new ActorInitializationException(null, message, null);
    }

    public Throwable apply$default$3() {
        return null;
    }

    public Option<Tuple3<ActorRef, String, Throwable>> unapply(ActorInitializationException ex) {
        return new Some<Tuple3<ActorRef, String, Throwable>>(new Tuple3<ActorRef, String, Throwable>(ex.getActor(), ex.getMessage(), ex.getCause()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ActorInitializationException$.class);
    }

    private ActorInitializationException$() {
    }
}

