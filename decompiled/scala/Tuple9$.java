/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple9;
import scala.runtime.ModuleSerializationProxy;

public final class Tuple9$
implements Serializable {
    public static final Tuple9$ MODULE$ = new Tuple9$();

    public final String toString() {
        return "Tuple9";
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9) {
        return new Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(_1, _2, _3, _4, _5, _6, _7, _8, _9);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Option<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> unapply(Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>>(new Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(x$0._1(), x$0._2(), x$0._3(), x$0._4(), x$0._5(), x$0._6(), x$0._7(), x$0._8(), x$0._9()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Tuple9$.class);
    }

    private Tuple9$() {
    }
}

