/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple7;
import scala.runtime.ModuleSerializationProxy;

public final class Tuple7$
implements Serializable {
    public static final Tuple7$ MODULE$ = new Tuple7$();

    public final String toString() {
        return "Tuple7";
    }

    public <T1, T2, T3, T4, T5, T6, T7> Tuple7<T1, T2, T3, T4, T5, T6, T7> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7) {
        return new Tuple7<T1, T2, T3, T4, T5, T6, T7>(_1, _2, _3, _4, _5, _6, _7);
    }

    public <T1, T2, T3, T4, T5, T6, T7> Option<Tuple7<T1, T2, T3, T4, T5, T6, T7>> unapply(Tuple7<T1, T2, T3, T4, T5, T6, T7> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple7<T1, T2, T3, T4, T5, T6, T7>>(new Tuple7<T1, T2, T3, T4, T5, T6, T7>(x$0._1(), x$0._2(), x$0._3(), x$0._4(), x$0._5(), x$0._6(), x$0._7()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Tuple7$.class);
    }

    private Tuple7$() {
    }
}

