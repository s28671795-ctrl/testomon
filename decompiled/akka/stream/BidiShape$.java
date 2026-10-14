/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.BidiShape;
import akka.stream.FlowShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple4;
import scala.runtime.ModuleSerializationProxy;

public final class BidiShape$
implements Serializable {
    public static final BidiShape$ MODULE$ = new BidiShape$();

    public <I1, O1, I2, O2> BidiShape<I1, O1, I2, O2> fromFlows(FlowShape<I1, O1> top, FlowShape<I2, O2> bottom) {
        return new BidiShape<I1, O1, I2, O2>(top.in(), top.out(), bottom.in(), bottom.out());
    }

    public <In1, Out1, In2, Out2> BidiShape<In1, Out1, In2, Out2> of(Inlet<In1> in1, Outlet<Out1> out1, Inlet<In2> in2, Outlet<Out2> out2) {
        return new BidiShape<In1, Out1, In2, Out2>(in1, out1, in2, out2);
    }

    public <In1, Out1, In2, Out2> BidiShape<In1, Out1, In2, Out2> apply(Inlet<In1> in1, Outlet<Out1> out1, Inlet<In2> in2, Outlet<Out2> out2) {
        return new BidiShape<In1, Out1, In2, Out2>(in1, out1, in2, out2);
    }

    public <In1, Out1, In2, Out2> Option<Tuple4<Inlet<In1>, Outlet<Out1>, Inlet<In2>, Outlet<Out2>>> unapply(BidiShape<In1, Out1, In2, Out2> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple4<Inlet<In1>, Outlet<Out1>, Inlet<In2>, Outlet<Out2>>>(new Tuple4<Inlet<In1>, Outlet<Out1>, Inlet<In2>, Outlet<Out2>>(x$0.in1(), x$0.out1(), x$0.in2(), x$0.out2()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(BidiShape$.class);
    }

    private BidiShape$() {
    }
}

