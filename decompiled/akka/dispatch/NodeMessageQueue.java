/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.dispatch.AbstractNodeQueue;
import akka.dispatch.Envelope;
import akka.dispatch.MessageQueue;
import akka.dispatch.UnboundedMessageQueueSemantics;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005%3Aa\u0002\u0005\u0001\u001b!)1\u0004\u0001C\u00019!)a\u0004\u0001C\u0003?!)\u0001\u0007\u0001C\u0003c!)!\u0007\u0001C\u0003g!)q\u0007\u0001C\u0003q!)A\b\u0001C\u0003{\t\u0001bj\u001c3f\u001b\u0016\u001c8/Y4f#V,W/\u001a\u0006\u0003\u0013)\t\u0001\u0002Z5ta\u0006$8\r\u001b\u0006\u0002\u0017\u0005!\u0011m[6b\u0007\u0001\u0019B\u0001\u0001\b\u00161A\u0019q\u0002\u0005\n\u000e\u0003!I!!\u0005\u0005\u0003#\u0005\u00137\u000f\u001e:bGRtu\u000eZ3Rk\u0016,X\r\u0005\u0002\u0010'%\u0011A\u0003\u0003\u0002\t\u000b:4X\r\\8qKB\u0011qBF\u0005\u0003/!\u0011A\"T3tg\u0006<W-U;fk\u0016\u0004\"aD\r\n\u0005iA!AH+oE>,h\u000eZ3e\u001b\u0016\u001c8/Y4f#V,W/Z*f[\u0006tG/[2t\u0003\u0019a\u0014N\\5u}Q\tQ\u0004\u0005\u0002\u0010\u0001\u00059QM\\9vKV,Gc\u0001\u0011']A\u0011\u0011\u0005J\u0007\u0002E)\t1%A\u0003tG\u0006d\u0017-\u0003\u0002&E\t!QK\\5u\u0011\u00159#\u00011\u0001)\u0003!\u0011XmY3jm\u0016\u0014\bCA\u0015-\u001b\u0005Q#BA\u0016\u000b\u0003\u0015\t7\r^8s\u0013\ti#F\u0001\u0005BGR|'OU3g\u0011\u0015y#\u00011\u0001\u0013\u0003\u0019A\u0017M\u001c3mK\u00069A-Z9vKV,G#\u0001\n\u0002!9,XNY3s\u001f\u001alUm]:bO\u0016\u001cX#\u0001\u001b\u0011\u0005\u0005*\u0014B\u0001\u001c#\u0005\rIe\u000e^\u0001\fQ\u0006\u001cX*Z:tC\u001e,7/F\u0001:!\t\t#(\u0003\u0002<E\t9!i\\8mK\u0006t\u0017aB2mK\u0006tW\u000b\u001d\u000b\u0004Ay\u0002\u0005\"B \u0007\u0001\u0004A\u0013!B8x]\u0016\u0014\b\"B!\u0007\u0001\u0004)\u0012a\u00033fC\u0012dU\r\u001e;feND#AB\"\u0011\u0005\u0011;U\"A#\u000b\u0005\u0019\u0013\u0013AC1o]>$\u0018\r^5p]&\u0011\u0001*\u0012\u0002\bi\u0006LGN]3d\u0001")
public class NodeMessageQueue
extends AbstractNodeQueue<Envelope>
implements MessageQueue,
UnboundedMessageQueueSemantics {
    @Override
    public final void enqueue(ActorRef receiver, Envelope handle2) {
        this.add(handle2);
    }

    @Override
    public final Envelope dequeue() {
        return (Envelope)this.poll();
    }

    @Override
    public final int numberOfMessages() {
        return this.count();
    }

    @Override
    public final boolean hasMessages() {
        return !this.isEmpty();
    }

    @Override
    public final void cleanUp(ActorRef owner, MessageQueue deadLetters) {
        Envelope envelope;
        while ((envelope = this.dequeue()) != null) {
            deadLetters.enqueue(owner, envelope);
        }
    }
}

