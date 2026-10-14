/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple3;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class Tuple3$
implements Serializable {
    public static final Tuple3$ MODULE$ = new Tuple3$();

    public <T1, T2, T3> Tuple3<T1, T2, T3> create(T1 t1, T2 t2, T3 t3) {
        return new Tuple3<T1, T2, T3>(t1, t2, t3);
    }

    public <T1, T2, T3> Tuple3<T1, T2, T3> apply(T1 t1, T2 t2, T3 t3) {
        return new Tuple3<T1, T2, T3>(t1, t2, t3);
    }

    public <T1, T2, T3> Option<scala.Tuple3<T1, T2, T3>> unapply(Tuple3<T1, T2, T3> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<scala.Tuple3<T1, T2, T3>>(new scala.Tuple3<T1, T2, T3>(x$0.t1(), x$0.t2(), x$0.t3()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Tuple3$.class);
    }

    private Tuple3$() {
    }
}

