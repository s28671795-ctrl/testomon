/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch.affinity;

import akka.dispatch.affinity.QueueSelector;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005]1qAA\u0002\u0011\u0002G\u0005!\u0002C\u0003\u0012\u0001\u0019\u0005!C\u0001\u000bRk\u0016,XmU3mK\u000e$xN\u001d$bGR|'/\u001f\u0006\u0003\t\u0015\t\u0001\"\u00194gS:LG/\u001f\u0006\u0003\r\u001d\t\u0001\u0002Z5ta\u0006$8\r\u001b\u0006\u0002\u0011\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001A\u0006\u0011\u00051yQ\"A\u0007\u000b\u00039\tQa]2bY\u0006L!\u0001E\u0007\u0003\r\u0005s\u0017PU3g\u0003\u0019\u0019'/Z1uKR\t1\u0003\u0005\u0002\u0015+5\t1!\u0003\u0002\u0017\u0007\ti\u0011+^3vKN+G.Z2u_J\u0004")
public interface QueueSelectorFactory {
    public QueueSelector create();
}

