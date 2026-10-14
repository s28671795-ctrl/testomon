/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorNotFound;
import akka.actor.ActorSelection;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class ActorNotFound$
extends AbstractFunction1<ActorSelection, ActorNotFound>
implements Serializable {
    public static final ActorNotFound$ MODULE$ = new ActorNotFound$();

    @Override
    public final String toString() {
        return "ActorNotFound";
    }

    @Override
    public ActorNotFound apply(ActorSelection selection) {
        return new ActorNotFound(selection);
    }

    public Option<ActorSelection> unapply(ActorNotFound x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<ActorSelection>(x$0.selection());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ActorNotFound$.class);
    }

    private ActorNotFound$() {
    }
}

