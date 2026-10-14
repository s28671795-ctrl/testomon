/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model.enums;

public enum RandomAccessFileMode {
    READ("r"),
    WRITE("rw");

    private String value;

    private RandomAccessFileMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

