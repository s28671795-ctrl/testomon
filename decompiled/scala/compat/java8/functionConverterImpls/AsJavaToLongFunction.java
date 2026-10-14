/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToLongFunction;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005q2A\u0001B\u0003\u0001\u001d!AA\u0006\u0001B\u0001B\u0003%Q\u0006C\u00034\u0001\u0011\u0005A\u0007C\u00039\u0001\u0011\u0005\u0011H\u0001\u000bBg*\u000bg/\u0019+p\u0019>twMR;oGRLwN\u001c\u0006\u0003\r\u001d\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003\u0011%\tQA[1wCbR!AC\u0006\u0002\r\r|W\u000e]1u\u0015\u0005a\u0011!B:dC2\f7\u0001A\u000b\u0003\u001f\t\u001a2\u0001\u0001\t\u0019!\t\tb#D\u0001\u0013\u0015\t\u0019B#\u0001\u0003mC:<'\"A\u000b\u0002\t)\fg/Y\u0005\u0003/I\u0011aa\u00142kK\u000e$\bcA\r\u001fA5\t!D\u0003\u0002\u001c9\u0005Aa-\u001e8di&|gN\u0003\u0002\u001e)\u0005!Q\u000f^5m\u0013\ty\"D\u0001\bU_2{gn\u001a$v]\u000e$\u0018n\u001c8\u0011\u0005\u0005\u0012C\u0002\u0001\u0003\u0006G\u0001\u0011\r\u0001\n\u0002\u0002)F\u0011Q%\u000b\t\u0003M\u001dj\u0011aC\u0005\u0003Q-\u0011qAT8uQ&tw\r\u0005\u0002'U%\u00111f\u0003\u0002\u0004\u0003:L\u0018AA:g!\u00111c\u0006\t\u0019\n\u0005=Z!!\u0003$v]\u000e$\u0018n\u001c82!\t1\u0013'\u0003\u00023\u0017\t!Aj\u001c8h\u0003\u0019a\u0014N\\5u}Q\u0011Qg\u000e\t\u0004m\u0001\u0001S\"A\u0003\t\u000b1\u0012\u0001\u0019A\u0017\u0002\u0017\u0005\u0004\b\u000f\\=Bg2{gn\u001a\u000b\u0003aiBQaO\u0002A\u0002\u0001\n!\u0001_\u0019")
public class AsJavaToLongFunction<T>
implements ToLongFunction<T> {
    private final Function1<T, Object> sf;

    @Override
    public long applyAsLong(T x1) {
        return BoxesRunTime.unboxToLong(this.sf.apply(x1));
    }

    public AsJavaToLongFunction(Function1<T, Object> sf) {
        this.sf = sf;
    }
}

