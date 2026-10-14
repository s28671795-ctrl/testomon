/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntConsumer;
import scala.Function1;
import scala.compat.java8.functionConverterImpls.RichFunction1AsIntConsumer$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005y3A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!Iq\u0005\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006Q\u0001!\t!\u000b\u0005\u0006]\u0001!\ta\f\u0005\b}\u0001\t\t\u0011\"\u0011@\u0011\u001d\u0001\u0005!!A\u0005B\u0005;qA\u0013\b\u0002\u0002#\u00051JB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001'\t\u000b!BA\u0011\u0001)\t\u000bECAQ\u0001*\t\u000fYC\u0011\u0011!C\u0003/\"9\u0011\fCA\u0001\n\u000bQ&A\u0007*jG\"4UO\\2uS>t\u0017'Q:J]R\u001cuN\\:v[\u0016\u0014(BA\b\u0011\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(BA\t\u0013\u0003\u0015Q\u0017M^19\u0015\t\u0019B#\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002+\u0005)1oY1mC\u000e\u00011C\u0001\u0001\u0019!\tI\"$D\u0001\u0015\u0013\tYBC\u0001\u0004B]f4\u0016\r\\\u0001Qg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007NR;oGRLwN\\\u0019Bg&sGoQ8ogVlWM\u001d\u0013%k:$WM\u001d7zS:<W#\u0001\u0010\u0011\tey\u0012\u0005J\u0005\u0003AQ\u0011\u0011BR;oGRLwN\\\u0019\u0011\u0005e\u0011\u0013BA\u0012\u0015\u0005\rIe\u000e\u001e\t\u00033\u0015J!A\n\u000b\u0003\tUs\u0017\u000e^\u0001Rg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\b\n4v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:%%&\u001c\u0007NR;oGRLwN\\\u0019Bg&sGoQ8ogVlWM\u001d\u0013%k:$WM\u001d7zS:<\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0003U1\u0002\"a\u000b\u0001\u000e\u00039AQ!L\u0002A\u0002y\t!\"\u001e8eKJd\u00170\u001b8h\u0003\u0019\t7OS1wCV\t\u0001\u0007\u0005\u00022q5\t!G\u0003\u00024i\u0005Aa-\u001e8di&|gN\u0003\u00026m\u0005!Q\u000f^5m\u0015\u00059\u0014\u0001\u00026bm\u0006L!!\u000f\u001a\u0003\u0017%sGoQ8ogVlWM\u001d\u0015\u0003\tm\u0002\"!\u0007\u001f\n\u0005u\"\"AB5oY&tW-\u0001\u0005iCND7i\u001c3f)\u0005\t\u0013AB3rk\u0006d7\u000f\u0006\u0002C\u000bB\u0011\u0011dQ\u0005\u0003\tR\u0011qAQ8pY\u0016\fg\u000eC\u0004G\r\u0005\u0005\t\u0019A$\u0002\u0007a$\u0013\u0007\u0005\u0002\u001a\u0011&\u0011\u0011\n\u0006\u0002\u0004\u0003:L\u0018A\u0007*jG\"4UO\\2uS>t\u0017'Q:J]R\u001cuN\\:v[\u0016\u0014\bCA\u0016\t'\tAQ\n\u0005\u0002\u001a\u001d&\u0011q\n\u0006\u0002\u0007\u0003:L(+\u001a4\u0015\u0003-\u000b\u0001#Y:KCZ\fG%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005A\u001a\u0006\"\u0002+\u000b\u0001\u0004Q\u0013!\u0002\u0013uQ&\u001c\bF\u0001\u0006<\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005}B\u0006\"\u0002+\f\u0001\u0004Q\u0013\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o)\tYV\f\u0006\u0002C9\"9a\tDA\u0001\u0002\u00049\u0005\"\u0002+\r\u0001\u0004Q\u0003")
public final class RichFunction1AsIntConsumer {
    private final Function1<Object, BoxedUnit> scala$compat$java8$functionConverterImpls$RichFunction1AsIntConsumer$$underlying;

    public static boolean equals$extension(Function1 function1, Object object) {
        return RichFunction1AsIntConsumer$.MODULE$.equals$extension(function1, object);
    }

    public static int hashCode$extension(Function1 function1) {
        return RichFunction1AsIntConsumer$.MODULE$.hashCode$extension(function1);
    }

    public static IntConsumer asJava$extension(Function1 function1) {
        return RichFunction1AsIntConsumer$.MODULE$.asJava$extension(function1);
    }

    public Function1<Object, BoxedUnit> scala$compat$java8$functionConverterImpls$RichFunction1AsIntConsumer$$underlying() {
        return this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntConsumer$$underlying;
    }

    public IntConsumer asJava() {
        return RichFunction1AsIntConsumer$.MODULE$.asJava$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntConsumer$$underlying());
    }

    public int hashCode() {
        return RichFunction1AsIntConsumer$.MODULE$.hashCode$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntConsumer$$underlying());
    }

    public boolean equals(Object x$1) {
        return RichFunction1AsIntConsumer$.MODULE$.equals$extension(this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntConsumer$$underlying(), x$1);
    }

    public RichFunction1AsIntConsumer(Function1<Object, BoxedUnit> underlying) {
        this.scala$compat$java8$functionConverterImpls$RichFunction1AsIntConsumer$$underlying = underlying;
    }
}

