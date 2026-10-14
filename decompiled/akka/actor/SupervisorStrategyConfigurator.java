/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.SupervisorStrategy;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U1qAA\u0002\u0011\u0002G\u0005\u0001\u0002C\u0003\u0010\u0001\u0019\u0005\u0001C\u0001\u0010TkB,'O^5t_J\u001cFO]1uK\u001eL8i\u001c8gS\u001e,(/\u0019;pe*\u0011A!B\u0001\u0006C\u000e$xN\u001d\u0006\u0002\r\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001A\u0005\u0011\u0005)iQ\"A\u0006\u000b\u00031\tQa]2bY\u0006L!AD\u0006\u0003\r\u0005s\u0017PU3g\u0003\u0019\u0019'/Z1uKR\t\u0011\u0003\u0005\u0002\u0013'5\t1!\u0003\u0002\u0015\u0007\t\u00112+\u001e9feZL7o\u001c:TiJ\fG/Z4z\u0001")
public interface SupervisorStrategyConfigurator {
    public SupervisorStrategy create();
}

