/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.actor.ExtendedActorSystem;
import akka.event.LogMarker$;
import akka.event.LogSource$;
import akka.event.Logging$;
import akka.event.MarkerLoggingAdapter;
import akka.serialization.BaseSerializer$;
import akka.serialization.ByteBufferSerializer;
import akka.serialization.DisabledJavaSerializer$;
import akka.serialization.JavaSerializer;
import akka.serialization.Serializer;
import java.io.NotSerializableException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import scala.Array$;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.control.NoStackTrace;

@ScalaSignature(bytes="\u0006\u0005\t]a\u0001B\u0013'\u0005.B\u0001\u0002\u0013\u0001\u0003\u0016\u0004%\t!\u0013\u0005\t!\u0002\u0011\t\u0012)A\u0005\u0015\")\u0011\u000b\u0001C\u0001%\"9Q\u000b\u0001b\u0001\n\u00032\u0006B\u0002.\u0001A\u0003%q\u000b\u0003\u0004\\\u0001\u0001\u0006I\u0001\u0018\u0005\u0007E\u0002\u0001\u000b\u0011B2\t\u000b%\u0004A\u0011\u00016\t\u000b9\u0004A\u0011I8\t\r}\u0004A\u0011IA\u0001\u0011\u0019y\b\u0001\"\u0011\u0002P!1a\u000e\u0001C!\u0003?B\u0011\"a\u0019\u0001\u0003\u0003%\t!!\u001a\t\u0013\u0005%\u0004!%A\u0005\u0002\u0005-\u0004\"CAA\u0001\u0005\u0005I\u0011IAB\u0011!\ty\tAA\u0001\n\u00031\u0006\"CAI\u0001\u0005\u0005I\u0011AAJ\u0011%\tI\nAA\u0001\n\u0003\nY\nC\u0005\u0002*\u0002\t\t\u0011\"\u0001\u0002,\"I\u0011q\u0016\u0001\u0002\u0002\u0013\u0005\u0013\u0011\u0017\u0005\n\u0003k\u0003\u0011\u0011!C!\u0003oC\u0011\"!/\u0001\u0003\u0003%\t%a/\t\u0013\u0005u\u0006!!A\u0005B\u0005}vaBAbM!\u0005\u0011Q\u0019\u0004\u0007K\u0019B\t!a2\t\rEKB\u0011AAg\r\u0019\ty-\u0007\u0002\u0002R\"Q\u0011\u0011^\u000e\u0003\u0002\u0003\u0006I!a\u0016\t\rE[B\u0011AAv\u0011%\t\u00190\u0007b\u0001\n\u000b\t)\u0010\u0003\u0005\u0002xf\u0001\u000bQBAw\u0011%\tI0\u0007b\u0001\n\u000b\t)\u0010\u0003\u0005\u0002|f\u0001\u000bQBAw\u0011%\ti0GA\u0001\n\u0003\u000by\u0010C\u0005\u0003\u0004e\t\t\u0011\"!\u0003\u0006!I!QB\r\u0002\u0002\u0013%!q\u0002\u0002\u0017\t&\u001c\u0018M\u00197fI*\u000bg/Y*fe&\fG.\u001b>fe*\u0011q\u0005K\u0001\u000eg\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8\u000b\u0003%\nA!Y6lC\u000e\u00011C\u0002\u0001-eYJD\b\u0005\u0002.a5\taFC\u00010\u0003\u0015\u00198-\u00197b\u0013\t\tdF\u0001\u0004B]f\u0014VM\u001a\t\u0003gQj\u0011AJ\u0005\u0003k\u0019\u0012!bU3sS\u0006d\u0017N_3s!\t\u0019t'\u0003\u00029M\t!\")\u001f;f\u0005V4g-\u001a:TKJL\u0017\r\\5{KJ\u0004\"!\f\u001e\n\u0005mr#a\u0002)s_\u0012,8\r\u001e\t\u0003{\u0015s!AP\"\u000f\u0005}\u0012U\"\u0001!\u000b\u0005\u0005S\u0013A\u0002\u001fs_>$h(C\u00010\u0013\t!e&A\u0004qC\u000e\\\u0017mZ3\n\u0005\u0019;%\u0001D*fe&\fG.\u001b>bE2,'B\u0001#/\u0003\u0019\u0019\u0018p\u001d;f[V\t!\n\u0005\u0002L\u001d6\tAJ\u0003\u0002NQ\u0005)\u0011m\u0019;pe&\u0011q\n\u0014\u0002\u0014\u000bb$XM\u001c3fI\u0006\u001bGo\u001c:TsN$X-\\\u0001\bgf\u001cH/Z7!\u0003\u0019a\u0014N\\5u}Q\u00111\u000b\u0016\t\u0003g\u0001AQ\u0001S\u0002A\u0002)\u000b!\"\u001b3f]RLg-[3s+\u00059\u0006CA\u0017Y\u0013\tIfFA\u0002J]R\f1\"\u001b3f]RLg-[3sA\u0005)Q-\u001c9usB\u0019Q&X0\n\u0005ys#!B!se\u0006L\bCA\u0017a\u0013\t\tgF\u0001\u0003CsR,\u0017a\u00017pOB\u0011AmZ\u0007\u0002K*\u0011a\rK\u0001\u0006KZ,g\u000e^\u0005\u0003Q\u0016\u0014A#T1sW\u0016\u0014Hj\\4hS:<\u0017\tZ1qi\u0016\u0014\u0018aD5oG2,H-Z'b]&4Wm\u001d;\u0016\u0003-\u0004\"!\f7\n\u00055t#a\u0002\"p_2,\u0017M\\\u0001\ti>\u0014\u0015N\\1ssR\u0019\u0001o];\u0011\u00055\n\u0018B\u0001:/\u0005\u0011)f.\u001b;\t\u000bQL\u0001\u0019\u0001\u0017\u0002\u0003=DQA^\u0005A\u0002]\f1AY;g!\tAX0D\u0001z\u0015\tQ80A\u0002oS>T\u0011\u0001`\u0001\u0005U\u00064\u0018-\u0003\u0002\u007fs\nQ!)\u001f;f\u0005V4g-\u001a:\u0002\u0015\u0019\u0014x.\u001c\"j]\u0006\u0014\u0018\u0010F\u0003-\u0003\u0007\t9\u0001\u0003\u0004\u0002\u0006)\u0001\r\u0001X\u0001\u0006Ef$Xm\u001d\u0005\b\u0003\u0013Q\u0001\u0019AA\u0006\u0003\u0015\u0019G.\u0019>{!\u0015i\u0013QBA\t\u0013\r\tyA\f\u0002\u0007\u001fB$\u0018n\u001c81\t\u0005M\u0011q\u0005\t\u0007\u0003+\ti\"a\t\u000f\t\u0005]\u0011\u0011\u0004\t\u0003\u007f9J1!a\u0007/\u0003\u0019\u0001&/\u001a3fM&!\u0011qDA\u0011\u0005\u0015\u0019E.Y:t\u0015\r\tYB\f\t\u0005\u0003K\t9\u0003\u0004\u0001\u0005\u0019\u0005%\u0012qAA\u0001\u0002\u0003\u0015\t!a\u000b\u0003\u0007}#\u0003(\u0005\u0003\u0002.\u0005M\u0002cA\u0017\u00020%\u0019\u0011\u0011\u0007\u0018\u0003\u000f9{G\u000f[5oOB\u0019Q&!\u000e\n\u0007\u0005]bFA\u0002B]fDSACA\u001e\u0003\u001b\u0002R!LA\u001f\u0003\u0003J1!a\u0010/\u0005\u0019!\bN]8xgB!\u00111IA%\u001b\t\t)EC\u0002\u0002Hm\f!![8\n\t\u0005-\u0013Q\t\u0002\u0019\u001d>$8+\u001a:jC2L'0\u00192mK\u0016C8-\u001a9uS>t7EAA!)\u0015a\u0013\u0011KA*\u0011\u001518\u00021\u0001x\u0011\u001d\t)f\u0003a\u0001\u0003/\n\u0001\"\\1oS\u001a,7\u000f\u001e\t\u0005\u0003+\tI&\u0003\u0003\u0002\\\u0005\u0005\"AB*ue&tw\rK\u0003\f\u0003w\ti\u0005F\u0002]\u0003CBQ\u0001\u001e\u0007A\u00021\nAaY8qsR\u00191+a\u001a\t\u000f!k\u0001\u0013!a\u0001\u0015\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nTCAA7U\rQ\u0015qN\u0016\u0003\u0003c\u0002B!a\u001d\u0002~5\u0011\u0011Q\u000f\u0006\u0005\u0003o\nI(A\u0005v]\u000eDWmY6fI*\u0019\u00111\u0010\u0018\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002\u0000\u0005U$!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006i\u0001O]8ek\u000e$\bK]3gSb,\"!!\"\u0011\t\u0005\u001d\u0015QR\u0007\u0003\u0003\u0013S1!a#|\u0003\u0011a\u0017M\\4\n\t\u0005m\u0013\u0011R\u0001\raJ|G-^2u\u0003JLG/_\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\t\u0019$!&\t\u0011\u0005]\u0015#!AA\u0002]\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAAO!\u0019\ty*!*\u000245\u0011\u0011\u0011\u0015\u0006\u0004\u0003Gs\u0013AC2pY2,7\r^5p]&!\u0011qUAQ\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0007-\fi\u000bC\u0005\u0002\u0018N\t\t\u00111\u0001\u00024\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\t))a-\t\u0011\u0005]E#!AA\u0002]\u000b\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002/\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\u0006\u00061Q-];bYN$2a[Aa\u0011%\t9jFA\u0001\u0002\u0004\t\u0019$\u0001\fESN\f'\r\\3e\u0015\u00064\u0018mU3sS\u0006d\u0017N_3s!\t\u0019\u0014d\u0005\u0003\u001aY\u0005%\u0007\u0003BA\"\u0003\u0017L1ARA#)\t\t)M\u0001\u000eKCZ\f7+\u001a:jC2L'0\u0019;j_:,\u0005pY3qi&|gnE\u0003\u001c\u0003'\fI\u000eE\u0002>\u0003+L1!a6H\u0005A\u0011VO\u001c;j[\u0016,\u0005pY3qi&|g\u000e\u0005\u0003\u0002\\\u0006\u0015XBAAo\u0015\u0011\ty.!9\u0002\u000f\r|g\u000e\u001e:pY*\u0019\u00111\u001d\u0018\u0002\tU$\u0018\u000e\\\u0005\u0005\u0003O\fiN\u0001\u0007O_N#\u0018mY6Ue\u0006\u001cW-A\u0002ng\u001e$B!!<\u0002rB\u0019\u0011q^\u000e\u000e\u0003eAq!!;\u001e\u0001\u0004\t9&\u0001\u000bJY2,w-\u00197TKJL\u0017\r\\5{CRLwN\\\u000b\u0003\u0003[\fQ#\u00137mK\u001e\fGnU3sS\u0006d\u0017N_1uS>t\u0007%\u0001\fJY2,w-\u00197EKN,'/[1mSj\fG/[8o\u0003]IE\u000e\\3hC2$Um]3sS\u0006d\u0017N_1uS>t\u0007%A\u0003baBd\u0017\u0010F\u0002T\u0005\u0003AQ\u0001\u0013\u0012A\u0002)\u000bq!\u001e8baBd\u0017\u0010\u0006\u0003\u0003\b\t%\u0001\u0003B\u0017\u0002\u000e)C\u0001Ba\u0003$\u0003\u0003\u0005\raU\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001B\t!\u0011\t9Ia\u0005\n\t\tU\u0011\u0011\u0012\u0002\u0007\u001f\nTWm\u0019;")
public final class DisabledJavaSerializer
implements Serializer,
ByteBufferSerializer,
Product,
Serializable {
    private final ExtendedActorSystem system;
    private final int identifier;
    private final byte[] empty;
    private final MarkerLoggingAdapter log;

    public static Option<ExtendedActorSystem> unapply(DisabledJavaSerializer x$0) {
        return DisabledJavaSerializer$.MODULE$.unapply(x$0);
    }

    public static DisabledJavaSerializer apply(ExtendedActorSystem system) {
        return DisabledJavaSerializer$.MODULE$.apply(system);
    }

    public static JavaSerializationException IllegalDeserialization() {
        return DisabledJavaSerializer$.MODULE$.IllegalDeserialization();
    }

    public static JavaSerializationException IllegalSerialization() {
        return DisabledJavaSerializer$.MODULE$.IllegalSerialization();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public final Object fromBinary(byte[] bytes) {
        return Serializer.fromBinary$(this, bytes);
    }

    @Override
    public final Object fromBinary(byte[] bytes, Class<?> clazz) throws NotSerializableException {
        return Serializer.fromBinary$(this, bytes, clazz);
    }

    public ExtendedActorSystem system() {
        return this.system;
    }

    @Override
    public int identifier() {
        return this.identifier;
    }

    @Override
    public boolean includeManifest() {
        return false;
    }

    @Override
    public void toBinary(Object o, ByteBuffer buf) {
        this.log.warning(LogMarker$.MODULE$.Security(), "Outgoing message attempted to use Java Serialization even though `akka.actor.allow-java-serialization = off` was set! Message type was: [{}]", o.getClass());
        throw DisabledJavaSerializer$.MODULE$.IllegalSerialization();
    }

    @Override
    public Object fromBinary(byte[] bytes, Option<Class<?>> clazz) throws NotSerializableException {
        this.log.warning(LogMarker$.MODULE$.Security(), "Incoming message attempted to use Java Serialization even though `akka.actor.allow-java-serialization = off` was set!");
        throw DisabledJavaSerializer$.MODULE$.IllegalDeserialization();
    }

    @Override
    public Object fromBinary(ByteBuffer buf, String manifest) throws NotSerializableException {
        this.log.warning(LogMarker$.MODULE$.Security(), "Incoming message attempted to use Java Serialization even though `akka.actor.allow-java-serialization = off` was set!");
        throw DisabledJavaSerializer$.MODULE$.IllegalDeserialization();
    }

    @Override
    public byte[] toBinary(Object o) {
        this.toBinary(o, null);
        return this.empty;
    }

    public DisabledJavaSerializer copy(ExtendedActorSystem system) {
        return new DisabledJavaSerializer(system);
    }

    public ExtendedActorSystem copy$default$1() {
        return this.system();
    }

    @Override
    public String productPrefix() {
        return "DisabledJavaSerializer";
    }

    @Override
    public int productArity() {
        return 1;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.system();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof DisabledJavaSerializer;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "system";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof DisabledJavaSerializer)) return false;
        boolean bl = true;
        if (!bl) return false;
        DisabledJavaSerializer disabledJavaSerializer = (DisabledJavaSerializer)x$1;
        ExtendedActorSystem extendedActorSystem = this.system();
        ExtendedActorSystem extendedActorSystem2 = disabledJavaSerializer.system();
        if (extendedActorSystem != null) {
            if (!extendedActorSystem.equals(extendedActorSystem2)) return false;
            return true;
        }
        if (extendedActorSystem2 == null) return true;
        return false;
    }

    public DisabledJavaSerializer(ExtendedActorSystem system) {
        this.system = system;
        Serializer.$init$(this);
        Product.$init$(this);
        this.identifier = BaseSerializer$.MODULE$.identifierFromConfig(JavaSerializer.class, system);
        this.empty = (byte[])Array$.MODULE$.empty(ClassTag$.MODULE$.Byte());
        this.log = Logging$.MODULE$.withMarker(system, DisabledJavaSerializer.class, LogSource$.MODULE$.fromAnyClass());
    }

    public static final class JavaSerializationException
    extends RuntimeException
    implements NoStackTrace {
        @Override
        public /* synthetic */ Throwable scala$util$control$NoStackTrace$$super$fillInStackTrace() {
            return super.fillInStackTrace();
        }

        @Override
        public Throwable fillInStackTrace() {
            return NoStackTrace.fillInStackTrace$(this);
        }

        public JavaSerializationException(String msg) {
            super(msg);
            NoStackTrace.$init$(this);
        }
    }
}

