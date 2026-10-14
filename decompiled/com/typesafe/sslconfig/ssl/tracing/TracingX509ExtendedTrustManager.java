/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl.tracing;

import com.typesafe.sslconfig.ssl.SSLDebugConfig;
import com.typesafe.sslconfig.ssl.tracing.TraceLogger;
import com.typesafe.sslconfig.util.LoggerFactory;
import java.io.Serializable;
import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import scala.Function0;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0005\rf!B\u0007\u000f\u0001IA\u0002\u0002\u0003\u0014\u0001\u0005\u0003%\u000b\u0011\u0002\u0015\t\u00119\u0002!\u0011!Q\u0001\n=B\u0001b\r\u0001\u0003\u0002\u0003\u0006Y\u0001\u000e\u0005\u0006u\u0001!\ta\u000f\u0005\u0006\u0003\u0002!\tE\u0011\u0005\b\u0003/\u0001A\u0011IA\r\u0011\u0019\t\u0005\u0001\"\u0011\u0002.!9\u0011q\u0003\u0001\u0005B\u0005%\u0003BB!\u0001\t\u0003\ni\u0006C\u0004\u0002\u0018\u0001!\t%a\u001c\t\u000f\u0005\u0005\u0005\u0001\"\u0011\u0002\u0004\"9\u0011Q\u0011\u0001\u0005B\u0005\u001d%a\b+sC\u000eLgn\u001a-6ae*\u0005\u0010^3oI\u0016$GK];ti6\u000bg.Y4fe*\u0011q\u0002E\u0001\biJ\f7-\u001b8h\u0015\t\t\"#A\u0002tg2T!a\u0005\u000b\u0002\u0013M\u001cHnY8oM&<'BA\u000b\u0017\u0003!!\u0018\u0010]3tC\u001a,'\"A\f\u0002\u0007\r|WnE\u0002\u00013\t\u0002\"A\u0007\u0011\u000e\u0003mQ!!\u0005\u000f\u000b\u0005uq\u0012a\u00018fi*\tq$A\u0003kCZ\f\u00070\u0003\u0002\"7\tA\u0002,\u000e\u0019:\u000bb$XM\u001c3fIR\u0013Xo\u001d;NC:\fw-\u001a:\u0011\u0005\r\"S\"\u0001\b\n\u0005\u0015r!a\u0003+sC\u000e,Gj\\4hKJ\f\u0001b];qa2LWM]\u0002\u0001!\rIC&G\u0007\u0002U)\t1&A\u0003tG\u0006d\u0017-\u0003\u0002.U\tAAHY=oC6,g(A\u0003eK\n,x\r\u0005\u00021c5\t\u0001#\u0003\u00023!\tq1k\u0015'EK\n,xmQ8oM&<\u0017!\u00047pO\u001e,'OR1di>\u0014\u0018\u0010\u0005\u00026q5\taG\u0003\u00028%\u0005!Q\u000f^5m\u0013\tIdGA\u0007M_\u001e<WM\u001d$bGR|'/_\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0007qz\u0004\t\u0006\u0002>}A\u00111\u0005\u0001\u0005\u0006g\u0011\u0001\u001d\u0001\u000e\u0005\u0007M\u0011!\t\u0019\u0001\u0015\t\u000b9\"\u0001\u0019A\u0018\u0002%\rDWmY6DY&,g\u000e\u001e+skN$X\r\u001a\u000b\u0005\u0007\u001a+&\r\u0005\u0002*\t&\u0011QI\u000b\u0002\u0005+:LG\u000fC\u0003H\u000b\u0001\u0007\u0001*A\u0003dQ\u0006Lg\u000eE\u0002*\u0013.K!A\u0013\u0016\u0003\u000b\u0005\u0013(/Y=\u0011\u00051\u001bV\"A'\u000b\u00059{\u0015\u0001B2feRT!\u0001U)\u0002\u0011M,7-\u001e:jifT\u0011AU\u0001\u0005U\u00064\u0018-\u0003\u0002U\u001b\ny\u0001,\u000e\u0019:\u0007\u0016\u0014H/\u001b4jG\u0006$X\rC\u0003W\u000b\u0001\u0007q+\u0001\u0005bkRDG+\u001f9f!\tAvL\u0004\u0002Z;B\u0011!LK\u0007\u00027*\u0011AlJ\u0001\u0007yI|w\u000e\u001e \n\u0005yS\u0013A\u0002)sK\u0012,g-\u0003\u0002aC\n11\u000b\u001e:j]\u001eT!A\u0018\u0016\t\u000b\r,\u0001\u0019\u00013\u0002\rM|7m[3u!\t)w-D\u0001g\u0015\ti\u0012+\u0003\u0002iM\n11k\\2lKRD3!\u00026q!\rI3.\\\u0005\u0003Y*\u0012a\u0001\u001e5s_^\u001c\bC\u0001'o\u0013\tyWJ\u0001\u000bDKJ$\u0018NZ5dCR,W\t_2faRLwN\\\u0019\u0006=]\u000b\u0018QC\u0019\u0007GI4\u00181B<\u0016\u0005M$X#A,\u0005\u000bU<#\u0019\u0001>\u0003\u0003QK!a\u001e=\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00132\u0015\tI(&\u0001\u0004uQJ|wo]\t\u0003wz\u0004\"!\u000b?\n\u0005uT#a\u0002(pi\"Lgn\u001a\t\u0004\u007f\u0006\u0015abA\u0015\u0002\u0002%\u0019\u00111\u0001\u0016\u0002\u000fA\f7m[1hK&!\u0011qAA\u0005\u0005%!\u0006N]8xC\ndWMC\u0002\u0002\u0004)\n\u0004bIA\u0007\u0003\u001f\t\t\"\u001f\b\u0004S\u0005=\u0011BA=+c\u0015\u0011\u0013FKA\n\u0005\u0015\u00198-\u00197bc\t1S.\u0001\ndQ\u0016\u001c7nU3sm\u0016\u0014HK];ti\u0016$GcB\"\u0002\u001c\u0005u\u0011q\u0004\u0005\u0006\u000f\u001a\u0001\r\u0001\u0013\u0005\u0006-\u001a\u0001\ra\u0016\u0005\u0006G\u001a\u0001\r\u0001\u001a\u0015\u0005\r)\f\u0019#\r\u0004\u001f/\u0006\u0015\u00121F\u0019\u0007GI4\u0018qE<2\u0011\r\ni!a\u0004\u0002*e\fTAI\u0015+\u0003'\t$AJ7\u0015\u000f\r\u000by#!\r\u00024!)qi\u0002a\u0001\u0011\")ak\u0002a\u0001/\"9\u0011QG\u0004A\u0002\u0005]\u0012AB3oO&tW\rE\u0002\u001b\u0003sI1!a\u000f\u001c\u0005%\u00196\u000bT#oO&tW\r\u000b\u0003\bU\u0006}\u0012G\u0002\u0010X\u0003\u0003\n9%\r\u0004$eZ\f\u0019e^\u0019\tG\u00055\u0011qBA#sF*!%\u000b\u0016\u0002\u0014E\u0012a%\u001c\u000b\b\u0007\u0006-\u0013QJA(\u0011\u00159\u0005\u00021\u0001I\u0011\u00151\u0006\u00021\u0001X\u0011\u001d\t)\u0004\u0003a\u0001\u0003oAC\u0001\u00036\u0002TE2adVA+\u00037\nda\t:w\u0003/:\u0018\u0007C\u0012\u0002\u000e\u0005=\u0011\u0011L=2\u000b\tJ#&a\u00052\u0005\u0019jG#B\"\u0002`\u0005\u0005\u0004\"B$\n\u0001\u0004A\u0005\"\u0002,\n\u0001\u00049\u0006\u0006B\u0005k\u0003K\ndAH,\u0002h\u00055\u0014GB\u0012sm\u0006%t/\r\u0005$\u0003\u001b\ty!a\u001bzc\u0015\u0011\u0013FKA\nc\t1S\u000eF\u0003D\u0003c\n\u0019\bC\u0003H\u0015\u0001\u0007\u0001\nC\u0003W\u0015\u0001\u0007q\u000b\u000b\u0003\u000bU\u0006]\u0014G\u0002\u0010X\u0003s\ny(\r\u0004$eZ\fYh^\u0019\tG\u00055\u0011qBA?sF*!%\u000b\u0016\u0002\u0014E\u0012a%\\\u0001\u0013O\u0016$\u0018iY2faR,G-S:tk\u0016\u00148\u000fF\u0001I\u00031I7\u000fT8h\u000b:\f'\r\\3e)\u0019\tI)a$\u0002\u0014B\u0019\u0011&a#\n\u0007\u00055%FA\u0004C_>dW-\u00198\t\r\u0005EE\u00021\u0001X\u0003)iW\r\u001e5pI:\u000bW.\u001a\u0005\b\u0003+c\u0001\u0019AAL\u0003)\u0001\u0018M]1nKR,'o\u001d\t\u00071\u0006eu+!(\n\u0007\u0005m\u0015MA\u0002NCB\u00042!KAP\u0013\r\t\tK\u000b\u0002\u0004\u0003:L\b")
public class TracingX509ExtendedTrustManager
extends X509ExtendedTrustManager
implements TraceLogger {
    private final Function0<X509ExtendedTrustManager> supplier;
    private final SSLDebugConfig debug;
    private final LoggerFactory loggerFactory;

    @Override
    public <T, E> T tracer(String methodName, Map<String, Object> parameters, Function0<T> function, LoggerFactory loggerFactory) {
        return (T)TraceLogger.tracer$(this, methodName, parameters, function, loggerFactory);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        this.tracer("checkClientTrusted", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("chain"), chain), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("authType"), authType), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("socket"), socket)})), () -> $this.supplier.apply().checkClientTrusted(chain, authType, socket), this.loggerFactory);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        this.tracer("checkServerTrusted", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("chain"), chain), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("authType"), authType), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("socket"), socket)})), () -> $this.supplier.apply().checkServerTrusted(chain, authType, socket), this.loggerFactory);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
        this.tracer("checkClientTrusted", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("chain"), chain), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("authType"), authType), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("engine"), engine)})), () -> $this.supplier.apply().checkClientTrusted(chain, authType, engine), this.loggerFactory);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
        this.tracer("checkServerTrusted", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("chain"), chain), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("authType"), authType), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("engine"), engine)})), () -> $this.supplier.apply().checkServerTrusted(chain, authType, engine), this.loggerFactory);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        this.tracer("checkClientTrusted", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("chain"), chain), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("authType"), authType)})), () -> ((X509TrustManager)$this.supplier.apply()).checkClientTrusted(chain, authType), this.loggerFactory);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        this.tracer("checkServerTrusted", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("chain"), chain), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("authType"), authType)})), () -> ((X509TrustManager)$this.supplier.apply()).checkServerTrusted(chain, authType), this.loggerFactory);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return (X509Certificate[])this.tracer("getAcceptedIssuers", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<X509Certificate[]> & Serializable)() -> ((X509TrustManager)$this.supplier.apply()).getAcceptedIssuers(), this.loggerFactory);
    }

    @Override
    public boolean isLogEnabled(String methodName, Map<String, Object> parameters) {
        return this.debug.all() || this.debug.trustmanager();
    }

    public TracingX509ExtendedTrustManager(Function0<X509ExtendedTrustManager> supplier, SSLDebugConfig debug, LoggerFactory loggerFactory) {
        this.supplier = supplier;
        this.debug = debug;
        this.loggerFactory = loggerFactory;
        TraceLogger.$init$(this);
    }
}

