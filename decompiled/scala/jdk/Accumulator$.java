/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.Option;
import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.View;
import scala.collection.immutable.NumericRange;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.jdk.Accumulator;
import scala.jdk.Accumulator$AccumulatorFactoryShape$;
import scala.jdk.AnyAccumulator;
import scala.math.Integral;
import scala.runtime.BoxesRunTime;

public final class Accumulator$ {
    public static final Accumulator$ MODULE$ = new Accumulator$();

    public <A, C> Factory<A, C> toFactory(Accumulator$ sa, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        return canAccumulate.factory();
    }

    public <A, C> C from(IterableOnce<A> source2, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        return source2.iterator().to(canAccumulate.factory());
    }

    public <A, C> C empty(Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        return canAccumulate.empty();
    }

    public <A, C> C apply(Seq<A> elems, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        return canAccumulate.factory().fromSpecific(elems);
    }

    public <A, C> C iterate(A start2, int len, Function1<A, A> f, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        return this.from(new View.Iterate<A>(start2, len, f), canAccumulate);
    }

    public <A, S, C> C unfold(S init2, Function1<S, Option<Tuple2<A, S>>> f, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        return this.from(new View.Unfold<A, S>(init2, f), canAccumulate);
    }

    public <A, C> C range(A start2, A end, Integral<A> evidence$1, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        Object apply_step = evidence$1.one();
        Object var5_5 = null;
        return this.from(new NumericRange.Exclusive<A>(start2, end, apply_step, evidence$1), canAccumulate);
    }

    public <A, C> C range(A start2, A end, A step, Integral<A> evidence$2, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        return this.from(new NumericRange.Exclusive<A>(start2, end, step, evidence$2), canAccumulate);
    }

    public <A, C> Builder<A, C> newBuilder(Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        return canAccumulate.factory().newBuilder();
    }

    public <A, C> C fill(int n, Function0<A> elem, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        return this.from(new View.Fill<A>(n, elem), canAccumulate);
    }

    /*
     * WARNING - void declaration
     */
    public <A, C> AnyAccumulator<C> fill(int n1, int n2, Function0<A> elem, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        void fill_canAccumulate;
        Accumulator$AccumulatorFactoryShape$ anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var5_5 = null;
        Accumulator.AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function0<Object> & Serializable fill_elem = (Function0<Object> & Serializable)() -> MODULE$.fill(n2, elem, canAccumulate);
        return (AnyAccumulator)this.from(new View.Fill(n1, fill_elem), (Accumulator.AccumulatorFactoryShape<A, C>)fill_canAccumulate);
    }

    /*
     * WARNING - void declaration
     */
    public <A, C> AnyAccumulator<AnyAccumulator<C>> fill(int n1, int n2, int n3, Function0<A> elem, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        void fill_canAccumulate;
        Accumulator$AccumulatorFactoryShape$ anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var6_6 = null;
        Accumulator.AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function0<AnyAccumulator> & Serializable fill_elem = (Function0<AnyAccumulator> & Serializable)() -> MODULE$.fill(n2, n3, elem, canAccumulate);
        return (AnyAccumulator)this.from(new View.Fill(n1, fill_elem), (Accumulator.AccumulatorFactoryShape<A, C>)fill_canAccumulate);
    }

    /*
     * WARNING - void declaration
     */
    public <A, C> AnyAccumulator<AnyAccumulator<AnyAccumulator<C>>> fill(int n1, int n2, int n3, int n4, Function0<A> elem, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        void fill_canAccumulate;
        Accumulator$AccumulatorFactoryShape$ anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var7_7 = null;
        Accumulator.AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function0<AnyAccumulator> & Serializable fill_elem = (Function0<AnyAccumulator> & Serializable)() -> MODULE$.fill(n2, n3, n4, elem, canAccumulate);
        return (AnyAccumulator)this.from(new View.Fill(n1, fill_elem), (Accumulator.AccumulatorFactoryShape<A, C>)fill_canAccumulate);
    }

    /*
     * WARNING - void declaration
     */
    public <A, C> AnyAccumulator<AnyAccumulator<AnyAccumulator<AnyAccumulator<C>>>> fill(int n1, int n2, int n3, int n4, int n5, Function0<A> elem, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        void fill_canAccumulate;
        Accumulator$AccumulatorFactoryShape$ anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var8_8 = null;
        Accumulator.AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function0<AnyAccumulator> & Serializable fill_elem = (Function0<AnyAccumulator> & Serializable)() -> MODULE$.fill(n2, n3, n4, n5, elem, canAccumulate);
        return (AnyAccumulator)this.from(new View.Fill(n1, fill_elem), (Accumulator.AccumulatorFactoryShape<A, C>)fill_canAccumulate);
    }

    public <A, C> C tabulate(int n, Function1<Object, A> f, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        return this.from(new View.Tabulate<A>(n, f), canAccumulate);
    }

    /*
     * WARNING - void declaration
     */
    public <A, C> AnyAccumulator<C> tabulate(int n1, int n2, Function2<Object, Object, A> f, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        void tabulate_canAccumulate;
        Accumulator$AccumulatorFactoryShape$ anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var5_5 = null;
        Accumulator.AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function1<Object, Object> & Serializable tabulate_f = (Function1<Object, Object> & Serializable)i1 -> Accumulator$.$anonfun$tabulate$1(n2, f, canAccumulate, BoxesRunTime.unboxToInt(i1));
        return (AnyAccumulator)this.from(new View.Tabulate(n1, tabulate_f), (Accumulator.AccumulatorFactoryShape<A, C>)tabulate_canAccumulate);
    }

    /*
     * WARNING - void declaration
     */
    public <A, C> AnyAccumulator<AnyAccumulator<C>> tabulate(int n1, int n2, int n3, Function3<Object, Object, Object, A> f, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        void tabulate_canAccumulate;
        Accumulator$AccumulatorFactoryShape$ anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var6_6 = null;
        Accumulator.AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function1<Object, AnyAccumulator> & Serializable tabulate_f = (Function1<Object, AnyAccumulator> & Serializable)i1 -> Accumulator$.$anonfun$tabulate$3(n2, n3, f, canAccumulate, BoxesRunTime.unboxToInt(i1));
        return (AnyAccumulator)this.from(new View.Tabulate(n1, tabulate_f), (Accumulator.AccumulatorFactoryShape<A, C>)tabulate_canAccumulate);
    }

    /*
     * WARNING - void declaration
     */
    public <A, C> AnyAccumulator<AnyAccumulator<AnyAccumulator<C>>> tabulate(int n1, int n2, int n3, int n4, Function4<Object, Object, Object, Object, A> f, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        void tabulate_canAccumulate;
        Accumulator$AccumulatorFactoryShape$ anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var7_7 = null;
        Accumulator.AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function1<Object, AnyAccumulator> & Serializable tabulate_f = (Function1<Object, AnyAccumulator> & Serializable)i1 -> Accumulator$.$anonfun$tabulate$5(n2, n3, n4, f, canAccumulate, BoxesRunTime.unboxToInt(i1));
        return (AnyAccumulator)this.from(new View.Tabulate(n1, tabulate_f), (Accumulator.AccumulatorFactoryShape<A, C>)tabulate_canAccumulate);
    }

    /*
     * WARNING - void declaration
     */
    public <A, C> AnyAccumulator<AnyAccumulator<AnyAccumulator<AnyAccumulator<C>>>> tabulate(int n1, int n2, int n3, int n4, int n5, Function5<Object, Object, Object, Object, Object, A> f, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        void tabulate_canAccumulate;
        Accumulator$AccumulatorFactoryShape$ anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var8_8 = null;
        Accumulator.AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function1<Object, AnyAccumulator> & Serializable tabulate_f = (Function1<Object, AnyAccumulator> & Serializable)i1 -> Accumulator$.$anonfun$tabulate$7(n2, n3, n4, n5, f, canAccumulate, BoxesRunTime.unboxToInt(i1));
        return (AnyAccumulator)this.from(new View.Tabulate(n1, tabulate_f), (Accumulator.AccumulatorFactoryShape<A, C>)tabulate_canAccumulate);
    }

    public <A, C> C concat(Seq<Iterable<A>> xss, Accumulator.AccumulatorFactoryShape<A, C> canAccumulate) {
        if (xss.isEmpty()) {
            return canAccumulate.empty();
        }
        Builder b = canAccumulate.factory().newBuilder();
        xss.foreach((Function1<Iterable, Builder> & Serializable)x$11 -> (Builder)b.$plus$plus$eq((IterableOnce)x$11));
        return b.result();
    }

    public static final /* synthetic */ Object $anonfun$tabulate$1(int n2$5, Function2 f$1, Accumulator.AccumulatorFactoryShape canAccumulate$5, int i1) {
        Function1<Object, Object> & Serializable tabulate_f = (Function1<Object, Object> & Serializable)x$1 -> f$1.apply(BoxesRunTime.boxToInteger(i1), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$1)));
        return MODULE$.from(new View.Tabulate(n2$5, tabulate_f), canAccumulate$5);
    }

    /*
     * WARNING - void declaration
     */
    public static final /* synthetic */ AnyAccumulator $anonfun$tabulate$3(int n2$6, int n3$4, Function3 f$2, Accumulator.AccumulatorFactoryShape canAccumulate$6, int i1) {
        void tabulate_tabulate_canAccumulate;
        void tabulate_f;
        Function2<Object, Object, Object> & Serializable intersect = (Function2<Object, Object, Object> & Serializable)(x$2, x$3) -> f$2.apply(BoxesRunTime.boxToInteger(i1), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$2)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$3)));
        Accumulator$ tabulate_this = MODULE$;
        Accumulator$AccumulatorFactoryShape$ tabulate_anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var7_7 = null;
        Accumulator.AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = tabulate_anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function1<Object, Object> & Serializable tabulate_tabulate_f = arg_0 -> Accumulator$.$anonfun$tabulate$1$adapted(n3$4, (Function2)tabulate_f, canAccumulate$6, arg_0);
        return (AnyAccumulator)tabulate_this.from(new View.Tabulate(n2$6, tabulate_tabulate_f), (Accumulator.AccumulatorFactoryShape)tabulate_tabulate_canAccumulate);
    }

    /*
     * WARNING - void declaration
     */
    public static final /* synthetic */ AnyAccumulator $anonfun$tabulate$5(int n2$7, int n3$5, int n4$3, Function4 f$3, Accumulator.AccumulatorFactoryShape canAccumulate$7, int i1) {
        void tabulate_tabulate_canAccumulate;
        void tabulate_f;
        Function3<Object, Object, Object, Object> & Serializable intersect = (Function3<Object, Object, Object, Object> & Serializable)(x$4, x$5, x$6) -> f$3.apply(BoxesRunTime.boxToInteger(i1), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$4)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$5)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$6)));
        Accumulator$ tabulate_this = MODULE$;
        Accumulator$AccumulatorFactoryShape$ tabulate_anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var8_8 = null;
        Accumulator.AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = tabulate_anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function1<Object, AnyAccumulator> & Serializable tabulate_tabulate_f = arg_0 -> Accumulator$.$anonfun$tabulate$3$adapted(n3$5, n4$3, (Function3)tabulate_f, canAccumulate$7, arg_0);
        return (AnyAccumulator)tabulate_this.from(new View.Tabulate(n2$7, tabulate_tabulate_f), (Accumulator.AccumulatorFactoryShape)tabulate_tabulate_canAccumulate);
    }

    /*
     * WARNING - void declaration
     */
    public static final /* synthetic */ AnyAccumulator $anonfun$tabulate$7(int n2$8, int n3$6, int n4$4, int n5$2, Function5 f$4, Accumulator.AccumulatorFactoryShape canAccumulate$8, int i1) {
        void tabulate_tabulate_canAccumulate;
        void tabulate_f;
        Function4<Object, Object, Object, Object, Object> & Serializable intersect = (Function4<Object, Object, Object, Object, Object> & Serializable)(x$7, x$8, x$9, x$10) -> f$4.apply(BoxesRunTime.boxToInteger(i1), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$7)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$8)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$9)), BoxesRunTime.boxToInteger(BoxesRunTime.unboxToInt(x$10)));
        Accumulator$ tabulate_this = MODULE$;
        Accumulator$AccumulatorFactoryShape$ tabulate_anyAccumulatorFactoryShape_this = Accumulator$AccumulatorFactoryShape$.MODULE$;
        Object var9_9 = null;
        Accumulator.AccumulatorFactoryShape<Object, AnyAccumulator<Object>> accumulatorFactoryShape = tabulate_anyAccumulatorFactoryShape_this.scala$jdk$Accumulator$LowPriorityAccumulatorFactoryShape$$anyAccumulatorFactoryShapePrototype();
        Function1<Object, AnyAccumulator> & Serializable tabulate_tabulate_f = arg_0 -> Accumulator$.$anonfun$tabulate$5$adapted(n3$6, n4$4, n5$2, (Function4)tabulate_f, canAccumulate$8, arg_0);
        return (AnyAccumulator)tabulate_this.from(new View.Tabulate(n2$8, tabulate_tabulate_f), (Accumulator.AccumulatorFactoryShape)tabulate_tabulate_canAccumulate);
    }

    private Accumulator$() {
    }
}

