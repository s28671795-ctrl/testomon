/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.immutable.Map;
import scala.collection.immutable.SortedMapOps;
import scala.collection.immutable.StrictOptimizedMapOps;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e3qa\u0001\u0003\u0011\u0002\u0007\u00051\u0002C\u0003E\u0001\u0011\u0005Q\tC\u0003J\u0001\u0011\u0005#JA\u000eTiJL7\r^(qi&l\u0017N_3e'>\u0014H/\u001a3NCB|\u0005o\u001d\u0006\u0003\u000b\u0019\t\u0011\"[7nkR\f'\r\\3\u000b\u0005\u001dA\u0011AC2pY2,7\r^5p]*\t\u0011\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u000b19\u0012\u0005\n\u001e\u0014\u000b\u0001i\u0011#\u0010!\u0011\u00059yQ\"\u0001\u0005\n\u0005AA!AB!osJ+g\r\u0005\u0004\u0013'U\u00013%O\u0007\u0002\t%\u0011A\u0003\u0002\u0002\r'>\u0014H/\u001a3NCB|\u0005o\u001d\t\u0003-]a\u0001\u0001B\u0003\u0019\u0001\t\u0007\u0011DA\u0001L#\tQR\u0004\u0005\u0002\u000f7%\u0011A\u0004\u0003\u0002\b\u001d>$\b.\u001b8h!\tqa$\u0003\u0002 \u0011\t\u0019\u0011I\\=\u0011\u0005Y\tCA\u0002\u0012\u0001\t\u000b\u0007\u0011DA\u0001W!\t1B\u0005\u0002\u0004&\u0001\u0011\u0015\rA\n\u0002\u0003\u0007\u000e+2aJ\u00183#\tQ\u0002FE\u0002*WQ2AA\u000b\u0001\u0001Q\taAH]3gS:,W.\u001a8u}A!!\u0003\f\u00182\u0013\tiCAA\u0002NCB\u0004\"AF\u0018\u0005\u000bA\"#\u0019A\r\u0003\u0003a\u0003\"A\u0006\u001a\u0005\rM\"CQ1\u0001\u001a\u0005\u0005I\u0006GA\u001b8!\u0019\u00112CL\u0019$mA\u0011ac\u000e\u0003\nq\u0011\n\t\u0011!A\u0003\u0002e\u00111a\u0018\u00133!\t1\"\b\u0002\u0004<\u0001\u0011\u0015\r\u0001\u0010\u0002\u0002\u0007F\u0011!$\u0005\t\u0007}}*\u0002eI\u001d\u000e\u0003\u0019I!a\u0001\u0004\u0011\rI\tU\u0003I\":\u0013\t\u0011EAA\u000bTiJL7\r^(qi&l\u0017N_3e\u001b\u0006\u0004x\n]:\u0011\u0005Ia\u0013A\u0002\u0013j]&$H\u0005F\u0001G!\tqq)\u0003\u0002I\u0011\t!QK\\5u\u0003\u0019\u0019wN\\2biV\u00111J\u0014\u000b\u0003\u0019F\u0003BA\u0006\u0013\u0016\u001bB\u0011aC\u0014\u0003\u0006\u001f\n\u0011\r\u0001\u0015\u0002\u0003-J\n\"\u0001I\u000f\t\u000bI\u0013\u0001\u0019A*\u0002\u0005a\u001c\bc\u0001 U-&\u0011QK\u0002\u0002\r\u0013R,'/\u00192mK>s7-\u001a\t\u0005\u001d]+R*\u0003\u0002Y\u0011\t1A+\u001e9mKJ\u0002")
public interface StrictOptimizedSortedMapOps<K, V, CC extends Map<Object, Object>, C extends SortedMapOps<K, V, CC, C>>
extends SortedMapOps<K, V, CC, C>,
scala.collection.StrictOptimizedSortedMapOps<K, V, CC, C>,
StrictOptimizedMapOps<K, V, Map, C> {
    public static /* synthetic */ Map concat$(StrictOptimizedSortedMapOps $this, IterableOnce xs) {
        return $this.concat(xs);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    default public <V2> CC concat(IterableOnce<Tuple2<K, V2>> xs) {
        Map result = (Map)this.coll();
        Iterator<Tuple2<K, V2>> it = xs.iterator();
        while (it.hasNext()) {
            void $plus_kv;
            SortedMapOps $plus_this;
            Tuple2<K, V2> tuple2 = it.next();
            if ((SortedMapOps)((Object)result) == null) {
                throw null;
            }
            Object var4_4 = null;
            tuple2 = null;
            result = $plus_this.updated($plus_kv._1(), $plus_kv._2());
        }
        return (CC)result;
    }

    public static void $init$(StrictOptimizedSortedMapOps $this) {
    }
}

