/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongPredicate;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M2A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003+\u0001\u0011\u00051\u0006C\u00030\u0001\u0011\u0005\u0001GA\nBg*\u000bg/\u0019'p]\u001e\u0004&/\u001a3jG\u0006$XM\u0003\u0002\u0007\u000f\u00051b-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7O\u0003\u0002\t\u0013\u0005)!.\u0019<bq)\u0011!bC\u0001\u0007G>l\u0007/\u0019;\u000b\u00031\tQa]2bY\u0006\u001c\u0001aE\u0002\u0001\u001f]\u0001\"\u0001E\u000b\u000e\u0003EQ!AE\n\u0002\t1\fgn\u001a\u0006\u0002)\u0005!!.\u0019<b\u0013\t1\u0012C\u0001\u0004PE*,7\r\u001e\t\u00031ui\u0011!\u0007\u0006\u00035m\t\u0001BZ;oGRLwN\u001c\u0006\u00039M\tA!\u001e;jY&\u0011a$\u0007\u0002\u000e\u0019>tw\r\u0015:fI&\u001c\u0017\r^3\u0002\u0005M4\u0007\u0003B\u0011#I\u001dj\u0011aC\u0005\u0003G-\u0011\u0011BR;oGRLwN\\\u0019\u0011\u0005\u0005*\u0013B\u0001\u0014\f\u0005\u0011auN\\4\u0011\u0005\u0005B\u0013BA\u0015\f\u0005\u001d\u0011un\u001c7fC:\fa\u0001P5oSRtDC\u0001\u0017/!\ti\u0003!D\u0001\u0006\u0011\u0015y\"\u00011\u0001!\u0003\u0011!Xm\u001d;\u0015\u0005\u001d\n\u0004\"\u0002\u001a\u0004\u0001\u0004!\u0013A\u0001=2\u0001")
public class AsJavaLongPredicate
implements LongPredicate {
    private final Function1<Object, Object> sf;

    @Override
    public LongPredicate and(LongPredicate x$1) {
        return LongPredicate.super.and(x$1);
    }

    @Override
    public LongPredicate negate() {
        return LongPredicate.super.negate();
    }

    @Override
    public LongPredicate or(LongPredicate x$1) {
        return LongPredicate.super.or(x$1);
    }

    @Override
    public boolean test(long x1) {
        return this.sf.apply$mcZJ$sp(x1);
    }

    public AsJavaLongPredicate(Function1<Object, Object> sf) {
        this.sf = sf;
    }
}

