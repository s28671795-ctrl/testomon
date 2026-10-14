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
import akka.dispatch.UnboundedQueueBasedMessageQueue;
import akka.util.StablePriorityBlockingQueue;
import java.util.Comparator;
import java.util.Queue;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000594A\u0001E\t\u0001-!AQ\b\u0001BC\u0002\u0013\u0005q\n\u0003\u0005Q\u0001\t\u0005\t\u0015!\u0003?\u0011!I\u0004A!b\u0001\n\u0003\t\u0006\u0002\u0003*\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u001e\t\u000b%\u0002A\u0011A*\t\u000b%\u0002A\u0011A,\t\u000be\u0003AQ\t.\b\u000b\u0019\n\u0002\u0012A\u0014\u0007\u000bA\t\u0002\u0012\u0001\u0015\t\u000b%JA\u0011\u0001\u0016\u0007\t-J\u0001\u0001\f\u0005\ts-\u0011\t\u0011)A\u0005u!AQh\u0003B\u0001B\u0003%a\bC\u0003*\u0017\u0011\u0005Q\tC\u0003K\u0017\u0011\u00151J\u0001\u0010V]\n|WO\u001c3fIN#\u0018M\u00197f!JLwN]5us6\u000b\u0017\u000e\u001c2pq*\u0011!cE\u0001\tI&\u001c\b/\u0019;dQ*\tA#\u0001\u0003bW.\f7\u0001A\n\u0005\u0001]i\u0012\u0005\u0005\u0002\u001975\t\u0011DC\u0001\u001b\u0003\u0015\u00198-\u00197b\u0013\ta\u0012D\u0001\u0004B]f\u0014VM\u001a\t\u0003=}i\u0011!E\u0005\u0003AE\u00111\"T1jY\n|\u0007\u0010V=qKB\u0019aD\t\u0013\n\u0005\r\n\"\u0001\u0006)s_\u0012,8-Z:NKN\u001c\u0018mZ3Rk\u0016,X\r\u0005\u0002&\u00179\u0011a\u0004C\u0001\u001f+:\u0014w.\u001e8eK\u0012\u001cF/\u00192mKB\u0013\u0018n\u001c:jifl\u0015-\u001b7c_b\u0004\"AH\u0005\u0014\u0005%9\u0012A\u0002\u001fj]&$h\bF\u0001(\u00051iUm]:bO\u0016\fV/Z;f'\rYQF\u000e\t\u0004]E\u001aT\"A\u0018\u000b\u0005A\u001a\u0012\u0001B;uS2L!AM\u0018\u00037M#\u0018M\u00197f!JLwN]5us\ncwnY6j]\u001e\fV/Z;f!\tqB'\u0003\u00026#\tAQI\u001c<fY>\u0004X\r\u0005\u0002\u001fo%\u0011\u0001(\u0005\u0002 +:\u0014w.\u001e8eK\u0012\fV/Z;f\u0005\u0006\u001cX\rZ'fgN\fw-Z)vKV,\u0017aD5oSRL\u0017\r\\\"ba\u0006\u001c\u0017\u000e^=\u0011\u0005aY\u0014B\u0001\u001f\u001a\u0005\rIe\u000e^\u0001\u0004G6\u0004\bcA Dg5\t\u0001I\u0003\u00021\u0003*\t!)\u0001\u0003kCZ\f\u0017B\u0001#A\u0005)\u0019u.\u001c9be\u0006$xN\u001d\u000b\u0004\r\"K\u0005CA$\f\u001b\u0005I\u0001\"B\u001d\u000f\u0001\u0004Q\u0004\"B\u001f\u000f\u0001\u0004q\u0014!B9vKV,W#\u0001'\u0011\u0007}j5'\u0003\u0002O\u0001\n)\u0011+^3vKV\ta(\u0001\u0003d[B\u0004S#\u0001\u001e\u0002!%t\u0017\u000e^5bY\u000e\u000b\u0007/Y2jif\u0004Cc\u0001+V-B\u0011a\u0004\u0001\u0005\u0006{\u0015\u0001\rA\u0010\u0005\u0006s\u0015\u0001\rA\u000f\u000b\u0003)bCQ!\u0010\u0004A\u0002y\naa\u0019:fCR,GcA.^QB\u0011a\u0004X\u0005\u0003WEAQAX\u0004A\u0002}\u000bQa\\<oKJ\u00042\u0001\u00071c\u0013\t\t\u0017D\u0001\u0004PaRLwN\u001c\t\u0003G\u001al\u0011\u0001\u001a\u0006\u0003KN\tQ!Y2u_JL!a\u001a3\u0003\u0011\u0005\u001bGo\u001c:SK\u001aDQ![\u0004A\u0002)\faa]=ti\u0016l\u0007c\u0001\raWB\u00111\r\\\u0005\u0003[\u0012\u00141\"Q2u_J\u001c\u0016p\u001d;f[\u0002")
public class UnboundedStablePriorityMailbox
implements MailboxType,
ProducesMessageQueue<MessageQueue> {
    private final Comparator<Envelope> cmp;
    private final int initialCapacity;

    public Comparator<Envelope> cmp() {
        return this.cmp;
    }

    public int initialCapacity() {
        return this.initialCapacity;
    }

    @Override
    public final akka.dispatch.MessageQueue create(Option<ActorRef> owner, Option<ActorSystem> system) {
        return new MessageQueue(this.initialCapacity(), this.cmp());
    }

    public UnboundedStablePriorityMailbox(Comparator<Envelope> cmp, int initialCapacity) {
        this.cmp = cmp;
        this.initialCapacity = initialCapacity;
    }

    public UnboundedStablePriorityMailbox(Comparator<Envelope> cmp) {
        this(cmp, 11);
    }

    public static class MessageQueue
    extends StablePriorityBlockingQueue<Envelope>
    implements UnboundedQueueBasedMessageQueue {
        @Override
        public void enqueue(ActorRef receiver, Envelope handle2) {
            UnboundedQueueBasedMessageQueue.enqueue$(this, receiver, handle2);
        }

        @Override
        public Envelope dequeue() {
            return UnboundedQueueBasedMessageQueue.dequeue$(this);
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
        public final Queue<Envelope> queue() {
            return this;
        }

        public MessageQueue(int initialCapacity, Comparator<Envelope> cmp) {
            super(initialCapacity, cmp);
            QueueBasedMessageQueue.$init$(this);
            UnboundedQueueBasedMessageQueue.$init$(this);
        }
    }
}

