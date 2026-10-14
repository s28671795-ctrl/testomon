/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.CompositeCertificateException;
import com.typesafe.sslconfig.ssl.package$;
import com.typesafe.sslconfig.util.LoggerFactory;
import com.typesafe.sslconfig.util.NoDepsLogger;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.cert.CertPathBuilderException;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.ArrayBuffer;
import scala.collection.mutable.ArrayBuffer$;
import scala.collection.mutable.Buffer;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BooleanRef;
import scala.runtime.BoxedUnit;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005e4Aa\u0003\u0007\u0001+!Aq\u0005\u0001B\u0001B\u0003%\u0001\u0006\u0003\u0005/\u0001\t\u0005\t\u0015!\u00030\u0011\u0015i\u0004\u0001\"\u0001?\u0011\u001d\u0019\u0005A1A\u0005\n\u0011Ca\u0001\u0013\u0001!\u0002\u0013)\u0005\"B%\u0001\t\u0003Q\u0005\"B,\u0001\t\u0003A\u0006\"\u00025\u0001\t\u0003I\u0007\"\u00027\u0001\t\u0013i\u0007\"B<\u0001\t\u0003B(!G\"p[B|7/\u001b;f1V\u0002\u0014\b\u0016:vgRl\u0015M\\1hKJT!!\u0004\b\u0002\u0007M\u001cHN\u0003\u0002\u0010!\u0005I1o\u001d7d_:4\u0017n\u001a\u0006\u0003#I\t\u0001\u0002^=qKN\fg-\u001a\u0006\u0002'\u0005\u00191m\\7\u0004\u0001M\u0019\u0001A\u0006\u0010\u0011\u0005]aR\"\u0001\r\u000b\u0005eQ\u0012\u0001\u00027b]\u001eT\u0011aG\u0001\u0005U\u00064\u0018-\u0003\u0002\u001e1\t1qJ\u00196fGR\u0004\"aH\u0013\u000e\u0003\u0001R!!D\u0011\u000b\u0005\t\u001a\u0013a\u00018fi*\tA%A\u0003kCZ\f\u00070\u0003\u0002'A\t\u0001\u0002,\u000e\u0019:)J,8\u000f^'b]\u0006<WM]\u0001\t[.dunZ4feB\u0011\u0011\u0006L\u0007\u0002U)\u00111FD\u0001\u0005kRLG.\u0003\u0002.U\tiAj\\4hKJ4\u0015m\u0019;pef\fQ\u0002\u001e:vgRl\u0015M\\1hKJ\u001c\bc\u0001\u0019;=9\u0011\u0011g\u000e\b\u0003eUj\u0011a\r\u0006\u0003iQ\ta\u0001\u0010:p_Rt\u0014\"\u0001\u001c\u0002\u000bM\u001c\u0017\r\\1\n\u0005aJ\u0014a\u00029bG.\fw-\u001a\u0006\u0002m%\u00111\b\u0010\u0002\u0004'\u0016\f(B\u0001\u001d:\u0003\u0019a\u0014N\\5u}Q\u0019q(\u0011\"\u0011\u0005\u0001\u0003Q\"\u0001\u0007\t\u000b\u001d\u001a\u0001\u0019\u0001\u0015\t\u000b9\u001a\u0001\u0019A\u0018\u0002\r1|wmZ3s+\u0005)\u0005CA\u0015G\u0013\t9%F\u0001\u0007O_\u0012+\u0007o\u001d'pO\u001e,'/A\u0004m_\u001e<WM\u001d\u0011\u0002%\u001d,G/Q2dKB$X\rZ%tgV,'o\u001d\u000b\u0002\u0017B\u0019A*T(\u000e\u0003eJ!AT\u001d\u0003\u000b\u0005\u0013(/Y=\u0011\u0005A+V\"A)\u000b\u0005I\u001b\u0016\u0001B2feRT!\u0001\u0016\u000e\u0002\u0011M,7-\u001e:jifL!AV)\u0003\u001fa+\u0004'O\"feRLg-[2bi\u0016\f!c\u00195fG.\u001cE.[3oiR\u0013Xo\u001d;fIR\u0019\u0011\f\u00180\u0011\u00051S\u0016BA.:\u0005\u0011)f.\u001b;\t\u000bu;\u0001\u0019A&\u0002\u000b\rD\u0017-\u001b8\t\u000b};\u0001\u0019\u00011\u0002\u0011\u0005,H\u000f\u001b+za\u0016\u0004\"!Y3\u000f\u0005\t\u001c\u0007C\u0001\u001a:\u0013\t!\u0017(\u0001\u0004Qe\u0016$WMZ\u0005\u0003M\u001e\u0014aa\u0015;sS:<'B\u00013:\u0003I\u0019\u0007.Z2l'\u0016\u0014h/\u001a:UeV\u001cH/\u001a3\u0015\u0007eS7\u000eC\u0003^\u0011\u0001\u00071\nC\u0003`\u0011\u0001\u0007\u0001-A\txSRDGK];ti6\u000bg.Y4feN$\"A\u001c:\u0011\u0007ARt\u000e\u0005\u00021a&\u0011\u0011\u000f\u0010\u0002\n)\"\u0014xn^1cY\u0016DQa]\u0005A\u0002Q\fQA\u00197pG.\u0004B\u0001T;\u001f3&\u0011a/\u000f\u0002\n\rVt7\r^5p]F\n\u0001\u0002^8TiJLgn\u001a\u000b\u0002A\u0002")
public class CompositeX509TrustManager
implements X509TrustManager {
    private final Seq<X509TrustManager> trustManagers;
    private final NoDepsLogger logger;

    private NoDepsLogger logger() {
        return this.logger;
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        this.logger().debug("getAcceptedIssuers: ");
        ArrayBuffer certificates = (ArrayBuffer)ArrayBuffer$.MODULE$.apply((Seq)Nil$.MODULE$);
        Seq<Throwable> exceptionList = this.withTrustManagers((Function1<X509TrustManager, BoxedUnit>)(Function1<X509TrustManager, Object> & Serializable)trustManager -> {
            certificates.$plus$plus$eq(Predef$.MODULE$.wrapRefArray((Object[])trustManager.getAcceptedIssuers()));
            return BoxedUnit.UNIT;
        });
        if (!exceptionList.isEmpty()) {
            String msg = ((Throwable)exceptionList.apply(false)).getMessage();
            throw new CompositeCertificateException(msg, (Throwable[])exceptionList.toArray(ClassTag$.MODULE$.apply(Throwable.class)));
        }
        return (X509Certificate[])certificates.toArray(ClassTag$.MODULE$.apply(X509Certificate.class));
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
        this.logger().debug(new StringBuilder(28).append("checkClientTrusted: chain = ").append(package$.MODULE$.debugChain(chain)).toString());
        TrustAnchor anchor = new TrustAnchor(chain[chain.length - 1], null);
        BooleanRef trusted = BooleanRef.create(false);
        Seq<Throwable> exceptionList = this.withTrustManagers((Function1<X509TrustManager, BoxedUnit>)(Function1<X509TrustManager, Object> & Serializable)trustManager -> {
            CompositeX509TrustManager.$anonfun$checkClientTrusted$1(this, chain, authType, trusted, trustManager);
            return BoxedUnit.UNIT;
        });
        if (!trusted.elem) {
            String msg = "No trust manager was able to validate this certificate chain.";
            throw new CompositeCertificateException(msg, (Throwable[])exceptionList.toArray(ClassTag$.MODULE$.apply(Throwable.class)));
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
        this.logger().debug(new StringBuilder(41).append("checkServerTrusted: chain = ").append(package$.MODULE$.debugChain(chain)).append(", authType = ").append(authType).toString());
        BooleanRef trusted = BooleanRef.create(false);
        Seq<Throwable> exceptionList = this.withTrustManagers((Function1<X509TrustManager, BoxedUnit>)(Function1<X509TrustManager, Object> & Serializable)trustManager -> {
            CompositeX509TrustManager.$anonfun$checkServerTrusted$1(this, chain, authType, trusted, trustManager);
            return BoxedUnit.UNIT;
        });
        if (!trusted.elem) {
            String msg = new StringBuilder(80).append("No trust manager was able to validate this certificate chain: # of exceptions = ").append(exceptionList.size()).toString();
            throw new CompositeCertificateException(msg, (Throwable[])exceptionList.toArray(ClassTag$.MODULE$.apply(Throwable.class)));
        }
    }

    private Seq<Throwable> withTrustManagers(Function1<X509TrustManager, BoxedUnit> block) {
        ArrayBuffer exceptionList = (ArrayBuffer)ArrayBuffer$.MODULE$.apply((Seq)Nil$.MODULE$);
        this.trustManagers.foreach((Function1<X509TrustManager, Object> & Serializable)trustManager -> {
            Object object;
            try {
                object = block.apply((X509TrustManager)trustManager);
            }
            catch (Throwable throwable) {
                Option<Throwable> option;
                Buffer<Throwable> buffer;
                Throwable throwable2 = throwable;
                if (throwable2 instanceof CertPathBuilderException) {
                    CertPathBuilderException certPathBuilderException = (CertPathBuilderException)throwable2;
                    this.logger().debug(new StringBuilder(90).append("No path found to certificate: this usually means the CA is not in the trust store. Cause: ").append(certPathBuilderException).toString());
                    buffer = exceptionList.append(certPathBuilderException);
                } else if (throwable2 instanceof GeneralSecurityException) {
                    GeneralSecurityException generalSecurityException = (GeneralSecurityException)throwable2;
                    this.logger().debug(new StringBuilder(35).append("General security exception. Cause: ").append(generalSecurityException).toString());
                    buffer = exceptionList.append(generalSecurityException);
                } else if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                    Throwable e = option.get();
                    this.logger().debug(new StringBuilder(29).append("Unexpected exception! Cause: ").append(e).toString());
                    buffer = exceptionList.append(e);
                } else {
                    throw throwable;
                }
                object = buffer;
            }
            return object;
        });
        return exceptionList.toSeq();
    }

    public String toString() {
        return new StringBuilder(45).append("CompositeX509TrustManager(trustManagers = [").append(this.trustManagers).append("])").toString();
    }

    public static final /* synthetic */ void $anonfun$checkClientTrusted$1(CompositeX509TrustManager $this, X509Certificate[] chain$1, String authType$1, BooleanRef trusted$1, X509TrustManager trustManager) {
        trustManager.checkClientTrusted(chain$1, authType$1);
        $this.logger().debug(new StringBuilder(52).append("checkClientTrusted: trustManager ").append(trustManager).append(" found a match for ").append(package$.MODULE$.debugChain(chain$1)).toString());
        trusted$1.elem = true;
    }

    public static final /* synthetic */ void $anonfun$checkServerTrusted$1(CompositeX509TrustManager $this, X509Certificate[] chain$2, String authType$2, BooleanRef trusted$2, X509TrustManager trustManager) {
        trustManager.checkServerTrusted(chain$2, authType$2);
        $this.logger().debug(new StringBuilder(68).append("checkServerTrusted: trustManager ").append(trustManager).append(" using authType ").append(authType$2).append(" found a match for ").append(package$.MODULE$.debugChain(chain$2).toSeq()).toString());
        trusted$2.elem = true;
    }

    public CompositeX509TrustManager(LoggerFactory mkLogger, Seq<X509TrustManager> trustManagers) {
        this.trustManagers = trustManagers;
        this.logger = mkLogger.apply(this.getClass());
    }
}

