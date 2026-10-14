/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.io.Inet;
import akka.io.Udp;
import akka.io.UdpMessage$;
import akka.util.ByteString;
import java.net.InetSocketAddress;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005]<QAD\b\t\u0002Q1QAF\b\t\u0002]AQAH\u0001\u0005\u0002}AQ\u0001I\u0001\u0005\u0002\u0005BQ\u0001I\u0001\u0005\u0002-BQ\u0001L\u0001\u0005\u00025BQ\u0001L\u0001\u0005\u0002!CQaS\u0001\u0005\u00021CQaS\u0001\u0005\u00025DQ\u0001]\u0001\u0005\u0002EDQA]\u0001\u0005\u0002MDQA]\u0001\u0005\u0002EDQ!^\u0001\u0005\u0002EDQA^\u0001\u0005\u0002E\f!\"\u00163q\u001b\u0016\u001c8/Y4f\u0015\t\u0001\u0012#\u0001\u0002j_*\t!#\u0001\u0003bW.\f7\u0001\u0001\t\u0003+\u0005i\u0011a\u0004\u0002\u000b+\u0012\u0004X*Z:tC\u001e,7CA\u0001\u0019!\tIB$D\u0001\u001b\u0015\u0005Y\u0012!B:dC2\f\u0017BA\u000f\u001b\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012\u0001F\u0001\u0006]>\f5m\u001b\u000b\u0003E%\u0002\"a\t\u0014\u000f\u0005U!\u0013BA\u0013\u0010\u0003\r)F\r]\u0005\u0003O!\u0012QAT8BG.T!!J\b\t\u000b)\u001a\u0001\u0019\u0001\r\u0002\u000bQ|7.\u001a8\u0016\u0003\t\nAa]3oIR!a&M\u001dD!\t\u0019s&\u0003\u00021Q\t91i\\7nC:$\u0007\"\u0002\u001a\u0006\u0001\u0004\u0019\u0014a\u00029bs2|\u0017\r\u001a\t\u0003i]j\u0011!\u000e\u0006\u0003mE\tA!\u001e;jY&\u0011\u0001(\u000e\u0002\u000b\u0005f$Xm\u0015;sS:<\u0007\"\u0002\u001e\u0006\u0001\u0004Y\u0014A\u0002;be\u001e,G\u000f\u0005\u0002=\u00036\tQH\u0003\u0002?\u007f\u0005\u0019a.\u001a;\u000b\u0003\u0001\u000bAA[1wC&\u0011!)\u0010\u0002\u0012\u0013:,GoU8dW\u0016$\u0018\t\u001a3sKN\u001c\b\"\u0002#\u0006\u0001\u0004)\u0015aA1dWB\u00111ER\u0005\u0003\u000f\"\u0012Q!\u0012<f]R$2AL%K\u0011\u0015\u0011d\u00011\u00014\u0011\u0015Qd\u00011\u0001<\u0003\u0011\u0011\u0017N\u001c3\u0015\t9jUk\u0016\u0005\u0006\u001d\u001e\u0001\raT\u0001\bQ\u0006tG\r\\3s!\t\u00016+D\u0001R\u0015\t\u0011\u0016#A\u0003bGR|'/\u0003\u0002U#\nA\u0011i\u0019;peJ+g\rC\u0003W\u000f\u0001\u00071(\u0001\u0005f]\u0012\u0004x.\u001b8u\u0011\u0015Av\u00011\u0001Z\u0003\u001dy\u0007\u000f^5p]N\u00042AW/`\u001b\u0005Y&B\u0001/@\u0003\u0011a\u0017M\\4\n\u0005y[&\u0001C%uKJ\f'\r\\3\u0011\u0005\u0001TgBA1i\u001d\t\u0011wM\u0004\u0002dM6\tAM\u0003\u0002f'\u00051AH]8pizJ\u0011AE\u0005\u0003!EI!![\b\u0002\t%sW\r^\u0005\u0003W2\u0014AbU8dW\u0016$x\n\u001d;j_:T!![\b\u0015\u00079rw\u000eC\u0003O\u0011\u0001\u0007q\nC\u0003W\u0011\u0001\u00071(\u0001\u0004v]\nLg\u000eZ\u000b\u0002]\u0005a1/[7qY\u0016\u001cVM\u001c3feR\u0011a\u0006\u001e\u0005\u00061*\u0001\r!W\u0001\u000fgV\u001c\b/\u001a8e%\u0016\fG-\u001b8h\u00035\u0011Xm];nKJ+\u0017\rZ5oO\u0002")
public final class UdpMessage {
    public static Udp.Command resumeReading() {
        return UdpMessage$.MODULE$.resumeReading();
    }

    public static Udp.Command suspendReading() {
        return UdpMessage$.MODULE$.suspendReading();
    }

    public static Udp.Command simpleSender() {
        return UdpMessage$.MODULE$.simpleSender();
    }

    public static Udp.Command simpleSender(Iterable<Inet.SocketOption> options) {
        return UdpMessage$.MODULE$.simpleSender(options);
    }

    public static Udp.Command unbind() {
        return UdpMessage$.MODULE$.unbind();
    }

    public static Udp.Command bind(ActorRef handler, InetSocketAddress endpoint) {
        return UdpMessage$.MODULE$.bind(handler, endpoint);
    }

    public static Udp.Command bind(ActorRef handler, InetSocketAddress endpoint, Iterable<Inet.SocketOption> options) {
        return UdpMessage$.MODULE$.bind(handler, endpoint, options);
    }

    public static Udp.Command send(ByteString payload, InetSocketAddress target) {
        return UdpMessage$.MODULE$.send(payload, target);
    }

    public static Udp.Command send(ByteString payload, InetSocketAddress target, Udp.Event ack) {
        return UdpMessage$.MODULE$.send(payload, target, ack);
    }

    public static Udp.NoAck noAck() {
        return UdpMessage$.MODULE$.noAck();
    }

    public static Udp.NoAck noAck(Object token) {
        return UdpMessage$.MODULE$.noAck(token);
    }
}

