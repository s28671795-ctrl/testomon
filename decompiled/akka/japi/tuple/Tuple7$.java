/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple7;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class Tuple7$
implements Serializable {
    public static final Tuple7$ MODULE$ = new Tuple7$();

    public <T1, T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> create(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
        return new Tuple7<T1, T2, T3, T4, T5, T6, T7>(t1, t2, t3, t4, t5, t6, t7);
    }

    public <T1, T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6, T7 t7) {
        return new Tuple7<T1, T2, T3, T4, T5, T6, T7>(t1, t2, t3, t4, t5, t6, t7);
    }

    public <T1, T2, T3, T4, T5, T6, T7> Option<scala.Tuple7<T1, T2, T3, T4, T5, T6, T7>> unapply(Tuple7<T1, T2, T3, T4, T5, T6, T7> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<scala.Tuple7<T1, T2, T3, T4, T5, T6, T7>>(new scala.Tuple7<T1, T2, T3, T4, T5, T6, T7>(x$0.t1(), x$0.t2(), x$0.t3(), x$0.t4(), x$0.t5(), x$0.t6(), x$0.t7()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Tuple7$.class);
    }

    private Tuple7$() {
    }
}

