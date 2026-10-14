/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.Envelope;
import akka.dispatch.MailboxType;
import akka.dispatch.ProducesMessageQueue;
import akka.dispatch.QueueBasedMessageQueue;
import akka.dispatch.UnboundedDequeBasedMailbox$;
import akka.dispatch.UnboundedDequeBasedMessageQueue;
import com.typesafe.config.Config;
import java.io.Serializable;
import java.util.concurrent.LinkedBlockingDeque;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0015e\u0001\u0002\r\u001a\u0005zAQ!\u000f\u0001\u0005\u0002ICQ!\u000f\u0001\u0005\u0002EDq!a\u0005\u0001\t\u0003\n)\u0002\u0003\u0005\u00028\u0001\t\t\u0011\"\u0001S\u0011%\tI\u0004AA\u0001\n\u0003\nY\u0004C\u0005\u0002D\u0001\t\t\u0011\"\u0001\u0002F!I\u0011Q\n\u0001\u0002\u0002\u0013\u0005\u0011q\n\u0005\n\u00037\u0002\u0011\u0011!C!\u0003;B\u0011\"a\u001b\u0001\u0003\u0003%\t!!\u001c\t\u0013\u0005E\u0004!!A\u0005B\u0005M\u0004\"CA<\u0001\u0005\u0005I\u0011IA=\u0011%\tY\bAA\u0001\n\u0003\ni\bC\u0005\u0002\u0000\u0001\t\t\u0011\"\u0011\u0002\u0002\u001e)a&\u0007E\u0001_\u0019)\u0001$\u0007E\u0001a!)\u0011h\u0004C\u0001u\u0019!1h\u0004\u0001=\u0011\u0015I\u0014\u0003\"\u0001L\u0011\u001dq\u0015C1A\u0005\u0006=Ca\u0001U\t!\u0002\u001ba\u0005bB)\u0010\u0003\u0003%\tI\u0015\u0005\b)>\t\t\u0011\"!V\u0011\u001dYv\"!A\u0005\nq\u0013!$\u00168c_VtG-\u001a3EKF,XMQ1tK\u0012l\u0015-\u001b7c_bT!AG\u000e\u0002\u0011\u0011L7\u000f]1uG\"T\u0011\u0001H\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\r\u0001yR%K2g!\t\u00013%D\u0001\"\u0015\u0005\u0011\u0013!B:dC2\f\u0017B\u0001\u0013\"\u0005\u0019\te.\u001f*fMB\u0011aeJ\u0007\u00023%\u0011\u0001&\u0007\u0002\f\u001b\u0006LGNY8y)f\u0004X\rE\u0002'U1J!aK\r\u0003)A\u0013x\u000eZ;dKNlUm]:bO\u0016\fV/Z;f!\ti\u0013C\u0004\u0002'\u001d\u0005QRK\u001c2pk:$W\r\u001a#fcV,')Y:fI6\u000b\u0017\u000e\u001c2pqB\u0011aeD\n\u0004\u001f}\t\u0004C\u0001\u001a8\u001b\u0005\u0019$B\u0001\u001b6\u0003\tIwNC\u00017\u0003\u0011Q\u0017M^1\n\u0005a\u001a$\u0001D*fe&\fG.\u001b>bE2,\u0017A\u0002\u001fj]&$h\bF\u00010\u00051iUm]:bO\u0016\fV/Z;f'\r\tR\b\u0013\t\u0004}\r+U\"A \u000b\u0005\u0001\u000b\u0015AC2p]\u000e,(O]3oi*\u0011!)N\u0001\u0005kRLG.\u0003\u0002E\u007f\t\u0019B*\u001b8lK\u0012\u0014En\\2lS:<G)Z9vKB\u0011aER\u0005\u0003\u000ff\u0011\u0001\"\u00128wK2|\u0007/\u001a\t\u0003M%K!AS\r\u0003?Us'm\\;oI\u0016$G)Z9vK\n\u000b7/\u001a3NKN\u001c\u0018mZ3Rk\u0016,X\rF\u0001M!\ti\u0015#D\u0001\u0010\u0003\u0015\tX/Z;f+\u0005a\u0015AB9vKV,\u0007%A\u0003baBd\u0017\u0010F\u0001T!\t1\u0003!A\u0004v]\u0006\u0004\b\u000f\\=\u0015\u0005YK\u0006C\u0001\u0011X\u0013\tA\u0016EA\u0004C_>dW-\u00198\t\u000fi3\u0012\u0011!a\u0001'\u0006\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0003u\u0003\"AX1\u000e\u0003}S!\u0001Y\u001b\u0002\t1\fgnZ\u0005\u0003E~\u0013aa\u00142kK\u000e$\bC\u0001\u0011e\u0013\t)\u0017EA\u0004Qe>$Wo\u0019;\u0011\u0005\u001d|gB\u00015n\u001d\tIG.D\u0001k\u0015\tYW$\u0001\u0004=e>|GOP\u0005\u0002E%\u0011a.I\u0001\ba\u0006\u001c7.Y4f\u0013\tA\u0004O\u0003\u0002oCQ\u00191K\u001d@\t\u000bM\u0014\u0001\u0019\u0001;\u0002\u0011M,G\u000f^5oON\u0004\"!^>\u000f\u0005YLX\"A<\u000b\u0005a\\\u0012!B1di>\u0014\u0018B\u0001>x\u0003-\t5\r^8s'f\u001cH/Z7\n\u0005ql(\u0001C*fiRLgnZ:\u000b\u0005i<\bBB@\u0003\u0001\u0004\t\t!\u0001\u0004d_:4\u0017n\u001a\t\u0005\u0003\u0007\ty!\u0004\u0002\u0002\u0006)\u0019q0a\u0002\u000b\t\u0005%\u00111B\u0001\tif\u0004Xm]1gK*\u0011\u0011QB\u0001\u0004G>l\u0017\u0002BA\t\u0003\u000b\u0011aaQ8oM&<\u0017AB2sK\u0006$X\r\u0006\u0004\u0002\u0018\u0005m\u00111\u0006\t\u0004M\u0005e\u0011BA\u001e\u001a\u0011\u001d\tib\u0001a\u0001\u0003?\tQa\\<oKJ\u0004R\u0001IA\u0011\u0003KI1!a\t\"\u0005\u0019y\u0005\u000f^5p]B\u0019a/a\n\n\u0007\u0005%rO\u0001\u0005BGR|'OU3g\u0011\u001d\tic\u0001a\u0001\u0003_\taa]=ti\u0016l\u0007#\u0002\u0011\u0002\"\u0005E\u0002c\u0001<\u00024%\u0019\u0011QG<\u0003\u0017\u0005\u001bGo\u001c:TsN$X-\\\u0001\u0005G>\u0004\u00180A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003{\u00012AXA \u0013\r\t\te\u0018\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\u0005\u001d\u0003c\u0001\u0011\u0002J%\u0019\u00111J\u0011\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005E\u0013q\u000b\t\u0004A\u0005M\u0013bAA+C\t\u0019\u0011I\\=\t\u0013\u0005es!!AA\u0002\u0005\u001d\u0013a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002`A1\u0011\u0011MA4\u0003#j!!a\u0019\u000b\u0007\u0005\u0015\u0014%\u0001\u0006d_2dWm\u0019;j_:LA!!\u001b\u0002d\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\r1\u0016q\u000e\u0005\n\u00033J\u0011\u0011!a\u0001\u0003#\n!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011QHA;\u0011%\tIFCA\u0001\u0002\u0004\t9%\u0001\u0005iCND7i\u001c3f)\t\t9%\u0001\u0005u_N#(/\u001b8h)\t\ti$\u0001\u0004fcV\fGn\u001d\u000b\u0004-\u0006\r\u0005\"CA-\u001b\u0005\u0005\t\u0019AA)\u0001")
public final class UnboundedDequeBasedMailbox
implements MailboxType,
ProducesMessageQueue<MessageQueue>,
Product,
Serializable {
    public static boolean unapply(UnboundedDequeBasedMailbox x$0) {
        return UnboundedDequeBasedMailbox$.MODULE$.unapply(x$0);
    }

    public static UnboundedDequeBasedMailbox apply() {
        return UnboundedDequeBasedMailbox$.MODULE$.apply();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public akka.dispatch.MessageQueue create(Option<ActorRef> owner, Option<ActorSystem> system) {
        return new MessageQueue();
    }

    public UnboundedDequeBasedMailbox copy() {
        return new UnboundedDequeBasedMailbox();
    }

    @Override
    public String productPrefix() {
        return "UnboundedDequeBasedMailbox";
    }

    @Override
    public int productArity() {
        return 0;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof UnboundedDequeBasedMailbox;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return ScalaRunTime$.MODULE$._hashCode(this);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    @Override
    public boolean equals(Object x$1) {
        Object object = x$1;
        return object instanceof UnboundedDequeBasedMailbox;
    }

    public UnboundedDequeBasedMailbox() {
        Product.$init$(this);
    }

    public UnboundedDequeBasedMailbox(ActorSystem.Settings settings, Config config) {
        this();
    }

    public static class MessageQueue
    extends LinkedBlockingDeque<Envelope>
    implements UnboundedDequeBasedMessageQueue {
        private final MessageQueue queue;

        @Override
        public void enqueue(ActorRef receiver, Envelope handle2) {
            UnboundedDequeBasedMessageQueue.enqueue$(this, receiver, handle2);
        }

        @Override
        public void enqueueFirst(ActorRef receiver, Envelope handle2) {
            UnboundedDequeBasedMessageQueue.enqueueFirst$(this, receiver, handle2);
        }

        @Override
        public Envelope dequeue() {
            return UnboundedDequeBasedMessageQueue.dequeue$(this);
        }

        @Override
        public int numberOfMessages() {
            return QueueBasedMessageQueue.numberOfMessages$(this);
        }

        @Override
        public boolean hasMessages() {
            return QueueBasedMessageQueue.hasMessages$(this);
        }

        @Override
        public void cleanUp(ActorRef owner, akka.dispatch.MessageQueue deadLetters) {
            QueueBasedMessageQueue.cleanUp$(this, owner, deadLetters);
        }

        public final MessageQueue queue() {
            return this.queue;
        }

        public MessageQueue() {
            QueueBasedMessageQueue.$init$(this);
            UnboundedDequeBasedMessageQueue.$init$(this);
            this.queue = this;
        }
    }
}

