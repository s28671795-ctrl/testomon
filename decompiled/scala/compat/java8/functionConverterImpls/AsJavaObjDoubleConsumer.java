/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ObjDoubleConsumer;
import scala.Function2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u00053A\u0001B\u0003\u0001\u001d!AA\u0006\u0001B\u0001B\u0003%Q\u0006C\u00037\u0001\u0011\u0005q\u0007C\u0003<\u0001\u0011\u0005AHA\fBg*\u000bg/Y(cU\u0012{WO\u00197f\u0007>t7/^7fe*\u0011aaB\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0001\"C\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003\u0015-\taaY8na\u0006$(\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011qBI\n\u0004\u0001AA\u0002CA\t\u0017\u001b\u0005\u0011\"BA\n\u0015\u0003\u0011a\u0017M\\4\u000b\u0003U\tAA[1wC&\u0011qC\u0005\u0002\u0007\u001f\nTWm\u0019;\u0011\u0007eq\u0002%D\u0001\u001b\u0015\tYB$\u0001\u0005gk:\u001cG/[8o\u0015\tiB#\u0001\u0003vi&d\u0017BA\u0010\u001b\u0005Ey%M\u001b#pk\ndWmQ8ogVlWM\u001d\t\u0003C\tb\u0001\u0001B\u0003$\u0001\t\u0007AEA\u0001U#\t)\u0013\u0006\u0005\u0002'O5\t1\"\u0003\u0002)\u0017\t9aj\u001c;iS:<\u0007C\u0001\u0014+\u0013\tY3BA\u0002B]f\f!a\u001d4\u0011\u000b\u0019r\u0003\u0005M\u001a\n\u0005=Z!!\u0003$v]\u000e$\u0018n\u001c83!\t1\u0013'\u0003\u00023\u0017\t1Ai\\;cY\u0016\u0004\"A\n\u001b\n\u0005UZ!\u0001B+oSR\fa\u0001P5oSRtDC\u0001\u001d;!\rI\u0004\u0001I\u0007\u0002\u000b!)AF\u0001a\u0001[\u00051\u0011mY2faR$2aM\u001f@\u0011\u0015q4\u00011\u0001!\u0003\tA\u0018\u0007C\u0003A\u0007\u0001\u0007\u0001'\u0001\u0002ye\u0001")
public class AsJavaObjDoubleConsumer<T>
implements ObjDoubleConsumer<T> {
    private final Function2<T, Object, BoxedUnit> sf;

    @Override
    public void accept(T x1, double x2) {
        this.sf.apply(x1, BoxesRunTime.boxToDouble(x2));
    }

    public AsJavaObjDoubleConsumer(Function2<T, Object, BoxedUnit> sf) {
        this.sf = sf;
    }
}

