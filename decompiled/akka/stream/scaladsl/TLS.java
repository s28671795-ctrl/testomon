/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.stream.TLSClosing;
import akka.stream.TLSProtocol;
import akka.stream.TLSRole;
import akka.stream.scaladsl.BidiFlow;
import akka.stream.scaladsl.TLS$;
import akka.util.ByteString;
import com.typesafe.sslconfig.akka.AkkaSSLConfig;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Tuple2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\u0005mt!\u0002\u0006\f\u0011\u0003\u0011b!\u0002\u000b\f\u0011\u0003)\u0002\"\u0002\u000f\u0002\t\u0003i\u0002\"\u0002\u0010\u0002\t\u0003y\u0002\"CA\u0006\u0003E\u0005I\u0011AA\u0007\u0011%\t\u0019#AI\u0001\n\u0003\t)\u0003\u0003\u0004\u001f\u0003\u0011\u0005\u0011\u0011\u0006\u0005\u0007=\u0005!\t!a\u000e\t\ry\tA\u0011AA!\u0011\u0019q\u0012\u0001\"\u0001\u0002v\u0005\u0019A\u000bT*\u000b\u00051i\u0011\u0001C:dC2\fGm\u001d7\u000b\u00059y\u0011AB:ue\u0016\fWNC\u0001\u0011\u0003\u0011\t7n[1\u0004\u0001A\u00111#A\u0007\u0002\u0017\t\u0019A\u000bT*\u0014\u0005\u00051\u0002CA\f\u001b\u001b\u0005A\"\"A\r\u0002\u000bM\u001c\u0017\r\\1\n\u0005mA\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002%\u0005)\u0011\r\u001d9msR9\u0001E\u0010&[?\u0016T\u0007cB\n\"GE\ntGO\u0005\u0003E-\u0011\u0001BQ5eS\u001acwn\u001e\t\u0003I9r!!\n\u0017\u000f\u0005\u0019ZcBA\u0014+\u001b\u0005A#BA\u0015\u0012\u0003\u0019a$o\\8u}%\t\u0001#\u0003\u0002\u000f\u001f%\u0011Q&D\u0001\f)2\u001b\u0006K]8u_\u000e|G.\u0003\u00020a\tq1k\u001d7UYN|U\u000f\u001e2pk:$'BA\u0017\u000e!\t\u0011T'D\u00014\u0015\t!t\"\u0001\u0003vi&d\u0017B\u0001\u001c4\u0005)\u0011\u0015\u0010^3TiJLgn\u001a\t\u0003IaJ!!\u000f\u0019\u0003\u001bM\u001bH\u000e\u00167t\u0013:\u0014w.\u001e8e!\tYD(D\u0001\u0010\u0013\titBA\u0004O_R,6/\u001a3\t\u000b}\u001a\u0001\u0019\u0001!\u0002\u0015M\u001cHnQ8oi\u0016DH\u000f\u0005\u0002B\u00116\t!I\u0003\u0002D\t\u0006\u00191o\u001d7\u000b\u0005\u00153\u0015a\u00018fi*\tq)A\u0003kCZ\f\u00070\u0003\u0002J\u0005\nQ1k\u0015'D_:$X\r\u001f;\t\u000b-\u001b\u0001\u0019\u0001'\u0002\u0013M\u001cHnQ8oM&<\u0007cA\fN\u001f&\u0011a\n\u0007\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0005ACV\"A)\u000b\u0005A\u0011&BA*U\u0003%\u00198\u000f\\2p]\u001aLwM\u0003\u0002V-\u0006AA/\u001f9fg\u00064WMC\u0001X\u0003\r\u0019w.\\\u0005\u00033F\u0013Q\"Q6lCN\u001bFjQ8oM&<\u0007\"B.\u0004\u0001\u0004a\u0016\u0001\u00044jeN$8+Z:tS>t\u0007C\u0001\u0013^\u0013\tq\u0006GA\nOK\u001e|G/[1uK:+woU3tg&|g\u000eC\u0003a\u0007\u0001\u0007\u0011-\u0001\u0003s_2,\u0007C\u00012d\u001b\u0005i\u0011B\u00013\u000e\u0005\u001d!Fj\u0015*pY\u0016DqAZ\u0002\u0011\u0002\u0003\u0007q-A\u0004dY>\u001c\u0018N\\4\u0011\u0005\tD\u0017BA5\u000e\u0005)!FjU\"m_NLgn\u001a\u0005\bW\u000e\u0001\n\u00111\u0001m\u0003!Awn\u001d;J]\u001a|\u0007cA\fN[B!qC\u001c9y\u0013\ty\u0007D\u0001\u0004UkBdWM\r\t\u0003cVt!A]:\u0011\u0005\u001dB\u0012B\u0001;\u0019\u0003\u0019\u0001&/\u001a3fM&\u0011ao\u001e\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005QD\u0002CA\fz\u0013\tQ\bDA\u0002J]RD\u0013b\u0001?\u0000\u0003\u0003\t)!a\u0002\u0011\u0005]i\u0018B\u0001@\u0019\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\t\t\u0019!A/Vg\u0016\u0004\u0013\r\u001d9ms\u0002\"\b.\u0019;!i\u0006\\Wm\u001d\u0011bAM\u001bF*\u00128hS:,\u0007EZ1di>\u0014\u0018\u0010I5ogR,\u0017\r\u001a\u0018!'\u0016$X\u000f\u001d\u0011uQ\u0016\u00043k\u0015'F]\u001eLg.\u001a\u0011xSRD\u0007E\\3fI\u0016$\u0007\u0005]1sC6,G/\u001a:t]\u0005)1/\u001b8dK\u0006\u0012\u0011\u0011B\u0001\u0006e92d\u0006M\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%kU\u0011\u0011q\u0002\u0016\u0004O\u0006E1FAA\n!\u0011\t)\"a\b\u000e\u0005\u0005]!\u0002BA\r\u00037\t\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\u0005u\u0001$\u0001\u0006b]:|G/\u0019;j_:LA!!\t\u0002\u0018\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIY*\"!a\n+\u00071\f\t\u0002F\u0006!\u0003W\ti#a\f\u00022\u0005M\u0002\"B \u0007\u0001\u0004\u0001\u0005\"B.\u0007\u0001\u0004a\u0006\"\u00021\u0007\u0001\u0004\t\u0007\"\u00024\u0007\u0001\u00049\u0007\"B6\u0007\u0001\u0004a\u0007&\u0003\u0004}\u007f\u0006\u0005\u0011QAA\u0004)\u001d\u0001\u0013\u0011HA\u001e\u0003{AQaP\u0004A\u0002\u0001CQaW\u0004A\u0002qCQ\u0001Y\u0004A\u0002\u0005D\u0013b\u0002?\u0000\u0003\u0003\t)!a\u0002\u0015\u000f\u0001\n\u0019%a\u0015\u0002t!9\u0011Q\t\u0005A\u0002\u0005\u001d\u0013aD2sK\u0006$XmU*M\u000b:<\u0017N\\3\u0011\u000b]\tI%!\u0014\n\u0007\u0005-\u0003DA\u0005Gk:\u001cG/[8oaA\u0019\u0011)a\u0014\n\u0007\u0005E#IA\u0005T'2+enZ5oK\"9\u0011Q\u000b\u0005A\u0002\u0005]\u0013!\u0004<fe&4\u0017pU3tg&|g\u000eE\u0004\u0018\u00033\ni&a\u0019\n\u0007\u0005m\u0003DA\u0005Gk:\u001cG/[8ocA\u0019\u0011)a\u0018\n\u0007\u0005\u0005$I\u0001\u0006T'2\u001bVm]:j_:\u0004b!!\u001a\u0002j\u00055TBAA4\u0015\t!\u0004$\u0003\u0003\u0002l\u0005\u001d$a\u0001+ssB\u0019q#a\u001c\n\u0007\u0005E\u0004D\u0001\u0003V]&$\b\"\u00024\t\u0001\u00049G#\u0002\u0011\u0002x\u0005e\u0004bBA#\u0013\u0001\u0007\u0011q\t\u0005\u0006M&\u0001\ra\u001a")
public final class TLS {
    public static BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> apply(Function0<SSLEngine> createSSLEngine, TLSClosing closing2) {
        return TLS$.MODULE$.apply(createSSLEngine, closing2);
    }

    public static BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> apply(Function0<SSLEngine> createSSLEngine, Function1<SSLSession, Try<BoxedUnit>> verifySession, TLSClosing closing2) {
        return TLS$.MODULE$.apply(createSSLEngine, verifySession, closing2);
    }

    public static BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> apply(SSLContext sslContext, TLSProtocol.NegotiateNewSession firstSession, TLSRole role) {
        return TLS$.MODULE$.apply(sslContext, firstSession, role);
    }

    public static BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> apply(SSLContext sslContext, TLSProtocol.NegotiateNewSession firstSession, TLSRole role, TLSClosing closing2, Option<Tuple2<String, Object>> hostInfo) {
        return TLS$.MODULE$.apply(sslContext, firstSession, role, closing2, hostInfo);
    }

    public static Option<Tuple2<String, Object>> apply$default$6() {
        return TLS$.MODULE$.apply$default$6();
    }

    public static TLSClosing apply$default$5() {
        return TLS$.MODULE$.apply$default$5();
    }

    public static BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> apply(SSLContext sslContext, Option<AkkaSSLConfig> sslConfig, TLSProtocol.NegotiateNewSession firstSession, TLSRole role, TLSClosing closing2, Option<Tuple2<String, Object>> hostInfo) {
        return TLS$.MODULE$.apply(sslContext, sslConfig, firstSession, role, closing2, hostInfo);
    }
}

