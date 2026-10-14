/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.stream.TLSProtocol;
import akka.stream.javadsl.BidiFlow;
import akka.stream.javadsl.TLSPlacebo$;
import akka.util.ByteString;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u:QAB\u0004\t\u000291Q\u0001E\u0004\t\u0002EAQ\u0001G\u0001\u0005\u0002eAQAG\u0001\u0005\u0002mAqAO\u0001C\u0002\u0013%1\b\u0003\u0004=\u0003\u0001\u0006I\u0001H\u0001\u000b)2\u001b\u0006\u000b\\1dK\n|'B\u0001\u0005\n\u0003\u001dQ\u0017M^1eg2T!AC\u0006\u0002\rM$(/Z1n\u0015\u0005a\u0011\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002\u0010\u00035\tqA\u0001\u0006U\u0019N\u0003F.Y2fE>\u001c\"!\u0001\n\u0011\u0005M1R\"\u0001\u000b\u000b\u0003U\tQa]2bY\u0006L!a\u0006\u000b\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\ta\"A\u0006hKRLen\u001d;b]\u000e,G#\u0001\u000f\u0011\u000f=ir$L\u00174m%\u0011ad\u0002\u0002\t\u0005&$\u0017N\u00127poB\u0011\u0001E\u000b\b\u0003C!r!AI\u0014\u000f\u0005\r2S\"\u0001\u0013\u000b\u0005\u0015j\u0011A\u0002\u001fs_>$h(C\u0001\r\u0013\tQ1\"\u0003\u0002*\u0013\u0005YA\u000bT*Qe>$xnY8m\u0013\tYCF\u0001\bTg2$Fn](vi\n|WO\u001c3\u000b\u0005%J\u0001C\u0001\u00182\u001b\u0005y#B\u0001\u0019\f\u0003\u0011)H/\u001b7\n\u0005Iz#A\u0003\"zi\u0016\u001cFO]5oOB\u0011\u0001\u0005N\u0005\u0003k1\u0012AbU3tg&|gNQ=uKN\u0004\"a\u000e\u001d\u000e\u0003-I!!O\u0006\u0003\u000f9{G/V:fI\u00069am\u001c:KCZ\fW#\u0001\u000f\u0002\u0011\u0019|'OS1wC\u0002\u0002")
public final class TLSPlacebo {
    public static BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SessionBytes, NotUsed> getInstance() {
        return TLSPlacebo$.MODULE$.getInstance();
    }
}

