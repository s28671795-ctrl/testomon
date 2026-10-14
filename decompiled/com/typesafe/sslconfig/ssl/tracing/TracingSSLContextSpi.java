/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl.tracing;

import com.typesafe.sslconfig.ssl.SSLDebugConfig;
import com.typesafe.sslconfig.ssl.tracing.TraceLogger;
import com.typesafe.sslconfig.ssl.tracing.TracingSSLEngine;
import com.typesafe.sslconfig.ssl.tracing.TracingSSLServerSocketFactory;
import com.typesafe.sslconfig.ssl.tracing.TracingSSLSocketFactory;
import com.typesafe.sslconfig.util.LoggerFactory;
import java.io.Serializable;
import java.security.SecureRandom;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLContextSpi;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import scala.Function0;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0005Eb!B\u0007\u000f\u00019A\u0002\u0002\u0003\u0014\u0001\u0005\u0003%\u000b\u0011\u0002\u0015\t\u0011E\u0002!\u0011!Q\u0001\nIB\u0001B\u000e\u0001\u0003\u0002\u0003\u0006Ya\u000e\u0005\u0006{\u0001!\tA\u0010\u0005\u0006\t\u0002!\t!\u0012\u0005\u0006=\u0002!\te\u0018\u0005\u0006w\u0002!\t\u0005 \u0005\b\u0003\u0003\u0001A\u0011IA\u0002\u0011\u001d\tY\u0001\u0001C!\u0003\u001bAq!a\u0003\u0001\t\u0003\n)\u0002C\u0004\u0002&\u0001!\t%a\n\t\u000f\u0005=\u0002\u0001\"\u0011\u0002(\t!BK]1dS:<7k\u0015'D_:$X\r\u001f;Ta&T!a\u0004\t\u0002\u000fQ\u0014\u0018mY5oO*\u0011\u0011CE\u0001\u0004gNd'BA\n\u0015\u0003%\u00198\u000f\\2p]\u001aLwM\u0003\u0002\u0016-\u0005AA/\u001f9fg\u00064WMC\u0001\u0018\u0003\r\u0019w.\\\n\u0004\u0001e\u0011\u0003C\u0001\u000e!\u001b\u0005Y\"BA\t\u001d\u0015\tib$A\u0002oKRT\u0011aH\u0001\u0006U\u00064\u0018\r_\u0005\u0003Cm\u0011QbU*M\u0007>tG/\u001a=u'BL\u0007CA\u0012%\u001b\u0005q\u0011BA\u0013\u000f\u0005-!&/Y2f\u0019><w-\u001a:\u0002\u0007\r$\bp\u0001\u0001\u0011\u0007%bc&D\u0001+\u0015\u0005Y\u0013!B:dC2\f\u0017BA\u0017+\u0005!a$-\u001f8b[\u0016t\u0004C\u0001\u000e0\u0013\t\u00014D\u0001\u0006T'2\u001buN\u001c;fqR\fQ\u0001Z3ck\u001e\u0004\"a\r\u001b\u000e\u0003AI!!\u000e\t\u0003\u001dM\u001bF\nR3ck\u001e\u001cuN\u001c4jO\u0006iAn\\4hKJ4\u0015m\u0019;pef\u0004\"\u0001O\u001e\u000e\u0003eR!A\u000f\n\u0002\tU$\u0018\u000e\\\u0005\u0003ye\u0012Q\u0002T8hO\u0016\u0014h)Y2u_JL\u0018A\u0002\u001fj]&$h\bF\u0002@\u0005\u000e#\"\u0001Q!\u0011\u0005\r\u0002\u0001\"\u0002\u001c\u0005\u0001\b9\u0004B\u0002\u0014\u0005\t\u0003\u0007\u0001\u0006C\u00032\t\u0001\u0007!'\u0001\u0007jg2{w-\u00128bE2,G\rF\u0002G\u0013Z\u0003\"!K$\n\u0005!S#a\u0002\"p_2,\u0017M\u001c\u0005\u0006\u0015\u0016\u0001\raS\u0001\u000b[\u0016$\bn\u001c3OC6,\u0007C\u0001'T\u001d\ti\u0015\u000b\u0005\u0002OU5\tqJ\u0003\u0002QO\u00051AH]8pizJ!A\u0015\u0016\u0002\rA\u0013X\rZ3g\u0013\t!VK\u0001\u0004TiJLgn\u001a\u0006\u0003%*BQaV\u0003A\u0002a\u000b!\u0002]1sC6,G/\u001a:t!\u0011a\u0015lS.\n\u0005i+&aA'baB\u0011\u0011\u0006X\u0005\u0003;*\u00121!\u00118z\u0003))gnZ5oK&s\u0017\u000e\u001e\u000b\u0005A\u000e\\\u0017\u000f\u0005\u0002*C&\u0011!M\u000b\u0002\u0005+:LG\u000fC\u0003e\r\u0001\u0007Q-A\u0006lKfl\u0015M\\1hKJ\u001c\bcA\u0015gQ&\u0011qM\u000b\u0002\u0006\u0003J\u0014\u0018-\u001f\t\u00035%L!A[\u000e\u0003\u0015-+\u00170T1oC\u001e,'\u000fC\u0003m\r\u0001\u0007Q.A\u0007ueV\u001cH/T1oC\u001e,'o\u001d\t\u0004S\u0019t\u0007C\u0001\u000ep\u0013\t\u00018D\u0001\u0007UeV\u001cH/T1oC\u001e,'\u000fC\u0003s\r\u0001\u00071/\u0001\u0007tK\u000e,(/\u001a*b]\u0012|W\u000e\u0005\u0002us6\tQO\u0003\u0002wo\u0006A1/Z2ve&$\u0018PC\u0001y\u0003\u0011Q\u0017M^1\n\u0005i,(\u0001D*fGV\u0014XMU1oI>l\u0017AF3oO&tWmR3u'>\u001c7.\u001a;GC\u000e$xN]=\u0015\u0003u\u0004\"A\u0007@\n\u0005}\\\"\u0001E*T\u0019N{7m[3u\r\u0006\u001cGo\u001c:z\u0003q)gnZ5oK\u001e+GoU3sm\u0016\u00148k\\2lKR4\u0015m\u0019;pef$\"!!\u0002\u0011\u0007i\t9!C\u0002\u0002\nm\u0011acU*M'\u0016\u0014h/\u001a:T_\u000e\\W\r\u001e$bGR|'/_\u0001\u0016K:<\u0017N\\3De\u0016\fG/Z*T\u0019\u0016sw-\u001b8f)\t\ty\u0001E\u0002\u001b\u0003#I1!a\u0005\u001c\u0005%\u00196\u000bT#oO&tW\r\u0006\u0004\u0002\u0010\u0005]\u00111\u0004\u0005\u0007\u00033Q\u0001\u0019A&\u0002\t!|7\u000f\u001e\u0005\b\u0003;Q\u0001\u0019AA\u0010\u0003\u0011\u0001xN\u001d;\u0011\u0007%\n\t#C\u0002\u0002$)\u00121!\u00138u\u0003u)gnZ5oK\u001e+GoU3sm\u0016\u00148+Z:tS>t7i\u001c8uKb$HCAA\u0015!\rQ\u00121F\u0005\u0004\u0003[Y\"!E*T\u0019N+7o]5p]\u000e{g\u000e^3yi\u0006iRM\\4j]\u0016<U\r^\"mS\u0016tGoU3tg&|gnQ8oi\u0016DH\u000f")
public class TracingSSLContextSpi
extends SSLContextSpi
implements TraceLogger {
    private final Function0<SSLContext> ctx;
    private final SSLDebugConfig debug;
    private final LoggerFactory loggerFactory;

    @Override
    public <T, E> T tracer(String methodName, Map<String, Object> parameters, Function0<T> function, LoggerFactory loggerFactory) {
        return (T)TraceLogger.tracer$(this, methodName, parameters, function, loggerFactory);
    }

    @Override
    public boolean isLogEnabled(String methodName, Map<String, Object> parameters) {
        return this.debug.all() || this.debug.sslctx();
    }

    @Override
    public void engineInit(KeyManager[] keyManagers, TrustManager[] trustManagers, SecureRandom secureRandom) {
        this.tracer("init", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("keyManagers"), keyManagers), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("trustManagers"), trustManagers), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("secureRandom"), secureRandom)})), () -> $this.ctx.apply().init(keyManagers, trustManagers, secureRandom), this.loggerFactory);
    }

    @Override
    public SSLSocketFactory engineGetSocketFactory() {
        return (SSLSocketFactory)this.tracer("getSocketFactory", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<TracingSSLSocketFactory> & Serializable)() -> new TracingSSLSocketFactory($this.ctx.apply().getSocketFactory(), $this.debug, $this.loggerFactory), this.loggerFactory);
    }

    @Override
    public SSLServerSocketFactory engineGetServerSocketFactory() {
        return (SSLServerSocketFactory)this.tracer("getServerSocketFactory", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<TracingSSLServerSocketFactory> & Serializable)() -> new TracingSSLServerSocketFactory((Function0<SSLServerSocketFactory> & Serializable)() -> $this.ctx.apply().getServerSocketFactory(), $this.debug, $this.loggerFactory), this.loggerFactory);
    }

    @Override
    public SSLEngine engineCreateSSLEngine() {
        return (SSLEngine)this.tracer("createSSLEngine", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<TracingSSLEngine> & Serializable)() -> {
            SSLEngine engine = $this.ctx.apply().createSSLEngine();
            return new TracingSSLEngine((Function0<SSLEngine> & Serializable)() -> engine, $this.debug, $this.loggerFactory);
        }, this.loggerFactory);
    }

    @Override
    public SSLEngine engineCreateSSLEngine(String host2, int port) {
        return (SSLEngine)this.tracer("createSSLEngine", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("host"), host2), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("port"), BoxesRunTime.boxToInteger(port))})), (Function0<TracingSSLEngine> & Serializable)() -> {
            SSLEngine engine = $this.ctx.apply().createSSLEngine(host2, port);
            return new TracingSSLEngine((Function0<SSLEngine> & Serializable)() -> engine, $this.debug, $this.loggerFactory);
        }, this.loggerFactory);
    }

    @Override
    public SSLSessionContext engineGetServerSessionContext() {
        return (SSLSessionContext)this.tracer("getServerSessionContext", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<SSLSessionContext> & Serializable)() -> $this.ctx.apply().getServerSessionContext(), this.loggerFactory);
    }

    @Override
    public SSLSessionContext engineGetClientSessionContext() {
        return (SSLSessionContext)this.tracer("getClientSessionContext", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<SSLSessionContext> & Serializable)() -> $this.ctx.apply().getClientSessionContext(), this.loggerFactory);
    }

    public TracingSSLContextSpi(Function0<SSLContext> ctx, SSLDebugConfig debug, LoggerFactory loggerFactory) {
        this.ctx = ctx;
        this.debug = debug;
        this.loggerFactory = loggerFactory;
        TraceLogger.$init$(this);
    }
}

