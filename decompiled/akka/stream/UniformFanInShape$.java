/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.UniformFanInShape;
import akka.util.ccompat.package$JavaConverters$;
import java.util.List;
import scala.collection.immutable.Seq;

public final class UniformFanInShape$ {
    public static final UniformFanInShape$ MODULE$ = new UniformFanInShape$();

    public <I, O> UniformFanInShape<I, O> apply(Outlet<O> outlet, Seq<Inlet<I>> inlets) {
        return new UniformFanInShape(inlets.size(), new FanInShape.Ports<O>(outlet, inlets.toList()));
    }

    public <I, O> UniformFanInShape<I, O> create(Outlet<O> outlet, List<Inlet<I>> inlets) {
        return new UniformFanInShape(inlets.size(), new FanInShape.Ports<O>(outlet, package$JavaConverters$.MODULE$.ListHasAsScala(inlets).asScala().toList()));
    }

    private UniformFanInShape$() {
    }
}

