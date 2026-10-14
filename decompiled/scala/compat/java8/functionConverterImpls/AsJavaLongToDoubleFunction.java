/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongToDoubleFunction;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M2A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003+\u0001\u0011\u00051\u0006C\u00030\u0001\u0011\u0005\u0001G\u0001\u000eBg*\u000bg/\u0019'p]\u001e$v\u000eR8vE2,g)\u001e8di&|gN\u0003\u0002\u0007\u000f\u00051b-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7O\u0003\u0002\t\u0013\u0005)!.\u0019<bq)\u0011!bC\u0001\u0007G>l\u0007/\u0019;\u000b\u00031\tQa]2bY\u0006\u001c\u0001aE\u0002\u0001\u001f]\u0001\"\u0001E\u000b\u000e\u0003EQ!AE\n\u0002\t1\fgn\u001a\u0006\u0002)\u0005!!.\u0019<b\u0013\t1\u0012C\u0001\u0004PE*,7\r\u001e\t\u00031ui\u0011!\u0007\u0006\u00035m\t\u0001BZ;oGRLwN\u001c\u0006\u00039M\tA!\u001e;jY&\u0011a$\u0007\u0002\u0015\u0019>tw\rV8E_V\u0014G.\u001a$v]\u000e$\u0018n\u001c8\u0002\u0005M4\u0007\u0003B\u0011#I\u001dj\u0011aC\u0005\u0003G-\u0011\u0011BR;oGRLwN\\\u0019\u0011\u0005\u0005*\u0013B\u0001\u0014\f\u0005\u0011auN\\4\u0011\u0005\u0005B\u0013BA\u0015\f\u0005\u0019!u.\u001e2mK\u00061A(\u001b8jiz\"\"\u0001\f\u0018\u0011\u00055\u0002Q\"A\u0003\t\u000b}\u0011\u0001\u0019\u0001\u0011\u0002\u001b\u0005\u0004\b\u000f\\=Bg\u0012{WO\u00197f)\t9\u0013\u0007C\u00033\u0007\u0001\u0007A%\u0001\u0002yc\u0001")
public class AsJavaLongToDoubleFunction
implements LongToDoubleFunction {
    private final Function1<Object, Object> sf;

    @Override
    public double applyAsDouble(long x1) {
        return this.sf.apply$mcDJ$sp(x1);
    }

    public AsJavaLongToDoubleFunction(Function1<Object, Object> sf) {
        this.sf = sf;
    }
}

