/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import scala.Option;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005!2qAA\u0002\u0011\u0002G\u0005!\u0002C\u0003\u0013\u0001\u0019\u00051CA\u0005TS:\\\u0017+^3vK*\u0011A!B\u0001\tg\u000e\fG.\u00193tY*\u0011aaB\u0001\u0007gR\u0014X-Y7\u000b\u0003!\tA!Y6lC\u000e\u0001QCA\u0006 '\t\u0001A\u0002\u0005\u0002\u000e!5\taBC\u0001\u0010\u0003\u0015\u00198-\u00197b\u0013\t\tbB\u0001\u0004B]f\u0014VMZ\u0001\u0005aVdG\u000eF\u0001\u0015!\r)\u0002DG\u0007\u0002-)\u0011qCD\u0001\u000bG>t7-\u001e:sK:$\u0018BA\r\u0017\u0005\u00191U\u000f^;sKB\u0019QbG\u000f\n\u0005qq!AB(qi&|g\u000e\u0005\u0002\u001f?1\u0001A!\u0002\u0011\u0001\u0005\u0004\t#!\u0001+\u0012\u0005\t*\u0003CA\u0007$\u0013\t!cBA\u0004O_RD\u0017N\\4\u0011\u000551\u0013BA\u0014\u000f\u0005\r\te.\u001f")
public interface SinkQueue<T> {
    public Future<Option<T>> pull();
}

