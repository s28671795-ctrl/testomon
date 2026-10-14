/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.collection.MapFactory;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Map;
import scala.collection.mutable.SeqMap$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U3qa\u0002\u0005\u0011\u0002\u0007\u0005q\u0002C\u00036\u0001\u0011\u0005a\u0007C\u0003;\u0001\u0011\u00053hB\u0003@\u0011!\u0005\u0001IB\u0003\b\u0011!\u0005\u0011\tC\u0003J\t\u0011\u0005!\nC\u0004L\t\u0005\u0005I\u0011\u0002'\u0003\rM+\u0017/T1q\u0015\tI!\"A\u0004nkR\f'\r\\3\u000b\u0005-a\u0011AC2pY2,7\r^5p]*\tQ\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0007AYRe\u0005\u0004\u0001#U9#f\f\t\u0003%Mi\u0011\u0001D\u0005\u0003)1\u0011a!\u00118z%\u00164\u0007\u0003\u0002\f\u00183\u0011j\u0011\u0001C\u0005\u00031!\u00111!T1q!\tQ2\u0004\u0004\u0001\u0005\u000bq\u0001!\u0019A\u000f\u0003\u0003-\u000b\"AH\u0011\u0011\u0005Iy\u0012B\u0001\u0011\r\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\u0005\u0012\n\u0005\rb!aA!osB\u0011!$\n\u0003\u0006M\u0001\u0011\r!\b\u0002\u0002-B!\u0001&K\r%\u001b\u0005Q\u0011BA\u0004\u000b!\u001912&\u0007\u0013.]%\u0011A\u0006\u0003\u0002\u0007\u001b\u0006\u0004x\n]:\u0011\u0005Y\u0001\u0001\u0003\u0002\f\u00013\u0011\u0002b\u0001\u000b\u0019\u001aI5\u0012\u0014BA\u0019\u000b\u0005Ii\u0015\r\u001d$bGR|'/\u001f#fM\u0006,H\u000e^:\u0011\u0005Y\u0019\u0014B\u0001\u001b\t\u0005!IE/\u001a:bE2,\u0017A\u0002\u0013j]&$H\u0005F\u00018!\t\u0011\u0002(\u0003\u0002:\u0019\t!QK\\5u\u0003)i\u0017\r\u001d$bGR|'/_\u000b\u0002yA\u0019\u0001&P\u0017\n\u0005yR!AC'ba\u001a\u000b7\r^8ss\u000611+Z9NCB\u0004\"A\u0006\u0003\u0014\u0005\u0011\u0011\u0005cA\"G[9\u0011\u0001\u0006R\u0005\u0003\u000b*\t!\"T1q\r\u0006\u001cGo\u001c:z\u0013\t9\u0005J\u0001\u0005EK2,w-\u0019;f\u0015\t)%\"\u0001\u0004=S:LGO\u0010\u000b\u0002\u0001\u0006aqO]5uKJ+\u0007\u000f\\1dKR\tQ\n\u0005\u0002O'6\tqJ\u0003\u0002Q#\u0006!A.\u00198h\u0015\u0005\u0011\u0016\u0001\u00026bm\u0006L!\u0001V(\u0003\r=\u0013'.Z2u\u0001")
public interface SeqMap<K, V>
extends Map<K, V>,
scala.collection.SeqMap<K, V> {
    public static <K, V> Builder<Tuple2<K, V>, SeqMap<K, V>> newBuilder() {
        return SeqMap$.MODULE$.newBuilder();
    }

    public static Object from(IterableOnce it) {
        return SeqMap$.MODULE$.from(it);
    }

    public static /* synthetic */ MapFactory mapFactory$(SeqMap $this) {
        return $this.mapFactory();
    }

    @Override
    default public MapFactory<SeqMap> mapFactory() {
        return SeqMap$.MODULE$;
    }

    public static void $init$(SeqMap $this) {
    }
}

