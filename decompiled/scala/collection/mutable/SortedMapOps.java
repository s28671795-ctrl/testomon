/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Tuple2;
import scala.collection.mutable.Map;
import scala.collection.mutable.MapOps;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00054q\u0001B\u0003\u0011\u0002\u0007\u0005A\u0002C\u0003D\u0001\u0011\u0005A\tC\u0003I\u0001\u0019\u0005\u0011\nC\u0003L\u0001\u0011\u0005CJ\u0001\u0007T_J$X\rZ'ba>\u00038O\u0003\u0002\u0007\u000f\u00059Q.\u001e;bE2,'B\u0001\u0005\n\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u0015\u0005)1oY1mC\u000e\u0001Q#B\u0007\u0018C\u0011Z4\u0003\u0002\u0001\u000f%}\u0002\"a\u0004\t\u000e\u0003%I!!E\u0005\u0003\r\u0005s\u0017PU3g!\u0019\u0019B#\u0006\u0011$u5\tq!\u0003\u0002\u0005\u000fA\u0011ac\u0006\u0007\u0001\t\u0015A\u0002A1\u0001\u001a\u0005\u0005Y\u0015C\u0001\u000e\u001e!\ty1$\u0003\u0002\u001d\u0013\t9aj\u001c;iS:<\u0007CA\b\u001f\u0013\ty\u0012BA\u0002B]f\u0004\"AF\u0011\u0005\u000b\t\u0002!\u0019A\r\u0003\u0003Y\u0003\"A\u0006\u0013\u0005\r\u0015\u0002AQ1\u0001'\u0005\t\u00195)F\u0002(aM\n\"A\u0007\u0015\u0013\u0007%ZSG\u0002\u0003+\u0001\u0001A#\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004\u0003\u0002\u0017._Ij\u0011!B\u0005\u0003]\u0015\u00111!T1q!\t1\u0002\u0007B\u00032I\t\u0007\u0011DA\u0001Y!\t12\u0007B\u00035I\t\u0007\u0011DA\u0001Za\t1\u0004\b\u0005\u0004-\u0001=\u00124e\u000e\t\u0003-a\"\u0011\"\u000f\u0013\u0002\u0002\u0003\u0005)\u0011A\r\u0003\u0007}#\u0013\u0007\u0005\u0002\u0017w\u00111A\b\u0001CC\u0002u\u0012\u0011aQ\t\u00035y\u0002b\u0001\f\u0001\u0016A\rR\u0004C\u0002\u0017A+\u0001\u0012%(\u0003\u0002B\u000b\t1Q*\u00199PaN\u0004\"\u0001L\u0017\u0002\r\u0011Jg.\u001b;%)\u0005)\u0005CA\bG\u0013\t9\u0015B\u0001\u0003V]&$\u0018\u0001C;og>\u0014H/\u001a3\u0016\u0003)\u0003B\u0001L\u0017\u0016A\u00059Q\u000f\u001d3bi\u0016$WCA'Q)\rq5+\u0016\t\u0005-\u0011*r\n\u0005\u0002\u0017!\u0012)\u0011k\u0001b\u0001%\n\u0011a+M\t\u0003AuAQ\u0001V\u0002A\u0002U\t1a[3z\u0011\u001516\u00011\u0001P\u0003\u00151\u0018\r\\;fQ\u0019\u0019\u0001l\u0017/_?B\u0011q\"W\u0005\u00035&\u0011!\u0002Z3qe\u0016\u001c\u0017\r^3e\u0003\u001diWm]:bO\u0016\f\u0013!X\u00017+N,\u0007%\u001c\u0018dY>tW\rK\u0015/C\u0012$wJ\\3)Q-dc/K\u0015!S:\u001cH/Z1eA=4\u0007%\u001c\u0018va\u0012\fG/\u001a3)W2\u0002c/K\u0001\u0006g&t7-Z\u0011\u0002A\u00061!GL\u00194]A\u0002")
public interface SortedMapOps<K, V, CC extends Map<Object, Object>, C extends SortedMapOps<K, V, CC, C>>
extends scala.collection.SortedMapOps<K, V, CC, C>,
MapOps<K, V, Map, C> {
    @Override
    public Map<K, V> unsorted();

    public static /* synthetic */ Map updated$(SortedMapOps $this, Object key, Object value) {
        return $this.updated(key, value);
    }

    @Override
    default public <V1> CC updated(K key, V1 value) {
        return (CC)((Map)((Map)this.clone()).addOne(new Tuple2<K, V1>(key, value)));
    }

    public static void $init$(SortedMapOps $this) {
    }
}

