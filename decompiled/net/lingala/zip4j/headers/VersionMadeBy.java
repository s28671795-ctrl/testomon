/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.headers;

public enum VersionMadeBy {
    SPECIFICATION_VERSION(51),
    WINDOWS(0),
    UNIX(3);

    private final byte code;

    private VersionMadeBy(byte code) {
        this.code = code;
    }

    public byte getCode() {
        return this.code;
    }
}

