/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.Function7;
import scala.MatchError;
import scala.Tuple8;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E4qAB\u0004\u0011\u0002\u0007\u0005!\u0002C\u0003\u0011\u0001\u0011\u0005\u0011\u0003C\u0003\u0016\u0001\u0019\u0005a\u0003C\u0003K\u0001\u0011\u00051\nC\u0003^\u0001\u0011\u0005a\fC\u0003e\u0001\u0011\u0005SMA\u0005Gk:\u001cG/[8oq)\t\u0001\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0015-)#f\f\u001b:}\rC\u0015d\u0005\u0002\u0001\u0019A\u0011QBD\u0007\u0002\u000f%\u0011qb\u0002\u0002\u0007\u0003:L(+\u001a4\u0002\r\u0011Jg.\u001b;%)\u0005\u0011\u0002CA\u0007\u0014\u0013\t!rA\u0001\u0003V]&$\u0018!B1qa2LH#C\f#O1\ndg\u000f!F!\tA\u0012\u0004\u0004\u0001\u0005\ri\u0001AQ1\u0001\u001c\u0005\u0005\u0011\u0016C\u0001\u000f !\tiQ$\u0003\u0002\u001f\u000f\t9aj\u001c;iS:<\u0007CA\u0007!\u0013\t\tsAA\u0002B]fDQa\t\u0002A\u0002\u0011\n!A^\u0019\u0011\u0005a)CA\u0002\u0014\u0001\u0011\u000b\u00071D\u0001\u0002Uc!)\u0001F\u0001a\u0001S\u0005\u0011aO\r\t\u00031)\"aa\u000b\u0001\t\u0006\u0004Y\"A\u0001+3\u0011\u0015i#\u00011\u0001/\u0003\t18\u0007\u0005\u0002\u0019_\u00111\u0001\u0007\u0001EC\u0002m\u0011!\u0001V\u001a\t\u000bI\u0012\u0001\u0019A\u001a\u0002\u0005Y$\u0004C\u0001\r5\t\u0019)\u0004\u0001#b\u00017\t\u0011A\u000b\u000e\u0005\u0006o\t\u0001\r\u0001O\u0001\u0003mV\u0002\"\u0001G\u001d\u0005\ri\u0002\u0001R1\u0001\u001c\u0005\t!V\u0007C\u0003=\u0005\u0001\u0007Q(\u0001\u0002wmA\u0011\u0001D\u0010\u0003\u0007\u007f\u0001A)\u0019A\u000e\u0003\u0005Q3\u0004\"B!\u0003\u0001\u0004\u0011\u0015A\u0001<8!\tA2\t\u0002\u0004E\u0001!\u0015\ra\u0007\u0002\u0003)^BQA\u0012\u0002A\u0002\u001d\u000b!A\u001e\u001d\u0011\u0005aAEAB%\u0001\u0011\u000b\u00071D\u0001\u0002Uq\u000591-\u001e:sS\u0016$W#\u0001'\u0011\t5iEeT\u0005\u0003\u001d\u001e\u0011\u0011BR;oGRLwN\\\u0019\u0011\t5i\u0015\u0006\u0015\t\u0005\u001b5s\u0013\u000b\u0005\u0003\u000e\u001bN\u0012\u0006\u0003B\u0007NqM\u0003B!D'>)B!Q\"\u0014\"V!\u0011iQjR\f)\u0005\r9\u0006C\u0001-\\\u001b\u0005I&B\u0001.\b\u0003)\tgN\\8uCRLwN\\\u0005\u00039f\u0013Q\"\u001e8ta\u0016\u001c\u0017.\u00197ju\u0016$\u0017A\u0002;va2,G-F\u0001`!\u0011iQ\nY\f\u0011\u00155\tG%\u000b\u00184qu\u0012u)\u0003\u0002c\u000f\t1A+\u001e9mKbB#\u0001B,\u0002\u0011Q|7\u000b\u001e:j]\u001e$\u0012A\u001a\t\u0003O:t!\u0001\u001b7\u0011\u0005%<Q\"\u00016\u000b\u0005-L\u0011A\u0002\u001fs_>$h(\u0003\u0002n\u000f\u00051\u0001K]3eK\u001aL!a\u001c9\u0003\rM#(/\u001b8h\u0015\tiw\u0001")
public interface Function8<T1, T2, T3, T4, T5, T6, T7, T8, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8);

    public static /* synthetic */ Function1 curried$(Function8 $this) {
        return $this.curried();
    }

    default public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, R>>>>>>>> curried() {
        return (Function1<Object, Function1> & Serializable)x1 -> ((Function7<Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(Function7<Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(x2, x3, x4, x5, x6, x7, x8) -> this.apply(x1, x2, x3, x4, x5, x6, x7, x8)).curried();
    }

    public static /* synthetic */ Function1 tupled$(Function8 $this) {
        return $this.tupled();
    }

    default public Function1<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>, R> tupled() {
        return (Function1<Tuple8, Object> & Serializable)x0$1 -> {
            if (x0$1 != null) {
                Object x1 = x0$1._1();
                Object x2 = x0$1._2();
                Object x3 = x0$1._3();
                Object x4 = x0$1._4();
                Object x5 = x0$1._5();
                Object x6 = x0$1._6();
                Object x7 = x0$1._7();
                Object x8 = x0$1._8();
                return this.apply(x1, x2, x3, x4, x5, x6, x7, x8);
            }
            throw new MatchError((Object)null);
        };
    }

    public static /* synthetic */ String toString$(Function8 $this) {
        return $this.toString();
    }

    default public String toString() {
        return "<function8>";
    }

    public static void $init$(Function8 $this) {
    }
}

