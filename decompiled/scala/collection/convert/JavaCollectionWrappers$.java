/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert;

import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;
import scala.util.control.ControlThrowable;

public final class JavaCollectionWrappers$
implements Serializable {
    public static final JavaCollectionWrappers$ MODULE$ = new JavaCollectionWrappers$();
    private static final long serialVersionUID = 3L;
    private static final ControlThrowable scala$collection$convert$JavaCollectionWrappers$$PutNull = new ControlThrowable(){};

    public ControlThrowable scala$collection$convert$JavaCollectionWrappers$$PutNull() {
        return scala$collection$convert$JavaCollectionWrappers$$PutNull;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(JavaCollectionWrappers$.class);
    }

    private JavaCollectionWrappers$() {
    }
}

