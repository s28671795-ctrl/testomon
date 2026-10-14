/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.annotation.ApiMayChange;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.FlowWithContext;
import akka.stream.scaladsl.RetryFlow$;
import scala.Function2;
import scala.Option;
import scala.Tuple2;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m<Q!\u0002\u0004\t\u000251Qa\u0004\u0004\t\u0002AAQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002iAQaW\u0001\u0005\u0002q\u000b\u0011BU3uef4En\\<\u000b\u0005\u001dA\u0011\u0001C:dC2\fGm\u001d7\u000b\u0005%Q\u0011AB:ue\u0016\fWNC\u0001\f\u0003\u0011\t7n[1\u0004\u0001A\u0011a\"A\u0007\u0002\r\tI!+\u001a;ss\u001acwn^\n\u0003\u0003E\u0001\"AE\u000b\u000e\u0003MQ\u0011\u0001F\u0001\u0006g\u000e\fG.Y\u0005\u0003-M\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001\u000e\u0003-9\u0018\u000e\u001e5CC\u000e\\wN\u001a4\u0016\tm\u0011Cf\f\u000b\u00079e\u001aUIS(\u0015\u0005u\t\u0004#\u0002\b\u001fA-r\u0013BA\u0010\u0007\u0005\u00111En\\<\u0011\u0005\u0005\u0012C\u0002\u0001\u0003\u0006G\r\u0011\r\u0001\n\u0002\u0003\u0013:\f\"!\n\u0015\u0011\u0005I1\u0013BA\u0014\u0014\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"AE\u0015\n\u0005)\u001a\"aA!osB\u0011\u0011\u0005\f\u0003\u0006[\r\u0011\r\u0001\n\u0002\u0004\u001fV$\bCA\u00110\t\u0015\u00014A1\u0001%\u0005\ri\u0015\r\u001e\u0005\u0006e\r\u0001\raM\u0001\fI\u0016\u001c\u0017\u000eZ3SKR\u0014\u0018\u0010E\u0003\u0013i\u0001Zc'\u0003\u00026'\tIa)\u001e8di&|gN\r\t\u0004%]\u0002\u0013B\u0001\u001d\u0014\u0005\u0019y\u0005\u000f^5p]\")!h\u0001a\u0001w\u0005QQ.\u001b8CC\u000e\\wN\u001a4\u0011\u0005q\nU\"A\u001f\u000b\u0005yz\u0014\u0001\u00033ve\u0006$\u0018n\u001c8\u000b\u0005\u0001\u001b\u0012AC2p]\u000e,(O]3oi&\u0011!)\u0010\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8o\u0011\u0015!5\u00011\u0001<\u0003)i\u0017\r\u001f\"bG.|gM\u001a\u0005\u0006\r\u000e\u0001\raR\u0001\re\u0006tGm\\7GC\u000e$xN\u001d\t\u0003%!K!!S\n\u0003\r\u0011{WO\u00197f\u0011\u0015Y5\u00011\u0001M\u0003)i\u0017\r\u001f*fiJLWm\u001d\t\u0003%5K!AT\n\u0003\u0007%sG\u000fC\u0003Q\u0007\u0001\u0007Q$\u0001\u0003gY><\b\u0006B\u0002S1f\u0003\"a\u0015,\u000e\u0003QS!!\u0016\u0006\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002X)\na\u0011\t]5NCf\u001c\u0005.\u00198hK\u0006)\u0011n]:vK\u0006\n!,A\u0015iiR\u00048OO\u00180O&$\b.\u001e2/G>lw&Y6lC>\n7n[10SN\u001cX/Z:0e]Jd\u0007M\u0001\u0016o&$\bNQ1dW>4g-\u00118e\u0007>tG/\u001a=u+\u0019i6-\u001a5k[R1a,\u001e<xqf$\"a\u00188\u0011\u000f9\u0001'\rZ4jY&\u0011\u0011M\u0002\u0002\u0010\r2|woV5uQ\u000e{g\u000e^3yiB\u0011\u0011e\u0019\u0003\u0006G\u0011\u0011\r\u0001\n\t\u0003C\u0015$QA\u001a\u0003C\u0002\u0011\u0012Qa\u0011;y\u0013:\u0004\"!\t5\u0005\u000b5\"!\u0019\u0001\u0013\u0011\u0005\u0005RG!B6\u0005\u0005\u0004!#AB\"uq>+H\u000f\u0005\u0002\"[\u0012)\u0001\u0007\u0002b\u0001I!)!\u0007\u0002a\u0001_B)!\u0003\u000e9tiB!!#\u001d2e\u0013\t\u00118C\u0001\u0004UkBdWM\r\t\u0005%E<\u0017\u000eE\u0002\u0013oADQA\u000f\u0003A\u0002mBQ\u0001\u0012\u0003A\u0002mBQA\u0012\u0003A\u0002\u001dCQa\u0013\u0003A\u00021CQ\u0001\u0015\u0003A\u0002}CC\u0001\u0002*Y3\u0002")
public final class RetryFlow {
    @ApiMayChange(issue="https://github.com/akka/akka/issues/27960")
    public static <In, CtxIn, Out, CtxOut, Mat> FlowWithContext<In, CtxIn, Out, CtxOut, Mat> withBackoffAndContext(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, int maxRetries, FlowWithContext<In, CtxIn, Out, CtxOut, Mat> flow, Function2<Tuple2<In, CtxIn>, Tuple2<Out, CtxOut>, Option<Tuple2<In, CtxIn>>> decideRetry) {
        return RetryFlow$.MODULE$.withBackoffAndContext(minBackoff, maxBackoff, randomFactor, maxRetries, flow, decideRetry);
    }

    @ApiMayChange(issue="https://github.com/akka/akka/issues/27960")
    public static <In, Out, Mat> Flow<In, Out, Mat> withBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, int maxRetries, Flow<In, Out, Mat> flow, Function2<In, Out, Option<In>> decideRetry) {
        return RetryFlow$.MODULE$.withBackoff(minBackoff, maxBackoff, randomFactor, maxRetries, flow, decideRetry);
    }
}

