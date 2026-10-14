/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongToDoubleFunction;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichLongToDoubleFunctionAsFunction1$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00054A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!I\u0001\u0006\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006S\u0001!\tA\u000b\u0005\u0006_\u0001!\t\u0001\r\u0005\b}\u0001\t\t\u0011\"\u0011@\u0011\u001d\u0019\u0005!!A\u0005B\u0011;q!\u0014\b\u0002\u0002#\u0005aJB\u0004\u000e\u001d\u0005\u0005\t\u0012A(\t\u000b%BA\u0011A*\t\u000bQCAQA+\t\u000feC\u0011\u0011!C\u00035\"9A\fCA\u0001\n\u000bi&a\t*jG\"duN\\4U_\u0012{WO\u00197f\rVt7\r^5p]\u0006\u001bh)\u001e8di&|g.\r\u0006\u0003\u001fA\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003#I\tQA[1wCbR!a\u0005\u000b\u0002\r\r|W\u000e]1u\u0015\u0005)\u0012!B:dC2\f7\u0001A\n\u0003\u0001a\u0001\"!\u0007\u000e\u000e\u0003QI!a\u0007\u000b\u0003\r\u0005s\u0017PV1m\u0003e\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195M_:<Gk\u001c#pk\ndWMR;oGRLwN\\!t\rVt7\r^5p]F\"C%\u001e8eKJd\u00170\u001b8h+\u0005q\u0002CA\u0010'\u001b\u0005\u0001#BA\u0011#\u0003!1WO\\2uS>t'BA\u0012%\u0003\u0011)H/\u001b7\u000b\u0003\u0015\nAA[1wC&\u0011q\u0005\t\u0002\u0015\u0019>tw\rV8E_V\u0014G.\u001a$v]\u000e$\u0018n\u001c8\u00025N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b'p]\u001e$v\u000eR8vE2,g)\u001e8di&|g.Q:Gk:\u001cG/[8oc\u0011\"SO\u001c3fe2L\u0018N\\4!\u0003\u0019a\u0014N\\5u}Q\u00111&\f\t\u0003Y\u0001i\u0011A\u0004\u0005\u0006]\r\u0001\rAH\u0001\u000bk:$WM\u001d7zS:<\u0017aB1t'\u000e\fG.Y\u000b\u0002cA!\u0011D\r\u001b8\u0013\t\u0019DCA\u0005Gk:\u001cG/[8ocA\u0011\u0011$N\u0005\u0003mQ\u0011A\u0001T8oOB\u0011\u0011\u0004O\u0005\u0003sQ\u0011a\u0001R8vE2,\u0007F\u0001\u0003<!\tIB(\u0003\u0002>)\t1\u0011N\u001c7j]\u0016\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002\u0001B\u0011\u0011$Q\u0005\u0003\u0005R\u00111!\u00138u\u0003\u0019)\u0017/^1mgR\u0011Q\t\u0013\t\u00033\u0019K!a\u0012\u000b\u0003\u000f\t{w\u000e\\3b]\"9\u0011JBA\u0001\u0002\u0004Q\u0015a\u0001=%cA\u0011\u0011dS\u0005\u0003\u0019R\u00111!\u00118z\u0003\r\u0012\u0016n\u00195M_:<Gk\u001c#pk\ndWMR;oGRLwN\\!t\rVt7\r^5p]F\u0002\"\u0001\f\u0005\u0014\u0005!\u0001\u0006CA\rR\u0013\t\u0011FC\u0001\u0004B]f\u0014VM\u001a\u000b\u0002\u001d\u0006\t\u0012m]*dC2\fG%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005E2\u0006\"B,\u000b\u0001\u0004Y\u0013!\u0002\u0013uQ&\u001c\bF\u0001\u0006<\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005}Z\u0006\"B,\f\u0001\u0004Y\u0013\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o)\tq\u0006\r\u0006\u0002F?\"9\u0011\nDA\u0001\u0002\u0004Q\u0005\"B,\r\u0001\u0004Y\u0003")
public final class RichLongToDoubleFunctionAsFunction1 {
    private final LongToDoubleFunction scala$compat$java8$functionConverterImpls$RichLongToDoubleFunctionAsFunction1$$underlying;

    public static boolean equals$extension(LongToDoubleFunction longToDoubleFunction, Object object) {
        return RichLongToDoubleFunctionAsFunction1$.MODULE$.equals$extension(longToDoubleFunction, object);
    }

    public static int hashCode$extension(LongToDoubleFunction longToDoubleFunction) {
        return RichLongToDoubleFunctionAsFunction1$.MODULE$.hashCode$extension(longToDoubleFunction);
    }

    public static Function1<Object, Object> asScala$extension(LongToDoubleFunction longToDoubleFunction) {
        return RichLongToDoubleFunctionAsFunction1$.MODULE$.asScala$extension(longToDoubleFunction);
    }

    public LongToDoubleFunction scala$compat$java8$functionConverterImpls$RichLongToDoubleFunctionAsFunction1$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichLongToDoubleFunctionAsFunction1$$underlying;
    }

    public Function1<Object, Object> asScala() {
        return RichLongToDoubleFunctionAsFunction1$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichLongToDoubleFunctionAsFunction1$$underlying());
    }

    public int hashCode() {
        return RichLongToDoubleFunctionAsFunction1$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichLongToDoubleFunctionAsFunction1$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichLongToDoubleFunctionAsFunction1$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichLongToDoubleFunctionAsFunction1$$underlying(), x$1);
    }

    public RichLongToDoubleFunctionAsFunction1(LongToDoubleFunction underlying) {
        this.scala$compat$java8$functionConverterImpls$RichLongToDoubleFunctionAsFunction1$$underlying = underlying;
    }
}

