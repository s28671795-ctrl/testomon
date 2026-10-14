/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.immutable.MapOps;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y3qa\u0001\u0003\u0011\u0002\u0007\u00051\u0002C\u0003D\u0001\u0011\u0005A\tC\u0003I\u0001\u0011\u0005\u0013JA\u000bTiJL7\r^(qi&l\u0017N_3e\u001b\u0006\u0004x\n]:\u000b\u0005\u00151\u0011!C5n[V$\u0018M\u00197f\u0015\t9\u0001\"\u0001\u0006d_2dWm\u0019;j_:T\u0011!C\u0001\u0006g\u000e\fG.Y\u0002\u0001+\u0015aq#\t\u00135'\u0015\u0001Q\"E\u001c;!\tqq\"D\u0001\t\u0013\t\u0001\u0002B\u0001\u0004B]f\u0014VM\u001a\t\u0007%M)\u0002eI\u001a\u000e\u0003\u0011I!\u0001\u0006\u0003\u0003\r5\u000b\u0007o\u00149t!\t1r\u0003\u0004\u0001\u0005\u000ba\u0001!\u0019A\r\u0003\u0003-\u000b\"AG\u000f\u0011\u00059Y\u0012B\u0001\u000f\t\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0004\u0010\n\u0005}A!aA!osB\u0011a#\t\u0003\u0007E\u0001!)\u0019A\r\u0003\u0003Y\u0003\"A\u0006\u0013\u0005\r\u0015\u0002AQ1\u0001'\u0005\t\u00195)F\u0002(W9\n\"A\u0007\u00151\u0005%\n\u0004C\u0002\n\u0014U5\u001a\u0003\u0007\u0005\u0002\u0017W\u0011)A\u0006\nb\u00013\t\t\u0001\f\u0005\u0002\u0017]\u00111q\u0006\nCC\u0002e\u0011\u0011!\u0017\t\u0003-E\"\u0011B\r\u0013\u0002\u0002\u0003\u0005)\u0011A\r\u0003\u0007}##\u0007\u0005\u0002\u0017i\u00111Q\u0007\u0001CC\u0002Y\u0012\u0011aQ\t\u00035E\u0001b\u0001O\u001d\u0016A\r\u001aT\"\u0001\u0004\n\u0005\r1\u0001#\u0002\u001d<{\u0001\u001b\u0014B\u0001\u001f\u0007\u0005i\u0019FO]5di>\u0003H/[7ju\u0016$\u0017\n^3sC\ndWm\u00149t!\u0011qa(\u0006\u0011\n\u0005}B!A\u0002+va2,'\u0007\u0005\u0002\u0013\u0003&\u0011!\t\u0002\u0002\t\u0013R,'/\u00192mK\u00061A%\u001b8ji\u0012\"\u0012!\u0012\t\u0003\u001d\u0019K!a\u0012\u0005\u0003\tUs\u0017\u000e^\u0001\u0007G>t7-\u0019;\u0016\u0005)kECA&Q!\u00111B%\u0006'\u0011\u0005YiE!\u0002(\u0003\u0005\u0004y%A\u0001,2#\t\u0001S\u0004C\u0003R\u0005\u0001\u0007!+\u0001\u0003uQ\u0006$\bc\u0001\u001dT+&\u0011AK\u0002\u0002\r\u0013R,'/\u00192mK>s7-\u001a\t\u0005\u001dy*B\n")
public interface StrictOptimizedMapOps<K, V, CC extends MapOps<Object, Object, CC, ?>, C extends MapOps<K, V, CC, C>>
extends MapOps<K, V, CC, C>,
scala.collection.StrictOptimizedMapOps<K, V, CC, C> {
    public static /* synthetic */ MapOps concat$(StrictOptimizedMapOps $this, IterableOnce that) {
        return $this.concat(that);
    }

    @Override
    default public <V1> CC concat(IterableOnce<Tuple2<K, V1>> that) {
        Object result = this.coll();
        Iterator<Tuple2<K, V1>> it = that.iterator();
        while (it.hasNext()) {
            result = result.$plus(it.next());
        }
        return (CC)result;
    }

    public static void $init$(StrictOptimizedMapOps $this) {
    }
}

