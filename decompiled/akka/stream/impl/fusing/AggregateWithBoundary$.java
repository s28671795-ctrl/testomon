/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.AggregateWithBoundary;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.Tuple4;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.ModuleSerializationProxy;

public final class AggregateWithBoundary$
implements Serializable {
    public static final AggregateWithBoundary$ MODULE$ = new AggregateWithBoundary$();

    public final String toString() {
        return "AggregateWithBoundary";
    }

    public <In, Agg, Out> AggregateWithBoundary<In, Agg, Out> apply(Function0<Agg> allocate, Function2<Agg, In, Tuple2<Agg, Object>> aggregate, Function1<Agg, Out> harvest, Option<Tuple2<Function1<Agg, Object>, FiniteDuration>> emitOnTimer) {
        return new AggregateWithBoundary<In, Agg, Out>(allocate, aggregate, harvest, emitOnTimer);
    }

    public <In, Agg, Out> Option<Tuple4<Function0<Agg>, Function2<Agg, In, Tuple2<Agg, Object>>, Function1<Agg, Out>, Option<Tuple2<Function1<Agg, Object>, FiniteDuration>>>> unapply(AggregateWithBoundary<In, Agg, Out> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple4<Function0<Agg>, Function2<Agg, In, Tuple2<Agg, Object>>, Function1<Agg, Out>, Option<Tuple2<Function1<Agg, Object>, FiniteDuration>>>>(new Tuple4<Function0<Agg>, Function2<Agg, In, Tuple2<Agg, Object>>, Function1<Agg, Out>, Option<Tuple2<Function1<Agg, Object>, FiniteDuration>>>(x$0.allocate(), x$0.aggregate(), x$0.harvest(), x$0.emitOnTimer()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(AggregateWithBoundary$.class);
    }

    private AggregateWithBoundary$() {
    }
}

