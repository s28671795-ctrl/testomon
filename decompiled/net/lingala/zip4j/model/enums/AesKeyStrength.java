/*
 * Decompiled with CFR 0.152.
 */
package net.lingala.zip4j.model.enums;

public enum AesKeyStrength {
    KEY_STRENGTH_128(1, 8, 16, 16),
    KEY_STRENGTH_192(2, 12, 24, 24),
    KEY_STRENGTH_256(3, 16, 32, 32);

    private int rawCode;
    private int saltLength;
    private int macLength;
    private int keyLength;

    private AesKeyStrength(int rawCode, int saltLength, int macLength, int keyLength) {
        this.rawCode = rawCode;
        this.saltLength = saltLength;
        this.macLength = macLength;
        this.keyLength = keyLength;
    }

    public int getRawCode() {
        return this.rawCode;
    }

    public int getSaltLength() {
        return this.saltLength;
    }

    public int getMacLength() {
        return this.macLength;
    }

    public int getKeyLength() {
        return this.keyLength;
    }

    public static AesKeyStrength getAesKeyStrengthFromRawCode(int code) {
        for (AesKeyStrength aesKeyStrength : AesKeyStrength.values()) {
            if (aesKeyStrength.getRawCode() != code) continue;
            return aesKeyStrength;
        }
        return null;
    }
}

