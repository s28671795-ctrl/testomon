/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToIntBiFunction;
import scala.Function2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u00053A\u0001B\u0003\u0001\u001d!Aq\u0006\u0001B\u0001B\u0003%\u0001\u0007C\u00037\u0001\u0011\u0005q\u0007C\u0003<\u0001\u0011\u0005AHA\u000bBg*\u000bg/\u0019+p\u0013:$()\u001b$v]\u000e$\u0018n\u001c8\u000b\u0005\u00199\u0011A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005!I\u0011!\u00026bm\u0006D$B\u0001\u0006\f\u0003\u0019\u0019w.\u001c9bi*\tA\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0007=\u0011SfE\u0002\u0001!a\u0001\"!\u0005\f\u000e\u0003IQ!a\u0005\u000b\u0002\t1\fgn\u001a\u0006\u0002+\u0005!!.\u0019<b\u0013\t9\"C\u0001\u0004PE*,7\r\u001e\t\u00053y\u0001C&D\u0001\u001b\u0015\tYB$\u0001\u0005gk:\u001cG/[8o\u0015\tiB#\u0001\u0003vi&d\u0017BA\u0010\u001b\u0005=!v.\u00138u\u0005&4UO\\2uS>t\u0007CA\u0011#\u0019\u0001!Qa\t\u0001C\u0002\u0011\u0012\u0011\u0001V\t\u0003K%\u0002\"AJ\u0014\u000e\u0003-I!\u0001K\u0006\u0003\u000f9{G\u000f[5oOB\u0011aEK\u0005\u0003W-\u00111!\u00118z!\t\tS\u0006B\u0003/\u0001\t\u0007AEA\u0001V\u0003\t\u0019h\rE\u0003'c\u0001b3'\u0003\u00023\u0017\tIa)\u001e8di&|gN\r\t\u0003MQJ!!N\u0006\u0003\u0007%sG/\u0001\u0004=S:LGO\u0010\u000b\u0003qi\u0002B!\u000f\u0001!Y5\tQ\u0001C\u00030\u0005\u0001\u0007\u0001'\u0001\u0006baBd\u00170Q:J]R$2aM\u001f@\u0011\u0015q4\u00011\u0001!\u0003\tA\u0018\u0007C\u0003A\u0007\u0001\u0007A&\u0001\u0002ye\u0001")
public class AsJavaToIntBiFunction<T, U>
implements ToIntBiFunction<T, U> {
    private final Function2<T, U, Object> sf;

    @Override
    public int applyAsInt(T x1, U x2) {
        return BoxesRunTime.unboxToInt(this.sf.apply(x1, x2));
    }

    public AsJavaToIntBiFunction(Function2<T, U, Object> sf) {
        this.sf = sf;
    }
}

