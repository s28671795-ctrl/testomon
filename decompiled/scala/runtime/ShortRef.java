/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class ShortRef
implements Serializable {
    private static final long serialVersionUID = 4218441291229072313L;
    public short elem;

    public ShortRef(short elem) {
        this.elem = elem;
    }

    public String toString() {
        return Short.toString(this.elem);
    }

    public static ShortRef create(short e) {
        return new ShortRef(e);
    }

    public static ShortRef zero() {
        return new ShortRef(0);
    }
}

