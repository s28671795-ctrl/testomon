/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.ActorRef;
import akka.routing.Deafen;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class Deafen$
extends AbstractFunction1<ActorRef, Deafen>
implements Serializable {
    public static final Deafen$ MODULE$ = new Deafen$();

    @Override
    public final String toString() {
        return "Deafen";
    }

    @Override
    public Deafen apply(ActorRef listener) {
        return new Deafen(listener);
    }

    public Option<ActorRef> unapply(Deafen x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<ActorRef>(x$0.listener());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Deafen$.class);
    }

    private Deafen$() {
    }
}

