/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class VolatileIntRef
implements Serializable {
    private static final long serialVersionUID = 1488197132022872888L;
    public volatile int elem;

    public VolatileIntRef(int elem) {
        this.elem = elem;
    }

    public String toString() {
        return Integer.toString(this.elem);
    }

    public static VolatileIntRef create(int e) {
        return new VolatileIntRef(e);
    }

    public static VolatileIntRef zero() {
        return new VolatileIntRef(0);
    }
}

