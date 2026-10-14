/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Tuple2;
import scala.collection.immutable.LongMap;
import scala.collection.immutable.LongMapIterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}2Q\u0001B\u0003\u0001\u000b-A\u0001\u0002\f\u0001\u0003\u0002\u0003\u0006I!\f\u0005\u0006a\u0001!\t!\r\u0005\u0006i\u0001!\t!\u000e\u0002\u0015\u0019>tw-T1q\u000b:$(/_%uKJ\fGo\u001c:\u000b\u0005\u00199\u0011!C5n[V$\u0018M\u00197f\u0015\tA\u0011\"\u0001\u0006d_2dWm\u0019;j_:T\u0011AC\u0001\u0006g\u000e\fG.Y\u000b\u0003\u0019M\u0019\"\u0001A\u0007\u0011\t9y\u0011CH\u0007\u0002\u000b%\u0011\u0001#\u0002\u0002\u0010\u0019>tw-T1q\u0013R,'/\u0019;peB\u0011!c\u0005\u0007\u0001\t\u0015!\u0002A1\u0001\u0017\u0005\u000516\u0001A\t\u0003/m\u0001\"\u0001G\r\u000e\u0003%I!AG\u0005\u0003\u000f9{G\u000f[5oOB\u0011\u0001\u0004H\u0005\u0003;%\u00111!\u00118z!\u0011Ar$I\t\n\u0005\u0001J!A\u0002+va2,'\u0007\u0005\u0002#K9\u0011abI\u0005\u0003I\u0015\tA\u0002T8oO6\u000b\u0007/\u0016;jYNL!AJ\u0014\u0003\t1{gnZ\u0005\u0003M!R!!\u000b\u0016\u0002\u001b\tKGo\u00149fe\u0006$\u0018n\u001c8t\u0015\tYs!A\u0004hK:,'/[2\u0002\u0005%$\bc\u0001\b/#%\u0011q&\u0002\u0002\b\u0019>tw-T1q\u0003\u0019a\u0014N\\5u}Q\u0011!g\r\t\u0004\u001d\u0001\t\u0002\"\u0002\u0017\u0003\u0001\u0004i\u0013a\u0002<bYV,wJ\u001a\u000b\u0003=YBQaN\u0002A\u0002a\n1\u0001^5q!\rID(\u0005\b\u0003\u001diJ!aO\u0003\u0002\u000f1{gnZ'ba&\u0011QH\u0010\u0002\u0004)&\u0004(BA\u001e\u0006\u0001")
public class LongMapEntryIterator<V>
extends LongMapIterator<V, Tuple2<Object, V>> {
    @Override
    public Tuple2<Object, V> valueOf(LongMap.Tip<V> tip) {
        return new Tuple2<Long, V>(tip.key(), tip.value());
    }

    public LongMapEntryIterator(LongMap<V> it) {
        super(it);
    }
}

