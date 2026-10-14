/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Function1;
import scala.Function8;
import scala.MatchError;
import scala.Tuple9;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005]4qAB\u0004\u0011\u0002\u0007\u0005!\u0002C\u0003\u0011\u0001\u0011\u0005\u0011\u0003C\u0003\u0016\u0001\u0019\u0005a\u0003C\u0003P\u0001\u0011\u0005\u0001\u000bC\u0003d\u0001\u0011\u0005A\rC\u0003k\u0001\u0011\u00053NA\u0005Gk:\u001cG/[8os)\t\u0001\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0017-)#f\f\u001b:}\rCU*G\n\u0003\u00011\u0001\"!\u0004\b\u000e\u0003\u001dI!aD\u0004\u0003\r\u0005s\u0017PU3g\u0003\u0019!\u0013N\\5uIQ\t!\u0003\u0005\u0002\u000e'%\u0011Ac\u0002\u0002\u0005+:LG/A\u0003baBd\u0017\u0010\u0006\u0006\u0018E\u001db\u0013GN\u001eA\u000b*\u0003\"\u0001G\r\r\u0001\u00111!\u0004\u0001CC\u0002m\u0011\u0011AU\t\u00039}\u0001\"!D\u000f\n\u0005y9!a\u0002(pi\"Lgn\u001a\t\u0003\u001b\u0001J!!I\u0004\u0003\u0007\u0005s\u0017\u0010C\u0003$\u0005\u0001\u0007A%\u0001\u0002wcA\u0011\u0001$\n\u0003\u0007M\u0001A)\u0019A\u000e\u0003\u0005Q\u000b\u0004\"\u0002\u0015\u0003\u0001\u0004I\u0013A\u0001<3!\tA\"\u0006\u0002\u0004,\u0001!\u0015\ra\u0007\u0002\u0003)JBQ!\f\u0002A\u00029\n!A^\u001a\u0011\u0005ayCA\u0002\u0019\u0001\u0011\u000b\u00071D\u0001\u0002Ug!)!G\u0001a\u0001g\u0005\u0011a\u000f\u000e\t\u00031Q\"a!\u000e\u0001\t\u0006\u0004Y\"A\u0001+5\u0011\u00159$\u00011\u00019\u0003\t1X\u0007\u0005\u0002\u0019s\u00111!\b\u0001EC\u0002m\u0011!\u0001V\u001b\t\u000bq\u0012\u0001\u0019A\u001f\u0002\u0005Y4\u0004C\u0001\r?\t\u0019y\u0004\u0001#b\u00017\t\u0011AK\u000e\u0005\u0006\u0003\n\u0001\rAQ\u0001\u0003m^\u0002\"\u0001G\"\u0005\r\u0011\u0003\u0001R1\u0001\u001c\u0005\t!v\u0007C\u0003G\u0005\u0001\u0007q)\u0001\u0002wqA\u0011\u0001\u0004\u0013\u0003\u0007\u0013\u0002A)\u0019A\u000e\u0003\u0005QC\u0004\"B&\u0003\u0001\u0004a\u0015A\u0001<:!\tAR\n\u0002\u0004O\u0001!\u0015\ra\u0007\u0002\u0003)f\nqaY;se&,G-F\u0001R!\u0011i!\u000b\n+\n\u0005M;!!\u0003$v]\u000e$\u0018n\u001c82!\u0011i!+K+\u0011\t5\u0011fF\u0016\t\u0005\u001bI\u001bt\u000b\u0005\u0003\u000e%bB\u0006\u0003B\u0007S{e\u0003B!\u0004*C5B!QBU$\\!\u0011i!\u000bT\f)\u0005\ri\u0006C\u00010b\u001b\u0005y&B\u00011\b\u0003)\tgN\\8uCRLwN\\\u0005\u0003E~\u0013Q\"\u001e8ta\u0016\u001c\u0017.\u00197ju\u0016$\u0017A\u0002;va2,G-F\u0001f!\u0011i!KZ\f\u0011\u001759G%\u000b\u00184qu\u0012u\tT\u0005\u0003Q\u001e\u0011a\u0001V;qY\u0016L\u0004F\u0001\u0003^\u0003!!xn\u0015;sS:<G#\u00017\u0011\u00055$hB\u00018s!\tyw!D\u0001q\u0015\t\t\u0018\"\u0001\u0004=e>|GOP\u0005\u0003g\u001e\ta\u0001\u0015:fI\u00164\u0017BA;w\u0005\u0019\u0019FO]5oO*\u00111o\u0002")
public interface Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> {
    public R apply(T1 var1, T2 var2, T3 var3, T4 var4, T5 var5, T6 var6, T7 var7, T8 var8, T9 var9);

    public static /* synthetic */ Function1 curried$(Function9 $this) {
        return $this.curried();
    }

    default public Function1<T1, Function1<T2, Function1<T3, Function1<T4, Function1<T5, Function1<T6, Function1<T7, Function1<T8, Function1<T9, R>>>>>>>>> curried() {
        return (Function1<Object, Function1> & Serializable)x1 -> ((Function8<Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(Function8<Object, Object, Object, Object, Object, Object, Object, Object, Object> & Serializable)(x2, x3, x4, x5, x6, x7, x8, x9) -> this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9)).curried();
    }

    public static /* synthetic */ Function1 tupled$(Function9 $this) {
        return $this.tupled();
    }

    default public Function1<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>, R> tupled() {
        return (Function1<Tuple9, Object> & Serializable)x0$1 -> {
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
                return this.apply(x1, x2, x3, x4, x5, x6, x7, x8, x9);
            }
            throw new MatchError((Object)null);
        };
    }

    public static /* synthetic */ String toString$(Function9 $this) {
        return $this.toString();
    }

    default public String toString() {
        return "<function9>";
    }

    public static void $init$(Function9 $this) {
    }
}

