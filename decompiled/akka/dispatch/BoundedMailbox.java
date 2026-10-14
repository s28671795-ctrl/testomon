/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.BoundedMailbox$;
import akka.dispatch.BoundedQueueBasedMessageQueue;
import akka.dispatch.Envelope;
import akka.dispatch.MailboxType;
import akka.dispatch.ProducesMessageQueue;
import akka.dispatch.ProducesPushTimeoutSemanticsMailbox;
import akka.dispatch.QueueBasedMessageQueue;
import akka.util.Helpers$;
import akka.util.Helpers$ConfigOps$;
import com.typesafe.config.Config;
import java.io.Serializable;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005Eh\u0001\u0002\u0011\"\u0005\u001aB\u0011b\u0015\u0001\u0003\u0016\u0004%\t!a\n\t\u0013\u0005%\u0002A!E!\u0002\u0013!\u0006\u0002C,\u0001\u0005+\u0007I\u0011\t-\t\u0011\u0001\u0004!\u0011#Q\u0001\neCa!\u0011\u0001\u0005\u0002\u0005-\u0002BB!\u0001\t\u0003\t\t\u0004C\u0004\u0002b\u0001!\t%a\u0019\t\u0013\u0005\u0005\u0005!!A\u0005\u0002\u0005\r\u0005\"CAE\u0001E\u0005I\u0011AAF\u0011%\t\t\u000bAI\u0001\n\u0003\t\u0019\u000bC\u0005\u0002(\u0002\t\t\u0011\"\u0011\u0002*\"I\u0011\u0011\u0017\u0001\u0002\u0002\u0013\u0005\u0011q\u0005\u0005\n\u0003g\u0003\u0011\u0011!C\u0001\u0003kC\u0011\"!1\u0001\u0003\u0003%\t%a1\t\u0013\u0005E\u0007!!A\u0005\u0002\u0005M\u0007\"CAo\u0001\u0005\u0005I\u0011IAp\u0011%\t\u0019\u000fAA\u0001\n\u0003\n)\u000fC\u0005\u0002h\u0002\t\t\u0011\"\u0011\u0002j\"I\u00111\u001e\u0001\u0002\u0002\u0013\u0005\u0013Q^\u0004\u0006m\u0005B\ta\u000e\u0004\u0006A\u0005B\t\u0001\u000f\u0005\u0006\u0003V!\tA\u0011\u0004\u0005\u0007V\u0001A\t\u0003\u0005T/\t\u0005\t\u0015!\u0003U\u0011!9vC!b\u0001\n\u000bA\u0006\u0002\u00031\u0018\u0005\u0003\u0005\u000bQB-\t\u000b\u0005;B\u0011A1\t\u000b\u0019<BQA4\t\u000f-,\u0012\u0011!CAY\"9\u0001/FA\u0001\n\u0003\u000b\bb\u0002>\u0016\u0003\u0003%Ia\u001f\u0002\u000f\u0005>,h\u000eZ3e\u001b\u0006LGNY8y\u0015\t\u00113%\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005!\u0013\u0001B1lW\u0006\u001c\u0001a\u0005\u0006\u0001O5\n\u0014QAA\u0006\u0003#\u0001\"\u0001K\u0016\u000e\u0003%R\u0011AK\u0001\u0006g\u000e\fG.Y\u0005\u0003Y%\u0012a!\u00118z%\u00164\u0007C\u0001\u00180\u001b\u0005\t\u0013B\u0001\u0019\"\u0005-i\u0015-\u001b7c_b$\u0016\u0010]3\u0011\u00079\u0012D'\u0003\u00024C\t!\u0002K]8ek\u000e,7/T3tg\u0006<W-U;fk\u0016\u0004\"!N\f\u000f\u00059\"\u0012A\u0004\"pk:$W\rZ'bS2\u0014w\u000e\u001f\t\u0003]U\u00192!F\u0014:!\tQt(D\u0001<\u0015\taT(\u0001\u0002j_*\ta(\u0001\u0003kCZ\f\u0017B\u0001!<\u00051\u0019VM]5bY&T\u0018M\u00197f\u0003\u0019a\u0014N\\5u}Q\tqG\u0001\u0007NKN\u001c\u0018mZ3Rk\u0016,XmE\u0002\u0018\u000bB\u00032AR&N\u001b\u00059%B\u0001%J\u0003)\u0019wN\\2veJ,g\u000e\u001e\u0006\u0003\u0015v\nA!\u001e;jY&\u0011Aj\u0012\u0002\u0014\u0019&t7.\u001a3CY>\u001c7.\u001b8h#V,W/\u001a\t\u0003]9K!aT\u0011\u0003\u0011\u0015sg/\u001a7pa\u0016\u0004\"AL)\n\u0005I\u000b#!\b\"pk:$W\rZ)vKV,')Y:fI6+7o]1hKF+X-^3\u0002\u0011\r\f\u0007/Y2jif\u0004\"\u0001K+\n\u0005YK#aA%oi\u0006Y\u0001/^:i)&lWmT;u+\u0005I\u0006C\u0001._\u001b\u0005Y&B\u0001/^\u0003!!WO]1uS>t'B\u0001%*\u0013\ty6L\u0001\bGS:LG/\u001a#ve\u0006$\u0018n\u001c8\u0002\u0019A,8\u000f\u001b+j[\u0016|U\u000f\u001e\u0011\u0015\u0007\t$W\r\u0005\u0002d/5\tQ\u0003C\u0003T7\u0001\u0007A\u000bC\u0003X7\u0001\u0007\u0011,A\u0003rk\u0016,X-F\u0001i!\r1\u0015.T\u0005\u0003U\u001e\u0013QB\u00117pG.LgnZ)vKV,\u0017!B1qa2LHcA7o_B\u0011a\u0006\u0001\u0005\u0006'v\u0001\r\u0001\u0016\u0005\u0006/v\u0001\r!W\u0001\bk:\f\u0007\u000f\u001d7z)\t\u0011\b\u0010E\u0002)gVL!\u0001^\u0015\u0003\r=\u0003H/[8o!\u0011Ac\u000fV-\n\u0005]L#A\u0002+va2,'\u0007C\u0004z=\u0005\u0005\t\u0019A7\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001}!\ri\u0018\u0011A\u0007\u0002}*\u0011q0P\u0001\u0005Y\u0006tw-C\u0002\u0002\u0004y\u0014aa\u00142kK\u000e$\bc\u0001\u0018\u0002\b%\u0019\u0011\u0011B\u0011\u0003GA\u0013x\u000eZ;dKN\u0004Vo\u001d5US6,w.\u001e;TK6\fg\u000e^5dg6\u000b\u0017\u000e\u001c2pqB\u0019\u0001&!\u0004\n\u0007\u0005=\u0011FA\u0004Qe>$Wo\u0019;\u0011\t\u0005M\u00111\u0005\b\u0005\u0003+\tyB\u0004\u0003\u0002\u0018\u0005uQBAA\r\u0015\r\tY\"J\u0001\u0007yI|w\u000e\u001e \n\u0003)J1!!\t*\u0003\u001d\u0001\u0018mY6bO\u0016L1\u0001QA\u0013\u0015\r\t\t#K\u000b\u0002)\u0006I1-\u00199bG&$\u0018\u0010\t\u000b\u0006[\u00065\u0012q\u0006\u0005\u0006'\u0016\u0001\r\u0001\u0016\u0005\u0006/\u0016\u0001\r!\u0017\u000b\u0006[\u0006M\u00121\n\u0005\b\u0003k1\u0001\u0019AA\u001c\u0003!\u0019X\r\u001e;j]\u001e\u001c\b\u0003BA\u001d\u0003\u000brA!a\u000f\u0002B5\u0011\u0011Q\b\u0006\u0004\u0003\u007f\u0019\u0013!B1di>\u0014\u0018\u0002BA\"\u0003{\t1\"Q2u_J\u001c\u0016p\u001d;f[&!\u0011qIA%\u0005!\u0019V\r\u001e;j]\u001e\u001c(\u0002BA\"\u0003{Aq!!\u0014\u0007\u0001\u0004\ty%\u0001\u0004d_:4\u0017n\u001a\t\u0005\u0003#\ni&\u0004\u0002\u0002T)!\u0011QJA+\u0015\u0011\t9&!\u0017\u0002\u0011QL\b/Z:bM\u0016T!!a\u0017\u0002\u0007\r|W.\u0003\u0003\u0002`\u0005M#AB\"p]\u001aLw-\u0001\u0004de\u0016\fG/\u001a\u000b\u0007\u0003K\nI'!\u001e\u0011\u00079\n9'\u0003\u0002DC!9\u00111N\u0004A\u0002\u00055\u0014!B8x]\u0016\u0014\b\u0003\u0002\u0015t\u0003_\u0002B!a\u000f\u0002r%!\u00111OA\u001f\u0005!\t5\r^8s%\u00164\u0007bBA<\u000f\u0001\u0007\u0011\u0011P\u0001\u0007gf\u001cH/Z7\u0011\t!\u001a\u00181\u0010\t\u0005\u0003w\ti(\u0003\u0003\u0002\u0000\u0005u\"aC!di>\u00148+_:uK6\fAaY8qsR)Q.!\"\u0002\b\"91\u000b\u0003I\u0001\u0002\u0004!\u0006bB,\t!\u0003\u0005\r!W\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\tiIK\u0002U\u0003\u001f[#!!%\u0011\t\u0005M\u0015QT\u0007\u0003\u0003+SA!a&\u0002\u001a\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u00037K\u0013AC1o]>$\u0018\r^5p]&!\u0011qTAK\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t\t)KK\u0002Z\u0003\u001f\u000bQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAAV!\ri\u0018QV\u0005\u0004\u0003_s(AB*ue&tw-\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005]\u0016Q\u0018\t\u0004Q\u0005e\u0016bAA^S\t\u0019\u0011I\\=\t\u0011\u0005}V\"!AA\u0002Q\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAAc!\u0019\t9-!4\u000286\u0011\u0011\u0011\u001a\u0006\u0004\u0003\u0017L\u0013AC2pY2,7\r^5p]&!\u0011qZAe\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005U\u00171\u001c\t\u0004Q\u0005]\u0017bAAmS\t9!i\\8mK\u0006t\u0007\"CA`\u001f\u0005\u0005\t\u0019AA\\\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0005-\u0016\u0011\u001d\u0005\t\u0003\u007f\u0003\u0012\u0011!a\u0001)\u0006A\u0001.Y:i\u0007>$W\rF\u0001U\u0003!!xn\u0015;sS:<GCAAV\u0003\u0019)\u0017/^1mgR!\u0011Q[Ax\u0011%\tylEA\u0001\u0002\u0004\t9\f")
public final class BoundedMailbox
implements MailboxType,
ProducesMessageQueue<MessageQueue>,
ProducesPushTimeoutSemanticsMailbox,
Product,
Serializable {
    private final int capacity;
    private final FiniteDuration pushTimeOut;

    public static Option<Tuple2<Object, FiniteDuration>> unapply(BoundedMailbox x$0) {
        return BoundedMailbox$.MODULE$.unapply(x$0);
    }

    public static BoundedMailbox apply(int capacity, FiniteDuration pushTimeOut) {
        return BoundedMailbox$.MODULE$.apply(capacity, pushTimeOut);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public int capacity() {
        return this.capacity;
    }

    @Override
    public FiniteDuration pushTimeOut() {
        return this.pushTimeOut;
    }

    @Override
    public akka.dispatch.MessageQueue create(Option<ActorRef> owner, Option<ActorSystem> system) {
        return new MessageQueue(this.capacity(), this.pushTimeOut());
    }

    public BoundedMailbox copy(int capacity, FiniteDuration pushTimeOut) {
        return new BoundedMailbox(capacity, pushTimeOut);
    }

    public int copy$default$1() {
        return this.capacity();
    }

    public FiniteDuration copy$default$2() {
        return this.pushTimeOut();
    }

    @Override
    public String productPrefix() {
        return "BoundedMailbox";
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
        return x$1 instanceof BoundedMailbox;
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
        if (!(object instanceof BoundedMailbox)) return false;
        boolean bl = true;
        if (!bl) return false;
        BoundedMailbox boundedMailbox = (BoundedMailbox)x$1;
        if (this.capacity() != boundedMailbox.capacity()) return false;
        FiniteDuration finiteDuration = this.pushTimeOut();
        FiniteDuration finiteDuration2 = boundedMailbox.pushTimeOut();
        if (finiteDuration != null) {
            if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
            return true;
        }
        if (finiteDuration2 == null) return true;
        return false;
    }

    public BoundedMailbox(int capacity, FiniteDuration pushTimeOut) {
        this.capacity = capacity;
        this.pushTimeOut = pushTimeOut;
        Product.$init$(this);
        if (capacity < 0) {
            throw new IllegalArgumentException("The capacity for BoundedMailbox can not be negative");
        }
        if (pushTimeOut == null) {
            throw new IllegalArgumentException("The push time-out for BoundedMailbox can not be null");
        }
    }

    public BoundedMailbox(ActorSystem.Settings settings, Config config) {
        this(config.getInt("mailbox-capacity"), Helpers$ConfigOps$.MODULE$.getNanosDuration$extension(Helpers$.MODULE$.ConfigOps(config), "mailbox-push-timeout-time"));
    }

    public static class MessageQueue
    extends LinkedBlockingQueue<Envelope>
    implements BoundedQueueBasedMessageQueue {
        private final FiniteDuration pushTimeOut;

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
        public final FiniteDuration pushTimeOut() {
            return this.pushTimeOut;
        }

        @Override
        public final BlockingQueue<Envelope> queue() {
            return this;
        }

        public MessageQueue(int capacity, FiniteDuration pushTimeOut) {
            this.pushTimeOut = pushTimeOut;
            super(capacity);
            QueueBasedMessageQueue.$init$(this);
            BoundedQueueBasedMessageQueue.$init$(this);
        }
    }
}

