/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.Function16;
import scala.MatchError;
import scala.Tuple17;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005=ca\u0002\u0004\b!\u0003\r\tA\u0003\u0005\u0006!\u0001!\t!\u0005\u0005\u0006+\u00011\tA\u0006\u0005\u0006o\u0002!\t\u0001\u001f\u0005\b\u0003O\u0001A\u0011AA\u0015\u0011\u001d\t)\u0004\u0001C!\u0003o\u0011!BR;oGRLwN\\\u00198\u0015\u0005A\u0011!B:dC2\f7\u0001A\u000b\u0014\u0017\u0015Rs\u0006N\u001d?\u0007\"k%k\u0016/bM.\u0004X/G\n\u0003\u00011\u0001\"!\u0004\b\u000e\u0003\u001dI!aD\u0004\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uIQ\t!\u0003\u0005\u0002\u000e'%\u0011Ac\u0002\u0002\u0005+:LG/A\u0003baBd\u0017\u0010\u0006\n\u0018E\u001db\u0013GN\u001eA\u000b*{E+\u00170dQ6\u0014\bC\u0001\r\u001a\u0019\u0001!aA\u0007\u0001\u0005\u0006\u0004Y\"!\u0001*\u0012\u0005qy\u0002CA\u0007\u001e\u0013\tqrAA\u0004O_RD\u0017N\\4\u0011\u00055\u0001\u0013BA\u0011\b\u0005\r\te.\u001f\u0005\u0006G\t\u0001\r\u0001J\u0001\u0003mF\u0002\"\u0001G\u0013\u0005\r\u0019\u0002\u0001R1\u0001\u001c\u0005\t!\u0016\u0007C\u0003)\u0005\u0001\u0007\u0011&\u0001\u0002weA\u0011\u0001D\u000b\u0003\u0007W\u0001A)\u0019A\u000e\u0003\u0005Q\u0013\u0004\"B\u0017\u0003\u0001\u0004q\u0013A\u0001<4!\tAr\u0006\u0002\u00041\u0001!\u0015\ra\u0007\u0002\u0003)NBQA\r\u0002A\u0002M\n!A\u001e\u001b\u0011\u0005a!DAB\u001b\u0001\u0011\u000b\u00071D\u0001\u0002Ui!)qG\u0001a\u0001q\u0005\u0011a/\u000e\t\u00031e\"aA\u000f\u0001\t\u0006\u0004Y\"A\u0001+6\u0011\u0015a$\u00011\u0001>\u0003\t1h\u0007\u0005\u0002\u0019}\u00111q\b\u0001EC\u0002m\u0011!\u0001\u0016\u001c\t\u000b\u0005\u0013\u0001\u0019\u0001\"\u0002\u0005Y<\u0004C\u0001\rD\t\u0019!\u0005\u0001#b\u00017\t\u0011Ak\u000e\u0005\u0006\r\n\u0001\raR\u0001\u0003mb\u0002\"\u0001\u0007%\u0005\r%\u0003\u0001R1\u0001\u001c\u0005\t!\u0006\bC\u0003L\u0005\u0001\u0007A*\u0001\u0002wsA\u0011\u0001$\u0014\u0003\u0007\u001d\u0002A)\u0019A\u000e\u0003\u0005QK\u0004\"\u0002)\u0003\u0001\u0004\t\u0016a\u0001<2aA\u0011\u0001D\u0015\u0003\u0007'\u0002A)\u0019A\u000e\u0003\u0007Q\u000b\u0004\u0007C\u0003V\u0005\u0001\u0007a+A\u0002wcE\u0002\"\u0001G,\u0005\ra\u0003\u0001R1\u0001\u001c\u0005\r!\u0016'\r\u0005\u00065\n\u0001\raW\u0001\u0004mF\u0012\u0004C\u0001\r]\t\u0019i\u0006\u0001#b\u00017\t\u0019A+\r\u001a\t\u000b}\u0013\u0001\u0019\u00011\u0002\u0007Y\f4\u0007\u0005\u0002\u0019C\u00121!\r\u0001EC\u0002m\u00111\u0001V\u00194\u0011\u0015!'\u00011\u0001f\u0003\r1\u0018\u0007\u000e\t\u00031\u0019$aa\u001a\u0001\t\u0006\u0004Y\"a\u0001+2i!)\u0011N\u0001a\u0001U\u0006\u0019a/M\u001b\u0011\u0005aYGA\u00027\u0001\u0011\u000b\u00071DA\u0002UcUBQA\u001c\u0002A\u0002=\f1A^\u00197!\tA\u0002\u000f\u0002\u0004r\u0001!\u0015\ra\u0007\u0002\u0004)F2\u0004\"B:\u0003\u0001\u0004!\u0018a\u0001<2oA\u0011\u0001$\u001e\u0003\u0007m\u0002A)\u0019A\u000e\u0003\u0007Q\u000bt'A\u0004dkJ\u0014\u0018.\u001a3\u0016\u0003e\u0004B!\u0004>%y&\u00111p\u0002\u0002\n\rVt7\r^5p]F\u0002B!\u0004>*{B!QB\u001f\u0018\u007f!\u0011i!pM@\u0011\u000b5Q\b(!\u0001\u0011\u000b5QX(a\u0001\u0011\u000b5Q()!\u0002\u0011\u000b5Qx)a\u0002\u0011\u000b5QH*!\u0003\u0011\u000b5Q\u0018+a\u0003\u0011\u000b5Qh+!\u0004\u0011\u000b5Q8,a\u0004\u0011\u000b5Q\b-!\u0005\u0011\u000b5QX-a\u0005\u0011\u000b5Q(.!\u0006\u0011\u000b5Qx.a\u0006\u0011\t5QHo\u0006\u0015\u0004\u0007\u0005m\u0001\u0003BA\u000f\u0003Gi!!a\b\u000b\u0007\u0005\u0005r!\u0001\u0006b]:|G/\u0019;j_:LA!!\n\u0002 \tiQO\\:qK\u000eL\u0017\r\\5{K\u0012\fa\u0001^;qY\u0016$WCAA\u0016!\u0015i!0!\f\u0018!Qi\u0011q\u0006\u0013*]MBTHQ$M#Z[\u0006-\u001a6pi&\u0019\u0011\u0011G\u0004\u0003\u000fQ+\b\u000f\\32o!\u001aA!a\u0007\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!\u000f\u0011\t\u0005m\u0012\u0011\n\b\u0005\u0003{\t)\u0005E\u0002\u0002@\u001di!!!\u0011\u000b\u0007\u0005\r\u0013\"\u0001\u0004=e>|GOP\u0005\u0004\u0003\u000f:\u0011A\u0002)sK\u0012,g-\u0003\u0003\u0002L\u00055#AB*ue&twMC\u0002\u0002H\u001d\u0001")
public interface Function17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14, T15 var15, T16 var16, T17 var17);

    public static /* synthetic */ Function1 curried$(Function17 $this) {
        return $this.curried();
    }

    default public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, Function1<T10, Function1<T11, Function1<T12, Function1<T13, Function1<T14, Function1<T15, Function1<T16, Function1<T17, R>>>>>>>>>>>>>>>>> curried() {
        return (Function1<Object, Function1> & Serializable)x1 -> ((Function16<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(Function16<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17) -> this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17)).curried();
    }

    public static /* synthetic */ Function1 tupled$(Function17 $this) {
        return $this.tupled();
    }

    default public Function1<Tuple17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>, R> tupled() {
        return (Function1<Tuple17, Object> & Serializable)x0$1 -> {
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
                return this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17);
            }
            throw new MatchError((Object)null);
        };
    }

    public static /* synthetic */ String toString$(Function17 $this) {
        return $this.toString();
    }

    default public String toString() {
        return "<function17>";
    }

    public static void $init$(Function17 $this) {
    }
}

