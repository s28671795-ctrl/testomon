/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

public enum Access {
    PRIVATE,
    PROTECTED,
    DEFAULT,
    PUBLIC;


    public static Access fromString(String s2) {
        if ("private".equals(s2)) {
            return PRIVATE;
        }
        if ("protected".equals(s2)) {
            return PROTECTED;
        }
        if ("public".equals(s2)) {
            return PUBLIC;
        }
        throw new IllegalArgumentException(s2);
    }

    public String toString() {
        return this.name().toLowerCase();
    }
}

