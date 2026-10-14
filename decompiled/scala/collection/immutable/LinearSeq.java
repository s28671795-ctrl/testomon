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
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.View;
import scala.collection.immutable.LinearSeq$;
import scala.collection.immutable.LinearSeqOps;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.math.Integral;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r4q\u0001C\u0005\u0011\u0002\u0007\u0005\u0001\u0003C\u00031\u0001\u0011\u0005\u0011\u0007C\u00036\u0001\u0011\u0005cgB\u0003;\u0013!\u00051HB\u0003\t\u0013!\u0005A\bC\u0003E\t\u0011\u0005Q\tC\u0003G\t\u0011\u0005s\tC\u0004S\t\u0005\u0005I\u0011B*\u0003\u00131Kg.Z1s'\u0016\f(B\u0001\u0006\f\u0003%IW.\\;uC\ndWM\u0003\u0002\r\u001b\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u00039\tQa]2bY\u0006\u001c\u0001!\u0006\u0002\u00129M1\u0001A\u0005\f&Q5\u0002\"a\u0005\u000b\u000e\u00035I!!F\u0007\u0003\r\u0005s\u0017PU3g!\r9\u0002DG\u0007\u0002\u0013%\u0011\u0011$\u0003\u0002\u0004'\u0016\f\bCA\u000e\u001d\u0019\u0001!a!\b\u0001\u0005\u0006\u0004q\"!A!\u0012\u0005}\u0011\u0003CA\n!\u0013\t\tSBA\u0004O_RD\u0017N\\4\u0011\u0005M\u0019\u0013B\u0001\u0013\u000e\u0005\r\te.\u001f\t\u0004M\u001dRR\"A\u0006\n\u0005!Y\u0001#B\f*5-b\u0013B\u0001\u0016\n\u00051a\u0015N\\3beN+\u0017o\u00149t!\t9\u0002\u0001E\u0002\u0018\u0001i\u0001BA\n\u0018\u001bW%\u0011qf\u0003\u0002\u0018\u0013R,'/\u00192mK\u001a\u000b7\r^8ss\u0012+g-Y;miN\fa\u0001J5oSR$C#\u0001\u001a\u0011\u0005M\u0019\u0014B\u0001\u001b\u000e\u0005\u0011)f.\u001b;\u0002\u001f%$XM]1cY\u00164\u0015m\u0019;pef,\u0012a\u000e\t\u0004MaZ\u0013BA\u001d\f\u0005)\u0019V-\u001d$bGR|'/_\u0001\n\u0019&tW-\u0019:TKF\u0004\"a\u0006\u0003\u0014\u0005\u0011i\u0004c\u0001 BW9\u0011aeP\u0005\u0003\u0001.\t!bU3r\r\u0006\u001cGo\u001c:z\u0013\t\u00115I\u0001\u0005EK2,w-\u0019;f\u0015\t\u00015\"\u0001\u0004=S:LGO\u0010\u000b\u0002w\u0005!aM]8n+\tA5\n\u0006\u0002J\u001bB\u0019q\u0003\u0001&\u0011\u0005mYE!\u0002'\u0007\u0005\u0004q\"!A#\t\u000b93\u0001\u0019A(\u0002\u0005%$\bc\u0001\u0014Q\u0015&\u0011\u0011k\u0003\u0002\r\u0013R,'/\u00192mK>s7-Z\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0002)B\u0011QKW\u0007\u0002-*\u0011q\u000bW\u0001\u0005Y\u0006twMC\u0001Z\u0003\u0011Q\u0017M^1\n\u0005m3&AB(cU\u0016\u001cG\u000f\u000b\u0003\u0005;\u0002\f\u0007CA\n_\u0013\tyVB\u0001\tTKJL\u0017\r\u001c,feNLwN\\+J\t\u0006)a/\u00197vKz\t1\u0001\u000b\u0003\u0004;\u0002\f\u0007")
public interface LinearSeq<A>
extends Seq<A>,
scala.collection.LinearSeq<A>,
LinearSeqOps<A, LinearSeq, LinearSeq<A>> {
    public static <E> LinearSeq<E> from(IterableOnce<E> it) {
        return LinearSeq$.MODULE$.from((IterableOnce)it);
    }

    public static <A> Builder<A, LinearSeq<A>> newBuilder() {
        return LinearSeq$.MODULE$.newBuilder();
    }

    public static SeqOps unapplySeq(SeqOps x) {
        return x;
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        LinearSeq$ tabulate_this = LinearSeq$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$7$adapted(tabulate_this, n2, n3, n4, n5, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        LinearSeq$ tabulate_this = LinearSeq$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$5$adapted(tabulate_this, n2, n3, n4, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f) {
        LinearSeq$ tabulate_this = LinearSeq$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$3$adapted(tabulate_this, n2, n3, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, Function2 f) {
        LinearSeq$ tabulate_this = LinearSeq$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$1$adapted(tabulate_this, n2, f, arg_0));
    }

    public static Object tabulate(int n, Function1 f) {
        LinearSeq$ tabulate_this = LinearSeq$.MODULE$;
        View.Tabulate from_source = new View.Tabulate(n, f);
        return tabulate_this.from((IterableOnce)from_source);
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        LinearSeq$ fill_this = LinearSeq$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$4(fill_this, n2, n3, n4, n5, elem));
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        LinearSeq$ fill_this = LinearSeq$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$3(fill_this, n2, n3, n4, elem));
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem) {
        LinearSeq$ fill_this = LinearSeq$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$2(fill_this, n2, n3, elem));
    }

    public static Object fill(int n1, int n2, Function0 elem) {
        LinearSeq$ fill_this = LinearSeq$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$1(fill_this, n2, elem));
    }

    public static Object fill(int n, Function0 elem) {
        LinearSeq$ fill_this = LinearSeq$.MODULE$;
        View.Fill from_source = new View.Fill(n, elem);
        return fill_this.from((IterableOnce)from_source);
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(LinearSeq$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(LinearSeq$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        LinearSeq$ unfold_this = LinearSeq$.MODULE$;
        View.Unfold from_source = new View.Unfold(init2, f);
        return unfold_this.from((IterableOnce)from_source);
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        LinearSeq$ iterate_this = LinearSeq$.MODULE$;
        View.Iterate<Object> from_source = new View.Iterate<Object>(start2, len, f);
        return iterate_this.from((IterableOnce)from_source);
    }

    public static /* synthetic */ SeqFactory iterableFactory$(LinearSeq $this) {
        return $this.iterableFactory();
    }

    @Override
    default public SeqFactory<LinearSeq> iterableFactory() {
        return LinearSeq$.MODULE$;
    }

    public static void $init$(LinearSeq $this) {
    }
}

