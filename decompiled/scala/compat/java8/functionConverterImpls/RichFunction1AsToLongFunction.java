/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ToLongFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsToLongFunction$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Q4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!I\u0001\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006c\u0001!\tA\r\u0005\u0006o\u0001!\t\u0001\u000f\u0005\b\u000f\u0002\t\t\u0011\"\u0011I\u0011\u001da\u0005!!A\u0005B5;qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000bEBA\u0011A-\t\u000biCAQA.\t\u000f\u0011D\u0011\u0011!C\u0003K\"91\u000eCA\u0001\n\u000ba'!\b*jG\"4UO\\2uS>t\u0017'Q:U_2{gn\u001a$v]\u000e$\u0018n\u001c8\u000b\u0005=\u0001\u0012A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005E\u0011\u0012!\u00026bm\u0006D$BA\n\u0015\u0003\u0019\u0019w.\u001c9bi*\tQ#A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005a!3C\u0001\u0001\u001a!\tQ2$D\u0001\u0015\u0013\taBC\u0001\u0004B]f4\u0016\r\\\u0001Tg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007NR;oGRLwN\\\u0019BgR{Gj\u001c8h\rVt7\r^5p]\u0012\"SO\u001c3fe2L\u0018N\\4\u0016\u0003}\u0001BA\u0007\u0011#[%\u0011\u0011\u0005\u0006\u0002\n\rVt7\r^5p]F\u0002\"a\t\u0013\r\u0001\u0011)Q\u0005\u0001b\u0001M\t\tA+\u0005\u0002(UA\u0011!\u0004K\u0005\u0003SQ\u0011qAT8uQ&tw\r\u0005\u0002\u001bW%\u0011A\u0006\u0006\u0002\u0004\u0003:L\bC\u0001\u000e/\u0013\tyCC\u0001\u0003M_:<\u0017\u0001V:dC2\fGeY8na\u0006$HE[1wCb\"c-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7\u000f\n*jG\"4UO\\2uS>t\u0017'Q:U_2{gn\u001a$v]\u000e$\u0018n\u001c8%IUtG-\u001a:ms&tw\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005M*\u0004c\u0001\u001b\u0001E5\ta\u0002C\u00037\u0007\u0001\u0007q$\u0001\u0006v]\u0012,'\u000f\\=j]\u001e\fa!Y:KCZ\fW#A\u001d\u0011\u0007i\n%%D\u0001<\u0015\taT(\u0001\u0005gk:\u001cG/[8o\u0015\tqt(\u0001\u0003vi&d'\"\u0001!\u0002\t)\fg/Y\u0005\u0003\u0005n\u0012a\u0002V8M_:<g)\u001e8di&|g\u000e\u000b\u0002\u0005\tB\u0011!$R\u0005\u0003\rR\u0011a!\u001b8mS:,\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003%\u0003\"A\u0007&\n\u0005-#\"aA%oi\u00061Q-];bYN$\"AT)\u0011\u0005iy\u0015B\u0001)\u0015\u0005\u001d\u0011un\u001c7fC:DqA\u0015\u0004\u0002\u0002\u0003\u0007!&A\u0002yIE\nQDU5dQ\u001a+hn\u0019;j_:\f\u0014i\u001d+p\u0019>twMR;oGRLwN\u001c\t\u0003i!\u0019\"\u0001\u0003,\u0011\u0005i9\u0016B\u0001-\u0015\u0005\u0019\te.\u001f*fMR\tA+\u0001\tbg*\u000bg/\u0019\u0013fqR,gn]5p]V\u0011Al\u0018\u000b\u0003;\u0002\u00042AO!_!\t\u0019s\fB\u0003&\u0015\t\u0007a\u0005C\u0003b\u0015\u0001\u0007!-A\u0003%i\"L7\u000fE\u00025\u0001yC#A\u0003#\u0002%!\f7\u000f[\"pI\u0016$S\r\u001f;f]NLwN\\\u000b\u0003M*$\"\u0001S4\t\u000b\u0005\\\u0001\u0019\u00015\u0011\u0007Q\u0002\u0011\u000e\u0005\u0002$U\u0012)Qe\u0003b\u0001M\u0005\u0001R-];bYN$S\r\u001f;f]NLwN\\\u000b\u0003[N$\"A\u001c9\u0015\u00059{\u0007b\u0002*\r\u0003\u0003\u0005\rA\u000b\u0005\u0006C2\u0001\r!\u001d\t\u0004i\u0001\u0011\bCA\u0012t\t\u0015)CB1\u0001'\u0001")
public final class RichFunction1AsToLongFunction<T> {
    private final Function1<T, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsToLongFunction$$underlying;

    public static <T> boolean equals$extension(Function1<T, Object> function1, Object object) {
        return RichFunction1AsToLongFunction$.MODULE$.equals$extension(function1, object);
    }

    public static <T> int hashCode$extension(Function1<T, Object> function1) {
        return RichFunction1AsToLongFunction$.MODULE$.hashCode$extension(function1);
    }

    public static <T> ToLongFunction<T> asJava$extension(Function1<T, Object> function1) {
        return RichFunction1AsToLongFunction$.MODULE$.asJava$extension(function1);
    }

    public Function1<T, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsToLongFunction$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsToLongFunction$$underlying;
    }

    public ToLongFunction<T> asJava() {
        return RichFunction1AsToLongFunction$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsToLongFunction$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsToLongFunction$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsToLongFunction$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsToLongFunction$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsToLongFunction$$underlying(), x$1);
    }

    public RichFunction1AsToLongFunction(Function1<T, Object> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsToLongFunction$$underlying = underlying;
    }
}

