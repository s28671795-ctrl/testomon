/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.RemoveRoutee;
import akka.routing.Routee;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class RemoveRoutee$
extends AbstractFunction1<Routee, RemoveRoutee>
implements Serializable {
    public static final RemoveRoutee$ MODULE$ = new RemoveRoutee$();

    @Override
    public final String toString() {
        return "RemoveRoutee";
    }

    @Override
    public RemoveRoutee apply(Routee routee) {
        return new RemoveRoutee(routee);
    }

    public Option<Routee> unapply(RemoveRoutee x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Routee>(x$0.routee());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(RemoveRoutee$.class);
    }

    private RemoveRoutee$() {
    }
}

