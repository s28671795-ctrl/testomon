/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.tuple;

import akka.japi.tuple.Tuple6;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.runtime.ModuleSerializationProxy;

public final class Tuple6$
implements Serializable {
    public static final Tuple6$ MODULE$ = new Tuple6$();

    public <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> create(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
        return new Tuple6<T1, T2, T3, T4, T5, T6>(t1, t2, t3, t4, t5, t6);
    }

    public <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6) {
        return new Tuple6<T1, T2, T3, T4, T5, T6>(t1, t2, t3, t4, t5, t6);
    }

    public <T1, T2, T3, T4, T5, T6> Option<scala.Tuple6<T1, T2, T3, T4, T5, T6>> unapply(Tuple6<T1, T2, T3, T4, T5, T6> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<scala.Tuple6<T1, T2, T3, T4, T5, T6>>(new scala.Tuple6<T1, T2, T3, T4, T5, T6>(x$0.t1(), x$0.t2(), x$0.t3(), x$0.t4(), x$0.t5(), x$0.t6()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Tuple6$.class);
    }

    private Tuple6$() {
    }
}

