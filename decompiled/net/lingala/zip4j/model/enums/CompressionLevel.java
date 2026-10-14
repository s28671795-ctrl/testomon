/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model.enums;

public enum CompressionLevel {
    NO_COMPRESSION(0),
    FASTEST(1),
    FASTER(2),
    FAST(3),
    MEDIUM_FAST(4),
    NORMAL(5),
    HIGHER(6),
    MAXIMUM(7),
    PRE_ULTRA(8),
    ULTRA(9);

    private final int level;

    private CompressionLevel(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }
}

