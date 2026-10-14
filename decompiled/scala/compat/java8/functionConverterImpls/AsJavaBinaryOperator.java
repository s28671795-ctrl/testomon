/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BinaryOperator;
import scala.Function2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005m2A\u0001B\u0003\u0001\u001d!AA\u0006\u0001B\u0001B\u0003%Q\u0006C\u00031\u0001\u0011\u0005\u0011\u0007C\u00036\u0001\u0011\u0005aG\u0001\u000bBg*\u000bg/\u0019\"j]\u0006\u0014\u0018p\u00149fe\u0006$xN\u001d\u0006\u0003\r\u001d\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003\u0011%\tQA[1wCbR!AC\u0006\u0002\r\r|W\u000e]1u\u0015\u0005a\u0011!B:dC2\f7\u0001A\u000b\u0003\u001f\t\u001a2\u0001\u0001\t\u0019!\t\tb#D\u0001\u0013\u0015\t\u0019B#\u0001\u0003mC:<'\"A\u000b\u0002\t)\fg/Y\u0005\u0003/I\u0011aa\u00142kK\u000e$\bcA\r\u001fA5\t!D\u0003\u0002\u001c9\u0005Aa-\u001e8di&|gN\u0003\u0002\u001e)\u0005!Q\u000f^5m\u0013\ty\"D\u0001\bCS:\f'/_(qKJ\fGo\u001c:\u0011\u0005\u0005\u0012C\u0002\u0001\u0003\u0006G\u0001\u0011\r\u0001\n\u0002\u0002)F\u0011Q%\u000b\t\u0003M\u001dj\u0011aC\u0005\u0003Q-\u0011qAT8uQ&tw\r\u0005\u0002'U%\u00111f\u0003\u0002\u0004\u0003:L\u0018AA:g!\u00151c\u0006\t\u0011!\u0013\ty3BA\u0005Gk:\u001cG/[8oe\u00051A(\u001b8jiz\"\"A\r\u001b\u0011\u0007M\u0002\u0001%D\u0001\u0006\u0011\u0015a#\u00011\u0001.\u0003\u0015\t\u0007\u000f\u001d7z)\r\u0001s'\u000f\u0005\u0006q\r\u0001\r\u0001I\u0001\u0003qFBQAO\u0002A\u0002\u0001\n!\u0001\u001f\u001a")
public class AsJavaBinaryOperator<T>
implements BinaryOperator<T> {
    private final Function2<T, T, T> sf;

    @Override
    public T apply(T x1, T x2) {
        return this.sf.apply(x1, x2);
    }

    public AsJavaBinaryOperator(Function2<T, T, T> sf) {
        this.sf = sf;
    }
}

