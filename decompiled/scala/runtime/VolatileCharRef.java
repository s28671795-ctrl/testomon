/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class VolatileCharRef
implements Serializable {
    private static final long serialVersionUID = 6537214938268005702L;
    public volatile char elem;

    public VolatileCharRef(char elem) {
        this.elem = elem;
    }

    public String toString() {
        return Character.toString(this.elem);
    }

    public static VolatileCharRef create(char e) {
        return new VolatileCharRef(e);
    }

    public static VolatileCharRef zero() {
        return new VolatileCharRef('\u0000');
    }
}

