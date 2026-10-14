/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.ConsistentHash;
import akka.routing.MurmurHash$;
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.SortedMapOps;
import scala.collection.immutable.SortedMap;
import scala.collection.immutable.SortedMap$;
import scala.math.Ordering$Int$;
import scala.reflect.ClassTag;
import scala.reflect.ClassTag$;
import scala.runtime.BoxesRunTime;
import scala.runtime.RichInt$;

public final class ConsistentHash$ {
    public static final ConsistentHash$ MODULE$ = new ConsistentHash$();

    public <T> ConsistentHash<T> apply(Iterable<T> nodes, int virtualNodesFactor, ClassTag<T> evidence$2) {
        return new ConsistentHash<T>((SortedMap)((SortedMapOps)SortedMap$.MODULE$.empty(Ordering$Int$.MODULE$)).$plus$plus((IterableOnce)((IterableOps)nodes.map((Function1<Object, Tuple2> & Serializable)node -> {
            int nodeHash = MODULE$.akka$routing$ConsistentHash$$hashFor(node.toString());
            return new Tuple2<Object, Integer>(node, BoxesRunTime.boxToInteger(nodeHash));
        })).flatMap((Function1<Tuple2, IterableOnce> & Serializable)x$3 -> {
            Tuple2 tuple2 = x$3;
            if (tuple2 != null) {
                Object node = tuple2._1();
                int nodeHash = tuple2._2$mcI$sp();
                return RichInt$.MODULE$.to$extension(Predef$.MODULE$.intWrapper(1), virtualNodesFactor).map((Function1<Object, Tuple2> & Serializable)vnode -> Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(BoxesRunTime.boxToInteger(ConsistentHash$.MODULE$.akka$routing$ConsistentHash$$concatenateNodeHash(nodeHash, BoxesRunTime.unboxToInt(vnode)))), node));
            }
            throw new MatchError(tuple2);
        })), virtualNodesFactor, evidence$2);
    }

    public <T> ConsistentHash<T> create(java.lang.Iterable<T> nodes, int virtualNodesFactor) {
        return this.apply(package$JavaConverters$.MODULE$.IterableHasAsScala(nodes).asScala(), virtualNodesFactor, ClassTag$.MODULE$.apply(Object.class));
    }

    public int akka$routing$ConsistentHash$$concatenateNodeHash(int nodeHash, int vnode) {
        int h2 = MurmurHash$.MODULE$.startHash(nodeHash);
        h2 = MurmurHash$.MODULE$.extendHash(h2, vnode, MurmurHash$.MODULE$.startMagicA(), MurmurHash$.MODULE$.startMagicB());
        return MurmurHash$.MODULE$.finalizeHash(h2);
    }

    public int akka$routing$ConsistentHash$$hashFor(byte[] bytes) {
        return MurmurHash$.MODULE$.arrayHash$mBc$sp(bytes);
    }

    public int akka$routing$ConsistentHash$$hashFor(String string) {
        return MurmurHash$.MODULE$.stringHash(string);
    }

    private ConsistentHash$() {
    }
}

