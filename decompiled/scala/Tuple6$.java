/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple6;
import scala.runtime.ModuleSerializationProxy;

public final class Tuple6$
implements Serializable {
    public static final Tuple6$ MODULE$ = new Tuple6$();

    public final String toString() {
        return "Tuple6";
    }

    public <T1, T2, T3, T4, T5, T6> Tuple6<T1, T2, T3, T4, T5, T6> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6) {
        return new Tuple6<T1, T2, T3, T4, T5, T6>(_1, _2, _3, _4, _5, _6);
    }

    public <T1, T2, T3, T4, T5, T6> Option<Tuple6<T1, T2, T3, T4, T5, T6>> unapply(Tuple6<T1, T2, T3, T4, T5, T6> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple6<T1, T2, T3, T4, T5, T6>>(new Tuple6<T1, T2, T3, T4, T5, T6>(x$0._1(), x$0._2(), x$0._3(), x$0._4(), x$0._5(), x$0._6()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Tuple6$.class);
    }

    private Tuple6$() {
    }
}

