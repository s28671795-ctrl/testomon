/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import java.io.Serializable;
import scala.runtime.ModuleSerializationProxy;

@InternalApi
public final class VirtualProcessor$
implements Serializable {
    public static final VirtualProcessor$ MODULE$ = new VirtualProcessor$();

    public final boolean Debug() {
        return false;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(VirtualProcessor$.class);
    }

    private VirtualProcessor$() {
    }
}

