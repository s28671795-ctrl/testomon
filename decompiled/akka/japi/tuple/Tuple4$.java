/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple4;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class Tuple4$
implements Serializable {
    public static final Tuple4$ MODULE$ = new Tuple4$();

    public <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> create(T1 t1, T2 t2, T3 t3, T4 t4) {
        return new Tuple4<T1, T2, T3, T4>(t1, t2, t3, t4);
    }

    public <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> apply(T1 t1, T2 t2, T3 t3, T4 t4) {
        return new Tuple4<T1, T2, T3, T4>(t1, t2, t3, t4);
    }

    public <T1, T2, T3, T4> Option<scala.Tuple4<T1, T2, T3, T4>> unapply(Tuple4<T1, T2, T3, T4> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<scala.Tuple4<T1, T2, T3, T4>>(new scala.Tuple4<T1, T2, T3, T4>(x$0.t1(), x$0.t2(), x$0.t3(), x$0.t4()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Tuple4$.class);
    }

    private Tuple4$() {
    }
}

