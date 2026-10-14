/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function0;
import scala.Function1;
import scala.collection.EvidenceIterableFactory;
import scala.collection.Factory;
import scala.collection.Iterator;
import scala.collection.Set;
import scala.collection.SortedIterableFactory;
import scala.collection.SortedSet$;
import scala.collection.SortedSetFactoryDefaults;
import scala.collection.View;
import scala.collection.mutable.Builder;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A4qa\u0003\u0007\u0011\u0002\u0007\u0005\u0011\u0003C\u00030\u0001\u0011\u0005\u0001\u0007C\u00035\u0001\u0011\u0005Q\u0007C\u00037\u0001\u0011\u0005q\u0007\u0003\u0004<\u0001\u0001&\t\u0006\u0010\u0005\u0006\u0011\u0002!\t%\u0013\u0005\f\u001f\u0002\u0001\n1!A\u0001\n\u0013\u0001&kB\u0003T\u0019!\u0005AKB\u0003\f\u0019!\u0005Q\u000bC\u0003^\u0011\u0011\u0005a\fC\u0004`\u0011\u0005\u0005I\u0011\u00021\u0003\u0013M{'\u000f^3e'\u0016$(BA\u0007\u000f\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u001f\u0005)1oY1mC\u000e\u0001QC\u0001\n\u001e'\u0015\u00011c\u0006\u0014,!\t!R#D\u0001\u000f\u0013\t1bB\u0001\u0004B]f\u0014VM\u001a\t\u00041eYR\"\u0001\u0007\n\u0005ia!aA*fiB\u0011A$\b\u0007\u0001\t\u0015q\u0002A1\u0001 \u0005\u0005\t\u0015C\u0001\u0011$!\t!\u0012%\u0003\u0002#\u001d\t9aj\u001c;iS:<\u0007C\u0001\u000b%\u0013\t)cBA\u0002B]f\u0004R\u0001G\u0014\u001cS)J!\u0001\u000b\u0007\u0003\u0019M{'\u000f^3e'\u0016$x\n]:\u0011\u0005a\u0001\u0001c\u0001\r\u00017A)\u0001\u0004L\u000e*]%\u0011Q\u0006\u0004\u0002\u0019'>\u0014H/\u001a3TKR4\u0015m\u0019;pef$UMZ1vYR\u001c\bC\u0001\r\u001a\u0003\u0019!\u0013N\\5uIQ\t\u0011\u0007\u0005\u0002\u0015e%\u00111G\u0004\u0002\u0005+:LG/\u0001\u0005v]N|'\u000f^3e+\u00059\u0012!F:peR,G-\u0013;fe\u0006\u0014G.\u001a$bGR|'/_\u000b\u0002qA\u0019\u0001$O\u0015\n\u0005ib!!F*peR,G-\u0013;fe\u0006\u0014G.\u001a$bGR|'/_\u0001\rgR\u0014\u0018N\\4Qe\u00164\u0017\u000e_\u000b\u0002{A\u0011a(\u0012\b\u0003\u007f\r\u0003\"\u0001\u0011\b\u000e\u0003\u0005S!A\u0011\t\u0002\rq\u0012xn\u001c;?\u0013\t!e\"\u0001\u0004Qe\u0016$WMZ\u0005\u0003\r\u001e\u0013aa\u0015;sS:<'B\u0001#\u000f\u0003\u0019)\u0017/^1mgR\u0011!*\u0014\t\u0003)-K!\u0001\u0014\b\u0003\u000f\t{w\u000e\\3b]\")a*\u0002a\u0001G\u0005!A\u000f[1u\u00031\u0019X\u000f]3sI\u0015\fX/\u00197t)\tQ\u0015\u000bC\u0003O\r\u0001\u00071%\u0003\u0002I3\u0005I1k\u001c:uK\u0012\u001cV\r\u001e\t\u00031!\u0019\"\u0001\u0003,\u0011\u0007]S\u0016F\u0004\u0002\u00191&\u0011\u0011\fD\u0001\u0016'>\u0014H/\u001a3Ji\u0016\u0014\u0018M\u00197f\r\u0006\u001cGo\u001c:z\u0013\tYFL\u0001\u0005EK2,w-\u0019;f\u0015\tIF\"\u0001\u0004=S:LGO\u0010\u000b\u0002)\u0006aqO]5uKJ+\u0007\u000f\\1dKR\t\u0011\r\u0005\u0002cO6\t1M\u0003\u0002eK\u0006!A.\u00198h\u0015\u00051\u0017\u0001\u00026bm\u0006L!\u0001[2\u0003\r=\u0013'.Z2uQ\u0011A!.\u001c8\u0011\u0005QY\u0017B\u00017\u000f\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0004Q\u00119!.\u001c8")
public interface SortedSet<A>
extends Set<A>,
SortedSetFactoryDefaults<A, SortedSet, Set> {
    public static Builder newBuilder(Object evidence$21) {
        return SortedSet$.MODULE$.newBuilder(evidence$21);
    }

    public static Factory evidenceIterableFactory(Object evidence$13) {
        return EvidenceIterableFactory.evidenceIterableFactory$(SortedSet$.MODULE$, evidence$13);
    }

    public static Object unfold(Object init2, Function1 f, Object evidence$11) {
        return SortedSet$.MODULE$.from(new View.Unfold(init2, f), evidence$11);
    }

    public static Object iterate(Object start2, int len, Function1 f, Object evidence$10) {
        return SortedSet$.MODULE$.from(new View.Iterate<Object>(start2, len, f), evidence$10);
    }

    public static Object tabulate(int n, Function1 f, Object evidence$9) {
        return SortedSet$.MODULE$.from(new View.Tabulate(n, f), evidence$9);
    }

    public static Object fill(int n, Function0 elem, Object evidence$8) {
        return SortedSet$.MODULE$.from(new View.Fill(n, elem), evidence$8);
    }

    public /* synthetic */ boolean scala$collection$SortedSet$$super$equals(Object var1);

    public static /* synthetic */ Set unsorted$(SortedSet $this) {
        return $this.unsorted();
    }

    @Override
    default public Set<A> unsorted() {
        return this;
    }

    public static /* synthetic */ SortedIterableFactory sortedIterableFactory$(SortedSet $this) {
        return $this.sortedIterableFactory();
    }

    @Override
    default public SortedIterableFactory<SortedSet> sortedIterableFactory() {
        return SortedSet$.MODULE$;
    }

    public static /* synthetic */ String stringPrefix$(SortedSet $this) {
        return $this.stringPrefix();
    }

    @Override
    default public String stringPrefix() {
        return "SortedSet";
    }

    public static /* synthetic */ boolean equals$(SortedSet $this, Object that) {
        return $this.equals(that);
    }

    @Override
    default public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof SortedSet) {
            SortedSet sortedSet = (SortedSet)that;
            Ordering ordering = sortedSet.ordering();
            Ordering ordering2 = this.ordering();
            if (!(ordering != null ? !ordering.equals(ordering2) : ordering2 != null)) {
                if (sortedSet.canEqual(this) && this.size() == sortedSet.size()) {
                    Iterator i1 = this.iterator();
                    Iterator i2 = sortedSet.iterator();
                    boolean allEqual = true;
                    while (allEqual && i1.hasNext()) {
                        allEqual = this.ordering().equiv(i1.next(), i2.next());
                    }
                    if (allEqual) {
                        return true;
                    }
                }
                return false;
            }
        }
        return this.scala$collection$SortedSet$$super$equals(that);
    }

    public static void $init$(SortedSet $this) {
    }
}

