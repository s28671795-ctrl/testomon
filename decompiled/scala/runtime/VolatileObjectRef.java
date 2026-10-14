/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class VolatileObjectRef<T>
implements Serializable {
    private static final long serialVersionUID = -9055728157600312291L;
    public volatile T elem;

    public VolatileObjectRef(T elem) {
        this.elem = elem;
    }

    public String toString() {
        return String.valueOf(this.elem);
    }

    public static <U> VolatileObjectRef<U> create(U e) {
        return new VolatileObjectRef<U>(e);
    }

    public static VolatileObjectRef<Object> zero() {
        return new VolatileObjectRef<Object>(null);
    }
}

