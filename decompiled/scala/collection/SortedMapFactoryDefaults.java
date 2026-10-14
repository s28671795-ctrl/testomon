/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Function1;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.Map;
import scala.collection.SortedMapOps;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005-b!\u0003\u0004\b!\u0003\r\t\u0001DA\u000e\u0011\u0015\u0001\u0006\u0001\"\u0001R\u0011\u0015)\u0006\u0001\"\u0011W\u0011\u00159\u0006\u0001\"\u0015Y\u0011\u0015\t\u0007\u0001\"\u0015c\u0011\u0015I\u0007\u0001\"\u0011k\u0005a\u0019vN\u001d;fI6\u000b\u0007OR1di>\u0014\u0018\u0010R3gCVdGo\u001d\u0006\u0003\u0011%\t!bY8mY\u0016\u001cG/[8o\u0015\u0005Q\u0011!B:dC2\f7\u0001A\u000b\u0007\u001ba\u0011S%\u001e\u001d\u0014\t\u0001q!\u0003\u0014\t\u0003\u001fAi\u0011!C\u0005\u0003#%\u0011a!\u00118z%\u00164\u0007CB\n\u0015-\u0005\"\u0013)D\u0001\b\u0013\t)rA\u0001\u0007T_J$X\rZ'ba>\u00038\u000f\u0005\u0002\u001811\u0001A!B\r\u0001\u0005\u0004Q\"!A&\u0012\u0005mq\u0002CA\b\u001d\u0013\ti\u0012BA\u0004O_RD\u0017N\\4\u0011\u0005=y\u0012B\u0001\u0011\n\u0005\r\te.\u001f\t\u0003/\t\"aa\t\u0001\u0005\u0006\u0004Q\"!\u0001,\u0011\u0005])CA\u0002\u0014\u0001\t\u000b\u0007qE\u0001\u0002D\u0007V\u0019\u0001\u0006M\u001a\u0012\u0005mI#\u0003\u0002\u0016-k]2Aa\u000b\u0001\u0001S\taAH]3gS:,W.\u001a8u}A!1#L\u00183\u0013\tqsAA\u0002NCB\u0004\"a\u0006\u0019\u0005\u000bE*#\u0019\u0001\u000e\u0003\u0003a\u0004\"aF\u001a\u0005\u000bQ*#\u0019\u0001\u000e\u0003\u0003e\u0004ba\u0005\u000b0e\u00112\u0004\u0003B\f&_I\u0002Ba\u0006\u001d0e\u00111\u0011\b\u0001CC\u0002i\u0012!\"\u00168t_J$X\rZ\"D+\rYd\bQ\t\u00037q\u0002BaE\u0017>\u007fA\u0011qC\u0010\u0003\u0006ca\u0012\rA\u0007\t\u0003/\u0001#Q\u0001\u000e\u001dC\u0002i\u0001BaF\u0013\u0017\u0005*\u0012\u0011eQ\u0016\u0002\tB\u0011QIS\u0007\u0002\r*\u0011q\tS\u0001\nk:\u001c\u0007.Z2lK\u0012T!!S\u0005\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002L\r\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0011\rMie#I(B\u0013\tquA\u0001\u0004NCB|\u0005o\u001d\t\u0003/a\na\u0001J5oSR$C#\u0001*\u0011\u0005=\u0019\u0016B\u0001+\n\u0005\u0011)f.\u001b;\u0002\u000b\u0015l\u0007\u000f^=\u0016\u0003\u0005\u000bAB\u001a:p[N\u0003XmY5gS\u000e$\"!Q-\t\u000bi\u001b\u0001\u0019A.\u0002\t\r|G\u000e\u001c\t\u0004'qs\u0016BA/\b\u00051IE/\u001a:bE2,wJ\\2f!\u0011yqL\u0006\"\n\u0005\u0001L!A\u0002+va2,''\u0001\noK^\u001c\u0006/Z2jM&\u001c')^5mI\u0016\u0014X#A2\u0011\t\u0011<g,Q\u0007\u0002K*\u0011amB\u0001\b[V$\u0018M\u00197f\u0013\tAWMA\u0004Ck&dG-\u001a:\u0002\u0015]LG\u000f\u001b$jYR,'\u000fF\u0002l\u0003\u0013\u0001r\u0001\\9\u0017CQ|EE\u0004\u0002n_:\u0011qB\\\u0005\u0003\u0011%I!\u0001]\u0004\u0002\u0019M{'\u000f^3e\u001b\u0006\u0004x\n]:\n\u0005I\u001c(AC,ji\"4\u0015\u000e\u001c;fe*\u0011\u0001o\u0002\t\u0003/U$aA\u001e\u0001\u0005\u0006\u00049(\u0001D,ji\"4\u0015\u000e\u001c;fe\u000e\u001bUC\u0001=\u0000#\tY\u0012P\u0005\u0003{w\u0006\ra\u0001B\u0016\u0001\u0001e\u0004ba\u0005?\u007fi\u0006\u0005\u0011BA?\b\u0005-IE/\u001a:bE2,w\n]:\u0011\u0005]yH!B\u0019v\u0005\u0004Q\u0002cA\fv}B!1#!\u0002\u007f\u0013\r\t9a\u0002\u0002\t\u0013R,'/\u00192mK\"9\u00111B\u0003A\u0002\u00055\u0011!\u00019\u0011\u000f=\ty!a\u0005\u0002\u0016%\u0019\u0011\u0011C\u0005\u0003\u0013\u0019+hn\u0019;j_:\f\u0004\u0003B\b`-\u0005\u00022aDA\f\u0013\r\tI\"\u0003\u0002\b\u0005>|G.Z1o%\u0019\ti\"a\b\u0002\"\u0019)1\u0006\u0001\u0001\u0002\u001cA91\u0003\u0001\f\"IQ|\u0005\u0007BA\u0012\u0003O\u0001ra\u0005?\u0002\u0014Q\f)\u0003E\u0002\u0018\u0003O!!\"!\u000b\u0001\u0003\u0003\u0005\tQ!\u0001\u001b\u0005\ryFE\u000e")
public interface SortedMapFactoryDefaults<K, V, CC extends SortedMapOps<Object, Object, CC, CC>, WithFilterCC extends Iterable<Object>, UnsortedCC extends Map<Object, Object>>
extends SortedMapOps<K, V, CC, CC> {
    public static /* synthetic */ SortedMapOps empty$(SortedMapFactoryDefaults $this) {
        return $this.empty();
    }

    @Override
    default public CC empty() {
        return (CC)((SortedMapOps)this.sortedMapFactory().empty(this.ordering()));
    }

    public static /* synthetic */ SortedMapOps fromSpecific$(SortedMapFactoryDefaults $this, IterableOnce coll) {
        return $this.fromSpecific(coll);
    }

    @Override
    default public CC fromSpecific(IterableOnce<Tuple2<K, V>> coll) {
        return (CC)((SortedMapOps)this.sortedMapFactory().from(coll, this.ordering()));
    }

    public static /* synthetic */ Builder newSpecificBuilder$(SortedMapFactoryDefaults $this) {
        return $this.newSpecificBuilder();
    }

    @Override
    default public Builder<Tuple2<K, V>, CC> newSpecificBuilder() {
        return this.sortedMapFactory().newBuilder(this.ordering());
    }

    public static /* synthetic */ SortedMapOps.WithFilter withFilter$(SortedMapFactoryDefaults $this, Function1 p) {
        return $this.withFilter(p);
    }

    default public SortedMapOps.WithFilter<K, V, WithFilterCC, UnsortedCC, CC> withFilter(Function1<Tuple2<K, V>, Object> p) {
        return new SortedMapOps.WithFilter(this, p);
    }

    public static void $init$(SortedMapFactoryDefaults $this) {
    }
}

