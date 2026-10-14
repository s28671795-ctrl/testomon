/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.asn1.types;

public enum ASN1TagClass {
    UNIVERSAL(0),
    APPLICATION(64),
    CONTEXT_SPECIFIC(128),
    PRIVATE(192);

    private int value;

    private ASN1TagClass(int value) {
        this.value = value;
    }

    public static ASN1TagClass parseClass(byte tagByte) {
        int classValue = tagByte & 0xC0;
        for (ASN1TagClass asn1TagClass : ASN1TagClass.values()) {
            if (asn1TagClass.value != classValue) continue;
            return asn1TagClass;
        }
        throw new IllegalStateException("Could not parse ASN.1 Tag Class (should be impossible)");
    }

    public int getValue() {
        return this.value;
    }
}

