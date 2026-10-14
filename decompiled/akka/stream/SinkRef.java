/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.NotUsed;
import akka.annotation.DoNotInherit;
import akka.stream.SinkRef$;
import akka.stream.javadsl.Sink;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A;Q\u0001C\u0005\t\u000291Q\u0001E\u0005\t\u0002EAQ\u0001G\u0001\u0005\u0002eAQAG\u0001\u0005\u0004m1q\u0001E\u0005\u0011\u0002\u0007\u0005Q\u0007C\u00038\t\u0011\u0005\u0001\bC\u0003=\t\u0019\u0005Q\bC\u0003C\t\u0011\u00151)A\u0004TS:\\'+\u001a4\u000b\u0005)Y\u0011AB:ue\u0016\fWNC\u0001\r\u0003\u0011\t7n[1\u0004\u0001A\u0011q\"A\u0007\u0002\u0013\t91+\u001b8l%\u001647CA\u0001\u0013!\t\u0019b#D\u0001\u0015\u0015\u0005)\u0012!B:dC2\f\u0017BA\f\u0015\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012AD\u0001\u0011G>tg/\u001a:u%\u00164Gk\\*j].,\"\u0001H\u0013\u0015\u0005u\u0011\u0004\u0003\u0002\u0010\"G9j\u0011a\b\u0006\u0003A%\t\u0001b]2bY\u0006$7\u000f\\\u0005\u0003E}\u0011AaU5oWB\u0011A%\n\u0007\u0001\t\u001513A1\u0001(\u0005\u0005!\u0016C\u0001\u0015,!\t\u0019\u0012&\u0003\u0002+)\t9aj\u001c;iS:<\u0007CA\n-\u0013\tiCCA\u0002B]f\u0004\"a\f\u0019\u000e\u0003-I!!M\u0006\u0003\u000f9{G/V:fI\")1g\u0001a\u0001i\u000591/\u001b8l%\u00164\u0007cA\b\u0005GU\u0011a\u0007Q\n\u0003\tI\ta\u0001J5oSR$C#A\u001d\u0011\u0005MQ\u0014BA\u001e\u0015\u0005\u0011)f.\u001b;\u0002\tMLgn\u001b\u000b\u0002}A!a$I /!\t!\u0003\tB\u0003B\t\t\u0007qE\u0001\u0002J]\u00069q-\u001a;TS:\\G#\u0001#\u0011\t\u0015CuHL\u0007\u0002\r*\u0011q)C\u0001\bU\u00064\u0018\rZ:m\u0013\t\u0011c\t\u000b\u0002\u0005\u0015B\u00111JT\u0007\u0002\u0019*\u0011QjC\u0001\u000bC:tw\u000e^1uS>t\u0017BA(M\u00051!uNT8u\u0013:DWM]5u\u0001")
@DoNotInherit
public interface SinkRef<In> {
    public static <T> akka.stream.scaladsl.Sink<T, NotUsed> convertRefToSink(SinkRef<T> sinkRef) {
        return SinkRef$.MODULE$.convertRefToSink(sinkRef);
    }

    public akka.stream.scaladsl.Sink<In, NotUsed> sink();

    public static /* synthetic */ Sink getSink$(SinkRef $this) {
        return $this.getSink();
    }

    default public Sink<In, NotUsed> getSink() {
        return this.sink().asJava();
    }

    public static void $init$(SinkRef $this) {
    }
}

