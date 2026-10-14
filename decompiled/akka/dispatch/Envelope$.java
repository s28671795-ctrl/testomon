/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.Actor$;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.InvalidMessageException$;
import akka.dispatch.Envelope;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.ModuleSerializationProxy;

public final class Envelope$
implements Serializable {
    public static final Envelope$ MODULE$ = new Envelope$();

    public Envelope apply(Object message, ActorRef sender, ActorSystem system) {
        if (message == null) {
            if (sender == Actor$.MODULE$.noSender()) {
                throw InvalidMessageException$.MODULE$.apply("Message is null.");
            }
            throw InvalidMessageException$.MODULE$.apply(new StringBuilder(29).append("Message sent from [").append(sender).append("] is null.").toString());
        }
        return new Envelope(message, sender != Actor$.MODULE$.noSender() ? sender : system.deadLetters());
    }

    public Envelope apply(Object message, ActorRef sender) {
        return new Envelope(message, sender);
    }

    public Option<Tuple2<Object, ActorRef>> unapply(Envelope x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Object, ActorRef>>(new Tuple2<Object, ActorRef>(x$0.message(), x$0.sender()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Envelope$.class);
    }

    private Envelope$() {
    }
}

