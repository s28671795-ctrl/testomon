/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl.tracing;

import com.typesafe.sslconfig.ssl.SSLDebugConfig;
import com.typesafe.sslconfig.ssl.tracing.TraceLogger;
import com.typesafe.sslconfig.util.LoggerFactory;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
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

@ScalaSignature(bytes="\u0006\u0005m4Q\u0001D\u0007\u0001#]A\u0001\"\n\u0001\u0003\u0002\u0013\u0006Ia\n\u0005\t[\u0001\u0011\t\u0011)A\u0005]!A!\u0007\u0001B\u0001B\u0003-1\u0007C\u0003:\u0001\u0011\u0005!\bC\u0003A\u0001\u0011\u0005\u0013\tC\u0003A\u0001\u0011\u0005\u0013\nC\u0003A\u0001\u0011\u0005s\nC\u0003A\u0001\u0011\u00053\u000bC\u0003\\\u0001\u0011\u0005C\fC\u0003l\u0001\u0011\u0005C\fC\u0003m\u0001\u0011\u0005SNA\u000fUe\u0006\u001c\u0017N\\4T'2\u001bVM\u001d<feN{7m[3u\r\u0006\u001cGo\u001c:z\u0015\tqq\"A\u0004ue\u0006\u001c\u0017N\\4\u000b\u0005A\t\u0012aA:tY*\u0011!cE\u0001\ngNd7m\u001c8gS\u001eT!\u0001F\u000b\u0002\u0011QL\b/Z:bM\u0016T\u0011AF\u0001\u0004G>l7c\u0001\u0001\u0019CA\u0011\u0011dH\u0007\u00025)\u0011\u0001c\u0007\u0006\u00039u\t1A\\3u\u0015\u0005q\u0012!\u00026bm\u0006D\u0018B\u0001\u0011\u001b\u0005Y\u00196\u000bT*feZ,'oU8dW\u0016$h)Y2u_JL\bC\u0001\u0012$\u001b\u0005i\u0011B\u0001\u0013\u000e\u0005-!&/Y2f\u0019><w-\u001a:\u0002\u000f\u0019\f7\r^8ss\u000e\u0001\u0001c\u0001\u0015,15\t\u0011FC\u0001+\u0003\u0015\u00198-\u00197b\u0013\ta\u0013F\u0001\u0005=Eft\u0017-\\3?\u0003\u0015!WMY;h!\ty\u0003'D\u0001\u0010\u0013\t\ttB\u0001\bT'2#UMY;h\u0007>tg-[4\u0002\u000514\u0007C\u0001\u001b8\u001b\u0005)$B\u0001\u001c\u0012\u0003\u0011)H/\u001b7\n\u0005a*$!\u0004'pO\u001e,'OR1di>\u0014\u00180\u0001\u0004=S:LGO\u0010\u000b\u0004wyzDC\u0001\u001f>!\t\u0011\u0003\u0001C\u00033\t\u0001\u000f1\u0007\u0003\u0004&\t\u0011\u0005\ra\n\u0005\u0006[\u0011\u0001\rAL\u0001\u0013GJ,\u0017\r^3TKJ4XM]*pG.,G\u000fF\u0001C!\t\u0019u)D\u0001E\u0015\taRIC\u0001G\u0003\u0011Q\u0017M^1\n\u0005!#%\u0001D*feZ,'oU8dW\u0016$HC\u0001\"K\u0011\u0015Ye\u00011\u0001M\u0003\u0011\u0001xN\u001d;\u0011\u0005!j\u0015B\u0001(*\u0005\rIe\u000e\u001e\u000b\u0004\u0005B\u000b\u0006\"B&\b\u0001\u0004a\u0005\"\u0002*\b\u0001\u0004a\u0015a\u00022bG.dwn\u001a\u000b\u0005\u0005R+f\u000bC\u0003L\u0011\u0001\u0007A\nC\u0003S\u0011\u0001\u0007A\nC\u0003X\u0011\u0001\u0007\u0001,A\u0005jM\u0006#GM]3tgB\u00111)W\u0005\u00035\u0012\u00131\"\u00138fi\u0006#GM]3tg\u00061r-\u001a;EK\u001a\fW\u000f\u001c;DSBDWM]*vSR,7\u000fF\u0001^!\rAc\fY\u0005\u0003?&\u0012Q!\u0011:sCf\u0004\"!\u00195\u000f\u0005\t4\u0007CA2*\u001b\u0005!'BA3'\u0003\u0019a$o\\8u}%\u0011q-K\u0001\u0007!J,G-\u001a4\n\u0005%T'AB*ue&twM\u0003\u0002hS\u0005Ar-\u001a;TkB\u0004xN\u001d;fI\u000eK\u0007\u000f[3s'VLG/Z:\u0002\u0019%\u001cHj\\4F]\u0006\u0014G.\u001a3\u0015\u00079\f8\u000f\u0005\u0002)_&\u0011\u0001/\u000b\u0002\b\u0005>|G.Z1o\u0011\u0015\u00118\u00021\u0001a\u0003)iW\r\u001e5pI:\u000bW.\u001a\u0005\u0006i.\u0001\r!^\u0001\u000ba\u0006\u0014\u0018-\\3uKJ\u001c\b\u0003B1wAbL!a\u001e6\u0003\u00075\u000b\u0007\u000f\u0005\u0002)s&\u0011!0\u000b\u0002\u0004\u0003:L\b")
public class TracingSSLServerSocketFactory
extends SSLServerSocketFactory
implements TraceLogger {
    private final Function0<SSLServerSocketFactory> factory;
    private final SSLDebugConfig debug;
    private final LoggerFactory lf;

    @Override
    public <T, E> T tracer(String methodName, Map<String, Object> parameters, Function0<T> function, LoggerFactory loggerFactory) {
        return (T)TraceLogger.tracer$(this, methodName, parameters, function, loggerFactory);
    }

    @Override
    public ServerSocket createServerSocket() {
        return (ServerSocket)this.tracer("createServerSocket", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<ServerSocket> & Serializable)() -> ((ServerSocketFactory)$this.factory.apply()).createServerSocket(), this.lf);
    }

    @Override
    public ServerSocket createServerSocket(int port) {
        return (ServerSocket)this.tracer("createServerSocket", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("port"), BoxesRunTime.boxToInteger(port))})), (Function0<ServerSocket> & Serializable)() -> ((ServerSocketFactory)$this.factory.apply()).createServerSocket(port), this.lf);
    }

    @Override
    public ServerSocket createServerSocket(int port, int backlog) {
        return (ServerSocket)this.tracer("createServerSocket", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("port"), BoxesRunTime.boxToInteger(port)), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("backlog"), BoxesRunTime.boxToInteger(backlog))})), (Function0<ServerSocket> & Serializable)() -> ((ServerSocketFactory)$this.factory.apply()).createServerSocket(port, backlog), this.lf);
    }

    @Override
    public ServerSocket createServerSocket(int port, int backlog, InetAddress ifAddress) {
        return (ServerSocket)this.tracer("createServerSocket", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("port"), BoxesRunTime.boxToInteger(port)), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("backlog"), BoxesRunTime.boxToInteger(backlog)), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("ifAddress"), ifAddress)})), (Function0<ServerSocket> & Serializable)() -> ((ServerSocketFactory)$this.factory.apply()).createServerSocket(port, backlog, ifAddress), this.lf);
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return (String[])this.tracer("getDefaultCipherSuites", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<String[]> & Serializable)() -> $this.factory.apply().getDefaultCipherSuites(), this.lf);
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return (String[])this.tracer("getSupportedCipherSuites", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<String[]> & Serializable)() -> $this.factory.apply().getSupportedCipherSuites(), this.lf);
    }

    @Override
    public boolean isLogEnabled(String methodName, Map<String, Object> parameters) {
        return this.debug.all() || this.debug.ssl();
    }

    public TracingSSLServerSocketFactory(Function0<SSLServerSocketFactory> factory2, SSLDebugConfig debug, LoggerFactory lf) {
        this.factory = factory2;
        this.debug = debug;
        this.lf = lf;
        TraceLogger.$init$(this);
    }
}

