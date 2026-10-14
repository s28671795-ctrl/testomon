/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.AmorphousShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.immutable.Seq;
import scala.runtime.AbstractFunction2;
import scala.runtime.ModuleSerializationProxy;

public final class AmorphousShape$
extends AbstractFunction2<Seq<Inlet<?>>, Seq<Outlet<?>>, AmorphousShape>
implements Serializable {
    public static final AmorphousShape$ MODULE$ = new AmorphousShape$();

    @Override
    public final String toString() {
        return "AmorphousShape";
    }

    @Override
    public AmorphousShape apply(Seq<Inlet<?>> inlets, Seq<Outlet<?>> outlets) {
        return new AmorphousShape(inlets, outlets);
    }

    public Option<Tuple2<Seq<Inlet<?>>, Seq<Outlet<?>>>> unapply(AmorphousShape x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some(new Tuple2(x$0.inlets(), x$0.outlets()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AmorphousShape$.class);
    }

    private AmorphousShape$() {
    }
}

