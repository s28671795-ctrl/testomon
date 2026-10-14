/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

public final class StashOverflowException$
implements Serializable {
    public static final StashOverflowException$ MODULE$ = new StashOverflowException$();

    public Throwable $lessinit$greater$default$2() {
        return null;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(StashOverflowException$.class);
    }

    private StashOverflowException$() {
    }
}

