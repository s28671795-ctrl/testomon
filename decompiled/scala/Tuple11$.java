/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple11;
import scala.runtime.ModuleSerializationProxy;

public final class Tuple11$
implements Serializable {
    public static final Tuple11$ MODULE$ = new Tuple11$();

    public final String toString() {
        return "Tuple11";
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11) {
        return new Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Option<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> unapply(Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>>(new Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>(x$0._1(), x$0._2(), x$0._3(), x$0._4(), x$0._5(), x$0._6(), x$0._7(), x$0._8(), x$0._9(), x$0._10(), x$0._11()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Tuple11$.class);
    }

    private Tuple11$() {
    }
}

