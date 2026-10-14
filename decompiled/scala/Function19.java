/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.Function18;
import scala.MatchError;
import scala.Tuple19;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dda\u0002\u0004\b!\u0003\r\tA\u0003\u0005\u0006!\u0001!\t!\u0005\u0005\u0006+\u00011\tA\u0006\u0005\b\u0003\u0007\u0001A\u0011AA\u0003\u0011\u001d\ty\u0004\u0001C\u0001\u0003\u0003Bq!!\u0014\u0001\t\u0003\nyE\u0001\u0006Gk:\u001cG/[8oceR\u0011\u0001C\u0001\u0006g\u000e\fG.Y\u0002\u0001+UYQEK\u00185sy\u001a\u0005*\u0014*X9\u000647\u000e];{\u007ff\u0019\"\u0001\u0001\u0007\u0011\u00055qQ\"A\u0004\n\u0005=9!AB!osJ+g-\u0001\u0004%S:LG\u000f\n\u000b\u0002%A\u0011QbE\u0005\u0003)\u001d\u0011A!\u00168ji\u0006)\u0011\r\u001d9msR!rCI\u0014-cYZ\u0004)\u0012&P)fs6\r[7sor\u0004\"\u0001G\r\r\u0001\u00111!\u0004\u0001CC\u0002m\u0011\u0011AU\t\u00039}\u0001\"!D\u000f\n\u0005y9!a\u0002(pi\"Lgn\u001a\t\u0003\u001b\u0001J!!I\u0004\u0003\u0007\u0005s\u0017\u0010C\u0003$\u0005\u0001\u0007A%\u0001\u0002wcA\u0011\u0001$\n\u0003\u0007M\u0001A)\u0019A\u000e\u0003\u0005Q\u000b\u0004\"\u0002\u0015\u0003\u0001\u0004I\u0013A\u0001<3!\tA\"\u0006\u0002\u0004,\u0001!\u0015\ra\u0007\u0002\u0003)JBQ!\f\u0002A\u00029\n!A^\u001a\u0011\u0005ayCA\u0002\u0019\u0001\u0011\u000b\u00071D\u0001\u0002Ug!)!G\u0001a\u0001g\u0005\u0011a\u000f\u000e\t\u00031Q\"a!\u000e\u0001\t\u0006\u0004Y\"A\u0001+5\u0011\u00159$\u00011\u00019\u0003\t1X\u0007\u0005\u0002\u0019s\u00111!\b\u0001EC\u0002m\u0011!\u0001V\u001b\t\u000bq\u0012\u0001\u0019A\u001f\u0002\u0005Y4\u0004C\u0001\r?\t\u0019y\u0004\u0001#b\u00017\t\u0011AK\u000e\u0005\u0006\u0003\n\u0001\rAQ\u0001\u0003m^\u0002\"\u0001G\"\u0005\r\u0011\u0003\u0001R1\u0001\u001c\u0005\t!v\u0007C\u0003G\u0005\u0001\u0007q)\u0001\u0002wqA\u0011\u0001\u0004\u0013\u0003\u0007\u0013\u0002A)\u0019A\u000e\u0003\u0005QC\u0004\"B&\u0003\u0001\u0004a\u0015A\u0001<:!\tAR\n\u0002\u0004O\u0001!\u0015\ra\u0007\u0002\u0003)fBQ\u0001\u0015\u0002A\u0002E\u000b1A^\u00191!\tA\"\u000b\u0002\u0004T\u0001!\u0015\ra\u0007\u0002\u0004)F\u0002\u0004\"B+\u0003\u0001\u00041\u0016a\u0001<2cA\u0011\u0001d\u0016\u0003\u00071\u0002A)\u0019A\u000e\u0003\u0007Q\u000b\u0014\u0007C\u0003[\u0005\u0001\u00071,A\u0002wcI\u0002\"\u0001\u0007/\u0005\ru\u0003\u0001R1\u0001\u001c\u0005\r!\u0016G\r\u0005\u0006?\n\u0001\r\u0001Y\u0001\u0004mF\u001a\u0004C\u0001\rb\t\u0019\u0011\u0007\u0001#b\u00017\t\u0019A+M\u001a\t\u000b\u0011\u0014\u0001\u0019A3\u0002\u0007Y\fD\u0007\u0005\u0002\u0019M\u00121q\r\u0001EC\u0002m\u00111\u0001V\u00195\u0011\u0015I'\u00011\u0001k\u0003\r1\u0018'\u000e\t\u00031-$a\u0001\u001c\u0001\t\u0006\u0004Y\"a\u0001+2k!)aN\u0001a\u0001_\u0006\u0019a/\r\u001c\u0011\u0005a\u0001HAB9\u0001\u0011\u000b\u00071DA\u0002UcYBQa\u001d\u0002A\u0002Q\f1A^\u00198!\tAR\u000f\u0002\u0004w\u0001!\u0015\ra\u0007\u0002\u0004)F:\u0004\"\u0002=\u0003\u0001\u0004I\u0018a\u0001<2qA\u0011\u0001D\u001f\u0003\u0007w\u0002A)\u0019A\u000e\u0003\u0007Q\u000b\u0004\bC\u0003~\u0005\u0001\u0007a0A\u0002wce\u0002\"\u0001G@\u0005\u000f\u0005\u0005\u0001\u0001#b\u00017\t\u0019A+M\u001d\u0002\u000f\r,(O]5fIV\u0011\u0011q\u0001\t\u0007\u001b\u0005%A%!\u0004\n\u0007\u0005-qAA\u0005Gk:\u001cG/[8ocA1Q\"!\u0003*\u0003\u001f\u0001b!DA\u0005]\u0005E\u0001CB\u0007\u0002\nM\n\u0019\u0002\u0005\u0004\u000e\u0003\u0013A\u0014Q\u0003\t\u0007\u001b\u0005%Q(a\u0006\u0011\r5\tIAQA\r!\u0019i\u0011\u0011B$\u0002\u001cA1Q\"!\u0003M\u0003;\u0001b!DA\u0005#\u0006}\u0001CB\u0007\u0002\nY\u000b\t\u0003\u0005\u0004\u000e\u0003\u0013Y\u00161\u0005\t\u0007\u001b\u0005%\u0001-!\n\u0011\r5\tI!ZA\u0014!\u0019i\u0011\u0011\u00026\u0002*A1Q\"!\u0003p\u0003W\u0001b!DA\u0005i\u00065\u0002CB\u0007\u0002\ne\fy\u0003E\u0003\u000e\u0003\u0013qx\u0003K\u0002\u0004\u0003g\u0001B!!\u000e\u0002<5\u0011\u0011q\u0007\u0006\u0004\u0003s9\u0011AC1o]>$\u0018\r^5p]&!\u0011QHA\u001c\u00055)hn\u001d9fG&\fG.\u001b>fI\u00061A/\u001e9mK\u0012,\"!a\u0011\u0011\r5\tI!!\u0012\u0018!Yi\u0011q\t\u0013*]MBTHQ$M#Z[\u0006-\u001a6pift\u0018bAA%\u000f\t9A+\u001e9mKFJ\u0004f\u0001\u0003\u00024\u0005AAo\\*ue&tw\r\u0006\u0002\u0002RA!\u00111KA1\u001d\u0011\t)&!\u0018\u0011\u0007\u0005]s!\u0004\u0002\u0002Z)\u0019\u00111L\u0005\u0002\rq\u0012xn\u001c;?\u0013\r\tyfB\u0001\u0007!J,G-\u001a4\n\t\u0005\r\u0014Q\r\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005}s\u0001")
public interface Function19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14, T15 var15, T16 var16, T17 var17, T18 var18, T19 var19);

    public static /* synthetic */ Function1 curried$(Function19 $this) {
        return $this.curried();
    }

    default public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, Function1<T10, Function1<T11, Function1<T12, Function1<T13, Function1<T14, Function1<T15, Function1<T16, Function1<T17, Function1<T18, Function1<T19, R>>>>>>>>>>>>>>>>>>> curried() {
        return (Function1<Object, Function1> & Serializable)x1 -> ((Function18<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(Function18<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17, x18, x19) -> this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17, x18, x19)).curried();
    }

    public static /* synthetic */ Function1 tupled$(Function19 $this) {
        return $this.tupled();
    }

    default public Function1<Tuple19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>, R> tupled() {
        return (Function1<Tuple19, Object> & Serializable)x0$1 -> {
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
                return this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17, x18, x19);
            }
            throw new MatchError((Object)null);
        };
    }

    public static /* synthetic */ String toString$(Function19 $this) {
        return $this.toString();
    }

    default public String toString() {
        return "<function19>";
    }

    public static void $init$(Function19 $this) {
    }
}

