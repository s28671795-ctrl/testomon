/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class VolatileDoubleRef
implements Serializable {
    private static final long serialVersionUID = 8304402127373655534L;
    public volatile double elem;

    public VolatileDoubleRef(double elem) {
        this.elem = elem;
    }

    public String toString() {
        return Double.toString(this.elem);
    }

    public static VolatileDoubleRef create(double e) {
        return new VolatileDoubleRef(e);
    }

    public static VolatileDoubleRef zero() {
        return new VolatileDoubleRef(0.0);
    }
}

