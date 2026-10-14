/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.Broadcast;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class Broadcast$
extends AbstractFunction1<Object, Broadcast>
implements Serializable {
    public static final Broadcast$ MODULE$ = new Broadcast$();

    @Override
    public final String toString() {
        return "Broadcast";
    }

    @Override
    public Broadcast apply(Object message) {
        return new Broadcast(message);
    }

    public Option<Object> unapply(Broadcast x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Object>(x$0.message());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Broadcast$.class);
    }

    private Broadcast$() {
    }
}

