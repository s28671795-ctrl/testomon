/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.Fold;
import java.io.Serializable;
import scala.Function2;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.ModuleSerializationProxy;

public final class Fold$
implements Serializable {
    public static final Fold$ MODULE$ = new Fold$();

    public final String toString() {
        return "Fold";
    }

    public <In, Out> Fold<In, Out> apply(Out zero, Function2<Out, In, Out> f) {
        return new Fold<In, Out>(zero, f);
    }

    public <In, Out> Option<Tuple2<Out, Function2<Out, In, Out>>> unapply(Fold<In, Out> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Out, Function2<Out, In, Out>>>(new Tuple2<Out, Function2<Out, In, Out>>(x$0.zero(), x$0.f()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Fold$.class);
    }

    private Fold$() {
    }
}

