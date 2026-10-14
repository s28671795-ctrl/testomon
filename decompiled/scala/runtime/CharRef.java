/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import java.io.Serializable;

public final class CharRef
implements Serializable {
    private static final long serialVersionUID = 6537214938268005702L;
    public char elem;

    public CharRef(char elem) {
        this.elem = elem;
    }

    public String toString() {
        return Character.toString(this.elem);
    }

    public static CharRef create(char e) {
        return new CharRef(e);
    }

    public static CharRef zero() {
        return new CharRef('\u0000');
    }
}

