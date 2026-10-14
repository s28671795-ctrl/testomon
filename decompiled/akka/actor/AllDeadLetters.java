/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.WrappedMessage;
import akka.annotation.DoNotInherit;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u001d2q\u0001B\u0003\u0011\u0002G\u0005!\u0002C\u0003\u0016\u0001\u0019\u0005a\u0003C\u0003\u001b\u0001\u0019\u00051\u0004C\u0003 \u0001\u0019\u00051D\u0001\bBY2$U-\u00193MKR$XM]:\u000b\u0005\u00199\u0011!B1di>\u0014(\"\u0001\u0005\u0002\t\u0005\\7.Y\u0002\u0001'\r\u00011\"\u0005\t\u0003\u0019=i\u0011!\u0004\u0006\u0002\u001d\u0005)1oY1mC&\u0011\u0001#\u0004\u0002\u0007\u0003:L(+\u001a4\u0011\u0005I\u0019R\"A\u0003\n\u0005Q)!AD,sCB\u0004X\rZ'fgN\fw-Z\u0001\b[\u0016\u001c8/Y4f+\u00059\u0002C\u0001\u0007\u0019\u0013\tIRBA\u0002B]f\faa]3oI\u0016\u0014X#\u0001\u000f\u0011\u0005Ii\u0012B\u0001\u0010\u0006\u0005!\t5\r^8s%\u00164\u0017!\u0003:fG&\u0004\u0018.\u001a8uQ\t\u0001\u0011\u0005\u0005\u0002#K5\t1E\u0003\u0002%\u000f\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005\u0019\u001a#\u0001\u0004#p\u001d>$\u0018J\u001c5fe&$\b")
@DoNotInherit
public interface AllDeadLetters
extends WrappedMessage {
    @Override
    public Object message();

    public ActorRef sender();

    public ActorRef recipient();
}

