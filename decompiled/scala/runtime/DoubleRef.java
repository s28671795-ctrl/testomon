/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class DoubleRef
implements Serializable {
    private static final long serialVersionUID = 8304402127373655534L;
    public double elem;

    public DoubleRef(double elem) {
        this.elem = elem;
    }

    public String toString() {
        return Double.toString(this.elem);
    }

    public static DoubleRef create(double e) {
        return new DoubleRef(e);
    }

    public static DoubleRef zero() {
        return new DoubleRef(0.0);
    }
}

