/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntPredicate;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M2A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003+\u0001\u0011\u00051\u0006C\u00030\u0001\u0011\u0005\u0001G\u0001\nBg*\u000bg/Y%oiB\u0013X\rZ5dCR,'B\u0001\u0004\b\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(B\u0001\u0005\n\u0003\u0015Q\u0017M^19\u0015\tQ1\"\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002\u0019\u0005)1oY1mC\u000e\u00011c\u0001\u0001\u0010/A\u0011\u0001#F\u0007\u0002#)\u0011!cE\u0001\u0005Y\u0006twMC\u0001\u0015\u0003\u0011Q\u0017M^1\n\u0005Y\t\"AB(cU\u0016\u001cG\u000f\u0005\u0002\u0019;5\t\u0011D\u0003\u0002\u001b7\u0005Aa-\u001e8di&|gN\u0003\u0002\u001d'\u0005!Q\u000f^5m\u0013\tq\u0012D\u0001\u0007J]R\u0004&/\u001a3jG\u0006$X-\u0001\u0002tMB!\u0011E\t\u0013(\u001b\u0005Y\u0011BA\u0012\f\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0002\"K%\u0011ae\u0003\u0002\u0004\u0013:$\bCA\u0011)\u0013\tI3BA\u0004C_>dW-\u00198\u0002\rqJg.\u001b;?)\tac\u0006\u0005\u0002.\u00015\tQ\u0001C\u0003 \u0005\u0001\u0007\u0001%\u0001\u0003uKN$HCA\u00142\u0011\u0015\u00114\u00011\u0001%\u0003\tA\u0018\u0007")
public class AsJavaIntPredicate
implements IntPredicate {
    private final Function1<Object, Object> sf;

    @Override
    public IntPredicate and(IntPredicate x$1) {
        return IntPredicate.super.and(x$1);
    }

    @Override
    public IntPredicate negate() {
        return IntPredicate.super.negate();
    }

    @Override
    public IntPredicate or(IntPredicate x$1) {
        return IntPredicate.super.or(x$1);
    }

    @Override
    public boolean test(int x1) {
        return this.sf.apply$mcZI$sp(x1);
    }

    public AsJavaIntPredicate(Function1<Object, Object> sf) {
        this.sf = sf;
    }
}

