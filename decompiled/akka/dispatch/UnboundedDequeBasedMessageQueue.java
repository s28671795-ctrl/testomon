/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.dispatch.DequeBasedMessageQueue;
import akka.dispatch.Envelope;
import akka.dispatch.UnboundedDequeBasedMessageQueueSemantics;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M2q!\u0002\u0004\u0011\u0002\u0007\u00051\u0002C\u0003\u001a\u0001\u0011\u0005!\u0004C\u0003\u001f\u0001\u0011\u0005q\u0004C\u0003.\u0001\u0011\u0005a\u0006C\u00032\u0001\u0011\u0005!GA\u0010V]\n|WO\u001c3fI\u0012+\u0017/^3CCN,G-T3tg\u0006<W-U;fk\u0016T!a\u0002\u0005\u0002\u0011\u0011L7\u000f]1uG\"T\u0011!C\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\t\u0001a!C\u0006\t\u0003\u001bAi\u0011A\u0004\u0006\u0002\u001f\u0005)1oY1mC&\u0011\u0011C\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\u0005M!R\"\u0001\u0004\n\u0005U1!A\u0006#fcV,')Y:fI6+7o]1hKF+X-^3\u0011\u0005M9\u0012B\u0001\r\u0007\u0005!*fNY8v]\u0012,G\rR3rk\u0016\u0014\u0015m]3e\u001b\u0016\u001c8/Y4f#V,W/Z*f[\u0006tG/[2t\u0003\u0019!\u0013N\\5uIQ\t1\u0004\u0005\u0002\u000e9%\u0011QD\u0004\u0002\u0005+:LG/A\u0004f]F,X-^3\u0015\u0007m\u0001\u0003\u0006C\u0003\"\u0005\u0001\u0007!%\u0001\u0005sK\u000e,\u0017N^3s!\t\u0019c%D\u0001%\u0015\t)\u0003\"A\u0003bGR|'/\u0003\u0002(I\tA\u0011i\u0019;peJ+g\rC\u0003*\u0005\u0001\u0007!&\u0001\u0004iC:$G.\u001a\t\u0003'-J!\u0001\f\u0004\u0003\u0011\u0015sg/\u001a7pa\u0016\fA\"\u001a8rk\u0016,XMR5sgR$2aG\u00181\u0011\u0015\t3\u00011\u0001#\u0011\u0015I3\u00011\u0001+\u0003\u001d!W-];fk\u0016$\u0012A\u000b")
public interface UnboundedDequeBasedMessageQueue
extends DequeBasedMessageQueue,
UnboundedDequeBasedMessageQueueSemantics {
    public static /* synthetic */ void enqueue$(UnboundedDequeBasedMessageQueue $this, ActorRef receiver, Envelope handle2) {
        $this.enqueue(receiver, handle2);
    }

    @Override
    default public void enqueue(ActorRef receiver, Envelope handle2) {
        this.queue().add(handle2);
    }

    public static /* synthetic */ void enqueueFirst$(UnboundedDequeBasedMessageQueue $this, ActorRef receiver, Envelope handle2) {
        $this.enqueueFirst(receiver, handle2);
    }

    @Override
    default public void enqueueFirst(ActorRef receiver, Envelope handle2) {
        this.queue().addFirst(handle2);
    }

    public static /* synthetic */ Envelope dequeue$(UnboundedDequeBasedMessageQueue $this) {
        return $this.dequeue();
    }

    @Override
    default public Envelope dequeue() {
        return this.queue().poll();
    }

    public static void $init$(UnboundedDequeBasedMessageQueue $this) {
    }
}

