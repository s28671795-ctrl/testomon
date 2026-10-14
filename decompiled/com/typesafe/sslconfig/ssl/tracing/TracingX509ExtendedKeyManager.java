/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl.tracing;

import com.typesafe.sslconfig.ssl.SSLDebugConfig;
import com.typesafe.sslconfig.ssl.tracing.TraceLogger;
import com.typesafe.sslconfig.util.LoggerFactory;
import java.io.Serializable;
import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509KeyManager;
import scala.Function0;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.collection.immutable.Map;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015c!\u0002\b\u0010\u0001MI\u0002\u0002C\u0014\u0001\u0005\u0003%\u000b\u0011B\u0015\t\u0011=\u0002!\u0011!Q\u0001\nAB\u0001\u0002\u000e\u0001\u0003\u0002\u0003\u0006Y!\u000e\u0005\u0006w\u0001!\t\u0001\u0010\u0005\u0006\u0005\u0002!\te\u0011\u0005\u0006I\u0002!\t%\u001a\u0005\u0006S\u0002!\tE\u001b\u0005\u0006]\u0002!\te\u001c\u0005\u0006s\u0002!\tE\u001f\u0005\u0006{\u0002!\tE \u0005\b\u0003\u000b\u0001A\u0011IA\u0004\u0011\u001d\tY\u0002\u0001C!\u0003;Aq!a\n\u0001\t\u0003\nICA\u000fUe\u0006\u001c\u0017N\\4YkAJT\t\u001f;f]\u0012,GmS3z\u001b\u0006t\u0017mZ3s\u0015\t\u0001\u0012#A\u0004ue\u0006\u001c\u0017N\\4\u000b\u0005I\u0019\u0012aA:tY*\u0011A#F\u0001\ngNd7m\u001c8gS\u001eT!AF\f\u0002\u0011QL\b/Z:bM\u0016T\u0011\u0001G\u0001\u0004G>l7c\u0001\u0001\u001bGA\u00111$I\u0007\u00029)\u0011!#\b\u0006\u0003=}\t1A\\3u\u0015\u0005\u0001\u0013!\u00026bm\u0006D\u0018B\u0001\u0012\u001d\u0005YAV\u0007M\u001dFqR,g\u000eZ3e\u0017\u0016LX*\u00198bO\u0016\u0014\bC\u0001\u0013&\u001b\u0005y\u0011B\u0001\u0014\u0010\u0005-!&/Y2f\u0019><w-\u001a:\u0002\u0011M,\b\u000f\u001d7jKJ\u001c\u0001\u0001E\u0002+[ii\u0011a\u000b\u0006\u0002Y\u0005)1oY1mC&\u0011af\u000b\u0002\ty\tLh.Y7f}\u0005)A-\u001a2vOB\u0011\u0011GM\u0007\u0002#%\u00111'\u0005\u0002\u000f'NcE)\u001a2vO\u000e{gNZ5h\u00035awnZ4fe\u001a\u000b7\r^8ssB\u0011a'O\u0007\u0002o)\u0011\u0001hE\u0001\u0005kRLG.\u0003\u0002;o\tiAj\\4hKJ4\u0015m\u0019;pef\fa\u0001P5oSRtDcA\u001fA\u0003R\u0011ah\u0010\t\u0003I\u0001AQ\u0001\u000e\u0003A\u0004UBaa\n\u0003\u0005\u0002\u0004I\u0003\"B\u0018\u0005\u0001\u0004\u0001\u0014aF2i_>\u001cX-\u00128hS:,7\t\\5f]R\fE.[1t)\u0011!u\nV0\u0011\u0005\u0015ceB\u0001$K!\t95&D\u0001I\u0015\tI\u0005&\u0001\u0004=e>|GOP\u0005\u0003\u0017.\na\u0001\u0015:fI\u00164\u0017BA'O\u0005\u0019\u0019FO]5oO*\u00111j\u000b\u0005\u0006!\u0016\u0001\r!U\u0001\tW\u0016LH+\u001f9fgB\u0019!F\u0015#\n\u0005M[#!B!se\u0006L\b\"B+\u0006\u0001\u00041\u0016aB5tgV,'o\u001d\t\u0004UI;\u0006C\u0001-^\u001b\u0005I&B\u0001.\\\u0003!\u0019XmY;sSRL(\"\u0001/\u0002\t)\fg/Y\u0005\u0003=f\u0013\u0011\u0002\u0015:j]\u000eL\u0007/\u00197\t\u000b\u0001,\u0001\u0019A1\u0002\r\u0015tw-\u001b8f!\tY\"-\u0003\u0002d9\tI1k\u0015'F]\u001eLg.Z\u0001\u0018G\"|wn]3F]\u001eLg.Z*feZ,'/\u00117jCN$B\u0001\u00124hQ\")\u0001K\u0002a\u0001\t\")QK\u0002a\u0001-\")\u0001M\u0002a\u0001C\u0006\u0001r-\u001a;DY&,g\u000e^!mS\u0006\u001cXm\u001d\u000b\u0004#.l\u0007\"\u00027\b\u0001\u0004!\u0015aB6fsRK\b/\u001a\u0005\u0006+\u001e\u0001\rAV\u0001\u0012G\"|wn]3DY&,g\u000e^!mS\u0006\u001cH\u0003\u0002#qcJDQ\u0001\u0015\u0005A\u0002ECQ!\u0016\u0005A\u0002YCQa\u001d\u0005A\u0002Q\faa]8dW\u0016$\bCA;x\u001b\u00051(B\u0001\u0010\\\u0013\tAhO\u0001\u0004T_\u000e\\W\r^\u0001\u0011O\u0016$8+\u001a:wKJ\fE.[1tKN$2!U>}\u0011\u0015a\u0017\u00021\u0001E\u0011\u0015)\u0016\u00021\u0001W\u0003E\u0019\u0007n\\8tKN+'O^3s\u00032L\u0017m\u001d\u000b\u0007\t~\f\t!a\u0001\t\u000b1T\u0001\u0019\u0001#\t\u000bUS\u0001\u0019\u0001,\t\u000bMT\u0001\u0019\u0001;\u0002'\u001d,GoQ3si&4\u0017nY1uK\u000eC\u0017-\u001b8\u0015\t\u0005%\u0011q\u0003\t\u0005UI\u000bY\u0001\u0005\u0003\u0002\u000e\u0005MQBAA\b\u0015\r\t\t\"W\u0001\u0005G\u0016\u0014H/\u0003\u0003\u0002\u0016\u0005=!a\u0004-6ae\u001aUM\u001d;jM&\u001c\u0017\r^3\t\r\u0005e1\u00021\u0001E\u0003\u0015\tG.[1t\u000359W\r\u001e)sSZ\fG/Z&fsR!\u0011qDA\u0013!\rA\u0016\u0011E\u0005\u0004\u0003GI&A\u0003)sSZ\fG/Z&fs\"1\u0011\u0011\u0004\u0007A\u0002\u0011\u000bA\"[:M_\u001e,e.\u00192mK\u0012$b!a\u000b\u00022\u0005U\u0002c\u0001\u0016\u0002.%\u0019\u0011qF\u0016\u0003\u000f\t{w\u000e\\3b]\"1\u00111G\u0007A\u0002\u0011\u000b!\"\\3uQ>$g*Y7f\u0011\u001d\t9$\u0004a\u0001\u0003s\t!\u0002]1sC6,G/\u001a:t!\u0019)\u00151\b#\u0002@%\u0019\u0011Q\b(\u0003\u00075\u000b\u0007\u000fE\u0002+\u0003\u0003J1!a\u0011,\u0005\r\te.\u001f")
public class TracingX509ExtendedKeyManager
extends X509ExtendedKeyManager
implements TraceLogger {
    private final Function0<X509ExtendedKeyManager> supplier;
    private final SSLDebugConfig debug;
    private final LoggerFactory loggerFactory;

    @Override
    public <T, E> T tracer(String methodName, Map<String, Object> parameters, Function0<T> function, LoggerFactory loggerFactory) {
        return (T)TraceLogger.tracer$(this, methodName, parameters, function, loggerFactory);
    }

    @Override
    public String chooseEngineClientAlias(String[] keyTypes, Principal[] issuers, SSLEngine engine) {
        return (String)this.tracer("chooseEngineClientAlias", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("keyTypes"), keyTypes), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("issuers"), issuers), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("engine"), engine)})), (Function0<String> & Serializable)() -> $this.supplier.apply().chooseEngineClientAlias(keyTypes, issuers, engine), this.loggerFactory);
    }

    @Override
    public String chooseEngineServerAlias(String keyTypes, Principal[] issuers, SSLEngine engine) {
        return (String)this.tracer("chooseEngineServerAlias", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("keyTypes"), keyTypes), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("issuers"), issuers), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("engine"), engine)})), (Function0<String> & Serializable)() -> $this.supplier.apply().chooseEngineServerAlias(keyTypes, issuers, engine), this.loggerFactory);
    }

    @Override
    public String[] getClientAliases(String keyType, Principal[] issuers) {
        return (String[])this.tracer("getClientAliases", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("keyType"), keyType), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("issuers"), issuers)})), (Function0<String[]> & Serializable)() -> ((X509KeyManager)$this.supplier.apply()).getClientAliases(keyType, issuers), this.loggerFactory);
    }

    @Override
    public String chooseClientAlias(String[] keyTypes, Principal[] issuers, Socket socket) {
        return (String)this.tracer("chooseClientAlias", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("keyTypes"), keyTypes), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("issuers"), issuers), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("socket"), socket)})), (Function0<String> & Serializable)() -> ((X509KeyManager)$this.supplier.apply()).chooseClientAlias(keyTypes, issuers, socket), this.loggerFactory);
    }

    @Override
    public String[] getServerAliases(String keyType, Principal[] issuers) {
        return (String[])this.tracer("getServerAliases", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("keyType"), keyType), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("issuers"), issuers)})), (Function0<String[]> & Serializable)() -> ((X509KeyManager)$this.supplier.apply()).getServerAliases(keyType, issuers), this.loggerFactory);
    }

    @Override
    public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
        return (String)this.tracer("chooseServerAlias", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("keyType"), keyType), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("issuers"), issuers), Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("socket"), socket)})), (Function0<String> & Serializable)() -> ((X509KeyManager)$this.supplier.apply()).chooseServerAlias(keyType, issuers, socket), this.loggerFactory);
    }

    @Override
    public X509Certificate[] getCertificateChain(String alias) {
        return (X509Certificate[])this.tracer("getCertificateChain", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("alias"), alias)})), (Function0<X509Certificate[]> & Serializable)() -> ((X509KeyManager)$this.supplier.apply()).getCertificateChain(alias), this.loggerFactory);
    }

    @Override
    public PrivateKey getPrivateKey(String alias) {
        return (PrivateKey)this.tracer("getPrivateKey", (Map)Predef$.MODULE$.Map().apply(ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Tuple2[]{Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc("alias"), alias)})), (Function0<PrivateKey> & Serializable)() -> ((X509KeyManager)$this.supplier.apply()).getPrivateKey(alias), this.loggerFactory);
    }

    @Override
    public boolean isLogEnabled(String methodName, Map<String, Object> parameters) {
        return this.debug.all() || this.debug.keymanager();
    }

    public TracingX509ExtendedKeyManager(Function0<X509ExtendedKeyManager> supplier, SSLDebugConfig debug, LoggerFactory loggerFactory) {
        this.supplier = supplier;
        this.debug = debug;
        this.loggerFactory = loggerFactory;
        TraceLogger.$init$(this);
    }
}

