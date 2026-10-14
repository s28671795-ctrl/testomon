/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Consumer;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005q2A\u0001B\u0003\u0001\u001d!AA\u0006\u0001B\u0001B\u0003%Q\u0006C\u00034\u0001\u0011\u0005A\u0007C\u00039\u0001\u0011\u0005\u0011H\u0001\bBg*\u000bg/Y\"p]N,X.\u001a:\u000b\u0005\u00199\u0011A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005!I\u0011!\u00026bm\u0006D$B\u0001\u0006\f\u0003\u0019\u0019w.\u001c9bi*\tA\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005=\u00113c\u0001\u0001\u00111A\u0011\u0011CF\u0007\u0002%)\u00111\u0003F\u0001\u0005Y\u0006twMC\u0001\u0016\u0003\u0011Q\u0017M^1\n\u0005]\u0011\"AB(cU\u0016\u001cG\u000fE\u0002\u001a=\u0001j\u0011A\u0007\u0006\u00037q\t\u0001BZ;oGRLwN\u001c\u0006\u0003;Q\tA!\u001e;jY&\u0011qD\u0007\u0002\t\u0007>t7/^7feB\u0011\u0011E\t\u0007\u0001\t\u0015\u0019\u0003A1\u0001%\u0005\u0005!\u0016CA\u0013*!\t1s%D\u0001\f\u0013\tA3BA\u0004O_RD\u0017N\\4\u0011\u0005\u0019R\u0013BA\u0016\f\u0005\r\te._\u0001\u0003g\u001a\u0004BA\n\u0018!a%\u0011qf\u0003\u0002\n\rVt7\r^5p]F\u0002\"AJ\u0019\n\u0005IZ!\u0001B+oSR\fa\u0001P5oSRtDCA\u001b8!\r1\u0004\u0001I\u0007\u0002\u000b!)AF\u0001a\u0001[\u00051\u0011mY2faR$\"\u0001\r\u001e\t\u000bm\u001a\u0001\u0019\u0001\u0011\u0002\u0005a\f\u0004")
public class AsJavaConsumer<T>
implements Consumer<T> {
    private final Function1<T, BoxedUnit> sf;

    @Override
    public Consumer<T> andThen(Consumer<? super T> x$1) {
        return Consumer.super.andThen(x$1);
    }

    @Override
    public void accept(T x1) {
        this.sf.apply(x1);
    }

    public AsJavaConsumer(Function1<T, BoxedUnit> sf) {
        this.sf = sf;
    }
}

