/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.io.Inet;
import akka.io.Tcp;
import akka.io.TcpMessage$;
import akka.util.ByteString;
import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.time.Duration;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015x!B\r\u001b\u0011\u0003yb!B\u0011\u001b\u0011\u0003\u0011\u0003\"B\u0015\u0002\t\u0003Q\u0003\"B\u0016\u0002\t\u0003a\u0003\"B\u0016\u0002\t\u0003)\u0007\"B\u0016\u0002\t\u0003\t\b\"B:\u0002\t\u0003!\bBB:\u0002\t\u0003\ti\u0001C\u0004\u0002\u0016\u0005!\t!a\u0006\t\u000f\u0005U\u0011\u0001\"\u0001\u0002$!9\u0011qE\u0001\u0005\u0002\u0005%\u0002bBA\u0016\u0003\u0011\u0005\u0011\u0011\u0006\u0005\b\u0003[\tA\u0011AA\u0015\u0011\u001d\ty#\u0001C\u0001\u0003SAq!!\r\u0002\t\u0003\t\u0019\u0004C\u0004\u00022\u0005!\t!a\u0010\t\u000f\u0005\u0005\u0013\u0001\"\u0001\u0002D!9\u0011\u0011I\u0001\u0005\u0002\u0005}\u0003bBA2\u0003\u0011\u0005\u0011Q\r\u0005\b\u0003\u0017\u000bA\u0011AAG\u0011\u001d\t9+\u0001C\u0001\u0003SAq!!+\u0002\t\u0003\tI\u0003C\u0004\u0002,\u0006!\t!!\u000b\t\u000f\u00055\u0016\u0001\"\u0001\u00020\"9\u0011QW\u0001\u0005\f\u0005]\u0016A\u0003+da6+7o]1hK*\u00111\u0004H\u0001\u0003S>T\u0011!H\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005\u0001\nQ\"\u0001\u000e\u0003\u0015Q\u001b\u0007/T3tg\u0006<Wm\u0005\u0002\u0002GA\u0011AeJ\u0007\u0002K)\ta%A\u0003tG\u0006d\u0017-\u0003\u0002)K\t1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#A\u0010\u0002\u000f\r|gN\\3diR1Q\u0006\u000e A-\u0002\u0004\"AL\u0019\u000f\u0005\u0001z\u0013B\u0001\u0019\u001b\u0003\r!6\r]\u0005\u0003eM\u0012qaQ8n[\u0006tGM\u0003\u000215!)Qg\u0001a\u0001m\u0005i!/Z7pi\u0016\fE\r\u001a:fgN\u0004\"a\u000e\u001f\u000e\u0003aR!!\u000f\u001e\u0002\u00079,GOC\u0001<\u0003\u0011Q\u0017M^1\n\u0005uB$!E%oKR\u001cvnY6fi\u0006#GM]3tg\")qh\u0001a\u0001m\u0005aAn\\2bY\u0006#GM]3tg\")\u0011i\u0001a\u0001\u0005\u00069q\u000e\u001d;j_:\u001c\bcA\"G\u00116\tAI\u0003\u0002Fu\u0005!A.\u00198h\u0013\t9EI\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\tI5K\u0004\u0002K#:\u00111\n\u0015\b\u0003\u0019>k\u0011!\u0014\u0006\u0003\u001dz\ta\u0001\u0010:p_Rt\u0014\"A\u000f\n\u0005ma\u0012B\u0001*\u001b\u0003\u0011Ie.\u001a;\n\u0005Q+&\u0001D*pG.,Go\u00149uS>t'B\u0001*\u001b\u0011\u001596\u00011\u0001Y\u0003\u001d!\u0018.\\3pkR\u0004\"!\u00170\u000e\u0003iS!a\u0017/\u0002\u0011\u0011,(/\u0019;j_:T!!X\u0013\u0002\u0015\r|gnY;se\u0016tG/\u0003\u0002`5\nqa)\u001b8ji\u0016$UO]1uS>t\u0007\"B1\u0004\u0001\u0004\u0011\u0017\u0001\u00039vY2lu\u000eZ3\u0011\u0005\u0011\u001a\u0017B\u00013&\u0005\u001d\u0011un\u001c7fC:$b!\f4hQ&\u0004\b\"B\u001b\u0005\u0001\u00041\u0004\"B \u0005\u0001\u00041\u0004\"B!\u0005\u0001\u0004\u0011\u0005\"B,\u0005\u0001\u0004Q\u0007CA6o\u001b\u0005a'BA7;\u0003\u0011!\u0018.\\3\n\u0005=d'\u0001\u0003#ve\u0006$\u0018n\u001c8\t\u000b\u0005$\u0001\u0019\u00012\u0015\u00055\u0012\b\"B\u001b\u0006\u0001\u00041\u0014\u0001\u00022j]\u0012$\u0002\"L;~\u007f\u0006%\u00111\u0002\u0005\u0006m\u001a\u0001\ra^\u0001\bQ\u0006tG\r\\3s!\tA80D\u0001z\u0015\tQH$A\u0003bGR|'/\u0003\u0002}s\nA\u0011i\u0019;peJ+g\rC\u0003\u007f\r\u0001\u0007a'\u0001\u0005f]\u0012\u0004x.\u001b8u\u0011\u001d\t\tA\u0002a\u0001\u0003\u0007\tqAY1dW2|w\rE\u0002%\u0003\u000bI1!a\u0002&\u0005\rIe\u000e\u001e\u0005\u0006\u0003\u001a\u0001\rA\u0011\u0005\u0006C\u001a\u0001\rA\u0019\u000b\b[\u0005=\u0011\u0011CA\n\u0011\u00151x\u00011\u0001x\u0011\u0015qx\u00011\u00017\u0011\u001d\t\ta\u0002a\u0001\u0003\u0007\t\u0001B]3hSN$XM\u001d\u000b\b[\u0005e\u00111DA\u0010\u0011\u00151\b\u00021\u0001x\u0011\u0019\ti\u0002\u0003a\u0001E\u0006!2.Z3q\u001fB,gn\u00148QK\u0016\u00148\t\\8tK\u0012Da!!\t\t\u0001\u0004\u0011\u0017\u0001E;tKJ+7/^7f/JLG/\u001b8h)\ri\u0013Q\u0005\u0005\u0006m&\u0001\ra^\u0001\u0007k:\u0014\u0017N\u001c3\u0016\u00035\nQa\u00197pg\u0016\fabY8oM&\u0014X.\u001a3DY>\u001cX-A\u0003bE>\u0014H/A\u0003o_\u0006\u001b7\u000e\u0006\u0003\u00026\u0005m\u0002c\u0001\u0018\u00028%\u0019\u0011\u0011H\u001a\u0003\u000b9{\u0017iY6\t\r\u0005ub\u00021\u0001$\u0003\u0015!xn[3o+\t\t)$A\u0003xe&$X\rF\u0003.\u0003\u000b\n)\u0006C\u0004\u0002HA\u0001\r!!\u0013\u0002\t\u0011\fG/\u0019\t\u0005\u0003\u0017\n\t&\u0004\u0002\u0002N)\u0019\u0011q\n\u000f\u0002\tU$\u0018\u000e\\\u0005\u0005\u0003'\niE\u0001\u0006CsR,7\u000b\u001e:j]\u001eDq!a\u0016\u0011\u0001\u0004\tI&A\u0002bG.\u00042ALA.\u0013\r\tif\r\u0002\u0006\u000bZ,g\u000e\u001e\u000b\u0004[\u0005\u0005\u0004bBA$#\u0001\u0007\u0011\u0011J\u0001\noJLG/\u001a$jY\u0016$\u0012\"LA4\u0003w\n))!#\t\u000f\u0005%$\u00031\u0001\u0002l\u0005Aa-\u001b7f!\u0006$\b\u000e\u0005\u0003\u0002n\u0005Ud\u0002BA8\u0003c\u0002\"\u0001T\u0013\n\u0007\u0005MT%\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003o\nIH\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003g*\u0003bBA?%\u0001\u0007\u0011qP\u0001\ta>\u001c\u0018\u000e^5p]B\u0019A%!!\n\u0007\u0005\rUE\u0001\u0003M_:<\u0007bBAD%\u0001\u0007\u0011qP\u0001\u0006G>,h\u000e\u001e\u0005\b\u0003/\u0012\u0002\u0019AA-\u0003%9(/\u001b;f!\u0006$\b\u000eF\u0005.\u0003\u001f\u000b\t+a)\u0002&\"9\u0011\u0011N\nA\u0002\u0005E\u0005\u0003BAJ\u0003;k!!!&\u000b\t\u0005]\u0015\u0011T\u0001\u0005M&dWMC\u0002\u0002\u001cj\n1A\\5p\u0013\u0011\ty*!&\u0003\tA\u000bG\u000f\u001b\u0005\b\u0003{\u001a\u0002\u0019AA@\u0011\u001d\t9i\u0005a\u0001\u0003\u007fBq!a\u0016\u0014\u0001\u0004\tI&A\u0007sKN,X.Z,sSRLgnZ\u0001\u000fgV\u001c\b/\u001a8e%\u0016\fG-\u001b8h\u00035\u0011Xm];nKJ+\u0017\rZ5oO\u0006y!/Z:v[\u0016\f5mY3qi&tw\rF\u0002.\u0003cCq!a-\u0018\u0001\u0004\t\u0019!A\u0005cCR\u001c\u0007nU5{K\u0006AaM]8n\u0015\u00064\u0018-\u0006\u0003\u0002:\u00065G\u0003BA^\u0003?\u0004b!!0\u0002H\u0006%WBAA`\u0015\u0011\t\t-a1\u0002\u0013%lW.\u001e;bE2,'bAAcK\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0007\u001d\u000by\f\u0005\u0003\u0002L\u00065G\u0002\u0001\u0003\b\u0003\u001fD\"\u0019AAi\u0005\u0005!\u0016\u0003BAj\u00033\u00042\u0001JAk\u0013\r\t9.\n\u0002\b\u001d>$\b.\u001b8h!\r!\u00131\\\u0005\u0004\u0003;,#aA!os\"9\u0011\u0011\u001d\rA\u0002\u0005\r\u0018\u0001B2pY2\u0004Ba\u0011$\u0002J\u0002")
public final class TcpMessage {
    public static Tcp.Command resumeAccepting(int batchSize) {
        return TcpMessage$.MODULE$.resumeAccepting(batchSize);
    }

    public static Tcp.Command resumeReading() {
        return TcpMessage$.MODULE$.resumeReading();
    }

    public static Tcp.Command suspendReading() {
        return TcpMessage$.MODULE$.suspendReading();
    }

    public static Tcp.Command resumeWriting() {
        return TcpMessage$.MODULE$.resumeWriting();
    }

    public static Tcp.Command writePath(Path filePath, long position, long count, Tcp.Event ack) {
        return TcpMessage$.MODULE$.writePath(filePath, position, count, ack);
    }

    public static Tcp.Command writeFile(String filePath, long position, long count, Tcp.Event ack) {
        return TcpMessage$.MODULE$.writeFile(filePath, position, count, ack);
    }

    public static Tcp.Command write(ByteString data) {
        return TcpMessage$.MODULE$.write(data);
    }

    public static Tcp.Command write(ByteString data, Tcp.Event ack) {
        return TcpMessage$.MODULE$.write(data, ack);
    }

    public static Tcp.NoAck noAck() {
        return TcpMessage$.MODULE$.noAck();
    }

    public static Tcp.NoAck noAck(Object token) {
        return TcpMessage$.MODULE$.noAck(token);
    }

    public static Tcp.Command abort() {
        return TcpMessage$.MODULE$.abort();
    }

    public static Tcp.Command confirmedClose() {
        return TcpMessage$.MODULE$.confirmedClose();
    }

    public static Tcp.Command close() {
        return TcpMessage$.MODULE$.close();
    }

    public static Tcp.Command unbind() {
        return TcpMessage$.MODULE$.unbind();
    }

    public static Tcp.Command register(ActorRef handler) {
        return TcpMessage$.MODULE$.register(handler);
    }

    public static Tcp.Command register(ActorRef handler, boolean keepOpenOnPeerClosed, boolean useResumeWriting) {
        return TcpMessage$.MODULE$.register(handler, keepOpenOnPeerClosed, useResumeWriting);
    }

    public static Tcp.Command bind(ActorRef handler, InetSocketAddress endpoint, int backlog) {
        return TcpMessage$.MODULE$.bind(handler, endpoint, backlog);
    }

    public static Tcp.Command bind(ActorRef handler, InetSocketAddress endpoint, int backlog, Iterable<Inet.SocketOption> options, boolean pullMode) {
        return TcpMessage$.MODULE$.bind(handler, endpoint, backlog, options, pullMode);
    }

    public static Tcp.Command connect(InetSocketAddress remoteAddress) {
        return TcpMessage$.MODULE$.connect(remoteAddress);
    }

    public static Tcp.Command connect(InetSocketAddress remoteAddress, InetSocketAddress localAddress, Iterable<Inet.SocketOption> options, Duration timeout2, boolean pullMode) {
        return TcpMessage$.MODULE$.connect(remoteAddress, localAddress, options, timeout2, pullMode);
    }

    public static Tcp.Command connect(InetSocketAddress remoteAddress, InetSocketAddress localAddress, Iterable<Inet.SocketOption> options, FiniteDuration timeout2, boolean pullMode) {
        return TcpMessage$.MODULE$.connect(remoteAddress, localAddress, options, timeout2, pullMode);
    }
}

