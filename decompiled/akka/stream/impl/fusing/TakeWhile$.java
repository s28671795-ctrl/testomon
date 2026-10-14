/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.stream.impl.fusing.TakeWhile;
import java.io.Serializable;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class TakeWhile$
implements Serializable {
    public static final TakeWhile$ MODULE$ = new TakeWhile$();

    public <T> boolean $lessinit$greater$default$2() {
        return false;
    }

    public final String toString() {
        return "TakeWhile";
    }

    public <T> TakeWhile<T> apply(Function1<T, Object> p, boolean inclusive) {
        return new TakeWhile<T>(p, inclusive);
    }

    public <T> boolean apply$default$2() {
        return false;
    }

    public <T> Option<Tuple2<Function1<T, Object>, Object>> unapply(TakeWhile<T> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple2<Function1<T, Object>, Object>>(new Tuple2<Function1<T, Object>, Boolean>(x$0.p(), BoxesRunTime.boxToBoolean(x$0.inclusive())));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(TakeWhile$.class);
    }

    private TakeWhile$() {
    }
}

