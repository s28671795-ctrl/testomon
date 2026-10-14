/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.ControlAwareMessageQueueSemantics;
import akka.dispatch.Envelope;
import akka.dispatch.MailboxType;
import akka.dispatch.ProducesMessageQueue;
import akka.dispatch.QueueBasedMessageQueue;
import akka.dispatch.UnboundedControlAwareMailbox$;
import akka.dispatch.UnboundedControlAwareMessageQueueSemantics;
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

@ScalaSignature(bytes="\u0006\u0005\u0005%e\u0001\u0002\u000e\u001c\u0005\u0002BQa\u000f\u0001\u0005\u0002QCQa\u000f\u0001\u0005\u0002MDq!a\u0006\u0001\t\u0003\tI\u0002\u0003\u0005\u0002<\u0001\t\t\u0011\"\u0001U\u0011%\ti\u0004AA\u0001\n\u0003\ny\u0004C\u0005\u0002H\u0001\t\t\u0011\"\u0001\u0002J!I\u0011\u0011\u000b\u0001\u0002\u0002\u0013\u0005\u00111\u000b\u0005\n\u0003?\u0002\u0011\u0011!C!\u0003CB\u0011\"a\u001c\u0001\u0003\u0003%\t!!\u001d\t\u0013\u0005U\u0004!!A\u0005B\u0005]\u0004\"CA>\u0001\u0005\u0005I\u0011IA?\u0011%\ty\bAA\u0001\n\u0003\n\t\tC\u0005\u0002\u0004\u0002\t\t\u0011\"\u0011\u0002\u0006\u001e)\u0001g\u0007E\u0001c\u0019)!d\u0007E\u0001e!)1h\u0004C\u0001y\u0019!Qh\u0004\u0001?\u0011\u0015Y\u0014\u0003\"\u0001C\u0011\u001d)\u0015C1A\u0005\u0002\u0019Ca\u0001U\t!\u0002\u00139\u0005bB)\u0012\u0005\u0004%\tA\u0012\u0005\u0007%F\u0001\u000b\u0011B$\t\u000fM{\u0011\u0011!CA)\"9akDA\u0001\n\u0003;\u0006bB/\u0010\u0003\u0003%IA\u0018\u0002\u001d+:\u0014w.\u001e8eK\u0012\u001cuN\u001c;s_2\fu/\u0019:f\u001b\u0006LGNY8y\u0015\taR$\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005q\u0012\u0001B1lW\u0006\u001c\u0001a\u0005\u0004\u0001C\u001dZS\r\u001b\t\u0003E\u0015j\u0011a\t\u0006\u0002I\u0005)1oY1mC&\u0011ae\t\u0002\u0007\u0003:L(+\u001a4\u0011\u0005!JS\"A\u000e\n\u0005)Z\"aC'bS2\u0014w\u000e\u001f+za\u0016\u00042\u0001\u000b\u0017/\u0013\ti3D\u0001\u000bQe>$WoY3t\u001b\u0016\u001c8/Y4f#V,W/\u001a\t\u0003_Eq!\u0001\u000b\b\u00029Us'm\\;oI\u0016$7i\u001c8ue>d\u0017i^1sK6\u000b\u0017\u000e\u001c2pqB\u0011\u0001fD\n\u0004\u001f\u0005\u001a\u0004C\u0001\u001b:\u001b\u0005)$B\u0001\u001c8\u0003\tIwNC\u00019\u0003\u0011Q\u0017M^1\n\u0005i*$\u0001D*fe&\fG.\u001b>bE2,\u0017A\u0002\u001fj]&$h\bF\u00012\u00051iUm]:bO\u0016\fV/Z;f'\u0011\t\u0012eP\u001a\u0011\u0005!\u0002\u0015BA!\u001c\u0005)*fNY8v]\u0012,GmQ8oiJ|G.Q<be\u0016lUm]:bO\u0016\fV/Z;f'\u0016l\u0017M\u001c;jGN$\u0012a\u0011\t\u0003\tFi\u0011aD\u0001\rG>tGO]8m#V,W/Z\u000b\u0002\u000fB\u0019\u0001jS'\u000e\u0003%S!AS\u001c\u0002\tU$\u0018\u000e\\\u0005\u0003\u0019&\u0013Q!U;fk\u0016\u0004\"\u0001\u000b(\n\u0005=[\"\u0001C#om\u0016dw\u000e]3\u0002\u001b\r|g\u000e\u001e:pYF+X-^3!\u0003\u0015\tX/Z;f\u0003\u0019\tX/Z;fA\u0005)\u0011\r\u001d9msR\tQ\u000b\u0005\u0002)\u0001\u00059QO\\1qa2LHC\u0001-\\!\t\u0011\u0013,\u0003\u0002[G\t9!i\\8mK\u0006t\u0007b\u0002/\u0019\u0003\u0003\u0005\r!V\u0001\u0004q\u0012\u0002\u0014\u0001D<sSR,'+\u001a9mC\u000e,G#A0\u0011\u0005\u0001\u001cW\"A1\u000b\u0005\t<\u0014\u0001\u00027b]\u001eL!\u0001Z1\u0003\r=\u0013'.Z2u!\t\u0011c-\u0003\u0002hG\t9\u0001K]8ek\u000e$\bCA5r\u001d\tQwN\u0004\u0002l]6\tAN\u0003\u0002n?\u00051AH]8pizJ\u0011\u0001J\u0005\u0003a\u000e\nq\u0001]1dW\u0006<W-\u0003\u0002;e*\u0011\u0001o\t\u000b\u0005+R\f\t\u0001C\u0003v\u0005\u0001\u0007a/\u0001\u0005tKR$\u0018N\\4t!\t9XP\u0004\u0002yw6\t\u0011P\u0003\u0002{;\u0005)\u0011m\u0019;pe&\u0011A0_\u0001\f\u0003\u000e$xN]*zgR,W.\u0003\u0002\u007f\u007f\nA1+\u001a;uS:<7O\u0003\u0002}s\"9\u00111\u0001\u0002A\u0002\u0005\u0015\u0011AB2p]\u001aLw\r\u0005\u0003\u0002\b\u0005MQBAA\u0005\u0015\u0011\t\u0019!a\u0003\u000b\t\u00055\u0011qB\u0001\tif\u0004Xm]1gK*\u0011\u0011\u0011C\u0001\u0004G>l\u0017\u0002BA\u000b\u0003\u0013\u0011aaQ8oM&<\u0017AB2sK\u0006$X\r\u0006\u0004\u0002\u001c\u0005}\u0011q\u0006\t\u0004Q\u0005u\u0011BA\u001f\u001c\u0011\u001d\t\tc\u0001a\u0001\u0003G\tQa\\<oKJ\u0004RAIA\u0013\u0003SI1!a\n$\u0005\u0019y\u0005\u000f^5p]B\u0019\u00010a\u000b\n\u0007\u00055\u0012P\u0001\u0005BGR|'OU3g\u0011\u001d\t\td\u0001a\u0001\u0003g\taa]=ti\u0016l\u0007#\u0002\u0012\u0002&\u0005U\u0002c\u0001=\u00028%\u0019\u0011\u0011H=\u0003\u0017\u0005\u001bGo\u001c:TsN$X-\\\u0001\u0005G>\u0004\u00180A\u0007qe>$Wo\u0019;Qe\u00164\u0017\u000e_\u000b\u0003\u0003\u0003\u00022\u0001YA\"\u0013\r\t)%\u0019\u0002\u0007'R\u0014\u0018N\\4\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\u0005-\u0003c\u0001\u0012\u0002N%\u0019\u0011qJ\u0012\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005U\u00131\f\t\u0004E\u0005]\u0013bAA-G\t\u0019\u0011I\\=\t\u0013\u0005us!!AA\u0002\u0005-\u0013a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0002dA1\u0011QMA6\u0003+j!!a\u001a\u000b\u0007\u0005%4%\u0001\u0006d_2dWm\u0019;j_:LA!!\u001c\u0002h\tA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\rA\u00161\u000f\u0005\n\u0003;J\u0011\u0011!a\u0001\u0003+\n!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u0011\u0011IA=\u0011%\tiFCA\u0001\u0002\u0004\tY%\u0001\u0005iCND7i\u001c3f)\t\tY%\u0001\u0005u_N#(/\u001b8h)\t\t\t%\u0001\u0004fcV\fGn\u001d\u000b\u00041\u0006\u001d\u0005\"CA/\u001b\u0005\u0005\t\u0019AA+\u0001")
public final class UnboundedControlAwareMailbox
implements MailboxType,
ProducesMessageQueue<MessageQueue>,
Product,
Serializable {
    public static boolean unapply(UnboundedControlAwareMailbox x$0) {
        return UnboundedControlAwareMailbox$.MODULE$.unapply(x$0);
    }

    public static UnboundedControlAwareMailbox apply() {
        return UnboundedControlAwareMailbox$.MODULE$.apply();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public akka.dispatch.MessageQueue create(Option<ActorRef> owner, Option<ActorSystem> system) {
        return new MessageQueue();
    }

    public UnboundedControlAwareMailbox copy() {
        return new UnboundedControlAwareMailbox();
    }

    @Override
    public String productPrefix() {
        return "UnboundedControlAwareMailbox";
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
        return x$1 instanceof UnboundedControlAwareMailbox;
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
        return object instanceof UnboundedControlAwareMailbox;
    }

    public UnboundedControlAwareMailbox() {
        Product.$init$(this);
    }

    public UnboundedControlAwareMailbox(ActorSystem.Settings settings, Config config) {
        this();
    }

    public static class MessageQueue
    implements UnboundedControlAwareMessageQueueSemantics,
    Serializable {
        private final Queue<Envelope> controlQueue;
        private final Queue<Envelope> queue;

        @Override
        public void enqueue(ActorRef receiver, Envelope handle2) {
            ControlAwareMessageQueueSemantics.enqueue$(this, receiver, handle2);
        }

        @Override
        public Envelope dequeue() {
            return ControlAwareMessageQueueSemantics.dequeue$(this);
        }

        @Override
        public int numberOfMessages() {
            return ControlAwareMessageQueueSemantics.numberOfMessages$(this);
        }

        @Override
        public boolean hasMessages() {
            return ControlAwareMessageQueueSemantics.hasMessages$(this);
        }

        @Override
        public void cleanUp(ActorRef owner, akka.dispatch.MessageQueue deadLetters) {
            QueueBasedMessageQueue.cleanUp$(this, owner, deadLetters);
        }

        @Override
        public Queue<Envelope> controlQueue() {
            return this.controlQueue;
        }

        @Override
        public Queue<Envelope> queue() {
            return this.queue;
        }

        public MessageQueue() {
            QueueBasedMessageQueue.$init$(this);
            ControlAwareMessageQueueSemantics.$init$(this);
            this.controlQueue = new ConcurrentLinkedQueue<Envelope>();
            this.queue = new ConcurrentLinkedQueue<Envelope>();
        }
    }
}

