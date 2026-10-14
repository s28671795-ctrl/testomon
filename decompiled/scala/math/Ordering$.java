/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import java.io.Serializable;
import java.util.Comparator;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.Tuple3;
import scala.Tuple4;
import scala.Tuple5;
import scala.Tuple6;
import scala.Tuple7;
import scala.Tuple8;
import scala.Tuple9;
import scala.collection.Iterable;
import scala.math.LowPriorityOrderingImplicits;
import scala.math.Ordering;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class Ordering$
implements LowPriorityOrderingImplicits,
Serializable {
    public static final Ordering$ MODULE$ = new Ordering$();

    @Override
    public <A> Ordering<A> ordered(Function1<A, Comparable<? super A>> asComparable) {
        return LowPriorityOrderingImplicits.ordered$(this, asComparable);
    }

    @Override
    public <A> Ordering<A> comparatorToOrdering(Comparator<A> cmp) {
        return LowPriorityOrderingImplicits.comparatorToOrdering$(this, cmp);
    }

    private final int reverseSeed() {
        return 41;
    }

    private final int optionSeed() {
        return 43;
    }

    private final int iterableSeed() {
        return 47;
    }

    public <T> Ordering<T> apply(Ordering<T> ord) {
        return ord;
    }

    public <T> Ordering<T> fromLessThan(Function2<T, T, Object> cmp) {
        return new Ordering<T>(cmp){
            private final Function2 cmp$2;

            public Some<Object> tryCompare(T x, T y) {
                return Ordering.tryCompare$(this, x, y);
            }

            public boolean equiv(T x, T y) {
                return Ordering.equiv$(this, x, y);
            }

            public <U extends T> U max(U x, U y) {
                return (U)Ordering.max$(this, x, y);
            }

            public <U extends T> U min(U x, U y) {
                return (U)Ordering.min$(this, x, y);
            }

            public Ordering<T> reverse() {
                return Ordering.reverse$(this);
            }

            public boolean isReverseOf(Ordering<?> other) {
                return Ordering.isReverseOf$(this, other);
            }

            public <U> Ordering<U> on(Function1<U, T> f) {
                return Ordering.on$(this, f);
            }

            public Ordering<T> orElse(Ordering<T> other) {
                return Ordering.orElse$(this, other);
            }

            public <S> Ordering<T> orElseBy(Function1<T, S> f, Ordering<S> ord) {
                return Ordering.orElseBy$(this, f, ord);
            }

            public Ordering.OrderingOps mkOrderingOps(T lhs) {
                return Ordering.mkOrderingOps$(this, lhs);
            }

            public int compare(T x, T y) {
                if (BoxesRunTime.unboxToBoolean(this.cmp$2.apply(x, y))) {
                    return -1;
                }
                if (BoxesRunTime.unboxToBoolean(this.cmp$2.apply(y, x))) {
                    return 1;
                }
                return 0;
            }

            public boolean lt(T x, T y) {
                return BoxesRunTime.unboxToBoolean(this.cmp$2.apply(x, y));
            }

            public boolean gt(T x, T y) {
                return BoxesRunTime.unboxToBoolean(this.cmp$2.apply(y, x));
            }

            public boolean gteq(T x, T y) {
                return !BoxesRunTime.unboxToBoolean(this.cmp$2.apply(x, y));
            }

            public boolean lteq(T x, T y) {
                return !BoxesRunTime.unboxToBoolean(this.cmp$2.apply(y, x));
            }
            {
                this.cmp$2 = cmp$2;
            }
        };
    }

    public <T, S> Ordering<T> by(Function1<T, S> f, Ordering<S> ord) {
        return new Ordering<T>(ord, f){
            private final Ordering ord$2;
            private final Function1 f$3;

            public Some<Object> tryCompare(T x, T y) {
                return Ordering.tryCompare$(this, x, y);
            }

            public boolean equiv(T x, T y) {
                return Ordering.equiv$(this, x, y);
            }

            public <U extends T> U max(U x, U y) {
                return (U)Ordering.max$(this, x, y);
            }

            public <U extends T> U min(U x, U y) {
                return (U)Ordering.min$(this, x, y);
            }

            public Ordering<T> reverse() {
                return Ordering.reverse$(this);
            }

            public boolean isReverseOf(Ordering<?> other) {
                return Ordering.isReverseOf$(this, other);
            }

            public <U> Ordering<U> on(Function1<U, T> f) {
                return Ordering.on$(this, f);
            }

            public Ordering<T> orElse(Ordering<T> other) {
                return Ordering.orElse$(this, other);
            }

            public <S> Ordering<T> orElseBy(Function1<T, S> f, Ordering<S> ord) {
                return Ordering.orElseBy$(this, f, ord);
            }

            public Ordering.OrderingOps mkOrderingOps(T lhs) {
                return Ordering.mkOrderingOps$(this, lhs);
            }

            public int compare(T x, T y) {
                return this.ord$2.compare(this.f$3.apply(x), this.f$3.apply(y));
            }

            public boolean lt(T x, T y) {
                return this.ord$2.lt(this.f$3.apply(x), this.f$3.apply(y));
            }

            public boolean gt(T x, T y) {
                return this.ord$2.gt(this.f$3.apply(x), this.f$3.apply(y));
            }

            public boolean gteq(T x, T y) {
                return this.ord$2.gteq(this.f$3.apply(x), this.f$3.apply(y));
            }

            public boolean lteq(T x, T y) {
                return this.ord$2.lteq(this.f$3.apply(x), this.f$3.apply(y));
            }
            {
                this.ord$2 = ord$2;
                this.f$3 = f$3;
            }
        };
    }

    public <T> Ordering<Option<T>> Option(Ordering<T> ord) {
        public class Scala_math_Ordering$O$1
        implements Ordering.OptionOrdering<T> {
            private static final long serialVersionUID = 6958068162830323876L;
            private final Ordering<T> optionOrdering;

            public int compare(Option<T> x, Option<T> y) {
                return Ordering.OptionOrdering.compare$(this, x, y);
            }

            public boolean equals(Object obj) {
                return Ordering.OptionOrdering.equals$(this, obj);
            }

            public int hashCode() {
                return Ordering.OptionOrdering.hashCode$(this);
            }

            public Some tryCompare(Object x, Object y) {
                return Ordering.tryCompare$(this, x, y);
            }

            public boolean lteq(Object x, Object y) {
                return Ordering.lteq$(this, x, y);
            }

            public boolean gteq(Object x, Object y) {
                return Ordering.gteq$(this, x, y);
            }

            public boolean lt(Object x, Object y) {
                return Ordering.lt$(this, x, y);
            }

            public boolean gt(Object x, Object y) {
                return Ordering.gt$(this, x, y);
            }

            public boolean equiv(Object x, Object y) {
                return Ordering.equiv$(this, x, y);
            }

            public Object max(Object x, Object y) {
                return Ordering.max$(this, x, y);
            }

            public Object min(Object x, Object y) {
                return Ordering.min$(this, x, y);
            }

            public Ordering<Option<T>> reverse() {
                return Ordering.reverse$(this);
            }

            public boolean isReverseOf(Ordering<?> other) {
                return Ordering.isReverseOf$(this, other);
            }

            public <U> Ordering<U> on(Function1<U, Option<T>> f) {
                return Ordering.on$(this, f);
            }

            public Ordering<Option<T>> orElse(Ordering<Option<T>> other) {
                return Ordering.orElse$(this, other);
            }

            public <S> Ordering<Option<T>> orElseBy(Function1<Option<T>, S> f, Ordering<S> ord) {
                return Ordering.orElseBy$(this, f, ord);
            }

            public Ordering.OrderingOps mkOrderingOps(Object lhs) {
                return Ordering.mkOrderingOps$(this, lhs);
            }

            public Ordering<T> optionOrdering() {
                return this.optionOrdering;
            }

            public Scala_math_Ordering$O$1(Ordering ord$3) {
                this.optionOrdering = ord$3;
            }
        }
        return new Scala_math_Ordering$O$1(ord);
    }

    public <T> Ordering<Iterable<T>> Iterable(Ordering<T> ord) {
        return new Ordering.IterableOrdering(ord);
    }

    public <T1, T2> Ordering<Tuple2<T1, T2>> Tuple2(Ordering<T1> ord1, Ordering<T2> ord2) {
        return new Ordering.Tuple2Ordering<T1, T2>(ord1, ord2);
    }

    public <T1, T2, T3> Ordering<Tuple3<T1, T2, T3>> Tuple3(Ordering<T1> ord1, Ordering<T2> ord2, Ordering<T3> ord3) {
        return new Ordering.Tuple3Ordering<T1, T2, T3>(ord1, ord2, ord3);
    }

    public <T1, T2, T3, T4> Ordering<Tuple4<T1, T2, T3, T4>> Tuple4(Ordering<T1> ord1, Ordering<T2> ord2, Ordering<T3> ord3, Ordering<T4> ord4) {
        return new Ordering.Tuple4Ordering<T1, T2, T3, T4>(ord1, ord2, ord3, ord4);
    }

    public <T1, T2, T3, T4, T5> Ordering<Tuple5<T1, T2, T3, T4, T5>> Tuple5(Ordering<T1> ord1, Ordering<T2> ord2, Ordering<T3> ord3, Ordering<T4> ord4, Ordering<T5> ord5) {
        return new Ordering.Tuple5Ordering<T1, T2, T3, T4, T5>(ord1, ord2, ord3, ord4, ord5);
    }

    public <T1, T2, T3, T4, T5, T6> Ordering<Tuple6<T1, T2, T3, T4, T5, T6>> Tuple6(Ordering<T1> ord1, Ordering<T2> ord2, Ordering<T3> ord3, Ordering<T4> ord4, Ordering<T5> ord5, Ordering<T6> ord6) {
        return new Ordering.Tuple6Ordering<T1, T2, T3, T4, T5, T6>(ord1, ord2, ord3, ord4, ord5, ord6);
    }

    public <T1, T2, T3, T4, T5, T6, T7> Ordering<Tuple7<T1, T2, T3, T4, T5, T6, T7>> Tuple7(Ordering<T1> ord1, Ordering<T2> ord2, Ordering<T3> ord3, Ordering<T4> ord4, Ordering<T5> ord5, Ordering<T6> ord6, Ordering<T7> ord7) {
        return new Ordering.Tuple7Ordering<T1, T2, T3, T4, T5, T6, T7>(ord1, ord2, ord3, ord4, ord5, ord6, ord7);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> Ordering<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> Tuple8(Ordering<T1> ord1, Ordering<T2> ord2, Ordering<T3> ord3, Ordering<T4> ord4, Ordering<T5> ord5, Ordering<T6> ord6, Ordering<T7> ord7, Ordering<T8> ord8) {
        return new Ordering.Tuple8Ordering<T1, T2, T3, T4, T5, T6, T7, T8>(ord1, ord2, ord3, ord4, ord5, ord6, ord7, ord8);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Ordering<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> Tuple9(Ordering<T1> ord1, Ordering<T2> ord2, Ordering<T3> ord3, Ordering<T4> ord4, Ordering<T5> ord5, Ordering<T6> ord6, Ordering<T7> ord7, Ordering<T8> ord8, Ordering<T9> ord9) {
        return new Ordering.Tuple9Ordering<T1, T2, T3, T4, T5, T6, T7, T8, T9>(ord1, ord2, ord3, ord4, ord5, ord6, ord7, ord8, ord9);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Ordering$.class);
    }

    private Ordering$() {
    }
}

