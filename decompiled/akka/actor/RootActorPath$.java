/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Address;
import akka.actor.RootActorPath;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

public final class RootActorPath$
extends AbstractFunction2<Address, String, RootActorPath>
implements Serializable {
    public static final RootActorPath$ MODULE$ = new RootActorPath$();

    public String $lessinit$greater$default$2() {
        return "/";
    }

    @Override
    public final String toString() {
        return "RootActorPath";
    }

    @Override
    public RootActorPath apply(Address address, String name) {
        return new RootActorPath(address, name);
    }

    public String apply$default$2() {
        return "/";
    }

    public Option<Tuple2<Address, String>> unapply(RootActorPath x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Address, String>>(new Tuple2<Address, String>(x$0.address(), x$0.name()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(RootActorPath$.class);
    }

    private RootActorPath$() {
    }
}

