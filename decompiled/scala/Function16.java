/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.Function15;
import scala.MatchError;
import scala.Tuple16;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\rca\u0002\u0004\b!\u0003\r\tA\u0003\u0005\u0006!\u0001!\t!\u0005\u0005\u0006+\u00011\tA\u0006\u0005\u0006e\u0002!\ta\u001d\u0005\b\u00037\u0001A\u0011AA\u000f\u0011\u001d\tI\u0003\u0001C!\u0003W\u0011!BR;oGRLwN\\\u00197\u0015\u0005A\u0011!B:dC2\f7\u0001A\u000b\u0013\u0017\u0015Rs\u0006N\u001d?\u0007\"k%k\u0016/bM.\u0004\u0018d\u0005\u0002\u0001\u0019A\u0011QBD\u0007\u0002\u000f%\u0011qb\u0002\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%)\u0005\u0011\u0002CA\u0007\u0014\u0013\t!rA\u0001\u0003V]&$\u0018!B1qa2LH#E\f#O1\ndg\u000f!F\u0015>#\u0016LX2i[B\u0011\u0001$\u0007\u0007\u0001\t\u0019Q\u0002\u0001\"b\u00017\t\t!+\u0005\u0002\u001d?A\u0011Q\"H\u0005\u0003=\u001d\u0011qAT8uQ&tw\r\u0005\u0002\u000eA%\u0011\u0011e\u0002\u0002\u0004\u0003:L\b\"B\u0012\u0003\u0001\u0004!\u0013A\u0001<2!\tAR\u0005\u0002\u0004'\u0001!\u0015\ra\u0007\u0002\u0003)FBQ\u0001\u000b\u0002A\u0002%\n!A\u001e\u001a\u0011\u0005aQCAB\u0016\u0001\u0011\u000b\u00071D\u0001\u0002Ue!)QF\u0001a\u0001]\u0005\u0011ao\r\t\u00031=\"a\u0001\r\u0001\t\u0006\u0004Y\"A\u0001+4\u0011\u0015\u0011$\u00011\u00014\u0003\t1H\u0007\u0005\u0002\u0019i\u00111Q\u0007\u0001EC\u0002m\u0011!\u0001\u0016\u001b\t\u000b]\u0012\u0001\u0019\u0001\u001d\u0002\u0005Y,\u0004C\u0001\r:\t\u0019Q\u0004\u0001#b\u00017\t\u0011A+\u000e\u0005\u0006y\t\u0001\r!P\u0001\u0003mZ\u0002\"\u0001\u0007 \u0005\r}\u0002\u0001R1\u0001\u001c\u0005\t!f\u0007C\u0003B\u0005\u0001\u0007!)\u0001\u0002woA\u0011\u0001d\u0011\u0003\u0007\t\u0002A)\u0019A\u000e\u0003\u0005Q;\u0004\"\u0002$\u0003\u0001\u00049\u0015A\u0001<9!\tA\u0002\n\u0002\u0004J\u0001!\u0015\ra\u0007\u0002\u0003)bBQa\u0013\u0002A\u00021\u000b!A^\u001d\u0011\u0005aiEA\u0002(\u0001\u0011\u000b\u00071D\u0001\u0002Us!)\u0001K\u0001a\u0001#\u0006\u0019a/\r\u0019\u0011\u0005a\u0011FAB*\u0001\u0011\u000b\u00071DA\u0002UcABQ!\u0016\u0002A\u0002Y\u000b1A^\u00192!\tAr\u000b\u0002\u0004Y\u0001!\u0015\ra\u0007\u0002\u0004)F\n\u0004\"\u0002.\u0003\u0001\u0004Y\u0016a\u0001<2eA\u0011\u0001\u0004\u0018\u0003\u0007;\u0002A)\u0019A\u000e\u0003\u0007Q\u000b$\u0007C\u0003`\u0005\u0001\u0007\u0001-A\u0002wcM\u0002\"\u0001G1\u0005\r\t\u0004\u0001R1\u0001\u001c\u0005\r!\u0016g\r\u0005\u0006I\n\u0001\r!Z\u0001\u0004mF\"\u0004C\u0001\rg\t\u00199\u0007\u0001#b\u00017\t\u0019A+\r\u001b\t\u000b%\u0014\u0001\u0019\u00016\u0002\u0007Y\fT\u0007\u0005\u0002\u0019W\u00121A\u000e\u0001EC\u0002m\u00111\u0001V\u00196\u0011\u0015q'\u00011\u0001p\u0003\r1\u0018G\u000e\t\u00031A$a!\u001d\u0001\t\u0006\u0004Y\"a\u0001+2m\u000591-\u001e:sS\u0016$W#\u0001;\u0011\t5)He^\u0005\u0003m\u001e\u0011\u0011BR;oGRLwN\\\u0019\u0011\t5)\u0018\u0006\u001f\t\u0005\u001bUt\u0013\u0010\u0005\u0003\u000ekNR\b\u0003B\u0007vqm\u0004B!D;>yB!Q\"\u001e\"~!\u0011iQo\u0012@\u0011\t5)Hj \t\u0006\u001bU\f\u0016\u0011\u0001\t\u0006\u001bU4\u00161\u0001\t\u0006\u001bU\\\u0016Q\u0001\t\u0006\u001bU\u0004\u0017q\u0001\t\u0006\u001bU,\u0017\u0011\u0002\t\u0006\u001bUT\u00171\u0002\t\u0005\u001bU|w\u0003K\u0002\u0004\u0003\u001f\u0001B!!\u0005\u0002\u00185\u0011\u00111\u0003\u0006\u0004\u0003+9\u0011AC1o]>$\u0018\r^5p]&!\u0011\u0011DA\n\u00055)hn\u001d9fG&\fG.\u001b>fI\u00061A/\u001e9mK\u0012,\"!a\b\u0011\u000b5)\u0018\u0011E\f\u0011'5\t\u0019\u0003J\u0015/gaj$i\u0012'R-n\u0003WM[8\n\u0007\u0005\u0015rAA\u0004UkBdW-\r\u001c)\u0007\u0011\ty!\u0001\u0005u_N#(/\u001b8h)\t\ti\u0003\u0005\u0003\u00020\u0005ub\u0002BA\u0019\u0003s\u00012!a\r\b\u001b\t\t)DC\u0002\u00028%\ta\u0001\u0010:p_Rt\u0014bAA\u001e\u000f\u00051\u0001K]3eK\u001aLA!a\u0010\u0002B\t11\u000b\u001e:j]\u001eT1!a\u000f\b\u0001")
public interface Function16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14, T15 var15, T16 var16);

    public static /* synthetic */ Function1 curried$(Function16 $this) {
        return $this.curried();
    }

    default public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, Function1<T10, Function1<T11, Function1<T12, Function1<T13, Function1<T14, Function1<T15, Function1<T16, R>>>>>>>>>>>>>>>> curried() {
        return (Function1<Object, Function1> & Serializable)x1 -> ((Function15<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(Function15<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16) -> this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16)).curried();
    }

    public static /* synthetic */ Function1 tupled$(Function16 $this) {
        return $this.tupled();
    }

    default public Function1<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>, R> tupled() {
        return (Function1<Tuple16, Object> & Serializable)x0$1 -> {
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
                Object x16 = x0$1._16();
                return this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16);
            }
            throw new MatchError((Object)null);
        };
    }

    public static /* synthetic */ String toString$(Function16 $this) {
        return $this.toString();
    }

    default public String toString() {
        return "<function16>";
    }

    public static void $init$(Function16 $this) {
    }
}

