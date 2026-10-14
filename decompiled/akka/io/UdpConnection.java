/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.Status;
import akka.actor.SupervisorStrategy;
import akka.annotation.InternalApi;
import akka.dispatch.RequiresMessageQueue;
import akka.dispatch.UnboundedMessageQueueSemantics;
import akka.event.LoggingAdapter;
import akka.io.ChannelRegistration;
import akka.io.ChannelRegistry;
import akka.io.Dns$;
import akka.io.Inet;
import akka.io.SelectionHandler$ChannelReadable$;
import akka.io.SelectionHandler$ChannelWritable$;
import akka.io.UdpConnected;
import akka.io.UdpConnected$Connected$;
import akka.io.UdpConnected$Disconnect$;
import akka.io.UdpConnected$Disconnected$;
import akka.io.UdpConnected$ResumeReading$;
import akka.io.UdpConnected$SuspendReading$;
import akka.io.UdpConnectedExt;
import akka.io.UdpConnection$;
import akka.io.dns.DnsProtocol;
import akka.io.dns.DnsProtocol$Resolve$;
import akka.util.ByteString$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import scala.Function0;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.Tuple2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.Nothing$;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0005\rd!B\u000b\u0017\u0001YQ\u0002\u0002C\u001a\u0001\u0005\u0003\u0005\u000b\u0011B\u001b\t\u0011e\u0002!\u0011!Q\u0001\niB\u0001\"\u0010\u0001\u0003\u0002\u0003\u0006IA\u0010\u0005\t\u0003\u0002\u0011\t\u0011)A\u0005\u0005\")\u0001\u000b\u0001C\u0001#\"9q\u000b\u0001a\u0001\n\u0003A\u0006bB0\u0001\u0001\u0004%\t\u0001\u0019\u0005\u0007M\u0002\u0001\u000b\u0015B-\t\u000b\u001d\u0004A\u0011\u00015\t\u000f1\u0004\u0001\u0019!C\u0001[\"9\u0001\u0010\u0001a\u0001\n\u0003I\bBB>\u0001A\u0003&a\u000eC\u0003}\u0001\u0011\u0005Q\u0010C\u0004\u0002\u0006\u0001!\t!a\u0002\t\u000f\u0005\u001d\u0002\u0001\"\u0001\u0002*!9\u0011q\u0007\u0001\u0005\u0002\u0005e\u0002bBA#\u0001\u0011\u0005\u0011q\t\u0005\b\u0003\u001f\u0002AQAA)\u0011\u001d\t\u0019\u0006\u0001C!\u0003#Bq!!\u0016\u0001\t\u0013\t9FA\u0007VIB\u001cuN\u001c8fGRLwN\u001c\u0006\u0003/a\t!![8\u000b\u0003e\tA!Y6lCN)\u0001aG\u0011(UA\u0011AdH\u0007\u0002;)\ta$A\u0003tG\u0006d\u0017-\u0003\u0002!;\t1\u0011I\\=SK\u001a\u0004\"AI\u0013\u000e\u0003\rR!\u0001\n\r\u0002\u000b\u0005\u001cGo\u001c:\n\u0005\u0019\u001a#!B!di>\u0014\bC\u0001\u0012)\u0013\tI3E\u0001\u0007BGR|'\u000fT8hO&tw\rE\u0002,]Aj\u0011\u0001\f\u0006\u0003[a\t\u0001\u0002Z5ta\u0006$8\r[\u0005\u0003_1\u0012ACU3rk&\u0014Xm]'fgN\fw-Z)vKV,\u0007CA\u00162\u0013\t\u0011DF\u0001\u0010V]\n|WO\u001c3fI6+7o]1hKF+X-^3TK6\fg\u000e^5dg\u00069Q\u000f\u001a9D_:t7\u0001\u0001\t\u0003m]j\u0011AF\u0005\u0003qY\u0011q\"\u00163q\u0007>tg.Z2uK\u0012,\u0005\u0010^\u0001\u0010G\"\fgN\\3m%\u0016<\u0017n\u001d;ssB\u0011agO\u0005\u0003yY\u0011qb\u00115b]:,GNU3hSN$(/_\u0001\nG>lW.\u00198eKJ\u0004\"AI \n\u0005\u0001\u001b#\u0001C!di>\u0014(+\u001a4\u0002\u000f\r|gN\\3diB\u00111)\u0014\b\u0003\t.s!!\u0012&\u000f\u0005\u0019KU\"A$\u000b\u0005!#\u0014A\u0002\u001fs_>$h(C\u0001\u001a\u0013\t9\u0002$\u0003\u0002M-\u0005aQ\u000b\u001a9D_:tWm\u0019;fI&\u0011aj\u0014\u0002\b\u0007>tg.Z2u\u0015\tae#\u0001\u0004=S:LGO\u0010\u000b\u0006%N#VK\u0016\t\u0003m\u0001AQaM\u0003A\u0002UBQ!O\u0003A\u0002iBQ!P\u0003A\u0002yBQ!Q\u0003A\u0002\t\u000b1\u0002]3oI&twmU3oIV\t\u0011\f\u0005\u0003\u001d5rs\u0014BA.\u001e\u0005\u0019!V\u000f\u001d7feA\u00111)X\u0005\u0003=>\u0013AaU3oI\u0006y\u0001/\u001a8eS:<7+\u001a8e?\u0012*\u0017\u000f\u0006\u0002bIB\u0011ADY\u0005\u0003Gv\u0011A!\u00168ji\"9QmBA\u0001\u0002\u0004I\u0016a\u0001=%c\u0005a\u0001/\u001a8eS:<7+\u001a8eA\u0005aqO]5uKB+g\u000eZ5oOV\t\u0011\u000e\u0005\u0002\u001dU&\u00111.\b\u0002\b\u0005>|G.Z1o\u0003\u001d\u0019\u0007.\u00198oK2,\u0012A\u001c\t\u0003_Zl\u0011\u0001\u001d\u0006\u0003cJ\f\u0001b\u00195b]:,Gn\u001d\u0006\u0003gR\f1A\\5p\u0015\u0005)\u0018\u0001\u00026bm\u0006L!a\u001e9\u0003\u001f\u0011\u000bG/Y4sC6\u001c\u0005.\u00198oK2\f1b\u00195b]:,Gn\u0018\u0013fcR\u0011\u0011M\u001f\u0005\bK.\t\t\u00111\u0001o\u0003!\u0019\u0007.\u00198oK2\u0004\u0013!\u0003:fg>dg/\u001b8h)\u0005q\bcA@\u0002\u00025\t\u0001!C\u0002\u0002\u0004\u0015\u0012qAU3dK&4X-A\u0005e_\u000e{gN\\3diR\u0019\u0011-!\u0003\t\u000f\u0005-a\u00021\u0001\u0002\u000e\u00059\u0011\r\u001a3sKN\u001c\b\u0003BA\b\u0003+i!!!\u0005\u000b\u0007\u0005MA/A\u0002oKRLA!a\u0006\u0002\u0012\t\t\u0012J\\3u'>\u001c7.\u001a;BI\u0012\u0014Xm]:)\t\u0005%\u00111\u0004\t\u0005\u0003;\t\u0019#\u0004\u0002\u0002 )\u0019\u0011\u0011\u0005\r\u0002\tU$\u0018\u000e\\\u0005\u0005\u0003K\tyB\u0001\u0004v]V\u001cX\rZ\u0001\be\u0016\u001cW-\u001b<f+\t\tY\u0003\u0005\u0004\u001d\u0003[\t\t$Y\u0005\u0004\u0003_i\"a\u0004)beRL\u0017\r\u001c$v]\u000e$\u0018n\u001c8\u0011\u0007q\t\u0019$C\u0002\u00026u\u00111!\u00118z\u0003%\u0019wN\u001c8fGR,G\rF\u0002\u007f\u0003wAq!!\u0010\u0011\u0001\u0004\ty$\u0001\u0007sK\u001eL7\u000f\u001e:bi&|g\u000eE\u00027\u0003\u0003J1!a\u0011\u0017\u0005M\u0019\u0005.\u00198oK2\u0014VmZ5tiJ\fG/[8o\u0003\u0019!wNU3bIR)\u0011-!\u0013\u0002L!9\u0011QH\tA\u0002\u0005}\u0002BBA'#\u0001\u0007a(A\u0004iC:$G.\u001a:\u0002\u000f\u0011|wK]5uKR\t\u0011-\u0001\u0005q_N$8\u000b^8q\u0003Q\u0011X\r]8si\u000e{gN\\3di\u001a\u000b\u0017\u000e\\;sKR\u0019\u0011-!\u0017\t\u0011\u0005mC\u0003\"a\u0001\u0003;\nQ\u0001\u001e5v].\u0004B\u0001HA0C&\u0019\u0011\u0011M\u000f\u0003\u0011q\u0012\u0017P\\1nKz\u0002")
public class UdpConnection
implements Actor,
ActorLogging,
RequiresMessageQueue<UnboundedMessageQueueSemantics> {
    public final UdpConnectedExt akka$io$UdpConnection$$udpConn;
    private final ChannelRegistry channelRegistry;
    public final ActorRef akka$io$UdpConnection$$commander;
    public final UdpConnected.Connect akka$io$UdpConnection$$connect;
    private Tuple2<UdpConnected.Send, ActorRef> pendingSend;
    private DatagramChannel channel;
    private LoggingAdapter akka$actor$ActorLogging$$_log;
    private ActorContext context;
    private ActorRef self;

    @Override
    public LoggingAdapter log() {
        return ActorLogging.log$(this);
    }

    @Override
    public final ActorRef sender() {
        return Actor.sender$(this);
    }

    @Override
    @InternalApi
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Actor.aroundReceive$(this, receive2, msg);
    }

    @Override
    @InternalApi
    public void aroundPreStart() {
        Actor.aroundPreStart$(this);
    }

    @Override
    @InternalApi
    public void aroundPostStop() {
        Actor.aroundPostStop$(this);
    }

    @Override
    @InternalApi
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        Actor.aroundPreRestart$(this, reason, message);
    }

    @Override
    @InternalApi
    public void aroundPostRestart(Throwable reason) {
        Actor.aroundPostRestart$(this, reason);
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return Actor.supervisorStrategy$(this);
    }

    @Override
    public void preStart() throws Exception {
        Actor.preStart$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Actor.preRestart$(this, reason, message);
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        Actor.postRestart$(this, reason);
    }

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }

    @Override
    public LoggingAdapter akka$actor$ActorLogging$$_log() {
        return this.akka$actor$ActorLogging$$_log;
    }

    @Override
    public void akka$actor$ActorLogging$$_log_$eq(LoggingAdapter x$1) {
        this.akka$actor$ActorLogging$$_log = x$1;
    }

    @Override
    public ActorContext context() {
        return this.context;
    }

    @Override
    public final ActorRef self() {
        return this.self;
    }

    @Override
    public void akka$actor$Actor$_setter_$context_$eq(ActorContext x$1) {
        this.context = x$1;
    }

    @Override
    public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
        this.self = x$1;
    }

    public Tuple2<UdpConnected.Send, ActorRef> pendingSend() {
        return this.pendingSend;
    }

    public void pendingSend_$eq(Tuple2<UdpConnected.Send, ActorRef> x$1) {
        this.pendingSend = x$1;
    }

    public boolean writePending() {
        return this.pendingSend() != null;
    }

    public DatagramChannel channel() {
        return this.channel;
    }

    public void channel_$eq(DatagramChannel x$1) {
        this.channel = x$1;
    }

    public PartialFunction<Object, BoxedUnit> resolving() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ UdpConnection $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof DnsProtocol.Resolved) {
                    DnsProtocol.Resolved resolved = (DnsProtocol.Resolved)A1;
                    this.$outer.akka$io$UdpConnection$$reportConnectFailure(() -> $this.$outer.doConnect(new InetSocketAddress(resolved.address(), $this.$outer.akka$io$UdpConnection$$connect.remoteAddress().getPort())));
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Status.Failure) {
                    Status.Failure failure = (Status.Failure)A1;
                    Throwable ex = failure.cause();
                    this.$outer.akka$io$UdpConnection$$reportConnectFailure((Function0<BoxedUnit>)(Function0<Nothing$> & Serializable)() -> {
                        throw new RuntimeException(ex);
                    });
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof DnsProtocol.Resolved) {
                    return true;
                }
                return object instanceof Status.Failure;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1(akka.io.UdpConnection$$anonfun$resolving$1 akka.io.dns.DnsProtocol$Resolved ), $anonfun$applyOrElse$2(java.lang.Throwable )}, serializedLambda);
            }
        };
    }

    public void doConnect(InetSocketAddress address) {
        this.channel_$eq(DatagramChannel.open());
        this.channel().configureBlocking(false);
        DatagramSocket socket = this.channel().socket();
        this.akka$io$UdpConnection$$connect.options().foreach((Function1<Inet.SocketOption, Object> & Serializable)x$1 -> {
            x$1.beforeDatagramBind(socket);
            return BoxedUnit.UNIT;
        });
        this.akka$io$UdpConnection$$connect.localAddress().foreach((Function1<SocketAddress, Object> & Serializable)x$1 -> {
            socket.bind(x$1);
            return BoxedUnit.UNIT;
        });
        this.channel().connect(this.akka$io$UdpConnection$$connect.remoteAddress());
        this.channelRegistry.register(this.channel(), 1, this.self());
        this.log().debug("Successfully connected to [{}]", this.akka$io$UdpConnection$$connect.remoteAddress());
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ UdpConnection $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof ChannelRegistration) {
                    ChannelRegistration channelRegistration = (ChannelRegistration)A1;
                    this.$outer.akka$io$UdpConnection$$connect.options().foreach((Function1<Inet.SocketOption, Object> & Serializable)x0$1 -> {
                        $anonfun$receive$1.$anonfun$applyOrElse$3(this, x0$1);
                        return BoxedUnit.UNIT;
                    });
                    this.$outer.akka$io$UdpConnection$$commander.$bang(UdpConnected$Connected$.MODULE$, this.$outer.self());
                    this.$outer.context().become(this.$outer.connected(channelRegistration), true);
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return object instanceof ChannelRegistration;
            }

            public static final /* synthetic */ void $anonfun$applyOrElse$3($anonfun$receive$1 $this, Inet.SocketOption x0$1) {
                Inet.SocketOption socketOption = x0$1;
                if (socketOption instanceof Inet.SocketOptionV2) {
                    Inet.SocketOptionV2 socketOptionV2 = (Inet.SocketOptionV2)socketOption;
                    socketOptionV2.afterConnect($this.$outer.channel().socket());
                    return;
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$3$adapted(akka.io.UdpConnection$$anonfun$receive$1 akka.io.Inet$SocketOption )}, serializedLambda);
            }
        };
    }

    public PartialFunction<Object, BoxedUnit> connected(ChannelRegistration registration) {
        return new Serializable(this, registration){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ UdpConnection $outer;
            private final ChannelRegistration registration$1;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                boolean bl = false;
                UdpConnected.Send send = null;
                A1 A1 = x1;
                if (UdpConnected$SuspendReading$.MODULE$.equals(A1)) {
                    this.registration$1.disableInterest(1);
                    return (B1)BoxedUnit.UNIT;
                }
                if (UdpConnected$ResumeReading$.MODULE$.equals(A1)) {
                    this.registration$1.enableInterest(1);
                    return (B1)BoxedUnit.UNIT;
                }
                if (SelectionHandler$ChannelReadable$.MODULE$.equals(A1)) {
                    this.$outer.doRead(this.registration$1, this.$outer.akka$io$UdpConnection$$connect.handler());
                    return (B1)BoxedUnit.UNIT;
                }
                if (UdpConnected$Disconnect$.MODULE$.equals(A1)) {
                    this.$outer.log().debug("Closing UDP connection to [{}]", this.$outer.akka$io$UdpConnection$$connect.remoteAddress());
                    this.$outer.channel().close();
                    this.$outer.sender().$bang(UdpConnected$Disconnected$.MODULE$, this.$outer.self());
                    this.$outer.log().debug("Connection closed to [{}], stopping listener", this.$outer.akka$io$UdpConnection$$connect.remoteAddress());
                    this.$outer.context().stop(this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof UdpConnected.Send) {
                    bl = true;
                    send = (UdpConnected.Send)A1;
                    if (this.$outer.writePending()) {
                        if (this.$outer.akka$io$UdpConnection$$udpConn.settings().TraceLogging()) {
                            this.$outer.log().debug("Dropping write because queue is full");
                        }
                        this.$outer.sender().$bang(new UdpConnected.CommandFailed(send), this.$outer.self());
                        return (B1)BoxedUnit.UNIT;
                    }
                }
                if (bl && send.payload().isEmpty()) {
                    if (send.wantsAck()) {
                        this.$outer.sender().$bang(send.ack(), this.$outer.self());
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                if (bl) {
                    this.$outer.pendingSend_$eq(new Tuple2<UdpConnected.Send, ActorRef>(send, this.$outer.sender()));
                    this.registration$1.enableInterest(4);
                    return (B1)BoxedUnit.UNIT;
                }
                if (SelectionHandler$ChannelWritable$.MODULE$.equals(A1)) {
                    this.$outer.doWrite();
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                boolean bl = false;
                UdpConnected.Send send = null;
                Object object = x1;
                if (UdpConnected$SuspendReading$.MODULE$.equals(object)) {
                    return true;
                }
                if (UdpConnected$ResumeReading$.MODULE$.equals(object)) {
                    return true;
                }
                if (SelectionHandler$ChannelReadable$.MODULE$.equals(object)) {
                    return true;
                }
                if (UdpConnected$Disconnect$.MODULE$.equals(object)) {
                    return true;
                }
                if (object instanceof UdpConnected.Send) {
                    bl = true;
                    send = (UdpConnected.Send)object;
                    if (this.$outer.writePending()) {
                        return true;
                    }
                }
                if (bl && send.payload().isEmpty()) {
                    return true;
                }
                if (bl) {
                    return true;
                }
                return SelectionHandler$ChannelWritable$.MODULE$.equals(object);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.registration$1 = registration$1;
            }
        };
    }

    public void doRead(ChannelRegistration registration, ActorRef handler) {
        ByteBuffer buffer = this.akka$io$UdpConnection$$udpConn.bufferPool().acquire();
        try {
            try {
                this.innerRead$1(this.akka$io$UdpConnection$$udpConn.settings().BatchReceiveLimit(), buffer, handler);
            }
            catch (PortUnreachableException portUnreachableException) {
                if (this.akka$io$UdpConnection$$udpConn.settings().TraceLogging()) {
                    this.log().debug("Ignoring PortUnreachableException in doRead");
                }
            }
        }
        finally {
            registration.enableInterest(1);
            this.akka$io$UdpConnection$$udpConn.bufferPool().release(buffer);
        }
    }

    public final void doWrite() {
        ByteBuffer buffer = this.akka$io$UdpConnection$$udpConn.bufferPool().acquire();
        try {
            Tuple2<UdpConnected.Send, ActorRef> tuple2 = this.pendingSend();
            if (tuple2 == null) {
                throw new MatchError(tuple2);
            }
            UdpConnected.Send send = tuple2._1();
            ActorRef commander = tuple2._2();
            Tuple2<UdpConnected.Send, ActorRef> tuple22 = new Tuple2<UdpConnected.Send, ActorRef>(send, commander);
            UdpConnected.Send send2 = tuple22._1();
            ActorRef commander2 = tuple22._2();
            buffer.clear();
            send2.payload().copyToBuffer(buffer);
            buffer.flip();
            int writtenBytes = this.channel().write(buffer);
            if (this.akka$io$UdpConnection$$udpConn.settings().TraceLogging()) {
                this.log().debug("Wrote [{}] bytes to channel", BoxesRunTime.boxToInteger(writtenBytes));
            }
            if (writtenBytes == 0) {
                commander2.$bang(new UdpConnected.CommandFailed(send2), this.self());
            } else if (send2.wantsAck()) {
                commander2.$bang(send2.ack(), this.self());
            }
        }
        finally {
            this.akka$io$UdpConnection$$udpConn.bufferPool().release(buffer);
            this.pendingSend_$eq(null);
        }
    }

    @Override
    public void postStop() {
        if (this.channel() != null && this.channel().isOpen()) {
            this.log().debug("Closing DatagramChannel after being stopped");
            try {
                this.channel().close();
            }
            catch (Throwable throwable) {
                Option<Throwable> option;
                Throwable throwable2 = throwable;
                if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                    Throwable e = option.get();
                    this.log().debug("Error closing DatagramChannel: {}", e);
                }
                throw throwable;
            }
            return;
        }
    }

    public void akka$io$UdpConnection$$reportConnectFailure(Function0<BoxedUnit> thunk) {
        try {
            thunk.apply$mcV$sp();
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e = option.get();
                this.log().debug("Failure while connecting UDP channel to remote address [{}] local address [{}]: {}", this.akka$io$UdpConnection$$connect.remoteAddress(), this.akka$io$UdpConnection$$connect.localAddress().getOrElse((Function0<String> & Serializable)() -> "undefined"), e);
                this.akka$io$UdpConnection$$commander.$bang(new UdpConnected.CommandFailed(this.akka$io$UdpConnection$$connect), this.self());
                this.context().stop(this.self());
            }
            throw throwable;
        }
    }

    private final void innerRead$1(int readsLeft, ByteBuffer buffer, ActorRef handler$1) {
        while (true) {
            buffer.clear();
            buffer.limit(this.akka$io$UdpConnection$$udpConn.settings().DirectBufferSize());
            if (this.channel().read(buffer) <= 0) break;
            buffer.flip();
            handler$1.$bang(new UdpConnected.Received(ByteString$.MODULE$.apply(buffer)), this.self());
            --readsLeft;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public UdpConnection(UdpConnectedExt udpConn, ChannelRegistry channelRegistry, ActorRef commander, UdpConnected.Connect connect2) {
        this.akka$io$UdpConnection$$udpConn = udpConn;
        this.channelRegistry = channelRegistry;
        this.akka$io$UdpConnection$$commander = commander;
        this.akka$io$UdpConnection$$connect = connect2;
        Actor.$init$(this);
        ActorLogging.$init$(this);
        this.pendingSend = null;
        this.context().watch(connect2.handler());
        this.channel = null;
        if (connect2.remoteAddress().isUnresolved()) {
            Option<DnsProtocol.Resolved> option = Dns$.MODULE$.resolve(DnsProtocol$Resolve$.MODULE$.apply(connect2.remoteAddress().getHostName()), this.context().system(), this.self());
            if (option instanceof Some) {
                Some some = (Some)option;
                DnsProtocol.Resolved r = (DnsProtocol.Resolved)some.value();
                this.akka$io$UdpConnection$$reportConnectFailure(() -> this.doConnect(new InetSocketAddress(r.address(), $this.akka$io$UdpConnection$$connect.remoteAddress().getPort())));
            } else {
                if (!None$.MODULE$.equals(option)) throw new MatchError(option);
                this.context().become(this.resolving());
            }
        } else {
            this.akka$io$UdpConnection$$reportConnectFailure(() -> this.doConnect($this.akka$io$UdpConnection$$connect.remoteAddress()));
        }
        Statics.releaseFence();
    }
}

