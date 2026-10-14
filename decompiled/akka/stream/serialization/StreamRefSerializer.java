/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.serialization;

import akka.actor.ActorRef;
import akka.actor.ExtendedActorSystem;
import akka.annotation.InternalApi;
import akka.protobufv3.internal.ByteString;
import akka.protobufv3.internal.UnsafeByteOperations;
import akka.serialization.BaseSerializer;
import akka.serialization.Serialization;
import akka.serialization.Serialization$;
import akka.serialization.SerializationExtension$;
import akka.serialization.Serializer;
import akka.serialization.SerializerWithStringManifest;
import akka.serialization.Serializers$;
import akka.stream.StreamRefMessages;
import akka.stream.impl.streamref.SinkRefImpl;
import akka.stream.impl.streamref.SourceRefImpl;
import akka.stream.impl.streamref.StreamRefsProtocol;
import akka.stream.impl.streamref.StreamRefsProtocol$Ack$;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import scala.Array$;
import scala.Function0;
import scala.Option$;
import scala.Predef$;
import scala.collection.StringOps$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\u00055h!\u0002\u0010 \u0005\r*\u0003\u0002\u0003\u0018\u0001\u0005\u000b\u0007I\u0011\u0001\u0019\t\u0011]\u0002!\u0011!Q\u0001\nEBQ\u0001\u000f\u0001\u0005\u0002eB\u0001\u0002\t\u0001\t\u0006\u0004&I!\u0010\u0005\u0007\u0003\u0002\u0001\u000b\u0011\u0002\"\t\r)\u0003\u0001\u0015!\u0003C\u0011\u0019Y\u0005\u0001)A\u0005\u0005\"1A\n\u0001Q\u0001\n\tCa!\u0014\u0001!\u0002\u0013\u0011\u0005B\u0002(\u0001A\u0003%!\t\u0003\u0004P\u0001\u0001\u0006IA\u0011\u0005\u0007!\u0002\u0001\u000b\u0011\u0002\"\t\u000bE\u0003A\u0011\t*\t\u000b\u0015\u0004A\u0011\t4\t\u000b9\u0004A\u0011I8\t\u000bM\u0004A\u0011\u0002;\t\u000f\u0005U\u0001\u0001\"\u0003\u0002\u0018!9\u0011Q\u0005\u0001\u0005\n\u0005\u001d\u0002bBA\u001b\u0001\u0011%\u0011q\u0007\u0005\b\u0003\u000b\u0002A\u0011BA$\u0011\u001d\t9\b\u0001C\u0005\u0003sBq!a%\u0001\t\u0013\t)\nC\u0004\u00020\u0002!I!!-\t\u000f\u0005U\u0006\u0001\"\u0003\u00028\"9\u0011Q\u0018\u0001\u0005\n\u0005}\u0006bBAc\u0001\u0011%\u0011q\u0019\u0005\b\u0003\u001b\u0004A\u0011BAh\u0011\u001d\t\u0019\u000e\u0001C\u0005\u0003+Dq!!7\u0001\t\u0013\tYNA\nTiJ,\u0017-\u001c*fMN+'/[1mSj,'O\u0003\u0002!C\u0005i1/\u001a:jC2L'0\u0019;j_:T!AI\u0012\u0002\rM$(/Z1n\u0015\u0005!\u0013\u0001B1lW\u0006\u001c2\u0001\u0001\u0014,!\t9\u0013&D\u0001)\u0015\t\u00013%\u0003\u0002+Q\ta2+\u001a:jC2L'0\u001a:XSRD7\u000b\u001e:j]\u001el\u0015M\\5gKN$\bCA\u0014-\u0013\ti\u0003F\u0001\bCCN,7+\u001a:jC2L'0\u001a:\u0002\rML8\u000f^3n\u0007\u0001)\u0012!\r\t\u0003eUj\u0011a\r\u0006\u0003i\r\nQ!Y2u_JL!AN\u001a\u0003'\u0015CH/\u001a8eK\u0012\f5\r^8s'f\u001cH/Z7\u0002\u000fML8\u000f^3nA\u00051A(\u001b8jiz\"\"A\u000f\u001f\u0011\u0005m\u0002Q\"A\u0010\t\u000b9\u001a\u0001\u0019A\u0019\u0016\u0003y\u0002\"aJ \n\u0005\u0001C#!D*fe&\fG.\u001b>bi&|g.A\fTKF,XM\\2fI>sg*\u001a=u\u001b\u0006t\u0017NZ3tiB\u00111\tS\u0007\u0002\t*\u0011QIR\u0001\u0005Y\u0006twMC\u0001H\u0003\u0011Q\u0017M^1\n\u0005%#%AB*ue&tw-\u0001\rDk6,H.\u0019;jm\u0016$U-\\1oI6\u000bg.\u001b4fgR\f\u0011DU3n_R,7+\u001b8l\r\u0006LG.\u001e:f\u001b\u0006t\u0017NZ3ti\u0006Y\"+Z7pi\u0016\u001c\u0016N\\6D_6\u0004H.\u001a;fI6\u000bg.\u001b4fgR\f\u0011cU8ve\u000e,'+\u001a4NC:Lg-Z:u\u0003=\u0019\u0016N\\6SK\u001al\u0015M\\5gKN$\u0018\u0001H(o'V\u00147o\u0019:jE\u0016D\u0015M\u001c3tQ\u0006\\W-T1oS\u001a,7\u000f^\u0001\f\u0003\u000e\\W*\u00198jM\u0016\u001cH/\u0001\u0005nC:Lg-Z:u)\t\u0019v\f\u0005\u0002U;:\u0011Qk\u0017\t\u0003-fk\u0011a\u0016\u0006\u00031>\na\u0001\u0010:p_Rt$\"\u0001.\u0002\u000bM\u001c\u0017\r\\1\n\u0005qK\u0016A\u0002)sK\u0012,g-\u0003\u0002J=*\u0011A,\u0017\u0005\u0006A6\u0001\r!Y\u0001\u0002_B\u0011!mY\u0007\u00023&\u0011A-\u0017\u0002\u0007\u0003:L(+\u001a4\u0002\u0011Q|')\u001b8bef$\"aZ7\u0011\u0007\tD'.\u0003\u0002j3\n)\u0011I\u001d:bsB\u0011!m[\u0005\u0003Yf\u0013AAQ=uK\")\u0001M\u0004a\u0001C\u0006QaM]8n\u0005&t\u0017M]=\u0015\u0007\u0005\u0004(\u000fC\u0003r\u001f\u0001\u0007q-A\u0003csR,7\u000fC\u0003R\u001f\u0001\u00071+A\rtKJL\u0017\r\\5{K\u000e+X.\u001e7bi&4X\rR3nC:$GCA;~!\t1(P\u0004\u0002xq6\t\u0011%\u0003\u0002zC\u0005\t2\u000b\u001e:fC6\u0014VMZ'fgN\fw-Z:\n\u0005md(\u0001E\"v[Vd\u0017\r^5wK\u0012+W.\u00198e\u0015\tI\u0018\u0005C\u0003\u007f!\u0001\u0007q0A\u0001e!\u0011\t\t!!\u0005\u000f\t\u0005\r\u0011QB\u0007\u0003\u0003\u000bQA!a\u0002\u0002\n\u0005I1\u000f\u001e:fC6\u0014XM\u001a\u0006\u0004\u0003\u0017\t\u0013\u0001B5na2LA!a\u0004\u0002\u0006\u0005\u00112\u000b\u001e:fC6\u0014VMZ:Qe>$xnY8m\u0013\rY\u00181\u0003\u0006\u0005\u0003\u001f\t)!\u0001\u000etKJL\u0017\r\\5{KJ+Wn\u001c;f'&t7NR1jYV\u0014X\r\u0006\u0003\u0002\u001a\u0005}\u0001c\u0001<\u0002\u001c%\u0019\u0011Q\u0004?\u0003'I+Wn\u001c;f'R\u0014X-Y7GC&dWO]3\t\ry\f\u0002\u0019AA\u0011!\u0011\t\t!a\t\n\t\u0005u\u00111C\u0001\u001dg\u0016\u0014\u0018.\u00197ju\u0016\u0014V-\\8uKNKgn[\"p[BdW\r^3e)\u0011\tI#a\f\u0011\u0007Y\fY#C\u0002\u0002.q\u0014QCU3n_R,7\u000b\u001e:fC6\u001cu.\u001c9mKR,G\r\u0003\u0004\u007f%\u0001\u0007\u0011\u0011\u0007\t\u0005\u0003\u0003\t\u0019$\u0003\u0003\u0002.\u0005M\u0011!H:fe&\fG.\u001b>f\u001f:\u001cVOY:de&\u0014W\rS1oIND\u0017m[3\u0015\t\u0005e\u0012q\b\t\u0004m\u0006m\u0012bAA\u001fy\n!rJ\\*vEN\u001c'/\u001b2f\u0011\u0006tGm\u001d5bW\u0016Da\u0001Y\nA\u0002\u0005\u0005\u0003\u0003BA\u0001\u0003\u0007JA!!\u0010\u0002\u0014\u0005A2/\u001a:jC2L'0Z*fcV,gnY3e\u001f:tU\r\u001f;\u0015\t\u0005%\u0013\u0011\f\t\u0005\u0003\u0017\n)FD\u0002\u0002NatA!a\u0014\u0002T9\u0019a+!\u0015\n\u0003\u0011J!AI\u0012\n\u0007\u0005]CPA\bTKF,XM\\2fI>sg*\u001a=u\u0011\u0019\u0001G\u00031\u0001\u0002\\A\"\u0011QLA3!\u0019\t\t!a\u0018\u0002b%!\u0011qKA\n!\u0011\t\u0019'!\u001a\r\u0001\u0011a\u0011qMA-\u0003\u0003\u0005\tQ!\u0001\u0002j\t\u0019q\fJ\u0019\u0012\t\u0005-\u0014\u0011\u000f\t\u0004E\u00065\u0014bAA83\n9aj\u001c;iS:<\u0007c\u00012\u0002t%\u0019\u0011QO-\u0003\u0007\u0005s\u00170\u0001\ttKJL\u0017\r\\5{KNKgn\u001b*fMR!\u00111PAA!\r1\u0018QP\u0005\u0004\u0003\u007fb(aB*j].\u0014VM\u001a\u0005\b\u0003\u0007+\u0002\u0019AAC\u0003\u0011\u0019\u0018N\\61\t\u0005\u001d\u0015q\u0012\t\u0007\u0003\u0007\tI)!$\n\t\u0005-\u0015Q\u0001\u0002\f'&t7NU3g\u00136\u0004H\u000e\u0005\u0003\u0002d\u0005=E\u0001DAI\u0003\u0003\u000b\t\u0011!A\u0003\u0002\u0005%$aA0%e\u0005\u00112/\u001a:jC2L'0Z*pkJ\u001cWMU3g)\u0011\t9*!(\u0011\u0007Y\fI*C\u0002\u0002\u001cr\u0014\u0011bU8ve\u000e,'+\u001a4\t\u000f\u0005}e\u00031\u0001\u0002\"\u000611o\\;sG\u0016\u0004D!a)\u0002,B1\u00111AAS\u0003SKA!a*\u0002\u0006\ti1k\\;sG\u0016\u0014VMZ%na2\u0004B!a\u0019\u0002,\u0012a\u0011QVAO\u0003\u0003\u0005\tQ!\u0001\u0002j\t\u0019q\fJ\u001a\u0002?\u0011,7/\u001a:jC2L'0Z(o'V\u00147o\u0019:jE\u0016D\u0015M\u001c3tQ\u0006\\W\r\u0006\u0003\u0002B\u0005M\u0006\"B9\u0018\u0001\u00049\u0017A\u00053fg\u0016\u0014\u0018.\u00197ju\u0016\u001c\u0016N\\6SK\u001a$B!!/\u0002<B1\u00111AAE\u0003cBQ!\u001d\rA\u0002\u001d\fA\u0003Z3tKJL\u0017\r\\5{KN{WO]2f%\u00164G\u0003BAa\u0003\u0007\u0004b!a\u0001\u0002&\u0006E\u0004\"B9\u001a\u0001\u00049\u0017A\u00073fg\u0016\u0014\u0018.\u00197ju\u0016\u001cV-];f]\u000e,Gm\u00148OKb$H\u0003BAe\u0003\u0017\u0004R!!\u0001\u0002`\u0005DQ!\u001d\u000eA\u0002\u001d\f1\u0004Z3tKJL\u0017\r\\5{K\u000e+X.\u001e7bi&4X\rR3nC:$GcA@\u0002R\")\u0011o\u0007a\u0001O\u0006\u0001C-Z:fe&\fG.\u001b>f%\u0016lw\u000e^3TiJ,\u0017-\\\"p[BdW\r^3e)\u0011\t\t$a6\t\u000bEd\u0002\u0019A4\u0002=\u0011,7/\u001a:jC2L'0\u001a*f[>$Xm\u0015;sK\u0006lg)Y5mkJ,GcA1\u0002^\")\u0011/\ba\u0001O\"\u001a\u0001!!9\u0011\t\u0005\r\u0018\u0011^\u0007\u0003\u0003KT1!a:$\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003W\f)OA\u0006J]R,'O\\1m\u0003BL\u0007")
@InternalApi
public final class StreamRefSerializer
extends SerializerWithStringManifest
implements BaseSerializer {
    private Serialization serialization;
    private final ExtendedActorSystem system;
    private final String SequencedOnNextManifest;
    private final String CumulativeDemandManifest;
    private final String RemoteSinkFailureManifest;
    private final String RemoteSinkCompletedManifest;
    private final String SourceRefManifest;
    private final String SinkRefManifest;
    private final String OnSubscribeHandshakeManifest;
    private final String AckManifest;
    private int identifier;
    private volatile boolean bitmap$0;

    @Override
    @InternalApi
    public int identifierFromConfig() {
        return BaseSerializer.identifierFromConfig$(this);
    }

    @Override
    public int identifier() {
        return this.identifier;
    }

    @Override
    public void akka$serialization$BaseSerializer$_setter_$identifier_$eq(int x$1) {
        this.identifier = x$1;
    }

    @Override
    public ExtendedActorSystem system() {
        return this.system;
    }

    private Serialization serialization$lzycompute() {
        StreamRefSerializer streamRefSerializer = this;
        synchronized (streamRefSerializer) {
            if (!this.bitmap$0) {
                this.serialization = (Serialization)SerializationExtension$.MODULE$.apply(this.system());
                this.bitmap$0 = true;
            }
        }
        return this.serialization;
    }

    private Serialization serialization() {
        if (!this.bitmap$0) {
            return this.serialization$lzycompute();
        }
        return this.serialization;
    }

    @Override
    public String manifest(Object o) {
        Object object = o;
        if (object instanceof StreamRefsProtocol.SequencedOnNext) {
            return this.SequencedOnNextManifest;
        }
        if (object instanceof StreamRefsProtocol.CumulativeDemand) {
            return this.CumulativeDemandManifest;
        }
        if (object instanceof StreamRefsProtocol.OnSubscribeHandshake) {
            return this.OnSubscribeHandshakeManifest;
        }
        if (object instanceof StreamRefsProtocol.RemoteStreamFailure) {
            return this.RemoteSinkFailureManifest;
        }
        if (object instanceof StreamRefsProtocol.RemoteStreamCompleted) {
            return this.RemoteSinkCompletedManifest;
        }
        if (object instanceof SourceRefImpl) {
            return this.SourceRefManifest;
        }
        if (object instanceof SinkRefImpl) {
            return this.SinkRefManifest;
        }
        if (StreamRefsProtocol$Ack$.MODULE$.equals(object)) {
            return this.AckManifest;
        }
        throw new IllegalArgumentException(new StringBuilder(19).append("Unsupported object ").append(object.getClass()).toString());
    }

    @Override
    public byte[] toBinary(Object o) {
        Object object = o;
        if (object instanceof StreamRefsProtocol.SequencedOnNext) {
            StreamRefsProtocol.SequencedOnNext sequencedOnNext = (StreamRefsProtocol.SequencedOnNext)object;
            return this.serializeSequencedOnNext(sequencedOnNext).toByteArray();
        }
        if (object instanceof StreamRefsProtocol.CumulativeDemand) {
            StreamRefsProtocol.CumulativeDemand cumulativeDemand = (StreamRefsProtocol.CumulativeDemand)object;
            return this.serializeCumulativeDemand(cumulativeDemand).toByteArray();
        }
        if (object instanceof StreamRefsProtocol.OnSubscribeHandshake) {
            StreamRefsProtocol.OnSubscribeHandshake onSubscribeHandshake = (StreamRefsProtocol.OnSubscribeHandshake)object;
            return this.serializeOnSubscribeHandshake(onSubscribeHandshake).toByteArray();
        }
        if (object instanceof StreamRefsProtocol.RemoteStreamFailure) {
            StreamRefsProtocol.RemoteStreamFailure remoteStreamFailure = (StreamRefsProtocol.RemoteStreamFailure)object;
            return this.serializeRemoteSinkFailure(remoteStreamFailure).toByteArray();
        }
        if (object instanceof StreamRefsProtocol.RemoteStreamCompleted) {
            StreamRefsProtocol.RemoteStreamCompleted remoteStreamCompleted = (StreamRefsProtocol.RemoteStreamCompleted)object;
            return this.serializeRemoteSinkCompleted(remoteStreamCompleted).toByteArray();
        }
        if (object instanceof SinkRefImpl) {
            SinkRefImpl sinkRefImpl = (SinkRefImpl)object;
            return this.serializeSinkRef(sinkRefImpl).toByteArray();
        }
        if (object instanceof SourceRefImpl) {
            SourceRefImpl sourceRefImpl = (SourceRefImpl)object;
            return this.serializeSourceRef(sourceRefImpl).toByteArray();
        }
        if (StreamRefsProtocol$Ack$.MODULE$.equals(object)) {
            return Array$.MODULE$.emptyByteArray();
        }
        throw new IllegalArgumentException(new StringBuilder(19).append("Unsupported object ").append(object.getClass()).toString());
    }

    @Override
    public Object fromBinary(byte[] bytes, String manifest) {
        String string = manifest;
        String string2 = this.OnSubscribeHandshakeManifest;
        String string3 = string;
        if (!(string2 != null ? !string2.equals(string3) : string3 != null)) {
            return this.deserializeOnSubscribeHandshake(bytes);
        }
        String string4 = this.SequencedOnNextManifest;
        String string5 = string;
        if (!(string4 != null ? !string4.equals(string5) : string5 != null)) {
            return this.deserializeSequencedOnNext(bytes);
        }
        String string6 = this.CumulativeDemandManifest;
        String string7 = string;
        if (!(string6 != null ? !string6.equals(string7) : string7 != null)) {
            return this.deserializeCumulativeDemand(bytes);
        }
        String string8 = this.RemoteSinkCompletedManifest;
        String string9 = string;
        if (!(string8 != null ? !string8.equals(string9) : string9 != null)) {
            return this.deserializeRemoteStreamCompleted(bytes);
        }
        String string10 = this.RemoteSinkFailureManifest;
        String string11 = string;
        if (!(string10 != null ? !string10.equals(string11) : string11 != null)) {
            return this.deserializeRemoteStreamFailure(bytes);
        }
        String string12 = this.SinkRefManifest;
        String string13 = string;
        if (!(string12 != null ? !string12.equals(string13) : string13 != null)) {
            return this.deserializeSinkRef(bytes);
        }
        String string14 = this.SourceRefManifest;
        String string15 = string;
        if (!(string14 != null ? !string14.equals(string15) : string15 != null)) {
            return this.deserializeSourceRef(bytes);
        }
        String string16 = this.AckManifest;
        String string17 = string;
        if (!(string16 != null ? !string16.equals(string17) : string17 != null)) {
            return StreamRefsProtocol$Ack$.MODULE$;
        }
        throw new IllegalArgumentException(new StringBuilder(24).append("Unsupported manifest '").append(string).append("''").toString());
    }

    private StreamRefMessages.CumulativeDemand serializeCumulativeDemand(StreamRefsProtocol.CumulativeDemand d) {
        return StreamRefMessages.CumulativeDemand.newBuilder().setSeqNr(d.seqNr()).build();
    }

    private StreamRefMessages.RemoteStreamFailure serializeRemoteSinkFailure(StreamRefsProtocol.RemoteStreamFailure d) {
        String msg = (String)Option$.MODULE$.apply(d.msg()).getOrElse((Function0<String> & Serializable)() -> d.getClass().getName());
        return StreamRefMessages.RemoteStreamFailure.newBuilder().setCause(UnsafeByteOperations.unsafeWrap(msg.getBytes(StandardCharsets.UTF_8))).build();
    }

    private StreamRefMessages.RemoteStreamCompleted serializeRemoteSinkCompleted(StreamRefsProtocol.RemoteStreamCompleted d) {
        return StreamRefMessages.RemoteStreamCompleted.newBuilder().setSeqNr(d.seqNr()).build();
    }

    private StreamRefMessages.OnSubscribeHandshake serializeOnSubscribeHandshake(StreamRefsProtocol.OnSubscribeHandshake o) {
        return StreamRefMessages.OnSubscribeHandshake.newBuilder().setTargetRef(StreamRefMessages.ActorRef.newBuilder().setPath(Serialization$.MODULE$.serializedActorPath(o.targetRef()))).build();
    }

    private StreamRefMessages.SequencedOnNext serializeSequencedOnNext(StreamRefsProtocol.SequencedOnNext<?> o) {
        Object p = o.payload();
        Serializer msgSerializer = this.serialization().findSerializerFor(p);
        StreamRefMessages.Payload.Builder payloadBuilder = StreamRefMessages.Payload.newBuilder().setEnclosedMessage(UnsafeByteOperations.unsafeWrap(msgSerializer.toBinary(p))).setSerializerId(msgSerializer.identifier());
        String ms = Serializers$.MODULE$.manifestFor(msgSerializer, p);
        Object object = StringOps$.MODULE$.nonEmpty$extension(Predef$.MODULE$.augmentString(ms)) ? payloadBuilder.setMessageManifest(ByteString.copyFromUtf8(ms)) : BoxedUnit.UNIT;
        return StreamRefMessages.SequencedOnNext.newBuilder().setSeqNr(o.seqNr()).setPayload(payloadBuilder.build()).build();
    }

    private StreamRefMessages.SinkRef serializeSinkRef(SinkRefImpl<?> sink2) {
        return StreamRefMessages.SinkRef.newBuilder().setTargetRef(StreamRefMessages.ActorRef.newBuilder().setPath(Serialization$.MODULE$.serializedActorPath(sink2.initialPartnerRef()))).build();
    }

    private StreamRefMessages.SourceRef serializeSourceRef(SourceRefImpl<?> source2) {
        return StreamRefMessages.SourceRef.newBuilder().setOriginRef(StreamRefMessages.ActorRef.newBuilder().setPath(Serialization$.MODULE$.serializedActorPath(source2.initialPartnerRef()))).build();
    }

    private StreamRefsProtocol.OnSubscribeHandshake deserializeOnSubscribeHandshake(byte[] bytes) {
        StreamRefMessages.OnSubscribeHandshake handshake2 = StreamRefMessages.OnSubscribeHandshake.parseFrom(bytes);
        ActorRef targetRef = this.serialization().system().provider().resolveActorRef(handshake2.getTargetRef().getPath());
        return new StreamRefsProtocol.OnSubscribeHandshake(targetRef);
    }

    private SinkRefImpl<Object> deserializeSinkRef(byte[] bytes) {
        StreamRefMessages.SinkRef ref = StreamRefMessages.SinkRef.parseFrom(bytes);
        ActorRef initialTargetRef = this.serialization().system().provider().resolveActorRef(ref.getTargetRef().getPath());
        return new SinkRefImpl<Object>(initialTargetRef);
    }

    private SourceRefImpl<Object> deserializeSourceRef(byte[] bytes) {
        StreamRefMessages.SourceRef ref = StreamRefMessages.SourceRef.parseFrom(bytes);
        ActorRef initialPartnerRef = this.serialization().system().provider().resolveActorRef(ref.getOriginRef().getPath());
        return new SourceRefImpl<Object>(initialPartnerRef);
    }

    private StreamRefsProtocol.SequencedOnNext<Object> deserializeSequencedOnNext(byte[] bytes) {
        StreamRefMessages.SequencedOnNext o = StreamRefMessages.SequencedOnNext.parseFrom(bytes);
        StreamRefMessages.Payload p = o.getPayload();
        Try<Object> payload = this.serialization().deserialize(p.getEnclosedMessage().toByteArray(), p.getSerializerId(), p.getMessageManifest().toStringUtf8());
        return new StreamRefsProtocol.SequencedOnNext<Object>(o.getSeqNr(), payload.get());
    }

    private StreamRefsProtocol.CumulativeDemand deserializeCumulativeDemand(byte[] bytes) {
        StreamRefMessages.CumulativeDemand d = StreamRefMessages.CumulativeDemand.parseFrom(bytes);
        return new StreamRefsProtocol.CumulativeDemand(d.getSeqNr());
    }

    private StreamRefsProtocol.RemoteStreamCompleted deserializeRemoteStreamCompleted(byte[] bytes) {
        StreamRefMessages.RemoteStreamCompleted d = StreamRefMessages.RemoteStreamCompleted.parseFrom(bytes);
        return new StreamRefsProtocol.RemoteStreamCompleted(d.getSeqNr());
    }

    private Object deserializeRemoteStreamFailure(byte[] bytes) {
        StreamRefMessages.RemoteStreamFailure d = StreamRefMessages.RemoteStreamFailure.parseFrom(bytes);
        return new StreamRefsProtocol.RemoteStreamFailure(d.getCause().toStringUtf8());
    }

    public StreamRefSerializer(ExtendedActorSystem system) {
        this.system = system;
        BaseSerializer.$init$(this);
        this.SequencedOnNextManifest = "A";
        this.CumulativeDemandManifest = "B";
        this.RemoteSinkFailureManifest = "C";
        this.RemoteSinkCompletedManifest = "D";
        this.SourceRefManifest = "E";
        this.SinkRefManifest = "F";
        this.OnSubscribeHandshakeManifest = "G";
        this.AckManifest = "H";
        Statics.releaseFence();
    }
}

