/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.CompositeX509KeyManager;
import com.typesafe.sslconfig.ssl.CompositeX509TrustManager;
import com.typesafe.sslconfig.ssl.FileBasedKeyStoreBuilder;
import com.typesafe.sslconfig.ssl.FileOnClasspathBasedKeyStoreBuilder;
import com.typesafe.sslconfig.ssl.KeyManagerConfig;
import com.typesafe.sslconfig.ssl.KeyManagerFactoryWrapper;
import com.typesafe.sslconfig.ssl.KeyStoreBuilder;
import com.typesafe.sslconfig.ssl.KeyStoreConfig;
import com.typesafe.sslconfig.ssl.SSLConfigSettings;
import com.typesafe.sslconfig.ssl.SSLContextBuilder;
import com.typesafe.sslconfig.ssl.SSLDebugConfig;
import com.typesafe.sslconfig.ssl.SimpleSSLContextBuilder;
import com.typesafe.sslconfig.ssl.StringBasedKeyStoreBuilder;
import com.typesafe.sslconfig.ssl.TrustManagerConfig;
import com.typesafe.sslconfig.ssl.TrustManagerFactoryWrapper;
import com.typesafe.sslconfig.ssl.TrustStoreConfig;
import com.typesafe.sslconfig.ssl.tracing.TracingSSLContext;
import com.typesafe.sslconfig.ssl.tracing.TracingX509ExtendedKeyManager;
import com.typesafe.sslconfig.ssl.tracing.TracingX509ExtendedTrustManager;
import com.typesafe.sslconfig.util.LoggerFactory;
import com.typesafe.sslconfig.util.NoDepsLogger;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CRL;
import java.security.cert.CertSelector;
import java.security.cert.CertStore;
import java.security.cert.CertificateFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.security.cert.PKIXBuilderParameters;
import java.security.cert.X509CRL;
import java.security.cert.X509CertSelector;
import javax.crypto.BadPaddingException;
import javax.net.ssl.CertPathTrustManagerParameters;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import scala.$less$colon$less$;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.collection.IterableOnceOps;
import scala.collection.JavaConverters$;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BooleanRef;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\tub\u0001\u0002\u000e\u001c\u0001\u0011B\u0001b\f\u0001\u0003\u0002\u0003\u0006I\u0001\r\u0005\tm\u0001\u0011\t\u0011)A\u0005o!A!\b\u0001B\u0001B\u0003%1\b\u0003\u0005?\u0001\t\u0005\t\u0015!\u0003@\u0011\u0015\u0011\u0005\u0001\"\u0001D\u0011\u001dI\u0005A1A\u0005\u0012)CaA\u0014\u0001!\u0002\u0013Y\u0005\"B(\u0001\t\u0003\u0001\u0006\"\u0002.\u0001\t\u0003Y\u0006bBA\u000b\u0001\u0011\u0005\u0011q\u0003\u0005\b\u0003g\u0001A\u0011AA\u001b\u0011\u001d\t9\u0007\u0001C\u0001\u0003SBq!a\u001f\u0001\t\u0003\ti\bC\u0004\u0002\n\u0002!\t!a#\t\u000f\u0005\u001d\u0006\u0001\"\u0001\u0002*\"9\u0011\u0011\u0017\u0001\u0005\u0002\u0005M\u0006bBA]\u0001\u0011\u0005\u00111\u0018\u0005\b\u0003\u007f\u0003A\u0011AAa\u0011\u001d\ti\r\u0001C\u0001\u0003\u001fDq!!6\u0001\t\u0003\t9\u000eC\u0004\u0002j\u0002!\t!a;\t\u000f\u0005m\b\u0001\"\u0001\u0002~\"9!\u0011\u0002\u0001\u0005\u0002\t-\u0001b\u0002B\u0011\u0001\u0011\u0005!1\u0005\u0005\b\u0005g\u0001A\u0011\u0001B\u001b\u0005]\u0019uN\u001c4jON\u001bFjQ8oi\u0016DHOQ;jY\u0012,'O\u0003\u0002\u001d;\u0005\u00191o\u001d7\u000b\u0005yy\u0012!C:tY\u000e|gNZ5h\u0015\t\u0001\u0013%\u0001\u0005usB,7/\u00194f\u0015\u0005\u0011\u0013aA2p[\u000e\u00011c\u0001\u0001&WA\u0011a%K\u0007\u0002O)\t\u0001&A\u0003tG\u0006d\u0017-\u0003\u0002+O\t1\u0011I\\=SK\u001a\u0004\"\u0001L\u0017\u000e\u0003mI!AL\u000e\u0003#M\u001bFjQ8oi\u0016DHOQ;jY\u0012,'/\u0001\u0005nW2{wmZ3s!\t\tD'D\u00013\u0015\t\u0019T$\u0001\u0003vi&d\u0017BA\u001b3\u00055aunZ4fe\u001a\u000b7\r^8ss\u0006!\u0011N\u001c4p!\ta\u0003(\u0003\u0002:7\t\t2k\u0015'D_:4\u0017nZ*fiRLgnZ:\u0002#-,\u00170T1oC\u001e,'OR1di>\u0014\u0018\u0010\u0005\u0002-y%\u0011Qh\u0007\u0002\u0019\u0017\u0016LX*\u00198bO\u0016\u0014h)Y2u_JLxK]1qa\u0016\u0014\u0018a\u0005;skN$X*\u00198bO\u0016\u0014h)Y2u_JL\bC\u0001\u0017A\u0013\t\t5D\u0001\u000eUeV\u001cH/T1oC\u001e,'OR1di>\u0014\u0018p\u0016:baB,'/\u0001\u0004=S:LGO\u0010\u000b\u0006\t\u00163u\t\u0013\t\u0003Y\u0001AQaL\u0003A\u0002ABQAN\u0003A\u0002]BQAO\u0003A\u0002mBQAP\u0003A\u0002}\na\u0001\\8hO\u0016\u0014X#A&\u0011\u0005Eb\u0015BA'3\u00051qu\u000eR3qg2{wmZ3s\u0003\u001dawnZ4fe\u0002\nQAY;jY\u0012$\u0012!\u0015\t\u0003%bk\u0011a\u0015\u0006\u00039QS!!\u0016,\u0002\u00079,GOC\u0001X\u0003\u0015Q\u0017M^1y\u0013\tI6K\u0001\u0006T'2\u001buN\u001c;fqR\fqBY;jY\u0012\u001c6\u000bT\"p]R,\u0007\u0010\u001e\u000b\u0006#rKw/ \u0005\u0006;&\u0001\rAX\u0001\taJ|Go\\2pYB\u0011qL\u001a\b\u0003A\u0012\u0004\"!Y\u0014\u000e\u0003\tT!aY\u0012\u0002\rq\u0012xn\u001c;?\u0013\t)w%\u0001\u0004Qe\u0016$WMZ\u0005\u0003O\"\u0014aa\u0015;sS:<'BA3(\u0011\u0015Q\u0017\u00021\u0001l\u0003-YW-_'b]\u0006<WM]:\u0011\u00071\fHO\u0004\u0002n_:\u0011\u0011M\\\u0005\u0002Q%\u0011\u0001oJ\u0001\ba\u0006\u001c7.Y4f\u0013\t\u00118OA\u0002TKFT!\u0001]\u0014\u0011\u0005I+\u0018B\u0001<T\u0005)YU-_'b]\u0006<WM\u001d\u0005\u0006q&\u0001\r!_\u0001\u000eiJ,8\u000f^'b]\u0006<WM]:\u0011\u00071\f(\u0010\u0005\u0002Sw&\u0011Ap\u0015\u0002\r)J,8\u000f^'b]\u0006<WM\u001d\u0005\u0006}&\u0001\ra`\u0001\rg\u0016\u001cWO]3SC:$w.\u001c\t\u0006M\u0005\u0005\u0011QA\u0005\u0004\u0003\u00079#AB(qi&|g\u000e\u0005\u0003\u0002\b\u0005EQBAA\u0005\u0015\u0011\tY!!\u0004\u0002\u0011M,7-\u001e:jifT!!a\u0004\u0002\t)\fg/Y\u0005\u0005\u0003'\tIA\u0001\u0007TK\u000e,(/\u001a*b]\u0012|W.\u0001\rck&dGmQ8na>\u001c\u0018\u000e^3LKfl\u0015M\\1hKJ$b!!\u0007\u0002 \u0005%\u0002c\u0001\u0017\u0002\u001c%\u0019\u0011QD\u000e\u0003/\r{W\u000e]8tSR,\u0007,\u000e\u0019:\u0017\u0016LX*\u00198bO\u0016\u0014\bbBA\u0011\u0015\u0001\u0007\u00111E\u0001\u0011W\u0016LX*\u00198bO\u0016\u00148i\u001c8gS\u001e\u00042\u0001LA\u0013\u0013\r\t9c\u0007\u0002\u0011\u0017\u0016LX*\u00198bO\u0016\u00148i\u001c8gS\u001eDq!a\u000b\u000b\u0001\u0004\ti#A\u0003eK\n,x\rE\u0002-\u0003_I1!!\r\u001c\u00059\u00196\u000b\u0014#fEV<7i\u001c8gS\u001e\f!DY;jY\u0012\u001cu.\u001c9pg&$X\r\u0016:vgRl\u0015M\\1hKJ$\"\"a\u000e\u0002>\u0005\u001d\u0013\u0011KA3!\ra\u0013\u0011H\u0005\u0004\u0003wY\"!G\"p[B|7/\u001b;f1V\u0002\u0014\b\u0016:vgRl\u0015M\\1hKJDq!a\u0010\f\u0001\u0004\t\t%\u0001\tueV\u001cH/T1oC\u001e,'/\u00138g_B\u0019A&a\u0011\n\u0007\u0005\u00153D\u0001\nUeV\u001cH/T1oC\u001e,'oQ8oM&<\u0007bBA%\u0017\u0001\u0007\u00111J\u0001\u0012e\u00164xnY1uS>tWI\\1cY\u0016$\u0007c\u0001\u0014\u0002N%\u0019\u0011qJ\u0014\u0003\u000f\t{w\u000e\\3b]\"9\u00111K\u0006A\u0002\u0005U\u0013a\u0004:fm>\u001c\u0017\r^5p]2K7\u000f^:\u0011\u000b\u0019\n\t!a\u0016\u0011\t1\f\u0018\u0011\f\t\u0005\u00037\n\t'\u0004\u0002\u0002^)!\u0011qLA\u0005\u0003\u0011\u0019WM\u001d;\n\t\u0005\r\u0014Q\f\u0002\u0004\u0007Jc\u0005bBA\u0016\u0017\u0001\u0007\u0011QF\u0001\u0010W\u0016L8\u000b^8sK\n+\u0018\u000e\u001c3feR!\u00111NA9!\ra\u0013QN\u0005\u0004\u0003_Z\"aD&fsN#xN]3Ck&dG-\u001a:\t\u000f\u0005MD\u00021\u0001\u0002v\u0005\u00191n]2\u0011\u00071\n9(C\u0002\u0002zm\u0011abS3z'R|'/Z\"p]\u001aLw-A\tueV\u001cHo\u0015;pe\u0016\u0014U/\u001b7eKJ$B!a\u001b\u0002\u0000!9\u0011\u0011Q\u0007A\u0002\u0005\r\u0015a\u0001;tGB\u0019A&!\"\n\u0007\u0005\u001d5D\u0001\tUeV\u001cHo\u0015;pe\u0016\u001cuN\u001c4jO\u0006Ya-\u001b7f\u0005VLG\u000eZ3s)!\tY'!$\u0002\u0012\u0006U\u0005BBAH\u001d\u0001\u0007a,A\u0005ti>\u0014X\rV=qK\"1\u00111\u0013\bA\u0002y\u000b\u0001BZ5mKB\u000bG\u000f\u001b\u0005\b\u0003/s\u0001\u0019AAM\u0003!\u0001\u0018m]:x_J$\u0007#\u0002\u0014\u0002\u0002\u0005m\u0005#\u0002\u0014\u0002\u001e\u0006\u0005\u0016bAAPO\t)\u0011I\u001d:bsB\u0019a%a)\n\u0007\u0005\u0015vE\u0001\u0003DQ\u0006\u0014\u0018A\u00064jY\u0016|en\u00117bgN\u0004\u0018\r\u001e5Ck&dG-\u001a:\u0015\u0011\u0005-\u00141VAW\u0003_Ca!a$\u0010\u0001\u0004q\u0006BBAJ\u001f\u0001\u0007a\fC\u0004\u0002\u0018>\u0001\r!!'\u0002\u001bM$(/\u001b8h\u0005VLG\u000eZ3s)\u0011\tY'!.\t\r\u0005]\u0006\u00031\u0001_\u0003\u0011!\u0017\r^1\u00029]\f'O\\(o!.\u001b5+\r\u001aF[B$\u0018\u0010U1tg^|'\u000f\u001a\"vOR!\u00111JA_\u0011\u001d\t\u0019(\u0005a\u0001\u0003k\nqBY;jY\u0012\\U-_'b]\u0006<WM\u001d\u000b\u0007\u0003\u0007\fI-a3\u0011\u0007I\u000b)-C\u0002\u0002HN\u0013a\u0002W\u001b1s-+\u00170T1oC\u001e,'\u000fC\u0004\u0002tI\u0001\r!!\u001e\t\u000f\u0005-\"\u00031\u0001\u0002.\u0005I2-\u001a:uS\u001aL7-\u0019;f%\u00164xnY1uS>tG*[:u)\u0011\t)&!5\t\r\u0005M7\u00031\u00018\u0003%\u00198\u000f\\\"p]\u001aLw-A\u0006hK:,'/\u0019;f\u0007JcE\u0003BA-\u00033Dq!a7\u0015\u0001\u0004\ti.A\u0006j]B,Ho\u0015;sK\u0006l\u0007\u0003BAp\u0003Kl!!!9\u000b\t\u0005\r\u0018QB\u0001\u0003S>LA!a:\u0002b\nY\u0011J\u001c9viN#(/Z1n\u0003I9WM\\3sCR,7I\u0015'Ge>lWK\u0015'\u0015\t\u0005e\u0013Q\u001e\u0005\b\u0003_,\u0002\u0019AAy\u0003\r)(\u000f\u001c\t\u0005\u0003g\f90\u0004\u0002\u0002v*\u0019Q+!\u0004\n\t\u0005e\u0018Q\u001f\u0002\u0004+Jc\u0015aE4f]\u0016\u0014\u0018\r^3D%23%o\\7GS2,G\u0003BA-\u0003\u007fDqA!\u0001\u0017\u0001\u0004\u0011\u0019!\u0001\u0003gS2,\u0007\u0003BAp\u0005\u000bIAAa\u0002\u0002b\n!a)\u001b7f\u0003m\u0011W/\u001b7e)J,8\u000f^'b]\u0006<WM\u001d)be\u0006lW\r^3sgRA!Q\u0002B\n\u0005;\u0011y\u0002E\u0002S\u0005\u001fI1A!\u0005T\u0005y\u0019UM\u001d;QCRDGK];ti6\u000bg.Y4feB\u000b'/Y7fi\u0016\u00148\u000fC\u0004\u0003\u0016]\u0001\rAa\u0006\u0002\u0015Q\u0014Xo\u001d;Ti>\u0014X\r\u0005\u0003\u0002\b\te\u0011\u0002\u0002B\u000e\u0003\u0013\u0011\u0001bS3z'R|'/\u001a\u0005\b\u0003\u0013:\u0002\u0019AA&\u0011\u001d\t\u0019f\u0006a\u0001\u0003+\n\u0011CY;jY\u0012$&/^:u\u001b\u0006t\u0017mZ3s))\u0011)Ca\u000b\u0003.\t=\"\u0011\u0007\t\u0004%\n\u001d\u0012b\u0001B\u0015'\n\u0001\u0002,\u000e\u0019:)J,8\u000f^'b]\u0006<WM\u001d\u0005\b\u0003\u0003C\u0002\u0019AAB\u0011\u001d\tI\u0005\u0007a\u0001\u0003\u0017Bq!a\u0015\u0019\u0001\u0004\t)\u0006C\u0004\u0002,a\u0001\r!!\f\u0002AY\fG.\u001b3bi\u0016\u001cFo\u001c:f\u0007>tG/Y5ogB\u0013\u0018N^1uK.+\u0017p\u001d\u000b\u0007\u0003\u0017\u00129D!\u000f\t\u000f\u0005M\u0014\u00041\u0001\u0002v!9!1H\rA\u0002\t]\u0011\u0001C6fsN#xN]3")
public class ConfigSSLContextBuilder
implements SSLContextBuilder {
    private final LoggerFactory mkLogger;
    private final SSLConfigSettings info;
    private final KeyManagerFactoryWrapper keyManagerFactory;
    private final TrustManagerFactoryWrapper trustManagerFactory;
    private final NoDepsLogger logger;

    public NoDepsLogger logger() {
        return this.logger;
    }

    @Override
    public SSLContext build() {
        Option<Seq<CRL>> revocationLists = this.certificateRevocationList(this.info);
        Nil$ keyManagers = this.info.keyManagerConfig().keyStoreConfigs().nonEmpty() ? (Seq)package$.MODULE$.Seq().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new CompositeX509KeyManager[]{this.buildCompositeKeyManager(this.info.keyManagerConfig(), this.info.debug())})) : package$.MODULE$.Nil();
        Nil$ trustManagers = this.info.trustManagerConfig().trustStoreConfigs().nonEmpty() ? (Seq)package$.MODULE$.Seq().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new CompositeX509TrustManager[]{this.buildCompositeTrustManager(this.info.trustManagerConfig(), BoxesRunTime.unboxToBoolean(this.info.checkRevocation().getOrElse(() -> false)), revocationLists, this.info.debug())})) : package$.MODULE$.Nil();
        SSLContext context = this.buildSSLContext(this.info.protocol(), keyManagers, trustManagers, this.info.secureRandom());
        return this.info.debug().enabled() ? new TracingSSLContext(context, this.info.debug(), this.mkLogger) : context;
    }

    public SSLContext buildSSLContext(String protocol, Seq<KeyManager> keyManagers, Seq<TrustManager> trustManagers, Option<SecureRandom> secureRandom) {
        SimpleSSLContextBuilder builder = new SimpleSSLContextBuilder(protocol, keyManagers, trustManagers, secureRandom);
        return builder.build();
    }

    public CompositeX509KeyManager buildCompositeKeyManager(KeyManagerConfig keyManagerConfig, SSLDebugConfig debug) {
        Seq keyManagers = (Seq)keyManagerConfig.keyStoreConfigs().map((Function1<KeyStoreConfig, X509KeyManager> & Serializable)ksc -> this.buildKeyManager((KeyStoreConfig)ksc, debug));
        return new CompositeX509KeyManager(this.mkLogger, keyManagers);
    }

    public CompositeX509TrustManager buildCompositeTrustManager(TrustManagerConfig trustManagerInfo, boolean revocationEnabled, Option<Seq<CRL>> revocationLists, SSLDebugConfig debug) {
        Seq trustManagers = (Seq)trustManagerInfo.trustStoreConfigs().map((Function1<TrustStoreConfig, X509TrustManager> & Serializable)tsc -> this.buildTrustManager((TrustStoreConfig)tsc, revocationEnabled, revocationLists, debug));
        return new CompositeX509TrustManager(this.mkLogger, trustManagers);
    }

    public KeyStoreBuilder keyStoreBuilder(KeyStoreConfig ksc) {
        Option password = ksc.password().map((Function1<String, char[]> & Serializable)x$1 -> x$1.toCharArray());
        return (KeyStoreBuilder)ksc.filePath().map((Function1<String, KeyStoreBuilder> & Serializable)f -> ksc.isFileOnClasspath() ? this.fileOnClasspathBuilder(ksc.storeType(), (String)f, password) : this.fileBuilder(ksc.storeType(), (String)f, password)).getOrElse((Function0<KeyStoreBuilder> & Serializable)() -> {
            String data = (String)ksc.data().getOrElse((Function0<Nothing$> & Serializable)() -> {
                throw new IllegalStateException("No keystore builder found!");
            });
            return this.stringBuilder(data);
        });
    }

    public KeyStoreBuilder trustStoreBuilder(TrustStoreConfig tsc) {
        return (KeyStoreBuilder)tsc.filePath().map((Function1<String, KeyStoreBuilder> & Serializable)f -> {
            Option<char[]> password = tsc.password().map((Function1<String, char[]> & Serializable)x$2 -> x$2.toCharArray());
            return tsc.isFileOnClasspath() ? this.fileOnClasspathBuilder(tsc.storeType(), (String)f, password) : this.fileBuilder(tsc.storeType(), (String)f, password);
        }).getOrElse((Function0<KeyStoreBuilder> & Serializable)() -> {
            String data = (String)tsc.data().getOrElse((Function0<Nothing$> & Serializable)() -> {
                throw new IllegalStateException("No truststore builder found!");
            });
            return this.stringBuilder(data);
        });
    }

    public KeyStoreBuilder fileBuilder(String storeType, String filePath, Option<char[]> password) {
        return new FileBasedKeyStoreBuilder(storeType, filePath, password);
    }

    public KeyStoreBuilder fileOnClasspathBuilder(String storeType, String filePath, Option<char[]> password) {
        return new FileOnClasspathBasedKeyStoreBuilder(storeType, filePath, password);
    }

    public KeyStoreBuilder stringBuilder(String data) {
        return new StringBasedKeyStoreBuilder(data);
    }

    public boolean warnOnPKCS12EmptyPasswordBug(KeyStoreConfig ksc) {
        return ksc.storeType().equalsIgnoreCase("pkcs12") && !ksc.password().exists((Function1<String, Object> & Serializable)x$3 -> BoxesRunTime.boxToBoolean(ConfigSSLContextBuilder.$anonfun$warnOnPKCS12EmptyPasswordBug$1(x$3)));
    }

    public X509KeyManager buildKeyManager(KeyStoreConfig ksc, SSLDebugConfig debug) {
        KeyStore keyStore;
        try {
            keyStore = this.keyStoreBuilder(ksc).build();
        }
        catch (BadPaddingException bpe) {
            throw new SecurityException("Mac verify error: invalid password?", bpe);
        }
        KeyStore keyStore2 = keyStore;
        if (!this.validateStoreContainsPrivateKeys(ksc, keyStore2)) {
            this.logger().warn(new StringBuilder(76).append("Client authentication is not possible as there are no private keys found in ").append(ksc.filePath()).toString());
        }
        KeyManagerFactoryWrapper factory2 = this.keyManagerFactory;
        try {
            Option password = ksc.password().map((Function1<String, char[]> & Serializable)x$4 -> x$4.toCharArray());
            factory2.init(keyStore2, (char[])password.orNull($less$colon$less$.MODULE$.refl()));
        }
        catch (UnrecoverableKeyException e) {
            this.logger().error(new StringBuilder(30).append("Unrecoverable key in keystore ").append(ksc).toString());
            throw new IllegalStateException(e);
        }
        KeyManager[] keyManagers = factory2.getKeyManagers();
        if (keyManagers == null) {
            String msg = new StringBuilder(45).append("Cannot create key manager with configuration ").append(ksc).toString();
            throw new IllegalStateException(msg);
        }
        X509ExtendedKeyManager keyManager = (X509ExtendedKeyManager)ArrayOps$.MODULE$.head$extension(Predef$.MODULE$.refArrayOps(keyManagers));
        return new TracingX509ExtendedKeyManager((Function0<X509ExtendedKeyManager> & Serializable)() -> keyManager, debug, this.mkLogger);
    }

    public Option<Seq<CRL>> certificateRevocationList(SSLConfigSettings sslConfig) {
        return sslConfig.revocationLists().map((Function1<Seq, Seq> & Serializable)urls2 -> (Seq)urls2.map((Function1<URL, CRL> & Serializable)url2 -> this.generateCRLFromURL((URL)url2)));
    }

    /*
     * WARNING - void declaration
     */
    public CRL generateCRL(InputStream inputStream2) {
        void var3_3;
        CertificateFactory cf = CertificateFactory.getInstance("X509");
        X509CRL crl = (X509CRL)cf.generateCRL(inputStream2);
        return var3_3;
    }

    public CRL generateCRLFromURL(URL url2) {
        CRL cRL;
        URLConnection connection = url2.openConnection();
        connection.setDoInput(true);
        connection.setUseCaches(false);
        try (DataInputStream inStream = new DataInputStream(connection.getInputStream());){
            cRL = this.generateCRL(inStream);
        }
        return cRL;
    }

    public CRL generateCRLFromFile(File file) {
        CRL cRL;
        BufferedInputStream fileStream = new BufferedInputStream(Files.newInputStream(file.toPath(), new OpenOption[0]));
        try (DataInputStream inStream = new DataInputStream(fileStream);){
            cRL = this.generateCRL(inStream);
        }
        return cRL;
    }

    public CertPathTrustManagerParameters buildTrustManagerParameters(KeyStore trustStore, boolean revocationEnabled, Option<Seq<CRL>> revocationLists) {
        X509CertSelector certSelect = new X509CertSelector();
        PKIXBuilderParameters pkixParameters = new PKIXBuilderParameters(trustStore, (CertSelector)certSelect);
        pkixParameters.setRevocationEnabled(revocationEnabled);
        revocationLists.map((Function1<Seq, Object> & Serializable)crlList -> {
            pkixParameters.addCertStore(CertStore.getInstance("Collection", new CollectionCertStoreParameters(JavaConverters$.MODULE$.asJavaCollectionConverter(crlList).asJavaCollection())));
            return BoxedUnit.UNIT;
        });
        return new CertPathTrustManagerParameters(pkixParameters);
    }

    public X509TrustManager buildTrustManager(TrustStoreConfig tsc, boolean revocationEnabled, Option<Seq<CRL>> revocationLists, SSLDebugConfig debug) {
        TrustManagerFactoryWrapper factory2 = this.trustManagerFactory;
        KeyStore trustStore = this.trustStoreBuilder(tsc).build();
        CertPathTrustManagerParameters trustManagerParameters = this.buildTrustManagerParameters(trustStore, revocationEnabled, revocationLists);
        factory2.init(trustManagerParameters);
        TrustManager[] trustManagers = factory2.getTrustManagers();
        if (trustManagers == null) {
            String msg = new StringBuilder(47).append("Cannot create trust manager with configuration ").append(tsc).toString();
            throw new IllegalStateException(msg);
        }
        X509ExtendedTrustManager manager = (X509ExtendedTrustManager)ArrayOps$.MODULE$.head$extension(Predef$.MODULE$.refArrayOps(trustManagers));
        return new TracingX509ExtendedTrustManager((Function0<X509ExtendedTrustManager> & Serializable)() -> manager, debug, this.mkLogger);
    }

    public boolean validateStoreContainsPrivateKeys(KeyStoreConfig ksc, KeyStore keyStore) {
        char[] password = (char[])ksc.password().map((Function1<String, char[]> & Serializable)x$5 -> x$5.toCharArray()).orNull($less$colon$less$.MODULE$.refl());
        BooleanRef containsPrivateKeys = BooleanRef.create(false);
        ((IterableOnceOps)JavaConverters$.MODULE$.enumerationAsScalaIteratorConverter(keyStore.aliases()).asScala()).foreach((Function1<String, Object> & Serializable)keyAlias -> {
            ConfigSSLContextBuilder.$anonfun$validateStoreContainsPrivateKeys$2(this, keyStore, password, containsPrivateKeys, keyAlias);
            return BoxedUnit.UNIT;
        });
        return containsPrivateKeys.elem;
    }

    public static final /* synthetic */ boolean $anonfun$warnOnPKCS12EmptyPasswordBug$1(String x$3) {
        return !x$3.isEmpty();
    }

    public static final /* synthetic */ void $anonfun$validateStoreContainsPrivateKeys$2(ConfigSSLContextBuilder $this, KeyStore keyStore$1, char[] password$2, BooleanRef containsPrivateKeys$1, String keyAlias) {
        Key key = keyStore$1.getKey(keyAlias, password$2);
        Key key2 = key;
        if (key2 instanceof PrivateKey) {
            $this.logger().debug(new StringBuilder(62).append("validateStoreContainsPrivateKeys: private key found for alias ").append(keyAlias).toString());
            containsPrivateKeys$1.elem = true;
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        } else {
            String msg = new StringBuilder(110).append("validateStoreContainsPrivateKeys: No private key found for alias ").append(keyAlias).append(", it cannot be used for client authentication").toString();
            $this.logger().warn(msg);
            BoxedUnit boxedUnit = BoxedUnit.UNIT;
        }
    }

    public ConfigSSLContextBuilder(LoggerFactory mkLogger, SSLConfigSettings info, KeyManagerFactoryWrapper keyManagerFactory, TrustManagerFactoryWrapper trustManagerFactory) {
        this.mkLogger = mkLogger;
        this.info = info;
        this.keyManagerFactory = keyManagerFactory;
        this.trustManagerFactory = trustManagerFactory;
        this.logger = mkLogger.apply(this.getClass());
    }
}

