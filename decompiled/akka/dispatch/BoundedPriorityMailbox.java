/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.BoundedQueueBasedMessageQueue;
import akka.dispatch.Envelope;
import akka.dispatch.MailboxType;
import akka.dispatch.ProducesMessageQueue;
import akka.dispatch.ProducesPushTimeoutSemanticsMailbox;
import akka.dispatch.QueueBasedMessageQueue;
import akka.util.BoundedBlockingQueue;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.BlockingQueue;
import scala.Option;
import scala.concurrent.duration.Duration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001da\u0001B\n\u0015\u0001eA\u0001B\u0011\u0001\u0003\u0006\u0004%)!\u001a\u0005\tM\u0002\u0011\t\u0011)A\u0007\u0007\"AA\b\u0001BC\u0002\u0013\u0015q\r\u0003\u0005i\u0001\t\u0005\t\u0015!\u0004>\u0011!Q\u0005A!b\u0001\n\u000bZ\u0005\u0002\u0003+\u0001\u0005\u0003\u0005\u000bQ\u0002'\t\u000b1\u0002A\u0011A5\t\u000b9\u0004AQI8\b\u000b%\"\u0002\u0012\u0001\u0016\u0007\u000bM!\u0002\u0012A\u0016\t\u000b1RA\u0011A\u0017\u0007\t9R\u0001a\f\u0005\ny1\u0011\t\u0011)A\u0005{\u0001C\u0001B\u0011\u0007\u0003\u0002\u0003\u0006Ia\u0011\u0005\t\u00152\u0011)\u0019!C\u0001\u0017\"AA\u000b\u0004B\u0001B\u0003%A\nC\u0003-\u0019\u0011\u0005Q\u000bC\u0003\\\u0019\u0011\u0015AL\u0001\fC_VtG-\u001a3Qe&|'/\u001b;z\u001b\u0006LGNY8y\u0015\t)b#\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u00059\u0012\u0001B1lW\u0006\u001c\u0001aE\u0003\u00015\u0001\"#\r\u0005\u0002\u001c=5\tADC\u0001\u001e\u0003\u0015\u00198-\u00197b\u0013\tyBD\u0001\u0004B]f\u0014VM\u001a\t\u0003C\tj\u0011\u0001F\u0005\u0003GQ\u00111\"T1jY\n|\u0007\u0010V=qKB\u0019\u0011%J\u0014\n\u0005\u0019\"\"\u0001\u0006)s_\u0012,8-Z:NKN\u001c\u0018mZ3Rk\u0016,X\r\u0005\u0002)\u00199\u0011\u0011%C\u0001\u0017\u0005>,h\u000eZ3e!JLwN]5us6\u000b\u0017\u000e\u001c2pqB\u0011\u0011EC\n\u0003\u0015i\ta\u0001P5oSRtD#\u0001\u0016\u0003\u00195+7o]1hKF+X-^3\u0014\u00071\u0001\u0014\bE\u00022iYj\u0011A\r\u0006\u0003gY\tA!\u001e;jY&\u0011QG\r\u0002\u0015\u0005>,h\u000eZ3e\u00052|7m[5oOF+X-^3\u0011\u0005\u0005:\u0014B\u0001\u001d\u0015\u0005!)eN^3m_B,\u0007CA\u0011;\u0013\tYDCA\u000fC_VtG-\u001a3Rk\u0016,XMQ1tK\u0012lUm]:bO\u0016\fV/Z;f\u0003!\u0019\u0017\r]1dSRL\bCA\u000e?\u0013\tyDDA\u0002J]RL!!\u0011\u001b\u0002\u00175\f\u0007pQ1qC\u000eLG/_\u0001\u0004G6\u0004\bc\u0001#Im5\tQI\u0003\u00024\r*\tq)\u0001\u0003kCZ\f\u0017BA%F\u0005)\u0019u.\u001c9be\u0006$xN]\u0001\faV\u001c\b\u000eV5nK>+H/F\u0001M!\ti%+D\u0001O\u0015\ty\u0005+\u0001\u0005ekJ\fG/[8o\u0015\t\tF$\u0001\u0006d_:\u001cWO\u001d:f]RL!a\u0015(\u0003\u0011\u0011+(/\u0019;j_:\fA\u0002];tQRKW.Z(vi\u0002\"BA\u0016-Z5B\u0011q\u000bD\u0007\u0002\u0015!)A(\u0005a\u0001{!)!)\u0005a\u0001\u0007\")!*\u0005a\u0001\u0019\u0006)\u0011/^3vKV\tQ\fE\u0002_AZj\u0011a\u0018\u0006\u0003#\u0016K!!Y0\u0003\u001b\tcwnY6j]\u001e\fV/Z;f!\t\t3-\u0003\u0002e)\t\u0019\u0003K]8ek\u000e,7\u000fU;tQRKW.Z8viN+W.\u00198uS\u000e\u001cX*Y5mE>DX#A\"\u0002\t\rl\u0007\u000fI\u000b\u0002{\u0005I1-\u00199bG&$\u0018\u0010\t\u000b\u0005U.dW\u000e\u0005\u0002\"\u0001!)!i\u0002a\u0001\u0007\")Ah\u0002a\u0001{!)!j\u0002a\u0001\u0019\u000611M]3bi\u0016$2\u0001\u001d:~!\t\t\u0013/\u0003\u0002/)!)1\u000f\u0003a\u0001i\u0006)qn\u001e8feB\u00191$^<\n\u0005Yd\"AB(qi&|g\u000e\u0005\u0002yw6\t\u0011P\u0003\u0002{-\u0005)\u0011m\u0019;pe&\u0011A0\u001f\u0002\t\u0003\u000e$xN\u001d*fM\")a\u0010\u0003a\u0001\u007f\u000611/_:uK6\u0004BaG;\u0002\u0002A\u0019\u00010a\u0001\n\u0007\u0005\u0015\u0011PA\u0006BGR|'oU=ti\u0016l\u0007")
public class BoundedPriorityMailbox
implements MailboxType,
ProducesMessageQueue<MessageQueue>,
ProducesPushTimeoutSemanticsMailbox {
    private final Comparator<Envelope> cmp;
    private final int capacity;
    private final Duration pushTimeOut;

    public final Comparator<Envelope> cmp() {
        return this.cmp;
    }

    public final int capacity() {
        return this.capacity;
    }

    @Override
    public final Duration pushTimeOut() {
        return this.pushTimeOut;
    }

    @Override
    public final akka.dispatch.MessageQueue create(Option<ActorRef> owner, Option<ActorSystem> system) {
        return new MessageQueue(this.capacity(), this.cmp(), this.pushTimeOut());
    }

    public BoundedPriorityMailbox(Comparator<Envelope> cmp, int capacity, Duration pushTimeOut) {
        this.cmp = cmp;
        this.capacity = capacity;
        this.pushTimeOut = pushTimeOut;
        if (capacity < 0) {
            throw new IllegalArgumentException("The capacity for BoundedMailbox can not be negative");
        }
        if (pushTimeOut == null) {
            throw new IllegalArgumentException("The push time-out for BoundedMailbox can not be null");
        }
    }

    public static class MessageQueue
    extends BoundedBlockingQueue<Envelope>
    implements BoundedQueueBasedMessageQueue {
        private final Duration pushTimeOut;

        @Override
        public void enqueue(ActorRef receiver, Envelope handle2) {
            BoundedQueueBasedMessageQueue.enqueue$(this, receiver, handle2);
        }

        @Override
        public Envelope dequeue() {
            return BoundedQueueBasedMessageQueue.dequeue$(this);
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

        @Override
        public Duration pushTimeOut() {
            return this.pushTimeOut;
        }

        @Override
        public final BlockingQueue<Envelope> queue() {
            return this;
        }

        public MessageQueue(int capacity, Comparator<Envelope> cmp, Duration pushTimeOut) {
            this.pushTimeOut = pushTimeOut;
            super(capacity, new PriorityQueue<Envelope>(11, cmp));
            QueueBasedMessageQueue.$init$(this);
            BoundedQueueBasedMessageQueue.$init$(this);
        }
    }
}

