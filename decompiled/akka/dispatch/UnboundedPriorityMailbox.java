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
import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005=4A\u0001E\t\u0001-!A\u0011\t\u0001BC\u0002\u0013\u0005\u0001\u000b\u0003\u0005R\u0001\t\u0005\t\u0015!\u0003C\u0011!i\u0004A!b\u0001\n\u0003\u0011\u0006\u0002C*\u0001\u0005\u0003\u0005\u000b\u0011\u0002 \t\u000b%\u0002A\u0011\u0001+\t\u000b%\u0002A\u0011\u0001-\t\u000bi\u0003AQI.\b\u000b\u0019\n\u0002\u0012A\u0014\u0007\u000bA\t\u0002\u0012\u0001\u0015\t\u000b%JA\u0011\u0001\u0016\u0007\t-J\u0001\u0001\f\u0005\t{-\u0011\t\u0011)A\u0005}!A\u0011i\u0003B\u0001B\u0003%!\tC\u0003*\u0017\u0011\u0005a\tC\u0003L\u0017\u0011\u0015AJ\u0001\rV]\n|WO\u001c3fIB\u0013\u0018n\u001c:jifl\u0015-\u001b7c_bT!AE\n\u0002\u0011\u0011L7\u000f]1uG\"T\u0011\u0001F\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\t\u00019R$\t\t\u00031mi\u0011!\u0007\u0006\u00025\u0005)1oY1mC&\u0011A$\u0007\u0002\u0007\u0003:L(+\u001a4\u0011\u0005yyR\"A\t\n\u0005\u0001\n\"aC'bS2\u0014w\u000e\u001f+za\u0016\u00042A\b\u0012%\u0013\t\u0019\u0013C\u0001\u000bQe>$WoY3t\u001b\u0016\u001c8/Y4f#V,W/\u001a\t\u0003K-q!A\b\u0005\u00021Us'm\\;oI\u0016$\u0007K]5pe&$\u00180T1jY\n|\u0007\u0010\u0005\u0002\u001f\u0013M\u0011\u0011bF\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003\u001d\u0012A\"T3tg\u0006<W-U;fk\u0016\u001c2aC\u0017;!\rqSgN\u0007\u0002_)\u0011\u0001'M\u0001\u000bG>t7-\u001e:sK:$(B\u0001\u001a4\u0003\u0011)H/\u001b7\u000b\u0003Q\nAA[1wC&\u0011ag\f\u0002\u0016!JLwN]5us\ncwnY6j]\u001e\fV/Z;f!\tq\u0002(\u0003\u0002:#\tAQI\u001c<fY>\u0004X\r\u0005\u0002\u001fw%\u0011A(\u0005\u0002 +:\u0014w.\u001e8eK\u0012\fV/Z;f\u0005\u0006\u001cX\rZ'fgN\fw-Z)vKV,\u0017aD5oSRL\u0017\r\\\"ba\u0006\u001c\u0017\u000e^=\u0011\u0005ay\u0014B\u0001!\u001a\u0005\rIe\u000e^\u0001\u0004G6\u0004\bcA\"Eo5\t\u0011'\u0003\u0002Fc\tQ1i\\7qCJ\fGo\u001c:\u0015\u0007\u001dK%\n\u0005\u0002I\u00175\t\u0011\u0002C\u0003>\u001d\u0001\u0007a\bC\u0003B\u001d\u0001\u0007!)A\u0003rk\u0016,X-F\u0001N!\r\u0019ejN\u0005\u0003\u001fF\u0012Q!U;fk\u0016,\u0012AQ\u0001\u0005G6\u0004\b%F\u0001?\u0003AIg.\u001b;jC2\u001c\u0015\r]1dSRL\b\u0005F\u0002V-^\u0003\"A\b\u0001\t\u000b\u0005+\u0001\u0019\u0001\"\t\u000bu*\u0001\u0019\u0001 \u0015\u0005UK\u0006\"B!\u0007\u0001\u0004\u0011\u0015AB2sK\u0006$X\rF\u0002]=&\u0004\"AH/\n\u0005-\n\u0002\"B0\b\u0001\u0004\u0001\u0017!B8x]\u0016\u0014\bc\u0001\rbG&\u0011!-\u0007\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u0005\u0011<W\"A3\u000b\u0005\u0019\u001c\u0012!B1di>\u0014\u0018B\u00015f\u0005!\t5\r^8s%\u00164\u0007\"\u00026\b\u0001\u0004Y\u0017AB:zgR,W\u000eE\u0002\u0019C2\u0004\"\u0001Z7\n\u00059,'aC!di>\u00148+_:uK6\u0004")
public class UnboundedPriorityMailbox
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

    public UnboundedPriorityMailbox(Comparator<Envelope> cmp, int initialCapacity) {
        this.cmp = cmp;
        this.initialCapacity = initialCapacity;
    }

    public UnboundedPriorityMailbox(Comparator<Envelope> cmp) {
        this(cmp, 11);
    }

    public static class MessageQueue
    extends PriorityBlockingQueue<Envelope>
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

