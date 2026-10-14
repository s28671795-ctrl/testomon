/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.View;
import scala.collection.mutable.Builder;
import scala.collection.mutable.IndexedSeq$;
import scala.collection.mutable.IndexedSeqOps;
import scala.collection.mutable.Seq;
import scala.math.Integral;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y3qa\u0002\u0005\u0011\u0002\u0007\u0005q\u0002C\u00030\u0001\u0011\u0005\u0001\u0007C\u00035\u0001\u0011\u0005SgB\u0003:\u0011!\u0005!HB\u0003\b\u0011!\u00051\bC\u0003D\t\u0011\u0005A\tC\u0004F\t\u0005\u0005I\u0011\u0002$\u0003\u0015%sG-\u001a=fIN+\u0017O\u0003\u0002\n\u0015\u00059Q.\u001e;bE2,'BA\u0006\r\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u001b\u0005)1oY1mC\u000e\u0001QC\u0001\t\u001c'\u0019\u0001\u0011#\u0006\u0013(YA\u0011!cE\u0007\u0002\u0019%\u0011A\u0003\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\u0007Y9\u0012$D\u0001\t\u0013\tA\u0002BA\u0002TKF\u0004\"AG\u000e\r\u0001\u0011)A\u0004\u0001b\u0001;\t\tA+\u0005\u0002\u001fCA\u0011!cH\u0005\u0003A1\u0011qAT8uQ&tw\r\u0005\u0002\u0013E%\u00111\u0005\u0004\u0002\u0004\u0003:L\bcA\u0013'35\t!\"\u0003\u0002\b\u0015A)a\u0003K\r+W%\u0011\u0011\u0006\u0003\u0002\u000e\u0013:$W\r_3e'\u0016\fx\n]:\u0011\u0005Y\u0001\u0001c\u0001\f\u00013A!Q%L\r+\u0013\tq#BA\fJi\u0016\u0014\u0018M\u00197f\r\u0006\u001cGo\u001c:z\t\u00164\u0017-\u001e7ug\u00061A%\u001b8ji\u0012\"\u0012!\r\t\u0003%IJ!a\r\u0007\u0003\tUs\u0017\u000e^\u0001\u0010SR,'/\u00192mK\u001a\u000b7\r^8ssV\ta\u0007E\u0002&o)J!\u0001\u000f\u0006\u0003\u0015M+\u0017OR1di>\u0014\u00180\u0001\u0006J]\u0012,\u00070\u001a3TKF\u0004\"A\u0006\u0003\u0014\u0005\u0011a\u0004cA\u001fAU9\u0011QEP\u0005\u0003\u007f)\t!bU3r\r\u0006\u001cGo\u001c:z\u0013\t\t%I\u0001\u0005EK2,w-\u0019;f\u0015\ty$\"\u0001\u0004=S:LGO\u0010\u000b\u0002u\u0005aqO]5uKJ+\u0007\u000f\\1dKR\tq\t\u0005\u0002I\u001b6\t\u0011J\u0003\u0002K\u0017\u0006!A.\u00198h\u0015\u0005a\u0015\u0001\u00026bm\u0006L!AT%\u0003\r=\u0013'.Z2uQ\u0011!\u0001k\u0015+\u0011\u0005I\t\u0016B\u0001*\r\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0004Q\u0011\u0019\u0001k\u0015+")
public interface IndexedSeq<T>
extends Seq<T>,
scala.collection.IndexedSeq<T>,
IndexedSeqOps<T, IndexedSeq, IndexedSeq<T>> {
    public static <A> Builder<A, IndexedSeq<A>> newBuilder() {
        return IndexedSeq$.MODULE$.newBuilder();
    }

    public static SeqOps from(IterableOnce it) {
        return IndexedSeq$.MODULE$.from(it);
    }

    public static SeqOps unapplySeq(SeqOps x) {
        return x;
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        IndexedSeq$ tabulate_this = IndexedSeq$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$7$adapted(tabulate_this, n2, n3, n4, n5, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        IndexedSeq$ tabulate_this = IndexedSeq$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$5$adapted(tabulate_this, n2, n3, n4, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f) {
        IndexedSeq$ tabulate_this = IndexedSeq$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$3$adapted(tabulate_this, n2, n3, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, Function2 f) {
        IndexedSeq$ tabulate_this = IndexedSeq$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$1$adapted(tabulate_this, n2, f, arg_0));
    }

    public static Object tabulate(int n, Function1 f) {
        return IndexedSeq$.MODULE$.from(new View.Tabulate(n, f));
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        IndexedSeq$ fill_this = IndexedSeq$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$4(fill_this, n2, n3, n4, n5, elem));
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        IndexedSeq$ fill_this = IndexedSeq$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$3(fill_this, n2, n3, n4, elem));
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem) {
        IndexedSeq$ fill_this = IndexedSeq$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$2(fill_this, n2, n3, elem));
    }

    public static Object fill(int n1, int n2, Function0 elem) {
        IndexedSeq$ fill_this = IndexedSeq$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$1(fill_this, n2, elem));
    }

    public static Object fill(int n, Function0 elem) {
        return IndexedSeq$.MODULE$.from(new View.Fill(n, elem));
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(IndexedSeq$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(IndexedSeq$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        return IndexedSeq$.MODULE$.from(new View.Unfold(init2, f));
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        return IndexedSeq$.MODULE$.from(new View.Iterate<Object>(start2, len, f));
    }

    public static /* synthetic */ SeqFactory iterableFactory$(IndexedSeq $this) {
        return $this.iterableFactory();
    }

    @Override
    default public SeqFactory<IndexedSeq> iterableFactory() {
        return IndexedSeq$.MODULE$;
    }

    public static void $init$(IndexedSeq $this) {
    }
}

