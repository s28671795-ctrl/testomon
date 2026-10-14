/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime.java8;

import java.io.Serializable;
import scala.Function0;
import scala.reflect.ScalaSignature;

@FunctionalInterface
@ScalaSignature(bytes="\u0006\u0005a2q\u0001B\u0003\u0011\u0002\u0007\u0005A\u0002C\u0003$\u0001\u0011\u0005A\u0005C\u0003)\u0001\u0019\u0005\u0011\u0006C\u0003.\u0001\u0011\u0005cFA\tK\rVt7\r^5p]B\"Sn\u0019#%gBT!AB\u0004\u0002\u000b)\fg/\u0019\u001d\u000b\u0005!I\u0011a\u0002:v]RLW.\u001a\u0006\u0002\u0015\u0005)1oY1mC\u000e\u00011\u0003\u0002\u0001\u000e#]\u0001\"AD\b\u000e\u0003%I!\u0001E\u0005\u0003\r\u0005s\u0017PU3g!\rq!\u0003F\u0005\u0003'%\u0011\u0011BR;oGRLwN\u001c\u0019\u0011\u00059)\u0012B\u0001\f\n\u0005\r\te.\u001f\t\u00031\u0001r!!\u0007\u0010\u000f\u0005iiR\"A\u000e\u000b\u0005qY\u0011A\u0002\u001fs_>$h(C\u0001\u000b\u0013\ty\u0012\"A\u0004qC\u000e\\\u0017mZ3\n\u0005\u0005\u0012#\u0001D*fe&\fG.\u001b>bE2,'BA\u0010\n\u0003\u0019!\u0013N\\5uIQ\tQ\u0005\u0005\u0002\u000fM%\u0011q%\u0003\u0002\u0005+:LG/\u0001\u0007baBd\u0017\u0010J7d\t\u0012\u001a\b\u000fF\u0001+!\tq1&\u0003\u0002-\u0013\t1Ai\\;cY\u0016\fQ!\u00199qYf$\u0012\u0001\u0006\u0015\u0003\u0001A\u0002\"!\r\u001c\u000e\u0003IR!a\r\u001b\u0002\t1\fgn\u001a\u0006\u0002k\u0005!!.\u0019<b\u0013\t9$GA\nGk:\u001cG/[8oC2Le\u000e^3sM\u0006\u001cW\r")
public interface JFunction0$mcD$sp
extends Function0<Object>,
Serializable {
    @Override
    public double apply$mcD$sp();

    public static /* synthetic */ Object apply$(JFunction0$mcD$sp $this) {
        return $this.apply();
    }

    @Override
    default public Object apply() {
        return this.apply$mcD$sp();
    }

    public static void $init$(JFunction0$mcD$sp $this) {
    }
}

