/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.InvalidActorNameException;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class InvalidActorNameException$
extends AbstractFunction1<String, InvalidActorNameException>
implements Serializable {
    public static final InvalidActorNameException$ MODULE$ = new InvalidActorNameException$();

    @Override
    public final String toString() {
        return "InvalidActorNameException";
    }

    @Override
    public InvalidActorNameException apply(String message) {
        return new InvalidActorNameException(message);
    }

    public Option<String> unapply(InvalidActorNameException x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<String>(x$0.message());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(InvalidActorNameException$.class);
    }

    private InvalidActorNameException$() {
    }
}

