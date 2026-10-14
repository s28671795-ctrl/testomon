/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.BoundedDequeBasedMailbox$;
import akka.dispatch.BoundedDequeBasedMessageQueue;
import akka.dispatch.Envelope;
import akka.dispatch.MailboxType;
import akka.dispatch.ProducesMessageQueue;
import akka.dispatch.ProducesPushTimeoutSemanticsMailbox;
import akka.dispatch.QueueBasedMessageQueue;
import akka.util.Helpers$;
import akka.util.Helpers$ConfigOps$;
import com.typesafe.config.Config;
import java.io.Serializable;
import java.util.concurrent.LinkedBlockingDeque;
import scala.Option;
import scala.Product;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005=h\u0001B\u0011#\u0001\u001eB\u0011\u0002\u0016\u0001\u0003\u0016\u0004%)!!\n\t\u0013\u0005\u001d\u0002A!E!\u0002\u001b)\u0006\u0002\u0003-\u0001\u0005+\u0007IQI-\t\u0011\u0005\u0004!\u0011#Q\u0001\u000eiCaA\u0011\u0001\u0005\u0002\u0005%\u0002B\u0002\"\u0001\t\u0003\ty\u0003C\u0004\u0002`\u0001!)%!\u0019\t\u0013\u0005}\u0004!!A\u0005\u0002\u0005\u0005\u0005\"CAD\u0001E\u0005I\u0011AAE\u0011%\ty\nAI\u0001\n\u0003\t\t\u000bC\u0005\u0002&\u0002\t\t\u0011\"\u0011\u0002(\"I\u0011q\u0016\u0001\u0002\u0002\u0013\u0005\u0011Q\u0005\u0005\n\u0003c\u0003\u0011\u0011!C\u0001\u0003gC\u0011\"a0\u0001\u0003\u0003%\t%!1\t\u0013\u0005=\u0007!!A\u0005\u0002\u0005E\u0007\"CAn\u0001\u0005\u0005I\u0011IAo\u0011%\t\t\u000fAA\u0001\n\u0003\n\u0019\u000fC\u0005\u0002f\u0002\t\t\u0011\"\u0011\u0002h\"I\u0011\u0011\u001e\u0001\u0002\u0002\u0013\u0005\u00131^\u0004\u0006o\tB\t\u0001\u000f\u0004\u0006C\tB\t!\u000f\u0005\u0006\u0005V!\ta\u0011\u0004\u0005\tV\u0001Q\t\u0003\u0005U/\t\u0005\t\u0015!\u0003V\u0011!AvC!b\u0001\n\u0003I\u0006\u0002C1\u0018\u0005\u0003\u0005\u000b\u0011\u0002.\t\u000b\t;B\u0011\u00012\t\u000f\u001d<\"\u0019!C\u0003Q\"1\u0011n\u0006Q\u0001\u000e\rDqA[\u000b\u0002\u0002\u0013\u00055\u000eC\u0004p+\u0005\u0005I\u0011\u00119\t\u000fe,\u0012\u0011!C\u0005u\nA\"i\\;oI\u0016$G)Z9vK\n\u000b7/\u001a3NC&d'm\u001c=\u000b\u0005\r\"\u0013\u0001\u00033jgB\fGo\u00195\u000b\u0003\u0015\nA!Y6lC\u000e\u00011C\u0003\u0001)]I\n\u0019!!\u0003\u0002\u0010A\u0011\u0011\u0006L\u0007\u0002U)\t1&A\u0003tG\u0006d\u0017-\u0003\u0002.U\t1\u0011I\\=SK\u001a\u0004\"a\f\u0019\u000e\u0003\tJ!!\r\u0012\u0003\u00175\u000b\u0017\u000e\u001c2pqRK\b/\u001a\t\u0004_M*\u0014B\u0001\u001b#\u0005Q\u0001&o\u001c3vG\u0016\u001cX*Z:tC\u001e,\u0017+^3vKB\u0011ag\u0006\b\u0003_Q\t\u0001DQ8v]\u0012,G\rR3rk\u0016\u0014\u0015m]3e\u001b\u0006LGNY8y!\tyScE\u0002\u0016Qi\u0002\"a\u000f!\u000e\u0003qR!!\u0010 \u0002\u0005%|'\"A \u0002\t)\fg/Y\u0005\u0003\u0003r\u0012AbU3sS\u0006d\u0017N_1cY\u0016\fa\u0001P5oSRtD#\u0001\u001d\u0003\u00195+7o]1hKF+X-^3\u0014\u0007]1\u0015\u000bE\u0002H\u0019:k\u0011\u0001\u0013\u0006\u0003\u0013*\u000b!bY8oGV\u0014(/\u001a8u\u0015\tYe(\u0001\u0003vi&d\u0017BA'I\u0005Ma\u0015N\\6fI\ncwnY6j]\u001e$U-];f!\tys*\u0003\u0002QE\tAQI\u001c<fY>\u0004X\r\u0005\u00020%&\u00111K\t\u0002\u001e\u0005>,h\u000eZ3e\t\u0016\fX/\u001a\"bg\u0016$W*Z:tC\u001e,\u0017+^3vK\u0006A1-\u00199bG&$\u0018\u0010\u0005\u0002*-&\u0011qK\u000b\u0002\u0004\u0013:$\u0018a\u00039vg\"$\u0016.\\3PkR,\u0012A\u0017\t\u00037~k\u0011\u0001\u0018\u0006\u0003;z\u000b\u0001\u0002Z;sCRLwN\u001c\u0006\u0003\u0013*J!\u0001\u0019/\u0003\u001d\u0019Kg.\u001b;f\tV\u0014\u0018\r^5p]\u0006a\u0001/^:i)&lWmT;uAQ\u00191-\u001a4\u0011\u0005\u0011<R\"A\u000b\t\u000bQ[\u0002\u0019A+\t\u000ba[\u0002\u0019\u0001.\u0002\u000bE,X-^3\u0016\u0003\r\fa!];fk\u0016\u0004\u0013!B1qa2LHc\u00017n]B\u0011q\u0006\u0001\u0005\u0006)z\u0001\r!\u0016\u0005\u00061z\u0001\rAW\u0001\bk:\f\u0007\u000f\u001d7z)\t\tx\u000fE\u0002*eRL!a\u001d\u0016\u0003\r=\u0003H/[8o!\u0011IS/\u0016.\n\u0005YT#A\u0002+va2,'\u0007C\u0004y?\u0005\u0005\t\u0019\u00017\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\rF\u0001|!\tax0D\u0001~\u0015\tqh(\u0001\u0003mC:<\u0017bAA\u0001{\n1qJ\u00196fGR\u00042aLA\u0003\u0013\r\t9A\t\u0002$!J|G-^2fgB+8\u000f\u001b+j[\u0016|W\u000f^*f[\u0006tG/[2t\u001b\u0006LGNY8y!\rI\u00131B\u0005\u0004\u0003\u001bQ#a\u0002)s_\u0012,8\r\u001e\t\u0005\u0003#\t\tC\u0004\u0003\u0002\u0014\u0005ua\u0002BA\u000b\u00037i!!a\u0006\u000b\u0007\u0005ea%\u0001\u0004=e>|GOP\u0005\u0002W%\u0019\u0011q\u0004\u0016\u0002\u000fA\f7m[1hK&\u0019\u0011)a\t\u000b\u0007\u0005}!&F\u0001V\u0003%\u0019\u0017\r]1dSRL\b\u0005F\u0003m\u0003W\ti\u0003C\u0003U\u000b\u0001\u0007Q\u000bC\u0003Y\u000b\u0001\u0007!\fF\u0003m\u0003c\tI\u0005C\u0004\u00024\u0019\u0001\r!!\u000e\u0002\u0011M,G\u000f^5oON\u0004B!a\u000e\u0002D9!\u0011\u0011HA \u001b\t\tYDC\u0002\u0002>\u0011\nQ!Y2u_JLA!!\u0011\u0002<\u0005Y\u0011i\u0019;peNK8\u000f^3n\u0013\u0011\t)%a\u0012\u0003\u0011M+G\u000f^5oONTA!!\u0011\u0002<!9\u00111\n\u0004A\u0002\u00055\u0013AB2p]\u001aLw\r\u0005\u0003\u0002P\u0005mSBAA)\u0015\u0011\tY%a\u0015\u000b\t\u0005U\u0013qK\u0001\tif\u0004Xm]1gK*\u0011\u0011\u0011L\u0001\u0004G>l\u0017\u0002BA/\u0003#\u0012aaQ8oM&<\u0017AB2sK\u0006$X\r\u0006\u0004\u0002d\u0005\u001d\u00141\u000f\t\u0004_\u0005\u0015\u0014B\u0001##\u0011\u001d\tIg\u0002a\u0001\u0003W\nQa\\<oKJ\u0004B!\u000b:\u0002nA!\u0011\u0011HA8\u0013\u0011\t\t(a\u000f\u0003\u0011\u0005\u001bGo\u001c:SK\u001aDq!!\u001e\b\u0001\u0004\t9(\u0001\u0004tsN$X-\u001c\t\u0005SI\fI\b\u0005\u0003\u0002:\u0005m\u0014\u0002BA?\u0003w\u00111\"Q2u_J\u001c\u0016p\u001d;f[\u0006!1m\u001c9z)\u0015a\u00171QAC\u0011\u001d!\u0006\u0002%AA\u0002UCq\u0001\u0017\u0005\u0011\u0002\u0003\u0007!,\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\u0005-%fA+\u0002\u000e.\u0012\u0011q\u0012\t\u0005\u0003#\u000bY*\u0004\u0002\u0002\u0014*!\u0011QSAL\u0003%)hn\u00195fG.,GMC\u0002\u0002\u001a*\n!\"\u00198o_R\fG/[8o\u0013\u0011\ti*a%\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001a\u0016\u0005\u0005\r&f\u0001.\u0002\u000e\u0006i\u0001O]8ek\u000e$\bK]3gSb,\"!!+\u0011\u0007q\fY+C\u0002\u0002.v\u0014aa\u0015;sS:<\u0017\u0001\u00049s_\u0012,8\r^!sSRL\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003k\u000bY\fE\u0002*\u0003oK1!!/+\u0005\r\te.\u001f\u0005\t\u0003{k\u0011\u0011!a\u0001+\u0006\u0019\u0001\u0010J\u0019\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"!a1\u0011\r\u0005\u0015\u00171ZA[\u001b\t\t9MC\u0002\u0002J*\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\ti-a2\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0003'\fI\u000eE\u0002*\u0003+L1!a6+\u0005\u001d\u0011un\u001c7fC:D\u0011\"!0\u0010\u0003\u0003\u0005\r!!.\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u0003S\u000by\u000e\u0003\u0005\u0002>B\t\t\u00111\u0001V\u0003!A\u0017m\u001d5D_\u0012,G#A+\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!+\u0002\r\u0015\fX/\u00197t)\u0011\t\u0019.!<\t\u0013\u0005u6#!AA\u0002\u0005U\u0006")
public class BoundedDequeBasedMailbox
implements MailboxType,
ProducesMessageQueue<MessageQueue>,
ProducesPushTimeoutSemanticsMailbox,
Product,
Serializable {
    private final int capacity;
    private final FiniteDuration pushTimeOut;

    public static Option<Tuple2<Object, FiniteDuration>> unapply(BoundedDequeBasedMailbox x$0) {
        return BoundedDequeBasedMailbox$.MODULE$.unapply(x$0);
    }

    public static BoundedDequeBasedMailbox apply(int capacity, FiniteDuration pushTimeOut) {
        return BoundedDequeBasedMailbox$.MODULE$.apply(capacity, pushTimeOut);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public final int capacity() {
        return this.capacity;
    }

    @Override
    public final FiniteDuration pushTimeOut() {
        return this.pushTimeOut;
    }

    @Override
    public final akka.dispatch.MessageQueue create(Option<ActorRef> owner, Option<ActorSystem> system) {
        return new MessageQueue(this.capacity(), this.pushTimeOut());
    }

    public BoundedDequeBasedMailbox copy(int capacity, FiniteDuration pushTimeOut) {
        return new BoundedDequeBasedMailbox(capacity, pushTimeOut);
    }

    public int copy$default$1() {
        return this.capacity();
    }

    public FiniteDuration copy$default$2() {
        return this.pushTimeOut();
    }

    @Override
    public String productPrefix() {
        return "BoundedDequeBasedMailbox";
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
        return x$1 instanceof BoundedDequeBasedMailbox;
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
        if (!(object instanceof BoundedDequeBasedMailbox)) return false;
        boolean bl = true;
        if (!bl) return false;
        BoundedDequeBasedMailbox boundedDequeBasedMailbox = (BoundedDequeBasedMailbox)x$1;
        if (this.capacity() != boundedDequeBasedMailbox.capacity()) return false;
        FiniteDuration finiteDuration = this.pushTimeOut();
        FiniteDuration finiteDuration2 = boundedDequeBasedMailbox.pushTimeOut();
        if (finiteDuration == null) {
            if (finiteDuration2 != null) {
                return false;
            }
        } else if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
        if (!boundedDequeBasedMailbox.canEqual(this)) return false;
        return true;
    }

    public BoundedDequeBasedMailbox(int capacity, FiniteDuration pushTimeOut) {
        this.capacity = capacity;
        this.pushTimeOut = pushTimeOut;
        Product.$init$(this);
        if (capacity < 0) {
            throw new IllegalArgumentException("The capacity for BoundedDequeBasedMailbox can not be negative");
        }
        if (pushTimeOut == null) {
            throw new IllegalArgumentException("The push time-out for BoundedDequeBasedMailbox can not be null");
        }
    }

    public BoundedDequeBasedMailbox(ActorSystem.Settings settings, Config config) {
        this(config.getInt("mailbox-capacity"), Helpers$ConfigOps$.MODULE$.getNanosDuration$extension(Helpers$.MODULE$.ConfigOps(config), "mailbox-push-timeout-time"));
    }

    public static class MessageQueue
    extends LinkedBlockingDeque<Envelope>
    implements BoundedDequeBasedMessageQueue {
        private final FiniteDuration pushTimeOut;
        private final MessageQueue queue;

        @Override
        public void enqueue(ActorRef receiver, Envelope handle2) {
            BoundedDequeBasedMessageQueue.enqueue$(this, receiver, handle2);
        }

        @Override
        public void enqueueFirst(ActorRef receiver, Envelope handle2) {
            BoundedDequeBasedMessageQueue.enqueueFirst$(this, receiver, handle2);
        }

        @Override
        public Envelope dequeue() {
            return BoundedDequeBasedMessageQueue.dequeue$(this);
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
        public FiniteDuration pushTimeOut() {
            return this.pushTimeOut;
        }

        public final MessageQueue queue() {
            return this.queue;
        }

        public MessageQueue(int capacity, FiniteDuration pushTimeOut) {
            this.pushTimeOut = pushTimeOut;
            super(capacity);
            QueueBasedMessageQueue.$init$(this);
            BoundedDequeBasedMessageQueue.$init$(this);
            this.queue = this;
        }
    }
}

