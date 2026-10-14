/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class BooleanRef
implements Serializable {
    private static final long serialVersionUID = -5730524563015615974L;
    public boolean elem;

    public BooleanRef(boolean elem) {
        this.elem = elem;
    }

    public String toString() {
        return String.valueOf(this.elem);
    }

    public static BooleanRef create(boolean e) {
        return new BooleanRef(e);
    }

    public static BooleanRef zero() {
        return new BooleanRef(false);
    }
}

