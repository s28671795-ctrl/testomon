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
import scala.collection.View;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Iterable;
import scala.collection.mutable.Seq$;
import scala.collection.mutable.SeqOps;
import scala.math.Integral;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y3qa\u0002\u0005\u0011\u0002\u0007\u0005q\u0002C\u00030\u0001\u0011\u0005\u0001\u0007C\u00035\u0001\u0011\u0005SgB\u0003:\u0011!\u0005!HB\u0003\b\u0011!\u00051\bC\u0003D\t\u0011\u0005A\tC\u0004F\t\u0005\u0005I\u0011\u0002$\u0003\u0007M+\u0017O\u0003\u0002\n\u0015\u00059Q.\u001e;bE2,'BA\u0006\r\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u001b\u0005)1oY1mC\u000e\u0001QC\u0001\t\u001c'\u0019\u0001\u0011#\u0006\u0013(YA\u0011!cE\u0007\u0002\u0019%\u0011A\u0003\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\u0007Y9\u0012$D\u0001\t\u0013\tA\u0002B\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\tQ2\u0004\u0004\u0001\u0005\u000bq\u0001!\u0019A\u000f\u0003\u0003\u0005\u000b\"AH\u0011\u0011\u0005Iy\u0012B\u0001\u0011\r\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0005\u0012\n\u0005\rb!aA!osB\u0019QEJ\r\u000e\u0003)I!a\u0002\u0006\u0011\u000bYA\u0013DK\u0016\n\u0005%B!AB*fc>\u00038\u000f\u0005\u0002\u0017\u0001A\u0019a\u0003A\r\u0011\t\u0015j\u0013DK\u0005\u0003])\u0011q#\u0013;fe\u0006\u0014G.\u001a$bGR|'/\u001f#fM\u0006,H\u000e^:\u0002\r\u0011Jg.\u001b;%)\u0005\t\u0004C\u0001\n3\u0013\t\u0019DB\u0001\u0003V]&$\u0018aD5uKJ\f'\r\\3GC\u000e$xN]=\u0016\u0003Y\u00022!J\u001c+\u0013\tA$B\u0001\u0006TKF4\u0015m\u0019;pef\f1aU3r!\t1Ba\u0005\u0002\u0005yA\u0019Q\b\u0011\u0016\u000f\u0005\u0015r\u0014BA \u000b\u0003)\u0019V-\u001d$bGR|'/_\u0005\u0003\u0003\n\u0013\u0001\u0002R3mK\u001e\fG/\u001a\u0006\u0003\u007f)\ta\u0001P5oSRtD#\u0001\u001e\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0003\u001d\u0003\"\u0001S'\u000e\u0003%S!AS&\u0002\t1\fgn\u001a\u0006\u0002\u0019\u0006!!.\u0019<b\u0013\tq\u0015J\u0001\u0004PE*,7\r\u001e\u0015\u0005\tA\u001bF\u000b\u0005\u0002\u0013#&\u0011!\u000b\u0004\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012a\u0001\u0015\u0005\u0007A\u001bF\u000b")
public interface Seq<A>
extends Iterable<A>,
scala.collection.Seq<A>,
SeqOps<A, Seq, Seq<A>> {
    public static <A> Builder<A, Seq<A>> newBuilder() {
        return Seq$.MODULE$.newBuilder();
    }

    public static scala.collection.SeqOps from(IterableOnce it) {
        return Seq$.MODULE$.from(it);
    }

    public static scala.collection.SeqOps unapplySeq(scala.collection.SeqOps x) {
        return x;
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        Seq$ tabulate_this = Seq$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$7$adapted(tabulate_this, n2, n3, n4, n5, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        Seq$ tabulate_this = Seq$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$5$adapted(tabulate_this, n2, n3, n4, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f) {
        Seq$ tabulate_this = Seq$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$3$adapted(tabulate_this, n2, n3, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, Function2 f) {
        Seq$ tabulate_this = Seq$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$1$adapted(tabulate_this, n2, f, arg_0));
    }

    public static Object tabulate(int n, Function1 f) {
        return Seq$.MODULE$.from(new View.Tabulate(n, f));
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        Seq$ fill_this = Seq$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$4(fill_this, n2, n3, n4, n5, elem));
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        Seq$ fill_this = Seq$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$3(fill_this, n2, n3, n4, elem));
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem) {
        Seq$ fill_this = Seq$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$2(fill_this, n2, n3, elem));
    }

    public static Object fill(int n1, int n2, Function0 elem) {
        Seq$ fill_this = Seq$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$1(fill_this, n2, elem));
    }

    public static Object fill(int n, Function0 elem) {
        return Seq$.MODULE$.from(new View.Fill(n, elem));
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(Seq$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(Seq$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        return Seq$.MODULE$.from(new View.Unfold(init2, f));
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        return Seq$.MODULE$.from(new View.Iterate<Object>(start2, len, f));
    }

    public static /* synthetic */ SeqFactory iterableFactory$(Seq $this) {
        return $this.iterableFactory();
    }

    @Override
    default public SeqFactory<Seq> iterableFactory() {
        return Seq$.MODULE$;
    }

    public static void $init$(Seq $this) {
    }
}

