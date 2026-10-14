/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorKilledException;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class ActorKilledException$
extends AbstractFunction1<String, ActorKilledException>
implements Serializable {
    public static final ActorKilledException$ MODULE$ = new ActorKilledException$();

    @Override
    public final String toString() {
        return "ActorKilledException";
    }

    @Override
    public ActorKilledException apply(String message) {
        return new ActorKilledException(message);
    }

    public Option<String> unapply(ActorKilledException x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<String>(x$0.message());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ActorKilledException$.class);
    }

    private ActorKilledException$() {
    }
}

