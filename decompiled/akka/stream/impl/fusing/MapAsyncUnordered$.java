/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.MapAsyncUnordered;
import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.concurrent.Future;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class MapAsyncUnordered$
implements Serializable {
    public static final MapAsyncUnordered$ MODULE$ = new MapAsyncUnordered$();

    public final String toString() {
        return "MapAsyncUnordered";
    }

    public <In, Out> MapAsyncUnordered<In, Out> apply(int parallelism, Function1<In, Future<Out>> f) {
        return new MapAsyncUnordered<In, Out>(parallelism, f);
    }

    public <In, Out> Option<Tuple2<Object, Function1<In, Future<Out>>>> unapply(MapAsyncUnordered<In, Out> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Object, Function1<In, Future<Out>>>>(new Tuple2<Integer, Function1<In, Future<Out>>>(BoxesRunTime.boxToInteger(x$0.parallelism()), x$0.f()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(MapAsyncUnordered$.class);
    }

    private MapAsyncUnordered$() {
    }
}

