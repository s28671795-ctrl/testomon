/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.ObjLongConsumer;
import scala.Function2;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u00053A\u0001B\u0003\u0001\u001d!AA\u0006\u0001B\u0001B\u0003%Q\u0006C\u00037\u0001\u0011\u0005q\u0007C\u0003<\u0001\u0011\u0005AHA\u000bBg*\u000bg/Y(cU2{gnZ\"p]N,X.\u001a:\u000b\u0005\u00199\u0011A\u00064v]\u000e$\u0018n\u001c8D_:4XM\u001d;fe&k\u0007\u000f\\:\u000b\u0005!I\u0011!\u00026bm\u0006D$B\u0001\u0006\f\u0003\u0019\u0019w.\u001c9bi*\tA\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005=\u00113c\u0001\u0001\u00111A\u0011\u0011CF\u0007\u0002%)\u00111\u0003F\u0001\u0005Y\u0006twMC\u0001\u0016\u0003\u0011Q\u0017M^1\n\u0005]\u0011\"AB(cU\u0016\u001cG\u000fE\u0002\u001a=\u0001j\u0011A\u0007\u0006\u00037q\t\u0001BZ;oGRLwN\u001c\u0006\u0003;Q\tA!\u001e;jY&\u0011qD\u0007\u0002\u0010\u001f\nTGj\u001c8h\u0007>t7/^7feB\u0011\u0011E\t\u0007\u0001\t\u0015\u0019\u0003A1\u0001%\u0005\u0005!\u0016CA\u0013*!\t1s%D\u0001\f\u0013\tA3BA\u0004O_RD\u0017N\\4\u0011\u0005\u0019R\u0013BA\u0016\f\u0005\r\te._\u0001\u0003g\u001a\u0004RA\n\u0018!aMJ!aL\u0006\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004C\u0001\u00142\u0013\t\u00114B\u0001\u0003M_:<\u0007C\u0001\u00145\u0013\t)4B\u0001\u0003V]&$\u0018A\u0002\u001fj]&$h\b\u0006\u00029uA\u0019\u0011\b\u0001\u0011\u000e\u0003\u0015AQ\u0001\f\u0002A\u00025\na!Y2dKB$HcA\u001a>\u007f!)ah\u0001a\u0001A\u0005\u0011\u00010\r\u0005\u0006\u0001\u000e\u0001\r\u0001M\u0001\u0003qJ\u0002")
public class AsJavaObjLongConsumer<T>
implements ObjLongConsumer<T> {
    private final Function2<T, Object, BoxedUnit> sf;

    @Override
    public void accept(T x1, long x2) {
        this.sf.apply(x1, BoxesRunTime.boxToLong(x2));
    }

    public AsJavaObjLongConsumer(Function2<T, Object, BoxedUnit> sf) {
        this.sf = sf;
    }
}

