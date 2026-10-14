/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.annotation.ApiMayChange;
import akka.util.WallClock$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)2qa\u0002\u0005\u0011\u0002G\u0005Q\u0002C\u0003\u0015\u0001\u0019\u0005QcB\u0003!\u0011!\u0005\u0011EB\u0003\b\u0011!\u00051\u0005C\u0003%\u0007\u0011\u0005Q\u0005C\u0004'\u0007\t\u0007I\u0011A\u0014\t\r%\u001a\u0001\u0015!\u0003)\u0005%9\u0016\r\u001c7DY>\u001c7N\u0003\u0002\n\u0015\u0005!Q\u000f^5m\u0015\u0005Y\u0011\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001\u001dA\u0011qBE\u0007\u0002!)\t\u0011#A\u0003tG\u0006d\u0017-\u0003\u0002\u0014!\t1\u0011I\\=SK\u001a\f\u0011cY;se\u0016tG\u000fV5nK6KG\u000e\\5t)\u00051\u0002CA\b\u0018\u0013\tA\u0002C\u0001\u0003M_:<\u0007F\u0001\u0001\u001b!\tYb$D\u0001\u001d\u0015\ti\"\"\u0001\u0006b]:|G/\u0019;j_:L!a\b\u000f\u0003\u0019\u0005\u0003\u0018.T1z\u0007\"\fgnZ3\u0002\u0013]\u000bG\u000e\\\"m_\u000e\\\u0007C\u0001\u0012\u0004\u001b\u0005A1CA\u0002\u000f\u0003\u0019a\u0014N\\5u}Q\t\u0011%A\u000bBY^\f\u0017p]%oGJ,\u0017m]5oO\u000ecwnY6\u0016\u0003!\u0002\"A\t\u0001\u0002-\u0005cw/Y=t\u0013:\u001c'/Z1tS:<7\t\\8dW\u0002\u0002")
@ApiMayChange
public interface WallClock {
    public static WallClock AlwaysIncreasingClock() {
        return WallClock$.MODULE$.AlwaysIncreasingClock();
    }

    public long currentTimeMillis();
}

