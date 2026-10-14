/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.dispatch.Envelope;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t2qAA\u0002\u0011\u0002G\u0005\u0001\u0002C\u0003\u0010\u0001\u0019\u0005\u0001CA\u0010EKF,XMQ1tK\u0012lUm]:bO\u0016\fV/Z;f'\u0016l\u0017M\u001c;jGNT!\u0001B\u0003\u0002\u0011\u0011L7\u000f]1uG\"T\u0011AB\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0005\u0001I\u0001C\u0001\u0006\u000e\u001b\u0005Y!\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\n\u00059Y!AB!osJ+g-\u0001\u0007f]F,X-^3GSJ\u001cH\u000fF\u0002\u0012)q\u0001\"A\u0003\n\n\u0005MY!\u0001B+oSRDQ!F\u0001A\u0002Y\t\u0001B]3dK&4XM\u001d\t\u0003/ii\u0011\u0001\u0007\u0006\u00033\u0015\tQ!Y2u_JL!a\u0007\r\u0003\u0011\u0005\u001bGo\u001c:SK\u001aDQ!H\u0001A\u0002y\ta\u0001[1oI2,\u0007CA\u0010!\u001b\u0005\u0019\u0011BA\u0011\u0004\u0005!)eN^3m_B,\u0007")
public interface DequeBasedMessageQueueSemantics {
    public void enqueueFirst(ActorRef var1, Envelope var2);
}

