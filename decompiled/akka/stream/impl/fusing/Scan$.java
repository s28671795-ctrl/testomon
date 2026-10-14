/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.Scan;
import java.io.Serializable;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.ModuleSerializationProxy;

public final class Scan$
implements Serializable {
    public static final Scan$ MODULE$ = new Scan$();

    public final String toString() {
        return "Scan";
    }

    public <In, Out> Scan<In, Out> apply(Out zero, Function2<Out, In, Out> f) {
        return new Scan<In, Out>(zero, f);
    }

    public <In, Out> Option<Tuple2<Out, Function2<Out, In, Out>>> unapply(Scan<In, Out> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Out, Function2<Out, In, Out>>>(new Tuple2<Out, Function2<Out, In, Out>>(x$0.zero(), x$0.f()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Scan$.class);
    }

    private Scan$() {
    }
}

