/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model.enums;

import net.lingala.zip4j.exception.ZipException;

public enum CompressionMethod {
    STORE(0),
    DEFLATE(8),
    AES_INTERNAL_ONLY(99);

    private int code;

    private CompressionMethod(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static CompressionMethod getCompressionMethodFromCode(int code) throws ZipException {
        for (CompressionMethod compressionMethod : CompressionMethod.values()) {
            if (compressionMethod.getCode() != code) continue;
            return compressionMethod;
        }
        throw new ZipException("Unknown compression method", ZipException.Type.UNKNOWN_COMPRESSION_METHOD);
    }
}

