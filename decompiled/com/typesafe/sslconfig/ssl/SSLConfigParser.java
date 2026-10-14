/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.ClientAuth$Default$;
import com.typesafe.sslconfig.ssl.ClientAuth$Need$;
import com.typesafe.sslconfig.ssl.ClientAuth$None$;
import com.typesafe.sslconfig.ssl.ClientAuth$Want$;
import com.typesafe.sslconfig.ssl.KeyManagerConfig;
import com.typesafe.sslconfig.ssl.KeyStoreConfig;
import com.typesafe.sslconfig.ssl.NoopHostnameVerifier;
import com.typesafe.sslconfig.ssl.SSLConfigSettings;
import com.typesafe.sslconfig.ssl.SSLConfigSettings$;
import com.typesafe.sslconfig.ssl.SSLDebugConfig;
import com.typesafe.sslconfig.ssl.SSLDebugConfig$;
import com.typesafe.sslconfig.ssl.SSLDebugHandshakeOptions;
import com.typesafe.sslconfig.ssl.SSLDebugRecordOptions;
import com.typesafe.sslconfig.ssl.SSLLooseConfig;
import com.typesafe.sslconfig.ssl.SSLLooseConfig$;
import com.typesafe.sslconfig.ssl.SSLParametersConfig;
import com.typesafe.sslconfig.ssl.TrustManagerConfig;
import com.typesafe.sslconfig.ssl.TrustStoreConfig;
import com.typesafe.sslconfig.util.ConfigLoader$;
import com.typesafe.sslconfig.util.EnrichedConfig;
import com.typesafe.sslconfig.util.LoggerFactory;
import com.typesafe.sslconfig.util.NoDepsLogger;
import java.io.Serializable;
import java.net.URL;
import java.security.KeyStore;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005A4AAD\b\u00011!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005\u0003\u0005'\u0001\t\u0005\t\u0015!\u0003(\u0011!y\u0003A!A!\u0002\u0013\u0001\u0004\"\u0002\u001c\u0001\t\u00039\u0004\"\u0002\u001c\u0001\t\u0003i\u0004\"\u0002!\u0001\t\u0003\t\u0005\"B#\u0001\t\u00031\u0005\"\u0002'\u0001\t\u0003i\u0005\"\u0002*\u0001\t\u0003\u0019\u0006\"\u0002-\u0001\t\u0003I\u0006\"\u00020\u0001\t\u0003y\u0006\"\u00023\u0001\t\u0003)\u0007\"\u00026\u0001\t\u0003Y'aD*T\u0019\u000e{gNZ5h!\u0006\u00148/\u001a:\u000b\u0005A\t\u0012aA:tY*\u0011!cE\u0001\ngNd7m\u001c8gS\u001eT!\u0001F\u000b\u0002\u0011QL\b/Z:bM\u0016T\u0011AF\u0001\u0004G>l7\u0001A\n\u0003\u0001e\u0001\"AG\u000f\u000e\u0003mQ\u0011\u0001H\u0001\u0006g\u000e\fG.Y\u0005\u0003=m\u0011a!\u00118z%\u00164\u0017!A2\u0011\u0005\u0005\"S\"\u0001\u0012\u000b\u0005\r\n\u0012\u0001B;uS2L!!\n\u0012\u0003\u001d\u0015s'/[2iK\u0012\u001cuN\u001c4jO\u0006Y1\r\\1tg2{\u0017\rZ3s!\tAS&D\u0001*\u0015\tQ3&\u0001\u0003mC:<'\"\u0001\u0017\u0002\t)\fg/Y\u0005\u0003]%\u00121b\u00117bgNdu.\u00193fe\u0006iAn\\4hKJ4\u0015m\u0019;pef\u00042AG\u00194\u0013\t\u00114D\u0001\u0004PaRLwN\u001c\t\u0003CQJ!!\u000e\u0012\u0003\u001b1{wmZ3s\r\u0006\u001cGo\u001c:z\u0003\u0019a\u0014N\\5u}Q!\u0001HO\u001e=!\tI\u0004!D\u0001\u0010\u0011\u0015yB\u00011\u0001!\u0011\u00151C\u00011\u0001(\u0011\u0015yC\u00011\u00011)\rAdh\u0010\u0005\u0006?\u0015\u0001\r\u0001\t\u0005\u0006M\u0015\u0001\raJ\u0001\u0006a\u0006\u00148/\u001a\u000b\u0002\u0005B\u0011\u0011hQ\u0005\u0003\t>\u0011\u0011cU*M\u0007>tg-[4TKR$\u0018N\\4t\u0003E\u0001\u0018M]:f\u0019>|7/Z(qi&|gn\u001d\u000b\u0003\u000f*\u0003\"!\u000f%\n\u0005%{!AD*T\u00192{wn]3D_:4\u0017n\u001a\u0005\u0006\u0017\u001e\u0001\r\u0001I\u0001\u0007G>tg-[4\u0002\u0015A\f'o]3EK\n,x\r\u0006\u0002O#B\u0011\u0011hT\u0005\u0003!>\u0011abU*M\t\u0016\u0014WoZ\"p]\u001aLw\rC\u0003L\u0011\u0001\u0007\u0001%A\tqCJ\u001cXmS3z'R|'/Z%oM>$\"\u0001V,\u0011\u0005e*\u0016B\u0001,\u0010\u00059YU-_*u_J,7i\u001c8gS\u001eDQaS\u0005A\u0002\u0001\n1\u0003]1sg\u0016$&/^:u'R|'/Z%oM>$\"AW/\u0011\u0005eZ\u0016B\u0001/\u0010\u0005A!&/^:u'R|'/Z\"p]\u001aLw\rC\u0003L\u0015\u0001\u0007\u0001%A\bqCJ\u001cXmS3z\u001b\u0006t\u0017mZ3s)\t\u00017\r\u0005\u0002:C&\u0011!m\u0004\u0002\u0011\u0017\u0016LX*\u00198bO\u0016\u00148i\u001c8gS\u001eDQaS\u0006A\u0002\u0001\n\u0011\u0003]1sg\u0016$&/^:u\u001b\u0006t\u0017mZ3s)\t1\u0017\u000e\u0005\u0002:O&\u0011\u0001n\u0004\u0002\u0013)J,8\u000f^'b]\u0006<WM]\"p]\u001aLw\rC\u0003L\u0019\u0001\u0007\u0001%\u0001\nqCJ\u001cXmU*M!\u0006\u0014\u0018-\\3uKJ\u001cHC\u00017p!\tIT.\u0003\u0002o\u001f\t\u00192k\u0015'QCJ\fW.\u001a;feN\u001cuN\u001c4jO\")1*\u0004a\u0001A\u0001")
public class SSLConfigParser {
    private final EnrichedConfig c;
    private final ClassLoader classLoader;
    private final Option<LoggerFactory> loggerFactory;

    public SSLConfigSettings parse() {
        Class clazz;
        boolean bl = BoxesRunTime.unboxToBoolean(this.c.get("default", ConfigLoader$.MODULE$.booleanLoader()));
        String protocol = this.c.get("protocol", ConfigLoader$.MODULE$.stringLoader());
        Option<Object> checkRevocation = this.c.getOptional("checkRevocation", ConfigLoader$.MODULE$.booleanLoader());
        Option<Seq<URL>> revocationLists = new Some(this.c.getSeq("revocationLists", ConfigLoader$.MODULE$.seqStringLoader()).map((Function1<String, URL> & Serializable)x$1 -> new URL((String)x$1))).filter((Function1<Seq, Object> & Serializable)x$2 -> BoxesRunTime.boxToBoolean(x$2.nonEmpty()));
        SSLDebugConfig debug = this.parseDebug(this.c.get("debug", ConfigLoader$.MODULE$.playConfigLoader()));
        SSLLooseConfig looseOptions = this.parseLooseOptions(this.c.get("loose", ConfigLoader$.MODULE$.playConfigLoader()));
        Option<Seq<String>> ciphers = new Some(this.c.getSeq("enabledCipherSuites", ConfigLoader$.MODULE$.seqStringLoader())).filter((Function1<Seq, Object> & Serializable)x$3 -> BoxesRunTime.boxToBoolean(x$3.nonEmpty()));
        Option<Seq<String>> protocols = new Some(this.c.getSeq("enabledProtocols", ConfigLoader$.MODULE$.seqStringLoader())).filter((Function1<Seq, Object> & Serializable)x$4 -> BoxesRunTime.boxToBoolean(x$4.nonEmpty()));
        Option<String> option = this.c.getOptional("hostnameVerifierClass", ConfigLoader$.MODULE$.stringLoader());
        if (None$.MODULE$.equals(option)) {
            clazz = NoopHostnameVerifier.class;
        } else if (option instanceof Some) {
            Some some = (Some)option;
            String fqcn = (String)some.value();
            clazz = this.classLoader.loadClass(fqcn).asSubclass(HostnameVerifier.class);
        } else {
            throw new MatchError(option);
        }
        Class<NoopHostnameVerifier> hostnameVerifierClass = clazz;
        KeyManagerConfig keyManagers = this.parseKeyManager(this.c.get("keyManager", ConfigLoader$.MODULE$.playConfigLoader()));
        TrustManagerConfig trustManagers = this.parseTrustManager(this.c.get("trustManager", ConfigLoader$.MODULE$.playConfigLoader()));
        SSLParametersConfig sslParametersConfig = this.parseSSLParameters(this.c.get("sslParameters", ConfigLoader$.MODULE$.playConfigLoader()));
        boolean x$12 = bl;
        String x$22 = protocol;
        Option<Object> x$32 = checkRevocation;
        Option<Seq<URL>> x$42 = revocationLists;
        Option<Seq<String>> x$5 = ciphers;
        Option<Seq<String>> x$6 = protocols;
        KeyManagerConfig x$7 = keyManagers;
        Class<NoopHostnameVerifier> x$8 = hostnameVerifierClass;
        SSLParametersConfig x$9 = sslParametersConfig;
        TrustManagerConfig x$10 = trustManagers;
        None$ x$11 = None$.MODULE$;
        SSLDebugConfig x$122 = debug;
        SSLLooseConfig x$13 = looseOptions;
        Seq<String> x$14 = SSLConfigSettings$.MODULE$.$lessinit$greater$default$7();
        return new SSLConfigSettings(x$12, x$22, x$32, x$42, x$5, x$6, x$14, x$9, x$7, x$10, x$8, x$11, x$122, x$13);
    }

    public SSLLooseConfig parseLooseOptions(EnrichedConfig config) {
        Option<Object> allowMessages = config.getOptional("allowLegacyHelloMessages", ConfigLoader$.MODULE$.booleanLoader());
        Option<Object> allowUnsafeRenegotiation = config.getOptional("allowUnsafeRenegotiation", ConfigLoader$.MODULE$.booleanLoader());
        boolean disableHostnameVerification = BoxesRunTime.unboxToBoolean(config.get("disableHostnameVerification", ConfigLoader$.MODULE$.booleanLoader()));
        boolean disableSNI = BoxesRunTime.unboxToBoolean(config.get("disableSNI", ConfigLoader$.MODULE$.booleanLoader()));
        boolean acceptAnyCertificate = BoxesRunTime.unboxToBoolean(config.get("acceptAnyCertificate", ConfigLoader$.MODULE$.booleanLoader()));
        Option<Object> x$1 = allowMessages;
        Option<Object> x$2 = allowUnsafeRenegotiation;
        boolean x$3 = disableHostnameVerification;
        boolean x$4 = disableSNI;
        boolean x$5 = acceptAnyCertificate;
        boolean x$6 = SSLLooseConfig$.MODULE$.$lessinit$greater$default$4();
        boolean x$7 = SSLLooseConfig$.MODULE$.$lessinit$greater$default$5();
        return new SSLLooseConfig(x$5, x$1, x$2, x$6, x$7, x$3, x$4);
    }

    public SSLDebugConfig parseDebug(EnrichedConfig config) {
        SSLDebugConfig sSLDebugConfig;
        if (BoxesRunTime.unboxToBoolean(config.get("all", ConfigLoader$.MODULE$.booleanLoader()))) {
            sSLDebugConfig = new SSLDebugConfig(true, SSLDebugConfig$.MODULE$.$lessinit$greater$default$2(), SSLDebugConfig$.MODULE$.$lessinit$greater$default$3(), SSLDebugConfig$.MODULE$.$lessinit$greater$default$4(), SSLDebugConfig$.MODULE$.$lessinit$greater$default$5(), SSLDebugConfig$.MODULE$.$lessinit$greater$default$6(), SSLDebugConfig$.MODULE$.$lessinit$greater$default$7(), SSLDebugConfig$.MODULE$.$lessinit$greater$default$8(), SSLDebugConfig$.MODULE$.$lessinit$greater$default$9(), SSLDebugConfig$.MODULE$.$lessinit$greater$default$10(), SSLDebugConfig$.MODULE$.$lessinit$greater$default$11(), SSLDebugConfig$.MODULE$.$lessinit$greater$default$12(), SSLDebugConfig$.MODULE$.$lessinit$greater$default$13(), SSLDebugConfig$.MODULE$.$lessinit$greater$default$14());
        } else {
            boolean ssl = BoxesRunTime.unboxToBoolean(config.get("ssl", ConfigLoader$.MODULE$.booleanLoader()));
            boolean sslctx = BoxesRunTime.unboxToBoolean(config.get("sslctx", ConfigLoader$.MODULE$.booleanLoader()));
            boolean keymanager = BoxesRunTime.unboxToBoolean(config.get("keymanager", ConfigLoader$.MODULE$.booleanLoader()));
            boolean trustmanager = BoxesRunTime.unboxToBoolean(config.get("trustmanager", ConfigLoader$.MODULE$.booleanLoader()));
            this.loggerFactory.foreach((Function1<LoggerFactory, Object> & Serializable)lf -> {
                SSLConfigParser.$anonfun$parseDebug$1(this, config, lf);
                return BoxedUnit.UNIT;
            });
            boolean x$1 = ssl;
            boolean x$2 = sslctx;
            boolean x$3 = keymanager;
            boolean x$4 = trustmanager;
            boolean x$5 = SSLDebugConfig$.MODULE$.$lessinit$greater$default$1();
            boolean x$6 = SSLDebugConfig$.MODULE$.$lessinit$greater$default$2();
            boolean x$7 = SSLDebugConfig$.MODULE$.$lessinit$greater$default$3();
            Option<SSLDebugHandshakeOptions> x$8 = SSLDebugConfig$.MODULE$.$lessinit$greater$default$4();
            boolean x$9 = SSLDebugConfig$.MODULE$.$lessinit$greater$default$5();
            boolean x$10 = SSLDebugConfig$.MODULE$.$lessinit$greater$default$7();
            boolean x$11 = SSLDebugConfig$.MODULE$.$lessinit$greater$default$8();
            Option<SSLDebugRecordOptions> x$12 = SSLDebugConfig$.MODULE$.$lessinit$greater$default$9();
            boolean x$13 = SSLDebugConfig$.MODULE$.$lessinit$greater$default$10();
            boolean x$14 = SSLDebugConfig$.MODULE$.$lessinit$greater$default$11();
            sSLDebugConfig = new SSLDebugConfig(x$5, x$6, x$7, x$8, x$9, x$3, x$10, x$11, x$12, x$13, x$14, x$1, x$2, x$4);
        }
        return sSLDebugConfig;
    }

    public KeyStoreConfig parseKeyStoreInfo(EnrichedConfig config) {
        String storeType = (String)config.getOptional("type", ConfigLoader$.MODULE$.stringLoader()).getOrElse((Function0<String> & Serializable)() -> KeyStore.getDefaultType());
        Option<String> path = config.getOptional("path", ConfigLoader$.MODULE$.stringLoader());
        boolean classPath = BoxesRunTime.unboxToBoolean(config.getOptional("classpath", ConfigLoader$.MODULE$.booleanLoader()).getOrElse(() -> false));
        Option<String> data = config.getOptional("data", ConfigLoader$.MODULE$.stringLoader());
        Option<String> password = config.getOptional("password", ConfigLoader$.MODULE$.stringLoader());
        Option<String> x$1 = path;
        String x$2 = storeType;
        boolean x$3 = classPath;
        Option<String> x$4 = data;
        Option<String> x$5 = password;
        return new KeyStoreConfig(x$4, x$1, x$3, x$5, x$2);
    }

    public TrustStoreConfig parseTrustStoreInfo(EnrichedConfig config) {
        String storeType = (String)config.getOptional("type", ConfigLoader$.MODULE$.stringLoader()).getOrElse((Function0<String> & Serializable)() -> KeyStore.getDefaultType());
        Option<String> path = config.getOptional("path", ConfigLoader$.MODULE$.stringLoader());
        boolean classPath = BoxesRunTime.unboxToBoolean(config.getOptional("classpath", ConfigLoader$.MODULE$.booleanLoader()).getOrElse(() -> false));
        Option<String> data = config.getOptional("data", ConfigLoader$.MODULE$.stringLoader());
        Option<String> password = config.getOptional("password", ConfigLoader$.MODULE$.stringLoader());
        Option<String> x$1 = path;
        boolean x$2 = classPath;
        String x$3 = storeType;
        Option<String> x$4 = data;
        Option<String> x$5 = password;
        return new TrustStoreConfig(x$4, x$1, x$5, x$2, x$3);
    }

    public KeyManagerConfig parseKeyManager(EnrichedConfig config) {
        String string;
        Option<String> option = config.getOptional("algorithm", ConfigLoader$.MODULE$.stringLoader());
        if (None$.MODULE$.equals(option)) {
            string = KeyManagerFactory.getDefaultAlgorithm();
        } else if (option instanceof Some) {
            String other;
            Some some = (Some)option;
            string = other = (String)some.value();
        } else {
            throw new MatchError(option);
        }
        String algorithm = string;
        Seq keyStoreInfos = (Seq)config.getPrototypedSeq("stores", config.getPrototypedSeq$default$2()).map((Function1<EnrichedConfig, KeyStoreConfig> & Serializable)store -> this.parseKeyStoreInfo((EnrichedConfig)store));
        return new KeyManagerConfig(algorithm, keyStoreInfos);
    }

    public TrustManagerConfig parseTrustManager(EnrichedConfig config) {
        String string;
        Option<String> option = config.getOptional("algorithm", ConfigLoader$.MODULE$.stringLoader());
        if (None$.MODULE$.equals(option)) {
            string = TrustManagerFactory.getDefaultAlgorithm();
        } else if (option instanceof Some) {
            String other;
            Some some = (Some)option;
            string = other = (String)some.value();
        } else {
            throw new MatchError(option);
        }
        String algorithm = string;
        Seq trustStoreInfos = (Seq)config.getPrototypedSeq("stores", config.getPrototypedSeq$default$2()).map((Function1<EnrichedConfig, TrustStoreConfig> & Serializable)store -> this.parseTrustStoreInfo((EnrichedConfig)store));
        return new TrustManagerConfig(algorithm, trustStoreInfos);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public SSLParametersConfig parseSSLParameters(EnrichedConfig config) {
        var5_2 = false;
        var6_3 = null;
        var7_4 = config.getOptional("clientAuth", ConfigLoader$.MODULE$.stringLoader());
        if (!(var7_4 instanceof Some)) ** GOTO lbl-1000
        var5_2 = true;
        var6_3 = (Some)var7_4;
        var8_5 = (String)var6_3.value();
        if ("none".equals(var8_5)) {
            var2_6 /* !! */  = ClientAuth$None$.MODULE$;
        } else if (var5_2 && "want".equals(var9_7 = (String)var6_3.value())) {
            var2_6 /* !! */  = ClientAuth$Want$.MODULE$;
        } else if (var5_2 && "need".equals(var10_8 = (String)var6_3.value())) {
            var2_6 /* !! */  = ClientAuth$Need$.MODULE$;
        } else {
            var3_9 = None$.MODULE$.equals(var7_4) != false ? true : var7_4 instanceof Some != false;
            if (var3_9) {
                var2_6 /* !! */  = ClientAuth$Default$.MODULE$;
            } else {
                throw new MatchError(var7_4);
            }
        }
        clientAuth /* !! */  = var2_6 /* !! */ ;
        protocols = config.getSeq("protocols", ConfigLoader$.MODULE$.seqStringLoader());
        return new SSLParametersConfig(clientAuth /* !! */ , protocols);
    }

    public static final /* synthetic */ void $anonfun$parseDebug$2(EnrichedConfig config$1, NoDepsLogger logger$1, String setting) {
        block0: {
            if (!BoxesRunTime.unboxToBoolean(config$1.get(setting, ConfigLoader$.MODULE$.booleanLoader()))) break block0;
            logger$1.warn(new StringBuilder(49).append(setting).append(" is a deprecated debug setting and has no effect!").toString());
        }
    }

    public static final /* synthetic */ void $anonfun$parseDebug$1(SSLConfigParser $this, EnrichedConfig config$1, LoggerFactory lf) {
        NoDepsLogger logger2 = lf.apply($this.getClass());
        Seq deprecatedSettings = (Seq)package$.MODULE$.Seq().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new String[]{"plaintext", "packet", "handshake", "data", "verbose", "keygen", "session", "defaultctx", "sessioncache", "pluggability"}));
        deprecatedSettings.foreach((Function1<String, Object> & Serializable)setting -> {
            SSLConfigParser.$anonfun$parseDebug$2(config$1, logger2, setting);
            return BoxedUnit.UNIT;
        });
    }

    public SSLConfigParser(EnrichedConfig c, ClassLoader classLoader, Option<LoggerFactory> loggerFactory) {
        this.c = c;
        this.classLoader = classLoader;
        this.loggerFactory = loggerFactory;
    }

    public SSLConfigParser(EnrichedConfig c, ClassLoader classLoader) {
        this(c, classLoader, None$.MODULE$);
    }
}

