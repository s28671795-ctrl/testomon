/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.dispatch.ControlMessage;
import akka.dispatch.Envelope;
import akka.dispatch.QueueBasedMessageQueue;
import java.util.Queue;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00113q\u0001C\u0005\u0011\u0002\u0007\u0005a\u0002C\u0003\u001a\u0001\u0011\u0005!\u0004C\u0003\u001f\u0001\u0019\u0005q\u0004C\u0003,\u0001\u0019\u0005q\u0004C\u0003-\u0001\u0011\u0005Q\u0006C\u00039\u0001\u0011\u0005\u0011\bC\u0003;\u0001\u0011\u00053\bC\u0003@\u0001\u0011\u0005\u0003IA\u0011D_:$(o\u001c7Bo\u0006\u0014X-T3tg\u0006<W-U;fk\u0016\u001cV-\\1oi&\u001c7O\u0003\u0002\u000b\u0017\u0005AA-[:qCR\u001c\u0007NC\u0001\r\u0003\u0011\t7n[1\u0004\u0001M\u0019\u0001aD\u000b\u0011\u0005A\u0019R\"A\t\u000b\u0003I\tQa]2bY\u0006L!\u0001F\t\u0003\r\u0005s\u0017PU3g!\t1r#D\u0001\n\u0013\tA\u0012B\u0001\fRk\u0016,XMQ1tK\u0012lUm]:bO\u0016\fV/Z;f\u0003\u0019!\u0013N\\5uIQ\t1\u0004\u0005\u0002\u00119%\u0011Q$\u0005\u0002\u0005+:LG/\u0001\u0007d_:$(o\u001c7Rk\u0016,X-F\u0001!!\r\tc\u0005K\u0007\u0002E)\u00111\u0005J\u0001\u0005kRLGNC\u0001&\u0003\u0011Q\u0017M^1\n\u0005\u001d\u0012#!B)vKV,\u0007C\u0001\f*\u0013\tQ\u0013B\u0001\u0005F]Z,Gn\u001c9f\u0003\u0015\tX/Z;f\u0003\u001d)g.];fk\u0016$2a\u0007\u00187\u0011\u0015yC\u00011\u00011\u0003!\u0011XmY3jm\u0016\u0014\bCA\u00195\u001b\u0005\u0011$BA\u001a\f\u0003\u0015\t7\r^8s\u0013\t)$G\u0001\u0005BGR|'OU3g\u0011\u00159D\u00011\u0001)\u0003\u0019A\u0017M\u001c3mK\u00069A-Z9vKV,G#\u0001\u0015\u0002!9,XNY3s\u001f\u001alUm]:bO\u0016\u001cX#\u0001\u001f\u0011\u0005Ai\u0014B\u0001 \u0012\u0005\rIe\u000e^\u0001\fQ\u0006\u001cX*Z:tC\u001e,7/F\u0001B!\t\u0001\")\u0003\u0002D#\t9!i\\8mK\u0006t\u0007")
public interface ControlAwareMessageQueueSemantics
extends QueueBasedMessageQueue {
    public Queue<Envelope> controlQueue();

    @Override
    public Queue<Envelope> queue();

    public static /* synthetic */ void enqueue$(ControlAwareMessageQueueSemantics $this, ActorRef receiver, Envelope handle2) {
        $this.enqueue(receiver, handle2);
    }

    @Override
    default public void enqueue(ActorRef receiver, Envelope handle2) {
        Envelope envelope = handle2;
        if (envelope != null && envelope.message() instanceof ControlMessage) {
            this.controlQueue().add(envelope);
            return;
        }
        this.queue().add(envelope);
    }

    public static /* synthetic */ Envelope dequeue$(ControlAwareMessageQueueSemantics $this) {
        return $this.dequeue();
    }

    @Override
    default public Envelope dequeue() {
        Envelope controlMsg = this.controlQueue().poll();
        if (controlMsg != null) {
            return controlMsg;
        }
        return this.queue().poll();
    }

    public static /* synthetic */ int numberOfMessages$(ControlAwareMessageQueueSemantics $this) {
        return $this.numberOfMessages();
    }

    @Override
    default public int numberOfMessages() {
        return this.controlQueue().size() + this.queue().size();
    }

    public static /* synthetic */ boolean hasMessages$(ControlAwareMessageQueueSemantics $this) {
        return $this.hasMessages();
    }

    @Override
    default public boolean hasMessages() {
        return !this.queue().isEmpty() || !this.controlQueue().isEmpty();
    }

    public static void $init$(ControlAwareMessageQueueSemantics $this) {
    }
}

