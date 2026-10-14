/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class VolatileFloatRef
implements Serializable {
    private static final long serialVersionUID = -5793980990371366933L;
    public volatile float elem;

    public VolatileFloatRef(float elem) {
        this.elem = elem;
    }

    public String toString() {
        return Float.toString(this.elem);
    }

    public static VolatileFloatRef create(float e) {
        return new VolatileFloatRef(e);
    }

    public static VolatileFloatRef zero() {
        return new VolatileFloatRef(0.0f);
    }
}

