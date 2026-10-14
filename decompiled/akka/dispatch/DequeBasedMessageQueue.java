/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.dispatch.DequeBasedMessageQueueSemantics;
import akka.dispatch.Envelope;
import akka.dispatch.QueueBasedMessageQueue;
import java.util.Deque;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r2qAA\u0002\u0011\u0002G\u0005\u0001\u0002C\u0003\u0017\u0001\u0019\u0005qC\u0001\fEKF,XMQ1tK\u0012lUm]:bO\u0016\fV/Z;f\u0015\t!Q!\u0001\u0005eSN\u0004\u0018\r^2i\u0015\u00051\u0011\u0001B1lW\u0006\u001c\u0001a\u0005\u0003\u0001\u0013=\u0019\u0002C\u0001\u0006\u000e\u001b\u0005Y!\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\n\u00059Y!AB!osJ+g\r\u0005\u0002\u0011#5\t1!\u0003\u0002\u0013\u0007\t1\u0012+^3vK\n\u000b7/\u001a3NKN\u001c\u0018mZ3Rk\u0016,X\r\u0005\u0002\u0011)%\u0011Qc\u0001\u0002 \t\u0016\fX/\u001a\"bg\u0016$W*Z:tC\u001e,\u0017+^3vKN+W.\u00198uS\u000e\u001c\u0018!B9vKV,W#\u0001\r\u0011\u0007eq\u0002%D\u0001\u001b\u0015\tYB$\u0001\u0003vi&d'\"A\u000f\u0002\t)\fg/Y\u0005\u0003?i\u0011Q\u0001R3rk\u0016\u0004\"\u0001E\u0011\n\u0005\t\u001a!\u0001C#om\u0016dw\u000e]3")
public interface DequeBasedMessageQueue
extends QueueBasedMessageQueue,
DequeBasedMessageQueueSemantics {
    public Deque<Envelope> queue();
}

