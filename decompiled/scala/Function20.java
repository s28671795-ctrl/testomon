/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.Function19;
import scala.MatchError;
import scala.Tuple20;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Mda\u0002\u0004\b!\u0003\r\tA\u0003\u0005\u0006!\u0001!\t!\u0005\u0005\u0006+\u00011\tA\u0006\u0005\b\u0003\u001b\u0001A\u0011AA\b\u0011\u001d\tY\u0005\u0001C\u0001\u0003\u001bBq!!\u0017\u0001\t\u0003\nYF\u0001\u0006Gk:\u001cG/[8oeAR\u0011\u0001C\u0001\u0006g\u000e\fG.Y\u0002\u0001+]YQEK\u00185sy\u001a\u0005*\u0014*X9\u000647\u000e];{\u007f\u0006%\u0011d\u0005\u0002\u0001\u0019A\u0011QBD\u0007\u0002\u000f%\u0011qb\u0002\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%)\u0005\u0011\u0002CA\u0007\u0014\u0013\t!rA\u0001\u0003V]&$\u0018!B1qa2LHCF\f#O1\ndg\u000f!F\u0015>#\u0016LX2i[J<H0a\u0001\u0011\u0005aIB\u0002\u0001\u0003\u00075\u0001!)\u0019A\u000e\u0003\u0003I\u000b\"\u0001H\u0010\u0011\u00055i\u0012B\u0001\u0010\b\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!\u0004\u0011\n\u0005\u0005:!aA!os\")1E\u0001a\u0001I\u0005\u0011a/\r\t\u00031\u0015\"aA\n\u0001\t\u0006\u0004Y\"A\u0001+2\u0011\u0015A#\u00011\u0001*\u0003\t1(\u0007\u0005\u0002\u0019U\u001111\u0006\u0001EC\u0002m\u0011!\u0001\u0016\u001a\t\u000b5\u0012\u0001\u0019\u0001\u0018\u0002\u0005Y\u001c\u0004C\u0001\r0\t\u0019\u0001\u0004\u0001#b\u00017\t\u0011Ak\r\u0005\u0006e\t\u0001\raM\u0001\u0003mR\u0002\"\u0001\u0007\u001b\u0005\rU\u0002\u0001R1\u0001\u001c\u0005\t!F\u0007C\u00038\u0005\u0001\u0007\u0001(\u0001\u0002wkA\u0011\u0001$\u000f\u0003\u0007u\u0001A)\u0019A\u000e\u0003\u0005Q+\u0004\"\u0002\u001f\u0003\u0001\u0004i\u0014A\u0001<7!\tAb\b\u0002\u0004@\u0001!\u0015\ra\u0007\u0002\u0003)ZBQ!\u0011\u0002A\u0002\t\u000b!A^\u001c\u0011\u0005a\u0019EA\u0002#\u0001\u0011\u000b\u00071D\u0001\u0002Uo!)aI\u0001a\u0001\u000f\u0006\u0011a\u000f\u000f\t\u00031!#a!\u0013\u0001\t\u0006\u0004Y\"A\u0001+9\u0011\u0015Y%\u00011\u0001M\u0003\t1\u0018\b\u0005\u0002\u0019\u001b\u00121a\n\u0001EC\u0002m\u0011!\u0001V\u001d\t\u000bA\u0013\u0001\u0019A)\u0002\u0007Y\f\u0004\u0007\u0005\u0002\u0019%\u001211\u000b\u0001EC\u0002m\u00111\u0001V\u00191\u0011\u0015)&\u00011\u0001W\u0003\r1\u0018'\r\t\u00031]#a\u0001\u0017\u0001\t\u0006\u0004Y\"a\u0001+2c!)!L\u0001a\u00017\u0006\u0019a/\r\u001a\u0011\u0005aaFAB/\u0001\u0011\u000b\u00071DA\u0002UcIBQa\u0018\u0002A\u0002\u0001\f1A^\u00194!\tA\u0012\r\u0002\u0004c\u0001!\u0015\ra\u0007\u0002\u0004)F\u001a\u0004\"\u00023\u0003\u0001\u0004)\u0017a\u0001<2iA\u0011\u0001D\u001a\u0003\u0007O\u0002A)\u0019A\u000e\u0003\u0007Q\u000bD\u0007C\u0003j\u0005\u0001\u0007!.A\u0002wcU\u0002\"\u0001G6\u0005\r1\u0004\u0001R1\u0001\u001c\u0005\r!\u0016'\u000e\u0005\u0006]\n\u0001\ra\\\u0001\u0004mF2\u0004C\u0001\rq\t\u0019\t\b\u0001#b\u00017\t\u0019A+\r\u001c\t\u000bM\u0014\u0001\u0019\u0001;\u0002\u0007Y\ft\u0007\u0005\u0002\u0019k\u00121a\u000f\u0001EC\u0002m\u00111\u0001V\u00198\u0011\u0015A(\u00011\u0001z\u0003\r1\u0018\u0007\u000f\t\u00031i$aa\u001f\u0001\t\u0006\u0004Y\"a\u0001+2q!)QP\u0001a\u0001}\u0006\u0019a/M\u001d\u0011\u0005ayHaBA\u0001\u0001!\u0015\ra\u0007\u0002\u0004)FJ\u0004bBA\u0003\u0005\u0001\u0007\u0011qA\u0001\u0004mJ\u0002\u0004c\u0001\r\u0002\n\u00119\u00111\u0002\u0001\t\u0006\u0004Y\"a\u0001+3a\u000591-\u001e:sS\u0016$WCAA\t!\u0019i\u00111\u0003\u0013\u0002\u0018%\u0019\u0011QC\u0004\u0003\u0013\u0019+hn\u0019;j_:\f\u0004CB\u0007\u0002\u0014%\nI\u0002\u0005\u0004\u000e\u0003'q\u00131\u0004\t\u0007\u001b\u0005M1'!\b\u0011\r5\t\u0019\u0002OA\u0010!\u0019i\u00111C\u001f\u0002\"A1Q\"a\u0005C\u0003G\u0001b!DA\n\u000f\u0006\u0015\u0002CB\u0007\u0002\u00141\u000b9\u0003\u0005\u0004\u000e\u0003'\t\u0016\u0011\u0006\t\u0007\u001b\u0005Ma+a\u000b\u0011\r5\t\u0019bWA\u0017!\u0019i\u00111\u00031\u00020A1Q\"a\u0005f\u0003c\u0001b!DA\nU\u0006M\u0002CB\u0007\u0002\u0014=\f)\u0004\u0005\u0004\u000e\u0003'!\u0018q\u0007\t\u0007\u001b\u0005M\u00110!\u000f\u0011\r5\t\u0019B`A\u001e!\u0019i\u00111CA\u0004/!\u001a1!a\u0010\u0011\t\u0005\u0005\u0013qI\u0007\u0003\u0003\u0007R1!!\u0012\b\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003\u0013\n\u0019EA\u0007v]N\u0004XmY5bY&TX\rZ\u0001\u0007iV\u0004H.\u001a3\u0016\u0005\u0005=\u0003CB\u0007\u0002\u0014\u0005Es\u0003\u0005\r\u000e\u0003'\"\u0013FL\u001a9{\t;E*\u0015,\\A\u0016Tw\u000e^=\u007f\u0003\u000fI1!!\u0016\b\u0005\u001d!V\u000f\u001d7feAB3\u0001BA \u0003!!xn\u0015;sS:<GCAA/!\u0011\ty&!\u001c\u000f\t\u0005\u0005\u0014\u0011\u000e\t\u0004\u0003G:QBAA3\u0015\r\t9'C\u0001\u0007yI|w\u000e\u001e \n\u0007\u0005-t!\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003_\n\tH\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003W:\u0001")
public interface Function20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9, T10 var10, T11 var11, T12 var12, T13 var13, T14 var14, T15 var15, T16 var16, T17 var17, T18 var18, T19 var19, T20 var20);

    public static /* synthetic */ Function1 curried$(Function20 $this) {
        return $this.curried();
    }

    default public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, Function1<T10, Function1<T11, Function1<T12, Function1<T13, Function1<T14, Function1<T15, Function1<T16, Function1<T17, Function1<T18, Function1<T19, Function1<T20, R>>>>>>>>>>>>>>>>>>>> curried() {
        return (Function1<Object, Function1> & Serializable)x1 -> ((Function19<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(Function19<Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17, x18, x19, x20) -> this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17, x18, x19, x20)).curried();
    }

    public static /* synthetic */ Function1 tupled$(Function20 $this) {
        return $this.tupled();
    }

    default public Function1<Tuple20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>, R> tupled() {
        return (Function1<Tuple20, Object> & Serializable)x0$1 -> {
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
                return this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17, x18, x19, x20);
            }
            throw new MatchError((Object)null);
        };
    }

    public static /* synthetic */ String toString$(Function20 $this) {
        return $this.toString();
    }

    default public String toString() {
        return "<function20>";
    }

    public static void $init$(Function20 $this) {
    }
}

