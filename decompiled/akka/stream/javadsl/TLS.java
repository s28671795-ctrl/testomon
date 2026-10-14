/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.Pair;
import akka.stream.TLSClosing;
import akka.stream.TLSProtocol;
import akka.stream.TLSRole;
import akka.stream.javadsl.BidiFlow;
import akka.stream.javadsl.TLS$;
import akka.util.ByteString;
import com.typesafe.sslconfig.akka.AkkaSSLConfig;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Et!B\u0005\u000b\u0011\u0003\tb!B\n\u000b\u0011\u0003!\u0002\"B\u000e\u0002\t\u0003a\u0002\"B\u000f\u0002\t\u0003q\u0002\"B\u000f\u0002\t\u0003\u0011\b\"B\u000f\u0002\t\u00039\bBB\u000f\u0002\t\u0003\t\u0019\u0004\u0003\u0004\u001e\u0003\u0011\u0005\u0011\u0011\t\u0005\u0007;\u0005!\t!a\u001b\u0002\u0007Qc5K\u0003\u0002\f\u0019\u00059!.\u0019<bINd'BA\u0007\u000f\u0003\u0019\u0019HO]3b[*\tq\"\u0001\u0003bW.\f7\u0001\u0001\t\u0003%\u0005i\u0011A\u0003\u0002\u0004)2\u001b6CA\u0001\u0016!\t1\u0012$D\u0001\u0018\u0015\u0005A\u0012!B:dC2\f\u0017B\u0001\u000e\u0018\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012!E\u0001\u0007GJ,\u0017\r^3\u0015\u000b}i\u0014*\u00182\u0011\u000fI\u0001#\u0005\r\u00197s%\u0011\u0011E\u0003\u0002\t\u0005&$\u0017N\u00127poB\u00111%\f\b\u0003I-r!!\n\u0016\u000f\u0005\u0019JS\"A\u0014\u000b\u0005!\u0002\u0012A\u0002\u001fs_>$h(C\u0001\u0010\u0013\tia\"\u0003\u0002-\u0019\u0005YA\u000bT*Qe>$xnY8m\u0013\tqsF\u0001\bTg2$Fn](vi\n|WO\u001c3\u000b\u00051b\u0001CA\u00195\u001b\u0005\u0011$BA\u001a\u000f\u0003\u0011)H/\u001b7\n\u0005U\u0012$A\u0003\"zi\u0016\u001cFO]5oOB\u00111eN\u0005\u0003q=\u0012QbU:m)2\u001c\u0018J\u001c2pk:$\u0007C\u0001\u001e<\u001b\u0005q\u0011B\u0001\u001f\u000f\u0005\u001dqu\u000e^+tK\u0012DQAP\u0002A\u0002}\n!b]:m\u0007>tG/\u001a=u!\t\u0001u)D\u0001B\u0015\t\u00115)A\u0002tg2T!\u0001R#\u0002\u00079,GOC\u0001G\u0003\u0015Q\u0017M^1y\u0013\tA\u0015I\u0001\u0006T'2\u001buN\u001c;fqRDQAS\u0002A\u0002-\u000b\u0011b]:m\u0007>tg-[4\u0011\u00071\u0003&+D\u0001N\u0015\t\u0019dJC\u0001P\u0003\u0011Q\u0017M^1\n\u0005Ek%\u0001C(qi&|g.\u00197\u0011\u0005M[V\"\u0001+\u000b\u0005=)&B\u0001,X\u0003%\u00198\u000f\\2p]\u001aLwM\u0003\u0002Y3\u0006AA/\u001f9fg\u00064WMC\u0001[\u0003\r\u0019w.\\\u0005\u00039R\u0013Q\"Q6lCN\u001bFjQ8oM&<\u0007\"\u00020\u0004\u0001\u0004y\u0016\u0001\u00044jeN$8+Z:tS>t\u0007CA\u0012a\u0013\t\twFA\nOK\u001e|G/[1uK:+woU3tg&|g\u000eC\u0003d\u0007\u0001\u0007A-\u0001\u0003s_2,\u0007CA3g\u001b\u0005a\u0011BA4\r\u0005\u001d!Fj\u0015*pY\u0016DcaA5m[>\u0004\bC\u0001\fk\u0013\tYwC\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-I\u0001o\u0003y+6/\u001a\u0011de\u0016\fG/\u001a\u0011uQ\u0006$\b\u0005^1lKN\u0004\u0013\rI*T\u0019\u0016sw-\u001b8fA\u0019\f7\r^8ss\u0002Jgn\u001d;fC\u0012t\u0003eU3ukB\u0004C\u000f[3!'NcUI\\4j]\u0016\u0004s/\u001b;iA9,W\rZ3eAA\f'/Y7fi\u0016\u00148OL\u0001\u0006g&t7-Z\u0011\u0002c\u0006)!G\f\u001c/aQ!qd\u001d;v\u0011\u0015qD\u00011\u0001@\u0011\u0015qF\u00011\u0001`\u0011\u0015\u0019G\u00011\u0001eQ\u0019!\u0011\u000e\\7paRAq\u0004_={wr\f9\u0003C\u0003?\u000b\u0001\u0007q\bC\u0003K\u000b\u0001\u00071\nC\u0003_\u000b\u0001\u0007q\fC\u0003d\u000b\u0001\u0007A\rC\u0003~\u000b\u0001\u0007a0\u0001\u0005i_N$\u0018J\u001c4p!\ra\u0005k \t\t\u0003\u0003\t9!a\u0003\u0002\u001c5\u0011\u00111\u0001\u0006\u0004\u0003\u000bq\u0011\u0001\u00026ba&LA!!\u0003\u0002\u0004\t!\u0001+Y5s!\u0011\ti!!\u0006\u000f\t\u0005=\u0011\u0011\u0003\t\u0003M]I1!a\u0005\u0018\u0003\u0019\u0001&/\u001a3fM&!\u0011qCA\r\u0005\u0019\u0019FO]5oO*\u0019\u00111C\f\u0011\t\u0005u\u00111E\u0007\u0003\u0003?Q1!!\tO\u0003\u0011a\u0017M\\4\n\t\u0005\u0015\u0012q\u0004\u0002\b\u0013:$XmZ3s\u0011\u001d\tI#\u0002a\u0001\u0003W\tqa\u00197pg&tw\rE\u0002f\u0003[I1!a\f\r\u0005)!FjU\"m_NLgn\u001a\u0015\u0007\u000b%dWn\u001c9\u0015\u0017}\t)$a\u000e\u0002:\u0005m\u0012Q\b\u0005\u0006}\u0019\u0001\ra\u0010\u0005\u0006=\u001a\u0001\ra\u0018\u0005\u0006G\u001a\u0001\r\u0001\u001a\u0005\u0006{\u001a\u0001\rA \u0005\b\u0003S1\u0001\u0019AA\u0016Q\u00191\u0011\u000e\\7paR9q$a\u0011\u0002Z\u0005%\u0004bBA#\u000f\u0001\u0007\u0011qI\u0001\u0011gNdWI\\4j]\u0016\u001c%/Z1u_J\u0004b!!\u0013\u0002P\u0005MSBAA&\u0015\r\ti%T\u0001\tMVt7\r^5p]&!\u0011\u0011KA&\u0005!\u0019V\u000f\u001d9mS\u0016\u0014\bc\u0001!\u0002V%\u0019\u0011qK!\u0003\u0013M\u001bF*\u00128hS:,\u0007bBA.\u000f\u0001\u0007\u0011QL\u0001\u0010g\u0016\u001c8/[8o-\u0016\u0014\u0018NZ5feB1\u0011\u0011JA0\u0003GJA!!\u0019\u0002L\tA1i\u001c8tk6,'\u000fE\u0002A\u0003KJ1!a\u001aB\u0005)\u00196\u000bT*fgNLwN\u001c\u0005\b\u0003S9\u0001\u0019AA\u0016)\u0015y\u0012QNA8\u0011\u001d\t)\u0005\u0003a\u0001\u0003\u000fBq!!\u000b\t\u0001\u0004\tY\u0003")
public final class TLS {
    public static BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> create(Supplier<SSLEngine> sslEngineCreator, TLSClosing closing2) {
        return TLS$.MODULE$.create(sslEngineCreator, closing2);
    }

    public static BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> create(Supplier<SSLEngine> sslEngineCreator, Consumer<SSLSession> sessionVerifier, TLSClosing closing2) {
        return TLS$.MODULE$.create(sslEngineCreator, sessionVerifier, closing2);
    }

    public static BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> create(SSLContext sslContext, TLSProtocol.NegotiateNewSession firstSession, TLSRole role, Optional<Pair<String, Integer>> hostInfo, TLSClosing closing2) {
        return TLS$.MODULE$.create(sslContext, firstSession, role, hostInfo, closing2);
    }

    public static BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> create(SSLContext sslContext, Optional<AkkaSSLConfig> sslConfig, TLSProtocol.NegotiateNewSession firstSession, TLSRole role, Optional<Pair<String, Integer>> hostInfo, TLSClosing closing2) {
        return TLS$.MODULE$.create(sslContext, sslConfig, firstSession, role, hostInfo, closing2);
    }

    public static BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> create(SSLContext sslContext, TLSProtocol.NegotiateNewSession firstSession, TLSRole role) {
        return TLS$.MODULE$.create(sslContext, firstSession, role);
    }

    public static BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> create(SSLContext sslContext, Optional<AkkaSSLConfig> sslConfig, TLSProtocol.NegotiateNewSession firstSession, TLSRole role) {
        return TLS$.MODULE$.create(sslContext, sslConfig, firstSession, role);
    }
}

