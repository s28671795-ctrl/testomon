/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleUnaryOperator;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsDoubleUnaryOperator$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!IA\u0005\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006K\u0001!\tA\n\u0005\u0006W\u0001!\t\u0001\f\u0005\bw\u0001\t\t\u0011\"\u0011=\u0011\u001d\u0001\u0005!!A\u0005B\u0005;qA\u0013\b\u0002\u0002#\u00051JB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001'\t\u000b\u0015BA\u0011\u0001)\t\u000bECAQ\u0001*\t\u000fYC\u0011\u0011!C\u0003/\"9\u0011\fCA\u0001\n\u000bQ&A\t*jG\"4UO\\2uS>t\u0017'Q:E_V\u0014G.Z+oCJLx\n]3sCR|'O\u0003\u0002\u0010!\u00051b-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7O\u0003\u0002\u0012%\u0005)!.\u0019<bq)\u00111\u0003F\u0001\u0007G>l\u0007/\u0019;\u000b\u0003U\tQa]2bY\u0006\u001c\u0001a\u0005\u0002\u00011A\u0011\u0011DG\u0007\u0002)%\u00111\u0004\u0006\u0002\u0007\u0003:Lh+\u00197\u00021N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b$v]\u000e$\u0018n\u001c82\u0003N$u.\u001e2mKVs\u0017M]=Pa\u0016\u0014\u0018\r^8sI\u0011*h\u000eZ3sYfLgnZ\u000b\u0002=A!\u0011dH\u0011\"\u0013\t\u0001CCA\u0005Gk:\u001cG/[8ocA\u0011\u0011DI\u0005\u0003GQ\u0011a\u0001R8vE2,\u0017!W:dC2\fGeY8na\u0006$HE[1wCb\"c-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7\u000f\n*jG\"4UO\\2uS>t\u0017'Q:E_V\u0014G.Z+oCJLx\n]3sCR|'\u000f\n\u0013v]\u0012,'\u000f\\=j]\u001e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002(SA\u0011\u0001\u0006A\u0007\u0002\u001d!)!f\u0001a\u0001=\u0005QQO\u001c3fe2L\u0018N\\4\u0002\r\u0005\u001c(*\u0019<b+\u0005i\u0003C\u0001\u00186\u001b\u0005y#B\u0001\u00192\u0003!1WO\\2uS>t'B\u0001\u001a4\u0003\u0011)H/\u001b7\u000b\u0003Q\nAA[1wC&\u0011ag\f\u0002\u0014\t>,(\r\\3V]\u0006\u0014\u0018p\u00149fe\u0006$xN\u001d\u0015\u0003\ta\u0002\"!G\u001d\n\u0005i\"\"AB5oY&tW-\u0001\u0005iCND7i\u001c3f)\u0005i\u0004CA\r?\u0013\tyDCA\u0002J]R\fa!Z9vC2\u001cHC\u0001\"F!\tI2)\u0003\u0002E)\t9!i\\8mK\u0006t\u0007b\u0002$\u0007\u0003\u0003\u0005\raR\u0001\u0004q\u0012\n\u0004CA\rI\u0013\tIECA\u0002B]f\f!EU5dQ\u001a+hn\u0019;j_:\f\u0014i\u001d#pk\ndW-\u00168bef|\u0005/\u001a:bi>\u0014\bC\u0001\u0015\t'\tAQ\n\u0005\u0002\u001a\u001d&\u0011q\n\u0006\u0002\u0007\u0003:L(+\u001a4\u0015\u0003-\u000b\u0001#Y:KCZ\fG%\u001a=uK:\u001c\u0018n\u001c8\u0015\u00055\u001a\u0006\"\u0002+\u000b\u0001\u00049\u0013!\u0002\u0013uQ&\u001c\bF\u0001\u00069\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005qB\u0006\"\u0002+\f\u0001\u00049\u0013\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o)\tYV\f\u0006\u0002C9\"9a\tDA\u0001\u0002\u00049\u0005\"\u0002+\r\u0001\u00049\u0003")
public final class RichFunction1AsDoubleUnaryOperator {
    private final Function1<Object, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleUnaryOperator$$underlying;

    public static boolean equals$extension(Function1 function1, Object object) {
        return RichFunction1AsDoubleUnaryOperator$.MODULE$.equals$extension(function1, object);
    }

    public static int hashCode$extension(Function1 function1) {
        return RichFunction1AsDoubleUnaryOperator$.MODULE$.hashCode$extension(function1);
    }

    public static DoubleUnaryOperator asJava$extension(Function1 function1) {
        return RichFunction1AsDoubleUnaryOperator$.MODULE$.asJava$extension(function1);
    }

    public Function1<Object, Object> scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleUnaryOperator$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleUnaryOperator$$underlying;
    }

    public DoubleUnaryOperator asJava() {
        return RichFunction1AsDoubleUnaryOperator$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleUnaryOperator$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsDoubleUnaryOperator$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleUnaryOperator$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsDoubleUnaryOperator$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleUnaryOperator$$underlying(), x$1);
    }

    public RichFunction1AsDoubleUnaryOperator(Function1<Object, Object> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsDoubleUnaryOperator$$underlying = underlying;
    }
}

