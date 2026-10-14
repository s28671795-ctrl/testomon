/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.Batch;
import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple4;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class Batch$
implements Serializable {
    public static final Batch$ MODULE$ = new Batch$();

    public final String toString() {
        return "Batch";
    }

    public <In, Out> Batch<In, Out> apply(long max, Function1<In, Object> costFn, Function1<In, Out> seed, Function2<Out, In, Out> aggregate) {
        return new Batch<In, Out>(max, costFn, seed, aggregate);
    }

    public <In, Out> Option<Tuple4<Object, Function1<In, Object>, Function1<In, Out>, Function2<Out, In, Out>>> unapply(Batch<In, Out> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple4<Object, Function1<In, Object>, Function1<In, Out>, Function2<Out, In, Out>>>(new Tuple4<Long, Function1<In, Object>, Function1<In, Out>, Function2<Out, In, Out>>(BoxesRunTime.boxToLong(x$0.max()), x$0.costFn(), x$0.seed(), x$0.aggregate()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Batch$.class);
    }

    private Batch$() {
    }
}

