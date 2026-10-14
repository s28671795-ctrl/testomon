/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.LimitWeighted;
import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class LimitWeighted$
implements Serializable {
    public static final LimitWeighted$ MODULE$ = new LimitWeighted$();

    public final String toString() {
        return "LimitWeighted";
    }

    public <T> LimitWeighted<T> apply(long n, Function1<T, Object> costFn) {
        return new LimitWeighted<T>(n, costFn);
    }

    public <T> Option<Tuple2<Object, Function1<T, Object>>> unapply(LimitWeighted<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Object, Function1<T, Object>>>(new Tuple2<Long, Function1<T, Object>>(BoxesRunTime.boxToLong(x$0.n()), x$0.costFn()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(LimitWeighted$.class);
    }

    private LimitWeighted$() {
    }
}

