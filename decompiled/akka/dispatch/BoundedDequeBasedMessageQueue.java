/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.actor.DeadLetter;
import akka.actor.InternalActorRef;
import akka.dispatch.BoundedDequeBasedMessageQueueSemantics;
import akka.dispatch.DequeBasedMessageQueue;
import akka.dispatch.Envelope;
import java.util.concurrent.BlockingDeque;
import scala.concurrent.duration.Duration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)3qa\u0002\u0005\u0011\u0002\u0007\u0005Q\u0002C\u0003\u001c\u0001\u0011\u0005A\u0004C\u0003!\u0001\u0019\u0005\u0011\u0005C\u0003+\u0001\u0019\u00053\u0006C\u00039\u0001\u0011\u0005\u0011\bC\u0003E\u0001\u0011\u0005Q\tC\u0003I\u0001\u0011\u0005\u0011JA\u000fC_VtG-\u001a3EKF,XMQ1tK\u0012lUm]:bO\u0016\fV/Z;f\u0015\tI!\"\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u0005Y\u0011\u0001B1lW\u0006\u001c\u0001a\u0005\u0003\u0001\u001dQA\u0002CA\b\u0013\u001b\u0005\u0001\"\"A\t\u0002\u000bM\u001c\u0017\r\\1\n\u0005M\u0001\"AB!osJ+g\r\u0005\u0002\u0016-5\t\u0001\"\u0003\u0002\u0018\u0011\t1B)Z9vK\n\u000b7/\u001a3NKN\u001c\u0018mZ3Rk\u0016,X\r\u0005\u0002\u00163%\u0011!\u0004\u0003\u0002'\u0005>,h\u000eZ3e\t\u0016\fX/\u001a\"bg\u0016$W*Z:tC\u001e,\u0017+^3vKN+W.\u00198uS\u000e\u001c\u0018A\u0002\u0013j]&$H\u0005F\u0001\u001e!\tya$\u0003\u0002 !\t!QK\\5u\u0003-\u0001Xo\u001d5US6,w*\u001e;\u0016\u0003\t\u0002\"a\t\u0015\u000e\u0003\u0011R!!\n\u0014\u0002\u0011\u0011,(/\u0019;j_:T!a\n\t\u0002\u0015\r|gnY;se\u0016tG/\u0003\u0002*I\tAA)\u001e:bi&|g.A\u0003rk\u0016,X-F\u0001-!\ri3'N\u0007\u0002])\u0011qe\f\u0006\u0003aE\nA!\u001e;jY*\t!'\u0001\u0003kCZ\f\u0017B\u0001\u001b/\u00055\u0011En\\2lS:<G)Z9vKB\u0011QCN\u0005\u0003o!\u0011\u0001\"\u00128wK2|\u0007/Z\u0001\bK:\fX/Z;f)\ri\"H\u0011\u0005\u0006w\u0011\u0001\r\u0001P\u0001\te\u0016\u001cW-\u001b<feB\u0011Q\bQ\u0007\u0002})\u0011qHC\u0001\u0006C\u000e$xN]\u0005\u0003\u0003z\u0012\u0001\"Q2u_J\u0014VM\u001a\u0005\u0006\u0007\u0012\u0001\r!N\u0001\u0007Q\u0006tG\r\\3\u0002\u0019\u0015t\u0017/^3vK\u001aK'o\u001d;\u0015\u0007u1u\tC\u0003<\u000b\u0001\u0007A\bC\u0003D\u000b\u0001\u0007Q'A\u0004eKF,X-^3\u0015\u0003U\u0002")
public interface BoundedDequeBasedMessageQueue
extends DequeBasedMessageQueue,
BoundedDequeBasedMessageQueueSemantics {
    @Override
    public Duration pushTimeOut();

    public BlockingDeque<Envelope> queue();

    public static /* synthetic */ void enqueue$(BoundedDequeBasedMessageQueue $this, ActorRef receiver, Envelope handle2) {
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

    public static /* synthetic */ void enqueueFirst$(BoundedDequeBasedMessageQueue $this, ActorRef receiver, Envelope handle2) {
        $this.enqueueFirst(receiver, handle2);
    }

    @Override
    default public void enqueueFirst(ActorRef receiver, Envelope handle2) {
        if (this.pushTimeOut().length() >= 0L) {
            if (!this.queue().offerFirst(handle2, this.pushTimeOut().length(), this.pushTimeOut().unit())) {
                ((InternalActorRef)receiver).provider().deadLetters().tell(new DeadLetter(handle2.message(), handle2.sender(), receiver), handle2.sender());
                return;
            }
            return;
        }
        this.queue().putFirst(handle2);
    }

    public static /* synthetic */ Envelope dequeue$(BoundedDequeBasedMessageQueue $this) {
        return $this.dequeue();
    }

    @Override
    default public Envelope dequeue() {
        return this.queue().poll();
    }

    public static void $init$(BoundedDequeBasedMessageQueue $this) {
    }
}

