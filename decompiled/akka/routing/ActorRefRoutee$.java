/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorRef;
import akka.routing.ActorRefRoutee;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class ActorRefRoutee$
extends AbstractFunction1<ActorRef, ActorRefRoutee>
implements Serializable {
    public static final ActorRefRoutee$ MODULE$ = new ActorRefRoutee$();

    @Override
    public final String toString() {
        return "ActorRefRoutee";
    }

    @Override
    public ActorRefRoutee apply(ActorRef ref) {
        return new ActorRefRoutee(ref);
    }

    public Option<ActorRef> unapply(ActorRefRoutee x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<ActorRef>(x$0.ref());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ActorRefRoutee$.class);
    }

    private ActorRefRoutee$() {
    }
}

