/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.annotation.InternalApi;
import akka.stream.impl.fusing.MapAsync;
import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.concurrent.Future;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;
import scala.util.Failure;
import scala.util.control.NoStackTrace;

@InternalApi
public final class MapAsync$
implements Serializable {
    public static final MapAsync$ MODULE$ = new MapAsync$();
    private static final Failure<Nothing$> NotYetThere = new Failure((Throwable)((Object)new NoStackTrace(){

        public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
            return super.fillInStackTrace();
        }

        public Throwable fillInStackTrace() {
            return NoStackTrace.fillInStackTrace$(this);
        }
        {
            NoStackTrace.$init$(this);
        }
    }));

    public Failure<Nothing$> NotYetThere() {
        return NotYetThere;
    }

    public <In, Out> MapAsync<In, Out> apply(int parallelism, Function1<In, Future<Out>> f) {
        return new MapAsync<In, Out>(parallelism, f);
    }

    public <In, Out> Option<Tuple2<Object, Function1<In, Future<Out>>>> unapply(MapAsync<In, Out> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Object, Function1<In, Future<Out>>>>(new Tuple2<Integer, Function1<In, Future<Out>>>(BoxesRunTime.boxToInteger(x$0.parallelism()), x$0.f()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(MapAsync$.class);
    }

    private MapAsync$() {
    }
}

