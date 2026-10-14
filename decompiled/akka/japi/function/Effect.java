/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.function;

import java.io.Serializable;
import scala.reflect.ScalaSignature;

@FunctionalInterface
@ScalaSignature(bytes="\u0006\u0005q2qAA\u0002\u0011\u0002G\u0005!\u0002C\u0003\u001a\u0001\u0019\u0005!D\u0001\u0004FM\u001a,7\r\u001e\u0006\u0003\t\u0015\t\u0001BZ;oGRLwN\u001c\u0006\u0003\r\u001d\tAA[1qS*\t\u0001\"\u0001\u0003bW.\f7\u0001A\n\u0004\u0001-\t\u0002C\u0001\u0007\u0010\u001b\u0005i!\"\u0001\b\u0002\u000bM\u001c\u0017\r\\1\n\u0005Ai!AB!osJ+g\r\u0005\u0002\u0013/5\t1C\u0003\u0002\u0015+\u0005\u0011\u0011n\u001c\u0006\u0002-\u0005!!.\u0019<b\u0013\tA2C\u0001\u0007TKJL\u0017\r\\5{C\ndW-A\u0003baBd\u0017\u0010F\u0001\u001c!\taA$\u0003\u0002\u001e\u001b\t!QK\\5uQ\r\tqD\f\t\u0004\u0019\u0001\u0012\u0013BA\u0011\u000e\u0005\u0019!\bN]8xgB\u00111e\u000b\b\u0003I%r!!\n\u0015\u000e\u0003\u0019R!aJ\u0005\u0002\rq\u0012xn\u001c;?\u0013\u0005q\u0011B\u0001\u0016\u000e\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001L\u0017\u0003\u0013\u0015C8-\u001a9uS>t'B\u0001\u0016\u000eG\u0005\u0011\u0003F\u0001\u00011!\t\tD'D\u00013\u0015\t\u0019T#\u0001\u0003mC:<\u0017BA\u001b3\u0005M1UO\\2uS>t\u0017\r\\%oi\u0016\u0014h-Y2fQ\u0011\u0001qGO\u001e\u0011\u00051A\u0014BA\u001d\u000e\u0005A\u0019VM]5bYZ+'o]5p]VKE)A\u0003wC2,XMH\u0001\u0002\u0001")
public interface Effect
extends Serializable {
    public void apply() throws Exception;
}

