/*
 * Decompiled with CFR 0.152.
 */
package scala.reflect.macros.internal;

import scala.annotation.Annotation;
import scala.annotation.StaticAnnotation;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00112Q\u0001B\u0003\u0003\u00175A\u0001b\u0006\u0001\u0003\u0006\u0004%\t!\u0007\u0005\t=\u0001\u0011\t\u0011)A\u00055!)q\u0004\u0001C\u0001A\tIQ.Y2s_&k\u0007\u000f\u001c\u0006\u0003\r\u001d\t\u0001\"\u001b8uKJt\u0017\r\u001c\u0006\u0003\u0011%\ta!\\1de>\u001c(B\u0001\u0006\f\u0003\u001d\u0011XM\u001a7fGRT\u0011\u0001D\u0001\u0006g\u000e\fG.Y\n\u0004\u00019!\u0002CA\b\u0013\u001b\u0005\u0001\"BA\t\f\u0003)\tgN\\8uCRLwN\\\u0005\u0003'A\u0011!\"\u00118o_R\fG/[8o!\tyQ#\u0003\u0002\u0017!\t\u00012\u000b^1uS\u000e\feN\\8uCRLwN\\\u0001\u0015e\u00164WM]3oG\u0016$v.T1de>LU\u000e\u001d7\u0004\u0001U\t!\u0004\u0005\u0002\u001c95\t1\"\u0003\u0002\u001e\u0017\t\u0019\u0011I\\=\u0002+I,g-\u001a:f]\u000e,Gk\\'bGJ|\u0017*\u001c9mA\u00051A(\u001b8jiz\"\"!I\u0012\u0011\u0005\t\u0002Q\"A\u0003\t\u000b]\u0019\u0001\u0019\u0001\u000e")
public final class macroImpl
extends Annotation
implements StaticAnnotation {
    private final Object referenceToMacroImpl;

    public Object referenceToMacroImpl() {
        return this.referenceToMacroImpl;
    }

    public macroImpl(Object referenceToMacroImpl) {
        this.referenceToMacroImpl = referenceToMacroImpl;
    }
}

