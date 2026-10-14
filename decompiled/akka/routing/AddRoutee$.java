/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.AddRoutee;
import akka.routing.Routee;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.AbstractFunction1;
import scala.runtime.ModuleSerializationProxy;

public final class AddRoutee$
extends AbstractFunction1<Routee, AddRoutee>
implements Serializable {
    public static final AddRoutee$ MODULE$ = new AddRoutee$();

    @Override
    public final String toString() {
        return "AddRoutee";
    }

    @Override
    public AddRoutee apply(Routee routee) {
        return new AddRoutee(routee);
    }

    public Option<Routee> unapply(AddRoutee x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Routee>(x$0.routee());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AddRoutee$.class);
    }

    private AddRoutee$() {
    }
}

