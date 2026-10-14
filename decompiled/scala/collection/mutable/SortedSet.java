/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Function0;
import scala.Function1;
import scala.collection.EvidenceIterableFactory;
import scala.collection.Factory;
import scala.collection.SortedIterableFactory;
import scala.collection.View;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Set;
import scala.collection.mutable.SortedSet$;
import scala.collection.mutable.SortedSetOps;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i3q\u0001C\u0005\u0011\u0002\u0007\u0005\u0001\u0003C\u00032\u0001\u0011\u0005!\u0007C\u00037\u0001\u0011\u0005s\u0007C\u00039\u0001\u0011\u0005\u0013hB\u0003>\u0013!\u0005aHB\u0003\t\u0013!\u0005q\bC\u0003H\u000b\u0011\u0005\u0001\nC\u0004J\u000b\u0005\u0005I\u0011\u0002&\u0003\u0013M{'\u000f^3e'\u0016$(B\u0001\u0006\f\u0003\u001diW\u000f^1cY\u0016T!\u0001D\u0007\u0002\u0015\r|G\u000e\\3di&|gNC\u0001\u000f\u0003\u0015\u00198-\u00197b\u0007\u0001)\"!\u0005\u000f\u0014\r\u0001\u0011b#\n\u0015.!\t\u0019B#D\u0001\u000e\u0013\t)RB\u0001\u0004B]f\u0014VM\u001a\t\u0004/aQR\"A\u0005\n\u0005eI!aA*fiB\u00111\u0004\b\u0007\u0001\t\u0015i\u0002A1\u0001\u001f\u0005\u0005\t\u0015CA\u0010#!\t\u0019\u0002%\u0003\u0002\"\u001b\t9aj\u001c;iS:<\u0007CA\n$\u0013\t!SBA\u0002B]f\u00042AJ\u0014\u001b\u001b\u0005Y\u0011B\u0001\u0005\f!\u00159\u0012FG\u0016-\u0013\tQ\u0013B\u0001\u0007T_J$X\rZ*fi>\u00038\u000f\u0005\u0002\u0018\u0001A\u0019q\u0003\u0001\u000e\u0011\u000b\u0019r#d\u000b\u0019\n\u0005=Z!\u0001G*peR,GmU3u\r\u0006\u001cGo\u001c:z\t\u00164\u0017-\u001e7ugB\u0011q\u0003G\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003M\u0002\"a\u0005\u001b\n\u0005Uj!\u0001B+oSR\f\u0001\"\u001e8t_J$X\rZ\u000b\u0002-\u0005)2o\u001c:uK\u0012LE/\u001a:bE2,g)Y2u_JLX#\u0001\u001e\u0011\u0007\u0019Z4&\u0003\u0002=\u0017\t)2k\u001c:uK\u0012LE/\u001a:bE2,g)Y2u_JL\u0018!C*peR,GmU3u!\t9Ra\u0005\u0002\u0006\u0001B\u0019\u0011\tR\u0016\u000f\u0005\u0019\u0012\u0015BA\"\f\u0003U\u0019vN\u001d;fI&#XM]1cY\u00164\u0015m\u0019;pefL!!\u0012$\u0003\u0011\u0011+G.Z4bi\u0016T!aQ\u0006\u0002\rqJg.\u001b;?)\u0005q\u0014\u0001D<sSR,'+\u001a9mC\u000e,G#A&\u0011\u00051\u000bV\"A'\u000b\u00059{\u0015\u0001\u00027b]\u001eT\u0011\u0001U\u0001\u0005U\u00064\u0018-\u0003\u0002S\u001b\n1qJ\u00196fGRDC!\u0002+X1B\u00111#V\u0005\u0003-6\u0011\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\rAC\u0001\u0002+X1\u0002")
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

