/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.MatchError;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.Tuple3;
import scala.Tuple4;
import scala.Tuple5;
import scala.collection.Seq;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
public final class Function$ {
    public static final Function$ MODULE$ = new Function$();

    public <T> Function1<T, T> chain(Seq<Function1<T, T>> fs) {
        return (Function1<Object, Object> & Serializable)x2 -> fs.foldLeft(x2, (Function2<Object, Function1, Object> & Serializable)(x, f) -> f.apply(x));
    }

    public <T, U> T const(T x, U y) {
        return x;
    }

    public <T, R> PartialFunction<T, R> unlift(Function1<T, Option<R>> f) {
        if (f instanceof PartialFunction.Lifted) {
            return ((PartialFunction.Lifted)f).pf();
        }
        return new PartialFunction.Unlifted<T, R>(f);
    }

    public <T1, T2, R> Function2<T1, T2, R> uncurried(Function1<T1, Function1<T2, R>> f) {
        return (Function2<Object, Object, Object> & Serializable)(x1, x2) -> ((Function1)f.apply(x1)).apply(x2);
    }

    public <T1, T2, T3, R> Function3<T1, T2, T3, R> uncurried(Function1<T1, Function1<T2, Function1<T3, R>>> f) {
        return (Function3<Object, Object, Object, Object> & Serializable)(x1, x2, x3) -> ((Function1)((Function1)f.apply(x1)).apply(x2)).apply(x3);
    }

    public <T1, T2, T3, T4, R> Function4<T1, T2, T3, T4, R> uncurried(Function1<T1, Function1<T2, Function1<T3, Function1<T4, R>>>> f) {
        return (Function4<Object, Object, Object, Object, Object> & Serializable)(x1, x2, x3, x4) -> ((Function1)((Function1)((Function1)f.apply(x1)).apply(x2)).apply(x3)).apply(x4);
    }

    public <T1, T2, T3, T4, T5, R> Function5<T1, T2, T3, T4, T5, R> uncurried(Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, R>>>>> f) {
        return (Function5<Object, Object, Object, Object, Object, Object> & Serializable)(x1, x2, x3, x4, x5) -> ((Function1)((Function1)((Function1)((Function1)f.apply(x1)).apply(x2)).apply(x3)).apply(x4)).apply(x5);
    }

    public <T1, T2, R> Function1<Tuple2<T1, T2>, R> tupled(Function2<T1, T2, R> f) {
        return (Function1<Tuple2, Object> & Serializable)x0$1 -> {
            if (x0$1 != null) {
                Object x1 = x0$1._1();
                Object x2 = x0$1._2();
                return f.apply(x1, x2);
            }
            throw new MatchError((Object)null);
        };
    }

    public <T1, T2, T3, R> Function1<Tuple3<T1, T2, T3>, R> tupled(Function3<T1, T2, T3, R> f) {
        return (Function1<Tuple3, Object> & Serializable)x0$1 -> {
            if (x0$1 != null) {
                Object x1 = x0$1._1();
                Object x2 = x0$1._2();
                Object x3 = x0$1._3();
                return f.apply(x1, x2, x3);
            }
            throw new MatchError((Object)null);
        };
    }

    public <T1, T2, T3, T4, R> Function1<Tuple4<T1, T2, T3, T4>, R> tupled(Function4<T1, T2, T3, T4, R> f) {
        return (Function1<Tuple4, Object> & Serializable)x0$1 -> {
            if (x0$1 != null) {
                Object x1 = x0$1._1();
                Object x2 = x0$1._2();
                Object x3 = x0$1._3();
                Object x4 = x0$1._4();
                return f.apply(x1, x2, x3, x4);
            }
            throw new MatchError((Object)null);
        };
    }

    public <T1, T2, T3, T4, T5, R> Function1<Tuple5<T1, T2, T3, T4, T5>, R> tupled(Function5<T1, T2, T3, T4, T5, R> f) {
        return (Function1<Tuple5, Object> & Serializable)x0$1 -> {
            if (x0$1 != null) {
                Object x1 = x0$1._1();
                Object x2 = x0$1._2();
                Object x3 = x0$1._3();
                Object x4 = x0$1._4();
                Object x5 = x0$1._5();
                return f.apply(x1, x2, x3, x4, x5);
            }
            throw new MatchError((Object)null);
        };
    }

    public <T1, T2, R> Function2<T1, T2, R> untupled(Function1<Tuple2<T1, T2>, R> f) {
        return (Function2<Object, Object, Object> & Serializable)(x1, x2) -> f.apply(new Tuple2<Object, Object>(x1, x2));
    }

    public <T1, T2, T3, R> Function3<T1, T2, T3, R> untupled(Function1<Tuple3<T1, T2, T3>, R> f) {
        return (Function3<Object, Object, Object, Object> & Serializable)(x1, x2, x3) -> f.apply(new Tuple3<Object, Object, Object>(x1, x2, x3));
    }

    public <T1, T2, T3, T4, R> Function4<T1, T2, T3, T4, R> untupled(Function1<Tuple4<T1, T2, T3, T4>, R> f) {
        return (Function4<Object, Object, Object, Object, Object> & Serializable)(x1, x2, x3, x4) -> f.apply(new Tuple4<Object, Object, Object, Object>(x1, x2, x3, x4));
    }

    public <T1, T2, T3, T4, T5, R> Function5<T1, T2, T3, T4, T5, R> untupled(Function1<Tuple5<T1, T2, T3, T4, T5>, R> f) {
        return (Function5<Object, Object, Object, Object, Object, Object> & Serializable)(x1, x2, x3, x4, x5) -> f.apply(new Tuple5<Object, Object, Object, Object, Object>(x1, x2, x3, x4, x5));
    }

    private Function$() {
    }
}

