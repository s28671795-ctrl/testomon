/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.Function21;
import scala.MatchError;
import scala.Tuple22;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005-ea\u0002\u0004\b!\u0003\r\tA\u0003\u0005\u0006!\u0001!\t!\u0005\u0005\u0006+\u00011\tA\u0006\u0005\b\u0003C\u0001A\u0011AA\u0012\u0011\u001d\t\u0019\u0007\u0001C\u0001\u0003KBq!!\u001d\u0001\t\u0003\n\u0019H\u0001\u0006Gk:\u001cG/[8oeIR\u0011\u0001C\u0001\u0006g\u000e\fG.Y\u0002\u0001+mYQEK\u00185sy\u001a\u0005*\u0014*X9\u000647\u000e];{\u007f\u0006%\u00111CA\u000f3M\u0011\u0001\u0001\u0004\t\u0003\u001b9i\u0011aB\u0005\u0003\u001f\u001d\u0011a!\u00118z%\u00164\u0017A\u0002\u0013j]&$H\u0005F\u0001\u0013!\ti1#\u0003\u0002\u0015\u000f\t!QK\\5u\u0003\u0015\t\u0007\u000f\u001d7z)i9\"e\n\u00172mm\u0002UIS(U3z\u001b\u0007.\u001c:xy\u0006\r\u0011QBA\f!\tA\u0012\u0004\u0004\u0001\u0005\ri\u0001AQ1\u0001\u001c\u0005\u0005\u0011\u0016C\u0001\u000f !\tiQ$\u0003\u0002\u001f\u000f\t9aj\u001c;iS:<\u0007CA\u0007!\u0013\t\tsAA\u0002B]fDQa\t\u0002A\u0002\u0011\n!A^\u0019\u0011\u0005a)CA\u0002\u0014\u0001\u0011\u000b\u00071D\u0001\u0002Uc!)\u0001F\u0001a\u0001S\u0005\u0011aO\r\t\u00031)\"aa\u000b\u0001\t\u0006\u0004Y\"A\u0001+3\u0011\u0015i#\u00011\u0001/\u0003\t18\u0007\u0005\u0002\u0019_\u00111\u0001\u0007\u0001EC\u0002m\u0011!\u0001V\u001a\t\u000bI\u0012\u0001\u0019A\u001a\u0002\u0005Y$\u0004C\u0001\r5\t\u0019)\u0004\u0001#b\u00017\t\u0011A\u000b\u000e\u0005\u0006o\t\u0001\r\u0001O\u0001\u0003mV\u0002\"\u0001G\u001d\u0005\ri\u0002\u0001R1\u0001\u001c\u0005\t!V\u0007C\u0003=\u0005\u0001\u0007Q(\u0001\u0002wmA\u0011\u0001D\u0010\u0003\u0007\u007f\u0001A)\u0019A\u000e\u0003\u0005Q3\u0004\"B!\u0003\u0001\u0004\u0011\u0015A\u0001<8!\tA2\t\u0002\u0004E\u0001!\u0015\ra\u0007\u0002\u0003)^BQA\u0012\u0002A\u0002\u001d\u000b!A\u001e\u001d\u0011\u0005aAEAB%\u0001\u0011\u000b\u00071D\u0001\u0002Uq!)1J\u0001a\u0001\u0019\u0006\u0011a/\u000f\t\u000315#aA\u0014\u0001\t\u0006\u0004Y\"A\u0001+:\u0011\u0015\u0001&\u00011\u0001R\u0003\r1\u0018\u0007\r\t\u00031I#aa\u0015\u0001\t\u0006\u0004Y\"a\u0001+2a!)QK\u0001a\u0001-\u0006\u0019a/M\u0019\u0011\u0005a9FA\u0002-\u0001\u0011\u000b\u00071DA\u0002UcEBQA\u0017\u0002A\u0002m\u000b1A^\u00193!\tAB\f\u0002\u0004^\u0001!\u0015\ra\u0007\u0002\u0004)F\u0012\u0004\"B0\u0003\u0001\u0004\u0001\u0017a\u0001<2gA\u0011\u0001$\u0019\u0003\u0007E\u0002A)\u0019A\u000e\u0003\u0007Q\u000b4\u0007C\u0003e\u0005\u0001\u0007Q-A\u0002wcQ\u0002\"\u0001\u00074\u0005\r\u001d\u0004\u0001R1\u0001\u001c\u0005\r!\u0016\u0007\u000e\u0005\u0006S\n\u0001\rA[\u0001\u0004mF*\u0004C\u0001\rl\t\u0019a\u0007\u0001#b\u00017\t\u0019A+M\u001b\t\u000b9\u0014\u0001\u0019A8\u0002\u0007Y\fd\u0007\u0005\u0002\u0019a\u00121\u0011\u000f\u0001EC\u0002m\u00111\u0001V\u00197\u0011\u0015\u0019(\u00011\u0001u\u0003\r1\u0018g\u000e\t\u00031U$aA\u001e\u0001\t\u0006\u0004Y\"a\u0001+2o!)\u0001P\u0001a\u0001s\u0006\u0019a/\r\u001d\u0011\u0005aQHAB>\u0001\u0011\u000b\u00071DA\u0002UcaBQ! \u0002A\u0002y\f1A^\u0019:!\tAr\u0010B\u0004\u0002\u0002\u0001A)\u0019A\u000e\u0003\u0007Q\u000b\u0014\bC\u0004\u0002\u0006\t\u0001\r!a\u0002\u0002\u0007Y\u0014\u0004\u0007E\u0002\u0019\u0003\u0013!q!a\u0003\u0001\u0011\u000b\u00071DA\u0002UeABq!a\u0004\u0003\u0001\u0004\t\t\"A\u0002weE\u00022\u0001GA\n\t\u001d\t)\u0002\u0001EC\u0002m\u00111\u0001\u0016\u001a2\u0011\u001d\tIB\u0001a\u0001\u00037\t1A\u001e\u001a3!\rA\u0012Q\u0004\u0003\b\u0003?\u0001\u0001R1\u0001\u001c\u0005\r!&GM\u0001\bGV\u0014(/[3e+\t\t)\u0003\u0005\u0004\u000e\u0003O!\u00131F\u0005\u0004\u0003S9!!\u0003$v]\u000e$\u0018n\u001c82!\u0019i\u0011qE\u0015\u0002.A1Q\"a\n/\u0003_\u0001b!DA\u0014g\u0005E\u0002CB\u0007\u0002(a\n\u0019\u0004\u0005\u0004\u000e\u0003Oi\u0014Q\u0007\t\u0007\u001b\u0005\u001d\")a\u000e\u0011\r5\t9cRA\u001d!\u0019i\u0011q\u0005'\u0002<A1Q\"a\nR\u0003{\u0001b!DA\u0014-\u0006}\u0002CB\u0007\u0002(m\u000b\t\u0005\u0005\u0004\u000e\u0003O\u0001\u00171\t\t\u0007\u001b\u0005\u001dR-!\u0012\u0011\r5\t9C[A$!\u0019i\u0011qE8\u0002JA1Q\"a\nu\u0003\u0017\u0002b!DA\u0014s\u00065\u0003CB\u0007\u0002(y\fy\u0005E\u0004\u000e\u0003O\t9!!\u0015\u0011\u000f5\t9#!\u0005\u0002TA1Q\"a\n\u0002\u001c]A3aAA,!\u0011\tI&a\u0018\u000e\u0005\u0005m#bAA/\u000f\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005\u0005\u00141\f\u0002\u000ek:\u001c\b/Z2jC2L'0\u001a3\u0002\rQ,\b\u000f\\3e+\t\t9\u0007\u0005\u0004\u000e\u0003O\tIg\u0006\t\u001d\u001b\u0005-D%\u000b\u00184qu\u0012u\tT)W7\u0002,'n\u001c;z}\u0006\u001d\u0011\u0011CA\u000e\u0013\r\tig\u0002\u0002\b)V\u0004H.\u001a\u001a3Q\r!\u0011qK\u0001\ti>\u001cFO]5oOR\u0011\u0011Q\u000f\t\u0005\u0003o\n)I\u0004\u0003\u0002z\u0005\u0005\u0005cAA>\u000f5\u0011\u0011Q\u0010\u0006\u0004\u0003\u007fJ\u0011A\u0002\u001fs_>$h(C\u0002\u0002\u0004\u001e\ta\u0001\u0015:fI\u00164\u0017\u0002BAD\u0003\u0013\u0013aa\u0015;sS:<'bAAB\u000f\u0001")
public interface Function22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14, T15 var15, T16 var16, T17 var17, T18 var18, T19 var19, T20 var20, T21 var21, T22 var22);

    public static /* synthetic */ Function1 curried$(Function22 $this) {
        return $this.curried();
    }

    default public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, Function1<T10, Function1<T11, Function1<T12, Function1<T13, Function1<T14, Function1<T15, Function1<T16, Function1<T17, Function1<T18, Function1<T19, Function1<T20, Function1<T21, Function1<T22, R>>>>>>>>>>>>>>>>>>>>>> curried() {
        return (Function1<Object, Function1> & Serializable)x1 -> ((Function21<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(Function21<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17, x18, x19, x20, x21, x22) -> this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17, x18, x19, x20, x21, x22)).curried();
    }

    public static /* synthetic */ Function1 tupled$(Function22 $this) {
        return $this.tupled();
    }

    default public Function1<Tuple22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>, R> tupled() {
        return (Function1<Tuple22, Object> & Serializable)x0$1 -> {
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
                Object x17 = x0$1._17();
                Object x18 = x0$1._18();
                Object x19 = x0$1._19();
                Object x20 = x0$1._20();
                Object x21 = x0$1._21();
                Object x22 = x0$1._22();
                return this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17, x18, x19, x20, x21, x22);
            }
            throw new MatchError((Object)null);
        };
    }

    public static /* synthetic */ String toString$(Function22 $this) {
        return $this.toString();
    }

    default public String toString() {
        return "<function22>";
    }

    public static void $init$(Function22 $this) {
    }
}

