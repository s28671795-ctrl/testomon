/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.Function5;
import scala.MatchError;
import scala.Tuple6;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00154qAB\u0004\u0011\u0002\u0007\u0005!\u0002C\u0003\u0011\u0001\u0011\u0005\u0011\u0003C\u0003\u0016\u0001\u0019\u0005a\u0003C\u0003A\u0001\u0011\u0005\u0011\tC\u0003R\u0001\u0011\u0005!\u000bC\u0003Y\u0001\u0011\u0005\u0013LA\u0005Gk:\u001cG/[8om)\t\u0001\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0011-)#f\f\u001b:}e\u0019\"\u0001\u0001\u0007\u0011\u00055qQ\"A\u0004\n\u0005=9!AB!osJ+g-\u0001\u0004%S:LG\u000f\n\u000b\u0002%A\u0011QbE\u0005\u0003)\u001d\u0011A!\u00168ji\u0006)\u0011\r\u001d9msR9qCI\u0014-cYZ\u0004C\u0001\r\u001a\u0019\u0001!aA\u0007\u0001\u0005\u0006\u0004Y\"!\u0001*\u0012\u0005qy\u0002CA\u0007\u001e\u0013\tqrAA\u0004O_RD\u0017N\\4\u0011\u00055\u0001\u0013BA\u0011\b\u0005\r\te.\u001f\u0005\u0006G\t\u0001\r\u0001J\u0001\u0003mF\u0002\"\u0001G\u0013\u0005\r\u0019\u0002\u0001R1\u0001\u001c\u0005\t!\u0016\u0007C\u0003)\u0005\u0001\u0007\u0011&\u0001\u0002weA\u0011\u0001D\u000b\u0003\u0007W\u0001A)\u0019A\u000e\u0003\u0005Q\u0013\u0004\"B\u0017\u0003\u0001\u0004q\u0013A\u0001<4!\tAr\u0006\u0002\u00041\u0001!\u0015\ra\u0007\u0002\u0003)NBQA\r\u0002A\u0002M\n!A\u001e\u001b\u0011\u0005a!DAB\u001b\u0001\u0011\u000b\u00071D\u0001\u0002Ui!)qG\u0001a\u0001q\u0005\u0011a/\u000e\t\u00031e\"aA\u000f\u0001\t\u0006\u0004Y\"A\u0001+6\u0011\u0015a$\u00011\u0001>\u0003\t1h\u0007\u0005\u0002\u0019}\u00111q\b\u0001EC\u0002m\u0011!\u0001\u0016\u001c\u0002\u000f\r,(O]5fIV\t!\t\u0005\u0003\u000e\u0007\u0012*\u0015B\u0001#\b\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0003\u000e\u0007&2\u0005\u0003B\u0007D]\u001d\u0003B!D\"4\u0011B!Qb\u0011\u001dJ!\u0011i1)P\f)\u0005\rY\u0005C\u0001'P\u001b\u0005i%B\u0001(\b\u0003)\tgN\\8uCRLwN\\\u0005\u0003!6\u0013Q\"\u001e8ta\u0016\u001c\u0017.\u00197ju\u0016$\u0017A\u0002;va2,G-F\u0001T!\u0011i1\tV\f\u0011\u00115)F%\u000b\u00184quJ!AV\u0004\u0003\rQ+\b\u000f\\37Q\t!1*\u0001\u0005u_N#(/\u001b8h)\u0005Q\u0006CA.c\u001d\ta\u0006\r\u0005\u0002^\u000f5\taL\u0003\u0002`\u0013\u00051AH]8pizJ!!Y\u0004\u0002\rA\u0013X\rZ3g\u0013\t\u0019GM\u0001\u0004TiJLgn\u001a\u0006\u0003C\u001e\u0001")
public interface Function6<T1, T2, T3, T4, T5, T6, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6);

    public static /* synthetic */ Function1 curried$(Function6 $this) {
        return $this.curried();
    }

    default public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, R>>>>>> curried() {
        return (Function1<Object, Function1> & Serializable)x1 -> ((Function5<Object, Object, Object, Object, Object, Object> & Serializable)(Function5<Object, Object, Object, Object, Object, Object> & Serializable)(x2, x3, x4, x5, x6) -> this.apply(x1, x2, x3, x4, x5, x6)).curried();
    }

    public static /* synthetic */ Function1 tupled$(Function6 $this) {
        return $this.tupled();
    }

    default public Function1<Tuple6<T1, T2, T3, T4, T5, T6>, R> tupled() {
        return (Function1<Tuple6, Object> & Serializable)x0$1 -> {
            if (x0$1 != null) {
                Object x1 = x0$1._1();
                Object x2 = x0$1._2();
                Object x3 = x0$1._3();
                Object x4 = x0$1._4();
                Object x5 = x0$1._5();
                Object x6 = x0$1._6();
                return this.apply(x1, x2, x3, x4, x5, x6);
            }
            throw new MatchError((Object)null);
        };
    }

    public static /* synthetic */ String toString$(Function6 $this) {
        return $this.toString();
    }

    default public String toString() {
        return "<function6>";
    }

    public static void $init$(Function6 $this) {
    }
}

