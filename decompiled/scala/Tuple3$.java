/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple3;
import scala.runtime.ModuleSerializationProxy;

public final class Tuple3$
implements Serializable {
    public static final Tuple3$ MODULE$ = new Tuple3$();

    public final String toString() {
        return "Tuple3";
    }

    public <T1, T2, T3> Tuple3<T1, T2, T3> apply(T1 _1, T2 _2, T3 _3) {
        return new Tuple3<T1, T2, T3>(_1, _2, _3);
    }

    public <T1, T2, T3> Option<Tuple3<T1, T2, T3>> unapply(Tuple3<T1, T2, T3> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple3<T1, T2, T3>>(new Tuple3<T1, T2, T3>(x$0._1(), x$0._2(), x$0._3()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Tuple3$.class);
    }

    private Tuple3$() {
    }
}

