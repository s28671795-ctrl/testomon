/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.UUIDComparator$;
import java.util.Comparator;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)3AAC\u0006\u0001!!)\u0011\u0005\u0001C\u0001E!)Q\u0005\u0001C\u0001M!)\u0011\u0007\u0001C\u0005e!)!\b\u0001C\u0005w\u001d)\u0001i\u0003E\u0001\u0003\u001a)!b\u0003E\u0001\u0005\")\u0011E\u0002C\u0001\r\"9qI\u0002b\u0001\n\u0003A\u0005BB%\u0007A\u0003%1E\u0001\bV+&#5i\\7qCJ\fGo\u001c:\u000b\u00051i\u0011\u0001B;uS2T\u0011AD\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0007\u0001\t\u0012\u0004\u0005\u0002\u0013/5\t1C\u0003\u0002\u0015+\u0005!A.\u00198h\u0015\u00051\u0012\u0001\u00026bm\u0006L!\u0001G\n\u0003\r=\u0013'.Z2u!\rQBDH\u0007\u00027)\u0011A\"F\u0005\u0003;m\u0011!bQ8na\u0006\u0014\u0018\r^8s!\tQr$\u0003\u0002!7\t!Q+V%E\u0003\u0019a\u0014N\\5u}Q\t1\u0005\u0005\u0002%\u00015\t1\"A\u0004d_6\u0004\u0018M]3\u0015\u0007\u001djs\u0006\u0005\u0002)W5\t\u0011FC\u0001+\u0003\u0015\u00198-\u00197b\u0013\ta\u0013FA\u0002J]RDQA\f\u0002A\u0002y\t!!^\u0019\t\u000bA\u0012\u0001\u0019\u0001\u0010\u0002\u0005U\u0014\u0014!D2p[B\f'/Z+M_:<7\u000fF\u0002(gaBQ\u0001N\u0002A\u0002U\n!\u0001\\\u0019\u0011\u0005!2\u0014BA\u001c*\u0005\u0011auN\\4\t\u000be\u001a\u0001\u0019A\u001b\u0002\u00051\u0014\u0014\u0001D2p[B\f'/Z+J]R\u001cHcA\u0014=}!)Q\b\u0002a\u0001O\u0005\u0011\u0011.\r\u0005\u0006\u007f\u0011\u0001\raJ\u0001\u0003SJ\na\"V+J\t\u000e{W\u000e]1sCR|'\u000f\u0005\u0002%\rM\u0011aa\u0011\t\u0003Q\u0011K!!R\u0015\u0003\r\u0005s\u0017PU3g)\u0005\t\u0015AC2p[B\f'/\u0019;peV\t1%A\u0006d_6\u0004\u0018M]1u_J\u0004\u0003")
public class UUIDComparator
implements Comparator<UUID> {
    public static UUIDComparator comparator() {
        return UUIDComparator$.MODULE$.comparator();
    }

    @Override
    public Comparator<UUID> reversed() {
        return Comparator.super.reversed();
    }

    @Override
    public Comparator<UUID> thenComparing(Comparator<? super UUID> x$1) {
        return Comparator.super.thenComparing(x$1);
    }

    @Override
    public <U> Comparator<UUID> thenComparing(Function<? super UUID, ? extends U> x$1, Comparator<? super U> x$2) {
        return Comparator.super.thenComparing(x$1, x$2);
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<UUID> thenComparing(Function<? super UUID, ? extends U> x$1) {
        return Comparator.super.thenComparing(x$1);
    }

    @Override
    public Comparator<UUID> thenComparingInt(ToIntFunction<? super UUID> x$1) {
        return Comparator.super.thenComparingInt(x$1);
    }

    @Override
    public Comparator<UUID> thenComparingLong(ToLongFunction<? super UUID> x$1) {
        return Comparator.super.thenComparingLong(x$1);
    }

    @Override
    public Comparator<UUID> thenComparingDouble(ToDoubleFunction<? super UUID> x$1) {
        return Comparator.super.thenComparingDouble(x$1);
    }

    @Override
    public int compare(UUID u1, UUID u2) {
        int version = u1.version();
        int diff = version - u2.version();
        if (diff != 0) {
            return diff;
        }
        if (version == 1) {
            int diff2 = this.compareULongs(u1.timestamp(), u2.timestamp());
            if (diff2 == 0) {
                return this.compareULongs(u1.getLeastSignificantBits(), u2.getLeastSignificantBits());
            }
            return diff2;
        }
        int diff2 = this.compareULongs(u1.getMostSignificantBits(), u2.getMostSignificantBits());
        if (diff2 == 0) {
            return this.compareULongs(u1.getLeastSignificantBits(), u2.getLeastSignificantBits());
        }
        return diff2;
    }

    private int compareULongs(long l1, long l2) {
        int diff = this.compareUInts((int)(l1 >> 32), (int)(l2 >> 32));
        if (diff == 0) {
            return this.compareUInts((int)l1, (int)l2);
        }
        return diff;
    }

    private int compareUInts(int i1, int i2) {
        if (i1 < 0) {
            if (i2 < 0) {
                return i1 - i2;
            }
            return 1;
        }
        if (i2 < 0) {
            return -1;
        }
        return i1 - i2;
    }
}

