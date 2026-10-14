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
import akka.dispatch.UnboundedMailbox$;
import akka.dispatch.UnboundedQueueBasedMessageQueue;
import com.typesafe.config.Config;
import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import scala.Option;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0005%e\u0001B\f\u0019\u0005vAQ\u0001\u000f\u0001\u0005\u0002QCQ\u0001\u000f\u0001\u0005\u0002MDq!a\u0006\u0001\t\u0003\nI\u0002\u0003\u0005\u0002<\u0001\t\t\u0011\"\u0001U\u0011%\ti\u0004AA\u0001\n\u0003\ny\u0004C\u0005\u0002H\u0001\t\t\u0011\"\u0001\u0002J!I\u0011\u0011\u000b\u0001\u0002\u0002\u0013\u0005\u00111\u000b\u0005\n\u0003?\u0002\u0011\u0011!C!\u0003CB\u0011\"a\u001c\u0001\u0003\u0003%\t!!\u001d\t\u0013\u0005U\u0004!!A\u0005B\u0005]\u0004\"CA>\u0001\u0005\u0005I\u0011IA?\u0011%\ty\bAA\u0001\n\u0003\n\t\tC\u0005\u0002\u0004\u0002\t\t\u0011\"\u0011\u0002\u0006\u001e)Q\u0006\u0007E\u0001]\u0019)q\u0003\u0007E\u0001_!)\u0001h\u0004C\u0001s\u0019!!h\u0004\u0001<\u0011\u0015A\u0014\u0003\"\u0001K\u0011\u0015i\u0015\u0003\"\u0002O\u0011\u001d\u0019v\"!A\u0005\u0002RCqAV\b\u0002\u0002\u0013\u0005u\u000bC\u0004^\u001f\u0005\u0005I\u0011\u00020\u0003!Us'm\\;oI\u0016$W*Y5mE>D(BA\r\u001b\u0003!!\u0017n\u001d9bi\u000eD'\"A\u000e\u0002\t\u0005\\7.Y\u0002\u0001'\u0019\u0001a\u0004\n\u0015fQB\u0011qDI\u0007\u0002A)\t\u0011%A\u0003tG\u0006d\u0017-\u0003\u0002$A\t1\u0011I\\=SK\u001a\u0004\"!\n\u0014\u000e\u0003aI!a\n\r\u0003\u00175\u000b\u0017\u000e\u001c2pqRK\b/\u001a\t\u0004K%Z\u0013B\u0001\u0016\u0019\u0005Q\u0001&o\u001c3vG\u0016\u001cX*Z:tC\u001e,\u0017+^3vKB\u0011A&\u0005\b\u0003K9\t\u0001#\u00168c_VtG-\u001a3NC&d'm\u001c=\u0011\u0005\u0015z1cA\b\u001faA\u0011\u0011GN\u0007\u0002e)\u00111\u0007N\u0001\u0003S>T\u0011!N\u0001\u0005U\u00064\u0018-\u0003\u00028e\ta1+\u001a:jC2L'0\u00192mK\u00061A(\u001b8jiz\"\u0012A\f\u0002\r\u001b\u0016\u001c8/Y4f#V,W/Z\n\u0004#q:\u0005cA\u001fC\t6\taH\u0003\u0002@\u0001\u0006Q1m\u001c8dkJ\u0014XM\u001c;\u000b\u0005\u0005#\u0014\u0001B;uS2L!a\u0011 \u0003+\r{gnY;se\u0016tG\u000fT5oW\u0016$\u0017+^3vKB\u0011Q%R\u0005\u0003\rb\u0011\u0001\"\u00128wK2|\u0007/\u001a\t\u0003K!K!!\u0013\r\u0003?Us'm\\;oI\u0016$\u0017+^3vK\n\u000b7/\u001a3NKN\u001c\u0018mZ3Rk\u0016,X\rF\u0001L!\ta\u0015#D\u0001\u0010\u0003\u0015\tX/Z;f+\u0005y\u0005c\u0001)R\t6\t\u0001)\u0003\u0002S\u0001\n)\u0011+^3vK\u0006)\u0011\r\u001d9msR\tQ\u000b\u0005\u0002&\u0001\u00059QO\\1qa2LHC\u0001-\\!\ty\u0012,\u0003\u0002[A\t9!i\\8mK\u0006t\u0007b\u0002/\u0016\u0003\u0003\u0005\r!V\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,G#A0\u0011\u0005\u0001\u001cW\"A1\u000b\u0005\t$\u0014\u0001\u00027b]\u001eL!\u0001Z1\u0003\r=\u0013'.Z2u!\tyb-\u0003\u0002hA\t9\u0001K]8ek\u000e$\bCA5r\u001d\tQwN\u0004\u0002l]6\tAN\u0003\u0002n9\u00051AH]8pizJ\u0011!I\u0005\u0003a\u0002\nq\u0001]1dW\u0006<W-\u0003\u00028e*\u0011\u0001\u000f\t\u000b\u0005+R\f\t\u0001C\u0003v\u0005\u0001\u0007a/\u0001\u0005tKR$\u0018N\\4t!\t9XP\u0004\u0002yw6\t\u0011P\u0003\u0002{5\u0005)\u0011m\u0019;pe&\u0011A0_\u0001\f\u0003\u000e$xN]*zgR,W.\u0003\u0002\u007f\u007f\nA1+\u001a;uS:<7O\u0003\u0002}s\"9\u00111\u0001\u0002A\u0002\u0005\u0015\u0011AB2p]\u001aLw\r\u0005\u0003\u0002\b\u0005MQBAA\u0005\u0015\u0011\t\u0019!a\u0003\u000b\t\u00055\u0011qB\u0001\tif\u0004Xm]1gK*\u0011\u0011\u0011C\u0001\u0004G>l\u0017\u0002BA\u000b\u0003\u0013\u0011aaQ8oM&<\u0017AB2sK\u0006$X\r\u0006\u0004\u0002\u001c\u0005}\u0011q\u0006\t\u0004K\u0005u\u0011B\u0001\u001e\u0019\u0011\u001d\t\tc\u0001a\u0001\u0003G\tQa\\<oKJ\u0004RaHA\u0013\u0003SI1!a\n!\u0005\u0019y\u0005\u000f^5p]B\u0019\u00010a\u000b\n\u0007\u00055\u0012P\u0001\u0005BGR|'OU3g\u0011\u001d\t\td\u0001a\u0001\u0003g\taa]=ti\u0016l\u0007#B\u0010\u0002&\u0005U\u0002c\u0001=\u00028%\u0019\u0011\u0011H=\u0003\u0017\u0005\u001bGo\u001c:TsN$X-\\\u0001\u0005G>\u0004\u00180A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003\u0003\u00022\u0001YA\"\u0013\r\t)%\u0019\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\u0005-\u0003cA\u0010\u0002N%\u0019\u0011q\n\u0011\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005U\u00131\f\t\u0004?\u0005]\u0013bAA-A\t\u0019\u0011I\\=\t\u0013\u0005us!!AA\u0002\u0005-\u0013a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002dA1\u0011QMA6\u0003+j!!a\u001a\u000b\u0007\u0005%\u0004%\u0001\u0006d_2dWm\u0019;j_:LA!!\u001c\u0002h\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\rA\u00161\u000f\u0005\n\u0003;J\u0011\u0011!a\u0001\u0003+\n!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011\u0011IA=\u0011%\tiFCA\u0001\u0002\u0004\tY%\u0001\u0005iCND7i\u001c3f)\t\tY%\u0001\u0005u_N#(/\u001b8h)\t\t\t%\u0001\u0004fcV\fGn\u001d\u000b\u00041\u0006\u001d\u0005\"CA/\u001b\u0005\u0005\t\u0019AA+\u0001")
public final class UnboundedMailbox
implements MailboxType,
ProducesMessageQueue<MessageQueue>,
Product,
Serializable {
    public static boolean unapply(UnboundedMailbox x$0) {
        return UnboundedMailbox$.MODULE$.unapply(x$0);
    }

    public static UnboundedMailbox apply() {
        return UnboundedMailbox$.MODULE$.apply();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public akka.dispatch.MessageQueue create(Option<ActorRef> owner, Option<ActorSystem> system) {
        return new MessageQueue();
    }

    public UnboundedMailbox copy() {
        return new UnboundedMailbox();
    }

    @Override
    public String productPrefix() {
        return "UnboundedMailbox";
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
        return x$1 instanceof UnboundedMailbox;
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
        return object instanceof UnboundedMailbox;
    }

    public UnboundedMailbox() {
        Product.$init$(this);
    }

    public UnboundedMailbox(ActorSystem.Settings settings, Config config) {
        this();
    }

    public static class MessageQueue
    extends ConcurrentLinkedQueue<Envelope>
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

        public MessageQueue() {
            QueueBasedMessageQueue.$init$(this);
            UnboundedQueueBasedMessageQueue.$init$(this);
        }
    }
}

