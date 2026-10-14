/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.Actor;
import akka.event.ActorWithLogClass;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

public final class ActorWithLogClass$
extends AbstractFunction2<Actor, Class<?>, ActorWithLogClass>
implements Serializable {
    public static final ActorWithLogClass$ MODULE$ = new ActorWithLogClass$();

    @Override
    public final String toString() {
        return "ActorWithLogClass";
    }

    @Override
    public ActorWithLogClass apply(Actor actor, Class<?> logClass) {
        return new ActorWithLogClass(actor, logClass);
    }

    public Option<Tuple2<Actor, Class<?>>> unapply(ActorWithLogClass x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some(new Tuple2(x$0.actor(), x$0.logClass()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ActorWithLogClass$.class);
    }

    private ActorWithLogClass$() {
    }
}

