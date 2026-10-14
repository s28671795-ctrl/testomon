/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongBinaryOperator;
import scala.Function2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005I2A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005QF\u0001\rBg*\u000bg/\u0019'p]\u001e\u0014\u0015N\\1ss>\u0003XM]1u_JT!AB\u0004\u0002-\u0019,hn\u0019;j_:\u001cuN\u001c<feR,'/S7qYNT!\u0001C\u0005\u0002\u000b)\fg/\u0019\u001d\u000b\u0005)Y\u0011AB2p[B\fGOC\u0001\r\u0003\u0015\u00198-\u00197b\u0007\u0001\u00192\u0001A\b\u0018!\t\u0001R#D\u0001\u0012\u0015\t\u00112#\u0001\u0003mC:<'\"\u0001\u000b\u0002\t)\fg/Y\u0005\u0003-E\u0011aa\u00142kK\u000e$\bC\u0001\r\u001e\u001b\u0005I\"B\u0001\u000e\u001c\u0003!1WO\\2uS>t'B\u0001\u000f\u0014\u0003\u0011)H/\u001b7\n\u0005yI\"A\u0005'p]\u001e\u0014\u0015N\\1ss>\u0003XM]1u_J\f!a\u001d4\u0011\u000b\u0005\u0012C\u0005\n\u0013\u000e\u0003-I!aI\u0006\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004CA\u0011&\u0013\t13B\u0001\u0003M_:<\u0017A\u0002\u001fj]&$h\b\u0006\u0002*WA\u0011!\u0006A\u0007\u0002\u000b!)qD\u0001a\u0001A\u0005Y\u0011\r\u001d9ms\u0006\u001bHj\u001c8h)\r!c\u0006\r\u0005\u0006_\r\u0001\r\u0001J\u0001\u0003qFBQ!M\u0002A\u0002\u0011\n!\u0001\u001f\u001a")
public class AsJavaLongBinaryOperator
implements LongBinaryOperator {
    private final Function2<Object, Object, Object> sf;

    @Override
    public long applyAsLong(long x1, long x2) {
        return this.sf.apply$mcJJJ$sp(x1, x2);
    }

    public AsJavaLongBinaryOperator(Function2<Object, Object, Object> sf) {
        this.sf = sf;
    }
}

