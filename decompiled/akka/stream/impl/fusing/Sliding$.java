/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.Sliding;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.Tuple2$mcII$sp;
import scala.runtime.ModuleSerializationProxy;

public final class Sliding$
implements Serializable {
    public static final Sliding$ MODULE$ = new Sliding$();

    public final String toString() {
        return "Sliding";
    }

    public <T> Sliding<T> apply(int n, int step) {
        return new Sliding(n, step);
    }

    public <T> Option<Tuple2<Object, Object>> unapply(Sliding<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Object, Object>>(new Tuple2$mcII$sp(x$0.n(), x$0.step()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Sliding$.class);
    }

    private Sliding$() {
    }
}

