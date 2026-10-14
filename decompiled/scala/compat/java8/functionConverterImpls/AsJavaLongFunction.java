/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongFunction;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005q2A\u0001B\u0003\u0001\u001d!AA\u0006\u0001B\u0001B\u0003%Q\u0006C\u00034\u0001\u0011\u0005A\u0007C\u00039\u0001\u0011\u0005\u0011H\u0001\nBg*\u000bg/\u0019'p]\u001e4UO\\2uS>t'B\u0001\u0004\b\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(B\u0001\u0005\n\u0003\u0015Q\u0017M^19\u0015\tQ1\"\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002\u0019\u0005)1oY1mC\u000e\u0001QCA\b#'\r\u0001\u0001\u0003\u0007\t\u0003#Yi\u0011A\u0005\u0006\u0003'Q\tA\u0001\\1oO*\tQ#\u0001\u0003kCZ\f\u0017BA\f\u0013\u0005\u0019y%M[3diB\u0019\u0011D\b\u0011\u000e\u0003iQ!a\u0007\u000f\u0002\u0011\u0019,hn\u0019;j_:T!!\b\u000b\u0002\tU$\u0018\u000e\\\u0005\u0003?i\u0011A\u0002T8oO\u001a+hn\u0019;j_:\u0004\"!\t\u0012\r\u0001\u0011)1\u0005\u0001b\u0001I\t\t!+\u0005\u0002&SA\u0011aeJ\u0007\u0002\u0017%\u0011\u0001f\u0003\u0002\b\u001d>$\b.\u001b8h!\t1#&\u0003\u0002,\u0017\t\u0019\u0011I\\=\u0002\u0005M4\u0007\u0003\u0002\u0014/a\u0001J!aL\u0006\u0003\u0013\u0019+hn\u0019;j_:\f\u0004C\u0001\u00142\u0013\t\u00114B\u0001\u0003M_:<\u0017A\u0002\u001fj]&$h\b\u0006\u00026oA\u0019a\u0007\u0001\u0011\u000e\u0003\u0015AQ\u0001\f\u0002A\u00025\nQ!\u00199qYf$\"\u0001\t\u001e\t\u000bm\u001a\u0001\u0019\u0001\u0019\u0002\u0005a\f\u0004")
public class AsJavaLongFunction<R>
implements LongFunction<R> {
    private final Function1<Object, R> sf;

    @Override
    public R apply(long x1) {
        return this.sf.apply(BoxesRunTime.boxToLong(x1));
    }

    public AsJavaLongFunction(Function1<Object, R> sf) {
        this.sf = sf;
    }
}

