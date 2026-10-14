/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongConsumer;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005M2A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003+\u0001\u0011\u00051\u0006C\u00030\u0001\u0011\u0005\u0001G\u0001\nBg*\u000bg/\u0019'p]\u001e\u001cuN\\:v[\u0016\u0014(B\u0001\u0004\b\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(B\u0001\u0005\n\u0003\u0015Q\u0017M^19\u0015\tQ1\"\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002\u0019\u0005)1oY1mC\u000e\u00011c\u0001\u0001\u0010/A\u0011\u0001#F\u0007\u0002#)\u0011!cE\u0001\u0005Y\u0006twMC\u0001\u0015\u0003\u0011Q\u0017M^1\n\u0005Y\t\"AB(cU\u0016\u001cG\u000f\u0005\u0002\u0019;5\t\u0011D\u0003\u0002\u001b7\u0005Aa-\u001e8di&|gN\u0003\u0002\u001d'\u0005!Q\u000f^5m\u0013\tq\u0012D\u0001\u0007M_:<7i\u001c8tk6,'/\u0001\u0002tMB!\u0011E\t\u0013(\u001b\u0005Y\u0011BA\u0012\f\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0002\"K%\u0011ae\u0003\u0002\u0005\u0019>tw\r\u0005\u0002\"Q%\u0011\u0011f\u0003\u0002\u0005+:LG/\u0001\u0004=S:LGO\u0010\u000b\u0003Y9\u0002\"!\f\u0001\u000e\u0003\u0015AQa\b\u0002A\u0002\u0001\na!Y2dKB$HCA\u00142\u0011\u0015\u00114\u00011\u0001%\u0003\tA\u0018\u0007")
public class AsJavaLongConsumer
implements LongConsumer {
    private final Function1<Object, BoxedUnit> sf;

    @Override
    public LongConsumer andThen(LongConsumer x$1) {
        return LongConsumer.super.andThen(x$1);
    }

    @Override
    public void accept(long x1) {
        this.sf.apply$mcVJ$sp(x1);
    }

    public AsJavaLongConsumer(Function1<Object, BoxedUnit> sf) {
        this.sf = sf;
    }
}

