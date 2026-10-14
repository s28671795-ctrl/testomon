/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple13;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class Tuple13$
implements Serializable {
    public static final Tuple13$ MODULE$ = new Tuple13$();

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> create(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
        return new Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8, T9 t9, T10 t10, T11 t11, T12 t12, T13 t13) {
        return new Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Option<scala.Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> unapply(Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<scala.Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>>(new scala.Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>(x$0.t1(), x$0.t2(), x$0.t3(), x$0.t4(), x$0.t5(), x$0.t6(), x$0.t7(), x$0.t8(), x$0.t9(), x$0.t10(), x$0.t11(), x$0.t12(), x$0.t13()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Tuple13$.class);
    }

    private Tuple13$() {
    }
}

