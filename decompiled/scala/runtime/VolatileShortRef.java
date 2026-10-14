/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class VolatileShortRef
implements Serializable {
    private static final long serialVersionUID = 4218441291229072313L;
    public volatile short elem;

    public VolatileShortRef(short elem) {
        this.elem = elem;
    }

    public String toString() {
        return Short.toString(this.elem);
    }

    public static VolatileShortRef create(short e) {
        return new VolatileShortRef(e);
    }

    public static VolatileShortRef zero() {
        return new VolatileShortRef(0);
    }
}

