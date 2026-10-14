/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.UnrestrictedStash;
import akka.dispatch.RequiresMessageQueue;
import akka.dispatch.UnboundedDequeBasedMessageQueueSemantics;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m1q!\u0001\u0002\u0011\u0002G\u0005qA\u0001\bV]\n|WO\u001c3fIN#\u0018m\u001d5\u000b\u0005\r!\u0011!B1di>\u0014(\"A\u0003\u0002\t\u0005\\7.Y\u0002\u0001'\u0011\u0001\u0001B\u0004\n\u0011\u0005%aQ\"\u0001\u0006\u000b\u0003-\tQa]2bY\u0006L!!\u0004\u0006\u0003\r\u0005s\u0017PU3g!\ty\u0001#D\u0001\u0003\u0013\t\t\"AA\tV]J,7\u000f\u001e:jGR,Gm\u0015;bg\"\u00042a\u0005\f\u0019\u001b\u0005!\"BA\u000b\u0005\u0003!!\u0017n\u001d9bi\u000eD\u0017BA\f\u0015\u0005Q\u0011V-];je\u0016\u001cX*Z:tC\u001e,\u0017+^3vKB\u00111#G\u0005\u00035Q\u0011\u0001&\u00168c_VtG-\u001a3EKF,XMQ1tK\u0012lUm]:bO\u0016\fV/Z;f'\u0016l\u0017M\u001c;jGN\u0004")
public interface UnboundedStash
extends UnrestrictedStash,
RequiresMessageQueue<UnboundedDequeBasedMessageQueueSemantics> {
}

