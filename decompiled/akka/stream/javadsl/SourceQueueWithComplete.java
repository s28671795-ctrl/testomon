/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.Done;
import akka.stream.javadsl.SourceQueue;
import akka.stream.javadsl.SourceQueueWithComplete$;
import java.util.concurrent.CompletionStage;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3q\u0001C\u0005\u0011\u0002G\u0005\u0001\u0003C\u0003(\u0001\u0019\u0005\u0001\u0006C\u0003-\u0001\u0019\u0005Q\u0006C\u0003=\u0001\u0019\u0005ShB\u0003M\u0013!\u0005QJB\u0003\t\u0013!\u0005a\nC\u0003P\u000b\u0011\u0005\u0001\u000bC\u0003R\u000b\u0011\u0005!KA\fT_V\u00148-Z)vKV,w+\u001b;i\u0007>l\u0007\u000f\\3uK*\u0011!bC\u0001\bU\u00064\u0018\rZ:m\u0015\taQ\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u001d\u0005!\u0011m[6b\u0007\u0001)\"!\u0005\u0010\u0014\u0007\u0001\u0011\u0002\u0004\u0005\u0002\u0014-5\tACC\u0001\u0016\u0003\u0015\u00198-\u00197b\u0013\t9BC\u0001\u0004B]f\u0014VM\u001a\t\u00043iaR\"A\u0005\n\u0005mI!aC*pkJ\u001cW-U;fk\u0016\u0004\"!\b\u0010\r\u0001\u0011)q\u0004\u0001b\u0001A\t\tA+\u0005\u0002\"IA\u00111CI\u0005\u0003GQ\u0011qAT8uQ&tw\r\u0005\u0002\u0014K%\u0011a\u0005\u0006\u0002\u0004\u0003:L\u0018\u0001C2p[BdW\r^3\u0015\u0003%\u0002\"a\u0005\u0016\n\u0005-\"\"\u0001B+oSR\fAAZ1jYR\u0011\u0011F\f\u0005\u0006_\t\u0001\r\u0001M\u0001\u0003Kb\u0004\"!M\u001d\u000f\u0005I:dBA\u001a7\u001b\u0005!$BA\u001b\u0010\u0003\u0019a$o\\8u}%\tQ#\u0003\u00029)\u00059\u0001/Y2lC\u001e,\u0017B\u0001\u001e<\u0005%!\u0006N]8xC\ndWM\u0003\u00029)\u0005yq/\u0019;dQ\u000e{W\u000e\u001d7fi&|g\u000eF\u0001?!\ryd\tS\u0007\u0002\u0001*\u0011\u0011IQ\u0001\u000bG>t7-\u001e:sK:$(BA\"E\u0003\u0011)H/\u001b7\u000b\u0003\u0015\u000bAA[1wC&\u0011q\t\u0011\u0002\u0010\u0007>l\u0007\u000f\\3uS>t7\u000b^1hKB\u0011\u0011JS\u0007\u0002\u001b%\u00111*\u0004\u0002\u0005\t>tW-A\fT_V\u00148-Z)vKV,w+\u001b;i\u0007>l\u0007\u000f\\3uKB\u0011\u0011$B\n\u0003\u000bI\ta\u0001P5oSRtD#A'\u0002\u000f\u0005\u001c8kY1mCV\u00111K\u0017\u000b\u0003)n\u00032!\u0016-Z\u001b\u00051&BA,\f\u0003!\u00198-\u00197bINd\u0017B\u0001\u0005W!\ti\"\fB\u0003 \u000f\t\u0007\u0001\u0005C\u0003]\u000f\u0001\u0007Q,A\u0003rk\u0016,X\rE\u0002\u001a\u0001e\u0003")
public interface SourceQueueWithComplete<T>
extends SourceQueue<T> {
    public static <T> akka.stream.scaladsl.SourceQueueWithComplete<T> asScala(SourceQueueWithComplete<T> queue) {
        return SourceQueueWithComplete$.MODULE$.asScala(queue);
    }

    public void complete();

    public void fail(Throwable var1);

    @Override
    public CompletionStage<Done> watchCompletion();
}

