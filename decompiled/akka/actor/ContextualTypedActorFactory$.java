/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorContext;
import akka.actor.ContextualTypedActorFactory;
import akka.actor.TypedActorExtension;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

public final class ContextualTypedActorFactory$
extends AbstractFunction2<TypedActorExtension, ActorContext, ContextualTypedActorFactory>
implements Serializable {
    public static final ContextualTypedActorFactory$ MODULE$ = new ContextualTypedActorFactory$();

    @Override
    public final String toString() {
        return "ContextualTypedActorFactory";
    }

    @Override
    public ContextualTypedActorFactory apply(TypedActorExtension typedActor, ActorContext actorFactory) {
        return new ContextualTypedActorFactory(typedActor, actorFactory);
    }

    public Option<Tuple2<TypedActorExtension, ActorContext>> unapply(ContextualTypedActorFactory x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<TypedActorExtension, ActorContext>>(new Tuple2<TypedActorExtension, ActorContext>(x$0.typedActor(), x$0.actorFactory()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ContextualTypedActorFactory$.class);
    }

    private ContextualTypedActorFactory$() {
    }
}

