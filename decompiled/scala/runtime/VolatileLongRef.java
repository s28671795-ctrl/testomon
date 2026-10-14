/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class VolatileLongRef
implements Serializable {
    private static final long serialVersionUID = -3567869820105829499L;
    public volatile long elem;

    public VolatileLongRef(long elem) {
        this.elem = elem;
    }

    public String toString() {
        return Long.toString(this.elem);
    }

    public static VolatileLongRef create(long e) {
        return new VolatileLongRef(e);
    }

    public static VolatileLongRef zero() {
        return new VolatileLongRef(0L);
    }
}

