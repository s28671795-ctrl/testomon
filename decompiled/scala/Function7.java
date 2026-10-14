/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.Function6;
import scala.MatchError;
import scala.Tuple7;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-4qAB\u0004\u0011\u0002\u0007\u0005!\u0002C\u0003\u0011\u0001\u0011\u0005\u0011\u0003C\u0003\u0016\u0001\u0019\u0005a\u0003C\u0003F\u0001\u0011\u0005a\tC\u0003X\u0001\u0011\u0005\u0001\fC\u0003_\u0001\u0011\u0005sLA\u0005Gk:\u001cG/[8oo)\t\u0001\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0013-)#f\f\u001b:}\rK2C\u0001\u0001\r!\tia\"D\u0001\b\u0013\tyqA\u0001\u0004B]f\u0014VMZ\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003I\u0001\"!D\n\n\u0005Q9!\u0001B+oSR\fQ!\u00199qYf$\u0002b\u0006\u0012(YE24\b\u0011\t\u00031ea\u0001\u0001\u0002\u0004\u001b\u0001\u0011\u0015\ra\u0007\u0002\u0002%F\u0011Ad\b\t\u0003\u001buI!AH\u0004\u0003\u000f9{G\u000f[5oOB\u0011Q\u0002I\u0005\u0003C\u001d\u00111!\u00118z\u0011\u0015\u0019#\u00011\u0001%\u0003\t1\u0018\u0007\u0005\u0002\u0019K\u00111a\u0005\u0001EC\u0002m\u0011!\u0001V\u0019\t\u000b!\u0012\u0001\u0019A\u0015\u0002\u0005Y\u0014\u0004C\u0001\r+\t\u0019Y\u0003\u0001#b\u00017\t\u0011AK\r\u0005\u0006[\t\u0001\rAL\u0001\u0003mN\u0002\"\u0001G\u0018\u0005\rA\u0002\u0001R1\u0001\u001c\u0005\t!6\u0007C\u00033\u0005\u0001\u00071'\u0001\u0002wiA\u0011\u0001\u0004\u000e\u0003\u0007k\u0001A)\u0019A\u000e\u0003\u0005Q#\u0004\"B\u001c\u0003\u0001\u0004A\u0014A\u0001<6!\tA\u0012\b\u0002\u0004;\u0001!\u0015\ra\u0007\u0002\u0003)VBQ\u0001\u0010\u0002A\u0002u\n!A\u001e\u001c\u0011\u0005aqDAB \u0001\u0011\u000b\u00071D\u0001\u0002Um!)\u0011I\u0001a\u0001\u0005\u0006\u0011ao\u000e\t\u00031\r#a\u0001\u0012\u0001\t\u0006\u0004Y\"A\u0001+8\u0003\u001d\u0019WO\u001d:jK\u0012,\u0012a\u0012\t\u0005\u001b!##*\u0003\u0002J\u000f\tIa)\u001e8di&|g.\r\t\u0005\u001b!K3\n\u0005\u0003\u000e\u0011:b\u0005\u0003B\u0007Ig5\u0003B!\u0004%9\u001dB!Q\u0002S\u001fP!\u0011i\u0001JQ\f)\u0005\r\t\u0006C\u0001*V\u001b\u0005\u0019&B\u0001+\b\u0003)\tgN\\8uCRLwN\\\u0005\u0003-N\u0013Q\"\u001e8ta\u0016\u001c\u0017.\u00197ju\u0016$\u0017A\u0002;va2,G-F\u0001Z!\u0011i\u0001JW\f\u0011\u00135YF%\u000b\u00184qu\u0012\u0015B\u0001/\b\u0005\u0019!V\u000f\u001d7fo!\u0012A!U\u0001\ti>\u001cFO]5oOR\t\u0001\r\u0005\u0002bQ:\u0011!M\u001a\t\u0003G\u001ei\u0011\u0001\u001a\u0006\u0003K&\ta\u0001\u0010:p_Rt\u0014BA4\b\u0003\u0019\u0001&/\u001a3fM&\u0011\u0011N\u001b\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\u001d<\u0001")
public interface Function7<T1, T2, T3, T4, T5, T6, T7, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7);

    public static /* synthetic */ Function1 curried$(Function7 $this) {
        return $this.curried();
    }

    default public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, R>>>>>>> curried() {
        return (Function1<Object, Function1> & Serializable)x1 -> ((Function6<Object, Object, Object, Object, Object, Object, Object> & Serializable)(Function6<Object, Object, Object, Object, Object, Object, Object> & Serializable)(x2, x3, x4, x5, x6, x7) -> this.apply(x1, x2, x3, x4, x5, x6, x7)).curried();
    }

    public static /* synthetic */ Function1 tupled$(Function7 $this) {
        return $this.tupled();
    }

    default public Function1<Tuple7<T1, T2, T3, T4, T5, T6, T7>, R> tupled() {
        return (Function1<Tuple7, Object> & Serializable)x0$1 -> {
            if (x0$1 != null) {
                Object x1 = x0$1._1();
                Object x2 = x0$1._2();
                Object x3 = x0$1._3();
                Object x4 = x0$1._4();
                Object x5 = x0$1._5();
                Object x6 = x0$1._6();
                Object x7 = x0$1._7();
                return this.apply(x1, x2, x3, x4, x5, x6, x7);
            }
            throw new MatchError((Object)null);
        };
    }

    public static /* synthetic */ String toString$(Function7 $this) {
        return $this.toString();
    }

    default public String toString() {
        return "<function7>";
    }

    public static void $init$(Function7 $this) {
    }
}

