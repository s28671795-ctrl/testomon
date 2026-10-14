/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple14;
import scala.runtime.ModuleSerializationProxy;

public final class Tuple14$
implements Serializable {
    public static final Tuple14$ MODULE$ = new Tuple14$();

    public final String toString() {
        return "Tuple14";
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13, T14 _14) {
        return new Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Option<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> unapply(Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>>(new Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>(x$0._1(), x$0._2(), x$0._3(), x$0._4(), x$0._5(), x$0._6(), x$0._7(), x$0._8(), x$0._9(), x$0._10(), x$0._11(), x$0._12(), x$0._13(), x$0._14()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Tuple14$.class);
    }

    private Tuple14$() {
    }
}

