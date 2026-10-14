/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class VolatileBooleanRef
implements Serializable {
    private static final long serialVersionUID = -5730524563015615974L;
    public volatile boolean elem;

    public VolatileBooleanRef(boolean elem) {
        this.elem = elem;
    }

    public String toString() {
        return String.valueOf(this.elem);
    }

    public static VolatileBooleanRef create(boolean e) {
        return new VolatileBooleanRef(e);
    }

    public static VolatileBooleanRef zero() {
        return new VolatileBooleanRef(false);
    }
}

