/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.ScanAsync;
import java.io.Serializable;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.concurrent.Future;
import scala.runtime.ModuleSerializationProxy;

public final class ScanAsync$
implements Serializable {
    public static final ScanAsync$ MODULE$ = new ScanAsync$();

    public final String toString() {
        return "ScanAsync";
    }

    public <In, Out> ScanAsync<In, Out> apply(Out zero, Function2<Out, In, Future<Out>> f) {
        return new ScanAsync<In, Out>(zero, f);
    }

    public <In, Out> Option<Tuple2<Out, Function2<Out, In, Future<Out>>>> unapply(ScanAsync<In, Out> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Out, Function2<Out, In, Future<Out>>>>(new Tuple2<Out, Function2<Out, In, Future<Out>>>(x$0.zero(), x$0.f()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ScanAsync$.class);
    }

    private ScanAsync$() {
    }
}

