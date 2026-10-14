/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy$;
import akka.actor.SupervisorStrategyConfigurator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q1Aa\u0001\u0003\u0003\u0013!)A\u0003\u0001C\u0001+!)q\u0003\u0001C!1\tQ2\u000b^8qa&twmU;qKJ4\u0018n]8s'R\u0014\u0018\r^3hs*\u0011QAB\u0001\u0006C\u000e$xN\u001d\u0006\u0002\u000f\u0005!\u0011m[6b\u0007\u0001\u00192\u0001\u0001\u0006\u0011!\tYa\"D\u0001\r\u0015\u0005i\u0011!B:dC2\f\u0017BA\b\r\u0005\u0019\te.\u001f*fMB\u0011\u0011CE\u0007\u0002\t%\u00111\u0003\u0002\u0002\u001f'V\u0004XM\u001d<jg>\u00148\u000b\u001e:bi\u0016<\u0017pQ8oM&<WO]1u_J\fa\u0001P5oSRtD#\u0001\f\u0011\u0005E\u0001\u0011AB2sK\u0006$X\rF\u0001\u001a!\t\t\"$\u0003\u0002\u001c\t\t\u00112+\u001e9feZL7o\u001c:TiJ\fG/Z4z\u0001")
public final class StoppingSupervisorStrategy
implements SupervisorStrategyConfigurator {
    @Override
    public SupervisorStrategy create() {
        return SupervisorStrategy$.MODULE$.stoppingStrategy();
    }
}

