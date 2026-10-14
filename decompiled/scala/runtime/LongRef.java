/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class LongRef
implements Serializable {
    private static final long serialVersionUID = -3567869820105829499L;
    public long elem;

    public LongRef(long elem) {
        this.elem = elem;
    }

    public String toString() {
        return Long.toString(this.elem);
    }

    public static LongRef create(long e) {
        return new LongRef(e);
    }

    public static LongRef zero() {
        return new LongRef(0L);
    }
}

