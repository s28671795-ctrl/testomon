/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.LinearSeq$;
import scala.collection.LinearSeqOps;
import scala.collection.Seq;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.View;
import scala.collection.mutable.Builder;
import scala.math.Integral;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}3q\u0001C\u0005\u0011\u0002\u0007\u0005a\u0002C\u0003,\u0001\u0011\u0005A\u0006\u0003\u00041\u0001\u0001&\t&\r\u0005\u0006{\u0001!\tEP\u0004\u0006\u0005&A\ta\u0011\u0004\u0006\u0011%A\t\u0001\u0012\u0005\u0006\u0019\u0016!\t!\u0014\u0005\b\u001d\u0016\t\t\u0011\"\u0003P\u0005%a\u0015N\\3beN+\u0017O\u0003\u0002\u000b\u0017\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u00031\tQa]2bY\u0006\u001c\u0001!\u0006\u0002\u00105M)\u0001\u0001\u0005\u000b$QA\u0011\u0011CE\u0007\u0002\u0017%\u00111c\u0003\u0002\u0007\u0003:L(+\u001a4\u0011\u0007U1\u0002$D\u0001\n\u0013\t9\u0012BA\u0002TKF\u0004\"!\u0007\u000e\r\u0001\u001111\u0004\u0001CC\u0002q\u0011\u0011!Q\t\u0003;\u0001\u0002\"!\u0005\u0010\n\u0005}Y!a\u0002(pi\"Lgn\u001a\t\u0003#\u0005J!AI\u0006\u0003\u0007\u0005s\u0017\u0010E\u0003\u0016Ia1s%\u0003\u0002&\u0013\taA*\u001b8fCJ\u001cV-](qgB\u0011Q\u0003\u0001\t\u0004+\u0001A\u0002\u0003B\u000b*1\u0019J!AK\u0005\u0003/%#XM]1cY\u00164\u0015m\u0019;pef$UMZ1vYR\u001c\u0018A\u0002\u0013j]&$H\u0005F\u0001.!\t\tb&\u0003\u00020\u0017\t!QK\\5u\u00031\u0019HO]5oOB\u0013XMZ5y+\u0005\u0011\u0004CA\u001a;\u001d\t!\u0004\b\u0005\u00026\u00175\taG\u0003\u00028\u001b\u00051AH]8pizJ!!O\u0006\u0002\rA\u0013X\rZ3g\u0013\tYDH\u0001\u0004TiJLgn\u001a\u0006\u0003s-\tq\"\u001b;fe\u0006\u0014G.\u001a$bGR|'/_\u000b\u0002\u007fA\u0019Q\u0003\u0011\u0014\n\u0005\u0005K!AC*fc\u001a\u000b7\r^8ss\u0006IA*\u001b8fCJ\u001cV-\u001d\t\u0003+\u0015\u0019\"!B#\u0011\u0007\u0019KeE\u0004\u0002\u0016\u000f&\u0011\u0001*C\u0001\u000b'\u0016\fh)Y2u_JL\u0018B\u0001&L\u0005!!U\r\\3hCR,'B\u0001%\n\u0003\u0019a\u0014N\\5u}Q\t1)\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001Q!\t\tf+D\u0001S\u0015\t\u0019F+\u0001\u0003mC:<'\"A+\u0002\t)\fg/Y\u0005\u0003/J\u0013aa\u00142kK\u000e$\b\u0006B\u0003Z9v\u0003\"!\u0005.\n\u0005m[!\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\u0019\u0001\u0006\u0002\u0003Z9v\u0003")
public interface LinearSeq<A>
extends Seq<A>,
LinearSeqOps<A, LinearSeq, LinearSeq<A>> {
    public static <A> Builder<A, LinearSeq<A>> newBuilder() {
        return LinearSeq$.MODULE$.newBuilder();
    }

    public static SeqOps from(IterableOnce it) {
        return LinearSeq$.MODULE$.from(it);
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
        return LinearSeq$.MODULE$.from(new View.Tabulate(n, f));
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
        return LinearSeq$.MODULE$.from(new View.Fill(n, elem));
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(LinearSeq$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(LinearSeq$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        return LinearSeq$.MODULE$.from(new View.Unfold(init2, f));
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        return LinearSeq$.MODULE$.from(new View.Iterate<Object>(start2, len, f));
    }

    public static /* synthetic */ String stringPrefix$(LinearSeq $this) {
        return $this.stringPrefix();
    }

    @Override
    default public String stringPrefix() {
        return "LinearSeq";
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

