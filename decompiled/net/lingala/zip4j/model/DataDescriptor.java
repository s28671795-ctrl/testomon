/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model;

import net.lingala.zip4j.model.ZipHeader;

public class DataDescriptor
extends ZipHeader {
    private long crc;
    private long compressedSize;
    private long uncompressedSize;

    public long getCrc() {
        return this.crc;
    }

    public void setCrc(long crc) {
        this.crc = crc;
    }

    public long getCompressedSize() {
        return this.compressedSize;
    }

    public void setCompressedSize(long compressedSize) {
        this.compressedSize = compressedSize;
    }

    public long getUncompressedSize() {
        return this.uncompressedSize;
    }

    public void setUncompressedSize(long uncompressedSize) {
        this.uncompressedSize = uncompressedSize;
    }
}

