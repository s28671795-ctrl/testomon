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
import akka.util.StablePriorityQueue;
import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import scala.Option;
import scala.concurrent.duration.Duration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001da\u0001B\n\u0015\u0001eA\u0001B\u0011\u0001\u0003\u0006\u0004%)!\u001a\u0005\tM\u0002\u0011\t\u0011)A\u0007\u0007\"AA\b\u0001BC\u0002\u0013\u0015q\r\u0003\u0005i\u0001\t\u0005\t\u0015!\u0004>\u0011!Q\u0005A!b\u0001\n\u000bZ\u0005\u0002\u0003+\u0001\u0005\u0003\u0005\u000bQ\u0002'\t\u000b1\u0002A\u0011A5\t\u000b9\u0004AQI8\b\u000b%\"\u0002\u0012\u0001\u0016\u0007\u000bM!\u0002\u0012A\u0016\t\u000b1RA\u0011A\u0017\u0007\t9R\u0001a\f\u0005\ny1\u0011\t\u0011)A\u0005{\u0001C\u0001B\u0011\u0007\u0003\u0002\u0003\u0006Ia\u0011\u0005\t\u00152\u0011)\u0019!C\u0001\u0017\"AA\u000b\u0004B\u0001B\u0003%A\nC\u0003-\u0019\u0011\u0005Q\u000bC\u0003\\\u0019\u0011\u0015AL\u0001\u000fC_VtG-\u001a3Ti\u0006\u0014G.\u001a)sS>\u0014\u0018\u000e^=NC&d'm\u001c=\u000b\u0005U1\u0012\u0001\u00033jgB\fGo\u00195\u000b\u0003]\tA!Y6lC\u000e\u00011#\u0002\u0001\u001bA\u0011\u0012\u0007CA\u000e\u001f\u001b\u0005a\"\"A\u000f\u0002\u000bM\u001c\u0017\r\\1\n\u0005}a\"AB!osJ+g\r\u0005\u0002\"E5\tA#\u0003\u0002$)\tYQ*Y5mE>DH+\u001f9f!\r\tSeJ\u0005\u0003MQ\u0011A\u0003\u0015:pIV\u001cWm]'fgN\fw-Z)vKV,\u0007C\u0001\u0015\r\u001d\t\t\u0013\"\u0001\u000fC_VtG-\u001a3Ti\u0006\u0014G.\u001a)sS>\u0014\u0018\u000e^=NC&d'm\u001c=\u0011\u0005\u0005R1C\u0001\u0006\u001b\u0003\u0019a\u0014N\\5u}Q\t!F\u0001\u0007NKN\u001c\u0018mZ3Rk\u0016,XmE\u0002\rae\u00022!\r\u001b7\u001b\u0005\u0011$BA\u001a\u0017\u0003\u0011)H/\u001b7\n\u0005U\u0012$\u0001\u0006\"pk:$W\r\u001a\"m_\u000e\\\u0017N\\4Rk\u0016,X\r\u0005\u0002\"o%\u0011\u0001\b\u0006\u0002\t\u000b:4X\r\\8qKB\u0011\u0011EO\u0005\u0003wQ\u0011QDQ8v]\u0012,G-U;fk\u0016\u0014\u0015m]3e\u001b\u0016\u001c8/Y4f#V,W/Z\u0001\tG\u0006\u0004\u0018mY5usB\u00111DP\u0005\u0003\u007fq\u00111!\u00138u\u0013\t\tE'A\u0006nCb\u001c\u0015\r]1dSRL\u0018aA2naB\u0019A\t\u0013\u001c\u000e\u0003\u0015S!a\r$\u000b\u0003\u001d\u000bAA[1wC&\u0011\u0011*\u0012\u0002\u000b\u0007>l\u0007/\u0019:bi>\u0014\u0018a\u00039vg\"$\u0016.\\3PkR,\u0012\u0001\u0014\t\u0003\u001bJk\u0011A\u0014\u0006\u0003\u001fB\u000b\u0001\u0002Z;sCRLwN\u001c\u0006\u0003#r\t!bY8oGV\u0014(/\u001a8u\u0013\t\u0019fJ\u0001\u0005EkJ\fG/[8o\u00031\u0001Xo\u001d5US6,w*\u001e;!)\u00111\u0006,\u0017.\u0011\u0005]cQ\"\u0001\u0006\t\u000bq\n\u0002\u0019A\u001f\t\u000b\t\u000b\u0002\u0019A\"\t\u000b)\u000b\u0002\u0019\u0001'\u0002\u000bE,X-^3\u0016\u0003u\u00032A\u001817\u001b\u0005y&BA)F\u0013\t\twLA\u0007CY>\u001c7.\u001b8h#V,W/\u001a\t\u0003C\rL!\u0001\u001a\u000b\u0003GA\u0013x\u000eZ;dKN\u0004Vo\u001d5US6,w.\u001e;TK6\fg\u000e^5dg6\u000b\u0017\u000e\u001c2pqV\t1)\u0001\u0003d[B\u0004S#A\u001f\u0002\u0013\r\f\u0007/Y2jif\u0004C\u0003\u00026lY6\u0004\"!\t\u0001\t\u000b\t;\u0001\u0019A\"\t\u000bq:\u0001\u0019A\u001f\t\u000b);\u0001\u0019\u0001'\u0002\r\r\u0014X-\u0019;f)\r\u0001(/ \t\u0003CEL!A\f\u000b\t\u000bMD\u0001\u0019\u0001;\u0002\u000b=<h.\u001a:\u0011\u0007m)x/\u0003\u0002w9\t1q\n\u001d;j_:\u0004\"\u0001_>\u000e\u0003eT!A\u001f\f\u0002\u000b\u0005\u001cGo\u001c:\n\u0005qL(\u0001C!di>\u0014(+\u001a4\t\u000byD\u0001\u0019A@\u0002\rML8\u000f^3n!\u0011YR/!\u0001\u0011\u0007a\f\u0019!C\u0002\u0002\u0006e\u00141\"Q2u_J\u001c\u0016p\u001d;f[\u0002")
public class BoundedStablePriorityMailbox
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

    public BoundedStablePriorityMailbox(Comparator<Envelope> cmp, int capacity, Duration pushTimeOut) {
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
            super(capacity, new StablePriorityQueue<Envelope>(11, cmp));
            QueueBasedMessageQueue.$init$(this);
            BoundedQueueBasedMessageQueue.$init$(this);
        }
    }
}

