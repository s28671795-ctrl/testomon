/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model;

import net.lingala.zip4j.model.ZipHeader;

public class DigitalSignature
extends ZipHeader {
    private int sizeOfData;
    private String signatureData;

    public int getSizeOfData() {
        return this.sizeOfData;
    }

    public void setSizeOfData(int sizeOfData) {
        this.sizeOfData = sizeOfData;
    }

    public String getSignatureData() {
        return this.signatureData;
    }

    public void setSignatureData(String signatureData) {
        this.signatureData = signatureData;
    }
}

