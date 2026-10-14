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
import akka.util.ByteString;
import com.typesafe.sslconfig.akka.AkkaSSLConfig;
import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import scala.Function0;
import scala.Function1;
import scala.None$;
import scala.Predef$;
import scala.Tuple2;
import scala.compat.java8.OptionConverters$;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.util.Try;
import scala.util.Try$;

public final class TLS$ {
    public static final TLS$ MODULE$ = new TLS$();

    public BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> create(SSLContext sslContext, Optional<AkkaSSLConfig> sslConfig, TLSProtocol.NegotiateNewSession firstSession, TLSRole role) {
        return new BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed>(akka.stream.scaladsl.TLS$.MODULE$.apply(sslContext, OptionConverters$.MODULE$.toScala(sslConfig), firstSession, role, akka.stream.scaladsl.TLS$.MODULE$.apply$default$5(), akka.stream.scaladsl.TLS$.MODULE$.apply$default$6()));
    }

    public BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> create(SSLContext sslContext, TLSProtocol.NegotiateNewSession firstSession, TLSRole role) {
        return new BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed>(akka.stream.scaladsl.TLS$.MODULE$.apply(sslContext, None$.MODULE$, firstSession, role, akka.stream.scaladsl.TLS$.MODULE$.apply$default$5(), akka.stream.scaladsl.TLS$.MODULE$.apply$default$6()));
    }

    public BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> create(SSLContext sslContext, Optional<AkkaSSLConfig> sslConfig, TLSProtocol.NegotiateNewSession firstSession, TLSRole role, Optional<Pair<String, Integer>> hostInfo, TLSClosing closing2) {
        return new BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed>(akka.stream.scaladsl.TLS$.MODULE$.apply(sslContext, OptionConverters$.MODULE$.toScala(sslConfig), firstSession, role, closing2, OptionConverters$.MODULE$.toScala(hostInfo).map((Function1<Pair, Tuple2> & Serializable)e -> new Tuple2(e.first(), BoxesRunTime.boxToInteger(Predef$.MODULE$.Integer2int((Integer)e.second()))))));
    }

    public BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> create(SSLContext sslContext, TLSProtocol.NegotiateNewSession firstSession, TLSRole role, Optional<Pair<String, Integer>> hostInfo, TLSClosing closing2) {
        return new BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed>(akka.stream.scaladsl.TLS$.MODULE$.apply(sslContext, None$.MODULE$, firstSession, role, closing2, OptionConverters$.MODULE$.toScala(hostInfo).map((Function1<Pair, Tuple2> & Serializable)e -> new Tuple2(e.first(), BoxesRunTime.boxToInteger(Predef$.MODULE$.Integer2int((Integer)e.second()))))));
    }

    public BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> create(Supplier<SSLEngine> sslEngineCreator, Consumer<SSLSession> sessionVerifier, TLSClosing closing2) {
        return new BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed>(akka.stream.scaladsl.TLS$.MODULE$.apply((Function0<SSLEngine> & Serializable)() -> (SSLEngine)sslEngineCreator.get(), (Function1<SSLSession, Try<BoxedUnit>>)(Function1<SSLSession, Try> & Serializable)session -> Try$.MODULE$.apply(() -> sessionVerifier.accept((SSLSession)session)), closing2));
    }

    public BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> create(Supplier<SSLEngine> sslEngineCreator, TLSClosing closing2) {
        return new BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed>(akka.stream.scaladsl.TLS$.MODULE$.apply((Function0<SSLEngine> & Serializable)() -> (SSLEngine)sslEngineCreator.get(), closing2));
    }

    private TLS$() {
    }
}

