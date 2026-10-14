/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.Supplier;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005]2A\u0001B\u0003\u0001\u001d!AA\u0006\u0001B\u0001B\u0003%Q\u0006C\u00031\u0001\u0011\u0005\u0011\u0007C\u00036\u0001\u0011\u0005aG\u0001\bBg*\u000bg/Y*vaBd\u0017.\u001a:\u000b\u0005\u00199\u0011A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005!I\u0011!\u00026bm\u0006D$B\u0001\u0006\f\u0003\u0019\u0019w.\u001c9bi*\tA\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005=\u00113c\u0001\u0001\u00111A\u0011\u0011CF\u0007\u0002%)\u00111\u0003F\u0001\u0005Y\u0006twMC\u0001\u0016\u0003\u0011Q\u0017M^1\n\u0005]\u0011\"AB(cU\u0016\u001cG\u000fE\u0002\u001a=\u0001j\u0011A\u0007\u0006\u00037q\t\u0001BZ;oGRLwN\u001c\u0006\u0003;Q\tA!\u001e;jY&\u0011qD\u0007\u0002\t'V\u0004\b\u000f\\5feB\u0011\u0011E\t\u0007\u0001\t\u0015\u0019\u0003A1\u0001%\u0005\u0005!\u0016CA\u0013*!\t1s%D\u0001\f\u0013\tA3BA\u0004O_RD\u0017N\\4\u0011\u0005\u0019R\u0013BA\u0016\f\u0005\r\te._\u0001\u0003g\u001a\u00042A\n\u0018!\u0013\ty3BA\u0005Gk:\u001cG/[8oa\u00051A(\u001b8jiz\"\"A\r\u001b\u0011\u0007M\u0002\u0001%D\u0001\u0006\u0011\u0015a#\u00011\u0001.\u0003\r9W\r\u001e\u000b\u0002A\u0001")
public class AsJavaSupplier<T>
implements Supplier<T> {
    private final Function0<T> sf;

    @Override
    public T get() {
        return this.sf.apply();
    }

    public AsJavaSupplier(Function0<T> sf) {
        this.sf = sf;
    }
}

