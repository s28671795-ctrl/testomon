/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.ActorRef$;
import akka.actor.Dropped;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple4;
import scala.runtime.ModuleSerializationProxy;

public final class Dropped$
implements Serializable {
    public static final Dropped$ MODULE$ = new Dropped$();

    public Dropped apply(Object message, String reason, ActorRef recipient) {
        return new Dropped(message, reason, ActorRef$.MODULE$.noSender(), recipient);
    }

    public Dropped apply(Object message, String reason, ActorRef sender, ActorRef recipient) {
        return new Dropped(message, reason, sender, recipient);
    }

    public Option<Tuple4<Object, String, ActorRef, ActorRef>> unapply(Dropped x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple4<Object, String, ActorRef, ActorRef>>(new Tuple4<Object, String, ActorRef, ActorRef>(x$0.message(), x$0.reason(), x$0.sender(), x$0.recipient()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Dropped$.class);
    }

    private Dropped$() {
    }
}

