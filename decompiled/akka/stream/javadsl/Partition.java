/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Function;
import akka.stream.Graph;
import akka.stream.UniformFanOutShape;
import akka.stream.javadsl.Partition$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dq!B\u0004\t\u0011\u0003ya!B\t\t\u0011\u0003\u0011\u0002\"B\r\u0002\t\u0003Q\u0002\"B\u000e\u0002\t\u0003a\u0002\"B\u000e\u0002\t\u0003Y\u0005\"B\u000e\u0002\t\u0003I\u0006\"B\u000e\u0002\t\u00031\u0018!\u0003)beRLG/[8o\u0015\tI!\"A\u0004kCZ\fGm\u001d7\u000b\u0005-a\u0011AB:ue\u0016\fWNC\u0001\u000e\u0003\u0011\t7n[1\u0004\u0001A\u0011\u0001#A\u0007\u0002\u0011\tI\u0001+\u0019:uSRLwN\\\n\u0003\u0003M\u0001\"\u0001F\f\u000e\u0003UQ\u0011AF\u0001\u0006g\u000e\fG.Y\u0005\u00031U\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\u0010\u0003\u0019\u0019'/Z1uKV\u0011Qd\n\u000b\u0004=QJ\u0004\u0003B\u0010!EAj\u0011AC\u0005\u0003C)\u0011Qa\u0012:ba\"\u0004BaH\u0012&K%\u0011AE\u0003\u0002\u0013+:Lgm\u001c:n\r\u0006tw*\u001e;TQ\u0006\u0004X\r\u0005\u0002'O1\u0001A!\u0002\u0015\u0004\u0005\u0004I#!\u0001+\u0012\u0005)j\u0003C\u0001\u000b,\u0013\taSCA\u0004O_RD\u0017N\\4\u0011\u0005Qq\u0013BA\u0018\u0016\u0005\r\te.\u001f\t\u0003cIj\u0011\u0001D\u0005\u0003g1\u0011qAT8u+N,G\rC\u00036\u0007\u0001\u0007a'A\u0006pkR\u0004X\u000f^\"pk:$\bC\u0001\u000b8\u0013\tATCA\u0002J]RDQAO\u0002A\u0002m\n1\u0002]1si&$\u0018n\u001c8feB!A(Q\u0013D\u001b\u0005i$B\u0001 @\u0003!1WO\\2uS>t'B\u0001!\r\u0003\u0011Q\u0017\r]5\n\u0005\tk$\u0001\u0003$v]\u000e$\u0018n\u001c8\u0011\u0005\u0011KU\"A#\u000b\u0005\u0019;\u0015\u0001\u00027b]\u001eT\u0011\u0001S\u0001\u0005U\u00064\u0018-\u0003\u0002K\u000b\n9\u0011J\u001c;fO\u0016\u0014XC\u0001'Q)\u0011i\u0015K\u0015+\u0011\t}\u0001c\n\r\t\u0005?\rzu\n\u0005\u0002'!\u0012)\u0001\u0006\u0002b\u0001S!)Q\u0007\u0002a\u0001m!)!\b\u0002a\u0001'B!A(Q(D\u0011\u0015)F\u00011\u0001W\u0003-)\u0017mZ3s\u0007\u0006t7-\u001a7\u0011\u0005Q9\u0016B\u0001-\u0016\u0005\u001d\u0011un\u001c7fC:,\"A\u00170\u0015\tm{6\u000f\u001e\t\u0005?\u0001b\u0006\u0007\u0005\u0003 Guk\u0006C\u0001\u0014_\t\u0015ASA1\u0001*\u0011\u0015\u0001W\u00011\u0001b\u0003\u0015\u0019G.\u0019>{!\r\u0011\u0017.\u0018\b\u0003G\u001e\u0004\"\u0001Z\u000b\u000e\u0003\u0015T!A\u001a\b\u0002\rq\u0012xn\u001c;?\u0013\tAW#\u0001\u0004Qe\u0016$WMZ\u0005\u0003U.\u0014Qa\u00117bgNT!\u0001[\u000b)\u0005}k\u0007C\u00018r\u001b\u0005y'B\u00019\r\u0003\u0011)H/\u001b7\n\u0005I|'AB;okN,G\rC\u00036\u000b\u0001\u0007a\u0007C\u0003;\u000b\u0001\u0007Q\u000f\u0005\u0003=\u0003v\u001bUCA<|)\u001dAHp`A\u0001\u0003\u000b\u0001Ba\b\u0011zaA!qd\t>{!\t13\u0010B\u0003)\r\t\u0007\u0011\u0006C\u0003a\r\u0001\u0007Q\u0010E\u0002cSjD#\u0001`7\t\u000bU2\u0001\u0019\u0001\u001c\t\ri2\u0001\u0019AA\u0002!\u0011a\u0014I_\"\t\u000bU3\u0001\u0019\u0001,")
public final class Partition {
    public static <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(Class<T> clazz, int outputCount, Function<T, Integer> partitioner, boolean eagerCancel) {
        return Partition$.MODULE$.create(clazz, outputCount, partitioner, eagerCancel);
    }

    public static <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(Class<T> clazz, int outputCount, Function<T, Integer> partitioner) {
        return Partition$.MODULE$.create(clazz, outputCount, partitioner);
    }

    public static <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(int outputCount, Function<T, Integer> partitioner, boolean eagerCancel) {
        return Partition$.MODULE$.create(outputCount, partitioner, eagerCancel);
    }

    public static <T> Graph<UniformFanOutShape<T, T>, NotUsed> create(int outputCount, Function<T, Integer> partitioner) {
        return Partition$.MODULE$.create(outputCount, partitioner);
    }
}

