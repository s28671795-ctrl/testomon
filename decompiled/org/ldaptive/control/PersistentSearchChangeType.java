/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.control;

public enum PersistentSearchChangeType {
    ADD(1),
    DELETE(2),
    MODIFY(4),
    MODDN(8);

    private final int value;

    private PersistentSearchChangeType(int i) {
        this.value = i;
    }

    public int value() {
        return this.value;
    }

    public static PersistentSearchChangeType valueOf(int i) {
        for (PersistentSearchChangeType ct : PersistentSearchChangeType.values()) {
            if (ct.value() != i) continue;
            return ct;
        }
        return null;
    }
}

