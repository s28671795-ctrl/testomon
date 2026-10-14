/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.Done;
import akka.stream.QueueOfferResult;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M2qa\u0001\u0003\u0011\u0002G\u00051\u0002C\u0003\u0014\u0001\u0019\u0005A\u0003C\u0003-\u0001\u0019\u0005QFA\u0006T_V\u00148-Z)vKV,'BA\u0003\u0007\u0003!\u00198-\u00197bINd'BA\u0004\t\u0003\u0019\u0019HO]3b[*\t\u0011\"\u0001\u0003bW.\f7\u0001A\u000b\u0003\u0019\r\u001a\"\u0001A\u0007\u0011\u00059\tR\"A\b\u000b\u0003A\tQa]2bY\u0006L!AE\b\u0003\r\u0005s\u0017PU3g\u0003\u0015ygMZ3s)\t)r\u0004E\u0002\u00173mi\u0011a\u0006\u0006\u00031=\t!bY8oGV\u0014(/\u001a8u\u0013\tQrC\u0001\u0004GkR,(/\u001a\t\u00039ui\u0011AB\u0005\u0003=\u0019\u0011\u0001#U;fk\u0016|eMZ3s%\u0016\u001cX\u000f\u001c;\t\u000b\u0001\n\u0001\u0019A\u0011\u0002\t\u0015dW-\u001c\t\u0003E\rb\u0001\u0001B\u0003%\u0001\t\u0007QEA\u0001U#\t1\u0013\u0006\u0005\u0002\u000fO%\u0011\u0001f\u0004\u0002\b\u001d>$\b.\u001b8h!\tq!&\u0003\u0002,\u001f\t\u0019\u0011I\\=\u0002\u001f]\fGo\u00195D_6\u0004H.\u001a;j_:$\u0012A\f\t\u0004-ey\u0003C\u0001\u00192\u001b\u0005A\u0011B\u0001\u001a\t\u0005\u0011!uN\\3")
public interface SourceQueue<T> {
    public Future<QueueOfferResult> offer(T var1);

    public Future<Done> watchCompletion();
}

