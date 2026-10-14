/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.UniformFanOutShape;
import scala.collection.immutable.Seq;

public final class UniformFanOutShape$ {
    public static final UniformFanOutShape$ MODULE$ = new UniformFanOutShape$();

    public <I, O> UniformFanOutShape<I, O> apply(Inlet<I> inlet, Seq<Outlet<O>> outlets) {
        return new UniformFanOutShape(outlets.size(), new FanOutShape.Ports<I>(inlet, outlets.toList()));
    }

    private UniformFanOutShape$() {
    }
}

