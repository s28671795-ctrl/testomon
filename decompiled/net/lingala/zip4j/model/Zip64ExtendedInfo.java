/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model;

import net.lingala.zip4j.model.ZipHeader;

public class Zip64ExtendedInfo
extends ZipHeader {
    private int size;
    private long compressedSize = -1L;
    private long uncompressedSize = -1L;
    private long offsetLocalHeader = -1L;
    private int diskNumberStart = -1;

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public long getOffsetLocalHeader() {
        return this.offsetLocalHeader;
    }

    public void setOffsetLocalHeader(long offsetLocalHeader) {
        this.offsetLocalHeader = offsetLocalHeader;
    }

    public int getDiskNumberStart() {
        return this.diskNumberStart;
    }

    public void setDiskNumberStart(int diskNumberStart) {
        this.diskNumberStart = diskNumberStart;
    }
}

