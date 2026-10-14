/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.akka;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.annotation.InternalApi;
import akka.event.LogSource$;
import akka.event.Logging$;
import akka.event.LoggingAdapter;
import com.typesafe.sslconfig.akka.AkkaSSLConfig$;
import com.typesafe.sslconfig.akka.DefaultSSLEngineConfigurator;
import com.typesafe.sslconfig.akka.util.AkkaLoggerFactory;
import com.typesafe.sslconfig.ssl.ConfigSSLContextBuilder;
import com.typesafe.sslconfig.ssl.DefaultHostnameVerifier;
import com.typesafe.sslconfig.ssl.DefaultKeyManagerFactoryWrapper;
import com.typesafe.sslconfig.ssl.DefaultTrustManagerFactoryWrapper;
import com.typesafe.sslconfig.ssl.DisabledComplainingHostnameVerifier;
import com.typesafe.sslconfig.ssl.KeyManagerFactoryWrapper;
import com.typesafe.sslconfig.ssl.NoopHostnameVerifier;
import com.typesafe.sslconfig.ssl.Protocols$;
import com.typesafe.sslconfig.ssl.SSLConfigSettings;
import com.typesafe.sslconfig.ssl.TrustManagerFactoryWrapper;
import com.typesafe.sslconfig.util.LoggerFactory;
import java.io.Serializable;
import java.util.Collections;
import java.util.function.Function;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Some;
import scala.collection.ArrayOps$;
import scala.collection.IterableOnceOps;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\u00055x!B\u0011#\u0011\u0003Yc!B\u0017#\u0011\u0003q\u0003B\u0002(\u0002\t\u0003\tI\fC\u0004\u0002<\u0006!\t%!0\t\u000f\u0005m\u0016\u0001\"\u0011\u0002H\"9\u0011\u0011[\u0001\u0005\u0002\u0005M\u0007bBAm\u0003\u0011\u0005\u00131\u001c\u0005\b\u0003;\fA\u0011IAp\u0011\u001d\t\u0019/\u0001C\u0001\u0003K4A!\f\u0012\u0003{!A\u0011)\u0003B\u0001B\u0003%!\t\u0003\u0005F\u0013\t\u0015\r\u0011\"\u0001G\u0011!i\u0015B!A!\u0002\u00139\u0005\"\u0002(\n\t\u0003y\u0005b\u0002*\n\u0005\u0004%Ia\u0015\u0005\u00075&\u0001\u000b\u0011\u0002+\t\u000fmK!\u0019!C\u00059\"11-\u0003Q\u0001\nuCQ\u0001Z\u0005\u0005\u0002\u0015DQ\u0001[\u0005\u0005\u0002%DQa\\\u0005\u0005\u0002ADqa_\u0005C\u0002\u0013\u0005A\u0010C\u0004\u0002\u000e%\u0001\u000b\u0011B?\t\u000f\u0005=\u0011\u0002\"\u0001\u0002\u0012!I\u0011qE\u0005C\u0002\u0013\u0005\u0011\u0011\u0006\u0005\t\u0003cI\u0001\u0015!\u0003\u0002,!9\u00111G\u0005\u0005\u0002\u0005U\u0002bBA \u0013\u0011\u0005\u0011\u0011\t\u0005\b\u0003\u0017JA\u0011AA'\u0011\u001d\t\u0019&\u0003C\u0001\u0003+Bq!!\u0019\n\t\u0003\t\u0019\u0007C\u0004\u0002\b&!\t!!#\t\u000f\u0005E\u0015\u0002\"\u0003\u0002\u0014\u0006i\u0011i[6b'Nc5i\u001c8gS\u001eT!a\t\u0013\u0002\t\u0005\\7.\u0019\u0006\u0003K\u0019\n\u0011b]:mG>tg-[4\u000b\u0005\u001dB\u0013\u0001\u0003;za\u0016\u001c\u0018MZ3\u000b\u0003%\n1aY8n\u0007\u0001\u0001\"\u0001L\u0001\u000e\u0003\t\u0012Q\"Q6lCN\u001bFjQ8oM&<7#B\u00010k\u0005M\u0006C\u0001\u00194\u001b\u0005\t$\"\u0001\u001a\u0002\u000bM\u001c\u0017\r\\1\n\u0005Q\n$AB!osJ+g\rE\u00027uqj\u0011a\u000e\u0006\u0003qe\nQ!Y2u_JT\u0011aI\u0005\u0003w]\u00121\"\u0012=uK:\u001c\u0018n\u001c8JIB\u0011A&C\n\u0004\u0013=r\u0004C\u0001\u001c@\u0013\t\u0001uGA\u0005FqR,gn]5p]\u000611/_:uK6\u0004\"AN\"\n\u0005\u0011;$aE#yi\u0016tG-\u001a3BGR|'oU=ti\u0016l\u0017AB2p]\u001aLw-F\u0001H!\tA5*D\u0001J\u0015\tQE%A\u0002tg2L!\u0001T%\u0003#M\u001bFjQ8oM&<7+\u001a;uS:<7/A\u0004d_:4\u0017n\u001a\u0011\u0002\rqJg.\u001b;?)\ra\u0004+\u0015\u0005\u0006\u00036\u0001\rA\u0011\u0005\u0006\u000b6\u0001\raR\u0001\t[.dunZ4feV\tA\u000b\u0005\u0002V16\taK\u0003\u0002XE\u0005!Q\u000f^5m\u0013\tIfKA\tBW.\fGj\\4hKJ4\u0015m\u0019;pef\f\u0011\"\\6M_\u001e<WM\u001d\u0011\u0002\u00071|w-F\u0001^!\tq\u0016-D\u0001`\u0015\t\u0001\u0017(A\u0003fm\u0016tG/\u0003\u0002c?\nqAj\\4hS:<\u0017\tZ1qi\u0016\u0014\u0018\u0001\u00027pO\u0002\nAb^5uQN+G\u000f^5oON$\"\u0001\u00104\t\u000b\u001d\u0014\u0002\u0019A$\u0002\u0003\r\f1\"\\1q'\u0016$H/\u001b8hgR\u0011AH\u001b\u0005\u0006WN\u0001\r\u0001\\\u0001\u0002MB!\u0001'\\$H\u0013\tq\u0017GA\u0005Gk:\u001cG/[8oc\u0005y1m\u001c8wKJ$8+\u001a;uS:<7\u000f\u0006\u0002=c\")1\u000e\u0006a\u0001eB!1/_$H\u001b\u0005!(BA;w\u0003!1WO\\2uS>t'BA,x\u0015\u0005A\u0018\u0001\u00026bm\u0006L!A\u001f;\u0003\u0011\u0019+hn\u0019;j_:\f\u0001\u0003[8ti:\fW.\u001a,fe&4\u0017.\u001a:\u0016\u0003u\u00042A`A\u0005\u001b\u0005y(b\u0001&\u0002\u0002)!\u00111AA\u0003\u0003\rqW\r\u001e\u0006\u0003\u0003\u000f\tQA[1wCbL1!a\u0003\u0000\u0005AAun\u001d;oC6,g+\u001a:jM&,'/A\ti_N$h.Y7f-\u0016\u0014\u0018NZ5fe\u0002\n!$^:f\u0015Zl\u0007j\\:u]\u0006lWMV3sS\u001aL7-\u0019;j_:,\"!a\u0005\u0011\u0007A\n)\"C\u0002\u0002\u0018E\u0012qAQ8pY\u0016\fg\u000eK\u0002\u0018\u00037\u0001B!!\b\u0002$5\u0011\u0011q\u0004\u0006\u0004\u0003CI\u0014AC1o]>$\u0018\r^5p]&!\u0011QEA\u0010\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0002+M\u001cH.\u00128hS:,7i\u001c8gS\u001e,(/\u0019;peV\u0011\u00111\u0006\t\u0004Y\u00055\u0012bAA\u0018E\taB)\u001a4bk2$8k\u0015'F]\u001eLg.Z\"p]\u001aLw-\u001e:bi>\u0014\u0018AF:tY\u0016sw-\u001b8f\u0007>tg-[4ve\u0006$xN\u001d\u0011\u0002-\t,\u0018\u000e\u001c3LKfl\u0015M\\1hKJ4\u0015m\u0019;pef$B!a\u000e\u0002>A\u0019\u0001*!\u000f\n\u0007\u0005m\u0012J\u0001\rLKfl\u0015M\\1hKJ4\u0015m\u0019;pef<&/\u00199qKJDQA\u0013\u000eA\u0002\u001d\u000b\u0001DY;jY\u0012$&/^:u\u001b\u0006t\u0017mZ3s\r\u0006\u001cGo\u001c:z)\u0011\t\u0019%!\u0013\u0011\u0007!\u000b)%C\u0002\u0002H%\u0013!\u0004\u0016:vgRl\u0015M\\1hKJ4\u0015m\u0019;pef<&/\u00199qKJDQAS\u000eA\u0002\u001d\u000bQCY;jY\u0012Dun\u001d;oC6,g+\u001a:jM&,'\u000fF\u0002~\u0003\u001fBa!!\u0015\u001d\u0001\u00049\u0015\u0001B2p]\u001a\f1D^1mS\u0012\fG/\u001a#fM\u0006,H\u000e\u001e+skN$X*\u00198bO\u0016\u0014H\u0003BA,\u0003;\u00022\u0001MA-\u0013\r\tY&\r\u0002\u0005+:LG\u000f\u0003\u0004\u0002`u\u0001\raR\u0001\ngNd7i\u001c8gS\u001e\f!cY8oM&<WO]3Qe>$xnY8mgR1\u0011QMAA\u0003\u000b\u0003R\u0001MA4\u0003WJ1!!\u001b2\u0005\u0015\t%O]1z!\u0011\ti'a\u001f\u000f\t\u0005=\u0014q\u000f\t\u0004\u0003c\nTBAA:\u0015\r\t)HK\u0001\u0007yI|w\u000e\u001e \n\u0007\u0005e\u0014'\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003{\nyH\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003s\n\u0004bBAB=\u0001\u0007\u0011QM\u0001\u0012KbL7\u000f^5oOB\u0013x\u000e^8d_2\u001c\bBBA0=\u0001\u0007q)A\u000bd_:4\u0017nZ;sK\u000eK\u0007\u000f[3s'VLG/Z:\u0015\r\u0005\u0015\u00141RAH\u0011\u001d\tii\ba\u0001\u0003K\nq\"\u001a=jgRLgnZ\"ja\",'o\u001d\u0005\u0007\u0003?z\u0002\u0019A$\u0002\u001f1|wn]3ESN\f'\r\\3T\u001d&#B!a\u0016\u0002\u0016\"9\u0011q\u0013\u0011A\u0002\u0005e\u0015!\u00043fM\u0006,H\u000e\u001e)be\u0006l7\u000fE\u0002\u007f\u00037K1!!(\u0000\u00055\u00196\u000b\u0014)be\u0006lW\r^3sg\"Z\u0011\"!)\u0002(\u0006%\u0016QVAX!\r\u0001\u00141U\u0005\u0004\u0003K\u000b$A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017EAAV\u0003y+6/\u001a\u0011UGB\u0004\u0013M\u001c3!)2\u001b\u0006e^5uQ\u0002\u001a6\u000bT#oO&tW\r\t9be\u0006lW\r^3sg\u0002Jgn\u001d;fC\u0012t\u0003eU3ukB\u0004C\u000f[3!'NcUI\\4j]\u0016\u0004s/\u001b;iA9,W\rZ3eAA\f'/Y7fi\u0016\u00148OL\u0001\u0006g&t7-Z\u0011\u0003\u0003c\u000bQA\r\u00187]A\u00022ANA[\u0013\r\t9l\u000e\u0002\u0014\u000bb$XM\\:j_:LE\r\u0015:pm&$WM\u001d\u000b\u0002W\u0005\u0019q-\u001a;\u0015\u0007q\ny\f\u0003\u0004B\u0007\u0001\u0007\u0011\u0011\u0019\t\u0004m\u0005\r\u0017bAAco\tY\u0011i\u0019;peNK8\u000f^3n)\ra\u0014\u0011\u001a\u0005\u0007\u0003\u0012\u0001\r!a3\u0011\u0007Y\ni-C\u0002\u0002P^\u0012!d\u00117bgNL7-Q2u_J\u001c\u0016p\u001d;f[B\u0013xN^5eKJ\fQ!\u00199qYf$\"!!6\u0015\u0007q\n9\u000e\u0003\u0004B\u000b\u0001\u000f\u0011\u0011Y\u0001\u0007Y>|7.\u001e9\u0016\u0003-\nqb\u0019:fCR,W\t\u001f;f]NLwN\u001c\u000b\u0004y\u0005\u0005\b\"B!\b\u0001\u0004\u0011\u0015\u0001\u00073fM\u0006,H\u000e^*T\u0019\u000e{gNZ5h'\u0016$H/\u001b8hgR\u0019q)a:\t\r\u0005C\u0001\u0019AAaQ-\t\u0011\u0011UAT\u0003S\u000bi+a,)\u0017\u0001\t\t+a*\u0002*\u00065\u0016q\u0016")
public final class AkkaSSLConfig
implements Extension {
    private final ExtendedActorSystem system;
    private final SSLConfigSettings config;
    private final AkkaLoggerFactory mkLogger;
    private final LoggingAdapter log;
    private final HostnameVerifier hostnameVerifier;
    private final DefaultSSLEngineConfigurator sslEngineConfigurator;

    public static SSLConfigSettings defaultSSLConfigSettings(ActorSystem system) {
        return AkkaSSLConfig$.MODULE$.defaultSSLConfigSettings(system);
    }

    public static AkkaSSLConfig createExtension(ExtendedActorSystem system) {
        return AkkaSSLConfig$.MODULE$.createExtension(system);
    }

    public static AkkaSSLConfig$ lookup() {
        return AkkaSSLConfig$.MODULE$.lookup();
    }

    public static AkkaSSLConfig apply(ActorSystem system) {
        return AkkaSSLConfig$.MODULE$.apply(system);
    }

    public static AkkaSSLConfig get(ClassicActorSystemProvider system) {
        return AkkaSSLConfig$.MODULE$.get(system);
    }

    public static AkkaSSLConfig get(ActorSystem system) {
        return AkkaSSLConfig$.MODULE$.get(system);
    }

    public static Extension apply(ClassicActorSystemProvider system) {
        return AkkaSSLConfig$.MODULE$.apply(system);
    }

    public SSLConfigSettings config() {
        return this.config;
    }

    private AkkaLoggerFactory mkLogger() {
        return this.mkLogger;
    }

    private LoggingAdapter log() {
        return this.log;
    }

    public AkkaSSLConfig withSettings(SSLConfigSettings c) {
        return new AkkaSSLConfig(this.system, c);
    }

    public AkkaSSLConfig mapSettings(Function1<SSLConfigSettings, SSLConfigSettings> f) {
        return new AkkaSSLConfig(this.system, f.apply(this.config()));
    }

    public AkkaSSLConfig convertSettings(Function<SSLConfigSettings, SSLConfigSettings> f) {
        return new AkkaSSLConfig(this.system, f.apply(this.config()));
    }

    public HostnameVerifier hostnameVerifier() {
        return this.hostnameVerifier;
    }

    @InternalApi
    public boolean useJvmHostnameVerification() {
        HostnameVerifier hostnameVerifier = this.hostnameVerifier();
        return hostnameVerifier instanceof DefaultHostnameVerifier ? true : hostnameVerifier instanceof NoopHostnameVerifier;
    }

    public DefaultSSLEngineConfigurator sslEngineConfigurator() {
        return this.sslEngineConfigurator;
    }

    public KeyManagerFactoryWrapper buildKeyManagerFactory(SSLConfigSettings ssl) {
        String keyManagerAlgorithm = ssl.keyManagerConfig().algorithm();
        return new DefaultKeyManagerFactoryWrapper(keyManagerAlgorithm);
    }

    public TrustManagerFactoryWrapper buildTrustManagerFactory(SSLConfigSettings ssl) {
        String trustManagerAlgorithm = ssl.trustManagerConfig().algorithm();
        return new DefaultTrustManagerFactoryWrapper(trustManagerAlgorithm);
    }

    public HostnameVerifier buildHostnameVerifier(SSLConfigSettings conf) {
        boolean bl = conf != null;
        Class<DisabledComplainingHostnameVerifier> clazz = this.config().loose().disableHostnameVerification() ? DisabledComplainingHostnameVerifier.class : this.config().hostnameVerifierClass();
        HostnameVerifier v = (HostnameVerifier)this.system.dynamicAccess().createInstanceFor(clazz, Nil$.MODULE$, ClassTag$.MODULE$.apply(HostnameVerifier.class)).orElse((Function0<Try> & Serializable)() -> $this.system.dynamicAccess().createInstanceFor(clazz, new $colon$colon<Nothing$>((Nothing$)((Object)Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(LoggerFactory.class), this.mkLogger())), Nil$.MODULE$), ClassTag$.MODULE$.apply(HostnameVerifier.class))).getOrElse((Function0<Nothing$> & Serializable)() -> {
            throw new Exception(new StringBuilder(46).append("Unable to obtain hostname verifier for class: ").append(clazz).toString());
        });
        this.log().debug("buildHostnameVerifier: created hostname verifier: {}", v);
        return v;
    }

    public void validateDefaultTrustManager(SSLConfigSettings sslConfig) {
        this.log().warning("validateDefaultTrustManager is not doing anything since akka 2.6.19, it was useful only in Java 7 and below");
    }

    public String[] configureProtocols(String[] existingProtocols, SSLConfigSettings sslConfig) {
        String[] stringArray;
        Option<Seq<String>> option = sslConfig.enabledProtocols();
        if (option instanceof Some) {
            Some some = (Some)option;
            Seq configuredProtocols = (Seq)some.value();
            Object object = Predef$.MODULE$.refArrayOps(existingProtocols);
            stringArray = (String[])((IterableOnceOps)configuredProtocols.filter((Function1<String, Object> & Serializable)elem -> BoxesRunTime.boxToBoolean(ArrayOps$.MODULE$.contains$extension(object, elem)))).toArray(ClassTag$.MODULE$.apply(String.class));
        } else if (None$.MODULE$.equals(option)) {
            Object object = Predef$.MODULE$.refArrayOps(existingProtocols);
            stringArray = (String[])ArrayOps$.MODULE$.filter$extension(Predef$.MODULE$.refArrayOps(Protocols$.MODULE$.recommendedProtocols()), (Function1<String, Object> & Serializable)elem -> BoxesRunTime.boxToBoolean(ArrayOps$.MODULE$.contains$extension(object, elem)));
        } else {
            throw new MatchError(option);
        }
        String[] definedProtocols = stringArray;
        return definedProtocols;
    }

    public String[] configureCipherSuites(String[] existingCiphers, SSLConfigSettings sslConfig) {
        String[] stringArray;
        Option<Seq<String>> option = sslConfig.enabledCipherSuites();
        if (option instanceof Some) {
            Some some = (Some)option;
            Seq configuredCiphers = (Seq)some.value();
            stringArray = (String[])((IterableOnceOps)configuredCiphers.filter((Function1<String, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(ArrayOps$.MODULE$.contains$extension(Predef$.MODULE$.refArrayOps(existingCiphers), x$1)))).toArray(ClassTag$.MODULE$.apply(String.class));
        } else if (None$.MODULE$.equals(option)) {
            stringArray = existingCiphers;
        } else {
            throw new MatchError(option);
        }
        String[] definedCiphers = stringArray;
        return definedCiphers;
    }

    private void looseDisableSNI(SSLParameters defaultParams) {
        if (this.config().loose().disableSNI()) {
            this.log().warning("You are using ssl-config.loose.disableSNI=true! It is strongly discouraged to disable Server Name Indication, as it is crucial to preventing man-in-the-middle attacks.");
            defaultParams.setServerNames(Collections.emptyList());
            defaultParams.setSNIMatchers(Collections.emptyList());
            return;
        }
    }

    public AkkaSSLConfig(ExtendedActorSystem system, SSLConfigSettings config) {
        SSLContext sSLContext;
        this.system = system;
        this.config = config;
        this.mkLogger = new AkkaLoggerFactory(system);
        this.log = Logging$.MODULE$.apply(system, AkkaSSLConfig.class, LogSource$.MODULE$.fromAnyClass());
        this.log().debug("Initializing AkkaSSLConfig extension...");
        this.hostnameVerifier = this.buildHostnameVerifier(config);
        if (config.default()) {
            this.log().info("ssl-config.default is true, using the JDK's default SSLContext");
            sSLContext = SSLContext.getDefault();
        } else {
            KeyManagerFactoryWrapper keyManagerFactory = this.buildKeyManagerFactory(config);
            TrustManagerFactoryWrapper trustManagerFactory = this.buildTrustManagerFactory(config);
            sSLContext = new ConfigSSLContextBuilder(this.mkLogger(), config, keyManagerFactory, trustManagerFactory).build();
        }
        SSLContext sslContext = sSLContext;
        SSLParameters defaultParams = sslContext.getDefaultSSLParameters();
        String[] defaultProtocols = defaultParams.getProtocols();
        String[] protocols = this.configureProtocols(defaultProtocols, config);
        String[] defaultCiphers = defaultParams.getCipherSuites();
        String[] cipherSuites = this.configureCipherSuites(defaultCiphers, config);
        this.looseDisableSNI(defaultParams);
        this.sslEngineConfigurator = new DefaultSSLEngineConfigurator(config, protocols, cipherSuites);
    }
}

