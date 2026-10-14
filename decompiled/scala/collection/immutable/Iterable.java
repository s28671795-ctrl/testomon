/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.View;
import scala.collection.immutable.Iterable$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.math.Integral;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005%4q\u0001C\u0005\u0011\u0002\u0007\u0005\u0001\u0003C\u0003.\u0001\u0011\u0005a\u0006C\u00033\u0001\u0011\u00053gB\u00038\u0013!\u0005\u0001HB\u0003\t\u0013!\u0005\u0011\bC\u0003B\t\u0011\u0005!\tC\u0003D\t\u0011\u0005C\tC\u0004Y\t\u0005\u0005I\u0011B-\u0003\u0011%#XM]1cY\u0016T!AC\u0006\u0002\u0013%lW.\u001e;bE2,'B\u0001\u0007\u000e\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u001d\u0005)1oY1mC\u000e\u0001QCA\t\u001c'\u0015\u0001!C\u0006\u0013+!\t\u0019B#D\u0001\u000e\u0013\t)RB\u0001\u0004B]f\u0014VM\u001a\t\u0004/aIR\"A\u0006\n\u0005!Y\u0001C\u0001\u000e\u001c\u0019\u0001!a\u0001\b\u0001\u0005\u0006\u0004i\"!A!\u0012\u0005y\t\u0003CA\n \u0013\t\u0001SBA\u0004O_RD\u0017N\\4\u0011\u0005M\u0011\u0013BA\u0012\u000e\u0005\r\te.\u001f\t\u0006/\u0015Jr%K\u0005\u0003M-\u00111\"\u0013;fe\u0006\u0014G.Z(qgB\u0011\u0001\u0006A\u0007\u0002\u0013A\u0019\u0001\u0006A\r\u0011\t]Y\u0013dJ\u0005\u0003Y-\u0011q#\u0013;fe\u0006\u0014G.\u001a$bGR|'/\u001f#fM\u0006,H\u000e^:\u0002\r\u0011Jg.\u001b;%)\u0005y\u0003CA\n1\u0013\t\tTB\u0001\u0003V]&$\u0018aD5uKJ\f'\r\\3GC\u000e$xN]=\u0016\u0003Q\u00022aF\u001b(\u0013\t14BA\bJi\u0016\u0014\u0018M\u00197f\r\u0006\u001cGo\u001c:z\u0003!IE/\u001a:bE2,\u0007C\u0001\u0015\u0005'\t!!\bE\u0002<}\u001dr!a\u0006\u001f\n\u0005uZ\u0011aD%uKJ\f'\r\\3GC\u000e$xN]=\n\u0005}\u0002%\u0001\u0003#fY\u0016<\u0017\r^3\u000b\u0005uZ\u0011A\u0002\u001fj]&$h\bF\u00019\u0003\u00111'o\\7\u0016\u0005\u0015CEC\u0001$K!\rA\u0003a\u0012\t\u00035!#Q!\u0013\u0004C\u0002u\u0011\u0011!\u0012\u0005\u0006\u0017\u001a\u0001\r\u0001T\u0001\u0003SR\u00042!T+H\u001d\tq5K\u0004\u0002P%6\t\u0001K\u0003\u0002R\u001f\u00051AH]8pizJ\u0011AD\u0005\u0003)6\tq\u0001]1dW\u0006<W-\u0003\u0002W/\na\u0011\n^3sC\ndWm\u00148dK*\u0011A+D\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u00025B\u00111\fY\u0007\u00029*\u0011QLX\u0001\u0005Y\u0006twMC\u0001`\u0003\u0011Q\u0017M^1\n\u0005\u0005d&AB(cU\u0016\u001cG\u000f\u000b\u0003\u0005G\u001a<\u0007CA\ne\u0013\t)WB\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t1\u0001\u000b\u0003\u0004G\u001a<\u0007")
public interface Iterable<A>
extends scala.collection.Iterable<A> {
    public static <E> Iterable<E> from(IterableOnce<E> it) {
        return Iterable$.MODULE$.from((IterableOnce)it);
    }

    public static <A> Builder<A, Iterable<A>> newBuilder() {
        return Iterable$.MODULE$.newBuilder();
    }

    public static Object apply(Seq elems) {
        return Iterable$.MODULE$.apply(elems);
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        Iterable$ tabulate_this = Iterable$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$7$adapted(tabulate_this, n2, n3, n4, n5, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        Iterable$ tabulate_this = Iterable$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$5$adapted(tabulate_this, n2, n3, n4, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f) {
        Iterable$ tabulate_this = Iterable$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$3$adapted(tabulate_this, n2, n3, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, Function2 f) {
        Iterable$ tabulate_this = Iterable$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$1$adapted(tabulate_this, n2, f, arg_0));
    }

    public static Object tabulate(int n, Function1 f) {
        Iterable$ tabulate_this = Iterable$.MODULE$;
        View.Tabulate from_it = new View.Tabulate(n, f);
        return tabulate_this.from((IterableOnce)from_it);
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        Iterable$ fill_this = Iterable$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$4(fill_this, n2, n3, n4, n5, elem));
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        Iterable$ fill_this = Iterable$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$3(fill_this, n2, n3, n4, elem));
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem) {
        Iterable$ fill_this = Iterable$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$2(fill_this, n2, n3, elem));
    }

    public static Object fill(int n1, int n2, Function0 elem) {
        Iterable$ fill_this = Iterable$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$1(fill_this, n2, elem));
    }

    public static Object fill(int n, Function0 elem) {
        Iterable$ fill_this = Iterable$.MODULE$;
        View.Fill from_it = new View.Fill(n, elem);
        return fill_this.from((IterableOnce)from_it);
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(Iterable$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(Iterable$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        Iterable$ unfold_this = Iterable$.MODULE$;
        View.Unfold from_it = new View.Unfold(init2, f);
        return unfold_this.from((IterableOnce)from_it);
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        Iterable$ iterate_this = Iterable$.MODULE$;
        View.Iterate<Object> from_it = new View.Iterate<Object>(start2, len, f);
        return iterate_this.from((IterableOnce)from_it);
    }

    public static /* synthetic */ IterableFactory iterableFactory$(Iterable $this) {
        return $this.iterableFactory();
    }

    @Override
    default public IterableFactory<Iterable> iterableFactory() {
        return Iterable$.MODULE$;
    }

    public static void $init$(Iterable $this) {
    }
}

