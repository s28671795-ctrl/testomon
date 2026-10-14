/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple5;
import scala.runtime.ModuleSerializationProxy;

public final class Tuple5$
implements Serializable {
    public static final Tuple5$ MODULE$ = new Tuple5$();

    public final String toString() {
        return "Tuple5";
    }

    public <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4, T5> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5) {
        return new Tuple5<T1, T2, T3, T4, T5>(_1, _2, _3, _4, _5);
    }

    public <T1, T2, T3, T4, T5> Option<Tuple5<T1, T2, T3, T4, T5>> unapply(Tuple5<T1, T2, T3, T4, T5> x$0) {
        if (x$0 == null) {
            return None$.MODULE$;
        }
        return new Some<Tuple5<T1, T2, T3, T4, T5>>(new Tuple5<T1, T2, T3, T4, T5>(x$0._1(), x$0._2(), x$0._3(), x$0._4(), x$0._5()));
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Tuple5$.class);
    }

    private Tuple5$() {
    }
}

