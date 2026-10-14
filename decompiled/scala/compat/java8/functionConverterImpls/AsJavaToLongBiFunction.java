/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToLongBiFunction;
import scala.Function2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u00053A\u0001B\u0003\u0001\u001d!Aq\u0006\u0001B\u0001B\u0003%\u0001\u0007C\u00037\u0001\u0011\u0005q\u0007C\u0003<\u0001\u0011\u0005AH\u0001\fBg*\u000bg/\u0019+p\u0019>twMQ5Gk:\u001cG/[8o\u0015\t1q!\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\tA\u0011\"A\u0003kCZ\f\u0007H\u0003\u0002\u000b\u0017\u000511m\\7qCRT\u0011\u0001D\u0001\u0006g\u000e\fG.Y\u0002\u0001+\ry!%L\n\u0004\u0001AA\u0002CA\t\u0017\u001b\u0005\u0011\"BA\n\u0015\u0003\u0011a\u0017M\\4\u000b\u0003U\tAA[1wC&\u0011qC\u0005\u0002\u0007\u001f\nTWm\u0019;\u0011\teq\u0002\u0005L\u0007\u00025)\u00111\u0004H\u0001\tMVt7\r^5p]*\u0011Q\u0004F\u0001\u0005kRLG.\u0003\u0002 5\t\u0001Bk\u001c'p]\u001e\u0014\u0015NR;oGRLwN\u001c\t\u0003C\tb\u0001\u0001B\u0003$\u0001\t\u0007AEA\u0001U#\t)\u0013\u0006\u0005\u0002'O5\t1\"\u0003\u0002)\u0017\t9aj\u001c;iS:<\u0007C\u0001\u0014+\u0013\tY3BA\u0002B]f\u0004\"!I\u0017\u0005\u000b9\u0002!\u0019\u0001\u0013\u0003\u0003U\u000b!a\u001d4\u0011\u000b\u0019\n\u0004\u0005L\u001a\n\u0005IZ!!\u0003$v]\u000e$\u0018n\u001c83!\t1C'\u0003\u00026\u0017\t!Aj\u001c8h\u0003\u0019a\u0014N\\5u}Q\u0011\u0001H\u000f\t\u0005s\u0001\u0001C&D\u0001\u0006\u0011\u0015y#\u00011\u00011\u0003-\t\u0007\u000f\u001d7z\u0003NduN\\4\u0015\u0007Mjt\bC\u0003?\u0007\u0001\u0007\u0001%\u0001\u0002yc!)\u0001i\u0001a\u0001Y\u0005\u0011\u0001P\r")
public class AsJavaToLongBiFunction<T, U>
implements ToLongBiFunction<T, U> {
    private final Function2<T, U, Object> sf;

    @Override
    public long applyAsLong(T x1, U x2) {
        return BoxesRunTime.unboxToLong(this.sf.apply(x1, x2));
    }

    public AsJavaToLongBiFunction(Function2<T, U, Object> sf) {
        this.sf = sf;
    }
}

