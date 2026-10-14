/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Function0;
import scala.Function1;
import scala.collection.EvidenceIterableFactory;
import scala.collection.Factory;
import scala.collection.SortedIterableFactory;
import scala.collection.View;
import scala.collection.immutable.Set;
import scala.collection.immutable.SortedSet$;
import scala.collection.immutable.SortedSetOps;
import scala.collection.mutable.Builder;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4q!\u0003\u0006\u0011\u0002\u0007\u0005\u0011\u0003C\u00033\u0001\u0011\u00051\u0007C\u00038\u0001\u0011\u0005\u0003\bC\u0003:\u0001\u0011\u0005#hB\u0003?\u0015!\u0005qHB\u0003\n\u0015!\u0005\u0001\tC\u0003I\u000b\u0011\u0005\u0011\nC\u0003K\u000b\u0011\u00053\nC\u0004a\u000b\u0005\u0005I\u0011B1\u0003\u0013M{'\u000f^3e'\u0016$(BA\u0006\r\u0003%IW.\\;uC\ndWM\u0003\u0002\u000e\u001d\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003=\tQa]2bY\u0006\u001c\u0001!\u0006\u0002\u0013;M1\u0001aE\f'S9\u0002\"\u0001F\u000b\u000e\u00039I!A\u0006\b\u0003\r\u0005s\u0017PU3g!\rA\u0012dG\u0007\u0002\u0015%\u0011!D\u0003\u0002\u0004'\u0016$\bC\u0001\u000f\u001e\u0019\u0001!QA\b\u0001C\u0002}\u0011\u0011!Q\t\u0003A\r\u0002\"\u0001F\u0011\n\u0005\tr!a\u0002(pi\"Lgn\u001a\t\u0003)\u0011J!!\n\b\u0003\u0007\u0005s\u0017\u0010E\u0002(Qmi\u0011\u0001D\u0005\u0003\u00131\u0001R\u0001\u0007\u0016\u001cY5J!a\u000b\u0006\u0003\u0019M{'\u000f^3e'\u0016$x\n]:\u0011\u0005a\u0001\u0001c\u0001\r\u00017A)qeL\u000e-c%\u0011\u0001\u0007\u0004\u0002\u0019'>\u0014H/\u001a3TKR4\u0015m\u0019;pef$UMZ1vYR\u001c\bC\u0001\r\u001a\u0003\u0019!\u0013N\\5uIQ\tA\u0007\u0005\u0002\u0015k%\u0011aG\u0004\u0002\u0005+:LG/\u0001\u0005v]N|'\u000f^3e+\u00059\u0012!F:peR,G-\u0013;fe\u0006\u0014G.\u001a$bGR|'/_\u000b\u0002wA\u0019q\u0005\u0010\u0017\n\u0005ub!!F*peR,G-\u0013;fe\u0006\u0014G.\u001a$bGR|'/_\u0001\n'>\u0014H/\u001a3TKR\u0004\"\u0001G\u0003\u0014\u0005\u0015\t\u0005c\u0001\"FY9\u0011qeQ\u0005\u0003\t2\tQcU8si\u0016$\u0017\n^3sC\ndWMR1di>\u0014\u00180\u0003\u0002G\u000f\nAA)\u001a7fO\u0006$XM\u0003\u0002E\u0019\u00051A(\u001b8jiz\"\u0012aP\u0001\u0005MJ|W.\u0006\u0002M!R\u0011Qj\u0017\u000b\u0003\u001dJ\u00032\u0001\u0007\u0001P!\ta\u0002\u000bB\u0003R\u000f\t\u0007qDA\u0001F\u0011\u001d\u0019v!!AA\u0004Q\u000b!\"\u001a<jI\u0016t7-\u001a\u00132!\r)\u0006l\u0014\b\u0003)YK!a\u0016\b\u0002\u000fA\f7m[1hK&\u0011\u0011L\u0017\u0002\t\u001fJ$WM]5oO*\u0011qK\u0004\u0005\u00069\u001e\u0001\r!X\u0001\u0003SR\u00042a\n0P\u0013\tyFB\u0001\u0007Ji\u0016\u0014\u0018M\u00197f\u001f:\u001cW-\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001c!\t\u0019\u0007.D\u0001e\u0015\t)g-\u0001\u0003mC:<'\"A4\u0002\t)\fg/Y\u0005\u0003S\u0012\u0014aa\u00142kK\u000e$\b\u0006B\u0003l]>\u0004\"\u0001\u00067\n\u00055t!\u0001E*fe&\fGNV3sg&|g.V%E\u0003\u00151\u0018\r\\;f=\u0005\u0019\u0001\u0006\u0002\u0003l]>\u0004")
public interface SortedSet<A>
extends Set<A>,
scala.collection.SortedSet<A>,
SortedSetOps<A, SortedSet, SortedSet<A>> {
    public static Builder newBuilder(Object evidence$21) {
        return SortedSet$.MODULE$.newBuilder(evidence$21);
    }

    public static Factory evidenceIterableFactory(Object evidence$13) {
        return EvidenceIterableFactory.evidenceIterableFactory$(SortedSet$.MODULE$, evidence$13);
    }

    public static Object unfold(Object init2, Function1 f, Object evidence$11) {
        SortedSet$ unfold_this = SortedSet$.MODULE$;
        View.Unfold from_it = new View.Unfold(init2, f);
        return unfold_this.from(from_it, (Ordering)evidence$11);
    }

    public static Object iterate(Object start2, int len, Function1 f, Object evidence$10) {
        SortedSet$ iterate_this = SortedSet$.MODULE$;
        View.Iterate<Object> from_it = new View.Iterate<Object>(start2, len, f);
        return iterate_this.from(from_it, (Ordering)evidence$10);
    }

    public static Object tabulate(int n, Function1 f, Object evidence$9) {
        SortedSet$ tabulate_this = SortedSet$.MODULE$;
        View.Tabulate from_it = new View.Tabulate(n, f);
        return tabulate_this.from(from_it, (Ordering)evidence$9);
    }

    public static Object fill(int n, Function0 elem, Object evidence$8) {
        SortedSet$ fill_this = SortedSet$.MODULE$;
        View.Fill from_it = new View.Fill(n, elem);
        return fill_this.from(from_it, (Ordering)evidence$8);
    }

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

    public static void $init$(SortedSet $this) {
    }
}

