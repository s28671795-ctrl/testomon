/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.Function13;
import scala.MatchError;
import scala.Tuple14;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005-ba\u0002\u0004\b!\u0003\r\tA\u0003\u0005\u0006!\u0001!\t!\u0005\u0005\u0006+\u00011\tA\u0006\u0005\u0006Q\u0002!\t!\u001b\u0005\b\u0003\u0007\u0001A\u0011AA\u0003\u0011\u001d\t\t\u0002\u0001C!\u0003'\u0011!BR;oGRLwN\\\u00195\u0015\u0005A\u0011!B:dC2\f7\u0001A\u000b\u0011\u0017\u0015Rs\u0006N\u001d?\u0007\"k%k\u0016/bMf\u0019\"\u0001\u0001\u0007\u0011\u00055qQ\"A\u0004\n\u0005=9!AB!osJ+g-\u0001\u0004%S:LG\u000f\n\u000b\u0002%A\u0011QbE\u0005\u0003)\u001d\u0011A!\u00168ji\u0006)\u0011\r\u001d9msRyqCI\u0014-cYZ\u0004)\u0012&P)fs6\r\u0005\u0002\u001931\u0001AA\u0002\u000e\u0001\t\u000b\u00071DA\u0001S#\tar\u0004\u0005\u0002\u000e;%\u0011ad\u0002\u0002\b\u001d>$\b.\u001b8h!\ti\u0001%\u0003\u0002\"\u000f\t\u0019\u0011I\\=\t\u000b\r\u0012\u0001\u0019\u0001\u0013\u0002\u0005Y\f\u0004C\u0001\r&\t\u00191\u0003\u0001#b\u00017\t\u0011A+\r\u0005\u0006Q\t\u0001\r!K\u0001\u0003mJ\u0002\"\u0001\u0007\u0016\u0005\r-\u0002\u0001R1\u0001\u001c\u0005\t!&\u0007C\u0003.\u0005\u0001\u0007a&\u0001\u0002wgA\u0011\u0001d\f\u0003\u0007a\u0001A)\u0019A\u000e\u0003\u0005Q\u001b\u0004\"\u0002\u001a\u0003\u0001\u0004\u0019\u0014A\u0001<5!\tAB\u0007\u0002\u00046\u0001!\u0015\ra\u0007\u0002\u0003)RBQa\u000e\u0002A\u0002a\n!A^\u001b\u0011\u0005aIDA\u0002\u001e\u0001\u0011\u000b\u00071D\u0001\u0002Uk!)AH\u0001a\u0001{\u0005\u0011aO\u000e\t\u00031y\"aa\u0010\u0001\t\u0006\u0004Y\"A\u0001+7\u0011\u0015\t%\u00011\u0001C\u0003\t1x\u0007\u0005\u0002\u0019\u0007\u00121A\t\u0001EC\u0002m\u0011!\u0001V\u001c\t\u000b\u0019\u0013\u0001\u0019A$\u0002\u0005YD\u0004C\u0001\rI\t\u0019I\u0005\u0001#b\u00017\t\u0011A\u000b\u000f\u0005\u0006\u0017\n\u0001\r\u0001T\u0001\u0003mf\u0002\"\u0001G'\u0005\r9\u0003\u0001R1\u0001\u001c\u0005\t!\u0016\bC\u0003Q\u0005\u0001\u0007\u0011+A\u0002wcA\u0002\"\u0001\u0007*\u0005\rM\u0003\u0001R1\u0001\u001c\u0005\r!\u0016\u0007\r\u0005\u0006+\n\u0001\rAV\u0001\u0004mF\n\u0004C\u0001\rX\t\u0019A\u0006\u0001#b\u00017\t\u0019A+M\u0019\t\u000bi\u0013\u0001\u0019A.\u0002\u0007Y\f$\u0007\u0005\u0002\u00199\u00121Q\f\u0001EC\u0002m\u00111\u0001V\u00193\u0011\u0015y&\u00011\u0001a\u0003\r1\u0018g\r\t\u00031\u0005$aA\u0019\u0001\t\u0006\u0004Y\"a\u0001+2g!)AM\u0001a\u0001K\u0006\u0019a/\r\u001b\u0011\u0005a1GAB4\u0001\u0011\u000b\u00071DA\u0002UcQ\nqaY;se&,G-F\u0001k!\u0011i1\u000eJ7\n\u00051<!!\u0003$v]\u000e$\u0018n\u001c82!\u0011i1.\u000b8\u0011\t5Ygf\u001c\t\u0005\u001b-\u001c\u0004\u000f\u0005\u0003\u000eWb\n\b\u0003B\u0007l{I\u0004B!D6CgB!Qb[$u!\u0011i1\u000eT;\u0011\t5Y\u0017K\u001e\t\u0005\u001b-4v\u000f\u0005\u0003\u000eWnC\b\u0003B\u0007lAf\u0004B!D6f/!\u00121a\u001f\t\u0003y~l\u0011! \u0006\u0003}\u001e\t!\"\u00198o_R\fG/[8o\u0013\r\t\t! \u0002\u000ek:\u001c\b/Z2jC2L'0\u001a3\u0002\rQ,\b\u000f\\3e+\t\t9\u0001E\u0003\u000eW\u0006%q\u0003E\t\u000e\u0003\u0017!\u0013FL\u001a9{\t;E*\u0015,\\A\u0016L1!!\u0004\b\u0005\u001d!V\u000f\u001d7fcQB#\u0001B>\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!\u0006\u0011\t\u0005]\u0011Q\u0005\b\u0005\u00033\t\t\u0003E\u0002\u0002\u001c\u001di!!!\b\u000b\u0007\u0005}\u0011\"\u0001\u0004=e>|GOP\u0005\u0004\u0003G9\u0011A\u0002)sK\u0012,g-\u0003\u0003\u0002(\u0005%\"AB*ue&twMC\u0002\u0002$\u001d\u0001")
public interface Function14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14);

    public static /* synthetic */ Function1 curried$(Function14 $this) {
        return $this.curried();
    }

    default public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, Function1<T10, Function1<T11, Function1<T12, Function1<T13, Function1<T14, R>>>>>>>>>>>>>> curried() {
        return (Function1<Object, Function1> & Serializable)x1 -> ((Function13<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(Function13<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14) -> this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14)).curried();
    }

    public static /* synthetic */ Function1 tupled$(Function14 $this) {
        return $this.tupled();
    }

    default public Function1<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>, R> tupled() {
        return (Function1<Tuple14, Object> & Serializable)x0$1 -> {
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
                return this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14);
            }
            throw new MatchError((Object)null);
        };
    }

    public static /* synthetic */ String toString$(Function14 $this) {
        return $this.toString();
    }

    default public String toString() {
        return "<function14>";
    }

    public static void $init$(Function14 $this) {
    }
}

