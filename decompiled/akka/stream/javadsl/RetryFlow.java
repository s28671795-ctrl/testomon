/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.annotation.ApiMayChange;
import akka.japi.Pair;
import akka.japi.function.Function2;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.FlowWithContext;
import akka.stream.javadsl.RetryFlow$;
import java.time.Duration;
import java.util.Optional;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015q!B\u0003\u0007\u0011\u0003ia!B\b\u0007\u0011\u0003\u0001\u0002\"B\f\u0002\t\u0003A\u0002\"B\r\u0002\t\u0003Q\u0002\"\u00022\u0002\t\u0003\u0019\u0017!\u0003*fiJLh\t\\8x\u0015\t9\u0001\"A\u0004kCZ\fGm\u001d7\u000b\u0005%Q\u0011AB:ue\u0016\fWNC\u0001\f\u0003\u0011\t7n[1\u0004\u0001A\u0011a\"A\u0007\u0002\r\tI!+\u001a;ss\u001acwn^\n\u0003\u0003E\u0001\"AE\u000b\u000e\u0003MQ\u0011\u0001F\u0001\u0006g\u000e\fG.Y\u0005\u0003-M\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\u000e\u0003-9\u0018\u000e\u001e5CC\u000e\\wN\u001a4\u0016\tm\t3F\f\u000b\b9ARD(\u0011$I!\u0015qQd\b\u0016.\u0013\tqbA\u0001\u0003GY><\bC\u0001\u0011\"\u0019\u0001!QAI\u0002C\u0002\r\u0012!!\u00138\u0012\u0005\u0011:\u0003C\u0001\n&\u0013\t13CA\u0004O_RD\u0017N\\4\u0011\u0005IA\u0013BA\u0015\u0014\u0005\r\te.\u001f\t\u0003A-\"Q\u0001L\u0002C\u0002\r\u00121aT;u!\t\u0001c\u0006B\u00030\u0007\t\u00071EA\u0002NCRDQ!M\u0002A\u0002I\n!\"\\5o\u0005\u0006\u001c7n\u001c4g!\t\u0019\u0004(D\u00015\u0015\t)d'\u0001\u0003uS6,'\"A\u001c\u0002\t)\fg/Y\u0005\u0003sQ\u0012\u0001\u0002R;sCRLwN\u001c\u0005\u0006w\r\u0001\rAM\u0001\u000b[\u0006D()Y2l_\u001a4\u0007\"B\u001f\u0004\u0001\u0004q\u0014\u0001\u0004:b]\u0012|WNR1di>\u0014\bC\u0001\n@\u0013\t\u00015C\u0001\u0004E_V\u0014G.\u001a\u0005\u0006\u0005\u000e\u0001\raQ\u0001\u000b[\u0006D(+\u001a;sS\u0016\u001c\bC\u0001\nE\u0013\t)5CA\u0002J]RDQaR\u0002A\u0002q\tAA\u001a7po\")\u0011j\u0001a\u0001\u0015\u0006YA-Z2jI\u0016\u0014V\r\u001e:z!\u0015Y\u0005k\b\u0016S\u001b\u0005a%BA'O\u0003!1WO\\2uS>t'BA(\u000b\u0003\u0011Q\u0017\r]5\n\u0005Ec%!\u0003$v]\u000e$\u0018n\u001c83!\r\u0019fkH\u0007\u0002)*\u0011QKN\u0001\u0005kRLG.\u0003\u0002X)\nAq\n\u001d;j_:\fG\u000e\u000b\u0003\u00043~\u0003\u0007C\u0001.^\u001b\u0005Y&B\u0001/\u000b\u0003)\tgN\\8uCRLwN\\\u0005\u0003=n\u0013A\"\u00119j\u001b\u0006L8\t[1oO\u0016\fQ![:tk\u0016\f\u0013!Y\u0001*QR$\bo\u001d\u001e0_\u001dLG\u000f[;c]\r|WnL1lW\u0006|\u0013m[6b_%\u001c8/^3t_I:\u0014H\u000e\u0019\u0002+]LG\u000f\u001b\"bG.|gMZ!oI\u000e{g\u000e^3yiV1A-[6oaN$r!\u001a;vm^D\u0018\u0010E\u0004\u000fM\"TWn\u001c:\n\u0005\u001d4!a\u0004$m_^<\u0016\u000e\u001e5D_:$X\r\u001f;\u0011\u0005\u0001JG!\u0002\u0012\u0005\u0005\u0004\u0019\u0003C\u0001\u0011l\t\u0015aGA1\u0001$\u0005\u0015Ien\u0011;y!\t\u0001c\u000eB\u0003-\t\t\u00071\u0005\u0005\u0002!a\u0012)\u0011\u000f\u0002b\u0001G\t1q*\u001e;Dib\u0004\"\u0001I:\u0005\u000b=\"!\u0019A\u0012\t\u000bE\"\u0001\u0019\u0001\u001a\t\u000bm\"\u0001\u0019\u0001\u001a\t\u000bu\"\u0001\u0019\u0001 \t\u000b\t#\u0001\u0019A\"\t\u000b\u001d#\u0001\u0019A3\t\u000b%#\u0001\u0019\u0001>\u0011\r-\u00036p`A\u0001!\u0011aX\u0010\u001b6\u000e\u00039K!A (\u0003\tA\u000b\u0017N\u001d\t\u0005yvlw\u000eE\u0002T-nDC\u0001B-`A\u0002")
public final class RetryFlow {
    @ApiMayChange(issue="https://github.com/akka/akka/issues/27960")
    public static <In, InCtx, Out, OutCtx, Mat> FlowWithContext<In, InCtx, Out, OutCtx, Mat> withBackoffAndContext(Duration minBackoff, Duration maxBackoff, double randomFactor, int maxRetries, FlowWithContext<In, InCtx, Out, OutCtx, Mat> flow, Function2<Pair<In, InCtx>, Pair<Out, OutCtx>, Optional<Pair<In, InCtx>>> decideRetry) {
        return RetryFlow$.MODULE$.withBackoffAndContext(minBackoff, maxBackoff, randomFactor, maxRetries, flow, decideRetry);
    }

    @ApiMayChange(issue="https://github.com/akka/akka/issues/27960")
    public static <In, Out, Mat> Flow<In, Out, Mat> withBackoff(Duration minBackoff, Duration maxBackoff, double randomFactor, int maxRetries, Flow<In, Out, Mat> flow, Function2<In, Out, Optional<In>> decideRetry) {
        return RetryFlow$.MODULE$.withBackoff(minBackoff, maxBackoff, randomFactor, maxRetries, flow, decideRetry);
    }
}

