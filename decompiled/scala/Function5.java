/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.Function4;
import scala.MatchError;
import scala.Tuple5;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}3qAB\u0004\u0011\u0002\u0007\u0005!\u0002C\u0003\u0011\u0001\u0011\u0005\u0011\u0003C\u0003\u0016\u0001\u0019\u0005a\u0003C\u0003<\u0001\u0011\u0005A\bC\u0003L\u0001\u0011\u0005A\nC\u0003S\u0001\u0011\u00053KA\u0005Gk:\u001cG/[8ok)\t\u0001\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u000f-)#f\f\u001b:3M\u0011\u0001\u0001\u0004\t\u0003\u001b9i\u0011aB\u0005\u0003\u001f\u001d\u0011a!\u00118z%\u00164\u0017A\u0002\u0013j]&$H\u0005F\u0001\u0013!\ti1#\u0003\u0002\u0015\u000f\t!QK\\5u\u0003\u0015\t\u0007\u000f\u001d7z)\u00199\"e\n\u00172mA\u0011\u0001$\u0007\u0007\u0001\t\u0019Q\u0002\u0001\"b\u00017\t\t!+\u0005\u0002\u001d?A\u0011Q\"H\u0005\u0003=\u001d\u0011qAT8uQ&tw\r\u0005\u0002\u000eA%\u0011\u0011e\u0002\u0002\u0004\u0003:L\b\"B\u0012\u0003\u0001\u0004!\u0013A\u0001<2!\tAR\u0005\u0002\u0004'\u0001!\u0015\ra\u0007\u0002\u0003)FBQ\u0001\u000b\u0002A\u0002%\n!A\u001e\u001a\u0011\u0005aQCAB\u0016\u0001\u0011\u000b\u00071D\u0001\u0002Ue!)QF\u0001a\u0001]\u0005\u0011ao\r\t\u00031=\"a\u0001\r\u0001\t\u0006\u0004Y\"A\u0001+4\u0011\u0015\u0011$\u00011\u00014\u0003\t1H\u0007\u0005\u0002\u0019i\u00111Q\u0007\u0001EC\u0002m\u0011!\u0001\u0016\u001b\t\u000b]\u0012\u0001\u0019\u0001\u001d\u0002\u0005Y,\u0004C\u0001\r:\t\u0019Q\u0004\u0001#b\u00017\t\u0011A+N\u0001\bGV\u0014(/[3e+\u0005i\u0004\u0003B\u0007?I\u0001K!aP\u0004\u0003\u0013\u0019+hn\u0019;j_:\f\u0004\u0003B\u0007?S\u0005\u0003B!\u0004 /\u0005B!QBP\u001aD!\u0011ia\bO\f)\u0005\r)\u0005C\u0001$J\u001b\u00059%B\u0001%\b\u0003)\tgN\\8uCRLwN\\\u0005\u0003\u0015\u001e\u0013Q\"\u001e8ta\u0016\u001c\u0017.\u00197ju\u0016$\u0017A\u0002;va2,G-F\u0001N!\u0011iaHT\f\u0011\u000f5yE%\u000b\u00184q%\u0011\u0001k\u0002\u0002\u0007)V\u0004H.Z\u001b)\u0005\u0011)\u0015\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0003Q\u0003\"!\u0016/\u000f\u0005YS\u0006CA,\b\u001b\u0005A&BA-\n\u0003\u0019a$o\\8u}%\u00111lB\u0001\u0007!J,G-\u001a4\n\u0005us&AB*ue&twM\u0003\u0002\\\u000f\u0001")
public interface Function5<T1, T2, T3, T4, T5, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5);

    public static /* synthetic */ Function1 curried$(Function5 $this) {
        return $this.curried();
    }

    default public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, R>>>>> curried() {
        return (Function1<Object, Function1> & Serializable)x1 -> ((Function4<Object, Object, Object, Object, Object> & Serializable)(Function4<Object, Object, Object, Object, Object> & Serializable)(x2, x3, x4, x5) -> this.apply(x1, x2, x3, x4, x5)).curried();
    }

    public static /* synthetic */ Function1 tupled$(Function5 $this) {
        return $this.tupled();
    }

    default public Function1<Tuple5<T1, T2, T3, T4, T5>, R> tupled() {
        return (Function1<Tuple5, Object> & Serializable)x0$1 -> {
            if (x0$1 != null) {
                Object x1 = x0$1._1();
                Object x2 = x0$1._2();
                Object x3 = x0$1._3();
                Object x4 = x0$1._4();
                Object x5 = x0$1._5();
                return this.apply(x1, x2, x3, x4, x5);
            }
            throw new MatchError((Object)null);
        };
    }

    public static /* synthetic */ String toString$(Function5 $this) {
        return $this.toString();
    }

    default public String toString() {
        return "<function5>";
    }

    public static void $init$(Function5 $this) {
    }
}

