/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleToIntFunction;
import scala.Function1;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M2A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003+\u0001\u0011\u00051\u0006C\u00030\u0001\u0011\u0005\u0001GA\rBg*\u000bg/\u0019#pk\ndW\rV8J]R4UO\\2uS>t'B\u0001\u0004\b\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(B\u0001\u0005\n\u0003\u0015Q\u0017M^19\u0015\tQ1\"\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002\u0019\u0005)1oY1mC\u000e\u00011c\u0001\u0001\u0010/A\u0011\u0001#F\u0007\u0002#)\u0011!cE\u0001\u0005Y\u0006twMC\u0001\u0015\u0003\u0011Q\u0017M^1\n\u0005Y\t\"AB(cU\u0016\u001cG\u000f\u0005\u0002\u0019;5\t\u0011D\u0003\u0002\u001b7\u0005Aa-\u001e8di&|gN\u0003\u0002\u001d'\u0005!Q\u000f^5m\u0013\tq\u0012DA\nE_V\u0014G.\u001a+p\u0013:$h)\u001e8di&|g.\u0001\u0002tMB!\u0011E\t\u0013(\u001b\u0005Y\u0011BA\u0012\f\u0005%1UO\\2uS>t\u0017\u0007\u0005\u0002\"K%\u0011ae\u0003\u0002\u0007\t>,(\r\\3\u0011\u0005\u0005B\u0013BA\u0015\f\u0005\rIe\u000e^\u0001\u0007y%t\u0017\u000e\u001e \u0015\u00051r\u0003CA\u0017\u0001\u001b\u0005)\u0001\"B\u0010\u0003\u0001\u0004\u0001\u0013AC1qa2L\u0018i]%oiR\u0011q%\r\u0005\u0006e\r\u0001\r\u0001J\u0001\u0003qF\u0002")
public class AsJavaDoubleToIntFunction
implements DoubleToIntFunction {
    private final Function1<Object, Object> sf;

    @Override
    public int applyAsInt(double x1) {
        return this.sf.apply$mcID$sp(x1);
    }

    public AsJavaDoubleToIntFunction(Function1<Object, Object> sf) {
        this.sf = sf;
    }
}

