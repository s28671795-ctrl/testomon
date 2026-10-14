/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.UnrestrictedStash;
import akka.dispatch.DequeBasedMessageQueueSemantics;
import akka.dispatch.RequiresMessageQueue;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m1q!\u0001\u0002\u0011\u0002G\u0005qAA\u0003Ti\u0006\u001c\bN\u0003\u0002\u0004\t\u0005)\u0011m\u0019;pe*\tQ!\u0001\u0003bW.\f7\u0001A\n\u0005\u0001!q!\u0003\u0005\u0002\n\u00195\t!BC\u0001\f\u0003\u0015\u00198-\u00197b\u0013\ti!B\u0001\u0004B]f\u0014VM\u001a\t\u0003\u001fAi\u0011AA\u0005\u0003#\t\u0011\u0011#\u00168sKN$(/[2uK\u0012\u001cF/Y:i!\r\u0019b\u0003G\u0007\u0002))\u0011Q\u0003B\u0001\tI&\u001c\b/\u0019;dQ&\u0011q\u0003\u0006\u0002\u0015%\u0016\fX/\u001b:fg6+7o]1hKF+X-^3\u0011\u0005MI\u0012B\u0001\u000e\u0015\u0005}!U-];f\u0005\u0006\u001cX\rZ'fgN\fw-Z)vKV,7+Z7b]RL7m\u001d")
public interface Stash
extends UnrestrictedStash,
RequiresMessageQueue<DequeBasedMessageQueueSemantics> {
}

