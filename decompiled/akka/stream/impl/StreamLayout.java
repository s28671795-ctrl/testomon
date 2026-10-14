/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Graph;
import akka.stream.Shape;
import akka.stream.impl.StreamLayout$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0001;aAB\u0004\t\u0002%iaAB\b\b\u0011\u0003I\u0001\u0003C\u0003\u0018\u0003\u0011\u0005\u0011\u0004C\u0004\u001b\u0003\t\u0007IQA\u000e\t\ry\t\u0001\u0015!\u0004\u001d\r\u001dy\u0012\u0001%A\u0012\u0002\u0001\nAb\u0015;sK\u0006lG*Y=pkRT!\u0001C\u0005\u0002\t%l\u0007\u000f\u001c\u0006\u0003\u0015-\taa\u001d;sK\u0006l'\"\u0001\u0007\u0002\t\u0005\\7.\u0019\t\u0003\u001d\u0005i\u0011a\u0002\u0002\r'R\u0014X-Y7MCf|W\u000f^\n\u0003\u0003E\u0001\"AE\u000b\u000e\u0003MQ\u0011\u0001F\u0001\u0006g\u000e\fG.Y\u0005\u0003-M\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$hh\u0001\u0001\u0015\u00035\tQ\u0001R3ck\u001e,\u0012\u0001H\b\u0002;e\t\u0001!\u0001\u0004EK\n,x\r\t\u0002\r\u0003R|W.[2N_\u0012,H.Z\u000b\u0004C!\u00124cA\u0003\u0012EA!1\u0005\n\u00142\u001b\u0005I\u0011BA\u0013\n\u0005\u00159%/\u00199i!\t9\u0003\u0006\u0004\u0001\u0005\r%*AQ1\u0001+\u0005\u0005\u0019\u0016CA\u0016/!\t\u0011B&\u0003\u0002.'\t9aj\u001c;iS:<\u0007CA\u00120\u0013\t\u0001\u0014BA\u0003TQ\u0006\u0004X\r\u0005\u0002(e\u001111'\u0002CC\u0002Q\u0012\u0011!T\t\u0003WU\u0002\"A\u0005\u001c\n\u0005]\u001a\"aA!os\"\u0012\u0011!\u000f\t\u0003uuj\u0011a\u000f\u0006\u0003y-\t!\"\u00198o_R\fG/[8o\u0013\tq4HA\u0006J]R,'O\\1m\u0003BL\u0007F\u0001\u0001:\u0001")
@InternalApi
public final class StreamLayout {
    public static boolean Debug() {
        return StreamLayout$.MODULE$.Debug();
    }

    public static interface AtomicModule<S extends Shape, M>
    extends Graph<S, M> {
    }
}

