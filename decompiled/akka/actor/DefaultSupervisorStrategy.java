/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy$;
import akka.actor.SupervisorStrategyConfigurator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q1Aa\u0001\u0003\u0003\u0013!)A\u0003\u0001C\u0001+!)q\u0003\u0001C!1\tIB)\u001a4bk2$8+\u001e9feZL7o\u001c:TiJ\fG/Z4z\u0015\t)a!A\u0003bGR|'OC\u0001\b\u0003\u0011\t7n[1\u0004\u0001M\u0019\u0001A\u0003\t\u0011\u0005-qQ\"\u0001\u0007\u000b\u00035\tQa]2bY\u0006L!a\u0004\u0007\u0003\r\u0005s\u0017PU3g!\t\t\"#D\u0001\u0005\u0013\t\u0019BA\u0001\u0010TkB,'O^5t_J\u001cFO]1uK\u001eL8i\u001c8gS\u001e,(/\u0019;pe\u00061A(\u001b8jiz\"\u0012A\u0006\t\u0003#\u0001\taa\u0019:fCR,G#A\r\u0011\u0005EQ\u0012BA\u000e\u0005\u0005I\u0019V\u000f]3sm&\u001cxN]*ue\u0006$XmZ=")
public final class DefaultSupervisorStrategy
implements SupervisorStrategyConfigurator {
    @Override
    public SupervisorStrategy create() {
        return SupervisorStrategy$.MODULE$.defaultStrategy();
    }
}

