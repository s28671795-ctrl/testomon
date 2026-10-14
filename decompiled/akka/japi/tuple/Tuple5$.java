/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple5;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class Tuple5$
implements Serializable {
    public static final Tuple5$ MODULE$ = new Tuple5$();

    public <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> create(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
        return new Tuple5<T1, T2, T3, T4, T5>(t1, t2, t3, t4, t5);
    }

    public <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5) {
        return new Tuple5<T1, T2, T3, T4, T5>(t1, t2, t3, t4, t5);
    }

    public <T1, T2, T3, T4, T5> Option<scala.Tuple5<T1, T2, T3, T4, T5>> unapply(Tuple5<T1, T2, T3, T4, T5> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<scala.Tuple5<T1, T2, T3, T4, T5>>(new scala.Tuple5<T1, T2, T3, T4, T5>(x$0.t1(), x$0.t2(), x$0.t3(), x$0.t4(), x$0.t5()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Tuple5$.class);
    }

    private Tuple5$() {
    }
}

