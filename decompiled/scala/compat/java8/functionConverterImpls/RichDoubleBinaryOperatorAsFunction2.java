/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleBinaryOperator;
import scala.Function2;
import scala.compat.java8.functionConverterImpls.RichDoubleBinaryOperatorAsFunction2$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!I\u0001\u0006\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006S\u0001!\tA\u000b\u0005\u0006_\u0001!\t\u0001\r\u0005\bw\u0001\t\t\u0011\"\u0011=\u0011\u001d\u0001\u0005!!A\u0005B\u0005;qA\u0013\b\u0002\u0002#\u00051JB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001'\t\u000b%BA\u0011\u0001)\t\u000bECAQ\u0001*\t\u000fYC\u0011\u0011!C\u0003/\"9\u0011\fCA\u0001\n\u000bQ&a\t*jG\"$u.\u001e2mK\nKg.\u0019:z\u001fB,'/\u0019;pe\u0006\u001bh)\u001e8di&|gN\r\u0006\u0003\u001fA\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003#I\tQA[1wCbR!a\u0005\u000b\u0002\r\r|W\u000e]1u\u0015\u0005)\u0012!B:dC2\f7\u0001A\n\u0003\u0001a\u0001\"!\u0007\u000e\u000e\u0003QI!a\u0007\u000b\u0003\r\u0005s\u0017PV1m\u0003e\u001b8-\u00197bI\r|W\u000e]1uI)\fg/\u0019\u001d%MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg\u0012\u0012\u0016n\u00195E_V\u0014G.\u001a\"j]\u0006\u0014\u0018p\u00149fe\u0006$xN]!t\rVt7\r^5p]J\"C%\u001e8eKJd\u00170\u001b8h+\u0005q\u0002CA\u0010'\u001b\u0005\u0001#BA\u0011#\u0003!1WO\\2uS>t'BA\u0012%\u0003\u0011)H/\u001b7\u000b\u0003\u0015\nAA[1wC&\u0011q\u0005\t\u0002\u0015\t>,(\r\\3CS:\f'/_(qKJ\fGo\u001c:\u00025N\u001c\u0017\r\\1%G>l\u0007/\u0019;%U\u00064\u0018\r\u000f\u0013gk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7tIIK7\r\u001b#pk\ndWMQ5oCJLx\n]3sCR|'/Q:Gk:\u001cG/[8oe\u0011\"SO\u001c3fe2L\u0018N\\4!\u0003\u0019a\u0014N\\5u}Q\u00111&\f\t\u0003Y\u0001i\u0011A\u0004\u0005\u0006]\r\u0001\rAH\u0001\u000bk:$WM\u001d7zS:<\u0017aB1t'\u000e\fG.Y\u000b\u0002cA)\u0011D\r\u001b5i%\u00111\u0007\u0006\u0002\n\rVt7\r^5p]J\u0002\"!G\u001b\n\u0005Y\"\"A\u0002#pk\ndW\r\u000b\u0002\u0005qA\u0011\u0011$O\u0005\u0003uQ\u0011a!\u001b8mS:,\u0017\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0003u\u0002\"!\u0007 \n\u0005}\"\"aA%oi\u00061Q-];bYN$\"AQ#\u0011\u0005e\u0019\u0015B\u0001#\u0015\u0005\u001d\u0011un\u001c7fC:DqA\u0012\u0004\u0002\u0002\u0003\u0007q)A\u0002yIE\u0002\"!\u0007%\n\u0005%#\"aA!os\u0006\u0019#+[2i\t>,(\r\\3CS:\f'/_(qKJ\fGo\u001c:Bg\u001a+hn\u0019;j_:\u0014\u0004C\u0001\u0017\t'\tAQ\n\u0005\u0002\u001a\u001d&\u0011q\n\u0006\u0002\u0007\u0003:L(+\u001a4\u0015\u0003-\u000b\u0011#Y:TG\u0006d\u0017\rJ3yi\u0016t7/[8o)\t\t4\u000bC\u0003U\u0015\u0001\u00071&A\u0003%i\"L7\u000f\u000b\u0002\u000bq\u0005\u0011\u0002.Y:i\u0007>$W\rJ3yi\u0016t7/[8o)\ta\u0004\fC\u0003U\u0017\u0001\u00071&\u0001\tfcV\fGn\u001d\u0013fqR,gn]5p]R\u00111,\u0018\u000b\u0003\u0005rCqA\u0012\u0007\u0002\u0002\u0003\u0007q\tC\u0003U\u0019\u0001\u00071\u0006")
public final class RichDoubleBinaryOperatorAsFunction2 {
    private final DoubleBinaryOperator scala$compat$java8$functionConverterImpls$RichDoubleBinaryOperatorAsFunction2$$underlying;

    public static boolean equals$extension(DoubleBinaryOperator doubleBinaryOperator, Object object) {
        return RichDoubleBinaryOperatorAsFunction2$.MODULE$.equals$extension(doubleBinaryOperator, object);
    }

    public static int hashCode$extension(DoubleBinaryOperator doubleBinaryOperator) {
        return RichDoubleBinaryOperatorAsFunction2$.MODULE$.hashCode$extension(doubleBinaryOperator);
    }

    public static Function2<Object, Object, Object> asScala$extension(DoubleBinaryOperator doubleBinaryOperator) {
        return RichDoubleBinaryOperatorAsFunction2$.MODULE$.asScala$extension(doubleBinaryOperator);
    }

    public DoubleBinaryOperator scala$compat$java8$functionConverterImpls$RichDoubleBinaryOperatorAsFunction2$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichDoubleBinaryOperatorAsFunction2$$underlying;
    }

    public Function2<Object, Object, Object> asScala() {
        return RichDoubleBinaryOperatorAsFunction2$.MODULE$.asScala$extension(this.scala$compat$java8$functionConverterImpls$RichDoubleBinaryOperatorAsFunction2$$underlying());
    }

    public int hashCode() {
        return RichDoubleBinaryOperatorAsFunction2$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichDoubleBinaryOperatorAsFunction2$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichDoubleBinaryOperatorAsFunction2$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichDoubleBinaryOperatorAsFunction2$$underlying(), x$1);
    }

    public RichDoubleBinaryOperatorAsFunction2(DoubleBinaryOperator underlying) {
        this.scala$compat$java8$functionConverterImpls$RichDoubleBinaryOperatorAsFunction2$$underlying = underlying;
    }
}

