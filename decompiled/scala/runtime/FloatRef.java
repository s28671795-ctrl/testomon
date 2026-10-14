/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class FloatRef
implements Serializable {
    private static final long serialVersionUID = -5793980990371366933L;
    public float elem;

    public FloatRef(float elem) {
        this.elem = elem;
    }

    public String toString() {
        return Float.toString(this.elem);
    }

    public static FloatRef create(float e) {
        return new FloatRef(e);
    }

    public static FloatRef zero() {
        return new FloatRef(0.0f);
    }
}

