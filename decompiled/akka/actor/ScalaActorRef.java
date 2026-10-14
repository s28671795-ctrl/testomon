/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor$;
import akka.actor.ActorRef;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00013\u0001b\u0001\u0003\u0011\u0002\u0007\u0005\u0011B\f\u0005\u0006!\u00011\t!\u0005\u0005\bC\u0001\t\n\u0011\"\u0001#\u00055\u00196-\u00197b\u0003\u000e$xN\u001d*fM*\u0011QAB\u0001\u0006C\u000e$xN\u001d\u0006\u0002\u000f\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001\u0001\u0006\u0011\u0005-qQ\"\u0001\u0007\u000b\u00035\tQa]2bY\u0006L!a\u0004\u0007\u0003\r\u0005s\u0017PU3g\u0003\u0015!#-\u00198h)\t\u0011B\u0004\u0006\u0002\u0014-A\u00111\u0002F\u0005\u0003+1\u0011A!\u00168ji\"9q#\u0001I\u0001\u0002\bA\u0012AB:f]\u0012,'\u000f\u0005\u0002\u001a55\tA!\u0003\u0002\u001c\t\tA\u0011i\u0019;peJ+g\rC\u0003\u001e\u0003\u0001\u0007a$A\u0004nKN\u001c\u0018mZ3\u0011\u0005-y\u0012B\u0001\u0011\r\u0005\r\te._\u0001\u0010I\t\fgn\u001a\u0013eK\u001a\fW\u000f\u001c;%eQ\u00111%\f\u0016\u00031\u0011Z\u0013!\n\t\u0003M-j\u0011a\n\u0006\u0003Q%\n\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0005)b\u0011AC1o]>$\u0018\r^5p]&\u0011Af\n\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0007\"B\u000f\u0003\u0001\u0004q\"\u0003B\u0018\u0019cQ2A\u0001\r\u0001\u0001]\taAH]3gS:,W.\u001a8u}A\u0011\u0011DM\u0005\u0003g\u0011\u0011\u0001#\u00138uKJt\u0017\r\\!di>\u0014(+\u001a4\u0011\u0005e)\u0014B\u0001\u001c\u0005\u00055\t5\r^8s%\u001647kY8qK\"2\u0001\u0001O\u000f<{y\u0002\"aC\u001d\n\u0005ib!A\u00033faJ,7-\u0019;fI\u0006\nA(A\u0017uK2d\u0007%\\3uQ>$\u0007%[:!]><\b\u0005\u001d:pm&$W\r\u001a\u0011cs\u0002\n5\r^8s%\u00164\u0007\u0005\u001e:bSR\fQa]5oG\u0016\f\u0013aP\u0001\u0007e92d&M\u001a")
public interface ScalaActorRef {
    public void $bang(Object var1, ActorRef var2);

    public static /* synthetic */ ActorRef $bang$default$2$(ScalaActorRef $this, Object message) {
        return $this.$bang$default$2(message);
    }

    default public ActorRef $bang$default$2(Object message) {
        return Actor$.MODULE$.noSender();
    }
}

