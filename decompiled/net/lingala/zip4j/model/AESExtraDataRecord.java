/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model;

import net.lingala.zip4j.headers.HeaderSignature;
import net.lingala.zip4j.model.ZipHeader;
import net.lingala.zip4j.model.enums.AesKeyStrength;
import net.lingala.zip4j.model.enums.AesVersion;
import net.lingala.zip4j.model.enums.CompressionMethod;

public class AESExtraDataRecord
extends ZipHeader {
    private int dataSize;
    private AesVersion aesVersion;
    private String vendorID;
    private AesKeyStrength aesKeyStrength;
    private CompressionMethod compressionMethod;

    public AESExtraDataRecord() {
        this.setSignature(HeaderSignature.AES_EXTRA_DATA_RECORD);
        this.dataSize = 7;
        this.aesVersion = AesVersion.TWO;
        this.vendorID = "AE";
        this.aesKeyStrength = AesKeyStrength.KEY_STRENGTH_256;
        this.compressionMethod = CompressionMethod.DEFLATE;
    }

    public int getDataSize() {
        return this.dataSize;
    }

    public void setDataSize(int dataSize) {
        this.dataSize = dataSize;
    }

    public AesVersion getAesVersion() {
        return this.aesVersion;
    }

    public void setAesVersion(AesVersion aesVersion) {
        this.aesVersion = aesVersion;
    }

    public String getVendorID() {
        return this.vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
    }

    public AesKeyStrength getAesKeyStrength() {
        return this.aesKeyStrength;
    }

    public void setAesKeyStrength(AesKeyStrength aesKeyStrength) {
        this.aesKeyStrength = aesKeyStrength;
    }

    public CompressionMethod getCompressionMethod() {
        return this.compressionMethod;
    }

    public void setCompressionMethod(CompressionMethod compressionMethod) {
        this.compressionMethod = compressionMethod;
    }
}

