/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class ByteRef
implements Serializable {
    private static final long serialVersionUID = -100666928446877072L;
    public byte elem;

    public ByteRef(byte elem) {
        this.elem = elem;
    }

    public String toString() {
        return Byte.toString(this.elem);
    }

    public static ByteRef create(byte e) {
        return new ByteRef(e);
    }

    public static ByteRef zero() {
        return new ByteRef(0);
    }
}

