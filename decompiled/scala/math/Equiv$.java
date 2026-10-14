/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import java.io.Serializable;
import java.util.Comparator;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.Tuple2;
import scala.Tuple3;
import scala.Tuple4;
import scala.Tuple5;
import scala.Tuple6;
import scala.Tuple7;
import scala.Tuple8;
import scala.Tuple9;
import scala.math.Equiv;
import scala.math.LowPriorityEquiv;
import scala.runtime.BoxesRunTime;
import scala.runtime.ModuleSerializationProxy;

public final class Equiv$
implements LowPriorityEquiv,
Serializable {
    public static final Equiv$ MODULE$ = new Equiv$();

    @Override
    public <T> Equiv<T> universalEquiv() {
        return LowPriorityEquiv.universalEquiv$(this);
    }

    public <T> Equiv<T> reference() {
        return (x$1, x$2) -> x$1 == x$2;
    }

    public <T> Equiv<T> universal() {
        return (x$3, x$4) -> BoxesRunTime.equals(x$3, x$4);
    }

    public <T> Equiv<T> fromComparator(Comparator<T> cmp) {
        return (x, y) -> cmp.compare(x, y) == 0;
    }

    public <T> Equiv<T> fromFunction(Function2<T, T, Object> cmp) {
        return (x, y) -> BoxesRunTime.unboxToBoolean(cmp.apply(x, y));
    }

    public <T, S> Equiv<T> by(Function1<T, S> f, Equiv<S> evidence$1) {
        return (x, y) -> evidence$1.equiv(f.apply(x), f.apply(y));
    }

    public <T> Equiv<T> apply(Equiv<T> evidence$2) {
        return evidence$2;
    }

    private final int optionSeed() {
        return 43;
    }

    private final int iterableSeed() {
        return 47;
    }

    public <T> Equiv<Option<T>> Option(Equiv<T> eqv) {
        return new Equiv.OptionEquiv<T>(eqv);
    }

    public <T1, T2> Equiv<Tuple2<T1, T2>> Tuple2(Equiv<T1> eqv1, Equiv<T2> eqv2) {
        return new Equiv.Tuple2Equiv<T1, T2>(eqv1, eqv2);
    }

    public <T1, T2, T3> Equiv<Tuple3<T1, T2, T3>> Tuple3(Equiv<T1> eqv1, Equiv<T2> eqv2, Equiv<T3> eqv3) {
        return new Equiv.Tuple3Equiv<T1, T2, T3>(eqv1, eqv2, eqv3);
    }

    public <T1, T2, T3, T4> Equiv<Tuple4<T1, T2, T3, T4>> Tuple4(Equiv<T1> eqv1, Equiv<T2> eqv2, Equiv<T3> eqv3, Equiv<T4> eqv4) {
        return new Equiv.Tuple4Equiv<T1, T2, T3, T4>(eqv1, eqv2, eqv3, eqv4);
    }

    public <T1, T2, T3, T4, T5> Equiv<Tuple5<T1, T2, T3, T4, T5>> Tuple5(Equiv<T1> eqv1, Equiv<T2> eqv2, Equiv<T3> eqv3, Equiv<T4> eqv4, Equiv<T5> eqv5) {
        return new Equiv.Tuple5Equiv<T1, T2, T3, T4, T5>(eqv1, eqv2, eqv3, eqv4, eqv5);
    }

    public <T1, T2, T3, T4, T5, T6> Equiv<Tuple6<T1, T2, T3, T4, T5, T6>> Tuple6(Equiv<T1> eqv1, Equiv<T2> eqv2, Equiv<T3> eqv3, Equiv<T4> eqv4, Equiv<T5> eqv5, Equiv<T6> eqv6) {
        return new Equiv.Tuple6Equiv<T1, T2, T3, T4, T5, T6>(eqv1, eqv2, eqv3, eqv4, eqv5, eqv6);
    }

    public <T1, T2, T3, T4, T5, T6, T7> Equiv<Tuple7<T1, T2, T3, T4, T5, T6, T7>> Tuple7(Equiv<T1> eqv1, Equiv<T2> eqv2, Equiv<T3> eqv3, Equiv<T4> eqv4, Equiv<T5> eqv5, Equiv<T6> eqv6, Equiv<T7> eqv7) {
        return new Equiv.Tuple7Equiv<T1, T2, T3, T4, T5, T6, T7>(eqv1, eqv2, eqv3, eqv4, eqv5, eqv6, eqv7);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8> Equiv<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> Tuple8(Equiv<T1> eqv1, Equiv<T2> eqv2, Equiv<T3> eqv3, Equiv<T4> eqv4, Equiv<T5> eqv5, Equiv<T6> eqv6, Equiv<T7> eqv7, Equiv<T8> eqv8) {
        return new Equiv.Tuple8Equiv<T1, T2, T3, T4, T5, T6, T7, T8>(eqv1, eqv2, eqv3, eqv4, eqv5, eqv6, eqv7, eqv8);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9> Equiv<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> Tuple9(Equiv<T1> eqv1, Equiv<T2> eqv2, Equiv<T3> eqv3, Equiv<T4> eqv4, Equiv<T5> eqv5, Equiv<T6> eqv6, Equiv<T7> eqv7, Equiv<T8> eqv8, Equiv<T9> eqv9) {
        return new Equiv.Tuple9Equiv<T1, T2, T3, T4, T5, T6, T7, T8, T9>(eqv1, eqv2, eqv3, eqv4, eqv5, eqv6, eqv7, eqv8, eqv9);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Equiv$.class);
    }

    private Equiv$() {
    }
}

