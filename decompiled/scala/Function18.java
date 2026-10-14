/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.Function17;
import scala.MatchError;
import scala.Tuple18;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005mca\u0002\u0004\b!\u0003\r\tA\u0003\u0005\u0006!\u0001!\t!\u0005\u0005\u0006+\u00011\tA\u0006\u0005\u0006y\u0002!\t! \u0005\b\u0003g\u0001A\u0011AA\u001b\u0011\u001d\t\t\u0005\u0001C!\u0003\u0007\u0012!BR;oGRLwN\\\u00199\u0015\u0005A\u0011!B:dC2\f7\u0001A\u000b\u0015\u0017\u0015Rs\u0006N\u001d?\u0007\"k%k\u0016/bM.\u0004XO_\r\u0014\u0005\u0001a\u0001CA\u0007\u000f\u001b\u00059\u0011BA\b\b\u0005\u0019\te.\u001f*fM\u00061A%\u001b8ji\u0012\"\u0012A\u0005\t\u0003\u001bMI!\u0001F\u0004\u0003\tUs\u0017\u000e^\u0001\u0006CB\u0004H.\u001f\u000b\u0014/\t:C&\r\u001c<\u0001\u0016Su\nV-_G\"l'o\u001e\t\u00031ea\u0001\u0001\u0002\u0004\u001b\u0001\u0011\u0015\ra\u0007\u0002\u0002%F\u0011Ad\b\t\u0003\u001buI!AH\u0004\u0003\u000f9{G\u000f[5oOB\u0011Q\u0002I\u0005\u0003C\u001d\u00111!\u00118z\u0011\u0015\u0019#\u00011\u0001%\u0003\t1\u0018\u0007\u0005\u0002\u0019K\u00111a\u0005\u0001EC\u0002m\u0011!\u0001V\u0019\t\u000b!\u0012\u0001\u0019A\u0015\u0002\u0005Y\u0014\u0004C\u0001\r+\t\u0019Y\u0003\u0001#b\u00017\t\u0011AK\r\u0005\u0006[\t\u0001\rAL\u0001\u0003mN\u0002\"\u0001G\u0018\u0005\rA\u0002\u0001R1\u0001\u001c\u0005\t!6\u0007C\u00033\u0005\u0001\u00071'\u0001\u0002wiA\u0011\u0001\u0004\u000e\u0003\u0007k\u0001A)\u0019A\u000e\u0003\u0005Q#\u0004\"B\u001c\u0003\u0001\u0004A\u0014A\u0001<6!\tA\u0012\b\u0002\u0004;\u0001!\u0015\ra\u0007\u0002\u0003)VBQ\u0001\u0010\u0002A\u0002u\n!A\u001e\u001c\u0011\u0005aqDAB \u0001\u0011\u000b\u00071D\u0001\u0002Um!)\u0011I\u0001a\u0001\u0005\u0006\u0011ao\u000e\t\u00031\r#a\u0001\u0012\u0001\t\u0006\u0004Y\"A\u0001+8\u0011\u00151%\u00011\u0001H\u0003\t1\b\b\u0005\u0002\u0019\u0011\u00121\u0011\n\u0001EC\u0002m\u0011!\u0001\u0016\u001d\t\u000b-\u0013\u0001\u0019\u0001'\u0002\u0005YL\u0004C\u0001\rN\t\u0019q\u0005\u0001#b\u00017\t\u0011A+\u000f\u0005\u0006!\n\u0001\r!U\u0001\u0004mF\u0002\u0004C\u0001\rS\t\u0019\u0019\u0006\u0001#b\u00017\t\u0019A+\r\u0019\t\u000bU\u0013\u0001\u0019\u0001,\u0002\u0007Y\f\u0014\u0007\u0005\u0002\u0019/\u00121\u0001\f\u0001EC\u0002m\u00111\u0001V\u00192\u0011\u0015Q&\u00011\u0001\\\u0003\r1\u0018G\r\t\u00031q#a!\u0018\u0001\t\u0006\u0004Y\"a\u0001+2e!)qL\u0001a\u0001A\u0006\u0019a/M\u001a\u0011\u0005a\tGA\u00022\u0001\u0011\u000b\u00071DA\u0002UcMBQ\u0001\u001a\u0002A\u0002\u0015\f1A^\u00195!\tAb\r\u0002\u0004h\u0001!\u0015\ra\u0007\u0002\u0004)F\"\u0004\"B5\u0003\u0001\u0004Q\u0017a\u0001<2kA\u0011\u0001d\u001b\u0003\u0007Y\u0002A)\u0019A\u000e\u0003\u0007Q\u000bT\u0007C\u0003o\u0005\u0001\u0007q.A\u0002wcY\u0002\"\u0001\u00079\u0005\rE\u0004\u0001R1\u0001\u001c\u0005\r!\u0016G\u000e\u0005\u0006g\n\u0001\r\u0001^\u0001\u0004mF:\u0004C\u0001\rv\t\u00191\b\u0001#b\u00017\t\u0019A+M\u001c\t\u000ba\u0014\u0001\u0019A=\u0002\u0007Y\f\u0004\b\u0005\u0002\u0019u\u001211\u0010\u0001EC\u0002m\u00111\u0001V\u00199\u0003\u001d\u0019WO\u001d:jK\u0012,\u0012A \t\u0006\u001b}$\u00131A\u0005\u0004\u0003\u00039!!\u0003$v]\u000e$\u0018n\u001c82!\u0015iq0KA\u0003!\u0015iqPLA\u0004!\u0015iqpMA\u0005!\u0015iq\u0010OA\u0006!\u0015iq0PA\u0007!\u0015iqPQA\b!\u0015iqpRA\t!\u0015iq\u0010TA\n!\u0015iq0UA\u000b!\u0015iqPVA\f!\u0015iqpWA\r!\u0015iq\u0010YA\u000e!\u0015iq0ZA\u000f!\u0015iqP[A\u0010!\u0015iqp\\A\u0011!\u0015iq\u0010^A\u0012!\u0011iq0_\f)\u0007\r\t9\u0003\u0005\u0003\u0002*\u0005=RBAA\u0016\u0015\r\ticB\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA\u0019\u0003W\u0011Q\"\u001e8ta\u0016\u001c\u0017.\u00197ju\u0016$\u0017A\u0002;va2,G-\u0006\u0002\u00028A)Qb`A\u001d/A)R\"a\u000f%S9\u001a\u0004(\u0010\"H\u0019F36\fY3k_RL\u0018bAA\u001f\u000f\t9A+\u001e9mKFB\u0004f\u0001\u0003\u0002(\u0005AAo\\*ue&tw\r\u0006\u0002\u0002FA!\u0011qIA+\u001d\u0011\tI%!\u0015\u0011\u0007\u0005-s!\u0004\u0002\u0002N)\u0019\u0011qJ\u0005\u0002\rq\u0012xn\u001c;?\u0013\r\t\u0019fB\u0001\u0007!J,G-\u001a4\n\t\u0005]\u0013\u0011\f\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005Ms\u0001")
public interface Function18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14, T15 var15, T16 var16, T17 var17, T18 var18);

    public static /* synthetic */ Function1 curried$(Function18 $this) {
        return $this.curried();
    }

    default public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, Function1<T10, Function1<T11, Function1<T12, Function1<T13, Function1<T14, Function1<T15, Function1<T16, Function1<T17, Function1<T18, R>>>>>>>>>>>>>>>>>> curried() {
        return (Function1<Object, Function1> & Serializable)x1 -> ((Function17<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(Function17<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17, x18) -> this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17, x18)).curried();
    }

    public static /* synthetic */ Function1 tupled$(Function18 $this) {
        return $this.tupled();
    }

    default public Function1<Tuple18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>, R> tupled() {
        return (Function1<Tuple18, Object> & Serializable)x0$1 -> {
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
                return this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17, x18);
            }
            throw new MatchError((Object)null);
        };
    }

    public static /* synthetic */ String toString$(Function18 $this) {
        return $this.toString();
    }

    default public String toString() {
        return "<function18>";
    }

    public static void $init$(Function18 $this) {
    }
}

