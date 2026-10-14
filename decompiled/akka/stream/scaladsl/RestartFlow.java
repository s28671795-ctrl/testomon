/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.RestartSettings;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.RestartFlow$;
import scala.Function0;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005-v!\u0002\u0005\n\u0011\u0003\u0001b!\u0002\n\n\u0011\u0003\u0019\u0002\"\u0002\u000e\u0002\t\u0003Y\u0002\"\u0002\u000f\u0002\t\u0003i\u0002\"\u0002\u000f\u0002\t\u0003I\u0007B\u0002\u000f\u0002\t\u0003\t\t\u0002C\u0004\u0002H\u0005!\t!!\u0013\t\u000f\u0005\u001d\u0013\u0001\"\u0001\u0002\u0000\u0005Y!+Z:uCJ$h\t\\8x\u0015\tQ1\"\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\taQ\"\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002\u001d\u0005!\u0011m[6b\u0007\u0001\u0001\"!E\u0001\u000e\u0003%\u00111BU3ti\u0006\u0014HO\u00127poN\u0011\u0011\u0001\u0006\t\u0003+ai\u0011A\u0006\u0006\u0002/\u0005)1oY1mC&\u0011\u0011D\u0006\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005\u0001\u0012aC<ji\"\u0014\u0015mY6pM\u001a,2AH\u00130)\u0011yRiT)\u0015\u0005\u0001*\u0004#B\t\"G9\n\u0014B\u0001\u0012\n\u0005\u00111En\\<\u0011\u0005\u0011*C\u0002\u0001\u0003\u0006M\r\u0011\ra\n\u0002\u0003\u0013:\f\"\u0001K\u0016\u0011\u0005UI\u0013B\u0001\u0016\u0017\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!\u0006\u0017\n\u000552\"aA!osB\u0011Ae\f\u0003\u0006a\r\u0011\ra\n\u0002\u0004\u001fV$\bC\u0001\u001a4\u001b\u0005i\u0011B\u0001\u001b\u000e\u0005\u001dqu\u000e^+tK\u0012DQAN\u0002A\u0002]\n1B\u001a7po\u001a\u000b7\r^8ssB\u0019Q\u0003\u000f\u001e\n\u0005e2\"!\u0003$v]\u000e$\u0018n\u001c81a\tYT\bE\u0003\u0012C\rrC\b\u0005\u0002%{\u0011IahPA\u0001\u0002\u0003\u0015\ta\n\u0002\u0004?\u0012\n\u0004\"\u0002\u001c\u0004\u0001\u0004\u0001\u0005cA\u000b9\u0003B\u0012!)\u0010\t\u0006#\u0005\u001aE\t\u0010\t\u0003I\u0015\u0002\"\u0001J\u0018\t\u000b\u0019\u001b\u0001\u0019A$\u0002\u00155LgNQ1dW>4g\r\u0005\u0002I\u001b6\t\u0011J\u0003\u0002K\u0017\u0006AA-\u001e:bi&|gN\u0003\u0002M-\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\u00059K%A\u0004$j]&$X\rR;sCRLwN\u001c\u0005\u0006!\u000e\u0001\raR\u0001\u000b[\u0006D()Y2l_\u001a4\u0007\"\u0002*\u0004\u0001\u0004\u0019\u0016\u0001\u0004:b]\u0012|WNR1di>\u0014\bCA\u000bU\u0013\t)fC\u0001\u0004E_V\u0014G.\u001a\u0015\u0007\u0007]S6,\u00180\u0011\u0005UA\u0016BA-\u0017\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\u0005a\u0016\u0001T+tK\u0002\"\b.\u001a\u0011pm\u0016\u0014Hn\\1eK\u0012\u0004S.\u001a;i_\u0012\u0004s\u000f[5dQ\u0002\n7mY3qiN\u0004\u0013m[6b]M$(/Z1n]I+7\u000f^1siN+G\u000f^5oON\u0004\u0013N\\:uK\u0006$g&A\u0003tS:\u001cW-I\u0001`\u0003\u0019\u0011dF\u000e\u00182a!\u00121!\u0019\t\u0003E\u001el\u0011a\u0019\u0006\u0003I\u0016\fA\u0001\\1oO*\ta-\u0001\u0003kCZ\f\u0017B\u00015d\u0005)!U\r\u001d:fG\u0006$X\rZ\u000b\u0004U:\u0004HcB6\u007f\u007f\u0006\u0005\u00111\u0001\u000b\u0003YF\u0004R!E\u0011n_F\u0002\"\u0001\n8\u0005\u000b\u0019\"!\u0019A\u0014\u0011\u0005\u0011\u0002H!\u0002\u0019\u0005\u0005\u00049\u0003\"\u0002\u001c\u0005\u0001\u0004\u0011\bcA\u000b9gB\u0012AO\u001e\t\u0006#\u0005jw.\u001e\t\u0003IY$\u0011b\u001e=\u0002\u0002\u0003\u0005)\u0011A\u0014\u0003\u0007}##\u0007C\u00037\t\u0001\u0007\u0011\u0010E\u0002\u0016qi\u0004$a\u001f<\u0011\u000bE\tC0`;\u0011\u0005\u0011r\u0007C\u0001\u0013q\u0011\u00151E\u00011\u0001H\u0011\u0015\u0001F\u00011\u0001H\u0011\u0015\u0011F\u00011\u0001T\u0011\u001d\t)\u0001\u0002a\u0001\u0003\u000f\t1\"\\1y%\u0016\u001cH/\u0019:ugB\u0019Q#!\u0003\n\u0007\u0005-aCA\u0002J]RDc\u0001B,[7vs\u0006F\u0001\u0003b+\u0019\t\u0019\"a\u0007\u0002 Q!\u0011QCA\u001e)\u0011\t9\"!\t\u0011\u000fE\t\u0013\u0011DA\u000fcA\u0019A%a\u0007\u0005\u000b\u0019*!\u0019A\u0014\u0011\u0007\u0011\ny\u0002B\u00031\u000b\t\u0007q\u0005\u0003\u00047\u000b\u0001\u0007\u00111\u0005\t\u0005+a\n)\u0003\r\u0003\u0002(\u0005-\u0002\u0003C\t\"\u00033\ti\"!\u000b\u0011\u0007\u0011\nY\u0003B\u0006\u0002.\u0005=\u0012\u0011!A\u0001\u0006\u00039#aA0%g!1a'\u0002a\u0001\u0003c\u0001B!\u0006\u001d\u00024A\"\u0011QGA\u0016!!\t\u0012%a\u000e\u0002:\u0005%\u0002c\u0001\u0013\u0002\u001cA\u0019A%a\b\t\u000f\u0005uR\u00011\u0001\u0002@\u0005A1/\u001a;uS:<7\u000f\u0005\u0003\u0002B\u0005\rS\"A\u0006\n\u0007\u0005\u00153BA\bSKN$\u0018M\u001d;TKR$\u0018N\\4t\u0003UygNR1jYV\u0014Xm],ji\"\u0014\u0015mY6pM\u001a,b!a\u0013\u0002T\u0005]CCCA'\u0003g\n)(a\u001e\u0002zQ!\u0011qJA-!\u001d\t\u0012%!\u0015\u0002VE\u00022\u0001JA*\t\u00151cA1\u0001(!\r!\u0013q\u000b\u0003\u0006a\u0019\u0011\ra\n\u0005\u0007m\u0019\u0001\r!a\u0017\u0011\tUA\u0014Q\f\u0019\u0005\u0003?\n\u0019\u0007\u0005\u0005\u0012C\u0005E\u0013QKA1!\r!\u00131\r\u0003\f\u0003K\n9'!A\u0001\u0002\u000b\u0005qEA\u0002`IQBaA\u000e\u0004A\u0002\u0005%\u0004\u0003B\u000b9\u0003W\u0002D!!\u001c\u0002dAA\u0011#IA8\u0003c\n\t\u0007E\u0002%\u0003'\u00022\u0001JA,\u0011\u00151e\u00011\u0001H\u0011\u0015\u0001f\u00011\u0001H\u0011\u0015\u0011f\u00011\u0001T\u0011\u001d\t)A\u0002a\u0001\u0003\u000fAcAB,[7vs\u0006F\u0001\u0004b+\u0019\t\t)!#\u0002\u000eR!\u00111QAU)\u0011\t))a$\u0011\u000fE\t\u0013qQAFcA\u0019A%!#\u0005\u000b\u0019:!\u0019A\u0014\u0011\u0007\u0011\ni\tB\u00031\u000f\t\u0007q\u0005\u0003\u00047\u000f\u0001\u0007\u0011\u0011\u0013\t\u0005+a\n\u0019\n\r\u0003\u0002\u0016\u0006e\u0005\u0003C\t\"\u0003\u000f\u000bY)a&\u0011\u0007\u0011\nI\nB\u0006\u0002\u001c\u0006u\u0015\u0011!A\u0001\u0006\u00039#aA0%k!1ag\u0002a\u0001\u0003?\u0003B!\u0006\u001d\u0002\"B\"\u00111UAM!!\t\u0012%!*\u0002(\u0006]\u0005c\u0001\u0013\u0002\nB\u0019A%!$\t\u000f\u0005ur\u00011\u0001\u0002@\u0001")
public final class RestartFlow {
    public static <In, Out> Flow<In, Out, NotUsed> onFailuresWithBackoff(RestartSettings settings, Function0<Flow<In, Out, ?>> flowFactory) {
        return RestartFlow$.MODULE$.onFailuresWithBackoff(settings, flowFactory);
    }

    @Deprecated
    public static <In, Out> Flow<In, Out, NotUsed> onFailuresWithBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, int maxRestarts, Function0<Flow<In, Out, ?>> flowFactory) {
        return RestartFlow$.MODULE$.onFailuresWithBackoff(minBackoff, maxBackoff, randomFactor, maxRestarts, flowFactory);
    }

    public static <In, Out> Flow<In, Out, NotUsed> withBackoff(RestartSettings settings, Function0<Flow<In, Out, ?>> flowFactory) {
        return RestartFlow$.MODULE$.withBackoff(settings, flowFactory);
    }

    @Deprecated
    public static <In, Out> Flow<In, Out, NotUsed> withBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, int maxRestarts, Function0<Flow<In, Out, ?>> flowFactory) {
        return RestartFlow$.MODULE$.withBackoff(minBackoff, maxBackoff, randomFactor, maxRestarts, flowFactory);
    }

    @Deprecated
    public static <In, Out> Flow<In, Out, NotUsed> withBackoff(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, Function0<Flow<In, Out, ?>> flowFactory) {
        return RestartFlow$.MODULE$.withBackoff(minBackoff, maxBackoff, randomFactor, flowFactory);
    }
}

