/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple8;
import scala.runtime.ModuleSerializationProxy;

public final class Tuple8$
implements Serializable {
    public static final Tuple8$ MODULE$ = new Tuple8$();

    public final String toString() {
        return "Tuple8";
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8) {
        return new Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>(_1, _2, _3, _4, _5, _6, _7, _8);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> Option<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> unapply(Tuple8<T1, T2, T3, T4, T5, T6, T7, T8> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>>(new Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>(x$0._1(), x$0._2(), x$0._3(), x$0._4(), x$0._5(), x$0._6(), x$0._7(), x$0._8()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Tuple8$.class);
    }

    private Tuple8$() {
    }
}

