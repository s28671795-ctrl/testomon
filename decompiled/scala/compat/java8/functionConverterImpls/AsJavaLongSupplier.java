/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongSupplier;
import scala.Function0;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u000592A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005QF\u0001\nBg*\u000bg/\u0019'p]\u001e\u001cV\u000f\u001d9mS\u0016\u0014(B\u0001\u0004\b\u0003Y1WO\\2uS>t7i\u001c8wKJ$XM]%na2\u001c(B\u0001\u0005\n\u0003\u0015Q\u0017M^19\u0015\tQ1\"\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002\u0019\u0005)1oY1mC\u000e\u00011c\u0001\u0001\u0010/A\u0011\u0001#F\u0007\u0002#)\u0011!cE\u0001\u0005Y\u0006twMC\u0001\u0015\u0003\u0011Q\u0017M^1\n\u0005Y\t\"AB(cU\u0016\u001cG\u000f\u0005\u0002\u0019;5\t\u0011D\u0003\u0002\u001b7\u0005Aa-\u001e8di&|gN\u0003\u0002\u001d'\u0005!Q\u000f^5m\u0013\tq\u0012D\u0001\u0007M_:<7+\u001e9qY&,'/\u0001\u0002tMB\u0019\u0011E\t\u0013\u000e\u0003-I!aI\u0006\u0003\u0013\u0019+hn\u0019;j_:\u0004\u0004CA\u0011&\u0013\t13B\u0001\u0003M_:<\u0017A\u0002\u001fj]&$h\b\u0006\u0002*WA\u0011!\u0006A\u0007\u0002\u000b!)qD\u0001a\u0001A\u0005Iq-\u001a;Bg2{gn\u001a\u000b\u0002I\u0001")
public class AsJavaLongSupplier
implements LongSupplier {
    private final Function0<Object> sf;

    @Override
    public long getAsLong() {
        return this.sf.apply$mcJ$sp();
    }

    public AsJavaLongSupplier(Function0<Object> sf) {
        this.sf = sf;
    }
}

