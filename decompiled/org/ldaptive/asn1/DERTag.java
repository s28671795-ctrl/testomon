/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

public interface DERTag {
    public static final int ASN_CONSTRUCTED = 32;

    public int getTagNo();

    public String name();

    public boolean isConstructed();

    public int getTagByte();
}

