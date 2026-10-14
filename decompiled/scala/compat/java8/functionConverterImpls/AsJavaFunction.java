/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Function;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q2A\u0001B\u0003\u0001\u001d!Aq\u0006\u0001B\u0001B\u0003%\u0001\u0007C\u00034\u0001\u0011\u0005A\u0007C\u00039\u0001\u0011\u0005\u0011H\u0001\bBg*\u000bg/\u0019$v]\u000e$\u0018n\u001c8\u000b\u0005\u00199\u0011A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005!I\u0011!\u00026bm\u0006D$B\u0001\u0006\f\u0003\u0019\u0019w.\u001c9bi*\tA\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0007=\u0011SfE\u0002\u0001!a\u0001\"!\u0005\f\u000e\u0003IQ!a\u0005\u000b\u0002\t1\fgn\u001a\u0006\u0002+\u0005!!.\u0019<b\u0013\t9\"C\u0001\u0004PE*,7\r\u001e\t\u00053y\u0001C&D\u0001\u001b\u0015\tYB$\u0001\u0005gk:\u001cG/[8o\u0015\tiB#\u0001\u0003vi&d\u0017BA\u0010\u001b\u0005!1UO\\2uS>t\u0007CA\u0011#\u0019\u0001!Qa\t\u0001C\u0002\u0011\u0012\u0011\u0001V\t\u0003K%\u0002\"AJ\u0014\u000e\u0003-I!\u0001K\u0006\u0003\u000f9{G\u000f[5oOB\u0011aEK\u0005\u0003W-\u00111!\u00118z!\t\tS\u0006B\u0003/\u0001\t\u0007AEA\u0001S\u0003\t\u0019h\r\u0005\u0003'c\u0001b\u0013B\u0001\u001a\f\u0005%1UO\\2uS>t\u0017'\u0001\u0004=S:LGO\u0010\u000b\u0003k]\u0002BA\u000e\u0001!Y5\tQ\u0001C\u00030\u0005\u0001\u0007\u0001'A\u0003baBd\u0017\u0010\u0006\u0002-u!)1h\u0001a\u0001A\u0005\u0011\u00010\r")
public class AsJavaFunction<T, R>
implements Function<T, R> {
    private final Function1<T, R> sf;

    @Override
    public <V> Function<V, R> compose(Function<? super V, ? extends T> x$1) {
        return Function.super.compose(x$1);
    }

    @Override
    public <V> Function<T, V> andThen(Function<? super R, ? extends V> x$1) {
        return Function.super.andThen(x$1);
    }

    @Override
    public R apply(T x1) {
        return this.sf.apply(x1);
    }

    public AsJavaFunction(Function1<T, R> sf) {
        this.sf = sf;
    }
}

