/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.Completed$;
import akka.stream.impl.TransferState;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005):a!\u0002\u0004\t\u0002)aaA\u0002\b\u0007\u0011\u0003Qq\u0002C\u0003\u001a\u0003\u0011\u00051\u0004C\u0003\u001d\u0003\u0011\u0005Q\u0004C\u0003\"\u0003\u0011\u0005Q$A\u0005D_6\u0004H.\u001a;fI*\u0011q\u0001C\u0001\u0005S6\u0004HN\u0003\u0002\n\u0015\u000511\u000f\u001e:fC6T\u0011aC\u0001\u0005C.\\\u0017\r\u0005\u0002\u000e\u00035\taAA\u0005D_6\u0004H.\u001a;fIN\u0019\u0011\u0001\u0005\f\u0011\u0005E!R\"\u0001\n\u000b\u0003M\tQa]2bY\u0006L!!\u0006\n\u0003\r\u0005s\u0017PU3g!\tiq#\u0003\u0002\u0019\r\tiAK]1og\u001a,'o\u0015;bi\u0016\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002\u0019\u00059\u0011n\u001d*fC\u0012LX#\u0001\u0010\u0011\u0005Ey\u0012B\u0001\u0011\u0013\u0005\u001d\u0011un\u001c7fC:\f1\"[:D_6\u0004H.\u001a;fI\"\u0012\u0011a\t\t\u0003I\u001dj\u0011!\n\u0006\u0003M)\t!\"\u00198o_R\fG/[8o\u0013\tASEA\u0006J]R,'O\\1m\u0003BL\u0007F\u0001\u0001$\u0001")
@InternalApi
public final class Completed {
    public static boolean isCompleted() {
        return Completed$.MODULE$.isCompleted();
    }

    public static boolean isReady() {
        return Completed$.MODULE$.isReady();
    }

    public static TransferState $amp$amp(TransferState other) {
        return Completed$.MODULE$.$amp$amp(other);
    }

    public static TransferState $bar$bar(TransferState other) {
        return Completed$.MODULE$.$bar$bar(other);
    }

    public static boolean isExecutable() {
        return Completed$.MODULE$.isExecutable();
    }
}

