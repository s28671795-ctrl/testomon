/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.actor.DeadLetter;
import akka.actor.InternalActorRef;
import akka.dispatch.BoundedMessageQueueSemantics;
import akka.dispatch.Envelope;
import akka.dispatch.QueueBasedMessageQueue;
import java.util.concurrent.BlockingQueue;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m2q!\u0002\u0004\u0011\u0002\u0007\u00051\u0002C\u0003\u001a\u0001\u0011\u0005!\u0004C\u0003\u001f\u0001\u0019\u0005s\u0004C\u0003.\u0001\u0011\u0005a\u0006C\u0003:\u0001\u0011\u0005!HA\u000fC_VtG-\u001a3Rk\u0016,XMQ1tK\u0012lUm]:bO\u0016\fV/Z;f\u0015\t9\u0001\"\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005I\u0011\u0001B1lW\u0006\u001c\u0001a\u0005\u0003\u0001\u0019I1\u0002CA\u0007\u0011\u001b\u0005q!\"A\b\u0002\u000bM\u001c\u0017\r\\1\n\u0005Eq!AB!osJ+g\r\u0005\u0002\u0014)5\ta!\u0003\u0002\u0016\r\t1\u0012+^3vK\n\u000b7/\u001a3NKN\u001c\u0018mZ3Rk\u0016,X\r\u0005\u0002\u0014/%\u0011\u0001D\u0002\u0002\u001d\u0005>,h\u000eZ3e\u001b\u0016\u001c8/Y4f#V,W/Z*f[\u0006tG/[2t\u0003\u0019!\u0013N\\5uIQ\t1\u0004\u0005\u0002\u000e9%\u0011QD\u0004\u0002\u0005+:LG/A\u0003rk\u0016,X-F\u0001!!\r\t\u0003FK\u0007\u0002E)\u00111\u0005J\u0001\u000bG>t7-\u001e:sK:$(BA\u0013'\u0003\u0011)H/\u001b7\u000b\u0003\u001d\nAA[1wC&\u0011\u0011F\t\u0002\u000e\u00052|7m[5oOF+X-^3\u0011\u0005MY\u0013B\u0001\u0017\u0007\u0005!)eN^3m_B,\u0017aB3ocV,W/\u001a\u000b\u00047=:\u0004\"\u0002\u0019\u0004\u0001\u0004\t\u0014\u0001\u0003:fG\u0016Lg/\u001a:\u0011\u0005I*T\"A\u001a\u000b\u0005QB\u0011!B1di>\u0014\u0018B\u0001\u001c4\u0005!\t5\r^8s%\u00164\u0007\"\u0002\u001d\u0004\u0001\u0004Q\u0013A\u00025b]\u0012dW-A\u0004eKF,X-^3\u0015\u0003)\u0002")
public interface BoundedQueueBasedMessageQueue
extends QueueBasedMessageQueue,
BoundedMessageQueueSemantics {
    public BlockingQueue<Envelope> queue();

    public static /* synthetic */ void enqueue$(BoundedQueueBasedMessageQueue $this, ActorRef receiver, Envelope handle2) {
        $this.enqueue(receiver, handle2);
    }

    @Override
    default public void enqueue(ActorRef receiver, Envelope handle2) {
        if (this.pushTimeOut().length() >= 0L) {
            if (!this.queue().offer(handle2, this.pushTimeOut().length(), this.pushTimeOut().unit())) {
                ((InternalActorRef)receiver).provider().deadLetters().tell(new DeadLetter(handle2.message(), handle2.sender(), receiver), handle2.sender());
                return;
            }
            return;
        }
        this.queue().put(handle2);
    }

    public static /* synthetic */ Envelope dequeue$(BoundedQueueBasedMessageQueue $this) {
        return $this.dequeue();
    }

    @Override
    default public Envelope dequeue() {
        return (Envelope)this.queue().poll();
    }

    public static void $init$(BoundedQueueBasedMessageQueue $this) {
    }
}

