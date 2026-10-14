/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class ClassTagSeqFactory$
implements Serializable {
    public static final ClassTagSeqFactory$ MODULE$ = new ClassTagSeqFactory$();

    private Object writeReplace() {
        return new ModuleSerializationProxy(ClassTagSeqFactory$.class);
    }

    private ClassTagSeqFactory$() {
    }
}

