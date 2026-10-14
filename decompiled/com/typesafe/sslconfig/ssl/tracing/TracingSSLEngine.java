/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl.tracing;

import com.typesafe.sslconfig.ssl.SSLDebugConfig;
import com.typesafe.sslconfig.ssl.tracing.TraceLogger;
import com.typesafe.sslconfig.util.LoggerFactory;
import java.io.Serializable;
import java.nio.ByteBuffer;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLSession;
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

@ScalaSignature(bytes="\u0006\u0005\u0005\rf!\u0002\u0010 \u0001\rJ\u0003\u0002C\u001c\u0001\u0005\u0003%\u000b\u0011B\u001d\t\u0011}\u0002!\u0011!Q\u0001\n\u0001C\u0001\u0002\u0012\u0001\u0003\u0002\u0003\u0006Y!\u0012\u0005\u0006\u0017\u0002!\t\u0001\u0014\u0005\u0006%\u0002!\te\u0015\u0005\u0006[\u0002!\tE\u001c\u0005\u0006k\u0002!\tE\u001e\u0005\u0006{\u0002!\tE \u0005\b\u0003\u000b\u0001A\u0011IA\u0004\u0011\u0019\ty\u0001\u0001C!}\"9\u0011\u0011\u0003\u0001\u0005B\u0005\u001d\u0001bBA\n\u0001\u0011\u0005\u0013Q\u0003\u0005\b\u0003_\u0001A\u0011IA\u000b\u0011\u001d\t\t\u0004\u0001C!\u0003gAq!!\u000f\u0001\t\u0003\n)\u0002C\u0004\u0002<\u0001!\t%!\u0006\t\u000f\u0005u\u0002\u0001\"\u0011\u0002@!9\u0011Q\t\u0001\u0005B\u0005\u001d\u0003BBA(\u0001\u0011\u0005c\u0010C\u0004\u0002R\u0001!\t%a\u0015\t\u000f\u0005\r\u0004\u0001\"\u0011\u0002f!9\u00111\u000e\u0001\u0005B\u0005\u001d\u0001bBA7\u0001\u0011\u0005\u0013q\u000e\u0005\b\u0003k\u0002A\u0011IA\u0004\u0011\u001d\t9\b\u0001C!\u0003sBq!a \u0001\t\u0003\n9\u0001C\u0004\u0002\u0002\u0002!\t%a!\t\u000f\u0005%\u0005\u0001\"\u0011\u0002\b!9\u00111\u0012\u0001\u0005B\u00055%\u0001\u0005+sC\u000eLgnZ*T\u0019\u0016sw-\u001b8f\u0015\t\u0001\u0013%A\u0004ue\u0006\u001c\u0017N\\4\u000b\u0005\t\u001a\u0013aA:tY*\u0011A%J\u0001\ngNd7m\u001c8gS\u001eT!AJ\u0014\u0002\u0011QL\b/Z:bM\u0016T\u0011\u0001K\u0001\u0004G>l7c\u0001\u0001+gA\u00111&M\u0007\u0002Y)\u0011!%\f\u0006\u0003]=\n1A\\3u\u0015\u0005\u0001\u0014!\u00026bm\u0006D\u0018B\u0001\u001a-\u0005%\u00196\u000bT#oO&tW\r\u0005\u00025k5\tq$\u0003\u00027?\tYAK]1dK2{wmZ3s\u0003\u0019)gnZ5oK\u000e\u0001\u0001c\u0001\u001e>U5\t1HC\u0001=\u0003\u0015\u00198-\u00197b\u0013\tq4H\u0001\u0005=Eft\u0017-\\3?\u0003\u0015!WMY;h!\t\t%)D\u0001\"\u0013\t\u0019\u0015E\u0001\bT'2#UMY;h\u0007>tg-[4\u0002\u001b1|wmZ3s\r\u0006\u001cGo\u001c:z!\t1\u0015*D\u0001H\u0015\tA5%\u0001\u0003vi&d\u0017B\u0001&H\u00055aunZ4fe\u001a\u000b7\r^8ss\u00061A(\u001b8jiz\"2!\u0014)R)\tqu\n\u0005\u00025\u0001!)A\t\u0002a\u0002\u000b\"1q\u0007\u0002CA\u0002eBQa\u0010\u0003A\u0002\u0001\u000bAa\u001e:baR)Ak\u00163jWB\u00111&V\u0005\u0003-2\u0012qbU*M\u000b:<\u0017N\\3SKN,H\u000e\u001e\u0005\u00061\u0016\u0001\r!W\u0001\u0005gJ\u001c7\u000fE\u0002;5rK!aW\u001e\u0003\u000b\u0005\u0013(/Y=\u0011\u0005u\u0013W\"\u00010\u000b\u0005}\u0003\u0017a\u00018j_*\t\u0011-\u0001\u0003kCZ\f\u0017BA2_\u0005)\u0011\u0015\u0010^3Ck\u001a4WM\u001d\u0005\u0006K\u0016\u0001\rAZ\u0001\u0007_\u001a47/\u001a;\u0011\u0005i:\u0017B\u00015<\u0005\rIe\u000e\u001e\u0005\u0006U\u0016\u0001\rAZ\u0001\u0007Y\u0016tw\r\u001e5\t\u000b1,\u0001\u0019\u0001/\u0002\u0007\u0011\u001cH/\u0001\u0004v]^\u0014\u0018\r\u001d\u000b\u0006)>\f8\u000f\u001e\u0005\u0006a\u001a\u0001\r\u0001X\u0001\u0004gJ\u001c\u0007\"\u0002:\u0007\u0001\u0004I\u0016\u0001\u00023tiNDQ!\u001a\u0004A\u0002\u0019DQA\u001b\u0004A\u0002\u0019\f\u0001cZ3u\t\u0016dWmZ1uK\u0012$\u0016m]6\u0015\u0003]\u0004\"\u0001_>\u000e\u0003eT!A\u001f1\u0002\t1\fgnZ\u0005\u0003yf\u0014\u0001BU;o]\u0006\u0014G.Z\u0001\rG2|7/Z%oE>,h\u000e\u001a\u000b\u0002\u007fB\u0019!(!\u0001\n\u0007\u0005\r1H\u0001\u0003V]&$\u0018!D5t\u0013:\u0014w.\u001e8e\t>tW\r\u0006\u0002\u0002\nA\u0019!(a\u0003\n\u0007\u000551HA\u0004C_>dW-\u00198\u0002\u001b\rdwn]3PkR\u0014w.\u001e8e\u00039I7oT;uE>,h\u000e\u001a#p]\u0016\f\u0001dZ3u'V\u0004\bo\u001c:uK\u0012\u001c\u0015\u000e\u001d5feN+\u0018\u000e^3t)\t\t9\u0002\u0005\u0003;5\u0006e\u0001\u0003BA\u000e\u0003SqA!!\b\u0002&A\u0019\u0011qD\u001e\u000e\u0005\u0005\u0005\"bAA\u0012q\u00051AH]8pizJ1!a\n<\u0003\u0019\u0001&/\u001a3fM&!\u00111FA\u0017\u0005\u0019\u0019FO]5oO*\u0019\u0011qE\u001e\u0002-\u001d,G/\u00128bE2,GmQ5qQ\u0016\u00148+^5uKN\fac]3u\u000b:\f'\r\\3e\u0007&\u0004\b.\u001a:Tk&$Xm\u001d\u000b\u0004\u007f\u0006U\u0002bBA\u001c\u001d\u0001\u0007\u0011qC\u0001\u0007gVLG/Z:\u0002+\u001d,GoU;qa>\u0014H/\u001a3Qe>$xnY8mg\u0006\u0019r-\u001a;F]\u0006\u0014G.\u001a3Qe>$xnY8mg\u0006\u00192/\u001a;F]\u0006\u0014G.\u001a3Qe>$xnY8mgR\u0019q0!\u0011\t\u000f\u0005\r\u0013\u00031\u0001\u0002\u0018\u0005I\u0001O]8u_\u000e|Gn]\u0001\u000bO\u0016$8+Z:tS>tGCAA%!\rY\u00131J\u0005\u0004\u0003\u001bb#AC*T\u0019N+7o]5p]\u0006q!-Z4j]\"\u000bg\u000eZ:iC.,\u0017AE4fi\"\u000bg\u000eZ:iC.,7\u000b^1ukN$\"!!\u0016\u0011\t\u0005]\u0013Q\f\b\u0004W\u0005e\u0013bAA.Y\u0005y1k\u0015'F]\u001eLg.\u001a*fgVdG/\u0003\u0003\u0002`\u0005\u0005$a\u0004%b]\u0012\u001c\b.Y6f'R\fG/^:\u000b\u0007\u0005mC&\u0001\ttKR,6/Z\"mS\u0016tG/T8eKR\u0019q0a\u001a\t\u000f\u0005%T\u00031\u0001\u0002\n\u0005Q1\r\\5f]Rlu\u000eZ3\u0002!\u001d,G/V:f\u00072LWM\u001c;N_\u0012,\u0017!E:fi:+W\rZ\"mS\u0016tG/Q;uQR\u0019q0!\u001d\t\u000f\u0005Mt\u00031\u0001\u0002\n\u0005qa.Z3e\u00072LWM\u001c;BkRD\u0017!E4fi:+W\rZ\"mS\u0016tG/Q;uQ\u0006\t2/\u001a;XC:$8\t\\5f]R\fU\u000f\u001e5\u0015\u0007}\fY\bC\u0004\u0002~e\u0001\r!!\u0003\u0002\u001d]\fg\u000e^\"mS\u0016tG/Q;uQ\u0006\tr-\u001a;XC:$8\t\\5f]R\fU\u000f\u001e5\u00021M,G/\u00128bE2,7+Z:tS>t7I]3bi&|g\u000eF\u0002\u0000\u0003\u000bCq!a\"\u001c\u0001\u0004\tI!A\u000bf]\u0006\u0014G.Z*fgNLwN\\\"sK\u0006$\u0018n\u001c8\u00021\u001d,G/\u00128bE2,7+Z:tS>t7I]3bi&|g.\u0001\u0007jg2{w-\u00128bE2,G\r\u0006\u0004\u0002\n\u0005=\u00151\u0013\u0005\b\u0003#k\u0002\u0019AA\r\u0003)iW\r\u001e5pI:\u000bW.\u001a\u0005\b\u0003+k\u0002\u0019AAL\u0003)\u0001\u0018M]1nKR,'o\u001d\t\t\u00037\tI*!\u0007\u0002\u001e&!\u00111TA\u0017\u0005\ri\u0015\r\u001d\t\u0004u\u0005}\u0015bAAQw\t\u0019\u0011I\\=")
public class TracingSSLEngine
extends SSLEngine
implements TraceLogger {
    private final Function0<SSLEngine> engine;
    private final SSLDebugConfig debug;
    private final LoggerFactory loggerFactory;

    @Override
    public <T, E> T tracer(String methodName, Map<String, Object> parameters, Function0<T> function, LoggerFactory loggerFactory) {
        return (T)TraceLogger.tracer$(this, methodName, parameters, function, loggerFactory);
    }

    @Override
    public SSLEngineResult wrap(ByteBuffer[] srcs, int offset, int length, ByteBuffer dst) {
        return (SSLEngineResult)this.tracer("wrap", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("srcs"), srcs), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("offset"), BoxesRunTime.boxToInteger(offset)), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("length"), BoxesRunTime.boxToInteger(length)), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("dst"), dst)})), (Function0<SSLEngineResult> & Serializable)() -> $this.engine.apply().wrap(srcs, offset, length, dst), this.loggerFactory);
    }

    @Override
    public SSLEngineResult unwrap(ByteBuffer src, ByteBuffer[] dsts, int offset, int length) {
        return (SSLEngineResult)this.tracer("wrap", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("src"), src), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("dsts"), dsts), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("offset"), BoxesRunTime.boxToInteger(offset)), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("length"), BoxesRunTime.boxToInteger(length))})), (Function0<SSLEngineResult> & Serializable)() -> $this.engine.apply().unwrap(src, dsts, offset, length), this.loggerFactory);
    }

    @Override
    public Runnable getDelegatedTask() {
        return (Runnable)this.tracer("getDelegatedTask", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<Runnable> & Serializable)() -> $this.engine.apply().getDelegatedTask(), this.loggerFactory);
    }

    @Override
    public void closeInbound() {
        this.tracer("closeInbound", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), () -> $this.engine.apply().closeInbound(), this.loggerFactory);
    }

    @Override
    public boolean isInboundDone() {
        return BoxesRunTime.unboxToBoolean(this.tracer("isInboundDone", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), () -> $this.engine.apply().isInboundDone(), this.loggerFactory));
    }

    @Override
    public void closeOutbound() {
        this.tracer("isInboundDone", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), () -> $this.engine.apply().closeOutbound(), this.loggerFactory);
    }

    @Override
    public boolean isOutboundDone() {
        return BoxesRunTime.unboxToBoolean(this.tracer("isOutboundDone", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), () -> $this.engine.apply().isOutboundDone(), this.loggerFactory));
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return (String[])this.tracer("getSupportedCipherSuites", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<String[]> & Serializable)() -> $this.engine.apply().getSupportedCipherSuites(), this.loggerFactory);
    }

    @Override
    public String[] getEnabledCipherSuites() {
        return (String[])this.tracer("getEnabledCipherSuites", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<String[]> & Serializable)() -> $this.engine.apply().getEnabledCipherSuites(), this.loggerFactory);
    }

    @Override
    public void setEnabledCipherSuites(String[] suites) {
        this.tracer("setEnabledCipherSuites", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("suites"), suites)})), () -> $this.engine.apply().setEnabledCipherSuites(suites), this.loggerFactory);
    }

    @Override
    public String[] getSupportedProtocols() {
        return (String[])this.tracer("getSupportedProtocols", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<String[]> & Serializable)() -> $this.engine.apply().getSupportedProtocols(), this.loggerFactory);
    }

    @Override
    public String[] getEnabledProtocols() {
        return (String[])this.tracer("getEnabledProtocols", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<String[]> & Serializable)() -> $this.engine.apply().getEnabledProtocols(), this.loggerFactory);
    }

    @Override
    public void setEnabledProtocols(String[] protocols) {
        this.tracer("setEnabledProtocols", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("protocols"), protocols)})), () -> $this.engine.apply().setEnabledProtocols(protocols), this.loggerFactory);
    }

    @Override
    public SSLSession getSession() {
        return (SSLSession)this.tracer("getSession", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<SSLSession> & Serializable)() -> $this.engine.apply().getSession(), this.loggerFactory);
    }

    @Override
    public void beginHandshake() {
        this.tracer("beginHandshake", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), () -> $this.engine.apply().beginHandshake(), this.loggerFactory);
    }

    @Override
    public SSLEngineResult.HandshakeStatus getHandshakeStatus() {
        return (SSLEngineResult.HandshakeStatus)((Object)this.tracer("getHandshakeStatus", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), (Function0<SSLEngineResult.HandshakeStatus> & Serializable)() -> $this.engine.apply().getHandshakeStatus(), this.loggerFactory));
    }

    @Override
    public void setUseClientMode(boolean clientMode) {
        this.tracer("setUseClientMode", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("clientMode"), BoxesRunTime.boxToBoolean(clientMode))})), () -> $this.engine.apply().setUseClientMode(clientMode), this.loggerFactory);
    }

    @Override
    public boolean getUseClientMode() {
        return BoxesRunTime.unboxToBoolean(this.tracer("getUseClientMode", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), () -> $this.engine.apply().getUseClientMode(), this.loggerFactory));
    }

    @Override
    public void setNeedClientAuth(boolean needClientAuth) {
        this.tracer("setNeedClientAuth", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("needClientAuth"), BoxesRunTime.boxToBoolean(needClientAuth))})), () -> $this.engine.apply().setNeedClientAuth(needClientAuth), this.loggerFactory);
    }

    @Override
    public boolean getNeedClientAuth() {
        return BoxesRunTime.unboxToBoolean(this.tracer("getNeedClientAuth", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), () -> $this.engine.apply().getNeedClientAuth(), this.loggerFactory));
    }

    @Override
    public void setWantClientAuth(boolean wantClientAuth) {
        this.tracer("setWantClientAuth", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("wantClientAuth"), BoxesRunTime.boxToBoolean(wantClientAuth))})), () -> $this.engine.apply().setWantClientAuth(wantClientAuth), this.loggerFactory);
    }

    @Override
    public boolean getWantClientAuth() {
        return BoxesRunTime.unboxToBoolean(this.tracer("getWantClientAuth", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), () -> $this.engine.apply().getWantClientAuth(), this.loggerFactory));
    }

    @Override
    public void setEnableSessionCreation(boolean enableSessionCreation) {
        this.tracer("setEnableSessionCreation", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("enableSessionCreation"), BoxesRunTime.boxToBoolean(enableSessionCreation))})), () -> $this.engine.apply().setEnableSessionCreation(enableSessionCreation), this.loggerFactory);
    }

    @Override
    public boolean getEnableSessionCreation() {
        return BoxesRunTime.unboxToBoolean(this.tracer("getEnableSessionCreation", (Map)Predef$.MODULE$.Map().apply((Seq)Nil$.MODULE$), () -> $this.engine.apply().getEnableSessionCreation(), this.loggerFactory));
    }

    @Override
    public boolean isLogEnabled(String methodName, Map<String, Object> parameters) {
        return this.debug.all() || this.debug.ssl();
    }

    public TracingSSLEngine(Function0<SSLEngine> engine, SSLDebugConfig debug, LoggerFactory loggerFactory) {
        this.engine = engine;
        this.debug = debug;
        this.loggerFactory = loggerFactory;
        TraceLogger.$init$(this);
    }
}

