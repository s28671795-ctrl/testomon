/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleSupplier;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000592A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005QF\u0001\u000bBg*\u000bg/\u0019#pk\ndWmU;qa2LWM\u001d\u0006\u0003\r\u001d\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003\u0011%\tQA[1wCbR!AC\u0006\u0002\r\r|W\u000e]1u\u0015\u0005a\u0011!B:dC2\f7\u0001A\n\u0004\u0001=9\u0002C\u0001\t\u0016\u001b\u0005\t\"B\u0001\n\u0014\u0003\u0011a\u0017M\\4\u000b\u0003Q\tAA[1wC&\u0011a#\u0005\u0002\u0007\u001f\nTWm\u0019;\u0011\u0005aiR\"A\r\u000b\u0005iY\u0012\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005q\u0019\u0012\u0001B;uS2L!AH\r\u0003\u001d\u0011{WO\u00197f'V\u0004\b\u000f\\5fe\u0006\u00111O\u001a\t\u0004C\t\"S\"A\u0006\n\u0005\rZ!!\u0003$v]\u000e$\u0018n\u001c81!\t\tS%\u0003\u0002'\u0017\t1Ai\\;cY\u0016\fa\u0001P5oSRtDCA\u0015,!\tQ\u0003!D\u0001\u0006\u0011\u0015y\"\u00011\u0001!\u0003-9W\r^!t\t>,(\r\\3\u0015\u0003\u0011\u0002")
public class AsJavaDoubleSupplier
implements DoubleSupplier {
    private final Function0<Object> sf;

    @Override
    public double getAsDouble() {
        return this.sf.apply$mcD$sp();
    }

    public AsJavaDoubleSupplier(Function0<Object> sf) {
        this.sf = sf;
    }
}

