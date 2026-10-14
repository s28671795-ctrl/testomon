/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.dispatch.Envelope;
import akka.dispatch.MessageQueue;
import akka.dispatch.MultipleConsumerSemantics;
import java.util.Queue;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t3qAB\u0004\u0011\u0002\u0007\u0005A\u0002C\u0003\u001b\u0001\u0011\u00051\u0004C\u0003 \u0001\u0019\u0005\u0001\u0005C\u0003-\u0001\u0011\u0005Q\u0006C\u00032\u0001\u0011\u0005!\u0007C\u00037\u0001\u0011\u0005qG\u0001\fRk\u0016,XMQ1tK\u0012lUm]:bO\u0016\fV/Z;f\u0015\tA\u0011\"\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005Q\u0011\u0001B1lW\u0006\u001c\u0001a\u0005\u0003\u0001\u001bM9\u0002C\u0001\b\u0012\u001b\u0005y!\"\u0001\t\u0002\u000bM\u001c\u0017\r\\1\n\u0005Iy!AB!osJ+g\r\u0005\u0002\u0015+5\tq!\u0003\u0002\u0017\u000f\taQ*Z:tC\u001e,\u0017+^3vKB\u0011A\u0003G\u0005\u00033\u001d\u0011\u0011$T;mi&\u0004H.Z\"p]N,X.\u001a:TK6\fg\u000e^5dg\u00061A%\u001b8ji\u0012\"\u0012\u0001\b\t\u0003\u001duI!AH\b\u0003\tUs\u0017\u000e^\u0001\u0006cV,W/Z\u000b\u0002CA\u0019!eJ\u0015\u000e\u0003\rR!\u0001J\u0013\u0002\tU$\u0018\u000e\u001c\u0006\u0002M\u0005!!.\u0019<b\u0013\tA3EA\u0003Rk\u0016,X\r\u0005\u0002\u0015U%\u00111f\u0002\u0002\t\u000b:4X\r\\8qK\u0006\u0001b.^7cKJ|e-T3tg\u0006<Wm]\u000b\u0002]A\u0011abL\u0005\u0003a=\u00111!\u00138u\u0003-A\u0017m]'fgN\fw-Z:\u0016\u0003M\u0002\"A\u0004\u001b\n\u0005Uz!a\u0002\"p_2,\u0017M\\\u0001\bG2,\u0017M\\+q)\ra\u0002\b\u0011\u0005\u0006s\u0015\u0001\rAO\u0001\u0006_^tWM\u001d\t\u0003wyj\u0011\u0001\u0010\u0006\u0003{%\tQ!Y2u_JL!a\u0010\u001f\u0003\u0011\u0005\u001bGo\u001c:SK\u001aDQ!Q\u0003A\u0002M\t1\u0002Z3bI2+G\u000f^3sg\u0002")
public interface QueueBasedMessageQueue
extends MessageQueue,
MultipleConsumerSemantics {
    public Queue<Envelope> queue();

    public static /* synthetic */ int numberOfMessages$(QueueBasedMessageQueue $this) {
        return $this.numberOfMessages();
    }

    @Override
    default public int numberOfMessages() {
        return this.queue().size();
    }

    public static /* synthetic */ boolean hasMessages$(QueueBasedMessageQueue $this) {
        return $this.hasMessages();
    }

    @Override
    default public boolean hasMessages() {
        return !this.queue().isEmpty();
    }

    public static /* synthetic */ void cleanUp$(QueueBasedMessageQueue $this, ActorRef owner, MessageQueue deadLetters) {
        $this.cleanUp(owner, deadLetters);
    }

    @Override
    default public void cleanUp(ActorRef owner, MessageQueue deadLetters) {
        if (this.hasMessages()) {
            Envelope envelope = this.dequeue();
            while (envelope != null) {
                deadLetters.enqueue(owner, envelope);
                envelope = this.dequeue();
            }
            return;
        }
    }

    public static void $init$(QueueBasedMessageQueue $this) {
    }
}

