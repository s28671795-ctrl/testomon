/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorRef;
import akka.stream.AbruptTerminationException;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class AbruptTerminationException$
extends AbstractFunction1<ActorRef, AbruptTerminationException>
implements Serializable {
    public static final AbruptTerminationException$ MODULE$ = new AbruptTerminationException$();

    @Override
    public final String toString() {
        return "AbruptTerminationException";
    }

    @Override
    public AbruptTerminationException apply(ActorRef actor) {
        return new AbruptTerminationException(actor);
    }

    public Option<ActorRef> unapply(AbruptTerminationException x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<ActorRef>(x$0.actor());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AbruptTerminationException$.class);
    }

    private AbruptTerminationException$() {
    }
}

