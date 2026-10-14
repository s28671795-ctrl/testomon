/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.Tuple4;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e3qAB\u0004\u0011\u0002\u0007\u0005!\u0002C\u0003\u0011\u0001\u0011\u0005\u0011\u0003C\u0003\u0016\u0001\u0019\u0005a\u0003C\u00037\u0001\u0011\u0005q\u0007C\u0003F\u0001\u0011\u0005a\tC\u0003M\u0001\u0011\u0005SJA\u0005Gk:\u001cG/[8oi)\t\u0001\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\r-)#f\f\u001b\u001a'\t\u0001A\u0002\u0005\u0002\u000e\u001d5\tq!\u0003\u0002\u0010\u000f\t1\u0011I\\=SK\u001a\fa\u0001J5oSR$C#\u0001\n\u0011\u00055\u0019\u0012B\u0001\u000b\b\u0005\u0011)f.\u001b;\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u000b]\u0011s\u0005L\u0019\u0011\u0005aIB\u0002\u0001\u0003\u00075\u0001!)\u0019A\u000e\u0003\u0003I\u000b\"\u0001H\u0010\u0011\u00055i\u0012B\u0001\u0010\b\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!\u0004\u0011\n\u0005\u0005:!aA!os\")1E\u0001a\u0001I\u0005\u0011a/\r\t\u00031\u0015\"aA\n\u0001\t\u0006\u0004Y\"A\u0001+2\u0011\u0015A#\u00011\u0001*\u0003\t1(\u0007\u0005\u0002\u0019U\u001111\u0006\u0001EC\u0002m\u0011!\u0001\u0016\u001a\t\u000b5\u0012\u0001\u0019\u0001\u0018\u0002\u0005Y\u001c\u0004C\u0001\r0\t\u0019\u0001\u0004\u0001#b\u00017\t\u0011Ak\r\u0005\u0006e\t\u0001\raM\u0001\u0003mR\u0002\"\u0001\u0007\u001b\u0005\rU\u0002\u0001R1\u0001\u001c\u0005\t!F'A\u0004dkJ\u0014\u0018.\u001a3\u0016\u0003a\u0002B!D\u001d%w%\u0011!h\u0002\u0002\n\rVt7\r^5p]F\u0002B!D\u001d*yA!Q\"\u000f\u0018>!\u0011i\u0011hM\f)\u0005\ry\u0004C\u0001!D\u001b\u0005\t%B\u0001\"\b\u0003)\tgN\\8uCRLwN\\\u0005\u0003\t\u0006\u0013Q\"\u001e8ta\u0016\u001c\u0017.\u00197ju\u0016$\u0017A\u0002;va2,G-F\u0001H!\u0011i\u0011\bS\f\u0011\r5IE%\u000b\u00184\u0013\tQuA\u0001\u0004UkBdW\r\u000e\u0015\u0003\t}\n\u0001\u0002^8TiJLgn\u001a\u000b\u0002\u001dB\u0011qJ\u0016\b\u0003!R\u0003\"!U\u0004\u000e\u0003IS!aU\u0005\u0002\rq\u0012xn\u001c;?\u0013\t)v!\u0001\u0004Qe\u0016$WMZ\u0005\u0003/b\u0013aa\u0015;sS:<'BA+\b\u0001")
public interface Function4<T1, T2, T3, T4, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4);

    public static /* synthetic */ Function1 curried$(Function4 $this) {
        return $this.curried();
    }

    default public Function1<T1, Function1<T2, Function1<T3, Function1<T4, R>>>> curried() {
        return (Function1<Object, Function1> & Serializable)x1 -> (Function1<Object, Function1> & Serializable)x2 -> (Function1<Object, Function1> & Serializable)x3 -> (Function1<Object, Object> & Serializable)x4 -> this.apply(x1, x2, x3, x4);
    }

    public static /* synthetic */ Function1 tupled$(Function4 $this) {
        return $this.tupled();
    }

    default public Function1<Tuple4<T1, T2, T3, T4>, R> tupled() {
        return (Function1<Tuple4, Object> & Serializable)x0$1 -> {
            if (x0$1 != null) {
                Object x1 = x0$1._1();
                Object x2 = x0$1._2();
                Object x3 = x0$1._3();
                Object x4 = x0$1._4();
                return this.apply(x1, x2, x3, x4);
            }
            throw new MatchError((Object)null);
        };
    }

    public static /* synthetic */ String toString$(Function4 $this) {
        return $this.toString();
    }

    default public String toString() {
        return "<function4>";
    }

    public static void $init$(Function4 $this) {
    }
}

