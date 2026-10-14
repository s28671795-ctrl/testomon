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
import akka.io.Udp;
import akka.io.Udp$SimpleSenderReady$;
import akka.io.UdpExt;
import akka.io.UdpSender$;
import akka.io.WithUdpSend;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.net.DatagramSocket;
import java.nio.channels.DatagramChannel;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.collection.immutable.Iterable;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.runtime.Statics;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005a4Qa\u0003\u0007\u0001\u0019AA\u0001\"\f\u0001\u0003\u0006\u0004%\ta\f\u0005\tg\u0001\u0011\t\u0011)A\u0005a!AA\u0007\u0001B\u0001B\u0003%Q\u0007\u0003\u00059\u0001\t\u0005\t\u0015!\u0003:\u0011!a\u0004A!A!\u0002\u0013i\u0004\"B-\u0001\t\u0003Q\u0006b\u00021\u0001\u0005\u0004%\t!\u0019\u0005\u0007Y\u0002\u0001\u000b\u0011\u00022\t\u000b5\u0004A\u0011\u00018\t\u000bM\u0004A\u0011\t;\u0003\u0013U#\u0007oU3oI\u0016\u0014(BA\u0007\u000f\u0003\tIwNC\u0001\u0010\u0003\u0011\t7n[1\u0014\r\u0001\tr#\b\u0011%!\t\u0011R#D\u0001\u0014\u0015\u0005!\u0012!B:dC2\f\u0017B\u0001\f\u0014\u0005\u0019\te.\u001f*fMB\u0011\u0001dG\u0007\u00023)\u0011!DD\u0001\u0006C\u000e$xN]\u0005\u00039e\u0011Q!Q2u_J\u0004\"\u0001\u0007\u0010\n\u0005}I\"\u0001D!di>\u0014Hj\\4hS:<\u0007CA\u0011#\u001b\u0005a\u0011BA\u0012\r\u0005-9\u0016\u000e\u001e5VIB\u001cVM\u001c3\u0011\u0007\u0015B#&D\u0001'\u0015\t9c\"\u0001\u0005eSN\u0004\u0018\r^2i\u0013\tIcE\u0001\u000bSKF,\u0018N]3t\u001b\u0016\u001c8/Y4f#V,W/\u001a\t\u0003K-J!\u0001\f\u0014\u0003=Us'm\\;oI\u0016$W*Z:tC\u001e,\u0017+^3vKN+W.\u00198uS\u000e\u001c\u0018aA;ea\u000e\u0001Q#\u0001\u0019\u0011\u0005\u0005\n\u0014B\u0001\u001a\r\u0005\u0019)F\r]#yi\u0006!Q\u000f\u001a9!\u0003=\u0019\u0007.\u00198oK2\u0014VmZ5tiJL\bCA\u00117\u0013\t9DBA\bDQ\u0006tg.\u001a7SK\u001eL7\u000f\u001e:z\u0003%\u0019w.\\7b]\u0012,'\u000f\u0005\u0002\u0019u%\u00111(\u0007\u0002\t\u0003\u000e$xN\u001d*fM\u00069q\u000e\u001d;j_:\u001c\bc\u0001 I\u0017:\u0011q(\u0012\b\u0003\u0001\u000ek\u0011!\u0011\u0006\u0003\u0005N\t!bY8mY\u0016\u001cG/[8o\u0013\t!\u0015)A\u0005j[6,H/\u00192mK&\u0011aiR\u0001\ba\u0006\u001c7.Y4f\u0015\t!\u0015)\u0003\u0002J\u0015\nYAK]1wKJ\u001c\u0018M\u00197f\u0015\t1u\t\u0005\u0002M-:\u0011Q\n\u0016\b\u0003\u001dNs!a\u0014*\u000e\u0003AS!!\u0015\u0018\u0002\rq\u0012xn\u001c;?\u0013\u0005y\u0011BA\u0007\u000f\u0013\t)F\"\u0001\u0003J]\u0016$\u0018BA,Y\u00051\u0019vnY6fi>\u0003H/[8o\u0015\t)F\"\u0001\u0004=S:LGO\u0010\u000b\u00067rkfl\u0018\t\u0003C\u0001AQ!\f\u0004A\u0002ABQ\u0001\u000e\u0004A\u0002UBQ\u0001\u000f\u0004A\u0002eBQ\u0001\u0010\u0004A\u0002u\nqa\u00195b]:,G.F\u0001c!\t\u0019'.D\u0001e\u0015\t)g-\u0001\u0005dQ\u0006tg.\u001a7t\u0015\t9\u0007.A\u0002oS>T\u0011![\u0001\u0005U\u00064\u0018-\u0003\u0002lI\nyA)\u0019;bOJ\fWn\u00115b]:,G.\u0001\u0005dQ\u0006tg.\u001a7!\u0003\u001d\u0011XmY3jm\u0016,\u0012a\u001c\t\u0003aFl\u0011\u0001A\u0005\u0003en\u0011qAU3dK&4X-\u0001\u0005q_N$8\u000b^8q)\u0005)\bC\u0001\nw\u0013\t98C\u0001\u0003V]&$\b")
public class UdpSender
implements Actor,
ActorLogging,
WithUdpSend,
RequiresMessageQueue<UnboundedMessageQueueSemantics> {
    private final UdpExt udp;
    public final ActorRef akka$io$UdpSender$$commander;
    public final Iterable<Inet.SocketOption> akka$io$UdpSender$$options;
    private final DatagramChannel channel;
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

    @Override
    public DatagramChannel channel() {
        return this.channel;
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ UdpSender $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof ChannelRegistration) {
                    ChannelRegistration channelRegistration = (ChannelRegistration)A1;
                    this.$outer.akka$io$UdpSender$$options.foreach((Function1<Inet.SocketOption, Object> & Serializable)x0$1 -> {
                        $anonfun$receive$1.$anonfun$applyOrElse$1(this, x0$1);
                        return BoxedUnit.UNIT;
                    });
                    this.$outer.akka$io$UdpSender$$commander.$bang(Udp$SimpleSenderReady$.MODULE$, this.$outer.self());
                    this.$outer.context().become(this.$outer.sendHandlers(channelRegistration));
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return object instanceof ChannelRegistration;
            }

            public static final /* synthetic */ void $anonfun$applyOrElse$1($anonfun$receive$1 $this, Inet.SocketOption x0$1) {
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
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1$adapted(akka.io.UdpSender$$anonfun$receive$1 akka.io.Inet$SocketOption )}, serializedLambda);
            }
        };
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

    public UdpSender(UdpExt udp, ChannelRegistry channelRegistry, ActorRef commander, Iterable<Inet.SocketOption> options) {
        this.udp = udp;
        this.akka$io$UdpSender$$commander = commander;
        this.akka$io$UdpSender$$options = options;
        Actor.$init$(this);
        ActorLogging.$init$(this);
        WithUdpSend.$init$(this);
        DatagramChannel datagramChannel = ((Inet.DatagramChannelCreator)options.collectFirst(new Serializable(null){
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
        datagramChannel.configureBlocking(false);
        DatagramSocket socket = datagramChannel.socket();
        options.foreach((Function1<Inet.SocketOption, Object> & Serializable)x$1 -> {
            x$1.beforeDatagramBind(socket);
            return BoxedUnit.UNIT;
        });
        this.channel = datagramChannel;
        channelRegistry.register(this.channel(), 0, this.self());
        Statics.releaseFence();
    }
}

