/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorIdentity;
import akka.actor.ActorRef;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

public final class ActorIdentity$
extends AbstractFunction2<Object, Option<ActorRef>, ActorIdentity>
implements Serializable {
    public static final ActorIdentity$ MODULE$ = new ActorIdentity$();

    @Override
    public final String toString() {
        return "ActorIdentity";
    }

    @Override
    public ActorIdentity apply(Object correlationId, Option<ActorRef> ref) {
        return new ActorIdentity(correlationId, ref);
    }

    public Option<Tuple2<Object, Option<ActorRef>>> unapply(ActorIdentity x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Object, Option<ActorRef>>>(new Tuple2<Object, Option<ActorRef>>(x$0.correlationId(), x$0.ref()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ActorIdentity$.class);
    }

    private ActorIdentity$() {
    }
}

