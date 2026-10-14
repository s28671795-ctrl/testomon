/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.actor.Address;
import akka.routing.ConsistentRoutee;
import akka.routing.Routee;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

public final class ConsistentRoutee$
extends AbstractFunction2<Routee, Address, ConsistentRoutee>
implements Serializable {
    public static final ConsistentRoutee$ MODULE$ = new ConsistentRoutee$();

    @Override
    public final String toString() {
        return "ConsistentRoutee";
    }

    @Override
    public ConsistentRoutee apply(Routee routee, Address selfAddress) {
        return new ConsistentRoutee(routee, selfAddress);
    }

    public Option<Tuple2<Routee, Address>> unapply(ConsistentRoutee x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Routee, Address>>(new Tuple2<Routee, Address>(x$0.routee(), x$0.selfAddress()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ConsistentRoutee$.class);
    }

    private ConsistentRoutee$() {
    }
}

