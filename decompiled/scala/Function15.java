/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.Function14;
import scala.MatchError;
import scala.Tuple15;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005]ba\u0002\u0004\b!\u0003\r\tA\u0003\u0005\u0006!\u0001!\t!\u0005\u0005\u0006+\u00011\tA\u0006\u0005\u0006[\u0002!\tA\u001c\u0005\b\u0003\u001f\u0001A\u0011AA\t\u0011\u001d\ti\u0002\u0001C!\u0003?\u0011!BR;oGRLwN\\\u00196\u0015\u0005A\u0011!B:dC2\f7\u0001A\u000b\u0012\u0017\u0015Rs\u0006N\u001d?\u0007\"k%k\u0016/bM.L2C\u0001\u0001\r!\tia\"D\u0001\b\u0013\tyqA\u0001\u0004B]f\u0014VMZ\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003I\u0001\"!D\n\n\u0005Q9!\u0001B+oSR\fQ!\u00199qYf$\u0002c\u0006\u0012(YE24\bQ#K\u001fRKfl\u00195\u0011\u0005aIB\u0002\u0001\u0003\u00075\u0001!)\u0019A\u000e\u0003\u0003I\u000b\"\u0001H\u0010\u0011\u00055i\u0012B\u0001\u0010\b\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!\u0004\u0011\n\u0005\u0005:!aA!os\")1E\u0001a\u0001I\u0005\u0011a/\r\t\u00031\u0015\"aA\n\u0001\t\u0006\u0004Y\"A\u0001+2\u0011\u0015A#\u00011\u0001*\u0003\t1(\u0007\u0005\u0002\u0019U\u001111\u0006\u0001EC\u0002m\u0011!\u0001\u0016\u001a\t\u000b5\u0012\u0001\u0019\u0001\u0018\u0002\u0005Y\u001c\u0004C\u0001\r0\t\u0019\u0001\u0004\u0001#b\u00017\t\u0011Ak\r\u0005\u0006e\t\u0001\raM\u0001\u0003mR\u0002\"\u0001\u0007\u001b\u0005\rU\u0002\u0001R1\u0001\u001c\u0005\t!F\u0007C\u00038\u0005\u0001\u0007\u0001(\u0001\u0002wkA\u0011\u0001$\u000f\u0003\u0007u\u0001A)\u0019A\u000e\u0003\u0005Q+\u0004\"\u0002\u001f\u0003\u0001\u0004i\u0014A\u0001<7!\tAb\b\u0002\u0004@\u0001!\u0015\ra\u0007\u0002\u0003)ZBQ!\u0011\u0002A\u0002\t\u000b!A^\u001c\u0011\u0005a\u0019EA\u0002#\u0001\u0011\u000b\u00071D\u0001\u0002Uo!)aI\u0001a\u0001\u000f\u0006\u0011a\u000f\u000f\t\u00031!#a!\u0013\u0001\t\u0006\u0004Y\"A\u0001+9\u0011\u0015Y%\u00011\u0001M\u0003\t1\u0018\b\u0005\u0002\u0019\u001b\u00121a\n\u0001EC\u0002m\u0011!\u0001V\u001d\t\u000bA\u0013\u0001\u0019A)\u0002\u0007Y\f\u0004\u0007\u0005\u0002\u0019%\u001211\u000b\u0001EC\u0002m\u00111\u0001V\u00191\u0011\u0015)&\u00011\u0001W\u0003\r1\u0018'\r\t\u00031]#a\u0001\u0017\u0001\t\u0006\u0004Y\"a\u0001+2c!)!L\u0001a\u00017\u0006\u0019a/\r\u001a\u0011\u0005aaFAB/\u0001\u0011\u000b\u00071DA\u0002UcIBQa\u0018\u0002A\u0002\u0001\f1A^\u00194!\tA\u0012\r\u0002\u0004c\u0001!\u0015\ra\u0007\u0002\u0004)F\u001a\u0004\"\u00023\u0003\u0001\u0004)\u0017a\u0001<2iA\u0011\u0001D\u001a\u0003\u0007O\u0002A)\u0019A\u000e\u0003\u0007Q\u000bD\u0007C\u0003j\u0005\u0001\u0007!.A\u0002wcU\u0002\"\u0001G6\u0005\r1\u0004\u0001R1\u0001\u001c\u0005\r!\u0016'N\u0001\bGV\u0014(/[3e+\u0005y\u0007\u0003B\u0007qIIL!!]\u0004\u0003\u0013\u0019+hn\u0019;j_:\f\u0004\u0003B\u0007qSM\u0004B!\u00049/iB!Q\u0002]\u001av!\u0011i\u0001\u000f\u000f<\u0011\t5\u0001Xh\u001e\t\u0005\u001bA\u0014\u0005\u0010\u0005\u0003\u000ea\u001eK\b\u0003B\u0007q\u0019j\u0004B!\u00049RwB!Q\u0002\u001d,}!\u0011i\u0001oW?\u0011\t5\u0001\bM \t\u0005\u001bA,w\u0010\u0005\u0003\u000ea*<\u0002fA\u0002\u0002\u0004A!\u0011QAA\u0006\u001b\t\t9AC\u0002\u0002\n\u001d\t!\"\u00198o_R\fG/[8o\u0013\u0011\ti!a\u0002\u0003\u001bUt7\u000f]3dS\u0006d\u0017N_3e\u0003\u0019!X\u000f\u001d7fIV\u0011\u00111\u0003\t\u0006\u001bA\f)b\u0006\t\u0013\u001b\u0005]A%\u000b\u00184qu\u0012u\tT)W7\u0002,'.C\u0002\u0002\u001a\u001d\u0011q\u0001V;qY\u0016\fT\u0007K\u0002\u0005\u0003\u0007\t\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003C\u0001B!a\t\u000229!\u0011QEA\u0017!\r\t9cB\u0007\u0003\u0003SQ1!a\u000b\n\u0003\u0019a$o\\8u}%\u0019\u0011qF\u0004\u0002\rA\u0013X\rZ3g\u0013\u0011\t\u0019$!\u000e\u0003\rM#(/\u001b8h\u0015\r\tyc\u0002")
public interface Function15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14, T15 var15);

    public static /* synthetic */ Function1 curried$(Function15 $this) {
        return $this.curried();
    }

    default public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, Function1<T10, Function1<T11, Function1<T12, Function1<T13, Function1<T14, Function1<T15, R>>>>>>>>>>>>>>> curried() {
        return (Function1<Object, Function1> & Serializable)x1 -> ((Function14<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(Function14<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15) -> this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15)).curried();
    }

    public static /* synthetic */ Function1 tupled$(Function15 $this) {
        return $this.tupled();
    }

    default public Function1<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>, R> tupled() {
        return (Function1<Tuple15, Object> & Serializable)x0$1 -> {
            if (x0$1 != null) {
                Object x1 = x0$1._1();
                Object x2 = x0$1._2();
                Object x3 = x0$1._3();
                Object x4 = x0$1._4();
                Object x5 = x0$1._5();
                Object x6 = x0$1._6();
                Object x7 = x0$1._7();
                Object x8 = x0$1._8();
                Object x9 = x0$1._9();
                Object x10 = x0$1._10();
                Object x11 = x0$1._11();
                Object x12 = x0$1._12();
                Object x13 = x0$1._13();
                Object x14 = x0$1._14();
                Object x15 = x0$1._15();
                return this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15);
            }
            throw new MatchError((Object)null);
        };
    }

    public static /* synthetic */ String toString$(Function15 $this) {
        return $this.toString();
    }

    default public String toString() {
        return "<function15>";
    }

    public static void $init$(Function15 $this) {
    }
}

