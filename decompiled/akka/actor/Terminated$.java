/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.Terminated;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class Terminated$
implements Serializable {
    public static final Terminated$ MODULE$ = new Terminated$();

    public final String toString() {
        return "Terminated";
    }

    public Terminated apply(ActorRef actor, boolean existenceConfirmed, boolean addressTerminated) {
        return new Terminated(actor, existenceConfirmed, addressTerminated);
    }

    public Option<ActorRef> unapply(Terminated x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<ActorRef>(x$0.actor());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Terminated$.class);
    }

    private Terminated$() {
    }
}

