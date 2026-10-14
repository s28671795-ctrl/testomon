/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ObjLongConsumer;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichFunction2AsObjLongConsumer$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005]4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!I1\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006i\u0001!\t!\u000e\u0005\u0006u\u0001!\ta\u000f\u0005\b\u0015\u0002\t\t\u0011\"\u0011L\u0011\u001dy\u0005!!A\u0005BA;qA\u0016\b\u0002\u0002#\u0005qKB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001-\t\u000bQBA\u0011\u0001/\t\u000buCAQ\u00010\t\u000f\u001dD\u0011\u0011!C\u0003Q\"9a\u000eCA\u0001\n\u000by'A\b*jG\"4UO\\2uS>t''Q:PE*duN\\4D_:\u001cX/\\3s\u0015\ty\u0001#\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\t\t\"#A\u0003kCZ\f\u0007H\u0003\u0002\u0014)\u000511m\\7qCRT\u0011!F\u0001\u0006g\u000e\fG.Y\u0002\u0001+\tABe\u0005\u0002\u00013A\u0011!dG\u0007\u0002)%\u0011A\u0004\u0006\u0002\u0007\u0003:Lh+\u00197\u0002)N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b$v]\u000e$\u0018n\u001c83\u0003N|%M\u001b'p]\u001e\u001cuN\\:v[\u0016\u0014H\u0005J;oI\u0016\u0014H._5oOV\tq\u0004E\u0003\u001bA\tj\u0003'\u0003\u0002\")\tIa)\u001e8di&|gN\r\t\u0003G\u0011b\u0001\u0001B\u0003&\u0001\t\u0007aEA\u0001U#\t9#\u0006\u0005\u0002\u001bQ%\u0011\u0011\u0006\u0006\u0002\b\u001d>$\b.\u001b8h!\tQ2&\u0003\u0002-)\t\u0019\u0011I\\=\u0011\u0005iq\u0013BA\u0018\u0015\u0005\u0011auN\\4\u0011\u0005i\t\u0014B\u0001\u001a\u0015\u0005\u0011)f.\u001b;\u0002+N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b$v]\u000e$\u0018n\u001c83\u0003N|%M\u001b'p]\u001e\u001cuN\\:v[\u0016\u0014H\u0005J;oI\u0016\u0014H._5oO\u0002\na\u0001P5oSRtDC\u0001\u001c9!\r9\u0004AI\u0007\u0002\u001d!)\u0011h\u0001a\u0001?\u0005QQO\u001c3fe2L\u0018N\\4\u0002\r\u0005\u001c(*\u0019<b+\u0005a\u0004cA\u001fEE5\taH\u0003\u0002@\u0001\u0006Aa-\u001e8di&|gN\u0003\u0002B\u0005\u0006!Q\u000f^5m\u0015\u0005\u0019\u0015\u0001\u00026bm\u0006L!!\u0012 \u0003\u001f=\u0013'\u000eT8oO\u000e{gn];nKJD#\u0001B$\u0011\u0005iA\u0015BA%\u0015\u0005\u0019Ig\u000e\\5oK\u0006A\u0001.Y:i\u0007>$W\rF\u0001M!\tQR*\u0003\u0002O)\t\u0019\u0011J\u001c;\u0002\r\u0015\fX/\u00197t)\t\tF\u000b\u0005\u0002\u001b%&\u00111\u000b\u0006\u0002\b\u0005>|G.Z1o\u0011\u001d)f!!AA\u0002)\n1\u0001\u001f\u00132\u0003y\u0011\u0016n\u00195Gk:\u001cG/[8oe\u0005\u001bxJ\u00196M_:<7i\u001c8tk6,'\u000f\u0005\u00028\u0011M\u0011\u0001\"\u0017\t\u00035iK!a\u0017\u000b\u0003\r\u0005s\u0017PU3g)\u00059\u0016\u0001E1t\u0015\u00064\u0018\rJ3yi\u0016t7/[8o+\ty&\r\u0006\u0002aGB\u0019Q\bR1\u0011\u0005\r\u0012G!B\u0013\u000b\u0005\u00041\u0003\"\u00023\u000b\u0001\u0004)\u0017!\u0002\u0013uQ&\u001c\bcA\u001c\u0001C\"\u0012!bR\u0001\u0013Q\u0006\u001c\bnQ8eK\u0012*\u0007\u0010^3og&|g.\u0006\u0002j[R\u00111J\u001b\u0005\u0006I.\u0001\ra\u001b\t\u0004o\u0001a\u0007CA\u0012n\t\u0015)3B1\u0001'\u0003A)\u0017/^1mg\u0012*\u0007\u0010^3og&|g.\u0006\u0002qmR\u0011\u0011o\u001d\u000b\u0003#JDq!\u0016\u0007\u0002\u0002\u0003\u0007!\u0006C\u0003e\u0019\u0001\u0007A\u000fE\u00028\u0001U\u0004\"a\t<\u0005\u000b\u0015b!\u0019\u0001\u0014")
public final class RichFunction2AsObjLongConsumer<T> {
    private final Function2<T, Object, BoxedUnit> scala$compat$java8$functionConverterImpls$RichFunction2AsObjLongConsumer$$underlying;

    public static <T> boolean equals$extension(Function2<T, Object, BoxedUnit> function2, Object object) {
        return RichFunction2AsObjLongConsumer$.MODULE$.equals$extension(function2, object);
    }

    public static <T> int hashCode$extension(Function2<T, Object, BoxedUnit> function2) {
        return RichFunction2AsObjLongConsumer$.MODULE$.hashCode$extension(function2);
    }

    public static <T> ObjLongConsumer<T> asJava$extension(Function2<T, Object, BoxedUnit> function2) {
        return RichFunction2AsObjLongConsumer$.MODULE$.asJava$extension(function2);
    }

    public Function2<T, Object, BoxedUnit> scala$compat$java8$functionConverterImpls$RichFunction2AsObjLongConsumer$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction2AsObjLongConsumer$$underlying;
    }

    public ObjLongConsumer<T> asJava() {
        return RichFunction2AsObjLongConsumer$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsObjLongConsumer$$underlying());
    }

    public int hashCode() {
        return RichFunction2AsObjLongConsumer$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsObjLongConsumer$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction2AsObjLongConsumer$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsObjLongConsumer$$underlying(), x$1);
    }

    public RichFunction2AsObjLongConsumer(Function2<T, Object, BoxedUnit> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction2AsObjLongConsumer$$underlying = underlying;
    }
}

