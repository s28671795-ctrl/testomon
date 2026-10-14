/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorRef;
import akka.stream.InvalidPartnerActorException;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.AbstractFunction3;
import scala.runtime.ModuleSerializationProxy;

public final class InvalidPartnerActorException$
extends AbstractFunction3<ActorRef, ActorRef, String, InvalidPartnerActorException>
implements Serializable {
    public static final InvalidPartnerActorException$ MODULE$ = new InvalidPartnerActorException$();

    @Override
    public final String toString() {
        return "InvalidPartnerActorException";
    }

    @Override
    public InvalidPartnerActorException apply(ActorRef expectedRef, ActorRef gotRef, String msg) {
        return new InvalidPartnerActorException(expectedRef, gotRef, msg);
    }

    public Option<Tuple3<ActorRef, ActorRef, String>> unapply(InvalidPartnerActorException x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<ActorRef, ActorRef, String>>(new Tuple3<ActorRef, ActorRef, String>(x$0.expectedRef(), x$0.gotRef(), x$0.msg()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(InvalidPartnerActorException$.class);
    }

    private InvalidPartnerActorException$() {
    }
}

