/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.SupervisorStrategy;
import akka.annotation.InternalApi;
import akka.dispatch.RequiresMessageQueue;
import akka.dispatch.UnboundedMessageQueueSemantics;
import akka.event.LoggingAdapter;
import akka.io.ChannelRegistration;
import akka.io.ChannelRegistry;
import akka.io.Inet;
import akka.io.Inet$DatagramChannelCreator$;
import akka.io.SelectionHandler$ChannelReadable$;
import akka.io.Udp;
import akka.io.Udp$ResumeReading$;
import akka.io.Udp$SuspendReading$;
import akka.io.Udp$Unbind$;
import akka.io.Udp$Unbound$;
import akka.io.UdpExt;
import akka.io.WithUdpSend;
import akka.util.ByteString$;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0005Ea!B\t\u0013\u0001I1\u0002\u0002C\u001a\u0001\u0005\u000b\u0007I\u0011A\u001b\t\u0011e\u0002!\u0011!Q\u0001\nYB\u0001B\u000f\u0001\u0003\u0002\u0003\u0006Ia\u000f\u0005\t}\u0001\u0011\t\u0011)A\u0005\u007f!A!\t\u0001B\u0001B\u0003%1\tC\u0003R\u0001\u0011\u0005!\u000bC\u0003Y\u0001\u0011\u0005\u0011\fC\u0004[\u0001\t\u0007I\u0011A.\t\r\u0019\u0004\u0001\u0015!\u0003]\u0011\u001d9\u0007A1A\u0005\u0002!Da\u0001\u001c\u0001!\u0002\u0013I\u0007\"B7\u0001\t\u0003q\u0007\"B:\u0001\t\u0003!\b\"\u0002>\u0001\t\u0003Y\b\"\u0002@\u0001\t\u0003y\bbBA\u0007\u0001\u0011\u0005\u0013q\u0002\u0002\f+\u0012\u0004H*[:uK:,'O\u0003\u0002\u0014)\u0005\u0011\u0011n\u001c\u0006\u0002+\u0005!\u0011m[6b'\u0019\u0001q#H\u0012'UA\u0011\u0001dG\u0007\u00023)\t!$A\u0003tG\u0006d\u0017-\u0003\u0002\u001d3\t1\u0011I\\=SK\u001a\u0004\"AH\u0011\u000e\u0003}Q!\u0001\t\u000b\u0002\u000b\u0005\u001cGo\u001c:\n\u0005\tz\"!B!di>\u0014\bC\u0001\u0010%\u0013\t)sD\u0001\u0007BGR|'\u000fT8hO&tw\r\u0005\u0002(Q5\t!#\u0003\u0002*%\tYq+\u001b;i+\u0012\u00048+\u001a8e!\rYc\u0006M\u0007\u0002Y)\u0011Q\u0006F\u0001\tI&\u001c\b/\u0019;dQ&\u0011q\u0006\f\u0002\u0015%\u0016\fX/\u001b:fg6+7o]1hKF+X-^3\u0011\u0005-\n\u0014B\u0001\u001a-\u0005y)fNY8v]\u0012,G-T3tg\u0006<W-U;fk\u0016\u001cV-\\1oi&\u001c7/A\u0002vIB\u001c\u0001!F\u00017!\t9s'\u0003\u00029%\t1Q\u000b\u001a9FqR\fA!\u001e3qA\u0005y1\r[1o]\u0016d'+Z4jgR\u0014\u0018\u0010\u0005\u0002(y%\u0011QH\u0005\u0002\u0010\u0007\"\fgN\\3m%\u0016<\u0017n\u001d;ss\u0006i!-\u001b8e\u0007>lW.\u00198eKJ\u0004\"A\b!\n\u0005\u0005{\"\u0001C!di>\u0014(+\u001a4\u0002\t\tLg\u000e\u001a\t\u0003\t:s!!\u0012'\u000f\u0005\u0019[eBA$K\u001b\u0005A%BA%5\u0003\u0019a$o\\8u}%\tQ#\u0003\u0002\u0014)%\u0011QJE\u0001\u0004+\u0012\u0004\u0018BA(Q\u0005\u0011\u0011\u0015N\u001c3\u000b\u00055\u0013\u0012A\u0002\u001fj]&$h\bF\u0003T)V3v\u000b\u0005\u0002(\u0001!)1G\u0002a\u0001m!)!H\u0002a\u0001w!)aH\u0002a\u0001\u007f!)!I\u0002a\u0001\u0007\u0006A1/\u001a7fGR|'/F\u0001@\u0003\u001d\u0019\u0007.\u00198oK2,\u0012\u0001\u0018\t\u0003;\u0012l\u0011A\u0018\u0006\u0003?\u0002\f\u0001b\u00195b]:,Gn\u001d\u0006\u0003C\n\f1A\\5p\u0015\u0005\u0019\u0017\u0001\u00026bm\u0006L!!\u001a0\u0003\u001f\u0011\u000bG/Y4sC6\u001c\u0005.\u00198oK2\f\u0001b\u00195b]:,G\u000eI\u0001\rY>\u001c\u0017\r\\!eIJ,7o]\u000b\u0002SB\u0011\u0001D[\u0005\u0003Wf\u00111!\u00118z\u00035awnY1m\u0003\u0012$'/Z:tA\u00059!/Z2fSZ,W#A8\u0011\u0005A\fX\"\u0001\u0001\n\u0005I\f#a\u0002*fG\u0016Lg/Z\u0001\re\u0016\fG\rS1oI2,'o\u001d\u000b\u0003_VDQA^\u0007A\u0002]\fAB]3hSN$(/\u0019;j_:\u0004\"a\n=\n\u0005e\u0014\"aE\"iC:tW\r\u001c*fO&\u001cHO]1uS>t\u0017!D;oe\u0016<\u0017n\u001d;fe&tw\r\u0006\u0002py\")QP\u0004a\u0001\u007f\u0005I!/Z9vKN$XM]\u0001\nI>\u0014VmY3jm\u0016$b!!\u0001\u0002\b\u0005%\u0001c\u0001\r\u0002\u0004%\u0019\u0011QA\r\u0003\tUs\u0017\u000e\u001e\u0005\u0006m>\u0001\ra\u001e\u0005\u0007\u0003\u0017y\u0001\u0019A \u0002\u000f!\fg\u000e\u001a7fe\u0006A\u0001o\\:u'R|\u0007\u000f\u0006\u0002\u0002\u0002\u0001")
public class UdpListener
implements Actor,
ActorLogging,
WithUdpSend,
RequiresMessageQueue<UnboundedMessageQueueSemantics> {
    private final UdpExt udp;
    private final ChannelRegistry channelRegistry;
    public final ActorRef akka$io$UdpListener$$bindCommander;
    public final Udp.Bind akka$io$UdpListener$$bind;
    private final DatagramChannel channel;
    private final Object localAddress;
    private Udp.Send akka$io$WithUdpSend$$pendingSend;
    private ActorRef akka$io$WithUdpSend$$pendingCommander;
    private boolean akka$io$WithUdpSend$$retriedSend;
    private Udp.UdpSettings settings;
    private LoggingAdapter akka$actor$ActorLogging$$_log;
    private ActorContext context;
    private ActorRef self;

    @Override
    public PartialFunction<Object, BoxedUnit> sendHandlers(ChannelRegistration registration) {
        return WithUdpSend.sendHandlers$(this, registration);
    }

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
    public Udp.Send akka$io$WithUdpSend$$pendingSend() {
        return this.akka$io$WithUdpSend$$pendingSend;
    }

    @Override
    public void akka$io$WithUdpSend$$pendingSend_$eq(Udp.Send x$1) {
        this.akka$io$WithUdpSend$$pendingSend = x$1;
    }

    @Override
    public ActorRef akka$io$WithUdpSend$$pendingCommander() {
        return this.akka$io$WithUdpSend$$pendingCommander;
    }

    @Override
    public void akka$io$WithUdpSend$$pendingCommander_$eq(ActorRef x$1) {
        this.akka$io$WithUdpSend$$pendingCommander = x$1;
    }

    @Override
    public boolean akka$io$WithUdpSend$$retriedSend() {
        return this.akka$io$WithUdpSend$$retriedSend;
    }

    @Override
    public void akka$io$WithUdpSend$$retriedSend_$eq(boolean x$1) {
        this.akka$io$WithUdpSend$$retriedSend = x$1;
    }

    @Override
    public Udp.UdpSettings settings() {
        return this.settings;
    }

    @Override
    public void akka$io$WithUdpSend$_setter_$settings_$eq(Udp.UdpSettings x$1) {
        this.settings = x$1;
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

    @Override
    public UdpExt udp() {
        return this.udp;
    }

    public ActorRef selector() {
        return this.context().parent();
    }

    @Override
    public DatagramChannel channel() {
        return this.channel;
    }

    public Object localAddress() {
        return this.localAddress;
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ UdpListener $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof ChannelRegistration) {
                    ChannelRegistration channelRegistration = (ChannelRegistration)A1;
                    this.$outer.akka$io$UdpListener$$bindCommander.$bang(new Udp.Bound((InetSocketAddress)this.$outer.channel().socket().getLocalSocketAddress()), this.$outer.self());
                    this.$outer.context().become(this.$outer.readHandlers(channelRegistration).orElse(this.$outer.sendHandlers(channelRegistration)), true);
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return object instanceof ChannelRegistration;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public PartialFunction<Object, BoxedUnit> readHandlers(ChannelRegistration registration) {
        return new Serializable(this, registration){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ UdpListener $outer;
            private final ChannelRegistration registration$1;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (Udp$SuspendReading$.MODULE$.equals(A1)) {
                    this.registration$1.disableInterest(1);
                    return (B1)BoxedUnit.UNIT;
                }
                if (Udp$ResumeReading$.MODULE$.equals(A1)) {
                    this.registration$1.enableInterest(1);
                    return (B1)BoxedUnit.UNIT;
                }
                if (SelectionHandler$ChannelReadable$.MODULE$.equals(A1)) {
                    this.$outer.doReceive(this.registration$1, this.$outer.akka$io$UdpListener$$bind.handler());
                    return (B1)BoxedUnit.UNIT;
                }
                if (Udp$Unbind$.MODULE$.equals(A1)) {
                    this.$outer.log().debug("Unbinding endpoint [{}]", this.$outer.akka$io$UdpListener$$bind.localAddress());
                    this.registration$1.cancelAndClose(() -> $this.$outer.self().$bang(Udp$Unbound$.MODULE$, $this.$outer.self()));
                    this.$outer.context().become(this.$outer.unregistering(this.$outer.sender()));
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (Udp$SuspendReading$.MODULE$.equals(object)) {
                    return true;
                }
                if (Udp$ResumeReading$.MODULE$.equals(object)) {
                    return true;
                }
                if (SelectionHandler$ChannelReadable$.MODULE$.equals(object)) {
                    return true;
                }
                return Udp$Unbind$.MODULE$.equals(object);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.registration$1 = registration$1;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1(akka.io.UdpListener$$anonfun$readHandlers$1 )}, serializedLambda);
            }
        };
    }

    public PartialFunction<Object, BoxedUnit> unregistering(ActorRef requester) {
        return new Serializable(this, requester){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ UdpListener $outer;
            private final ActorRef requester$1;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (Udp$Unbound$.MODULE$.equals(A1)) {
                    this.$outer.log().debug("Unbound endpoint [{}], stopping listener", this.$outer.akka$io$UdpListener$$bind.localAddress());
                    this.requester$1.$bang(Udp$Unbound$.MODULE$, this.$outer.self());
                    this.$outer.context().stop(this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return Udp$Unbound$.MODULE$.equals(object);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.requester$1 = requester$1;
            }
        };
    }

    public void doReceive(ChannelRegistration registration, ActorRef handler) {
        ByteBuffer buffer = this.udp().bufferPool().acquire();
        try {
            this.innerReceive$1(this.udp().settings().BatchReceiveLimit(), buffer, handler);
        }
        finally {
            this.udp().bufferPool().release(buffer);
            registration.enableInterest(1);
        }
    }

    @Override
    public void postStop() {
        if (this.channel().isOpen()) {
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

    public static final /* synthetic */ void $anonfun$localAddress$2(UdpListener $this, Inet.SocketOption x0$1) {
        Inet.SocketOption socketOption = x0$1;
        if (socketOption instanceof Inet.SocketOptionV2) {
            Inet.SocketOptionV2 socketOptionV2 = (Inet.SocketOptionV2)socketOption;
            socketOptionV2.afterBind($this.channel().socket());
            return;
        }
    }

    private final /* synthetic */ Object liftedTree1$1() {
        Serializable serializable;
        try {
            DatagramSocket socket = this.channel().socket();
            this.akka$io$UdpListener$$bind.options().foreach((Function1<Inet.SocketOption, Object> & Serializable)x$1 -> {
                x$1.beforeDatagramBind(socket);
                return BoxedUnit.UNIT;
            });
            socket.bind(this.akka$io$UdpListener$$bind.localAddress());
            SocketAddress socketAddress = socket.getLocalSocketAddress();
            if (!(socketAddress instanceof InetSocketAddress)) {
                throw new IllegalArgumentException(new StringBuilder(33).append("bound to unknown SocketAddress [").append(socketAddress).append("]").toString());
            }
            InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
            InetSocketAddress ret = inetSocketAddress;
            this.channelRegistry.register(this.channel(), 1, this.self());
            this.log().debug("Successfully bound to [{}]", ret);
            this.akka$io$UdpListener$$bind.options().foreach((Function1<Inet.SocketOption, Object> & Serializable)x0$1 -> {
                UdpListener.$anonfun$localAddress$2(this, x0$1);
                return BoxedUnit.UNIT;
            });
            serializable = ret;
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e = option.get();
                this.akka$io$UdpListener$$bindCommander.$bang(new Udp.CommandFailed(this.akka$io$UdpListener$$bind), this.self());
                this.log().error(e, "Failed to bind UDP channel to endpoint [{}]", (Object)this.akka$io$UdpListener$$bind.localAddress());
                this.context().stop(this.self());
                serializable = BoxedUnit.UNIT;
            }
            throw throwable;
        }
        return serializable;
    }

    private final void innerReceive$1(int readsLeft, ByteBuffer buffer, ActorRef handler$1) {
        block4: {
            SocketAddress socketAddress;
            block3: {
                while (true) {
                    buffer.clear();
                    buffer.limit(this.udp().settings().DirectBufferSize());
                    socketAddress = this.channel().receive(buffer);
                    if (!(socketAddress instanceof InetSocketAddress)) break block3;
                    InetSocketAddress inetSocketAddress = (InetSocketAddress)socketAddress;
                    buffer.flip();
                    handler$1.$bang(new Udp.Received(ByteString$.MODULE$.apply(buffer), inetSocketAddress), this.self());
                    if (readsLeft <= 0) break;
                    --readsLeft;
                }
                break block4;
            }
            if (socketAddress == null) {
            } else {
                throw new RuntimeException(new StringBuilder(30).append("Unexpected address in buffer: ").append(socketAddress).toString());
            }
        }
    }

    public UdpListener(UdpExt udp, ChannelRegistry channelRegistry, ActorRef bindCommander, Udp.Bind bind) {
        this.udp = udp;
        this.channelRegistry = channelRegistry;
        this.akka$io$UdpListener$$bindCommander = bindCommander;
        this.akka$io$UdpListener$$bind = bind;
        Actor.$init$(this);
        ActorLogging.$init$(this);
        WithUdpSend.$init$(this);
        this.context().watch(bind.handler());
        this.channel = ((Inet.DatagramChannelCreator)bind.options().collectFirst(new Serializable(null){
            private static final long serialVersionUID = 0L;

            public final <A1 extends Inet.SocketOption, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Inet.DatagramChannelCreator) {
                    Inet.DatagramChannelCreator datagramChannelCreator = (Inet.DatagramChannelCreator)A1;
                    return (B1)datagramChannelCreator;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Inet.SocketOption x1) {
                Inet.SocketOption socketOption = x1;
                return socketOption instanceof Inet.DatagramChannelCreator;
            }
        }).getOrElse((Function0<Inet.DatagramChannelCreator> & Serializable)() -> Inet$DatagramChannelCreator$.MODULE$.apply())).create();
        this.channel().configureBlocking(false);
        this.localAddress = this.liftedTree1$1();
        Statics.releaseFence();
    }
}

