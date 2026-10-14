/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Function;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsFunction$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i4A!\u0004\b\u0003/!aQ\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005=!I\u0001\u0007\u0001B\u0003\u0002\u0003\u0006Ia\b\u0005\u0006c\u0001!\tA\r\u0005\u0006o\u0001!\t\u0001\u000f\u0005\b\u000f\u0002\t\t\u0011\"\u0011I\u0011\u001da\u0005!!A\u0005B5;qa\u0015\b\u0002\u0002#\u0005AKB\u0004\u000e\u001d\u0005\u0005\t\u0012A+\t\u000bEBA\u0011A-\t\u000biCAQA.\t\u000f\u0019D\u0011\u0011!C\u0003O\"9q\u000eCA\u0001\n\u000b\u0001(a\u0006*jG\"4UO\\2uS>t\u0017'Q:Gk:\u001cG/[8o\u0015\ty\u0001#\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\t\t\"#A\u0003kCZ\f\u0007H\u0003\u0002\u0014)\u000511m\\7qCRT\u0011!F\u0001\u0006g\u000e\fG.Y\u0002\u0001+\rABEL\n\u0003\u0001e\u0001\"AG\u000e\u000e\u0003QI!\u0001\b\u000b\u0003\r\u0005s\u0017PV1m\u00035\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195Gk:\u001cG/[8oc\u0005\u001bh)\u001e8di&|g\u000e\n\u0013v]\u0012,'\u000f\\=j]\u001e,\u0012a\b\t\u00055\u0001\u0012S&\u0003\u0002\")\tIa)\u001e8di&|g.\r\t\u0003G\u0011b\u0001\u0001B\u0003&\u0001\t\u0007aEA\u0001U#\t9#\u0006\u0005\u0002\u001bQ%\u0011\u0011\u0006\u0006\u0002\b\u001d>$\b.\u001b8h!\tQ2&\u0003\u0002-)\t\u0019\u0011I\\=\u0011\u0005\rrC!B\u0018\u0001\u0005\u00041#!\u0001*\u0002\u001dN\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b$v]\u000e$\u0018n\u001c82\u0003N4UO\\2uS>tG\u0005J;oI\u0016\u0014H._5oO\u0002\na\u0001P5oSRtDCA\u001a6!\u0011!\u0004AI\u0017\u000e\u00039AQAN\u0002A\u0002}\t!\"\u001e8eKJd\u00170\u001b8h\u0003\u0019\t7OS1wCV\t\u0011\b\u0005\u0003;\u0003\njS\"A\u001e\u000b\u0005qj\u0014\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005yz\u0014\u0001B;uS2T\u0011\u0001Q\u0001\u0005U\u00064\u0018-\u0003\u0002Cw\tAa)\u001e8di&|g\u000e\u000b\u0002\u0005\tB\u0011!$R\u0005\u0003\rR\u0011a!\u001b8mS:,\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003%\u0003\"A\u0007&\n\u0005-#\"aA%oi\u00061Q-];bYN$\"AT)\u0011\u0005iy\u0015B\u0001)\u0015\u0005\u001d\u0011un\u001c7fC:DqA\u0015\u0004\u0002\u0002\u0003\u0007!&A\u0002yIE\nqCU5dQ\u001a+hn\u0019;j_:\f\u0014i\u001d$v]\u000e$\u0018n\u001c8\u0011\u0005QB1C\u0001\u0005W!\tQr+\u0003\u0002Y)\t1\u0011I\\=SK\u001a$\u0012\u0001V\u0001\u0011CNT\u0015M^1%Kb$XM\\:j_:,2\u0001X0b)\ti&\r\u0005\u0003;\u0003z\u0003\u0007CA\u0012`\t\u0015)#B1\u0001'!\t\u0019\u0013\rB\u00030\u0015\t\u0007a\u0005C\u0003d\u0015\u0001\u0007A-A\u0003%i\"L7\u000f\u0005\u00035\u0001y\u0003\u0007F\u0001\u0006E\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0016\u0007!dg\u000e\u0006\u0002IS\")1m\u0003a\u0001UB!A\u0007A6n!\t\u0019C\u000eB\u0003&\u0017\t\u0007a\u0005\u0005\u0002$]\u0012)qf\u0003b\u0001M\u0005\u0001R-];bYN$S\r\u001f;f]NLwN\\\u000b\u0004c^LHC\u0001:u)\tq5\u000fC\u0004S\u0019\u0005\u0005\t\u0019\u0001\u0016\t\u000b\rd\u0001\u0019A;\u0011\tQ\u0002a\u000f\u001f\t\u0003G]$Q!\n\u0007C\u0002\u0019\u0002\"aI=\u0005\u000b=b!\u0019\u0001\u0014")
public final class RichFunction1AsFunction<T, R> {
    private final Function1<T, R> scala$compat$java8$functionConverterImpls$RichFunction1AsFunction$$underlying;

    public static <T, R> boolean equals$extension(Function1<T, R> function1, Object object) {
        return RichFunction1AsFunction$.MODULE$.equals$extension(function1, object);
    }

    public static <T, R> int hashCode$extension(Function1<T, R> function1) {
        return RichFunction1AsFunction$.MODULE$.hashCode$extension(function1);
    }

    public static <T, R> Function<T, R> asJava$extension(Function1<T, R> function1) {
        return RichFunction1AsFunction$.MODULE$.asJava$extension(function1);
    }

    public Function1<T, R> scala$compat$java8$functionConverterImpls$RichFunction1AsFunction$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsFunction$$underlying;
    }

    public Function<T, R> asJava() {
        return RichFunction1AsFunction$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsFunction$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsFunction$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsFunction$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsFunction$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsFunction$$underlying(), x$1);
    }

    public RichFunction1AsFunction(Function1<T, R> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsFunction$$underlying = underlying;
    }
}

