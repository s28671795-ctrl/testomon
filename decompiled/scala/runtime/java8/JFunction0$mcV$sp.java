/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime.java8;

import java.io.Serializable;
import scala.Function0;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@FunctionalInterface
@ScalaSignature(bytes="\u0006\u0005Q2q\u0001B\u0003\u0011\u0002\u0007\u0005A\u0002C\u0003$\u0001\u0011\u0005A\u0005C\u0003)\u0001\u0019\u0005A\u0005C\u0003*\u0001\u0011\u0005#FA\tK\rVt7\r^5p]B\"Sn\u0019,%gBT!AB\u0004\u0002\u000b)\fg/\u0019\u001d\u000b\u0005!I\u0011a\u0002:v]RLW.\u001a\u0006\u0002\u0015\u0005)1oY1mC\u000e\u00011\u0003\u0002\u0001\u000e#]\u0001\"AD\b\u000e\u0003%I!\u0001E\u0005\u0003\r\u0005s\u0017PU3g!\rq!\u0003F\u0005\u0003'%\u0011\u0011BR;oGRLwN\u001c\u0019\u0011\u00059)\u0012B\u0001\f\n\u0005\r\te.\u001f\t\u00031\u0001r!!\u0007\u0010\u000f\u0005iiR\"A\u000e\u000b\u0005qY\u0011A\u0002\u001fs_>$h(C\u0001\u000b\u0013\ty\u0012\"A\u0004qC\u000e\\\u0017mZ3\n\u0005\u0005\u0012#\u0001D*fe&\fG.\u001b>bE2,'BA\u0010\n\u0003\u0019!\u0013N\\5uIQ\tQ\u0005\u0005\u0002\u000fM%\u0011q%\u0003\u0002\u0005+:LG/\u0001\u0007baBd\u0017\u0010J7d-\u0012\u001a\b/A\u0003baBd\u0017\u0010F\u0001\u0015Q\t\u0001A\u0006\u0005\u0002.e5\taF\u0003\u00020a\u0005!A.\u00198h\u0015\u0005\t\u0014\u0001\u00026bm\u0006L!a\r\u0018\u0003'\u0019+hn\u0019;j_:\fG.\u00138uKJ4\u0017mY3")
public interface JFunction0$mcV$sp
extends Function0<Object>,
Serializable {
    @Override
    public void apply$mcV$sp();

    public static /* synthetic */ Object apply$(JFunction0$mcV$sp $this) {
        return $this.apply();
    }

    @Override
    default public Object apply() {
        this.apply$mcV$sp();
        return BoxedUnit.UNIT;
    }

    public static void $init$(JFunction0$mcV$sp $this) {
    }
}

