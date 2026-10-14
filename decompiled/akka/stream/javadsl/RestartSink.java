/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Creator;
import akka.stream.RestartSettings;
import akka.stream.javadsl.RestartSink$;
import akka.stream.javadsl.Sink;
import java.time.Duration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005=r!\u0002\u0004\b\u0011\u0003qa!\u0002\t\b\u0011\u0003\t\u0002\"\u0002\r\u0002\t\u0003I\u0002\"\u0002\u000e\u0002\t\u0003Y\u0002\"\u0002\u000e\u0002\t\u0003)\u0007B\u0002\u000e\u0002\t\u0003\t\t!A\u0006SKN$\u0018M\u001d;TS:\\'B\u0001\u0005\n\u0003\u001dQ\u0017M^1eg2T!AC\u0006\u0002\rM$(/Z1n\u0015\u0005a\u0011\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002\u0010\u00035\tqAA\u0006SKN$\u0018M\u001d;TS:\\7CA\u0001\u0013!\t\u0019b#D\u0001\u0015\u0015\u0005)\u0012!B:dC2\f\u0017BA\f\u0015\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012AD\u0001\fo&$\bNQ1dW>4g-\u0006\u0002\u001dEQ)QdL\u001d<\u0001B!qB\b\u0011,\u0013\tyrA\u0001\u0003TS:\\\u0007CA\u0011#\u0019\u0001!QaI\u0002C\u0002\u0011\u0012\u0011\u0001V\t\u0003K!\u0002\"a\u0005\u0014\n\u0005\u001d\"\"a\u0002(pi\"Lgn\u001a\t\u0003'%J!A\u000b\u000b\u0003\u0007\u0005s\u0017\u0010\u0005\u0002-[5\t1\"\u0003\u0002/\u0017\t9aj\u001c;Vg\u0016$\u0007\"\u0002\u0019\u0004\u0001\u0004\t\u0014AC7j]\n\u000b7m[8gMB\u0011!gN\u0007\u0002g)\u0011A'N\u0001\u0005i&lWMC\u00017\u0003\u0011Q\u0017M^1\n\u0005a\u001a$\u0001\u0003#ve\u0006$\u0018n\u001c8\t\u000bi\u001a\u0001\u0019A\u0019\u0002\u00155\f\u0007PQ1dW>4g\rC\u0003=\u0007\u0001\u0007Q(\u0001\u0007sC:$w.\u001c$bGR|'\u000f\u0005\u0002\u0014}%\u0011q\b\u0006\u0002\u0007\t>,(\r\\3\t\u000b\u0005\u001b\u0001\u0019\u0001\"\u0002\u0017MLgn\u001b$bGR|'/\u001f\t\u0004\u0007\"SU\"\u0001#\u000b\u0005\u00153\u0015\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005\u001d[\u0011\u0001\u00026ba&L!!\u0013#\u0003\u000f\r\u0013X-\u0019;peB\u00121*\u0014\t\u0005\u001fy\u0001C\n\u0005\u0002\"\u001b\u0012IajTA\u0001\u0002\u0003\u0015\t\u0001\n\u0002\u0004?\u0012\n\u0004\"B!\u0004\u0001\u0004\u0001\u0006cA\"I#B\u0012!+\u0014\t\u0005\u001fy\u0019F\n\u0005\u0002\"E!21!\u0016-Z7r\u0003\"a\u0005,\n\u0005]#\"A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017%\u0001.\u0002\u0019V\u001bX\r\t;iK\u0002zg/\u001a:m_\u0006$W\r\u001a\u0011nKRDw\u000e\u001a\u0011xQ&\u001c\u0007\u000eI1dG\u0016\u0004Ho\u001d\u0011bW.\fgf\u001d;sK\u0006lgFU3ti\u0006\u0014HoU3ui&twm\u001d\u0011j]N$X-\u00193/\u0003\u0015\u0019\u0018N\\2fC\u0005i\u0016A\u0002\u001a/m9\n\u0004\u0007\u000b\u0002\u0004?B\u0011\u0001mY\u0007\u0002C*\u0011!-N\u0001\u0005Y\u0006tw-\u0003\u0002eC\nQA)\u001a9sK\u000e\fG/\u001a3\u0016\u0005\u0019LGCB4kW2l'\u000f\u0005\u0003\u0010=!\\\u0003CA\u0011j\t\u0015\u0019CA1\u0001%\u0011\u0015\u0001D\u00011\u00012\u0011\u0015QD\u00011\u00012\u0011\u0015aD\u00011\u0001>\u0011\u0015qG\u00011\u0001p\u0003-i\u0017\r\u001f*fgR\f'\u000f^:\u0011\u0005M\u0001\u0018BA9\u0015\u0005\rIe\u000e\u001e\u0005\u0006\u0003\u0012\u0001\ra\u001d\t\u0004\u0007\"#\bGA;x!\u0011ya\u0004\u001b<\u0011\u0005\u0005:H!\u0003=z\u0003\u0003\u0005\tQ!\u0001%\u0005\ryFE\r\u0005\u0006\u0003\u0012\u0001\rA\u001f\t\u0004\u0007\"[\bG\u0001?x!\u0011ya$ <\u0011\u0005\u0005J\u0007F\u0002\u0003V1f[F\f\u000b\u0002\u0005?V!\u00111AA\u0005)\u0019\t)!a\u0003\u0002\u0018A)qBHA\u0004WA\u0019\u0011%!\u0003\u0005\u000b\r*!\u0019\u0001\u0013\t\u000f\u00055Q\u00011\u0001\u0002\u0010\u0005A1/\u001a;uS:<7\u000f\u0005\u0003\u0002\u0012\u0005MQ\"A\u0005\n\u0007\u0005U\u0011BA\bSKN$\u0018M\u001d;TKR$\u0018N\\4t\u0011\u0019\tU\u00011\u0001\u0002\u001aA!1\tSA\u000ea\u0011\ti\"!\t\u0011\r=q\u0012qAA\u0010!\r\t\u0013\u0011\u0005\u0003\f\u0003G\t)#!A\u0001\u0002\u000b\u0005AEA\u0002`IMBa!Q\u0003A\u0002\u0005\u001d\u0002\u0003B\"I\u0003S\u0001D!a\u000b\u0002\"A1qBHA\u0017\u0003?\u00012!IA\u0005\u0001")
public final class RestartSink {
    public static <T> Sink<T, NotUsed> withBackoff(RestartSettings settings, Creator<Sink<T, ?>> sinkFactory) {
        return RestartSink$.MODULE$.withBackoff(settings, sinkFactory);
    }

    @Deprecated
    public static <T> Sink<T, NotUsed> withBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, int maxRestarts, Creator<Sink<T, ?>> sinkFactory) {
        return RestartSink$.MODULE$.withBackoff(minBackoff, maxBackoff, randomFactor, maxRestarts, sinkFactory);
    }

    @Deprecated
    public static <T> Sink<T, NotUsed> withBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, Creator<Sink<T, ?>> sinkFactory) {
        return RestartSink$.MODULE$.withBackoff(minBackoff, maxBackoff, randomFactor, sinkFactory);
    }
}

