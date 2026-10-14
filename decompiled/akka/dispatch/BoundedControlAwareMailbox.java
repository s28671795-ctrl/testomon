/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.DeadLetter;
import akka.actor.InternalActorRef;
import akka.dispatch.BoundedControlAwareMailbox$;
import akka.dispatch.BoundedControlAwareMessageQueueSemantics;
import akka.dispatch.ControlAwareMessageQueueSemantics;
import akka.dispatch.ControlMessage;
import akka.dispatch.Envelope;
import akka.dispatch.MailboxType;
import akka.dispatch.ProducesMessageQueue;
import akka.dispatch.ProducesPushTimeoutSemanticsMailbox;
import akka.dispatch.QueueBasedMessageQueue;
import akka.util.Helpers$;
import akka.util.Helpers$ConfigOps$;
import com.typesafe.config.Config;
import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t]d\u0001\u0002\u00192\u0005ZB\u0001\u0002\u0017\u0001\u0003\u0016\u0004%\t!\u0017\u0005\t;\u0002\u0011\t\u0012)A\u00055\"Aa\f\u0001BK\u0002\u0013\u0015s\f\u0003\u0005i\u0001\tE\t\u0015!\u0004a\u0011\u0019\t\u0006\u0001\"\u0001\u0002D\"1\u0011\u000b\u0001C\u0001\u0003\u0013Dq!a=\u0001\t\u0003\t)\u0010C\u0005\u0003\u000e\u0001\t\t\u0011\"\u0001\u0003\u0010!I!Q\u0003\u0001\u0012\u0002\u0013\u0005!q\u0003\u0005\n\u0005[\u0001\u0011\u0013!C\u0001\u0005_A\u0011Ba\r\u0001\u0003\u0003%\tE!\u000e\t\u0011\tu\u0002!!A\u0005\u0002eC\u0011Ba\u0010\u0001\u0003\u0003%\tA!\u0011\t\u0013\t5\u0003!!A\u0005B\t=\u0003\"\u0003B/\u0001\u0005\u0005I\u0011\u0001B0\u0011%\u0011\u0019\u0007AA\u0001\n\u0003\u0012)\u0007C\u0005\u0003j\u0001\t\t\u0011\"\u0011\u0003l!I!Q\u000e\u0001\u0002\u0002\u0013\u0005#q\u000e\u0005\n\u0005c\u0002\u0011\u0011!C!\u0005g:QAR\u0019\t\u0002\u001d3Q\u0001M\u0019\t\u0002!CQ!U\u000b\u0005\u0002I3AaU\u000b\u0001)\"A\u0001l\u0006BC\u0002\u0013\u0005\u0011\f\u0003\u0005^/\t\u0005\t\u0015!\u0003[\u0011!qvC!b\u0001\n\u0003y\u0006\u0002\u00035\u0018\u0005\u0003\u0005\u000b\u0011\u00021\t\u000bE;B\u0011A5\t\u000f9<\"\u0019!C\u0007_\"1\u0011p\u0006Q\u0001\u000eADqA_\fC\u0002\u001351\u0010C\u0004\u0002\u0006]\u0001\u000bQ\u0002?\t\u0013\u0005\u001dqC1A\u0005\u000e\u0005%\u0001\u0002CA\t/\u0001\u0006i!a\u0003\t\u0013\u0005MqC1A\u0005\u0002\u0005U\u0001\u0002CA\u0013/\u0001\u0006I!a\u0006\t\u0013\u0005\u001drC1A\u0005\u0002\u0005U\u0001\u0002CA\u0015/\u0001\u0006I!a\u0006\t\u000f\u0005-r\u0003\"\u0011\u0002.!1\u0011\u0011J\f\u0005BeCq!a\u0013\u0018\t\u0003\ni\u0005C\u0004\u0002V]!)%a\u0016\t\u000f\u0005es\u0003\"\u0003\u0002\\!9\u0011QL\f\u0005\u000e\u0005}\u0003\"CA:+\u0005\u0005I\u0011QA;\u0011%\ti(FA\u0001\n\u0003\u000by\bC\u0005\u0002\u0012V\t\t\u0011\"\u0003\u0002\u0014\nQ\"i\\;oI\u0016$7i\u001c8ue>d\u0017i^1sK6\u000b\u0017\u000e\u001c2pq*\u0011!gM\u0001\tI&\u001c\b/\u0019;dQ*\tA'\u0001\u0003bW.\f7\u0001A\n\u000b\u0001]j\u0014)!)\u0002(\u00065\u0006C\u0001\u001d<\u001b\u0005I$\"\u0001\u001e\u0002\u000bM\u001c\u0017\r\\1\n\u0005qJ$AB!osJ+g\r\u0005\u0002?\u007f5\t\u0011'\u0003\u0002Ac\tYQ*Y5mE>DH+\u001f9f!\rq$\tR\u0005\u0003\u0007F\u0012A\u0003\u0015:pIV\u001cWm]'fgN\fw-Z)vKV,\u0007CA#\u0018\u001d\tqD#\u0001\u000eC_VtG-\u001a3D_:$(o\u001c7Bo\u0006\u0014X-T1jY\n|\u0007\u0010\u0005\u0002?+M\u0019QcN%\u0011\u0005){U\"A&\u000b\u00051k\u0015AA5p\u0015\u0005q\u0015\u0001\u00026bm\u0006L!\u0001U&\u0003\u0019M+'/[1mSj\f'\r\\3\u0002\rqJg.\u001b;?)\u00059%\u0001D'fgN\fw-Z)vKV,7\u0003B\f8+&\u0003\"A\u0010,\n\u0005]\u000b$\u0001\u000b\"pk:$W\rZ\"p]R\u0014x\u000e\\!xCJ,W*Z:tC\u001e,\u0017+^3vKN+W.\u00198uS\u000e\u001c\u0018\u0001C2ba\u0006\u001c\u0017\u000e^=\u0016\u0003i\u0003\"\u0001O.\n\u0005qK$aA%oi\u0006I1-\u00199bG&$\u0018\u0010I\u0001\faV\u001c\b\u000eV5nK>+H/F\u0001a!\t\tg-D\u0001c\u0015\t\u0019G-\u0001\u0005ekJ\fG/[8o\u0015\t)\u0017(\u0001\u0006d_:\u001cWO\u001d:f]RL!a\u001a2\u0003\u001d\u0019Kg.\u001b;f\tV\u0014\u0018\r^5p]\u0006a\u0001/^:i)&lWmT;uAQ\u0019!\u000e\\7\u0011\u0005-<R\"A\u000b\t\u000bac\u0002\u0019\u0001.\t\u000byc\u0002\u0019\u00011\u0002\tML'0Z\u000b\u0002aB\u0011\u0011o^\u0007\u0002e*\u00111\u000f^\u0001\u0007CR|W.[2\u000b\u0005\u0015,(B\u0001<N\u0003\u0011)H/\u001b7\n\u0005a\u0014(!D!u_6L7-\u00138uK\u001e,'/A\u0003tSj,\u0007%A\u0004qkRdunY6\u0016\u0003q\u00042!`A\u0001\u001b\u0005q(BA@u\u0003\u0015awnY6t\u0013\r\t\u0019A \u0002\u000e%\u0016,g\u000e\u001e:b]RdunY6\u0002\u0011A,H\u000fT8dW\u0002\nqA\\8u\rVdG.\u0006\u0002\u0002\fA\u0019Q0!\u0004\n\u0007\u0005=aPA\u0005D_:$\u0017\u000e^5p]\u0006Aan\u001c;Gk2d\u0007%\u0001\u0007d_:$(o\u001c7Rk\u0016,X-\u0006\u0002\u0002\u0018A1\u0011\u0011DA\u000e\u0003?i\u0011\u0001^\u0005\u0004\u0003;!(!F\"p]\u000e,(O]3oi2Kgn[3e#V,W/\u001a\t\u0004}\u0005\u0005\u0012bAA\u0012c\tAQI\u001c<fY>\u0004X-A\u0007d_:$(o\u001c7Rk\u0016,X\rI\u0001\u0006cV,W/Z\u0001\u0007cV,W/\u001a\u0011\u0002\u000f\u0015t\u0017/^3vKR1\u0011qFA\u001b\u0003\u000b\u00022\u0001OA\u0019\u0013\r\t\u0019$\u000f\u0002\u0005+:LG\u000fC\u0004\u00028\u001d\u0002\r!!\u000f\u0002\u0011I,7-Z5wKJ\u0004B!a\u000f\u0002B5\u0011\u0011Q\b\u0006\u0004\u0003\u007f\u0019\u0014!B1di>\u0014\u0018\u0002BA\"\u0003{\u0011\u0001\"Q2u_J\u0014VM\u001a\u0005\b\u0003\u000f:\u0003\u0019AA\u0010\u0003\u0019A\u0017M\u001c3mK\u0006\u0001b.^7cKJ|e-T3tg\u0006<Wm]\u0001\fQ\u0006\u001cX*Z:tC\u001e,7/\u0006\u0002\u0002PA\u0019\u0001(!\u0015\n\u0007\u0005M\u0013HA\u0004C_>dW-\u00198\u0002\u000f\u0011,\u0017/^3vKR\u0011\u0011qD\u0001\u000eg&<g.\u00197O_R4U\u000f\u001c7\u0015\u0005\u0005=\u0012AE3ocV,W/Z,ji\"$\u0016.\\3pkR$\u0002\"a\f\u0002b\u00055\u0014q\u000e\u0005\b\u0003Gb\u0003\u0019AA3\u0003\u0005\t\bCBA4\u0003S\ny\"D\u0001v\u0013\r\tY'\u001e\u0002\u0006#V,W/\u001a\u0005\b\u0003oa\u0003\u0019AA\u001d\u0011\u001d\t\t\b\fa\u0001\u0003?\t\u0001\"\u001a8wK2|\u0007/Z\u0001\u0006CB\u0004H.\u001f\u000b\u0007\u0003o\nI(a\u001f\u0011\u0005y\u0002\u0001\"\u0002-.\u0001\u0004Q\u0006\"\u00020.\u0001\u0004\u0001\u0017aB;oCB\u0004H.\u001f\u000b\u0005\u0003\u0003\u000bi\tE\u00039\u0003\u0007\u000b9)C\u0002\u0002\u0006f\u0012aa\u00149uS>t\u0007#\u0002\u001d\u0002\nj\u0003\u0017bAAFs\t1A+\u001e9mKJB\u0011\"a$/\u0003\u0003\u0005\r!a\u001e\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002\u0016B!\u0011qSAO\u001b\t\tIJC\u0002\u0002\u001c6\u000bA\u0001\\1oO&!\u0011qTAM\u0005\u0019y%M[3diB\u0019a(a)\n\u0007\u0005\u0015\u0016GA\u0012Qe>$WoY3t!V\u001c\b\u000eV5nK>,HoU3nC:$\u0018nY:NC&d'm\u001c=\u0011\u0007a\nI+C\u0002\u0002,f\u0012q\u0001\u0015:pIV\u001cG\u000f\u0005\u0003\u00020\u0006}f\u0002BAY\u0003wsA!a-\u0002:6\u0011\u0011Q\u0017\u0006\u0004\u0003o+\u0014A\u0002\u001fs_>$h(C\u0001;\u0013\r\ti,O\u0001\ba\u0006\u001c7.Y4f\u0013\r\u0001\u0016\u0011\u0019\u0006\u0004\u0003{KDCBA<\u0003\u000b\f9\rC\u0003Y\u000b\u0001\u0007!\fC\u0003_\u000b\u0001\u0007\u0001\r\u0006\u0004\u0002x\u0005-\u0017Q\u001c\u0005\b\u0003\u001b4\u0001\u0019AAh\u0003!\u0019X\r\u001e;j]\u001e\u001c\b\u0003BAi\u0003/tA!a\u000f\u0002T&!\u0011Q[A\u001f\u0003-\t5\r^8s'f\u001cH/Z7\n\t\u0005e\u00171\u001c\u0002\t'\u0016$H/\u001b8hg*!\u0011Q[A\u001f\u0011\u001d\tyN\u0002a\u0001\u0003C\faaY8oM&<\u0007\u0003BAr\u0003_l!!!:\u000b\t\u0005}\u0017q\u001d\u0006\u0005\u0003S\fY/\u0001\u0005usB,7/\u00194f\u0015\t\ti/A\u0002d_6LA!!=\u0002f\n11i\u001c8gS\u001e\faa\u0019:fCR,GCBA|\u0003w\u0014\t\u0001E\u0002?\u0003sL!aU\u0019\t\u000f\u0005ux\u00011\u0001\u0002\u0000\u0006)qn\u001e8feB)\u0001(a!\u0002:!9!1A\u0004A\u0002\t\u0015\u0011AB:zgR,W\u000eE\u00039\u0003\u0007\u00139\u0001\u0005\u0003\u0002<\t%\u0011\u0002\u0002B\u0006\u0003{\u00111\"Q2u_J\u001c\u0016p\u001d;f[\u0006!1m\u001c9z)\u0019\t9H!\u0005\u0003\u0014!9\u0001\f\u0003I\u0001\u0002\u0004Q\u0006b\u00020\t!\u0003\u0005\r\u0001Y\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\u0011IBK\u0002[\u00057Y#A!\b\u0011\t\t}!\u0011F\u0007\u0003\u0005CQAAa\t\u0003&\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0005OI\u0014AC1o]>$\u0018\r^5p]&!!1\u0006B\u0011\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t\u0011\tDK\u0002a\u00057\tQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXC\u0001B\u001c!\u0011\t9J!\u000f\n\t\tm\u0012\u0011\u0014\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!!1\tB%!\rA$QI\u0005\u0004\u0005\u000fJ$aA!os\"A!1J\u0007\u0002\u0002\u0003\u0007!,A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0005#\u0002bAa\u0015\u0003Z\t\rSB\u0001B+\u0015\r\u00119&O\u0001\u000bG>dG.Z2uS>t\u0017\u0002\u0002B.\u0005+\u0012\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u0011q\nB1\u0011%\u0011YeDA\u0001\u0002\u0004\u0011\u0019%\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003\u0002B\u001c\u0005OB\u0001Ba\u0013\u0011\u0003\u0003\u0005\rAW\u0001\tQ\u0006\u001c\bnQ8eKR\t!,\u0001\u0005u_N#(/\u001b8h)\t\u00119$\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003\u001f\u0012)\bC\u0005\u0003LM\t\t\u00111\u0001\u0003D\u0001")
public final class BoundedControlAwareMailbox
implements MailboxType,
ProducesMessageQueue<MessageQueue>,
ProducesPushTimeoutSemanticsMailbox,
Product,
Serializable {
    private final int capacity;
    private final FiniteDuration pushTimeOut;

    public static Option<Tuple2<Object, FiniteDuration>> unapply(BoundedControlAwareMailbox x$0) {
        return BoundedControlAwareMailbox$.MODULE$.unapply(x$0);
    }

    public static BoundedControlAwareMailbox apply(int capacity, FiniteDuration pushTimeOut) {
        return BoundedControlAwareMailbox$.MODULE$.apply(capacity, pushTimeOut);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int capacity() {
        return this.capacity;
    }

    @Override
    public final FiniteDuration pushTimeOut() {
        return this.pushTimeOut;
    }

    @Override
    public akka.dispatch.MessageQueue create(Option<ActorRef> owner, Option<ActorSystem> system) {
        return new MessageQueue(this.capacity(), this.pushTimeOut());
    }

    public BoundedControlAwareMailbox copy(int capacity, FiniteDuration pushTimeOut) {
        return new BoundedControlAwareMailbox(capacity, pushTimeOut);
    }

    public int copy$default$1() {
        return this.capacity();
    }

    public FiniteDuration copy$default$2() {
        return this.pushTimeOut();
    }

    @Override
    public String productPrefix() {
        return "BoundedControlAwareMailbox";
    }

    @Override
    public int productArity() {
        return 2;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return BoxesRunTime.boxToInteger(this.capacity());
            }
            case 1: {
                return this.pushTimeOut();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof BoundedControlAwareMailbox;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "capacity";
            }
            case 1: {
                return "pushTimeOut";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, this.capacity());
        n = Statics.mix(n, Statics.anyHash(this.pushTimeOut()));
        return Statics.finalizeHash(n, 2);
    }

    public String toString() {
        return ScalaRunTime$.MODULE$._toString(this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof BoundedControlAwareMailbox)) return false;
        boolean bl = true;
        if (!bl) return false;
        BoundedControlAwareMailbox boundedControlAwareMailbox = (BoundedControlAwareMailbox)x$1;
        if (this.capacity() != boundedControlAwareMailbox.capacity()) return false;
        FiniteDuration finiteDuration = this.pushTimeOut();
        FiniteDuration finiteDuration2 = boundedControlAwareMailbox.pushTimeOut();
        if (finiteDuration != null) {
            if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
            return true;
        }
        if (finiteDuration2 == null) return true;
        return false;
    }

    public BoundedControlAwareMailbox(int capacity, FiniteDuration pushTimeOut) {
        this.capacity = capacity;
        this.pushTimeOut = pushTimeOut;
        Product.$init$(this);
    }

    public BoundedControlAwareMailbox(ActorSystem.Settings settings, Config config) {
        this(config.getInt("mailbox-capacity"), Helpers$ConfigOps$.MODULE$.getNanosDuration$extension(Helpers$.MODULE$.ConfigOps(config), "mailbox-push-timeout-time"));
    }

    public static class MessageQueue
    implements BoundedControlAwareMessageQueueSemantics,
    Serializable {
        private final int capacity;
        private final FiniteDuration pushTimeOut;
        private final AtomicInteger size;
        private final ReentrantLock putLock;
        private final Condition notFull;
        private final ConcurrentLinkedQueue<Envelope> controlQueue;
        private final ConcurrentLinkedQueue<Envelope> queue;

        @Override
        public void cleanUp(ActorRef owner, akka.dispatch.MessageQueue deadLetters) {
            QueueBasedMessageQueue.cleanUp$(this, owner, deadLetters);
        }

        public int capacity() {
            return this.capacity;
        }

        @Override
        public FiniteDuration pushTimeOut() {
            return this.pushTimeOut;
        }

        private final AtomicInteger size() {
            return this.size;
        }

        private final ReentrantLock putLock() {
            return this.putLock;
        }

        private final Condition notFull() {
            return this.notFull;
        }

        public ConcurrentLinkedQueue<Envelope> controlQueue() {
            return this.controlQueue;
        }

        public ConcurrentLinkedQueue<Envelope> queue() {
            return this.queue;
        }

        @Override
        public void enqueue(ActorRef receiver, Envelope handle2) {
            Envelope envelope = handle2;
            if (envelope != null && envelope.message() instanceof ControlMessage) {
                this.enqueueWithTimeout(this.controlQueue(), receiver, envelope);
                return;
            }
            this.enqueueWithTimeout(this.queue(), receiver, envelope);
        }

        @Override
        public int numberOfMessages() {
            return this.size().get();
        }

        @Override
        public boolean hasMessages() {
            return this.numberOfMessages() > 0;
        }

        @Override
        public final Envelope dequeue() {
            return this.tailrecDequeue$1();
        }

        private void signalNotFull() {
            this.putLock().lock();
            try {
                this.notFull().signal();
            }
            finally {
                this.putLock().unlock();
            }
        }

        private final void enqueueWithTimeout(Queue<Envelope> q, ActorRef receiver, Envelope envelope) {
            boolean bl;
            long remaining = this.pushTimeOut().toNanos();
            this.putLock().lockInterruptibly();
            try {
                boolean stop = false;
                while (this.size().get() == this.capacity() && !stop) {
                    remaining = this.notFull().awaitNanos(remaining);
                    stop = remaining <= 0L;
                }
                if (stop) {
                    bl = false;
                } else {
                    q.add(envelope);
                    int c = this.size().incrementAndGet();
                    if (c < this.capacity()) {
                        this.notFull().signal();
                    }
                    bl = true;
                }
            }
            finally {
                this.putLock().unlock();
            }
            boolean inserted = bl;
            if (!inserted) {
                ((InternalActorRef)receiver).provider().deadLetters().tell(new DeadLetter(envelope.message(), envelope.sender(), receiver), envelope.sender());
                return;
            }
        }

        private final Envelope tailrecDequeue$1() {
            int count;
            while ((count = this.size().get()) > 0) {
                if (!this.size().compareAndSet(count, count - 1)) continue;
                Envelope item = ControlAwareMessageQueueSemantics.dequeue$(this);
                if (this.size().get() < this.capacity()) {
                    this.signalNotFull();
                }
                return item;
            }
            return null;
        }

        public MessageQueue(int capacity, FiniteDuration pushTimeOut) {
            this.capacity = capacity;
            this.pushTimeOut = pushTimeOut;
            QueueBasedMessageQueue.$init$(this);
            ControlAwareMessageQueueSemantics.$init$(this);
            this.size = new AtomicInteger(0);
            this.putLock = new ReentrantLock();
            this.notFull = this.putLock().newCondition();
            this.controlQueue = new ConcurrentLinkedQueue();
            this.queue = new ConcurrentLinkedQueue();
        }
    }
}

