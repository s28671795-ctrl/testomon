/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.Actor;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.io.ChannelRegistration;
import akka.io.Dns$;
import akka.io.SelectionHandler$ChannelWritable$;
import akka.io.Udp;
import akka.io.UdpExt;
import akka.io.dns.DnsProtocol;
import akka.io.dns.DnsProtocol$Resolve$;
import akka.util.ByteString;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Some;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005i4\u0011BD\b\u0011\u0002\u0007\u0005qb\u00059\t\u000bi\u0001A\u0011\u0001\u000f\t\u000f\u0001\u0002\u0001\u0019!C\u0005C!9\u0001\u0007\u0001a\u0001\n\u0013\t\u0004b\u0002\u001b\u0001\u0001\u0004%I!\u000e\u0005\by\u0001\u0001\r\u0011\"\u0003>\u0011\u001dy\u0004\u00011A\u0005\n\u0001Cq\u0001\u0012\u0001A\u0002\u0013%Q\tC\u0003H\u0001\u0011%\u0001\tC\u0003I\u0001\u0019\u0005\u0011\nC\u0003U\u0001\u0019\u0005Q\u000bC\u0004[\u0001\t\u0007I\u0011A.\t\u000b\u0001\u0004A\u0011A1\t\u000b5\u0004A\u0011\u00028\u0003\u0017]KG\u000f[+eaN+g\u000e\u001a\u0006\u0003!E\t!![8\u000b\u0003I\tA!Y6lCN\u0011\u0001\u0001\u0006\t\u0003+ai\u0011A\u0006\u0006\u0002/\u0005)1oY1mC&\u0011\u0011D\u0006\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%\u0007\u0001!\u0012!\b\t\u0003+yI!a\b\f\u0003\tUs\u0017\u000e^\u0001\fa\u0016tG-\u001b8h'\u0016tG-F\u0001#!\t\u0019SF\u0004\u0002%W9\u0011QE\u000b\b\u0003M%j\u0011a\n\u0006\u0003Qm\ta\u0001\u0010:p_Rt\u0014\"\u0001\n\n\u0005A\t\u0012B\u0001\u0017\u0010\u0003\r)F\r]\u0005\u0003]=\u0012AaU3oI*\u0011AfD\u0001\u0010a\u0016tG-\u001b8h'\u0016tGm\u0018\u0013fcR\u0011QD\r\u0005\bg\r\t\t\u00111\u0001#\u0003\rAH%M\u0001\u0011a\u0016tG-\u001b8h\u0007>lW.\u00198eKJ,\u0012A\u000e\t\u0003oij\u0011\u0001\u000f\u0006\u0003sE\tQ!Y2u_JL!a\u000f\u001d\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\fA\u0003]3oI&twmQ8n[\u0006tG-\u001a:`I\u0015\fHCA\u000f?\u0011\u001d\u0019T!!AA\u0002Y\n1B]3ue&,GmU3oIV\t\u0011\t\u0005\u0002\u0016\u0005&\u00111I\u0006\u0002\b\u0005>|G.Z1o\u0003=\u0011X\r\u001e:jK\u0012\u001cVM\u001c3`I\u0015\fHCA\u000fG\u0011\u001d\u0019t!!AA\u0002\u0005\u000bq\u0002[1t/JLG/\u001a)f]\u0012LgnZ\u0001\bG\"\fgN\\3m+\u0005Q\u0005CA&S\u001b\u0005a%BA'O\u0003!\u0019\u0007.\u00198oK2\u001c(BA(Q\u0003\rq\u0017n\u001c\u0006\u0002#\u0006!!.\u0019<b\u0013\t\u0019FJA\bECR\fwM]1n\u0007\"\fgN\\3m\u0003\r)H\r]\u000b\u0002-B\u0011q\u000bW\u0007\u0002\u001f%\u0011\u0011l\u0004\u0002\u0007+\u0012\u0004X\t\u001f;\u0002\u0011M,G\u000f^5oON,\u0012\u0001\u0018\t\u0003;zs!aV\u0016\n\u0005}{#aC+eaN+G\u000f^5oON\fAb]3oI\"\u000bg\u000e\u001a7feN$\"A\u00195\u0011\u0005\r$W\"\u0001\u0001\n\u0005\u00154'a\u0002*fG\u0016Lg/Z\u0005\u0003Ob\u0012Q!Q2u_JDQ!\u001b\u0007A\u0002)\fAB]3hSN$(/\u0019;j_:\u0004\"aV6\n\u00051|!aE\"iC:tW\r\u001c*fO&\u001cHO]1uS>t\u0017A\u00023p'\u0016tG\r\u0006\u0002\u001e_\")\u0011.\u0004a\u0001UJ\u0019\u0011o\u001d;\u0007\tI\u0004\u0001\u0001\u001d\u0002\ryI,g-\u001b8f[\u0016tGO\u0010\t\u0003/\u0002\u00112!\u001e<x\r\u0011\u0011\b\u0001\u0001;\u0011\u0005]2\u0007CA\u001cy\u0013\tI\bH\u0001\u0007BGR|'\u000fT8hO&tw\r")
public interface WithUdpSend {
    public void akka$io$WithUdpSend$_setter_$settings_$eq(Udp.UdpSettings var1);

    public Udp.Send akka$io$WithUdpSend$$pendingSend();

    public void akka$io$WithUdpSend$$pendingSend_$eq(Udp.Send var1);

    public ActorRef akka$io$WithUdpSend$$pendingCommander();

    public void akka$io$WithUdpSend$$pendingCommander_$eq(ActorRef var1);

    public boolean akka$io$WithUdpSend$$retriedSend();

    public void akka$io$WithUdpSend$$retriedSend_$eq(boolean var1);

    public static /* synthetic */ boolean akka$io$WithUdpSend$$hasWritePending$(WithUdpSend $this) {
        return $this.akka$io$WithUdpSend$$hasWritePending();
    }

    default public boolean akka$io$WithUdpSend$$hasWritePending() {
        return this.akka$io$WithUdpSend$$pendingSend() != null;
    }

    public DatagramChannel channel();

    public UdpExt udp();

    public Udp.UdpSettings settings();

    public static /* synthetic */ PartialFunction sendHandlers$(WithUdpSend $this, ChannelRegistration registration) {
        return $this.sendHandlers(registration);
    }

    default public PartialFunction<Object, BoxedUnit> sendHandlers(ChannelRegistration registration) {
        return new Serializable(this, registration){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ WithUdpSend $outer;
            private final ChannelRegistration registration$1;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                boolean bl = false;
                Udp.Send send = null;
                A1 A1 = x1;
                if (A1 instanceof Udp.Send) {
                    bl = true;
                    send = (Udp.Send)A1;
                    if (this.$outer.akka$io$WithUdpSend$$hasWritePending()) {
                        if (this.$outer.settings().TraceLogging()) {
                            ((ActorLogging)((Object)this.$outer)).log().debug("Dropping write because queue is full");
                        }
                        ((Actor)((Object)this.$outer)).sender().$bang(new Udp.CommandFailed(send), ((Actor)((Object)this.$outer)).self());
                        return (B1)BoxedUnit.UNIT;
                    }
                }
                if (bl && send.payload().isEmpty()) {
                    if (send.wantsAck()) {
                        ((Actor)((Object)this.$outer)).sender().$bang(send.ack(), ((Actor)((Object)this.$outer)).self());
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                if (bl) {
                    this.$outer.akka$io$WithUdpSend$$pendingSend_$eq(send);
                    this.$outer.akka$io$WithUdpSend$$pendingCommander_$eq(((Actor)((Object)this.$outer)).sender());
                    if (send.target().isUnresolved()) {
                        Option<DnsProtocol.Resolved> option = Dns$.MODULE$.resolve(DnsProtocol$Resolve$.MODULE$.apply(send.target().getHostName()), ((Actor)((Object)this.$outer)).context().system(), ((Actor)((Object)this.$outer)).self());
                        if (option instanceof Some) {
                            BoxedUnit boxedUnit;
                            Some some = (Some)option;
                            DnsProtocol.Resolved r = (DnsProtocol.Resolved)some.value();
                            try {
                                Udp.Send qual$1 = this.$outer.akka$io$WithUdpSend$$pendingSend();
                                InetSocketAddress x$1 = new InetSocketAddress(r.address(), this.$outer.akka$io$WithUdpSend$$pendingSend().target().getPort());
                                ByteString x$2 = qual$1.copy$default$1();
                                Udp.Event x$3 = qual$1.copy$default$3();
                                this.$outer.akka$io$WithUdpSend$$pendingSend_$eq(qual$1.copy(x$2, x$1, x$3));
                                this.$outer.akka$io$WithUdpSend$$doSend(this.registration$1);
                                boxedUnit = BoxedUnit.UNIT;
                            }
                            catch (Throwable throwable) {
                                Option<Throwable> option2;
                                Throwable throwable2 = throwable;
                                if (throwable2 == null || (option2 = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                                    throw throwable;
                                }
                                Throwable e = option2.get();
                                ((Actor)((Object)this.$outer)).sender().$bang(new Udp.CommandFailed(send), ((Actor)((Object)this.$outer)).self());
                                ((ActorLogging)((Object)this.$outer)).log().debug("Failure while sending UDP datagram to remote address [{}]: {}", send.target(), e);
                                this.$outer.akka$io$WithUdpSend$$retriedSend_$eq(false);
                                this.$outer.akka$io$WithUdpSend$$pendingSend_$eq(null);
                                this.$outer.akka$io$WithUdpSend$$pendingCommander_$eq(null);
                                boxedUnit = BoxedUnit.UNIT;
                            }
                        } else if (None$.MODULE$.equals(option)) {
                            ((Actor)((Object)this.$outer)).sender().$bang(new Udp.CommandFailed(send), ((Actor)((Object)this.$outer)).self());
                            ((ActorLogging)((Object)this.$outer)).log().debug("Name resolution failed for remote address [{}]", send.target());
                            this.$outer.akka$io$WithUdpSend$$retriedSend_$eq(false);
                            this.$outer.akka$io$WithUdpSend$$pendingSend_$eq(null);
                            this.$outer.akka$io$WithUdpSend$$pendingCommander_$eq(null);
                        } else {
                            throw new MatchError(option);
                        }
                        return (B1)BoxedUnit.UNIT;
                    }
                    this.$outer.akka$io$WithUdpSend$$doSend(this.registration$1);
                    return (B1)BoxedUnit.UNIT;
                }
                if (SelectionHandler$ChannelWritable$.MODULE$.equals(A1)) {
                    if (this.$outer.akka$io$WithUdpSend$$hasWritePending()) {
                        this.$outer.akka$io$WithUdpSend$$doSend(this.registration$1);
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                boolean bl = false;
                Udp.Send send = null;
                Object object = x1;
                if (object instanceof Udp.Send) {
                    bl = true;
                    send = (Udp.Send)object;
                    if (this.$outer.akka$io$WithUdpSend$$hasWritePending()) {
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

    public static /* synthetic */ void akka$io$WithUdpSend$$doSend$(WithUdpSend $this, ChannelRegistration registration) {
        $this.akka$io$WithUdpSend$$doSend(registration);
    }

    default public void akka$io$WithUdpSend$$doSend(ChannelRegistration registration) {
        ByteBuffer buffer = this.udp().bufferPool().acquire();
        try {
            buffer.clear();
            this.akka$io$WithUdpSend$$pendingSend().payload().copyToBuffer(buffer);
            buffer.flip();
            int writtenBytes = this.channel().send(buffer, this.akka$io$WithUdpSend$$pendingSend().target());
            if (this.settings().TraceLogging()) {
                ((ActorLogging)((Object)this)).log().debug("Wrote [{}] bytes to channel", BoxesRunTime.boxToInteger(writtenBytes));
            }
            if (writtenBytes == 0) {
                if (this.akka$io$WithUdpSend$$retriedSend()) {
                    this.akka$io$WithUdpSend$$pendingCommander().$bang(new Udp.CommandFailed(this.akka$io$WithUdpSend$$pendingSend()), ((Actor)((Object)this)).self());
                    this.akka$io$WithUdpSend$$retriedSend_$eq(false);
                    this.akka$io$WithUdpSend$$pendingSend_$eq(null);
                    this.akka$io$WithUdpSend$$pendingCommander_$eq(null);
                } else {
                    registration.enableInterest(4);
                    this.akka$io$WithUdpSend$$retriedSend_$eq(true);
                }
            } else {
                if (this.akka$io$WithUdpSend$$pendingSend().wantsAck()) {
                    this.akka$io$WithUdpSend$$pendingCommander().$bang(this.akka$io$WithUdpSend$$pendingSend().ack(), ((Actor)((Object)this)).self());
                }
                this.akka$io$WithUdpSend$$retriedSend_$eq(false);
                this.akka$io$WithUdpSend$$pendingSend_$eq(null);
                this.akka$io$WithUdpSend$$pendingCommander_$eq(null);
            }
        }
        finally {
            this.udp().bufferPool().release(buffer);
        }
    }

    public static void $init$(WithUdpSend $this) {
        $this.akka$io$WithUdpSend$$pendingSend_$eq(null);
        $this.akka$io$WithUdpSend$$pendingCommander_$eq(null);
        $this.akka$io$WithUdpSend$$retriedSend_$eq(false);
        $this.akka$io$WithUdpSend$_setter_$settings_$eq($this.udp().settings());
    }
}

