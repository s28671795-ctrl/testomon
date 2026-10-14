/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.io.Inet;
import akka.io.UdpConnected;
import akka.io.UdpConnectedMessage$;
import akka.util.ByteString;
import java.net.InetSocketAddress;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005eq!\u0002\b\u0010\u0011\u0003!b!\u0002\f\u0010\u0011\u00039\u0002\"\u0002\u0010\u0002\t\u0003y\u0002\"\u0002\u0011\u0002\t\u0003\t\u0003\"\u0002\u0011\u0002\t\u0003\u0019\u0006\"\u0002\u0011\u0002\t\u00039\u0006\"\u0002.\u0002\t\u0003Y\u0006\"\u0002.\u0002\t\u00031\u0007\"\u00025\u0002\t\u0003I\u0007\"\u00026\u0002\t\u0003Y\u0007\"\u00026\u0002\t\u0003\t\b\"\u0002:\u0002\t\u0003I\u0007\"B:\u0002\t\u0003I\u0007\"\u0002;\u0002\t\u0017)\u0018aE+ea\u000e{gN\\3di\u0016$W*Z:tC\u001e,'B\u0001\t\u0012\u0003\tIwNC\u0001\u0013\u0003\u0011\t7n[1\u0004\u0001A\u0011Q#A\u0007\u0002\u001f\t\u0019R\u000b\u001a9D_:tWm\u0019;fI6+7o]1hKN\u0011\u0011\u0001\u0007\t\u00033qi\u0011A\u0007\u0006\u00027\u0005)1oY1mC&\u0011QD\u0007\u0002\u0007\u0003:L(+\u001a4\u0002\rqJg.\u001b;?)\u0005!\u0012aB2p]:,7\r\u001e\u000b\u0006E%\n4(\u0010\t\u0003G\u0019r!!\u0006\u0013\n\u0005\u0015z\u0011\u0001D+ea\u000e{gN\\3di\u0016$\u0017BA\u0014)\u0005\u001d\u0019u.\\7b]\u0012T!!J\b\t\u000b)\u001a\u0001\u0019A\u0016\u0002\u000f!\fg\u000e\u001a7feB\u0011AfL\u0007\u0002[)\u0011a&E\u0001\u0006C\u000e$xN]\u0005\u0003a5\u0012\u0001\"Q2u_J\u0014VM\u001a\u0005\u0006e\r\u0001\raM\u0001\u000ee\u0016lw\u000e^3BI\u0012\u0014Xm]:\u0011\u0005QJT\"A\u001b\u000b\u0005Y:\u0014a\u00018fi*\t\u0001(\u0001\u0003kCZ\f\u0017B\u0001\u001e6\u0005EIe.\u001a;T_\u000e\\W\r^!eIJ,7o\u001d\u0005\u0006y\r\u0001\raM\u0001\rY>\u001c\u0017\r\\!eIJ,7o\u001d\u0005\u0006}\r\u0001\raP\u0001\b_B$\u0018n\u001c8t!\r\u00015)R\u0007\u0002\u0003*\u0011!iN\u0001\u0005Y\u0006tw-\u0003\u0002E\u0003\nA\u0011\n^3sC\ndW\r\u0005\u0002G!:\u0011qI\u0014\b\u0003\u00116s!!\u0013'\u000e\u0003)S!aS\n\u0002\rq\u0012xn\u001c;?\u0013\u0005\u0011\u0012B\u0001\t\u0012\u0013\tyu\"\u0001\u0003J]\u0016$\u0018BA)S\u00051\u0019vnY6fi>\u0003H/[8o\u0015\tyu\u0002\u0006\u0003#)V3\u0006\"\u0002\u0016\u0005\u0001\u0004Y\u0003\"\u0002\u001a\u0005\u0001\u0004\u0019\u0004\"\u0002 \u0005\u0001\u0004yDc\u0001\u0012Y3\")!&\u0002a\u0001W!)!'\u0002a\u0001g\u0005!1/\u001a8e)\r\u0011C\f\u001a\u0005\u0006;\u001a\u0001\rAX\u0001\u0005I\u0006$\u0018\r\u0005\u0002`E6\t\u0001M\u0003\u0002b#\u0005!Q\u000f^5m\u0013\t\u0019\u0007M\u0001\u0006CsR,7\u000b\u001e:j]\u001eDQ!\u001a\u0004A\u0002a\t1!Y2l)\t\u0011s\rC\u0003^\u000f\u0001\u0007a,\u0001\u0006eSN\u001cwN\u001c8fGR,\u0012AI\u0001\u0006]>\f5m\u001b\u000b\u0003Y>\u0004\"aI7\n\u00059D#!\u0002(p\u0003\u000e\\\u0007\"\u00029\n\u0001\u0004A\u0012!\u0002;pW\u0016tW#\u00017\u0002\u001dM,8\u000f]3oIJ+\u0017\rZ5oO\u0006i!/Z:v[\u0016\u0014V-\u00193j]\u001e\f\u0001B\u001a:p[*\u000bg/Y\u000b\u0004m\u0006\u0005AcA<\u0002\u0014A\u0019\u00010 @\u000e\u0003eT!A_>\u0002\u0013%lW.\u001e;bE2,'B\u0001?\u001b\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003\tf\u00042a`A\u0001\u0019\u0001!q!a\u0001\u000e\u0005\u0004\t)AA\u0001U#\u0011\t9!!\u0004\u0011\u0007e\tI!C\u0002\u0002\fi\u0011qAT8uQ&tw\rE\u0002\u001a\u0003\u001fI1!!\u0005\u001b\u0005\r\te.\u001f\u0005\b\u0003+i\u0001\u0019AA\f\u0003\u0011\u0019w\u000e\u001c7\u0011\u0007\u0001\u001be\u0010")
public final class UdpConnectedMessage {
    public static UdpConnected.Command resumeReading() {
        return UdpConnectedMessage$.MODULE$.resumeReading();
    }

    public static UdpConnected.Command suspendReading() {
        return UdpConnectedMessage$.MODULE$.suspendReading();
    }

    public static UdpConnected.NoAck noAck() {
        return UdpConnectedMessage$.MODULE$.noAck();
    }

    public static UdpConnected.NoAck noAck(Object token) {
        return UdpConnectedMessage$.MODULE$.noAck(token);
    }

    public static UdpConnected.Command disconnect() {
        return UdpConnectedMessage$.MODULE$.disconnect();
    }

    public static UdpConnected.Command send(ByteString data) {
        return UdpConnectedMessage$.MODULE$.send(data);
    }

    public static UdpConnected.Command send(ByteString data, Object ack) {
        return UdpConnectedMessage$.MODULE$.send(data, ack);
    }

    public static UdpConnected.Command connect(ActorRef handler, InetSocketAddress remoteAddress) {
        return UdpConnectedMessage$.MODULE$.connect(handler, remoteAddress);
    }

    public static UdpConnected.Command connect(ActorRef handler, InetSocketAddress remoteAddress, Iterable<Inet.SocketOption> options) {
        return UdpConnectedMessage$.MODULE$.connect(handler, remoteAddress, options);
    }

    public static UdpConnected.Command connect(ActorRef handler, InetSocketAddress remoteAddress, InetSocketAddress localAddress, Iterable<Inet.SocketOption> options) {
        return UdpConnectedMessage$.MODULE$.connect(handler, remoteAddress, localAddress, options);
    }
}

