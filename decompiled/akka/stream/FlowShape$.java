/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.ModuleSerializationProxy;

public final class FlowShape$
implements Serializable {
    public static final FlowShape$ MODULE$ = new FlowShape$();

    public <I, O> FlowShape<I, O> of(Inlet<I> inlet, Outlet<O> outlet) {
        return new FlowShape<I, O>(inlet, outlet);
    }

    public <I, O> FlowShape<I, O> apply(Inlet<I> in, Outlet<O> out) {
        return new FlowShape<I, O>(in, out);
    }

    public <I, O> Option<Tuple2<Inlet<I>, Outlet<O>>> unapply(FlowShape<I, O> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Inlet<I>, Outlet<O>>>(new Tuple2<Inlet<I>, Outlet<O>>(x$0.in(), x$0.out()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(FlowShape$.class);
    }

    private FlowShape$() {
    }
}

