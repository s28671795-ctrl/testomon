/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.UnhandledMessage;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.AbstractFunction3;
import scala.runtime.ModuleSerializationProxy;

public final class UnhandledMessage$
extends AbstractFunction3<Object, ActorRef, ActorRef, UnhandledMessage>
implements Serializable {
    public static final UnhandledMessage$ MODULE$ = new UnhandledMessage$();

    @Override
    public final String toString() {
        return "UnhandledMessage";
    }

    @Override
    public UnhandledMessage apply(Object message, ActorRef sender, ActorRef recipient) {
        return new UnhandledMessage(message, sender, recipient);
    }

    public Option<Tuple3<Object, ActorRef, ActorRef>> unapply(UnhandledMessage x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<Object, ActorRef, ActorRef>>(new Tuple3<Object, ActorRef, ActorRef>(x$0.message(), x$0.sender(), x$0.recipient()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(UnhandledMessage$.class);
    }

    private UnhandledMessage$() {
    }
}

