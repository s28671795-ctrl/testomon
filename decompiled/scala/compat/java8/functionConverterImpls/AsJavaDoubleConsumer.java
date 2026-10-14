/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleConsumer;
import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005M2A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003+\u0001\u0011\u00051\u0006C\u00030\u0001\u0011\u0005\u0001G\u0001\u000bBg*\u000bg/\u0019#pk\ndWmQ8ogVlWM\u001d\u0006\u0003\r\u001d\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003\u0011%\tQA[1wCbR!AC\u0006\u0002\r\r|W\u000e]1u\u0015\u0005a\u0011!B:dC2\f7\u0001A\n\u0004\u0001=9\u0002C\u0001\t\u0016\u001b\u0005\t\"B\u0001\n\u0014\u0003\u0011a\u0017M\\4\u000b\u0003Q\tAA[1wC&\u0011a#\u0005\u0002\u0007\u001f\nTWm\u0019;\u0011\u0005aiR\"A\r\u000b\u0005iY\u0012\u0001\u00034v]\u000e$\u0018n\u001c8\u000b\u0005q\u0019\u0012\u0001B;uS2L!AH\r\u0003\u001d\u0011{WO\u00197f\u0007>t7/^7fe\u0006\u00111O\u001a\t\u0005C\t\"s%D\u0001\f\u0013\t\u00193BA\u0005Gk:\u001cG/[8ocA\u0011\u0011%J\u0005\u0003M-\u0011a\u0001R8vE2,\u0007CA\u0011)\u0013\tI3B\u0001\u0003V]&$\u0018A\u0002\u001fj]&$h\b\u0006\u0002-]A\u0011Q\u0006A\u0007\u0002\u000b!)qD\u0001a\u0001A\u00051\u0011mY2faR$\"aJ\u0019\t\u000bI\u001a\u0001\u0019\u0001\u0013\u0002\u0005a\f\u0004")
public class AsJavaDoubleConsumer
implements DoubleConsumer {
    private final Function1<Object, BoxedUnit> sf;

    @Override
    public DoubleConsumer andThen(DoubleConsumer x$1) {
        return DoubleConsumer.super.andThen(x$1);
    }

    @Override
    public void accept(double x1) {
        this.sf.apply$mcVD$sp(x1);
    }

    public AsJavaDoubleConsumer(Function1<Object, BoxedUnit> sf) {
        this.sf = sf;
    }
}

