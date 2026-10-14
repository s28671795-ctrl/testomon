/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.Attributes$;
import akka.stream.Client$;
import akka.stream.ConnectionException;
import akka.stream.IgnoreComplete$;
import akka.stream.TLSClosing;
import akka.stream.TLSProtocol;
import akka.stream.TLSRole;
import akka.stream.impl.io.TlsModule$;
import akka.stream.impl.io.TlsUtils$;
import akka.stream.scaladsl.BidiFlow;
import akka.stream.scaladsl.BidiFlow$;
import akka.util.ByteString;
import com.typesafe.sslconfig.akka.AkkaSSLConfig;
import com.typesafe.sslconfig.akka.AkkaSSLConfig$;
import java.io.Serializable;
import java.lang.invoke.LambdaMetafactory;
import java.util.Collections;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.runtime.BoxedUnit;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;

public final class TLS$ {
    public static final TLS$ MODULE$ = new TLS$();

    public BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> apply(SSLContext sslContext, Option<AkkaSSLConfig> sslConfig, TLSProtocol.NegotiateNewSession firstSession, TLSRole role, TLSClosing closing2, Option<Tuple2<String, Object>> hostInfo) {
        Function1<ActorSystem, SSLEngine> & Serializable createSSLEngine = arg_0 -> TLS$.$anonfun$apply$2(hostInfo, sslContext, role, firstSession, sslConfig, arg_0);
        return BidiFlow$.MODULE$.fromGraph(TlsModule$.MODULE$.apply(Attributes$.MODULE$.none(), createSSLEngine, TLS$.verifySession$1(hostInfo, sslConfig), closing2));
    }

    public BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> apply(SSLContext sslContext, TLSProtocol.NegotiateNewSession firstSession, TLSRole role, TLSClosing closing2, Option<Tuple2<String, Object>> hostInfo) {
        return this.apply(sslContext, None$.MODULE$, firstSession, role, closing2, hostInfo);
    }

    public BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> apply(SSLContext sslContext, TLSProtocol.NegotiateNewSession firstSession, TLSRole role) {
        return this.apply(sslContext, None$.MODULE$, firstSession, role, IgnoreComplete$.MODULE$, None$.MODULE$);
    }

    public BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> apply(Function0<SSLEngine> createSSLEngine, Function1<SSLSession, Try<BoxedUnit>> verifySession, TLSClosing closing2) {
        return BidiFlow$.MODULE$.fromGraph(TlsModule$.MODULE$.apply(Attributes$.MODULE$.none(), (Function1<ActorSystem, SSLEngine> & Serializable)x$3 -> (SSLEngine)createSSLEngine.apply(), (Function2<ActorSystem, SSLSession, Try<BoxedUnit>>)(Function2<ActorSystem, SSLSession, Try> & Serializable)(x$4, session) -> (Try)verifySession.apply((SSLSession)session), closing2));
    }

    public BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> apply(Function0<SSLEngine> createSSLEngine, TLSClosing closing2) {
        return this.apply(createSSLEngine, (Function1<SSLSession, Try<BoxedUnit>>)(Function1<SSLSession, Success> & Serializable)x$5 -> new Success<BoxedUnit>(BoxedUnit.UNIT), closing2);
    }

    public TLSClosing apply$default$5() {
        return IgnoreComplete$.MODULE$;
    }

    public Option<Tuple2<String, Object>> apply$default$6() {
        return None$.MODULE$;
    }

    private static final AkkaSSLConfig theSslConfig$1(ActorSystem system, Option sslConfig$1) {
        return (AkkaSSLConfig)sslConfig$1.getOrElse((Function0<AkkaSSLConfig> & Serializable)() -> (AkkaSSLConfig)AkkaSSLConfig$.MODULE$.apply(system));
    }

    public static final /* synthetic */ SSLParameters $anonfun$apply$3(SSLParameters old) {
        return TlsUtils$.MODULE$.cloneParameters(old);
    }

    public static final /* synthetic */ SSLParameters $anonfun$apply$4() {
        return new SSLParameters();
    }

    /*
     * Unable to fully structure code
     */
    public static final /* synthetic */ SSLEngine $anonfun$apply$2(Option hostInfo$1, SSLContext sslContext$1, TLSRole role$1, TLSProtocol.NegotiateNewSession firstSession$1, Option sslConfig$1, ActorSystem system) {
        config = TLS$.theSslConfig$1(system, sslConfig$1);
        var9_7 = hostInfo$1;
        if (!(var9_7 instanceof Some) || (var11_9 = (Tuple2)(var10_8 = (Some)var9_7).value()) == null) ** GOTO lbl-1000
        hostname = (String)var11_9._1();
        port = var11_9._2$mcI$sp();
        if (!config.config().loose().disableSNI()) {
            v0 = sslContext$1.createSSLEngine(hostname, port);
        } else lbl-1000:
        // 2 sources

        {
            v0 = sslContext$1.createSSLEngine();
        }
        engine = v0;
        config.sslEngineConfigurator().configure(engine, sslContext$1);
        v1 = role$1;
        var14_13 = Client$.MODULE$;
        engine.setUseClientMode(!(v1 != null ? v1.equals(var14_13) == false : var14_13 != null));
        if (firstSession$1.sslParameters().isDefined() && hostInfo$1.isDefined() && !config.config().loose().disableSNI()) {
            newParams = TlsUtils$.MODULE$.cloneParameters(firstSession$1.sslParameters().get());
            newParams.setServerNames(Collections.singletonList(new SNIHostName((String)((Tuple2)hostInfo$1.get())._1())));
            x$1 = new Some<SSLParameters>(newParams);
            x$2 = firstSession$1.copy$default$1();
            x$3 = firstSession$1.copy$default$2();
            x$4 = firstSession$1.copy$default$3();
            v2 = firstSession$1.copy(x$2, x$3, x$4, x$1);
        } else {
            v2 = paramsWithSni = firstSession$1;
        }
        if (hostInfo$1.isDefined() && config.useJvmHostnameVerification()) {
            newParams = (SSLParameters)paramsWithSni.sslParameters().map((Function1<SSLParameters, SSLParameters> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, (Ljava/lang/Object;)Ljava/lang/Object;, $anonfun$apply$3(javax.net.ssl.SSLParameters ), (Ljavax/net/ssl/SSLParameters;)Ljavax/net/ssl/SSLParameters;)()).getOrElse((Function0<SSLParameters> & Serializable)LambdaMetafactory.altMetafactory(null, null, null, ()Ljava/lang/Object;, $anonfun$apply$4(), ()Ljavax/net/ssl/SSLParameters;)());
            newParams.setEndpointIdentificationAlgorithm("HTTPS");
            x$5 = new Some<SSLParameters>(newParams);
            x$6 = paramsWithSni.copy$default$1();
            x$7 = paramsWithSni.copy$default$2();
            x$8 = paramsWithSni.copy$default$3();
            v3 = paramsWithSni.copy(x$6, x$7, x$8, x$5);
        } else {
            v3 = paramsWithSni;
        }
        paramsWithHostnameVerification = v3;
        TlsUtils$.MODULE$.applySessionParameters(engine, paramsWithHostnameVerification);
        return engine;
    }

    private static final Function2 verifySession$1(Option hostInfo$1, Option sslConfig$1) {
        Some some;
        Tuple2 tuple2;
        Option option = hostInfo$1;
        if (option instanceof Some && (tuple2 = (Tuple2)(some = (Some)option).value()) != null) {
            String hostname = (String)tuple2._1();
            return (Function2<ActorSystem, SSLSession, Try> & Serializable)(system, session) -> {
                AkkaSSLConfig config = TLS$.theSslConfig$1(system, sslConfig$1);
                if (config.useJvmHostnameVerification() || config.hostnameVerifier().verify(hostname, (SSLSession)session)) {
                    return new Success<BoxedUnit>(BoxedUnit.UNIT);
                }
                return new Failure(new ConnectionException(new StringBuilder(57).append("Hostname verification failed! Expected session to be for ").append(hostname).toString()));
            };
        }
        if (None$.MODULE$.equals(option)) {
            return (Function2<ActorSystem, SSLSession, Success> & Serializable)(x$1, x$2) -> new Success<BoxedUnit>(BoxedUnit.UNIT);
        }
        throw new MatchError(option);
    }

    private TLS$() {
    }
}

