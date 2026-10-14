/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.headers;

public enum VersionNeededToExtract {
    DEFAULT(10),
    DEFLATE_COMPRESSED(20),
    ZIP_64_FORMAT(45),
    AES_ENCRYPTED(51);

    private final int code;

    private VersionNeededToExtract(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}

