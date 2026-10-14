/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleBinaryOperator;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichFunction2AsDoubleBinaryOperator$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!IA\u0005\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006K\u0001!\tA\n\u0005\u0006W\u0001!\t\u0001\f\u0005\bw\u0001\t\t\u0011\"\u0011=\u0011\u001d\u0001\u0005!!A\u0005B\u0005;qA\u0013\b\u0002\u0002#\u00051JB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001'\t\u000b\u0015BA\u0011\u0001)\t\u000bECAQ\u0001*\t\u000fYC\u0011\u0011!C\u0003/\"9\u0011\fCA\u0001\n\u000bQ&a\t*jG\"4UO\\2uS>t''Q:E_V\u0014G.\u001a\"j]\u0006\u0014\u0018p\u00149fe\u0006$xN\u001d\u0006\u0003\u001fA\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003#I\tQA[1wCbR!a\u0005\u000b\u0002\r\r|W\u000e]1u\u0015\u0005)\u0012!B:dC2\f7\u0001A\n\u0003\u0001a\u0001\"!\u0007\u000e\u000e\u0003QI!a\u0007\u000b\u0003\r\u0005s\u0017PV1m\u0003e\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195Gk:\u001cG/[8oe\u0005\u001bHi\\;cY\u0016\u0014\u0015N\\1ss>\u0003XM]1u_J$C%\u001e8eKJd\u00170\u001b8h+\u0005q\u0002#B\r C\u0005\n\u0013B\u0001\u0011\u0015\u0005%1UO\\2uS>t'\u0007\u0005\u0002\u001aE%\u00111\u0005\u0006\u0002\u0007\t>,(\r\\3\u00025N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b$v]\u000e$\u0018n\u001c83\u0003N$u.\u001e2mK\nKg.\u0019:z\u001fB,'/\u0019;pe\u0012\"SO\u001c3fe2L\u0018N\\4!\u0003\u0019a\u0014N\\5u}Q\u0011q%\u000b\t\u0003Q\u0001i\u0011A\u0004\u0005\u0006U\r\u0001\rAH\u0001\u000bk:$WM\u001d7zS:<\u0017AB1t\u0015\u00064\u0018-F\u0001.!\tqS'D\u00010\u0015\t\u0001\u0014'\u0001\u0005gk:\u001cG/[8o\u0015\t\u00114'\u0001\u0003vi&d'\"\u0001\u001b\u0002\t)\fg/Y\u0005\u0003m=\u0012A\u0003R8vE2,')\u001b8bef|\u0005/\u001a:bi>\u0014\bF\u0001\u00039!\tI\u0012(\u0003\u0002;)\t1\u0011N\u001c7j]\u0016\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002{A\u0011\u0011DP\u0005\u0003\u007fQ\u00111!\u00138u\u0003\u0019)\u0017/^1mgR\u0011!)\u0012\t\u00033\rK!\u0001\u0012\u000b\u0003\u000f\t{w\u000e\\3b]\"9aIBA\u0001\u0002\u00049\u0015a\u0001=%cA\u0011\u0011\u0004S\u0005\u0003\u0013R\u00111!\u00118z\u0003\r\u0012\u0016n\u00195Gk:\u001cG/[8oe\u0005\u001bHi\\;cY\u0016\u0014\u0015N\\1ss>\u0003XM]1u_J\u0004\"\u0001\u000b\u0005\u0014\u0005!i\u0005CA\rO\u0013\tyEC\u0001\u0004B]f\u0014VM\u001a\u000b\u0002\u0017\u0006\u0001\u0012m\u001d&bm\u0006$S\r\u001f;f]NLwN\u001c\u000b\u0003[MCQ\u0001\u0016\u0006A\u0002\u001d\nQ\u0001\n;iSND#A\u0003\u001d\u0002%!\f7\u000f[\"pI\u0016$S\r\u001f;f]NLwN\u001c\u000b\u0003yaCQ\u0001V\u0006A\u0002\u001d\n\u0001#Z9vC2\u001cH%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005mkFC\u0001\"]\u0011\u001d1E\"!AA\u0002\u001dCQ\u0001\u0016\u0007A\u0002\u001d\u0002")
public final class RichFunction2AsDoubleBinaryOperator {
    private final Function2<Object, Object, Object> scala$compat$java8$functionConverterImpls$RichFunction2AsDoubleBinaryOperator$$underlying;

    public static boolean equals$extension(Function2 function2, Object object) {
        return RichFunction2AsDoubleBinaryOperator$.MODULE$.equals$extension(function2, object);
    }

    public static int hashCode$extension(Function2 function2) {
        return RichFunction2AsDoubleBinaryOperator$.MODULE$.hashCode$extension(function2);
    }

    public static DoubleBinaryOperator asJava$extension(Function2 function2) {
        return RichFunction2AsDoubleBinaryOperator$.MODULE$.asJava$extension(function2);
    }

    public Function2<Object, Object, Object> scala$compat$java8$functionConverterImpls$RichFunction2AsDoubleBinaryOperator$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction2AsDoubleBinaryOperator$$underlying;
    }

    public DoubleBinaryOperator asJava() {
        return RichFunction2AsDoubleBinaryOperator$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsDoubleBinaryOperator$$underlying());
    }

    public int hashCode() {
        return RichFunction2AsDoubleBinaryOperator$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsDoubleBinaryOperator$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction2AsDoubleBinaryOperator$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction2AsDoubleBinaryOperator$$underlying(), x$1);
    }

    public RichFunction2AsDoubleBinaryOperator(Function2<Object, Object, Object> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction2AsDoubleBinaryOperator$$underlying = underlying;
    }
}

