/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.dispatch.Envelope;
import akka.dispatch.QueueBasedMessageQueue;
import akka.dispatch.UnboundedMessageQueueSemantics;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000592q\u0001B\u0003\u0011\u0002\u0007\u0005!\u0002C\u0003\u0019\u0001\u0011\u0005\u0011\u0004C\u0003\u001e\u0001\u0011\u0005a\u0004C\u0003-\u0001\u0011\u0005QFA\u0010V]\n|WO\u001c3fIF+X-^3CCN,G-T3tg\u0006<W-U;fk\u0016T!AB\u0004\u0002\u0011\u0011L7\u000f]1uG\"T\u0011\u0001C\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\t\u0001Y\u0011#\u0006\t\u0003\u0019=i\u0011!\u0004\u0006\u0002\u001d\u0005)1oY1mC&\u0011\u0001#\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\u0005I\u0019R\"A\u0003\n\u0005Q)!AF)vKV,')Y:fI6+7o]1hKF+X-^3\u0011\u0005I1\u0012BA\f\u0006\u0005y)fNY8v]\u0012,G-T3tg\u0006<W-U;fk\u0016\u001cV-\\1oi&\u001c7/\u0001\u0004%S:LG\u000f\n\u000b\u00025A\u0011AbG\u0005\u000395\u0011A!\u00168ji\u00069QM\\9vKV,Gc\u0001\u000e O!)\u0001E\u0001a\u0001C\u0005A!/Z2fSZ,'\u000f\u0005\u0002#K5\t1E\u0003\u0002%\u000f\u0005)\u0011m\u0019;pe&\u0011ae\t\u0002\t\u0003\u000e$xN\u001d*fM\")\u0001F\u0001a\u0001S\u00051\u0001.\u00198eY\u0016\u0004\"A\u0005\u0016\n\u0005-*!\u0001C#om\u0016dw\u000e]3\u0002\u000f\u0011,\u0017/^3vKR\t\u0011\u0006")
public interface UnboundedQueueBasedMessageQueue
extends QueueBasedMessageQueue,
UnboundedMessageQueueSemantics {
    public static /* synthetic */ void enqueue$(UnboundedQueueBasedMessageQueue $this, ActorRef receiver, Envelope handle2) {
        $this.enqueue(receiver, handle2);
    }

    @Override
    default public void enqueue(ActorRef receiver, Envelope handle2) {
        this.queue().add(handle2);
    }

    public static /* synthetic */ Envelope dequeue$(UnboundedQueueBasedMessageQueue $this) {
        return $this.dequeue();
    }

    @Override
    default public Envelope dequeue() {
        return this.queue().poll();
    }

    public static void $init$(UnboundedQueueBasedMessageQueue $this) {
    }
}

