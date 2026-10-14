/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.DeathPactException;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class DeathPactException$
extends AbstractFunction1<ActorRef, DeathPactException>
implements Serializable {
    public static final DeathPactException$ MODULE$ = new DeathPactException$();

    @Override
    public final String toString() {
        return "DeathPactException";
    }

    @Override
    public DeathPactException apply(ActorRef dead) {
        return new DeathPactException(dead);
    }

    public Option<ActorRef> unapply(DeathPactException x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<ActorRef>(x$0.dead());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DeathPactException$.class);
    }

    private DeathPactException$() {
    }
}

