/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.impl.fusing.MapAsync$;
import akka.stream.impl.fusing.MapAsyncPartitioned;
import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple4;
import scala.concurrent.Future;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;
import scala.util.Failure;

@InternalApi
public final class MapAsyncPartitioned$
implements Serializable {
    public static final MapAsyncPartitioned$ MODULE$ = new MapAsyncPartitioned$();
    private static final Failure<Nothing$> NotYetThere = MapAsync$.MODULE$.NotYetThere();

    public Failure<Nothing$> NotYetThere() {
        return NotYetThere;
    }

    public <In, Out, Partition> MapAsyncPartitioned<In, Out, Partition> apply(int parallelism, int perPartition, Function1<In, Partition> partitioner, Function2<In, Partition, Future<Out>> f) {
        return new MapAsyncPartitioned<In, Out, Partition>(parallelism, perPartition, partitioner, f);
    }

    public <In, Out, Partition> Option<Tuple4<Object, Object, Function1<In, Partition>, Function2<In, Partition, Future<Out>>>> unapply(MapAsyncPartitioned<In, Out, Partition> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple4<Object, Object, Function1<In, Partition>, Function2<In, Partition, Future<Out>>>>(new Tuple4<Integer, Integer, Function1<In, Partition>, Function2<In, Partition, Future<Out>>>(BoxesRunTime.boxToInteger(x$0.parallelism()), BoxesRunTime.boxToInteger(x$0.perPartition()), x$0.partitioner(), x$0.f()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(MapAsyncPartitioned$.class);
    }

    private MapAsyncPartitioned$() {
    }
}

