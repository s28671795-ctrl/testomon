/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.DeadLetter;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.AbstractFunction3;
import scala.runtime.ModuleSerializationProxy;

public final class DeadLetter$
extends AbstractFunction3<Object, ActorRef, ActorRef, DeadLetter>
implements Serializable {
    public static final DeadLetter$ MODULE$ = new DeadLetter$();

    @Override
    public final String toString() {
        return "DeadLetter";
    }

    @Override
    public DeadLetter apply(Object message, ActorRef sender, ActorRef recipient) {
        return new DeadLetter(message, sender, recipient);
    }

    public Option<Tuple3<Object, ActorRef, ActorRef>> unapply(DeadLetter x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<Object, ActorRef, ActorRef>>(new Tuple3<Object, ActorRef, ActorRef>(x$0.message(), x$0.sender(), x$0.recipient()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(DeadLetter$.class);
    }

    private DeadLetter$() {
    }
}

