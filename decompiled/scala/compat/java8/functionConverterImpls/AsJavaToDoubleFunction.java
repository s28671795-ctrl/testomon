/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToDoubleFunction;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005q2A\u0001B\u0003\u0001\u001d!AA\u0006\u0001B\u0001B\u0003%Q\u0006C\u00034\u0001\u0011\u0005A\u0007C\u00039\u0001\u0011\u0005\u0011H\u0001\fBg*\u000bg/\u0019+p\t>,(\r\\3Gk:\u001cG/[8o\u0015\t1q!\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\tA\u0011\"A\u0003kCZ\f\u0007H\u0003\u0002\u000b\u0017\u000511m\\7qCRT\u0011\u0001D\u0001\u0006g\u000e\fG.Y\u0002\u0001+\ty!eE\u0002\u0001!a\u0001\"!\u0005\f\u000e\u0003IQ!a\u0005\u000b\u0002\t1\fgn\u001a\u0006\u0002+\u0005!!.\u0019<b\u0013\t9\"C\u0001\u0004PE*,7\r\u001e\t\u00043y\u0001S\"\u0001\u000e\u000b\u0005ma\u0012\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005u!\u0012\u0001B;uS2L!a\b\u000e\u0003!Q{Gi\\;cY\u00164UO\\2uS>t\u0007CA\u0011#\u0019\u0001!Qa\t\u0001C\u0002\u0011\u0012\u0011\u0001V\t\u0003K%\u0002\"AJ\u0014\u000e\u0003-I!\u0001K\u0006\u0003\u000f9{G\u000f[5oOB\u0011aEK\u0005\u0003W-\u00111!\u00118z\u0003\t\u0019h\r\u0005\u0003']\u0001\u0002\u0014BA\u0018\f\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0002'c%\u0011!g\u0003\u0002\u0007\t>,(\r\\3\u0002\rqJg.\u001b;?)\t)t\u0007E\u00027\u0001\u0001j\u0011!\u0002\u0005\u0006Y\t\u0001\r!L\u0001\u000eCB\u0004H._!t\t>,(\r\\3\u0015\u0005AR\u0004\"B\u001e\u0004\u0001\u0004\u0001\u0013A\u0001=2\u0001")
public class AsJavaToDoubleFunction<T>
implements ToDoubleFunction<T> {
    private final Function1<T, Object> sf;

    @Override
    public double applyAsDouble(T x1) {
        return BoxesRunTime.unboxToDouble(this.sf.apply(x1));
    }

    public AsJavaToDoubleFunction(Function1<T, Object> sf) {
        this.sf = sf;
    }
}

