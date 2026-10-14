/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Creator;
import akka.stream.RestartSettings;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.RestartFlow$;
import java.time.Duration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dv!\u0002\u0005\n\u0011\u0003\u0001b!\u0002\n\n\u0011\u0003\u0019\u0002\"\u0002\u000e\u0002\t\u0003Y\u0002\"\u0002\u000f\u0002\t\u0003i\u0002\"\u0002\u000f\u0002\t\u0003Y\u0007B\u0002\u000f\u0002\t\u0003\t\u0019\u0002C\u0004\u0002H\u0005!\t!!\u0013\t\u000f\u0005\u001d\u0013\u0001\"\u0001\u0002~\u0005Y!+Z:uCJ$h\t\\8x\u0015\tQ1\"A\u0004kCZ\fGm\u001d7\u000b\u00051i\u0011AB:ue\u0016\fWNC\u0001\u000f\u0003\u0011\t7n[1\u0004\u0001A\u0011\u0011#A\u0007\u0002\u0013\tY!+Z:uCJ$h\t\\8x'\t\tA\u0003\u0005\u0002\u001615\taCC\u0001\u0018\u0003\u0015\u00198-\u00197b\u0013\tIbC\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003A\t1b^5uQ\n\u000b7m[8gMV\u0019a\u0004\n\u0018\u0015\u000b}!d\bQ#\u0011\u000bE\u0001#%\f\u0019\n\u0005\u0005J!\u0001\u0002$m_^\u0004\"a\t\u0013\r\u0001\u0011)Qe\u0001b\u0001M\t\u0011\u0011J\\\t\u0003O)\u0002\"!\u0006\u0015\n\u0005%2\"a\u0002(pi\"Lgn\u001a\t\u0003+-J!\u0001\f\f\u0003\u0007\u0005s\u0017\u0010\u0005\u0002$]\u0011)qf\u0001b\u0001M\t\u0019q*\u001e;\u0011\u0005E\u0012T\"A\u0007\n\u0005Mj!a\u0002(piV\u001bX\r\u001a\u0005\u0006k\r\u0001\rAN\u0001\u000b[&t')Y2l_\u001a4\u0007CA\u001c=\u001b\u0005A$BA\u001d;\u0003\u0011!\u0018.\\3\u000b\u0003m\nAA[1wC&\u0011Q\b\u000f\u0002\t\tV\u0014\u0018\r^5p]\")qh\u0001a\u0001m\u0005QQ.\u0019=CC\u000e\\wN\u001a4\t\u000b\u0005\u001b\u0001\u0019\u0001\"\u0002\u0019I\fg\u000eZ8n\r\u0006\u001cGo\u001c:\u0011\u0005U\u0019\u0015B\u0001#\u0017\u0005\u0019!u.\u001e2mK\")ai\u0001a\u0001\u000f\u0006Ya\r\\8x\r\u0006\u001cGo\u001c:z!\rAUjT\u0007\u0002\u0013*\u0011!jS\u0001\tMVt7\r^5p]*\u0011A*D\u0001\u0005U\u0006\u0004\u0018.\u0003\u0002O\u0013\n91I]3bi>\u0014\bG\u0001)S!\u0015\t\u0002EI\u0017R!\t\u0019#\u000bB\u0005T)\u0006\u0005\t\u0011!B\u0001M\t\u0019q\fJ\u0019\t\u000b\u0019\u001b\u0001\u0019A+\u0011\u0007!ke\u000b\r\u0002X%B)\u0011\u0003\t-Z#B\u00111\u0005\n\t\u0003G9BcaA._?\u0006\u0014\u0007CA\u000b]\u0013\tifC\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-I\u0001a\u00031+6/\u001a\u0011uQ\u0016\u0004sN^3sY>\fG-\u001a3![\u0016$\bn\u001c3!o\"L7\r\u001b\u0011bG\u000e,\u0007\u000f^:!C.\\\u0017ML:ue\u0016\fWN\f*fgR\f'\u000f^*fiRLgnZ:!S:\u001cH/Z1e]\u0005)1/\u001b8dK\u0006\n1-\u0001\u00043]Yr\u0013\u0007\r\u0015\u0003\u0007\u0015\u0004\"AZ5\u000e\u0003\u001dT!\u0001\u001b\u001e\u0002\t1\fgnZ\u0005\u0003U\u001e\u0014!\u0002R3qe\u0016\u001c\u0017\r^3e+\raw.\u001d\u000b\u0007[J\u001cH/\u001e>\u0011\u000bE\u0001c\u000e\u001d\u0019\u0011\u0005\rzG!B\u0013\u0005\u0005\u00041\u0003CA\u0012r\t\u0015yCA1\u0001'\u0011\u0015)D\u00011\u00017\u0011\u0015yD\u00011\u00017\u0011\u0015\tE\u00011\u0001C\u0011\u00151H\u00011\u0001x\u0003-i\u0017\r\u001f*fgR\f'\u000f^:\u0011\u0005UA\u0018BA=\u0017\u0005\rIe\u000e\u001e\u0005\u0006\r\u0012\u0001\ra\u001f\t\u0004\u00116c\bGA?\u0000!\u0015\t\u0002E\u001c9\u007f!\t\u0019s\u0010B\u0006\u0002\u0002\u0005\r\u0011\u0011!A\u0001\u0006\u00031#aA0%e!1a\t\u0002a\u0001\u0003\u000b\u0001B\u0001S'\u0002\bA\u001a\u0011\u0011B@\u0011\u000fE\u0001\u00131BA\u0007}B\u00111e\u001c\t\u0003GEDc\u0001B._?\u0006\u0014\u0007F\u0001\u0003f+\u0019\t)\"a\u0007\u0002 Q1\u0011qCA\u0011\u0003[\u0001r!\u0005\u0011\u0002\u001a\u0005u\u0001\u0007E\u0002$\u00037!Q!J\u0003C\u0002\u0019\u00022aIA\u0010\t\u0015ySA1\u0001'\u0011\u001d\t\u0019#\u0002a\u0001\u0003K\t\u0001b]3ui&twm\u001d\t\u0005\u0003O\tI#D\u0001\f\u0013\r\tYc\u0003\u0002\u0010%\u0016\u001cH/\u0019:u'\u0016$H/\u001b8hg\"1a)\u0002a\u0001\u0003_\u0001B\u0001S'\u00022A\"\u00111GA\u001c!!\t\u0002%!\u0007\u0002\u001e\u0005U\u0002cA\u0012\u00028\u0011Y\u0011\u0011HA\u001e\u0003\u0003\u0005\tQ!\u0001'\u0005\ryFe\r\u0005\u0007\r\u0016\u0001\r!!\u0010\u0011\t!k\u0015q\b\u0019\u0005\u0003\u0003\n9\u0004\u0005\u0005\u0012A\u0005\r\u0013QIA\u001b!\r\u0019\u00131\u0004\t\u0004G\u0005}\u0011!F8o\r\u0006LG.\u001e:fg^KG\u000f\u001b\"bG.|gMZ\u000b\u0007\u0003\u0017\n\t&!\u0016\u0015\u0019\u00055\u0013qKA-\u00037\ni&a\u0018\u0011\u000fE\u0001\u0013qJA*aA\u00191%!\u0015\u0005\u000b\u00152!\u0019\u0001\u0014\u0011\u0007\r\n)\u0006B\u00030\r\t\u0007a\u0005C\u00036\r\u0001\u0007a\u0007C\u0003@\r\u0001\u0007a\u0007C\u0003B\r\u0001\u0007!\tC\u0003w\r\u0001\u0007q\u000f\u0003\u0004G\r\u0001\u0007\u0011\u0011\r\t\u0005\u00116\u000b\u0019\u0007\r\u0003\u0002f\u0005%\u0004\u0003C\t!\u0003\u001f\n\u0019&a\u001a\u0011\u0007\r\nI\u0007B\u0006\u0002l\u00055\u0014\u0011!A\u0001\u0006\u00031#aA0%i!1aI\u0002a\u0001\u0003_\u0002B\u0001S'\u0002rA\"\u00111OA5!!\t\u0002%!\u001e\u0002x\u0005\u001d\u0004cA\u0012\u0002RA\u00191%!\u0016)\r\u0019YflX1cQ\t1Q-\u0006\u0004\u0002\u0000\u0005\u0015\u0015\u0011\u0012\u000b\u0007\u0003\u0003\u000bY)!$\u0011\u000fE\u0001\u00131QADaA\u00191%!\"\u0005\u000b\u0015:!\u0019\u0001\u0014\u0011\u0007\r\nI\tB\u00030\u000f\t\u0007a\u0005C\u0004\u0002$\u001d\u0001\r!!\n\t\r\u0019;\u0001\u0019AAH!\u0011AU*!%1\t\u0005M\u0015q\u0013\t\t#\u0001\n\u0019)a\"\u0002\u0016B\u00191%a&\u0005\u0017\u0005e\u00151TA\u0001\u0002\u0003\u0015\tA\n\u0002\u0004?\u0012*\u0004B\u0002$\b\u0001\u0004\ti\n\u0005\u0003I\u001b\u0006}\u0005\u0007BAQ\u0003/\u0003\u0002\"\u0005\u0011\u0002$\u0006\u0015\u0016Q\u0013\t\u0004G\u0005\u0015\u0005cA\u0012\u0002\n\u0002")
public final class RestartFlow {
    public static <In, Out> Flow<In, Out, NotUsed> onFailuresWithBackoff(RestartSettings settings, Creator<Flow<In, Out, ?>> flowFactory) {
        return RestartFlow$.MODULE$.onFailuresWithBackoff(settings, flowFactory);
    }

    @Deprecated
    public static <In, Out> Flow<In, Out, NotUsed> onFailuresWithBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, int maxRestarts, Creator<Flow<In, Out, ?>> flowFactory) {
        return RestartFlow$.MODULE$.onFailuresWithBackoff(minBackoff, maxBackoff, randomFactor, maxRestarts, flowFactory);
    }

    public static <In, Out> Flow<In, Out, NotUsed> withBackoff(RestartSettings settings, Creator<Flow<In, Out, ?>> flowFactory) {
        return RestartFlow$.MODULE$.withBackoff(settings, flowFactory);
    }

    @Deprecated
    public static <In, Out> Flow<In, Out, NotUsed> withBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, int maxRestarts, Creator<Flow<In, Out, ?>> flowFactory) {
        return RestartFlow$.MODULE$.withBackoff(minBackoff, maxBackoff, randomFactor, maxRestarts, flowFactory);
    }

    @Deprecated
    public static <In, Out> Flow<In, Out, NotUsed> withBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, Creator<Flow<In, Out, ?>> flowFactory) {
        return RestartFlow$.MODULE$.withBackoff(minBackoff, maxBackoff, randomFactor, flowFactory);
    }
}

