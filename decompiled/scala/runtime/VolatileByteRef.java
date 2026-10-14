/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class VolatileByteRef
implements Serializable {
    private static final long serialVersionUID = -100666928446877072L;
    public volatile byte elem;

    public VolatileByteRef(byte elem) {
        this.elem = elem;
    }

    public String toString() {
        return Byte.toString(this.elem);
    }

    public static VolatileByteRef create(byte e) {
        return new VolatileByteRef(e);
    }

    public static VolatileByteRef zero() {
        return new VolatileByteRef(0);
    }
}

