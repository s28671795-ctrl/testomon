/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class MaterializationException$
implements Serializable {
    public static final MaterializationException$ MODULE$ = new MaterializationException$();

    public Throwable $lessinit$greater$default$2() {
        return null;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(MaterializationException$.class);
    }

    private MaterializationException$() {
    }
}

