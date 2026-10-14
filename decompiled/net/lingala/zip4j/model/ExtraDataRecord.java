/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model;

import net.lingala.zip4j.model.ZipHeader;

public class ExtraDataRecord
extends ZipHeader {
    private long header;
    private int sizeOfData;
    private byte[] data;

    public long getHeader() {
        return this.header;
    }

    public void setHeader(long header) {
        this.header = header;
    }

    public int getSizeOfData() {
        return this.sizeOfData;
    }

    public void setSizeOfData(int sizeOfData) {
        this.sizeOfData = sizeOfData;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}

