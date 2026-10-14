/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function1;
import scala.collection.IterableOnce;
import scala.collection.Set;
import scala.collection.SortedSet;
import scala.collection.SortedSetOps;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q4\u0001BB\u0004\u0011\u0002\u0007\u0005A\u0002\u001e\u0005\u0006y\u0001!\t!\u0010\u0005\u0006\u0003\u0002!\tF\u0011\u0005\u0006\u0011\u0002!\t&\u0013\u0005\u0006!\u0002!\t%\u0015\u0005\u0006%\u0002!\te\u0015\u0002\u0019'>\u0014H/\u001a3TKR4\u0015m\u0019;pef$UMZ1vYR\u001c(B\u0001\u0005\n\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u0015\u0005)1oY1mC\u000e\u0001Q\u0003B\u0007\u001aYq\u001b2\u0001\u0001\b\u0013!\ty\u0001#D\u0001\n\u0013\t\t\u0012B\u0001\u0004B]f\u0014VM\u001a\t\u0006'Q12fO\u0007\u0002\u000f%\u0011Qc\u0002\u0002\r'>\u0014H/\u001a3TKR|\u0005o\u001d\u0016\u0003/\t\u0002\"\u0001G\r\r\u0001\u00111!\u0004\u0001CC\u0002m\u0011\u0011!Q\t\u00039}\u0001\"aD\u000f\n\u0005yI!a\u0002(pi\"Lgn\u001a\t\u0003\u001f\u0001J!!I\u0005\u0003\u0007\u0005s\u0017pK\u0001$!\t!\u0013&D\u0001&\u0015\t1s%A\u0005v]\u000eDWmY6fI*\u0011\u0001&C\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001\u0016&\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\t\u000311\"a!\f\u0001\u0005\u0006\u0004q#AA\"D+\tys'\u0005\u0002\u001daI\u0019\u0011gM\u001d\u0007\tI\u0002\u0001\u0001\r\u0002\ryI,g-\u001b8f[\u0016tGO\u0010\t\u0004'Q2\u0014BA\u001b\b\u0005%\u0019vN\u001d;fIN+G\u000f\u0005\u0002\u0019o\u0011)\u0001\b\fb\u00017\t\t\u0001\fE\u0003\u0014)YZ#\bE\u0002\u0019YY\u00022\u0001\u0007\u0017\u0017\u0003\u0019!\u0013N\\5uIQ\ta\b\u0005\u0002\u0010\u007f%\u0011\u0001)\u0003\u0002\u0005+:LG/\u0001\u0007ge>l7\u000b]3dS\u001aL7\r\u0006\u0002<\u0007\")AI\u0001a\u0001\u000b\u0006!1m\u001c7m!\r\u0019bIF\u0005\u0003\u000f\u001e\u0011A\"\u0013;fe\u0006\u0014G.Z(oG\u0016\f!C\\3x'B,7-\u001b4jG\n+\u0018\u000e\u001c3feV\t!\n\u0005\u0003L\u001dZYT\"\u0001'\u000b\u00055;\u0011aB7vi\u0006\u0014G.Z\u0005\u0003\u001f2\u0013qAQ;jY\u0012,'/A\u0003f[B$\u00180F\u0001<\u0003)9\u0018\u000e\u001e5GS2$XM\u001d\u000b\u0003)2\u0004R!\u0016-\u00187.r!a\u0005,\n\u0005];\u0011\u0001D*peR,GmU3u\u001fB\u001c\u0018BA-[\u0005)9\u0016\u000e\u001e5GS2$XM\u001d\u0006\u0003/\u001e\u0001\"\u0001\u0007/\u0005\ru\u0003AQ1\u0001_\u000519\u0016\u000e\u001e5GS2$XM]\"D+\tyf-\u0005\u0002\u001dAJ\u0019\u0011MY5\u0007\tI\u0002\u0001\u0001\u0019\t\u0006'\r,7\f[\u0005\u0003I\u001e\u00111\"\u0013;fe\u0006\u0014G.Z(qgB\u0011\u0001D\u001a\u0003\u0006Or\u0013\ra\u0007\u0002\u0002qB\u0019\u0001\u0004X3\u0011\u0007MQW-\u0003\u0002l\u000f\t\u00191+\u001a;\t\u000b5,\u0001\u0019\u00018\u0002\u0003A\u0004BaD8\u0018c&\u0011\u0001/\u0003\u0002\n\rVt7\r^5p]F\u0002\"a\u0004:\n\u0005ML!a\u0002\"p_2,\u0017M\u001c\n\u0004kZ<h\u0001\u0002\u001a\u0001\u0001Q\u0004Ra\u0005\u0001\u0018Wm\u0003$\u0001\u001f>\u0011\u000bM\u0019wcW=\u0011\u0005aQH!C>\u0001\u0003\u0003\u0005\tQ!\u0001\u001c\u0005\ryF%\u000e")
public interface SortedSetFactoryDefaults<A, CC extends SortedSet<Object>, WithFilterCC extends Set<Object>>
extends SortedSetOps<A, CC, CC> {
    public static /* synthetic */ SortedSet fromSpecific$(SortedSetFactoryDefaults $this, IterableOnce coll) {
        return $this.fromSpecific(coll);
    }

    @Override
    default public CC fromSpecific(IterableOnce<A> coll) {
        return (CC)((SortedSet)this.sortedIterableFactory().from(coll, this.ordering()));
    }

    public static /* synthetic */ Builder newSpecificBuilder$(SortedSetFactoryDefaults $this) {
        return $this.newSpecificBuilder();
    }

    @Override
    default public Builder<A, CC> newSpecificBuilder() {
        return this.sortedIterableFactory().newBuilder(this.ordering());
    }

    public static /* synthetic */ SortedSet empty$(SortedSetFactoryDefaults $this) {
        return $this.empty();
    }

    @Override
    default public CC empty() {
        return (CC)((SortedSet)this.sortedIterableFactory().empty(this.ordering()));
    }

    public static /* synthetic */ SortedSetOps.WithFilter withFilter$(SortedSetFactoryDefaults $this, Function1 p) {
        return $this.withFilter(p);
    }

    default public SortedSetOps.WithFilter<A, WithFilterCC, CC> withFilter(Function1<A, Object> p) {
        return new SortedSetOps.WithFilter(this, p);
    }

    public static void $init$(SortedSetFactoryDefaults $this) {
    }
}

