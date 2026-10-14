/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ObjIntConsumer;
import scala.Function2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u00053A\u0001B\u0003\u0001\u001d!AA\u0006\u0001B\u0001B\u0003%Q\u0006C\u00037\u0001\u0011\u0005q\u0007C\u0003<\u0001\u0011\u0005AH\u0001\u000bBg*\u000bg/Y(cU&sGoQ8ogVlWM\u001d\u0006\u0003\r\u001d\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003\u0011%\tQA[1wCbR!AC\u0006\u0002\r\r|W\u000e]1u\u0015\u0005a\u0011!B:dC2\f7\u0001A\u000b\u0003\u001f\t\u001a2\u0001\u0001\t\u0019!\t\tb#D\u0001\u0013\u0015\t\u0019B#\u0001\u0003mC:<'\"A\u000b\u0002\t)\fg/Y\u0005\u0003/I\u0011aa\u00142kK\u000e$\bcA\r\u001fA5\t!D\u0003\u0002\u001c9\u0005Aa-\u001e8di&|gN\u0003\u0002\u001e)\u0005!Q\u000f^5m\u0013\ty\"D\u0001\bPE*Le\u000e^\"p]N,X.\u001a:\u0011\u0005\u0005\u0012C\u0002\u0001\u0003\u0006G\u0001\u0011\r\u0001\n\u0002\u0002)F\u0011Q%\u000b\t\u0003M\u001dj\u0011aC\u0005\u0003Q-\u0011qAT8uQ&tw\r\u0005\u0002'U%\u00111f\u0003\u0002\u0004\u0003:L\u0018AA:g!\u00151c\u0006\t\u00194\u0013\ty3BA\u0005Gk:\u001cG/[8oeA\u0011a%M\u0005\u0003e-\u00111!\u00138u!\t1C'\u0003\u00026\u0017\t!QK\\5u\u0003\u0019a\u0014N\\5u}Q\u0011\u0001H\u000f\t\u0004s\u0001\u0001S\"A\u0003\t\u000b1\u0012\u0001\u0019A\u0017\u0002\r\u0005\u001c7-\u001a9u)\r\u0019Th\u0010\u0005\u0006}\r\u0001\r\u0001I\u0001\u0003qFBQ\u0001Q\u0002A\u0002A\n!\u0001\u001f\u001a")
public class AsJavaObjIntConsumer<T>
implements ObjIntConsumer<T> {
    private final Function2<T, Object, BoxedUnit> sf;

    @Override
    public void accept(T x1, int x2) {
        this.sf.apply(x1, BoxesRunTime.boxToInteger(x2));
    }

    public AsJavaObjIntConsumer(Function2<T, Object, BoxedUnit> sf) {
        this.sf = sf;
    }
}

