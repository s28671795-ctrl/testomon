/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorRef;
import akka.routing.Listen;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class Listen$
extends AbstractFunction1<ActorRef, Listen>
implements Serializable {
    public static final Listen$ MODULE$ = new Listen$();

    @Override
    public final String toString() {
        return "Listen";
    }

    @Override
    public Listen apply(ActorRef listener) {
        return new Listen(listener);
    }

    public Option<ActorRef> unapply(Listen x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<ActorRef>(x$0.listener());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Listen$.class);
    }

    private Listen$() {
    }
}

