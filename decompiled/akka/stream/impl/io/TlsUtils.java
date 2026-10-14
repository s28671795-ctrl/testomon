/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.annotation.InternalApi;
import akka.stream.TLSProtocol;
import akka.stream.impl.io.TlsUtils$;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-;a!\u0002\u0004\t\u00021qaA\u0002\t\u0007\u0011\u0003a\u0011\u0003C\u0003\u0019\u0003\u0011\u0005!\u0004C\u0003\u001c\u0003\u0011\u0005A\u0004C\u0003=\u0003\u0011\u0005Q(\u0001\u0005UYN,F/\u001b7t\u0015\t9\u0001\"\u0001\u0002j_*\u0011\u0011BC\u0001\u0005S6\u0004HN\u0003\u0002\f\u0019\u000511\u000f\u001e:fC6T\u0011!D\u0001\u0005C.\\\u0017\r\u0005\u0002\u0010\u00035\taA\u0001\u0005UYN,F/\u001b7t'\t\t!\u0003\u0005\u0002\u0014-5\tACC\u0001\u0016\u0003\u0015\u00198-\u00197b\u0013\t9BC\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\ta\"\u0001\fbaBd\u0017pU3tg&|g\u000eU1sC6,G/\u001a:t)\ri\u0002\u0005\f\t\u0003'yI!a\b\u000b\u0003\tUs\u0017\u000e\u001e\u0005\u0006C\r\u0001\rAI\u0001\u0007K:<\u0017N\\3\u0011\u0005\rRS\"\u0001\u0013\u000b\u0005\u00152\u0013aA:tY*\u0011q\u0005K\u0001\u0004]\u0016$(\"A\u0015\u0002\u000b)\fg/\u0019=\n\u0005-\"#!C*T\u0019\u0016sw-\u001b8f\u0011\u0015i3\u00011\u0001/\u0003E\u0019Xm]:j_:\u0004\u0016M]1nKR,'o\u001d\t\u0003_er!\u0001M\u001c\u000f\u0005E2dB\u0001\u001a6\u001b\u0005\u0019$B\u0001\u001b\u001a\u0003\u0019a$o\\8u}%\tQ\"\u0003\u0002\f\u0019%\u0011\u0001HC\u0001\f)2\u001b\u0006K]8u_\u000e|G.\u0003\u0002;w\t\u0019b*Z4pi&\fG/\u001a(foN+7o]5p]*\u0011\u0001HC\u0001\u0010G2|g.\u001a)be\u0006lW\r^3sgR\u0011a(\u0011\t\u0003G}J!\u0001\u0011\u0013\u0003\u001bM\u001bF\nU1sC6,G/\u001a:t\u0011\u0015\u0011E\u00011\u0001?\u0003\ryG\u000e\u001a\u0015\u0003\u0003\u0011\u0003\"!\u0012%\u000e\u0003\u0019S!a\u0012\u0007\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002J\r\nY\u0011J\u001c;fe:\fG.\u00119jQ\t\u0001A\t")
@InternalApi
public final class TlsUtils {
    public static SSLParameters cloneParameters(SSLParameters old) {
        return TlsUtils$.MODULE$.cloneParameters(old);
    }

    public static void applySessionParameters(SSLEngine engine, TLSProtocol.NegotiateNewSession sessionParameters) {
        TlsUtils$.MODULE$.applySessionParameters(engine, sessionParameters);
    }
}

