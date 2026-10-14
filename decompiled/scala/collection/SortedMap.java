/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.collection.Map;
import scala.collection.SortedMap$;
import scala.collection.SortedMapFactory;
import scala.collection.SortedMapFactoryDefaults;
import scala.collection.mutable.Builder;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005]4qa\u0003\u0007\u0011\u0002\u0007\u0005\u0011\u0003C\u00036\u0001\u0011\u0005a\u0007C\u0003;\u0001\u0011\u00051\bC\u0003=\u0001\u0011\u0005Q\b\u0003\u0004B\u0001\u0001&\tF\u0011\u0005\u0006\u001d\u0002!\te\u0014\u0005\f+\u0002\u0001\n1!A\u0001\n\u00131\u0016lB\u0003[\u0019!\u00051LB\u0003\f\u0019!\u0005A\fC\u0003e\u0011\u0011\u0005Q\rC\u0004g\u0011\u0005\u0005I\u0011B4\u0003\u0013M{'\u000f^3e\u001b\u0006\u0004(BA\u0007\u000f\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u001f\u0005)1oY1mC\u000e\u0001Qc\u0001\n\u001eOM)\u0001aE\f*]A\u0011A#F\u0007\u0002\u001d%\u0011aC\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\taI2DJ\u0007\u0002\u0019%\u0011!\u0004\u0004\u0002\u0004\u001b\u0006\u0004\bC\u0001\u000f\u001e\u0019\u0001!QA\b\u0001C\u0002}\u0011\u0011aS\t\u0003A\r\u0002\"\u0001F\u0011\n\u0005\tr!a\u0002(pi\"Lgn\u001a\t\u0003)\u0011J!!\n\b\u0003\u0007\u0005s\u0017\u0010\u0005\u0002\u001dO\u00111\u0001\u0006\u0001CC\u0002}\u0011\u0011A\u0016\t\u00071)Zb\u0005L\u0017\n\u0005-b!\u0001D*peR,G-T1q\u001fB\u001c\bC\u0001\r\u0001!\u0011A\u0002a\u0007\u0014\u0011\u000fay3D\n\u00172i%\u0011\u0001\u0007\u0004\u0002\u0019'>\u0014H/\u001a3NCB4\u0015m\u0019;pef$UMZ1vYR\u001c\bC\u0001\r3\u0013\t\u0019DB\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\tA\u0012$\u0001\u0004%S:LG\u000f\n\u000b\u0002oA\u0011A\u0003O\u0005\u0003s9\u0011A!\u00168ji\u0006AQO\\:peR,G-F\u0001\u0018\u0003A\u0019xN\u001d;fI6\u000b\u0007OR1di>\u0014\u00180F\u0001?!\rAr\bL\u0005\u0003\u00012\u0011\u0001cU8si\u0016$W*\u00199GC\u000e$xN]=\u0002\u0019M$(/\u001b8h!J,g-\u001b=\u0016\u0003\r\u0003\"\u0001R&\u000f\u0005\u0015K\u0005C\u0001$\u000f\u001b\u00059%B\u0001%\u0011\u0003\u0019a$o\\8u}%\u0011!JD\u0001\u0007!J,G-\u001a4\n\u00051k%AB*ue&twM\u0003\u0002K\u001d\u00051Q-];bYN$\"\u0001U*\u0011\u0005Q\t\u0016B\u0001*\u000f\u0005\u001d\u0011un\u001c7fC:DQ\u0001V\u0003A\u0002\r\nA\u0001\u001e5bi\u0006a1/\u001e9fe\u0012*\u0017/^1mgR\u0011\u0001k\u0016\u0005\u00061\u001a\u0001\raI\u0001\u0002_&\u0011a*G\u0001\n'>\u0014H/\u001a3NCB\u0004\"\u0001\u0007\u0005\u0014\u0005!i\u0006c\u00010bY9\u0011\u0001dX\u0005\u0003A2\t\u0001cU8si\u0016$W*\u00199GC\u000e$xN]=\n\u0005\t\u001c'\u0001\u0003#fY\u0016<\u0017\r^3\u000b\u0005\u0001d\u0011A\u0002\u001fj]&$h\bF\u0001\\\u000319(/\u001b;f%\u0016\u0004H.Y2f)\u0005A\u0007CA5o\u001b\u0005Q'BA6m\u0003\u0011a\u0017M\\4\u000b\u00035\fAA[1wC&\u0011qN\u001b\u0002\u0007\u001f\nTWm\u0019;)\t!\tH/\u001e\t\u0003)IL!a\u001d\b\u0003!M+'/[1m-\u0016\u00148/[8o+&#\u0015!\u0002<bYV,g$A\u0002)\t\u001d\tH/\u001e")
public interface SortedMap<K, V>
extends Map<K, V>,
SortedMapFactoryDefaults<K, V, SortedMap, Iterable, Map> {
    public static <K, V> Builder<Tuple2<K, V>, SortedMap<K, V>> newBuilder(Ordering<K> evidence$48) {
        return SortedMap$.MODULE$.newBuilder(evidence$48);
    }

    public /* synthetic */ boolean scala$collection$SortedMap$$super$equals(Object var1);

    public static /* synthetic */ Map unsorted$(SortedMap $this) {
        return $this.unsorted();
    }

    @Override
    default public Map<K, V> unsorted() {
        return this;
    }

    public static /* synthetic */ SortedMapFactory sortedMapFactory$(SortedMap $this) {
        return $this.sortedMapFactory();
    }

    @Override
    default public SortedMapFactory<SortedMap> sortedMapFactory() {
        return SortedMap$.MODULE$;
    }

    public static /* synthetic */ String stringPrefix$(SortedMap $this) {
        return $this.stringPrefix();
    }

    @Override
    default public String stringPrefix() {
        return "SortedMap";
    }

    public static /* synthetic */ boolean equals$(SortedMap $this, Object that) {
        return $this.equals(that);
    }

    @Override
    default public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that instanceof SortedMap) {
            SortedMap sortedMap = (SortedMap)that;
            Ordering ordering = sortedMap.ordering();
            Ordering ordering2 = this.ordering();
            if (!(ordering != null ? !ordering.equals(ordering2) : ordering2 != null)) {
                if (sortedMap.canEqual(this) && this.size() == sortedMap.size()) {
                    Iterator i1 = this.iterator();
                    Iterator i2 = sortedMap.iterator();
                    boolean allEqual = true;
                    while (allEqual && i1.hasNext()) {
                        Tuple2 kv1 = (Tuple2)i1.next();
                        Tuple2 kv2 = (Tuple2)i2.next();
                        allEqual = this.ordering().equiv(kv1._1(), kv2._1()) && BoxesRunTime.equals(kv1._2(), kv2._2());
                    }
                    if (allEqual) {
                        return true;
                    }
                }
                return false;
            }
        }
        return this.scala$collection$SortedMap$$super$equals(that);
    }

    public static void $init$(SortedMap $this) {
    }
}

