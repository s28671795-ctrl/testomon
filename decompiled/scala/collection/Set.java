/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Equals;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.collection.Iterable;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.Set$;
import scala.collection.SetOps;
import scala.collection.View;
import scala.collection.mutable.Builder;
import scala.math.Integral;
import scala.reflect.ScalaSignature;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005=4q\u0001D\u0007\u0011\u0002\u0007\u0005!\u0003C\u00033\u0001\u0011\u00051\u0007C\u00038\u0001\u0011\u0005\u0001\bC\u0003?\u0001\u0011\u0005s\bC\u0003B\u0001\u0011\u0005#\tC\u0003G\u0001\u0011\u0005s\t\u0003\u0004L\u0001\u0001&\t\u0006\u0014\u0005\u0006+\u0002!\tEV\u0004\u0006/6A\t\u0001\u0017\u0004\u0006\u00195A\t!\u0017\u0005\u0006C&!\tA\u0019\u0005\bG&\t\t\u0011\"\u0003e\u0005\r\u0019V\r\u001e\u0006\u0003\u001d=\t!bY8mY\u0016\u001cG/[8o\u0015\u0005\u0001\u0012!B:dC2\f7\u0001A\u000b\u0003'y\u0019b\u0001\u0001\u000b\u0019O1z\u0003CA\u000b\u0017\u001b\u0005y\u0011BA\f\u0010\u0005\u0019\te.\u001f*fMB\u0019\u0011D\u0007\u000f\u000e\u00035I!aG\u0007\u0003\u0011%#XM]1cY\u0016\u0004\"!\b\u0010\r\u0001\u0011)q\u0004\u0001b\u0001A\t\t\u0011)\u0005\u0002\"IA\u0011QCI\u0005\u0003G=\u0011qAT8uQ&tw\r\u0005\u0002\u0016K%\u0011ae\u0004\u0002\u0004\u0003:L\b#B\r)9)Z\u0013BA\u0015\u000e\u0005\u0019\u0019V\r^(qgB\u0011\u0011\u0004\u0001\t\u00043\u0001a\u0002CA\u000b.\u0013\tqsB\u0001\u0004FcV\fGn\u001d\t\u00053Ab\"&\u0003\u00022\u001b\t9\u0012\n^3sC\ndWMR1di>\u0014\u0018\u0010R3gCVdGo]\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003Q\u0002\"!F\u001b\n\u0005Yz!\u0001B+oSR\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0003sq\u0002\"!\u0006\u001e\n\u0005mz!a\u0002\"p_2,\u0017M\u001c\u0005\u0006{\t\u0001\r\u0001J\u0001\u0005i\"\fG/\u0001\u0004fcV\fGn\u001d\u000b\u0003s\u0001CQ!P\u0002A\u0002\u0011\n\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002\u0007B\u0011Q\u0003R\u0005\u0003\u000b>\u00111!\u00138u\u0003=IG/\u001a:bE2,g)Y2u_JLX#\u0001%\u0011\u0007eI%&\u0003\u0002K\u001b\ty\u0011\n^3sC\ndWMR1di>\u0014\u00180\u0001\u0007tiJLgn\u001a)sK\u001aL\u00070F\u0001N!\tq5+D\u0001P\u0015\t\u0001\u0016+\u0001\u0003mC:<'\"\u0001*\u0002\t)\fg/Y\u0005\u0003)>\u0013aa\u0015;sS:<\u0017\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u00035\u000b1aU3u!\tI\u0012b\u0005\u0002\n5B\u00191L\u0018\u0016\u000f\u0005ea\u0016BA/\u000e\u0003=IE/\u001a:bE2,g)Y2u_JL\u0018BA0a\u0005!!U\r\\3hCR,'BA/\u000e\u0003\u0019a\u0014N\\5u}Q\t\u0001,\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001f!\tqe-\u0003\u0002h\u001f\n1qJ\u00196fGRDC!C5m[B\u0011QC[\u0005\u0003W>\u0011\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\rAC\u0001C5m[\u0002")
public interface Set<A>
extends Iterable<A>,
SetOps<A, Set, Set<A>>,
Equals {
    public static <A> Builder<A, Set<A>> newBuilder() {
        return Set$.MODULE$.newBuilder();
    }

    public static Object from(IterableOnce it) {
        return Set$.MODULE$.from(it);
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        Set$ tabulate_this = Set$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$7$adapted(tabulate_this, n2, n3, n4, n5, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        Set$ tabulate_this = Set$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$5$adapted(tabulate_this, n2, n3, n4, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, int n3, Function3 f) {
        Set$ tabulate_this = Set$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$3$adapted(tabulate_this, n2, n3, f, arg_0));
    }

    public static Object tabulate(int n1, int n2, Function2 f) {
        Set$ tabulate_this = Set$.MODULE$;
        return tabulate_this.tabulate(n1, arg_0 -> IterableFactory.$anonfun$tabulate$1$adapted(tabulate_this, n2, f, arg_0));
    }

    public static Object tabulate(int n, Function1 f) {
        return Set$.MODULE$.from(new View.Tabulate(n, f));
    }

    public static Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        Set$ fill_this = Set$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$4(fill_this, n2, n3, n4, n5, elem));
    }

    public static Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        Set$ fill_this = Set$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$3(fill_this, n2, n3, n4, elem));
    }

    public static Object fill(int n1, int n2, int n3, Function0 elem) {
        Set$ fill_this = Set$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$2(fill_this, n2, n3, elem));
    }

    public static Object fill(int n1, int n2, Function0 elem) {
        Set$ fill_this = Set$.MODULE$;
        return fill_this.fill(n1, () -> IterableFactory.$anonfun$fill$1(fill_this, n2, elem));
    }

    public static Object fill(int n, Function0 elem) {
        return Set$.MODULE$.from(new View.Fill(n, elem));
    }

    public static Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(Set$.MODULE$, start2, end, step, evidence$4);
    }

    public static Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(Set$.MODULE$, start2, end, evidence$3);
    }

    public static Object unfold(Object init2, Function1 f) {
        return Set$.MODULE$.from(new View.Unfold(init2, f));
    }

    public static Object iterate(Object start2, int len, Function1 f) {
        return Set$.MODULE$.from(new View.Iterate<Object>(start2, len, f));
    }

    public static /* synthetic */ boolean canEqual$(Set $this, Object that) {
        return $this.canEqual(that);
    }

    @Override
    default public boolean canEqual(Object that) {
        return true;
    }

    public static /* synthetic */ boolean equals$(Set $this, Object that) {
        return $this.equals(that);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    default public boolean equals(Object that) {
        boolean bl;
        if (this == that) return true;
        if (!(that instanceof Set)) return false;
        Set set = (Set)that;
        if (!set.canEqual(this)) return false;
        if (this.size() != set.size()) return false;
        try {
            bl = this.subsetOf(set);
        }
        catch (ClassCastException classCastException) {
            return false;
        }
        if (!bl) return false;
        return true;
    }

    public static /* synthetic */ int hashCode$(Set $this) {
        return $this.hashCode();
    }

    default public int hashCode() {
        return MurmurHash3$.MODULE$.setHash(this);
    }

    public static /* synthetic */ IterableFactory iterableFactory$(Set $this) {
        return $this.iterableFactory();
    }

    @Override
    default public IterableFactory<Set> iterableFactory() {
        return Set$.MODULE$;
    }

    public static /* synthetic */ String stringPrefix$(Set $this) {
        return $this.stringPrefix();
    }

    @Override
    default public String stringPrefix() {
        return "Set";
    }

    public static /* synthetic */ String toString$(Set $this) {
        return $this.toString();
    }

    @Override
    default public String toString() {
        return Iterable.toString$(this);
    }

    public static void $init$(Set $this) {
    }
}

