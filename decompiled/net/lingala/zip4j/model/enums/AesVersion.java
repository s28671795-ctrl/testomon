/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model.enums;

import net.lingala.zip4j.exception.ZipException;

public enum AesVersion {
    ONE(1),
    TWO(2);

    private int versionNumber;

    private AesVersion(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public int getVersionNumber() {
        return this.versionNumber;
    }

    public static AesVersion getFromVersionNumber(int versionNumber) throws ZipException {
        for (AesVersion aesVersion : AesVersion.values()) {
            if (aesVersion.versionNumber != versionNumber) continue;
            return aesVersion;
        }
        throw new ZipException("Unsupported Aes version");
    }
}

