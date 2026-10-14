/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.MatchError;
import scala.Tuple3;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M3qAB\u0004\u0011\u0002\u0007\u0005!\u0002C\u0003\u0011\u0001\u0011\u0005\u0011\u0003C\u0003\u0016\u0001\u0019\u0005a\u0003C\u00032\u0001\u0011\u0005!\u0007C\u0003@\u0001\u0011\u0005\u0001\tC\u0003G\u0001\u0011\u0005sIA\u0005Gk:\u001cG/[8og)\t\u0001\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u000b-)#fL\r\u0014\u0005\u0001a\u0001CA\u0007\u000f\u001b\u00059\u0011BA\b\b\u0005\u0019\te.\u001f*fM\u00061A%\u001b8ji\u0012\"\u0012A\u0005\t\u0003\u001bMI!\u0001F\u0004\u0003\tUs\u0017\u000e^\u0001\u0006CB\u0004H.\u001f\u000b\u0005/\t:C\u0006\u0005\u0002\u001931\u0001AA\u0002\u000e\u0001\t\u000b\u00071DA\u0001S#\tar\u0004\u0005\u0002\u000e;%\u0011ad\u0002\u0002\b\u001d>$\b.\u001b8h!\ti\u0001%\u0003\u0002\"\u000f\t\u0019\u0011I\\=\t\u000b\r\u0012\u0001\u0019\u0001\u0013\u0002\u0005Y\f\u0004C\u0001\r&\t\u00191\u0003\u0001#b\u00017\t\u0011A+\r\u0005\u0006Q\t\u0001\r!K\u0001\u0003mJ\u0002\"\u0001\u0007\u0016\u0005\r-\u0002\u0001R1\u0001\u001c\u0005\t!&\u0007C\u0003.\u0005\u0001\u0007a&\u0001\u0002wgA\u0011\u0001d\f\u0003\u0007a\u0001A)\u0019A\u000e\u0003\u0005Q\u001b\u0014aB2veJLW\rZ\u000b\u0002gA!Q\u0002\u000e\u00137\u0013\t)tAA\u0005Gk:\u001cG/[8ocA!Q\u0002N\u00158!\u0011iAGL\f)\u0005\rI\u0004C\u0001\u001e>\u001b\u0005Y$B\u0001\u001f\b\u0003)\tgN\\8uCRLwN\\\u0005\u0003}m\u0012Q\"\u001e8ta\u0016\u001c\u0017.\u00197ju\u0016$\u0017A\u0002;va2,G-F\u0001B!\u0011iAGQ\f\u0011\u000b5\u0019E%\u000b\u0018\n\u0005\u0011;!A\u0002+va2,7\u0007\u000b\u0002\u0005s\u0005AAo\\*ue&tw\rF\u0001I!\tI\u0005K\u0004\u0002K\u001dB\u00111jB\u0007\u0002\u0019*\u0011Q*C\u0001\u0007yI|w\u000e\u001e \n\u0005=;\u0011A\u0002)sK\u0012,g-\u0003\u0002R%\n11\u000b\u001e:j]\u001eT!aT\u0004")
public interface Function3<T1, T2, T3, R> {
    public R apply(T1 var1, T2 var2, T3 var3);

    public static /* synthetic */ Function1 curried$(Function3 $this) {
        return $this.curried();
    }

    default public Function1<T1, Function1<T2, Function1<T3, R>>> curried() {
        return (Function1<Object, Function1> & Serializable)x1 -> (Function1<Object, Function1> & Serializable)x2 -> (Function1<Object, Object> & Serializable)x3 -> this.apply(x1, x2, x3);
    }

    public static /* synthetic */ Function1 tupled$(Function3 $this) {
        return $this.tupled();
    }

    default public Function1<Tuple3<T1, T2, T3>, R> tupled() {
        return (Function1<Tuple3, Object> & Serializable)x0$1 -> {
            if (x0$1 != null) {
                Object x1 = x0$1._1();
                Object x2 = x0$1._2();
                Object x3 = x0$1._3();
                return this.apply(x1, x2, x3);
            }
            throw new MatchError((Object)null);
        };
    }

    public static /* synthetic */ String toString$(Function3 $this) {
        return $this.toString();
    }

    default public String toString() {
        return "<function3>";
    }

    public static void $init$(Function3 $this) {
    }
}

