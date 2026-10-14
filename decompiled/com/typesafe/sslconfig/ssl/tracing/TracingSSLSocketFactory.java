/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl.tracing;

import com.typesafe.sslconfig.ssl.SSLDebugConfig;
import com.typesafe.sslconfig.ssl.tracing.TraceLogger;
import com.typesafe.sslconfig.util.LoggerFactory;
import java.io.InputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import javax.net.ssl.SSLSocketFactory;
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

@ScalaSignature(bytes="\u0006\u0005\u0005=b!B\b\u0011\u0001QQ\u0002\u0002\u0003\u0015\u0001\u0005\u0003\u0005\u000b\u0011B\u000e\t\u0011)\u0002!\u0011!Q\u0001\n-B\u0001b\f\u0001\u0003\u0002\u0003\u0006Y\u0001\r\u0005\u0006m\u0001!\ta\u000e\u0005\u0006{\u0001!\tE\u0010\u0005\u0006{\u0001!\te\u0017\u0005\u0006{\u0001!\t\u0005\u0018\u0005\u0006{\u0001!\tE\u001a\u0005\u0006{\u0001!\t%\u001d\u0005\u0006{\u0001!\te\u001f\u0005\u0006{\u0001!\tE \u0005\b\u0003\u0017\u0001A\u0011IA\u0007\u0011\u001d\t)\u0002\u0001C!\u0003\u001bAq!a\u0006\u0001\t\u0003\nIBA\fUe\u0006\u001c\u0017N\\4T'2\u001bvnY6fi\u001a\u000b7\r^8ss*\u0011\u0011CE\u0001\biJ\f7-\u001b8h\u0015\t\u0019B#A\u0002tg2T!!\u0006\f\u0002\u0013M\u001cHnY8oM&<'BA\f\u0019\u0003!!\u0018\u0010]3tC\u001a,'\"A\r\u0002\u0007\r|WnE\u0002\u00017\u0011\u0002\"\u0001\b\u0012\u000e\u0003uQ!a\u0005\u0010\u000b\u0005}\u0001\u0013a\u00018fi*\t\u0011%A\u0003kCZ\f\u00070\u0003\u0002$;\t\u00012k\u0015'T_\u000e\\W\r\u001e$bGR|'/\u001f\t\u0003K\u0019j\u0011\u0001E\u0005\u0003OA\u00111\u0002\u0016:bG\u0016dunZ4fe\u00069a-Y2u_JL8\u0001A\u0001\u0006I\u0016\u0014Wo\u001a\t\u0003Y5j\u0011AE\u0005\u0003]I\u0011abU*M\t\u0016\u0014WoZ\"p]\u001aLw-A\u0007m_\u001e<WM\u001d$bGR|'/\u001f\t\u0003cQj\u0011A\r\u0006\u0003gQ\tA!\u001e;jY&\u0011QG\r\u0002\u000e\u0019><w-\u001a:GC\u000e$xN]=\u0002\rqJg.\u001b;?)\rA4\b\u0010\u000b\u0003si\u0002\"!\n\u0001\t\u000b=\"\u00019\u0001\u0019\t\u000b!\"\u0001\u0019A\u000e\t\u000b)\"\u0001\u0019A\u0016\u0002\u0019\r\u0014X-\u0019;f'>\u001c7.\u001a;\u0015\u0007}2U\u000b\u0005\u0002A\t6\t\u0011I\u0003\u0002 \u0005*\t1)\u0001\u0003kCZ\f\u0017BA#B\u0005\u0019\u0019vnY6fi\")q)\u0002a\u0001\u0011\u0006!\u0001n\\:u!\tI%K\u0004\u0002K!B\u00111JT\u0007\u0002\u0019*\u0011Q*K\u0001\u0007yI|w\u000e\u001e \u000b\u0003=\u000bQa]2bY\u0006L!!\u0015(\u0002\rA\u0013X\rZ3g\u0013\t\u0019FK\u0001\u0004TiJLgn\u001a\u0006\u0003#:CQAV\u0003A\u0002]\u000bA\u0001]8siB\u0011\u0001,W\u0007\u0002\u001d&\u0011!L\u0014\u0002\u0004\u0013:$H#A \u0015\u000b}jv\fY1\t\u000by;\u0001\u0019A \u0002\u0003MDQaR\u0004A\u0002!CQAV\u0004A\u0002]CQAY\u0004A\u0002\r\f\u0011\"Y;u_\u000ecwn]3\u0011\u0005a#\u0017BA3O\u0005\u001d\u0011un\u001c7fC:$BaP4ia\")a\f\u0003a\u0001\u007f!)\u0011\u000e\u0003a\u0001U\u0006A1m\u001c8tk6,G\r\u0005\u0002l]6\tAN\u0003\u0002n\u0005\u0006\u0011\u0011n\\\u0005\u0003_2\u00141\"\u00138qkR\u001cFO]3b[\")!\r\u0003a\u0001GR)qH]:us\")q)\u0003a\u0001\u0011\")a+\u0003a\u0001/\")Q/\u0003a\u0001m\u0006IAn\\2bY\"{7\u000f\u001e\t\u0003\u0001^L!\u0001_!\u0003\u0017%sW\r^!eIJ,7o\u001d\u0005\u0006u&\u0001\raV\u0001\nY>\u001c\u0017\r\u001c)peR$2a\u0010?~\u0011\u00159%\u00021\u0001w\u0011\u00151&\u00021\u0001X)!yt0a\u0001\u0002\u0006\u0005%\u0001BBA\u0001\u0017\u0001\u0007a/A\u0004bI\u0012\u0014Xm]:\t\u000bY[\u0001\u0019A,\t\r\u0005\u001d1\u00021\u0001w\u00031awnY1m\u0003\u0012$'/Z:t\u0011\u0015Q8\u00021\u0001X\u0003Y9W\r\u001e#fM\u0006,H\u000e^\"ja\",'oU;ji\u0016\u001cHCAA\b!\u0011A\u0016\u0011\u0003%\n\u0007\u0005MaJA\u0003BeJ\f\u00170\u0001\rhKR\u001cV\u000f\u001d9peR,GmQ5qQ\u0016\u00148+^5uKN\fA\"[:M_\u001e,e.\u00192mK\u0012$RaYA\u000e\u0003?Aa!!\b\u000f\u0001\u0004A\u0015AC7fi\"|GMT1nK\"9\u0011\u0011\u0005\bA\u0002\u0005\r\u0012A\u00039be\u0006lW\r^3sgB1\u0011*!\nI\u0003SI1!a\nU\u0005\ri\u0015\r\u001d\t\u00041\u0006-\u0012bAA\u0017\u001d\n\u0019\u0011I\\=")
public class TracingSSLSocketFactory
extends SSLSocketFactory
implements TraceLogger {
    private final SSLSocketFactory factory;
    private final SSLDebugConfig debug;
    private final LoggerFactory loggerFactory;

    @Override
    public <T, E> T tracer(String methodName, Map<String, Object> parameters, Function0<T> function, LoggerFactory loggerFactory) {
        return (T)TraceLogger.tracer$(this, methodName, parameters, function, loggerFactory);
    }

    @Override
    public Socket createSocket(String host2, int port) {
        return (Socket)this.tracer("createSocket", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("host"), host2), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("port"), BoxesRunTime.boxToInteger(port))})), (Function0<Socket> & Serializable)() -> $this.factory.createSocket(host2, port), this.loggerFactory);
    }

    @Override
    public Socket createSocket() {
        return (Socket)this.tracer("createSocket", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<Socket> & Serializable)() -> $this.factory.createSocket(), this.loggerFactory);
    }

    @Override
    public Socket createSocket(Socket s2, String host2, int port, boolean autoClose) {
        return (Socket)this.tracer("createSocket", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("s"), s2), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("host"), host2), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("port"), BoxesRunTime.boxToInteger(port)), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("autoClose"), BoxesRunTime.boxToBoolean(autoClose))})), (Function0<Socket> & Serializable)() -> $this.factory.createSocket(s2, host2, port, autoClose), this.loggerFactory);
    }

    @Override
    public Socket createSocket(Socket s2, InputStream consumed, boolean autoClose) {
        return (Socket)this.tracer("createSocket", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("s"), s2), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("consumed"), consumed), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("autoClose"), BoxesRunTime.boxToBoolean(autoClose))})), (Function0<Socket> & Serializable)() -> $this.factory.createSocket(s2, consumed, autoClose), this.loggerFactory);
    }

    @Override
    public Socket createSocket(String host2, int port, InetAddress localHost, int localPort) {
        return (Socket)this.tracer("createSocket", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("host"), host2), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("port"), BoxesRunTime.boxToInteger(port)), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("localHost"), localHost), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("localPort"), BoxesRunTime.boxToInteger(localPort))})), (Function0<Socket> & Serializable)() -> $this.factory.createSocket(host2, port, localHost, localPort), this.loggerFactory);
    }

    @Override
    public Socket createSocket(InetAddress host2, int port) {
        return (Socket)this.tracer("createSocket", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("host"), host2), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("port"), BoxesRunTime.boxToInteger(port))})), (Function0<Socket> & Serializable)() -> $this.factory.createSocket(host2, port), this.loggerFactory);
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) {
        return (Socket)this.tracer("createSocket", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("address"), address), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("port"), BoxesRunTime.boxToInteger(port)), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("localAddress"), localAddress), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("localPort"), BoxesRunTime.boxToInteger(localPort))})), (Function0<Socket> & Serializable)() -> $this.factory.createSocket(address, port, localAddress, localPort), this.loggerFactory);
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return (String[])this.tracer("getDefaultCipherSuites", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<String[]> & Serializable)() -> $this.factory.getDefaultCipherSuites(), this.loggerFactory);
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return (String[])this.tracer("getSupportedCipherSuites", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<String[]> & Serializable)() -> $this.factory.getSupportedCipherSuites(), this.loggerFactory);
    }

    @Override
    public boolean isLogEnabled(String methodName, Map<String, Object> parameters) {
        return this.debug.all() || this.debug.ssl();
    }

    public TracingSSLSocketFactory(SSLSocketFactory factory2, SSLDebugConfig debug, LoggerFactory loggerFactory) {
        this.factory = factory2;
        this.debug = debug;
        this.loggerFactory = loggerFactory;
        TraceLogger.$init$(this);
    }
}

