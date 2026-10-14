/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.DeadLetterSuppression;
import akka.actor.SuppressedDeadLetter;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.AbstractFunction3;
import scala.runtime.ModuleSerializationProxy;

public final class SuppressedDeadLetter$
extends AbstractFunction3<DeadLetterSuppression, ActorRef, ActorRef, SuppressedDeadLetter>
implements Serializable {
    public static final SuppressedDeadLetter$ MODULE$ = new SuppressedDeadLetter$();

    @Override
    public final String toString() {
        return "SuppressedDeadLetter";
    }

    @Override
    public SuppressedDeadLetter apply(DeadLetterSuppression message, ActorRef sender, ActorRef recipient) {
        return new SuppressedDeadLetter(message, sender, recipient);
    }

    public Option<Tuple3<DeadLetterSuppression, ActorRef, ActorRef>> unapply(SuppressedDeadLetter x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<DeadLetterSuppression, ActorRef, ActorRef>>(new Tuple3<DeadLetterSuppression, ActorRef, ActorRef>(x$0.message(), x$0.sender(), x$0.recipient()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(SuppressedDeadLetter$.class);
    }

    private SuppressedDeadLetter$() {
    }
}

