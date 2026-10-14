/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.Index;
import java.util.Comparator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q2A\u0001B\u0003\u0001\u0015!I\u0011\u0005\u0001B\u0001B\u0003%!%\n\u0005\nM\u0001\u0011\t\u0011)A\u0005O9BQa\f\u0001\u0005\u0002A\u0012!cQ8oGV\u0014(/\u001a8u\u001bVdG/['ba*\u0011aaB\u0001\u0005kRLGNC\u0001\t\u0003\u0011\t7n[1\u0004\u0001U\u00191BE\u0010\u0014\u0005\u0001a\u0001\u0003B\u0007\u000f!yi\u0011!B\u0005\u0003\u001f\u0015\u0011Q!\u00138eKb\u0004\"!\u0005\n\r\u0001\u0011)1\u0003\u0001b\u0001)\t\t1*\u0005\u0002\u00167A\u0011a#G\u0007\u0002/)\t\u0001$A\u0003tG\u0006d\u0017-\u0003\u0002\u001b/\t9aj\u001c;iS:<\u0007C\u0001\f\u001d\u0013\tirCA\u0002B]f\u0004\"!E\u0010\u0005\u000b\u0001\u0002!\u0019\u0001\u000b\u0003\u0003Y\u000bq!\\1q'&TX\r\u0005\u0002\u0017G%\u0011Ae\u0006\u0002\u0004\u0013:$\u0018BA\u0011\u000f\u0003=1\u0018\r\\;f\u0007>l\u0007/\u0019:bi>\u0014\bc\u0001\u0015-=5\t\u0011F\u0003\u0002\u0007U)\t1&\u0001\u0003kCZ\f\u0017BA\u0017*\u0005)\u0019u.\u001c9be\u0006$xN]\u0005\u0003M9\ta\u0001P5oSRtDcA\u00193gA!Q\u0002\u0001\t\u001f\u0011\u0015\t3\u00011\u0001#\u0011\u001513\u00011\u0001(\u0001")
public class ConcurrentMultiMap<K, V>
extends Index<K, V> {
    public ConcurrentMultiMap(int mapSize, Comparator<V> valueComparator) {
        super(mapSize, valueComparator);
    }
}

