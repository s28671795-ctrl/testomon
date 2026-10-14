/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.InvalidMessageException;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class InvalidMessageException$
extends AbstractFunction1<String, InvalidMessageException>
implements Serializable {
    public static final InvalidMessageException$ MODULE$ = new InvalidMessageException$();

    @Override
    public final String toString() {
        return "InvalidMessageException";
    }

    @Override
    public InvalidMessageException apply(String message) {
        return new InvalidMessageException(message);
    }

    public Option<String> unapply(InvalidMessageException x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<String>(x$0.message());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(InvalidMessageException$.class);
    }

    private InvalidMessageException$() {
    }
}

