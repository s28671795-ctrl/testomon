/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple8;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class Tuple8$
implements Serializable {
    public static final Tuple8$ MODULE$ = new Tuple8$();

    public <T1, T2, T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> create(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
        return new Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>(t1, t2, t3, t4, t5, t6, t7, t8);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7, T8 t8) {
        return new Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>(t1, t2, t3, t4, t5, t6, t7, t8);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> Option<scala.Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> unapply(Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<scala.Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>>(new scala.Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>(x$0.t1(), x$0.t2(), x$0.t3(), x$0.t4(), x$0.t5(), x$0.t6(), x$0.t7(), x$0.t8()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Tuple8$.class);
    }

    private Tuple8$() {
    }
}

