/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class IntRef
implements Serializable {
    private static final long serialVersionUID = 1488197132022872888L;
    public int elem;

    public IntRef(int elem) {
        this.elem = elem;
    }

    public String toString() {
        return Integer.toString(this.elem);
    }

    public static IntRef create(int e) {
        return new IntRef(e);
    }

    public static IntRef zero() {
        return new IntRef(0);
    }
}

