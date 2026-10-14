/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.asn1.types;

public enum ASN1Encoding {
    PRIMITIVE(0),
    CONSTRUCTED(32);

    private int value;

    private ASN1Encoding(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static ASN1Encoding parseEncoding(byte tagByte) {
        if ((tagByte & 0x20) == 0) {
            return PRIMITIVE;
        }
        return CONSTRUCTED;
    }
}

