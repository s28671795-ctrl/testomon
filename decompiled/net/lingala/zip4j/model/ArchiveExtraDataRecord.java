/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model;

import net.lingala.zip4j.model.ZipHeader;

public class ArchiveExtraDataRecord
extends ZipHeader {
    private int extraFieldLength;
    private String extraFieldData;

    public int getExtraFieldLength() {
        return this.extraFieldLength;
    }

    public void setExtraFieldLength(int extraFieldLength) {
        this.extraFieldLength = extraFieldLength;
    }

    public String getExtraFieldData() {
        return this.extraFieldData;
    }

    public void setExtraFieldData(String extraFieldData) {
        this.extraFieldData = extraFieldData;
    }
}

