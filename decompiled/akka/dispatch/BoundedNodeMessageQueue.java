/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.actor.DeadLetter;
import akka.actor.InternalActorRef;
import akka.dispatch.AbstractBoundedNodeQueue;
import akka.dispatch.BoundedMessageQueueSemantics;
import akka.dispatch.Envelope;
import akka.dispatch.MessageQueue;
import akka.dispatch.MultipleConsumerSemantics;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.Duration$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i3A!\u0003\u0006\u0001\u001f!A\u0001\u0005\u0001B\u0001B\u0003%\u0011\u0005C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003,\u0001\u0011\u0015A\u0006C\u00036\u0001\u0011\u0015a\u0007C\u0003E\u0001\u0011\u0015Q\tC\u0003G\u0001\u0011\u0015q\tC\u0003I\u0001\u0011\u0015\u0011\nC\u0003N\u0001\u0011\u0015aJA\fC_VtG-\u001a3O_\u0012,W*Z:tC\u001e,\u0017+^3vK*\u00111\u0002D\u0001\tI&\u001c\b/\u0019;dQ*\tQ\"\u0001\u0003bW.\f7\u0001A\n\u0006\u0001A9\"$\b\t\u0004#I!R\"\u0001\u0006\n\u0005MQ!\u0001G!cgR\u0014\u0018m\u0019;C_VtG-\u001a3O_\u0012,\u0017+^3vKB\u0011\u0011#F\u0005\u0003-)\u0011\u0001\"\u00128wK2|\u0007/\u001a\t\u0003#aI!!\u0007\u0006\u0003\u00195+7o]1hKF+X-^3\u0011\u0005EY\u0012B\u0001\u000f\u000b\u0005q\u0011u.\u001e8eK\u0012lUm]:bO\u0016\fV/Z;f'\u0016l\u0017M\u001c;jGN\u0004\"!\u0005\u0010\n\u0005}Q!!G'vYRL\u0007\u000f\\3D_:\u001cX/\\3s'\u0016l\u0017M\u001c;jGN\f\u0001bY1qC\u000eLG/\u001f\t\u0003E\u0015j\u0011a\t\u0006\u0002I\u0005)1oY1mC&\u0011ae\t\u0002\u0004\u0013:$\u0018A\u0002\u001fj]&$h\b\u0006\u0002*UA\u0011\u0011\u0003\u0001\u0005\u0006A\t\u0001\r!I\u0001\faV\u001c\b\u000eV5nK>+H/F\u0001.!\tq3'D\u00010\u0015\t\u0001\u0014'\u0001\u0005ekJ\fG/[8o\u0015\t\u00114%\u0001\u0006d_:\u001cWO\u001d:f]RL!\u0001N\u0018\u0003\u0011\u0011+(/\u0019;j_:\fq!\u001a8rk\u0016,X\rF\u00028u\t\u0003\"A\t\u001d\n\u0005e\u001a#\u0001B+oSRDQa\u000f\u0003A\u0002q\n\u0001B]3dK&4XM\u001d\t\u0003{\u0001k\u0011A\u0010\u0006\u0003\u007f1\tQ!Y2u_JL!!\u0011 \u0003\u0011\u0005\u001bGo\u001c:SK\u001aDQa\u0011\u0003A\u0002Q\ta\u0001[1oI2,\u0017a\u00023fcV,W/\u001a\u000b\u0002)\u0005\u0001b.^7cKJ|e-T3tg\u0006<Wm]\u000b\u0002C\u0005Y\u0001.Y:NKN\u001c\u0018mZ3t+\u0005Q\u0005C\u0001\u0012L\u0013\ta5EA\u0004C_>dW-\u00198\u0002\u000f\rdW-\u00198VaR\u0019qgT)\t\u000bAC\u0001\u0019\u0001\u001f\u0002\u000b=<h.\u001a:\t\u000bIC\u0001\u0019A\f\u0002\u0017\u0011,\u0017\r\u001a'fiR,'o\u001d\u0015\u0003\u0011Q\u0003\"!\u0016-\u000e\u0003YS!aV\u0012\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002Z-\n9A/Y5me\u0016\u001c\u0007")
public class BoundedNodeMessageQueue
extends AbstractBoundedNodeQueue<Envelope>
implements MessageQueue,
BoundedMessageQueueSemantics,
MultipleConsumerSemantics {
    @Override
    public final Duration pushTimeOut() {
        return Duration$.MODULE$.Undefined();
    }

    @Override
    public final void enqueue(ActorRef receiver, Envelope handle2) {
        if (!this.add(handle2)) {
            ((InternalActorRef)receiver).provider().deadLetters().tell(new DeadLetter(handle2.message(), handle2.sender(), receiver), handle2.sender());
            return;
        }
    }

    @Override
    public final Envelope dequeue() {
        return (Envelope)this.poll();
    }

    @Override
    public final int numberOfMessages() {
        return this.size();
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

    public BoundedNodeMessageQueue(int capacity) {
        super(capacity);
    }
}

